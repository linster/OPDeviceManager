package com.squareup.okhttp.internal.tls;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public final class OkHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE;
    private static final Pattern VERIFY_AS_IP_ADDRESS;

    static {
        INSTANCE = new OkHostnameVerifier();
        VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    }

    private OkHostnameVerifier() {
    }

    public boolean verify(String host, SSLSession session) {
        try {
            return verify(host, (X509Certificate) session.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }

    public boolean verify(String host, X509Certificate certificate) {
        if (verifyAsIpAddress(host)) {
            return verifyIpAddress(host, certificate);
        }
        return verifyHostName(host, certificate);
    }

    static boolean verifyAsIpAddress(String host) {
        return VERIFY_AS_IP_ADDRESS.matcher(host).matches();
    }

    private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
        List<String> altNames = getSubjectAltNames(certificate, ALT_IPA_NAME);
        int size = altNames.size();
        for (int i = 0; i < size; i++) {
            if (ipAddress.equalsIgnoreCase((String) altNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyHostName(String hostName, X509Certificate certificate) {
        hostName = hostName.toLowerCase(Locale.US);
        boolean hasDns = false;
        List<String> altNames = getSubjectAltNames(certificate, ALT_DNS_NAME);
        int size = altNames.size();
        for (int i = 0; i < size; i++) {
            hasDns = true;
            if (verifyHostName(hostName, (String) altNames.get(i))) {
                return true;
            }
        }
        if (!hasDns) {
            String cn = new DistinguishedNameParser(certificate.getSubjectX500Principal()).findMostSpecific("cn");
            if (cn != null) {
                return verifyHostName(hostName, cn);
            }
        }
        return false;
    }

    public static List<String> allSubjectAltNames(X509Certificate certificate) {
        List<String> altIpaNames = getSubjectAltNames(certificate, ALT_IPA_NAME);
        List<String> altDnsNames = getSubjectAltNames(certificate, ALT_DNS_NAME);
        List<String> result = new ArrayList(altIpaNames.size() + altDnsNames.size());
        result.addAll(altIpaNames);
        result.addAll(altDnsNames);
        return result;
    }

    private static List<String> getSubjectAltNames(X509Certificate certificate, int type) {
        List<String> result = new ArrayList();
        try {
            Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) {
                return Collections.emptyList();
            }
            Iterator it = subjectAltNames.iterator();
            while (it.hasNext()) {
                List<?> entry = (List) it.next();
                if (entry != null && entry.size() >= ALT_DNS_NAME) {
                    Integer altNameType = (Integer) entry.get(0);
                    if (altNameType != null && altNameType.intValue() == type) {
                        String altName = (String) entry.get(1);
                        if (altName != null) {
                            result.add(altName);
                        }
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean verifyHostName(java.lang.String r9, java.lang.String r10) {
        /*
        r8 = this;
        r7 = -1;
        r6 = 46;
        r5 = 1;
        r4 = 0;
        if (r9 != 0) goto L_0x0008;
    L_0x0007:
        return r4;
    L_0x0008:
        r2 = r9.length();
        if (r2 == 0) goto L_0x0007;
    L_0x000e:
        r2 = ".";
        r2 = r9.startsWith(r2);
        if (r2 != 0) goto L_0x0007;
    L_0x0017:
        r2 = "..";
        r2 = r9.endsWith(r2);
        if (r2 != 0) goto L_0x0007;
    L_0x0020:
        if (r10 != 0) goto L_0x0023;
    L_0x0022:
        return r4;
    L_0x0023:
        r2 = r10.length();
        if (r2 == 0) goto L_0x0022;
    L_0x0029:
        r2 = ".";
        r2 = r10.startsWith(r2);
        if (r2 != 0) goto L_0x0022;
    L_0x0032:
        r2 = "..";
        r2 = r10.endsWith(r2);
        if (r2 != 0) goto L_0x0022;
    L_0x003b:
        r2 = ".";
        r2 = r9.endsWith(r2);
        if (r2 == 0) goto L_0x0066;
    L_0x0044:
        r2 = ".";
        r2 = r10.endsWith(r2);
        if (r2 == 0) goto L_0x0078;
    L_0x004d:
        r2 = java.util.Locale.US;
        r10 = r10.toLowerCase(r2);
        r2 = "*";
        r2 = r10.contains(r2);
        if (r2 == 0) goto L_0x008a;
    L_0x005c:
        r2 = "*.";
        r2 = r10.startsWith(r2);
        if (r2 != 0) goto L_0x008f;
    L_0x0065:
        return r4;
    L_0x0066:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r2 = r2.append(r9);
        r2 = r2.append(r6);
        r9 = r2.toString();
        goto L_0x0044;
    L_0x0078:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r2 = r2.append(r10);
        r2 = r2.append(r6);
        r10 = r2.toString();
        goto L_0x004d;
    L_0x008a:
        r2 = r9.equals(r10);
        return r2;
    L_0x008f:
        r2 = 42;
        r2 = r10.indexOf(r2, r5);
        if (r2 != r7) goto L_0x0065;
    L_0x0097:
        r2 = r9.length();
        r3 = r10.length();
        if (r2 < r3) goto L_0x00c1;
    L_0x00a1:
        r2 = "*.";
        r2 = r2.equals(r10);
        if (r2 != 0) goto L_0x00c2;
    L_0x00aa:
        r0 = r10.substring(r5);
        r2 = r9.endsWith(r0);
        if (r2 == 0) goto L_0x00c3;
    L_0x00b4:
        r2 = r9.length();
        r3 = r0.length();
        r1 = r2 - r3;
        if (r1 > 0) goto L_0x00c4;
    L_0x00c0:
        return r5;
    L_0x00c1:
        return r4;
    L_0x00c2:
        return r4;
    L_0x00c3:
        return r4;
    L_0x00c4:
        r2 = r1 + -1;
        r2 = r9.lastIndexOf(r6, r2);
        if (r2 == r7) goto L_0x00c0;
    L_0x00cc:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.tls.OkHostnameVerifier.verifyHostName(java.lang.String, java.lang.String):boolean");
    }
}
