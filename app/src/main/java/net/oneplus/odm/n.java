package net.oneplus.odm;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.Log;
import com.oneplus.config.ConfigGrabber;
import com.oneplus.config.ConfigObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class n {
    private static n cu;
    private Context cA;
    private Handler cB;
    private HandlerThread cC;
    private String cr = "device_event";
    private String cs = "device_info";
    private String ct = "device_sensitive_data";
    private ConfigGrabber cv;
    private ConfigObserver cw;
    private boolean cx = true;
    private boolean cy = true;
    private boolean cz = true;

    private n(Context context) {
        this.cA = context;
        this.cC = new HandlerThread("MDMConfigHandler", 10);
        this.cw = new ConfigObserver(this.cA, this.cB, new o(this), "MDMConfig");
        this.cv = new ConfigGrabber(this.cA, "MDMConfig");
        this.cw.register();
        cM(this.cv.grabConfig());
    }

    public static n cI(Context context) {
        if (cu == null) {
            cu = new n(context);
        }
        return cu;
    }

    private void cM(JSONArray jSONArray) {
        if (jSONArray == null) {
            Log.w("MDMConfig", "jsonArray is null");
            return;
        }
        int i = 0;
        while (i < jSONArray.length()) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("region");
                String string2 = jSONObject.getString("timezone");
                String str = SystemProperties.get("gsm.sim.operator.iso-country", "");
                String id = TimeZone.getDefault().getID();
                if (!str.equals("")) {
                    String[] split = str.split(",");
                    ArrayList arrayList = new ArrayList(Arrays.asList(string.split(",")));
                    for (Object contains : split) {
                        if (arrayList.contains(contains)) {
                            cN(jSONObject.getJSONArray("value"));
                            Log.d("MDMConfig", "Has config for this region, updated");
                            return;
                        }
                    }
                    if (new ArrayList(Arrays.asList(string2.split(","))).contains(id)) {
                        cN(jSONObject.getJSONArray("value"));
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

    private void cN(JSONArray jSONArray) {
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject.has(this.cs)) {
                this.cy = jSONObject.getBoolean(this.cs);
                Log.v("MDMConfig", "deviceinfo:" + this.cy);
            }
            if (jSONObject.has(this.cr)) {
                this.cx = jSONObject.getBoolean(this.cr);
                Log.v("MDMConfig", "deviceevent:" + this.cx);
            }
            if (jSONObject.has(this.ct)) {
                this.cz = jSONObject.getBoolean(this.ct);
                Log.v("MDMConfig", "sensitive data:" + this.cz);
            }
        }
    }

    public boolean cJ() {
        return this.cz;
    }

    public boolean cK() {
        return this.cy;
    }

    public boolean cL() {
        return this.cx;
    }
}
