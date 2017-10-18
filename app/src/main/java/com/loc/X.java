package com.loc;

import android.location.GpsStatus.NmeaListener;

/* compiled from: Unknown */
final class X implements NmeaListener {
    private /* synthetic */ bf mz;

    private X(bf bfVar) {
        this.mz = bfVar;
    }

    public final void onNmeaReceived(long j, String str) {
        try {
            this.mz.rQ = j;
            this.mz.rR = str;
        } catch (Exception e) {
        }
    }
}
