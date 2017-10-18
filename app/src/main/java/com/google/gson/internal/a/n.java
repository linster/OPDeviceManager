package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.internal.z;
import com.google.gson.l;
import com.google.gson.m;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

class n implements m {
    n() {
    }

    public l cX(i iVar, a aVar) {
        Type fs = aVar.fs();
        if (!(fs instanceof GenericArrayType)) {
            if (!(fs instanceof Class) || !((Class) fs).isArray()) {
                return null;
            }
        }
        Type fl = z.fl(fs);
        return new af(iVar, iVar.gT(a.ft(fl)), z.getRawType(fl));
    }
}
