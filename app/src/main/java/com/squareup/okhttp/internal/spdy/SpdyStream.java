package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class SpdyStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    long bytesLeftInWriteWindow;
    private final SpdyConnection connection;
    private ErrorCode errorCode;
    private final int id;
    private final SpdyTimeout readTimeout;
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    final SpdyDataSink sink;
    private final SpdyDataSource source;
    long unacknowledgedBytesRead;
    private final SpdyTimeout writeTimeout;

    final class SpdyDataSink implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled;
        private static final long EMIT_BUFFER_SIZE = 16384;
        private boolean closed;
        private boolean finished;
        private final Buffer sendBuffer;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r0 = 0;
            r1 = com.squareup.okhttp.internal.spdy.SpdyStream.class;
            if (r1 == 0) goto L_0x000a;
        L_0x0007:
            $assertionsDisabled = r0;
        L_0x000a:
            r0 = 1;
            goto L_0x0007;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSink.<clinit>():void");
        }

        SpdyDataSink() {
            this.sendBuffer = new Buffer();
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            this.sendBuffer.write(source, byteCount);
            while (true) {
                boolean z;
                if (this.sendBuffer.size() < EMIT_BUFFER_SIZE) {
                    z = true;
                } else {
                    z = $assertionsDisabled;
                }
                if (!z) {
                    emitDataFrame($assertionsDisabled);
                } else {
                    return;
                }
            }
        }

        private void emitDataFrame(boolean outFinished) throws IOException {
            long toWrite;
            boolean z = true;
            synchronized (SpdyStream.this) {
                SpdyStream.this.writeTimeout.enter();
                while (true) {
                    Object obj;
                    if (SpdyStream.this.bytesLeftInWriteWindow > 0) {
                        obj = 1;
                    } else {
                        obj = null;
                    }
                    if (obj == null && !this.finished) {
                        try {
                            if (!this.closed && SpdyStream.this.errorCode == null) {
                                SpdyStream.this.waitForIo();
                            }
                        } finally {
                            z = SpdyStream.this.writeTimeout;
                            z.exitAndThrowIfTimedOut();
                        }
                    }
                    break;
                }
                SpdyStream.this.checkOutNotClosed();
                toWrite = Math.min(SpdyStream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                SpdyStream spdyStream = SpdyStream.this;
                spdyStream.bytesLeftInWriteWindow -= toWrite;
            }
            SpdyConnection access$500 = SpdyStream.this.connection;
            int access$600 = SpdyStream.this.id;
            if (!outFinished || toWrite != this.sendBuffer.size()) {
                z = $assertionsDisabled;
            }
            access$500.writeData(access$600, z, this.sendBuffer, toWrite);
        }

        public void flush() throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            synchronized (SpdyStream.this) {
                SpdyStream.this.checkOutNotClosed();
            }
            while (true) {
                boolean z;
                if (this.sendBuffer.size() <= 0) {
                    z = true;
                } else {
                    z = $assertionsDisabled;
                }
                if (z) {
                    SpdyStream.this.connection.flush();
                    return;
                }
                emitDataFrame($assertionsDisabled);
            }
        }

        public Timeout timeout() {
            return SpdyStream.this.writeTimeout;
        }

        public void close() throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            synchronized (SpdyStream.this) {
                if (this.closed) {
                    return;
                }
                if (!SpdyStream.this.sink.finished) {
                    boolean z;
                    if (this.sendBuffer.size() <= 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z) {
                        while (true) {
                            if (this.sendBuffer.size() <= 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (z) {
                                break;
                            }
                            emitDataFrame(true);
                        }
                    } else {
                        SpdyStream.this.connection.writeData(SpdyStream.this.id, true, null, 0);
                    }
                }
                synchronized (SpdyStream.this) {
                    this.closed = true;
                }
                SpdyStream.this.connection.flush();
                SpdyStream.this.cancelStreamIfNecessary();
            }
        }
    }

    private final class SpdyDataSource implements Source {
        static final /* synthetic */ boolean $assertionsDisabled;
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer;
        private final Buffer receiveBuffer;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r0 = 0;
            r1 = com.squareup.okhttp.internal.spdy.SpdyStream.class;
            if (r1 == 0) goto L_0x000a;
        L_0x0007:
            $assertionsDisabled = r0;
        L_0x000a:
            r0 = 1;
            goto L_0x0007;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSource.<clinit>():void");
        }

        private SpdyDataSource(long maxByteCount) {
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
            this.maxByteCount = maxByteCount;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            Object obj = 1;
            if ((byteCount >= 0 ? 1 : null) == null) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            }
            synchronized (SpdyStream.this) {
                waitUntilReadable();
                checkNotClosed();
                if (this.readBuffer.size() == 0) {
                    return -1;
                }
                long read = this.readBuffer.read(sink, Math.min(byteCount, this.readBuffer.size()));
                SpdyStream spdyStream = SpdyStream.this;
                spdyStream.unacknowledgedBytesRead += read;
                if ((SpdyStream.this.unacknowledgedBytesRead < ((long) (SpdyStream.this.connection.okHttpSettings.getInitialWindowSize(65536) / 2)) ? 1 : null) == null) {
                    SpdyStream.this.connection.writeWindowUpdateLater(SpdyStream.this.id, SpdyStream.this.unacknowledgedBytesRead);
                    SpdyStream.this.unacknowledgedBytesRead = 0;
                }
                synchronized (SpdyStream.this.connection) {
                    SpdyConnection access$500 = SpdyStream.this.connection;
                    access$500.unacknowledgedBytesRead += read;
                    if (SpdyStream.this.connection.unacknowledgedBytesRead >= ((long) (SpdyStream.this.connection.okHttpSettings.getInitialWindowSize(65536) / 2))) {
                        obj = null;
                    }
                    if (obj == null) {
                        SpdyStream.this.connection.writeWindowUpdateLater(0, SpdyStream.this.connection.unacknowledgedBytesRead);
                        SpdyStream.this.connection.unacknowledgedBytesRead = 0;
                    }
                }
                return read;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void waitUntilReadable() throws java.io.IOException {
            /*
            r4 = this;
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = r0.readTimeout;
            r0.enter();
        L_0x0009:
            r0 = r4.readBuffer;	 Catch:{ all -> 0x0035 }
            r0 = r0.size();	 Catch:{ all -> 0x0035 }
            r2 = 0;
            r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r0 != 0) goto L_0x0019;
        L_0x0015:
            r0 = r4.finished;	 Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x0023;
        L_0x0019:
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = r0.readTimeout;
            r0.exitAndThrowIfTimedOut();
            return;
        L_0x0023:
            r0 = r4.closed;	 Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x0019;
        L_0x0027:
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x0035 }
            r0 = r0.errorCode;	 Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x0019;
        L_0x002f:
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x0035 }
            r0.waitForIo();	 Catch:{ all -> 0x0035 }
            goto L_0x0009;
        L_0x0035:
            r0 = move-exception;
            r1 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r1 = r1.readTimeout;
            r1.exitAndThrowIfTimedOut();
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSource.waitUntilReadable():void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        void receive(okio.BufferedSource r13, long r14) throws java.io.IOException {
            /*
            r12 = this;
            r5 = $assertionsDisabled;
            if (r5 == 0) goto L_0x003a;
        L_0x0004:
            r6 = 0;
            r5 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1));
            if (r5 > 0) goto L_0x0048;
        L_0x000a:
            r5 = 1;
        L_0x000b:
            if (r5 != 0) goto L_0x0087;
        L_0x000d:
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r6);
            r0 = r12.finished;	 Catch:{ all -> 0x004e }
            r5 = r12.readBuffer;	 Catch:{ all -> 0x004e }
            r8 = r5.size();	 Catch:{ all -> 0x004e }
            r8 = r8 + r14;
            r10 = r12.maxByteCount;	 Catch:{ all -> 0x004e }
            r5 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
            if (r5 > 0) goto L_0x004a;
        L_0x001f:
            r5 = 1;
        L_0x0020:
            if (r5 != 0) goto L_0x004c;
        L_0x0022:
            r1 = 1;
        L_0x0023:
            monitor-exit(r6);	 Catch:{ all -> 0x004e }
            if (r1 != 0) goto L_0x0051;
        L_0x0026:
            if (r0 != 0) goto L_0x005c;
        L_0x0028:
            r5 = r12.receiveBuffer;
            r2 = r13.read(r5, r14);
            r6 = -1;
            r5 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
            if (r5 != 0) goto L_0x0060;
        L_0x0034:
            r5 = new java.io.EOFException;
            r5.<init>();
            throw r5;
        L_0x003a:
            r5 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r5 = java.lang.Thread.holdsLock(r5);
            if (r5 == 0) goto L_0x0004;
        L_0x0042:
            r5 = new java.lang.AssertionError;
            r5.<init>();
            throw r5;
        L_0x0048:
            r5 = 0;
            goto L_0x000b;
        L_0x004a:
            r5 = 0;
            goto L_0x0020;
        L_0x004c:
            r1 = 0;
            goto L_0x0023;
        L_0x004e:
            r5 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x004e }
            throw r5;
        L_0x0051:
            r13.skip(r14);
            r5 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r6 = com.squareup.okhttp.internal.spdy.ErrorCode.FLOW_CONTROL_ERROR;
            r5.closeLater(r6);
            return;
        L_0x005c:
            r13.skip(r14);
            return;
        L_0x0060:
            r14 = r14 - r2;
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r6);
            r5 = r12.readBuffer;	 Catch:{ all -> 0x007c }
            r8 = r5.size();	 Catch:{ all -> 0x007c }
            r10 = 0;
            r5 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
            if (r5 != 0) goto L_0x007f;
        L_0x0070:
            r4 = 1;
        L_0x0071:
            r5 = r12.readBuffer;	 Catch:{ all -> 0x007c }
            r7 = r12.receiveBuffer;	 Catch:{ all -> 0x007c }
            r5.writeAll(r7);	 Catch:{ all -> 0x007c }
            if (r4 != 0) goto L_0x0081;
        L_0x007a:
            monitor-exit(r6);	 Catch:{ all -> 0x007c }
            goto L_0x0004;
        L_0x007c:
            r5 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x007c }
            throw r5;
        L_0x007f:
            r4 = 0;
            goto L_0x0071;
        L_0x0081:
            r5 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x007c }
            r5.notifyAll();	 Catch:{ all -> 0x007c }
            goto L_0x007a;
        L_0x0087:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSource.receive(okio.BufferedSource, long):void");
        }

        public Timeout timeout() {
            return SpdyStream.this.readTimeout;
        }

        public void close() throws IOException {
            synchronized (SpdyStream.this) {
                this.closed = true;
                this.readBuffer.clear();
                SpdyStream.this.notifyAll();
            }
            SpdyStream.this.cancelStreamIfNecessary();
        }

        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            } else if (SpdyStream.this.errorCode != null) {
                throw new IOException("stream was reset: " + SpdyStream.this.errorCode);
            }
        }
    }

    class SpdyTimeout extends AsyncTimeout {
        SpdyTimeout() {
        }

        protected void timedOut() {
            SpdyStream.this.closeLater(ErrorCode.CANCEL);
        }

        public void exitAndThrowIfTimedOut() throws InterruptedIOException {
            if (exit()) {
                throw new InterruptedIOException("timeout");
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 0;
        r1 = com.squareup.okhttp.internal.spdy.SpdyStream.class;
        if (r1 == 0) goto L_0x000a;
    L_0x0007:
        $assertionsDisabled = r0;
    L_0x000a:
        r0 = 1;
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.<clinit>():void");
    }

    SpdyStream(int id, SpdyConnection connection, boolean outFinished, boolean inFinished, List<Header> requestHeaders) {
        this.unacknowledgedBytesRead = 0;
        this.readTimeout = new SpdyTimeout();
        this.writeTimeout = new SpdyTimeout();
        this.errorCode = null;
        if (connection == null) {
            throw new NullPointerException("connection == null");
        } else if (requestHeaders != null) {
            this.id = id;
            this.connection = connection;
            this.bytesLeftInWriteWindow = (long) connection.peerSettings.getInitialWindowSize(65536);
            this.source = new SpdyDataSource((long) connection.okHttpSettings.getInitialWindowSize(65536), null);
            this.sink = new SpdyDataSink();
            this.source.finished = inFinished;
            this.sink.finished = outFinished;
            this.requestHeaders = requestHeaders;
        } else {
            throw new NullPointerException("requestHeaders == null");
        }
    }

    public int getId() {
        return this.id;
    }

    public synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if (!this.source.finished) {
            if (!this.source.closed) {
                return true;
            }
        }
        if (!this.sink.finished && !this.sink.closed) {
            return true;
        }
        if (this.responseHeaders != null) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        boolean streamIsClient;
        if ((this.id & 1) != 1) {
            streamIsClient = false;
        } else {
            streamIsClient = true;
        }
        return this.connection.client == streamIsClient;
    }

    public SpdyConnection getConnection() {
        return this.connection;
    }

    public List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }

    public synchronized List<Header> getResponseHeaders() throws IOException {
        this.readTimeout.enter();
        while (this.responseHeaders == null) {
            try {
                if (this.errorCode == null) {
                    waitForIo();
                }
            } finally {
                this.readTimeout.exitAndThrowIfTimedOut();
            }
        }
        break;
        if (this.responseHeaders == null) {
            throw new IOException("stream was reset: " + this.errorCode);
        }
        return this.responseHeaders;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void reply(List<Header> responseHeaders, boolean out) throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        boolean outFinished = false;
        synchronized (this) {
            if (responseHeaders == null) {
                throw new NullPointerException("responseHeaders == null");
            } else if (this.responseHeaders == null) {
                this.responseHeaders = responseHeaders;
                if (!out) {
                    this.sink.finished = true;
                    outFinished = true;
                }
            } else {
                throw new IllegalStateException("reply already sent");
            }
        }
        this.connection.writeSynReply(this.id, outFinished, responseHeaders);
        if (outFinished) {
            this.connection.flush();
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    public Source getSource() {
        return this.source;
    }

    public Sink getSink() {
        synchronized (this) {
            if (this.responseHeaders == null) {
                if (!isLocallyInitiated()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            }
        }
        return this.sink;
    }

    public void close(ErrorCode rstStatusCode) throws IOException {
        if (closeInternal(rstStatusCode)) {
            this.connection.writeSynReset(this.id, rstStatusCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.writeSynResetLater(this.id, errorCode);
        }
    }

    private boolean closeInternal(ErrorCode errorCode) {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            } else if (this.source.finished && this.sink.finished) {
                return false;
            } else {
                this.errorCode = errorCode;
                notifyAll();
                this.connection.removeStream(this.id);
                return true;
            }
        }
    }

    void receiveHeaders(List<Header> headers, HeadersMode headersMode) {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        ErrorCode errorCode = null;
        boolean open = true;
        synchronized (this) {
            if (this.responseHeaders != null) {
                if (headersMode.failIfHeadersPresent()) {
                    errorCode = ErrorCode.STREAM_IN_USE;
                } else {
                    List<Header> newHeaders = new ArrayList();
                    newHeaders.addAll(this.responseHeaders);
                    newHeaders.addAll(headers);
                    this.responseHeaders = newHeaders;
                }
            } else if (headersMode.failIfHeadersAbsent()) {
                errorCode = ErrorCode.PROTOCOL_ERROR;
            } else {
                this.responseHeaders = headers;
                open = isOpen();
                notifyAll();
            }
        }
        if (errorCode != null) {
            closeLater(errorCode);
        } else if (!open) {
            this.connection.removeStream(this.id);
        }
    }

    void receiveData(BufferedSource in, int length) throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        this.source.receive(in, (long) length);
    }

    void receiveFin() {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            this.source.finished = true;
            boolean open = isOpen();
            notifyAll();
        }
        if (!open) {
            this.connection.removeStream(this.id);
        }
    }

    synchronized void receiveRstStream(ErrorCode errorCode) {
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            notifyAll();
        }
    }

    private void cancelStreamIfNecessary() throws IOException {
        boolean cancel = false;
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            if (!this.source.finished) {
                if (this.source.closed) {
                    if (this.sink.finished || this.sink.closed) {
                        cancel = true;
                    }
                }
            }
            boolean open = isOpen();
        }
        if (cancel) {
            close(ErrorCode.CANCEL);
        } else if (!open) {
            this.connection.removeStream(this.id);
        }
    }

    void addBytesToWriteWindow(long delta) {
        this.bytesLeftInWriteWindow += delta;
        if ((delta <= 0 ? 1 : null) == null) {
            notifyAll();
        }
    }

    private void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (this.sink.finished) {
            throw new IOException("stream finished");
        } else if (this.errorCode != null) {
            throw new IOException("stream was reset: " + this.errorCode);
        }
    }

    private void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new InterruptedIOException();
        }
    }
}
