package com.google.gson.internal;

import java.lang.reflect.Method;

class e extends E {
    final /* synthetic */ Method fh;

    e(Method method) {
        this.fh = method;
    }

    public Object eX(Class cls) {
        return this.fh.invoke(null, new Object[]{cls, Object.class});
    }
}
