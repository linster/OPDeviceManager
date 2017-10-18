package com.google.gson.b;

import com.google.gson.internal.g;
import com.google.gson.internal.z;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class a {
    final Class ga;
    final Type gb;
    final int gc;

    protected a() {
        this.gb = fr(getClass());
        this.ga = z.getRawType(this.gb);
        this.gc = this.gb.hashCode();
    }

    a(Type type) {
        this.gb = z.fk((Type) g.eZ(type));
        this.ga = z.getRawType(this.gb);
        this.gc = this.gb.hashCode();
    }

    static Type fr(Class cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof Class)) {
            return z.fk(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public static a ft(Type type) {
        return new a(type);
    }

    public static a fu(Class cls) {
        return new a(cls);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof a) && z.equals(this.gb, ((a) obj).gb);
    }

    public final Type fs() {
        return this.gb;
    }

    public final Class getRawType() {
        return this.ga;
    }

    public final int hashCode() {
        return this.gc;
    }

    public final String toString() {
        return z.typeToString(this.gb);
    }
}
