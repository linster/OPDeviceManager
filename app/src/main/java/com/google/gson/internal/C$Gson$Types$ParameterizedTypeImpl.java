package com.google.gson.internal;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

final class C$Gson$Types$ParameterizedTypeImpl implements ParameterizedType, Serializable {
    private static final long serialVersionUID = 0;
    private final Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;

    public C$Gson$Types$ParameterizedTypeImpl(Type type, Type type2, Type... typeArr) {
        boolean z = true;
        int i = 0;
        if (type2 instanceof Class) {
            Class cls = (Class) type2;
            boolean z2 = Modifier.isStatic(cls.getModifiers()) || cls.getEnclosingClass() == null;
            if (type == null && !z2) {
                z = false;
            }
            g.fa(z);
        }
        this.ownerType = type != null ? z.fk(type) : null;
        this.rawType = z.fk(type2);
        this.typeArguments = (Type[]) typeArr.clone();
        while (i < this.typeArguments.length) {
            g.eZ(this.typeArguments[i]);
            z.checkNotPrimitive(this.typeArguments[i]);
            this.typeArguments[i] = z.fk(this.typeArguments[i]);
            i++;
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof ParameterizedType) && z.equals(this, (ParameterizedType) obj);
    }

    public Type[] getActualTypeArguments() {
        return (Type[]) this.typeArguments.clone();
    }

    public Type getOwnerType() {
        return this.ownerType;
    }

    public Type getRawType() {
        return this.rawType;
    }

    public int hashCode() {
        return (Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode()) ^ z.hashCodeOrZero(this.ownerType);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder((this.typeArguments.length + 1) * 30);
        stringBuilder.append(z.typeToString(this.rawType));
        if (this.typeArguments.length == 0) {
            return stringBuilder.toString();
        }
        stringBuilder.append("<").append(z.typeToString(this.typeArguments[0]));
        for (int i = 1; i < this.typeArguments.length; i++) {
            stringBuilder.append(", ").append(z.typeToString(this.typeArguments[i]));
        }
        return stringBuilder.append(">").toString();
    }
}
