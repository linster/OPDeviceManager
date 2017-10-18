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
import org.xml.sax.SAXException;

public class bK {
    private static String a = "";
    private static boolean tF = false;
    private static String tG = "";
    private static String tH = "";
    private static String tI = "";
    private static String tJ = "";

    public static void xH() {
        try {
            if (VERSION.SDK_INT > 14) {
                TrafficStats.class.getDeclaredMethod("setThreadStatsTag", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(40964)});
            }
        } catch (Throwable e) {
            D.mF(e, "DeviceInfo", "setTraficTag");
        } catch (Throwable e2) {
            D.mF(e2, "DeviceInfo", "setTraficTag");
        } catch (Throwable e22) {
            D.mF(e22, "DeviceInfo", "setTraficTag");
        } catch (Throwable e222) {
            D.mF(e222, "DeviceInfo", "setTraficTag");
        } catch (Throwable e2222) {
            D.mF(e2222, "DeviceInfo", "setTraficTag");
        }
    }

    public static String xI(Context context) {
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
                    SAXParserFactory.newInstance().newSAXParser().parse(file, new bm());
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

    static String xJ(Context context) {
        if (context != null) {
            String bssid;
            try {
                if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    bssid = !wifiManager.isWifiEnabled() ? null : wifiManager.getConnectionInfo().getBSSID();
                    return bssid;
                }
            } catch (Throwable th) {
                D.mF(th, "DeviceInfo", "getWifiMacs");
                bssid = null;
            }
        }
        return null;
    }

    static String xK(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        if (context != null && context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0) {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                List scanResults = wifiManager.getScanResults();
                if (scanResults == null || scanResults.size() == 0) {
                    return stringBuilder.toString();
                }
                List ya = ya(scanResults);
                Object obj = 1;
                int i = 0;
                while (i < ya.size() && i < 7) {
                    ScanResult scanResult = (ScanResult) ya.get(i);
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
                D.mF(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return stringBuilder.toString();
    }

    static String xL(Context context) {
        try {
            if (tG != null) {
                if (!"".equals(tG)) {
                    return tG;
                }
            }
            if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") != 0) {
                return tG;
            }
            tG = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            return tG;
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getDeviceMac");
        }
    }

    static String[] xM(Context context) {
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
            D.mF(th, "DeviceInfo", "cellInfo");
        }
    }

    static String xN(Context context) {
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
            D.mF(th, "DeviceInfo", "getMNC");
            networkOperator = str;
        }
    }

    public static int xO(Context context) {
        try {
            return xZ(context);
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getNetWorkType");
            return -1;
        }
    }

    public static int xP(Context context) {
        try {
            return xY(context);
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getActiveNetWorkType");
            return -1;
        }
    }

    public static NetworkInfo xQ(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return null;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
    }

    static String xR(Context context) {
        String extraInfo;
        try {
            NetworkInfo xQ = xQ(context);
            if (xQ == null) {
                return null;
            }
            extraInfo = xQ.getExtraInfo();
            return extraInfo;
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getNetworkExtraInfo");
            extraInfo = null;
        }
    }

    static String xS(Context context) {
        try {
            if (tH != null) {
                if (!"".equals(tH)) {
                    return tH;
                }
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            tH = i2 <= i ? i2 + "*" + i : i + "*" + i2;
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getReslution");
        }
        return tH;
    }

    public static String xT(Context context) {
        try {
            if (tI != null) {
                if (!"".equals(tI)) {
                    return tI;
                }
            }
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return tI;
            }
            tI = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (tI == null) {
                tI = "";
            }
            return tI;
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getDeviceID");
        }
    }

    public static String xU(Context context) {
        String str = "";
        try {
            return xW(context);
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getSubscriberId");
            return str;
        }
    }

    static String xV(Context context) {
        try {
            return xX(context);
        } catch (Throwable th) {
            D.mF(th, "DeviceInfo", "getNetworkOperatorName");
            return "";
        }
    }

    private static String xW(Context context) {
        if (tJ != null && !"".equals(tJ)) {
            return tJ;
        }
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            return tJ;
        }
        tJ = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        if (tJ == null) {
            tJ = "";
        }
        return tJ;
    }

    private static String xX(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        CharSequence simOperatorName = telephonyManager.getSimOperatorName();
        return !TextUtils.isEmpty(simOperatorName) ? simOperatorName : telephonyManager.getNetworkOperatorName();
    }

    private static int xY(Context context) {
        if (context == null || context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return -1;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null ? activeNetworkInfo.getType() : -1;
    }

    private static int xZ(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0 ? ((TelephonyManager) context.getSystemService("phone")).getNetworkType() : -1;
    }

    private static List ya(List list) {
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
