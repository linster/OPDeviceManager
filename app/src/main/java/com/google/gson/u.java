package com.google.gson;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class u extends l {
    final /* synthetic */ i hG;

    u(i iVar) {
        this.hG = iVar;
    }

    public Double cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return Double.valueOf(bVar.dn());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        hv(aVar, (Number) obj);
    }

    public void hv(a aVar, Number number) {
        if (number != null) {
            this.hG.gR(number.doubleValue());
            aVar.dG(number);
            return;
        }
        aVar.dD();
    }
}
