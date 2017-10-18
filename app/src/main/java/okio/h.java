package okio;

import java.io.InputStream;

class h extends InputStream {
    final /* synthetic */ k uG;

    h(k kVar) {
        this.uG = kVar;
    }

    public int available() {
        return (int) Math.min(this.uG.size, 2147483647L);
    }

    public void close() {
    }

    public int read() {
        return ((this.uG.size > 0 ? 1 : (this.uG.size == 0 ? 0 : -1)) <= 0 ? 1 : null) == null ? this.uG.readByte() & 255 : -1;
    }

    public int read(byte[] bArr, int i, int i2) {
        return this.uG.read(bArr, i, i2);
    }

    public String toString() {
        return this.uG + ".inputStream()";
    }
}
