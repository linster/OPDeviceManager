package com.loc;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONObject;

public class bj {
    public static aE ve(byte[] bArr) {
        boolean z = false;
        aE aEVar = new aE();
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
                    if (!"1".equals(vh(jSONObject, "status")) || !jSONObject.has("result")) {
                        return aEVar;
                    }
                    jSONObject = jSONObject.getJSONObject("result");
                    if (jSONObject != null) {
                        JSONObject jSONObject2;
                        boolean vl = !bw.wX(jSONObject, "exception") ? false : vl(jSONObject.getJSONObject("exception"));
                        if (bw.wX(jSONObject, "common")) {
                            z = vk(jSONObject.getJSONObject("common"));
                        }
                        as asVar = new as();
                        asVar.nu = vl;
                        asVar.nv = z;
                        aEVar.oB = asVar;
                        if (jSONObject.has("sdkupdate")) {
                            jSONObject2 = jSONObject.getJSONObject("sdkupdate");
                            z zVar = new z();
                            vj(jSONObject2, zVar);
                            aEVar.oC = zVar;
                        }
                        if (bw.wX(jSONObject, "sdkcoordinate")) {
                            jSONObject2 = jSONObject.getJSONObject("sdkcoordinate");
                            T t = new T();
                            vi(jSONObject2, t);
                            aEVar.oD = t;
                        }
                        if (bw.wX(jSONObject, "callamap")) {
                            aEVar.oz = jSONObject.getJSONObject("callamap");
                        }
                        if (bw.wX(jSONObject, "ca")) {
                            aEVar.oA = jSONObject.getJSONObject("ca");
                        }
                        if (bw.wX(jSONObject, "locate")) {
                            aEVar.oy = jSONObject.getJSONObject("locate");
                        }
                    }
                    return aEVar;
                }
            } catch (Throwable e) {
                D.mF(e, "ConfigManager", "loadConfig");
                return aEVar;
            } catch (Throwable e2) {
                D.mF(e2, "ConfigManager", "loadConfig");
                return aEVar;
            } catch (Throwable e22) {
                D.mF(e22, "ConfigManager", "loadConfig");
                return aEVar;
            }
        }
        return aEVar;
    }

    public static aE vf(Context context, x xVar, String str) {
        try {
            return ve(new o().lT(new I(context, xVar, str)));
        } catch (Throwable e) {
            D.mF(e, "ConfigManager", "loadConfig");
            return new aE();
        } catch (Throwable e2) {
            D.mF(e2, "ConfigManager", "loadConfig");
            return new aE();
        }
    }

    private static boolean vg(String str) {
        return str != null && str.equals("1");
    }

    public static String vh(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return "";
        }
        String str2 = "";
        if (jSONObject.has(str) && !jSONObject.getString(str).equals("[]")) {
            str2 = jSONObject.optString(str);
        }
        return str2;
    }

    private static void vi(JSONObject jSONObject, T t) {
        if (jSONObject != null) {
            try {
                String vh = vh(jSONObject, "md5");
                String vh2 = vh(jSONObject, "url");
                t.mr = vh;
                t.a = vh2;
            } catch (Throwable e) {
                D.mF(e, "ConfigManager", "parseSDKCoordinate");
            } catch (Throwable e2) {
                D.mF(e2, "ConfigManager", "parseSDKCoordinate");
            }
        }
    }

    private static void vj(JSONObject jSONObject, z zVar) {
        if (jSONObject != null) {
            try {
                Object vh = vh(jSONObject, "md5");
                Object vh2 = vh(jSONObject, "url");
                Object vh3 = vh(jSONObject, "sdkversion");
                if (!TextUtils.isEmpty(vh) && !TextUtils.isEmpty(vh2) && !TextUtils.isEmpty(vh3)) {
                    zVar.a = vh2;
                    zVar.ln = vh;
                    zVar.lo = vh3;
                }
            } catch (Throwable e) {
                D.mF(e, "ConfigManager", "parseSDKUpdate");
            } catch (Throwable e2) {
                D.mF(e2, "ConfigManager", "parseSDKUpdate");
            }
        }
    }

    private static boolean vk(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            return vg(vh(jSONObject.getJSONObject("commoninfo"), "com_isupload"));
        } catch (Throwable e) {
            D.mF(e, "ConfigManager", "parseCommon");
            return false;
        } catch (Throwable e2) {
            D.mF(e2, "ConfigManager", "parseCommon");
            return false;
        }
    }

    private static boolean vl(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            return vg(vh(jSONObject.getJSONObject("exceptinfo"), "ex_isupload"));
        } catch (Throwable e) {
            D.mF(e, "ConfigManager", "parseException");
            return false;
        } catch (Throwable e2) {
            D.mF(e2, "ConfigManager", "parseException");
            return false;
        }
    }
}
