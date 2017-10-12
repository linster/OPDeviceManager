package net.oneplus.odm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import net.oneplus.odm.common.Def;
import net.oneplus.odm.common.Util;
import net.oneplus.odm.common.Utils;
import net.oneplus.odm.data.Cell;
import net.oneplus.odm.database.DeviceInfoDatabaseHelper;
import net.oneplus.odm.geolocation.CellProvider;
import net.oneplus.odm.geolocation.LocationProvider;
import net.oneplus.odm.insight.OneplusAnalyticsScheduler;
import net.oneplus.odm.insight.OneplusAnalyticsUploader;
import net.oneplus.odm.insight.tracker.OSTracker;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceManagerService extends Service {
    private static String TAG;
    Runnable grabDeviceInfoTask;
    private AccountManager mAccountManager;
    private ConfigAdapter mConfigAdapter;
    private DeviceInfoDatabaseHelper mDeviceInfoDatabaseHelper;
    private DeviceManagerAddJobReceiver mDeviceManagerAddJobReceiver;
    private DeviceManagerLocalJobReceiver mDeviceManagerLocalScheduleReceiver;
    private DeviceManagerRemoveJobReceiver mDeviceManagerRemoveJobReceiver;
    private DeviceManagerScheduler mDeviceManagerScheduler;
    private DeviceManagerSetting mDeviceManagerSetting;
    private DeviceOperationReceiver mDeviceOperationReceiver;
    private DeviceManagerJob mGrabLocalDeviceInfoSchedule;
    private LocationProvider mLocationProvider;
    private OSTracker mOATracker;
    OnAccountsUpdateListener mOnAccountsUpdateListener;
    private OneplusAnalyticsScheduler mOneplusAnalyticsScheudler;
    private SendDeviceInfoNowReceiver mSendDeviceInfoNowReceiver;

    /* renamed from: net.oneplus.odm.DeviceManagerService.3 */
    class AnonymousClass3 implements Runnable {
        final /* synthetic */ Account[] val$accounts;

        AnonymousClass3(Account[] val$accounts) {
            this.val$accounts = val$accounts;
        }

        public void run() {
            try {
                DeviceManagerService.this.mDeviceManagerSetting.setPreference("oneplus_account", ((Bundle) DeviceManagerService.this.mAccountManager.getAuthToken(this.val$accounts[0], "com.oneplus.account", false, null, null).getResult()).getString("email", ""));
            } catch (Exception e) {
                DeviceManagerService.this.mDeviceManagerSetting.setPreference("oneplus_account", "");
                Log.e(DeviceManagerService.TAG, e.getMessage());
            }
        }
    }

    private class DeviceManagerAddJobReceiver extends BroadcastReceiver {
        private DeviceManagerAddJobReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Log.v(DeviceManagerService.TAG, "job receive");
            DeviceManagerJob job = (DeviceManagerJob) intent.getParcelableExtra("intent_help_job_schedule_payload");
            if (job != null) {
                DeviceManagerService.this.mDeviceManagerScheduler.addJob(job);
            }
        }
    }

    private class DeviceManagerLocalJobReceiver extends BroadcastReceiver {
        private DeviceManagerLocalJobReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("intent_local_schedule_payload").equals("task_grab_local_deviceinfo")) {
                if (Utils.isH2()) {
                    if (System.currentTimeMillis() - DeviceManagerService.this.mDeviceManagerSetting.getPreference("grab_location_timestamp", 0) > 86400000) {
                        DeviceManagerService.this.mLocationProvider.startLocation();
                    } else {
                        DeviceManagerService.this.mDeviceInfoDatabaseHelper.addDeviceInfoPayload(DeviceManagerService.this.changeDeviceInfoToNewMDMFormat(DeviceManagerService.this.appendLastKnownLocation(DeviceManagerService.this.grabDeviceInformation())).toString());
                    }
                }
                if (Utils.isO2()) {
                    DeviceManagerService.this.mDeviceInfoDatabaseHelper.addDeviceInfoPayload(DeviceManagerService.this.changeDeviceInfoToNewMDMFormat(DeviceManagerService.this.grabDeviceInformation()).toString());
                }
            }
        }
    }

    private class DeviceManagerRemoveJobReceiver extends BroadcastReceiver {
        private DeviceManagerRemoveJobReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            DeviceManagerJob job = (DeviceManagerJob) intent.getParcelableExtra("intent_help_job_schedule_payload");
            if (job != null) {
                DeviceManagerService.this.mDeviceManagerScheduler.removeJob(job);
            }
        }
    }

    private class DeviceOperationReceiver extends BroadcastReceiver {
        private DeviceOperationReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                DeviceManagerService.this.mOATracker.onEvent("screen_on", null);
            } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                DeviceManagerService.this.mOATracker.onEvent("screen_off", null);
            } else if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {
                DeviceManagerService.this.mOATracker.onEvent("unlock", null);
            }
        }
    }

    private class GrabDeviceInformationHandler extends Handler {
        JSONObject mJson;

        GrabDeviceInformationHandler(Looper looper) {
            super(looper);
            this.mJson = new JSONObject();
        }

        public void handleMessage(Message msg) {
            this.mJson = DeviceManagerService.this.grabDeviceInformation();
            switch (msg.what) {
                case 0:
                    this.mJson = DeviceManagerService.this.appendLastKnownLocation(this.mJson);
                    break;
                case 1:
                    Bundle data = msg.getData();
                    try {
                        DeviceManagerService.this.mDeviceManagerSetting.setPreference("grab_location_timestamp", System.currentTimeMillis());
                        this.mJson.put("gl", "");
                        this.mJson.put("go", "");
                        this.mJson.put("gt", "");
                        break;
                    } catch (JSONException e) {
                        Log.e(DeviceManagerService.TAG, e.getMessage());
                        break;
                    }
            }
            if (DeviceManagerService.this.mDeviceManagerSetting.getPreference("send_deviceinfo_immediately", false)) {
                DeviceManagerService.this.mDeviceManagerSetting.setPreference("send_deviceinfo_immediately", false);
                try {
                    this.mJson.put("fl", "true");
                } catch (JSONException e2) {
                    Log.e(DeviceManagerService.TAG, e2.getMessage());
                }
                OneplusAnalyticsUploader uploader = new OneplusAnalyticsUploader(DeviceManagerService.this.getApplicationContext());
                this.mJson = DeviceManagerService.this.changeDeviceInfoToNewMDMFormat(this.mJson);
                uploader.uploadDeviceInfo(this.mJson);
            } else {
                this.mJson = DeviceManagerService.this.changeDeviceInfoToNewMDMFormat(this.mJson);
                DeviceManagerService.this.mDeviceInfoDatabaseHelper.addDeviceInfoPayload(this.mJson.toString());
            }
            DeviceManagerService.this.mLocationProvider.stopLocation();
            super.handleMessage(msg);
        }
    }

    private class SendDeviceInfoNowReceiver extends BroadcastReceiver {
        private SendDeviceInfoNowReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("intent_send_deviceinfo")) {
                if (DeviceManagerService.this.mLocationProvider != null && !DeviceManagerService.this.mDeviceManagerSetting.getPreference("send_deviceinfo_immediately", false)) {
                    Handler handler = new Handler();
                    DeviceManagerService.this.mDeviceManagerSetting.setPreference("send_deviceinfo_immediately", true);
                    handler.postDelayed(DeviceManagerService.this.grabDeviceInfoTask, 10000);
                }
            } else if (!intent.getAction().equals("net.oneplus.odm.test.debugmode")) {
            } else {
                if (intent.getBooleanExtra("mdm_debug_mode", false)) {
                    Log.v(DeviceManagerService.TAG, "OPEN MDM DEBUG MODE");
                    DeviceManagerService.this.mDeviceManagerSetting.setPreference("debug_mode", true);
                    return;
                }
                Log.v(DeviceManagerService.TAG, "CLOSE MDM DEBUG MODE");
                DeviceManagerService.this.mDeviceManagerSetting.setPreference("debug_mode", false);
            }
        }
    }

    public DeviceManagerService() {
        this.grabDeviceInfoTask = new Runnable() {
            public void run() {
                DeviceManagerService.this.mLocationProvider.startLocation();
            }
        };
        this.mOnAccountsUpdateListener = new OnAccountsUpdateListener() {
            public void onAccountsUpdated(Account[] accounts) {
                DeviceManagerService.this.getOneplusAccountName();
            }
        };
    }

    static {
        TAG = "DeviceManagerService";
    }

    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        this.mDeviceManagerSetting = DeviceManagerSetting.getInstance(this);
        if (this.mDeviceManagerSetting.getPreference("debug_mode", false)) {
            Log.v(TAG, "START MDM DEBUG MODE");
            changeConfig();
        } else {
            Log.v(TAG, "START MDM NORMAL MODE");
        }
        this.mAccountManager = AccountManager.get(this);
        this.mAccountManager.addOnAccountsUpdatedListener(this.mOnAccountsUpdateListener, null, false);
        this.mDeviceManagerScheduler = DeviceManagerScheduler.getInstance(context);
        this.mDeviceManagerLocalScheduleReceiver = new DeviceManagerLocalJobReceiver();
        this.mDeviceInfoDatabaseHelper = DeviceInfoDatabaseHelper.getInstance(context);
        this.mOATracker = new OSTracker(this);
        HandlerThread handlerThread = new HandlerThread("LocationHandlerThread");
        handlerThread.start();
        GrabDeviceInformationHandler grabDeviceInformationHandler = new GrabDeviceInformationHandler(handlerThread.getLooper());
        this.mLocationProvider = new LocationProvider(this);
        this.mLocationProvider.setLocationMessageHandler(grabDeviceInformationHandler);
        registerReceiver(this.mDeviceManagerLocalScheduleReceiver, new IntentFilter("intent_local_schedule"));
        Intent taskIntent = new Intent("intent_local_schedule");
        taskIntent.putExtra("intent_local_schedule_payload", "task_grab_local_deviceinfo");
        this.mGrabLocalDeviceInfoSchedule = new DeviceManagerJob("task_grab_local_deviceinfo", 10800000, 2, PendingIntent.getBroadcast(getApplicationContext(), 0, taskIntent, 0));
        this.mDeviceManagerScheduler.addJob(this.mGrabLocalDeviceInfoSchedule);
        this.mDeviceManagerScheduler.startScheduler(2);
        this.mOneplusAnalyticsScheudler = OneplusAnalyticsScheduler.getInstance(getApplicationContext());
        this.mOneplusAnalyticsScheudler.start();
        this.mDeviceManagerAddJobReceiver = new DeviceManagerAddJobReceiver();
        registerReceiver(this.mDeviceManagerAddJobReceiver, new IntentFilter("intent_help_add_job_schedule"));
        this.mDeviceManagerRemoveJobReceiver = new DeviceManagerRemoveJobReceiver();
        registerReceiver(this.mDeviceManagerRemoveJobReceiver, new IntentFilter("intent_help_remove_job_schedule"));
        this.mSendDeviceInfoNowReceiver = new SendDeviceInfoNowReceiver();
        IntentFilter deviceIntentFilter = new IntentFilter();
        deviceIntentFilter.addAction("intent_send_deviceinfo");
        deviceIntentFilter.addAction("net.oneplus.odm.test.debugmode");
        registerReceiver(this.mSendDeviceInfoNowReceiver, deviceIntentFilter);
        this.mDeviceOperationReceiver = new DeviceOperationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(this.mDeviceOperationReceiver, intentFilter);
        Log.v(TAG, "register operation");
        this.mConfigAdapter = ConfigAdapter.getInstance(context);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mOneplusAnalyticsScheudler.stop();
        if (this.mDeviceManagerLocalScheduleReceiver != null) {
            unregisterReceiver(this.mDeviceManagerLocalScheduleReceiver);
        }
        if (this.mDeviceManagerAddJobReceiver != null) {
            unregisterReceiver(this.mDeviceManagerAddJobReceiver);
        }
        if (this.mDeviceManagerRemoveJobReceiver != null) {
            unregisterReceiver(this.mDeviceManagerRemoveJobReceiver);
        }
        if (this.mDeviceOperationReceiver != null) {
            unregisterReceiver(this.mDeviceOperationReceiver);
        }
        if (this.mSendDeviceInfoNowReceiver != null) {
            unregisterReceiver(this.mSendDeviceInfoNowReceiver);
        }
        Intent restartServiceIntent = new Intent(getApplicationContext(), getClass());
        restartServiceIntent.setPackage(getPackageName());
        ((AlarmManager) getApplicationContext().getSystemService("alarm")).set(3, SystemClock.elapsedRealtime() + 1000, PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, 1073741824));
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void changeConfig() {
        Def.UPLOAD_JOB_INTERVAL = 30000;
        Def.RANDOM_UPLOAD_DATA_DELAY = 10000;
        Def.HYDROGEN_SERVER_URL = "http://172.21.107.73:8880/open/";
        Def.HYDROGEN_OAUTH_URL = "http://172.21.107.73:8880/open/";
        Def.OXYGEN_SERVER_URL = "http://172.21.107.73:8880/open/";
        Def.OXYGEN_OAUTH_URL = "http://172.21.107.73:8880/open/";
    }

    private JSONObject appendLastKnownLocation(JSONObject json) {
        try {
            Location location = this.mLocationProvider.getLastKnownLocation();
            json.put("gl", "");
            json.put("go", "");
            json.put("gt", "");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return json;
    }

    private void getOneplusAccountName() {
        String accountName = "";
        new Thread(new AnonymousClass3(this.mAccountManager.getAccountsByType("com.oneplus.account"))).start();
    }

    private JSONObject grabDeviceInformation() {
        JSONObject json = new JSONObject();
        CellProvider cellProvider = new CellProvider();
        Context context = getApplicationContext();
        ContentResolver resolver = context.getContentResolver();
        int isMdmEnabled = Utils.isUserEnable(context) ? 1 : 0;
        Cell cell = cellProvider.getCellInfo(context);
        StringBuilder mdmLog = new StringBuilder();
        try {
            json.put("id", Utils.getDeviceId());
        } catch (Exception e) {
            mdmLog.append(e.getMessage());
        }
        try {
            if (this.mConfigAdapter.checkSensitiveData()) {
                try {
                    json.put("im", Utils.getIMEI(context));
                } catch (Exception e2) {
                    mdmLog.append(e2.getMessage());
                }
                try {
                    json.put("ac", this.mDeviceManagerSetting.getPreference("oneplus_account", ""));
                } catch (Exception e22) {
                    mdmLog.append(e22.getMessage());
                }
                try {
                    json.put("non", Util.getNetworkOperatorName(context));
                } catch (Exception e222) {
                    mdmLog.append(e222.getMessage());
                }
                try {
                    json.put("noi", Util.getNetworkOperator(context));
                } catch (Exception e2222) {
                    mdmLog.append(e2222.getMessage());
                }
                try {
                    json.put("ncn", Util.getSimOperatorName(context));
                } catch (Exception e22222) {
                    mdmLog.append(e22222.getMessage());
                }
                try {
                    json.put("nci", Util.getSimOperator(context));
                } catch (Exception e222222) {
                    mdmLog.append(e222222.getMessage());
                }
                try {
                    json.put("im1", Utils.getIMEI1(context));
                } catch (Exception e2222222) {
                    mdmLog.append(e2222222.getMessage());
                }
                try {
                    json.put("pcba", Utils.getPCBA());
                } catch (Exception e22222222) {
                    mdmLog.append(e22222222.getMessage());
                }
                if (cell != null) {
                    try {
                        json.put("gn", cell.getMnc());
                    } catch (Exception e222222222) {
                        mdmLog.append(e222222222.getMessage());
                    }
                    try {
                        json.put("gc", cell.getMcc());
                    } catch (Exception e2222222222) {
                        mdmLog.append(e2222222222.getMessage());
                    }
                    try {
                        json.put("ga", cell.getLac());
                    } catch (Exception e22222222222) {
                        mdmLog.append(e22222222222.getMessage());
                    }
                    try {
                        json.put("ge", cell.getCid());
                    } catch (Exception e222222222222) {
                        mdmLog.append(e222222222222.getMessage());
                    }
                } else {
                    json.put("gn", "");
                    json.put("gc", "");
                    json.put("ga", "");
                    json.put("ge", "");
                }
            }
            try {
                json.put("br", Utils.getBrandName());
            } catch (Exception e2222222222222) {
                mdmLog.append(e2222222222222.getMessage());
            }
            try {
                json.put("mn", Utils.getModelName());
            } catch (Exception e22222222222222) {
                mdmLog.append(e22222222222222.getMessage());
            }
            try {
                json.put("av", Utils.getAndroidVersion());
            } catch (Exception e222222222222222) {
                mdmLog.append(e222222222222222.getMessage());
            }
            try {
                json.put("ov", Utils.getOSVersion());
            } catch (Exception e2222222222222222) {
                mdmLog.append(e2222222222222222.getMessage());
            }
            try {
                json.put("rw", Utils.getResolutionWidth(context));
            } catch (Exception e22222222222222222) {
                mdmLog.append(e22222222222222222.getMessage());
            }
            try {
                json.put("rh", Utils.getResolutionHeight(context));
            } catch (Exception e222222222222222222) {
                mdmLog.append(e222222222222222222.getMessage());
            }
            try {
                json.put("ro", Utils.isRooted());
            } catch (Exception e2222222222222222222) {
                mdmLog.append(e2222222222222222222.getMessage());
            }
            try {
                json.put("ma", "");
            } catch (Exception e22222222222222222222) {
                mdmLog.append(e22222222222222222222.getMessage());
            }
            try {
                json.put("la", Utils.getLocale(context).getLanguage());
            } catch (Exception e222222222222222222222) {
                mdmLog.append(e222222222222222222222.getMessage());
            }
            try {
                json.put("co", Utils.getLocale(context).getCountry());
            } catch (Exception e2222222222222222222222) {
                mdmLog.append(e2222222222222222222222.getMessage());
            }
            try {
                json.put("bs", Utils.getBatteryStatus(context));
            } catch (Exception e22222222222222222222222) {
                mdmLog.append(e22222222222222222222222.getMessage());
            }
            try {
                json.put("bl", (double) Utils.getBatteryLevel(context));
            } catch (Exception e222222222222222222222222) {
                mdmLog.append(e222222222222222222222222.getMessage());
            }
            try {
                json.put("not", Util.getNetworkType(context));
            } catch (Exception e2222222222222222222222222) {
                mdmLog.append(e2222222222222222222222222.getMessage());
            }
            String cellSignalLevelString = Utils.getCellSignalLevel(context);
            if (cellSignalLevelString != null && cellSignalLevelString.length() > 500) {
                cellSignalLevelString = cellSignalLevelString.substring(0, 500);
            }
            try {
                json.put("npc", Utils.getSimCountryCode(context));
            } catch (Exception e22222222222222222222222222) {
                mdmLog.append(e22222222222222222222222222.getMessage());
            }
            try {
                json.put("npn", "");
            } catch (Exception e222222222222222222222222222) {
                mdmLog.append(e222222222222222222222222222.getMessage());
            }
            try {
                json.put("nws", Utils.getWifiSSID(context));
            } catch (Exception e2222222222222222222222222222) {
                mdmLog.append(e2222222222222222222222222222.getMessage());
            }
            try {
                json.put("nwh", Utils.getIsHiddenSSID(context));
            } catch (Exception e22222222222222222222222222222) {
                mdmLog.append(e22222222222222222222222222222.getMessage());
            }
            try {
                json.put("nwa", Utils.getWifiMacAddress(context));
            } catch (Exception e222222222222222222222222222222) {
                mdmLog.append(e222222222222222222222222222222.getMessage());
            }
            try {
                json.put("nwb", Utils.getBSSID(context));
            } catch (Exception e2222222222222222222222222222222) {
                mdmLog.append(e2222222222222222222222222222222.getMessage());
            }
            try {
                json.put("nwl", Utils.getWifiSignalLevel(context));
            } catch (Exception e22222222222222222222222222222222) {
                mdmLog.append(e22222222222222222222222222222222.getMessage());
            }
            try {
                json.put("tz", Utils.getTimezone());
            } catch (Exception e222222222222222222222222222222222) {
                mdmLog.append(e222222222222222222222222222222222.getMessage());
            }
            try {
                json.put("sov", Utils.getSoftVersion());
            } catch (Exception e2222222222222222222222222222222222) {
                mdmLog.append(e2222222222222222222222222222222222.getMessage());
            }
            try {
                json.put("romv", Utils.getRomVersion());
            } catch (Exception e22222222222222222222222222222222222) {
                mdmLog.append(e22222222222222222222222222222222222.getMessage());
            }
            try {
                json.put("doze", Utils.getDozeMode(context));
            } catch (Exception e222222222222222222222222222222222222) {
                mdmLog.append(e222222222222222222222222222222222222.getMessage());
            }
            json.put("ts", System.currentTimeMillis());
            json.put("iac", isMdmEnabled);
            json.put("it", Util.isRomDebugVersion());
            json.put("mdmv", "2.00.170515");
            json.put("log", mdmLog.toString());
        } catch (JSONException e3) {
            Log.e(TAG, e3.getMessage());
        } catch (IllegalStateException e4) {
            Log.e(TAG, e4.getMessage());
        }
        return json;
    }

    private JSONObject changeDeviceInfoToNewMDMFormat(JSONObject json) {
        JSONObject newJson = new JSONObject();
        try {
            newJson.put("pn", "");
            newJson.put("pvc", "");
            newJson.put("en", "");
            newJson.put("ts", System.currentTimeMillis());
            newJson.put("oi", json);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return newJson;
    }
}
