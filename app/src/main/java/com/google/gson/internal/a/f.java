package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;

class f implements m {
    final /* synthetic */ Class cM;
    final /* synthetic */ l cN;

    f(Class cls, l lVar) {
        this.cM = cls;
        this.cN = lVar;
    }

    public l cX(i iVar, a aVar) {
        return aVar.getRawType() != this.cM ? null : this.cN;
    }

    public String toString() {
        return "Factory[type=" + this.cM.getName() + ",adapter=" + this.cN + "]";
    }
}
