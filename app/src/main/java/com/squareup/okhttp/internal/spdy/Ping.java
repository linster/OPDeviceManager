package com.squareup.okhttp.internal.spdy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Ping {
    private final CountDownLatch latch = new CountDownLatch(1);
    private long received = -1;
    private long sent = -1;

    Ping() {
    }

    void cancel() {
        if (this.received != -1 || this.sent == -1) {
            throw new IllegalStateException();
        }
        this.received = this.sent - 1;
        this.latch.countDown();
    }

    void receive() {
        if (this.received != -1 || this.sent == -1) {
            throw new IllegalStateException();
        }
        this.received = System.nanoTime();
        this.latch.countDown();
    }

    public long roundTripTime() {
        this.latch.await();
        return this.received - this.sent;
    }

    public long roundTripTime(long j, TimeUnit timeUnit) {
        return !this.latch.await(j, timeUnit) ? -2 : this.received - this.sent;
    }

    void send() {
        if (this.sent != -1) {
            throw new IllegalStateException();
        }
        this.sent = System.nanoTime();
    }
}
