package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Dispatcher {
    private final Deque executedCalls = new ArrayDeque();
    private ExecutorService executorService;
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private final Deque readyCalls = new ArrayDeque();
    private final Deque runningCalls = new ArrayDeque();

    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private void promoteCalls() {
        if (this.runningCalls.size() < this.maxRequests && !this.readyCalls.isEmpty()) {
            Iterator it = this.readyCalls.iterator();
            while (it.hasNext()) {
                AsyncCall asyncCall = (AsyncCall) it.next();
                if (runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
                    it.remove();
                    this.runningCalls.add(asyncCall);
                    getExecutorService().execute(asyncCall);
                }
                if (this.runningCalls.size() >= this.maxRequests) {
                    return;
                }
            }
        }
    }

    private int runningCallsForHost(AsyncCall asyncCall) {
        int i = 0;
        for (AsyncCall host : this.runningCalls) {
            i = !host.host().equals(asyncCall.host()) ? i : i + 1;
        }
        return i;
    }

    public synchronized void cancel(Object obj) {
        for (AsyncCall asyncCall : this.readyCalls) {
            if (Util.equal(obj, asyncCall.tag())) {
                asyncCall.cancel();
            }
        }
        for (AsyncCall asyncCall2 : this.runningCalls) {
            if (Util.equal(obj, asyncCall2.tag())) {
                asyncCall2.get().canceled = true;
                HttpEngine httpEngine = asyncCall2.get().engine;
                if (httpEngine != null) {
                    httpEngine.disconnect();
                }
            }
        }
        for (Call call : this.executedCalls) {
            if (Util.equal(obj, call.tag())) {
                call.cancel();
            }
        }
    }

    synchronized void enqueue(AsyncCall asyncCall) {
        if (this.runningCalls.size() < this.maxRequests) {
            if (runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
                this.runningCalls.add(asyncCall);
                getExecutorService().execute(asyncCall);
            }
        }
        this.readyCalls.add(asyncCall);
    }

    synchronized void executed(Call call) {
        this.executedCalls.add(call);
    }

    synchronized void finished(AsyncCall asyncCall) {
        if (this.runningCalls.remove(asyncCall)) {
            promoteCalls();
        } else {
            throw new AssertionError("AsyncCall wasn't running!");
        }
    }

    synchronized void finished(Call call) {
        if (!this.executedCalls.remove(call)) {
            throw new AssertionError("Call wasn't in-flight!");
        }
    }

    public synchronized ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
        }
        return this.executorService;
    }

    public synchronized int getMaxRequests() {
        return this.maxRequests;
    }

    public synchronized int getMaxRequestsPerHost() {
        return this.maxRequestsPerHost;
    }

    public synchronized void setMaxRequests(int i) {
        if (i >= 1) {
            this.maxRequests = i;
            promoteCalls();
        } else {
            throw new IllegalArgumentException("max < 1: " + i);
        }
    }

    public synchronized void setMaxRequestsPerHost(int i) {
        if (i >= 1) {
            this.maxRequestsPerHost = i;
            promoteCalls();
        } else {
            throw new IllegalArgumentException("max < 1: " + i);
        }
    }
}
