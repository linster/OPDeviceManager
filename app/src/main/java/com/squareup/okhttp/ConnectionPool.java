package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
    private static final ConnectionPool systemDefault = null;
    private final LinkedList connections;
    private final Runnable connectionsCleanupRunnable;
    private Executor executor;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.squareup.okhttp.ConnectionPool.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:370)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:360)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:106)
	... 5 more
*/
        /*
        r4 = 0;
        r0 = "http.keepAlive";
        r2 = java.lang.System.getProperty(r0);
        r0 = "http.keepAliveDuration";
        r0 = java.lang.System.getProperty(r0);
        r1 = "http.maxConnections";
        r3 = java.lang.System.getProperty(r1);
        if (r0 != 0) goto L_0x0028;
    L_0x0018:
        r0 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
    L_0x001b:
        if (r2 != 0) goto L_0x002d;
    L_0x001d:
        if (r3 != 0) goto L_0x003b;
    L_0x001f:
        r2 = new com.squareup.okhttp.ConnectionPool;
        r3 = 5;
        r2.<init>(r3, r0);
        systemDefault = r2;
    L_0x0028:
        r0 = java.lang.Long.parseLong(r0);
        goto L_0x001b;
    L_0x002d:
        r2 = java.lang.Boolean.parseBoolean(r2);
        if (r2 != 0) goto L_0x001d;
    L_0x0033:
        r2 = new com.squareup.okhttp.ConnectionPool;
        r2.<init>(r4, r0);
        systemDefault = r2;
        goto L_0x0027;
    L_0x003b:
        r2 = new com.squareup.okhttp.ConnectionPool;
        r3 = java.lang.Integer.parseInt(r3);
        r2.<init>(r3, r0);
        systemDefault = r2;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.ConnectionPool.<clinit>():void");
    }

    public ConnectionPool(int i, long j) {
        this.connections = new LinkedList();
        this.executor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
        this.connectionsCleanupRunnable = new Runnable() {
            public void run() {
                ConnectionPool.this.runCleanupUntilPoolIsEmpty();
            }
        };
        this.maxIdleConnections = i;
        this.keepAliveDurationNs = (j * 1000) * 1000;
    }

    private void addConnection(Connection connection) {
        boolean isEmpty = this.connections.isEmpty();
        this.connections.addFirst(connection);
        if (isEmpty) {
            this.executor.execute(this.connectionsCleanupRunnable);
        } else {
            notifyAll();
        }
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    private void runCleanupUntilPoolIsEmpty() {
        do {
        } while (performCleanup());
    }

    public void evictAll() {
        List arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.connections);
            this.connections.clear();
            notifyAll();
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Util.closeQuietly(((Connection) arrayList.get(i)).getSocket());
        }
    }

    public synchronized Connection get(Address address) {
        Connection connection;
        ListIterator listIterator = this.connections.listIterator(this.connections.size());
        while (listIterator.hasPrevious()) {
            connection = (Connection) listIterator.previous();
            if (connection.getRoute().getAddress().equals(address) && connection.isAlive()) {
                if ((System.nanoTime() - connection.getIdleStartTimeNs() < this.keepAliveDurationNs ? 1 : null) != null) {
                    listIterator.remove();
                    if (connection.isSpdy()) {
                        break;
                    }
                    try {
                        Platform.get().tagSocket(connection.getSocket());
                        break;
                    } catch (SocketException e) {
                        Util.closeQuietly(connection.getSocket());
                        Platform.get().logW("Unable to tagSocket(): " + e);
                    }
                } else {
                    continue;
                }
            }
        }
        connection = null;
        if (connection != null) {
            if (connection.isSpdy()) {
                this.connections.addFirst(connection);
            }
        }
        return connection;
    }

    public synchronized int getConnectionCount() {
        return this.connections.size();
    }

    synchronized List getConnections() {
        return new ArrayList(this.connections);
    }

    public synchronized int getHttpConnectionCount() {
        return this.connections.size() - getMultiplexedConnectionCount();
    }

    public synchronized int getMultiplexedConnectionCount() {
        int i = 0;
        synchronized (this) {
            Iterator it = this.connections.iterator();
            while (it.hasNext()) {
                i = !((Connection) it.next()).isSpdy() ? i : i + 1;
            }
        }
        return i;
    }

    @Deprecated
    public synchronized int getSpdyConnectionCount() {
        return getMultiplexedConnectionCount();
    }

    boolean performCleanup() {
        int i;
        synchronized (this) {
            if (this.connections.isEmpty()) {
                return false;
            }
            List arrayList = new ArrayList();
            int i2 = 0;
            long nanoTime = System.nanoTime();
            long j = this.keepAliveDurationNs;
            ListIterator listIterator = this.connections.listIterator(this.connections.size());
            while (listIterator.hasPrevious()) {
                long j2;
                Connection connection = (Connection) listIterator.previous();
                long idleStartTimeNs = (connection.getIdleStartTimeNs() + this.keepAliveDurationNs) - nanoTime;
                long j3;
                if ((idleStartTimeNs <= 0 ? 1 : null) != null || !connection.isAlive()) {
                    listIterator.remove();
                    arrayList.add(connection);
                    j3 = j;
                    i = i2;
                    j2 = j3;
                } else if (connection.isIdle()) {
                    int i3 = i2 + 1;
                    j2 = Math.min(j, idleStartTimeNs);
                    i = i3;
                } else {
                    j3 = j;
                    i = i2;
                    j2 = j3;
                }
                i2 = i;
                j = j2;
            }
            ListIterator listIterator2 = this.connections.listIterator(this.connections.size());
            while (listIterator2.hasPrevious() && i2 > this.maxIdleConnections) {
                int i4;
                connection = (Connection) listIterator2.previous();
                if (connection.isIdle()) {
                    arrayList.add(connection);
                    listIterator2.remove();
                    i4 = i2 - 1;
                } else {
                    i4 = i2;
                }
                i2 = i4;
            }
            if (arrayList.isEmpty()) {
                try {
                    j2 = j / 1000000;
                    wait(j2, (int) (j - (1000000 * j2)));
                    return true;
                } catch (InterruptedException e) {
                    i = arrayList.size();
                    for (i2 = 0; i2 < i; i2++) {
                        Util.closeQuietly(((Connection) arrayList.get(i2)).getSocket());
                    }
                    return true;
                }
            }
        }
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

    void replaceCleanupExecutorForTests(Executor executor) {
        this.executor = executor;
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
}
