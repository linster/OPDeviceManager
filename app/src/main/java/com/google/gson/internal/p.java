package com.google.gson.internal;

import java.lang.reflect.Method;

class p extends E {
    final /* synthetic */ Method fr;
    final /* synthetic */ Object fs;

    p(Method method, Object obj) {
        this.fr = method;
        this.fs = obj;
    }

    public Object eX(Class cls) {
        return this.fr.invoke(this.fs, new Object[]{cls});
    }
}
