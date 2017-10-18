package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: Unknown */
final class F extends BroadcastReceiver {
    final /* synthetic */ bf lx;

    private F(bf bfVar) {
        this.lx = bfVar;
    }

    public final void onReceive(Context context, Intent intent) {
        if (!(context == null || intent == null)) {
            try {
                if (!(this.lx.rI == null || this.lx.sf == null || this.lx.se == null || intent.getAction() == null || !"android.net.wifi.SCAN_RESULTS".equals(intent.getAction()))) {
                    List scanResults = this.lx.rI.getScanResults();
                    synchronized (this) {
                        this.lx.se.clear();
                        this.lx.rX = System.currentTimeMillis();
                        if (scanResults != null) {
                            if (scanResults.size() > 0) {
                                for (int i = 0; i < scanResults.size(); i++) {
                                    this.lx.se.add((ScanResult) scanResults.get(i));
                                }
                            }
                        }
                    }
                    TimerTask aAVar = new aA(this);
                    synchronized (this) {
                        if (this.lx.sf != null) {
                            this.lx.sf.cancel();
                            this.lx.sf = null;
                        }
                        this.lx.sf = new Timer();
                        this.lx.sf.schedule(aAVar, (long) bf.si);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
