package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.text.TextUtils;

import com.loc.m.a.b;
import com.loc.m.a.c;

import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthUtil */
public class bp {
    private static Context a;
    private static String b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;
    private static String g;
    private static boolean h;
    private static long i;
    private static long j;
    private static boolean k;
    private static int l;
    private static boolean m;
    private static int n;
    private static boolean o;
    private static boolean p;
    private static String q;
    private static String r;
    private static String s;
    private static String t;
    private static SharedPreferences u;
    private static Editor v;
    private static Method w;

    /* compiled from: AuthUtil */
    static class a {
        String a;
        String b;
        String c;

        a() {
            this.a = "0";
            this.b = "0";
            this.c = "0";
        }
    }

    public static synchronized boolean a(Context context) {
        boolean z;
        synchronized (bp.class) {
            a = context;
            z = false;
            try {
                com.loc.m.a a = m.a(context, c.a("2.2.0"), "callamap;fast;sdkupdate;sdkcoordinate;locate");
                if (a != null) {
                    z = a(a);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return z;
    }

    static {
        b = "\u63d0\u793a\u4fe1\u606f";
        c = "\u786e\u8ba4";
        d = "\u53d6\u6d88";
        e = "";
        f = "";
        g = "";
        h = false;
        i = 0;
        j = 0;
        k = false;
        l = 0;
        m = false;
        n = 0;
        o = false;
        p = true;
        q = "1";
        t = "0";
        u = null;
        v = null;
    }

    public static boolean a() {
        return h;
    }

    public static long b() {
        return i;
    }

    public static long c() {
        return j;
    }

    public static boolean d() {
        return k;
    }

    public static int e() {
        return l;
    }

    public static boolean f() {
        return m;
    }

    public static int g() {
        return n;
    }

    public static boolean h() {
        return o;
    }

    public static boolean i() {
        av.a = p;
        return p;
    }

    public static String j() {
        return b;
    }

    public static String k() {
        return c;
    }

    public static String l() {
        return d;
    }

    public static String m() {
        return e;
    }

    public static String n() {
        return f;
    }

    public static String o() {
        return g;
    }

    private static String a(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        String str2 = "";
        try {
            if (jSONObject.has(str) && !jSONObject.getString(str).equals("[]")) {
                str2 = jSONObject.optString(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str2;
    }

    private static boolean a(com.loc.m.a aVar) {
        try {
            boolean z;
            JSONObject jSONObject = aVar.b;
            if (jSONObject != null && jSONObject.has("callamapflag")) {
                q = a(jSONObject, "callamapflag");
            }
            JSONObject jSONObject2 = aVar.c;
            if (jSONObject2 != null) {
                if (jSONObject2.has("f")) {
                    t = a(jSONObject2, "f");
                    if ("1".equals(t)) {
                        long b = b(a);
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        if (!(elapsedRealtime - b <= 3600000)) {
                            a(a, elapsedRealtime);
                        }
                        if (elapsedRealtime <= b) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (!z) {
                            if (!(elapsedRealtime - b >= 3600000)) {
                                t = "0";
                            }
                        }
                        if (elapsedRealtime >= b) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (!z) {
                            t = "0";
                            a(a, elapsedRealtime);
                        }
                    } else {
                        t = "0";
                    }
                }
                if (jSONObject2.has("a")) {
                    b = a(jSONObject2, "a");
                }
                if (jSONObject2.has("o")) {
                    c = a(jSONObject2, "o");
                }
                if (jSONObject2.has("c")) {
                    d = a(jSONObject2, "c");
                }
                if (jSONObject2.has("i")) {
                    e = a(jSONObject2, "i");
                }
                if (jSONObject2.has("u")) {
                    f = a(jSONObject2, "u");
                }
                if (jSONObject2.has("t")) {
                    g = a(jSONObject2, "t");
                }
                if ("".equals(e) || e == null) {
                    if ("".equals(f) || f == null) {
                        t = "0";
                    }
                }
            }
            s a = c.a("2.2.0");
            c cVar = aVar.e;
            if (cVar == null) {
                new aa(a, null, a).a();
            } else {
                Object obj = cVar.b;
                Object obj2 = cVar.a;
                Object obj3 = cVar.c;
                if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2) || TextUtils.isEmpty(obj3)) {
                    new aa(a, null, a).a();
                } else {
                    new aa(a, new ab(obj2, obj, obj3), a).a();
                }
            }
            b bVar = aVar.f;
            if (bVar != null) {
                r = bVar.a;
                s = bVar.b;
                if (!(TextUtils.isEmpty(r) || TextUtils.isEmpty(s))) {
                    new r(a, "loc", r, s).a();
                }
            }
            jSONObject2 = aVar.a;
            if (jSONObject2 != null) {
                a b2 = b(jSONObject2, "fs");
                if (b2 != null) {
                    k = b2.c.equals("1");
                    try {
                        l = Integer.parseInt(b2.b);
                    } catch (Exception e) {
                    }
                }
                b2 = b(jSONObject2, "us");
                if (b2 != null) {
                    m = b2.c.equals("1");
                    if (b2.a.equals("0")) {
                        z = false;
                    } else {
                        z = true;
                    }
                    o = z;
                    try {
                        n = Integer.parseInt(b2.b);
                    } catch (Exception e2) {
                    }
                }
                a b3 = b(jSONObject2, "rs");
                if (b3 != null) {
                    h = b3.c.equals("1");
                    if (h) {
                        j = br.b();
                    }
                    try {
                        i = (long) (Integer.parseInt(b3.b) * 1000);
                    } catch (Exception e3) {
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            t = "0";
            th.printStackTrace();
            return false;
        }
    }

    private static a b(JSONObject jSONObject, String str) {
        a aVar = null;
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                    if (jSONObject2 != null) {
                        a aVar2 = new a();
                        try {
                            if (jSONObject2.has("b")) {
                                aVar2.a = a(jSONObject2, "b");
                            }
                            if (jSONObject2.has("t")) {
                                aVar2.b = a(jSONObject2, "t");
                            }
                            if (jSONObject2.has("st")) {
                                aVar2.c = a(jSONObject2, "st");
                                aVar = aVar2;
                            } else {
                                aVar = aVar2;
                            }
                        } catch (JSONException e) {
                            return aVar2;
                        }
                    }
                }
            } catch (JSONException e2) {
                return null;
            }
        }
        return aVar;
    }

    public static boolean p() {
        return "1".equals(q);
    }

    public static synchronized boolean q() {
        boolean equals;
        synchronized (bp.class) {
            equals = "1".equals(t);
        }
        return equals;
    }

    public static synchronized void a(String str) {
        synchronized (bp.class) {
            t = str;
        }
    }

    public static void a(Context context, long j) {
        try {
            if (u == null) {
                u = context.getSharedPreferences("abcd", 0);
            }
            if (v == null) {
                v = u.edit();
            }
            v.putLong("abc", j);
            a(v);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static long b(Context context) {
        try {
            return context.getSharedPreferences("abcd", 0).getLong("abc", 0);
        } catch (Throwable th) {
            return 0;
        }
    }

    private static void a(Editor editor) {
        if (editor != null) {
            if (VERSION.SDK_INT < 9) {
                editor.commit();
            } else {
                try {
                    if (w == null) {
                        w = Editor.class.getDeclaredMethod("apply", new Class[0]);
                    }
                    w.invoke(editor, new Object[0]);
                } catch (Throwable th) {
                    th.printStackTrace();
                    editor.commit();
                }
            }
        }
    }
}
