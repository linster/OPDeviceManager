package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.b;

class a extends l {
    a() {
    }

    public Number cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return Integer.valueOf(bVar.dp());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public void cS(com.google.gson.stream.a aVar, Number number) {
        aVar.dG(number);
    }

    public /* bridge */ /* synthetic */ void cT(com.google.gson.stream.a aVar, Object obj) {
        cS(aVar, (Number) obj);
    }
}
