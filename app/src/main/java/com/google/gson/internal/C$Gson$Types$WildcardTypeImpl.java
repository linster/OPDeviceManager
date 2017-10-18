package com.google.gson.internal;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

final class C$Gson$Types$WildcardTypeImpl implements WildcardType, Serializable {
    private static final long serialVersionUID = 0;
    private final Type lowerBound;
    private final Type upperBound;

    public C$Gson$Types$WildcardTypeImpl(Type[] typeArr, Type[] typeArr2) {
        boolean z = true;
        g.fa(typeArr2.length <= 1);
        g.fa(typeArr.length == 1);
        if (typeArr2.length != 1) {
            g.eZ(typeArr[0]);
            z.checkNotPrimitive(typeArr[0]);
            this.lowerBound = null;
            this.upperBound = z.fk(typeArr[0]);
            return;
        }
        g.eZ(typeArr2[0]);
        z.checkNotPrimitive(typeArr2[0]);
        if (typeArr[0] != Object.class) {
            z = false;
        }
        g.fa(z);
        this.lowerBound = z.fk(typeArr2[0]);
        this.upperBound = Object.class;
    }

    public boolean equals(Object obj) {
        return (obj instanceof WildcardType) && z.equals(this, (WildcardType) obj);
    }

    public Type[] getLowerBounds() {
        if (this.lowerBound == null) {
            return z.EMPTY_TYPE_ARRAY;
        }
        return new Type[]{this.lowerBound};
    }

    public Type[] getUpperBounds() {
        return new Type[]{this.upperBound};
    }

    public int hashCode() {
        return (this.lowerBound == null ? 1 : this.lowerBound.hashCode() + 31) ^ (this.upperBound.hashCode() + 31);
    }

    public String toString() {
        return this.lowerBound == null ? this.upperBound != Object.class ? "? extends " + z.typeToString(this.upperBound) : "?" : "? super " + z.typeToString(this.lowerBound);
    }
}
