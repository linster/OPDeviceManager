package com.loc;

import android.os.Looper;
import java.util.Timer;

/* compiled from: Unknown */
final class ch extends Thread {
    private /* synthetic */ cg a;

    ch(cg cgVar, String str) {
        this.a = cgVar;
        super(str);
    }

    public final void run() {
        try {
            Looper.prepare();
            this.a.F = Looper.myLooper();
            this.a.D = new Timer();
            this.a.y = new ci((byte) 0);
            cg.a(this.a, this.a.y);
            this.a.z = new cj((byte) 0);
            try {
                cg.a(this.a, this.a.z);
            } catch (Exception e) {
            }
            Looper.loop();
        } catch (Exception e2) {
        }
    }
}
