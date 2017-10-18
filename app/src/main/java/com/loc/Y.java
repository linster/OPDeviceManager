package com.loc;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.security.MessageDigest;
import java.util.Locale;

public class Y {
    private static String a = "";
    private static String mA = "";
    private static String mB = "";
    private static String mC = "";
    private static String mD = null;

    public static String nH(Context context) {
        try {
            if (!"".equals(a)) {
                return a;
            }
            PackageManager packageManager = context.getPackageManager();
            a = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
            return a;
        } catch (Throwable e) {
            D.mF(e, "AppInfo", "getApplicationName");
        } catch (Throwable e2) {
            D.mF(e2, "AppInfo", "getApplicationName");
        }
    }

    public static String nI(Context context) {
        try {
            if (mA != null) {
                if (!"".equals(mA)) {
                    return mA;
                }
            }
            mA = context.getApplicationContext().getPackageName();
        } catch (Throwable th) {
            D.mF(th, "AppInfo", "getPackageName");
        }
        return mA;
    }

    public static String nJ(Context context) {
        try {
            if (!"".equals(mB)) {
                return mB;
            }
            mB = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return mB;
        } catch (Throwable e) {
            D.mF(e, "AppInfo", "getApplicationVersion");
        } catch (Throwable e2) {
            D.mF(e2, "AppInfo", "getApplicationVersion");
        }
    }

    public static String nK(Context context) {
        try {
            if (mD != null && !"".equals(mD)) {
                return mD;
            }
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            byte[] digest = MessageDigest.getInstance("SHA1").digest(packageInfo.signatures[0].toByteArray());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String toUpperCase = Integer.toHexString(b & 255).toUpperCase(Locale.US);
                if (toUpperCase.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(toUpperCase);
                stringBuffer.append(":");
            }
            stringBuffer.append(packageInfo.packageName);
            mD = stringBuffer.toString();
            return mD;
        } catch (Throwable e) {
            D.mF(e, "AppInfo", "getSHA1AndPackage");
            return mD;
        } catch (Throwable e2) {
            D.mF(e2, "AppInfo", "getSHA1AndPackage");
            return mD;
        } catch (Throwable e22) {
            D.mF(e22, "AppInfo", "getSHA1AndPackage");
            return mD;
        }
    }

    static void nL(String str) {
        mC = str;
    }

    private static String nM(Context context) {
        if (mC == null || mC.equals("")) {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return mC;
            }
            mC = applicationInfo.metaData.getString("com.amap.api.v2.apikey");
        }
        return mC;
    }

    public static String nN(Context context) {
        try {
            return nM(context);
        } catch (Throwable e) {
            D.mF(e, "AppInfo", "getKey");
            return mC;
        } catch (Throwable e2) {
            D.mF(e2, "AppInfo", "getKey");
            return mC;
        }
    }
}
