package net.oneplus.odm;

import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import net.oneplus.odm.b.b;
import net.oneplus.odm.c.a;
import net.oneplus.odm.common.Util;
import net.oneplus.odm.common.c;
import net.oneplus.odm.insight.e;
import net.oneplus.odm.insight.tracker.OSTracker;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceManagerService extends Service {
    private static String bT = "DeviceManagerService";
    Runnable bU = new t(this);
    private AccountManager bV;
    private n bW;
    private a bX;
    private h bY;
    private j bZ;
    private i ca;
    private a cb;
    private b cc;
    private k cd;
    private DeviceManagerJob ce;
    private b cf;
    private OSTracker cg;
    OnAccountsUpdateListener ch = new u(this);
    private e ci;
    private l cj;

    private void cu() {
        c.aa = 30000;
        c.V = 10000;
        c.X = "http://172.21.107.73:8880/open/";
        c.W = "http://172.21.107.73:8880/open/";
        c.Z = "http://172.21.107.73:8880/open/";
        c.Y = "http://172.21.107.73:8880/open/";
    }

    private JSONObject cv(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("pn", "");
            jSONObject2.put("pvc", "");
            jSONObject2.put("en", "");
            jSONObject2.put("ts", System.currentTimeMillis());
            jSONObject2.put("oi", jSONObject);
        } catch (JSONException e) {
            Log.e(bT, e.getMessage());
        }
        return jSONObject2;
    }

    private void cw() {
        String str = "";
        new Thread(new v(this, this.bV.getAccountsByType("com.oneplus.account"))).start();
    }

    private JSONObject cx() {
        JSONObject jSONObject = new JSONObject();
        net.oneplus.odm.b.a aVar = new net.oneplus.odm.b.a();
        Context applicationContext = getApplicationContext();
        applicationContext.getContentResolver();
        int i = net.oneplus.odm.common.a.aq(applicationContext) ? 1 : 0;
        aVar.w(applicationContext);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            jSONObject.put("id", net.oneplus.odm.common.a.V());
        } catch (Exception e) {
            stringBuilder.append(e.getMessage());
        }
        try {
            if (this.bW.cJ()) {
                try {
                    jSONObject.put("chs", net.oneplus.odm.common.a.X(applicationContext));
                } catch (Exception e2) {
                    stringBuilder.append(e2.getMessage());
                }
                try {
                    jSONObject.put("ac", this.cc.bU("oneplus_account", ""));
                } catch (Exception e22) {
                    stringBuilder.append(e22.getMessage());
                }
                try {
                    jSONObject.put("non", Util.getNetworkOperatorName(applicationContext));
                } catch (Exception e222) {
                    stringBuilder.append(e222.getMessage());
                }
                try {
                    jSONObject.put("noi", Util.getNetworkOperator(applicationContext));
                } catch (Exception e2222) {
                    stringBuilder.append(e2222.getMessage());
                }
                try {
                    jSONObject.put("ncn", Util.getSimOperatorName(applicationContext));
                } catch (Exception e22222) {
                    stringBuilder.append(e22222.getMessage());
                }
                try {
                    jSONObject.put("nci", Util.getSimOperator(applicationContext));
                } catch (Exception e222222) {
                    stringBuilder.append(e222222.getMessage());
                }
                try {
                    jSONObject.put("ch1", net.oneplus.odm.common.a.Y(applicationContext));
                } catch (Exception e2222222) {
                    stringBuilder.append(e2222222.getMessage());
                }
                try {
                    jSONObject.put("pcba", net.oneplus.odm.common.a.ac());
                } catch (Exception e22222222) {
                    stringBuilder.append(e22222222.getMessage());
                }
            }
            try {
                jSONObject.put("br", net.oneplus.odm.common.a.U());
            } catch (Exception e222222222) {
                stringBuilder.append(e222222222.getMessage());
            }
            try {
                jSONObject.put("mn", net.oneplus.odm.common.a.aa());
            } catch (Exception e2222222222) {
                stringBuilder.append(e2222222222.getMessage());
            }
            try {
                jSONObject.put("av", net.oneplus.odm.common.a.T());
            } catch (Exception e22222222222) {
                stringBuilder.append(e22222222222.getMessage());
            }
            try {
                jSONObject.put("ov", net.oneplus.odm.common.a.ab());
            } catch (Exception e222222222222) {
                stringBuilder.append(e222222222222.getMessage());
            }
            try {
                jSONObject.put("rw", net.oneplus.odm.common.a.ae(applicationContext));
            } catch (Exception e2222222222222) {
                stringBuilder.append(e2222222222222.getMessage());
            }
            try {
                jSONObject.put("rh", net.oneplus.odm.common.a.ad(applicationContext));
            } catch (Exception e22222222222222) {
                stringBuilder.append(e22222222222222.getMessage());
            }
            try {
                jSONObject.put("ro", net.oneplus.odm.common.a.ap());
            } catch (Exception e222222222222222) {
                stringBuilder.append(e222222222222222.getMessage());
            }
            try {
                jSONObject.put("la", net.oneplus.odm.common.a.Z(applicationContext).getLanguage());
            } catch (Exception e2222222222222222) {
                stringBuilder.append(e2222222222222222.getMessage());
            }
            try {
                jSONObject.put("not", Util.getNetworkType(applicationContext));
            } catch (Exception e22222222222222222) {
                stringBuilder.append(e22222222222222222.getMessage());
            }
            try {
                jSONObject.put("npc", net.oneplus.odm.common.a.ag(applicationContext));
            } catch (Exception e222222222222222222) {
                stringBuilder.append(e222222222222222222.getMessage());
            }
            try {
                jSONObject.put("tz", net.oneplus.odm.common.a.O());
            } catch (Exception e2222222222222222222) {
                stringBuilder.append(e2222222222222222222.getMessage());
            }
            try {
                jSONObject.put("sov", net.oneplus.odm.common.a.ah());
            } catch (Exception e22222222222222222222) {
                stringBuilder.append(e22222222222222222222.getMessage());
            }
            try {
                jSONObject.put("romv", net.oneplus.odm.common.a.af());
            } catch (Exception e222222222222222222222) {
                stringBuilder.append(e222222222222222222222.getMessage());
            }
            try {
                jSONObject.put("doze", net.oneplus.odm.common.a.W(applicationContext));
            } catch (Exception e2222222222222222222222) {
                stringBuilder.append(e2222222222222222222222.getMessage());
            }
            jSONObject.put("ts", System.currentTimeMillis());
            jSONObject.put("iac", i);
            jSONObject.put("it", Util.isRomDebugVersion());
            jSONObject.put("mdmv", "2.00.170515");
            jSONObject.put("log", stringBuilder.toString());
        } catch (JSONException e3) {
            Log.e(bT, e3.getMessage());
        } catch (IllegalStateException e4) {
            Log.e(bT, e4.getMessage());
        }
        return jSONObject;
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();
        this.cc = b.bT(this);
        if (this.cc.bV("debug_mode", false)) {
            Log.v(bT, "START MDM DEBUG MODE");
            cu();
        } else {
            Log.v(bT, "START MDM NORMAL MODE");
        }
        this.bV = AccountManager.get(this);
        this.bV.addOnAccountsUpdatedListener(this.ch, null, false);
        this.cb = a.bO(applicationContext);
        this.bZ = new j();
        this.bX = a.H(applicationContext);
        this.cg = new OSTracker(this);
        HandlerThread handlerThread = new HandlerThread("LocationHandlerThread");
        handlerThread.start();
        Handler mVar = new m(this, handlerThread.getLooper());
        this.cf = new b(this);
        this.cf.z(mVar);
        registerReceiver(this.bZ, new IntentFilter("intent_local_schedule"));
        Intent intent = new Intent("intent_local_schedule");
        intent.putExtra("intent_local_schedule_payload", "task_grab_local_deviceinfo");
        this.ce = new DeviceManagerJob("task_grab_local_deviceinfo", 10800000, 2, PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0));
        this.cb.bM(this.ce);
        this.cb.bS(2);
        this.ci = e.bq(getApplicationContext());
        this.ci.br();
        this.bY = new h();
        registerReceiver(this.bY, new IntentFilter("intent_help_add_job_schedule"));
        this.ca = new i();
        registerReceiver(this.ca, new IntentFilter("intent_help_remove_job_schedule"));
        this.cj = new l();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("intent_send_deviceinfo");
        intentFilter.addAction("net.oneplus.odm.test.debugmode");
        registerReceiver(this.cj, intentFilter);
        this.cd = new k();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(this.cd, intentFilter);
        Log.v(bT, "register operation");
        this.bW = n.cI(applicationContext);
    }

    public void onDestroy() {
        super.onDestroy();
        this.ci.bs();
        if (this.bZ != null) {
            unregisterReceiver(this.bZ);
        }
        if (this.bY != null) {
            unregisterReceiver(this.bY);
        }
        if (this.ca != null) {
            unregisterReceiver(this.ca);
        }
        if (this.cd != null) {
            unregisterReceiver(this.cd);
        }
        if (this.cj != null) {
            unregisterReceiver(this.cj);
        }
        Intent intent = new Intent(getApplicationContext(), getClass());
        intent.setPackage(getPackageName());
        ((AlarmManager) getApplicationContext().getSystemService("alarm")).set(3, SystemClock.elapsedRealtime() + 1000, PendingIntent.getService(getApplicationContext(), 1, intent, 1073741824));
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }
}
