package okio;

import java.io.InputStream;

class s implements v {
    final /* synthetic */ g uV;
    final /* synthetic */ InputStream uW;

    s(g gVar, InputStream inputStream) {
        this.uV = gVar;
        this.uW = inputStream;
    }

    public void close() {
        this.uW.close();
    }

    public long read(k kVar, long j) {
        if ((j >= 0 ? 1 : 0) == 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (j == 0) {
            return 0;
        } else {
            this.uV.throwIfReached();
            p AW = kVar.AW(1);
            int read = this.uW.read(AW.data, AW.limit, (int) Math.min(j, (long) (2048 - AW.limit)));
            if (read == -1) {
                return -1;
            }
            AW.limit += read;
            kVar.size += (long) read;
            return (long) read;
        }
    }

    public g timeout() {
        return this.uV;
    }

    public String toString() {
        return "source(" + this.uW + ")";
    }
}
