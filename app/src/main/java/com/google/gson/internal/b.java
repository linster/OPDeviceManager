package com.google.gson.internal;

import com.google.gson.a.c;
import com.google.gson.a.e;
import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.o;
import com.google.gson.r;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public final class b implements m, Cloneable {
    public static final b eR = new b();
    private double eS = -1.0d;
    private int eT = 136;
    private boolean eU = true;
    private boolean eV;
    private List eW = Collections.emptyList();
    private List eX = Collections.emptyList();

    private boolean eF(Class cls) {
        return Enum.class.isAssignableFrom(cls) ? false : cls.isAnonymousClass() || cls.isLocalClass();
    }

    private boolean eG(Class cls) {
        return cls.isMemberClass() && !eH(cls);
    }

    private boolean eH(Class cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    private boolean eI(c cVar, e eVar) {
        return eJ(cVar) && eK(eVar);
    }

    private boolean eJ(c cVar) {
        return cVar == null || cVar.value() <= this.eS;
    }

    private boolean eK(e eVar) {
        return eVar == null || eVar.value() > this.eS;
    }

    public l cX(i iVar, a aVar) {
        Class rawType = aVar.getRawType();
        boolean eE = eE(rawType, true);
        boolean eE2 = eE(rawType, false);
        return (eE || eE2) ? new s(this, eE2, eE, iVar, aVar) : null;
    }

    protected b clone() {
        try {
            return (b) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public boolean eD(Field field, boolean z) {
        if ((this.eT & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.eS != -1.0d && !eI((c) field.getAnnotation(c.class), (e) field.getAnnotation(e.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.eV) {
            com.google.gson.a.b bVar = (com.google.gson.a.b) field.getAnnotation(com.google.gson.a.b.class);
            if (bVar != null) {
                if (z) {
                    if (bVar.cP()) {
                    }
                } else if (bVar.cQ()) {
                }
            }
            return true;
        }
        if ((!this.eU && eG(field.getType())) || eF(field.getType())) {
            return true;
        }
        List<o> list = !z ? this.eX : this.eW;
        if (!list.isEmpty()) {
            r rVar = new r(field);
            for (o ho : list) {
                if (ho.ho(rVar)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean eE(Class cls, boolean z) {
        if (this.eS != -1.0d && !eI((c) cls.getAnnotation(c.class), (e) cls.getAnnotation(e.class))) {
            return true;
        }
        if ((!this.eU && eG(cls)) || eF(cls)) {
            return true;
        }
        for (o hp : !z ? this.eX : this.eW) {
            if (hp.hp(cls)) {
                return true;
            }
        }
        return false;
    }
}
