package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;

class H implements m {
    H() {
    }

    public l cX(i iVar, a aVar) {
        Class rawType = aVar.getRawType();
        if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
            return null;
        }
        if (!rawType.isEnum()) {
            rawType = rawType.getSuperclass();
        }
        return new V(rawType);
    }
}
