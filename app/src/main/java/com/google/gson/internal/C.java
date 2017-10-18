package com.google.gson.internal;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class C {
    private static final Map fP;
    private static final Map fQ;

    static {
        Map hashMap = new HashMap(16);
        Map hashMap2 = new HashMap(16);
        fo(hashMap, hashMap2, Boolean.TYPE, Boolean.class);
        fo(hashMap, hashMap2, Byte.TYPE, Byte.class);
        fo(hashMap, hashMap2, Character.TYPE, Character.class);
        fo(hashMap, hashMap2, Double.TYPE, Double.class);
        fo(hashMap, hashMap2, Float.TYPE, Float.class);
        fo(hashMap, hashMap2, Integer.TYPE, Integer.class);
        fo(hashMap, hashMap2, Long.TYPE, Long.class);
        fo(hashMap, hashMap2, Short.TYPE, Short.class);
        fo(hashMap, hashMap2, Void.TYPE, Void.class);
        fP = Collections.unmodifiableMap(hashMap);
        fQ = Collections.unmodifiableMap(hashMap2);
    }

    private C() {
    }

    private static void fo(Map map, Map map2, Class cls, Class cls2) {
        map.put(cls, cls2);
        map2.put(cls2, cls);
    }

    public static boolean fp(Type type) {
        return fP.containsKey(type);
    }
}
