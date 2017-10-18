package okio;

import java.io.IOException;
import java.io.OutputStream;

class B extends OutputStream {
    final /* synthetic */ o vd;

    B(o oVar) {
        this.vd = oVar;
    }

    public void close() {
        this.vd.close();
    }

    public void flush() {
        if (!this.vd.closed) {
            this.vd.flush();
        }
    }

    public String toString() {
        return this.vd + ".outputStream()";
    }

    public void write(int i) {
        if (this.vd.closed) {
            throw new IOException("closed");
        }
        this.vd.uN.Ad((byte) i);
        this.vd.Ai();
    }

    public void write(byte[] bArr, int i, int i2) {
        if (this.vd.closed) {
            throw new IOException("closed");
        }
        this.vd.uN.write(bArr, i, i2);
        this.vd.Ai();
    }
}
