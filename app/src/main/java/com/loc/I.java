package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

class I extends aX {
    private Context lV;
    private x lW;
    private String lX = "";

    I(Context context, x xVar, String str) {
        this.lV = context;
        this.lW = xVar;
        this.lX = str;
    }

    public Map mb() {
        Map hashMap = new HashMap();
        hashMap.put("User-Agent", this.lW.mB());
        hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{this.lW.mA(), this.lW.mz()}));
        hashMap.put("logversion", "2.0");
        return hashMap;
    }

    public Map mc() {
        Object xT = bK.xT(this.lV);
        if (!TextUtils.isEmpty(xT)) {
            xT = J.na(new StringBuilder(xT).reverse().toString());
        }
        Map hashMap = new HashMap();
        hashMap.put("key", Y.nN(this.lV));
        hashMap.put("opertype", this.lX);
        hashMap.put("plattype", "android");
        hashMap.put("product", this.lW.mz());
        hashMap.put("version", this.lW.mA());
        hashMap.put("output", "json");
        hashMap.put("androidversion", VERSION.SDK_INT + "");
        hashMap.put("deviceId", xT);
        hashMap.put("abitype", Build.CPU_ABI);
        hashMap.put("ext", this.lW.mC());
        String rA = aN.rA();
        String rB = aN.rB(this.lV, rA, bw.xb(hashMap));
        hashMap.put("ts", rA);
        hashMap.put("scode", rB);
        return hashMap;
    }

    public String md() {
        return "https://restapi.amap.com/v3/config/resource?";
    }
}
