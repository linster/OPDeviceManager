package net.oneplus.odm.common;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.OpFeatures;
import android.view.Display;
import android.view.WindowManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

public class a {
    private static SubscriptionManager R;
    private static TelephonyManager S;

    public static String O() {
        Date time = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault()).getTime();
        return "GMT" + new SimpleDateFormat("Z").format(time);
    }

    public static String P() {
        return UUID.randomUUID().toString();
    }

    public static byte[] Q(String str) {
        return R(str.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] R(byte[] bArr) {
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream(new String(bArr).length());
        S(byteArrayInputStream, byteArrayOutputStream);
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        byteArrayInputStream.close();
        return toByteArray;
    }

    public static void S(InputStream inputStream, OutputStream outputStream) {
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(outputStream);
        byte[] bArr = new byte[10240];
        while (true) {
            int read = inputStream.read(bArr, 0, 10240);
            if (read != -1) {
                gZIPOutputStream.write(bArr, 0, read);
            } else {
                gZIPOutputStream.finish();
                gZIPOutputStream.flush();
                gZIPOutputStream.close();
                return;
            }
        }
    }

    public static String T() {
        return VERSION.RELEASE;
    }

    public static String U() {
        return Build.BRAND;
    }

    public static String V() {
        return Build.SERIAL;
    }

    public static String W(Context context) {
        String string = System.getString(context.getContentResolver(), "doze_mode_enabaled");
        return string == null ? "1" : string;
    }

    public static String X(Context context) {
        SecurityException securityException;
        Exception exception;
        TelephonyManager aj = aj(context);
        String str = "";
        int simCount = aj.getSimCount();
        int i = 0;
        while (i < simCount) {
            try {
                String str2 = str + b.ar(aj.getDeviceId(i));
                if (i + 1 < simCount) {
                    try {
                        str2 = str2 + ",";
                    } catch (SecurityException e) {
                        SecurityException securityException2 = e;
                        str = str2;
                        securityException = securityException2;
                    } catch (Exception e2) {
                        Exception exception2 = e2;
                        str = str2;
                        exception = exception2;
                    }
                }
                i++;
                str = str2;
            } catch (SecurityException e3) {
                securityException = e3;
            } catch (Exception e4) {
                exception = e4;
            }
        }
        return str;
        Log.e("DeviceManagerUtil", securityException.getMessage());
        return str;
        Log.e("DeviceManagerUtil", exception.getMessage());
        return str;
    }

    public static String Y(Context context) {
        String str = "";
        try {
            return b.ar(aj(context).getImei(0));
        } catch (SecurityException e) {
            Log.e("DeviceManagerUtil", e.getMessage());
            return str;
        } catch (Exception e2) {
            Log.e("DeviceManagerUtil", e2.getMessage());
            return str;
        }
    }

    public static Locale Z(Context context) {
        return context.getResources().getConfiguration().locale;
    }

    public static String aa() {
        return Build.MODEL;
    }

    public static String ab() {
        return an() ? "Hydrogen " + Build.DISPLAY : ao() ? "Oxygen " + Build.DISPLAY : Build.DISPLAY;
    }

    public static String ac() {
        String str = SystemProperties.get("ro.boot.pcba_number", "");
        return str == null ? "" : str;
    }

    public static int ad(Context context) {
        Display defaultDisplay = ak(context).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }

    public static int ae(Context context) {
        Display defaultDisplay = ak(context).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    public static String af() {
        String str = null;
        if (an()) {
            str = SystemProperties.get("ro.rom.version", "");
        }
        if (ao()) {
            str = SystemProperties.get("ro.oxygen.version", "");
        }
        return str == null ? "" : str;
    }

    public static String ag(Context context) {
        StringBuilder stringBuilder = new StringBuilder("");
        SubscriptionManager ai = ai(context);
        if (ai != null) {
            int sIMCount = Util.getSIMCount(context);
            for (int i = 0; i < sIMCount; i++) {
                SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = ai.getActiveSubscriptionInfoForSimSlotIndex(i);
                if (activeSubscriptionInfoForSimSlotIndex != null) {
                    stringBuilder.append(activeSubscriptionInfoForSimSlotIndex.getCountryIso());
                }
                if (i + 1 < sIMCount) {
                    stringBuilder.append(",");
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String ah() {
        String str = SystemProperties.get("ro.build.soft.version", "");
        return str == null ? "" : str;
    }

    private static SubscriptionManager ai(Context context) {
        if (R == null) {
            R = SubscriptionManager.from(context);
        }
        return R;
    }

    private static TelephonyManager aj(Context context) {
        if (S == null) {
            S = (TelephonyManager) context.getSystemService("phone");
        }
        return S;
    }

    private static WindowManager ak(Context context) {
        return (WindowManager) context.getSystemService("window");
    }

    public static boolean al() {
        return SystemProperties.get("persist.sys.public.type", "").equals("daily");
    }

    public static boolean am() {
        return SystemProperties.get("ro.debuggable", "").equals("1");
    }

    public static boolean an() {
        return OpFeatures.isSupport(new int[]{0});
    }

    public static boolean ao() {
        return OpFeatures.isSupport(new int[]{1});
    }

    public static boolean ap() {
        return SystemProperties.get("ro.remount.time", "0").equals("1") || SystemProperties.get("persist.sys.adbroot", "0").equals("1") || SystemProperties.get("ro.remount.runtime", "0").equals("1");
    }

    public static boolean aq(Context context) {
        return System.getInt(context.getContentResolver(), "oem_join_user_plan_settings", 0) != 0;
    }
}
