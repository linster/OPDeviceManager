package com.google.gson.internal.a;

import com.google.gson.JsonIOException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.net.URI;

class ag extends l {
    ag() {
    }

    public URI cR(b bVar) {
        URI uri = null;
        if (bVar.df() != JsonToken.NULL) {
            try {
                String dk = bVar.dk();
                if (!"null".equals(dk)) {
                    uri = new URI(dk);
                }
                return uri;
            } catch (Throwable e) {
                throw new JsonIOException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ey(aVar, (URI) obj);
    }

    public void ey(a aVar, URI uri) {
        String str = null;
        if (uri != null) {
            str = uri.toASCIIString();
        }
        aVar.dC(str);
    }
}
