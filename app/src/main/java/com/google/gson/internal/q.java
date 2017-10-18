package com.google.gson.internal;

import java.lang.reflect.Type;

class q implements n {
    private final E ft = E.fq();
    final /* synthetic */ Class fu;
    final /* synthetic */ Type fv;
    final /* synthetic */ a fw;

    q(a aVar, Class cls, Type type) {
        this.fw = aVar;
        this.fu = cls;
        this.fv = type;
    }

    public Object eY() {
        try {
            return this.ft.eX(this.fu);
        } catch (Throwable e) {
            throw new RuntimeException("Unable to invoke no-args constructor for " + this.fv + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e);
        }
    }
}
