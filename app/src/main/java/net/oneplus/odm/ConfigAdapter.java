package net.oneplus.odm;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.Log;

import com.oneplus.config.ConfigGrabber;
import com.oneplus.config.ConfigObserver;
import com.oneplus.config.ConfigObserver.ConfigUpdater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigAdapter {
    private static ConfigAdapter mConfigAdapter;
    private String CONFIG_KEY_DEVICEEVENT;
    private String CONFIG_KEY_DEVICEINFO;
    private String CONFIG_KEY_SENSITIVE_DATA;
    private ConfigGrabber mConfigGrabber;
    private ConfigObserver mConfigObserver;
    private boolean mConfigValueDeviceEvent;
    private boolean mConfigValueDeviceInfo;
    private boolean mConfigValueSensitiveData;
    private Context mContext;
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    class MDMConfigUpdater implements ConfigUpdater {
        MDMConfigUpdater() {
        }

        public void updateConfig(JSONArray configJSONArray) {
            ConfigAdapter.this.resolveMDMConfigFromJSON(configJSONArray);
        }
    }

    private ConfigAdapter(Context context) {
        this.mConfigValueDeviceInfo = true;
        this.mConfigValueDeviceEvent = true;
        this.mConfigValueSensitiveData = true;
        this.CONFIG_KEY_DEVICEINFO = "device_info";
        this.CONFIG_KEY_DEVICEEVENT = "device_event";
        this.CONFIG_KEY_SENSITIVE_DATA = "device_sensitive_data";
        this.mContext = context;
        this.mHandlerThread = new HandlerThread("MDMConfigHandler", 10);
        this.mConfigObserver = new ConfigObserver(this.mContext, this.mHandler, new MDMConfigUpdater(), "MDMConfig");
        this.mConfigGrabber = new ConfigGrabber(this.mContext, "MDMConfig");
        this.mConfigObserver.register();
        resolveMDMConfigFromJSON(this.mConfigGrabber.grabConfig());
    }

    public static ConfigAdapter getInstance(Context context) {
        if (mConfigAdapter == null) {
            mConfigAdapter = new ConfigAdapter(context);
        }
        return mConfigAdapter;
    }

    public boolean checkUploadDeviceInfo() {
        return this.mConfigValueDeviceInfo;
    }

    public boolean checkUploadDeviceEvent() {
        return this.mConfigValueDeviceEvent;
    }

    public boolean checkSensitiveData() {
        return this.mConfigValueSensitiveData;
    }

    private void resolveMDMConfigFromJSON(JSONArray jsonArray) {
        if (jsonArray == null) {
            Log.w("MDMConfig", "jsonArray is null");
            return;
        }
        int i = 0;
        while (i < jsonArray.length()) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                String configRegion = json.getString("region");
                String configTimezone = json.getString("timezone");
                String currentRegion = SystemProperties.get("gsm.sim.operator.iso-country", "");
                String currentTimeZone = TimeZone.getDefault().getID();
                if (!currentRegion.equals("")) {
                    String[] currentRegionArray = currentRegion.split(",");
                    ArrayList<String> configRegionList = new ArrayList(Arrays.asList(configRegion.split(",")));
                    for (Object contains : currentRegionArray) {
                        if (configRegionList.contains(contains)) {
                            updateMDMConfig(json.getJSONArray("value"));
                            Log.d("MDMConfig", "Has config for this region, updated");
                            return;
                        }
                    }
                    if (new ArrayList(Arrays.asList(configTimezone.split(","))).contains(currentTimeZone)) {
                        updateMDMConfig(json.getJSONArray("value"));
                        Log.d("MDMConfig", "Has config for this timezone, updated");
                        return;
                    }
                    Log.d("MDMConfig", "No config for this region and timezone, no updated");
                    i++;
                } else {
                    return;
                }
            } catch (JSONException e) {
                Log.e("MDMConfig", "[OnlineConfig] MDM, error message:" + e.getMessage());
            } catch (Exception e2) {
                Log.e("MDMConfig", "[OnlineConfig] MDM, error message:" + e2.getMessage());
            }
        }
    }

    private void updateMDMConfig(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            if (json.has(this.CONFIG_KEY_DEVICEINFO)) {
                this.mConfigValueDeviceInfo = json.getBoolean(this.CONFIG_KEY_DEVICEINFO);
                Log.v("MDMConfig", "deviceinfo:" + this.mConfigValueDeviceInfo);
            }
            if (json.has(this.CONFIG_KEY_DEVICEEVENT)) {
                this.mConfigValueDeviceEvent = json.getBoolean(this.CONFIG_KEY_DEVICEEVENT);
                Log.v("MDMConfig", "deviceevent:" + this.mConfigValueDeviceEvent);
            }
            if (json.has(this.CONFIG_KEY_SENSITIVE_DATA)) {
                this.mConfigValueSensitiveData = json.getBoolean(this.CONFIG_KEY_SENSITIVE_DATA);
                Log.v("MDMConfig", "sensitive data:" + this.mConfigValueSensitiveData);
            }
        }
    }
}
