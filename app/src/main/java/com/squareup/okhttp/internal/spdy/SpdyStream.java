package com.squareup.okhttp.internal.spdy;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import okio.a;
import okio.g;
import okio.i;
import okio.k;
import okio.n;
import okio.v;

public final class SpdyStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    long bytesLeftInWriteWindow;
    private final SpdyConnection connection;
    private ErrorCode errorCode = null;
    private final int id;
    private final SpdyTimeout readTimeout = new SpdyTimeout();
    private final List requestHeaders;
    private List responseHeaders;
    final SpdyDataSink sink;
    private final SpdyDataSource source;
    long unacknowledgedBytesRead = 0;
    private final SpdyTimeout writeTimeout = new SpdyTimeout();

    final class SpdyDataSink implements n {
        static final /* synthetic */ boolean $assertionsDisabled;
        private static final long EMIT_BUFFER_SIZE = 16384;
        private boolean closed;
        private boolean finished;
        private final k sendBuffer = new k();

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
        }

        private void emitDataFrame(boolean z) {
            long min;
            boolean z2 = true;
            synchronized (SpdyStream.this) {
                SpdyStream.this.writeTimeout.enter();
                while (true) {
                    if ((SpdyStream.this.bytesLeftInWriteWindow > 0 ? 1 : null) == null && !this.finished) {
                        try {
                            if (!this.closed && SpdyStream.this.errorCode == null) {
                                SpdyStream.this.waitForIo();
                            }
                        } finally {
                            z2 = SpdyStream.this.writeTimeout;
                            z2.exitAndThrowIfTimedOut();
                        }
                    }
                    break;
                }
                SpdyStream.this.checkOutNotClosed();
                min = Math.min(SpdyStream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                SpdyStream spdyStream = SpdyStream.this;
                spdyStream.bytesLeftInWriteWindow -= min;
            }
            SpdyConnection access$500 = SpdyStream.this.connection;
            int access$600 = SpdyStream.this.id;
            if (!z || min != this.sendBuffer.size()) {
                z2 = $assertionsDisabled;
            }
            access$500.writeData(access$600, z2, this.sendBuffer, min);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
            r8 = this;
            r4 = 0;
            r2 = 1;
            r1 = 0;
            r0 = $assertionsDisabled;
            if (r0 == 0) goto L_0x002e;
        L_0x0008:
            r3 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r3);
            r0 = r8.closed;	 Catch:{ all -> 0x003e }
            if (r0 != 0) goto L_0x003c;
        L_0x000f:
            monitor-exit(r3);	 Catch:{ all -> 0x003e }
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = r0.sink;
            r0 = r0.finished;
            if (r0 == 0) goto L_0x0041;
        L_0x0018:
            r1 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r1);
            r0 = 1;
            r8.closed = r0;	 Catch:{ all -> 0x0074 }
            monitor-exit(r1);	 Catch:{ all -> 0x0074 }
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = r0.connection;
            r0.flush();
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0.cancelStreamIfNecessary();
            return;
        L_0x002e:
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = java.lang.Thread.holdsLock(r0);
            if (r0 == 0) goto L_0x0008;
        L_0x0036:
            r0 = new java.lang.AssertionError;
            r0.<init>();
            throw r0;
        L_0x003c:
            monitor-exit(r3);	 Catch:{ all -> 0x003e }
            return;
        L_0x003e:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x003e }
            throw r0;
        L_0x0041:
            r0 = r8.sendBuffer;
            r6 = r0.size();
            r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
            if (r0 > 0) goto L_0x005f;
        L_0x004b:
            r0 = r2;
        L_0x004c:
            if (r0 != 0) goto L_0x0063;
        L_0x004e:
            r0 = r8.sendBuffer;
            r6 = r0.size();
            r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
            if (r0 > 0) goto L_0x0061;
        L_0x0058:
            r0 = r2;
        L_0x0059:
            if (r0 != 0) goto L_0x0018;
        L_0x005b:
            r8.emitDataFrame(r2);
            goto L_0x004e;
        L_0x005f:
            r0 = r1;
            goto L_0x004c;
        L_0x0061:
            r0 = r1;
            goto L_0x0059;
        L_0x0063:
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = r0.connection;
            r1 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r1 = r1.id;
            r3 = 0;
            r0.writeData(r1, r2, r3, r4);
            goto L_0x0018;
        L_0x0074:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0074 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSink.close():void");
        }

        public void flush() {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            synchronized (SpdyStream.this) {
                SpdyStream.this.checkOutNotClosed();
            }
            while (true) {
                if (this.sendBuffer.size() <= 0 ? true : $assertionsDisabled) {
                    SpdyStream.this.connection.flush();
                    return;
                }
                emitDataFrame($assertionsDisabled);
            }
        }

        public g timeout() {
            return SpdyStream.this.writeTimeout;
        }

        public void write(k kVar, long j) {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            this.sendBuffer.write(kVar, j);
            while (true) {
                if (!(this.sendBuffer.size() < EMIT_BUFFER_SIZE ? true : $assertionsDisabled)) {
                    emitDataFrame($assertionsDisabled);
                } else {
                    return;
                }
            }
        }
    }

    final class SpdyDataSource implements v {
        static final /* synthetic */ boolean $assertionsDisabled;
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final k readBuffer;
        private final k receiveBuffer;

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

        private SpdyDataSource(long j) {
            this.receiveBuffer = new k();
            this.readBuffer = new k();
            this.maxByteCount = j;
        }

        private void checkNotClosed() {
            if (this.closed) {
                throw new IOException("stream closed");
            } else if (SpdyStream.this.errorCode != null) {
                throw new IOException("stream was reset: " + SpdyStream.this.errorCode);
            }
        }

        private void waitUntilReadable() {
            SpdyStream.this.readTimeout.enter();
            while (this.readBuffer.size() == 0 && !this.finished) {
                try {
                    if (this.closed || SpdyStream.this.errorCode != null) {
                        break;
                    }
                    SpdyStream.this.waitForIo();
                } catch (Throwable th) {
                    SpdyStream.this.readTimeout.exitAndThrowIfTimedOut();
                }
            }
            SpdyStream.this.readTimeout.exitAndThrowIfTimedOut();
        }

        public void close() {
            synchronized (SpdyStream.this) {
                this.closed = true;
                this.readBuffer.clear();
                SpdyStream.this.notifyAll();
            }
            SpdyStream.this.cancelStreamIfNecessary();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long read(okio.k r11, long r12) {
            /*
            r10 = this;
            r0 = 1;
            r6 = 0;
            r1 = 0;
            r2 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1));
            if (r2 < 0) goto L_0x0025;
        L_0x0008:
            r2 = r0;
        L_0x0009:
            if (r2 != 0) goto L_0x0027;
        L_0x000b:
            r0 = new java.lang.IllegalArgumentException;
            r1 = new java.lang.StringBuilder;
            r1.<init>();
            r2 = "byteCount < 0: ";
            r1 = r1.append(r2);
            r1 = r1.append(r12);
            r1 = r1.toString();
            r0.<init>(r1);
            throw r0;
        L_0x0025:
            r2 = r1;
            goto L_0x0009;
        L_0x0027:
            r3 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r3);
            r10.waitUntilReadable();	 Catch:{ all -> 0x00dc }
            r10.checkNotClosed();	 Catch:{ all -> 0x00dc }
            r2 = r10.readBuffer;	 Catch:{ all -> 0x00dc }
            r4 = r2.size();	 Catch:{ all -> 0x00dc }
            r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r2 != 0) goto L_0x003e;
        L_0x003a:
            r0 = -1;
            monitor-exit(r3);	 Catch:{ all -> 0x00dc }
            return r0;
        L_0x003e:
            r2 = r10.readBuffer;	 Catch:{ all -> 0x00dc }
            r4 = r10.readBuffer;	 Catch:{ all -> 0x00dc }
            r4 = r4.size();	 Catch:{ all -> 0x00dc }
            r4 = java.lang.Math.min(r12, r4);	 Catch:{ all -> 0x00dc }
            r4 = r2.read(r11, r4);	 Catch:{ all -> 0x00dc }
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r6 = r2.unacknowledgedBytesRead;	 Catch:{ all -> 0x00dc }
            r6 = r6 + r4;
            r2.unacknowledgedBytesRead = r6;	 Catch:{ all -> 0x00dc }
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r6 = r2.unacknowledgedBytesRead;	 Catch:{ all -> 0x00dc }
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r2 = r2.connection;	 Catch:{ all -> 0x00dc }
            r2 = r2.okHttpSettings;	 Catch:{ all -> 0x00dc }
            r8 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
            r2 = r2.getInitialWindowSize(r8);	 Catch:{ all -> 0x00dc }
            r2 = r2 / 2;
            r8 = (long) r2;	 Catch:{ all -> 0x00dc }
            r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
            if (r2 >= 0) goto L_0x00da;
        L_0x006e:
            r2 = r0;
        L_0x006f:
            if (r2 != 0) goto L_0x008a;
        L_0x0071:
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r2 = r2.connection;	 Catch:{ all -> 0x00dc }
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r6 = r6.id;	 Catch:{ all -> 0x00dc }
            r7 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r8 = r7.unacknowledgedBytesRead;	 Catch:{ all -> 0x00dc }
            r2.writeWindowUpdateLater(r6, r8);	 Catch:{ all -> 0x00dc }
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r6 = 0;
            r2.unacknowledgedBytesRead = r6;	 Catch:{ all -> 0x00dc }
        L_0x008a:
            monitor-exit(r3);	 Catch:{ all -> 0x00dc }
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r2 = r2.connection;
            monitor-enter(r2);
            r3 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00e1 }
            r3 = r3.connection;	 Catch:{ all -> 0x00e1 }
            r6 = r3.unacknowledgedBytesRead;	 Catch:{ all -> 0x00e1 }
            r6 = r6 + r4;
            r3.unacknowledgedBytesRead = r6;	 Catch:{ all -> 0x00e1 }
            r3 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00e1 }
            r3 = r3.connection;	 Catch:{ all -> 0x00e1 }
            r6 = r3.unacknowledgedBytesRead;	 Catch:{ all -> 0x00e1 }
            r3 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00e1 }
            r3 = r3.connection;	 Catch:{ all -> 0x00e1 }
            r3 = r3.okHttpSettings;	 Catch:{ all -> 0x00e1 }
            r8 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
            r3 = r3.getInitialWindowSize(r8);	 Catch:{ all -> 0x00e1 }
            r3 = r3 / 2;
            r8 = (long) r3;	 Catch:{ all -> 0x00e1 }
            r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
            if (r3 >= 0) goto L_0x00df;
        L_0x00ba:
            if (r0 != 0) goto L_0x00d8;
        L_0x00bc:
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00e1 }
            r0 = r0.connection;	 Catch:{ all -> 0x00e1 }
            r1 = 0;
            r3 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00e1 }
            r3 = r3.connection;	 Catch:{ all -> 0x00e1 }
            r6 = r3.unacknowledgedBytesRead;	 Catch:{ all -> 0x00e1 }
            r0.writeWindowUpdateLater(r1, r6);	 Catch:{ all -> 0x00e1 }
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00e1 }
            r0 = r0.connection;	 Catch:{ all -> 0x00e1 }
            r6 = 0;
            r0.unacknowledgedBytesRead = r6;	 Catch:{ all -> 0x00e1 }
        L_0x00d8:
            monitor-exit(r2);	 Catch:{ all -> 0x00e1 }
            return r4;
        L_0x00da:
            r2 = r1;
            goto L_0x006f;
        L_0x00dc:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x00dc }
            throw r0;
        L_0x00df:
            r0 = r1;
            goto L_0x00ba;
        L_0x00e1:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x00e1 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSource.read(okio.k, long):long");
        }

        void receive(a aVar, long j) {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            while (true) {
                if ((j <= 0 ? 1 : null) == null) {
                    boolean z;
                    Object obj;
                    synchronized (SpdyStream.this) {
                        z = this.finished;
                        obj = (((this.readBuffer.size() + j) > this.maxByteCount ? 1 : ((this.readBuffer.size() + j) == this.maxByteCount ? 0 : -1)) <= 0 ? 1 : null) == null ? 1 : null;
                    }
                    if (obj != null) {
                        aVar.skip(j);
                        SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                        return;
                    } else if (z) {
                        aVar.skip(j);
                        return;
                    } else {
                        long read = aVar.read(this.receiveBuffer, j);
                        if (read == -1) {
                            throw new EOFException();
                        }
                        j -= read;
                        synchronized (SpdyStream.this) {
                            obj = this.readBuffer.size() == 0 ? 1 : null;
                            this.readBuffer.Ab(this.receiveBuffer);
                            if (obj != null) {
                                SpdyStream.this.notifyAll();
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }

        public g timeout() {
            return SpdyStream.this.readTimeout;
        }
    }

    class SpdyTimeout extends i {
        SpdyTimeout() {
        }

        public void exitAndThrowIfTimedOut() {
            if (exit()) {
                throw new InterruptedIOException("timeout");
            }
        }

        protected void timedOut() {
            SpdyStream.this.closeLater(ErrorCode.CANCEL);
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

    SpdyStream(int i, SpdyConnection spdyConnection, boolean z, boolean z2, List list) {
        if (spdyConnection == null) {
            throw new NullPointerException("connection == null");
        } else if (list != null) {
            this.id = i;
            this.connection = spdyConnection;
            this.bytesLeftInWriteWindow = (long) spdyConnection.peerSettings.getInitialWindowSize(65536);
            this.source = new SpdyDataSource((long) spdyConnection.okHttpSettings.getInitialWindowSize(65536));
            this.sink = new SpdyDataSink();
            this.source.finished = z2;
            this.sink.finished = z;
            this.requestHeaders = list;
        } else {
            throw new NullPointerException("requestHeaders == null");
        }
    }

    private void cancelStreamIfNecessary() {
        Object obj = null;
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            if (!this.source.finished) {
                if (this.source.closed) {
                    if (this.sink.finished || this.sink.closed) {
                        obj = 1;
                    }
                }
            }
            boolean isOpen = isOpen();
        }
        if (obj != null) {
            close(ErrorCode.CANCEL);
        } else if (!isOpen) {
            this.connection.removeStream(this.id);
        }
    }

    private void checkOutNotClosed() {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (this.sink.finished) {
            throw new IOException("stream finished");
        } else if (this.errorCode != null) {
            throw new IOException("stream was reset: " + this.errorCode);
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

    private void waitForIo() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new InterruptedIOException();
        }
    }

    void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        if ((j <= 0 ? 1 : null) == null) {
            notifyAll();
        }
    }

    public void close(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.writeSynReset(this.id, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.writeSynResetLater(this.id, errorCode);
        }
    }

    public SpdyConnection getConnection() {
        return this.connection;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public int getId() {
        return this.id;
    }

    public List getRequestHeaders() {
        return this.requestHeaders;
    }

    public synchronized List getResponseHeaders() {
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
        if (this.responseHeaders == null) {
            throw new IOException("stream was reset: " + this.errorCode);
        }
        return this.responseHeaders;
    }

    public n getSink() {
        synchronized (this) {
            if (this.responseHeaders == null) {
                if (!isLocallyInitiated()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            }
        }
        return this.sink;
    }

    public v getSource() {
        return this.source;
    }

    public boolean isLocallyInitiated() {
        return this.connection.client == ((this.id & 1) == 1);
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

    public g readTimeout() {
        return this.readTimeout;
    }

    void receiveData(a aVar, int i) {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        this.source.receive(aVar, (long) i);
    }

    void receiveFin() {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            this.source.finished = true;
            boolean isOpen = isOpen();
            notifyAll();
        }
        if (!isOpen) {
            this.connection.removeStream(this.id);
        }
    }

    void receiveHeaders(List list, HeadersMode headersMode) {
        ErrorCode errorCode = null;
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        boolean z = true;
        synchronized (this) {
            if (this.responseHeaders != null) {
                if (headersMode.failIfHeadersPresent()) {
                    errorCode = ErrorCode.STREAM_IN_USE;
                } else {
                    List arrayList = new ArrayList();
                    arrayList.addAll(this.responseHeaders);
                    arrayList.addAll(list);
                    this.responseHeaders = arrayList;
                }
            } else if (headersMode.failIfHeadersAbsent()) {
                errorCode = ErrorCode.PROTOCOL_ERROR;
            } else {
                this.responseHeaders = list;
                z = isOpen();
                notifyAll();
            }
        }
        if (errorCode != null) {
            closeLater(errorCode);
        } else if (!z) {
            this.connection.removeStream(this.id);
        }
    }

    synchronized void receiveRstStream(ErrorCode errorCode) {
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            notifyAll();
        }
    }

    public void reply(List list, boolean z) {
        boolean z2 = false;
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            if (list == null) {
                throw new NullPointerException("responseHeaders == null");
            } else if (this.responseHeaders == null) {
                this.responseHeaders = list;
                if (!z) {
                    this.sink.finished = true;
                    z2 = true;
                }
            } else {
                throw new IllegalStateException("reply already sent");
            }
        }
        this.connection.writeSynReply(this.id, z2, list);
        if (z2) {
            this.connection.flush();
        }
    }

    public g writeTimeout() {
        return this.writeTimeout;
    }
}
