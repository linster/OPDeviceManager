package net.oneplus.odm.insight;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import net.oneplus.odm.data.api.b;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.RetrofitError;
import retrofit.client.Response;

class d extends AsyncTask {
    private JSONObject aA;
    final /* synthetic */ c aB;

    d(c cVar, JSONObject jSONObject) {
        this.aB = cVar;
        this.aA = jSONObject;
    }

    protected Boolean bo(Void... voidArr) {
        if (this.aB.bh(this.aB.aw) || this.aB.bg(this.aB.aw)) {
            try {
                b b = this.aB.ay.b("mdmclient0001", "client_credentials", "cd65b204c84348549f228b7cc61352a1", "write");
                if (b == null || b.j() != null) {
                    Log.e("OneplusAnalyticUploader", "get token error");
                    return Boolean.valueOf(false);
                }
                String l = b.l();
                if (l == null) {
                    Log.e("OneplusAnalyticUploader", "token is null");
                    return Boolean.valueOf(false);
                }
                try {
                    net.oneplus.odm.data.b bVar = new net.oneplus.odm.data.b(this.aB.aw, "1000000001");
                    bVar.q(this.aA);
                    Response a = this.aB.az.a(bVar.o(), l);
                    if (a != null) {
                        int status = a.getStatus();
                        if (status >= 200 && status < 300) {
                            Log.v("OneplusAnalyticUploader", "Success upload");
                            return Boolean.valueOf(true);
                        }
                    }
                    Log.v("OneplusAnalyticUploader", "Upload fail");
                    return Boolean.valueOf(false);
                } catch (RetrofitError e) {
                    Log.e("OneplusAnalyticUploader", "Error upload data:" + e.getMessage());
                    return Boolean.valueOf(false);
                } catch (JSONException e2) {
                    Log.e("OneplusAnalyticUploader", e2.getMessage());
                    return Boolean.valueOf(false);
                } catch (IOException e3) {
                    Log.e("OneplusAnalyticUploader", e3.getMessage());
                    return Boolean.valueOf(false);
                }
            } catch (RetrofitError e4) {
                Log.e("OneplusAnalyticUploader", "Error get token:" + e4.getMessage());
                return Boolean.valueOf(false);
            }
        }
        Log.e("OneplusAnalyticUploader", "No connection");
        return Boolean.valueOf(false);
    }

    protected void bp(Boolean bool) {
        if (!bool.booleanValue()) {
            Log.v("OneplusAnalyticUploader", "collect deviceinfo");
            this.aB.ax.F(this.aA.toString());
        }
    }

    protected /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        return bo((Void[]) objArr);
    }

    protected /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
        bp((Boolean) obj);
    }
}
