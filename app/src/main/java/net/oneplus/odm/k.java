package net.oneplus.odm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class k extends BroadcastReceiver {
    final /* synthetic */ DeviceManagerService cn;

    private k(DeviceManagerService deviceManagerService) {
        this.cn = deviceManagerService;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
            this.cn.cg.onEvent("screen_on", null);
        } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
            this.cn.cg.onEvent("screen_off", null);
        } else if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {
            this.cn.cg.onEvent("unlock", null);
        }
    }
}
