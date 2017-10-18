package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okio.a;
import okio.b;
import okio.g;
import okio.j;
import okio.k;
import okio.n;
import okio.v;

public final class HttpConnection {
    private static final int ON_IDLE_CLOSE = 2;
    private static final int ON_IDLE_HOLD = 0;
    private static final int ON_IDLE_POOL = 1;
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private final Connection connection;
    private int onIdle = 0;
    private final ConnectionPool pool;
    private final b sink;
    private final Socket socket;
    private final a source;
    private int state = 0;

    abstract class AbstractSource implements v {
        protected boolean closed;

        private AbstractSource() {
        }

        protected final void endOfInput(boolean z) {
            if (HttpConnection.this.state == HttpConnection.STATE_READING_RESPONSE_BODY) {
                HttpConnection.this.state = 0;
                if (z && HttpConnection.this.onIdle == 1) {
                    HttpConnection.this.onIdle = 0;
                    Internal.instance.recycle(HttpConnection.this.pool, HttpConnection.this.connection);
                    return;
                } else if (HttpConnection.this.onIdle == 2) {
                    HttpConnection.this.state = HttpConnection.STATE_CLOSED;
                    HttpConnection.this.connection.getSocket().close();
                    return;
                } else {
                    return;
                }
            }
            throw new IllegalStateException("state: " + HttpConnection.this.state);
        }

        public g timeout() {
            return HttpConnection.this.source.timeout();
        }

        protected final void unexpectedEndOfInput() {
            Util.closeQuietly(HttpConnection.this.connection.getSocket());
            HttpConnection.this.state = HttpConnection.STATE_CLOSED;
        }
    }

    final class ChunkedSink implements n {
        private boolean closed;

        private ChunkedSink() {
        }

        public synchronized void close() {
            if (!this.closed) {
                this.closed = true;
                HttpConnection.this.sink.Ac("0\r\n\r\n");
                HttpConnection.this.state = HttpConnection.STATE_READ_RESPONSE_HEADERS;
            }
        }

        public synchronized void flush() {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public g timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(k kVar, long j) {
            if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (j != 0) {
                HttpConnection.this.sink.Ah(j);
                HttpConnection.this.sink.Ac("\r\n");
                HttpConnection.this.sink.write(kVar, j);
                HttpConnection.this.sink.Ac("\r\n");
            }
        }
    }

    class ChunkedSource extends AbstractSource {
        private static final long NO_CHUNK_YET = -1;
        private long bytesRemainingInChunk = NO_CHUNK_YET;
        private boolean hasMoreChunks = true;
        private final HttpEngine httpEngine;

        ChunkedSource(HttpEngine httpEngine) {
            super();
            this.httpEngine = httpEngine;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void readChunkSize() {
            /*
            r10 = this;
            r8 = 0;
            r1 = 1;
            r2 = 0;
            r4 = r10.bytesRemainingInChunk;
            r6 = -1;
            r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r0 == 0) goto L_0x0015;
        L_0x000c:
            r0 = com.squareup.okhttp.internal.http.HttpConnection.this;
            r0 = r0.source;
            r0.zW();
        L_0x0015:
            r0 = com.squareup.okhttp.internal.http.HttpConnection.this;	 Catch:{ NumberFormatException -> 0x008f }
            r0 = r0.source;	 Catch:{ NumberFormatException -> 0x008f }
            r4 = r0.zS();	 Catch:{ NumberFormatException -> 0x008f }
            r10.bytesRemainingInChunk = r4;	 Catch:{ NumberFormatException -> 0x008f }
            r0 = com.squareup.okhttp.internal.http.HttpConnection.this;	 Catch:{ NumberFormatException -> 0x008f }
            r0 = r0.source;	 Catch:{ NumberFormatException -> 0x008f }
            r0 = r0.zW();	 Catch:{ NumberFormatException -> 0x008f }
            r3 = r0.trim();	 Catch:{ NumberFormatException -> 0x008f }
            r4 = r10.bytesRemainingInChunk;	 Catch:{ NumberFormatException -> 0x008f }
            r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
            if (r0 >= 0) goto L_0x005d;
        L_0x0035:
            r0 = r1;
        L_0x0036:
            if (r0 != 0) goto L_0x0068;
        L_0x0038:
            r0 = r3.isEmpty();	 Catch:{ NumberFormatException -> 0x008f }
            if (r0 == 0) goto L_0x005f;
        L_0x003e:
            r4 = r10.bytesRemainingInChunk;
            r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
            if (r0 != 0) goto L_0x005c;
        L_0x0044:
            r10.hasMoreChunks = r2;
            r0 = new com.squareup.okhttp.Headers$Builder;
            r0.<init>();
            r2 = com.squareup.okhttp.internal.http.HttpConnection.this;
            r2.readHeaders(r0);
            r2 = r10.httpEngine;
            r0 = r0.build();
            r2.receiveHeaders(r0);
            r10.endOfInput(r1);
        L_0x005c:
            return;
        L_0x005d:
            r0 = r2;
            goto L_0x0036;
        L_0x005f:
            r0 = ";";
            r0 = r3.startsWith(r0);	 Catch:{ NumberFormatException -> 0x008f }
            if (r0 != 0) goto L_0x003e;
        L_0x0068:
            r0 = new java.net.ProtocolException;	 Catch:{ NumberFormatException -> 0x008f }
            r1 = new java.lang.StringBuilder;	 Catch:{ NumberFormatException -> 0x008f }
            r1.<init>();	 Catch:{ NumberFormatException -> 0x008f }
            r2 = "expected chunk size and optional extensions but was \"";
            r1 = r1.append(r2);	 Catch:{ NumberFormatException -> 0x008f }
            r4 = r10.bytesRemainingInChunk;	 Catch:{ NumberFormatException -> 0x008f }
            r1 = r1.append(r4);	 Catch:{ NumberFormatException -> 0x008f }
            r1 = r1.append(r3);	 Catch:{ NumberFormatException -> 0x008f }
            r2 = "\"";
            r1 = r1.append(r2);	 Catch:{ NumberFormatException -> 0x008f }
            r1 = r1.toString();	 Catch:{ NumberFormatException -> 0x008f }
            r0.<init>(r1);	 Catch:{ NumberFormatException -> 0x008f }
            throw r0;	 Catch:{ NumberFormatException -> 0x008f }
        L_0x008f:
            r0 = move-exception;
            r1 = new java.net.ProtocolException;
            r0 = r0.getMessage();
            r1.<init>(r0);
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpConnection.ChunkedSource.readChunkSize():void");
        }

        public void close() {
            if (!this.closed) {
                if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }

        public long read(k kVar, long j) {
            Object obj = null;
            if (j >= 0) {
                obj = 1;
            }
            if (obj == null) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (!this.hasMoreChunks) {
                return NO_CHUNK_YET;
            } else {
                if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == NO_CHUNK_YET) {
                    readChunkSize();
                    if (!this.hasMoreChunks) {
                        return NO_CHUNK_YET;
                    }
                }
                long read = HttpConnection.this.source.read(kVar, Math.min(j, this.bytesRemainingInChunk));
                if (read == NO_CHUNK_YET) {
                    unexpectedEndOfInput();
                    throw new IOException("unexpected end of stream");
                }
                this.bytesRemainingInChunk -= read;
                return read;
            }
        }
    }

    final class FixedLengthSink implements n {
        private long bytesRemaining;
        private boolean closed;

        private FixedLengthSink(long j) {
            this.bytesRemaining = j;
        }

        public void close() {
            boolean z = true;
            if (!this.closed) {
                this.closed = true;
                if (this.bytesRemaining > 0) {
                    z = false;
                }
                if (z) {
                    HttpConnection.this.state = HttpConnection.STATE_READ_RESPONSE_HEADERS;
                    return;
                }
                throw new ProtocolException("unexpected end of stream");
            }
        }

        public void flush() {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public g timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(k kVar, long j) {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(kVar.size(), 0, j);
            if ((j <= this.bytesRemaining ? 1 : null) == null) {
                throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + j);
            }
            HttpConnection.this.sink.write(kVar, j);
            this.bytesRemaining -= j;
        }
    }

    class FixedLengthSource extends AbstractSource {
        private long bytesRemaining;

        public FixedLengthSource(long j) {
            super();
            this.bytesRemaining = j;
            if (this.bytesRemaining == 0) {
                endOfInput(true);
            }
        }

        public void close() {
            if (!this.closed) {
                if (!(this.bytesRemaining == 0 || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }

        public long read(k kVar, long j) {
            boolean z = false;
            if (j >= 0) {
                z = true;
            }
            if (!z) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.bytesRemaining == 0) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(kVar, Math.min(this.bytesRemaining, j));
                if (read == -1) {
                    unexpectedEndOfInput();
                    throw new ProtocolException("unexpected end of stream");
                }
                this.bytesRemaining -= read;
                if (this.bytesRemaining == 0) {
                    endOfInput(true);
                }
                return read;
            }
        }
    }

    class UnknownLengthSource extends AbstractSource {
        private boolean inputExhausted;

        private UnknownLengthSource() {
            super();
        }

        public void close() {
            if (!this.closed) {
                if (!this.inputExhausted) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }

        public long read(k kVar, long j) {
            if (!(j >= 0)) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.inputExhausted) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(kVar, j);
                if (read != -1) {
                    return read;
                }
                this.inputExhausted = true;
                endOfInput(false);
                return -1;
            }
        }
    }

    public HttpConnection(ConnectionPool connectionPool, Connection connection, Socket socket) {
        this.pool = connectionPool;
        this.connection = connection;
        this.socket = socket;
        this.source = j.AE(j.AL(socket));
        this.sink = j.AF(j.AI(socket));
    }

    public long bufferSize() {
        return this.source.zK().size();
    }

    public void closeIfOwnedBy(Object obj) {
        Internal.instance.closeIfOwnedBy(this.connection, obj);
    }

    public void closeOnIdle() {
        this.onIdle = 2;
        if (this.state == 0) {
            this.state = STATE_CLOSED;
            this.connection.getSocket().close();
        }
    }

    public void flush() {
        this.sink.flush();
    }

    public boolean isClosed() {
        return this.state == STATE_CLOSED;
    }

    public boolean isReadable() {
        int soTimeout;
        try {
            soTimeout = this.socket.getSoTimeout();
            this.socket.setSoTimeout(1);
            if (this.source.zL()) {
                this.socket.setSoTimeout(soTimeout);
                return false;
            }
            this.socket.setSoTimeout(soTimeout);
            return true;
        } catch (SocketTimeoutException e) {
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            this.socket.setSoTimeout(soTimeout);
        }
    }

    public n newChunkedSink() {
        if (this.state == 1) {
            this.state = 2;
            return new ChunkedSink();
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public v newChunkedSource(HttpEngine httpEngine) {
        if (this.state == STATE_OPEN_RESPONSE_BODY) {
            this.state = STATE_READING_RESPONSE_BODY;
            return new ChunkedSource(httpEngine);
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public n newFixedLengthSink(long j) {
        if (this.state == 1) {
            this.state = 2;
            return new FixedLengthSink(j);
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public v newFixedLengthSource(long j) {
        if (this.state == STATE_OPEN_RESPONSE_BODY) {
            this.state = STATE_READING_RESPONSE_BODY;
            return new FixedLengthSource(j);
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public v newUnknownLengthSource() {
        if (this.state == STATE_OPEN_RESPONSE_BODY) {
            this.state = STATE_READING_RESPONSE_BODY;
            return new UnknownLengthSource();
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public void poolOnIdle() {
        this.onIdle = 1;
        if (this.state == 0) {
            this.onIdle = 0;
            Internal.instance.recycle(this.pool, this.connection);
        }
    }

    public void readHeaders(Builder builder) {
        while (true) {
            String zW = this.source.zW();
            if (zW.length() != 0) {
                Internal.instance.addLenient(builder, zW);
            } else {
                return;
            }
        }
    }

    public Response.Builder readResponse() {
        if (this.state == 1 || this.state == STATE_READ_RESPONSE_HEADERS) {
            Response.Builder message;
            StatusLine parse;
            do {
                try {
                    parse = StatusLine.parse(this.source.zW());
                    message = new Response.Builder().protocol(parse.protocol).code(parse.code).message(parse.message);
                    Builder builder = new Builder();
                    readHeaders(builder);
                    builder.add(OkHeaders.SELECTED_PROTOCOL, parse.protocol.toString());
                    message.headers(builder.build());
                } catch (Throwable e) {
                    IOException iOException = new IOException("unexpected end of stream on " + this.connection + " (recycle count=" + Internal.instance.recycleCount(this.connection) + ")");
                    iOException.initCause(e);
                    throw iOException;
                }
            } while (parse.code == 100);
            this.state = STATE_OPEN_RESPONSE_BODY;
            return message;
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public void setTimeouts(int i, int i2) {
        if (i != 0) {
            this.source.timeout().timeout((long) i, TimeUnit.MILLISECONDS);
        }
        if (i2 != 0) {
            this.sink.timeout().timeout((long) i2, TimeUnit.MILLISECONDS);
        }
    }

    public void writeRequest(Headers headers, String str) {
        if (this.state == 0) {
            this.sink.Ac(str).Ac("\r\n");
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                this.sink.Ac(headers.name(i)).Ac(": ").Ac(headers.value(i)).Ac("\r\n");
            }
            this.sink.Ac("\r\n");
            this.state = 1;
            return;
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public void writeRequestBody(RetryableSink retryableSink) {
        if (this.state == 1) {
            this.state = STATE_READ_RESPONSE_HEADERS;
            retryableSink.writeToSocket(this.sink);
            return;
        }
        throw new IllegalStateException("state: " + this.state);
    }
}
