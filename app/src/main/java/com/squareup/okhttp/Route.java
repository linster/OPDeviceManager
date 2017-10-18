package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public final class Route {
    final Address address;
    final ConnectionSpec connectionSpec;
    final InetSocketAddress inetSocketAddress;
    final Proxy proxy;
    final boolean shouldSendTlsFallbackIndicator;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, ConnectionSpec connectionSpec) {
        this(address, proxy, inetSocketAddress, connectionSpec, false);
    }

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, ConnectionSpec connectionSpec, boolean z) {
        if (address == null) {
            throw new NullPointerException("address == null");
        } else if (proxy == null) {
            throw new NullPointerException("proxy == null");
        } else if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        } else if (connectionSpec != null) {
            this.address = address;
            this.proxy = proxy;
            this.inetSocketAddress = inetSocketAddress;
            this.connectionSpec = connectionSpec;
            this.shouldSendTlsFallbackIndicator = z;
        } else {
            throw new NullPointerException("connectionConfiguration == null");
        }
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Route)) {
            return false;
        }
        Route route = (Route) obj;
        if (this.address.equals(route.address) && this.proxy.equals(route.proxy) && this.inetSocketAddress.equals(route.inetSocketAddress) && this.connectionSpec.equals(route.connectionSpec) && this.shouldSendTlsFallbackIndicator == route.shouldSendTlsFallbackIndicator) {
            z = true;
        }
        return z;
    }

    public Address getAddress() {
        return this.address;
    }

    public ConnectionSpec getConnectionSpec() {
        return this.connectionSpec;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public boolean getShouldSendTlsFallbackIndicator() {
        return this.shouldSendTlsFallbackIndicator;
    }

    public InetSocketAddress getSocketAddress() {
        return this.inetSocketAddress;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((((((this.address.hashCode() + 527) * 31) + this.proxy.hashCode()) * 31) + this.inetSocketAddress.hashCode()) * 31) + this.connectionSpec.hashCode()) * 31;
        if (this.shouldSendTlsFallbackIndicator) {
            i = 1;
        }
        return i + hashCode;
    }

    public boolean requiresTunnel() {
        return this.address.sslSocketFactory != null && this.proxy.type() == Type.HTTP;
    }
}
