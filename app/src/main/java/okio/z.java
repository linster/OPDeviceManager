package okio;

import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public final class z implements n {
    private boolean closed;
    private final b va;
    private final Deflater vb;

    z(b bVar, Deflater deflater) {
        if (bVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (deflater != null) {
            this.va = bVar;
            this.vb = deflater;
        } else {
            throw new IllegalArgumentException("inflater == null");
        }
    }

    public z(n nVar, Deflater deflater) {
        this(j.AF(nVar), deflater);
    }

    @IgnoreJRERequirement
    private void Bn(boolean z) {
        k zK = this.va.zK();
        while (true) {
            p AW = zK.AW(1);
            int deflate = !z ? this.vb.deflate(AW.data, AW.limit, 2048 - AW.limit) : this.vb.deflate(AW.data, AW.limit, 2048 - AW.limit, 2);
            if (deflate > 0) {
                AW.limit += deflate;
                zK.size += (long) deflate;
                this.va.Ai();
            } else if (this.vb.needsInput()) {
                break;
            }
        }
        if (AW.pos == AW.limit) {
            zK.uI = AW.Bb();
            e.Aq(AW);
        }
    }

    void Bo() {
        this.vb.finish();
        Bn(false);
    }

    public void close() {
        Throwable th;
        Throwable th2 = null;
        if (!this.closed) {
            try {
                Bo();
            } catch (Throwable th3) {
                th2 = th3;
            }
            try {
                this.vb.end();
            } catch (Throwable th4) {
                if (th2 == null) {
                    th2 = th4;
                }
            }
            try {
                this.va.close();
                th4 = th2;
            } catch (Throwable th5) {
                th4 = th5;
                if (th2 != null) {
                    th4 = th2;
                }
            }
            this.closed = true;
            if (th4 != null) {
                r.Bk(th4);
            }
        }
    }

    public void flush() {
        Bn(true);
        this.va.flush();
    }

    public g timeout() {
        return this.va.timeout();
    }

    public String toString() {
        return "DeflaterSink(" + this.va + ")";
    }

    public void write(k kVar, long j) {
        r.checkOffsetAndCount(kVar.size, 0, j);
        while (true) {
            if (!(j <= 0)) {
                p pVar = kVar.uI;
                int min = (int) Math.min(j, (long) (pVar.limit - pVar.pos));
                this.vb.setInput(pVar.data, pVar.pos, min);
                Bn(false);
                kVar.size -= (long) min;
                pVar.pos += min;
                if (pVar.pos == pVar.limit) {
                    kVar.uI = pVar.Bb();
                    e.Aq(pVar);
                }
                j -= (long) min;
            } else {
                return;
            }
        }
    }
}
