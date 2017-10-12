package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Dispatcher {
    private final Deque<Call> executedCalls;
    private ExecutorService executorService;
    private int maxRequests;
    private int maxRequestsPerHost;
    private final Deque<AsyncCall> readyCalls;
    private final Deque<AsyncCall> runningCalls;

    public Dispatcher(ExecutorService executorService) {
        this.maxRequests = 64;
        this.maxRequestsPerHost = 5;
        this.readyCalls = new ArrayDeque();
        this.runningCalls = new ArrayDeque();
        this.executedCalls = new ArrayDeque();
        this.executorService = executorService;
    }

    public Dispatcher() {
        this.maxRequests = 64;
        this.maxRequestsPerHost = 5;
        this.readyCalls = new ArrayDeque();
        this.runningCalls = new ArrayDeque();
        this.executedCalls = new ArrayDeque();
    }

    public synchronized ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
        }
        return this.executorService;
    }

    public synchronized void setMaxRequests(int maxRequests) {
        if (maxRequests >= 1) {
            this.maxRequests = maxRequests;
            promoteCalls();
        } else {
            throw new IllegalArgumentException("max < 1: " + maxRequests);
        }
    }

    public synchronized int getMaxRequests() {
        return this.maxRequests;
    }

    public synchronized void setMaxRequestsPerHost(int maxRequestsPerHost) {
        if (maxRequestsPerHost >= 1) {
            this.maxRequestsPerHost = maxRequestsPerHost;
            promoteCalls();
        } else {
            throw new IllegalArgumentException("max < 1: " + maxRequestsPerHost);
        }
    }

    public synchronized int getMaxRequestsPerHost() {
        return this.maxRequestsPerHost;
    }

    synchronized void enqueue(AsyncCall call) {
        if (this.runningCalls.size() < this.maxRequests) {
            if (runningCallsForHost(call) < this.maxRequestsPerHost) {
                this.runningCalls.add(call);
                getExecutorService().execute(call);
            }
        }
        this.readyCalls.add(call);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void cancel(java.lang.Object r7) {
        /*
        r6 = this;
        monitor-enter(r6);
        r3 = r6.readyCalls;	 Catch:{ all -> 0x003b }
        r3 = r3.iterator();	 Catch:{ all -> 0x003b }
    L_0x0007:
        r4 = r3.hasNext();	 Catch:{ all -> 0x003b }
        if (r4 != 0) goto L_0x0027;
    L_0x000d:
        r3 = r6.runningCalls;	 Catch:{ all -> 0x003b }
        r3 = r3.iterator();	 Catch:{ all -> 0x003b }
    L_0x0013:
        r4 = r3.hasNext();	 Catch:{ all -> 0x003b }
        if (r4 != 0) goto L_0x003e;
    L_0x0019:
        r3 = r6.executedCalls;	 Catch:{ all -> 0x003b }
        r3 = r3.iterator();	 Catch:{ all -> 0x003b }
    L_0x001f:
        r4 = r3.hasNext();	 Catch:{ all -> 0x003b }
        if (r4 != 0) goto L_0x0061;
    L_0x0025:
        monitor-exit(r6);
        return;
    L_0x0027:
        r0 = r3.next();	 Catch:{ all -> 0x003b }
        r0 = (com.squareup.okhttp.Call.AsyncCall) r0;	 Catch:{ all -> 0x003b }
        r4 = r0.tag();	 Catch:{ all -> 0x003b }
        r4 = com.squareup.okhttp.internal.Util.equal(r7, r4);	 Catch:{ all -> 0x003b }
        if (r4 == 0) goto L_0x0007;
    L_0x0037:
        r0.cancel();	 Catch:{ all -> 0x003b }
        goto L_0x0007;
    L_0x003b:
        r3 = move-exception;
        monitor-exit(r6);
        throw r3;
    L_0x003e:
        r0 = r3.next();	 Catch:{ all -> 0x003b }
        r0 = (com.squareup.okhttp.Call.AsyncCall) r0;	 Catch:{ all -> 0x003b }
        r4 = r0.tag();	 Catch:{ all -> 0x003b }
        r4 = com.squareup.okhttp.internal.Util.equal(r7, r4);	 Catch:{ all -> 0x003b }
        if (r4 == 0) goto L_0x0013;
    L_0x004e:
        r4 = r0.get();	 Catch:{ all -> 0x003b }
        r5 = 1;
        r4.canceled = r5;	 Catch:{ all -> 0x003b }
        r4 = r0.get();	 Catch:{ all -> 0x003b }
        r2 = r4.engine;	 Catch:{ all -> 0x003b }
        if (r2 == 0) goto L_0x0013;
    L_0x005d:
        r2.disconnect();	 Catch:{ all -> 0x003b }
        goto L_0x0013;
    L_0x0061:
        r1 = r3.next();	 Catch:{ all -> 0x003b }
        r1 = (com.squareup.okhttp.Call) r1;	 Catch:{ all -> 0x003b }
        r4 = r1.tag();	 Catch:{ all -> 0x003b }
        r4 = com.squareup.okhttp.internal.Util.equal(r7, r4);	 Catch:{ all -> 0x003b }
        if (r4 == 0) goto L_0x001f;
    L_0x0071:
        r1.cancel();	 Catch:{ all -> 0x003b }
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.Dispatcher.cancel(java.lang.Object):void");
    }

    synchronized void finished(AsyncCall call) {
        if (this.runningCalls.remove(call)) {
            promoteCalls();
        } else {
            throw new AssertionError("AsyncCall wasn't running!");
        }
    }

    private void promoteCalls() {
        if (this.runningCalls.size() < this.maxRequests && !this.readyCalls.isEmpty()) {
            Iterator<AsyncCall> i = this.readyCalls.iterator();
            while (i.hasNext()) {
                AsyncCall call = (AsyncCall) i.next();
                if (runningCallsForHost(call) < this.maxRequestsPerHost) {
                    i.remove();
                    this.runningCalls.add(call);
                    getExecutorService().execute(call);
                }
                if (this.runningCalls.size() >= this.maxRequests) {
                    return;
                }
            }
        }
    }

    private int runningCallsForHost(AsyncCall call) {
        int result = 0;
        for (AsyncCall c : this.runningCalls) {
            if (c.host().equals(call.host())) {
                result++;
            }
        }
        return result;
    }

    synchronized void executed(Call call) {
        this.executedCalls.add(call);
    }

    synchronized void finished(Call call) {
        if (!this.executedCalls.remove(call)) {
            throw new AssertionError("Call wasn't in-flight!");
        }
    }
}
