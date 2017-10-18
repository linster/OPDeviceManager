package com.loc;

import android.content.Context;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/* compiled from: AuthManager */
public class k {
    public static int a;
    public static String b;
    private static s c;
    private static String d;
    private static String e;

    static {
        a = -1;
        b = "";
        d = "http://apiinit.amap.com/v3/log/init";
        e = null;
    }

    private static boolean a(Context context, s sVar, boolean z) {
        c = sVar;
        try {
            String a = a();
            Map hashMap = new HashMap();
            hashMap.put("Content-Type", "application/x-www-form-urlencoded");
            hashMap.put("Accept-Encoding", "gzip");
            hashMap.put("Connection", "Keep-Alive");
            hashMap.put("User-Agent", c.b);
            hashMap.put("X-INFO", l.a(context, c, null, z));
            hashMap.put("logversion", "2.1");
            hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{c.a, c.c}));
            am a2 = am.a();
            aq uVar = new u();
            uVar.a(q.a(context));
            uVar.a(hashMap);
            uVar.b(a(context));
            uVar.a(a);
            return a(a2.b(uVar));
        } catch (Throwable th) {
            v.a(th, "Auth", "getAuth");
            return true;
        }
    }

    public static synchronized boolean a(Context context, s sVar) {
        boolean a;
        synchronized (k.class) {
            a = a(context, sVar, true);
        }
        return a;
    }

    public static synchronized boolean b(Context context, s sVar) {
        boolean a;
        synchronized (k.class) {
            a = a(context, sVar, false);
        }
        return a;
    }

    public static void a(String str) {
        j.a(str);
    }

    private static String a() {
        return d;
    }

    private static boolean a(byte[] bArr) {
        if (bArr == null) {
            return true;
        }
        String str;
        try {
            str = new String(bArr, "UTF-8");
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("status")) {
                    int i = jSONObject.getInt("status");
                    if (i == 1) {
                        a = 1;
                    } else if (i == 0) {
                        a = 0;
                    }
                }
                if (jSONObject.has("info")) {
                    b = jSONObject.getString("info");
                }
                if (a == 0) {
                    Log.i("AuthFailure", b);
                }
                return a == 1;
            } catch (Throwable e) {
                v.a(e, "Auth", "lData");
                return false;
            } catch (Throwable e2) {
                v.a(e2, "Auth", "lData");
                return false;
            }
        } catch (UnsupportedEncodingException e3) {
            str = new String(bArr);
        }
    }

    private static Map<String, String> a(Context context) {
        Map<String, String> hashMap = new HashMap();
        try {
            hashMap.put("resType", "json");
            hashMap.put("encode", "UTF-8");
            String a = l.a();
            hashMap.put("ts", a);
            hashMap.put("key", j.e(context));
            hashMap.put("scode", l.a(context, a, t.a("resType=json&encode=UTF-8&key=" + j.e(context))));
        } catch (Throwable th) {
            v.a(th, "Auth", "gParams");
        }
        return hashMap;
    }
}
