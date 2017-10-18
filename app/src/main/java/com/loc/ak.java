package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: Unknown */
final class ak extends BroadcastReceiver {
    ak(aF aFVar) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction().equals("android.location.GPS_FIX_CHANGE")) {
                    aF.oF = false;
                }
            } catch (Exception e) {
            }
        }
    }
}
