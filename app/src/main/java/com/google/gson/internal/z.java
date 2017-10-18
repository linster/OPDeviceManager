package com.google.gson.internal;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class z {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    private z() {
    }

    private static void checkNotPrimitive(Type type) {
        boolean z = false;
        if (!(type instanceof Class) || !((Class) type).isPrimitive()) {
            z = true;
        }
        g.fa(z);
    }

    private static Class declaringClassOf(TypeVariable typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        return !(genericDeclaration instanceof Class) ? null : (Class) genericDeclaration;
    }

    static boolean equal(Object obj, Object obj2) {
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

    public static ParameterizedType fg(Type type, Type type2, Type... typeArr) {
        return new C$Gson$Types$ParameterizedTypeImpl(type, type2, typeArr);
    }

    public static GenericArrayType fh(Type type) {
        return new C$Gson$Types$GenericArrayTypeImpl(type);
    }

    public static WildcardType fi(Type type) {
        return new C$Gson$Types$WildcardTypeImpl(new Type[]{type}, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType fj(Type type) {
        return new C$Gson$Types$WildcardTypeImpl(new Type[]{Object.class}, new Type[]{type});
    }

    public static Type fk(Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            return !cls.isArray() ? cls : new C$Gson$Types$GenericArrayTypeImpl(fk(cls.getComponentType()));
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new C$Gson$Types$ParameterizedTypeImpl(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            return new C$Gson$Types$GenericArrayTypeImpl(((GenericArrayType) type).getGenericComponentType());
        } else {
            if (!(type instanceof WildcardType)) {
                return type;
            }
            WildcardType wildcardType = (WildcardType) type;
            return new C$Gson$Types$WildcardTypeImpl(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
        }
    }

    public static Type fl(Type type) {
        return !(type instanceof GenericArrayType) ? ((Class) type).getComponentType() : ((GenericArrayType) type).getGenericComponentType();
    }

    public static Type fm(Type type, Class cls) {
        Type supertype = getSupertype(type, cls, Collection.class);
        if (supertype instanceof WildcardType) {
            supertype = ((WildcardType) supertype).getUpperBounds()[0];
        }
        return !(supertype instanceof ParameterizedType) ? Object.class : ((ParameterizedType) supertype).getActualTypeArguments()[0];
    }

    public static Type[] fn(Type type, Class cls) {
        if (type != Properties.class) {
            Type supertype = getSupertype(type, cls, Map.class);
            if (supertype instanceof ParameterizedType) {
                return ((ParameterizedType) supertype).getActualTypeArguments();
            }
            return new Type[]{Object.class, Object.class};
        }
        return new Type[]{String.class, String.class};
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
            g.fa(rawType instanceof Class);
            return (Class) rawType;
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

    static Type getSupertype(Type type, Class cls, Class cls2) {
        g.fa(cls2.isAssignableFrom(cls));
        return resolve(type, cls, getGenericSupertype(type, cls, cls2));
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
                type3 = fh(resolve);
            }
            return type3;
        } else if (type3 instanceof GenericArrayType) {
            type3 = (GenericArrayType) type3;
            componentType = type3.getGenericComponentType();
            resolve = resolve(type, cls, componentType);
            if (componentType != resolve) {
                type3 = fh(resolve);
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
                type3 = fg(resolve2, type3.getRawType(), r1);
            }
            return type3;
        } else if (!(type3 instanceof WildcardType)) {
            return type3;
        } else {
            WildcardType wildcardType = (WildcardType) type3;
            r1 = wildcardType.getLowerBounds();
            r4 = wildcardType.getUpperBounds();
            if (r1.length == 1) {
                Type resolve4 = resolve(type, cls, r1[0]);
                if (resolve4 != r1[0]) {
                    return fj(resolve4);
                }
            } else if (r4.length == 1) {
                componentType = resolve(type, cls, r4[0]);
                if (componentType != r4[0]) {
                    return fi(componentType);
                }
            }
            return wildcardType;
        }
    }

    static Type resolveTypeVariable(Type type, Class cls, TypeVariable typeVariable) {
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
