package net.oneplus.odm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DeviceManagerSetting {
    private static DeviceManagerSetting mSetting;
    private SharedPreferences mPreference;
    private Editor mPreferenceEditor;

    static {
        mSetting = null;
    }

    private DeviceManagerSetting(Context context) {
        this.mPreference = context.getSharedPreferences("DeviceManagerAPP", 0);
        this.mPreferenceEditor = this.mPreference.edit();
    }

    public static DeviceManagerSetting getInstance(Context context) {
        if (mSetting == null) {
            mSetting = new DeviceManagerSetting(context);
        }
        return mSetting;
    }

    public void setPreference(String key, String value) {
        this.mPreferenceEditor.putString(key, value);
        this.mPreferenceEditor.commit();
    }

    public void setPreference(String key, boolean value) {
        this.mPreferenceEditor.putBoolean(key, value);
        this.mPreferenceEditor.commit();
    }

    public void setPreference(String key, long value) {
        this.mPreferenceEditor.putLong(key, value);
        this.mPreferenceEditor.commit();
    }

    public String getPreference(String key, String defValue) {
        return this.mPreference.getString(key, defValue);
    }

    public boolean getPreference(String key, boolean defValue) {
        return this.mPreference.getBoolean(key, defValue);
    }

    public long getPreference(String key, long defValue) {
        return this.mPreference.getLong(key, defValue);
    }
}
