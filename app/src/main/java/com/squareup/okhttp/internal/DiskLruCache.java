package com.squareup.okhttp.internal;

import com.squareup.okhttp.internal.io.FileSystem;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class DiskLruCache implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final Pattern LEGAL_KEY_PATTERN;
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final Sink NULL_SINK;
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Runnable cleanupRunnable;
    private boolean closed;
    private final File directory;
    private final Executor executor;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private BufferedSink journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries;
    private long maxSize;
    private long nextSequenceNumber;
    private int redundantOpCount;
    private long size;
    private final int valueCount;

    /* renamed from: com.squareup.okhttp.internal.DiskLruCache.2 */
    class AnonymousClass2 extends FaultHidingSink {
        static final /* synthetic */ boolean $assertionsDisabled;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r0 = 0;
            r1 = com.squareup.okhttp.internal.DiskLruCache.class;
            if (r1 == 0) goto L_0x000a;
        L_0x0007:
            $assertionsDisabled = r0;
        L_0x000a:
            r0 = 1;
            goto L_0x0007;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.2.<clinit>():void");
        }

        AnonymousClass2(Sink delegate) {
            super(delegate);
        }

        protected void onException(IOException e) {
            if ($assertionsDisabled || Thread.holdsLock(DiskLruCache.this)) {
                DiskLruCache.this.hasJournalErrors = true;
                return;
            }
            throw new AssertionError();
        }
    }

    public final class Editor {
        private boolean committed;
        private final Entry entry;
        private boolean hasErrors;
        private final boolean[] written;

        /* renamed from: com.squareup.okhttp.internal.DiskLruCache.Editor.1 */
        class AnonymousClass1 extends FaultHidingSink {
            AnonymousClass1(Sink delegate) {
                super(delegate);
            }

            protected void onException(IOException e) {
                synchronized (DiskLruCache.this) {
                    Editor.this.hasErrors = true;
                }
            }
        }

        private Editor(Entry entry) {
            boolean[] zArr;
            this.entry = entry;
            if (entry.readable) {
                zArr = null;
            } else {
                zArr = new boolean[DiskLruCache.this.valueCount];
            }
            this.written = zArr;
        }

        public Source newSource(int index) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor == this) {
                    if (this.entry.readable) {
                        try {
                            Source source = DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[index]);
                            return source;
                        } catch (FileNotFoundException e) {
                            return null;
                        }
                    }
                    return null;
                } else {
                    throw new IllegalStateException();
                }
            }
        }

        public Sink newSink(int index) throws IOException {
            Sink anonymousClass1;
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor == this) {
                    if (!this.entry.readable) {
                        this.written[index] = true;
                    }
                    try {
                        anonymousClass1 = new AnonymousClass1(DiskLruCache.this.fileSystem.sink(this.entry.dirtyFiles[index]));
                    } catch (FileNotFoundException e) {
                        return DiskLruCache.NULL_SINK;
                    }
                }
                throw new IllegalStateException();
            }
            return anonymousClass1;
        }

        public void commit() throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.hasErrors) {
                    DiskLruCache.this.completeEdit(this, DiskLruCache.$assertionsDisabled);
                    DiskLruCache.this.removeEntry(this.entry);
                } else {
                    DiskLruCache.this.completeEdit(this, true);
                }
                this.committed = true;
            }
        }

        public void abort() throws IOException {
            synchronized (DiskLruCache.this) {
                DiskLruCache.this.completeEdit(this, DiskLruCache.$assertionsDisabled);
            }
        }

        public void abortUnlessCommitted() {
            synchronized (DiskLruCache.this) {
                if (!this.committed) {
                    try {
                        DiskLruCache.this.completeEdit(this, DiskLruCache.$assertionsDisabled);
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    private final class Entry {
        private final File[] cleanFiles;
        private Editor currentEditor;
        private final File[] dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        private Entry(String key) {
            this.key = key;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            StringBuilder fileBuilder = new StringBuilder(key).append('.');
            int truncateTo = fileBuilder.length();
            for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                fileBuilder.append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.setLength(truncateTo);
            }
        }

        private void setLengths(String[] strings) throws IOException {
            if (strings.length == DiskLruCache.this.valueCount) {
                int i = 0;
                while (i < strings.length) {
                    try {
                        this.lengths[i] = Long.parseLong(strings[i]);
                        i++;
                    } catch (NumberFormatException e) {
                        throw invalidLengths(strings);
                    }
                }
                return;
            }
            throw invalidLengths(strings);
        }

        void writeLengths(BufferedSink writer) throws IOException {
            for (long length : this.lengths) {
                writer.writeByte(32).writeDecimalLong(length);
            }
        }

        private IOException invalidLengths(String[] strings) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strings));
        }

        Snapshot snapshot() {
            if (Thread.holdsLock(DiskLruCache.this)) {
                Source[] sources = new Source[DiskLruCache.this.valueCount];
                long[] lengths = (long[]) this.lengths.clone();
                int i = 0;
                while (i < DiskLruCache.this.valueCount) {
                    try {
                        sources[i] = DiskLruCache.this.fileSystem.source(this.cleanFiles[i]);
                        i++;
                    } catch (FileNotFoundException e) {
                        i = 0;
                        while (i < DiskLruCache.this.valueCount && sources[i] != null) {
                            Util.closeQuietly(sources[i]);
                            i++;
                        }
                        return null;
                    }
                }
                return new Snapshot(this.key, this.sequenceNumber, sources, lengths, null);
            }
            throw new AssertionError();
        }
    }

    public final class Snapshot implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final Source[] sources;

        private Snapshot(String key, long sequenceNumber, Source[] sources, long[] lengths) {
            this.key = key;
            this.sequenceNumber = sequenceNumber;
            this.sources = sources;
            this.lengths = lengths;
        }

        public String key() {
            return this.key;
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public Source getSource(int index) {
            return this.sources[index];
        }

        public long getLength(int index) {
            return this.lengths[index];
        }

        public void close() {
            for (Closeable in : this.sources) {
                Util.closeQuietly(in);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 0;
        r1 = com.squareup.okhttp.internal.DiskLruCache.class;
        if (r1 == 0) goto L_0x001a;
    L_0x0007:
        $assertionsDisabled = r0;
        r0 = "[a-z0-9_-]{1,120}";
        r0 = java.util.regex.Pattern.compile(r0);
        LEGAL_KEY_PATTERN = r0;
        r0 = new com.squareup.okhttp.internal.DiskLruCache$4;
        r0.<init>();
        NULL_SINK = r0;
    L_0x001a:
        r0 = 1;
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.<clinit>():void");
    }

    DiskLruCache(FileSystem fileSystem, File directory, int appVersion, int valueCount, long maxSize, Executor executor) {
        this.size = 0;
        this.lruEntries = new LinkedHashMap(0, 0.75f, true);
        this.nextSequenceNumber = 0;
        this.cleanupRunnable = new Runnable() {
            public void run() {
                int i = 0;
                synchronized (DiskLruCache.this) {
                    if (!DiskLruCache.this.initialized) {
                        i = 1;
                    }
                    if ((i | DiskLruCache.this.closed) == 0) {
                        try {
                            DiskLruCache.this.trimToSize();
                            if (DiskLruCache.this.journalRebuildRequired()) {
                                DiskLruCache.this.rebuildJournal();
                                DiskLruCache.this.redundantOpCount = 0;
                            }
                            return;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
        this.fileSystem = fileSystem;
        this.directory = directory;
        this.appVersion = appVersion;
        this.journalFile = new File(directory, JOURNAL_FILE);
        this.journalFileTmp = new File(directory, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(directory, JOURNAL_FILE_BACKUP);
        this.valueCount = valueCount;
        this.maxSize = maxSize;
        this.executor = executor;
    }

    void initialize() throws IOException {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (!this.initialized) {
            if (this.fileSystem.exists(this.journalFileBackup)) {
                if (this.fileSystem.exists(this.journalFile)) {
                    this.fileSystem.delete(this.journalFileBackup);
                } else {
                    this.fileSystem.rename(this.journalFileBackup, this.journalFile);
                }
            }
            if (this.fileSystem.exists(this.journalFile)) {
                try {
                    readJournal();
                    processJournal();
                    this.initialized = true;
                    return;
                } catch (IOException journalIsCorrupt) {
                    Platform.get().logW("DiskLruCache " + this.directory + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
                    delete();
                    this.closed = $assertionsDisabled;
                }
            }
            rebuildJournal();
            this.initialized = true;
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File directory, int appVersion, int valueCount, long maxSize) {
        if ((maxSize > 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (valueCount > 0) {
            return new DiskLruCache(fileSystem, directory, appVersion, valueCount, maxSize, new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    private void readJournal() throws IOException {
        Closeable source = Okio.buffer(this.fileSystem.source(this.journalFile));
        int lineCount;
        try {
            String magic = source.readUtf8LineStrict();
            String version = source.readUtf8LineStrict();
            String appVersionString = source.readUtf8LineStrict();
            String valueCountString = source.readUtf8LineStrict();
            String blank = source.readUtf8LineStrict();
            if (MAGIC.equals(magic)) {
                if (VERSION_1.equals(version) && Integer.toString(this.appVersion).equals(appVersionString) && Integer.toString(this.valueCount).equals(valueCountString) && "".equals(blank)) {
                    lineCount = 0;
                    while (true) {
                        readJournalLine(source.readUtf8LineStrict());
                        lineCount++;
                    }
                }
            }
            throw new IOException("unexpected journal header: [" + magic + ", " + version + ", " + valueCountString + ", " + blank + "]");
        } catch (EOFException e) {
            this.redundantOpCount = lineCount - this.lruEntries.size();
            if (source.exhausted()) {
                this.journalWriter = newJournalWriter();
            } else {
                rebuildJournal();
            }
            Util.closeQuietly(source);
        } catch (Throwable th) {
            Util.closeQuietly(source);
        }
    }

    private BufferedSink newJournalWriter() throws FileNotFoundException {
        return Okio.buffer(new AnonymousClass2(this.fileSystem.appendingSink(this.journalFile)));
    }

    private void readJournalLine(String line) throws IOException {
        int firstSpace = line.indexOf(32);
        if (firstSpace != -1) {
            String key;
            int keyBegin = firstSpace + 1;
            int secondSpace = line.indexOf(32, keyBegin);
            if (secondSpace != -1) {
                key = line.substring(keyBegin, secondSpace);
            } else {
                key = line.substring(keyBegin);
                if (firstSpace == REMOVE.length() && line.startsWith(REMOVE)) {
                    this.lruEntries.remove(key);
                    return;
                }
            }
            Entry entry = (Entry) this.lruEntries.get(key);
            if (entry == null) {
                entry = new Entry(key, null);
                this.lruEntries.put(key, entry);
            }
            if (secondSpace != -1 && firstSpace == CLEAN.length() && line.startsWith(CLEAN)) {
                String[] parts = line.substring(secondSpace + 1).split(" ");
                entry.readable = true;
                entry.currentEditor = null;
                entry.setLengths(parts);
            } else if (secondSpace == -1 && firstSpace == DIRTY.length() && line.startsWith(DIRTY)) {
                entry.currentEditor = new Editor(entry, null);
            } else {
                if (secondSpace == -1 && firstSpace == READ.length()) {
                    if (!line.startsWith(READ)) {
                    }
                }
                throw new IOException("unexpected journal line: " + line);
            }
            return;
        }
        throw new IOException("unexpected journal line: " + line);
    }

    private void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator<Entry> i = this.lruEntries.values().iterator();
        while (i.hasNext()) {
            Entry entry = (Entry) i.next();
            int t;
            if (entry.currentEditor != null) {
                entry.currentEditor = null;
                for (t = 0; t < this.valueCount; t++) {
                    this.fileSystem.delete(entry.cleanFiles[t]);
                    this.fileSystem.delete(entry.dirtyFiles[t]);
                }
                i.remove();
            } else {
                for (t = 0; t < this.valueCount; t++) {
                    this.size += entry.lengths[t];
                }
            }
        }
    }

    private synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        BufferedSink writer = Okio.buffer(this.fileSystem.sink(this.journalFileTmp));
        writer.writeUtf8(MAGIC).writeByte(10);
        writer.writeUtf8(VERSION_1).writeByte(10);
        writer.writeDecimalLong((long) this.appVersion).writeByte(10);
        writer.writeDecimalLong((long) this.valueCount).writeByte(10);
        writer.writeByte(10);
        for (Entry entry : this.lruEntries.values()) {
            if (entry.currentEditor == null) {
                writer.writeUtf8(CLEAN).writeByte(32);
                writer.writeUtf8(entry.key);
                entry.writeLengths(writer);
                writer.writeByte(10);
            } else {
                try {
                    writer.writeUtf8(DIRTY).writeByte(32);
                    writer.writeUtf8(entry.key);
                    writer.writeByte(10);
                } finally {
                    writer.close();
                }
            }
        }
        if (this.fileSystem.exists(this.journalFile)) {
            this.fileSystem.rename(this.journalFile, this.journalFileBackup);
        }
        this.fileSystem.rename(this.journalFileTmp, this.journalFile);
        this.fileSystem.delete(this.journalFileBackup);
        this.journalWriter = newJournalWriter();
        this.hasJournalErrors = $assertionsDisabled;
    }

    public synchronized Snapshot get(String key) throws IOException {
        initialize();
        checkNotClosed();
        validateKey(key);
        Entry entry = (Entry) this.lruEntries.get(key);
        if (entry != null) {
            if (entry.readable) {
                Snapshot snapshot = entry.snapshot();
                if (snapshot == null) {
                    return null;
                }
                this.redundantOpCount++;
                this.journalWriter.writeUtf8(READ).writeByte(32).writeUtf8(key).writeByte(10);
                if (journalRebuildRequired()) {
                    this.executor.execute(this.cleanupRunnable);
                }
                return snapshot;
            }
        }
        return null;
    }

    public Editor edit(String key) throws IOException {
        return edit(key, ANY_SEQUENCE_NUMBER);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.squareup.okhttp.internal.DiskLruCache.Editor edit(java.lang.String r7, long r8) throws java.io.IOException {
        /*
        r6 = this;
        r4 = 0;
        monitor-enter(r6);
        r6.initialize();	 Catch:{ all -> 0x006b }
        r6.checkNotClosed();	 Catch:{ all -> 0x006b }
        r6.validateKey(r7);	 Catch:{ all -> 0x006b }
        r2 = r6.lruEntries;	 Catch:{ all -> 0x006b }
        r1 = r2.get(r7);	 Catch:{ all -> 0x006b }
        r1 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r1;	 Catch:{ all -> 0x006b }
        r2 = -1;
        r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x0025;
    L_0x0019:
        if (r1 != 0) goto L_0x001d;
    L_0x001b:
        monitor-exit(r6);
        return r4;
    L_0x001d:
        r2 = r1.sequenceNumber;	 Catch:{ all -> 0x006b }
        r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r2 != 0) goto L_0x001b;
    L_0x0025:
        if (r1 != 0) goto L_0x0055;
    L_0x0027:
        r2 = r6.journalWriter;	 Catch:{ all -> 0x006b }
        r3 = "DIRTY";
        r2 = r2.writeUtf8(r3);	 Catch:{ all -> 0x006b }
        r3 = 32;
        r2 = r2.writeByte(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.writeUtf8(r7);	 Catch:{ all -> 0x006b }
        r3 = 10;
        r2.writeByte(r3);	 Catch:{ all -> 0x006b }
        r2 = r6.journalWriter;	 Catch:{ all -> 0x006b }
        r2.flush();	 Catch:{ all -> 0x006b }
        r2 = r6.hasJournalErrors;	 Catch:{ all -> 0x006b }
        if (r2 != 0) goto L_0x005d;
    L_0x0048:
        if (r1 == 0) goto L_0x005f;
    L_0x004a:
        r0 = new com.squareup.okhttp.internal.DiskLruCache$Editor;	 Catch:{ all -> 0x006b }
        r2 = 0;
        r0.<init>(r1, r2);	 Catch:{ all -> 0x006b }
        r1.currentEditor = r0;	 Catch:{ all -> 0x006b }
        monitor-exit(r6);
        return r0;
    L_0x0055:
        r2 = r1.currentEditor;	 Catch:{ all -> 0x006b }
        if (r2 == 0) goto L_0x0027;
    L_0x005b:
        monitor-exit(r6);
        return r4;
    L_0x005d:
        monitor-exit(r6);
        return r4;
    L_0x005f:
        r1 = new com.squareup.okhttp.internal.DiskLruCache$Entry;	 Catch:{ all -> 0x006b }
        r2 = 0;
        r1.<init>(r7, r2);	 Catch:{ all -> 0x006b }
        r2 = r6.lruEntries;	 Catch:{ all -> 0x006b }
        r2.put(r7, r1);	 Catch:{ all -> 0x006b }
        goto L_0x004a;
    L_0x006b:
        r2 = move-exception;
        monitor-exit(r6);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.edit(java.lang.String, long):com.squareup.okhttp.internal.DiskLruCache$Editor");
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
        if (this.initialized) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    public synchronized long size() throws IOException {
        initialize();
        return this.size;
    }

    private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
        Entry entry = editor.entry;
        if (entry.currentEditor == editor) {
            int i;
            if (success) {
                if (!entry.readable) {
                    i = 0;
                    while (i < this.valueCount) {
                        if (!editor.written[i]) {
                            editor.abort();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                        } else if (this.fileSystem.exists(entry.dirtyFiles[i])) {
                            i++;
                        } else {
                            editor.abort();
                            return;
                        }
                    }
                }
            }
            for (i = 0; i < this.valueCount; i++) {
                File dirty = entry.dirtyFiles[i];
                if (!success) {
                    this.fileSystem.delete(dirty);
                } else if (this.fileSystem.exists(dirty)) {
                    File clean = entry.cleanFiles[i];
                    this.fileSystem.rename(dirty, clean);
                    long oldLength = entry.lengths[i];
                    long newLength = this.fileSystem.size(clean);
                    entry.lengths[i] = newLength;
                    this.size = (this.size - oldLength) + newLength;
                }
            }
            this.redundantOpCount++;
            entry.currentEditor = null;
            if ((entry.readable | success) == 0) {
                this.lruEntries.remove(entry.key);
                this.journalWriter.writeUtf8(REMOVE).writeByte(32);
                this.journalWriter.writeUtf8(entry.key);
                this.journalWriter.writeByte(10);
            } else {
                entry.readable = true;
                this.journalWriter.writeUtf8(CLEAN).writeByte(32);
                this.journalWriter.writeUtf8(entry.key);
                entry.writeLengths(this.journalWriter);
                this.journalWriter.writeByte(10);
                if (success) {
                    long j = this.nextSequenceNumber;
                    this.nextSequenceNumber = 1 + j;
                    entry.sequenceNumber = j;
                }
            }
            this.journalWriter.flush();
            if ((this.size > this.maxSize ? 1 : null) != null || journalRebuildRequired()) {
                this.executor.execute(this.cleanupRunnable);
            }
            return;
        }
        throw new IllegalStateException();
    }

    private boolean journalRebuildRequired() {
        if (this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size()) {
            return true;
        }
        return $assertionsDisabled;
    }

    public synchronized boolean remove(String key) throws IOException {
        initialize();
        checkNotClosed();
        validateKey(key);
        Entry entry = (Entry) this.lruEntries.get(key);
        if (entry == null) {
            return $assertionsDisabled;
        }
        return removeEntry(entry);
    }

    private boolean removeEntry(Entry entry) throws IOException {
        if (entry.currentEditor != null) {
            entry.currentEditor.hasErrors = true;
        }
        for (int i = 0; i < this.valueCount; i++) {
            this.fileSystem.delete(entry.cleanFiles[i]);
            this.size -= entry.lengths[i];
            entry.lengths[i] = 0;
        }
        this.redundantOpCount++;
        this.journalWriter.writeUtf8(REMOVE).writeByte(32).writeUtf8(entry.key).writeByte(10);
        this.lruEntries.remove(entry.key);
        if (journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    public synchronized boolean isClosed() {
        return this.closed;
    }

    private synchronized void checkNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        if (this.initialized) {
            checkNotClosed();
            trimToSize();
            this.journalWriter.flush();
        }
    }

    public synchronized void close() throws IOException {
        synchronized (this) {
            if (this.initialized) {
                if (!this.closed) {
                    for (Entry entry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
                        if (entry.currentEditor != null) {
                            entry.currentEditor.abort();
                        }
                    }
                    trimToSize();
                    this.journalWriter.close();
                    this.journalWriter = null;
                    this.closed = true;
                    return;
                }
            }
            this.closed = true;
        }
    }

    private void trimToSize() throws IOException {
        while (true) {
            if ((this.size <= this.maxSize ? 1 : null) == null) {
                removeEntry((Entry) this.lruEntries.values().iterator().next());
            } else {
                return;
            }
        }
    }

    public void delete() throws IOException {
        close();
        this.fileSystem.deleteContents(this.directory);
    }

    public synchronized void evictAll() throws IOException {
        initialize();
        for (Entry entry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
            removeEntry(entry);
        }
    }

    private void validateKey(String key) {
        if (!LEGAL_KEY_PATTERN.matcher(key).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + key + "\"");
        }
    }

    public synchronized Iterator<Snapshot> snapshots() throws IOException {
        initialize();
        return new Iterator<Snapshot>() {
            final Iterator<Entry> delegate;
            Snapshot nextSnapshot;
            Snapshot removeSnapshot;

            {
                this.delegate = new ArrayList(DiskLruCache.this.lruEntries.values()).iterator();
            }

            public boolean hasNext() {
                if (this.nextSnapshot != null) {
                    return true;
                }
                synchronized (DiskLruCache.this) {
                    if (DiskLruCache.this.closed) {
                        return DiskLruCache.$assertionsDisabled;
                    }
                    while (this.delegate.hasNext()) {
                        Snapshot snapshot = ((Entry) this.delegate.next()).snapshot();
                        if (snapshot != null) {
                            this.nextSnapshot = snapshot;
                            return true;
                        }
                    }
                    return DiskLruCache.$assertionsDisabled;
                }
            }

            public Snapshot next() {
                if (hasNext()) {
                    this.removeSnapshot = this.nextSnapshot;
                    this.nextSnapshot = null;
                    return this.removeSnapshot;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                if (this.removeSnapshot != null) {
                    try {
                        DiskLruCache.this.remove(this.removeSnapshot.key);
                    } catch (IOException e) {
                    } catch (Throwable th) {
                        this.removeSnapshot = null;
                    }
                    this.removeSnapshot = null;
                    return;
                }
                throw new IllegalStateException("remove() before next()");
            }
        };
    }
}
