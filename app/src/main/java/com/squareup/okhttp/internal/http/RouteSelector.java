package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;

public final class RouteSelector {
    private final Address address;
    private final OkHttpClient client;
    private List connectionSpecs = Collections.emptyList();
    private List inetSocketAddresses = Collections.emptyList();
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private ConnectionSpec lastSpec;
    private final Network network;
    private int nextInetSocketAddressIndex;
    private int nextProxyIndex;
    private int nextSpecIndex;
    private final List postponedRoutes = new ArrayList();
    private List proxies = Collections.emptyList();
    private final Request request;
    private final RouteDatabase routeDatabase;
    private final URI uri;

    private RouteSelector(Address address, URI uri, OkHttpClient okHttpClient, Request request) {
        this.address = address;
        this.uri = uri;
        this.client = okHttpClient;
        this.routeDatabase = Internal.instance.routeDatabase(okHttpClient);
        this.network = Internal.instance.network(okHttpClient);
        this.request = request;
        resetNextProxy(uri, address.getProxy());
    }

    public static RouteSelector get(Address address, Request request, OkHttpClient okHttpClient) {
        return new RouteSelector(address, request.uri(), okHttpClient, request);
    }

    static String getHostString(InetSocketAddress inetSocketAddress) {
        InetAddress address = inetSocketAddress.getAddress();
        return address != null ? address.getHostAddress() : inetSocketAddress.getHostName();
    }

    private boolean hasNextConnectionSpec() {
        return this.nextSpecIndex < this.connectionSpecs.size();
    }

    private boolean hasNextInetSocketAddress() {
        return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
    }

    private boolean hasNextPostponed() {
        return !this.postponedRoutes.isEmpty();
    }

    private boolean hasNextProxy() {
        return this.nextProxyIndex < this.proxies.size();
    }

    private ConnectionSpec nextConnectionSpec() {
        if (this.connectionSpecs.isEmpty()) {
            throw new UnknownServiceException("No route to " + (this.uri.getScheme() == null ? "//" : this.uri.getScheme() + "://") + this.address.getUriHost() + "; no connection specs");
        } else if (hasNextConnectionSpec()) {
            List list = this.connectionSpecs;
            int i = this.nextSpecIndex;
            this.nextSpecIndex = i + 1;
            return (ConnectionSpec) list.get(i);
        } else {
            throw new SocketException("No route to " + (this.uri.getScheme() == null ? "//" : this.uri.getScheme() + "://") + this.address.getUriHost() + "; exhausted connection specs: " + this.connectionSpecs);
        }
    }

    private InetSocketAddress nextInetSocketAddress() {
        if (hasNextInetSocketAddress()) {
            List list = this.inetSocketAddresses;
            int i = this.nextInetSocketAddressIndex;
            this.nextInetSocketAddressIndex = i + 1;
            InetSocketAddress inetSocketAddress = (InetSocketAddress) list.get(i);
            resetConnectionSpecs();
            return inetSocketAddress;
        }
        throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted inet socket addresses: " + this.inetSocketAddresses);
    }

    private Route nextPostponed() {
        return (Route) this.postponedRoutes.remove(0);
    }

    private Proxy nextProxy() {
        if (hasNextProxy()) {
            List list = this.proxies;
            int i = this.nextProxyIndex;
            this.nextProxyIndex = i + 1;
            Proxy proxy = (Proxy) list.get(i);
            resetNextInetSocketAddress(proxy);
            return proxy;
        }
        throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted proxy configurations: " + this.proxies);
    }

    private void resetConnectionSpecs() {
        this.connectionSpecs = new ArrayList();
        List connectionSpecs = this.address.getConnectionSpecs();
        int size = connectionSpecs.size();
        for (int i = 0; i < size; i++) {
            ConnectionSpec connectionSpec = (ConnectionSpec) connectionSpecs.get(i);
            if (this.request.isHttps() == connectionSpec.isTls()) {
                this.connectionSpecs.add(connectionSpec);
            }
        }
        this.nextSpecIndex = 0;
    }

    private void resetNextInetSocketAddress(Proxy proxy) {
        String uriHost;
        int effectivePort;
        this.inetSocketAddresses = new ArrayList();
        if (proxy.type() == Type.DIRECT || proxy.type() == Type.SOCKS) {
            uriHost = this.address.getUriHost();
            effectivePort = Util.getEffectivePort(this.uri);
        } else {
            SocketAddress address = proxy.address();
            if (address instanceof InetSocketAddress) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
                String hostString = getHostString(inetSocketAddress);
                int port = inetSocketAddress.getPort();
                uriHost = hostString;
                effectivePort = port;
            } else {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + address.getClass());
            }
        }
        if (effectivePort >= 1 && effectivePort <= 65535) {
            for (InetAddress inetSocketAddress2 : this.network.resolveInetAddresses(uriHost)) {
                this.inetSocketAddresses.add(new InetSocketAddress(inetSocketAddress2, effectivePort));
            }
            this.nextInetSocketAddressIndex = 0;
            return;
        }
        throw new SocketException("No route to " + uriHost + ":" + effectivePort + "; port is out of range");
    }

    private void resetNextProxy(URI uri, Proxy proxy) {
        if (proxy == null) {
            this.proxies = new ArrayList();
            Collection select = this.client.getProxySelector().select(uri);
            if (select != null) {
                this.proxies.addAll(select);
            }
            this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
            this.proxies.add(Proxy.NO_PROXY);
        } else {
            this.proxies = Collections.singletonList(proxy);
        }
        this.nextProxyIndex = 0;
    }

    private boolean shouldSendTlsFallbackIndicator(ConnectionSpec connectionSpec) {
        return connectionSpec != this.connectionSpecs.get(0) && connectionSpec.isTls();
    }

    public void connectFailed(Route route, IOException iOException) {
        if (!(route.getProxy().type() == Type.DIRECT || this.address.getProxySelector() == null)) {
            this.address.getProxySelector().connectFailed(this.uri, route.getProxy().address(), iOException);
        }
        this.routeDatabase.failed(route);
        if (!(iOException instanceof SSLHandshakeException) && !(iOException instanceof SSLProtocolException)) {
            while (this.nextSpecIndex < this.connectionSpecs.size()) {
                List list = this.connectionSpecs;
                int i = this.nextSpecIndex;
                this.nextSpecIndex = i + 1;
                ConnectionSpec connectionSpec = (ConnectionSpec) list.get(i);
                this.routeDatabase.failed(new Route(this.address, this.lastProxy, this.lastInetSocketAddress, connectionSpec, shouldSendTlsFallbackIndicator(connectionSpec)));
            }
        }
    }

    public boolean hasNext() {
        return hasNextConnectionSpec() || hasNextInetSocketAddress() || hasNextProxy() || hasNextPostponed();
    }

    public Route next() {
        if (!hasNextConnectionSpec()) {
            if (!hasNextInetSocketAddress()) {
                if (hasNextProxy()) {
                    this.lastProxy = nextProxy();
                } else if (hasNextPostponed()) {
                    return nextPostponed();
                } else {
                    throw new NoSuchElementException();
                }
            }
            this.lastInetSocketAddress = nextInetSocketAddress();
        }
        this.lastSpec = nextConnectionSpec();
        Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.lastSpec, shouldSendTlsFallbackIndicator(this.lastSpec));
        if (!this.routeDatabase.shouldPostpone(route)) {
            return route;
        }
        this.postponedRoutes.add(route);
        return next();
    }
}
