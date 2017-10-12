package com.amap.api.location;

import android.location.Location;

public class AMapLocation extends Location {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private int j;
    private String k;
    private String l;
    private int m;

    public int getLocationType() {
        return this.m;
    }

    public void setLocationType(int i) {
        this.m = i;
    }

    public String getLocationDetail() {
        return this.l;
    }

    public void setLocationDetail(String str) {
        this.l = str;
    }

    public int getErrorCode() {
        return this.j;
    }

    public void setErrorCode(int i) {
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

    public String getErrorInfo() {
        return this.k;
    }

    public void setErrorInfo(String str) {
        this.k = str;
    }

    public void setCountry(String str) {
        this.h = str;
    }

    public void setRoad(String str) {
        this.i = str;
    }

    public void setAddress(String str) {
        this.f = str;
    }

    public AMapLocation(String str) {
        super(str);
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = "";
        this.f = "";
        this.g = "";
        this.h = "";
        this.i = "";
        this.j = 0;
        this.k = "success";
        this.l = "";
        this.m = 0;
    }

    public AMapLocation(Location location) {
        super(location);
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = "";
        this.f = "";
        this.g = "";
        this.h = "";
        this.i = "";
        this.j = 0;
        this.k = "success";
        this.l = "";
        this.m = 0;
    }

    public void setProvince(String str) {
        this.a = str;
    }

    public void setCity(String str) {
        this.b = str;
    }

    public void setDistrict(String str) {
        this.c = str;
    }

    public void setCityCode(String str) {
        this.d = str;
    }

    public void setAdCode(String str) {
        this.e = str;
    }

    public void setPoiName(String str) {
        this.g = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("province=" + this.a + "#");
        stringBuffer.append("city=" + this.b + "#");
        stringBuffer.append("district=" + this.c + "#");
        stringBuffer.append("cityCode=" + this.d + "#");
        stringBuffer.append("adCode=" + this.e + "#");
        stringBuffer.append("address=" + this.f + "#");
        stringBuffer.append("country=" + this.h + "#");
        stringBuffer.append("road=" + this.i + "#");
        stringBuffer.append("poiName=" + this.g + "#");
        stringBuffer.append("errorCode=" + this.j + "#");
        stringBuffer.append("errorInfo=" + this.k + "#");
        stringBuffer.append("locationDetail=" + this.l + "#");
        stringBuffer.append("locationType=" + this.m);
        return stringBuffer.toString();
    }

    public String toStr() {
        return toStr(1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toStr(int r7) {
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
        r3 = r6.h;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "province";
        r3 = r6.a;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "city";
        r3 = r6.b;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "cityCode";
        r3 = r6.d;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "district";
        r3 = r6.c;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "adCode";
        r3 = r6.e;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "address";
        r3 = r6.f;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "road";
        r3 = r6.i;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "poiName";
        r3 = r6.g;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "errorCode";
        r3 = r6.j;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "errorInfo";
        r3 = r6.k;	 Catch:{ Exception -> 0x00d3 }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x00d3 }
        r2 = "locationDetail";
        r3 = r6.l;	 Catch:{ Exception -> 0x00d3 }
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
        r3 = r6.m;	 Catch:{ Exception -> 0x00d3 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.location.AMapLocation.toStr(int):java.lang.String");
    }
}
