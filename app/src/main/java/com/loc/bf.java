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
public final class bf {
    static String a = "";
    private static bf rZ = null;
    private static int si = 10000;
    protected static boolean sj = true;
    protected static boolean sk = false;
    private static String[] sl = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.ACCESS_WIFI_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE"};
    private Context rF = null;
    private TelephonyManager rG = null;
    private LocationManager rH = null;
    private WifiManager rI = null;
    private SensorManager rJ = null;
    private String rK = "";
    private String rL = "";
    private String rM = "";
    private boolean rN = false;
    private int rO = 0;
    private boolean rP = false;
    private long rQ = -1;
    private String rR = "";
    private String rS = "";
    private int rT = 0;
    private int rU = 0;
    private int rV = 0;
    private String rW = "";
    private long rX = 0;
    private long rY = 0;
    private at sa = null;
    private X sb = null;
    private CellLocation sc = null;
    private F sd = null;
    private List se = new ArrayList();
    private Timer sf = null;
    private Thread sg = null;
    private Looper sh = null;

    private bf(Context context) {
        if (context != null) {
            this.rF = context;
            this.rK = Build.MODEL;
            this.rG = (TelephonyManager) context.getSystemService("phone");
            this.rH = (LocationManager) context.getSystemService("location");
            this.rI = (WifiManager) context.getSystemService("wifi");
            this.rJ = (SensorManager) context.getSystemService("sensor");
            if (this.rG != null && this.rI != null) {
                try {
                    this.rL = this.rG.getDeviceId();
                } catch (Exception e) {
                }
                this.rM = this.rG.getSubscriberId();
                if (this.rI.getConnectionInfo() != null) {
                    this.rS = this.rI.getConnectionInfo().getMacAddress();
                    if (this.rS != null && this.rS.length() > 0) {
                        this.rS = this.rS.replace(":", "");
                    }
                }
                String[] tB = tB(this.rG);
                this.rT = Integer.parseInt(tB[0]);
                this.rU = Integer.parseInt(tB[1]);
                this.rV = this.rG.getNetworkType();
                this.rW = context.getPackageName();
                this.rN = this.rG.getPhoneType() == 2;
            }
        }
    }

    protected static bf tA(Context context) {
        if (rZ == null && uw(context)) {
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
                rZ = new bf(context);
            }
        }
        return rZ;
    }

    private static String[] tB(TelephonyManager telephonyManager) {
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

    static /* synthetic */ void uD(bf bfVar, PhoneStateListener phoneStateListener) {
        if (bfVar.rG != null) {
            bfVar.rG.listen(phoneStateListener, 273);
        }
    }

    static /* synthetic */ void uG(bf bfVar, NmeaListener nmeaListener) {
        if (bfVar.rH != null && nmeaListener != null) {
            bfVar.rH.addNmeaListener(nmeaListener);
        }
    }

    private static boolean ul(Object obj) {
        try {
            Method declaredMethod = WifiManager.class.getDeclaredMethod("isScanAlwaysAvailable", null);
            if (declaredMethod != null) {
                return ((Boolean) declaredMethod.invoke(obj, null)).booleanValue();
            }
        } catch (Exception e) {
        }
        return false;
    }

    private static int um(Object obj) {
        try {
            Method declaredMethod = Sensor.class.getDeclaredMethod("getMinDelay", null);
            if (declaredMethod != null) {
                return ((Integer) declaredMethod.invoke(obj, null)).intValue();
            }
        } catch (Exception e) {
        }
        return 0;
    }

    private void un() {
        if (this.rI != null) {
            try {
                if (sj) {
                    uv(this.rI);
                }
            } catch (Exception e) {
            }
        }
    }

    private void uo(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.rF != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            this.rF.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    private void up(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.rF != null) {
            try {
                this.rF.unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
            }
        }
    }

    private static void uq(List list) {
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
    private boolean ur(android.telephony.CellLocation r6) {
        /*
        r5 = this;
        r4 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r3 = -1;
        r1 = 0;
        if (r6 == 0) goto L_0x0013;
    L_0x0007:
        r0 = 1;
        r2 = r5.rF;
        r2 = us(r6, r2);
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
        r2 = com.loc.aW.tg(r6, r2, r3);	 Catch:{ Exception -> 0x006a }
        if (r2 <= 0) goto L_0x0012;
    L_0x0050:
        r2 = "getNetworkId";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x006a }
        r2 = com.loc.aW.tg(r6, r2, r3);	 Catch:{ Exception -> 0x006a }
        if (r2 < 0) goto L_0x0012;
    L_0x005c:
        r2 = "getBaseStationId";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x006a }
        r2 = com.loc.aW.tg(r6, r2, r3);	 Catch:{ Exception -> 0x006a }
        if (r2 >= 0) goto L_0x0011;
    L_0x0068:
        r0 = r1;
        goto L_0x0011;
    L_0x006a:
        r1 = move-exception;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.ur(android.telephony.CellLocation):boolean");
    }

    private static int us(CellLocation cellLocation, Context context) {
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

    private CellLocation ut() {
        CellLocation uu;
        if (this.rG == null) {
            return null;
        }
        try {
            uu = uu((List) aW.tf(this.rG, "getAllCellInfo", new Object[0]));
        } catch (NoSuchMethodException e) {
            uu = null;
            return uu;
        } catch (Exception e2) {
            uu = null;
            return uu;
        }
        return uu;
    }

    private static CellLocation uu(List list) {
        CellLocation gsmCellLocation;
        int i;
        if (list == null || list.isEmpty()) {
            return null;
        }
        int i2;
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
                    i2 = !loadClass.isInstance(obj2) ? !loadClass2.isInstance(obj2) ? !loadClass3.isInstance(obj2) ? !loadClass4.isInstance(obj2) ? 0 : 4 : 3 : 2 : 1;
                    if (i2 > 0) {
                        obj = null;
                        if (i2 == 1) {
                            obj = loadClass.cast(obj2);
                        } else if (i2 == 2) {
                            obj = loadClass2.cast(obj2);
                        } else if (i2 == 3) {
                            obj = loadClass3.cast(obj2);
                        } else if (i2 == 4) {
                            obj = loadClass4.cast(obj2);
                        }
                        try {
                            obj2 = aW.tf(obj, "getCellIdentity", new Object[0]);
                            if (obj2 != null) {
                                if (i2 != 4) {
                                    int tg;
                                    int tg2;
                                    if (i2 != 3) {
                                        tg = aW.tg(obj2, "getLac", new Object[0]);
                                        tg2 = aW.tg(obj2, "getCid", new Object[0]);
                                        gsmCellLocation = new GsmCellLocation();
                                        try {
                                            gsmCellLocation.setLacAndCid(tg, tg2);
                                        } catch (Exception e) {
                                            cellLocation = gsmCellLocation;
                                            i = i2;
                                        }
                                    } else {
                                        tg = aW.tg(obj2, "getTac", new Object[0]);
                                        tg2 = aW.tg(obj2, "getCi", new Object[0]);
                                        gsmCellLocation = new GsmCellLocation();
                                        gsmCellLocation.setLacAndCid(tg, tg2);
                                    }
                                    cellLocation = gsmCellLocation;
                                    break;
                                }
                                gsmCellLocation = new CdmaCellLocation();
                                try {
                                    gsmCellLocation.setCellLocationData(aW.tg(obj2, "getBasestationId", new Object[0]), aW.tg(obj2, "getLatitude", new Object[0]), aW.tg(obj2, "getLongitude", new Object[0]), aW.tg(obj2, "getSystemId", new Object[0]), aW.tg(obj2, "getNetworkId", new Object[0]));
                                    cellLocation2 = gsmCellLocation;
                                    break;
                                } catch (Exception e2) {
                                    cellLocation2 = gsmCellLocation;
                                    i = i2;
                                }
                            }
                        } catch (Exception e3) {
                            i = i2;
                        }
                    }
                    cellLocation3 = cellLocation2;
                    cellLocation4 = cellLocation;
                    int i4 = i2;
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
        i2 = obj;
        return i2 != 4 ? cellLocation : cellLocation2;
    }

    private static void uv(WifiManager wifiManager) {
        if (wifiManager != null) {
            try {
                aW.tf(wifiManager, "startScanActive", new Object[0]);
            } catch (Exception e) {
                wifiManager.startScan();
            }
        }
    }

    private static boolean uw(Context context) {
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            for (Object obj : sl) {
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

    protected static boolean uy(Context context) {
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

    protected final void tC() {
        String str = "";
        tD();
        if (this.sh != null) {
            this.sh.quit();
            this.sh = null;
        }
        if (this.sg != null) {
            this.sg.interrupt();
            this.sg = null;
        }
        this.sg = new w(this, str);
        this.sg.start();
    }

    protected final void tD() {
        if (this.sa != null) {
            PhoneStateListener phoneStateListener = this.sa;
            if (this.rG != null) {
                this.rG.listen(phoneStateListener, 0);
            }
            this.sa = null;
        }
        if (this.sb != null) {
            NmeaListener nmeaListener = this.sb;
            if (!(this.rH == null || nmeaListener == null)) {
                this.rH.removeNmeaListener(nmeaListener);
            }
            this.sb = null;
        }
        if (this.sf != null) {
            this.sf.cancel();
            this.sf = null;
        }
        if (this.sh != null) {
            this.sh.quit();
            this.sh = null;
        }
        if (this.sg != null) {
            this.sg.interrupt();
            this.sg = null;
        }
    }

    protected final boolean tE() {
        CellLocation cellLocation = null;
        if (this.rG != null && this.rG.getSimState() == 5 && this.rP) {
            return true;
        }
        if (this.rG != null) {
            try {
                cellLocation = this.rG.getCellLocation();
            } catch (Exception e) {
            }
            if (cellLocation != null) {
                this.rY = System.currentTimeMillis();
                this.sc = cellLocation;
                return true;
            }
        }
        return false;
    }

    protected final boolean tF() {
        if (this.rI != null) {
            if (this.rI.isWifiEnabled() || ul(this.rI)) {
                return true;
            }
        }
        return false;
    }

    protected final boolean tG() {
        try {
            if (this.rH != null && this.rH.isProviderEnabled("gps")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    protected final String tH() {
        if (this.rK == null) {
            this.rK = Build.MODEL;
        }
        return this.rK == null ? "" : this.rK;
    }

    protected final String tI() {
        if (this.rL == null && this.rF != null) {
            this.rG = (TelephonyManager) this.rF.getSystemService("phone");
            if (this.rG != null) {
                try {
                    this.rL = this.rG.getDeviceId();
                } catch (Exception e) {
                }
            }
        }
        return this.rL == null ? "" : this.rL;
    }

    protected final String tJ() {
        if (this.rM == null && this.rF != null) {
            this.rG = (TelephonyManager) this.rF.getSystemService("phone");
            if (this.rG != null) {
                this.rM = this.rG.getSubscriberId();
            }
        }
        return this.rM == null ? "" : this.rM;
    }

    protected final boolean tK() {
        return this.rN;
    }

    protected final List tL() {
        if (System.getInt(this.rF.getContentResolver(), "airplane_mode_on", 0) == 1) {
            return new ArrayList();
        }
        if (!tE()) {
            return new ArrayList();
        }
        Object ut;
        List arrayList = new ArrayList();
        if (!ur(this.sc)) {
            ut = ut();
            if (ur(ut)) {
                this.rY = System.currentTimeMillis();
                arrayList.add(Long.valueOf(this.rY));
                arrayList.add(ut);
                return arrayList;
            }
        }
        ut = this.sc;
        arrayList.add(Long.valueOf(this.rY));
        arrayList.add(ut);
        return arrayList;
    }

    protected final List tM(boolean z) {
        int i = 1;
        int i2 = 0;
        List arrayList = new ArrayList();
        if (!tF()) {
            return new ArrayList();
        }
        List arrayList2 = new ArrayList();
        synchronized (this) {
            if (!z) {
                if ((System.currentTimeMillis() - this.rX >= 3500 ? 1 : 0) != 0) {
                    i = 0;
                }
                if (i == 0) {
                }
            }
            arrayList2.add(Long.valueOf(this.rX));
            while (i2 < this.se.size()) {
                arrayList.add(this.se.get(i2));
                i2++;
            }
            arrayList2.add(arrayList);
        }
        return arrayList2;
    }

    protected final byte tN() {
        return !tE() ? Byte.MIN_VALUE : (byte) this.rO;
    }

    protected final List tO() {
        List arrayList = new ArrayList();
        if (this.rG == null || !tE() || this.rG.getSimState() == 1) {
            return arrayList;
        }
        int i = 0;
        for (NeighboringCellInfo neighboringCellInfo : this.rG.getNeighboringCellInfo()) {
            if (i > 15) {
                break;
            } else if (!(neighboringCellInfo.getLac() == 0 || neighboringCellInfo.getLac() == 65535 || neighboringCellInfo.getCid() == 65535 || neighboringCellInfo.getCid() == 268435455)) {
                arrayList.add(neighboringCellInfo);
                i++;
            }
        }
        return arrayList;
    }

    protected final List tP() {
        Object obj = 1;
        List arrayList = new ArrayList();
        long j = -1;
        String str = "";
        if (tG()) {
            j = this.rQ;
            str = this.rR;
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

    protected final long tQ() {
        long j = this.rQ;
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

    protected final String tR() {
        if (this.rS == null && this.rF != null) {
            this.rI = (WifiManager) this.rF.getSystemService("wifi");
            if (!(this.rI == null || this.rI.getConnectionInfo() == null)) {
                this.rS = this.rI.getConnectionInfo().getMacAddress();
                if (this.rS != null && this.rS.length() > 0) {
                    this.rS = this.rS.replace(":", "");
                }
            }
        }
        return this.rS == null ? "" : this.rS;
    }

    protected final int tS() {
        return this.rT;
    }

    protected final int tT() {
        return this.rU;
    }

    protected final int tU() {
        return this.rV;
    }

    protected final String tV() {
        if (this.rW == null && this.rF != null) {
            this.rW = this.rF.getPackageName();
        }
        return this.rW == null ? "" : this.rW;
    }

    protected final List tW(float f) {
        List arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f) <= 1.0f) {
            f = 1.0f;
        }
        if (tE()) {
            CellLocation cellLocation = (CellLocation) tL().get(1);
            if (cellLocation != null && (cellLocation instanceof GsmCellLocation)) {
                arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getLac()));
                arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getCid()));
                if (((double) (currentTimeMillis - ((Long) tL().get(0)).longValue())) <= 50000.0d / ((double) f)) {
                    arrayList.add(Integer.valueOf(1));
                } else {
                    arrayList.add(Integer.valueOf(0));
                }
            }
        }
        return arrayList;
    }

    protected final List tX(float f) {
        List arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f) <= 1.0f) {
            f = 1.0f;
        }
        if (tE()) {
            CellLocation cellLocation = (CellLocation) tL().get(1);
            if (cellLocation != null && (cellLocation instanceof CdmaCellLocation)) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                arrayList.add(Integer.valueOf(cdmaCellLocation.getSystemId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getNetworkId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLongitude()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLatitude()));
                if (((double) (currentTimeMillis - ((Long) tL().get(0)).longValue())) <= 50000.0d / ((double) f)) {
                    arrayList.add(Integer.valueOf(1));
                } else {
                    arrayList.add(Integer.valueOf(0));
                }
            }
        }
        return arrayList;
    }

    protected final List tY() {
        int i = 0;
        List arrayList = new ArrayList();
        if (tF()) {
            List tM = tM(true);
            List list = (List) tM.get(1);
            long longValue = ((Long) tM.get(0)).longValue();
            uq(list);
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

    protected final void tZ(int i) {
        if (i != si) {
            synchronized (this) {
                this.se.clear();
            }
            if (this.sd != null) {
                up(this.sd);
                this.sd = null;
            }
            if (this.sf != null) {
                this.sf.cancel();
                this.sf = null;
            }
            if (i >= 5000) {
                si = i;
                this.sf = new Timer();
                this.sd = new F();
                uo(this.sd);
                un();
            }
        }
    }

    protected final void ua() {
        synchronized (this) {
            this.se.clear();
        }
        if (this.sd != null) {
            up(this.sd);
            this.sd = null;
        }
        if (this.sf != null) {
            this.sf.cancel();
            this.sf = null;
        }
        this.sf = new Timer();
        this.sd = new F();
        uo(this.sd);
        un();
    }

    protected final void ub() {
        synchronized (this) {
            this.se.clear();
        }
        if (this.sd != null) {
            up(this.sd);
            this.sd = null;
        }
        if (this.sf != null) {
            this.sf.cancel();
            this.sf = null;
        }
    }

    protected final byte uc() {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return (byte) 0;
        }
        List sensorList = this.rJ.getSensorList(-1);
        return sensorList != null ? (byte) sensorList.size() : (byte) 0;
    }

    protected final String ud(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return "null";
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getName() == null || ((Sensor) sensorList.get(i)).getName().length() <= 0) ? "null" : ((Sensor) sensorList.get(i)).getName();
    }

    protected final double ue(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return 0.0d;
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0.0d : (double) ((Sensor) sensorList.get(i)).getMaximumRange();
    }

    protected final int uf(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return 0;
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0 : um(sensorList.get(i));
    }

    protected final int ug(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return 0;
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0 : (int) (((double) ((Sensor) sensorList.get(i)).getPower()) * 100.0d);
    }

    protected final double uh(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return 0.0d;
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null) ? 0.0d : (double) ((Sensor) sensorList.get(i)).getResolution();
    }

    protected final byte ui(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return Byte.MAX_VALUE;
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getType() > 127) ? Byte.MAX_VALUE : (byte) ((Sensor) sensorList.get(i)).getType();
    }

    protected final String uj(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return "null";
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getVendor() == null || ((Sensor) sensorList.get(i)).getVendor().length() <= 0) ? "null" : ((Sensor) sensorList.get(i)).getVendor();
    }

    protected final byte uk(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.rJ == null) {
            return Byte.MAX_VALUE;
        }
        List sensorList = this.rJ.getSensorList(-1);
        return (sensorList == null || sensorList.get(i) == null || ((Sensor) sensorList.get(i)).getType() > 127) ? Byte.MAX_VALUE : (byte) ((Sensor) sensorList.get(i)).getVersion();
    }

    protected final Context ux() {
        return this.rF;
    }
}
