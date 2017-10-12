package com.loc;

/* compiled from: DynamicSDKFile */
public class aj {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;

    /* compiled from: DynamicSDKFile */
    static class a {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;

        public a(String str, String str2, String str3, String str4, String str5) {
            this.f = "copy";
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
            this.e = str5;
        }

        public a a(String str) {
            this.f = str;
            return this;
        }

        aj a() {
            return new aj();
        }
    }

    private aj(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar.c;
        this.d = aVar.d;
        this.e = aVar.e;
        this.f = aVar.f;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public String e() {
        return this.e;
    }

    public String f() {
        return this.f;
    }

    public void a(String str) {
        this.f = str;
    }
}
