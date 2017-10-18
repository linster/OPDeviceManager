package okio;

public abstract class u implements v {
    private final v delegate;

    public u(v vVar) {
        if (vVar != null) {
            this.delegate = vVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public void close() {
        this.delegate.close();
    }

    public final v delegate() {
        return this.delegate;
    }

    public long read(k kVar, long j) {
        return this.delegate.read(kVar, j);
    }

    public g timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}
