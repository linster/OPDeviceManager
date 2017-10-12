package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: Unknown */
final class co extends BroadcastReceiver {
    co(bw bwVar) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction().equals("android.location.GPS_FIX_CHANGE")) {
                    bw.b = false;
                }
            } catch (Exception e) {
            }
        }
    }
}
