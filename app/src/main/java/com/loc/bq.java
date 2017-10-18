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

public class bq {
    private static int a = 0;
    private static String[] sE = null;
    private static Hashtable sF = new Hashtable();
    private static DecimalFormat sG = null;
    private static SimpleDateFormat sH = null;

    public static void vB(Object... objArr) {
    }

    public static void vC(Throwable th) {
    }

    public static boolean vD(String str) {
        return (TextUtils.isEmpty(str) || !TextUtils.isDigitsOnly(str)) ? false : ",111,123,134,199,202,204,206,208,212,213,214,216,218,219,220,222,225,226,228,230,231,232,234,235,238,240,242,244,246,247,248,250,255,257,259,260,262,266,268,270,272,274,276,278,280,282,283,284,286,288,289,290,292,293,294,295,297,302,308,310,311,312,313,314,315,316,310,330,332,334,338,340,342,344,346,348,350,352,354,356,358,360,362,363,364,365,366,368,370,372,374,376,400,401,402,404,405,406,410,412,413,414,415,416,417,418,419,420,421,422,424,425,426,427,428,429,430,431,432,434,436,437,438,440,441,450,452,454,455,456,457,466,467,470,472,502,505,510,514,515,520,525,528,530,534,535,536,537,539,540,541,542,543,544,545,546,547,548,549,550,551,552,553,555,560,598,602,603,604,605,606,607,608,609,610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,639,640,641,642,643,645,646,647,648,649,650,651,652,653,654,655,657,659,665,702,704,706,708,710,712,714,716,722,724,730,732,734,736,738,740,742,744,746,748,750,850,901,".contains("," + str + ",");
    }

    public static boolean vE(double d) {
        return d <= 180.0d && d >= -180.0d;
    }

    public static boolean vF(double d) {
        return d <= 90.0d && d >= -90.0d;
    }

    public static boolean vG(AMapLocation aMapLocation) {
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
        return !((longitude > -180.0d ? 1 : (longitude == -180.0d ? 0 : -1)) < 0) && latitude >= -90.0d;
    }

    public static boolean vH(AmapLoc amapLoc) {
        if (amapLoc == null || amapLoc.yY().equals("8") || amapLoc.yY().equals("5") || amapLoc.yY().equals("6")) {
            return false;
        }
        double yG = amapLoc.yG();
        double yJ = amapLoc.yJ();
        float yN = amapLoc.yN();
        if (yG == 0.0d && yJ == 0.0d && ((double) yN) == 0.0d) {
            return false;
        }
        if ((yG > 180.0d) || yJ > 90.0d) {
            return false;
        }
        return !((yG > -180.0d ? 1 : (yG == -180.0d ? 0 : -1)) < 0) && yJ >= -90.0d;
    }

    public static int vI(int i) {
        return (i * 2) - 113;
    }

    public static String[] vJ(TelephonyManager telephonyManager) {
        int parseInt;
        String str = null;
        if (telephonyManager != null) {
            str = telephonyManager.getNetworkOperator();
        }
        String[] strArr = new String[]{"0", "0"};
        int i = !TextUtils.isEmpty(str) ? TextUtils.isDigitsOnly(str) ? str.length() > 4 ? 1 : 0 : 0 : 0;
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
        if (strArr[0].equals("0") || strArr[1].equals("0")) {
            return (strArr[0].equals("0") && strArr[1].equals("0")) ? sE == null ? strArr : sE : strArr;
        } else {
            sE = strArr;
            return strArr;
        }
    }

    public static int vK(CellLocation cellLocation, Context context) {
        if (vN(context) || cellLocation == null) {
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

    public static long vL() {
        return System.currentTimeMillis();
    }

    public static long vM() {
        return SystemClock.elapsedRealtime();
    }

    public static boolean vN(Context context) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        String str;
        String str2;
        if (vR() >= 17) {
            try {
                str = "android.provider.Settings$Global";
                str2 = ((String) bJ.xC(str, "AIRPLANE_MODE_ON")).toString();
                if (((Integer) bJ.xG(str, "getInt", new Object[]{contentResolver, str2}, new Class[]{ContentResolver.class, String.class})).intValue() == 1) {
                    z = true;
                }
                return z;
            } catch (Exception e) {
                return false;
            }
        }
        try {
            str = "android.provider.Settings$System";
            str2 = ((String) bJ.xC(str, "AIRPLANE_MODE_ON")).toString();
            return ((Integer) bJ.xG(str, "getInt", new Object[]{contentResolver, str2}, new Class[]{ContentResolver.class, String.class})).intValue() == 1;
        } catch (Exception e2) {
            return false;
        }
    }

    public static float vO(double[] dArr) {
        if (dArr.length != 4) {
            return 0.0f;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(dArr[0], dArr[1], dArr[2], dArr[3], fArr);
        return fArr[0];
    }

    public static float vP(AmapLoc amapLoc, AmapLoc amapLoc2) {
        return vO(new double[]{amapLoc.yJ(), amapLoc.yG(), amapLoc2.yJ(), amapLoc2.yG()});
    }

    public static Object vQ(Context context, String str) {
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

    public static int vR() {
        int i = 0;
        if (a > 0) {
            return a;
        }
        String str = "android.os.Build$VERSION";
        try {
            i = bJ.xD(str, "SDK_INT");
        } catch (Exception e) {
            try {
                i = Integer.parseInt(bJ.xC(str, "SDK").toString());
            } catch (Exception e2) {
            }
        }
        return i;
    }

    public static byte[] vS(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            bArr2 = bw.xd(bArr);
        } catch (Exception e) {
        }
        return bArr2;
    }

    public static String vT() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String vU() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vT()).append(File.separator);
        stringBuilder.append("amaplocationapi").append(File.separator);
        return stringBuilder.toString();
    }

    public static String vV() {
        return Build.MODEL;
    }

    public static String vW() {
        return VERSION.RELEASE;
    }

    public static String vX(Context context) {
        CharSequence charSequence = null;
        if (context == null) {
            return null;
        }
        if (!TextUtils.isEmpty(H.lN)) {
            return H.lN;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 64);
        } catch (Exception e) {
            packageInfo = null;
        }
        try {
            if (TextUtils.isEmpty(H.lO)) {
                H.lO = null;
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
        if (!TextUtils.isEmpty(H.lK)) {
            stringBuilder.append(",").append(H.lK);
        }
        if (!TextUtils.isEmpty(H.lO)) {
            stringBuilder.append(",").append(H.lO);
        }
        return stringBuilder.toString();
    }

    public static NetworkInfo vY(Context context) {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = bK.xQ(context);
        } catch (Exception e) {
        }
        return networkInfo;
    }

    public static boolean vZ() {
        return wa(0, 1) == 1;
    }

    public static int wa(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    public static boolean wb(JSONObject jSONObject, String str) {
        return bw.wX(jSONObject, str);
    }

    public static boolean wc(ScanResult scanResult) {
        return (scanResult == null || TextUtils.isEmpty(scanResult.BSSID)) ? false : !scanResult.BSSID.equals("00:00:00:00:00:00") ? !scanResult.BSSID.contains(" :") : false;
    }

    public static void wd() {
        sF.clear();
    }

    public static String we() {
        String str = "";
        try {
            return aG.ro(H.lI.getBytes("UTF-8")).substring(20);
        } catch (Exception e) {
            return "";
        }
    }

    public static int wf(NetworkInfo networkInfo) {
        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) ? networkInfo.getType() : -1;
    }

    public static String wg(TelephonyManager telephonyManager) {
        int i = 0;
        if (telephonyManager != null) {
            i = telephonyManager.getNetworkType();
        }
        return (String) H.lU.get(i, "UNKWN");
    }

    public static boolean wh(Context context) {
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

    public static final long wi(byte[] bArr) {
        long j = 0;
        for (int i = 0; i < 8; i++) {
            j = (j << 8) | ((long) (bArr[i] & 255));
        }
        return j;
    }

    public static final int wj(byte[] bArr) {
        return ((bArr[0] & 255) << 8) | (bArr[1] & 255);
    }

    public static final int wk(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (i < 4) {
            i2 = (i2 << 8) | (bArr[i] & 255);
            i++;
        }
        return i2;
    }

    public static final byte[] wl(File file) {
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

    public static byte[] wm(String str) {
        byte[] bArr = new byte[6];
        String[] split = str.split(":");
        for (int i = 0; i < split.length; i++) {
            bArr[i] = (byte) ((byte) Integer.parseInt(split[i], 16));
        }
        return bArr;
    }

    public static String wn(String str) {
        return wo(str, 0);
    }

    public static String wo(String str, int i) {
        byte[] bArr = null;
        try {
            bArr = str.getBytes("UTF-8");
        } catch (Exception e) {
        }
        return Base64.encodeToString(bArr, i);
    }

    public static String wp(String str) {
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

    public static byte[] wq(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ((byte) ((int) ((j >> (i * 8)) & 255)));
        }
        return bArr;
    }

    public static byte[] wr(String str) {
        return ws(Integer.parseInt(str));
    }

    public static byte[] ws(int i) {
        byte[] bArr = new byte[2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((byte) ((i >> (i2 * 8)) & 255));
        }
        return bArr;
    }

    public static synchronized String wt(long j, String str) {
        String format;
        Object obj = null;
        synchronized (bq.class) {
            if (TextUtils.isEmpty(str)) {
                str = "yyyy-MM-dd HH:mm:ss";
            }
            if (sH != null) {
                sH.applyPattern(str);
            } else {
                try {
                    sH = new SimpleDateFormat(str, Locale.CHINA);
                } catch (Exception e) {
                }
            }
            if (j > 0) {
                obj = 1;
            }
            if (obj == null) {
                j = vL();
            }
            format = sH != null ? sH.format(Long.valueOf(j)) : "NULL";
        }
        return format;
    }

    public static byte[] wu(String str) {
        return wv(Integer.parseInt(str));
    }

    public static byte[] wv(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((byte) ((i >> (i2 * 8)) & 255));
        }
        return bArr;
    }

    public static boolean ww() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String wx(Object obj, String str) {
        if (sG == null) {
            sG = new DecimalFormat("#");
        }
        sG.applyPattern(str);
        return sG.format(obj);
    }
}
