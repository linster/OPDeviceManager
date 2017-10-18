package com.google.gson.internal.a;

import com.google.gson.i;
import com.google.gson.internal.a;
import com.google.gson.internal.n;
import com.google.gson.internal.z;
import com.google.gson.l;
import com.google.gson.m;
import java.lang.reflect.Type;
import java.util.Map;

public final class E implements m {
    private final a du;
    private final boolean dv;

    public E(a aVar, boolean z) {
        this.du = aVar;
        this.dv = z;
    }

    private l dZ(i iVar, Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? ac.dS : iVar.gT(com.google.gson.b.a.ft(type));
    }

    public l cX(i iVar, com.google.gson.b.a aVar) {
        Type fs = aVar.fs();
        if (!Map.class.isAssignableFrom(aVar.getRawType())) {
            return null;
        }
        Type[] fn = z.fn(fs, z.getRawType(fs));
        l dZ = dZ(iVar, fn[0]);
        l gT = iVar.gT(com.google.gson.b.a.ft(fn[1]));
        n ez = this.du.ez(aVar);
        return new m(this, iVar, fn[0], dZ, fn[1], gT, ez);
    }
}
