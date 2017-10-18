package com.loc;

import android.content.Context;
import android.net.NetworkInfo;
import android.text.TextUtils;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ba {
    private static ba rq = null;
    x rr = null;
    String rs = null;
    o rt = null;
    be ru = null;
    private long rv = 0;
    private int rw = H.lM;
    private int rx = H.lM;

    private ba(Context context) {
        try {
            this.rr = new u("loc", "2.2.0", "AMAP_Location_SDK_Android 2.2.0").mm(H.mQ()).mn();
        } catch (i e) {
            e.printStackTrace();
        }
        this.rs = aN.rx(context, this.rr, new HashMap(), true);
        this.rt = o.lR();
    }

    public static synchronized ba tm(Context context) {
        ba baVar;
        synchronized (ba.class) {
            if (rq == null) {
                rq = new ba(context);
            }
            baVar = rq;
        }
        return baVar;
    }

    public static int tp(NetworkInfo networkInfo) {
        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) ? networkInfo.getType() : -1;
    }

    public static String[] tq(JSONObject jSONObject) {
        int i = 1;
        CharSequence charSequence = null;
        String[] strArr = new String[]{null, null, null, null, null};
        if (jSONObject != null) {
            try {
                CharSequence string = !bq.wb(jSONObject, "key") ? null : jSONObject.getString("key");
                CharSequence string2 = !bq.wb(jSONObject, "X-INFO") ? null : jSONObject.getString("X-INFO");
                if (bq.wb(jSONObject, "User-Agent")) {
                    charSequence = jSONObject.getString("User-Agent");
                }
                if (TextUtils.isEmpty(string)) {
                    i = 0;
                } else if (TextUtils.isEmpty(string2)) {
                    i = 0;
                } else if (TextUtils.isEmpty(charSequence)) {
                    i = 0;
                }
                if (i != 0) {
                    strArr[0] = "true";
                    strArr[1] = string;
                    strArr[2] = string2;
                    strArr[3] = "";
                    strArr[4] = charSequence;
                }
            } catch (Exception e) {
            }
            if (TextUtils.isEmpty(strArr[0]) || !strArr[0].equals("true")) {
                strArr[0] = "false";
            }
        } else {
            strArr[0] = "false";
        }
        return strArr;
    }

    public byte[] tn(Context context, JSONObject jSONObject, aS aSVar, String str) {
        if (bq.wb(jSONObject, "httptimeout")) {
            try {
                this.rw = jSONObject.getInt("httptimeout");
            } catch (JSONException e) {
            }
        }
        if (tp(bq.vY(context)) == -1) {
            return null;
        }
        Map hashMap = new HashMap();
        aX qVar = new q();
        hashMap.clear();
        hashMap.put("Content-Type", "application/octet-stream");
        hashMap.put("Accept-Encoding", "gzip");
        hashMap.put("gzipped", "1");
        hashMap.put("Connection", "Keep-Alive");
        hashMap.put("User-Agent", "AMAP_Location_SDK_Android 2.2.0");
        hashMap.put("X-INFO", this.rs);
        hashMap.put("KEY", Y.nN(context));
        hashMap.put("enginever", "4.2");
        String rA = aN.rA();
        String rB = aN.rB(context, rA, "key=" + Y.nN(context));
        hashMap.put("ts", rA);
        hashMap.put("scode", rB);
        hashMap.put("platinfo", String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s", new Object[]{"2.2.0", "loc"}));
        hashMap.put("logversion", "2.1");
        hashMap.put("encr", "1");
        qVar.ma(hashMap);
        qVar.me(str);
        qVar.mf(bq.vS(aSVar.rV()));
        qVar.tl(ai.oo(context));
        qVar.tj(this.rw);
        qVar.tk(this.rw);
        return this.rt.lU(qVar);
    }

    public String to(byte[] bArr, Context context, String str, boolean z) {
        if (tp(bq.vY(context)) == -1) {
            return null;
        }
        byte[] lT;
        String str2;
        Map hashMap = new HashMap();
        aX qVar = new q();
        hashMap.clear();
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        hashMap.put("Connection", "Keep-Alive");
        if (z) {
            hashMap.put("Accept-Encoding", "gzip");
            hashMap.put("User-Agent", "AMAP_Location_SDK_Android 2.2.0");
            hashMap.put("platinfo", String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s", new Object[]{"2.2.0", "loc"}));
            hashMap.put("logversion", "2.1");
        }
        qVar.ma(hashMap);
        qVar.me(str);
        qVar.mf(bArr);
        qVar.tl(ai.oo(context));
        qVar.tj(H.lM);
        qVar.tk(H.lM);
        if (z) {
            lT = this.rt.lT(qVar);
        } else {
            try {
                lT = this.rt.lU(qVar);
            } catch (i e) {
                e.printStackTrace();
                str2 = null;
            } catch (Throwable th) {
                th.printStackTrace();
                str2 = null;
            }
        }
        str2 = new String(lT, "utf-8");
        return str2;
    }

    public HttpURLConnection tr(Context context, String str, HashMap hashMap, byte[] bArr) {
        boolean z = false;
        try {
            if (tp(bq.vY(context)) == -1) {
                return null;
            }
            aX qVar = new q();
            qVar.ma(hashMap);
            qVar.me(str);
            qVar.mf(bArr);
            qVar.tl(ai.oo(context));
            qVar.tj(H.lM);
            qVar.tk(H.lM);
            if (str.toLowerCase(Locale.US).startsWith("https")) {
                z = true;
            }
            return this.rt.lS(qVar, z);
        } catch (Throwable th) {
            return null;
        }
    }
}
