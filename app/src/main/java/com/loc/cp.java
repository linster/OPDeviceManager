package com.loc;

import android.os.Looper;

import java.util.List;

/* compiled from: Unknown */
final class cp extends Thread {
    final /* synthetic */ bw a;

    cp(bw bwVar, String str) {
        this.a = bwVar;
        super(str);
    }

    public final void run() {
        try {
            Looper.prepare();
            this.a.y = Looper.myLooper();
            this.a.z = new cr(this.a);
            try {
                this.a.p.addGpsStatusListener(this.a.z);
                this.a.p.addNmeaListener(this.a.z);
            } catch (Exception e) {
            }
            this.a.A = new cq(this);
            List allProviders = this.a.p.getAllProviders();
            if (allProviders != null && allProviders.contains("gps")) {
                allProviders.contains("passive");
            }
            try {
                this.a.p.requestLocationUpdates("passive", 1000, (float) bw.c, this.a.C);
            } catch (Exception e2) {
            }
            Looper.loop();
        } catch (Exception e3) {
        }
    }
}
