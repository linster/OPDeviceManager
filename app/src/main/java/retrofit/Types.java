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
    private static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    final class GenericArrayTypeImpl implements GenericArrayType {
        private final Type componentType;

        public GenericArrayTypeImpl(Type type) {
            this.componentType = type;
        }

        public boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && Types.equals(this, (GenericArrayType) obj);
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return Types.typeToString(this.componentType) + "[]";
        }
    }

    final class ParameterizedTypeImpl implements ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type type, Type type2, Type... typeArr) {
            int i;
            int i2 = 1;
            int i3 = 0;
            if (type2 instanceof Class) {
                i = type != null ? 0 : 1;
                if (((Class) type2).getEnclosingClass() != null) {
                    i2 = 0;
                }
                if (i != i2) {
                    throw new IllegalArgumentException();
                }
            }
            this.ownerType = type;
            this.rawType = type2;
            this.typeArguments = (Type[]) typeArr.clone();
            Type[] typeArr2 = this.typeArguments;
            i = typeArr2.length;
            while (i3 < i) {
                Type type3 = typeArr2[i3];
                if (type3 != null) {
                    Types.checkNotPrimitive(type3);
                    i3++;
                } else {
                    throw new NullPointerException();
                }
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && Types.equals(this, (ParameterizedType) obj);
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
            return (Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode()) ^ Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder((this.typeArguments.length + 1) * 30);
            stringBuilder.append(Types.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append("<").append(Types.typeToString(this.typeArguments[0]));
            for (int i = 1; i < this.typeArguments.length; i++) {
                stringBuilder.append(", ").append(Types.typeToString(this.typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    final class WildcardTypeImpl implements WildcardType {
        private final Type lowerBound;
        private final Type upperBound;

        public WildcardTypeImpl(Type[] typeArr, Type[] typeArr2) {
            if (typeArr2.length > 1) {
                throw new IllegalArgumentException();
            } else if (typeArr.length != 1) {
                throw new IllegalArgumentException();
            } else if (typeArr2.length != 1) {
                if (typeArr[0] != null) {
                    Types.checkNotPrimitive(typeArr[0]);
                    this.lowerBound = null;
                    this.upperBound = typeArr[0];
                    return;
                }
                throw new NullPointerException();
            } else if (typeArr2[0] != null) {
                Types.checkNotPrimitive(typeArr2[0]);
                if (typeArr[0] == Object.class) {
                    this.lowerBound = typeArr2[0];
                    this.upperBound = Object.class;
                    return;
                }
                throw new IllegalArgumentException();
            } else {
                throw new NullPointerException();
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof WildcardType) && Types.equals(this, (WildcardType) obj);
        }

        public Type[] getLowerBounds() {
            if (this.lowerBound == null) {
                return Types.EMPTY_TYPE_ARRAY;
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
            return this.lowerBound == null ? this.upperBound != Object.class ? "? extends " + Types.typeToString(this.upperBound) : "?" : "? super " + Types.typeToString(this.lowerBound);
        }
    }

    private Types() {
    }

    private static void checkNotPrimitive(Type type) {
        if ((type instanceof Class) && ((Class) type).isPrimitive()) {
            throw new IllegalArgumentException();
        }
    }

    private static Class declaringClassOf(TypeVariable typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        return !(genericDeclaration instanceof Class) ? null : (Class) genericDeclaration;
    }

    private static boolean equal(Object obj, Object obj2) {
        if (obj != obj2) {
            if (obj == null) {
                return false;
            }
            if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(Type type, Type type2) {
        boolean z = true;
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedType2 = (ParameterizedType) type2;
            if (equal(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType())) {
                if (!Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments())) {
                }
                return z;
            }
            z = false;
            return z;
        } else if (type instanceof GenericArrayType) {
            if (!(type2 instanceof GenericArrayType)) {
                return false;
            }
            return equals(((GenericArrayType) type).getGenericComponentType(), ((GenericArrayType) type2).getGenericComponentType());
        } else if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            WildcardType wildcardType2 = (WildcardType) type2;
            if (Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds())) {
                if (!Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds())) {
                }
                return z;
            }
            z = false;
            return z;
        } else if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable typeVariable = (TypeVariable) type;
            TypeVariable typeVariable2 = (TypeVariable) type2;
            if (typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration()) {
                if (!typeVariable.getName().equals(typeVariable2.getName())) {
                }
                return z;
            }
            z = false;
            return z;
        }
    }

    static Type getGenericSupertype(Type type, Class cls, Class cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == cls2) {
                    return cls.getGenericInterfaces()[i];
                }
                if (cls2.isAssignableFrom(interfaces[i])) {
                    return getGenericSupertype(cls.getGenericInterfaces()[i], interfaces[i], cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            while (cls != Object.class) {
                Class superclass = cls.getSuperclass();
                if (superclass == cls2) {
                    return cls.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return getGenericSupertype(cls.getGenericSuperclass(), superclass, cls2);
                }
                cls = superclass;
            }
        }
        return cls2;
    }

    public static Class getRawType(Type type) {
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
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type != null ? type.getClass().getName() : "null"));
        }
    }

    public static Type getSupertype(Type type, Class cls, Class cls2) {
        if (cls2.isAssignableFrom(cls)) {
            return resolve(type, cls, getGenericSupertype(type, cls, cls2));
        }
        throw new IllegalArgumentException();
    }

    private static int hashCodeOrZero(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    private static int indexOf(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    public static Type resolve(Type type, Class cls, Type type2) {
        Type type3 = type2;
        while (type3 instanceof TypeVariable) {
            type3 = (TypeVariable) type3;
            type2 = resolveTypeVariable(type, cls, type3);
            if (type2 == type3) {
                return type2;
            }
            type3 = type2;
        }
        Type componentType;
        Type resolve;
        if ((type3 instanceof Class) && ((Class) type3).isArray()) {
            type3 = (Class) type3;
            componentType = type3.getComponentType();
            resolve = resolve(type, cls, componentType);
            if (componentType != resolve) {
                type3 = new GenericArrayTypeImpl(resolve);
            }
            return type3;
        } else if (type3 instanceof GenericArrayType) {
            type3 = (GenericArrayType) type3;
            componentType = type3.getGenericComponentType();
            resolve = resolve(type, cls, componentType);
            if (componentType != resolve) {
                type3 = new GenericArrayTypeImpl(resolve);
            }
            return type3;
        } else if (type3 instanceof ParameterizedType) {
            type3 = (ParameterizedType) type3;
            componentType = type3.getOwnerType();
            Type resolve2 = resolve(type, cls, componentType);
            int i = resolve2 == componentType ? 0 : 1;
            r4 = type3.getActualTypeArguments();
            int length = r4.length;
            int i2 = i;
            r1 = r4;
            for (int i3 = 0; i3 < length; i3++) {
                Type resolve3 = resolve(type, cls, r1[i3]);
                if (resolve3 != r1[i3]) {
                    if (i2 == 0) {
                        r1 = (Type[]) r1.clone();
                        i2 = 1;
                    }
                    r1[i3] = resolve3;
                }
            }
            if (i2 != 0) {
                Object parameterizedTypeImpl = new ParameterizedTypeImpl(resolve2, type3.getRawType(), r1);
            }
            return type3;
        } else if (!(type3 instanceof WildcardType)) {
            return type3;
        } else {
            WildcardType wildcardType = (WildcardType) type3;
            r1 = wildcardType.getLowerBounds();
            r4 = wildcardType.getUpperBounds();
            if (r1.length == 1) {
                if (resolve(type, cls, r1[0]) != r1[0]) {
                    return new WildcardTypeImpl(new Type[]{Object.class}, new Type[]{resolve(type, cls, r1[0])});
                }
            } else if (r4.length == 1 && resolve(type, cls, r4[0]) != r4[0]) {
                return new WildcardTypeImpl(new Type[]{resolve(type, cls, r4[0])}, EMPTY_TYPE_ARRAY);
            }
            return wildcardType;
        }
    }

    private static Type resolveTypeVariable(Type type, Class cls, TypeVariable typeVariable) {
        Class declaringClassOf = declaringClassOf(typeVariable);
        if (declaringClassOf == null) {
            return typeVariable;
        }
        Type genericSupertype = getGenericSupertype(type, cls, declaringClassOf);
        if (!(genericSupertype instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) genericSupertype).getActualTypeArguments()[indexOf(declaringClassOf.getTypeParameters(), typeVariable)];
    }

    public static String typeToString(Type type) {
        return !(type instanceof Class) ? type.toString() : ((Class) type).getName();
    }
}
