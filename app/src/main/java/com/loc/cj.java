package com.loc;

import android.location.GpsStatus.NmeaListener;

/* compiled from: Unknown */
final class cj implements NmeaListener {
    private /* synthetic */ cg a;

    private cj(cg cgVar) {
        this.a = cgVar;
    }

    public final void onNmeaReceived(long j, String str) {
        try {
            this.a.o = j;
            this.a.p = str;
        } catch (Exception e) {
        }
    }
}
