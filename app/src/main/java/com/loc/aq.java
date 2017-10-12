package com.loc;

import android.text.TextUtils;
import java.net.Proxy;
import java.util.Map;

/* compiled from: Request */
public abstract class aq {
    int a;
    int b;
    Proxy c;

    public abstract Map<String, String> a();

    public abstract Map<String, String> b();

    public abstract String c();

    public aq() {
        this.a = 20000;
        this.b = 20000;
        this.c = null;
    }

    String d() {
        byte[] f = f();
        if (f == null || f.length == 0) {
            return c();
        }
        Map b = b();
        if (b == null) {
            return c();
        }
        String a = ao.a(b);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(c()).append("?").append(a);
        return stringBuffer.toString();
    }

    byte[] e() {
        byte[] f = f();
        if (f != null && f.length != 0) {
            return f;
        }
        String a = ao.a(b());
        try {
            if (TextUtils.isEmpty(a)) {
                return f;
            }
            return a.getBytes("UTF-8");
        } catch (Throwable e) {
            byte[] bytes = a.getBytes();
            v.a(e, "Request", "getConnectionDatas");
            return bytes;
        }
    }

    public final void a(int i) {
        this.a = i;
    }

    public final void b(int i) {
        this.b = i;
    }

    public byte[] f() {
        return null;
    }

    public final void a(Proxy proxy) {
        this.c = proxy;
    }
}
