package com.loc;

import android.content.Context;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class au {
    public static int a = -1;
    private static String nA = null;
    public static String nx = "";
    private static x ny;
    private static String nz = "http://apiinit.amap.com/v3/log/init";

    private static boolean oY(Context context, x xVar, boolean z) {
        ny = xVar;
        try {
            String pc = pc();
            Map hashMap = new HashMap();
            hashMap.put("Content-Type", "application/x-www-form-urlencoded");
            hashMap.put("Accept-Encoding", "gzip");
            hashMap.put("Connection", "Keep-Alive");
            hashMap.put("User-Agent", ny.lh);
            hashMap.put("X-INFO", aN.rx(context, ny, null, z));
            hashMap.put("logversion", "2.1");
            hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{ny.a, ny.li}));
            o lR = o.lR();
            aX bIVar = new bI();
            bIVar.tl(ai.oo(context));
            bIVar.xz(hashMap);
            bIVar.xB(pe(context));
            bIVar.xA(pc);
            return pd(lR.lU(bIVar));
        } catch (Throwable th) {
            D.mF(th, "Auth", "getAuth");
            return true;
        }
    }

    public static synchronized boolean oZ(Context context, x xVar) {
        boolean oY;
        synchronized (au.class) {
            oY = oY(context, xVar, true);
        }
        return oY;
    }

    public static synchronized boolean pa(Context context, x xVar) {
        boolean oY;
        synchronized (au.class) {
            oY = oY(context, xVar, false);
        }
        return oY;
    }

    public static void pb(String str) {
        Y.nL(str);
    }

    private static String pc() {
        return nz;
    }

    private static boolean pd(byte[] bArr) {
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
                    nx = jSONObject.getString("info");
                }
                if (a == 0) {
                    Log.i("AuthFailure", nx);
                }
                return a == 1;
            } catch (Throwable e) {
                D.mF(e, "Auth", "lData");
                return false;
            } catch (Throwable e2) {
                D.mF(e2, "Auth", "lData");
                return false;
            }
        } catch (UnsupportedEncodingException e3) {
            str = new String(bArr);
        }
    }

    private static Map pe(Context context) {
        Map hashMap = new HashMap();
        try {
            hashMap.put("resType", "json");
            hashMap.put("encode", "UTF-8");
            String rA = aN.rA();
            hashMap.put("ts", rA);
            hashMap.put("key", Y.nN(context));
            hashMap.put("scode", aN.rB(context, rA, bw.xc("resType=json&encode=UTF-8&key=" + Y.nN(context))));
        } catch (Throwable th) {
            D.mF(th, "Auth", "gParams");
        }
        return hashMap;
    }
}
