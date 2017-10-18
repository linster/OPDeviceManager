package okio;

import java.io.OutputStream;

final class o implements b {
    private boolean closed;
    public final k uN;
    public final n uO;

    public o(n nVar) {
        this(nVar, new k());
    }

    public o(n nVar, k kVar) {
        if (nVar != null) {
            this.uN = kVar;
            this.uO = nVar;
            return;
        }
        throw new IllegalArgumentException("sink == null");
    }

    public b Aa(byte[] bArr) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.Aa(bArr);
        return Ai();
    }

    public long Ab(v vVar) {
        if (vVar != null) {
            long j = 0;
            while (true) {
                long read = vVar.read(this.uN, 2048);
                if (read == -1) {
                    return j;
                }
                j += read;
                Ai();
            }
        } else {
            throw new IllegalArgumentException("source == null");
        }
    }

    public b Ac(String str) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.Ac(str);
        return Ai();
    }

    public b Ad(int i) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.Ad(i);
        return Ai();
    }

    public b Ae(int i) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.Ae(i);
        return Ai();
    }

    public b Af(int i) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.Af(i);
        return Ai();
    }

    public b Ag(long j) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.Ag(j);
        return Ai();
    }

    public b Ah(long j) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.Ah(j);
        return Ai();
    }

    public b Ai() {
        Object obj = null;
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        long AO = this.uN.AO();
        if (AO <= 0) {
            obj = 1;
        }
        if (obj == null) {
            this.uO.write(this.uN, AO);
        }
        return this;
    }

    public b Aj() {
        Object obj = null;
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        long size = this.uN.size();
        if (size <= 0) {
            obj = 1;
        }
        if (obj == null) {
            this.uO.write(this.uN, size);
        }
        return this;
    }

    public OutputStream Ak() {
        return new B(this);
    }

    public void close() {
        Throwable th;
        boolean z = false;
        Throwable th2 = null;
        if (!this.closed) {
            try {
                if (this.uN.size <= 0) {
                    z = true;
                }
                if (!z) {
                    this.uO.write(this.uN, this.uN.size);
                }
            } catch (Throwable th3) {
                th2 = th3;
            }
            try {
                this.uO.close();
                th3 = th2;
            } catch (Throwable th4) {
                th3 = th4;
                if (th2 != null) {
                    th3 = th2;
                }
            }
            this.closed = true;
            if (th3 != null) {
                r.Bk(th3);
            }
        }
    }

    public void flush() {
        Object obj = null;
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.uN.size <= 0) {
            obj = 1;
        }
        if (obj == null) {
            this.uO.write(this.uN, this.uN.size);
        }
        this.uO.flush();
    }

    public g timeout() {
        return this.uO.timeout();
    }

    public String toString() {
        return "buffer(" + this.uO + ")";
    }

    public b write(byte[] bArr, int i, int i2) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.write(bArr, i, i2);
        return Ai();
    }

    public void write(k kVar, long j) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.write(kVar, j);
        Ai();
    }

    public k zK() {
        return this.uN;
    }

    public b zZ(ByteString byteString) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.uN.zZ(byteString);
        return Ai();
    }
}
