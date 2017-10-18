package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
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
import okio.a;
import okio.b;
import okio.g;
import okio.j;
import okio.k;
import okio.n;
import okio.v;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new ResponseBody() {
        public long contentLength() {
            return 0;
        }

        public MediaType contentType() {
            return null;
        }

        public a source() {
            return new k();
        }
    };
    public static final int MAX_FOLLOW_UPS = 20;
    private Address address;
    public final boolean bufferRequestBody;
    private b bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    private final boolean callerWritesRequestBody;
    final OkHttpClient client;
    private Connection connection;
    private final boolean forWebSocket;
    private Request networkRequest;
    private final Response priorResponse;
    private n requestBodyOut;
    private Route route;
    private RouteSelector routeSelector;
    long sentRequestMillis = -1;
    private CacheRequest storeRequest;
    private boolean transparentGzip;
    private Transport transport;
    private final Request userRequest;
    private Response userResponse;

    class NetworkInterceptorChain implements Chain {
        private int calls;
        private final int index;
        private final Request request;

        NetworkInterceptorChain(int i, Request request) {
            this.index = i;
            this.request = request;
        }

        public Connection connection() {
            return HttpEngine.this.connection;
        }

        public Response proceed(Request request) {
            Interceptor interceptor;
            this.calls++;
            if (this.index > 0) {
                interceptor = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index - 1);
                Address address = connection().getRoute().getAddress();
                if (!request.url().getHost().equals(address.getUriHost()) || Util.getEffectivePort(request.url()) != address.getUriPort()) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must retain the same host and port");
                } else if (this.calls > 1) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
                }
            }
            if (this.index >= HttpEngine.this.client.networkInterceptors().size()) {
                HttpEngine.this.transport.writeRequestHeaders(request);
                if (HttpEngine.this.permitsRequestBody() && request.body() != null) {
                    b AF = j.AF(HttpEngine.this.transport.createRequestBody(request, request.body().contentLength()));
                    request.body().writeTo(AF);
                    AF.close();
                }
                return HttpEngine.this.readNetworkResponse();
            }
            Object networkInterceptorChain = new NetworkInterceptorChain(this.index + 1, request);
            interceptor = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index);
            Response intercept = interceptor.intercept(networkInterceptorChain);
            if (networkInterceptorChain.calls == 1) {
                return intercept;
            }
            throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
        }

        public Request request() {
            return this.request;
        }
    }

    public HttpEngine(OkHttpClient okHttpClient, Request request, boolean z, boolean z2, boolean z3, Connection connection, RouteSelector routeSelector, RetryableSink retryableSink, Response response) {
        this.client = okHttpClient;
        this.userRequest = request;
        this.bufferRequestBody = z;
        this.callerWritesRequestBody = z2;
        this.forWebSocket = z3;
        this.connection = connection;
        this.routeSelector = routeSelector;
        this.requestBodyOut = retryableSink;
        this.priorResponse = response;
        if (connection == null) {
            this.route = null;
            return;
        }
        Internal.instance.setOwner(connection, this);
        this.route = connection.getRoute();
    }

    private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response) {
        if (cacheRequest == null) {
            return response;
        }
        n body = cacheRequest.body();
        if (body == null) {
            return response;
        }
        final a source = response.body().source();
        final b AF = j.AF(body);
        return response.newBuilder().body(new RealResponseBody(response.headers(), j.AE(new v() {
            boolean cacheRequestClosed;

            public void close() {
                if (!(this.cacheRequestClosed || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    this.cacheRequestClosed = true;
                    cacheRequest.abort();
                }
                source.close();
            }

            public long read(k kVar, long j) {
                try {
                    long read = source.read(kVar, j);
                    if (read == -1) {
                        if (!this.cacheRequestClosed) {
                            this.cacheRequestClosed = true;
                            AF.close();
                        }
                        return -1;
                    }
                    kVar.AN(AF.zK(), kVar.size() - read, read);
                    AF.Ai();
                    return read;
                } catch (IOException e) {
                    if (!this.cacheRequestClosed) {
                        this.cacheRequestClosed = true;
                        cacheRequest.abort();
                    }
                    throw e;
                }
            }

            public g timeout() {
                return source.timeout();
            }
        }))).build();
    }

    private static Headers combine(Headers headers, Headers headers2) {
        int i = 0;
        Builder builder = new Builder();
        int size = headers.size();
        int i2 = 0;
        while (i2 < size) {
            String name = headers.name(i2);
            String value = headers.value(i2);
            if ("Warning".equalsIgnoreCase(name)) {
                if (value.startsWith("1")) {
                    i2++;
                }
            }
            if (OkHeaders.isEndToEnd(name) && headers2.get(name) != null) {
                i2++;
            } else {
                builder.add(name, value);
                i2++;
            }
        }
        i2 = headers2.size();
        while (i < i2) {
            String name2 = headers2.name(i);
            if (!"Content-Length".equalsIgnoreCase(name2) && OkHeaders.isEndToEnd(name2)) {
                builder.add(name2, headers2.value(i));
            }
            i++;
        }
        return builder.build();
    }

    private void connect() {
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

    private void connectFailed(RouteSelector routeSelector, IOException iOException) {
        if (Internal.instance.recycleCount(this.connection) <= 0) {
            routeSelector.connectFailed(this.connection.getRoute(), iOException);
        }
    }

    private static Address createAddress(OkHttpClient okHttpClient, Request request) {
        CertificatePinner certificatePinner = null;
        String host = request.url().getHost();
        if (host == null || host.length() == 0) {
            throw new UnknownHostException(request.url().toString());
        }
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        if (request.isHttps()) {
            sslSocketFactory = okHttpClient.getSslSocketFactory();
            hostnameVerifier = okHttpClient.getHostnameVerifier();
            certificatePinner = okHttpClient.getCertificatePinner();
        } else {
            hostnameVerifier = null;
            sslSocketFactory = null;
        }
        return new Address(host, Util.getEffectivePort(request.url()), okHttpClient.getSocketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, okHttpClient.getAuthenticator(), okHttpClient.getProxy(), okHttpClient.getProtocols(), okHttpClient.getConnectionSpecs(), okHttpClient.getProxySelector());
    }

    private Connection createNextConnection() {
        Connection connection;
        ConnectionPool connectionPool = this.client.getConnectionPool();
        while (true) {
            connection = connectionPool.get(this.address);
            if (connection == null) {
                return new Connection(connectionPool, this.routeSelector.next());
            }
            if (!(this.networkRequest.method().equals("GET") || Internal.instance.isReadable(connection))) {
                connection.getSocket().close();
            }
        }
        return connection;
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int code = response.code();
        if (code < 100 || code >= 200) {
            if (!(code == 204 || code == 304)) {
                return true;
            }
        }
        return OkHeaders.contentLength(response) != -1 || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"));
    }

    public static String hostHeader(URL url) {
        return Util.getEffectivePort(url) == Util.getDefaultPort(url.getProtocol()) ? url.getHost() : url.getHost() + ":" + url.getPort();
    }

    private boolean isRecoverable(IOException iOException) {
        if (this.client.getRetryOnConnectionFailure() && !(iOException instanceof SSLPeerUnverifiedException)) {
            if (iOException instanceof SSLHandshakeException) {
                if (!(iOException.getCause() instanceof CertificateException)) {
                }
            }
            return ((iOException instanceof ProtocolException) || (iOException instanceof InterruptedIOException)) ? false : true;
        }
        return false;
    }

    private void maybeCache() {
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        if (internalCache == null) {
            return;
        }
        if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
            this.storeRequest = internalCache.put(stripBody(this.userResponse));
            return;
        }
        if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
            try {
                internalCache.remove(this.networkRequest);
            } catch (IOException e) {
            }
        }
    }

    private Request networkRequest(Request request) {
        Request.Builder newBuilder = request.newBuilder();
        if (request.header("Host") == null) {
            newBuilder.header("Host", hostHeader(request.url()));
        }
        if (this.connection == null || this.connection.getProtocol() != Protocol.HTTP_1_0) {
            if (request.header("Connection") == null) {
                newBuilder.header("Connection", "Keep-Alive");
            }
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            newBuilder.header("Accept-Encoding", "gzip");
        }
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            OkHeaders.addCookies(newBuilder, cookieHandler.get(request.uri(), OkHeaders.toMultimap(newBuilder.build().headers(), null)));
        }
        if (request.header("User-Agent") == null) {
            newBuilder.header("User-Agent", Version.userAgent());
        }
        return newBuilder.build();
    }

    private Connection nextConnection() {
        Connection createNextConnection = createNextConnection();
        Internal.instance.connectAndSetOwner(this.client, createNextConnection, this, this.networkRequest);
        return createNextConnection;
    }

    private Response readNetworkResponse() {
        this.transport.finishRequest();
        Response build = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        if (!this.forWebSocket) {
            build = build.newBuilder().body(this.transport.openResponseBody(build)).build();
        }
        Internal.instance.setProtocol(this.connection, build.protocol());
        return build;
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.squareup.okhttp.Response unzip(com.squareup.okhttp.Response r5) {
        /*
        r4 = this;
        r0 = r4.transparentGzip;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return r5;
    L_0x0005:
        r0 = "gzip";
        r1 = r4.userResponse;
        r2 = "Content-Encoding";
        r1 = r1.header(r2);
        r0 = r0.equalsIgnoreCase(r1);
        if (r0 == 0) goto L_0x0004;
    L_0x0017:
        r0 = r5.body();
        if (r0 == 0) goto L_0x005e;
    L_0x001d:
        r0 = new okio.c;
        r1 = r5.body();
        r1 = r1.source();
        r0.<init>(r1);
        r1 = r5.headers();
        r1 = r1.newBuilder();
        r2 = "Content-Encoding";
        r1 = r1.removeAll(r2);
        r2 = "Content-Length";
        r1 = r1.removeAll(r2);
        r1 = r1.build();
        r2 = r5.newBuilder();
        r2 = r2.headers(r1);
        r3 = new com.squareup.okhttp.internal.http.RealResponseBody;
        r0 = okio.j.AE(r0);
        r3.<init>(r1, r0);
        r0 = r2.body(r3);
        r0 = r0.build();
        return r0;
    L_0x005e:
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpEngine.unzip(com.squareup.okhttp.Response):com.squareup.okhttp.Response");
    }

    private static boolean validate(Response response, Response response2) {
        if (response2.code() == 304) {
            return true;
        }
        Date date = response.headers().getDate("Last-Modified");
        if (date != null) {
            Date date2 = response2.headers().getDate("Last-Modified");
            if (date2 != null) {
                if (!(date2.getTime() >= date.getTime())) {
                    return true;
                }
            }
        }
        return false;
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
                Connection connection = this.connection;
                this.connection = null;
                return connection;
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

    public void disconnect() {
        if (this.transport != null) {
            try {
                this.transport.disconnect(this);
            } catch (IOException e) {
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.squareup.okhttp.Request followUpRequest() {
        /*
        r4 = this;
        r3 = 0;
        r0 = r4.userResponse;
        if (r0 == 0) goto L_0x001b;
    L_0x0005:
        r0 = r4.getRoute();
        if (r0 != 0) goto L_0x0021;
    L_0x000b:
        r0 = r4.client;
        r0 = r0.getProxy();
    L_0x0011:
        r1 = r4.userResponse;
        r1 = r1.code();
        switch(r1) {
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
        return r3;
    L_0x001b:
        r0 = new java.lang.IllegalStateException;
        r0.<init>();
        throw r0;
    L_0x0021:
        r0 = r4.getRoute();
        r0 = r0.getProxy();
        goto L_0x0011;
    L_0x002a:
        r1 = r0.type();
        r2 = java.net.Proxy.Type.HTTP;
        if (r1 != r2) goto L_0x003f;
    L_0x0032:
        r1 = r4.client;
        r1 = r1.getAuthenticator();
        r2 = r4.userResponse;
        r0 = com.squareup.okhttp.internal.http.OkHeaders.processAuthHeader(r1, r2, r0);
        return r0;
    L_0x003f:
        r0 = new java.net.ProtocolException;
        r1 = "Received HTTP_PROXY_AUTH (407) code while not using proxy";
        r0.<init>(r1);
        throw r0;
    L_0x0048:
        r0 = r4.userRequest;
        r0 = r0.method();
        r1 = "GET";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x00b7;
    L_0x0057:
        r0 = r4.client;
        r0 = r0.getFollowRedirects();
        if (r0 == 0) goto L_0x00c7;
    L_0x005f:
        r0 = r4.userResponse;
        r1 = "Location";
        r0 = r0.header(r1);
        if (r0 == 0) goto L_0x00c8;
    L_0x006a:
        r1 = new java.net.URL;
        r2 = r4.userRequest;
        r2 = r2.url();
        r1.<init>(r2, r0);
        r0 = r1.getProtocol();
        r2 = "https";
        r0 = r0.equals(r2);
        if (r0 == 0) goto L_0x00c9;
    L_0x0082:
        r0 = r1.getProtocol();
        r2 = r4.userRequest;
        r2 = r2.url();
        r2 = r2.getProtocol();
        r0 = r0.equals(r2);
        if (r0 == 0) goto L_0x00d7;
    L_0x0096:
        r0 = r4.userRequest;
        r0 = r0.newBuilder();
        r2 = r4.userRequest;
        r2 = r2.method();
        r2 = com.squareup.okhttp.internal.http.HttpMethod.permitsRequestBody(r2);
        if (r2 != 0) goto L_0x00e0;
    L_0x00a8:
        r2 = r4.sameConnection(r1);
        if (r2 == 0) goto L_0x00f9;
    L_0x00ae:
        r0 = r0.url(r1);
        r0 = r0.build();
        return r0;
    L_0x00b7:
        r0 = r4.userRequest;
        r0 = r0.method();
        r1 = "HEAD";
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x0057;
    L_0x00c6:
        return r3;
    L_0x00c7:
        return r3;
    L_0x00c8:
        return r3;
    L_0x00c9:
        r0 = r1.getProtocol();
        r2 = "http";
        r0 = r0.equals(r2);
        if (r0 != 0) goto L_0x0082;
    L_0x00d6:
        return r3;
    L_0x00d7:
        r0 = r4.client;
        r0 = r0.getFollowSslRedirects();
        if (r0 != 0) goto L_0x0096;
    L_0x00df:
        return r3;
    L_0x00e0:
        r2 = "GET";
        r0.method(r2, r3);
        r2 = "Transfer-Encoding";
        r0.removeHeader(r2);
        r2 = "Content-Length";
        r0.removeHeader(r2);
        r2 = "Content-Type";
        r0.removeHeader(r2);
        goto L_0x00a8;
    L_0x00f9:
        r2 = "Authorization";
        r0.removeHeader(r2);
        goto L_0x00ae;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpEngine.followUpRequest():com.squareup.okhttp.Request");
    }

    public b getBufferedRequestBody() {
        b bVar = null;
        b bVar2 = this.bufferedRequestBody;
        if (bVar2 != null) {
            return bVar2;
        }
        n requestBody = getRequestBody();
        if (requestBody != null) {
            bVar = j.AF(requestBody);
            this.bufferedRequestBody = bVar;
        }
        return bVar;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public n getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public Response getResponse() {
        if (this.userResponse != null) {
            return this.userResponse;
        }
        throw new IllegalStateException();
    }

    public Route getRoute() {
        return this.route;
    }

    public boolean hasResponse() {
        return this.userResponse != null;
    }

    boolean permitsRequestBody() {
        return HttpMethod.permitsRequestBody(this.userRequest.method());
    }

    public void readResponse() {
        int i = 0;
        if (this.userResponse != null) {
            return;
        }
        if (this.networkRequest == null && this.cacheResponse == null) {
            throw new IllegalStateException("call sendRequest() first!");
        } else if (this.networkRequest != null) {
            Response readNetworkResponse;
            if (this.forWebSocket) {
                this.transport.writeRequestHeaders(this.networkRequest);
                readNetworkResponse = readNetworkResponse();
            } else if (this.callerWritesRequestBody) {
                if (this.bufferedRequestBody != null) {
                    if (this.bufferedRequestBody.zK().size() <= 0) {
                        i = 1;
                    }
                    if (i == 0) {
                        this.bufferedRequestBody.Aj();
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
                readNetworkResponse = readNetworkResponse();
            } else {
                readNetworkResponse = new NetworkInterceptorChain(0, this.networkRequest).proceed(this.networkRequest);
            }
            receiveHeaders(readNetworkResponse.headers());
            if (this.cacheResponse != null) {
                if (validate(this.cacheResponse, readNetworkResponse)) {
                    this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), readNetworkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(readNetworkResponse)).build();
                    readNetworkResponse.body().close();
                    releaseConnection();
                    InternalCache internalCache = Internal.instance.internalCache(this.client);
                    internalCache.trackConditionalCacheHit();
                    internalCache.update(this.cacheResponse, stripBody(this.userResponse));
                    this.userResponse = unzip(this.userResponse);
                    return;
                }
                Util.closeQuietly(this.cacheResponse.body());
            }
            this.userResponse = readNetworkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(readNetworkResponse)).build();
            if (hasBody(this.userResponse)) {
                maybeCache();
                this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
            }
        }
    }

    public void receiveHeaders(Headers headers) {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    public HttpEngine recover(IOException iOException) {
        return recover(iOException, this.requestBodyOut);
    }

    public HttpEngine recover(IOException iOException, n nVar) {
        Object obj = null;
        if (!(this.routeSelector == null || this.connection == null)) {
            connectFailed(this.routeSelector, iOException);
        }
        if (nVar == null || (nVar instanceof RetryableSink)) {
            obj = 1;
        }
        if (this.routeSelector != null || this.connection != null) {
            if (this.routeSelector == null || this.routeSelector.hasNext()) {
                if (isRecoverable(iOException) && r0 != null) {
                    return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), this.routeSelector, (RetryableSink) nVar, this.priorResponse);
                }
            }
        }
        return null;
    }

    public void releaseConnection() {
        if (!(this.transport == null || this.connection == null)) {
            this.transport.releaseConnectionOnIdle();
        }
        this.connection = null;
    }

    public boolean sameConnection(URL url) {
        URL url2 = this.userRequest.url();
        return url2.getHost().equals(url.getHost()) && Util.getEffectivePort(url2) == Util.getEffectivePort(url) && url2.getProtocol().equals(url.getProtocol());
    }

    public void sendRequest() {
        if (this.cacheStrategy != null) {
            return;
        }
        if (this.transport == null) {
            Request networkRequest = networkRequest(this.userRequest);
            InternalCache internalCache = Internal.instance.internalCache(this.client);
            Response response = internalCache == null ? null : internalCache.get(networkRequest);
            this.cacheStrategy = new Factory(System.currentTimeMillis(), networkRequest, response).get();
            this.networkRequest = this.cacheStrategy.networkRequest;
            this.cacheResponse = this.cacheStrategy.cacheResponse;
            if (internalCache != null) {
                internalCache.trackResponse(this.cacheStrategy);
            }
            if (response != null && this.cacheResponse == null) {
                Util.closeQuietly(response.body());
            }
            if (this.networkRequest == null) {
                if (this.connection != null) {
                    Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
                    this.connection = null;
                }
                if (this.cacheResponse == null) {
                    this.userResponse = new Response.Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
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
                    long contentLength = OkHeaders.contentLength(networkRequest);
                    if (this.bufferRequestBody) {
                        if ((contentLength <= 2147483647L ? 1 : null) == null) {
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

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }
}
