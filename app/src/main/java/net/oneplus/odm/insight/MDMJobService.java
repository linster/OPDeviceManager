package net.oneplus.odm.insight;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.oneplus.odm.c.a;
import net.oneplus.odm.c.b;
import net.oneplus.odm.common.c;
import net.oneplus.odm.n;
import retrofit.RetrofitError;
import retrofit.client.Client;

public class MDMJobService extends JobService {
    private static ExecutorService ac;
    private HashMap ad = new HashMap();
    private HashMap ae = new HashMap();
    private n af;
    private Context ag;
    private b ah;
    private a ai;
    private net.oneplus.odm.a.a aj;
    private net.oneplus.odm.a.a ak;
    private int al = 0;
    private JobParameters am;
    private int an = 1;
    private net.oneplus.odm.b ao;

    private void aA() {
        String bU = this.ao.bU("#ODM_OAUTH_TOKEN#", null);
        long bW = this.ao.bW("#ODM_OAUTH_TOKEN_TIMESTAMP#", 0);
        int i = 0;
        while (true) {
            if (bU == null || System.currentTimeMillis() >= bW) {
                long j;
                int i2;
                String str;
                long j2;
                try {
                    net.oneplus.odm.data.api.b b = this.aj.b("mdmclient0001", "client_credentials", "cd65b204c84348549f228b7cc61352a1", "write");
                    if (b.j() == null) {
                        bU = b.l();
                        bW = b.k() + System.currentTimeMillis();
                        this.ao.bX("#ODM_OAUTH_TOKEN#", bU);
                        Log.i("MDMJob", "Token recieve:" + bU);
                        this.ao.bZ("#ODM_OAUTH_TOKEN_TIMESTAMP#", bW);
                        this.an = 1;
                    }
                    j = bW;
                    i2 = i;
                    str = bU;
                    j2 = j;
                } catch (RetrofitError e) {
                    long j3 = bW;
                    RetrofitError retrofitError = e;
                    str = bU;
                    j2 = j3;
                    this.an = 0;
                    if (retrofitError.getResponse() != null) {
                        Log.e("MDMJob", "Error status:" + retrofitError.getResponse().getStatus());
                    }
                    i2 = i + 1;
                    if (!aK(i)) {
                        Log.e("MDMJob", "Retried token too many times, return");
                        return;
                    }
                }
                i = i2;
                j = j2;
                bU = str;
                bW = j;
            } else {
                Log.v("MDMJob", "Token is correct");
                return;
            }
        }
    }

    private static NetworkInfo aB(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    private void aC() {
        this.ad.put("1000000001", Integer.valueOf(1));
        this.ad.put("1000000002", Integer.valueOf(100));
        this.ad.put("1000000003", Integer.valueOf(100));
        this.ad.put("1000000004", Integer.valueOf(100));
        this.ad.put("1000000005", Integer.valueOf(100));
        this.ad.put("NYNCG4I0TI", Integer.valueOf(2));
        this.ad.put("7554P2RV0X", Integer.valueOf(2));
    }

    private boolean aD(Context context) {
        NetworkInfo aB = aB(context);
        return aB != null && aB.isConnected() && aB.getType() == 0;
    }

    private boolean aE(Context context) {
        NetworkInfo aB = aB(context);
        return aB != null && aB.isConnected() && aB.getType() == 1;
    }

    private boolean aF(Context context) {
        NetworkInfo aB = aB(context);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return (aB == null || telephonyManager == null) ? false : aB.isRoaming() || telephonyManager.isNetworkRoaming();
    }

    private void aG(net.oneplus.odm.data.b bVar) {
        Log.v("MDMJob", "Remove " + bVar.p().size());
        this.ah.N(bVar.p());
    }

    private void aH(net.oneplus.odm.data.b bVar) {
        Log.v("MDMJob", "Remove " + bVar.p().size());
        this.ai.J(bVar.p());
    }

    private void aI(int i) {
        Log.v("MDMJob", "Remove device info id:" + i);
        this.ai.I(i);
    }

    private void aJ(int i) {
        Log.v("MDMJob", "Remove " + i);
        this.ah.M(i);
    }

    private boolean aK(int i) {
        Log.v("MDMJob", "retry:" + i);
        if (i >= 3) {
            return false;
        }
        try {
            Thread.sleep((long) (Math.pow(3.0d, (double) i) * 2000.0d));
        } catch (InterruptedException e) {
            Log.e("MDMJob", e.getMessage());
        }
        return true;
    }

    private void aL(net.oneplus.odm.data.b bVar, String str, int i) {
        if (this.ak.a(bVar.o(), str).getStatus() == 200) {
            Log.v("MDMJob", "Success");
            if (i == 1) {
                aG(bVar);
            } else if (i == 0) {
                aH(bVar);
            }
        }
    }

    private void aM(boolean z) {
        if (this.af.cL()) {
            Log.v("MDMJob", "AppEvent: uploadAnalyticsData");
            new Handler().postDelayed(new j(this, z), (long) new Random().nextInt(c.V));
            return;
        }
        Log.d("MDMJob", "Stop upload deviceevent");
    }

    private void aN(boolean z) {
        if (this.af.cK()) {
            Log.v("MDMJob", "DeviceInfo: uploadDeviceInfo");
            Random random = new Random();
            new Handler().post(new i(this, z));
            return;
        }
        Log.d("MDMJob", "Stop upload deviceinfo");
    }

    private void ax() {
        Log.v("MDMJob", "Complete Device Event.");
        az();
    }

    private void ay() {
        Log.v("MDMJob", "Complete Device Info.");
        az();
    }

    private void az() {
        this.al--;
        if (this.al == 0) {
            jobFinished(this.am, false);
        }
    }

    public void onCreate() {
        super.onCreate();
        aC();
        this.ah = b.L(getApplicationContext());
        this.ai = a.H(getApplicationContext());
        this.ao = net.oneplus.odm.b.bT(getApplicationContext());
        ac = Executors.newSingleThreadExecutor();
    }

    public boolean onStartJob(JobParameters jobParameters) {
        Log.v("MDMJob", "Start upload job");
        this.am = jobParameters;
        Client bVar = new net.oneplus.odm.a.b(getApplicationContext());
        try {
            if (net.oneplus.odm.common.a.an()) {
                this.ak = new net.oneplus.odm.a.a(bVar, c.X);
                this.aj = new net.oneplus.odm.a.a(c.W);
            } else if (net.oneplus.odm.common.a.ao()) {
                this.ak = new net.oneplus.odm.a.a(bVar, c.Z);
                this.aj = new net.oneplus.odm.a.a(c.Y);
            } else {
                Log.e("MDMJob", "Neither O2 or H2");
                this.ak = new net.oneplus.odm.a.a(bVar, c.X);
                this.aj = new net.oneplus.odm.a.a(c.W);
            }
        } catch (Exception e) {
            Log.e("MDMJob", e.getMessage());
        }
        this.ag = getApplicationContext();
        this.ag.getContentResolver();
        boolean aE = aE(this.ag);
        boolean aD = aD(this.ag);
        boolean aF = aF(this.ag);
        this.af = n.cI(this.ag);
        if (aE) {
            Log.v("MDMJob", "Wifi connected");
            aN(true);
            if (net.oneplus.odm.common.a.aq(this.ag)) {
                aM(true);
                this.al++;
            }
            this.al++;
            return true;
        }
        Log.v("MDMJob", "No Wifi connected");
        if (!aD || aF) {
            Log.i("MDMJob", "No network connected or roaming now");
        } else {
            aN(false);
            this.al++;
        }
        return false;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        Log.v("MDMJob", "Stop job");
        return false;
    }
}
