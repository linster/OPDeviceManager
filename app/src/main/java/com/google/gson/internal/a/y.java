package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;

class y implements m {
    final /* synthetic */ Class dm;
    final /* synthetic */ l dn;

    y(Class cls, l lVar) {
        this.dm = cls;
        this.dn = lVar;
    }

    public l cX(i iVar, a aVar) {
        return !this.dm.isAssignableFrom(aVar.getRawType()) ? null : this.dn;
    }

    public String toString() {
        return "Factory[typeHierarchy=" + this.dm.getName() + ",adapter=" + this.dn + "]";
    }
}
