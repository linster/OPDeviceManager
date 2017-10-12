package com.loc;

import android.content.Context;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.loc.s.a;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LocNetManager */
public class bl {
    private static bl e;
    s a;
    String b;
    am c;
    an d;
    private long f;
    private int g;
    private int h;

    static {
        e = null;
    }

    private bl(Context context) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.f = 0;
        this.g = c.k;
        this.h = c.k;
        try {
            this.a = new a("loc", "2.2.0", "AMAP_Location_SDK_Android 2.2.0").a(c.b()).a();
        } catch (i e) {
            e.printStackTrace();
        }
        this.b = l.a(context, this.a, new HashMap(), true);
        this.c = am.a();
    }

    public static synchronized bl a(Context context) {
        bl blVar;
        synchronized (bl.class) {
            if (e == null) {
                e = new bl(context);
            }
            blVar = e;
        }
        return blVar;
    }

    public byte[] a(Context context, JSONObject jSONObject, bo boVar, String str) throws Exception {
        if (br.a(jSONObject, "httptimeout")) {
            try {
                this.g = jSONObject.getInt("httptimeout");
            } catch (JSONException e) {
            }
        }
        if (a(br.c(context)) == -1) {
            return null;
        }
        Map hashMap = new HashMap();
        aq bmVar = new bm();
        hashMap.clear();
        hashMap.put("Content-Type", "application/octet-stream");
        hashMap.put("Accept-Encoding", "gzip");
        hashMap.put("gzipped", "1");
        hashMap.put("Connection", "Keep-Alive");
        hashMap.put("User-Agent", "AMAP_Location_SDK_Android 2.2.0");
        hashMap.put("X-INFO", this.b);
        hashMap.put("KEY", j.e(context));
        hashMap.put("enginever", "4.2");
        String a = l.a();
        String a2 = l.a(context, a, "key=" + j.e(context));
        hashMap.put("ts", a);
        hashMap.put("scode", a2);
        hashMap.put("platinfo", String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s", new Object[]{"2.2.0", "loc"}));
        hashMap.put("logversion", "2.1");
        hashMap.put("encr", "1");
        bmVar.a(hashMap);
        bmVar.a(str);
        bmVar.a(br.a(boVar.a()));
        bmVar.a(q.a(context));
        bmVar.a(this.g);
        bmVar.b(this.g);
        return this.c.b(bmVar);
    }

    public String a(byte[] bArr, Context context, String str, boolean z) {
        if (a(br.c(context)) == -1) {
            return null;
        }
        byte[] a;
        String str2;
        Map hashMap = new HashMap();
        aq bmVar = new bm();
        hashMap.clear();
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        hashMap.put("Connection", "Keep-Alive");
        if (z) {
            hashMap.put("Accept-Encoding", "gzip");
            hashMap.put("User-Agent", "AMAP_Location_SDK_Android 2.2.0");
            hashMap.put("platinfo", String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s", new Object[]{"2.2.0", "loc"}));
            hashMap.put("logversion", "2.1");
        }
        bmVar.a(hashMap);
        bmVar.a(str);
        bmVar.a(bArr);
        bmVar.a(q.a(context));
        bmVar.a(c.k);
        bmVar.b(c.k);
        if (z) {
            a = this.c.a(bmVar);
        } else {
            try {
                a = this.c.b(bmVar);
            } catch (i e) {
                e.printStackTrace();
                str2 = null;
            } catch (Throwable th) {
                th.printStackTrace();
                str2 = null;
            }
        }
        str2 = new String(a, "utf-8");
        return str2;
    }

    public static int a(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static String[] a(JSONObject jSONObject) {
        int i = 1;
        CharSequence charSequence = null;
        String[] strArr = new String[]{null, null, null, null, null};
        if (jSONObject != null) {
            try {
                CharSequence string;
                CharSequence string2;
                if (br.a(jSONObject, "key")) {
                    string = jSONObject.getString("key");
                } else {
                    string = null;
                }
                if (br.a(jSONObject, "X-INFO")) {
                    string2 = jSONObject.getString("X-INFO");
                } else {
                    string2 = null;
                }
                if (br.a(jSONObject, "User-Agent")) {
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

    public HttpURLConnection a(Context context, String str, HashMap<String, String> hashMap, byte[] bArr) throws Exception {
        boolean z = false;
        try {
            if (a(br.c(context)) == -1) {
                return null;
            }
            aq bmVar = new bm();
            bmVar.a((Map) hashMap);
            bmVar.a(str);
            bmVar.a(bArr);
            bmVar.a(q.a(context));
            bmVar.a(c.k);
            bmVar.b(c.k);
            if (str.toLowerCase(Locale.US).startsWith("https")) {
                z = true;
            }
            return this.c.a(bmVar, z);
        } catch (Throwable th) {
            return null;
        }
    }
}
