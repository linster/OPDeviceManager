package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;

import java.io.IOException;
import java.net.ProtocolException;

import okio.Buffer;
import okio.Sink;
import okio.Timeout;

public final class RetryableSink implements Sink {
    private boolean closed;
    private final Buffer content;
    private final int limit;

    public RetryableSink(int limit) {
        this.content = new Buffer();
        this.limit = limit;
    }

    public RetryableSink() {
        this(-1);
    }

    public void close() throws IOException {
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

    public void write(Buffer source, long byteCount) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        Util.checkOffsetAndCount(source.size(), 0, byteCount);
        if (this.limit != -1) {
            Object obj;
            if (this.content.size() <= ((long) this.limit) - byteCount) {
                obj = 1;
            } else {
                obj = null;
            }
            if (obj == null) {
                throw new ProtocolException("exceeded content-length limit of " + this.limit + " bytes");
            }
        }
        this.content.write(source, byteCount);
    }

    public void flush() throws IOException {
    }

    public Timeout timeout() {
        return Timeout.NONE;
    }

    public long contentLength() throws IOException {
        return this.content.size();
    }

    public void writeToSocket(Sink socketOut) throws IOException {
        Buffer buffer = new Buffer();
        this.content.copyTo(buffer, 0, this.content.size());
        socketOut.write(buffer, buffer.size());
    }
}
