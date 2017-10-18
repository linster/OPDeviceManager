package net.oneplus.odm.insight;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import net.oneplus.odm.a.b;
import net.oneplus.odm.c.a;
import org.json.JSONObject;
import retrofit.client.Client;

public class c {
    private final int au = 300;
    private final int av = 200;
    private Context aw;
    private a ax;
    private net.oneplus.odm.a.a ay;
    private net.oneplus.odm.a.a az;

    public c(Context context) {
        this.aw = context;
        Client bVar = new b(context);
        if (net.oneplus.odm.common.a.an()) {
            try {
                this.az = new net.oneplus.odm.a.a(bVar, net.oneplus.odm.common.c.X);
            } catch (Exception e) {
                Log.e("OneplusAnalyticUploader", e.getMessage());
            }
            this.ay = new net.oneplus.odm.a.a(net.oneplus.odm.common.c.W);
        } else if (net.oneplus.odm.common.a.ao()) {
            try {
                this.az = new net.oneplus.odm.a.a(bVar, net.oneplus.odm.common.c.Z);
            } catch (Exception e2) {
                Log.e("OneplusAnalyticUploader", e2.getMessage());
            }
            this.ay = new net.oneplus.odm.a.a(net.oneplus.odm.common.c.Y);
        } else {
            Log.e("OneplusAnalyticUploader", "Neither O2 or H2");
            this.az = new net.oneplus.odm.a.a(net.oneplus.odm.common.c.X);
            this.ay = new net.oneplus.odm.a.a(net.oneplus.odm.common.c.W);
        }
        this.ax = a.H(context);
    }

    private static NetworkInfo bf(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    private boolean bg(Context context) {
        NetworkInfo bf = bf(context);
        return bf != null && bf.isConnected() && bf.getType() == 0;
    }

    private boolean bh(Context context) {
        NetworkInfo bf = bf(context);
        return bf != null && bf.isConnected() && bf.getType() == 1;
    }

    public void be(JSONObject jSONObject) {
        new d(this, jSONObject).execute(new Void[0]);
    }
}
