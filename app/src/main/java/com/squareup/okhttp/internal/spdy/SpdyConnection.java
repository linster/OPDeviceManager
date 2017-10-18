package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.a;
import okio.j;
import okio.k;

public final class SpdyConnection implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private static final ExecutorService executor;
    long bytesLeftInWriteWindow;
    final boolean client;
    private final Set currentPushRequests;
    final FrameWriter frameWriter;
    private final IncomingStreamHandler handler;
    private final String hostName;
    private long idleStartTimeNs;
    private int lastGoodStreamId;
    private int nextPingId;
    private int nextStreamId;
    final Settings okHttpSettings;
    final Settings peerSettings;
    private Map pings;
    final Protocol protocol;
    private final ExecutorService pushExecutor;
    private final PushObserver pushObserver;
    final Reader readerRunnable;
    private boolean receivedInitialPeerSettings;
    private boolean shutdown;
    final Socket socket;
    private final Map streams;
    long unacknowledgedBytesRead;
    final Variant variant;

    public class Builder {
        private boolean client;
        private IncomingStreamHandler handler;
        private String hostName;
        private Protocol protocol;
        private PushObserver pushObserver;
        private Socket socket;

        public Builder(String str, boolean z, Socket socket) {
            this.handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
            this.protocol = Protocol.SPDY_3;
            this.pushObserver = PushObserver.CANCEL;
            this.hostName = str;
            this.client = z;
            this.socket = socket;
        }

        public Builder(boolean z, Socket socket) {
            this(((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), z, socket);
        }

        public SpdyConnection build() {
            return new SpdyConnection();
        }

        public Builder handler(IncomingStreamHandler incomingStreamHandler) {
            this.handler = incomingStreamHandler;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.pushObserver = pushObserver;
            return this;
        }
    }

    class Reader extends NamedRunnable implements Handler {
        FrameReader frameReader;

        private Reader() {
            super("OkHttp %s", r5.hostName);
        }

        private void ackSettingsLater(final Settings settings) {
            SpdyConnection.executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{SpdyConnection.this.hostName}) {
                public void execute() {
                    try {
                        SpdyConnection.this.frameWriter.ackSettings(settings);
                    } catch (IOException e) {
                    }
                }
            });
        }

        public void ackSettings() {
        }

        public void alternateService(int i, String str, ByteString byteString, String str2, int i2, long j) {
        }

        public void data(boolean z, int i, a aVar, int i2) {
            if (SpdyConnection.this.pushedStream(i)) {
                SpdyConnection.this.pushDataLater(i, aVar, i2, z);
                return;
            }
            SpdyStream stream = SpdyConnection.this.getStream(i);
            if (stream != null) {
                stream.receiveData(aVar, i2);
                if (z) {
                    stream.receiveFin();
                }
                return;
            }
            SpdyConnection.this.writeSynResetLater(i, ErrorCode.INVALID_STREAM);
            aVar.skip((long) i2);
        }

        protected void execute() {
            ErrorCode errorCode;
            Throwable th;
            ErrorCode errorCode2 = ErrorCode.INTERNAL_ERROR;
            ErrorCode errorCode3 = ErrorCode.INTERNAL_ERROR;
            try {
                this.frameReader = SpdyConnection.this.variant.newReader(j.AE(j.AL(SpdyConnection.this.socket)), SpdyConnection.this.client);
                if (!SpdyConnection.this.client) {
                    this.frameReader.readConnectionPreface();
                }
                do {
                } while (this.frameReader.nextFrame(this));
                try {
                    SpdyConnection.this.close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
                } catch (IOException e) {
                }
                Util.closeQuietly(this.frameReader);
            } catch (IOException e2) {
                errorCode = ErrorCode.PROTOCOL_ERROR;
                try {
                    SpdyConnection.this.close(errorCode, ErrorCode.PROTOCOL_ERROR);
                } catch (IOException e3) {
                }
                Util.closeQuietly(this.frameReader);
            } catch (Throwable th2) {
                th = th2;
                SpdyConnection.this.close(errorCode, errorCode3);
                Util.closeQuietly(this.frameReader);
                throw th;
            }
        }

        public void goAway(int i, ErrorCode errorCode, ByteString byteString) {
            if (byteString.size() <= 0) {
            }
            synchronized (SpdyConnection.this) {
                SpdyStream[] spdyStreamArr = (SpdyStream[]) SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
                SpdyConnection.this.shutdown = true;
            }
            for (SpdyStream spdyStream : spdyStreamArr) {
                if (spdyStream.getId() > i && spdyStream.isLocallyInitiated()) {
                    spdyStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
                    SpdyConnection.this.removeStream(spdyStream.getId());
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void headers(boolean r9, boolean r10, int r11, int r12, java.util.List r13, com.squareup.okhttp.internal.spdy.HeadersMode r14) {
            /*
            r8 = this;
            r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            r0 = r0.pushedStream(r11);
            if (r0 != 0) goto L_0x0028;
        L_0x0008:
            r6 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            monitor-enter(r6);
            r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r0 = r0.shutdown;	 Catch:{ all -> 0x0098 }
            if (r0 != 0) goto L_0x002e;
        L_0x0013:
            r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r0 = r0.getStream(r11);	 Catch:{ all -> 0x0098 }
            if (r0 == 0) goto L_0x0030;
        L_0x001b:
            monitor-exit(r6);	 Catch:{ all -> 0x0098 }
            r1 = r14.failIfStreamPresent();
            if (r1 != 0) goto L_0x009b;
        L_0x0022:
            r0.receiveHeaders(r13, r14);
            if (r10 != 0) goto L_0x00a6;
        L_0x0027:
            return;
        L_0x0028:
            r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            r0.pushHeadersLater(r11, r13, r10);
            return;
        L_0x002e:
            monitor-exit(r6);	 Catch:{ all -> 0x0098 }
            return;
        L_0x0030:
            r0 = r14.failIfStreamAbsent();	 Catch:{ all -> 0x0098 }
            if (r0 != 0) goto L_0x008b;
        L_0x0036:
            r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r0 = r0.lastGoodStreamId;	 Catch:{ all -> 0x0098 }
            if (r11 <= r0) goto L_0x0094;
        L_0x003e:
            r0 = r11 % 2;
            r1 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r1 = r1.nextStreamId;	 Catch:{ all -> 0x0098 }
            r1 = r1 % 2;
            if (r0 == r1) goto L_0x0096;
        L_0x004a:
            r0 = new com.squareup.okhttp.internal.spdy.SpdyStream;	 Catch:{ all -> 0x0098 }
            r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r1 = r11;
            r3 = r9;
            r4 = r10;
            r5 = r13;
            r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x0098 }
            r1 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r1.lastGoodStreamId = r11;	 Catch:{ all -> 0x0098 }
            r1 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r1 = r1.streams;	 Catch:{ all -> 0x0098 }
            r2 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x0098 }
            r1.put(r2, r0);	 Catch:{ all -> 0x0098 }
            r1 = com.squareup.okhttp.internal.spdy.SpdyConnection.executor;	 Catch:{ all -> 0x0098 }
            r2 = new com.squareup.okhttp.internal.spdy.SpdyConnection$Reader$1;	 Catch:{ all -> 0x0098 }
            r3 = "OkHttp %s stream %d";
            r4 = 2;
            r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x0098 }
            r5 = 0;
            r7 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r7 = r7.hostName;	 Catch:{ all -> 0x0098 }
            r4[r5] = r7;	 Catch:{ all -> 0x0098 }
            r5 = 1;
            r7 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x0098 }
            r4[r5] = r7;	 Catch:{ all -> 0x0098 }
            r2.<init>(r3, r4, r0);	 Catch:{ all -> 0x0098 }
            r1.execute(r2);	 Catch:{ all -> 0x0098 }
            monitor-exit(r6);	 Catch:{ all -> 0x0098 }
            return;
        L_0x008b:
            r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0098 }
            r1 = com.squareup.okhttp.internal.spdy.ErrorCode.INVALID_STREAM;	 Catch:{ all -> 0x0098 }
            r0.writeSynResetLater(r11, r1);	 Catch:{ all -> 0x0098 }
            monitor-exit(r6);	 Catch:{ all -> 0x0098 }
            return;
        L_0x0094:
            monitor-exit(r6);	 Catch:{ all -> 0x0098 }
            return;
        L_0x0096:
            monitor-exit(r6);	 Catch:{ all -> 0x0098 }
            return;
        L_0x0098:
            r0 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x0098 }
            throw r0;
        L_0x009b:
            r1 = com.squareup.okhttp.internal.spdy.ErrorCode.PROTOCOL_ERROR;
            r0.closeLater(r1);
            r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            r0.removeStream(r11);
            return;
        L_0x00a6:
            r0.receiveFin();
            goto L_0x0027;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyConnection.Reader.headers(boolean, boolean, int, int, java.util.List, com.squareup.okhttp.internal.spdy.HeadersMode):void");
        }

        public void ping(boolean z, int i, int i2) {
            if (z) {
                Ping access$2200 = SpdyConnection.this.removePing(i);
                if (access$2200 != null) {
                    access$2200.receive();
                    return;
                }
                return;
            }
            SpdyConnection.this.writePingLater(true, i, i2, null);
        }

        public void priority(int i, int i2, int i3, boolean z) {
        }

        public void pushPromise(int i, int i2, List list) {
            SpdyConnection.this.pushRequestLater(i2, list);
        }

        public void rstStream(int i, ErrorCode errorCode) {
            if (SpdyConnection.this.pushedStream(i)) {
                SpdyConnection.this.pushResetLater(i, errorCode);
                return;
            }
            SpdyStream removeStream = SpdyConnection.this.removeStream(i);
            if (removeStream != null) {
                removeStream.receiveRstStream(errorCode);
            }
        }

        public void settings(boolean z, Settings settings) {
            SpdyStream[] spdyStreamArr;
            long j;
            synchronized (SpdyConnection.this) {
                int initialWindowSize = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
                if (z) {
                    SpdyConnection.this.peerSettings.clear();
                }
                SpdyConnection.this.peerSettings.merge(settings);
                if (SpdyConnection.this.getProtocol() == Protocol.HTTP_2) {
                    ackSettingsLater(settings);
                }
                int initialWindowSize2 = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
                if (initialWindowSize2 == -1 || initialWindowSize2 == initialWindowSize) {
                    spdyStreamArr = null;
                    j = 0;
                } else {
                    long j2 = (long) (initialWindowSize2 - initialWindowSize);
                    if (!SpdyConnection.this.receivedInitialPeerSettings) {
                        SpdyConnection.this.addBytesToWriteWindow(j2);
                        SpdyConnection.this.receivedInitialPeerSettings = true;
                    }
                    if (SpdyConnection.this.streams.isEmpty()) {
                        j = j2;
                        spdyStreamArr = null;
                    } else {
                        j = j2;
                        spdyStreamArr = (SpdyStream[]) SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
                    }
                }
            }
            if (spdyStreamArr != null && j != 0) {
                for (SpdyStream spdyStream : spdyStreamArr) {
                    synchronized (spdyStream) {
                        spdyStream.addBytesToWriteWindow(j);
                    }
                }
            }
        }

        public void windowUpdate(int i, long j) {
            if (i != 0) {
                SpdyStream stream = SpdyConnection.this.getStream(i);
                if (stream != null) {
                    synchronized (stream) {
                        stream.addBytesToWriteWindow(j);
                    }
                    return;
                }
                return;
            }
            synchronized (SpdyConnection.this) {
                SpdyConnection spdyConnection = SpdyConnection.this;
                spdyConnection.bytesLeftInWriteWindow += j;
                SpdyConnection.this.notifyAll();
            }
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:261)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:235)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:227)
	at java.util.ArrayList.add(ArrayList.java:458)
	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:146)
	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:94)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = 1;
        r2 = 0;
        r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.class;
        if (r0 == 0) goto L_0x0026;
    L_0x0008:
        r0 = r2;
    L_0x0009:
        $assertionsDisabled = r0;
        r1 = new java.util.concurrent.ThreadPoolExecutor;
        r3 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r4 = 60;
        r6 = java.util.concurrent.TimeUnit.SECONDS;
        r7 = new java.util.concurrent.SynchronousQueue;
        r7.<init>();
        r0 = "OkHttp SpdyConnection";
        r8 = com.squareup.okhttp.internal.Util.threadFactory(r0, r8);
        r1.<init>(r2, r3, r4, r6, r7, r8);
        executor = r1;
    L_0x0026:
        r0 = r8;
        goto L_0x0009;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyConnection.<clinit>():void");
    }

    private SpdyConnection(Builder builder) {
        int i = 2;
        this.streams = new HashMap();
        this.idleStartTimeNs = System.nanoTime();
        this.unacknowledgedBytesRead = 0;
        this.okHttpSettings = new Settings();
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = $assertionsDisabled;
        this.currentPushRequests = new LinkedHashSet();
        this.protocol = builder.protocol;
        this.pushObserver = builder.pushObserver;
        this.client = builder.client;
        this.handler = builder.handler;
        this.nextStreamId = !builder.client ? 2 : 1;
        if (builder.client && this.protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        if (builder.client) {
            i = 1;
        }
        this.nextPingId = i;
        if (builder.client) {
            this.okHttpSettings.set(7, 0, OKHTTP_CLIENT_WINDOW_SIZE);
        }
        this.hostName = builder.hostName;
        if (this.protocol == Protocol.HTTP_2) {
            this.variant = new Http2();
            this.pushExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", new Object[]{this.hostName}), true));
            this.peerSettings.set(7, 0, 65535);
            this.peerSettings.set(5, 0, 16384);
        } else if (this.protocol != Protocol.SPDY_3) {
            throw new AssertionError(this.protocol);
        } else {
            this.variant = new Spdy3();
            this.pushExecutor = null;
        }
        this.bytesLeftInWriteWindow = (long) this.peerSettings.getInitialWindowSize(65536);
        this.socket = builder.socket;
        this.frameWriter = this.variant.newWriter(j.AF(j.AI(builder.socket)), this.client);
        this.readerRunnable = new Reader();
        new Thread(this.readerRunnable).start();
    }

    private void close(ErrorCode errorCode, ErrorCode errorCode2) {
        IOException iOException;
        IOException e;
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        SpdyStream[] spdyStreamArr;
        Ping[] pingArr;
        try {
            shutdown(errorCode);
            iOException = null;
        } catch (IOException e2) {
            iOException = e2;
        }
        synchronized (this) {
            if (this.streams.isEmpty()) {
                spdyStreamArr = null;
            } else {
                SpdyStream[] spdyStreamArr2 = (SpdyStream[]) this.streams.values().toArray(new SpdyStream[this.streams.size()]);
                this.streams.clear();
                setIdle($assertionsDisabled);
                spdyStreamArr = spdyStreamArr2;
            }
            if (this.pings == null) {
                pingArr = null;
            } else {
                Ping[] pingArr2 = (Ping[]) this.pings.values().toArray(new Ping[this.pings.size()]);
                this.pings = null;
                pingArr = pingArr2;
            }
        }
        if (spdyStreamArr != null) {
            e2 = iOException;
            for (SpdyStream close : spdyStreamArr) {
                try {
                    close.close(errorCode2);
                } catch (IOException iOException2) {
                    if (e2 != null) {
                        e2 = iOException2;
                    }
                }
            }
            iOException2 = e2;
        }
        if (pingArr != null) {
            for (Ping cancel : pingArr) {
                cancel.cancel();
            }
        }
        try {
            this.frameWriter.close();
            e2 = iOException2;
        } catch (IOException e3) {
            e2 = e3;
            if (iOException2 != null) {
                e2 = iOException2;
            }
        }
        try {
            this.socket.close();
        } catch (IOException e4) {
            e2 = e4;
        }
        if (e2 != null) {
            throw e2;
        }
    }

    private SpdyStream newStream(int i, List list, boolean z, boolean z2) {
        SpdyStream spdyStream;
        boolean z3 = z ? $assertionsDisabled : true;
        boolean z4 = z2 ? $assertionsDisabled : true;
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                int i2 = this.nextStreamId;
                this.nextStreamId += 2;
                spdyStream = new SpdyStream(i2, this, z3, z4, list);
                if (spdyStream.isOpen()) {
                    this.streams.put(Integer.valueOf(i2), spdyStream);
                    setIdle($assertionsDisabled);
                }
            }
            if (i == 0) {
                this.frameWriter.synStream(z3, z4, i2, i, list);
            } else if (this.client) {
                throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            } else {
                this.frameWriter.pushPromise(i, i2, list);
            }
        }
        if (!z) {
            this.frameWriter.flush();
        }
        return spdyStream;
    }

    private void pushDataLater(int i, a aVar, int i2, boolean z) {
        final k kVar = new k();
        aVar.zM((long) i2);
        aVar.read(kVar, (long) i2);
        if (kVar.size() != ((long) i2)) {
            throw new IOException(kVar.size() + " != " + i2);
        }
        final int i3 = i;
        final int i4 = i2;
        final boolean z2 = z;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                try {
                    boolean onData = SpdyConnection.this.pushObserver.onData(i3, kVar, i4, z2);
                    if (onData) {
                        SpdyConnection.this.frameWriter.rstStream(i3, ErrorCode.CANCEL);
                    }
                    if (onData || z2) {
                        synchronized (SpdyConnection.this) {
                            SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i3));
                        }
                    }
                } catch (IOException e) {
                }
            }
        });
    }

    private void pushHeadersLater(int i, List list, boolean z) {
        final int i2 = i;
        final List list2 = list;
        final boolean z2 = z;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                boolean onHeaders = SpdyConnection.this.pushObserver.onHeaders(i2, list2, z2);
                if (onHeaders) {
                    SpdyConnection.this.frameWriter.rstStream(i2, ErrorCode.CANCEL);
                }
                if (onHeaders || z2) {
                    try {
                        synchronized (SpdyConnection.this) {
                            SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                        }
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    private void pushRequestLater(int i, List list) {
        synchronized (this) {
            if (this.currentPushRequests.contains(Integer.valueOf(i))) {
                writeSynResetLater(i, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(Integer.valueOf(i));
            final int i2 = i;
            final List list2 = list;
            this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
                public void execute() {
                    if (SpdyConnection.this.pushObserver.onRequest(i2, list2)) {
                        try {
                            SpdyConnection.this.frameWriter.rstStream(i2, ErrorCode.CANCEL);
                            synchronized (SpdyConnection.this) {
                                SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            });
        }
    }

    private void pushResetLater(int i, ErrorCode errorCode) {
        final int i2 = i;
        final ErrorCode errorCode2 = errorCode;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                SpdyConnection.this.pushObserver.onReset(i2, errorCode2);
                synchronized (SpdyConnection.this) {
                    SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                }
            }
        });
    }

    private boolean pushedStream(int i) {
        return (this.protocol == Protocol.HTTP_2 && i != 0 && (i & 1) == 0) ? true : $assertionsDisabled;
    }

    private synchronized Ping removePing(int i) {
        Ping ping = null;
        synchronized (this) {
            if (this.pings != null) {
                ping = (Ping) this.pings.remove(Integer.valueOf(i));
            }
        }
        return ping;
    }

    private synchronized void setIdle(boolean z) {
        this.idleStartTimeNs = !z ? Long.MAX_VALUE : System.nanoTime();
    }

    private void writePing(boolean z, int i, int i2, Ping ping) {
        synchronized (this.frameWriter) {
            if (ping != null) {
                ping.send();
            }
            this.frameWriter.ping(z, i, i2);
        }
    }

    private void writePingLater(boolean z, int i, int i2, Ping ping) {
        final boolean z2 = z;
        final int i3 = i;
        final int i4 = i2;
        final Ping ping2 = ping;
        executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostName, Integer.valueOf(i), Integer.valueOf(i2)}) {
            public void execute() {
                try {
                    SpdyConnection.this.writePing(z2, i3, i4, ping2);
                } catch (IOException e) {
                }
            }
        });
    }

    void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        if ((j <= 0 ? 1 : null) == null) {
            notifyAll();
        }
    }

    public void close() {
        close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    public void flush() {
        this.frameWriter.flush();
    }

    public synchronized long getIdleStartTimeNs() {
        return this.idleStartTimeNs;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    synchronized SpdyStream getStream(int i) {
        return (SpdyStream) this.streams.get(Integer.valueOf(i));
    }

    public synchronized boolean isIdle() {
        return this.idleStartTimeNs != Long.MAX_VALUE ? true : $assertionsDisabled;
    }

    public SpdyStream newStream(List list, boolean z, boolean z2) {
        return newStream(0, list, z, z2);
    }

    public synchronized int openStreamCount() {
        return this.streams.size();
    }

    public Ping ping() {
        int i;
        Ping ping = new Ping();
        synchronized (this) {
            if (this.shutdown) {
                throw new IOException("shutdown");
            }
            i = this.nextPingId;
            this.nextPingId += 2;
            if (this.pings == null) {
                this.pings = new HashMap();
            }
            this.pings.put(Integer.valueOf(i), ping);
        }
        writePing($assertionsDisabled, i, 1330343787, ping);
        return ping;
    }

    public SpdyStream pushStream(int i, List list, boolean z) {
        if (this.client) {
            throw new IllegalStateException("Client cannot push requests.");
        } else if (this.protocol == Protocol.HTTP_2) {
            return newStream(i, list, z, $assertionsDisabled);
        } else {
            throw new IllegalStateException("protocol != HTTP_2");
        }
    }

    synchronized SpdyStream removeStream(int i) {
        SpdyStream spdyStream;
        spdyStream = (SpdyStream) this.streams.remove(Integer.valueOf(i));
        if (spdyStream != null) {
            if (this.streams.isEmpty()) {
                setIdle(true);
            }
        }
        return spdyStream;
    }

    public void sendConnectionPreface() {
        this.frameWriter.connectionPreface();
        this.frameWriter.settings(this.okHttpSettings);
        int initialWindowSize = this.okHttpSettings.getInitialWindowSize(65536);
        if (initialWindowSize != 65536) {
            this.frameWriter.windowUpdate(0, (long) (initialWindowSize - 65536));
        }
    }

    public void shutdown(ErrorCode errorCode) {
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                int i = this.lastGoodStreamId;
                this.frameWriter.goAway(i, errorCode, Util.EMPTY_BYTE_ARRAY);
            }
        }
    }

    public void writeData(int i, boolean z, k kVar, long j) {
        if (j == 0) {
            this.frameWriter.data(z, i, kVar, 0);
            return;
        }
        while (true) {
            if ((j <= 0 ? 1 : 0) == 0) {
                int min;
                synchronized (this) {
                    while (true) {
                        try {
                            if ((this.bytesLeftInWriteWindow > 0 ? 1 : 0) != 0) {
                                break;
                            }
                            wait();
                        } catch (InterruptedException e) {
                            throw new InterruptedIOException();
                        }
                    }
                    min = Math.min((int) Math.min(j, this.bytesLeftInWriteWindow), this.frameWriter.maxDataLength());
                    this.bytesLeftInWriteWindow -= (long) min;
                }
                j -= (long) min;
                FrameWriter frameWriter = this.frameWriter;
                boolean z2 = (z && j == 0) ? true : $assertionsDisabled;
                frameWriter.data(z2, i, kVar, min);
            } else {
                return;
            }
        }
    }

    void writeSynReply(int i, boolean z, List list) {
        this.frameWriter.synReply(z, i, list);
    }

    void writeSynReset(int i, ErrorCode errorCode) {
        this.frameWriter.rstStream(i, errorCode);
    }

    void writeSynResetLater(int i, ErrorCode errorCode) {
        final int i2 = i;
        final ErrorCode errorCode2 = errorCode;
        executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                try {
                    SpdyConnection.this.writeSynReset(i2, errorCode2);
                } catch (IOException e) {
                }
            }
        });
    }

    void writeWindowUpdateLater(int i, long j) {
        final int i2 = i;
        final long j2 = j;
        executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                try {
                    SpdyConnection.this.frameWriter.windowUpdate(i2, j2);
                } catch (IOException e) {
                }
            }
        });
    }
}
