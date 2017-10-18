package com.google.gson;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class v extends l {
    final /* synthetic */ i hH;

    v(i iVar) {
        this.hH = iVar;
    }

    public Float cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return Float.valueOf((float) bVar.dn());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        hw(aVar, (Number) obj);
    }

    public void hw(a aVar, Number number) {
        if (number != null) {
            this.hH.gR((double) number.floatValue());
            aVar.dG(number);
            return;
        }
        aVar.dD();
    }
}
