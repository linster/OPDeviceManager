package okio;

import java.io.IOException;
import java.io.InputStream;

class l extends InputStream {
    final /* synthetic */ q uJ;

    l(q qVar) {
        this.uJ = qVar;
    }

    public int available() {
        if (!this.uJ.closed) {
            return (int) Math.min(this.uJ.uT.size, 2147483647L);
        }
        throw new IOException("closed");
    }

    public void close() {
        this.uJ.close();
    }

    public int read() {
        if (!this.uJ.closed) {
            return (this.uJ.uT.size == 0 && this.uJ.uU.read(this.uJ.uT, 2048) == -1) ? -1 : this.uJ.uT.readByte() & 255;
        } else {
            throw new IOException("closed");
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        if (this.uJ.closed) {
            throw new IOException("closed");
        }
        r.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
        return (this.uJ.uT.size == 0 && this.uJ.uU.read(this.uJ.uT, 2048) == -1) ? -1 : this.uJ.uT.read(bArr, i, i2);
    }

    public String toString() {
        return this.uJ + ".inputStream()";
    }
}
