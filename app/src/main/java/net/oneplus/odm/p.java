package net.oneplus.odm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class p extends BroadcastReceiver {
    final /* synthetic */ a cE;

    p(a aVar) {
        this.cE = aVar;
    }

    public void onReceive(Context context, Intent intent) {
        this.cE.bP((DeviceManagerJob) intent.getParcelableExtra("stop_job_extra"));
    }
}
