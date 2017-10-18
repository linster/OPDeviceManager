package okio;

import java.io.OutputStream;

class y implements n {
    final /* synthetic */ g uY;
    final /* synthetic */ OutputStream uZ;

    y(g gVar, OutputStream outputStream) {
        this.uY = gVar;
        this.uZ = outputStream;
    }

    public void close() {
        this.uZ.close();
    }

    public void flush() {
        this.uZ.flush();
    }

    public g timeout() {
        return this.uY;
    }

    public String toString() {
        return "sink(" + this.uZ + ")";
    }

    public void write(k kVar, long j) {
        r.checkOffsetAndCount(kVar.size, 0, j);
        while (true) {
            if ((j <= 0 ? 1 : null) == null) {
                this.uY.throwIfReached();
                p pVar = kVar.uI;
                int min = (int) Math.min(j, (long) (pVar.limit - pVar.pos));
                this.uZ.write(pVar.data, pVar.pos, min);
                pVar.pos += min;
                j -= (long) min;
                kVar.size -= (long) min;
                if (pVar.pos == pVar.limit) {
                    kVar.uI = pVar.Bb();
                    e.Aq(pVar);
                }
            } else {
                return;
            }
        }
    }
}
