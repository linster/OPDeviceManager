package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class g {
    public static final g NONE = new x();
    private long deadlineNanoTime;
    private boolean hasDeadline;
    private long timeoutNanos;

    public g clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    public g clearTimeout() {
        this.timeoutNanos = 0;
        return this;
    }

    public final g deadline(long j, TimeUnit timeUnit) {
        if ((j > 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("duration <= 0: " + j);
        } else if (timeUnit != null) {
            return deadlineNanoTime(System.nanoTime() + timeUnit.toNanos(j));
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public long deadlineNanoTime() {
        if (this.hasDeadline) {
            return this.deadlineNanoTime;
        }
        throw new IllegalStateException("No deadline");
    }

    public g deadlineNanoTime(long j) {
        this.hasDeadline = true;
        this.deadlineNanoTime = j;
        return this;
    }

    public boolean hasDeadline() {
        return this.hasDeadline;
    }

    public void throwIfReached() {
        Object obj = null;
        if (Thread.interrupted()) {
            throw new InterruptedIOException();
        } else if (this.hasDeadline) {
            if (System.nanoTime() <= this.deadlineNanoTime) {
                obj = 1;
            }
            if (obj == null) {
                throw new IOException("deadline reached");
            }
        }
    }

    public g timeout(long j, TimeUnit timeUnit) {
        if ((j >= 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("timeout < 0: " + j);
        } else if (timeUnit != null) {
            this.timeoutNanos = timeUnit.toNanos(j);
            return this;
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public long timeoutNanos() {
        return this.timeoutNanos;
    }
}
