package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: Unknown */
final class Q extends BroadcastReceiver {
    Q(aF aFVar) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                String action = intent.getAction();
                if (action.equals("android.intent.action.MEDIA_MOUNTED")) {
                    bf.sk = false;
                }
                if (action.equals("android.intent.action.MEDIA_UNMOUNTED")) {
                    bf.sk = true;
                }
                if (action.equals("android.intent.action.MEDIA_EJECT")) {
                    bf.sk = true;
                }
            } catch (Exception e) {
            }
        }
    }
}
