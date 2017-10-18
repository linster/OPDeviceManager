package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;
import java.net.ProtocolException;
import okio.g;
import okio.k;
import okio.n;

public final class RetryableSink implements n {
    private boolean closed;
    private final k content;
    private final int limit;

    public RetryableSink() {
        this(-1);
    }

    public RetryableSink(int i) {
        this.content = new k();
        this.limit = i;
    }

    public void close() {
        boolean z = true;
        if (!this.closed) {
            this.closed = true;
            if (this.content.size() < ((long) this.limit)) {
                z = false;
            }
            if (!z) {
                throw new ProtocolException("content-length promised " + this.limit + " bytes, but received " + this.content.size());
            }
        }
    }

    public long contentLength() {
        return this.content.size();
    }

    public void flush() {
    }

    public g timeout() {
        return g.NONE;
    }

    public void write(k kVar, long j) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        Util.checkOffsetAndCount(kVar.size(), 0, j);
        if (this.limit != -1) {
            if ((this.content.size() <= ((long) this.limit) - j ? 1 : null) == null) {
                throw new ProtocolException("exceeded content-length limit of " + this.limit + " bytes");
            }
        }
        this.content.write(kVar, j);
    }

    public void writeToSocket(n nVar) {
        k kVar = new k();
        this.content.AN(kVar, 0, this.content.size());
        nVar.write(kVar, kVar.size());
    }
}
