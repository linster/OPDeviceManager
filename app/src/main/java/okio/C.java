package okio;

import java.io.IOException;

class C implements v {
    final /* synthetic */ v ve;
    final /* synthetic */ i vf;

    C(i iVar, v vVar) {
        this.vf = iVar;
        this.ve = vVar;
    }

    public void close() {
        try {
            this.ve.close();
            this.vf.exit(true);
        } catch (IOException e) {
            throw this.vf.exit(e);
        } catch (Throwable th) {
            this.vf.exit(false);
        }
    }

    public long read(k kVar, long j) {
        this.vf.enter();
        try {
            long read = this.ve.read(kVar, j);
            this.vf.exit(true);
            return read;
        } catch (IOException e) {
            throw this.vf.exit(e);
        } catch (Throwable th) {
            this.vf.exit(false);
        }
    }

    public g timeout() {
        return this.vf;
    }

    public String toString() {
        return "AsyncTimeout.source(" + this.ve + ")";
    }
}
