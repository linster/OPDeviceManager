package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class g extends l {
    g() {
    }

    public Boolean cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return Boolean.valueOf(bVar.dk());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        cY(aVar, (Boolean) obj);
    }

    public void cY(a aVar, Boolean bool) {
        aVar.dC(bool != null ? bool.toString() : "null");
    }
}
