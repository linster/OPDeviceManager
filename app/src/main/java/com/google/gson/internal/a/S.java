package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.math.BigInteger;

class S extends l {
    S() {
    }

    public BigInteger cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return new BigInteger(bVar.dk());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ei(aVar, (BigInteger) obj);
    }

    public void ei(a aVar, BigInteger bigInteger) {
        aVar.dG(bigInteger);
    }
}
