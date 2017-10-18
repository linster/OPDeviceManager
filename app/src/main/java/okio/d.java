package okio;

import java.io.IOException;

class d implements n {
    final /* synthetic */ n uy;
    final /* synthetic */ i uz;

    d(i iVar, n nVar) {
        this.uz = iVar;
        this.uy = nVar;
    }

    public void close() {
        this.uz.enter();
        try {
            this.uy.close();
            this.uz.exit(true);
        } catch (IOException e) {
            throw this.uz.exit(e);
        } catch (Throwable th) {
            this.uz.exit(false);
        }
    }

    public void flush() {
        this.uz.enter();
        try {
            this.uy.flush();
            this.uz.exit(true);
        } catch (IOException e) {
            throw this.uz.exit(e);
        } catch (Throwable th) {
            this.uz.exit(false);
        }
    }

    public g timeout() {
        return this.uz;
    }

    public String toString() {
        return "AsyncTimeout.sink(" + this.uy + ")";
    }

    public void write(k kVar, long j) {
        this.uz.enter();
        try {
            this.uy.write(kVar, j);
            this.uz.exit(true);
        } catch (IOException e) {
            throw this.uz.exit(e);
        } catch (Throwable th) {
            this.uz.exit(false);
        }
    }
}
