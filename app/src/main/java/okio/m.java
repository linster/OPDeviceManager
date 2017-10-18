package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.Inflater;

public final class m implements v {
    private boolean closed;
    private final a uK;
    private final Inflater uL;
    private int uM;

    m(a aVar, Inflater inflater) {
        if (aVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (inflater != null) {
            this.uK = aVar;
            this.uL = inflater;
        } else {
            throw new IllegalArgumentException("inflater == null");
        }
    }

    public m(v vVar, Inflater inflater) {
        this(j.AE(vVar), inflater);
    }

    private void AZ() {
        if (this.uM != 0) {
            int remaining = this.uM - this.uL.getRemaining();
            this.uM -= remaining;
            this.uK.skip((long) remaining);
        }
    }

    public boolean AY() {
        if (!this.uL.needsInput()) {
            return false;
        }
        AZ();
        if (this.uL.getRemaining() != 0) {
            throw new IllegalStateException("?");
        } else if (this.uK.zL()) {
            return true;
        } else {
            p pVar = this.uK.zK().uI;
            this.uM = pVar.limit - pVar.pos;
            this.uL.setInput(pVar.data, pVar.pos, this.uM);
            return false;
        }
    }

    public void close() {
        if (!this.closed) {
            this.uL.end();
            this.closed = true;
            this.uK.close();
        }
    }

    public long read(k kVar, long j) {
        Object obj = 1;
        if (j < 0) {
            obj = null;
        }
        if (obj == null) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.closed) {
            throw new IllegalStateException("closed");
        } else if (j == 0) {
            return 0;
        } else {
            boolean AY;
            do {
                AY = AY();
                try {
                    p AW = kVar.AW(1);
                    int inflate = this.uL.inflate(AW.data, AW.limit, 2048 - AW.limit);
                    if (inflate <= 0) {
                        if (!this.uL.finished()) {
                            if (this.uL.needsDictionary()) {
                            }
                        }
                        AZ();
                        if (AW.pos == AW.limit) {
                            kVar.uI = AW.Bb();
                            e.Aq(AW);
                        }
                        return -1;
                    }
                    AW.limit += inflate;
                    kVar.size += (long) inflate;
                    return (long) inflate;
                } catch (Throwable e) {
                    throw new IOException(e);
                }
            } while (!AY);
            throw new EOFException("source exhausted prematurely");
        }
    }

    public g timeout() {
        return this.uK.timeout();
    }
}
