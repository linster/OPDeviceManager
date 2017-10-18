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

public class DeviceManagerScheduler {
    private static String TAG;
    private static AlarmManager mAlarmManager;
    private static Context mContext;
    private static DeviceManagerScheduler mDeviceManagerScheduler;
    private int mPowerSaving;
    private LinkedList<DeviceManagerJob> mScheduleList;
    private boolean mSchedulerStatus;
    private BroadcastReceiver mStopJobReceiver;

    static {
        TAG = "DeviceManagerScheduler";
    }

    private DeviceManagerScheduler(Context context) {
        this.mSchedulerStatus = false;
        this.mPowerSaving = 1;
        this.mScheduleList = new LinkedList();
        this.mStopJobReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                DeviceManagerScheduler.this.removeJob((DeviceManagerJob) intent.getParcelableExtra("stop_job_extra"));
            }
        };
        mContext = context;
        mContext.registerReceiver(this.mStopJobReceiver, new IntentFilter("stop_job_action"));
    }

    protected void finalize() {
        if (mContext != null) {
            mContext.unregisterReceiver(this.mStopJobReceiver);
        }
    }

    public static DeviceManagerScheduler getInstance(Context context) {
        if (mDeviceManagerScheduler == null) {
            mDeviceManagerScheduler = new DeviceManagerScheduler(context);
            mAlarmManager = (AlarmManager) mContext.getSystemService("alarm");
        }
        return mDeviceManagerScheduler;
    }

    public boolean addJob(DeviceManagerJob job) {
        if (job.getOperation() == null) {
            Log.e(TAG, "Add job error. Job's operation is null.");
            return false;
        } else if (job.getType() < 0 || job.getType() > 3) {
            Log.e(TAG, "Add job error. Job's type is wrong.");
            return false;
        } else if (job.getInterval() < 0) {
            Log.e(TAG, "Add job error. Job's interval is smaller than 0");
            return false;
        } else {
            if (this.mScheduleList != null) {
                this.mScheduleList.add(job);
            } else {
                this.mScheduleList = new LinkedList();
                this.mScheduleList.add(job);
            }
            if (this.mSchedulerStatus) {
                reserveAlarmJob(job, this.mPowerSaving);
                if (job.getPeriodTime() > 0) {
                    reserveStopAlarmJob(job);
                }
            }
            Log.v(TAG, "Add job: " + job.getLabel() + " in scheduler.");
            return true;
        }
    }

    public void removeJob(DeviceManagerJob job) {
        cancelAlarmJob(job);
        this.mScheduleList.remove(job);
        Log.v(TAG, "Remove job: " + job.getLabel() + " in scheduler.");
    }

    void startScheduler(int powerSaving) {
        Log.v(TAG, "Start Scheduler.");
        this.mPowerSaving = powerSaving;
        for (DeviceManagerJob schedule : this.mScheduleList) {
            if (!schedule.getActivatedStatus()) {
                reserveAlarmJob(schedule, powerSaving);
                schedule.setActivated(true);
            }
        }
        this.mSchedulerStatus = true;
    }

    private void reserveAlarmJob(DeviceManagerJob job, int powerSaving) {
        if (powerSaving == 1) {
            mAlarmManager.setInexactRepeating(job.getType(), SystemClock.elapsedRealtime() + 10000, job.getInterval(), job.getOperation());
            Log.v(TAG, "Job: " + job.getLabel() + " have been fired inexactly on time. Schedule information: interval:" + job.getInterval() + "type:" + job.getType() + " operation:" + job.getOperation());
            return;
        }
        mAlarmManager.setRepeating(job.getType(), SystemClock.elapsedRealtime() + 10000, job.getInterval(), job.getOperation());
        Log.v(TAG, "Job: " + job.getLabel() + " have been fired exactly on time. Schedule information: interval:" + job.getInterval() + " type:" + job.getType() + " operation:" + job.getOperation());
    }

    private void reserveStopAlarmJob(DeviceManagerJob job) {
        Intent stopJobIntent = new Intent("stop_job_action");
        stopJobIntent.putExtra("stop_job_extra", job);
        mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + job.getPeriodTime(), PendingIntent.getBroadcast(mContext, 0, stopJobIntent, 0));
    }

    private void cancelAlarmJob(DeviceManagerJob job) {
        mAlarmManager.cancel(job.getOperation());
    }
}
