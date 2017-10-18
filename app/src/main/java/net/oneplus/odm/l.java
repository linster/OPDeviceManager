package net.oneplus.odm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

class l extends BroadcastReceiver {
    final /* synthetic */ DeviceManagerService co;

    private l(DeviceManagerService deviceManagerService) {
        this.co = deviceManagerService;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("intent_send_deviceinfo")) {
            if (this.co.cf != null && !this.co.cc.bV("send_deviceinfo_immediately", false)) {
                Handler handler = new Handler();
                this.co.cc.bY("send_deviceinfo_immediately", true);
                handler.postDelayed(this.co.bU, 10000);
            }
        } else if (!intent.getAction().equals("net.oneplus.odm.test.debugmode")) {
        } else {
            if (intent.getBooleanExtra("mdm_debug_mode", false)) {
                Log.v(DeviceManagerService.bT, "OPEN MDM DEBUG MODE");
                this.co.cc.bY("debug_mode", true);
                return;
            }
            Log.v(DeviceManagerService.bT, "CLOSE MDM DEBUG MODE");
            this.co.cc.bY("debug_mode", false);
        }
    }
}
