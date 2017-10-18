package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.lang.Thread.UncaughtExceptionHandler;

public class R implements UncaughtExceptionHandler {
    private static R mk;
    private UncaughtExceptionHandler ml = Thread.getDefaultUncaughtExceptionHandler();
    private Context mm;
    private x mn;

    private R(Context context, x xVar) {
        this.mm = context.getApplicationContext();
        this.mn = xVar;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    static synchronized R nt(Context context, x xVar) {
        R r;
        synchronized (R.class) {
            if (mk == null) {
                mk = new R(context, xVar);
            }
            r = mk;
        }
        return r;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String xa = bw.xa(th);
        try {
            if (!TextUtils.isEmpty(xa)) {
                if (xa.contains("amapdynamic") && xa.contains("com.amap.api")) {
                    k.lF(new ae(this.mm, v.mu()), this.mm, this.mn);
                }
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        if (this.ml != null) {
            this.ml.uncaughtException(thread, th);
        }
    }
}
