package com.loc;

import java.util.Locale;

public class af {
    private String a;
    private int b;
    private String mP;
    private long mQ;

    public af(String str, long j, int i, String str2) {
        this.a = str;
        this.mQ = j;
        this.b = i;
        this.mP = str2;
    }

    public String of() {
        return this.a;
    }

    public int og() {
        return this.b;
    }

    public String toString() {
        return String.format(Locale.US, "##h=%s, n=%d, t=%d, ex=%s##", new Object[]{this.a, Integer.valueOf(this.b), Long.valueOf(this.mQ), this.mP});
    }
}
