package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class q extends l {
    q() {
    }

    public Boolean cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return bVar.df() != JsonToken.STRING ? Boolean.valueOf(bVar.dl()) : Boolean.valueOf(Boolean.parseBoolean(bVar.dk()));
        } else {
            bVar.dm();
            return null;
        }
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        dK(aVar, (Boolean) obj);
    }

    public void dK(a aVar, Boolean bool) {
        if (bool != null) {
            aVar.dE(bool.booleanValue());
        } else {
            aVar.dD();
        }
    }
}
