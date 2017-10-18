package net.oneplus.odm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import net.oneplus.odm.common.a;

class j extends BroadcastReceiver {
    final /* synthetic */ DeviceManagerService cm;

    private j(DeviceManagerService deviceManagerService) {
        this.cm = deviceManagerService;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra("intent_local_schedule_payload").equals("task_grab_local_deviceinfo")) {
            if (a.an()) {
                if (System.currentTimeMillis() - this.cm.cc.bW("grab_location_timestamp", 0) > 86400000) {
                    this.cm.cf.x();
                } else {
                    this.cm.bX.F(this.cm.cv(this.cm.cx()).toString());
                }
            }
            if (a.ao()) {
                this.cm.bX.F(this.cm.cv(this.cm.cx()).toString());
            }
        }
    }
}
