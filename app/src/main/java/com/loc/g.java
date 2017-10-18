package com.loc;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.location.CoordUtil;
import com.amap.api.location.DPoint;

import java.io.File;

/* compiled from: OffsetUtil */
public class g {
    static double a;
    private static boolean b;

    static {
        b = false;
        a = 3.141592653589793d;
    }

    public static DPoint a(Context context, DPoint dPoint) {
        if (context == null) {
            return null;
        }
        String a = r.a(context, "libwgs2gcj.so");
        if (!(TextUtils.isEmpty(a) || !new File(a).exists() || b)) {
            try {
                System.load(a);
                b = true;
            } catch (Throwable th) {
                br.a(th);
            }
        }
        return a(dPoint, b);
    }

    public static DPoint a(Context context, double d, double d2) {
        if (context != null) {
            return a(context, new DPoint(d2, d));
        }
        return null;
    }

    private static DPoint a(DPoint dPoint, boolean z) {
        double[] dArr;
        try {
            dArr = new double[2];
            if (z) {
                if (CoordUtil.convertToGcj(new double[]{dPoint.getLongitude(), dPoint.getLatitude()}, dArr) != 0) {
                    dArr = bs.a(dPoint.getLongitude(), dPoint.getLatitude());
                }
            } else {
                dArr = bs.a(dPoint.getLongitude(), dPoint.getLatitude());
            }
        } catch (Throwable th) {
            return dPoint;
        }
        return new DPoint(dArr[1], dArr[0]);
    }
}
