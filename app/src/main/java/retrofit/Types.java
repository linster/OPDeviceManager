package retrofit;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.NoSuchElementException;

final class Types {
    private static final Type[] EMPTY_TYPE_ARRAY;

    private static final class GenericArrayTypeImpl implements GenericArrayType {
        private final Type componentType;

        public GenericArrayTypeImpl(Type componentType) {
            this.componentType = componentType;
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public boolean equals(Object o) {
            if ((o instanceof GenericArrayType) && Types.equals(this, (GenericArrayType) o)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return Types.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
            int i;
            int i2 = 1;
            int i3 = 0;
            if (rawType instanceof Class) {
                if (ownerType != null) {
                    i = 0;
                } else {
                    i = 1;
                }
                if (((Class) rawType).getEnclosingClass() != null) {
                    i2 = 0;
                }
                if (i != i2) {
                    throw new IllegalArgumentException();
                }
            }
            this.ownerType = ownerType;
            this.rawType = rawType;
            this.typeArguments = (Type[]) typeArguments.clone();
            Type[] typeArr = this.typeArguments;
            i = typeArr.length;
            while (i3 < i) {
                Type typeArgument = typeArr[i3];
                if (typeArgument != null) {
                    Types.checkNotPrimitive(typeArgument);
                    i3++;
                } else {
                    throw new NullPointerException();
                }
            }
        }

        public Type[] getActualTypeArguments() {
            return (Type[]) this.typeArguments.clone();
        }

        public Type getRawType() {
            return this.rawType;
        }

        public Type getOwnerType() {
            return this.ownerType;
        }

        public boolean equals(Object other) {
            return (other instanceof ParameterizedType) && Types.equals(this, (ParameterizedType) other);
        }

        public int hashCode() {
            return (Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode()) ^ Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            StringBuilder result = new StringBuilder((this.typeArguments.length + 1) * 30);
            result.append(Types.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return result.toString();
            }
            result.append("<").append(Types.typeToString(this.typeArguments[0]));
            for (int i = 1; i < this.typeArguments.length; i++) {
                result.append(", ").append(Types.typeToString(this.typeArguments[i]));
            }
            return result.append(">").toString();
        }
    }

    private static final class WildcardTypeImpl implements WildcardType {
        private final Type lowerBound;
        private final Type upperBound;

        public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
            if (lowerBounds.length > 1) {
                throw new IllegalArgumentException();
            } else if (upperBounds.length != 1) {
                throw new IllegalArgumentException();
            } else if (lowerBounds.length != 1) {
                if (upperBounds[0] != null) {
                    Types.checkNotPrimitive(upperBounds[0]);
                    this.lowerBound = null;
                    this.upperBound = upperBounds[0];
                    return;
                }
                throw new NullPointerException();
            } else if (lowerBounds[0] != null) {
                Types.checkNotPrimitive(lowerBounds[0]);
                if (upperBounds[0] == Object.class) {
                    this.lowerBound = lowerBounds[0];
                    this.upperBound = Object.class;
                    return;
                }
                throw new IllegalArgumentException();
            } else {
                throw new NullPointerException();
            }
        }

        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        public Type[] getLowerBounds() {
            if (this.lowerBound == null) {
                return Types.EMPTY_TYPE_ARRAY;
            }
            return new Type[]{this.lowerBound};
        }

        public boolean equals(Object other) {
            return (other instanceof WildcardType) && Types.equals(this, (WildcardType) other);
        }

        public int hashCode() {
            return (this.lowerBound == null ? 1 : this.lowerBound.hashCode() + 31) ^ (this.upperBound.hashCode() + 31);
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + Types.typeToString(this.lowerBound);
            }
            if (this.upperBound != Object.class) {
                return "? extends " + Types.typeToString(this.upperBound);
            }
            return "?";
        }
    }

    static {
        EMPTY_TYPE_ARRAY = new Type[0];
    }

    private Types() {
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
            throw new IllegalArgumentException();
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(getRawType(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return getRawType(((WildcardType) type).getUpperBounds()[0]);
            }
            String className;
            if (type != null) {
                className = type.getClass().getName();
            } else {
                className = "null";
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

    public static boolean equals(Type a, Type b) {
        boolean z = true;
        if (a == b) {
            return true;
        }
        if (a instanceof Class) {
            return a.equals(b);
        }
        if (a instanceof ParameterizedType) {
            if (!(b instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType pa = (ParameterizedType) a;
            ParameterizedType pb = (ParameterizedType) b;
            if (equal(pa.getOwnerType(), pb.getOwnerType()) && pa.getRawType().equals(pb.getRawType())) {
                if (!Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments())) {
                }
                return z;
            }
            z = false;
            return z;
        } else if (a instanceof GenericArrayType) {
            if (!(b instanceof GenericArrayType)) {
                return false;
            }
            return equals(((GenericArrayType) a).getGenericComponentType(), ((GenericArrayType) b).getGenericComponentType());
        } else if (a instanceof WildcardType) {
            if (!(b instanceof WildcardType)) {
                return false;
            }
            WildcardType wa = (WildcardType) a;
            WildcardType wb = (WildcardType) b;
            if (Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds())) {
                if (!Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds())) {
                }
                return z;
            }
            z = false;
            return z;
        } else if (!(a instanceof TypeVariable) || !(b instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable<?> va = (TypeVariable) a;
            TypeVariable<?> vb = (TypeVariable) b;
            if (va.getGenericDeclaration() == vb.getGenericDeclaration()) {
                if (!va.getName().equals(vb.getName())) {
                }
                return z;
            }
            z = false;
            return z;
        }
    }

    static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
        if (toResolve == rawType) {
            return context;
        }
        if (toResolve.isInterface()) {
            Class<?>[] interfaces = rawType.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == toResolve) {
                    return rawType.getGenericInterfaces()[i];
                }
                if (toResolve.isAssignableFrom(interfaces[i])) {
                    return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
                }
            }
        }
        if (!rawType.isInterface()) {
            while (rawType != Object.class) {
                Class<?> rawSupertype = rawType.getSuperclass();
                if (rawSupertype == toResolve) {
                    return rawType.getGenericSuperclass();
                }
                if (toResolve.isAssignableFrom(rawSupertype)) {
                    return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, toResolve);
                }
                rawType = rawSupertype;
            }
        }
        return toResolve;
    }

    private static int indexOf(Object[] array, Object toFind) {
        for (int i = 0; i < array.length; i++) {
            if (toFind.equals(array[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    private static boolean equal(Object a, Object b) {
        if (a != b) {
            if (a == null) {
                return false;
            }
            if (!a.equals(b)) {
                return false;
            }
        }
        return true;
    }

    private static int hashCodeOrZero(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    public static String typeToString(Type type) {
        return !(type instanceof Class) ? type.toString() : ((Class) type).getName();
    }

    public static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
        if (supertype.isAssignableFrom(contextRawType)) {
            return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
        }
        throw new IllegalArgumentException();
    }

    public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
        while (toResolve instanceof TypeVariable) {
            TypeVariable<?> typeVariable = (TypeVariable) toResolve;
            toResolve = resolveTypeVariable(context, contextRawType, typeVariable);
            if (toResolve == typeVariable) {
                return toResolve;
            }
        }
        Type componentType;
        Type newComponentType;
        if ((toResolve instanceof Class) && ((Class) toResolve).isArray()) {
            Type type = (Class) toResolve;
            componentType = type.getComponentType();
            newComponentType = resolve(context, contextRawType, componentType);
            if (componentType != newComponentType) {
                type = new GenericArrayTypeImpl(newComponentType);
            }
            return type;
        } else if (toResolve instanceof GenericArrayType) {
            Type type2 = (GenericArrayType) toResolve;
            componentType = type2.getGenericComponentType();
            newComponentType = resolve(context, contextRawType, componentType);
            if (componentType != newComponentType) {
                type2 = new GenericArrayTypeImpl(newComponentType);
            }
            return type2;
        } else if (toResolve instanceof ParameterizedType) {
            boolean changed;
            ParameterizedType original = (ParameterizedType) toResolve;
            Type ownerType = original.getOwnerType();
            Type newOwnerType = resolve(context, contextRawType, ownerType);
            if (newOwnerType == ownerType) {
                changed = false;
            } else {
                changed = true;
            }
            Type[] args = original.getActualTypeArguments();
            int length = args.length;
            for (int t = 0; t < length; t++) {
                Type resolvedTypeArgument = resolve(context, contextRawType, args[t]);
                if (resolvedTypeArgument != args[t]) {
                    if (!changed) {
                        args = (Type[]) args.clone();
                        changed = true;
                    }
                    args[t] = resolvedTypeArgument;
                }
            }
            if (changed) {
                Object original2 = new ParameterizedTypeImpl(newOwnerType, original.getRawType(), args);
            }
            return original;
        } else if (!(toResolve instanceof WildcardType)) {
            return toResolve;
        } else {
            WildcardType original3 = (WildcardType) toResolve;
            Type[] originalLowerBound = original3.getLowerBounds();
            Type[] originalUpperBound = original3.getUpperBounds();
            int length2 = originalLowerBound.length;
            if (r0 != 1) {
                length2 = originalUpperBound.length;
                if (r0 == 1 && resolve(context, contextRawType, originalUpperBound[0]) != originalUpperBound[0]) {
                    return new WildcardTypeImpl(new Type[]{resolve(context, contextRawType, originalUpperBound[0])}, EMPTY_TYPE_ARRAY);
                }
            }
            if (resolve(context, contextRawType, originalLowerBound[0]) != originalLowerBound[0]) {
                return new WildcardTypeImpl(new Type[]{Object.class}, new Type[]{resolve(context, contextRawType, originalLowerBound[0])});
            }
            return original3;
        }
    }

    private static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
        Class<?> declaredByRaw = declaringClassOf(unknown);
        if (declaredByRaw == null) {
            return unknown;
        }
        Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
        if (!(declaredBy instanceof ParameterizedType)) {
            return unknown;
        }
        return ((ParameterizedType) declaredBy).getActualTypeArguments()[indexOf(declaredByRaw.getTypeParameters(), unknown)];
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        return !(genericDeclaration instanceof Class) ? null : (Class) genericDeclaration;
    }

    private static void checkNotPrimitive(Type type) {
        if ((type instanceof Class) && ((Class) type).isPrimitive()) {
            throw new IllegalArgumentException();
        }
    }
}
