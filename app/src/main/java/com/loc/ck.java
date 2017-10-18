package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: Unknown */
final class ck extends BroadcastReceiver {
    final /* synthetic */ cg a;

    private ck(cg cgVar) {
        this.a = cgVar;
    }

    public final void onReceive(Context context, Intent intent) {
        if (!(context == null || intent == null)) {
            try {
                if (!(this.a.g == null || this.a.D == null || this.a.C == null || intent.getAction() == null || !"android.net.wifi.SCAN_RESULTS".equals(intent.getAction()))) {
                    List scanResults = this.a.g.getScanResults();
                    synchronized (this) {
                        this.a.C.clear();
                        this.a.v = System.currentTimeMillis();
                        if (scanResults != null) {
                            if (scanResults.size() > 0) {
                                for (int i = 0; i < scanResults.size(); i++) {
                                    this.a.C.add((ScanResult) scanResults.get(i));
                                }
                            }
                        }
                    }
                    TimerTask clVar = new cl(this);
                    synchronized (this) {
                        if (this.a.D != null) {
                            this.a.D.cancel();
                            this.a.D = null;
                        }
                        this.a.D = new Timer();
                        this.a.D.schedule(clVar, (long) cg.G);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
