package com.loc;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

class y implements HostnameVerifier {
    final /* synthetic */ bG lm;

    y(bG bGVar) {
        this.lm = bGVar;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify("*.amap.com", sSLSession);
    }
}
