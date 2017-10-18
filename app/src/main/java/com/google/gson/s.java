package com.google.gson;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class s extends l {
    final /* synthetic */ i hF;

    s(i iVar) {
        this.hF = iVar;
    }

    public Number cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return Long.valueOf(bVar.do());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ht(aVar, (Number) obj);
    }

    public void ht(a aVar, Number number) {
        if (number != null) {
            aVar.dC(number.toString());
        } else {
            aVar.dD();
        }
    }
}
