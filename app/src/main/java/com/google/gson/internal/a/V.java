package com.google.gson.internal.a;

import com.google.gson.a.a;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.b;
import java.util.HashMap;
import java.util.Map;

final class V extends l {
    private final Map dH = new HashMap();
    private final Map dI = new HashMap();

    public V(Class cls) {
        try {
            for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                String name = enumR.name();
                a aVar = (a) cls.getField(name).getAnnotation(a.class);
                Object value = aVar == null ? name : aVar.value();
                this.dH.put(value, enumR);
                this.dI.put(enumR, value);
            }
        } catch (NoSuchFieldException e) {
            throw new AssertionError();
        }
    }

    public Enum cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return (Enum) this.dH.get(bVar.dk());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(com.google.gson.stream.a aVar, Object obj) {
        en(aVar, (Enum) obj);
    }

    public void en(com.google.gson.stream.a aVar, Enum enumR) {
        String str = null;
        if (enumR != null) {
            str = (String) this.dI.get(enumR);
        }
        aVar.dC(str);
    }
}
