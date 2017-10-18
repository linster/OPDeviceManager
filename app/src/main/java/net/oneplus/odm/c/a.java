package net.oneplus.odm.c;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.List;

public class a {
    private static String L = "DeviceInfoDatabaseHelper";
    private static a N;
    private Context M;

    private a(Context context) {
        this.M = context;
    }

    public static a H(Context context) {
        N = new a(context);
        return N;
    }

    public void F(String str) {
        ContentResolver contentResolver = this.M.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("OP_deviceinfo_payload", str);
        contentValues.put("OP_deviceinfo_payload_timestamp", Long.valueOf(System.currentTimeMillis()));
        try {
            contentResolver.insert(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), contentValues);
        } catch (IllegalArgumentException e) {
            Log.e(L, e.getMessage());
        }
    }

    public Cursor G() {
        try {
            Cursor query = this.M.getContentResolver().query(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), new String[]{"OP_deviceinfo_payload_id", "OP_deviceinfo_payload", "OP_deviceinfo_payload_timestamp"}, null, null, null);
            Log.v(L, "get cursor");
            return query;
        } catch (IllegalArgumentException e) {
            Log.e(L, e.getMessage());
            return null;
        }
    }

    public void I(int i) {
        try {
            this.M.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), "OP_deviceinfo_payload_id=" + i, null);
        } catch (IllegalArgumentException e) {
            Log.e(L, e.getMessage());
        }
        Log.v(L, "remove payload");
    }

    public void J(List list) {
        int size = list.size();
        String str = "";
        int i = 0;
        while (i < size) {
            str = i == size + -1 ? str + list.get(i) : str + list.get(i) + ",";
            i++;
        }
        try {
            this.M.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), "OP_deviceinfo_payload_id IN (" + str + ")", null);
        } catch (IllegalArgumentException e) {
            Log.e(L, e.getMessage());
        }
        Log.v(L, "remove payloads");
    }
}
