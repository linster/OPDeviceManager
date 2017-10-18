package com.squareup.okhttp.internal.tls;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public final class OkHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    private OkHostnameVerifier() {
    }

    public static List allSubjectAltNames(X509Certificate x509Certificate) {
        Collection subjectAltNames = getSubjectAltNames(x509Certificate, ALT_IPA_NAME);
        Collection subjectAltNames2 = getSubjectAltNames(x509Certificate, ALT_DNS_NAME);
        List arrayList = new ArrayList(subjectAltNames.size() + subjectAltNames2.size());
        arrayList.addAll(subjectAltNames);
        arrayList.addAll(subjectAltNames2);
        return arrayList;
    }

    private static List getSubjectAltNames(X509Certificate x509Certificate, int i) {
        List arrayList = new ArrayList();
        try {
            Collection<List> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return Collections.emptyList();
            }
            for (List list : subjectAlternativeNames) {
                if (list != null && list.size() >= ALT_DNS_NAME) {
                    Integer num = (Integer) list.get(0);
                    if (num != null && num.intValue() == i) {
                        String str = (String) list.get(1);
                        if (str != null) {
                            arrayList.add(str);
                        }
                    }
                }
            }
            return arrayList;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    static boolean verifyAsIpAddress(String str) {
        return VERIFY_AS_IP_ADDRESS.matcher(str).matches();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean verifyHostName(java.lang.String r8, java.lang.String r9) {
        /*
        r7 = this;
        r6 = -1;
        r5 = 46;
        r4 = 1;
        r3 = 0;
        if (r8 != 0) goto L_0x0008;
    L_0x0007:
        return r3;
    L_0x0008:
        r0 = r8.length();
        if (r0 == 0) goto L_0x0007;
    L_0x000e:
        r0 = ".";
        r0 = r8.startsWith(r0);
        if (r0 != 0) goto L_0x0007;
    L_0x0017:
        r0 = "..";
        r0 = r8.endsWith(r0);
        if (r0 != 0) goto L_0x0007;
    L_0x0020:
        if (r9 != 0) goto L_0x0023;
    L_0x0022:
        return r3;
    L_0x0023:
        r0 = r9.length();
        if (r0 == 0) goto L_0x0022;
    L_0x0029:
        r0 = ".";
        r0 = r9.startsWith(r0);
        if (r0 != 0) goto L_0x0022;
    L_0x0032:
        r0 = "..";
        r0 = r9.endsWith(r0);
        if (r0 != 0) goto L_0x0022;
    L_0x003b:
        r0 = ".";
        r0 = r8.endsWith(r0);
        if (r0 == 0) goto L_0x0066;
    L_0x0044:
        r0 = ".";
        r0 = r9.endsWith(r0);
        if (r0 == 0) goto L_0x0078;
    L_0x004d:
        r0 = java.util.Locale.US;
        r0 = r9.toLowerCase(r0);
        r1 = "*";
        r1 = r0.contains(r1);
        if (r1 == 0) goto L_0x008a;
    L_0x005c:
        r1 = "*.";
        r1 = r0.startsWith(r1);
        if (r1 != 0) goto L_0x008f;
    L_0x0065:
        return r3;
    L_0x0066:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r0 = r0.append(r8);
        r0 = r0.append(r5);
        r8 = r0.toString();
        goto L_0x0044;
    L_0x0078:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r0 = r0.append(r9);
        r0 = r0.append(r5);
        r9 = r0.toString();
        goto L_0x004d;
    L_0x008a:
        r0 = r8.equals(r0);
        return r0;
    L_0x008f:
        r1 = 42;
        r1 = r0.indexOf(r1, r4);
        if (r1 != r6) goto L_0x0065;
    L_0x0097:
        r1 = r8.length();
        r2 = r0.length();
        if (r1 < r2) goto L_0x00c1;
    L_0x00a1:
        r1 = "*.";
        r1 = r1.equals(r0);
        if (r1 != 0) goto L_0x00c2;
    L_0x00aa:
        r0 = r0.substring(r4);
        r1 = r8.endsWith(r0);
        if (r1 == 0) goto L_0x00c3;
    L_0x00b4:
        r1 = r8.length();
        r0 = r0.length();
        r0 = r1 - r0;
        if (r0 > 0) goto L_0x00c4;
    L_0x00c0:
        return r4;
    L_0x00c1:
        return r3;
    L_0x00c2:
        return r3;
    L_0x00c3:
        return r3;
    L_0x00c4:
        r0 = r0 + -1;
        r0 = r8.lastIndexOf(r5, r0);
        if (r0 == r6) goto L_0x00c0;
    L_0x00cc:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.tls.OkHostnameVerifier.verifyHostName(java.lang.String, java.lang.String):boolean");
    }

    private boolean verifyHostName(String str, X509Certificate x509Certificate) {
        String toLowerCase = str.toLowerCase(Locale.US);
        List subjectAltNames = getSubjectAltNames(x509Certificate, ALT_DNS_NAME);
        int size = subjectAltNames.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            if (verifyHostName(toLowerCase, (String) subjectAltNames.get(i))) {
                return true;
            }
            i++;
            z = true;
        }
        if (!z) {
            String findMostSpecific = new DistinguishedNameParser(x509Certificate.getSubjectX500Principal()).findMostSpecific("cn");
            if (findMostSpecific != null) {
                return verifyHostName(toLowerCase, findMostSpecific);
            }
        }
        return false;
    }

    private boolean verifyIpAddress(String str, X509Certificate x509Certificate) {
        List subjectAltNames = getSubjectAltNames(x509Certificate, ALT_IPA_NAME);
        int size = subjectAltNames.size();
        for (int i = 0; i < size; i++) {
            if (str.equalsIgnoreCase((String) subjectAltNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean verify(String str, X509Certificate x509Certificate) {
        return !verifyAsIpAddress(str) ? verifyHostName(str, x509Certificate) : verifyIpAddress(str, x509Certificate);
    }

    public boolean verify(String str, SSLSession sSLSession) {
        try {
            return verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }
}
