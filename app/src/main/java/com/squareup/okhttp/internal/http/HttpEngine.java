package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import com.squareup.okhttp.internal.http.CacheStrategy.Factory;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY;
    public static final int MAX_FOLLOW_UPS = 20;
    private Address address;
    public final boolean bufferRequestBody;
    private BufferedSink bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    private final boolean callerWritesRequestBody;
    final OkHttpClient client;
    private Connection connection;
    private final boolean forWebSocket;
    private Request networkRequest;
    private final Response priorResponse;
    private Sink requestBodyOut;
    private Route route;
    private RouteSelector routeSelector;
    long sentRequestMillis;
    private CacheRequest storeRequest;
    private boolean transparentGzip;
    private Transport transport;
    private final Request userRequest;
    private Response userResponse;

    /* renamed from: com.squareup.okhttp.internal.http.HttpEngine.2 */
    class AnonymousClass2 implements Source {
        boolean cacheRequestClosed;
        final /* synthetic */ BufferedSink val$cacheBody;
        final /* synthetic */ CacheRequest val$cacheRequest;
        final /* synthetic */ BufferedSource val$source;

        AnonymousClass2(BufferedSource bufferedSource, CacheRequest cacheRequest, BufferedSink bufferedSink) {
            this.val$source = bufferedSource;
            this.val$cacheRequest = cacheRequest;
            this.val$cacheBody = bufferedSink;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            try {
                long bytesRead = this.val$source.read(sink, byteCount);
                if (bytesRead == -1) {
                    if (!this.cacheRequestClosed) {
                        this.cacheRequestClosed = true;
                        this.val$cacheBody.close();
                    }
                    return -1;
                }
                sink.copyTo(this.val$cacheBody.buffer(), sink.size() - bytesRead, bytesRead);
                this.val$cacheBody.emitCompleteSegments();
                return bytesRead;
            } catch (IOException e) {
                if (!this.cacheRequestClosed) {
                    this.cacheRequestClosed = true;
                    this.val$cacheRequest.abort();
                }
                throw e;
            }
        }

        public Timeout timeout() {
            return this.val$source.timeout();
        }

        public void close() throws IOException {
            if (!(this.cacheRequestClosed || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                this.cacheRequestClosed = true;
                this.val$cacheRequest.abort();
            }
            this.val$source.close();
        }
    }

    class NetworkInterceptorChain implements Chain {
        private int calls;
        private final int index;
        private final Request request;

        NetworkInterceptorChain(int index, Request request) {
            this.index = index;
            this.request = request;
        }

        public Connection connection() {
            return HttpEngine.this.connection;
        }

        public Request request() {
            return this.request;
        }

        public Response proceed(Request request) throws IOException {
            this.calls++;
            if (this.index > 0) {
                Interceptor caller = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index - 1);
                Address address = connection().getRoute().getAddress();
                if (!request.url().getHost().equals(address.getUriHost()) || Util.getEffectivePort(request.url()) != address.getUriPort()) {
                    throw new IllegalStateException("network interceptor " + caller + " must retain the same host and port");
                } else if (this.calls > 1) {
                    throw new IllegalStateException("network interceptor " + caller + " must call proceed() exactly once");
                }
            }
            if (this.index >= HttpEngine.this.client.networkInterceptors().size()) {
                HttpEngine.this.transport.writeRequestHeaders(request);
                if (HttpEngine.this.permitsRequestBody() && request.body() != null) {
                    BufferedSink bufferedRequestBody = Okio.buffer(HttpEngine.this.transport.createRequestBody(request, request.body().contentLength()));
                    request.body().writeTo(bufferedRequestBody);
                    bufferedRequestBody.close();
                }
                return HttpEngine.this.readNetworkResponse();
            }
            NetworkInterceptorChain chain = new NetworkInterceptorChain(this.index + 1, request);
            Interceptor interceptor = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index);
            Response interceptedResponse = interceptor.intercept(chain);
            if (chain.calls == 1) {
                return interceptedResponse;
            }
            throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
        }
    }

    static {
        EMPTY_BODY = new ResponseBody() {
            public MediaType contentType() {
                return null;
            }

            public long contentLength() {
                return 0;
            }

            public BufferedSource source() {
                return new Buffer();
            }
        };
    }

    public HttpEngine(OkHttpClient client, Request request, boolean bufferRequestBody, boolean callerWritesRequestBody, boolean forWebSocket, Connection connection, RouteSelector routeSelector, RetryableSink requestBodyOut, Response priorResponse) {
        this.sentRequestMillis = -1;
        this.client = client;
        this.userRequest = request;
        this.bufferRequestBody = bufferRequestBody;
        this.callerWritesRequestBody = callerWritesRequestBody;
        this.forWebSocket = forWebSocket;
        this.connection = connection;
        this.routeSelector = routeSelector;
        this.requestBodyOut = requestBodyOut;
        this.priorResponse = priorResponse;
        if (connection == null) {
            this.route = null;
            return;
        }
        Internal.instance.setOwner(connection, this);
        this.route = connection.getRoute();
    }

    public void sendRequest() throws IOException {
        if (this.cacheStrategy != null) {
            return;
        }
        if (this.transport == null) {
            Response cacheCandidate;
            Request request = networkRequest(this.userRequest);
            InternalCache responseCache = Internal.instance.internalCache(this.client);
            if (responseCache == null) {
                cacheCandidate = null;
            } else {
                cacheCandidate = responseCache.get(request);
            }
            this.cacheStrategy = new Factory(System.currentTimeMillis(), request, cacheCandidate).get();
            this.networkRequest = this.cacheStrategy.networkRequest;
            this.cacheResponse = this.cacheStrategy.cacheResponse;
            if (responseCache != null) {
                responseCache.trackResponse(this.cacheStrategy);
            }
            if (cacheCandidate != null && this.cacheResponse == null) {
                Util.closeQuietly(cacheCandidate.body());
            }
            if (this.networkRequest == null) {
                if (this.connection != null) {
                    Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
                    this.connection = null;
                }
                if (this.cacheResponse == null) {
                    this.userResponse = new Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
                } else {
                    this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
                }
                this.userResponse = unzip(this.userResponse);
            } else {
                if (this.connection == null) {
                    connect();
                }
                this.transport = Internal.instance.newTransport(this.connection, this);
                if (this.callerWritesRequestBody && permitsRequestBody() && this.requestBodyOut == null) {
                    long contentLength = OkHeaders.contentLength(request);
                    if (this.bufferRequestBody) {
                        Object obj;
                        if (contentLength <= 2147483647L) {
                            obj = 1;
                        } else {
                            obj = null;
                        }
                        if (obj == null) {
                            throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                        } else if (contentLength != -1) {
                            this.transport.writeRequestHeaders(this.networkRequest);
                            this.requestBodyOut = new RetryableSink((int) contentLength);
                        } else {
                            this.requestBodyOut = new RetryableSink();
                        }
                    } else {
                        this.transport.writeRequestHeaders(this.networkRequest);
                        this.requestBodyOut = this.transport.createRequestBody(this.networkRequest, contentLength);
                    }
                }
            }
            return;
        }
        throw new IllegalStateException();
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private void connect() throws IOException {
        if (this.connection == null) {
            if (this.routeSelector == null) {
                this.address = createAddress(this.client, this.networkRequest);
                this.routeSelector = RouteSelector.get(this.address, this.networkRequest, this.client);
            }
            this.connection = nextConnection();
            this.route = this.connection.getRoute();
            return;
        }
        throw new IllegalStateException();
    }

    private Connection nextConnection() throws IOException {
        Connection connection = createNextConnection();
        Internal.instance.connectAndSetOwner(this.client, connection, this, this.networkRequest);
        return connection;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.squareup.okhttp.Connection createNextConnection() throws java.io.IOException {
        /*
        r5 = this;
        r3 = r5.client;
        r0 = r3.getConnectionPool();
    L_0x0006:
        r3 = r5.address;
        r1 = r0.get(r3);
        if (r1 != 0) goto L_0x001a;
    L_0x000e:
        r3 = r5.routeSelector;
        r2 = r3.next();
        r3 = new com.squareup.okhttp.Connection;
        r3.<init>(r0, r2);
        return r3;
    L_0x001a:
        r3 = r5.networkRequest;
        r3 = r3.method();
        r4 = "GET";
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x002a;
    L_0x0029:
        return r1;
    L_0x002a:
        r3 = com.squareup.okhttp.internal.Internal.instance;
        r3 = r3.isReadable(r1);
        if (r3 != 0) goto L_0x0029;
    L_0x0032:
        r3 = r1.getSocket();
        r3.close();
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpEngine.createNextConnection():com.squareup.okhttp.Connection");
    }

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }

    boolean permitsRequestBody() {
        return HttpMethod.permitsRequestBody(this.userRequest.method());
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public BufferedSink getBufferedRequestBody() {
        BufferedSink bufferedSink = null;
        BufferedSink result = this.bufferedRequestBody;
        if (result != null) {
            return result;
        }
        Sink requestBody = getRequestBody();
        if (requestBody != null) {
            bufferedSink = Okio.buffer(requestBody);
            this.bufferedRequestBody = bufferedSink;
        }
        return bufferedSink;
    }

    public boolean hasResponse() {
        return this.userResponse != null;
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public Response getResponse() {
        if (this.userResponse != null) {
            return this.userResponse;
        }
        throw new IllegalStateException();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public HttpEngine recover(IOException e, Sink requestBodyOut) {
        boolean canRetryRequestBody = false;
        if (!(this.routeSelector == null || this.connection == null)) {
            connectFailed(this.routeSelector, e);
        }
        if (requestBodyOut == null || (requestBodyOut instanceof RetryableSink)) {
            canRetryRequestBody = true;
        }
        if (this.routeSelector != null || this.connection != null) {
            if (this.routeSelector == null || this.routeSelector.hasNext()) {
                if (isRecoverable(e) && canRetryRequestBody) {
                    return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), this.routeSelector, (RetryableSink) requestBodyOut, this.priorResponse);
                }
            }
        }
        return null;
    }

    private void connectFailed(RouteSelector routeSelector, IOException e) {
        if (Internal.instance.recycleCount(this.connection) <= 0) {
            routeSelector.connectFailed(this.connection.getRoute(), e);
        }
    }

    public HttpEngine recover(IOException e) {
        return recover(e, this.requestBodyOut);
    }

    private boolean isRecoverable(IOException e) {
        if (this.client.getRetryOnConnectionFailure() && !(e instanceof SSLPeerUnverifiedException)) {
            if (e instanceof SSLHandshakeException) {
                if (!(e.getCause() instanceof CertificateException)) {
                }
            }
            if ((e instanceof ProtocolException) || (e instanceof InterruptedIOException)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public Route getRoute() {
        return this.route;
    }

    private void maybeCache() throws IOException {
        InternalCache responseCache = Internal.instance.internalCache(this.client);
        if (responseCache == null) {
            return;
        }
        if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
            this.storeRequest = responseCache.put(stripBody(this.userResponse));
            return;
        }
        if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
            try {
                responseCache.remove(this.networkRequest);
            } catch (IOException e) {
            }
        }
    }

    public void releaseConnection() throws IOException {
        if (!(this.transport == null || this.connection == null)) {
            this.transport.releaseConnectionOnIdle();
        }
        this.connection = null;
    }

    public void disconnect() {
        if (this.transport != null) {
            try {
                this.transport.disconnect(this);
            } catch (IOException e) {
            }
        }
    }

    public Connection close() {
        if (this.bufferedRequestBody != null) {
            Util.closeQuietly(this.bufferedRequestBody);
        } else if (this.requestBodyOut != null) {
            Util.closeQuietly(this.requestBodyOut);
        }
        if (this.userResponse != null) {
            Util.closeQuietly(this.userResponse.body());
            if (this.transport == null || this.connection == null || this.transport.canReuseConnection()) {
                if (!(this.connection == null || Internal.instance.clearOwner(this.connection))) {
                    this.connection = null;
                }
                Connection result = this.connection;
                this.connection = null;
                return result;
            }
            Util.closeQuietly(this.connection.getSocket());
            this.connection = null;
            return null;
        }
        if (this.connection != null) {
            Util.closeQuietly(this.connection.getSocket());
        }
        this.connection = null;
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.squareup.okhttp.Response unzip(com.squareup.okhttp.Response r6) throws java.io.IOException {
        /*
        r5 = this;
        r2 = r5.transparentGzip;
        if (r2 != 0) goto L_0x0005;
    L_0x0004:
        return r6;
    L_0x0005:
        r2 = "gzip";
        r3 = r5.userResponse;
        r4 = "Content-Encoding";
        r3 = r3.header(r4);
        r2 = r2.equalsIgnoreCase(r3);
        if (r2 == 0) goto L_0x0004;
    L_0x0017:
        r2 = r6.body();
        if (r2 == 0) goto L_0x005e;
    L_0x001d:
        r0 = new okio.GzipSource;
        r2 = r6.body();
        r2 = r2.source();
        r0.<init>(r2);
        r2 = r6.headers();
        r2 = r2.newBuilder();
        r3 = "Content-Encoding";
        r2 = r2.removeAll(r3);
        r3 = "Content-Length";
        r2 = r2.removeAll(r3);
        r1 = r2.build();
        r2 = r6.newBuilder();
        r2 = r2.headers(r1);
        r3 = new com.squareup.okhttp.internal.http.RealResponseBody;
        r4 = okio.Okio.buffer(r0);
        r3.<init>(r1, r4);
        r2 = r2.body(r3);
        r2 = r2.build();
        return r2;
    L_0x005e:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpEngine.unzip(com.squareup.okhttp.Response):com.squareup.okhttp.Response");
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int responseCode = response.code();
        if (responseCode < 100 || responseCode >= 200) {
            if (!(responseCode == 204 || responseCode == 304)) {
                return true;
            }
        }
        return OkHeaders.contentLength(response) != -1 || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"));
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder result = request.newBuilder();
        if (request.header("Host") == null) {
            result.header("Host", hostHeader(request.url()));
        }
        if (this.connection == null || this.connection.getProtocol() != Protocol.HTTP_1_0) {
            if (request.header("Connection") == null) {
                result.header("Connection", "Keep-Alive");
            }
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            result.header("Accept-Encoding", "gzip");
        }
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            OkHeaders.addCookies(result, cookieHandler.get(request.uri(), OkHeaders.toMultimap(result.build().headers(), null)));
        }
        if (request.header("User-Agent") == null) {
            result.header("User-Agent", Version.userAgent());
        }
        return result.build();
    }

    public static String hostHeader(URL url) {
        if (Util.getEffectivePort(url) == Util.getDefaultPort(url.getProtocol())) {
            return url.getHost();
        }
        return url.getHost() + ":" + url.getPort();
    }

    public void readResponse() throws IOException {
        int i = 0;
        if (this.userResponse != null) {
            return;
        }
        if (this.networkRequest == null && this.cacheResponse == null) {
            throw new IllegalStateException("call sendRequest() first!");
        } else if (this.networkRequest != null) {
            Response networkResponse;
            if (this.forWebSocket) {
                this.transport.writeRequestHeaders(this.networkRequest);
                networkResponse = readNetworkResponse();
            } else if (this.callerWritesRequestBody) {
                if (this.bufferedRequestBody != null) {
                    if (this.bufferedRequestBody.buffer().size() <= 0) {
                        i = 1;
                    }
                    if (i == 0) {
                        this.bufferedRequestBody.emit();
                    }
                }
                if (this.sentRequestMillis == -1) {
                    if (OkHeaders.contentLength(this.networkRequest) == -1 && (this.requestBodyOut instanceof RetryableSink)) {
                        this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(((RetryableSink) this.requestBodyOut).contentLength())).build();
                    }
                    this.transport.writeRequestHeaders(this.networkRequest);
                }
                if (this.requestBodyOut != null) {
                    if (this.bufferedRequestBody == null) {
                        this.requestBodyOut.close();
                    } else {
                        this.bufferedRequestBody.close();
                    }
                    if (this.requestBodyOut instanceof RetryableSink) {
                        this.transport.writeRequestBody((RetryableSink) this.requestBodyOut);
                    }
                }
                networkResponse = readNetworkResponse();
            } else {
                networkResponse = new NetworkInterceptorChain(0, this.networkRequest).proceed(this.networkRequest);
            }
            receiveHeaders(networkResponse.headers());
            if (this.cacheResponse != null) {
                if (validate(this.cacheResponse, networkResponse)) {
                    this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                    networkResponse.body().close();
                    releaseConnection();
                    InternalCache responseCache = Internal.instance.internalCache(this.client);
                    responseCache.trackConditionalCacheHit();
                    responseCache.update(this.cacheResponse, stripBody(this.userResponse));
                    this.userResponse = unzip(this.userResponse);
                    return;
                }
                Util.closeQuietly(this.cacheResponse.body());
            }
            this.userResponse = networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
            if (hasBody(this.userResponse)) {
                maybeCache();
                this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
            }
        }
    }

    private Response readNetworkResponse() throws IOException {
        this.transport.finishRequest();
        Response networkResponse = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        if (!this.forWebSocket) {
            networkResponse = networkResponse.newBuilder().body(this.transport.openResponseBody(networkResponse)).build();
        }
        Internal.instance.setProtocol(this.connection, networkResponse.protocol());
        return networkResponse;
    }

    private Response cacheWritingResponse(CacheRequest cacheRequest, Response response) throws IOException {
        if (cacheRequest == null) {
            return response;
        }
        Sink cacheBodyUnbuffered = cacheRequest.body();
        if (cacheBodyUnbuffered == null) {
            return response;
        }
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer(new AnonymousClass2(response.body().source(), cacheRequest, Okio.buffer(cacheBodyUnbuffered))))).build();
    }

    private static boolean validate(Response cached, Response network) {
        if (network.code() == 304) {
            return true;
        }
        Date lastModified = cached.headers().getDate("Last-Modified");
        if (lastModified != null) {
            Date networkLastModified = network.headers().getDate("Last-Modified");
            if (networkLastModified != null) {
                boolean z;
                if (networkLastModified.getTime() >= lastModified.getTime()) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Headers combine(Headers cachedHeaders, Headers networkHeaders) throws IOException {
        Headers.Builder result = new Headers.Builder();
        int i = 0;
        int size = cachedHeaders.size();
        while (i < size) {
            String fieldName = cachedHeaders.name(i);
            String value = cachedHeaders.value(i);
            if ("Warning".equalsIgnoreCase(fieldName)) {
                if (value.startsWith("1")) {
                    i++;
                }
            }
            if (OkHeaders.isEndToEnd(fieldName) && networkHeaders.get(fieldName) != null) {
                i++;
            } else {
                result.add(fieldName, value);
                i++;
            }
        }
        size = networkHeaders.size();
        for (i = 0; i < size; i++) {
            fieldName = networkHeaders.name(i);
            if (!"Content-Length".equalsIgnoreCase(fieldName) && OkHeaders.isEndToEnd(fieldName)) {
                result.add(fieldName, networkHeaders.value(i));
            }
        }
        return result.build();
    }

    public void receiveHeaders(Headers headers) throws IOException {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.squareup.okhttp.Request followUpRequest() throws java.io.IOException {
        /*
        r9 = this;
        r8 = 0;
        r6 = r9.userResponse;
        if (r6 == 0) goto L_0x001b;
    L_0x0005:
        r6 = r9.getRoute();
        if (r6 != 0) goto L_0x0021;
    L_0x000b:
        r6 = r9.client;
        r4 = r6.getProxy();
    L_0x0011:
        r6 = r9.userResponse;
        r2 = r6.code();
        switch(r2) {
            case 300: goto L_0x0057;
            case 301: goto L_0x0057;
            case 302: goto L_0x0057;
            case 303: goto L_0x0057;
            case 307: goto L_0x0048;
            case 308: goto L_0x0048;
            case 401: goto L_0x0032;
            case 407: goto L_0x002a;
            default: goto L_0x001a;
        };
    L_0x001a:
        return r8;
    L_0x001b:
        r6 = new java.lang.IllegalStateException;
        r6.<init>();
        throw r6;
    L_0x0021:
        r6 = r9.getRoute();
        r4 = r6.getProxy();
        goto L_0x0011;
    L_0x002a:
        r6 = r4.type();
        r7 = java.net.Proxy.Type.HTTP;
        if (r6 != r7) goto L_0x003f;
    L_0x0032:
        r6 = r9.client;
        r6 = r6.getAuthenticator();
        r7 = r9.userResponse;
        r6 = com.squareup.okhttp.internal.http.OkHeaders.processAuthHeader(r6, r7, r4);
        return r6;
    L_0x003f:
        r6 = new java.net.ProtocolException;
        r7 = "Received HTTP_PROXY_AUTH (407) code while not using proxy";
        r6.<init>(r7);
        throw r6;
    L_0x0048:
        r6 = r9.userRequest;
        r6 = r6.method();
        r7 = "GET";
        r6 = r6.equals(r7);
        if (r6 == 0) goto L_0x00b7;
    L_0x0057:
        r6 = r9.client;
        r6 = r6.getFollowRedirects();
        if (r6 == 0) goto L_0x00c7;
    L_0x005f:
        r6 = r9.userResponse;
        r7 = "Location";
        r0 = r6.header(r7);
        if (r0 == 0) goto L_0x00c8;
    L_0x006a:
        r5 = new java.net.URL;
        r6 = r9.userRequest;
        r6 = r6.url();
        r5.<init>(r6, r0);
        r6 = r5.getProtocol();
        r7 = "https";
        r6 = r6.equals(r7);
        if (r6 == 0) goto L_0x00c9;
    L_0x0082:
        r6 = r5.getProtocol();
        r7 = r9.userRequest;
        r7 = r7.url();
        r7 = r7.getProtocol();
        r3 = r6.equals(r7);
        if (r3 == 0) goto L_0x00d7;
    L_0x0096:
        r6 = r9.userRequest;
        r1 = r6.newBuilder();
        r6 = r9.userRequest;
        r6 = r6.method();
        r6 = com.squareup.okhttp.internal.http.HttpMethod.permitsRequestBody(r6);
        if (r6 != 0) goto L_0x00e0;
    L_0x00a8:
        r6 = r9.sameConnection(r5);
        if (r6 == 0) goto L_0x00f9;
    L_0x00ae:
        r6 = r1.url(r5);
        r6 = r6.build();
        return r6;
    L_0x00b7:
        r6 = r9.userRequest;
        r6 = r6.method();
        r7 = "HEAD";
        r6 = r6.equals(r7);
        if (r6 != 0) goto L_0x0057;
    L_0x00c6:
        return r8;
    L_0x00c7:
        return r8;
    L_0x00c8:
        return r8;
    L_0x00c9:
        r6 = r5.getProtocol();
        r7 = "http";
        r6 = r6.equals(r7);
        if (r6 != 0) goto L_0x0082;
    L_0x00d6:
        return r8;
    L_0x00d7:
        r6 = r9.client;
        r6 = r6.getFollowSslRedirects();
        if (r6 != 0) goto L_0x0096;
    L_0x00df:
        return r8;
    L_0x00e0:
        r6 = "GET";
        r1.method(r6, r8);
        r6 = "Transfer-Encoding";
        r1.removeHeader(r6);
        r6 = "Content-Length";
        r1.removeHeader(r6);
        r6 = "Content-Type";
        r1.removeHeader(r6);
        goto L_0x00a8;
    L_0x00f9:
        r6 = "Authorization";
        r1.removeHeader(r6);
        goto L_0x00ae;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpEngine.followUpRequest():com.squareup.okhttp.Request");
    }

    public boolean sameConnection(URL followUp) {
        URL url = this.userRequest.url();
        if (url.getHost().equals(followUp.getHost()) && Util.getEffectivePort(url) == Util.getEffectivePort(followUp) && url.getProtocol().equals(followUp.getProtocol())) {
            return true;
        }
        return false;
    }

    private static Address createAddress(OkHttpClient client, Request request) throws UnknownHostException {
        String uriHost = request.url().getHost();
        if (uriHost == null || uriHost.length() == 0) {
            throw new UnknownHostException(request.url().toString());
        }
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (request.isHttps()) {
            sslSocketFactory = client.getSslSocketFactory();
            hostnameVerifier = client.getHostnameVerifier();
            certificatePinner = client.getCertificatePinner();
        }
        return new Address(uriHost, Util.getEffectivePort(request.url()), client.getSocketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, client.getAuthenticator(), client.getProxy(), client.getProtocols(), client.getConnectionSpecs(), client.getProxySelector());
    }
}
