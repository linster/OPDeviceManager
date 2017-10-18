package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public final class Handshake {
    private final String cipherSuite;
    private final List localCertificates;
    private final List peerCertificates;

    private Handshake(String str, List list, List list2) {
        this.cipherSuite = str;
        this.peerCertificates = list;
        this.localCertificates = list2;
    }

    public static Handshake get(String str, List list, List list2) {
        if (str != null) {
            return new Handshake(str, Util.immutableList(list), Util.immutableList(list2));
        }
        throw new IllegalArgumentException("cipherSuite == null");
    }

    public static Handshake get(SSLSession sSLSession) {
        Object[] objArr = null;
        String cipherSuite = sSLSession.getCipherSuite();
        if (cipherSuite != null) {
            try {
                objArr = sSLSession.getPeerCertificates();
            } catch (SSLPeerUnverifiedException e) {
            }
            List emptyList = objArr == null ? Collections.emptyList() : Util.immutableList(objArr);
            Object[] localCertificates = sSLSession.getLocalCertificates();
            return new Handshake(cipherSuite, emptyList, localCertificates == null ? Collections.emptyList() : Util.immutableList(localCertificates));
        }
        throw new IllegalStateException("cipherSuite == null");
    }

    public String cipherSuite() {
        return this.cipherSuite;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Handshake)) {
            return false;
        }
        Handshake handshake = (Handshake) obj;
        if (this.cipherSuite.equals(handshake.cipherSuite) && this.peerCertificates.equals(handshake.peerCertificates) && this.localCertificates.equals(handshake.localCertificates)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((((this.cipherSuite.hashCode() + 527) * 31) + this.peerCertificates.hashCode()) * 31) + this.localCertificates.hashCode();
    }

    public List localCertificates() {
        return this.localCertificates;
    }

    public Principal localPrincipal() {
        return this.localCertificates.isEmpty() ? null : ((X509Certificate) this.localCertificates.get(0)).getSubjectX500Principal();
    }

    public List peerCertificates() {
        return this.peerCertificates;
    }

    public Principal peerPrincipal() {
        return this.peerCertificates.isEmpty() ? null : ((X509Certificate) this.peerCertificates.get(0)).getSubjectX500Principal();
    }
}
