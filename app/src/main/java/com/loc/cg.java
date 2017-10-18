package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TreeMap;

/* compiled from: Unknown */
public final class cg {
    private static int G;
    private static String[] H;
    static String a;
    protected static boolean b;
    protected static boolean c;
    private static cg x;
    private CellLocation A;
    private ck B;
    private List C;
    private Timer D;
    private Thread E;
    private Looper F;
    private Context d;
    private TelephonyManager e;
    private LocationManager f;
    private WifiManager g;
    private SensorManager h;
    private String i;
    private String j;
    private String k;
    private boolean l;
    private int m;
    private boolean n;
    private long o;
    private String p;
    private String q;
    private int r;
    private int s;
    private int t;
    private String u;
    private long v;
    private long w;
    private ci y;
    private cj z;

    static {
        x = null;
        G = 10000;
        a = "";
        b = true;
        c = false;
        H = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.ACCESS_WIFI_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE"};
    }

    private cg(Context context) {
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = "";
        this.j = "";
        this.k = "";
        this.l = false;
        this.m = 0;
        this.n = false;
        this.o = -1;
        this.p = "";
        this.q = "";
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.u = "";
        this.v = 0;
        this.w = 0;
        this.y = null;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = new ArrayList();
        this.D = null;
        this.E = null;
        this.F = null;
        if (context != null) {
            this.d = context;
            this.i = Build.MODEL;
            this.e = (TelephonyManager) context.getSystemService("phone");
            this.f = (LocationManager) context.getSystemService("location");
            this.g = (WifiManager) context.getSystemService("wifi");
            this.h = (SensorManager) context.getSystemService("sensor");
            if (this.e != null && this.g != null) {
                try {
                    this.j = this.e.getDeviceId();
                } catch (Exception e) {
                }
                this.k = this.e.getSubscriberId();
                if (this.g.getConnectionInfo() != null) {
                    this.q = this.g.getConnectionInfo().getMacAddress();
                    if (this.q != null && this.q.length() > 0) {
                        this.q = this.q.replace(":", "");
                    }
                }
                String[] b = b(this.e);
                this.r = Integer.parseInt(b[0]);
                this.s = Integer.parseInt(b[1]);
                this.t = this.e.getNetworkType();
                this.u = context.getPackageName();
                this.l = this.e.getPhoneType() == 2;
            }
        }
    }

    private CellLocation A() {
        CellLocation b;
        if (this.e == null) {
            return null;
        }
        try {
            b = b((List) cd.a(this.e, "getAllCellInfo", new Object[0]));
        } catch (NoSuchMethodException e) {
            b = null;
            return b;
        } catch (Exception e2) {
            b = null;
            return b;
        }
        return b;
    }

    private static int a(CellLocation cellLocation, Context context) {
        if (System.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1 || cellLocation == null) {
            return 9;
        }
        if (cellLocation instanceof GsmCellLocation) {
            return 1;
        }
        try {
            Class.forName("android.telephony.cdma.CdmaCellLocation");
            return 2;
        } catch (Exception e) {
            return 9;
        }
    }

    protected static cg a(Context context) {
        if (x == null && c(context)) {
            Object obj;
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager != null) {
                for (String str : locationManager.getAllProviders()) {
                    if (str.equals("passive") || str.equals("gps")) {
                        obj = 1;
                        break;
                    }
                }
            }
            obj = null;
            if (obj != null) {
                x = new cg(context);
            }
        }
        return x;
    }

    private void a(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.d != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            this.d.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    private static void a(WifiManager wifiManager) {
        if (wifiManager != null) {
            try {
                cd.a(wifiManager, "startScanActive", new Object[0]);
            } catch (Exception e) {
                wifiManager.startScan();
            }
        }
    }

    static /* synthetic */ void a(cg cgVar, NmeaListener nmeaListener) {
        if (cgVar.f != null && nmeaListener != null) {
            cgVar.f.addNmeaListener(nmeaListener);
        }
    }

    static /* synthetic */ void a(cg cgVar, PhoneStateListener phoneStateListener) {
        if (cgVar.e != null) {
            cgVar.e.listen(phoneStateListener, 273);
        }
    }

    private static void a(List list) {
        if (list != null && list.size() > 0) {
            Object hashMap = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                ScanResult scanResult = (ScanResult) list.get(i);
                if (scanResult.SSID == null) {
                    scanResult.SSID = "null";
                }
                hashMap.put(Integer.valueOf(scanResult.level), scanResult);
            }
            TreeMap treeMap = new TreeMap(Collections.reverseOrder());
            treeMap.putAll(hashMap);
            list.clear();
            for (Object obj : treeMap.keySet()) {
                list.add(treeMap.get(obj));
            }
            hashMap.clear();
            treeMap.clear();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.telephony.CellLocation r6) {
        /*
        r5 = this;
        r4 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r3 = -1;
        r1 = 0;
        if (r6 == 0) goto L_0x0013;
    L_0x0007:
        r0 = 1;
        r2 = r5.d;
        r2 = a(r6, r2);
        switch(r2) {
            case 1: goto L_0x0014;
            case 2: goto L_0x0044;
            default: goto L_0x0011;
        };
    L_0x0011:
        r1 = r0;
    L_0x0012:
        return r1;
    L_0x0013:
        return r1;
    L_0x0014:
        r6 = (android.telephony.gsm.GsmCellLocation) r6;
        r2 = r6.getLac();
        if (r2 == r3) goto L_0x0012;
    L_0x001c:
        r2 = r6.getLac();
        if (r2 == 0) goto L_0x0012;
    L_0x0022:
        r2 = r6.getLac();
        if (r2 > r4) goto L_0x0012;
    L_0x0028:
        r2 = r6.getCid();
        if (r2 == r3) goto L_0x0012;
    L_0x002e:
        r2 = r6.getCid();
        if (r2 == 0) goto L_0x0012;
    L_0x0034:
        r2 = r6.getCid();
        if (r2 == r4) goto L_0x0012;
    L_0x003a:
        r2 = r6.getCid();
        r3 = 268435455; // 0xfffffff float:2.5243547E-29 double:1.326247364E-315;
        if (r2 >= r3) goto L_0x0012;
    L_0x0043:
        goto L_0x0011;
    L_0x0044:
        r2 = "getSystemId";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x006a }
        r2 = com.loc.cd.b(r6, r2, r3);	 Catch:{ Exception -> 0x006a }
        if (r2 <= 0) goto L_0x0012;
    L_0x0050:
        r2 = "getNetworkId";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x006a }
        r2 = com.loc.cd.b(r6, r2, r3);	 Catch:{ Exception -> 0x006a }
        if (r2 < 0) goto L_0x0012;
    L_0x005c:
        r2 = "getBaseStationId";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x006a }
        r2 = com.loc.cd.b(r6, r2, r3);	 Catch:{ Exception -> 0x006a }
        if (r2 >= 0) goto L_0x0011;
    L_0x0068:
        r0 = r1;
        goto L_0x0011;
    L_0x006a:
        r1 = move-exception;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cg.a(android.telephony.CellLocation):boolean");
    }

    private static boolean a(Object obj) {
        try {
            Method declaredMethod = WifiManager.class.getDeclaredMethod("isScanAlwaysAvailable", null);
            if (declaredMethod != null) {
                return ((Boolean) declaredMethod.invoke(obj, null)).booleanValue();
            }
        } catch (Exception e) {
        }
        return false;
    }

    private static int b(Object obj) {
        try {
            Method declaredMethod = Sensor.class.getDeclaredMethod("getMinDelay", null);
            if (declaredMethod != null) {
                return ((Integer) declaredMethod.invoke(obj, null)).intValue();
            }
        } catch (Exception e) {
        }
        return 0;
    }

    private static CellLocation b(List list) {
        int i;
        int i2;
        if (list == null || list.isEmpty()) {
            return null;
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        int i3 = 0;
        CellLocation cellLocation = null;
        Object obj = null;
        CellLocation cellLocation2 = null;
        while (i3 < list.size()) {
            CellLocation cellLocation3;
            CellLocation cellLocation4;
            Object obj2 = list.get(i3);
            if (obj2 != null) {
                try {
                    Class loadClass = systemClassLoader.loadClass("android.telephony.CellInfoGsm");
                    Class loadClass2 = systemClassLoader.loadClass("android.telephony.CellInfoWcdma");
                    Class loadClass3 = systemClassLoader.loadClass("android.telephony.CellInfoLte");
                    Class loadClass4 = systemClassLoader.loadClass("android.telephony.CellInfoCdma");
                    i = !loadClass.isInstance(obj2) ? !loadClass2.isInstance(obj2) ? !loadClass3.isInstance(obj2) ? !loadClass4.isInstance(obj2) ? 0 : 4 : 3 : 2 : 1;
                    if (i > 0) {
                        obj = null;
                        if (i == 1) {
                            obj = loadClass.cast(obj2);
                        } else if (i == 2) {
                            obj = loadClass2.cast(obj2);
                        } else if (i == 3) {
                            obj = loadClass3.cast(obj2);
                        } else if (i == 4) {
                            obj = loadClass4.cast(obj2);
                        }
                        try {
                            obj2 = cd.a(obj, "getCellIdentity", new Object[0]);
                            if (obj2 != null) {
                                CellLocation gsmCellLocation;
                                if (i != 4) {
                                    int b;
                                    int b2;
                                    if (i != 3) {
                                        b = cd.b(obj2, "getLac", new Object[0]);
                                        b2 = cd.b(obj2, "getCid", new Object[0]);
                                        gsmCellLocation = new GsmCellLocation();
                                        try {
                                            gsmCellLocation.setLacAndCid(b, b2);
                                        } catch (Exception e) {
                                            cellLocation = gsmCellLocation;
                                            i2 = i;
                                        }
                                    } else {
                                        b = cd.b(obj2, "getTac", new Object[0]);
                                        b2 = cd.b(obj2, "getCi", new Object[0]);
                                        gsmCellLocation = new GsmCellLocation();
                                        gsmCellLocation.setLacAndCid(b, b2);
                                    }
                                    cellLocation = gsmCellLocation;
                                    break;
                                }
                                gsmCellLocation = new CdmaCellLocation();
                                try {
                                    gsmCellLocation.setCellLocationData(cd.b(obj2, "getBasestationId", new Object[0]), cd.b(obj2, "getLatitude", new Object[0]), cd.b(obj2, "getLongitude", new Object[0]), cd.b(obj2, "getSystemId", new Object[0]), cd.b(obj2, "getNetworkId", new Object[0]));
                                    cellLocation2 = gsmCellLocation;
                                    break;
                                } catch (Exception e2) {
                                    cellLocation2 = gsmCellLocation;
                                    i2 = i;
                                }
                            }
                        } catch (Exception e3) {
                            i2 = i;
                        }
                    }
                    cellLocation3 = cellLocation2;
                    cellLocation4 = cellLocation;
                    int i4 = i;
                } catch (Exception e4) {
                }
                i3++;
                cellLocation = cellLocation4;
                obj = r1;
                cellLocation2 = cellLocation3;
            }
            cellLocation3 = cellLocation2;
            cellLocation4 = cellLocation;
            Object obj3 = obj;
            i3++;
            cellLocation = cellLocation4;
            obj = obj3;
            cellLocation2 = cellLocation3;
        }
        i = obj;
        return i != 4 ? cellLocation : cellLocation2;
    }

    private void b(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.d != null) {
            try {
                this.d.unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
            }
        }
    }

    protected static boolean b(Context context) {
        if (context == null) {
            return true;
        }
        boolean z;
        if (!Secure.getString(context.getContentResolver(), "mock_location").equals("0")) {
            PackageManager packageManager = context.getPackageManager();
            List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(128);
            String str = "android.permission.ACCESS_MOCK_LOCATION";
            String packageName = context.getPackageName();
            z = false;
            for (ApplicationInfo applicationInfo : installedApplications) {
                if (z) {
                    break;
                }
                boolean z2;
                try {
                    String[] strArr = packageManager.getPackageInfo(applicationInfo.packageName, 4096).requestedPermissions;
                    if (strArr != null) {
                        int length = strArr.length;
                        int i = 0;
                        while (i < length) {
                            if (!strArr[i].equals(str)) {
                                i++;
                            } else if (!applicationInfo.packageName.equals(packageName)) {
                                z2 = true;
                                z = z2;
                            }
                        }
                    }
                } catch (Exception e) {
                    z2 = z;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    private static String[] b(TelephonyManager telephonyManager) {
        String str = null;
        int i = 0;
        if (telephonyManager != null) {
            str = telephonyManager.getNetworkOperator();
        }
        String[] strArr = new String[]{"0", "0"};
        if (TextUtils.isDigitsOnly(str) && str.length() > 4) {
            strArr[0] = str.substring(0, 3);
            char[] toCharArray = str.substring(3).toCharArray();
            while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                i++;
            }
            strArr[1] = str.substring(3, i + 3);
        }
        return strArr;
    }

    private static boolean c(Context context) {
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            for (Object obj : H) {
                boolean z;
                if (!(strArr == null || obj == null)) {
                    for (String equals : strArr) {
                        if (equals.equals(obj)) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                if (!z) {
                    return false;
                }
            }
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private void z() {
        if (this.g != null) {
            try {
                if (b) {
                    a(this.g);
                }
            } catch (Exception e) {
            }
        }
    }

    protected final List a(float f) {
        List arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f) <= 1.0f) {
            f = 1.0f;
        }
        if (c()) {
            CellLocation cellLocation = (CellLocation) j().get(1);
            if (cellLocation != null && (cellLocation instanceof GsmCellLocation)) {
                arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getLac()));
                arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getCid()));
                if (((double) (currentTimeMillis - ((Long) j().get(0)).longValue())) <= 50000.0d / ((double) f)) {
                    arrayList.add(Integer.valueOf(1));
                } else {
                    arrayList.add(Integer.valueOf(0));
                }
            }
        }
        return arrayList;
    }

    protected final List a(boolean z) {
        int i = 1;
        int i2 = 0;
        List arrayList = new ArrayList();
        if (!d()) {
            return new ArrayList();
        }
        List arrayList2 = new ArrayList();
        synchronized (this) {
            if (!z) {
                if ((System.currentTimeMillis() - this.v >= 3500 ? 1 : 0) != 0) {
                    i = 0;
                }
                if (i == 0) {
                }
            }
            arrayList2.add(Long.valueOf(this.v));
            while (i2 < this.C.size()) {
                arrayList.add(this.C.get(i2));
                i2++;
            }
            arrayList2.add(arrayList);
        }
        return arrayList2;
    }

    protected final void a() {
        String str = "";
        b();
        if (this.F != null) {
            this.F.quit();
            this.F = null;
        }
        if (this.E != null) {
            this.E.interrupt();
            this.E = null;
        }
        this.E = new ch(this, str);
        this.E.start();
    }

    protected final void a(int i) {
        if (i != G) {
            synchronized (this) {
                this.C.clear();
            }
            if (this.B != null) {
                b(this.B);
                this.B = null;
            }
            if (this.D != null) {
                this.D.cancel();
                this.D = null;
            }
            if (i >= 5000) {
                G = i;
                this.D = new Timer();
                this.B = new ck();
                a(this.B);
                z();
            }
        }
    }

    protected final String b(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return "null";
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getName() == null || ((Sensor) sensorList.get(i)).getName().length() <= 0) ? "null" : ((Sensor) sensorList.get(i)).getName();
    }

    protected final List b(float f) {
        List arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f) <= 1.0f) {
            f = 1.0f;
        }
        if (c()) {
            CellLocation cellLocation = (CellLocation) j().get(1);
            if (cellLocation != null && (cellLocation instanceof CdmaCellLocation)) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                arrayList.add(Integer.valueOf(cdmaCellLocation.getSystemId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getNetworkId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLongitude()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLatitude()));
                if (((double) (currentTimeMillis - ((Long) j().get(0)).longValue())) <= 50000.0d / ((double) f)) {
                    arrayList.add(Integer.valueOf(1));
                } else {
                    arrayList.add(Integer.valueOf(0));
                }
            }
        }
        return arrayList;
    }

    protected final void b() {
        if (this.y != null) {
            PhoneStateListener phoneStateListener = this.y;
            if (this.e != null) {
                this.e.listen(phoneStateListener, 0);
            }
            this.y = null;
        }
        if (this.z != null) {
            NmeaListener nmeaListener = this.z;
            if (!(this.f == null || nmeaListener == null)) {
                this.f.removeNmeaListener(nmeaListener);
            }
            this.z = null;
        }
        if (this.D != null) {
            this.D.cancel();
            this.D = null;
        }
        if (this.F != null) {
            this.F.quit();
            this.F = null;
        }
        if (this.E != null) {
            this.E.interrupt();
            this.E = null;
        }
    }

    protected final double c(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return 0.0d;
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0.0d : (double) ((Sensor) sensorList.get(i)).getMaximumRange();
    }

    protected final boolean c() {
        CellLocation cellLocation = null;
        if (this.e != null && this.e.getSimState() == 5 && this.n) {
            return true;
        }
        if (this.e != null) {
            try {
                cellLocation = this.e.getCellLocation();
            } catch (Exception e) {
            }
            if (cellLocation != null) {
                this.w = System.currentTimeMillis();
                this.A = cellLocation;
                return true;
            }
        }
        return false;
    }

    protected final int d(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return 0;
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0 : b(sensorList.get(i));
    }

    protected final boolean d() {
        if (this.g != null) {
            if (this.g.isWifiEnabled() || a(this.g)) {
                return true;
            }
        }
        return false;
    }

    protected final int e(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return 0;
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0 : (int) (((double) ((Sensor) sensorList.get(i)).getPower()) * 100.0d);
    }

    protected final boolean e() {
        try {
            if (this.f != null && this.f.isProviderEnabled("gps")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    protected final double f(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return 0.0d;
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0.0d : (double) ((Sensor) sensorList.get(i)).getResolution();
    }

    protected final String f() {
        if (this.i == null) {
            this.i = Build.MODEL;
        }
        return this.i == null ? "" : this.i;
    }

    protected final byte g(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return Byte.MAX_VALUE;
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getType() > 127) ? Byte.MAX_VALUE : (byte) ((Sensor) sensorList.get(i)).getType();
    }

    protected final String g() {
        if (this.j == null && this.d != null) {
            this.e = (TelephonyManager) this.d.getSystemService("phone");
            if (this.e != null) {
                try {
                    this.j = this.e.getDeviceId();
                } catch (Exception e) {
                }
            }
        }
        return this.j == null ? "" : this.j;
    }

    protected final String h() {
        if (this.k == null && this.d != null) {
            this.e = (TelephonyManager) this.d.getSystemService("phone");
            if (this.e != null) {
                this.k = this.e.getSubscriberId();
            }
        }
        return this.k == null ? "" : this.k;
    }

    protected final String h(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return "null";
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getVendor() == null || ((Sensor) sensorList.get(i)).getVendor().length() <= 0) ? "null" : ((Sensor) sensorList.get(i)).getVendor();
    }

    protected final byte i(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return Byte.MAX_VALUE;
        }
        List sensorList = this.h.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getType() > 127) ? Byte.MAX_VALUE : (byte) ((Sensor) sensorList.get(i)).getVersion();
    }

    protected final boolean i() {
        return this.l;
    }

    protected final List j() {
        if (System.getInt(this.d.getContentResolver(), "airplane_mode_on", 0) == 1) {
            return new ArrayList();
        }
        if (!c()) {
            return new ArrayList();
        }
        List arrayList = new ArrayList();
        if (!a(this.A)) {
            CellLocation A = A();
            if (a(A)) {
                this.w = System.currentTimeMillis();
                arrayList.add(Long.valueOf(this.w));
                arrayList.add(r0);
                return arrayList;
            }
        }
        Object obj = this.A;
        arrayList.add(Long.valueOf(this.w));
        arrayList.add(obj);
        return arrayList;
    }

    protected final byte k() {
        return !c() ? Byte.MIN_VALUE : (byte) this.m;
    }

    protected final List l() {
        List arrayList = new ArrayList();
        if (this.e == null || !c() || this.e.getSimState() == 1) {
            return arrayList;
        }
        int i = 0;
        for (NeighboringCellInfo neighboringCellInfo : this.e.getNeighboringCellInfo()) {
            if (i > 15) {
                break;
            } else if (!(neighboringCellInfo.getLac() == 0 || neighboringCellInfo.getLac() == 65535 || neighboringCellInfo.getCid() == 65535 || neighboringCellInfo.getCid() == 268435455)) {
                arrayList.add(neighboringCellInfo);
                i++;
            }
        }
        return arrayList;
    }

    protected final List m() {
        Object obj = 1;
        List arrayList = new ArrayList();
        long j = -1;
        String str = "";
        if (e()) {
            j = this.o;
            str = this.p;
        }
        String str2 = str;
        long j2 = j;
        String str3 = str2;
        if ((j2 > 0 ? 1 : null) == null) {
            j2 = System.currentTimeMillis() / 1000;
        }
        if (j2 > 2147483647L) {
            obj = null;
        }
        if (obj == null) {
            j2 /= 1000;
        }
        arrayList.add(Long.valueOf(j2));
        arrayList.add(str3);
        return arrayList;
    }

    protected final long n() {
        long j = this.o;
        if ((j > 0 ? 1 : null) == null) {
            return 0;
        }
        int length = String.valueOf(j).length();
        while (length != 13) {
            long j2 = length <= 13 ? j * 10 : j / 10;
            j = j2;
            length = String.valueOf(j2).length();
        }
        return j;
    }

    protected final String o() {
        if (this.q == null && this.d != null) {
            this.g = (WifiManager) this.d.getSystemService("wifi");
            if (!(this.g == null || this.g.getConnectionInfo() == null)) {
                this.q = this.g.getConnectionInfo().getMacAddress();
                if (this.q != null && this.q.length() > 0) {
                    this.q = this.q.replace(":", "");
                }
            }
        }
        return this.q == null ? "" : this.q;
    }

    protected final int p() {
        return this.r;
    }

    protected final int q() {
        return this.s;
    }

    protected final int r() {
        return this.t;
    }

    protected final String s() {
        if (this.u == null && this.d != null) {
            this.u = this.d.getPackageName();
        }
        return this.u == null ? "" : this.u;
    }

    protected final List t() {
        int i = 0;
        List arrayList = new ArrayList();
        if (d()) {
            List a = a(true);
            List list = (List) a.get(1);
            long longValue = ((Long) a.get(0)).longValue();
            a(list);
            arrayList.add(Long.valueOf(longValue));
            if (list != null && list.size() > 0) {
                while (i < list.size()) {
                    ScanResult scanResult = (ScanResult) list.get(i);
                    if (arrayList.size() - 1 >= 40) {
                        break;
                    }
                    if (scanResult != null) {
                        List arrayList2 = new ArrayList();
                        arrayList2.add(scanResult.BSSID.replace(":", ""));
                        arrayList2.add(Integer.valueOf(scanResult.level));
                        arrayList2.add(scanResult.SSID);
                        arrayList.add(arrayList2);
                    }
                    i++;
                }
            }
        }
        return arrayList;
    }

    protected final void u() {
        synchronized (this) {
            this.C.clear();
        }
        if (this.B != null) {
            b(this.B);
            this.B = null;
        }
        if (this.D != null) {
            this.D.cancel();
            this.D = null;
        }
        this.D = new Timer();
        this.B = new ck();
        a(this.B);
        z();
    }

    protected final void v() {
        synchronized (this) {
            this.C.clear();
        }
        if (this.B != null) {
            b(this.B);
            this.B = null;
        }
        if (this.D != null) {
            this.D.cancel();
            this.D = null;
        }
    }

    protected final byte w() {
        ArrayList arrayList = new ArrayList();
        if (this.h == null) {
            return (byte) 0;
        }
        List sensorList = this.h.getSensorList(-1);
        return sensorList != null ? (byte) sensorList.size() : (byte) 0;
    }

    protected final Context x() {
        return this.d;
    }
}
