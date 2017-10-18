package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;

public final class ConnectionSpec {
    public static final ConnectionSpec CLEARTEXT = new Builder(false);
    public static final ConnectionSpec COMPATIBLE_TLS;
    public static final ConnectionSpec MODERN_TLS = new Builder(true);
    private final String[] cipherSuites;
    final boolean supportsTlsExtensions;
    final boolean tls;
    private final String[] tlsVersions;

    public final class Builder {
        private String[] cipherSuites;
        private boolean supportsTlsExtensions;
        private boolean tls;
        private String[] tlsVersions;

        public Builder(ConnectionSpec connectionSpec) {
            this.tls = connectionSpec.tls;
            this.cipherSuites = connectionSpec.cipherSuites;
            this.tlsVersions = connectionSpec.tlsVersions;
            this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions;
        }

        Builder(boolean z) {
            this.tls = z;
        }

        public ConnectionSpec build() {
            return new ConnectionSpec();
        }

        public Builder cipherSuites(CipherSuite... cipherSuiteArr) {
            if (this.tls) {
                String[] strArr = new String[cipherSuiteArr.length];
                for (int i = 0; i < cipherSuiteArr.length; i++) {
                    strArr[i] = cipherSuiteArr[i].javaName;
                }
                this.cipherSuites = strArr;
                return this;
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder cipherSuites(String... strArr) {
            if (this.tls) {
                if (strArr != null) {
                    this.cipherSuites = (String[]) strArr.clone();
                } else {
                    this.cipherSuites = null;
                }
                return this;
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder supportsTlsExtensions(boolean z) {
            if (this.tls) {
                this.supportsTlsExtensions = z;
                return this;
            }
            throw new IllegalStateException("no TLS extensions for cleartext connections");
        }

        public Builder tlsVersions(TlsVersion... tlsVersionArr) {
            if (this.tls) {
                String[] strArr = new String[tlsVersionArr.length];
                for (int i = 0; i < tlsVersionArr.length; i++) {
                    strArr[i] = tlsVersionArr[i].javaName;
                }
                this.tlsVersions = strArr;
                return this;
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public Builder tlsVersions(String... strArr) {
            if (this.tls) {
                if (strArr != null) {
                    this.tlsVersions = (String[]) strArr.clone();
                } else {
                    this.tlsVersions = null;
                }
                return this;
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }
    }

    static {
        CipherSuite[] cipherSuiteArr = new CipherSuite[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
        TlsVersion[] tlsVersionArr = new TlsVersion[]{TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0};
        Object builder = new Builder(MODERN_TLS);
        new TlsVersion[1][0] = TlsVersion.TLS_1_0;
        COMPATIBLE_TLS = builder;
    }

    private ConnectionSpec(Builder builder) {
        this.tls = builder.tls;
        this.cipherSuites = builder.cipherSuites;
        this.tlsVersions = builder.tlsVersions;
        this.supportsTlsExtensions = builder.supportsTlsExtensions;
    }

    private ConnectionSpec supportedSpec(SSLSocket sSLSocket) {
        String[] strArr;
        if (this.cipherSuites == null) {
            strArr = null;
        } else {
            strArr = (String[]) Util.intersect(String.class, this.cipherSuites, sSLSocket.getEnabledCipherSuites());
        }
        return new Builder(this).cipherSuites(strArr).tlsVersions((String[]) Util.intersect(String.class, this.tlsVersions, sSLSocket.getEnabledProtocols())).build();
    }

    void apply(SSLSocket sSLSocket, Route route) {
        ConnectionSpec supportedSpec = supportedSpec(sSLSocket);
        sSLSocket.setEnabledProtocols(supportedSpec.tlsVersions);
        Object obj = supportedSpec.cipherSuites;
        if (route.shouldSendTlsFallbackIndicator) {
            String str = "TLS_FALLBACK_SCSV";
            if (Arrays.asList(sSLSocket.getSupportedCipherSuites()).contains("TLS_FALLBACK_SCSV")) {
                if (obj == null) {
                    obj = sSLSocket.getEnabledCipherSuites();
                }
                Object obj2 = new String[(obj.length + 1)];
                System.arraycopy(obj, 0, obj2, 0, obj.length);
                obj2[obj2.length - 1] = "TLS_FALLBACK_SCSV";
                obj = obj2;
            }
        }
        if (r0 != null) {
            sSLSocket.setEnabledCipherSuites(r0);
        }
        Platform platform = Platform.get();
        if (supportedSpec.supportsTlsExtensions) {
            platform.configureTlsExtensions(sSLSocket, route.address.uriHost, route.address.protocols);
        }
    }

    public List cipherSuites() {
        if (this.cipherSuites == null) {
            return null;
        }
        Object[] objArr = new CipherSuite[this.cipherSuites.length];
        for (int i = 0; i < this.cipherSuites.length; i++) {
            objArr[i] = CipherSuite.forJavaName(this.cipherSuites[i]);
        }
        return Util.immutableList(objArr);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ConnectionSpec)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ConnectionSpec connectionSpec = (ConnectionSpec) obj;
        return this.tls == connectionSpec.tls ? !this.tls || (Arrays.equals(this.cipherSuites, connectionSpec.cipherSuites) && Arrays.equals(this.tlsVersions, connectionSpec.tlsVersions) && this.supportsTlsExtensions == connectionSpec.supportsTlsExtensions) : false;
    }

    public int hashCode() {
        int i = 0;
        if (!this.tls) {
            return 17;
        }
        int hashCode = (((Arrays.hashCode(this.cipherSuites) + 527) * 31) + Arrays.hashCode(this.tlsVersions)) * 31;
        if (!this.supportsTlsExtensions) {
            i = 1;
        }
        return i + hashCode;
    }

    public boolean isTls() {
        return this.tls;
    }

    public boolean supportsTlsExtensions() {
        return this.supportsTlsExtensions;
    }

    public List tlsVersions() {
        Object[] objArr = new TlsVersion[this.tlsVersions.length];
        for (int i = 0; i < this.tlsVersions.length; i++) {
            objArr[i] = TlsVersion.forJavaName(this.tlsVersions[i]);
        }
        return Util.immutableList(objArr);
    }

    public String toString() {
        if (!this.tls) {
            return "ConnectionSpec()";
        }
        List cipherSuites = cipherSuites();
        return "ConnectionSpec(cipherSuites=" + (cipherSuites != null ? cipherSuites.toString() : "[use default]") + ", tlsVersions=" + tlsVersions() + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")";
    }
}
