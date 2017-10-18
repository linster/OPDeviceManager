package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class Z extends l {
    Z() {
    }

    public Number cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return Byte.valueOf((byte) bVar.dp());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        eq(aVar, (Number) obj);
    }

    public void eq(a aVar, Number number) {
        aVar.dG(number);
    }
}
