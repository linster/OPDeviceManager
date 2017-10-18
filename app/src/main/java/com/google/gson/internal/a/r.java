package com.google.gson.internal.a;

import com.google.gson.a.d;
import com.google.gson.i;
import com.google.gson.internal.a;
import com.google.gson.l;
import com.google.gson.m;

public final class r implements m {
    private final a df;

    public r(a aVar) {
        this.df = aVar;
    }

    static l dL(a aVar, i iVar, com.google.gson.b.a aVar2, d dVar) {
        Class value = dVar.value();
        if (l.class.isAssignableFrom(value)) {
            return (l) aVar.ez(com.google.gson.b.a.fu(value)).eY();
        }
        if (m.class.isAssignableFrom(value)) {
            return ((m) aVar.ez(com.google.gson.b.a.fu(value)).eY()).cX(iVar, aVar2);
        }
        throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
    }

    public l cX(i iVar, com.google.gson.b.a aVar) {
        d dVar = (d) aVar.getRawType().getAnnotation(d.class);
        return dVar != null ? dL(this.df, iVar, aVar, dVar) : null;
    }
}
