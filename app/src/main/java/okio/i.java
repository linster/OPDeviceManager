package okio;

import java.io.IOException;
import java.io.InterruptedIOException;

public class i extends g {
    private static i head;
    private boolean inQueue;
    private i next;
    private long timeoutAt;

    private static synchronized i awaitTimeout() {
        synchronized (i.class) {
            i iVar = head.next;
            if (iVar != null) {
                long remainingNanos = iVar.remainingNanos(System.nanoTime());
                if ((remainingNanos <= 0 ? 1 : null) == null) {
                    long j = remainingNanos / 1000000;
                    i.class.wait(j, (int) (remainingNanos - (j * 1000000)));
                    return null;
                }
                head.next = iVar.next;
                iVar.next = null;
                return iVar;
            }
            i.class.wait();
            return null;
        }
    }

    private static synchronized boolean cancelScheduledTimeout(i iVar) {
        synchronized (i.class) {
            i iVar2 = head;
            while (iVar2 != null) {
                if (iVar2.next != iVar) {
                    iVar2 = iVar2.next;
                } else {
                    iVar2.next = iVar.next;
                    iVar.next = null;
                    return false;
                }
            }
            return true;
        }
    }

    private long remainingNanos(long j) {
        return this.timeoutAt - j;
    }

    private static synchronized void scheduleTimeout(i iVar, long j, boolean z) {
        synchronized (i.class) {
            i iVar2;
            if (head == null) {
                head = new i();
                new t().start();
            }
            long nanoTime = System.nanoTime();
            if (j != 0 && z) {
                iVar.timeoutAt = Math.min(j, iVar.deadlineNanoTime() - nanoTime) + nanoTime;
            } else if (j != 0) {
                iVar.timeoutAt = nanoTime + j;
            } else if (z) {
                iVar.timeoutAt = iVar.deadlineNanoTime();
            } else {
                throw new AssertionError();
            }
            long remainingNanos = iVar.remainingNanos(nanoTime);
            i iVar3 = head;
            while (true) {
                iVar2 = iVar3;
                if (iVar2.next == null) {
                    break;
                }
                if ((remainingNanos >= iVar2.next.remainingNanos(nanoTime) ? 1 : null) == null) {
                    break;
                }
                iVar3 = iVar2.next;
            }
            iVar.next = iVar2.next;
            iVar2.next = iVar;
            if (iVar2 == head) {
                i.class.notify();
            }
        }
    }

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long timeoutNanos = timeoutNanos();
        boolean hasDeadline = hasDeadline();
        if (timeoutNanos != 0 || hasDeadline) {
            this.inQueue = true;
            scheduleTimeout(this, timeoutNanos, hasDeadline);
        }
    }

    final IOException exit(IOException iOException) {
        if (!exit()) {
            return iOException;
        }
        IOException interruptedIOException = new InterruptedIOException("timeout");
        interruptedIOException.initCause(iOException);
        return interruptedIOException;
    }

    final void exit(boolean z) {
        if (exit() && z) {
            throw new InterruptedIOException("timeout");
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return cancelScheduledTimeout(this);
    }

    public final n sink(n nVar) {
        return new d(this, nVar);
    }

    public final v source(v vVar) {
        return new C(this, vVar);
    }

    protected void timedOut() {
    }
}
