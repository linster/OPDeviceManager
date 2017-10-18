package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.l;
import com.google.gson.m;

class i implements m {
    final /* synthetic */ Class cO;
    final /* synthetic */ Class cP;
    final /* synthetic */ l cQ;

    i(Class cls, Class cls2, l lVar) {
        this.cO = cls;
        this.cP = cls2;
        this.cQ = lVar;
    }

    public l cX(com.google.gson.i iVar, a aVar) {
        Class rawType = aVar.getRawType();
        return (rawType == this.cO || rawType == this.cP) ? this.cQ : null;
    }

    public String toString() {
        return "Factory[type=" + this.cO.getName() + "+" + this.cP.getName() + ",adapter=" + this.cQ + "]";
    }
}
