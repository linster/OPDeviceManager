package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;

import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

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
    private int onIdle;
    private final ConnectionPool pool;
    private final BufferedSink sink;
    private final Socket socket;
    private final BufferedSource source;
    private int state;

    private abstract class AbstractSource implements Source {
        protected boolean closed;

        private AbstractSource() {
        }

        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }

        protected final void endOfInput(boolean recyclable) throws IOException {
            if (HttpConnection.this.state == HttpConnection.STATE_READING_RESPONSE_BODY) {
                HttpConnection.this.state = HttpConnection.STATE_IDLE;
                if (recyclable && HttpConnection.this.onIdle == HttpConnection.STATE_OPEN_REQUEST_BODY) {
                    HttpConnection.this.onIdle = HttpConnection.STATE_IDLE;
                    Internal.instance.recycle(HttpConnection.this.pool, HttpConnection.this.connection);
                    return;
                } else if (HttpConnection.this.onIdle == HttpConnection.STATE_WRITING_REQUEST_BODY) {
                    HttpConnection.this.state = HttpConnection.STATE_CLOSED;
                    HttpConnection.this.connection.getSocket().close();
                    return;
                } else {
                    return;
                }
            }
            throw new IllegalStateException("state: " + HttpConnection.this.state);
        }

        protected final void unexpectedEndOfInput() {
            Util.closeQuietly(HttpConnection.this.connection.getSocket());
            HttpConnection.this.state = HttpConnection.STATE_CLOSED;
        }
    }

    private final class ChunkedSink implements Sink {
        private boolean closed;

        private ChunkedSink() {
        }

        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (byteCount != 0) {
                HttpConnection.this.sink.writeHexadecimalUnsignedLong(byteCount);
                HttpConnection.this.sink.writeUtf8("\r\n");
                HttpConnection.this.sink.write(source, byteCount);
                HttpConnection.this.sink.writeUtf8("\r\n");
            }
        }

        public synchronized void flush() throws IOException {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public synchronized void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                HttpConnection.this.sink.writeUtf8("0\r\n\r\n");
                HttpConnection.this.state = HttpConnection.STATE_READ_RESPONSE_HEADERS;
            }
        }
    }

    private class ChunkedSource extends AbstractSource {
        private static final long NO_CHUNK_YET = -1;
        private long bytesRemainingInChunk;
        private boolean hasMoreChunks;
        private final HttpEngine httpEngine;

        ChunkedSource(HttpEngine httpEngine) throws IOException {
            super(null);
            this.bytesRemainingInChunk = NO_CHUNK_YET;
            this.hasMoreChunks = true;
            this.httpEngine = httpEngine;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            Object obj = null;
            if (byteCount >= 0) {
                obj = HttpConnection.STATE_OPEN_REQUEST_BODY;
            }
            if (obj == null) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
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
                long read = HttpConnection.this.source.read(sink, Math.min(byteCount, this.bytesRemainingInChunk));
                if (read == NO_CHUNK_YET) {
                    unexpectedEndOfInput();
                    throw new IOException("unexpected end of stream");
                }
                this.bytesRemainingInChunk -= read;
                return read;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void readChunkSize() throws java.io.IOException {
            /*
            r12 = this;
            r10 = 0;
            r4 = 1;
            r5 = 0;
            r6 = r12.bytesRemainingInChunk;
            r8 = -1;
            r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
            if (r3 == 0) goto L_0x0015;
        L_0x000c:
            r3 = com.squareup.okhttp.internal.http.HttpConnection.this;
            r3 = r3.source;
            r3.readUtf8LineStrict();
        L_0x0015:
            r3 = com.squareup.okhttp.internal.http.HttpConnection.this;	 Catch:{ NumberFormatException -> 0x008f }
            r3 = r3.source;	 Catch:{ NumberFormatException -> 0x008f }
            r6 = r3.readHexadecimalUnsignedLong();	 Catch:{ NumberFormatException -> 0x008f }
            r12.bytesRemainingInChunk = r6;	 Catch:{ NumberFormatException -> 0x008f }
            r3 = com.squareup.okhttp.internal.http.HttpConnection.this;	 Catch:{ NumberFormatException -> 0x008f }
            r3 = r3.source;	 Catch:{ NumberFormatException -> 0x008f }
            r3 = r3.readUtf8LineStrict();	 Catch:{ NumberFormatException -> 0x008f }
            r1 = r3.trim();	 Catch:{ NumberFormatException -> 0x008f }
            r6 = r12.bytesRemainingInChunk;	 Catch:{ NumberFormatException -> 0x008f }
            r3 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
            if (r3 >= 0) goto L_0x005d;
        L_0x0035:
            r3 = r4;
        L_0x0036:
            if (r3 != 0) goto L_0x0068;
        L_0x0038:
            r3 = r1.isEmpty();	 Catch:{ NumberFormatException -> 0x008f }
            if (r3 == 0) goto L_0x005f;
        L_0x003e:
            r6 = r12.bytesRemainingInChunk;
            r3 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
            if (r3 != 0) goto L_0x005c;
        L_0x0044:
            r12.hasMoreChunks = r5;
            r2 = new com.squareup.okhttp.Headers$Builder;
            r2.<init>();
            r3 = com.squareup.okhttp.internal.http.HttpConnection.this;
            r3.readHeaders(r2);
            r3 = r12.httpEngine;
            r5 = r2.build();
            r3.receiveHeaders(r5);
            r12.endOfInput(r4);
        L_0x005c:
            return;
        L_0x005d:
            r3 = r5;
            goto L_0x0036;
        L_0x005f:
            r3 = ";";
            r3 = r1.startsWith(r3);	 Catch:{ NumberFormatException -> 0x008f }
            if (r3 != 0) goto L_0x003e;
        L_0x0068:
            r3 = new java.net.ProtocolException;	 Catch:{ NumberFormatException -> 0x008f }
            r4 = new java.lang.StringBuilder;	 Catch:{ NumberFormatException -> 0x008f }
            r4.<init>();	 Catch:{ NumberFormatException -> 0x008f }
            r5 = "expected chunk size and optional extensions but was \"";
            r4 = r4.append(r5);	 Catch:{ NumberFormatException -> 0x008f }
            r6 = r12.bytesRemainingInChunk;	 Catch:{ NumberFormatException -> 0x008f }
            r4 = r4.append(r6);	 Catch:{ NumberFormatException -> 0x008f }
            r4 = r4.append(r1);	 Catch:{ NumberFormatException -> 0x008f }
            r5 = "\"";
            r4 = r4.append(r5);	 Catch:{ NumberFormatException -> 0x008f }
            r4 = r4.toString();	 Catch:{ NumberFormatException -> 0x008f }
            r3.<init>(r4);	 Catch:{ NumberFormatException -> 0x008f }
            throw r3;	 Catch:{ NumberFormatException -> 0x008f }
        L_0x008f:
            r0 = move-exception;
            r3 = new java.net.ProtocolException;
            r4 = r0.getMessage();
            r3.<init>(r4);
            throw r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpConnection.ChunkedSource.readChunkSize():void");
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    private final class FixedLengthSink implements Sink {
        private long bytesRemaining;
        private boolean closed;

        private FixedLengthSink(long bytesRemaining) {
            this.bytesRemaining = bytesRemaining;
        }

        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Object obj;
            Util.checkOffsetAndCount(source.size(), 0, byteCount);
            if (byteCount <= this.bytesRemaining) {
                obj = HttpConnection.STATE_OPEN_REQUEST_BODY;
            } else {
                obj = HttpConnection.STATE_IDLE;
            }
            if (obj == null) {
                throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + byteCount);
            }
            HttpConnection.this.sink.write(source, byteCount);
            this.bytesRemaining -= byteCount;
        }

        public void flush() throws IOException {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public void close() throws IOException {
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
    }

    private class FixedLengthSource extends AbstractSource {
        private long bytesRemaining;

        public FixedLengthSource(long length) throws IOException {
            super(null);
            this.bytesRemaining = length;
            if (this.bytesRemaining == 0) {
                endOfInput(true);
            }
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            boolean z = false;
            if (byteCount >= 0) {
                z = true;
            }
            if (!z) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.bytesRemaining == 0) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(sink, Math.min(this.bytesRemaining, byteCount));
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

        public void close() throws IOException {
            if (!this.closed) {
                if (!(this.bytesRemaining == 0 || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    private class UnknownLengthSource extends AbstractSource {
        private boolean inputExhausted;

        private UnknownLengthSource() {
            super(null);
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (!(byteCount >= 0)) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.inputExhausted) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(sink, byteCount);
                if (read != -1) {
                    return read;
                }
                this.inputExhausted = true;
                endOfInput(false);
                return -1;
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (!this.inputExhausted) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    public HttpConnection(ConnectionPool pool, Connection connection, Socket socket) throws IOException {
        this.state = STATE_IDLE;
        this.onIdle = STATE_IDLE;
        this.pool = pool;
        this.connection = connection;
        this.socket = socket;
        this.source = Okio.buffer(Okio.source(socket));
        this.sink = Okio.buffer(Okio.sink(socket));
    }

    public void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) {
        if (readTimeoutMillis != 0) {
            this.source.timeout().timeout((long) readTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        if (writeTimeoutMillis != 0) {
            this.sink.timeout().timeout((long) writeTimeoutMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void poolOnIdle() {
        this.onIdle = STATE_OPEN_REQUEST_BODY;
        if (this.state == 0) {
            this.onIdle = STATE_IDLE;
            Internal.instance.recycle(this.pool, this.connection);
        }
    }

    public void closeOnIdle() throws IOException {
        this.onIdle = STATE_WRITING_REQUEST_BODY;
        if (this.state == 0) {
            this.state = STATE_CLOSED;
            this.connection.getSocket().close();
        }
    }

    public boolean isClosed() {
        return this.state == STATE_CLOSED;
    }

    public void closeIfOwnedBy(Object owner) throws IOException {
        Internal.instance.closeIfOwnedBy(this.connection, owner);
    }

    public void flush() throws IOException {
        this.sink.flush();
    }

    public long bufferSize() {
        return this.source.buffer().size();
    }

    public boolean isReadable() {
        int readTimeout;
        try {
            readTimeout = this.socket.getSoTimeout();
            this.socket.setSoTimeout(STATE_OPEN_REQUEST_BODY);
            if (this.source.exhausted()) {
                this.socket.setSoTimeout(readTimeout);
                return false;
            }
            this.socket.setSoTimeout(readTimeout);
            return true;
        } catch (SocketTimeoutException e) {
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            this.socket.setSoTimeout(readTimeout);
        }
    }

    public void writeRequest(Headers headers, String requestLine) throws IOException {
        if (this.state == 0) {
            this.sink.writeUtf8(requestLine).writeUtf8("\r\n");
            int size = headers.size();
            for (int i = STATE_IDLE; i < size; i += STATE_OPEN_REQUEST_BODY) {
                this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
            }
            this.sink.writeUtf8("\r\n");
            this.state = STATE_OPEN_REQUEST_BODY;
            return;
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public Builder readResponse() throws IOException {
        if (this.state == STATE_OPEN_REQUEST_BODY || this.state == STATE_READ_RESPONSE_HEADERS) {
            Builder responseBuilder;
            StatusLine statusLine;
            do {
                try {
                    statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
                    responseBuilder = new Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message);
                    Headers.Builder headersBuilder = new Headers.Builder();
                    readHeaders(headersBuilder);
                    headersBuilder.add(OkHeaders.SELECTED_PROTOCOL, statusLine.protocol.toString());
                    responseBuilder.headers(headersBuilder.build());
                } catch (EOFException e) {
                    IOException exception = new IOException("unexpected end of stream on " + this.connection + " (recycle count=" + Internal.instance.recycleCount(this.connection) + ")");
                    exception.initCause(e);
                    throw exception;
                }
            } while (statusLine.code == 100);
            this.state = STATE_OPEN_RESPONSE_BODY;
            return responseBuilder;
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public void readHeaders(Headers.Builder builder) throws IOException {
        while (true) {
            String line = this.source.readUtf8LineStrict();
            if (line.length() != 0) {
                Internal.instance.addLenient(builder, line);
            } else {
                return;
            }
        }
    }

    public Sink newChunkedSink() {
        if (this.state == STATE_OPEN_REQUEST_BODY) {
            this.state = STATE_WRITING_REQUEST_BODY;
            return new ChunkedSink();
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public Sink newFixedLengthSink(long contentLength) {
        if (this.state == STATE_OPEN_REQUEST_BODY) {
            this.state = STATE_WRITING_REQUEST_BODY;
            return new FixedLengthSink(contentLength, null);
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public void writeRequestBody(RetryableSink requestBody) throws IOException {
        if (this.state == STATE_OPEN_REQUEST_BODY) {
            this.state = STATE_READ_RESPONSE_HEADERS;
            requestBody.writeToSocket(this.sink);
            return;
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public Source newFixedLengthSource(long length) throws IOException {
        if (this.state == STATE_OPEN_RESPONSE_BODY) {
            this.state = STATE_READING_RESPONSE_BODY;
            return new FixedLengthSource(length);
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public Source newChunkedSource(HttpEngine httpEngine) throws IOException {
        if (this.state == STATE_OPEN_RESPONSE_BODY) {
            this.state = STATE_READING_RESPONSE_BODY;
            return new ChunkedSource(httpEngine);
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public Source newUnknownLengthSource() throws IOException {
        if (this.state == STATE_OPEN_RESPONSE_BODY) {
            this.state = STATE_READING_RESPONSE_BODY;
            return new UnknownLengthSource();
        }
        throw new IllegalStateException("state: " + this.state);
    }
}
