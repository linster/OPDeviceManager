package net.oneplus.odm.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.List;

public class DeviceInfoDatabaseHelper {
    private static String TAG;
    private static DeviceInfoDatabaseHelper mDeviceInfoDatabaseHelper;
    private Context mContext;

    static {
        TAG = "DeviceInfoDatabaseHelper";
    }

    private DeviceInfoDatabaseHelper(Context context) {
        this.mContext = context;
    }

    public static DeviceInfoDatabaseHelper getInstance(Context context) {
        mDeviceInfoDatabaseHelper = new DeviceInfoDatabaseHelper(context);
        return mDeviceInfoDatabaseHelper;
    }

    public void addDeviceInfoPayload(String payload) {
        ContentResolver resolver = this.mContext.getContentResolver();
        ContentValues content = new ContentValues();
        content.put("OP_deviceinfo_payload", payload);
        content.put("OP_deviceinfo_payload_timestamp", Long.valueOf(System.currentTimeMillis()));
        try {
            resolver.insert(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), content);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Cursor getAllDeviceInfoPayloadCursor() {
        try {
            Cursor cursor = this.mContext.getContentResolver().query(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), new String[]{"OP_deviceinfo_payload_id", "OP_deviceinfo_payload", "OP_deviceinfo_payload_timestamp"}, null, null, null);
            Log.v(TAG, "get cursor");
            return cursor;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public void removeDeviceInfoPayloadList(List<Integer> payloadIdList) {
        String payloadIdListString = "";
        int payloadIdListSize = payloadIdList.size();
        for (int i = 0; i < payloadIdListSize; i++) {
            if (i == payloadIdListSize - 1) {
                payloadIdListString = payloadIdListString + payloadIdList.get(i);
            } else {
                payloadIdListString = payloadIdListString + payloadIdList.get(i) + ",";
            }
        }
        try {
            this.mContext.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), "OP_deviceinfo_payload_id IN (" + payloadIdListString + ")", null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.v(TAG, "remove payloads");
    }

    public void removeDeviceInfoPayload(int payloadId) {
        try {
            this.mContext.getContentResolver().delete(Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_deviceinfo_payload_upload_table"), "OP_deviceinfo_payload_id=" + payloadId, null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.v(TAG, "remove payload");
    }
}
