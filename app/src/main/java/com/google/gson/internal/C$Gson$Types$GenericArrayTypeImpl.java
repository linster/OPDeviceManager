package com.google.gson.internal;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

final class C$Gson$Types$GenericArrayTypeImpl implements GenericArrayType, Serializable {
    private static final long serialVersionUID = 0;
    private final Type componentType;

    public C$Gson$Types$GenericArrayTypeImpl(Type type) {
        this.componentType = z.fk(type);
    }

    public boolean equals(Object obj) {
        return (obj instanceof GenericArrayType) && z.equals(this, (GenericArrayType) obj);
    }

    public Type getGenericComponentType() {
        return this.componentType;
    }

    public int hashCode() {
        return this.componentType.hashCode();
    }

    public String toString() {
        return z.typeToString(this.componentType) + "[]";
    }
}
