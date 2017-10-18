package com.google.gson.internal;

import com.google.gson.t;
import java.lang.reflect.Type;

class w implements n {
    final /* synthetic */ t fJ;
    final /* synthetic */ Type fK;
    final /* synthetic */ a fL;

    w(a aVar, t tVar, Type type) {
        this.fL = aVar;
        this.fJ = tVar;
        this.fK = type;
    }

    public Object eY() {
        return this.fJ.hu(this.fK);
    }
}
