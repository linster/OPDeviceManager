package com.loc;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.location.Location;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Base64;

import com.amap.api.location.AMapLocation;
import com.autonavi.aps.amapapi.model.AmapLoc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;

import org.json.JSONObject;

/* compiled from: Utils */
public class br {
    private static int a;
    private static String[] b;
    private static Hashtable<String, Long> c;
    private static DecimalFormat d;
    private static SimpleDateFormat e;

    static {
        a = 0;
        b = null;
        c = new Hashtable();
        d = null;
        e = null;
    }

    public static void a(Object... objArr) {
    }

    public static void a(Throwable th) {
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str) || !TextUtils.isDigitsOnly(str)) {
            return false;
        }
        return ",111,123,134,199,202,204,206,208,212,213,214,216,218,219,220,222,225,226,228,230,231,232,234,235,238,240,242,244,246,247,248,250,255,257,259,260,262,266,268,270,272,274,276,278,280,282,283,284,286,288,289,290,292,293,294,295,297,302,308,310,311,312,313,314,315,316,310,330,332,334,338,340,342,344,346,348,350,352,354,356,358,360,362,363,364,365,366,368,370,372,374,376,400,401,402,404,405,406,410,412,413,414,415,416,417,418,419,420,421,422,424,425,426,427,428,429,430,431,432,434,436,437,438,440,441,450,452,454,455,456,457,466,467,470,472,502,505,510,514,515,520,525,528,530,534,535,536,537,539,540,541,542,543,544,545,546,547,548,549,550,551,552,553,555,560,598,602,603,604,605,606,607,608,609,610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,639,640,641,642,643,645,646,647,648,649,650,651,652,653,654,655,657,659,665,702,704,706,708,710,712,714,716,722,724,730,732,734,736,738,740,742,744,746,748,750,850,901,".contains("," + str + ",");
    }

    public static boolean a(double d) {
        if (d <= 180.0d && d >= -180.0d) {
            return true;
        }
        return false;
    }

    public static boolean b(double d) {
        if (d <= 90.0d && d >= -90.0d) {
            return true;
        }
        return false;
    }

    public static boolean a(AMapLocation aMapLocation) {
        if (aMapLocation == null || aMapLocation.getErrorCode() != 0) {
            return false;
        }
        double longitude = aMapLocation.getLongitude();
        double latitude = aMapLocation.getLatitude();
        float accuracy = aMapLocation.getAccuracy();
        if (longitude == 0.0d && latitude == 0.0d && ((double) accuracy) == 0.0d) {
            return false;
        }
        if ((longitude > 180.0d) || latitude > 90.0d) {
            return false;
        }
        if ((longitude < -180.0d) || latitude < -90.0d) {
            return false;
        }
        return true;
    }

    public static boolean a(AmapLoc amapLoc) {
        if (amapLoc == null || amapLoc.k().equals("8") || amapLoc.k().equals("5") || amapLoc.k().equals("6")) {
            return false;
        }
        double f = amapLoc.f();
        double g = amapLoc.g();
        float h = amapLoc.h();
        if (f == 0.0d && g == 0.0d && ((double) h) == 0.0d) {
            return false;
        }
        if ((f > 180.0d) || g > 90.0d) {
            return false;
        }
        if ((f < -180.0d) || g < -90.0d) {
            return false;
        }
        return true;
    }

    public static int a(int i) {
        return (i * 2) - 113;
    }

    public static String[] a(TelephonyManager telephonyManager) {
        int i;
        int parseInt;
        String str = null;
        if (telephonyManager != null) {
            str = telephonyManager.getNetworkOperator();
        }
        String[] strArr = new String[]{"0", "0"};
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else if (!TextUtils.isDigitsOnly(str)) {
            i = 0;
        } else if (str.length() > 4) {
            i = 1;
        } else {
            i = 0;
        }
        if (i != 0) {
            strArr[0] = str.substring(0, 3);
            char[] toCharArray = str.substring(3).toCharArray();
            i = 0;
            while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                i++;
            }
            strArr[1] = str.substring(3, i + 3);
        }
        try {
            parseInt = Integer.parseInt(strArr[0]);
        } catch (Exception e) {
            parseInt = 0;
        }
        if (parseInt == 0) {
            strArr[0] = "0";
        }
        if (!strArr[0].equals("0") && !strArr[1].equals("0")) {
            b = strArr;
            return strArr;
        } else if (!strArr[0].equals("0") || !strArr[1].equals("0")) {
            return strArr;
        } else {
            if (b == null) {
                return strArr;
            }
            return b;
        }
    }

    public static int a(CellLocation cellLocation, Context context) {
        if (a(context) || cellLocation == null) {
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

    public static long a() {
        return System.currentTimeMillis();
    }

    public static long b() {
        return SystemClock.elapsedRealtime();
    }

    public static boolean a(Context context) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        String str;
        if (c() >= 17) {
            try {
                String str2;
                str2 = "android.provider.Settings$Global";
                str = ((String) bq.a(str2, "AIRPLANE_MODE_ON")).toString();
                if (((Integer) bq.a(str2, "getInt", new Object[]{contentResolver, str}, new Class[]{ContentResolver.class, String.class})).intValue() == 1) {
                    z = true;
                }
                return z;
            } catch (Exception e) {
                return false;
            }
        }
        try {
            str2 = "android.provider.Settings$System";
            str = ((String) bq.a(str2, "AIRPLANE_MODE_ON")).toString();
            return ((Integer) bq.a(str2, "getInt", new Object[]{contentResolver, str}, new Class[]{ContentResolver.class, String.class})).intValue() == 1;
        } catch (Exception e2) {
            return false;
        }
    }

    public static float a(double[] dArr) {
        if (dArr.length != 4) {
            return 0.0f;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(dArr[0], dArr[1], dArr[2], dArr[3], fArr);
        return fArr[0];
    }

    public static float a(AmapLoc amapLoc, AmapLoc amapLoc2) {
        return a(new double[]{amapLoc.g(), amapLoc.f(), amapLoc2.g(), amapLoc2.f()});
    }

    public static Object a(Context context, String str) {
        Object obj = null;
        if (context == null) {
            return obj;
        }
        try {
            obj = context.getApplicationContext().getSystemService(str);
        } catch (Throwable th) {
        }
        return obj;
    }

    public static int c() {
        int i = 0;
        if (a > 0) {
            return a;
        }
        String str = "android.os.Build$VERSION";
        try {
            i = bq.b(str, "SDK_INT");
        } catch (Exception e) {
            try {
                i = Integer.parseInt(bq.a(str, "SDK").toString());
            } catch (Exception e2) {
            }
        }
        return i;
    }

    public static byte[] a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            bArr2 = t.a(bArr);
        } catch (Exception e) {
        }
        return bArr2;
    }

    public static String d() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(d()).append(File.separator);
        stringBuilder.append("amaplocationapi").append(File.separator);
        return stringBuilder.toString();
    }

    public static String f() {
        return Build.MODEL;
    }

    public static String g() {
        return VERSION.RELEASE;
    }

    public static String b(Context context) {
        CharSequence charSequence = null;
        if (context == null) {
            return null;
        }
        if (!TextUtils.isEmpty(c.l)) {
            return c.l;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 64);
        } catch (Exception e) {
            packageInfo = null;
        }
        try {
            if (TextUtils.isEmpty(c.m)) {
                c.m = null;
            }
        } catch (Exception e2) {
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (packageInfo != null) {
            if (packageInfo.applicationInfo != null) {
                charSequence = packageInfo.applicationInfo.loadLabel(context.getPackageManager());
            }
            if (charSequence != null) {
                stringBuilder.append(charSequence.toString());
            }
            if (!TextUtils.isEmpty(packageInfo.versionName)) {
                stringBuilder.append(packageInfo.versionName);
            }
        }
        if (!TextUtils.isEmpty(c.i)) {
            stringBuilder.append(",").append(c.i);
        }
        if (!TextUtils.isEmpty(c.m)) {
            stringBuilder.append(",").append(c.m);
        }
        return stringBuilder.toString();
    }

    public static NetworkInfo c(Context context) {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = n.i(context);
        } catch (Exception e) {
        }
        return networkInfo;
    }

    public static boolean h() {
        return a(0, 1) == 1;
    }

    public static int a(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    public static boolean a(JSONObject jSONObject, String str) {
        return t.a(jSONObject, str);
    }

    public static boolean a(ScanResult scanResult) {
        if (scanResult == null || TextUtils.isEmpty(scanResult.BSSID)) {
            return false;
        }
        if (scanResult.BSSID.equals("00:00:00:00:00:00")) {
            return false;
        }
        if (scanResult.BSSID.contains(" :")) {
            return false;
        }
        return true;
    }

    public static void i() {
        c.clear();
    }

    public static String j() {
        String str = "";
        try {
            return o.a(c.g.getBytes("UTF-8")).substring(20);
        } catch (Exception e) {
            return "";
        }
    }

    public static int a(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static String b(TelephonyManager telephonyManager) {
        int i = 0;
        if (telephonyManager != null) {
            i = telephonyManager.getNetworkType();
        }
        return (String) c.s.get(i, "UNKWN");
    }

    public static boolean d(Context context) {
        try {
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.equals(context.getPackageName())) {
                    return runningAppProcessInfo.importance != 100;
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public static final long b(byte[] bArr) {
        long j = 0;
        for (int i = 0; i < 8; i++) {
            j = (j << 8) | ((long) (bArr[i] & 255));
        }
        return j;
    }

    public static final int c(byte[] bArr) {
        return ((bArr[0] & 255) << 8) | (bArr[1] & 255);
    }

    public static final int d(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (i < 4) {
            i2 = (i2 << 8) | (bArr[i] & 255);
            i++;
        }
        return i2;
    }

    public static final byte[] a(File file) throws IOException {
        if (file != null && file.exists()) {
            byte[] bArr = new byte[2048];
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } else {
            throw new IOException("can't operate on null");
        }
    }

    public static byte[] b(String str) {
        byte[] bArr = new byte[6];
        String[] split = str.split(":");
        for (int i = 0; i < split.length; i++) {
            bArr[i] = (byte) ((byte) Integer.parseInt(split[i], 16));
        }
        return bArr;
    }

    public static String c(String str) {
        return a(str, 0);
    }

    public static String a(String str, int i) {
        byte[] bArr = null;
        try {
            bArr = str.getBytes("UTF-8");
        } catch (Exception e) {
        }
        return Base64.encodeToString(bArr, i);
    }

    public static String d(String str) {
        byte[] decode;
        try {
            decode = Base64.decode(str, 0);
        } catch (Exception e) {
            decode = null;
        }
        try {
            return new String(decode, "UTF-8");
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ((byte) ((int) ((j >> (i * 8)) & 255)));
        }
        return bArr;
    }

    public static byte[] e(String str) {
        return b(Integer.parseInt(str));
    }

    public static byte[] b(int i) {
        byte[] bArr = new byte[2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((byte) ((i >> (i2 * 8)) & 255));
        }
        return bArr;
    }

    public static synchronized String a(long j, String str) {
        String format;
        Object obj = null;
        synchronized (br.class) {
            if (TextUtils.isEmpty(str)) {
                str = "yyyy-MM-dd HH:mm:ss";
            }
            if (e != null) {
                e.applyPattern(str);
            } else {
                try {
                    e = new SimpleDateFormat(str, Locale.CHINA);
                } catch (Exception e) {
                }
            }
            if (j > 0) {
                obj = 1;
            }
            if (obj == null) {
                j = a();
            }
            if (e != null) {
                format = e.format(Long.valueOf(j));
            } else {
                format = "NULL";
            }
        }
        return format;
    }

    public static byte[] f(String str) {
        return c(Integer.parseInt(str));
    }

    public static byte[] c(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((byte) ((i >> (i2 * 8)) & 255));
        }
        return bArr;
    }

    public static boolean k() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String a(Object obj, String str) {
        if (d == null) {
            d = new DecimalFormat("#");
        }
        d.applyPattern(str);
        return d.format(obj);
    }
}
