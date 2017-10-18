package com.loc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.amap.api.location.DPoint;
import com.autonavi.aps.amapapi.model.AmapLoc;

import org.json.JSONObject;

/* compiled from: ConnectionServiceManager */
public class at {
    a a;
    private String b;
    private Context c;
    private boolean d;
    private h e;
    private ServiceConnection f;
    private Intent g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;

    /* compiled from: ConnectionServiceManager */
    public interface a {
        void a(int i);
    }

    /* compiled from: ConnectionServiceManager */
    /* renamed from: com.loc.at.1 */
    class AnonymousClass1 implements ServiceConnection {
        final /* synthetic */ a a;
        final /* synthetic */ at b;

        AnonymousClass1(at atVar, a aVar) {
            this.b = atVar;
            this.a = aVar;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.b.e = com.loc.h.a.a(iBinder);
            this.a.a(0);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.b.e = null;
        }
    }

    at(Context context) {
        this.b = null;
        this.c = null;
        this.d = true;
        this.e = null;
        this.f = null;
        this.g = new Intent();
        this.h = "com.autonavi.minimap";
        this.i = "com.amap.api.service.AMapService";
        this.j = "com.taobao.agoo.PushService";
        this.a = null;
        this.k = "invaid type";
        this.l = "empty appkey";
        this.m = "refused";
        this.n = "failed";
        this.c = context;
        try {
            this.b = o.a(bd.a(j.e(context).getBytes("UTF-8"), "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n"));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a() {
        c();
        this.c = null;
        this.e = null;
        this.f = null;
        if (this.a != null) {
            this.a.a(-1);
        }
        this.d = true;
    }

    public void a(a aVar) {
        try {
            this.a = aVar;
            if (this.f == null) {
                this.f = new AnonymousClass1(this, aVar);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    boolean b() {
        boolean z = true;
        try {
            this.g.setComponent(new ComponentName(this.h, this.i));
            this.g.putExtra("appkey", this.b);
            boolean bindService = this.c.bindService(this.g, this.f, 1);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.h, this.j));
            boolean bindService2 = this.c.bindService(intent, null, 1);
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

    void c() {
        try {
            this.c.unbindService(this.f);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Throwable th) {
        }
        this.e = null;
    }

    AmapLoc d() {
        try {
            if (!this.d) {
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putString("type", "corse");
            bundle.putString("appkey", this.b);
            this.e.a(bundle);
            if (bundle.size() >= 1) {
                return a(bundle);
            }
            return null;
        } catch (RemoteException e) {
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private AmapLoc a(Bundle bundle) {
        double d = 0.0d;
        if (bundle == null) {
            return null;
        }
        byte[] b;
        if (bundle.containsKey("key")) {
            try {
                b = bd.b(o.a(bundle.getString("key")), "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n");
            } catch (Exception e) {
                e.printStackTrace();
                b = null;
            }
        } else {
            b = null;
        }
        if (bundle.containsKey("result")) {
            try {
                String str = new String(bd.a(b, o.a(bundle.getString("result"))), "utf-8");
                if (str != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.has("error")) {
                        String string = jSONObject.getString("error");
                        if (this.k.equals(string)) {
                            this.d = false;
                        }
                        if (this.l.equals(string)) {
                            this.d = false;
                        }
                        if (this.m.equals(string)) {
                            this.d = false;
                        }
                        return !this.n.equals(string) ? null : null;
                    } else {
                        double d2;
                        Object string2;
                        AmapLoc amapLoc = new AmapLoc();
                        if (jSONObject.has("time")) {
                            amapLoc.a(jSONObject.getLong("time"));
                        }
                        if (jSONObject.has("acc")) {
                            amapLoc.a((float) jSONObject.getInt("acc"));
                        }
                        if (jSONObject.has("dir")) {
                            amapLoc.c(Float.parseFloat(jSONObject.getString("dir")));
                        }
                        amapLoc.c("lbs");
                        amapLoc.a(7);
                        if (jSONObject.has("lat")) {
                            d2 = jSONObject.getDouble("lat");
                        } else {
                            d2 = 0.0d;
                        }
                        if (jSONObject.has("lon")) {
                            d = jSONObject.getDouble("lon");
                        }
                        if (jSONObject.has("type")) {
                            string2 = jSONObject.getString("type");
                        } else {
                            string2 = null;
                        }
                        if (jSONObject.has("poiname")) {
                            str = jSONObject.getString("poiname");
                        } else {
                            str = null;
                        }
                        amapLoc.r(str);
                        if (jSONObject.has("desc")) {
                            str = jSONObject.getString("desc");
                        } else {
                            str = null;
                        }
                        amapLoc.j(str);
                        if (jSONObject.has("street")) {
                            str = jSONObject.getString("street");
                        } else {
                            str = null;
                        }
                        amapLoc.q(str);
                        if (jSONObject.has("pid")) {
                            str = jSONObject.getString("pid");
                        } else {
                            str = null;
                        }
                        amapLoc.t(str);
                        if (jSONObject.has("flr")) {
                            str = jSONObject.getString("flr");
                        } else {
                            str = null;
                        }
                        amapLoc.u(str);
                        if (jSONObject.has("road")) {
                            str = jSONObject.getString("road");
                        } else {
                            str = null;
                        }
                        amapLoc.p(str);
                        if (jSONObject.has("city")) {
                            str = jSONObject.getString("city");
                        } else {
                            str = null;
                        }
                        amapLoc.n(str);
                        if (jSONObject.has("country")) {
                            str = jSONObject.getString("country");
                        } else {
                            str = null;
                        }
                        amapLoc.l(str);
                        if (jSONObject.has("citycode")) {
                            str = jSONObject.getString("citycode");
                        } else {
                            str = null;
                        }
                        amapLoc.i(str);
                        if (jSONObject.has("province")) {
                            str = jSONObject.getString("province");
                        } else {
                            str = null;
                        }
                        amapLoc.m(str);
                        if (jSONObject.has("adcode")) {
                            str = jSONObject.getString("adcode");
                        } else {
                            str = null;
                        }
                        amapLoc.k(str);
                        if (jSONObject.has("district")) {
                            str = jSONObject.getString("district");
                        } else {
                            str = null;
                        }
                        amapLoc.o(str);
                        if ("WGS84".equals(string2) && c.a(d2, d)) {
                            DPoint a = g.a(this.c, d, d2);
                            amapLoc.b(a.getLatitude());
                            amapLoc.a(a.getLongitude());
                        } else {
                            amapLoc.b(d2);
                            amapLoc.a(d);
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
}
