package com.autonavi.aps.amapapi.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

import com.loc.br;

import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class AmapLoc implements Parcelable {
    public static final Creator<AmapLoc> CREATOR;
    private String A;
    private String B;
    private int C;
    private String D;
    private JSONObject E;
    private String a;
    private double b;
    private double c;
    private double d;
    private float e;
    private float f;
    private float g;
    private long h;
    private String i;
    private int j;
    private String k;
    private int l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    public int a() {
        return this.j;
    }

    public int b() {
        return this.l;
    }

    public void a(int i) {
        this.l = i;
    }

    public void b(int i) {
        if (this.j == 0) {
            switch (i) {
                case 0:
                    this.k = "success";
                    break;
                case 1:
                    this.k = "\u91cd\u8981\u53c2\u6570\u4e3a\u7a7a";
                    break;
                case 2:
                    this.k = "WIFI\u4fe1\u606f\u4e0d\u8db3";
                    break;
                case 3:
                    this.k = "\u8bf7\u6c42\u53c2\u6570\u83b7\u53d6\u51fa\u73b0\u5f02\u5e38";
                    break;
                case 4:
                    this.k = "\u7f51\u7edc\u8fde\u63a5\u5f02\u5e38";
                    break;
                case 5:
                    this.k = "\u89e3\u6790XML\u51fa\u9519";
                    break;
                case 6:
                    this.k = "\u5b9a\u4f4d\u7ed3\u679c\u9519\u8bef";
                    break;
                case 7:
                    this.k = "KEY\u9519\u8bef";
                    break;
                case 8:
                    this.k = "\u5176\u4ed6\u9519\u8bef";
                    break;
                case 9:
                    this.k = "\u521d\u59cb\u5316\u5f02\u5e38";
                    break;
                case 10:
                    this.k = "\u5b9a\u4f4d\u670d\u52a1\u542f\u52a8\u5931\u8d25";
                    break;
                case 11:
                    this.k = "\u9519\u8bef\u7684\u57fa\u7ad9\u4fe1\u606f\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u63d2\u5165SIM\u5361";
                    break;
                case 12:
                    this.k = "\u7f3a\u5c11\u5b9a\u4f4d\u6743\u9650";
                    break;
            }
            this.j = i;
        }
    }

    public String c() {
        return this.k;
    }

    public void a(String str) {
        this.k = str;
    }

    public String d() {
        return this.m;
    }

    public void b(String str) {
        if (this.m == null || this.m.length() == 0) {
            this.m = str;
        }
    }

    public AmapLoc() {
        this.a = "";
        this.b = 0.0d;
        this.c = 0.0d;
        this.d = 0.0d;
        this.e = 0.0f;
        this.f = 0.0f;
        this.g = 0.0f;
        this.h = 0;
        this.i = "new";
        this.j = 0;
        this.k = "success";
        this.l = 0;
        this.m = "";
        this.n = "";
        this.o = "";
        this.p = "";
        this.q = "";
        this.r = "";
        this.s = "";
        this.t = "";
        this.u = "";
        this.v = "";
        this.w = "";
        this.x = "";
        this.y = "";
        this.z = null;
        this.A = "";
        this.B = "";
        this.C = -1;
        this.D = "";
        this.E = null;
    }

    public AmapLoc(Parcel parcel) {
        this.a = "";
        this.b = 0.0d;
        this.c = 0.0d;
        this.d = 0.0d;
        this.e = 0.0f;
        this.f = 0.0f;
        this.g = 0.0f;
        this.h = 0;
        this.i = "new";
        this.j = 0;
        this.k = "success";
        this.l = 0;
        this.m = "";
        this.n = "";
        this.o = "";
        this.p = "";
        this.q = "";
        this.r = "";
        this.s = "";
        this.t = "";
        this.u = "";
        this.v = "";
        this.w = "";
        this.x = "";
        this.y = "";
        this.z = null;
        this.A = "";
        this.B = "";
        this.C = -1;
        this.D = "";
        this.E = null;
        this.a = parcel.readString();
        this.i = parcel.readString();
        this.k = parcel.readString();
        this.j = parcel.readInt();
        this.g = parcel.readFloat();
        this.f = parcel.readFloat();
        this.e = parcel.readFloat();
        this.b = parcel.readDouble();
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        this.h = parcel.readLong();
        this.n = parcel.readString();
        this.o = parcel.readString();
        this.p = parcel.readString();
        this.q = parcel.readString();
        this.r = parcel.readString();
        this.s = parcel.readString();
        this.t = parcel.readString();
        this.u = parcel.readString();
        this.v = parcel.readString();
        this.w = parcel.readString();
        this.x = parcel.readString();
        this.y = parcel.readString();
        this.z = parcel.readString();
        this.A = parcel.readString();
        this.B = parcel.readString();
        this.D = parcel.readString();
        this.m = parcel.readString();
        this.C = parcel.readInt();
        this.l = parcel.readInt();
    }

    public AmapLoc(JSONObject jSONObject) {
        this.a = "";
        this.b = 0.0d;
        this.c = 0.0d;
        this.d = 0.0d;
        this.e = 0.0f;
        this.f = 0.0f;
        this.g = 0.0f;
        this.h = 0;
        this.i = "new";
        this.j = 0;
        this.k = "success";
        this.l = 0;
        this.m = "";
        this.n = "";
        this.o = "";
        this.p = "";
        this.q = "";
        this.r = "";
        this.s = "";
        this.t = "";
        this.u = "";
        this.v = "";
        this.w = "";
        this.x = "";
        this.y = "";
        this.z = null;
        this.A = "";
        this.B = "";
        this.C = -1;
        this.D = "";
        this.E = null;
        if (jSONObject != null) {
            try {
                c(jSONObject.getString("provider"));
                a(jSONObject.getDouble("lon"));
                b(jSONObject.getDouble("lat"));
                if (br.a(jSONObject, "altitude")) {
                    c(jSONObject.getDouble("altitude"));
                }
                a((float) jSONObject.getLong("accuracy"));
                b((float) jSONObject.getLong("speed"));
                c((float) jSONObject.getLong("bearing"));
                f(jSONObject.getString("type"));
                g(jSONObject.getString("retype"));
                i(jSONObject.getString("citycode"));
                j(jSONObject.getString("desc"));
                k(jSONObject.getString("adcode"));
                l(jSONObject.getString("country"));
                m(jSONObject.getString("province"));
                n(jSONObject.getString("city"));
                p(jSONObject.getString("road"));
                r(jSONObject.getString("poiname"));
                q(jSONObject.getString("street"));
                b(jSONObject.getInt("errorCode"));
                a(jSONObject.getString("errorInfo"));
                a(jSONObject.getInt("locationType"));
                b(jSONObject.getString("locationDetail"));
                if (br.a(jSONObject, "cens")) {
                    s(jSONObject.getString("cens"));
                }
                if (br.a(jSONObject, "poiid")) {
                    t(jSONObject.getString("poiid"));
                }
                if (br.a(jSONObject, "floor")) {
                    u(jSONObject.getString("floor"));
                }
                if (br.a(jSONObject, "coord")) {
                    v(jSONObject.getString("coord"));
                }
                if (br.a(jSONObject, "mcell")) {
                    w(jSONObject.getString("mcell"));
                }
                if (br.a(jSONObject, "time")) {
                    a(jSONObject.getLong("time"));
                }
                if (br.a(jSONObject, "district")) {
                    o(jSONObject.getString("district"));
                }
            } catch (Exception e) {
            }
        }
    }

    public String e() {
        return this.a;
    }

    public void c(String str) {
        this.a = str;
    }

    public double f() {
        return this.b;
    }

    public void a(double d) {
        d(br.a(Double.valueOf(d), "#.000000"));
    }

    public void d(String str) {
        this.b = Double.parseDouble(str);
    }

    public double g() {
        return this.c;
    }

    public void b(double d) {
        e(br.a(Double.valueOf(d), "#.000000"));
    }

    public void e(String str) {
        this.c = Double.parseDouble(str);
    }

    public void c(double d) {
        this.d = d;
    }

    public float h() {
        return this.e;
    }

    public void a(float f) {
        x(String.valueOf(Math.round(f)));
    }

    private void x(String str) {
        this.e = Float.parseFloat(str);
    }

    public void b(float f) {
        y(br.a(Float.valueOf(f), "#.0"));
    }

    private void y(String str) {
        this.f = Float.parseFloat(str);
        if (this.f > 100.0f) {
            this.f = 0.0f;
        }
    }

    public void c(float f) {
        z(br.a(Float.valueOf(f), "#.0"));
    }

    private void z(String str) {
        this.g = Float.parseFloat(str);
    }

    public long i() {
        return this.h;
    }

    public void a(long j) {
        this.h = j;
    }

    public String j() {
        return this.i;
    }

    public void f(String str) {
        this.i = str;
    }

    public String k() {
        return this.n;
    }

    public void g(String str) {
        this.n = str;
    }

    public String l() {
        return this.o;
    }

    public void h(String str) {
        this.o = str;
    }

    public String m() {
        return this.p;
    }

    public void i(String str) {
        this.p = str;
    }

    public String n() {
        return this.q;
    }

    public void j(String str) {
        this.q = str;
    }

    public String o() {
        return this.r;
    }

    public void k(String str) {
        this.r = str;
    }

    public String p() {
        return this.s;
    }

    public void l(String str) {
        this.s = str;
    }

    public String q() {
        return this.t;
    }

    public void m(String str) {
        this.t = str;
    }

    public String r() {
        return this.u;
    }

    public void n(String str) {
        this.u = str;
    }

    public String s() {
        return this.v;
    }

    public void o(String str) {
        this.v = str;
    }

    public String t() {
        return this.w;
    }

    public void p(String str) {
        this.w = str;
    }

    public void q(String str) {
        this.x = str;
    }

    public String u() {
        return this.y;
    }

    public void r(String str) {
        this.y = str;
    }

    public void s(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (Object obj : str.split("\\*")) {
                if (!TextUtils.isEmpty(obj)) {
                    String[] split = obj.split(",");
                    a(Double.parseDouble(split[0]));
                    b(Double.parseDouble(split[1]));
                    a((float) Integer.parseInt(split[2]));
                    break;
                }
            }
            this.z = str;
        }
    }

    public String v() {
        return this.A;
    }

    public void t(String str) {
        this.A = str;
    }

    public void u(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.replace("F", "");
            try {
                Integer.parseInt(str);
            } catch (Exception e) {
                str = null;
            }
        }
        this.B = str;
    }

    public int w() {
        return this.C;
    }

    public void v(String str) {
        if (TextUtils.isEmpty(str)) {
            this.C = -1;
        } else if (this.a.equals("gps")) {
            this.C = 0;
        } else if (str.equals("0")) {
            this.C = 0;
        } else if (str.equals("1")) {
            this.C = 1;
        } else {
            this.C = -1;
        }
    }

    public String x() {
        return this.D;
    }

    public AmapLoc y() {
        Object x = x();
        if (TextUtils.isEmpty(x)) {
            return null;
        }
        String[] split = x.split(",");
        if (split.length != 3) {
            return null;
        }
        AmapLoc amapLoc = new AmapLoc();
        amapLoc.c(e());
        amapLoc.d(split[0]);
        amapLoc.e(split[1]);
        amapLoc.a(Float.parseFloat(split[2]));
        amapLoc.i(m());
        amapLoc.k(o());
        amapLoc.l(p());
        amapLoc.m(q());
        amapLoc.n(r());
        amapLoc.a(i());
        amapLoc.f(j());
        amapLoc.v(String.valueOf(w()));
        if (br.a(amapLoc)) {
            return amapLoc;
        }
        return null;
    }

    public void w(String str) {
        this.D = str;
    }

    public JSONObject z() {
        return this.E;
    }

    public void a(JSONObject jSONObject) {
        this.E = jSONObject;
    }

    public String A() {
        return c(1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String c(int r7) {
        /*
        r6 = this;
        r0 = 0;
        r1 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0101 }
        r1.<init>();	 Catch:{ Exception -> 0x0101 }
        switch(r7) {
            case 1: goto L_0x0010;
            case 2: goto L_0x00ce;
            case 3: goto L_0x00d6;
            default: goto L_0x0009;
        };
    L_0x0009:
        if (r1 == 0) goto L_0x000f;
    L_0x000b:
        r0 = r1.toString();
    L_0x000f:
        return r0;
    L_0x0010:
        r2 = "altitude";
        r4 = r6.d;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "speed";
        r3 = r6.f;	 Catch:{ Exception -> 0x0101 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "bearing";
        r3 = r6.g;	 Catch:{ Exception -> 0x0101 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "retype";
        r3 = r6.n;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "citycode";
        r3 = r6.p;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "desc";
        r3 = r6.q;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "adcode";
        r3 = r6.r;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "country";
        r3 = r6.s;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "province";
        r3 = r6.t;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "city";
        r3 = r6.u;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "district";
        r3 = r6.v;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "road";
        r3 = r6.w;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "street";
        r3 = r6.x;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "poiname";
        r3 = r6.y;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "cens";
        r3 = r6.z;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "poiid";
        r3 = r6.A;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "floor";
        r3 = r6.B;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "coord";
        r3 = r6.C;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "mcell";
        r3 = r6.D;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "errorCode";
        r3 = r6.j;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "errorInfo";
        r3 = r6.k;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "locationType";
        r3 = r6.l;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "locationDetail";
        r3 = r6.m;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = r6.E;	 Catch:{ Exception -> 0x0101 }
        if (r2 != 0) goto L_0x0105;
    L_0x00ce:
        r2 = "time";
        r4 = r6.h;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
    L_0x00d6:
        r2 = "provider";
        r3 = r6.a;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "lon";
        r4 = r6.b;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "lat";
        r4 = r6.c;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "accuracy";
        r3 = r6.e;	 Catch:{ Exception -> 0x0101 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "type";
        r3 = r6.i;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        goto L_0x0009;
    L_0x0101:
        r1 = move-exception;
        r1 = r0;
        goto L_0x0009;
    L_0x0105:
        r2 = "offpct";
        r2 = com.loc.br.a(r1, r2);	 Catch:{ Exception -> 0x0101 }
        if (r2 == 0) goto L_0x00ce;
    L_0x010e:
        r2 = "offpct";
        r3 = r6.E;	 Catch:{ Exception -> 0x0101 }
        r4 = "offpct";
        r3 = r3.getString(r4);	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        goto L_0x00ce;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.aps.amapapi.model.AmapLoc.c(int):java.lang.String");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.i);
        parcel.writeString(this.k);
        parcel.writeInt(this.j);
        parcel.writeFloat(this.g);
        parcel.writeFloat(this.f);
        parcel.writeFloat(this.e);
        parcel.writeDouble(this.b);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeLong(this.h);
        parcel.writeString(this.n);
        parcel.writeString(this.o);
        parcel.writeString(this.p);
        parcel.writeString(this.q);
        parcel.writeString(this.r);
        parcel.writeString(this.s);
        parcel.writeString(this.t);
        parcel.writeString(this.u);
        parcel.writeString(this.v);
        parcel.writeString(this.w);
        parcel.writeString(this.x);
        parcel.writeString(this.y);
        parcel.writeString(this.z);
        parcel.writeString(this.A);
        parcel.writeString(this.B);
        parcel.writeString(this.D);
        parcel.writeString(this.m);
        parcel.writeInt(this.C);
        parcel.writeInt(this.l);
    }

    static {
        CREATOR = new Creator<AmapLoc>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public AmapLoc a(Parcel parcel) {
                return new AmapLoc(parcel);
            }

            public AmapLoc[] a(int i) {
                return null;
            }
        };
    }
}
