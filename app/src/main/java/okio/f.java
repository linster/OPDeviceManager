package okio;

public abstract class f implements n {
    private final n delegate;

    public f(n nVar) {
        if (nVar != null) {
            this.delegate = nVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public void close() {
        this.delegate.close();
    }

    public final n delegate() {
        return this.delegate;
    }

    public void flush() {
        this.delegate.flush();
    }

    public g timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }

    public void write(k kVar, long j) {
        this.delegate.write(kVar, j);
    }
}
