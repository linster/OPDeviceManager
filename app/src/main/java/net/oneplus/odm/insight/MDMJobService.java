package net.oneplus.odm.insight;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.oneplus.odm.ConfigAdapter;
import net.oneplus.odm.DeviceManagerSetting;
import net.oneplus.odm.common.Def;
import net.oneplus.odm.common.Utils;
import net.oneplus.odm.data.Capsule;
import net.oneplus.odm.data.api.TokenResponse;
import net.oneplus.odm.database.DeviceInfoDatabaseHelper;
import net.oneplus.odm.database.DeviceManagerDatabaseHelper;
import net.oneplus.odm.uploader.CustomClient;
import net.oneplus.odm.uploader.DeviceManagerUploader;
import retrofit.RetrofitError;

public class MDMJobService extends JobService {
    private static ExecutorService SINGLE_TASK_EXECUTOR;
    private HashMap<String, Integer> mCapsulePayloadLimitation;
    private HashMap<String, Capsule> mCapsuleStorage;
    private ConfigAdapter mConfigAdapter;
    private Context mContext;
    private DeviceManagerDatabaseHelper mDatabaseHelper;
    private DeviceInfoDatabaseHelper mDeviceInfoDatabaseHelper;
    private DeviceManagerUploader mDeviceManagerOAuthUploader;
    private DeviceManagerUploader mDeviceManagerUploader;
    private int mJobCount;
    private JobParameters mJobParameters;
    private int mServerStatus;
    private DeviceManagerSetting mSetting;

    /* renamed from: net.oneplus.odm.insight.MDMJobService.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ boolean val$isRetry;

        AnonymousClass1(boolean val$isRetry) {
            this.val$isRetry = val$isRetry;
        }

        public void run() {
            new UploadDeviceInfoAsyncTask(this.val$isRetry).executeOnExecutor(MDMJobService.SINGLE_TASK_EXECUTOR, new Void[0]);
        }
    }

    /* renamed from: net.oneplus.odm.insight.MDMJobService.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ boolean val$isRetry;

        AnonymousClass2(boolean val$isRetry) {
            this.val$isRetry = val$isRetry;
        }

        public void run() {
            new UploadAnalyticsAsyncTask(this.val$isRetry).executeOnExecutor(MDMJobService.SINGLE_TASK_EXECUTOR, new Void[0]);
        }
    }

    class UploadAnalyticsAsyncTask extends AsyncTask<Void, Object, Object> {
        private boolean mIsRetry;

        public UploadAnalyticsAsyncTask(boolean isRetry) {
            this.mIsRetry = false;
            this.mIsRetry = isRetry;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected java.lang.Object doInBackground(java.lang.Void... r15) {
            /*
            r14 = this;
            r7 = 0;
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.getAuthToken();
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9 = r9.mServerStatus;
            if (r9 != 0) goto L_0x001e;
        L_0x000e:
            r9 = "MDMJob";
            r10 = "Server is fail.";
            android.util.Log.e(r9, r10);
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.completeDeviceEvent();
            r9 = 0;
            return r9;
        L_0x001e:
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9 = r9.mDatabaseHelper;
            r1 = r9.getAllPayloadCursor();
            if (r1 != 0) goto L_0x003a;
        L_0x002a:
            r9 = "MDMJob";
            r10 = "Cursor is null.";
            android.util.Log.i(r9, r10);
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.completeDeviceEvent();
            r9 = 0;
            return r9;
        L_0x003a:
            r9 = r1.getCount();
            if (r9 != 0) goto L_0x0053;
        L_0x0040:
            r9 = "MDMJob";
            r10 = "No event.";
            android.util.Log.i(r9, r10);
            r1.close();
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.completeDeviceEvent();
            r9 = 0;
            return r9;
        L_0x0053:
            r1.moveToFirst();
        L_0x0056:
            r5 = 0;
            r6 = new net.oneplus.odm.analytics.OneplusAnalyticsPayload;	 Catch:{ JSONException -> 0x01f4, RetrofitError -> 0x01f7, Exception -> 0x01f9 }
            r9 = 0;
            r9 = r1.getInt(r9);	 Catch:{ JSONException -> 0x01f4, RetrofitError -> 0x01f7, Exception -> 0x01f9 }
            r10 = 1;
            r10 = r1.getString(r10);	 Catch:{ JSONException -> 0x01f4, RetrofitError -> 0x01f7, Exception -> 0x01f9 }
            r11 = 2;
            r11 = r1.getString(r11);	 Catch:{ JSONException -> 0x01f4, RetrofitError -> 0x01f7, Exception -> 0x01f9 }
            r12 = 3;
            r12 = r1.getString(r12);	 Catch:{ JSONException -> 0x01f4, RetrofitError -> 0x01f7, Exception -> 0x01f9 }
            r6.<init>(r9, r10, r11, r12);	 Catch:{ JSONException -> 0x01f4, RetrofitError -> 0x01f7, Exception -> 0x01f9 }
            r10 = java.lang.System.currentTimeMillis();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r6.getTimestamp();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r12 = java.lang.Long.parseLong(r9);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r10 - r12;
            r12 = 889032704; // 0x34fd9000 float:4.7229696E-7 double:4.39240517E-315;
            r9 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
            if (r9 <= 0) goto L_0x00a8;
        L_0x0084:
            r9 = "MDMJob";
            r10 = "Clean expire data";
            android.util.Log.d(r9, r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r6.getId();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9.removePayload(r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
        L_0x0096:
            r9 = r1.moveToNext();
            if (r9 != 0) goto L_0x0056;
        L_0x009c:
            if (r1 == 0) goto L_0x00a1;
        L_0x009e:
            r1.close();
        L_0x00a1:
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.completeDeviceEvent();
            r9 = 0;
            return r9;
        L_0x00a8:
            r9 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.length();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = 5;
            if (r9 >= r10) goto L_0x0101;
        L_0x00b3:
            r9 = "MDMJob";
            r10 = "Clean old data.";
            android.util.Log.d(r9, r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r6.getId();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9.removePayload(r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            goto L_0x0096;
        L_0x00c6:
            r3 = move-exception;
            r5 = r6;
        L_0x00c8:
            r9 = "MDMJob";
            r10 = new java.lang.StringBuilder;
            r10.<init>();
            r11 = "JSONException:";
            r10 = r10.append(r11);
            r11 = r3.getMessage();
            r10 = r10.append(r11);
            r10 = r10.toString();
            android.util.Log.e(r9, r10);
            if (r5 == 0) goto L_0x00fa;
        L_0x00e8:
            r9 = "MDMJob";
            r10 = "Remove error payload.";
            android.util.Log.e(r9, r10);
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r10 = r5.getId();
            r9.removePayload(r10);
        L_0x00fa:
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.completeDeviceEvent();
            r9 = 0;
            return r9;
        L_0x0101:
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.mCapsuleStorage;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.containsKey(r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            if (r9 == 0) goto L_0x01ac;
        L_0x0111:
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.mCapsuleStorage;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r0 = r9.get(r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r0 = (net.oneplus.odm.data.Capsule) r0;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r0.addPayload(r6);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r0.getCount();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.mCapsulePayloadLimitation;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r11 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r12 = 100;
            r12 = java.lang.Integer.valueOf(r12);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.getOrDefault(r11, r12);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = (java.lang.Integer) r9;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.intValue();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            if (r10 < r9) goto L_0x0096;
        L_0x0144:
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.mSetting;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = "#ODM_OAUTH_TOKEN#";
            r11 = 0;
            r8 = r9.getPreference(r10, r11);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = "MDMJob";
            r10 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10.<init>();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r11 = "Send ";
            r10 = r10.append(r11);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r11 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r10.append(r11);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r10.toString();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            android.util.Log.v(r9, r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = 1;
            r9.sendData(r0, r8, r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.mCapsuleStorage;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9.remove(r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            goto L_0x0096;
        L_0x0185:
            r4 = move-exception;
            r5 = r6;
        L_0x0187:
            r9 = "MDMJob";
            r10 = new java.lang.StringBuilder;
            r10.<init>();
            r11 = "Retrofit:";
            r10 = r10.append(r11);
            r11 = r4.getMessage();
            r10 = r10.append(r11);
            r10 = r10.toString();
            android.util.Log.e(r9, r10);
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.completeDeviceEvent();
            r9 = 0;
            return r9;
        L_0x01ac:
            r0 = new net.oneplus.odm.data.Capsule;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.mContext;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r0.<init>(r9, r10);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r0.addPayload(r6);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9 = r9.mCapsuleStorage;	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r10 = r6.getCategory();	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            r9.put(r10, r0);	 Catch:{ JSONException -> 0x00c6, RetrofitError -> 0x0185, Exception -> 0x01cd }
            goto L_0x0096;
        L_0x01cd:
            r2 = move-exception;
            r5 = r6;
        L_0x01cf:
            r9 = "MDMJob";
            r10 = new java.lang.StringBuilder;
            r10.<init>();
            r11 = "Exception:";
            r10 = r10.append(r11);
            r11 = r2.getMessage();
            r10 = r10.append(r11);
            r10 = r10.toString();
            android.util.Log.e(r9, r10);
            r9 = net.oneplus.odm.insight.MDMJobService.this;
            r9.completeDeviceEvent();
            r9 = 0;
            return r9;
        L_0x01f4:
            r3 = move-exception;
            goto L_0x00c8;
        L_0x01f7:
            r4 = move-exception;
            goto L_0x0187;
        L_0x01f9:
            r2 = move-exception;
            goto L_0x01cf;
            */
            throw new UnsupportedOperationException("Method not decompiled: net.oneplus.odm.insight.MDMJobService.UploadAnalyticsAsyncTask.doInBackground(java.lang.Void[]):java.lang.Object");
        }
    }

    class UploadDeviceInfoAsyncTask extends AsyncTask<Void, Object, Object> {
        private boolean mIsRetry;
        private ArrayList<Integer> mSucceededUploadPayloadID;

        public UploadDeviceInfoAsyncTask(boolean isRetry) {
            this.mSucceededUploadPayloadID = new ArrayList();
            this.mIsRetry = false;
            this.mIsRetry = isRetry;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected java.lang.Object doInBackground(java.lang.Void... r15) {
            /*
            r14 = this;
            r7 = 0;
            r8 = 0;
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.getAuthToken();
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10 = r10.mServerStatus;
            if (r10 != 0) goto L_0x001f;
        L_0x000f:
            r10 = "MDMJob";
            r11 = "Server is fail.";
            android.util.Log.e(r10, r11);
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.completeDeviceInfo();
            r10 = 0;
            return r10;
        L_0x001f:
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10 = r10.mDeviceInfoDatabaseHelper;
            r1 = r10.getAllDeviceInfoPayloadCursor();
            if (r1 != 0) goto L_0x003b;
        L_0x002b:
            r10 = "MDMJob";
            r11 = "Cursor is null.";
            android.util.Log.i(r10, r11);
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.completeDeviceInfo();
            r10 = 0;
            return r10;
        L_0x003b:
            r10 = r1.getCount();
            if (r10 != 0) goto L_0x0054;
        L_0x0041:
            r1.close();
            r10 = "MDMJob";
            r11 = "No event.";
            android.util.Log.i(r10, r11);
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.completeDeviceInfo();
            r10 = 0;
            return r10;
        L_0x0054:
            r1.moveToFirst();
        L_0x0057:
            r5 = 0;
            r10 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r10 = r10.mSetting;	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r11 = "#ODM_OAUTH_TOKEN#";
            r12 = 0;
            r9 = r10.getPreference(r11, r12);	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r6 = new net.oneplus.odm.analytics.OneplusAnalyticsPayload;	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r10 = 0;
            r10 = r1.getInt(r10);	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r11 = 1;
            r11 = r1.getString(r11);	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r12 = 2;
            r12 = r1.getString(r12);	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r13 = "1000000001";
            r6.<init>(r10, r11, r12, r13);	 Catch:{ JSONException -> 0x00f3, RetrofitError -> 0x00cd, Exception -> 0x00a7 }
            r0 = new net.oneplus.odm.data.Capsule;	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r10 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r10 = r10.mContext;	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r11 = r6.getCategory();	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r0.<init>(r10, r11);	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r0.addPayload(r6);	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r10 = net.oneplus.odm.insight.MDMJobService.this;	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r11 = 0;
            r10.sendData(r0, r9, r11);	 Catch:{ JSONException -> 0x012d, RetrofitError -> 0x0130, Exception -> 0x0133 }
            r10 = r1.moveToNext();
            if (r10 != 0) goto L_0x0057;
        L_0x009b:
            if (r1 == 0) goto L_0x00a0;
        L_0x009d:
            r1.close();
        L_0x00a0:
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.completeDeviceInfo();
            r10 = 0;
            return r10;
        L_0x00a7:
            r2 = move-exception;
        L_0x00a8:
            r10 = "MDMJob";
            r11 = new java.lang.StringBuilder;
            r11.<init>();
            r12 = "Exception:";
            r11 = r11.append(r12);
            r12 = r2.getMessage();
            r11 = r11.append(r12);
            r11 = r11.toString();
            android.util.Log.e(r10, r11);
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.completeDeviceInfo();
            r10 = 0;
            return r10;
        L_0x00cd:
            r4 = move-exception;
        L_0x00ce:
            r10 = "MDMJob";
            r11 = new java.lang.StringBuilder;
            r11.<init>();
            r12 = "RetrofitError:";
            r11 = r11.append(r12);
            r12 = r4.getMessage();
            r11 = r11.append(r12);
            r11 = r11.toString();
            android.util.Log.e(r10, r11);
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.completeDeviceInfo();
            r10 = 0;
            return r10;
        L_0x00f3:
            r3 = move-exception;
        L_0x00f4:
            r10 = "MDMJob";
            r11 = new java.lang.StringBuilder;
            r11.<init>();
            r12 = "JSONException:";
            r11 = r11.append(r12);
            r12 = r3.getMessage();
            r11 = r11.append(r12);
            r11 = r11.toString();
            android.util.Log.e(r10, r11);
            if (r5 == 0) goto L_0x0126;
        L_0x0114:
            r10 = "MDMJob";
            r11 = "Remove error payload.";
            android.util.Log.e(r10, r11);
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r11 = r5.getId();
            r10.removeDeviceInfoPayload(r11);
        L_0x0126:
            r10 = net.oneplus.odm.insight.MDMJobService.this;
            r10.completeDeviceInfo();
            r10 = 0;
            return r10;
        L_0x012d:
            r3 = move-exception;
            r5 = r6;
            goto L_0x00f4;
        L_0x0130:
            r4 = move-exception;
            r5 = r6;
            goto L_0x00ce;
        L_0x0133:
            r2 = move-exception;
            r5 = r6;
            goto L_0x00a8;
            */
            throw new UnsupportedOperationException("Method not decompiled: net.oneplus.odm.insight.MDMJobService.UploadDeviceInfoAsyncTask.doInBackground(java.lang.Void[]):java.lang.Object");
        }
    }

    public MDMJobService() {
        this.mCapsulePayloadLimitation = new HashMap();
        this.mCapsuleStorage = new HashMap();
        this.mServerStatus = 1;
        this.mJobCount = 0;
    }

    public boolean onStartJob(JobParameters params) {
        Log.v("MDMJob", "Start upload job");
        this.mJobParameters = params;
        CustomClient client = new CustomClient(getApplicationContext());
        try {
            if (Utils.isH2()) {
                this.mDeviceManagerUploader = new DeviceManagerUploader(client, Def.HYDROGEN_SERVER_URL);
                this.mDeviceManagerOAuthUploader = new DeviceManagerUploader(Def.HYDROGEN_OAUTH_URL);
            } else if (Utils.isO2()) {
                this.mDeviceManagerUploader = new DeviceManagerUploader(client, Def.OXYGEN_SERVER_URL);
                this.mDeviceManagerOAuthUploader = new DeviceManagerUploader(Def.OXYGEN_OAUTH_URL);
            } else {
                Log.e("MDMJob", "Neither O2 or H2");
                this.mDeviceManagerUploader = new DeviceManagerUploader(client, Def.HYDROGEN_SERVER_URL);
                this.mDeviceManagerOAuthUploader = new DeviceManagerUploader(Def.HYDROGEN_OAUTH_URL);
            }
        } catch (Exception e) {
            Log.e("MDMJob", e.getMessage());
        }
        this.mContext = getApplicationContext();
        ContentResolver resolver = this.mContext.getContentResolver();
        boolean isWifiConnected = isConnectedWifi(this.mContext);
        boolean isMobileNetworkConnected = isConnectedMobile(this.mContext);
        boolean isRoaming = isDataRoaming(this.mContext);
        this.mConfigAdapter = ConfigAdapter.getInstance(this.mContext);
        if (isWifiConnected) {
            Log.v("MDMJob", "Wifi connected");
            uploadDeviceInfo(true);
            if (Utils.isUserEnable(this.mContext)) {
                uploadAnalyticsData(true);
                this.mJobCount++;
            }
            this.mJobCount++;
            return true;
        }
        Log.v("MDMJob", "No Wifi connected");
        if (!isMobileNetworkConnected || isRoaming) {
            Log.i("MDMJob", "No network connected or roaming now");
        } else {
            uploadDeviceInfo(false);
            this.mJobCount++;
        }
        return false;
    }

    public boolean onStopJob(JobParameters params) {
        Log.v("MDMJob", "Stop job");
        return false;
    }

    public void onCreate() {
        super.onCreate();
        initCapsuleLimitation();
        this.mDatabaseHelper = DeviceManagerDatabaseHelper.getInstance(getApplicationContext());
        this.mDeviceInfoDatabaseHelper = DeviceInfoDatabaseHelper.getInstance(getApplicationContext());
        this.mSetting = DeviceManagerSetting.getInstance(getApplicationContext());
        SINGLE_TASK_EXECUTOR = Executors.newSingleThreadExecutor();
    }

    private boolean retryTask(int retried) {
        Log.v("MDMJob", "retry:" + retried);
        if (retried >= 3) {
            return false;
        }
        try {
            Thread.sleep((long) (Math.pow(3.0d, (double) retried) * 2000.0d));
        } catch (InterruptedException exception) {
            Log.e("MDMJob", exception.getMessage());
        }
        return true;
    }

    private void completeDeviceEvent() {
        Log.v("MDMJob", "Complete Device Event.");
        completeJob();
    }

    private void sendData(Capsule capsule, String token, int type) throws Exception, RetrofitError {
        if (this.mDeviceManagerUploader.postData(capsule.dump(), token).getStatus() == 200) {
            Log.v("MDMJob", "Success");
            if (type == 1) {
                removeCapsule(capsule);
            } else if (type == 0) {
                removeDeviceInfoCapsule(capsule);
            }
        }
    }

    private void removeCapsule(Capsule capsule) {
        Log.v("MDMJob", "Remove " + capsule.getIdList().size());
        this.mDatabaseHelper.removePayloadList(capsule.getIdList());
    }

    private void removeDeviceInfoCapsule(Capsule capsule) {
        Log.v("MDMJob", "Remove " + capsule.getIdList().size());
        this.mDeviceInfoDatabaseHelper.removeDeviceInfoPayloadList(capsule.getIdList());
    }

    private void removePayload(int payloadId) {
        Log.v("MDMJob", "Remove " + payloadId);
        this.mDatabaseHelper.removePayload(payloadId);
    }

    private void removeDeviceInfoPayload(int payloadId) {
        Log.v("MDMJob", "Remove device info id:" + payloadId);
        this.mDeviceInfoDatabaseHelper.removeDeviceInfoPayload(payloadId);
    }

    private void completeDeviceInfo() {
        Log.v("MDMJob", "Complete Device Info.");
        completeJob();
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

    private boolean isDataRoaming(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        TelephonyManager telephony = (TelephonyManager) context.getSystemService("phone");
        if (networkInfo == null || telephony == null) {
            return false;
        }
        if (networkInfo.isRoaming() || telephony.isNetworkRoaming()) {
            return true;
        }
        return false;
    }

    private void uploadDeviceInfo(boolean retry) {
        if (this.mConfigAdapter.checkUploadDeviceInfo()) {
            Log.v("MDMJob", "DeviceInfo: uploadDeviceInfo");
            boolean isRetry = retry;
            Random random = new Random();
            new Handler().post(new AnonymousClass1(retry));
            return;
        }
        Log.d("MDMJob", "Stop upload deviceinfo");
    }

    private void uploadAnalyticsData(boolean retry) {
        if (this.mConfigAdapter.checkUploadDeviceEvent()) {
            Log.v("MDMJob", "AppEvent: uploadAnalyticsData");
            boolean isRetry = retry;
            new Handler().postDelayed(new AnonymousClass2(retry), (long) new Random().nextInt(Def.RANDOM_UPLOAD_DATA_DELAY));
            return;
        }
        Log.d("MDMJob", "Stop upload deviceevent");
    }

    private void getAuthToken() {
        String token = this.mSetting.getPreference("#ODM_OAUTH_TOKEN#", null);
        long tokenExpires = this.mSetting.getPreference("#ODM_OAUTH_TOKEN_TIMESTAMP#", 0);
        int retry = 0;
        while (true) {
            int retry2;
            if (token == null || System.currentTimeMillis() >= tokenExpires) {
                try {
                    TokenResponse tokenResult = this.mDeviceManagerOAuthUploader.requestToken("mdmclient0001", "client_credentials", "cd65b204c84348549f228b7cc61352a1", "write");
                    if (tokenResult.getError() == null) {
                        token = tokenResult.getToken();
                        tokenExpires = System.currentTimeMillis() + tokenResult.getExpires();
                        this.mSetting.setPreference("#ODM_OAUTH_TOKEN#", token);
                        Log.i("MDMJob", "Token recieve:" + token);
                        this.mSetting.setPreference("#ODM_OAUTH_TOKEN_TIMESTAMP#", tokenExpires);
                        this.mServerStatus = 1;
                    }
                    retry2 = retry;
                } catch (RetrofitError e) {
                    this.mServerStatus = 0;
                    if (e.getResponse() != null) {
                        Log.e("MDMJob", "Error status:" + e.getResponse().getStatus());
                    }
                    retry2 = retry + 1;
                    if (!retryTask(retry)) {
                        Log.e("MDMJob", "Retried token too many times, return");
                        return;
                    }
                }
                retry = retry2;
            } else {
                Log.v("MDMJob", "Token is correct");
                retry2 = retry;
                return;
            }
        }
    }

    private void initCapsuleLimitation() {
        this.mCapsulePayloadLimitation.put("1000000001", Integer.valueOf(1));
        this.mCapsulePayloadLimitation.put("1000000002", Integer.valueOf(100));
        this.mCapsulePayloadLimitation.put("1000000003", Integer.valueOf(100));
        this.mCapsulePayloadLimitation.put("1000000004", Integer.valueOf(100));
        this.mCapsulePayloadLimitation.put("1000000005", Integer.valueOf(100));
        this.mCapsulePayloadLimitation.put("NYNCG4I0TI", Integer.valueOf(2));
        this.mCapsulePayloadLimitation.put("7554P2RV0X", Integer.valueOf(2));
    }

    private void completeJob() {
        this.mJobCount--;
        if (this.mJobCount == 0) {
            jobFinished(this.mJobParameters, false);
        }
    }
}
