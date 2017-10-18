package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class c implements v {
    private int ut = 0;
    private final a uu;
    private final Inflater uv;
    private final m uw;
    private final CRC32 ux = new CRC32();

    public c(v vVar) {
        if (vVar != null) {
            this.uv = new Inflater(true);
            this.uu = j.AE(vVar);
            this.uw = new m(this.uu, this.uv);
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    private void Al() {
        long zX;
        this.uu.zM(10);
        byte AP = this.uu.zK().AP(3);
        if (((AP >> 1) & 1) != 1) {
            Object obj = null;
        } else {
            int i = 1;
        }
        if (obj != null) {
            An(this.uu.zK(), 0, 10);
        }
        Ao("ID1ID2", 8075, this.uu.zN());
        this.uu.skip(8);
        if (((AP >> 2) & 1) == 1) {
            this.uu.zM(2);
            if (obj != null) {
                An(this.uu.zK(), 0, 2);
            }
            short zO = this.uu.zK().zO();
            this.uu.zM((long) zO);
            if (obj != null) {
                An(this.uu.zK(), 0, (long) zO);
            }
            this.uu.skip((long) zO);
        }
        if (((AP >> 3) & 1) == 1) {
            zX = this.uu.zX((byte) 0);
            if (zX == -1) {
                throw new EOFException();
            }
            if (obj != null) {
                An(this.uu.zK(), 0, 1 + zX);
            }
            this.uu.skip(1 + zX);
        }
        if (((AP >> 4) & 1) == 1) {
            zX = this.uu.zX((byte) 0);
            if (zX == -1) {
                throw new EOFException();
            }
            if (obj != null) {
                An(this.uu.zK(), 0, 1 + zX);
            }
            this.uu.skip(1 + zX);
        }
        if (obj != null) {
            Ao("FHCRC", this.uu.zO(), (short) ((int) this.ux.getValue()));
            this.ux.reset();
        }
    }

    private void Am() {
        Ao("CRC", this.uu.zQ(), (int) this.ux.getValue());
        Ao("ISIZE", this.uu.zQ(), this.uv.getTotalOut());
    }

    private void An(k kVar, long j, long j2) {
        p pVar = kVar.uI;
        while (true) {
            if ((j < ((long) (pVar.limit - pVar.pos)) ? 1 : null) != null) {
                break;
            }
            j -= (long) (pVar.limit - pVar.pos);
            pVar = pVar.uR;
        }
        p pVar2 = pVar;
        while (true) {
            if ((j2 <= 0 ? 1 : null) == null) {
                int i = (int) (((long) pVar2.pos) + j);
                int min = (int) Math.min((long) (pVar2.limit - i), j2);
                this.ux.update(pVar2.data, i, min);
                j2 -= (long) min;
                pVar2 = pVar2.uR;
                j = 0;
            } else {
                return;
            }
        }
    }

    private void Ao(String str, int i, int i2) {
        if (i2 != i) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{str, Integer.valueOf(i2), Integer.valueOf(i)}));
        }
    }

    public void close() {
        this.uw.close();
    }

    public long read(k kVar, long j) {
        int i = 0;
        if (j >= 0) {
            i = 1;
        }
        if (i == 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (j == 0) {
            return 0;
        } else {
            if (this.ut == 0) {
                Al();
                this.ut = 1;
            }
            if (this.ut == 1) {
                long j2 = kVar.size;
                long read = this.uw.read(kVar, j);
                if (read != -1) {
                    An(kVar, j2, read);
                    return read;
                }
                this.ut = 2;
            }
            if (this.ut == 2) {
                Am();
                this.ut = 3;
                if (!this.uu.zL()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    public g timeout() {
        return this.uu.timeout();
    }
}
