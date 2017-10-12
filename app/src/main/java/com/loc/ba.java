package com.loc;

import java.util.Locale;

/* compiled from: Cgi */
public class ba {
    public String a;
    public String b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;

    protected ba(int i) {
        this.a = "";
        this.b = "";
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = -113;
        this.k = 9;
        this.k = i;
    }

    public String toString() {
        String str = "unknown";
        switch (this.k) {
            case 1:
                return String.format(Locale.US, "GSM lac=%d, cid=%d, mnc=%s", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), this.b});
            case 2:
                return String.format(Locale.US, "CDMA bid=%d, nid=%d, sid=%d", new Object[]{Integer.valueOf(this.i), Integer.valueOf(this.h), Integer.valueOf(this.g)});
            default:
                return str;
        }
    }

    public boolean a(ba baVar) {
        if (baVar == null) {
            return false;
        }
        switch (baVar.k) {
            case 1:
                return this.k == 1 && baVar.c == this.c && baVar.d == this.d && baVar.b != null && baVar.b.equals(this.b);
            case 2:
                return this.k == 2 && baVar.i == this.i && baVar.h == this.h && baVar.g == this.g;
            default:
                return false;
        }
    }
}
