package com.google.gson.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class m implements n {
    final /* synthetic */ Constructor fo;
    final /* synthetic */ a fp;

    m(a aVar, Constructor constructor) {
        this.fp = aVar;
        this.fo = constructor;
    }

    public Object eY() {
        try {
            return this.fo.newInstance(null);
        } catch (Throwable e) {
            throw new RuntimeException("Failed to invoke " + this.fo + " with no args", e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException("Failed to invoke " + this.fo + " with no args", e2.getTargetException());
        } catch (IllegalAccessException e3) {
            throw new AssertionError(e3);
        }
    }
}
