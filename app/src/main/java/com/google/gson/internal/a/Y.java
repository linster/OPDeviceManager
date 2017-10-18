package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class Y extends l {
    Y() {
    }

    public String cR(b bVar) {
        JsonToken df = bVar.df();
        if (df != JsonToken.NULL) {
            return df != JsonToken.BOOLEAN ? bVar.dk() : Boolean.toString(bVar.dl());
        } else {
            bVar.dm();
            return null;
        }
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ep(aVar, (String) obj);
    }

    public void ep(a aVar, String str) {
        aVar.dC(str);
    }
}
