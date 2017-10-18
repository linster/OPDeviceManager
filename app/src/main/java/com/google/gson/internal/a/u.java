package com.google.gson.internal.a;

import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class u extends l {
    private final i dg;
    private final l dh;
    private final Type di;

    u(i iVar, l lVar, Type type) {
        this.dg = iVar;
        this.dh = lVar;
        this.di = type;
    }

    private Type dN(Type type, Object obj) {
        return obj == null ? type : (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type;
    }

    public Object cR(b bVar) {
        return this.dh.cR(bVar);
    }

    public void cT(a aVar, Object obj) {
        l lVar = this.dh;
        Type dN = dN(this.di, obj);
        if (dN != this.di) {
            lVar = this.dg.gT(com.google.gson.b.a.ft(dN));
            if ((lVar instanceof R) && !(this.dh instanceof R)) {
                lVar = this.dh;
            }
        }
        lVar.cT(aVar, obj);
    }
}
