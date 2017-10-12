package com.loc;

import java.util.HashMap;
import java.util.Map;

/* compiled from: AuthRequest */
class u extends aq {
    private Map<String, String> d;
    private String e;
    private Map<String, String> f;

    u() {
        this.d = new HashMap();
        this.f = new HashMap();
    }

    void a(Map<String, String> map) {
        this.d.clear();
        this.d.putAll(map);
    }

    void a(String str) {
        this.e = str;
    }

    void b(Map<String, String> map) {
        this.f.clear();
        this.f.putAll(map);
    }

    public String c() {
        return this.e;
    }

    public Map<String, String> a() {
        return this.d;
    }

    public Map<String, String> b() {
        return this.f;
    }
}
