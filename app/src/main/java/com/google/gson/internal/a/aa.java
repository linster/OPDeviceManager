package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.util.UUID;

class aa extends l {
    aa() {
    }

    public UUID cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return UUID.fromString(bVar.dk());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        er(aVar, (UUID) obj);
    }

    public void er(a aVar, UUID uuid) {
        String str = null;
        if (uuid != null) {
            str = uuid.toString();
        }
        aVar.dC(str);
    }
}
