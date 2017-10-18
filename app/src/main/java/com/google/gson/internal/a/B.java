package com.google.gson.internal.a;

import com.google.gson.i;
import com.google.gson.internal.a;
import com.google.gson.internal.z;
import com.google.gson.l;
import com.google.gson.m;
import java.lang.reflect.Type;
import java.util.Collection;

public final class B implements m {
    private final a dq;

    public B(a aVar) {
        this.dq = aVar;
    }

    public l cX(i iVar, com.google.gson.b.a aVar) {
        Type fs = aVar.fs();
        Class rawType = aVar.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        fs = z.fm(fs, rawType);
        return new ae(iVar, fs, iVar.gT(com.google.gson.b.a.ft(fs)), this.dq.ez(aVar));
    }
}
