package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

/* compiled from: Unknown */
public class aF {
    private static int c = 10;
    private static int d = 2;
    private static int e = 10;
    protected static boolean oE = false;
    protected static boolean oF = true;
    private static int oG = 10;
    private static int oH = 50;
    private static int oI = 200;
    private static Object oJ = new Object();
    private static aF oK;
    private static float pm = 1.1f;
    private static float pn = 2.2f;
    private static float po = 2.3f;
    private static float pp = 3.8f;
    private static int pq = 3;
    private static int pr = 10;
    private static int ps = 2;
    private static int pt = 7;
    private static int pu = 20;
    private static int pv = 70;
    private static int pw = 120;
    private boolean oL = false;
    private int oM = -1;
    private int oN = 0;
    private int oO = 0;
    private Context oP;
    private LocationManager oQ;
    private bf oR;
    private Z oS;
    private bC oT;
    private aW oU;
    private ay oV;
    private aU oW;
    private bl oX;
    private Thread oY = null;
    private Looper oZ = null;
    private bp pa = null;
    private volatile Handler pb = null;
    private c pc = new c(this);
    private LocationListener pd = new s(this);
    private BroadcastReceiver pe = new Q(this);
    private BroadcastReceiver pf = new ak(this);
    private GpsStatus pg = null;
    private int ph = 0;
    private int pi = 0;
    private HashMap pj = null;
    private int pk = 0;
    private int pl = 0;

    private aF(Context context) {
        this.oP = context;
        this.oR = bf.tA(context);
        this.oX = new bl();
        this.oS = new Z(this.oR);
        this.oU = new aW(context);
        this.oT = new bC(this.oU);
        this.oV = new ay(this.oU);
        this.oQ = (LocationManager) this.oP.getSystemService("location");
        this.oW = aU.sw(this.oP);
        this.oW.sv(this.pc);
        qA();
        List allProviders = this.oQ.getAllProviders();
        boolean z = allProviders != null && allProviders.contains("gps") && allProviders.contains("passive");
        this.oL = z;
        if (context == null) {
            Log.d(bf.a, "Error: No SD Card!");
        } else {
            bf.a = context.getPackageName();
        }
    }

    private void qA() {
        this.oN = this.oW.so() * 1000;
        this.oO = this.oW.sp();
        Z z = this.oS;
        int i = this.oN;
        i = this.oO;
        Z.nO();
    }

    private int qB(HashMap hashMap) {
        if (this.ph > 4) {
            int i;
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            int i2 = 0;
            Iterator it = hashMap.entrySet().iterator();
            while (true) {
                i = i2;
                if (!it.hasNext()) {
                    break;
                }
                List list = (List) ((Entry) it.next()).getValue();
                if (list == null) {
                    i2 = i;
                } else {
                    Object qC = qC(list);
                    if (qC == null) {
                        i2 = i;
                    } else {
                        arrayList.add(qC);
                        i2 = i + 1;
                        arrayList2.add(Integer.valueOf(i));
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                double[] dArr = new double[2];
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    double[] dArr2 = (double[]) arrayList.get(i3);
                    i = ((Integer) arrayList2.get(i3)).intValue();
                    dArr2[0] = dArr2[0] * ((double) i);
                    dArr2[1] = dArr2[1] * ((double) i);
                    dArr[0] = dArr[0] + dArr2[0];
                    dArr[1] = dArr[1] + dArr2[1];
                }
                dArr[0] = dArr[0] / ((double) size);
                dArr[1] = dArr[1] / ((double) size);
                double d = dArr[0];
                double d2 = dArr[1];
                double toDegrees = d2 == 0.0d ? d > 0.0d ? 90.0d : d < 0.0d ? 270.0d : 0.0d : Math.toDegrees(Math.atan(d / d2));
                double[] dArr3 = new double[]{Math.sqrt((d * d) + (d2 * d2)), toDegrees};
                String.format(Locale.CHINA, "%d,%d,%d,%d", new Object[]{Long.valueOf(Math.round(dArr[0] * 100.0d)), Long.valueOf(Math.round(dArr[1] * 100.0d)), Long.valueOf(Math.round(dArr3[0] * 100.0d)), Long.valueOf(Math.round(dArr3[1] * 100.0d))});
                if (dArr3[0] <= ((double) pv)) {
                    return 1;
                }
                if (dArr3[0] >= ((double) pw)) {
                    return 4;
                }
            }
        }
        return 3;
    }

    private double[] qC(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        double[] dArr = new double[2];
        for (GpsSatellite gpsSatellite : list) {
            if (gpsSatellite != null) {
                double elevation = (double) (90.0f - gpsSatellite.getElevation());
                double azimuth = (double) gpsSatellite.getAzimuth();
                double[] dArr2 = new double[]{Math.sin(Math.toRadians(azimuth)) * elevation, elevation * Math.cos(Math.toRadians(azimuth))};
                dArr[0] = dArr[0] + dArr2[0];
                dArr[1] = dArr[1] + dArr2[1];
            }
        }
        int size = list.size();
        dArr[0] = dArr[0] / ((double) size);
        dArr[1] = dArr[1] / ((double) size);
        return dArr;
    }

    static /* synthetic */ void qK(aF aFVar, Location location, int i, long j) {
        bv vn;
        Long valueOf;
        System.currentTimeMillis();
        boolean nP = aFVar.oS.nP(location);
        if (nP) {
            aFVar.oS.mH.nC = new Location(location);
        }
        boolean nS = aFVar.oS.nS(location);
        if (nS) {
            aFVar.oS.mG.tm = new Location(location);
        }
        int i2 = 0;
        if (nP) {
            i2 = 1;
            if (nS) {
                i2 = 3;
            }
        } else if (nS) {
            i2 = 2;
        }
        try {
            bl blVar = aFVar.oX;
            vn = bl.vn(location, aFVar.oR, i2, (byte) aFVar.pl, j, false);
        } catch (Exception e) {
            vn = null;
        }
        if (!(vn == null || aFVar.oR == null)) {
            List tP = aFVar.oR.tP();
            valueOf = Long.valueOf(0);
            if (tP != null && tP.size() > 0) {
                valueOf = (Long) tP.get(0);
            }
            aFVar.oT.xj(valueOf.longValue(), vn.ng());
        }
        if (aFVar.oP != null && aFVar.oX != null) {
            SharedPreferences sharedPreferences = aFVar.oP.getSharedPreferences("app_pref", 0);
            if (!sharedPreferences.getString("get_sensor", "").equals("true")) {
                try {
                    blVar = aFVar.oX;
                    vn = bl.vn(null, aFVar.oR, i2, (byte) aFVar.pl, j, true);
                } catch (Exception e2) {
                    vn = null;
                }
                if (!(vn == null || aFVar.oR == null)) {
                    List tP2 = aFVar.oR.tP();
                    valueOf = Long.valueOf(0);
                    if (tP2 != null && tP2.size() > 0) {
                        valueOf = (Long) tP2.get(0);
                    }
                    aFVar.oT.xj(valueOf.longValue(), vn.ng());
                    sharedPreferences.edit().putString("get_sensor", "true").commit();
                }
            }
        }
    }

    public static aF qo(Context context) {
        if (oK == null) {
            synchronized (oJ) {
                if (oK == null) {
                    oK = new aF(context);
                }
            }
        }
        return oK;
    }

    public static String qz(String str) {
        return !str.equals("version") ? !str.equals("date") ? null : "COL.15.0929r" : "V1.0.0r";
    }

    static /* synthetic */ int ri(aF aFVar, W w, int i) {
        if (aFVar.pk >= pr) {
            return 1;
        }
        if (aFVar.pk <= pq) {
            return 4;
        }
        double nG = w.nG();
        if (nG <= ((double) pm)) {
            return 1;
        }
        if (nG >= ((double) pn)) {
            return 4;
        }
        nG = w.nF();
        return nG <= ((double) po) ? 1 : nG >= ((double) pp) ? 4 : i < pt ? i > ps ? aFVar.pj == null ? 3 : aFVar.qB(aFVar.pj) : 4 : 1;
    }

    static /* synthetic */ String rk(aF aFVar, String str) {
        return str;
    }

    public void qp(int i) {
        if (i == 256 || i == 8736 || i == 768) {
            this.oU.sM(i);
            return;
        }
        throw new RuntimeException("invalid Size! must be COLLECTOR_SMALL_SIZE or COLLECTOR_BIG_SIZE or COLLECTOR_MEDIUM_SIZE");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void qq() {
        /*
        r5 = this;
        r4 = 1;
        r3 = 0;
        com.loc.bf.sj = r4;
        r0 = r5.oL;
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = r5.oR;
        if (r0 == 0) goto L_0x0008;
    L_0x000d:
        r0 = oE;
        if (r0 != 0) goto L_0x0077;
    L_0x0011:
        r0 = new android.content.IntentFilter;
        r1 = "android.location.GPS_ENABLED_CHANGE";
        r0.<init>(r1);
        r1 = "android.location.GPS_FIX_CHANGE";
        r0.addAction(r1);
        oF = r4;
        r1 = r5.oP;
        r2 = r5.pf;
        r1.registerReceiver(r2, r0);
        r0 = new android.content.IntentFilter;
        r0.<init>();
        r1 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0.setPriority(r1);
        r1 = "android.intent.action.MEDIA_UNMOUNTED";
        r0.addAction(r1);
        r1 = "android.intent.action.MEDIA_MOUNTED";
        r0.addAction(r1);
        r1 = "android.intent.action.MEDIA_EJECT";
        r0.addAction(r1);
        r1 = "file";
        r0.addDataScheme(r1);
        r1 = r5.oP;
        r2 = r5.pe;
        r1.registerReceiver(r2, r0);
        r0 = "";
        r1 = r5.oQ;
        r2 = r5.pd;
        r1.removeUpdates(r2);
        r1 = r5.oZ;
        if (r1 != 0) goto L_0x0078;
    L_0x005f:
        r1 = r5.oY;
        if (r1 != 0) goto L_0x0080;
    L_0x0063:
        r1 = new com.loc.l;
        r1.<init>(r5, r0);
        r5.oY = r1;
        r0 = r5.oY;
        r0.start();
        r0 = r5.oR;
        r0.tC();
        oE = r4;
        return;
    L_0x0077:
        return;
    L_0x0078:
        r1 = r5.oZ;
        r1.quit();
        r5.oZ = r3;
        goto L_0x005f;
    L_0x0080:
        r1 = r5.oY;
        r1.interrupt();
        r5.oY = r3;
        goto L_0x0063;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aF.qq():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void qr() {
        /*
        r4 = this;
        r3 = 0;
        r2 = 0;
        com.loc.bf.sj = r3;
        r4.pb = r2;
        com.loc.bf.sk = r3;
        r0 = r4.oL;
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r0 = r4.oR;
        if (r0 == 0) goto L_0x000c;
    L_0x0011:
        r0 = oE;
        if (r0 == 0) goto L_0x0044;
    L_0x0015:
        r0 = r4.pf;
        if (r0 != 0) goto L_0x0045;
    L_0x0019:
        r0 = r4.oR;
        if (r0 != 0) goto L_0x0056;
    L_0x001d:
        r0 = r4.oQ;
        r1 = r4.pa;
        r0.removeGpsStatusListener(r1);
        r0 = r4.oQ;
        r1 = r4.pa;
        r0.removeNmeaListener(r1);
        r4.pa = r2;
        r0 = r4.oQ;
        r1 = r4.pd;
        r0.removeUpdates(r1);
        r0 = r4.oZ;
        if (r0 != 0) goto L_0x005c;
    L_0x0038:
        r0 = r4.oY;
        if (r0 != 0) goto L_0x0064;
    L_0x003c:
        r0 = r4.oR;
        r0.tD();
        oE = r3;
        return;
    L_0x0044:
        return;
    L_0x0045:
        r0 = r4.oP;	 Catch:{ Exception -> 0x0054 }
        r1 = r4.pf;	 Catch:{ Exception -> 0x0054 }
        r0.unregisterReceiver(r1);	 Catch:{ Exception -> 0x0054 }
        r0 = r4.oP;	 Catch:{ Exception -> 0x0054 }
        r1 = r4.pe;	 Catch:{ Exception -> 0x0054 }
        r0.unregisterReceiver(r1);	 Catch:{ Exception -> 0x0054 }
        goto L_0x0019;
    L_0x0054:
        r0 = move-exception;
        goto L_0x0019;
    L_0x0056:
        r0 = r4.oR;
        r0.ub();
        goto L_0x001d;
    L_0x005c:
        r0 = r4.oZ;
        r0.quit();
        r4.oZ = r2;
        goto L_0x0038;
    L_0x0064:
        r0 = r4.oY;
        r0.interrupt();
        r4.oY = r2;
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aF.qr():void");
    }

    public void qs() {
        if (this.oL) {
            qr();
        }
    }

    public boolean qt() {
        return oE;
    }

    public am qu() {
        if (this.oV == null) {
            return null;
        }
        qw();
        return (!this.oW.sn() || bf.sk) ? null : this.oV.pj(this.oW.sq());
    }

    public void qv(am amVar, String str) {
        if (!bf.sk) {
            boolean sx = this.oW.sx(str);
            if (amVar != null) {
                byte[] oz = amVar.oz();
                if (sx && oz != null) {
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.oP.getSystemService("connectivity")).getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        if (activeNetworkInfo.getType() != 1) {
                            this.oW.su(oz.length + this.oW.st());
                        } else {
                            this.oW.ss(oz.length + this.oW.sr());
                        }
                    }
                }
                amVar.oA(sx);
                this.oV.pq(amVar);
            }
        }
    }

    public boolean qw() {
        if (this.oR != null) {
            List tP = this.oR.tP();
            if (tP != null && tP.size() > 0) {
                return this.oU.sQ(((Long) tP.get(0)).longValue());
            }
        }
        return false;
    }

    public int qx() {
        return this.oV == null ? 0 : this.oV.pp();
    }

    public void qy(int i) {
        if (this.oR != null) {
            this.oR.tZ(i);
        }
    }
}
