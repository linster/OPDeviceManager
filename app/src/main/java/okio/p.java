package okio;

final class p {
    final byte[] data;
    int limit;
    int pos;
    boolean uP;
    boolean uQ;
    p uR;
    p uS;

    p() {
        this.data = new byte[2048];
        this.uQ = true;
        this.uP = false;
    }

    p(p pVar) {
        this(pVar.data, pVar.pos, pVar.limit);
        pVar.uP = true;
    }

    p(byte[] bArr, int i, int i2) {
        this.data = bArr;
        this.pos = i;
        this.limit = i2;
        this.uQ = false;
        this.uP = true;
    }

    public p Bb() {
        p pVar = this.uR == this ? null : this.uR;
        this.uS.uR = this.uR;
        this.uR.uS = this.uS;
        this.uR = null;
        this.uS = null;
        return pVar;
    }

    public p Bc(p pVar) {
        pVar.uS = this;
        pVar.uR = this.uR;
        this.uR.uS = pVar;
        this.uR = pVar;
        return pVar;
    }

    public p Bd(int i) {
        if (i > 0 && i <= this.limit - this.pos) {
            p pVar = new p(this);
            pVar.limit = pVar.pos + i;
            this.pos += i;
            this.uS.Bc(pVar);
            return pVar;
        }
        throw new IllegalArgumentException();
    }

    public void Be() {
        int i = 0;
        if (this.uS == this) {
            throw new IllegalStateException();
        } else if (this.uS.uQ) {
            int i2 = this.limit - this.pos;
            int i3 = 2048 - this.uS.limit;
            if (!this.uS.uP) {
                i = this.uS.pos;
            }
            if (i2 <= i + i3) {
                Bf(this.uS, i2);
                Bb();
                e.Aq(this);
            }
        }
    }

    public void Bf(p pVar, int i) {
        if (pVar.uQ) {
            if (pVar.limit + i > 2048) {
                if (pVar.uP) {
                    throw new IllegalArgumentException();
                } else if ((pVar.limit + i) - pVar.pos <= 2048) {
                    System.arraycopy(pVar.data, pVar.pos, pVar.data, 0, pVar.limit - pVar.pos);
                    pVar.limit -= pVar.pos;
                    pVar.pos = 0;
                } else {
                    throw new IllegalArgumentException();
                }
            }
            System.arraycopy(this.data, this.pos, pVar.data, pVar.limit, i);
            pVar.limit += i;
            this.pos += i;
            return;
        }
        throw new IllegalArgumentException();
    }
}
