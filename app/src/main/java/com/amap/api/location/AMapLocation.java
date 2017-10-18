package com.amap.api.location;

import android.location.Location;

public class AMapLocation extends Location {
    private String a = "";
    private String hV = "";
    private String hW = "";
    private String hX = "";
    private String hY = "";
    private String hZ = "";
    private String ia = "";
    private String ib = "";
    private String ic = "";
    private int id = 0;
    private String ie = "success";
    private String if = "";
    private int ig = 0;

    public AMapLocation(Location location) {
        super(location);
    }

    public AMapLocation(String str) {
        super(str);
    }

    public int getErrorCode() {
        return this.id;
    }

    public void hA(int i) {
        this.ig = i;
    }

    public String hB() {
        return this.if;
    }

    public void hC(String str) {
        this.if = str;
    }

    public void hD(int i) {
        if (this.id == 0) {
            switch (i) {
                case 0:
                    this.ie = "success";
                    break;
                case 1:
                    this.ie = "重要参数为空";
                    break;
                case 2:
                    this.ie = "WIFI信息不足";
                    break;
                case 3:
                    this.ie = "请求参数获取出现异常";
                    break;
                case 4:
                    this.ie = "网络连接异常";
                    break;
                case 5:
                    this.ie = "解析XML出错";
                    break;
                case 6:
                    this.ie = "定位结果错误";
                    break;
                case 7:
                    this.ie = "KEY错误";
                    break;
                case 8:
                    this.ie = "其他错误";
                    break;
                case 9:
                    this.ie = "初始化异常";
                    break;
                case 10:
                    this.ie = "定位服务启动失败";
                    break;
                case 11:
                    this.ie = "错误的基站信息，请检查是否插入SIM卡";
                    break;
                case 12:
                    this.ie = "缺少定位权限";
                    break;
            }
            this.id = i;
        }
    }

    public String hE() {
        return this.ie;
    }

    public void hF(String str) {
        this.ie = str;
    }

    public void hG(String str) {
        this.ib = str;
    }

    public void hH(String str) {
        this.ic = str;
    }

    public void hI(String str) {
        this.hZ = str;
    }

    public void hJ(String str) {
        this.a = str;
    }

    public void hK(String str) {
        this.hV = str;
    }

    public void hL(String str) {
        this.hW = str;
    }

    public void hM(String str) {
        this.hX = str;
    }

    public void hN(String str) {
        this.hY = str;
    }

    public void hO(String str) {
        this.ia = str;
    }

    public String hP() {
        return hQ(1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String hQ(int r7) {
        /*
        r6 = this;
        r0 = 0;
        r1 = new org.json.JSONObject;	 Catch:{ Exception -> 0x00d3 }
        r1.<init>();	 Catch:{ Exception -> 0x00d3 }
        switch(r7) {
            case 1: goto L_0x0010;
            case 2: goto L_0x0096;
            case 3: goto L_0x00a0;
            default: goto L_0x0009;
        };
    L_0x0009:
        if (r1 == 0) goto L_0x000f;
    L_0x000b:
        r0 = r1.toString();
    L_0x000f:
        return r0;
    L_0x0010:
        r2 = "country";
        r3 = r6.ib;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "province";
        r3 = r6.a;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "city";
        r3 = r6.hV;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "cityCode";
        r3 = r6.hX;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "district";
        r3 = r6.hW;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "adCode";
        r3 = r6.hY;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "address";
        r3 = r6.hZ;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "road";
        r3 = r6.ic;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "poiName";
        r3 = r6.ia;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "errorCode";
        r3 = r6.id;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "errorInfo";
        r3 = r6.ie;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "locationDetail";
        r3 = r6.if;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "altitude";
        r4 = r6.getAltitude();	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x00d3 }
        r2 = "bearing";
        r3 = r6.getBearing();	 Catch:{ Exception -> 0x00d3 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x00d3 }
        r2 = "speed";
        r3 = r6.getSpeed();	 Catch:{ Exception -> 0x00d3 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x00d3 }
        r2 = r6.getExtras();	 Catch:{ Exception -> 0x00d3 }
        if (r2 != 0) goto L_0x00d7;
    L_0x0096:
        r2 = "time";
        r4 = r6.getTime();	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x00d3 }
    L_0x00a0:
        r2 = "locationType";
        r3 = r6.ig;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "accuracy";
        r3 = r6.getAccuracy();	 Catch:{ Exception -> 0x00d3 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x00d3 }
        r2 = "latitude";
        r4 = r6.getLatitude();	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x00d3 }
        r2 = "longitude";
        r4 = r6.getLongitude();	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r4);	 Catch:{ Exception -> 0x00d3 }
        r2 = "provider";
        r3 = r6.getProvider();	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        goto L_0x0009;
    L_0x00d3:
        r1 = move-exception;
        r1 = r0;
        goto L_0x0009;
    L_0x00d7:
        r3 = "desc";
        r3 = r2.containsKey(r3);	 Catch:{ Exception -> 0x00d3 }
        if (r3 == 0) goto L_0x0096;
    L_0x00e0:
        r3 = "desc";
        r4 = "desc";
        r2 = r2.getString(r4);	 Catch:{ Exception -> 0x00d3 }
        r1.put(r3, r2);	 Catch:{ Exception -> 0x00d3 }
        goto L_0x0096;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.location.AMapLocation.hQ(int):java.lang.String");
    }

    public int hz() {
        return this.ig;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("province=" + this.a + "#");
        stringBuffer.append("city=" + this.hV + "#");
        stringBuffer.append("district=" + this.hW + "#");
        stringBuffer.append("cityCode=" + this.hX + "#");
        stringBuffer.append("adCode=" + this.hY + "#");
        stringBuffer.append("address=" + this.hZ + "#");
        stringBuffer.append("country=" + this.ib + "#");
        stringBuffer.append("road=" + this.ic + "#");
        stringBuffer.append("poiName=" + this.ia + "#");
        stringBuffer.append("errorCode=" + this.id + "#");
        stringBuffer.append("errorInfo=" + this.ie + "#");
        stringBuffer.append("locationDetail=" + this.if + "#");
        stringBuffer.append("locationType=" + this.ig);
        return stringBuffer.toString();
    }
}
