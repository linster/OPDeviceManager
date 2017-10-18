package com.loc;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.HashMap;
import java.util.Map;

public class bP implements p {
    private bo tM;

    public static String yg(String str, String str2) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("dynamicversion", str2);
        return ae.nV(hashMap);
    }

    public static String yh(String str, String str2) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("status", str2);
        return ae.nV(hashMap);
    }

    public static String yi(String str) {
        Map hashMap = new HashMap();
        hashMap.put("sdkname", str);
        return ae.nV(hashMap);
    }

    public static String yj(String str) {
        Map hashMap = new HashMap();
        hashMap.put("filename", str);
        return ae.nV(hashMap);
    }

    public static String yk(String str, String str2, String str3, String str4) {
        Map hashMap = new HashMap();
        hashMap.put("filename", str);
        hashMap.put("sdkname", str2);
        hashMap.put("dynamicversion", str4);
        hashMap.put("version", str3);
        return ae.nV(hashMap);
    }

    public ContentValues lX() {
        if (this.tM == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("filename", this.tM.vt());
        contentValues.put("md5", this.tM.vu());
        contentValues.put("sdkname", this.tM.vv());
        contentValues.put("version", this.tM.vw());
        contentValues.put("dynamicversion", this.tM.vx());
        contentValues.put("status", this.tM.vy());
        return contentValues;
    }

    public /* synthetic */ Object lY(Cursor cursor) {
        return ye(cursor);
    }

    public String lZ() {
        return "file";
    }

    public bo ye(Cursor cursor) {
        String string = cursor.getString(1);
        String string2 = cursor.getString(2);
        String string3 = cursor.getString(3);
        String string4 = cursor.getString(4);
        String string5 = cursor.getString(5);
        return new E(string2, string3, string, string4, string5).mH(cursor.getString(6)).mI();
    }

    public void yf(bo boVar) {
        this.tM = boVar;
    }
}
