package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: Unknown */
final class cn extends BroadcastReceiver {
    cn(bw bwVar) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                String action = intent.getAction();
                if (action.equals("android.intent.action.MEDIA_MOUNTED")) {
                    cg.c = false;
                }
                if (action.equals("android.intent.action.MEDIA_UNMOUNTED")) {
                    cg.c = true;
                }
                if (action.equals("android.intent.action.MEDIA_EJECT")) {
                    cg.c = true;
                }
            } catch (Exception e) {
            }
        }
    }
}
