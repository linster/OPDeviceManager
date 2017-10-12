package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
    private static final ConnectionPool systemDefault = null;
    private final LinkedList<Connection> connections;
    private final Runnable connectionsCleanupRunnable;
    private Executor executor;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    static {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.squareup.okhttp.ConnectionPool.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:113)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:367)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:357)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:103)
	... 5 more
*/
        /*
        r6 = 0;
        r5 = "http.keepAlive";
        r0 = java.lang.System.getProperty(r5);
        r5 = "http.keepAliveDuration";
        r1 = java.lang.System.getProperty(r5);
        r5 = "http.maxConnections";
        r4 = java.lang.System.getProperty(r5);
        if (r1 != 0) goto L_0x0028;
    L_0x0018:
        r2 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
    L_0x001b:
        if (r0 != 0) goto L_0x002d;
    L_0x001d:
        if (r4 != 0) goto L_0x003b;
    L_0x001f:
        r5 = new com.squareup.okhttp.ConnectionPool;
        r6 = 5;
        r5.<init>(r6, r2);
        systemDefault = r5;
    L_0x0028:
        r2 = java.lang.Long.parseLong(r1);
        goto L_0x001b;
    L_0x002d:
        r5 = java.lang.Boolean.parseBoolean(r0);
        if (r5 != 0) goto L_0x001d;
    L_0x0033:
        r5 = new com.squareup.okhttp.ConnectionPool;
        r5.<init>(r6, r2);
        systemDefault = r5;
        goto L_0x0027;
    L_0x003b:
        r5 = new com.squareup.okhttp.ConnectionPool;
        r6 = java.lang.Integer.parseInt(r4);
        r5.<init>(r6, r2);
        systemDefault = r5;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.ConnectionPool.<clinit>():void");
    }

    public ConnectionPool(int maxIdleConnections, long keepAliveDurationMs) {
        this.connections = new LinkedList();
        this.executor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
        this.connectionsCleanupRunnable = new Runnable() {
            public void run() {
                ConnectionPool.this.runCleanupUntilPoolIsEmpty();
            }
        };
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDurationNs = (keepAliveDurationMs * 1000) * 1000;
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    public synchronized int getConnectionCount() {
        return this.connections.size();
    }

    @Deprecated
    public synchronized int getSpdyConnectionCount() {
        return getMultiplexedConnectionCount();
    }

    public synchronized int getMultiplexedConnectionCount() {
        int total;
        total = 0;
        Iterator it = this.connections.iterator();
        while (it.hasNext()) {
            if (((Connection) it.next()).isSpdy()) {
                total++;
            }
        }
        return total;
    }

    public synchronized int getHttpConnectionCount() {
        return this.connections.size() - getMultiplexedConnectionCount();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.Connection get(com.squareup.okhttp.Address r11) {
        /*
        r10 = this;
        r5 = 0;
        monitor-enter(r10);
        r2 = 0;
        r4 = r10.connections;	 Catch:{ all -> 0x0082 }
        r6 = r10.connections;	 Catch:{ all -> 0x0082 }
        r6 = r6.size();	 Catch:{ all -> 0x0082 }
        r3 = r4.listIterator(r6);	 Catch:{ all -> 0x0082 }
    L_0x000f:
        r4 = r3.hasPrevious();	 Catch:{ all -> 0x0082 }
        if (r4 != 0) goto L_0x0019;
    L_0x0015:
        if (r2 != 0) goto L_0x0085;
    L_0x0017:
        monitor-exit(r10);
        return r2;
    L_0x0019:
        r0 = r3.previous();	 Catch:{ all -> 0x0082 }
        r0 = (com.squareup.okhttp.Connection) r0;	 Catch:{ all -> 0x0082 }
        r4 = r0.getRoute();	 Catch:{ all -> 0x0082 }
        r4 = r4.getAddress();	 Catch:{ all -> 0x0082 }
        r4 = r4.equals(r11);	 Catch:{ all -> 0x0082 }
        if (r4 == 0) goto L_0x000f;
    L_0x002d:
        r4 = r0.isAlive();	 Catch:{ all -> 0x0082 }
        if (r4 == 0) goto L_0x000f;
    L_0x0033:
        r6 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0082 }
        r8 = r0.getIdleStartTimeNs();	 Catch:{ all -> 0x0082 }
        r6 = r6 - r8;
        r8 = r10.keepAliveDurationNs;	 Catch:{ all -> 0x0082 }
        r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r4 >= 0) goto L_0x0050;
    L_0x0042:
        r4 = 1;
    L_0x0043:
        if (r4 == 0) goto L_0x000f;
    L_0x0045:
        r3.remove();	 Catch:{ all -> 0x0082 }
        r4 = r0.isSpdy();	 Catch:{ all -> 0x0082 }
        if (r4 == 0) goto L_0x0052;
    L_0x004e:
        r2 = r0;
        goto L_0x0015;
    L_0x0050:
        r4 = r5;
        goto L_0x0043;
    L_0x0052:
        r4 = com.squareup.okhttp.internal.Platform.get();	 Catch:{ SocketException -> 0x005e }
        r6 = r0.getSocket();	 Catch:{ SocketException -> 0x005e }
        r4.tagSocket(r6);	 Catch:{ SocketException -> 0x005e }
        goto L_0x004e;
    L_0x005e:
        r1 = move-exception;
        r4 = r0.getSocket();	 Catch:{ all -> 0x0082 }
        com.squareup.okhttp.internal.Util.closeQuietly(r4);	 Catch:{ all -> 0x0082 }
        r4 = com.squareup.okhttp.internal.Platform.get();	 Catch:{ all -> 0x0082 }
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0082 }
        r6.<init>();	 Catch:{ all -> 0x0082 }
        r7 = "Unable to tagSocket(): ";
        r6 = r6.append(r7);	 Catch:{ all -> 0x0082 }
        r6 = r6.append(r1);	 Catch:{ all -> 0x0082 }
        r6 = r6.toString();	 Catch:{ all -> 0x0082 }
        r4.logW(r6);	 Catch:{ all -> 0x0082 }
        goto L_0x000f;
    L_0x0082:
        r4 = move-exception;
        monitor-exit(r10);
        throw r4;
    L_0x0085:
        r4 = r2.isSpdy();	 Catch:{ all -> 0x0082 }
        if (r4 == 0) goto L_0x0017;
    L_0x008b:
        r4 = r10.connections;	 Catch:{ all -> 0x0082 }
        r4.addFirst(r2);	 Catch:{ all -> 0x0082 }
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.ConnectionPool.get(com.squareup.okhttp.Address):com.squareup.okhttp.Connection");
    }

    void recycle(Connection connection) {
        if (!connection.isSpdy() && connection.clearOwner()) {
            if (connection.isAlive()) {
                try {
                    Platform.get().untagSocket(connection.getSocket());
                    synchronized (this) {
                        addConnection(connection);
                        connection.incrementRecycleCount();
                        connection.resetIdleStartTime();
                    }
                    return;
                } catch (SocketException e) {
                    Platform.get().logW("Unable to untagSocket(): " + e);
                    Util.closeQuietly(connection.getSocket());
                    return;
                }
            }
            Util.closeQuietly(connection.getSocket());
        }
    }

    private void addConnection(Connection connection) {
        boolean empty = this.connections.isEmpty();
        this.connections.addFirst(connection);
        if (empty) {
            this.executor.execute(this.connectionsCleanupRunnable);
        } else {
            notifyAll();
        }
    }

    void share(Connection connection) {
        if (!connection.isSpdy()) {
            throw new IllegalArgumentException();
        } else if (connection.isAlive()) {
            synchronized (this) {
                addConnection(connection);
            }
        }
    }

    public void evictAll() {
        List<Connection> toEvict;
        synchronized (this) {
            toEvict = new ArrayList(this.connections);
            this.connections.clear();
            notifyAll();
        }
        int size = toEvict.size();
        for (int i = 0; i < size; i++) {
            Util.closeQuietly(((Connection) toEvict.get(i)).getSocket());
        }
    }

    private void runCleanupUntilPoolIsEmpty() {
        do {
        } while (performCleanup());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean performCleanup() {
        /*
        r24 = this;
        monitor-enter(r24);
        r0 = r24;
        r0 = r0.connections;	 Catch:{ all -> 0x009f }
        r20 = r0;
        r20 = r20.isEmpty();	 Catch:{ all -> 0x009f }
        if (r20 != 0) goto L_0x0060;
    L_0x000d:
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x009f }
        r3.<init>();	 Catch:{ all -> 0x009f }
        r7 = 0;
        r16 = java.lang.System.nanoTime();	 Catch:{ all -> 0x009f }
        r0 = r24;
        r14 = r0.keepAliveDurationNs;	 Catch:{ all -> 0x009f }
        r0 = r24;
        r0 = r0.connections;	 Catch:{ all -> 0x009f }
        r20 = r0;
        r0 = r24;
        r0 = r0.connections;	 Catch:{ all -> 0x009f }
        r21 = r0;
        r21 = r21.size();	 Catch:{ all -> 0x009f }
        r6 = r20.listIterator(r21);	 Catch:{ all -> 0x009f }
    L_0x002f:
        r20 = r6.hasPrevious();	 Catch:{ all -> 0x009f }
        if (r20 != 0) goto L_0x0064;
    L_0x0035:
        r0 = r24;
        r0 = r0.connections;	 Catch:{ all -> 0x009f }
        r20 = r0;
        r0 = r24;
        r0 = r0.connections;	 Catch:{ all -> 0x009f }
        r21 = r0;
        r21 = r21.size();	 Catch:{ all -> 0x009f }
        r6 = r20.listIterator(r21);	 Catch:{ all -> 0x009f }
    L_0x0049:
        r20 = r6.hasPrevious();	 Catch:{ all -> 0x009f }
        if (r20 != 0) goto L_0x00a2;
    L_0x004f:
        r20 = r3.isEmpty();	 Catch:{ all -> 0x009f }
        if (r20 != 0) goto L_0x00c1;
    L_0x0055:
        monitor-exit(r24);	 Catch:{ all -> 0x009f }
        r5 = 0;
        r9 = r3.size();
    L_0x005b:
        if (r5 < r9) goto L_0x00e0;
    L_0x005d:
        r20 = 1;
        return r20;
    L_0x0060:
        monitor-exit(r24);	 Catch:{ all -> 0x009f }
        r20 = 0;
        return r20;
    L_0x0064:
        r2 = r6.previous();	 Catch:{ all -> 0x009f }
        r2 = (com.squareup.okhttp.Connection) r2;	 Catch:{ all -> 0x009f }
        r20 = r2.getIdleStartTimeNs();	 Catch:{ all -> 0x009f }
        r0 = r24;
        r0 = r0.keepAliveDurationNs;	 Catch:{ all -> 0x009f }
        r22 = r0;
        r20 = r20 + r22;
        r12 = r20 - r16;
        r20 = 0;
        r20 = (r12 > r20 ? 1 : (r12 == r20 ? 0 : -1));
        if (r20 > 0) goto L_0x0095;
    L_0x007e:
        r20 = 1;
    L_0x0080:
        if (r20 != 0) goto L_0x0098;
    L_0x0082:
        r20 = r2.isAlive();	 Catch:{ all -> 0x009f }
        if (r20 == 0) goto L_0x0098;
    L_0x0088:
        r20 = r2.isIdle();	 Catch:{ all -> 0x009f }
        if (r20 == 0) goto L_0x002f;
    L_0x008e:
        r7 = r7 + 1;
        r14 = java.lang.Math.min(r14, r12);	 Catch:{ all -> 0x009f }
        goto L_0x002f;
    L_0x0095:
        r20 = 0;
        goto L_0x0080;
    L_0x0098:
        r6.remove();	 Catch:{ all -> 0x009f }
        r3.add(r2);	 Catch:{ all -> 0x009f }
        goto L_0x002f;
    L_0x009f:
        r20 = move-exception;
        monitor-exit(r24);	 Catch:{ all -> 0x009f }
        throw r20;
    L_0x00a2:
        r0 = r24;
        r0 = r0.maxIdleConnections;	 Catch:{ all -> 0x009f }
        r20 = r0;
        r0 = r20;
        if (r7 <= r0) goto L_0x004f;
    L_0x00ac:
        r2 = r6.previous();	 Catch:{ all -> 0x009f }
        r2 = (com.squareup.okhttp.Connection) r2;	 Catch:{ all -> 0x009f }
        r20 = r2.isIdle();	 Catch:{ all -> 0x009f }
        if (r20 == 0) goto L_0x0049;
    L_0x00b8:
        r3.add(r2);	 Catch:{ all -> 0x009f }
        r6.remove();	 Catch:{ all -> 0x009f }
        r7 = r7 + -1;
        goto L_0x0049;
    L_0x00c1:
        r20 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r10 = r14 / r20;
        r20 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r20 = r20 * r10;
        r18 = r14 - r20;
        r0 = r18;
        r0 = (int) r0;	 Catch:{ InterruptedException -> 0x00dd }
        r20 = r0;
        r0 = r24;
        r1 = r20;
        r0.wait(r10, r1);	 Catch:{ InterruptedException -> 0x00dd }
        monitor-exit(r24);	 Catch:{ all -> 0x009f }
        r20 = 1;
        return r20;
    L_0x00dd:
        r8 = move-exception;
        goto L_0x0055;
    L_0x00e0:
        r4 = r3.get(r5);
        r4 = (com.squareup.okhttp.Connection) r4;
        r20 = r4.getSocket();
        com.squareup.okhttp.internal.Util.closeQuietly(r20);
        r5 = r5 + 1;
        goto L_0x005b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.ConnectionPool.performCleanup():boolean");
    }

    void replaceCleanupExecutorForTests(Executor cleanupExecutor) {
        this.executor = cleanupExecutor;
    }

    synchronized List<Connection> getConnections() {
        return new ArrayList(this.connections);
    }
}
