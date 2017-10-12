package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ConfigManager */
public class m {

    /* compiled from: ConfigManager */
    public static class a {
        public JSONObject a;
        public JSONObject b;
        public JSONObject c;
        public a d;
        public c e;
        public b f;

        /* compiled from: ConfigManager */
        public static class a {
            public boolean a;
            public boolean b;
        }

        /* compiled from: ConfigManager */
        public static class b {
            public String a;
            public String b;
        }

        /* compiled from: ConfigManager */
        public static class c {
            public String a;
            public String b;
            public String c;
        }
    }

    /* compiled from: ConfigManager */
    static class b extends aq {
        private Context d;
        private s e;
        private String f;

        b(Context context, s sVar, String str) {
            this.f = "";
            this.d = context;
            this.e = sVar;
            this.f = str;
        }

        public Map<String, String> a() {
            Map<String, String> hashMap = new HashMap();
            hashMap.put("User-Agent", this.e.c());
            hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{this.e.b(), this.e.a()}));
            hashMap.put("logversion", "2.0");
            return hashMap;
        }

        public Map<String, String> b() {
            Object l = n.l(this.d);
            if (!TextUtils.isEmpty(l)) {
                l = p.b(new StringBuilder(l).reverse().toString());
            }
            Map hashMap = new HashMap();
            hashMap.put("key", j.e(this.d));
            hashMap.put("opertype", this.f);
            hashMap.put("plattype", "android");
            hashMap.put("product", this.e.a());
            hashMap.put("version", this.e.b());
            hashMap.put("output", "json");
            hashMap.put("androidversion", VERSION.SDK_INT + "");
            hashMap.put("deviceId", l);
            hashMap.put("abitype", Build.CPU_ABI);
            hashMap.put("ext", this.e.d());
            String a = l.a();
            String a2 = l.a(this.d, a, t.b(hashMap));
            hashMap.put("ts", a);
            hashMap.put("scode", a2);
            return hashMap;
        }

        public String c() {
            return "https://restapi.amap.com/v3/config/resource?";
        }
    }

    public static a a(byte[] bArr) {
        boolean z = false;
        a aVar = new a();
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
                    if (!"1".equals(a(jSONObject, "status")) || !jSONObject.has("result")) {
                        return aVar;
                    }
                    jSONObject = jSONObject.getJSONObject("result");
                    if (jSONObject != null) {
                        boolean b;
                        JSONObject jSONObject2;
                        if (t.a(jSONObject, "exception")) {
                            b = b(jSONObject.getJSONObject("exception"));
                        } else {
                            b = false;
                        }
                        if (t.a(jSONObject, "common")) {
                            z = a(jSONObject.getJSONObject("common"));
                        }
                        a aVar2 = new a();
                        aVar2.a = b;
                        aVar2.b = z;
                        aVar.d = aVar2;
                        if (jSONObject.has("sdkupdate")) {
                            jSONObject2 = jSONObject.getJSONObject("sdkupdate");
                            c cVar = new c();
                            a(jSONObject2, cVar);
                            aVar.e = cVar;
                        }
                        if (t.a(jSONObject, "sdkcoordinate")) {
                            jSONObject2 = jSONObject.getJSONObject("sdkcoordinate");
                            b bVar = new b();
                            a(jSONObject2, bVar);
                            aVar.f = bVar;
                        }
                        if (t.a(jSONObject, "callamap")) {
                            aVar.b = jSONObject.getJSONObject("callamap");
                        }
                        if (t.a(jSONObject, "ca")) {
                            aVar.c = jSONObject.getJSONObject("ca");
                        }
                        if (t.a(jSONObject, "locate")) {
                            aVar.a = jSONObject.getJSONObject("locate");
                        }
                    }
                    return aVar;
                }
            } catch (Throwable e) {
                v.a(e, "ConfigManager", "loadConfig");
                return aVar;
            } catch (Throwable e2) {
                v.a(e2, "ConfigManager", "loadConfig");
                return aVar;
            } catch (Throwable e22) {
                v.a(e22, "ConfigManager", "loadConfig");
                return aVar;
            }
        }
        return aVar;
    }

    public static a a(Context context, s sVar, String str) {
        try {
            return a(new am().a(new b(context, sVar, str)));
        } catch (Throwable e) {
            v.a(e, "ConfigManager", "loadConfig");
            return new a();
        } catch (Throwable e2) {
            v.a(e2, "ConfigManager", "loadConfig");
            return new a();
        }
    }

    private static boolean a(String str) {
        if (str == null || !str.equals("1")) {
            return false;
        }
        return true;
    }

    public static String a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            return "";
        }
        String str2 = "";
        if (jSONObject.has(str) && !jSONObject.getString(str).equals("[]")) {
            str2 = jSONObject.optString(str);
        }
        return str2;
    }

    private static void a(JSONObject jSONObject, b bVar) {
        if (jSONObject != null) {
            try {
                String a = a(jSONObject, "md5");
                String a2 = a(jSONObject, "url");
                bVar.b = a;
                bVar.a = a2;
            } catch (Throwable e) {
                v.a(e, "ConfigManager", "parseSDKCoordinate");
            } catch (Throwable e2) {
                v.a(e2, "ConfigManager", "parseSDKCoordinate");
            }
        }
    }

    private static void a(JSONObject jSONObject, c cVar) {
        if (jSONObject != null) {
            try {
                Object a = a(jSONObject, "md5");
                Object a2 = a(jSONObject, "url");
                Object a3 = a(jSONObject, "sdkversion");
                if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(a2) && !TextUtils.isEmpty(a3)) {
                    cVar.a = a2;
                    cVar.b = a;
                    cVar.c = a3;
                }
            } catch (Throwable e) {
                v.a(e, "ConfigManager", "parseSDKUpdate");
            } catch (Throwable e2) {
                v.a(e2, "ConfigManager", "parseSDKUpdate");
            }
        }
    }

    private static boolean a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            return a(a(jSONObject.getJSONObject("commoninfo"), "com_isupload"));
        } catch (Throwable e) {
            v.a(e, "ConfigManager", "parseCommon");
            return false;
        } catch (Throwable e2) {
            v.a(e2, "ConfigManager", "parseCommon");
            return false;
        }
    }

    private static boolean b(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            return a(a(jSONObject.getJSONObject("exceptinfo"), "ex_isupload"));
        } catch (Throwable e) {
            v.a(e, "ConfigManager", "parseException");
            return false;
        } catch (Throwable e2) {
            v.a(e2, "ConfigManager", "parseException");
            return false;
        }
    }
}
