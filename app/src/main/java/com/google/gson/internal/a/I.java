package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class I extends l {
    I() {
    }

    public StringBuilder cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return new StringBuilder(bVar.dk());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ec(aVar, (StringBuilder) obj);
    }

    public void ec(a aVar, StringBuilder stringBuilder) {
        String str = null;
        if (stringBuilder != null) {
            str = stringBuilder.toString();
        }
        aVar.dC(str);
    }
}
