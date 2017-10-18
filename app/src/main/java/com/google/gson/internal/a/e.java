package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.net.URL;

class e extends l {
    e() {
    }

    public URL cR(b bVar) {
        URL url = null;
        if (bVar.df() != JsonToken.NULL) {
            String dk = bVar.dk();
            if (!"null".equals(dk)) {
                url = new URL(dk);
            }
            return url;
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        cW(aVar, (URL) obj);
    }

    public void cW(a aVar, URL url) {
        String str = null;
        if (url != null) {
            str = url.toExternalForm();
        }
        aVar.dC(str);
    }
}
