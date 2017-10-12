package com.squareup.okhttp;

import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.SpdyTransport;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import okio.Source;

public final class Connection {
    private boolean connected;
    private Handshake handshake;
    private HttpConnection httpConnection;
    private long idleStartTimeNs;
    private Object owner;
    private final ConnectionPool pool;
    private Protocol protocol;
    private int recycleCount;
    private final Route route;
    private Socket socket;
    private SpdyConnection spdyConnection;

    public Connection(ConnectionPool pool, Route route) {
        this.connected = false;
        this.protocol = Protocol.HTTP_1_1;
        this.pool = pool;
        this.route = route;
    }

    Object getOwner() {
        Object obj;
        synchronized (this.pool) {
            obj = this.owner;
        }
        return obj;
    }

    void setOwner(Object owner) {
        if (!isSpdy()) {
            synchronized (this.pool) {
                if (this.owner == null) {
                    this.owner = owner;
                } else {
                    throw new IllegalStateException("Connection already has an owner!");
                }
            }
        }
    }

    boolean clearOwner() {
        synchronized (this.pool) {
            if (this.owner != null) {
                this.owner = null;
                return true;
            }
            return false;
        }
    }

    void closeIfOwnedBy(Object owner) throws IOException {
        if (isSpdy()) {
            throw new IllegalStateException();
        }
        synchronized (this.pool) {
            if (this.owner == owner) {
                this.owner = null;
                this.socket.close();
                return;
            }
        }
    }

    void connect(int connectTimeout, int readTimeout, int writeTimeout, Request tunnelRequest) throws IOException {
        if (this.connected) {
            throw new IllegalStateException("already connected");
        }
        if (this.route.proxy.type() == Type.DIRECT || this.route.proxy.type() == Type.HTTP) {
            this.socket = this.route.address.socketFactory.createSocket();
        } else {
            this.socket = new Socket(this.route.proxy);
        }
        this.socket.setSoTimeout(readTimeout);
        Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, connectTimeout);
        if (this.route.address.sslSocketFactory == null) {
            this.httpConnection = new HttpConnection(this.pool, this, this.socket);
        } else {
            upgradeToTls(tunnelRequest, readTimeout, writeTimeout);
        }
        this.connected = true;
    }

    void connectAndSetOwner(OkHttpClient client, Object owner, Request request) throws IOException {
        setOwner(owner);
        if (!isConnected()) {
            connect(client.getConnectTimeout(), client.getReadTimeout(), client.getWriteTimeout(), tunnelRequest(request));
            if (isSpdy()) {
                client.getConnectionPool().share(this);
            }
            client.routeDatabase().connected(getRoute());
        }
        setTimeouts(client.getReadTimeout(), client.getWriteTimeout());
    }

    private Request tunnelRequest(Request request) throws IOException {
        if (!this.route.requiresTunnel()) {
            return null;
        }
        String authority;
        String host = request.url().getHost();
        int port = Util.getEffectivePort(request.url());
        if (port != Util.getDefaultPort("https")) {
            authority = host + ":" + port;
        } else {
            authority = host;
        }
        Builder result = new Builder().url(new URL("https", host, port, "/")).header("Host", authority).header("Proxy-Connection", "Keep-Alive");
        String userAgent = request.header("User-Agent");
        if (userAgent != null) {
            result.header("User-Agent", userAgent);
        }
        String proxyAuthorization = request.header("Proxy-Authorization");
        if (proxyAuthorization != null) {
            result.header("Proxy-Authorization", proxyAuthorization);
        }
        return result.build();
    }

    private void upgradeToTls(Request tunnelRequest, int readTimeout, int writeTimeout) throws IOException {
        Platform platform = Platform.get();
        if (tunnelRequest != null) {
            makeTunnel(tunnelRequest, readTimeout, writeTimeout);
        }
        this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
        SSLSocket sslSocket = this.socket;
        this.route.connectionSpec.apply(sslSocket, this.route);
        try {
            sslSocket.startHandshake();
            if (this.route.connectionSpec.supportsTlsExtensions()) {
                String maybeProtocol = platform.getSelectedProtocol(sslSocket);
                if (maybeProtocol != null) {
                    this.protocol = Protocol.get(maybeProtocol);
                }
            }
            platform.afterHandshake(sslSocket);
            this.handshake = Handshake.get(sslSocket.getSession());
            if (this.route.address.hostnameVerifier.verify(this.route.address.uriHost, sslSocket.getSession())) {
                this.route.address.certificatePinner.check(this.route.address.uriHost, this.handshake.peerCertificates());
                if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
                    sslSocket.setSoTimeout(0);
                    this.spdyConnection = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket).protocol(this.protocol).build();
                    this.spdyConnection.sendConnectionPreface();
                    return;
                }
                this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                return;
            }
            X509Certificate cert = sslSocket.getSession().getPeerCertificates()[0];
            throw new SSLPeerUnverifiedException("Hostname " + this.route.address.uriHost + " not verified:" + "\n    certificate: " + CertificatePinner.pin(cert) + "\n    DN: " + cert.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(cert));
        } catch (Throwable th) {
            platform.afterHandshake(sslSocket);
        }
    }

    boolean isConnected() {
        return this.connected;
    }

    public Route getRoute() {
        return this.route;
    }

    public Socket getSocket() {
        return this.socket;
    }

    boolean isAlive() {
        return (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) ? false : true;
    }

    boolean isReadable() {
        if (this.httpConnection == null) {
            return true;
        }
        return this.httpConnection.isReadable();
    }

    void resetIdleStartTime() {
        if (this.spdyConnection == null) {
            this.idleStartTimeNs = System.nanoTime();
            return;
        }
        throw new IllegalStateException("spdyConnection != null");
    }

    boolean isIdle() {
        return this.spdyConnection == null || this.spdyConnection.isIdle();
    }

    long getIdleStartTimeNs() {
        return this.spdyConnection != null ? this.spdyConnection.getIdleStartTimeNs() : this.idleStartTimeNs;
    }

    public Handshake getHandshake() {
        return this.handshake;
    }

    Transport newTransport(HttpEngine httpEngine) throws IOException {
        return this.spdyConnection == null ? new HttpTransport(httpEngine, this.httpConnection) : new SpdyTransport(httpEngine, this.spdyConnection);
    }

    boolean isSpdy() {
        return this.spdyConnection != null;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    void setProtocol(Protocol protocol) {
        if (protocol != null) {
            this.protocol = protocol;
            return;
        }
        throw new IllegalArgumentException("protocol == null");
    }

    void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) throws IOException {
        if (!this.connected) {
            throw new IllegalStateException("setTimeouts - not connected");
        } else if (this.httpConnection != null) {
            this.socket.setSoTimeout(readTimeoutMillis);
            this.httpConnection.setTimeouts(readTimeoutMillis, writeTimeoutMillis);
        }
    }

    void incrementRecycleCount() {
        this.recycleCount++;
    }

    int recycleCount() {
        return this.recycleCount;
    }

    private void makeTunnel(Request request, int readTimeout, int writeTimeout) throws IOException {
        HttpConnection tunnelConnection = new HttpConnection(this.pool, this, this.socket);
        tunnelConnection.setTimeouts(readTimeout, writeTimeout);
        URL url = request.url();
        String requestLine = "CONNECT " + url.getHost() + ":" + url.getPort() + " HTTP/1.1";
        do {
            tunnelConnection.writeRequest(request.headers(), requestLine);
            tunnelConnection.flush();
            Response response = tunnelConnection.readResponse().request(request).build();
            long contentLength = OkHeaders.contentLength(response);
            if (contentLength == -1) {
                contentLength = 0;
            }
            Source body = tunnelConnection.newFixedLengthSource(contentLength);
            Util.skipAll(body, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            body.close();
            switch (response.code()) {
                case 200:
                    if ((tunnelConnection.bufferSize() <= 0 ? 1 : null) == null) {
                        throw new IOException("TLS tunnel buffered too many bytes!");
                    }
                    return;
                case 407:
                    request = OkHeaders.processAuthHeader(this.route.address.authenticator, response, this.route.proxy);
                    break;
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + response.code());
            }
        } while (request != null);
        throw new IOException("Failed to authenticate with proxy");
    }

    public String toString() {
        String str;
        StringBuilder append = new StringBuilder().append("Connection{").append(this.route.address.uriHost).append(":").append(this.route.address.uriPort).append(", proxy=").append(this.route.proxy).append(" hostAddress=").append(this.route.inetSocketAddress.getAddress().getHostAddress()).append(" cipherSuite=");
        if (this.handshake == null) {
            str = "none";
        } else {
            str = this.handshake.cipherSuite();
        }
        return append.append(str).append(" protocol=").append(this.protocol).append('}').toString();
    }
}
