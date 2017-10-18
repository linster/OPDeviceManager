package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import okio.j;
import okio.n;
import okio.v;

public final class HttpTransport implements Transport {
    private final HttpConnection httpConnection;
    private final HttpEngine httpEngine;

    public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
        this.httpEngine = httpEngine;
        this.httpConnection = httpConnection;
    }

    private v getTransferStream(Response response) {
        if (!HttpEngine.hasBody(response)) {
            return this.httpConnection.newFixedLengthSource(0);
        }
        if ("chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return this.httpConnection.newChunkedSource(this.httpEngine);
        }
        long contentLength = OkHeaders.contentLength(response);
        return contentLength != -1 ? this.httpConnection.newFixedLengthSource(contentLength) : this.httpConnection.newUnknownLengthSource();
    }

    public boolean canReuseConnection() {
        return ("close".equalsIgnoreCase(this.httpEngine.getRequest().header("Connection")) || "close".equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")) || this.httpConnection.isClosed()) ? false : true;
    }

    public n createRequestBody(Request request, long j) {
        if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            return this.httpConnection.newChunkedSink();
        }
        if (j != -1) {
            return this.httpConnection.newFixedLengthSink(j);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    public void disconnect(HttpEngine httpEngine) {
        this.httpConnection.closeIfOwnedBy(httpEngine);
    }

    public void finishRequest() {
        this.httpConnection.flush();
    }

    public ResponseBody openResponseBody(Response response) {
        return new RealResponseBody(response.headers(), j.AE(getTransferStream(response)));
    }

    public Builder readResponseHeaders() {
        return this.httpConnection.readResponse();
    }

    public void releaseConnectionOnIdle() {
        if (canReuseConnection()) {
            this.httpConnection.poolOnIdle();
        } else {
            this.httpConnection.closeOnIdle();
        }
    }

    public void writeRequestBody(RetryableSink retryableSink) {
        this.httpConnection.writeRequestBody(retryableSink);
    }

    public void writeRequestHeaders(Request request) {
        this.httpEngine.writingRequestHeaders();
        this.httpConnection.writeRequest(request.headers(), RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type(), this.httpEngine.getConnection().getProtocol()));
    }
}
