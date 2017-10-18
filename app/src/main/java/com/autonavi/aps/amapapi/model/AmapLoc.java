package com.autonavi.aps.amapapi.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.loc.bq;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class AmapLoc implements Parcelable {
    public static final Creator us = new a();
    private String a = "";
    private double c = 0.0d;
    private double tP = 0.0d;
    private double tQ = 0.0d;
    private float tR = 0.0f;
    private float tS = 0.0f;
    private float tT = 0.0f;
    private long tU = 0;
    private String tV = "new";
    private int tW = 0;
    private String tX = "success";
    private int tY = 0;
    private String tZ = "";
    private String ua = "";
    private String ub = "";
    private String uc = "";
    private String ud = "";
    private String ue = "";
    private String uf = "";
    private String ug = "";
    private String uh = "";
    private String ui = "";
    private String uj = "";
    private String uk = "";
    private String ul = "";
    private String um = null;
    private String un = "";
    private String uo = "";
    private int up = -1;
    private String uq = "";
    private JSONObject ur = null;

    public AmapLoc(Parcel parcel) {
        this.a = parcel.readString();
        this.tV = parcel.readString();
        this.tX = parcel.readString();
        this.tW = parcel.readInt();
        this.tT = parcel.readFloat();
        this.tS = parcel.readFloat();
        this.tR = parcel.readFloat();
        this.tP = parcel.readDouble();
        this.c = parcel.readDouble();
        this.tQ = parcel.readDouble();
        this.tU = parcel.readLong();
        this.ua = parcel.readString();
        this.ub = parcel.readString();
        this.uc = parcel.readString();
        this.ud = parcel.readString();
        this.ue = parcel.readString();
        this.uf = parcel.readString();
        this.ug = parcel.readString();
        this.uh = parcel.readString();
        this.ui = parcel.readString();
        this.uj = parcel.readString();
        this.uk = parcel.readString();
        this.ul = parcel.readString();
        this.um = parcel.readString();
        this.un = parcel.readString();
        this.uo = parcel.readString();
        this.uq = parcel.readString();
        this.tZ = parcel.readString();
        this.up = parcel.readInt();
        this.tY = parcel.readInt();
    }

    public AmapLoc(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                yF(jSONObject.getString("provider"));
                yH(jSONObject.getDouble("lon"));
                yK(jSONObject.getDouble("lat"));
                if (bq.wb(jSONObject, "altitude")) {
                    yM(jSONObject.getDouble("altitude"));
                }
                yO((float) jSONObject.getLong("accuracy"));
                yQ((float) jSONObject.getLong("speed"));
                yS((float) jSONObject.getLong("bearing"));
                yX(jSONObject.getString("type"));
                yZ(jSONObject.getString("retype"));
                zd(jSONObject.getString("citycode"));
                zf(jSONObject.getString("desc"));
                zh(jSONObject.getString("adcode"));
                zj(jSONObject.getString("country"));
                zl(jSONObject.getString("province"));
                zn(jSONObject.getString("city"));
                zr(jSONObject.getString("road"));
                zu(jSONObject.getString("poiname"));
                zs(jSONObject.getString("street"));
                yz(jSONObject.getInt("errorCode"));
                yB(jSONObject.getString("errorInfo"));
                yy(jSONObject.getInt("locationType"));
                yD(jSONObject.getString("locationDetail"));
                if (bq.wb(jSONObject, "cens")) {
                    zv(jSONObject.getString("cens"));
                }
                if (bq.wb(jSONObject, "poiid")) {
                    zx(jSONObject.getString("poiid"));
                }
                if (bq.wb(jSONObject, "floor")) {
                    zy(jSONObject.getString("floor"));
                }
                if (bq.wb(jSONObject, "coord")) {
                    zA(jSONObject.getString("coord"));
                }
                if (bq.wb(jSONObject, "mcell")) {
                    zD(jSONObject.getString("mcell"));
                }
                if (bq.wb(jSONObject, "time")) {
                    yV(jSONObject.getLong("time"));
                }
                if (bq.wb(jSONObject, "district")) {
                    zp(jSONObject.getString("district"));
                }
            } catch (Exception e) {
            }
        }
    }

    private void yP(String str) {
        this.tR = Float.parseFloat(str);
    }

    private void yR(String str) {
        this.tS = Float.parseFloat(str);
        if (this.tS > 100.0f) {
            this.tS = 0.0f;
        }
    }

    private void yT(String str) {
        this.tT = Float.parseFloat(str);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.tV);
        parcel.writeString(this.tX);
        parcel.writeInt(this.tW);
        parcel.writeFloat(this.tT);
        parcel.writeFloat(this.tS);
        parcel.writeFloat(this.tR);
        parcel.writeDouble(this.tP);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.tQ);
        parcel.writeLong(this.tU);
        parcel.writeString(this.ua);
        parcel.writeString(this.ub);
        parcel.writeString(this.uc);
        parcel.writeString(this.ud);
        parcel.writeString(this.ue);
        parcel.writeString(this.uf);
        parcel.writeString(this.ug);
        parcel.writeString(this.uh);
        parcel.writeString(this.ui);
        parcel.writeString(this.uj);
        parcel.writeString(this.uk);
        parcel.writeString(this.ul);
        parcel.writeString(this.um);
        parcel.writeString(this.un);
        parcel.writeString(this.uo);
        parcel.writeString(this.uq);
        parcel.writeString(this.tZ);
        parcel.writeInt(this.up);
        parcel.writeInt(this.tY);
    }

    public String yA() {
        return this.tX;
    }

    public void yB(String str) {
        this.tX = str;
    }

    public String yC() {
        return this.tZ;
    }

    public void yD(String str) {
        if (this.tZ == null || this.tZ.length() == 0) {
            this.tZ = str;
        }
    }

    public String yE() {
        return this.a;
    }

    public void yF(String str) {
        this.a = str;
    }

    public double yG() {
        return this.tP;
    }

    public void yH(double d) {
        yI(bq.wx(Double.valueOf(d), "#.000000"));
    }

    public void yI(String str) {
        this.tP = Double.parseDouble(str);
    }

    public double yJ() {
        return this.c;
    }

    public void yK(double d) {
        yL(bq.wx(Double.valueOf(d), "#.000000"));
    }

    public void yL(String str) {
        this.c = Double.parseDouble(str);
    }

    public void yM(double d) {
        this.tQ = d;
    }

    public float yN() {
        return this.tR;
    }

    public void yO(float f) {
        yP(String.valueOf(Math.round(f)));
    }

    public void yQ(float f) {
        yR(bq.wx(Float.valueOf(f), "#.0"));
    }

    public void yS(float f) {
        yT(bq.wx(Float.valueOf(f), "#.0"));
    }

    public long yU() {
        return this.tU;
    }

    public void yV(long j) {
        this.tU = j;
    }

    public String yW() {
        return this.tV;
    }

    public void yX(String str) {
        this.tV = str;
    }

    public String yY() {
        return this.ua;
    }

    public void yZ(String str) {
        this.ua = str;
    }

    public int yw() {
        return this.tW;
    }

    public int yx() {
        return this.tY;
    }

    public void yy(int i) {
        this.tY = i;
    }

    public void yz(int i) {
        if (this.tW == 0) {
            switch (i) {
                case 0:
                    this.tX = "success";
                    break;
                case 1:
                    this.tX = "重要参数为空";
                    break;
                case 2:
                    this.tX = "WIFI信息不足";
                    break;
                case 3:
                    this.tX = "请求参数获取出现异常";
                    break;
                case 4:
                    this.tX = "网络连接异常";
                    break;
                case 5:
                    this.tX = "解析XML出错";
                    break;
                case 6:
                    this.tX = "定位结果错误";
                    break;
                case 7:
                    this.tX = "KEY错误";
                    break;
                case 8:
                    this.tX = "其他错误";
                    break;
                case 9:
                    this.tX = "初始化异常";
                    break;
                case 10:
                    this.tX = "定位服务启动失败";
                    break;
                case 11:
                    this.tX = "错误的基站信息，请检查是否插入SIM卡";
                    break;
                case 12:
                    this.tX = "缺少定位权限";
                    break;
            }
            this.tW = i;
        }
    }

    public void zA(String str) {
        if (TextUtils.isEmpty(str)) {
            this.up = -1;
        } else if (this.a.equals("gps")) {
            this.up = 0;
        } else if (str.equals("0")) {
            this.up = 0;
        } else if (str.equals("1")) {
            this.up = 1;
        } else {
            this.up = -1;
        }
    }

    public String zB() {
        return this.uq;
    }

    public AmapLoc zC() {
        Object zB = zB();
        if (TextUtils.isEmpty(zB)) {
            return null;
        }
        String[] split = zB.split(",");
        if (split.length != 3) {
            return null;
        }
        AmapLoc amapLoc = new AmapLoc();
        amapLoc.yF(yE());
        amapLoc.yI(split[0]);
        amapLoc.yL(split[1]);
        amapLoc.yO(Float.parseFloat(split[2]));
        amapLoc.zd(zc());
        amapLoc.zh(zg());
        amapLoc.zj(zi());
        amapLoc.zl(zk());
        amapLoc.zn(zm());
        amapLoc.yV(yU());
        amapLoc.yX(yW());
        amapLoc.zA(String.valueOf(zz()));
        return bq.vH(amapLoc) ? amapLoc : null;
    }

    public void zD(String str) {
        this.uq = str;
    }

    public JSONObject zE() {
        return this.ur;
    }

    public void zF(JSONObject jSONObject) {
        this.ur = jSONObject;
    }

    public String zG() {
        return zH(1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String zH(int r7) {
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
        r4 = r6.tQ;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "speed";
        r3 = r6.tS;	 Catch:{ Exception -> 0x0101 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "bearing";
        r3 = r6.tT;	 Catch:{ Exception -> 0x0101 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "retype";
        r3 = r6.ua;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "citycode";
        r3 = r6.uc;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "desc";
        r3 = r6.ud;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "adcode";
        r3 = r6.ue;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "country";
        r3 = r6.uf;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "province";
        r3 = r6.ug;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "city";
        r3 = r6.uh;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "district";
        r3 = r6.ui;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "road";
        r3 = r6.uj;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "street";
        r3 = r6.uk;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "poiname";
        r3 = r6.ul;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "cens";
        r3 = r6.um;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "poiid";
        r3 = r6.un;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "floor";
        r3 = r6.uo;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "coord";
        r3 = r6.up;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "mcell";
        r3 = r6.uq;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "errorCode";
        r3 = r6.tW;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "errorInfo";
        r3 = r6.tX;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "locationType";
        r3 = r6.tY;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "locationDetail";
        r3 = r6.tZ;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = r6.ur;	 Catch:{ Exception -> 0x0101 }
        if (r2 != 0) goto L_0x0105;
    L_0x00ce:
        r2 = "time";
        r4 = r6.tU;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
    L_0x00d6:
        r2 = "provider";
        r3 = r6.a;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        r2 = "lon";
        r4 = r6.tP;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "lat";
        r4 = r6.c;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "accuracy";
        r3 = r6.tR;	 Catch:{ Exception -> 0x0101 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x0101 }
        r2 = "type";
        r3 = r6.tV;	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        goto L_0x0009;
    L_0x0101:
        r1 = move-exception;
        r1 = r0;
        goto L_0x0009;
    L_0x0105:
        r2 = "offpct";
        r2 = com.loc.bq.wb(r1, r2);	 Catch:{ Exception -> 0x0101 }
        if (r2 == 0) goto L_0x00ce;
    L_0x010e:
        r2 = "offpct";
        r3 = r6.ur;	 Catch:{ Exception -> 0x0101 }
        r4 = "offpct";
        r3 = r3.getString(r4);	 Catch:{ Exception -> 0x0101 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x0101 }
        goto L_0x00ce;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.aps.amapapi.model.AmapLoc.zH(int):java.lang.String");
    }

    public String za() {
        return this.ub;
    }

    public void zb(String str) {
        this.ub = str;
    }

    public String zc() {
        return this.uc;
    }

    public void zd(String str) {
        this.uc = str;
    }

    public String ze() {
        return this.ud;
    }

    public void zf(String str) {
        this.ud = str;
    }

    public String zg() {
        return this.ue;
    }

    public void zh(String str) {
        this.ue = str;
    }

    public String zi() {
        return this.uf;
    }

    public void zj(String str) {
        this.uf = str;
    }

    public String zk() {
        return this.ug;
    }

    public void zl(String str) {
        this.ug = str;
    }

    public String zm() {
        return this.uh;
    }

    public void zn(String str) {
        this.uh = str;
    }

    public String zo() {
        return this.ui;
    }

    public void zp(String str) {
        this.ui = str;
    }

    public String zq() {
        return this.uj;
    }

    public void zr(String str) {
        this.uj = str;
    }

    public void zs(String str) {
        this.uk = str;
    }

    public String zt() {
        return this.ul;
    }

    public void zu(String str) {
        this.ul = str;
    }

    public void zv(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (Object obj : str.split("\\*")) {
                if (!TextUtils.isEmpty(obj)) {
                    String[] split = obj.split(",");
                    yH(Double.parseDouble(split[0]));
                    yK(Double.parseDouble(split[1]));
                    yO((float) Integer.parseInt(split[2]));
                    break;
                }
            }
            this.um = str;
        }
    }

    public String zw() {
        return this.un;
    }

    public void zx(String str) {
        this.un = str;
    }

    public void zy(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.replace("F", "");
            try {
                Integer.parseInt(str);
            } catch (Exception e) {
                str = null;
            }
        }
        this.uo = str;
    }

    public int zz() {
        return this.up;
    }
}
