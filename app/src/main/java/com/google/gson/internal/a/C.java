package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;

class C implements m {
    final /* synthetic */ Class dr;
    final /* synthetic */ Class ds;
    final /* synthetic */ l dt;

    C(Class cls, Class cls2, l lVar) {
        this.dr = cls;
        this.ds = cls2;
        this.dt = lVar;
    }

    public l cX(i iVar, a aVar) {
        Class rawType = aVar.getRawType();
        return (rawType == this.dr || rawType == this.ds) ? this.dt : null;
    }

    public String toString() {
        return "Factory[type=" + this.ds.getName() + "+" + this.dr.getName() + ",adapter=" + this.dt + "]";
    }
}
