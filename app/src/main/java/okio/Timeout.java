package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class Timeout {
    public static final Timeout NONE;
    private long deadlineNanoTime;
    private boolean hasDeadline;
    private long timeoutNanos;

    static {
        NONE = new Timeout() {
            public Timeout timeout(long timeout, TimeUnit unit) {
                return this;
            }

            public Timeout deadlineNanoTime(long deadlineNanoTime) {
                return this;
            }

            public void throwIfReached() throws IOException {
            }
        };
    }

    public Timeout timeout(long timeout, TimeUnit unit) {
        if ((timeout >= 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("timeout < 0: " + timeout);
        } else if (unit != null) {
            this.timeoutNanos = unit.toNanos(timeout);
            return this;
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public long timeoutNanos() {
        return this.timeoutNanos;
    }

    public boolean hasDeadline() {
        return this.hasDeadline;
    }

    public long deadlineNanoTime() {
        if (this.hasDeadline) {
            return this.deadlineNanoTime;
        }
        throw new IllegalStateException("No deadline");
    }

    public Timeout deadlineNanoTime(long deadlineNanoTime) {
        this.hasDeadline = true;
        this.deadlineNanoTime = deadlineNanoTime;
        return this;
    }

    public final Timeout deadline(long duration, TimeUnit unit) {
        if ((duration > 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("duration <= 0: " + duration);
        } else if (unit != null) {
            return deadlineNanoTime(System.nanoTime() + unit.toNanos(duration));
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public Timeout clearTimeout() {
        this.timeoutNanos = 0;
        return this;
    }

    public Timeout clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    public void throwIfReached() throws IOException {
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
}
