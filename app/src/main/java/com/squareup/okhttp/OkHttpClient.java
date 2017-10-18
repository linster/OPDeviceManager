package com.squareup.okhttp;

import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.AuthenticatorAdapter;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class OkHttpClient implements Cloneable {
    private static final List DEFAULT_CONNECTION_SPECS = Util.immutableList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT);
    private static final List DEFAULT_PROTOCOLS = Util.immutableList(Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1);
    private static SSLSocketFactory defaultSslSocketFactory;
    private Authenticator authenticator;
    private Cache cache;
    private CertificatePinner certificatePinner;
    private int connectTimeout;
    private ConnectionPool connectionPool;
    private List connectionSpecs;
    private CookieHandler cookieHandler;
    private Dispatcher dispatcher;
    private boolean followRedirects;
    private boolean followSslRedirects;
    private HostnameVerifier hostnameVerifier;
    private final List interceptors;
    private InternalCache internalCache;
    private Network network;
    private final List networkInterceptors;
    private List protocols;
    private Proxy proxy;
    private ProxySelector proxySelector;
    private int readTimeout;
    private boolean retryOnConnectionFailure;
    private final RouteDatabase routeDatabase;
    private SocketFactory socketFactory;
    private SSLSocketFactory sslSocketFactory;
    private int writeTimeout;

    static {
        Internal.instance = new Internal() {
            public void addLenient(Builder builder, String str) {
                builder.addLenient(str);
            }

            public Connection callEngineGetConnection(Call call) {
                return call.engine.getConnection();
            }

            public void callEngineReleaseConnection(Call call) {
                call.engine.releaseConnection();
            }

            public void callEnqueue(Call call, Callback callback, boolean z) {
                call.enqueue(callback, z);
            }

            public boolean clearOwner(Connection connection) {
                return connection.clearOwner();
            }

            public void closeIfOwnedBy(Connection connection, Object obj) {
                connection.closeIfOwnedBy(obj);
            }

            public void connectAndSetOwner(OkHttpClient okHttpClient, Connection connection, HttpEngine httpEngine, Request request) {
                connection.connectAndSetOwner(okHttpClient, httpEngine, request);
            }

            public void connectionSetOwner(Connection connection, Object obj) {
                connection.setOwner(obj);
            }

            public InternalCache internalCache(OkHttpClient okHttpClient) {
                return okHttpClient.internalCache();
            }

            public boolean isReadable(Connection connection) {
                return connection.isReadable();
            }

            public Network network(OkHttpClient okHttpClient) {
                return okHttpClient.network;
            }

            public Transport newTransport(Connection connection, HttpEngine httpEngine) {
                return connection.newTransport(httpEngine);
            }

            public void recycle(ConnectionPool connectionPool, Connection connection) {
                connectionPool.recycle(connection);
            }

            public int recycleCount(Connection connection) {
                return connection.recycleCount();
            }

            public RouteDatabase routeDatabase(OkHttpClient okHttpClient) {
                return okHttpClient.routeDatabase();
            }

            public void setCache(OkHttpClient okHttpClient, InternalCache internalCache) {
                okHttpClient.setInternalCache(internalCache);
            }

            public void setNetwork(OkHttpClient okHttpClient, Network network) {
                okHttpClient.network = network;
            }

            public void setOwner(Connection connection, HttpEngine httpEngine) {
                connection.setOwner(httpEngine);
            }

            public void setProtocol(Connection connection, Protocol protocol) {
                connection.setProtocol(protocol);
            }
        };
    }

    public OkHttpClient() {
        this.interceptors = new ArrayList();
        this.networkInterceptors = new ArrayList();
        this.followSslRedirects = true;
        this.followRedirects = true;
        this.retryOnConnectionFailure = true;
        this.routeDatabase = new RouteDatabase();
        this.dispatcher = new Dispatcher();
    }

    private OkHttpClient(OkHttpClient okHttpClient) {
        this.interceptors = new ArrayList();
        this.networkInterceptors = new ArrayList();
        this.followSslRedirects = true;
        this.followRedirects = true;
        this.retryOnConnectionFailure = true;
        this.routeDatabase = okHttpClient.routeDatabase;
        this.dispatcher = okHttpClient.dispatcher;
        this.proxy = okHttpClient.proxy;
        this.protocols = okHttpClient.protocols;
        this.connectionSpecs = okHttpClient.connectionSpecs;
        this.interceptors.addAll(okHttpClient.interceptors);
        this.networkInterceptors.addAll(okHttpClient.networkInterceptors);
        this.proxySelector = okHttpClient.proxySelector;
        this.cookieHandler = okHttpClient.cookieHandler;
        this.cache = okHttpClient.cache;
        this.internalCache = this.cache == null ? okHttpClient.internalCache : this.cache.internalCache;
        this.socketFactory = okHttpClient.socketFactory;
        this.sslSocketFactory = okHttpClient.sslSocketFactory;
        this.hostnameVerifier = okHttpClient.hostnameVerifier;
        this.certificatePinner = okHttpClient.certificatePinner;
        this.authenticator = okHttpClient.authenticator;
        this.connectionPool = okHttpClient.connectionPool;
        this.network = okHttpClient.network;
        this.followSslRedirects = okHttpClient.followSslRedirects;
        this.followRedirects = okHttpClient.followRedirects;
        this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure;
        this.connectTimeout = okHttpClient.connectTimeout;
        this.readTimeout = okHttpClient.readTimeout;
        this.writeTimeout = okHttpClient.writeTimeout;
    }

    private synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
        if (defaultSslSocketFactory == null) {
            try {
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init(null, null, null);
                defaultSslSocketFactory = instance.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new AssertionError();
            }
        }
        return defaultSslSocketFactory;
    }

    public OkHttpClient cancel(Object obj) {
        getDispatcher().cancel(obj);
        return this;
    }

    public final OkHttpClient clone() {
        try {
            return (OkHttpClient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    final OkHttpClient copyWithDefaults() {
        OkHttpClient okHttpClient = new OkHttpClient(this);
        if (okHttpClient.proxySelector == null) {
            okHttpClient.proxySelector = ProxySelector.getDefault();
        }
        if (okHttpClient.cookieHandler == null) {
            okHttpClient.cookieHandler = CookieHandler.getDefault();
        }
        if (okHttpClient.socketFactory == null) {
            okHttpClient.socketFactory = SocketFactory.getDefault();
        }
        if (okHttpClient.sslSocketFactory == null) {
            okHttpClient.sslSocketFactory = getDefaultSSLSocketFactory();
        }
        if (okHttpClient.hostnameVerifier == null) {
            okHttpClient.hostnameVerifier = OkHostnameVerifier.INSTANCE;
        }
        if (okHttpClient.certificatePinner == null) {
            okHttpClient.certificatePinner = CertificatePinner.DEFAULT;
        }
        if (okHttpClient.authenticator == null) {
            okHttpClient.authenticator = AuthenticatorAdapter.INSTANCE;
        }
        if (okHttpClient.connectionPool == null) {
            okHttpClient.connectionPool = ConnectionPool.getDefault();
        }
        if (okHttpClient.protocols == null) {
            okHttpClient.protocols = DEFAULT_PROTOCOLS;
        }
        if (okHttpClient.connectionSpecs == null) {
            okHttpClient.connectionSpecs = DEFAULT_CONNECTION_SPECS;
        }
        if (okHttpClient.network == null) {
            okHttpClient.network = Network.DEFAULT;
        }
        return okHttpClient;
    }

    public final Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public final Cache getCache() {
        return this.cache;
    }

    public final CertificatePinner getCertificatePinner() {
        return this.certificatePinner;
    }

    public final int getConnectTimeout() {
        return this.connectTimeout;
    }

    public final ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public final List getConnectionSpecs() {
        return this.connectionSpecs;
    }

    public final CookieHandler getCookieHandler() {
        return this.cookieHandler;
    }

    public final Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    public final boolean getFollowRedirects() {
        return this.followRedirects;
    }

    public final boolean getFollowSslRedirects() {
        return this.followSslRedirects;
    }

    public final HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public final List getProtocols() {
        return this.protocols;
    }

    public final Proxy getProxy() {
        return this.proxy;
    }

    public final ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public final int getReadTimeout() {
        return this.readTimeout;
    }

    public final boolean getRetryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    public final SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public final SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public final int getWriteTimeout() {
        return this.writeTimeout;
    }

    public List interceptors() {
        return this.interceptors;
    }

    final InternalCache internalCache() {
        return this.internalCache;
    }

    public List networkInterceptors() {
        return this.networkInterceptors;
    }

    public Call newCall(Request request) {
        return new Call(this, request);
    }

    final RouteDatabase routeDatabase() {
        return this.routeDatabase;
    }

    public final OkHttpClient setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public final OkHttpClient setCache(Cache cache) {
        this.cache = cache;
        this.internalCache = null;
        return this;
    }

    public final OkHttpClient setCertificatePinner(CertificatePinner certificatePinner) {
        this.certificatePinner = certificatePinner;
        return this;
    }

    public final void setConnectTimeout(long j, TimeUnit timeUnit) {
        Object obj = 1;
        if ((j >= 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (timeUnit != null) {
            long toMillis = timeUnit.toMillis(j);
            if ((toMillis <= 2147483647L ? 1 : null) == null) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            if (toMillis == 0) {
                if (j > 0) {
                    obj = null;
                }
                if (obj == null) {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
            this.connectTimeout = (int) toMillis;
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public final OkHttpClient setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public final OkHttpClient setConnectionSpecs(List list) {
        this.connectionSpecs = Util.immutableList(list);
        return this;
    }

    public final OkHttpClient setCookieHandler(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
        return this;
    }

    public final OkHttpClient setDispatcher(Dispatcher dispatcher) {
        if (dispatcher != null) {
            this.dispatcher = dispatcher;
            return this;
        }
        throw new IllegalArgumentException("dispatcher == null");
    }

    public final void setFollowRedirects(boolean z) {
        this.followRedirects = z;
    }

    public final OkHttpClient setFollowSslRedirects(boolean z) {
        this.followSslRedirects = z;
        return this;
    }

    public final OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    final void setInternalCache(InternalCache internalCache) {
        this.internalCache = internalCache;
        this.cache = null;
    }

    public final OkHttpClient setProtocols(List list) {
        List immutableList = Util.immutableList(list);
        if (!immutableList.contains(Protocol.HTTP_1_1)) {
            throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + immutableList);
        } else if (immutableList.contains(Protocol.HTTP_1_0)) {
            throw new IllegalArgumentException("protocols must not contain http/1.0: " + immutableList);
        } else if (immutableList.contains(null)) {
            throw new IllegalArgumentException("protocols must not contain null");
        } else {
            this.protocols = Util.immutableList(immutableList);
            return this;
        }
    }

    public final OkHttpClient setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public final OkHttpClient setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        return this;
    }

    public final void setReadTimeout(long j, TimeUnit timeUnit) {
        Object obj = 1;
        if ((j >= 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (timeUnit != null) {
            long toMillis = timeUnit.toMillis(j);
            if ((toMillis <= 2147483647L ? 1 : null) == null) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            if (toMillis == 0) {
                if (j > 0) {
                    obj = null;
                }
                if (obj == null) {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
            this.readTimeout = (int) toMillis;
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public final void setRetryOnConnectionFailure(boolean z) {
        this.retryOnConnectionFailure = z;
    }

    public final OkHttpClient setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
        return this;
    }

    public final OkHttpClient setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
        return this;
    }

    public final void setWriteTimeout(long j, TimeUnit timeUnit) {
        Object obj = 1;
        if ((j >= 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (timeUnit != null) {
            long toMillis = timeUnit.toMillis(j);
            if ((toMillis <= 2147483647L ? 1 : null) == null) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            if (toMillis == 0) {
                if (j > 0) {
                    obj = null;
                }
                if (obj == null) {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
            this.writeTimeout = (int) toMillis;
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }
}
