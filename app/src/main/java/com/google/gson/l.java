package com.google.gson;

import com.google.gson.internal.a.o;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

public abstract class l {
    public abstract Object cR(b bVar);

    public abstract void cT(a aVar, Object obj);

    public final b hj(Object obj) {
        try {
            a oVar = new o();
            cT(oVar, obj);
            return oVar.get();
        } catch (Throwable e) {
            throw new JsonIOException(e);
        }
    }
}
