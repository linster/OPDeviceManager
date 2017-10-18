package net.oneplus.odm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class i extends BroadcastReceiver {
    final /* synthetic */ DeviceManagerService cl;

    private i(DeviceManagerService deviceManagerService) {
        this.cl = deviceManagerService;
    }

    public void onReceive(Context context, Intent intent) {
        DeviceManagerJob deviceManagerJob = (DeviceManagerJob) intent.getParcelableExtra("intent_help_job_schedule_payload");
        if (deviceManagerJob != null) {
            this.cl.cb.bP(deviceManagerJob);
        }
    }
}
