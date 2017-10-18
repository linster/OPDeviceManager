package com.google.gson.internal;

public final class g {
    public static Object eZ(Object obj) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException();
    }

    public static void fa(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }
}
