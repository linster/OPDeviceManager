package com.amap.api.location;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Looper;
import android.os.Message;
import com.loc.H;
import com.loc.aB;
import com.loc.bB;

public class b implements c {
    d ih;
    Context ii;
    c ij;
    e ik;
    a il;
    b im;
    double in;
    double io;
    float ip;
    String iq;
    PendingIntent ir;
    long j;

    public b(Context context) {
        if (context != null) {
            try {
                this.ii = context.getApplicationContext();
                this.im = new b(this.ii, true);
                if (Looper.myLooper() != null) {
                    this.ih = new d(this.im);
                    return;
                } else {
                    this.ih = new d(this.im, this.ii.getMainLooper());
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        throw new IllegalArgumentException("Context参数不能为null");
    }

    private b(Context context, boolean z) {
        try {
            this.ii = context;
            Context context2 = context;
            this.ij = (c) bB.xi(context2, H.mY("2.2.0"), "com.amap.api.location.LocationManagerWrapper", aB.class, new Class[]{Context.class}, new Object[]{context});
        } catch (Throwable th) {
            this.ij = new aB(context);
        }
    }

    public void hR(e eVar) {
        if (eVar != null) {
            try {
                Message obtain = Message.obtain();
                obtain.arg1 = 1;
                obtain.obj = eVar;
                this.ih.sendMessage(obtain);
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        throw new IllegalArgumentException("LocationManagerOption参数不能为null");
    }

    public void hS(a aVar) {
        if (aVar != null) {
            try {
                Message obtain = Message.obtain();
                obtain.arg1 = 2;
                obtain.obj = aVar;
                this.ih.sendMessage(obtain);
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        throw new IllegalArgumentException("listener参数不能为null");
    }

    public void hT() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 3;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void hU() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 4;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void hV(String str, double d, double d2, float f, long j, PendingIntent pendingIntent) {
        try {
            Message obtain = Message.obtain();
            this.im.iq = str;
            this.im.in = d;
            this.im.io = d2;
            this.im.ip = f;
            this.im.ir = pendingIntent;
            this.im.j = j;
            obtain.arg1 = 6;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void hW(PendingIntent pendingIntent, String str) {
        try {
            Message obtain = Message.obtain();
            this.im.iq = str;
            obtain.obj = pendingIntent;
            obtain.arg1 = 10;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void hX(PendingIntent pendingIntent) {
        try {
            Message obtain = Message.obtain();
            obtain.obj = pendingIntent;
            obtain.arg1 = 7;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void hY() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 8;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void hZ() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 9;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void ia(a aVar) {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 5;
            obtain.obj = aVar;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void onDestroy() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 11;
            this.ih.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
