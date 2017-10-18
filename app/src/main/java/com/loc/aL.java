package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.location.CoordUtil;
import com.amap.api.location.g;
import java.io.File;

public class aL {
    private static boolean pH = false;
    static double pI = 3.141592653589793d;

    public static g ru(Context context, g gVar) {
        if (context == null) {
            return null;
        }
        String pf = aw.pf(context, "libwgs2gcj.so");
        if (!(TextUtils.isEmpty(pf) || !new File(pf).exists() || pH)) {
            try {
                System.load(pf);
                pH = true;
            } catch (Throwable th) {
                bq.vC(th);
            }
        }
        return rw(gVar, pH);
    }

    public static g rv(Context context, double d, double d2) {
        return context != null ? ru(context, new g(d2, d)) : null;
    }

    private static g rw(g gVar, boolean z) {
        double[] dArr;
        try {
            dArr = new double[2];
            if (z) {
                if (CoordUtil.convertToGcj(new double[]{gVar.ir(), gVar.is()}, dArr) != 0) {
                    dArr = b.iC(gVar.ir(), gVar.is());
                }
            } else {
                dArr = b.iC(gVar.ir(), gVar.is());
            }
        } catch (Throwable th) {
            return gVar;
        }
        return new g(dArr[1], dArr[0]);
    }
}
