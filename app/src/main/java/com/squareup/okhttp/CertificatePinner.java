package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder();
    private final Map hostnameToPins;

    public final class Builder {
        private final Map hostnameToPins = new LinkedHashMap();

        public Builder add(String str, String... strArr) {
            if (str != null) {
                List arrayList = new ArrayList();
                List list = (List) this.hostnameToPins.put(str, Collections.unmodifiableList(arrayList));
                if (list != null) {
                    arrayList.addAll(list);
                }
                int length = strArr.length;
                int i = 0;
                while (i < length) {
                    String str2 = strArr[i];
                    if (str2.startsWith("sha1/")) {
                        ByteString Ax = ByteString.Ax(str2.substring("sha1/".length()));
                        if (Ax != null) {
                            arrayList.add(Ax);
                            i++;
                        } else {
                            throw new IllegalArgumentException("pins must be base64: " + str2);
                        }
                    }
                    throw new IllegalArgumentException("pins must start with 'sha1/': " + str2);
                }
                return this;
            }
            throw new IllegalArgumentException("hostname == null");
        }

        public CertificatePinner build() {
            return new CertificatePinner();
        }
    }

    private CertificatePinner(Builder builder) {
        this.hostnameToPins = Util.immutableMap(builder.hostnameToPins);
    }

    public static String pin(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return "sha1/" + sha1((X509Certificate) certificate).Au();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    private static ByteString sha1(X509Certificate x509Certificate) {
        return Util.sha1(ByteString.Ar(x509Certificate.getPublicKey().getEncoded()));
    }

    public void check(String str, List list) {
        int i = 0;
        List list2 = (List) this.hostnameToPins.get(str);
        if (list2 != null) {
            int size = list.size();
            int i2 = 0;
            while (i2 < size) {
                if (!list2.contains(sha1((X509Certificate) list.get(i2)))) {
                    i2++;
                } else {
                    return;
                }
            }
            StringBuilder append = new StringBuilder().append("Certificate pinning failure!").append("\n  Peer certificate chain:");
            int size2 = list.size();
            for (i2 = 0; i2 < size2; i2++) {
                X509Certificate x509Certificate = (X509Certificate) list.get(i2);
                append.append("\n    ").append(pin(x509Certificate)).append(": ").append(x509Certificate.getSubjectDN().getName());
            }
            append.append("\n  Pinned certificates for ").append(str).append(":");
            i2 = list2.size();
            while (i < i2) {
                append.append("\n    sha1/").append(((ByteString) list2.get(i)).Au());
                i++;
            }
            throw new SSLPeerUnverifiedException(append.toString());
        }
    }

    public void check(String str, Certificate... certificateArr) {
        check(str, Arrays.asList(certificateArr));
    }
}
