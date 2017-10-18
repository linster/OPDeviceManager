package net.oneplus.odm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;
import java.util.LinkedList;

public class a {
    private static String bg = "DeviceManagerScheduler";
    private static AlarmManager bh;
    private static Context bi;
    private static a bj;
    private int bk = 1;
    private LinkedList bl = new LinkedList();
    private boolean bm = false;
    private BroadcastReceiver bn = new p(this);

    private a(Context context) {
        bi = context;
        bi.registerReceiver(this.bn, new IntentFilter("stop_job_action"));
    }

    private void bN(DeviceManagerJob deviceManagerJob) {
        bh.cancel(deviceManagerJob.ca());
    }

    public static a bO(Context context) {
        if (bj == null) {
            bj = new a(context);
            bh = (AlarmManager) bi.getSystemService("alarm");
        }
        return bj;
    }

    private void bQ(DeviceManagerJob deviceManagerJob, int i) {
        if (i == 1) {
            bh.setInexactRepeating(deviceManagerJob.cb(), SystemClock.elapsedRealtime() + 10000, deviceManagerJob.cc(), deviceManagerJob.ca());
            Log.v(bg, "Job: " + deviceManagerJob.ce() + " have been fired inexactly on time. Schedule information: interval:" + deviceManagerJob.cc() + "type:" + deviceManagerJob.cb() + " operation:" + deviceManagerJob.ca());
            return;
        }
        bh.setRepeating(deviceManagerJob.cb(), SystemClock.elapsedRealtime() + 10000, deviceManagerJob.cc(), deviceManagerJob.ca());
        Log.v(bg, "Job: " + deviceManagerJob.ce() + " have been fired exactly on time. Schedule information: interval:" + deviceManagerJob.cc() + " type:" + deviceManagerJob.cb() + " operation:" + deviceManagerJob.ca());
    }

    private void bR(DeviceManagerJob deviceManagerJob) {
        Intent intent = new Intent("stop_job_action");
        intent.putExtra("stop_job_extra", deviceManagerJob);
        bh.setExact(2, SystemClock.elapsedRealtime() + deviceManagerJob.cd(), PendingIntent.getBroadcast(bi, 0, intent, 0));
    }

    public boolean bM(DeviceManagerJob deviceManagerJob) {
        if (deviceManagerJob.ca() == null) {
            Log.e(bg, "Add job error. Job's operation is null.");
            return false;
        } else if (deviceManagerJob.cb() < 0 || deviceManagerJob.cb() > 3) {
            Log.e(bg, "Add job error. Job's type is wrong.");
            return false;
        } else if (deviceManagerJob.cc() < 0) {
            Log.e(bg, "Add job error. Job's interval is smaller than 0");
            return false;
        } else {
            if (this.bl != null) {
                this.bl.add(deviceManagerJob);
            } else {
                this.bl = new LinkedList();
                this.bl.add(deviceManagerJob);
            }
            if (this.bm) {
                bQ(deviceManagerJob, this.bk);
                if (deviceManagerJob.cd() > 0) {
                    bR(deviceManagerJob);
                }
            }
            Log.v(bg, "Add job: " + deviceManagerJob.ce() + " in scheduler.");
            return true;
        }
    }

    public void bP(DeviceManagerJob deviceManagerJob) {
        bN(deviceManagerJob);
        this.bl.remove(deviceManagerJob);
        Log.v(bg, "Remove job: " + deviceManagerJob.ce() + " in scheduler.");
    }

    void bS(int i) {
        Log.v(bg, "Start Scheduler.");
        this.bk = i;
        for (DeviceManagerJob deviceManagerJob : this.bl) {
            if (!deviceManagerJob.cf()) {
                bQ(deviceManagerJob, i);
                deviceManagerJob.cg(true);
            }
        }
        this.bm = true;
    }

    protected void finalize() {
        if (bi != null) {
            bi.unregisterReceiver(this.bn);
        }
    }
}
