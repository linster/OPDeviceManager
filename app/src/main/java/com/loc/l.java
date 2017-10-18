package com.loc;

import android.os.Looper;
import java.util.List;

/* compiled from: Unknown */
final class l extends Thread {
    final /* synthetic */ aF kL;

    l(aF aFVar, String str) {
        this.kL = aFVar;
        super(str);
    }

    public final void run() {
        try {
            Looper.prepare();
            this.kL.oZ = Looper.myLooper();
            this.kL.pa = new bp(this.kL);
            try {
                this.kL.oQ.addGpsStatusListener(this.kL.pa);
                this.kL.oQ.addNmeaListener(this.kL.pa);
            } catch (Exception e) {
            }
            this.kL.pb = new aY(this);
            List allProviders = this.kL.oQ.getAllProviders();
            if (allProviders != null && allProviders.contains("gps")) {
                allProviders.contains("passive");
            }
            try {
                this.kL.oQ.requestLocationUpdates("passive", 1000, (float) aF.c, this.kL.pd);
            } catch (Exception e2) {
            }
            Looper.loop();
        } catch (Exception e3) {
        }
    }
}
