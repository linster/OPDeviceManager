package com.loc;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.SparseArray;
import com.amap.api.location.AMapLocation;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.util.HashMap;
import java.util.Locale;

public class H {
    static int a = 2048;
    private static String lB = "http://apilocate.amap.com/mobile/binary";
    public static String lC = "";
    private static String[] lD = new String[]{"com.amap.api.location", "com.autonavi.aps.amapapi"};
    public static String lE = null;
    public static String lF = null;
    public static String lG = null;
    public static String lH = "";
    public static String lI = "";
    public static String lJ = "";
    public static String lK = "";
    public static String lL = "";
    public static int lM = 30000;
    public static String lN = null;
    public static String lO = null;
    public static int lP = 20;
    public static boolean lQ = true;
    public static int lR = 1;
    static String lS;
    static HashMap lT;
    public static final SparseArray lU = new SparseArray();

    static {
        SparseArray sparseArray = lU;
        String str = "UNKWN";
        sparseArray = lU;
        str = "GPRS";
        sparseArray = lU;
        str = "EDGE";
        sparseArray = lU;
        String str2 = "UMTS";
        sparseArray = lU;
        str2 = "CDMA";
        sparseArray = lU;
        str2 = "EVDO_0";
        sparseArray = lU;
        str2 = "EVDO_A";
        sparseArray = lU;
        str2 = "1xRTT";
        sparseArray = lU;
        str2 = "HSDPA";
        sparseArray = lU;
        str2 = "HSUPA";
        sparseArray = lU;
        str2 = "HSPA";
        sparseArray = lU;
        str2 = "IDEN";
        sparseArray = lU;
        str2 = "EVDO_B";
        sparseArray = lU;
        str2 = "LTE";
        sparseArray = lU;
        str2 = "EHRPD";
        sparseArray = lU;
        str2 = "HSPAP";
    }

    public static String mP() {
        return lB;
    }

    public static String[] mQ() {
        return (String[]) lD.clone();
    }

    public static boolean mR(double d, double d2) {
        boolean z = false;
        int i = (int) ((d2 - 73.0d) / 0.5d);
        int i2 = (int) ((d - 3.5d) / 0.5d);
        if (i2 < 0 || i2 >= 101 || i < 0 || i >= 124) {
            return false;
        }
        try {
            if ("00000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001100000001011000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011101010111100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000110111111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111101111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001000110111100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011010111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001110011100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000110000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001010011100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111100110001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011111000111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111110011000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111000000000111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111100000000000010111110100000011000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111110000000001111111111111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111111111000000111111111111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111101111111111111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111111111111111111111111111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000101111111111111111111111111111111111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111111111111111111111111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111111111111111111111111111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000111111111111111111111111111111111111111111111000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111111111111111111111111111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111111111111111111111111111111111111100000000000000000000000000000000000000000000000000000000000000000011110000000001111111111111111111111111111111111111111111110000000000000000000000000000000000000000000000000000000000011000011111100000000111111111111111111111111111111111111111111111100000000000000000000000000000000000000000000000000001111111100111111111100110111111111111111111111111111111111111111111111110000000000000000000000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000000000000000101111111111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111011111000000000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100100000000000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100011100000000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000111110000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110011111110000000000000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110111111110000000000000000000000111011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100000000000000000000000011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000011111111111111111111111111111111111111111111111100001111111111111111111111111111111111111111111111111010000000000000000000000111111111111111111111111111111111111111111110000000000000001111111111111111111111111111111111111111111100000000000000000000011111111111111111111111111111111100000000000000000000000000001111111111111111111111111111111111111111110000000000000000000001111111111111111111111111111111100000000000000000000000000000001111111111111111111111111111111111111111000000000000000000000111111111111111111111111111111110000000000000000000000000000001111111111111111111111111111111111111111100000000000000000000111111111111111111111111111111000000000000000000000000000000000111111111111111111111111111111111111111111000000000000000000001111111111111111111111111110000000000000000000000000000000000001110011111111111111111111111111111111111111100000000000000000000011111111111111111100000000000000000000000000000000000000000000000001111111111111111111111111111111111111000000000000000000001111111111111111111000000000000000000000000000000000000000000000000011111111111111111111111111111111111100000000000000000000011111111111111111100000000000000000000000000000000000000000000000000011111111111111111111111111111111111000000000000000000001111111111111111100000000000000000000000000000000000000000000000000000000111111111111111111111111111111110000000000000000000000000111111111100000000000000000000000000000000000000000000000000111111111111111111111111111111111111111000000000000000000000000011111111100000000000000000000000000000000000000000000000000011111111111111111111111111111110001111100000000000000000000000000111110000000000000000000000000000000000000000000000000000001111111111111111111111111111111000000000000000000000000000000000001110000000000000000000000000000000000000000000000000000000011111111111111111111111111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111111111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111111111111111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010110000000000000000000000".charAt(i + (i2 * 124)) == '1') {
                z = true;
            }
            return z;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public static AMapLocation mS(AmapLoc amapLoc) {
        AMapLocation aMapLocation = new AMapLocation("");
        aMapLocation.setLatitude(amapLoc.yJ());
        aMapLocation.setLongitude(amapLoc.yG());
        aMapLocation.setAccuracy(amapLoc.yN());
        aMapLocation.setTime(amapLoc.yU());
        aMapLocation.hG(amapLoc.zi());
        aMapLocation.hH(amapLoc.zq());
        aMapLocation.hD(amapLoc.yw());
        aMapLocation.hF(amapLoc.yA());
        aMapLocation.hC(amapLoc.yC());
        aMapLocation.hA(amapLoc.yx());
        aMapLocation.hO(amapLoc.zt());
        Bundle bundle = new Bundle();
        bundle.putString("citycode", amapLoc.zc());
        bundle.putString("desc", amapLoc.ze());
        bundle.putString("adcode", amapLoc.zg());
        aMapLocation.setExtras(bundle);
        String zc = amapLoc.zc();
        String ze = amapLoc.ze();
        String zg = amapLoc.zg();
        aMapLocation.hM(zc);
        aMapLocation.hN(zg);
        if (zg != null && zg.trim().length() > 0) {
            aMapLocation.hI(ze.replace(" ", ""));
        } else {
            aMapLocation.hI(ze);
        }
        aMapLocation.hK(amapLoc.zm());
        aMapLocation.hL(amapLoc.zo());
        aMapLocation.hJ(amapLoc.zk());
        return aMapLocation;
    }

    public static void mT(Context context) {
        if (bq.vD(mU(context))) {
            lB = "http://abroad.apilocate.amap.com/mobile/binary";
            return;
        }
        mV();
        lS = bQ.yq(Y.nN(context));
        mW(lS);
    }

    public static String mU(Context context) {
        String str = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return str;
            }
            String networkOperator = telephonyManager.getNetworkOperator();
            networkOperator = (networkOperator != null && networkOperator.length() >= 3) ? networkOperator.substring(0, 3) : str;
            return networkOperator;
        } catch (Throwable th) {
            th.printStackTrace();
            return str;
        }
    }

    private static void mV() {
        try {
            if (lT == null) {
                lT = new HashMap();
            }
            lT.clear();
            lT.put("a9a9d23668a1a7ea93de9b21d67e436a", "F13160D440C7D0229DA95450F66AF92154AC84DF088F8CA3100B2E8131D57F3DC67124D4C466056E7A3DFBE035E1B9A4B9DA4DB68AE65A43EDFD92F5C60EF0C9");
            lT.put("fe643c382e5c3b3962141f1a2e815a78", "FB923EE67A8B4032DAA517DD8CD7A26FF7C25B0C3663F92A0B61251C4FFFA858DF169D61321C3E7919CB67DF8EFEC827");
            lT.put("b2e8bd171989cb2c3c13bd89b4c1067a", "239CE372F804D4BE4EAFFD183668379BDF274440E6F246AB16BBE6F5D1D30DEACFBBF0C942485727FF12288228760A9E");
            lT.put("9a571aa113ad987d626c0457828962e6", "D2FF99A88BEB04683D89470D4FA72B1749DA456AB0D0F1A476477CE5A6874F53A9106423D905F9D808C0FCE8E7F1E04AC642F01FE41D0C7D933971F45CBA72B7");
            lT.put("668319f11506def6208d6afe320dfd52", "53E53D46011A6BBAEA4FAE5442E659E0577CDD336F930C28635C322FB3F51C3C63F7FBAC9EAE448DFA2E5E5D716C4807");
            lT.put("256b0f26bb2a9506be6cfdb84028ae08", "AF2228680EDC323FBA035362EB7E1E38A0C33E1CF6F6FB805EE553A230CBA754CD9552EB9B546542CBE619E8293151BE");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void mW(String str) {
        if (str != null) {
            try {
                if (str.length() != 0 && lT != null && lT.containsKey(str)) {
                    String str2 = (String) lT.get(str);
                    str2 = (str2 != null && str2.length() > 0) ? new String(bQ.yo(mX(str2), str), "utf-8") : null;
                    if (str2 != null) {
                        if (str2.length() > 0 && str2.contains("http:")) {
                            lB = str2;
                        }
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static byte[] mX(String str) {
        int i = 0;
        if (str == null || str.length() < 2) {
            return new byte[0];
        }
        String toLowerCase = str.toLowerCase(Locale.US);
        int length = toLowerCase.length() / 2;
        byte[] bArr = new byte[length];
        while (i < length) {
            bArr[i] = (byte) ((byte) (Integer.parseInt(toLowerCase.substring(i * 2, (i * 2) + 2), 16) & 255));
            i++;
        }
        return bArr;
    }

    public static x mY(String str) {
        try {
            return new u("loc", str, "AMAP_Location_SDK_Android 2.2.0").mm(mQ()).mn();
        } catch (i e) {
            e.printStackTrace();
            return null;
        }
    }
}