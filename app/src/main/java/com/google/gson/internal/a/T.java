package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class T extends l {
    T() {
    }

    public Number cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return Long.valueOf(bVar.do());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ej(aVar, (Number) obj);
    }

    public void ej(a aVar, Number number) {
        aVar.dG(number);
    }
}
