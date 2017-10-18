package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class j extends l {
    j() {
    }

    public Number cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return Double.valueOf(bVar.dn());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        da(aVar, (Number) obj);
    }

    public void da(a aVar, Number number) {
        aVar.dG(number);
    }
}
