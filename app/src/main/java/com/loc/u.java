package com.loc;

public class u {
    private String a;
    private String la;
    private String lb;
    private boolean lc = true;
    private String ld = "standard";
    private String[] le = null;

    public u(String str, String str2, String str3) {
        this.a = str2;
        this.lb = str3;
        this.la = str;
    }

    public u mm(String[] strArr) {
        this.le = (String[]) strArr.clone();
        return this;
    }

    public x mn() {
        if (this.le != null) {
            return new x();
        }
        throw new i("sdk packages is null");
    }
}
