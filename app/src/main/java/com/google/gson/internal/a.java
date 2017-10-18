package com.google.gson.internal;

import com.google.gson.t;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public final class a {
    private final Map eQ;

    public a(Map map) {
        this.eQ = map;
    }

    private n eA(Class cls) {
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new m(this, declaredConstructor);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private n eB(Type type, Class cls) {
        return !Collection.class.isAssignableFrom(cls) ? !Map.class.isAssignableFrom(cls) ? null : !SortedMap.class.isAssignableFrom(cls) ? ((type instanceof ParameterizedType) && !String.class.isAssignableFrom(com.google.gson.b.a.ft(((ParameterizedType) type).getActualTypeArguments()[0]).getRawType())) ? new x(this) : new i(this) : new D(this) : !SortedSet.class.isAssignableFrom(cls) ? !EnumSet.class.isAssignableFrom(cls) ? !Set.class.isAssignableFrom(cls) ? !Queue.class.isAssignableFrom(cls) ? new F(this) : new f(this) : new B(this) : new J(this, type) : new h(this);
    }

    private n eC(Type type, Class cls) {
        return new q(this, cls, type);
    }

    public n ez(com.google.gson.b.a aVar) {
        Type fs = aVar.fs();
        Class rawType = aVar.getRawType();
        t tVar = (t) this.eQ.get(fs);
        if (tVar != null) {
            return new j(this, tVar, fs);
        }
        tVar = (t) this.eQ.get(rawType);
        if (tVar != null) {
            return new w(this, tVar, fs);
        }
        n eA = eA(rawType);
        if (eA != null) {
            return eA;
        }
        eA = eB(fs, rawType);
        return eA == null ? eC(fs, rawType) : eA;
    }

    public String toString() {
        return this.eQ.toString();
    }
}
