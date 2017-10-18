package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class O extends l {
    O() {
    }

    public Number cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return Short.valueOf((short) bVar.dp());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ef(aVar, (Number) obj);
    }

    public void ef(a aVar, Number number) {
        aVar.dG(number);
    }
}
