package net.oneplus.odm.c;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.List;

public class b {
    private static String O = "DeviceManagerDatabaseHelper";
    private static b Q;
    private Context P;

    private b(Context context) {
        this.P = context;
    }

    public static b L(Context context) {
        Q = new b(context);
        return Q;
    }

    public Cursor K() {
        Cursor query;
        try {
            query = this.P.getContentResolver().query(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table"), new String[]{"OP_payload_id", "OP_payload", "OP_payload_timestamp", "OP_payload_category"}, null, null, null);
        } catch (IllegalArgumentException e) {
            Log.e(O, e.getMessage());
            query = null;
        }
        Log.v(O, "get cursor");
        return query;
    }

    public void M(int i) {
        try {
            this.P.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table"), "OP_payload_id=" + i, null);
        } catch (IllegalArgumentException e) {
            Log.e(O, e.getMessage());
        }
        Log.v(O, "remove payload");
    }

    public void N(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                stringBuilder.append(list.get(i));
            } else {
                stringBuilder.append(list.get(i));
                stringBuilder.append(",");
            }
        }
        try {
            this.P.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table"), "OP_payload_id IN (" + stringBuilder.toString() + ")", null);
        } catch (IllegalArgumentException e) {
            Log.e(O, e.getMessage());
        }
        Log.v(O, "remove payloads");
    }
}
