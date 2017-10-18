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
public class bw {
    private static float L;
    private static float M;
    private static float N;
    private static float O;
    private static int P;
    private static int Q;
    private static int R;
    private static int S;
    private static int T;
    private static int U;
    private static int V;
    protected static boolean a;
    protected static boolean b;
    private static int c;
    private static int d;
    private static int e;
    private static int f;
    private static int g;
    private static int h;
    private static Object i;
    private static bw j;
    private volatile Handler A;
    private cs B;
    private LocationListener C;
    private BroadcastReceiver D;
    private BroadcastReceiver E;
    private GpsStatus F;
    private int G;
    private int H;
    private HashMap I;
    private int J;
    private int K;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private Context o;
    private LocationManager p;
    private cg q;
    private cu r;
    private da s;
    private cd t;
    private cz u;
    private ct v;
    private bx w;
    private Thread x;
    private Looper y;
    private cr z;

    static {
        a = false;
        b = true;
        c = 10;
        d = 2;
        e = 10;
        f = 10;
        g = 50;
        h = 200;
        i = new Object();
        L = 1.1f;
        M = 2.2f;
        N = 2.3f;
        O = 3.8f;
        P = 3;
        Q = 10;
        R = 2;
        S = 7;
        T = 20;
        U = 70;
        V = 120;
    }

    private bw(Context context) {
        this.k = false;
        this.l = -1;
        this.m = 0;
        this.n = 0;
        this.x = null;
        this.y = null;
        this.z = null;
        this.A = null;
        this.B = new cs(this);
        this.C = new cm(this);
        this.D = new cn(this);
        this.E = new co(this);
        this.F = null;
        this.G = 0;
        this.H = 0;
        this.I = null;
        this.J = 0;
        this.K = 0;
        this.o = context;
        this.q = cg.a(context);
        this.w = new bx();
        this.r = new cu(this.q);
        this.t = new cd(context);
        this.s = new da(this.t);
        this.u = new cz(this.t);
        this.p = (LocationManager) this.o.getSystemService("location");
        this.v = ct.a(this.o);
        this.v.a(this.B);
        o();
        List allProviders = this.p.getAllProviders();
        boolean z = allProviders != null && allProviders.contains("gps") && allProviders.contains("passive");
        this.k = z;
        if (context == null) {
            Log.d(cg.a, "Error: No SD Card!");
        } else {
            cg.a = context.getPackageName();
        }
    }

    static /* synthetic */ int a(bw bwVar, dc dcVar, int i) {
        if (bwVar.J >= Q) {
            return 1;
        }
        if (bwVar.J <= P) {
            return 4;
        }
        double c = dcVar.c();
        if (c <= ((double) L)) {
            return 1;
        }
        if (c >= ((double) M)) {
            return 4;
        }
        c = dcVar.b();
        return c <= ((double) N) ? 1 : c >= ((double) O) ? 4 : i < S ? i > R ? bwVar.I == null ? 3 : bwVar.a(bwVar.I) : 4 : 1;
    }

    private int a(HashMap hashMap) {
        if (this.G > 4) {
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
                    Object a = a(list);
                    if (a == null) {
                        i2 = i;
                    } else {
                        arrayList.add(a);
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
                if (dArr3[0] <= ((double) U)) {
                    return 1;
                }
                if (dArr3[0] >= ((double) V)) {
                    return 4;
                }
            }
        }
        return 3;
    }

    public static bw a(Context context) {
        if (j == null) {
            synchronized (i) {
                if (j == null) {
                    j = new bw(context);
                }
            }
        }
        return j;
    }

    static /* synthetic */ String a(bw bwVar, String str) {
        return str;
    }

    public static String a(String str) {
        return !str.equals("version") ? !str.equals("date") ? null : "COL.15.0929r" : "V1.0.0r";
    }

    static /* synthetic */ void a(bw bwVar, Location location, int i, long j) {
        bv a;
        Long valueOf;
        System.currentTimeMillis();
        boolean a2 = bwVar.r.a(location);
        if (a2) {
            bwVar.r.b.b = new Location(location);
        }
        boolean b = bwVar.r.b(location);
        if (b) {
            bwVar.r.a.b = new Location(location);
        }
        int i2 = 0;
        if (a2) {
            i2 = 1;
            if (b) {
                i2 = 3;
            }
        } else if (b) {
            i2 = 2;
        }
        try {
            bx bxVar = bwVar.w;
            a = bx.a(location, bwVar.q, i2, (byte) bwVar.K, j, false);
        } catch (Exception e) {
            a = null;
        }
        if (!(a == null || bwVar.q == null)) {
            List m = bwVar.q.m();
            valueOf = Long.valueOf(0);
            if (m != null && m.size() > 0) {
                valueOf = (Long) m.get(0);
            }
            bwVar.s.a(valueOf.longValue(), a.a());
        }
        if (bwVar.o != null && bwVar.w != null) {
            SharedPreferences sharedPreferences = bwVar.o.getSharedPreferences("app_pref", 0);
            if (!sharedPreferences.getString("get_sensor", "").equals("true")) {
                try {
                    bxVar = bwVar.w;
                    a = bx.a(null, bwVar.q, i2, (byte) bwVar.K, j, true);
                } catch (Exception e2) {
                    a = null;
                }
                if (!(a == null || bwVar.q == null)) {
                    List m2 = bwVar.q.m();
                    valueOf = Long.valueOf(0);
                    if (m2 != null && m2.size() > 0) {
                        valueOf = (Long) m2.get(0);
                    }
                    bwVar.s.a(valueOf.longValue(), a.a());
                    sharedPreferences.edit().putString("get_sensor", "true").commit();
                }
            }
        }
    }

    private double[] a(List list) {
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

    private void o() {
        this.m = this.v.b() * 1000;
        this.n = this.v.c();
        cu cuVar = this.r;
        int i = this.m;
        i = this.n;
        cu.a();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
        r5 = this;
        r4 = 1;
        r3 = 0;
        com.loc.cg.b = r4;
        r0 = r5.k;
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = r5.q;
        if (r0 == 0) goto L_0x0008;
    L_0x000d:
        r0 = a;
        if (r0 != 0) goto L_0x0077;
    L_0x0011:
        r0 = new android.content.IntentFilter;
        r1 = "android.location.GPS_ENABLED_CHANGE";
        r0.<init>(r1);
        r1 = "android.location.GPS_FIX_CHANGE";
        r0.addAction(r1);
        b = r4;
        r1 = r5.o;
        r2 = r5.E;
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
        r1 = r5.o;
        r2 = r5.D;
        r1.registerReceiver(r2, r0);
        r0 = "";
        r1 = r5.p;
        r2 = r5.C;
        r1.removeUpdates(r2);
        r1 = r5.y;
        if (r1 != 0) goto L_0x0078;
    L_0x005f:
        r1 = r5.x;
        if (r1 != 0) goto L_0x0080;
    L_0x0063:
        r1 = new com.loc.cp;
        r1.<init>(r5, r0);
        r5.x = r1;
        r0 = r5.x;
        r0.start();
        r0 = r5.q;
        r0.a();
        a = r4;
        return;
    L_0x0077:
        return;
    L_0x0078:
        r1 = r5.y;
        r1.quit();
        r5.y = r3;
        goto L_0x005f;
    L_0x0080:
        r1 = r5.x;
        r1.interrupt();
        r5.x = r3;
        goto L_0x0063;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bw.a():void");
    }

    public void a(int i) {
        if (i == 256 || i == 8736 || i == 768) {
            this.t.a(i);
            return;
        }
        throw new RuntimeException("invalid Size! must be COLLECTOR_SMALL_SIZE or COLLECTOR_BIG_SIZE or COLLECTOR_MEDIUM_SIZE");
    }

    public void a(cc ccVar, String str) {
        if (!cg.c) {
            boolean a = this.v.a(str);
            if (ccVar != null) {
                byte[] a2 = ccVar.a();
                if (a && a2 != null) {
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.o.getSystemService("connectivity")).getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        if (activeNetworkInfo.getType() != 1) {
                            this.v.b(a2.length + this.v.f());
                        } else {
                            this.v.a(a2.length + this.v.e());
                        }
                    }
                }
                ccVar.a(a);
                this.u.a(ccVar);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
        r4 = this;
        r3 = 0;
        r2 = 0;
        com.loc.cg.b = r3;
        r4.A = r2;
        com.loc.cg.c = r3;
        r0 = r4.k;
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r0 = r4.q;
        if (r0 == 0) goto L_0x000c;
    L_0x0011:
        r0 = a;
        if (r0 == 0) goto L_0x0044;
    L_0x0015:
        r0 = r4.E;
        if (r0 != 0) goto L_0x0045;
    L_0x0019:
        r0 = r4.q;
        if (r0 != 0) goto L_0x0056;
    L_0x001d:
        r0 = r4.p;
        r1 = r4.z;
        r0.removeGpsStatusListener(r1);
        r0 = r4.p;
        r1 = r4.z;
        r0.removeNmeaListener(r1);
        r4.z = r2;
        r0 = r4.p;
        r1 = r4.C;
        r0.removeUpdates(r1);
        r0 = r4.y;
        if (r0 != 0) goto L_0x005c;
    L_0x0038:
        r0 = r4.x;
        if (r0 != 0) goto L_0x0064;
    L_0x003c:
        r0 = r4.q;
        r0.b();
        a = r3;
        return;
    L_0x0044:
        return;
    L_0x0045:
        r0 = r4.o;	 Catch:{ Exception -> 0x0054 }
        r1 = r4.E;	 Catch:{ Exception -> 0x0054 }
        r0.unregisterReceiver(r1);	 Catch:{ Exception -> 0x0054 }
        r0 = r4.o;	 Catch:{ Exception -> 0x0054 }
        r1 = r4.D;	 Catch:{ Exception -> 0x0054 }
        r0.unregisterReceiver(r1);	 Catch:{ Exception -> 0x0054 }
        goto L_0x0019;
    L_0x0054:
        r0 = move-exception;
        goto L_0x0019;
    L_0x0056:
        r0 = r4.q;
        r0.v();
        goto L_0x001d;
    L_0x005c:
        r0 = r4.y;
        r0.quit();
        r4.y = r2;
        goto L_0x0038;
    L_0x0064:
        r0 = r4.x;
        r0.interrupt();
        r4.x = r2;
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bw.b():void");
    }

    public void b(int i) {
        if (this.q != null) {
            this.q.a(i);
        }
    }

    public void c() {
        if (this.k) {
            b();
        }
    }

    public boolean d() {
        return a;
    }

    public cc e() {
        if (this.u == null) {
            return null;
        }
        f();
        return (!this.v.a() || cg.c) ? null : this.u.a(this.v.d());
    }

    public boolean f() {
        if (this.q != null) {
            List m = this.q.m();
            if (m != null && m.size() > 0) {
                return this.t.b(((Long) m.get(0)).longValue());
            }
        }
        return false;
    }

    public int g() {
        return this.u == null ? 0 : this.u.a();
    }
}
