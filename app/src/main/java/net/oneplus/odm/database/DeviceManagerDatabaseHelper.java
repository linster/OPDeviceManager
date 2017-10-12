package net.oneplus.odm.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.List;

public class DeviceManagerDatabaseHelper {
    private static String TAG;
    private static DeviceManagerDatabaseHelper mDeviceManagerDatabaseHelper;
    private Context mContext;

    static {
        TAG = "DeviceManagerDatabaseHelper";
    }

    private DeviceManagerDatabaseHelper(Context context) {
        this.mContext = context;
    }

    public static DeviceManagerDatabaseHelper getInstance(Context context) {
        mDeviceManagerDatabaseHelper = new DeviceManagerDatabaseHelper(context);
        return mDeviceManagerDatabaseHelper;
    }

    public Cursor getAllPayloadCursor() {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table"), new String[]{"OP_payload_id", "OP_payload", "OP_payload_timestamp", "OP_payload_category"}, null, null, null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.v(TAG, "get cursor");
        return cursor;
    }

    public void removePayloadList(List<Integer> payloadIdList) {
        StringBuilder payloadIdListStringBuilder = new StringBuilder();
        int payloadIdListSize = payloadIdList.size();
        for (int i = 0; i < payloadIdListSize; i++) {
            if (i == payloadIdListSize - 1) {
                payloadIdListStringBuilder.append(payloadIdList.get(i));
            } else {
                payloadIdListStringBuilder.append(payloadIdList.get(i));
                payloadIdListStringBuilder.append(",");
            }
        }
        try {
            this.mContext.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table"), "OP_payload_id IN (" + payloadIdListStringBuilder.toString() + ")", null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.v(TAG, "remove payloads");
    }

    public void removePayload(int payloadId) {
        try {
            this.mContext.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table"), "OP_payload_id=" + payloadId, null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.v(TAG, "remove payload");
    }
}
