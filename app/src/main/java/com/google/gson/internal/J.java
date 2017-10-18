package com.google.gson.internal;

import com.google.gson.JsonIOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;

class J implements n {
    final /* synthetic */ Type fY;
    final /* synthetic */ a fZ;

    J(a aVar, Type type) {
        this.fZ = aVar;
        this.fY = type;
    }

    public Object eY() {
        if (this.fY instanceof ParameterizedType) {
            Type type = ((ParameterizedType) this.fY).getActualTypeArguments()[0];
            if (type instanceof Class) {
                return EnumSet.noneOf((Class) type);
            }
            throw new JsonIOException("Invalid EnumSet type: " + this.fY.toString());
        }
        throw new JsonIOException("Invalid EnumSet type: " + this.fY.toString());
    }
}
