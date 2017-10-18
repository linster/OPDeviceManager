package net.oneplus.odm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class b {
    private static b bq = null;
    private SharedPreferences bo;
    private Editor bp = this.bo.edit();

    private b(Context context) {
        this.bo = context.getSharedPreferences("DeviceManagerAPP", 0);
    }

    public static b bT(Context context) {
        if (bq == null) {
            bq = new b(context);
        }
        return bq;
    }

    public String bU(String str, String str2) {
        return this.bo.getString(str, str2);
    }

    public boolean bV(String str, boolean z) {
        return this.bo.getBoolean(str, z);
    }

    public long bW(String str, long j) {
        return this.bo.getLong(str, j);
    }

    public void bX(String str, String str2) {
        this.bp.putString(str, str2);
        this.bp.commit();
    }

    public void bY(String str, boolean z) {
        this.bp.putBoolean(str, z);
        this.bp.commit();
    }

    public void bZ(String str, long j) {
        this.bp.putLong(str, j);
        this.bp.commit();
    }
}
