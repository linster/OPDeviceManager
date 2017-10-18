package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class P extends l {
    P() {
    }

    public Number cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return Float.valueOf((float) bVar.dn());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        eg(aVar, (Number) obj);
    }

    public void eg(a aVar, Number number) {
        aVar.dG(number);
    }
}
