package okio;

import java.io.EOFException;
import java.io.InputStream;

final class q implements a {
    private boolean closed;
    public final k uT;
    public final v uU;

    public q(v vVar) {
        this(vVar, new k());
    }

    public q(v vVar, k kVar) {
        if (vVar != null) {
            this.uT = kVar;
            this.uU = vVar;
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    public long AX(byte b, long j) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        do {
            if ((j < this.uT.size ? 1 : null) != null) {
                do {
                    long AX = this.uT.AX(b, j);
                    if (AX != -1) {
                        return AX;
                    }
                    j = this.uT.size;
                } while (this.uU.read(this.uT, 2048) != -1);
                return -1;
            }
        } while (this.uU.read(this.uT, 2048) != -1);
        return -1;
    }

    public boolean Bg(long j) {
        if (!(j >= 0)) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.closed) {
            throw new IllegalStateException("closed");
        } else {
            do {
                if (this.uT.size >= j) {
                    return true;
                }
            } while (this.uU.read(this.uT, 2048) != -1);
            return false;
        }
    }

    public void close() {
        if (!this.closed) {
            this.closed = true;
            this.uU.close();
            this.uT.clear();
        }
    }

    public long read(k kVar, long j) {
        Object obj = null;
        if (kVar != null) {
            if (j >= 0) {
                obj = 1;
            }
            if (obj == null) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.uT.size == 0 && this.uU.read(this.uT, 2048) == -1) {
                return -1;
            } else {
                return this.uT.read(kVar, Math.min(j, this.uT.size));
            }
        }
        throw new IllegalArgumentException("sink == null");
    }

    public byte readByte() {
        zM(1);
        return this.uT.readByte();
    }

    public void skip(long j) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            if ((j <= 0 ? 1 : null) != null) {
                return;
            }
            if (this.uT.size == 0 && this.uU.read(this.uT, 2048) == -1) {
                throw new EOFException();
            }
            long min = Math.min(j, this.uT.size());
            this.uT.skip(min);
            j -= min;
        }
    }

    public g timeout() {
        return this.uU.timeout();
    }

    public String toString() {
        return "buffer(" + this.uU + ")";
    }

    public k zK() {
        return this.uT;
    }

    public boolean zL() {
        if (!this.closed) {
            return this.uT.zL() && this.uU.read(this.uT, 2048) == -1;
        } else {
            throw new IllegalStateException("closed");
        }
    }

    public void zM(long j) {
        if (!Bg(j)) {
            throw new EOFException();
        }
    }

    public short zN() {
        zM(2);
        return this.uT.zN();
    }

    public short zO() {
        zM(2);
        return this.uT.zO();
    }

    public int zP() {
        zM(4);
        return this.uT.zP();
    }

    public int zQ() {
        zM(4);
        return this.uT.zQ();
    }

    public long zR() {
        int i = 0;
        while (Bg((long) (i + 1))) {
            byte AP = this.uT.AP((long) i);
            if (AP < (byte) 48 || AP > (byte) 57) {
                if (i != 0 || AP != (byte) 45) {
                    break;
                }
            }
            i++;
        }
        if (i != 0) {
            return this.uT.zR();
        }
        throw new NumberFormatException("Expected leading [0-9] or '-' character but was 0x" + Integer.toHexString(this.uT.AP(0)));
    }

    public long zS() {
        int i = 0;
        while (Bg((long) (i + 1))) {
            byte AP = this.uT.AP((long) i);
            if (AP < (byte) 48 || AP > (byte) 57) {
                if (AP < (byte) 97 || AP > (byte) 102) {
                    if (AP < (byte) 65 || AP > (byte) 70) {
                        break;
                    }
                }
            }
            i++;
        }
        if (i != 0) {
            return this.uT.zS();
        }
        throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(this.uT.AP(0)));
    }

    public ByteString zT(long j) {
        zM(j);
        return this.uT.zT(j);
    }

    public byte[] zU() {
        this.uT.Ab(this.uU);
        return this.uT.zU();
    }

    public byte[] zV(long j) {
        zM(j);
        return this.uT.zV(j);
    }

    public String zW() {
        long zX = zX((byte) 10);
        if (zX != -1) {
            return this.uT.AT(zX);
        }
        k kVar = new k();
        this.uT.AN(kVar, 0, Math.min(32, this.uT.size()));
        throw new EOFException("\\n not found: size=" + this.uT.size() + " content=" + kVar.readByteString().Ay() + "...");
    }

    public long zX(byte b) {
        return AX(b, 0);
    }

    public InputStream zY() {
        return new l(this);
    }
}
