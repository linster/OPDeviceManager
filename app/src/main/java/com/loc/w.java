package com.loc;

import android.os.Looper;
import java.util.Timer;

/* compiled from: Unknown */
final class w extends Thread {
    private /* synthetic */ bf lg;

    w(bf bfVar, String str) {
        this.lg = bfVar;
        super(str);
    }

    public final void run() {
        try {
            Looper.prepare();
            this.lg.sh = Looper.myLooper();
            this.lg.sf = new Timer();
            this.lg.sa = new at(this.lg);
            bf.uD(this.lg, this.lg.sa);
            this.lg.sb = new X(this.lg);
            try {
                bf.uG(this.lg, this.lg.sb);
            } catch (Exception e) {
            }
            Looper.loop();
        } catch (Exception e2) {
        }
    }
}
