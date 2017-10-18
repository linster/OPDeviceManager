package com.loc;

public class D {
    protected static D lr;

    public static void mF(Throwable th, String str, String str2) {
        th.printStackTrace();
        if (lr != null) {
            lr.mG(th, 1, str, str2);
        }
    }

    protected void mG(Throwable th, int i, String str, String str2) {
    }
}
