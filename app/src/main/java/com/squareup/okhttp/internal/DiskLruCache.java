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
import okio.b;
import okio.g;
import okio.j;
import okio.k;
import okio.n;
import okio.v;

public final class DiskLruCache implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final n NULL_SINK = new n() {
        public void close() {
        }

        public void flush() {
        }

        public g timeout() {
            return g.NONE;
        }

        public void write(k kVar, long j) {
            kVar.skip(j);
        }
    };
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Runnable cleanupRunnable = new Runnable() {
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
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    };
    private boolean closed;
    private final File directory;
    private final Executor executor;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private b journalWriter;
    private final LinkedHashMap lruEntries = new LinkedHashMap(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0;
    private int redundantOpCount;
    private long size = 0;
    private final int valueCount;

    public final class Editor {
        private boolean committed;
        private final Entry entry;
        private boolean hasErrors;
        private final boolean[] written;

        private Editor(Entry entry) {
            this.entry = entry;
            this.written = !entry.readable ? new boolean[DiskLruCache.this.valueCount] : null;
        }

        public void abort() {
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

        public void commit() {
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

        public n newSink(int i) {
            n anonymousClass1;
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor == this) {
                    if (!this.entry.readable) {
                        this.written[i] = true;
                    }
                    try {
                        anonymousClass1 = new FaultHidingSink(DiskLruCache.this.fileSystem.sink(this.entry.dirtyFiles[i])) {
                            protected void onException(IOException iOException) {
                                synchronized (DiskLruCache.this) {
                                    Editor.this.hasErrors = true;
                                }
                            }
                        };
                    } catch (FileNotFoundException e) {
                        return DiskLruCache.NULL_SINK;
                    }
                }
                throw new IllegalStateException();
            }
            return anonymousClass1;
        }

        public v newSource(int i) {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (this.entry.readable) {
                    try {
                        v source = DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[i]);
                        return source;
                    } catch (FileNotFoundException e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
    }

    final class Entry {
        private final File[] cleanFiles;
        private Editor currentEditor;
        private final File[] dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        private Entry(String str) {
            this.key = str;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            StringBuilder append = new StringBuilder(str).append('.');
            int length = append.length();
            for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                append.append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, append.toString());
                append.append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, append.toString());
                append.setLength(length);
            }
        }

        private IOException invalidLengths(String[] strArr) {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        private void setLengths(String[] strArr) {
            if (strArr.length == DiskLruCache.this.valueCount) {
                int i = 0;
                while (i < strArr.length) {
                    try {
                        this.lengths[i] = Long.parseLong(strArr[i]);
                        i++;
                    } catch (NumberFormatException e) {
                        throw invalidLengths(strArr);
                    }
                }
                return;
            }
            throw invalidLengths(strArr);
        }

        Snapshot snapshot() {
            int i = 0;
            if (Thread.holdsLock(DiskLruCache.this)) {
                v[] vVarArr = new v[DiskLruCache.this.valueCount];
                long[] jArr = (long[]) this.lengths.clone();
                int i2 = 0;
                while (i2 < DiskLruCache.this.valueCount) {
                    try {
                        vVarArr[i2] = DiskLruCache.this.fileSystem.source(this.cleanFiles[i2]);
                        i2++;
                    } catch (FileNotFoundException e) {
                        while (i < DiskLruCache.this.valueCount) {
                            if (vVarArr[i] == null) {
                                break;
                            }
                            Util.closeQuietly(vVarArr[i]);
                            i++;
                        }
                        return null;
                    }
                }
                return new Snapshot(this.key, this.sequenceNumber, vVarArr, jArr);
            }
            throw new AssertionError();
        }

        void writeLengths(b bVar) {
            for (long Ag : this.lengths) {
                bVar.Ad(32).Ag(Ag);
            }
        }
    }

    public final class Snapshot implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final v[] sources;

        private Snapshot(String str, long j, v[] vVarArr, long[] jArr) {
            this.key = str;
            this.sequenceNumber = j;
            this.sources = vVarArr;
            this.lengths = jArr;
        }

        public void close() {
            for (Closeable closeQuietly : this.sources) {
                Util.closeQuietly(closeQuietly);
            }
        }

        public Editor edit() {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public long getLength(int i) {
            return this.lengths[i];
        }

        public v getSource(int i) {
            return this.sources[i];
        }

        public String key() {
            return this.key;
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

    DiskLruCache(FileSystem fileSystem, File file, int i, int i2, long j, Executor executor) {
        this.fileSystem = fileSystem;
        this.directory = file;
        this.appVersion = i;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file, JOURNAL_FILE_BACKUP);
        this.valueCount = i2;
        this.maxSize = j;
        this.executor = executor;
    }

    private synchronized void checkNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void completeEdit(com.squareup.okhttp.internal.DiskLruCache.Editor r11, boolean r12) {
        /*
        r10 = this;
        r0 = 1;
        r1 = 0;
        monitor-enter(r10);
        r3 = r11.entry;	 Catch:{ all -> 0x0069 }
        r2 = r3.currentEditor;	 Catch:{ all -> 0x0069 }
        if (r2 != r11) goto L_0x0063;
    L_0x000d:
        if (r12 != 0) goto L_0x006c;
    L_0x000f:
        r2 = r1;
    L_0x0010:
        r4 = r10.valueCount;	 Catch:{ all -> 0x0069 }
        if (r2 < r4) goto L_0x00b2;
    L_0x0014:
        r2 = r10.redundantOpCount;	 Catch:{ all -> 0x0069 }
        r2 = r2 + 1;
        r10.redundantOpCount = r2;	 Catch:{ all -> 0x0069 }
        r2 = 0;
        r3.currentEditor = r2;	 Catch:{ all -> 0x0069 }
        r2 = r3.readable;	 Catch:{ all -> 0x0069 }
        r2 = r2 | r12;
        if (r2 != 0) goto L_0x00f0;
    L_0x0025:
        r2 = r10.lruEntries;	 Catch:{ all -> 0x0069 }
        r4 = r3.key;	 Catch:{ all -> 0x0069 }
        r2.remove(r4);	 Catch:{ all -> 0x0069 }
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r4 = "REMOVE";
        r2 = r2.Ac(r4);	 Catch:{ all -> 0x0069 }
        r4 = 32;
        r2.Ad(r4);	 Catch:{ all -> 0x0069 }
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r3 = r3.key;	 Catch:{ all -> 0x0069 }
        r2.Ac(r3);	 Catch:{ all -> 0x0069 }
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r3 = 10;
        r2.Ad(r3);	 Catch:{ all -> 0x0069 }
    L_0x004c:
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r2.flush();	 Catch:{ all -> 0x0069 }
        r2 = r10.size;	 Catch:{ all -> 0x0069 }
        r4 = r10.maxSize;	 Catch:{ all -> 0x0069 }
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 <= 0) goto L_0x0125;
    L_0x0059:
        if (r0 != 0) goto L_0x0128;
    L_0x005b:
        r0 = r10.journalRebuildRequired();	 Catch:{ all -> 0x0069 }
        if (r0 != 0) goto L_0x0128;
    L_0x0061:
        monitor-exit(r10);
        return;
    L_0x0063:
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0069 }
        r0.<init>();	 Catch:{ all -> 0x0069 }
        throw r0;	 Catch:{ all -> 0x0069 }
    L_0x0069:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x006c:
        r2 = r3.readable;	 Catch:{ all -> 0x0069 }
        if (r2 != 0) goto L_0x000f;
    L_0x0072:
        r2 = r1;
    L_0x0073:
        r4 = r10.valueCount;	 Catch:{ all -> 0x0069 }
        if (r2 >= r4) goto L_0x000f;
    L_0x0077:
        r4 = r11.written;	 Catch:{ all -> 0x0069 }
        r4 = r4[r2];	 Catch:{ all -> 0x0069 }
        if (r4 == 0) goto L_0x0090;
    L_0x007f:
        r4 = r10.fileSystem;	 Catch:{ all -> 0x0069 }
        r5 = r3.dirtyFiles;	 Catch:{ all -> 0x0069 }
        r5 = r5[r2];	 Catch:{ all -> 0x0069 }
        r4 = r4.exists(r5);	 Catch:{ all -> 0x0069 }
        if (r4 == 0) goto L_0x00ad;
    L_0x008d:
        r2 = r2 + 1;
        goto L_0x0073;
    L_0x0090:
        r11.abort();	 Catch:{ all -> 0x0069 }
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0069 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0069 }
        r1.<init>();	 Catch:{ all -> 0x0069 }
        r3 = "Newly created entry didn't create value for index ";
        r1 = r1.append(r3);	 Catch:{ all -> 0x0069 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x0069 }
        r1 = r1.toString();	 Catch:{ all -> 0x0069 }
        r0.<init>(r1);	 Catch:{ all -> 0x0069 }
        throw r0;	 Catch:{ all -> 0x0069 }
    L_0x00ad:
        r11.abort();	 Catch:{ all -> 0x0069 }
        monitor-exit(r10);
        return;
    L_0x00b2:
        r4 = r3.dirtyFiles;	 Catch:{ all -> 0x0069 }
        r4 = r4[r2];	 Catch:{ all -> 0x0069 }
        if (r12 != 0) goto L_0x00c3;
    L_0x00ba:
        r5 = r10.fileSystem;	 Catch:{ all -> 0x0069 }
        r5.delete(r4);	 Catch:{ all -> 0x0069 }
    L_0x00bf:
        r2 = r2 + 1;
        goto L_0x0010;
    L_0x00c3:
        r5 = r10.fileSystem;	 Catch:{ all -> 0x0069 }
        r5 = r5.exists(r4);	 Catch:{ all -> 0x0069 }
        if (r5 == 0) goto L_0x00bf;
    L_0x00cb:
        r5 = r3.cleanFiles;	 Catch:{ all -> 0x0069 }
        r5 = r5[r2];	 Catch:{ all -> 0x0069 }
        r6 = r10.fileSystem;	 Catch:{ all -> 0x0069 }
        r6.rename(r4, r5);	 Catch:{ all -> 0x0069 }
        r4 = r3.lengths;	 Catch:{ all -> 0x0069 }
        r6 = r4[r2];	 Catch:{ all -> 0x0069 }
        r4 = r10.fileSystem;	 Catch:{ all -> 0x0069 }
        r4 = r4.size(r5);	 Catch:{ all -> 0x0069 }
        r8 = r3.lengths;	 Catch:{ all -> 0x0069 }
        r8[r2] = r4;	 Catch:{ all -> 0x0069 }
        r8 = r10.size;	 Catch:{ all -> 0x0069 }
        r6 = r8 - r6;
        r4 = r4 + r6;
        r10.size = r4;	 Catch:{ all -> 0x0069 }
        goto L_0x00bf;
    L_0x00f0:
        r2 = 1;
        r3.readable = r2;	 Catch:{ all -> 0x0069 }
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r4 = "CLEAN";
        r2 = r2.Ac(r4);	 Catch:{ all -> 0x0069 }
        r4 = 32;
        r2.Ad(r4);	 Catch:{ all -> 0x0069 }
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r4 = r3.key;	 Catch:{ all -> 0x0069 }
        r2.Ac(r4);	 Catch:{ all -> 0x0069 }
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r3.writeLengths(r2);	 Catch:{ all -> 0x0069 }
        r2 = r10.journalWriter;	 Catch:{ all -> 0x0069 }
        r4 = 10;
        r2.Ad(r4);	 Catch:{ all -> 0x0069 }
        if (r12 == 0) goto L_0x004c;
    L_0x0119:
        r4 = r10.nextSequenceNumber;	 Catch:{ all -> 0x0069 }
        r6 = 1;
        r6 = r6 + r4;
        r10.nextSequenceNumber = r6;	 Catch:{ all -> 0x0069 }
        r3.sequenceNumber = r4;	 Catch:{ all -> 0x0069 }
        goto L_0x004c;
    L_0x0125:
        r0 = r1;
        goto L_0x0059;
    L_0x0128:
        r0 = r10.executor;	 Catch:{ all -> 0x0069 }
        r1 = r10.cleanupRunnable;	 Catch:{ all -> 0x0069 }
        r0.execute(r1);	 Catch:{ all -> 0x0069 }
        goto L_0x0061;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.completeEdit(com.squareup.okhttp.internal.DiskLruCache$Editor, boolean):void");
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int i, int i2, long j) {
        if ((j > 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 > 0) {
            return new DiskLruCache(fileSystem, file, i, i2, j, new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.squareup.okhttp.internal.DiskLruCache.Editor edit(java.lang.String r7, long r8) {
        /*
        r6 = this;
        r4 = 0;
        monitor-enter(r6);
        r6.initialize();	 Catch:{ all -> 0x006b }
        r6.checkNotClosed();	 Catch:{ all -> 0x006b }
        r6.validateKey(r7);	 Catch:{ all -> 0x006b }
        r0 = r6.lruEntries;	 Catch:{ all -> 0x006b }
        r0 = r0.get(r7);	 Catch:{ all -> 0x006b }
        r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0;	 Catch:{ all -> 0x006b }
        r2 = -1;
        r1 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
        if (r1 == 0) goto L_0x0025;
    L_0x0019:
        if (r0 != 0) goto L_0x001d;
    L_0x001b:
        monitor-exit(r6);
        return r4;
    L_0x001d:
        r2 = r0.sequenceNumber;	 Catch:{ all -> 0x006b }
        r1 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r1 != 0) goto L_0x001b;
    L_0x0025:
        if (r0 != 0) goto L_0x0055;
    L_0x0027:
        r1 = r6.journalWriter;	 Catch:{ all -> 0x006b }
        r2 = "DIRTY";
        r1 = r1.Ac(r2);	 Catch:{ all -> 0x006b }
        r2 = 32;
        r1 = r1.Ad(r2);	 Catch:{ all -> 0x006b }
        r1 = r1.Ac(r7);	 Catch:{ all -> 0x006b }
        r2 = 10;
        r1.Ad(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.journalWriter;	 Catch:{ all -> 0x006b }
        r1.flush();	 Catch:{ all -> 0x006b }
        r1 = r6.hasJournalErrors;	 Catch:{ all -> 0x006b }
        if (r1 != 0) goto L_0x005d;
    L_0x0048:
        if (r0 == 0) goto L_0x005f;
    L_0x004a:
        r1 = new com.squareup.okhttp.internal.DiskLruCache$Editor;	 Catch:{ all -> 0x006b }
        r2 = 0;
        r1.<init>(r0);	 Catch:{ all -> 0x006b }
        r0.currentEditor = r1;	 Catch:{ all -> 0x006b }
        monitor-exit(r6);
        return r1;
    L_0x0055:
        r1 = r0.currentEditor;	 Catch:{ all -> 0x006b }
        if (r1 == 0) goto L_0x0027;
    L_0x005b:
        monitor-exit(r6);
        return r4;
    L_0x005d:
        monitor-exit(r6);
        return r4;
    L_0x005f:
        r0 = new com.squareup.okhttp.internal.DiskLruCache$Entry;	 Catch:{ all -> 0x006b }
        r1 = 0;
        r0.<init>(r7);	 Catch:{ all -> 0x006b }
        r1 = r6.lruEntries;	 Catch:{ all -> 0x006b }
        r1.put(r7, r0);	 Catch:{ all -> 0x006b }
        goto L_0x004a;
    L_0x006b:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.edit(java.lang.String, long):com.squareup.okhttp.internal.DiskLruCache$Editor");
    }

    private boolean journalRebuildRequired() {
        return (this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size()) ? true : $assertionsDisabled;
    }

    private b newJournalWriter() {
        return j.AF(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile)) {
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

            protected void onException(IOException iOException) {
                if ($assertionsDisabled || Thread.holdsLock(DiskLruCache.this)) {
                    DiskLruCache.this.hasJournalErrors = true;
                    return;
                }
                throw new AssertionError();
            }
        });
    }

    private void processJournal() {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator it = this.lruEntries.values().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int i;
            if (entry.currentEditor != null) {
                entry.currentEditor = null;
                for (i = 0; i < this.valueCount; i++) {
                    this.fileSystem.delete(entry.cleanFiles[i]);
                    this.fileSystem.delete(entry.dirtyFiles[i]);
                }
                it.remove();
            } else {
                for (i = 0; i < this.valueCount; i++) {
                    this.size += entry.lengths[i];
                }
            }
        }
    }

    private void readJournal() {
        int i = 0;
        Closeable AE = j.AE(this.fileSystem.source(this.journalFile));
        try {
            String zW = AE.zW();
            String zW2 = AE.zW();
            String zW3 = AE.zW();
            String zW4 = AE.zW();
            String zW5 = AE.zW();
            if (MAGIC.equals(zW)) {
                if (VERSION_1.equals(zW2) && Integer.toString(this.appVersion).equals(zW3) && Integer.toString(this.valueCount).equals(zW4) && "".equals(zW5)) {
                    while (true) {
                        readJournalLine(AE.zW());
                        i++;
                    }
                }
            }
            throw new IOException("unexpected journal header: [" + zW + ", " + zW2 + ", " + zW4 + ", " + zW5 + "]");
        } catch (EOFException e) {
            this.redundantOpCount = i - this.lruEntries.size();
            if (AE.zL()) {
                this.journalWriter = newJournalWriter();
            } else {
                rebuildJournal();
            }
            Util.closeQuietly(AE);
        } catch (Throwable th) {
            Util.closeQuietly(AE);
        }
    }

    private void readJournalLine(String str) {
        int indexOf = str.indexOf(32);
        if (indexOf != -1) {
            String substring;
            int i = indexOf + 1;
            int indexOf2 = str.indexOf(32, i);
            if (indexOf2 != -1) {
                substring = str.substring(i, indexOf2);
            } else {
                String substring2 = str.substring(i);
                if (indexOf == REMOVE.length() && str.startsWith(REMOVE)) {
                    this.lruEntries.remove(substring2);
                    return;
                }
                substring = substring2;
            }
            Entry entry = (Entry) this.lruEntries.get(substring);
            if (entry == null) {
                entry = new Entry(substring);
                this.lruEntries.put(substring, entry);
            }
            if (indexOf2 != -1 && indexOf == CLEAN.length() && str.startsWith(CLEAN)) {
                String[] split = str.substring(indexOf2 + 1).split(" ");
                entry.readable = true;
                entry.currentEditor = null;
                entry.setLengths(split);
            } else if (indexOf2 == -1 && indexOf == DIRTY.length() && str.startsWith(DIRTY)) {
                entry.currentEditor = new Editor(entry);
            } else {
                if (indexOf2 == -1 && indexOf == READ.length()) {
                    if (!str.startsWith(READ)) {
                    }
                }
                throw new IOException("unexpected journal line: " + str);
            }
            return;
        }
        throw new IOException("unexpected journal line: " + str);
    }

    private synchronized void rebuildJournal() {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        b AF = j.AF(this.fileSystem.sink(this.journalFileTmp));
        AF.Ac(MAGIC).Ad(10);
        AF.Ac(VERSION_1).Ad(10);
        AF.Ag((long) this.appVersion).Ad(10);
        AF.Ag((long) this.valueCount).Ad(10);
        AF.Ad(10);
        for (Entry entry : this.lruEntries.values()) {
            if (entry.currentEditor == null) {
                AF.Ac(CLEAN).Ad(32);
                AF.Ac(entry.key);
                entry.writeLengths(AF);
                AF.Ad(10);
            } else {
                try {
                    AF.Ac(DIRTY).Ad(32);
                    AF.Ac(entry.key);
                    AF.Ad(10);
                } finally {
                    AF.close();
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

    private boolean removeEntry(Entry entry) {
        int i = 0;
        if (entry.currentEditor != null) {
            entry.currentEditor.hasErrors = true;
        }
        while (i < this.valueCount) {
            this.fileSystem.delete(entry.cleanFiles[i]);
            this.size -= entry.lengths[i];
            entry.lengths[i] = 0;
            i++;
        }
        this.redundantOpCount++;
        this.journalWriter.Ac(REMOVE).Ad(32).Ac(entry.key).Ad(10);
        this.lruEntries.remove(entry.key);
        if (journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    private void trimToSize() {
        while (true) {
            if ((this.size <= this.maxSize ? 1 : null) == null) {
                removeEntry((Entry) this.lruEntries.values().iterator().next());
            } else {
                return;
            }
        }
    }

    private void validateKey(String str) {
        if (!LEGAL_KEY_PATTERN.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
        }
    }

    public synchronized void close() {
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

    public void delete() {
        close();
        this.fileSystem.deleteContents(this.directory);
    }

    public Editor edit(String str) {
        return edit(str, ANY_SEQUENCE_NUMBER);
    }

    public synchronized void evictAll() {
        initialize();
        for (Entry removeEntry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
            removeEntry(removeEntry);
        }
    }

    public synchronized void flush() {
        if (this.initialized) {
            checkNotClosed();
            trimToSize();
            this.journalWriter.flush();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.internal.DiskLruCache.Snapshot get(java.lang.String r4) {
        /*
        r3 = this;
        r2 = 0;
        monitor-enter(r3);
        r3.initialize();	 Catch:{ all -> 0x0053 }
        r3.checkNotClosed();	 Catch:{ all -> 0x0053 }
        r3.validateKey(r4);	 Catch:{ all -> 0x0053 }
        r0 = r3.lruEntries;	 Catch:{ all -> 0x0053 }
        r0 = r0.get(r4);	 Catch:{ all -> 0x0053 }
        r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0;	 Catch:{ all -> 0x0053 }
        if (r0 != 0) goto L_0x0017;
    L_0x0015:
        monitor-exit(r3);
        return r2;
    L_0x0017:
        r1 = r0.readable;	 Catch:{ all -> 0x0053 }
        if (r1 == 0) goto L_0x0015;
    L_0x001d:
        r0 = r0.snapshot();	 Catch:{ all -> 0x0053 }
        if (r0 == 0) goto L_0x0049;
    L_0x0023:
        r1 = r3.redundantOpCount;	 Catch:{ all -> 0x0053 }
        r1 = r1 + 1;
        r3.redundantOpCount = r1;	 Catch:{ all -> 0x0053 }
        r1 = r3.journalWriter;	 Catch:{ all -> 0x0053 }
        r2 = "READ";
        r1 = r1.Ac(r2);	 Catch:{ all -> 0x0053 }
        r2 = 32;
        r1 = r1.Ad(r2);	 Catch:{ all -> 0x0053 }
        r1 = r1.Ac(r4);	 Catch:{ all -> 0x0053 }
        r2 = 10;
        r1.Ad(r2);	 Catch:{ all -> 0x0053 }
        r1 = r3.journalRebuildRequired();	 Catch:{ all -> 0x0053 }
        if (r1 != 0) goto L_0x004b;
    L_0x0047:
        monitor-exit(r3);
        return r0;
    L_0x0049:
        monitor-exit(r3);
        return r2;
    L_0x004b:
        r1 = r3.executor;	 Catch:{ all -> 0x0053 }
        r2 = r3.cleanupRunnable;	 Catch:{ all -> 0x0053 }
        r1.execute(r2);	 Catch:{ all -> 0x0053 }
        goto L_0x0047;
    L_0x0053:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.get(java.lang.String):com.squareup.okhttp.internal.DiskLruCache$Snapshot");
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    void initialize() {
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
                } catch (IOException e) {
                    Platform.get().logW("DiskLruCache " + this.directory + " is corrupt: " + e.getMessage() + ", removing");
                    delete();
                    this.closed = $assertionsDisabled;
                }
            }
            rebuildJournal();
            this.initialized = true;
        }
    }

    public synchronized boolean isClosed() {
        return this.closed;
    }

    public synchronized boolean remove(String str) {
        initialize();
        checkNotClosed();
        validateKey(str);
        Entry entry = (Entry) this.lruEntries.get(str);
        if (entry == null) {
            return $assertionsDisabled;
        }
        return removeEntry(entry);
    }

    public synchronized void setMaxSize(long j) {
        this.maxSize = j;
        if (this.initialized) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    public synchronized long size() {
        initialize();
        return this.size;
    }

    public synchronized Iterator snapshots() {
        initialize();
        return new Iterator() {
            final Iterator delegate = new ArrayList(DiskLruCache.this.lruEntries.values()).iterator();
            Snapshot nextSnapshot;
            Snapshot removeSnapshot;

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
