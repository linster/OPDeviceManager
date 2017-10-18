package com.google.gson.internal;

import java.lang.reflect.Method;

class G extends E {
    final /* synthetic */ Method fT;
    final /* synthetic */ int fU;

    G(Method method, int i) {
        this.fT = method;
        this.fU = i;
    }

    public Object eX(Class cls) {
        return this.fT.invoke(null, new Object[]{cls, Integer.valueOf(this.fU)});
    }
}
