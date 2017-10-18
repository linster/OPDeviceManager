package com.google.gson.internal.a;

import com.google.gson.a.d;
import com.google.gson.h;
import com.google.gson.i;
import com.google.gson.internal.C;
import com.google.gson.internal.a;
import com.google.gson.internal.b;
import com.google.gson.internal.z;
import com.google.gson.l;
import com.google.gson.m;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public final class v implements m {
    private final a dj;
    private final h dk;
    private final b dl;

    public v(a aVar, h hVar, b bVar) {
        this.dj = aVar;
        this.dk = hVar;
        this.dl = bVar;
    }

    static boolean dP(Field field, boolean z, b bVar) {
        return (bVar.eE(field.getType(), z) || bVar.eD(field, z)) ? false : true;
    }

    private String dQ(Field field) {
        return dR(this.dk, field);
    }

    static String dR(h hVar, Field field) {
        com.google.gson.a.a aVar = (com.google.gson.a.a) field.getAnnotation(com.google.gson.a.a.class);
        return aVar != null ? aVar.value() : hVar.gO(field);
    }

    private p dS(i iVar, Field field, String str, com.google.gson.b.a aVar, boolean z, boolean z2) {
        return new ad(this, str, z, z2, iVar, field, aVar, C.fp(aVar.getRawType()));
    }

    private l dT(i iVar, Field field, com.google.gson.b.a aVar) {
        d dVar = (d) field.getAnnotation(d.class);
        if (dVar != null) {
            l dL = r.dL(this.dj, iVar, aVar, dVar);
            if (dL != null) {
                return dL;
            }
        }
        return iVar.gT(aVar);
    }

    private Map dU(i iVar, com.google.gson.b.a aVar, Class cls) {
        Map linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type fs = aVar.fs();
        while (cls != Object.class) {
            Field[] declaredFields = cls.getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                boolean dO = dO(field, true);
                boolean dO2 = dO(field, false);
                if (!dO) {
                    if (!dO2) {
                        continue;
                        i++;
                    }
                }
                field.setAccessible(true);
                Type resolve = z.resolve(aVar.fs(), cls, field.getGenericType());
                p dS = dS(iVar, field, dQ(field), com.google.gson.b.a.ft(resolve), dO, dO2);
                dS = (p) linkedHashMap.put(dS.name, dS);
                if (dS == null) {
                    i++;
                } else {
                    throw new IllegalArgumentException(fs + " declares multiple JSON fields named " + dS.name);
                }
            }
            aVar = com.google.gson.b.a.ft(z.resolve(aVar.fs(), cls, cls.getGenericSuperclass()));
            cls = aVar.getRawType();
        }
        return linkedHashMap;
    }

    public l cX(i iVar, com.google.gson.b.a aVar) {
        Class rawType = aVar.getRawType();
        return Object.class.isAssignableFrom(rawType) ? new R(this.dj.ez(aVar), dU(iVar, aVar, rawType)) : null;
    }

    public boolean dO(Field field, boolean z) {
        return dP(field, z, this.dl);
    }
}
