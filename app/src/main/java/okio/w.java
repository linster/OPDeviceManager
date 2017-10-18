package okio;

import java.io.OutputStream;

class w extends OutputStream {
    final /* synthetic */ k uX;

    w(k kVar) {
        this.uX = kVar;
    }

    public void close() {
    }

    public void flush() {
    }

    public String toString() {
        return this + ".outputStream()";
    }

    public void write(int i) {
        this.uX.Ad((byte) i);
    }

    public void write(byte[] bArr, int i, int i2) {
        this.uX.write(bArr, i, i2);
    }
}
