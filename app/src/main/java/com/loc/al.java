package com.loc;

import android.text.TextUtils;
import java.util.Hashtable;
import java.util.Locale;

public class al {
    private static final char[] mZ = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int[] na = new int[]{16, 8, 4, 2, 1};

    public static final String ov(double d, double d2) {
        return ow(d, d2, 6);
    }

    public static final String ow(double d, double d2, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        int i2 = 0;
        int i3 = 0;
        double[] dArr = new double[]{-90.0d, 90.0d};
        double[] dArr2 = new double[]{-180.0d, 180.0d};
        while (stringBuilder.length() < i) {
            double d3;
            if (obj == null) {
                d3 = (dArr[0] + dArr[1]) / 2.0d;
                if (d > d3) {
                    i3 |= na[i2];
                    dArr[0] = d3;
                } else {
                    dArr[1] = d3;
                }
            } else {
                d3 = (dArr2[0] + dArr2[1]) / 2.0d;
                if (d2 > d3) {
                    i3 |= na[i2];
                    dArr2[0] = d3;
                } else {
                    dArr2[1] = d3;
                }
            }
            obj = obj != null ? null : 1;
            if (i2 >= 4) {
                stringBuilder.append(mZ[i3]);
                i2 = 0;
                i3 = 0;
            } else {
                i2++;
            }
        }
        return stringBuilder.toString();
    }

    public static final String[] ox(String str) {
        return new String[]{oy(str, "right"), oy(str, "btm"), oy(str, "left"), oy(str, "top"), oy(r0[0], "top"), oy(r0[0], "btm"), oy(r0[2], "top"), oy(r0[2], "btm"), oy(r0[0], "right"), oy(r0[8], "top"), oy(r0[9], "top"), oy(r0[10], "left"), oy(r0[11], "left"), oy(r0[12], "left"), oy(r0[13], "left"), oy(r0[14], "btm"), oy(r0[15], "btm"), oy(r0[16], "btm"), oy(r0[17], "btm"), oy(r0[18], "right"), oy(r0[19], "right"), oy(r0[20], "right"), oy(r0[21], "right"), oy(r0[22], "top")};
    }

    private static final String oy(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Object obj;
        String toLowerCase = str.toLowerCase(Locale.US);
        char charAt = toLowerCase.charAt(toLowerCase.length() - 1);
        if (toLowerCase.length() % 2 != 0) {
            obj = "even";
        } else {
            String str3 = "odd";
        }
        toLowerCase = toLowerCase.substring(0, toLowerCase.length() - 1);
        if (!(((String) ((Hashtable) aM.pJ.get(str2)).get(obj)).indexOf(charAt) == -1 || TextUtils.isEmpty(toLowerCase))) {
            toLowerCase = oy(toLowerCase, str2);
        }
        return toLowerCase + mZ[((String) ((Hashtable) K.lY.get(str2)).get(obj)).indexOf(charAt)];
    }
}
