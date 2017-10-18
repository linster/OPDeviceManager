package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import java.util.Locale;
import java.util.StringTokenizer;

class b extends l {
    b() {
    }

    public Locale cR(com.google.gson.stream.b bVar) {
        String str = null;
        if (bVar.df() != JsonToken.NULL) {
            StringTokenizer stringTokenizer = new StringTokenizer(bVar.dk(), "_");
            String nextToken = !stringTokenizer.hasMoreElements() ? null : stringTokenizer.nextToken();
            String nextToken2 = !stringTokenizer.hasMoreElements() ? null : stringTokenizer.nextToken();
            if (stringTokenizer.hasMoreElements()) {
                str = stringTokenizer.nextToken();
            }
            return (nextToken2 == null && str == null) ? new Locale(nextToken) : str != null ? new Locale(nextToken, nextToken2, str) : new Locale(nextToken, nextToken2);
        } else {
            bVar.dm();
            return null;
        }
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        cU(aVar, (Locale) obj);
    }

    public void cU(a aVar, Locale locale) {
        String str = null;
        if (locale != null) {
            str = locale.toString();
        }
        aVar.dC(str);
    }
}
