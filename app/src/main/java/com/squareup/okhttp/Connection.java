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
import okio.v;

public final class Connection {
    private boolean connected = false;
    private Handshake handshake;
    private HttpConnection httpConnection;
    private long idleStartTimeNs;
    private Object owner;
    private final ConnectionPool pool;
    private Protocol protocol = Protocol.HTTP_1_1;
    private int recycleCount;
    private final Route route;
    private Socket socket;
    private SpdyConnection spdyConnection;

    public Connection(ConnectionPool connectionPool, Route route) {
        this.pool = connectionPool;
        this.route = route;
    }

    private void makeTunnel(Request request, int i, int i2) {
        HttpConnection httpConnection = new HttpConnection(this.pool, this, this.socket);
        httpConnection.setTimeouts(i, i2);
        URL url = request.url();
        String str = "CONNECT " + url.getHost() + ":" + url.getPort() + " HTTP/1.1";
        do {
            httpConnection.writeRequest(request.headers(), str);
            httpConnection.flush();
            Response build = httpConnection.readResponse().request(request).build();
            long contentLength = OkHeaders.contentLength(build);
            if (contentLength == -1) {
                contentLength = 0;
            }
            v newFixedLengthSource = httpConnection.newFixedLengthSource(contentLength);
            Util.skipAll(newFixedLengthSource, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            newFixedLengthSource.close();
            switch (build.code()) {
                case 200:
                    if ((httpConnection.bufferSize() <= 0 ? 1 : null) == null) {
                        throw new IOException("TLS tunnel buffered too many bytes!");
                    }
                    return;
                case 407:
                    request = OkHeaders.processAuthHeader(this.route.address.authenticator, build, this.route.proxy);
                    break;
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + build.code());
            }
        } while (request != null);
        throw new IOException("Failed to authenticate with proxy");
    }

    private Request tunnelRequest(Request request) {
        if (!this.route.requiresTunnel()) {
            return null;
        }
        String host = request.url().getHost();
        int effectivePort = Util.getEffectivePort(request.url());
        Builder header = new Builder().url(new URL("https", host, effectivePort, "/")).header("Host", effectivePort != Util.getDefaultPort("https") ? host + ":" + effectivePort : host).header("Proxy-Connection", "Keep-Alive");
        host = request.header("User-Agent");
        if (host != null) {
            header.header("User-Agent", host);
        }
        host = request.header("Proxy-Authorization");
        if (host != null) {
            header.header("Proxy-Authorization", host);
        }
        return header.build();
    }

    private void upgradeToTls(Request request, int i, int i2) {
        Platform platform = Platform.get();
        if (request != null) {
            makeTunnel(request, i, i2);
        }
        this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
        SSLSocket sSLSocket = (SSLSocket) this.socket;
        this.route.connectionSpec.apply(sSLSocket, this.route);
        try {
            sSLSocket.startHandshake();
            if (this.route.connectionSpec.supportsTlsExtensions()) {
                String selectedProtocol = platform.getSelectedProtocol(sSLSocket);
                if (selectedProtocol != null) {
                    this.protocol = Protocol.get(selectedProtocol);
                }
            }
            platform.afterHandshake(sSLSocket);
            this.handshake = Handshake.get(sSLSocket.getSession());
            if (this.route.address.hostnameVerifier.verify(this.route.address.uriHost, sSLSocket.getSession())) {
                this.route.address.certificatePinner.check(this.route.address.uriHost, this.handshake.peerCertificates());
                if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
                    sSLSocket.setSoTimeout(0);
                    this.spdyConnection = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket).protocol(this.protocol).build();
                    this.spdyConnection.sendConnectionPreface();
                    return;
                }
                this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                return;
            }
            X509Certificate x509Certificate = (X509Certificate) sSLSocket.getSession().getPeerCertificates()[0];
            throw new SSLPeerUnverifiedException("Hostname " + this.route.address.uriHost + " not verified:" + "\n    certificate: " + CertificatePinner.pin(x509Certificate) + "\n    DN: " + x509Certificate.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(x509Certificate));
        } catch (Throwable th) {
            platform.afterHandshake(sSLSocket);
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

    void closeIfOwnedBy(Object obj) {
        if (isSpdy()) {
            throw new IllegalStateException();
        }
        synchronized (this.pool) {
            if (this.owner == obj) {
                this.owner = null;
                this.socket.close();
                return;
            }
        }
    }

    void connect(int i, int i2, int i3, Request request) {
        if (this.connected) {
            throw new IllegalStateException("already connected");
        }
        if (this.route.proxy.type() == Type.DIRECT || this.route.proxy.type() == Type.HTTP) {
            this.socket = this.route.address.socketFactory.createSocket();
        } else {
            this.socket = new Socket(this.route.proxy);
        }
        this.socket.setSoTimeout(i2);
        Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, i);
        if (this.route.address.sslSocketFactory == null) {
            this.httpConnection = new HttpConnection(this.pool, this, this.socket);
        } else {
            upgradeToTls(request, i2, i3);
        }
        this.connected = true;
    }

    void connectAndSetOwner(OkHttpClient okHttpClient, Object obj, Request request) {
        setOwner(obj);
        if (!isConnected()) {
            connect(okHttpClient.getConnectTimeout(), okHttpClient.getReadTimeout(), okHttpClient.getWriteTimeout(), tunnelRequest(request));
            if (isSpdy()) {
                okHttpClient.getConnectionPool().share(this);
            }
            okHttpClient.routeDatabase().connected(getRoute());
        }
        setTimeouts(okHttpClient.getReadTimeout(), okHttpClient.getWriteTimeout());
    }

    public Handshake getHandshake() {
        return this.handshake;
    }

    long getIdleStartTimeNs() {
        return this.spdyConnection != null ? this.spdyConnection.getIdleStartTimeNs() : this.idleStartTimeNs;
    }

    Object getOwner() {
        Object obj;
        synchronized (this.pool) {
            obj = this.owner;
        }
        return obj;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public Route getRoute() {
        return this.route;
    }

    public Socket getSocket() {
        return this.socket;
    }

    void incrementRecycleCount() {
        this.recycleCount++;
    }

    boolean isAlive() {
        return (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) ? false : true;
    }

    boolean isConnected() {
        return this.connected;
    }

    boolean isIdle() {
        return this.spdyConnection == null || this.spdyConnection.isIdle();
    }

    boolean isReadable() {
        return this.httpConnection == null ? true : this.httpConnection.isReadable();
    }

    boolean isSpdy() {
        return this.spdyConnection != null;
    }

    Transport newTransport(HttpEngine httpEngine) {
        return this.spdyConnection == null ? new HttpTransport(httpEngine, this.httpConnection) : new SpdyTransport(httpEngine, this.spdyConnection);
    }

    int recycleCount() {
        return this.recycleCount;
    }

    void resetIdleStartTime() {
        if (this.spdyConnection == null) {
            this.idleStartTimeNs = System.nanoTime();
            return;
        }
        throw new IllegalStateException("spdyConnection != null");
    }

    void setOwner(Object obj) {
        if (!isSpdy()) {
            synchronized (this.pool) {
                if (this.owner == null) {
                    this.owner = obj;
                } else {
                    throw new IllegalStateException("Connection already has an owner!");
                }
            }
        }
    }

    void setProtocol(Protocol protocol) {
        if (protocol != null) {
            this.protocol = protocol;
            return;
        }
        throw new IllegalArgumentException("protocol == null");
    }

    void setTimeouts(int i, int i2) {
        if (!this.connected) {
            throw new IllegalStateException("setTimeouts - not connected");
        } else if (this.httpConnection != null) {
            this.socket.setSoTimeout(i);
            this.httpConnection.setTimeouts(i, i2);
        }
    }

    public String toString() {
        return "Connection{" + this.route.address.uriHost + ":" + this.route.address.uriPort + ", proxy=" + this.route.proxy + " hostAddress=" + this.route.inetSocketAddress.getAddress().getHostAddress() + " cipherSuite=" + (this.handshake == null ? "none" : this.handshake.cipherSuite()) + " protocol=" + this.protocol + '}';
    }
}
