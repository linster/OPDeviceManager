package com.google.gson.internal.a;

import com.google.gson.stream.a;
import com.google.gson.stream.b;

abstract class p {
    final boolean dd;
    final boolean de;
    final String name;

    protected p(String str, boolean z, boolean z2) {
        this.name = str;
        this.dd = z;
        this.de = z2;
    }

    abstract boolean dH(Object obj);

    abstract void dI(a aVar, Object obj);

    abstract void dJ(b bVar, Object obj);
}
