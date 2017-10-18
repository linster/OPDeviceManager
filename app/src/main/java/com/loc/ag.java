package com.loc;

import android.content.Context;
import android.text.TextUtils;

import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: DynamicExceptionHandler */
public class ag implements UncaughtExceptionHandler {
    private static ag a;
    private UncaughtExceptionHandler b;
    private Context c;
    private s d;

    private ag(Context context, s sVar) {
        this.c = context.getApplicationContext();
        this.d = sVar;
        this.b = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    static synchronized ag a(Context context, s sVar) {
        ag agVar;
        synchronized (ag.class) {
            if (a == null) {
                a = new ag(context, sVar);
            }
            agVar = a;
        }
        return agVar;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String a = t.a(th);
        try {
            if (!TextUtils.isEmpty(a)) {
                if (a.contains("amapdynamic") && a.contains("com.amap.api")) {
                    ae.a(new x(this.c, ah.c()), this.c, this.d);
                }
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        if (this.b != null) {
            this.b.uncaughtException(thread, th);
        }
    }
}
