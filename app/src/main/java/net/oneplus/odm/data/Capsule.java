package net.oneplus.odm.data;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import net.oneplus.odm.analytics.OneplusAnalyticsPayload;
import net.oneplus.odm.common.Util;
import net.oneplus.odm.common.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.mime.TypedByteArray;

public class Capsule {
    private String mAppId;
    private JSONObject mCapsule;
    private int mCapsulePayloadCount;
    private Context mContext;
    private ArrayList<Integer> mPayloadIdList;
    private JSONArray mPayloadList;

    public Capsule(Context context, String appId) {
        this.mPayloadList = new JSONArray();
        this.mPayloadIdList = new ArrayList();
        this.mCapsule = new JSONObject();
        this.mCapsulePayloadCount = 0;
        this.mContext = context;
        this.mAppId = appId;
    }

    private void createCapsule() throws JSONException, IOException {
        this.mCapsule.put("id", Utils.getDeviceId());
        this.mCapsule.put("im", Utils.getIMEI(this.mContext));
        this.mCapsule.put("im1", Utils.getIMEI1(this.mContext));
        this.mCapsule.put("mn", Utils.getModelName());
        this.mCapsule.put("ov", Utils.getOSVersion());
        this.mCapsule.put("romv", Util.getRomVersion());
        this.mCapsule.put("av", Util.getAndroidVersion());
        this.mCapsule.put("la", Utils.getLocale(this.mContext).getLanguage());
        this.mCapsule.put("noi", Util.getNetworkOperator(this.mContext));
        this.mCapsule.put("appid", this.mAppId);
        this.mCapsule.put("ode", this.mPayloadList);
    }

    public void addPayload(OneplusAnalyticsPayload payload) throws JSONException {
        this.mPayloadList.put(new JSONObject(payload.getPayload()));
        this.mPayloadIdList.add(Integer.valueOf(payload.getId()));
        this.mCapsulePayloadCount++;
    }

    public void addPayload(JSONObject json) throws JSONException {
        this.mPayloadList.put(json);
        this.mCapsulePayloadCount++;
    }

    public int getCount() {
        return this.mCapsulePayloadCount;
    }

    public TypedByteArray dump() throws JSONException, IOException {
        createCapsule();
        return new TypedByteArray(null, this.mCapsule.toString().getBytes());
    }

    public ArrayList<Integer> getIdList() {
        return this.mPayloadIdList;
    }
}
