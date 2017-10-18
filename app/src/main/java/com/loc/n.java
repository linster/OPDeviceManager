package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.System;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: DeviceInfo */
public class n {
    private static String a;
    private static boolean b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;

    /* compiled from: DeviceInfo */
    static class a extends DefaultHandler {
        a() {
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (str2.equals("string") && "UTDID".equals(attributes.getValue("name"))) {
                n.b = true;
            }
        }

        public void characters(char[] cArr, int i, int i2) throws SAXException {
            if (n.b) {
                n.a = new String(cArr, i, i2);
            }
        }

        public void endElement(String str, String str2, String str3) throws SAXException {
            n.b = false;
        }
    }

    static {
        a = "";
        b = false;
        c = "";
        d = "";
        e = "";
        f = "";
    }

    public static void a() {
        try {
            if (VERSION.SDK_INT > 14) {
                TrafficStats.class.getDeclaredMethod("setThreadStatsTag", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(40964)});
            }
        } catch (Throwable e) {
            v.a(e, "DeviceInfo", "setTraficTag");
        } catch (Throwable e2) {
            v.a(e2, "DeviceInfo", "setTraficTag");
        } catch (Throwable e22) {
            v.a(e22, "DeviceInfo", "setTraficTag");
        } catch (Throwable e222) {
            v.a(e222, "DeviceInfo", "setTraficTag");
        } catch (Throwable e2222) {
            v.a(e2222, "DeviceInfo", "setTraficTag");
        }
    }

    public static String a(Context context) {
        try {
            if (a != null) {
                if (!"".equals(a)) {
                    return a;
                }
            }
            if (context.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0) {
                a = System.getString(context.getContentResolver(), "mqBRboGZkQPcAkyk");
            }
            if (a != null) {
                if (!"".equals(a)) {
                    return a;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.UTSystemConfig/Global/Alvin2.xml");
                if (file.exists()) {
                    SAXParserFactory.newInstance().newSAXParser().parse(file, new a());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace();
        } catch (SAXException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        return a;
    }

    static String b(Context context) {
        if (context != null) {
            String bssid;
            try {
                if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        bssid = wifiManager.getConnectionInfo().getBSSID();
                    } else {
                        bssid = null;
                    }
                    return bssid;
                }
            } catch (Throwable th) {
                v.a(th, "DeviceInfo", "getWifiMacs");
                bssid = null;
            }
        }
        return null;
    }

    static String c(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        if (context != null && context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0) {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                List scanResults = wifiManager.getScanResults();
                if (scanResults == null || scanResults.size() == 0) {
                    return stringBuilder.toString();
                }
                List a = a(scanResults);
                Object obj = 1;
                int i = 0;
                while (i < a.size() && i < 7) {
                    ScanResult scanResult = (ScanResult) a.get(i);
                    if (obj == null) {
                        stringBuilder.append(";");
                    } else {
                        obj = null;
                    }
                    stringBuilder.append(scanResult.BSSID);
                    i++;
                }
            }
        } else {
            try {
                return stringBuilder.toString();
            } catch (Throwable th) {
                v.a(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return stringBuilder.toString();
    }

    static String d(Context context) {
        try {
            if (c != null) {
                if (!"".equals(c)) {
                    return c;
                }
            }
            if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") != 0) {
                return c;
            }
            c = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            return c;
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getDeviceMac");
        }
    }

    static String[] e(Context context) {
        try {
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0 && context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                CellLocation cellLocation = ((TelephonyManager) context.getSystemService("phone")).getCellLocation();
                int cid;
                int lac;
                if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    cid = gsmCellLocation.getCid();
                    lac = gsmCellLocation.getLac();
                    return new String[]{lac + "||" + cid, "gsm"};
                }
                if (cellLocation instanceof CdmaCellLocation) {
                    CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                    cid = cdmaCellLocation.getSystemId();
                    int networkId = cdmaCellLocation.getNetworkId();
                    lac = cdmaCellLocation.getBaseStationId();
                    if (cid >= 0 && networkId >= 0 && lac >= 0) {
                    }
                    return new String[]{cid + "||" + networkId + "||" + lac, "cdma"};
                }
                return new String[]{"", ""};
            }
            return new String[]{"", ""};
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "cellInfo");
        }
    }

    static String f(Context context) {
        String str = "";
        String networkOperator;
        try {
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return str;
            }
            networkOperator = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
            if (TextUtils.isEmpty(networkOperator) && networkOperator.length() < 3) {
                return str;
            }
            networkOperator = networkOperator.substring(3);
            return networkOperator;
        } catch (Throwable th) {
            th.printStackTrace();
            v.a(th, "DeviceInfo", "getMNC");
            networkOperator = str;
        }
    }

    public static int g(Context context) {
        try {
            return r(context);
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getNetWorkType");
            return -1;
        }
    }

    public static int h(Context context) {
        try {
            return q(context);
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getActiveNetWorkType");
            return -1;
        }
    }

    public static NetworkInfo i(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return null;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }

    static String j(Context context) {
        String extraInfo;
        try {
            NetworkInfo i = i(context);
            if (i == null) {
                return null;
            }
            extraInfo = i.getExtraInfo();
            return extraInfo;
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getNetworkExtraInfo");
            extraInfo = null;
        }
    }

    static String k(Context context) {
        try {
            if (d != null) {
                if (!"".equals(d)) {
                    return d;
                }
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            d = i2 <= i ? i2 + "*" + i : i + "*" + i2;
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getReslution");
        }
        return d;
    }

    public static String l(Context context) {
        try {
            if (e != null) {
                if (!"".equals(e)) {
                    return e;
                }
            }
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return e;
            }
            e = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (e == null) {
                e = "";
            }
            return e;
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getDeviceID");
        }
    }

    public static String m(Context context) {
        String str = "";
        try {
            return o(context);
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getSubscriberId");
            return str;
        }
    }

    static String n(Context context) {
        try {
            return p(context);
        } catch (Throwable th) {
            v.a(th, "DeviceInfo", "getNetworkOperatorName");
            return "";
        }
    }

    private static String o(Context context) {
        if (f != null && !"".equals(f)) {
            return f;
        }
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            return f;
        }
        f = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        if (f == null) {
            f = "";
        }
        return f;
    }

    private static String p(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            return null;
        }
        String networkOperatorName;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        CharSequence simOperatorName = telephonyManager.getSimOperatorName();
        if (TextUtils.isEmpty(simOperatorName)) {
            networkOperatorName = telephonyManager.getNetworkOperatorName();
        } else {
            networkOperatorName = simOperatorName;
        }
        return networkOperatorName;
    }

    private static int q(Context context) {
        if (context == null || context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return -1;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getType();
        }
        return -1;
    }

    private static int r(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
            return ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
        }
        return -1;
    }

    private static List<ScanResult> a(List<ScanResult> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            for (int i2 = 1; i2 < size - i; i2++) {
                if (((ScanResult) list.get(i2 - 1)).level > ((ScanResult) list.get(i2)).level) {
                    ScanResult scanResult = (ScanResult) list.get(i2 - 1);
                    list.set(i2 - 1, list.get(i2));
                    list.set(i2, scanResult);
                }
            }
        }
        return list;
    }
}
