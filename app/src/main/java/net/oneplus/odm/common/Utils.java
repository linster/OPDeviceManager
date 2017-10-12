package net.oneplus.odm.common;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.telephony.CellInfo;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.OpFeatures;
import android.view.Display;
import android.view.WindowManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

public class Utils {
    private static SubscriptionManager mSubscriptionManager;
    private static TelephonyManager mTelephonyManager;
    private static WifiManager mWifiManager;

    public static String getDeviceId() {
        return Build.SERIAL;
    }

    public static String getIMEI(Context context) {
        TelephonyManager mngr = getTelephonyManager(context);
        String imeis = "";
        int simCount = mngr.getSimCount();
        int i = 0;
        while (i < simCount) {
            try {
                imeis = imeis + mngr.getDeviceId(i);
                if (i + 1 < simCount) {
                    imeis = imeis + ",";
                }
                i++;
            } catch (SecurityException e) {
                Log.e("DeviceManagerUtil", e.getMessage());
            } catch (Exception e2) {
                Log.e("DeviceManagerUtil", e2.getMessage());
            }
        }
        return imeis;
    }

    public static String getIMEI1(Context context) {
        String imei = "";
        try {
            imei = getTelephonyManager(context).getImei(0);
        } catch (SecurityException e) {
            Log.e("DeviceManagerUtil", e.getMessage());
        } catch (Exception e2) {
            Log.e("DeviceManagerUtil", e2.getMessage());
        }
        return imei;
    }

    public static String getSoftVersion() {
        String softVersion = SystemProperties.get("ro.build.soft.version", "");
        if (softVersion == null) {
            return "";
        }
        return softVersion;
    }

    public static String getRomVersion() {
        String romVersion = null;
        if (isH2()) {
            romVersion = SystemProperties.get("ro.rom.version", "");
        }
        if (isO2()) {
            romVersion = SystemProperties.get("ro.oxygen.version", "");
        }
        if (romVersion == null) {
            return "";
        }
        return romVersion;
    }

    public static String getPCBA() {
        String pcba = SystemProperties.get("ro.boot.pcba_number", "");
        if (pcba == null) {
            return "";
        }
        return pcba;
    }

    public static Locale getLocale(Context context) {
        return context.getResources().getConfiguration().locale;
    }

    public static String getAndroidVersion() {
        return VERSION.RELEASE;
    }

    public static int getResolutionWidth(Context context) {
        Display display = getWindowManager(context).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getResolutionHeight(Context context) {
        Display display = getWindowManager(context).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static String getCellSignalLevel(Context context) {
        TelephonyManager mngr = getTelephonyManager(context);
        List<CellInfo> cellInfoList = new ArrayList();
        String strength = "";
        if (mngr == null || mngr.getAllCellInfo() == null) {
            return "";
        }
        cellInfoList = mngr.getAllCellInfo();
        try {
            int size = cellInfoList.size();
            for (int i = 0; i < size; i++) {
                strength = strength + ((CellInfo) cellInfoList.get(i)).toString() + " ";
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e("DeviceManagerUtil", e.getMessage());
        }
        return strength;
    }

    public static String getWifiSSID(Context context) {
        return getWifiManager(context).getConnectionInfo().getSSID();
    }

    public static boolean getIsHiddenSSID(Context context) {
        return getWifiManager(context).getConnectionInfo().getHiddenSSID();
    }

    public static String getWifiMacAddress(Context context) {
        return getWifiManager(context).getConnectionInfo().getMacAddress();
    }

    public static String getBSSID(Context context) {
        return getWifiManager(context).getConnectionInfo().getBSSID();
    }

    public static int getWifiSignalLevel(Context context) {
        return WifiManager.calculateSignalLevel(getWifiManager(context).getConnectionInfo().getRssi(), 100);
    }

    public static boolean isRooted() {
        String remountTime = SystemProperties.get("ro.remount.time", "0");
        String adbroot = SystemProperties.get("persist.sys.adbroot", "0");
        String remountRuntime = SystemProperties.get("ro.remount.runtime", "0");
        if (remountTime.equals("1") || adbroot.equals("1") || remountRuntime.equals("1")) {
            return true;
        }
        return false;
    }

    public static String getBrandName() {
        return Build.BRAND;
    }

    public static String getModelName() {
        return Build.MODEL;
    }

    public static String getOSVersion() {
        if (isH2()) {
            return "Hydrogen " + Build.DISPLAY;
        }
        if (isO2()) {
            return "Oxygen " + Build.DISPLAY;
        }
        return Build.DISPLAY;
    }

    public static String getBatteryStatus(Context context) {
        String statusStr = "Unknown";
        switch (context.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("status", 0)) {
            case 2:
                return "CHARGING";
            case 3:
                return "DISCHARGING";
            case 4:
                return "NOT CHARGING";
            case 5:
                return "FULL";
            default:
                return statusStr;
        }
    }

    public static float getBatteryLevel(Context context) {
        Intent intent = context.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return (((float) intent.getIntExtra("level", 1)) / ((float) intent.getIntExtra("scale", 1))) * 100.0f;
    }

    public static String getTimezone() {
        Date currentLocalTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault()).getTime();
        return "GMT" + new SimpleDateFormat("Z").format(currentLocalTime);
    }

    private static TelephonyManager getTelephonyManager(Context context) {
        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        }
        return mTelephonyManager;
    }

    public static String getSimCountryCode(Context context) {
        StringBuilder sb = new StringBuilder("");
        SubscriptionManager subscriptionManager = getSubscriptionManager(context);
        if (subscriptionManager != null) {
            int simCount = Util.getSIMCount(context);
            for (int i = 0; i < simCount; i++) {
                SubscriptionInfo info = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i);
                if (info != null) {
                    sb.append(info.getCountryIso());
                }
                if (i + 1 < simCount) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    private static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService("window");
    }

    private static WifiManager getWifiManager(Context context) {
        if (mWifiManager == null) {
            mWifiManager = (WifiManager) context.getSystemService("wifi");
        }
        return mWifiManager;
    }

    public static boolean isDebugRom() {
        if (SystemProperties.get("ro.debuggable", "").equals("1")) {
            return true;
        }
        return false;
    }

    public static boolean isDailyRom() {
        if (SystemProperties.get("persist.sys.public.type", "").equals("daily")) {
            return true;
        }
        return false;
    }

    public static boolean isH2() {
        return OpFeatures.isSupport(new int[]{0});
    }

    public static boolean isO2() {
        return OpFeatures.isSupport(new int[]{1});
    }

    private static SubscriptionManager getSubscriptionManager(Context context) {
        if (mSubscriptionManager == null) {
            mSubscriptionManager = SubscriptionManager.from(context);
        }
        return mSubscriptionManager;
    }

    public static byte[] compress(String src) throws IOException {
        return compress(src.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(new String(data).length());
        compress(bais, baos);
        byte[] output = baos.toByteArray();
        baos.flush();
        baos.close();
        bais.close();
        return output;
    }

    public static void compress(InputStream is, OutputStream os) throws IOException {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        byte[] data = new byte[10240];
        while (true) {
            int count = is.read(data, 0, 10240);
            if (count != -1) {
                gos.write(data, 0, count);
            } else {
                gos.finish();
                gos.flush();
                gos.close();
                return;
            }
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean isUserEnable(Context context) {
        return System.getInt(context.getContentResolver(), "oem_join_user_plan_settings", 0) != 0;
    }

    public static String getDozeMode(Context context) {
        String result = System.getString(context.getContentResolver(), "doze_mode_enabaled");
        if (result == null) {
            return "1";
        }
        return result;
    }
}
