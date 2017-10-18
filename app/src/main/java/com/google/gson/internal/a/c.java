package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.math.BigDecimal;

class c extends l {
    c() {
    }

    public BigDecimal cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return new BigDecimal(bVar.dk());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        cV(aVar, (BigDecimal) obj);
    }

    public void cV(a aVar, BigDecimal bigDecimal) {
        aVar.dG(bigDecimal);
    }
}
