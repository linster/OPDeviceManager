package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;

import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLSocket;

public final class ConnectionSpec {
    public static final ConnectionSpec CLEARTEXT;
    public static final ConnectionSpec COMPATIBLE_TLS;
    public static final ConnectionSpec MODERN_TLS;
    private final String[] cipherSuites;
    final boolean supportsTlsExtensions;
    final boolean tls;
    private final String[] tlsVersions;

    public static final class Builder {
        private String[] cipherSuites;
        private boolean supportsTlsExtensions;
        private boolean tls;
        private String[] tlsVersions;

        Builder(boolean tls) {
            this.tls = tls;
        }

        public Builder(ConnectionSpec connectionSpec) {
            this.tls = connectionSpec.tls;
            this.cipherSuites = connectionSpec.cipherSuites;
            this.tlsVersions = connectionSpec.tlsVersions;
            this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions;
        }

        public Builder cipherSuites(CipherSuite... cipherSuites) {
            if (this.tls) {
                String[] strings = new String[cipherSuites.length];
                for (int i = 0; i < cipherSuites.length; i++) {
                    strings[i] = cipherSuites[i].javaName;
                }
                this.cipherSuites = strings;
                return this;
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder cipherSuites(String... cipherSuites) {
            if (this.tls) {
                if (cipherSuites != null) {
                    this.cipherSuites = (String[]) cipherSuites.clone();
                } else {
                    this.cipherSuites = null;
                }
                return this;
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder tlsVersions(TlsVersion... tlsVersions) {
            if (this.tls) {
                String[] strings = new String[tlsVersions.length];
                for (int i = 0; i < tlsVersions.length; i++) {
                    strings[i] = tlsVersions[i].javaName;
                }
                this.tlsVersions = strings;
                return this;
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public Builder tlsVersions(String... tlsVersions) {
            if (this.tls) {
                if (tlsVersions != null) {
                    this.tlsVersions = (String[]) tlsVersions.clone();
                } else {
                    this.tlsVersions = null;
                }
                return this;
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public Builder supportsTlsExtensions(boolean supportsTlsExtensions) {
            if (this.tls) {
                this.supportsTlsExtensions = supportsTlsExtensions;
                return this;
            }
            throw new IllegalStateException("no TLS extensions for cleartext connections");
        }

        public ConnectionSpec build() {
            return new ConnectionSpec();
        }
    }

    static {
        CipherSuite[] cipherSuiteArr = new CipherSuite[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
        TlsVersion[] tlsVersionArr = new TlsVersion[]{TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0};
        MODERN_TLS = new Builder(true);
        Object builder = new Builder(MODERN_TLS);
        new TlsVersion[1][0] = TlsVersion.TLS_1_0;
        COMPATIBLE_TLS = builder;
        CLEARTEXT = new Builder(false);
    }

    private ConnectionSpec(Builder builder) {
        this.tls = builder.tls;
        this.cipherSuites = builder.cipherSuites;
        this.tlsVersions = builder.tlsVersions;
        this.supportsTlsExtensions = builder.supportsTlsExtensions;
    }

    public boolean isTls() {
        return this.tls;
    }

    public List<CipherSuite> cipherSuites() {
        if (this.cipherSuites == null) {
            return null;
        }
        Object[] result = new CipherSuite[this.cipherSuites.length];
        for (int i = 0; i < this.cipherSuites.length; i++) {
            result[i] = CipherSuite.forJavaName(this.cipherSuites[i]);
        }
        return Util.immutableList(result);
    }

    public List<TlsVersion> tlsVersions() {
        Object[] result = new TlsVersion[this.tlsVersions.length];
        for (int i = 0; i < this.tlsVersions.length; i++) {
            result[i] = TlsVersion.forJavaName(this.tlsVersions[i]);
        }
        return Util.immutableList(result);
    }

    public boolean supportsTlsExtensions() {
        return this.supportsTlsExtensions;
    }

    void apply(SSLSocket sslSocket, Route route) {
        ConnectionSpec specToApply = supportedSpec(sslSocket);
        sslSocket.setEnabledProtocols(specToApply.tlsVersions);
        String[] cipherSuitesToEnable = specToApply.cipherSuites;
        if (route.shouldSendTlsFallbackIndicator) {
            String fallbackScsv = "TLS_FALLBACK_SCSV";
            if (Arrays.asList(sslSocket.getSupportedCipherSuites()).contains("TLS_FALLBACK_SCSV")) {
                String[] oldEnabledCipherSuites;
                if (cipherSuitesToEnable == null) {
                    oldEnabledCipherSuites = sslSocket.getEnabledCipherSuites();
                } else {
                    oldEnabledCipherSuites = cipherSuitesToEnable;
                }
                String[] newEnabledCipherSuites = new String[(oldEnabledCipherSuites.length + 1)];
                System.arraycopy(oldEnabledCipherSuites, 0, newEnabledCipherSuites, 0, oldEnabledCipherSuites.length);
                newEnabledCipherSuites[newEnabledCipherSuites.length - 1] = "TLS_FALLBACK_SCSV";
                cipherSuitesToEnable = newEnabledCipherSuites;
            }
        }
        if (cipherSuitesToEnable != null) {
            sslSocket.setEnabledCipherSuites(cipherSuitesToEnable);
        }
        Platform platform = Platform.get();
        if (specToApply.supportsTlsExtensions) {
            platform.configureTlsExtensions(sslSocket, route.address.uriHost, route.address.protocols);
        }
    }

    private ConnectionSpec supportedSpec(SSLSocket sslSocket) {
        String[] cipherSuitesToEnable = null;
        if (this.cipherSuites != null) {
            cipherSuitesToEnable = (String[]) Util.intersect(String.class, this.cipherSuites, sslSocket.getEnabledCipherSuites());
        }
        return new Builder(this).cipherSuites(cipherSuitesToEnable).tlsVersions((String[]) Util.intersect(String.class, this.tlsVersions, sslSocket.getEnabledProtocols())).build();
    }

    public boolean equals(Object other) {
        if (!(other instanceof ConnectionSpec)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        ConnectionSpec that = (ConnectionSpec) other;
        if (this.tls == that.tls) {
            return !this.tls || (Arrays.equals(this.cipherSuites, that.cipherSuites) && Arrays.equals(this.tlsVersions, that.tlsVersions) && this.supportsTlsExtensions == that.supportsTlsExtensions);
        } else {
            return false;
        }
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
        return hashCode + i;
    }

    public String toString() {
        if (!this.tls) {
            return "ConnectionSpec()";
        }
        String cipherSuitesString;
        List<CipherSuite> cipherSuites = cipherSuites();
        if (cipherSuites != null) {
            cipherSuitesString = cipherSuites.toString();
        } else {
            cipherSuitesString = "[use default]";
        }
        return "ConnectionSpec(cipherSuites=" + cipherSuitesString + ", tlsVersions=" + tlsVersions() + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")";
    }
}
