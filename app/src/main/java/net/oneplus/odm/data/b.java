package net.oneplus.odm.data;

import android.content.Context;
import java.util.ArrayList;
import net.oneplus.odm.analytics.OneplusAnalyticsPayload;
import net.oneplus.odm.common.Util;
import net.oneplus.odm.common.a;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit.mime.TypedByteArray;

public class b {
    private String r;
    private JSONObject s = new JSONObject();
    private int t = 0;
    private Context u;
    private ArrayList v = new ArrayList();
    private JSONArray w = new JSONArray();

    public b(Context context, String str) {
        this.u = context;
        this.r = str;
    }

    private void r() {
        this.s.put("id", a.V());
        this.s.put("chs", a.X(this.u));
        this.s.put("ch1", a.Y(this.u));
        this.s.put("mn", a.aa());
        this.s.put("ov", a.ab());
        this.s.put("romv", Util.getRomVersion());
        this.s.put("av", Util.getAndroidVersion());
        this.s.put("la", a.Z(this.u).getLanguage());
        this.s.put("noi", Util.getNetworkOperator(this.u));
        this.s.put("appid", this.r);
        this.s.put("ode", this.w);
    }

    public void m(OneplusAnalyticsPayload oneplusAnalyticsPayload) {
        this.w.put(new JSONObject(oneplusAnalyticsPayload.getPayload()));
        this.v.add(Integer.valueOf(oneplusAnalyticsPayload.getId()));
        this.t++;
    }

    public int n() {
        return this.t;
    }

    public TypedByteArray o() {
        r();
        return new TypedByteArray(null, this.s.toString().getBytes());
    }

    public ArrayList p() {
        return this.v;
    }

    public void q(JSONObject jSONObject) {
        this.w.put(jSONObject);
        this.t++;
    }
}
