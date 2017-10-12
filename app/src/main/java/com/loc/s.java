package com.loc;

/* compiled from: SDKInfo */
public class s {
    String a;
    String b;
    String c;
    private boolean d;
    private String e;
    private String[] f;

    /* compiled from: SDKInfo */
    public static class a {
        private String a;
        private String b;
        private String c;
        private boolean d;
        private String e;
        private String[] f;

        public a(String str, String str2, String str3) {
            this.d = true;
            this.e = "standard";
            this.f = null;
            this.a = str2;
            this.c = str3;
            this.b = str;
        }

        public a a(String[] strArr) {
            this.f = (String[]) strArr.clone();
            return this;
        }

        public s a() throws i {
            if (this.f != null) {
                return new s();
            }
            throw new i("sdk packages is null");
        }
    }

    private s(a aVar) {
        this.d = true;
        this.e = "standard";
        this.f = null;
        this.a = aVar.a;
        this.c = aVar.b;
        this.b = aVar.c;
        this.d = aVar.d;
        this.e = aVar.e;
        this.f = aVar.f;
    }

    public String a() {
        return this.c;
    }

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public String d() {
        return this.e;
    }
}
