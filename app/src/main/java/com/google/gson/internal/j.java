package com.google.gson.internal;

import com.google.gson.t;
import java.lang.reflect.Type;

class j implements n {
    final /* synthetic */ t fl;
    final /* synthetic */ Type fm;
    final /* synthetic */ a fn;

    j(a aVar, t tVar, Type type) {
        this.fn = aVar;
        this.fl = tVar;
        this.fm = type;
    }

    public Object eY() {
        return this.fl.hu(this.fm);
    }
}
