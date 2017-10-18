package com.loc;

class E {
    private String a;
    private String ls;
    private String lt;
    private String lu;
    private String lv;
    private String lw = "copy";

    public E(String str, String str2, String str3, String str4, String str5) {
        this.a = str;
        this.ls = str2;
        this.lt = str3;
        this.lu = str4;
        this.lv = str5;
    }

    public E mH(String str) {
        this.lw = str;
        return this;
    }

    bo mI() {
        return new bo();
    }
}
