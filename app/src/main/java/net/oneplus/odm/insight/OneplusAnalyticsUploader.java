package net.oneplus.odm.insight;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import net.oneplus.odm.common.Def;
import net.oneplus.odm.common.Utils;
import net.oneplus.odm.data.Capsule;
import net.oneplus.odm.data.api.TokenResponse;
import net.oneplus.odm.database.DeviceInfoDatabaseHelper;
import net.oneplus.odm.uploader.CustomClient;
import net.oneplus.odm.uploader.DeviceManagerUploader;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class OneplusAnalyticsUploader {
    private final int RESULT_CODE_MULTIPLE_CHOICES;
    private final int RESULT_CODE_OK;
    private Context mContext;
    private DeviceInfoDatabaseHelper mDeviceInfoDatabaseHelper;
    private DeviceManagerUploader mDeviceManagerOAuthUploader;
    private DeviceManagerUploader mDeviceManagerUploader;

    class UploadDeviceInfoTask extends AsyncTask<Void, Object, Boolean> {
        private JSONObject mPayload;

        UploadDeviceInfoTask(JSONObject payload) {
            this.mPayload = payload;
        }

        protected Boolean doInBackground(Void... param) {
            if (OneplusAnalyticsUploader.this.isConnectedWifi(OneplusAnalyticsUploader.this.mContext) || OneplusAnalyticsUploader.this.isConnectedMobile(OneplusAnalyticsUploader.this.mContext)) {
                try {
                    TokenResponse tokenResult = OneplusAnalyticsUploader.this.mDeviceManagerOAuthUploader.requestToken("mdmclient0001", "client_credentials", "cd65b204c84348549f228b7cc61352a1", "write");
                    if (tokenResult == null || tokenResult.getError() != null) {
                        Log.e("OneplusAnalyticUploader", "get token error");
                        return Boolean.valueOf(false);
                    }
                    String token = tokenResult.getToken();
                    if (token == null) {
                        Log.e("OneplusAnalyticUploader", "token is null");
                        return Boolean.valueOf(false);
                    }
                    try {
                        Capsule capsule = new Capsule(OneplusAnalyticsUploader.this.mContext, "1000000001");
                        capsule.addPayload(this.mPayload);
                        Response result = OneplusAnalyticsUploader.this.mDeviceManagerUploader.postData(capsule.dump(), token);
                        if (result != null) {
                            int resultCode = result.getStatus();
                            if (resultCode >= 200 && resultCode < 300) {
                                Log.v("OneplusAnalyticUploader", "Success upload");
                                return Boolean.valueOf(true);
                            }
                        }
                        Log.v("OneplusAnalyticUploader", "Upload fail");
                        return Boolean.valueOf(false);
                    } catch (RetrofitError re) {
                        Log.e("OneplusAnalyticUploader", "Error upload data:" + re.getMessage());
                        return Boolean.valueOf(false);
                    } catch (JSONException e) {
                        Log.e("OneplusAnalyticUploader", e.getMessage());
                        return Boolean.valueOf(false);
                    } catch (IOException e2) {
                        Log.e("OneplusAnalyticUploader", e2.getMessage());
                        return Boolean.valueOf(false);
                    }
                } catch (RetrofitError re2) {
                    Log.e("OneplusAnalyticUploader", "Error get token:" + re2.getMessage());
                    return Boolean.valueOf(false);
                }
            }
            Log.e("OneplusAnalyticUploader", "No connection");
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean result) {
            if (!result.booleanValue()) {
                Log.v("OneplusAnalyticUploader", "collect deviceinfo");
                OneplusAnalyticsUploader.this.mDeviceInfoDatabaseHelper.addDeviceInfoPayload(this.mPayload.toString());
            }
        }
    }

    public OneplusAnalyticsUploader(Context context) {
        this.RESULT_CODE_OK = 200;
        this.RESULT_CODE_MULTIPLE_CHOICES = 300;
        this.mContext = context;
        CustomClient client = new CustomClient(context);
        if (Utils.isH2()) {
            try {
                this.mDeviceManagerUploader = new DeviceManagerUploader(client, Def.HYDROGEN_SERVER_URL);
            } catch (Exception e) {
                Log.e("OneplusAnalyticUploader", e.getMessage());
            }
            this.mDeviceManagerOAuthUploader = new DeviceManagerUploader(Def.HYDROGEN_OAUTH_URL);
        } else if (Utils.isO2()) {
            try {
                this.mDeviceManagerUploader = new DeviceManagerUploader(client, Def.OXYGEN_SERVER_URL);
            } catch (Exception e2) {
                Log.e("OneplusAnalyticUploader", e2.getMessage());
            }
            this.mDeviceManagerOAuthUploader = new DeviceManagerUploader(Def.OXYGEN_OAUTH_URL);
        } else {
            Log.e("OneplusAnalyticUploader", "Neither O2 or H2");
            this.mDeviceManagerUploader = new DeviceManagerUploader(Def.HYDROGEN_SERVER_URL);
            this.mDeviceManagerOAuthUploader = new DeviceManagerUploader(Def.HYDROGEN_OAUTH_URL);
        }
        this.mDeviceInfoDatabaseHelper = DeviceInfoDatabaseHelper.getInstance(context);
    }

    public void uploadDeviceInfo(JSONObject json) {
        new UploadDeviceInfoTask(json).execute(new Void[0]);
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    private boolean isConnectedWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null && info.isConnected() && info.getType() == 1) {
            return true;
        }
        return false;
    }

    private boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null && info.isConnected() && info.getType() == 0) {
            return true;
        }
        return false;
    }
}
