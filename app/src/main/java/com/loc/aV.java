package com.loc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import com.amap.api.location.g;
import com.autonavi.aps.amapapi.model.AmapLoc;
import org.json.JSONObject;

public class aV {
    private String qT = null;
    private Context qU = null;
    private boolean qV = true;
    private bL qW = null;
    private ServiceConnection qX = null;
    private Intent qY = new Intent();
    private String qZ = "com.autonavi.minimap";
    private String ra = "com.amap.api.service.AMapService";
    private String rb = "com.taobao.agoo.PushService";
    bO rc = null;
    private String rd = "invaid type";
    private String re = "empty appkey";
    private String rf = "refused";
    private String rg = "failed";

    aV(Context context) {
        this.qU = context;
        try {
            this.qT = aG.ro(bQ.yl(Y.nN(context).getBytes("UTF-8"), "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n"));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private AmapLoc sJ(Bundle bundle) {
        double d = 0.0d;
        if (bundle == null) {
            return null;
        }
        byte[] ym;
        if (bundle.containsKey("key")) {
            try {
                ym = bQ.ym(aG.rm(bundle.getString("key")), "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n");
            } catch (Exception e) {
                e.printStackTrace();
                ym = null;
            }
        } else {
            ym = null;
        }
        if (bundle.containsKey("result")) {
            try {
                String str = new String(bQ.yt(ym, aG.rm(bundle.getString("result"))), "utf-8");
                if (str != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.has("error")) {
                        String string = jSONObject.getString("error");
                        if (this.rd.equals(string)) {
                            this.qV = false;
                        }
                        if (this.re.equals(string)) {
                            this.qV = false;
                        }
                        if (this.rf.equals(string)) {
                            this.qV = false;
                        }
                        return !this.rg.equals(string) ? null : null;
                    } else {
                        AmapLoc amapLoc = new AmapLoc();
                        if (jSONObject.has("time")) {
                            amapLoc.yV(jSONObject.getLong("time"));
                        }
                        if (jSONObject.has("acc")) {
                            amapLoc.yO((float) jSONObject.getInt("acc"));
                        }
                        if (jSONObject.has("dir")) {
                            amapLoc.yS(Float.parseFloat(jSONObject.getString("dir")));
                        }
                        amapLoc.yF("lbs");
                        amapLoc.yy(7);
                        double d2 = !jSONObject.has("lat") ? 0.0d : jSONObject.getDouble("lat");
                        if (jSONObject.has("lon")) {
                            d = jSONObject.getDouble("lon");
                        }
                        Object string2 = !jSONObject.has("type") ? null : jSONObject.getString("type");
                        amapLoc.zu(!jSONObject.has("poiname") ? null : jSONObject.getString("poiname"));
                        amapLoc.zf(!jSONObject.has("desc") ? null : jSONObject.getString("desc"));
                        amapLoc.zs(!jSONObject.has("street") ? null : jSONObject.getString("street"));
                        amapLoc.zx(!jSONObject.has("pid") ? null : jSONObject.getString("pid"));
                        amapLoc.zy(!jSONObject.has("flr") ? null : jSONObject.getString("flr"));
                        amapLoc.zr(!jSONObject.has("road") ? null : jSONObject.getString("road"));
                        amapLoc.zn(!jSONObject.has("city") ? null : jSONObject.getString("city"));
                        amapLoc.zj(!jSONObject.has("country") ? null : jSONObject.getString("country"));
                        amapLoc.zd(!jSONObject.has("citycode") ? null : jSONObject.getString("citycode"));
                        amapLoc.zl(!jSONObject.has("province") ? null : jSONObject.getString("province"));
                        amapLoc.zh(!jSONObject.has("adcode") ? null : jSONObject.getString("adcode"));
                        amapLoc.zp(!jSONObject.has("district") ? null : jSONObject.getString("district"));
                        if ("WGS84".equals(string2) && H.mR(d2, d)) {
                            g rv = aL.rv(this.qU, d, d2);
                            amapLoc.yK(rv.is());
                            amapLoc.yH(rv.ir());
                        } else {
                            amapLoc.yK(d2);
                            amapLoc.yH(d);
                        }
                        return amapLoc;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public void sE() {
        sH();
        this.qU = null;
        this.qW = null;
        this.qX = null;
        if (this.rc != null) {
            this.rc.ne(-1);
        }
        this.qV = true;
    }

    public void sF(bO bOVar) {
        try {
            this.rc = bOVar;
            if (this.qX == null) {
                this.qX = new ax(this, bOVar);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    boolean sG() {
        boolean z = true;
        try {
            this.qY.setComponent(new ComponentName(this.qZ, this.ra));
            this.qY.putExtra("appkey", this.qT);
            boolean bindService = this.qU.bindService(this.qY, this.qX, 1);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.qZ, this.rb));
            boolean bindService2 = this.qU.bindService(intent, null, 1);
            if (bindService) {
                if (!bindService2) {
                }
                return z;
            }
            z = false;
            return z;
        } catch (Exception e) {
            return false;
        }
    }

    void sH() {
        try {
            this.qU.unbindService(this.qX);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Throwable th) {
        }
        this.qW = null;
    }

    AmapLoc sI() {
        try {
            if (!this.qV) {
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putString("type", "corse");
            bundle.putString("appkey", this.qT);
            this.qW.mE(bundle);
            if (bundle.size() >= 1) {
                return sJ(bundle);
            }
            return null;
        } catch (RemoteException e) {
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
