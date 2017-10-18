package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address {
    final Authenticator authenticator;
    final CertificatePinner certificatePinner;
    final List connectionSpecs;
    final HostnameVerifier hostnameVerifier;
    final List protocols;
    final Proxy proxy;
    final ProxySelector proxySelector;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final String uriHost;
    final int uriPort;

    public Address(String str, int i, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, CertificatePinner certificatePinner, Authenticator authenticator, Proxy proxy, List list, List list2, ProxySelector proxySelector) {
        if (str == null) {
            throw new NullPointerException("uriHost == null");
        } else if (i <= 0) {
            throw new IllegalArgumentException("uriPort <= 0: " + i);
        } else if (authenticator == null) {
            throw new IllegalArgumentException("authenticator == null");
        } else if (list == null) {
            throw new IllegalArgumentException("protocols == null");
        } else if (proxySelector != null) {
            this.proxy = proxy;
            this.uriHost = str;
            this.uriPort = i;
            this.socketFactory = socketFactory;
            this.sslSocketFactory = sSLSocketFactory;
            this.hostnameVerifier = hostnameVerifier;
            this.certificatePinner = certificatePinner;
            this.authenticator = authenticator;
            this.protocols = Util.immutableList(list);
            this.connectionSpecs = Util.immutableList(list2);
            this.proxySelector = proxySelector;
        } else {
            throw new IllegalArgumentException("proxySelector == null");
        }
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Address)) {
            return false;
        }
        Address address = (Address) obj;
        if (Util.equal(this.proxy, address.proxy) && this.uriHost.equals(address.uriHost) && this.uriPort == address.uriPort && Util.equal(this.sslSocketFactory, address.sslSocketFactory) && Util.equal(this.hostnameVerifier, address.hostnameVerifier) && Util.equal(this.certificatePinner, address.certificatePinner) && Util.equal(this.authenticator, address.authenticator) && Util.equal(this.protocols, address.protocols) && Util.equal(this.connectionSpecs, address.connectionSpecs) && Util.equal(this.proxySelector, address.proxySelector)) {
            z = true;
        }
        return z;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public List getConnectionSpecs() {
        return this.connectionSpecs;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List getProtocols() {
        return this.protocols;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public String getUriHost() {
        return this.uriHost;
    }

    public int getUriPort() {
        return this.uriPort;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.hostnameVerifier == null ? 0 : this.hostnameVerifier.hashCode()) + (((this.sslSocketFactory == null ? 0 : this.sslSocketFactory.hashCode()) + (((((((this.proxy == null ? 0 : this.proxy.hashCode()) + 527) * 31) + this.uriHost.hashCode()) * 31) + this.uriPort) * 31)) * 31)) * 31;
        if (this.certificatePinner != null) {
            i = this.certificatePinner.hashCode();
        }
        return ((((((((hashCode + i) * 31) + this.authenticator.hashCode()) * 31) + this.protocols.hashCode()) * 31) + this.connectionSpecs.hashCode()) * 31) + this.proxySelector.hashCode();
    }
}
