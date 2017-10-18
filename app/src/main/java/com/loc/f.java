package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.text.TextUtils;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

public class f {
    private static long i = 0;
    private static long j = 0;
    private static Context ka;
    private static String kb = "提示信息";
    private static String kc = "确认";
    private static String kd = "取消";
    private static String ke = "";
    private static String kf = "";
    private static String kg = "";
    private static boolean kh = false;
    private static boolean ki = false;
    private static int kj = 0;
    private static boolean kk = false;
    private static int kl = 0;
    private static boolean km = false;
    private static boolean kn = true;
    private static String ko = "1";
    private static String kp;
    private static String kq;
    private static String kr = "0";
    private static SharedPreferences ks = null;
    private static Editor kt = null;
    private static Method ku;

    public static void kA(Context context, long j) {
        try {
            if (ks == null) {
                ks = context.getSharedPreferences("abcd", 0);
            }
            if (kt == null) {
                kt = ks.edit();
            }
            kt.putLong("abc", j);
            kC(kt);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static long kB(Context context) {
        try {
            return context.getSharedPreferences("abcd", 0).getLong("abc", 0);
        } catch (Throwable th) {
            return 0;
        }
    }

    private static void kC(Editor editor) {
        if (editor != null) {
            if (VERSION.SDK_INT < 9) {
                editor.commit();
            } else {
                try {
                    if (ku == null) {
                        ku = Editor.class.getDeclaredMethod("apply", new Class[0]);
                    }
                    ku.invoke(editor, new Object[0]);
                } catch (Throwable th) {
                    th.printStackTrace();
                    editor.commit();
                }
            }
        }
    }

    public static synchronized boolean ke(Context context) {
        boolean z;
        synchronized (f.class) {
            ka = context;
            z = false;
            try {
                aE vf = bj.vf(context, H.mY("2.2.0"), "callamap;fast;sdkupdate;sdkcoordinate;locate");
                if (vf != null) {
                    z = kv(vf);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return z;
    }

    public static boolean kf() {
        return kh;
    }

    public static long kg() {
        return i;
    }

    public static long kh() {
        return j;
    }

    public static boolean ki() {
        return ki;
    }

    public static int kj() {
        return kj;
    }

    public static boolean kk() {
        return kk;
    }

    public static int kl() {
        return kl;
    }

    public static boolean km() {
        return km;
    }

    public static boolean kn() {
        bN.tL = kn;
        return kn;
    }

    public static String ko() {
        return kb;
    }

    public static String kp() {
        return kc;
    }

    public static String kq() {
        return kd;
    }

    public static String kr() {
        return ke;
    }

    public static String ks() {
        return kf;
    }

    public static String kt() {
        return kg;
    }

    private static String ku(JSONObject jSONObject, String str) {
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

    private static boolean kv(aE aEVar) {
        try {
            JSONObject jSONObject = aEVar.oz;
            if (jSONObject != null && jSONObject.has("callamapflag")) {
                ko = ku(jSONObject, "callamapflag");
            }
            JSONObject jSONObject2 = aEVar.oA;
            if (jSONObject2 != null) {
                if (jSONObject2.has("f")) {
                    kr = ku(jSONObject2, "f");
                    if ("1".equals(kr)) {
                        long kB = kB(ka);
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        if (!(elapsedRealtime - kB <= 3600000)) {
                            kA(ka, elapsedRealtime);
                        }
                        if (!(elapsedRealtime <= kB)) {
                            if (!(elapsedRealtime - kB >= 3600000)) {
                                kr = "0";
                            }
                        }
                        if (!(elapsedRealtime >= kB)) {
                            kr = "0";
                            kA(ka, elapsedRealtime);
                        }
                    } else {
                        kr = "0";
                    }
                }
                if (jSONObject2.has("a")) {
                    kb = ku(jSONObject2, "a");
                }
                if (jSONObject2.has("o")) {
                    kc = ku(jSONObject2, "o");
                }
                if (jSONObject2.has("c")) {
                    kd = ku(jSONObject2, "c");
                }
                if (jSONObject2.has("i")) {
                    ke = ku(jSONObject2, "i");
                }
                if (jSONObject2.has("u")) {
                    kf = ku(jSONObject2, "u");
                }
                if (jSONObject2.has("t")) {
                    kg = ku(jSONObject2, "t");
                }
                if ("".equals(ke) || ke == null) {
                    if ("".equals(kf) || kf == null) {
                        kr = "0";
                    }
                }
            }
            x mY = H.mY("2.2.0");
            z zVar = aEVar.oC;
            if (zVar == null) {
                new aP(ka, null, mY).rD();
            } else {
                Object obj = zVar.ln;
                Object obj2 = zVar.a;
                Object obj3 = zVar.lo;
                if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2) || TextUtils.isEmpty(obj3)) {
                    new aP(ka, null, mY).rD();
                } else {
                    new aP(ka, new a(obj2, obj, obj3), mY).rD();
                }
            }
            T t = aEVar.oD;
            if (t != null) {
                kp = t.a;
                kq = t.mr;
                if (!(TextUtils.isEmpty(kp) || TextUtils.isEmpty(kq))) {
                    new aw(ka, "loc", kp, kq).ph();
                }
            }
            jSONObject2 = aEVar.oy;
            if (jSONObject2 != null) {
                aK kw = kw(jSONObject2, "fs");
                if (kw != null) {
                    ki = kw.pG.equals("1");
                    try {
                        kj = Integer.parseInt(kw.pF);
                    } catch (Exception e) {
                    }
                }
                kw = kw(jSONObject2, "us");
                if (kw != null) {
                    kk = kw.pG.equals("1");
                    km = !kw.a.equals("0");
                    try {
                        kl = Integer.parseInt(kw.pF);
                    } catch (Exception e2) {
                    }
                }
                aK kw2 = kw(jSONObject2, "rs");
                if (kw2 != null) {
                    kh = kw2.pG.equals("1");
                    if (kh) {
                        j = bq.vM();
                    }
                    try {
                        i = (long) (Integer.parseInt(kw2.pF) * 1000);
                    } catch (Exception e3) {
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            kr = "0";
            th.printStackTrace();
            return false;
        }
    }

    private static aK kw(JSONObject jSONObject, String str) {
        aK aKVar = null;
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                    if (jSONObject2 != null) {
                        aK aKVar2 = new aK();
                        try {
                            if (jSONObject2.has("b")) {
                                aKVar2.a = ku(jSONObject2, "b");
                            }
                            if (jSONObject2.has("t")) {
                                aKVar2.pF = ku(jSONObject2, "t");
                            }
                            if (jSONObject2.has("st")) {
                                aKVar2.pG = ku(jSONObject2, "st");
                                aKVar = aKVar2;
                            } else {
                                aKVar = aKVar2;
                            }
                        } catch (JSONException e) {
                            return aKVar2;
                        }
                    }
                }
            } catch (JSONException e2) {
                return null;
            }
        }
        return aKVar;
    }

    public static boolean kx() {
        return "1".equals(ko);
    }

    public static synchronized boolean ky() {
        boolean equals;
        synchronized (f.class) {
            equals = "1".equals(kr);
        }
        return equals;
    }

    public static synchronized void kz(String str) {
        synchronized (f.class) {
            kr = str;
        }
    }
}
