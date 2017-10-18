package com.loc;

public class bo {
    private String a;
    private String sr;
    private String ss;
    private String st;
    private String su;
    private String sv;

    private bo(E e) {
        this.a = e.a;
        this.sr = e.ls;
        this.ss = e.lt;
        this.st = e.lu;
        this.su = e.lv;
        this.sv = e.lw;
    }

    public String vt() {
        return this.a;
    }

    public String vu() {
        return this.sr;
    }

    public String vv() {
        return this.ss;
    }

    public String vw() {
        return this.st;
    }

    public String vx() {
        return this.su;
    }

    public String vy() {
        return this.sv;
    }

    public void vz(String str) {
        this.sv = str;
    }
}
