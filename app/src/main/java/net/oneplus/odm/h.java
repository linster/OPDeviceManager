package net.oneplus.odm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

class h extends BroadcastReceiver {
    final /* synthetic */ DeviceManagerService ck;

    private h(DeviceManagerService deviceManagerService) {
        this.ck = deviceManagerService;
    }

    public void onReceive(Context context, Intent intent) {
        Log.v(DeviceManagerService.bT, "job receive");
        DeviceManagerJob deviceManagerJob = (DeviceManagerJob) intent.getParcelableExtra("intent_help_job_schedule_payload");
        if (deviceManagerJob != null) {
            this.ck.cb.bM(deviceManagerJob);
        }
    }
}
