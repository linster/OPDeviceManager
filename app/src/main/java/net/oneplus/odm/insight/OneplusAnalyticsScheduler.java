package net.oneplus.odm.insight;

import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import net.oneplus.odm.common.Def;

public class OneplusAnalyticsScheduler {
    private static OneplusAnalyticsScheduler mOneplusAnalyticsScheduler;
    private Context mContext;
    private Handler mHandler;
    private JobScheduler mJobScheduler;
    private Builder mLogParserJobBuilder;
    Runnable mLogParserJobTask;
    private ComponentName mLogParserServiceComponent;
    private Builder mOAJobActiveBuilder;
    private Builder mOAJobBuilder;
    private ComponentName mServiceComponent;
    private boolean mStatus;

    static {
        mOneplusAnalyticsScheduler = null;
    }

    private OneplusAnalyticsScheduler(Context context) {
        this.mStatus = false;
        this.mLogParserJobTask = new Runnable() {
            public void run() {
                OneplusAnalyticsScheduler.this.mJobScheduler.schedule(OneplusAnalyticsScheduler.this.mLogParserJobBuilder.setPeriodic(7200000).build());
            }
        };
        this.mContext = context;
        this.mHandler = new Handler();
        this.mJobScheduler = (JobScheduler) this.mContext.getSystemService("jobscheduler");
        this.mServiceComponent = new ComponentName(context, MDMJobService.class);
        this.mOAJobBuilder = new Builder(102, this.mServiceComponent);
        this.mOAJobActiveBuilder = new Builder(104, this.mServiceComponent);
        this.mLogParserServiceComponent = new ComponentName(context, LogParserJobService.class);
        this.mLogParserJobBuilder = new Builder(103, this.mLogParserServiceComponent);
        this.mContext.startService(new Intent(context, MDMJobService.class));
    }

    public static OneplusAnalyticsScheduler getInstance(Context context) {
        if (mOneplusAnalyticsScheduler == null) {
            mOneplusAnalyticsScheduler = new OneplusAnalyticsScheduler(context);
        }
        return mOneplusAnalyticsScheduler;
    }

    public void start() {
        this.mJobScheduler.schedule(this.mOAJobBuilder.setPeriodic(Def.UPLOAD_JOB_INTERVAL).setRequiresCharging(false).build());
        this.mHandler.postDelayed(this.mLogParserJobTask, 300000);
        this.mStatus = true;
    }

    public void stop() {
        this.mJobScheduler.cancel(102);
        this.mStatus = false;
    }
}
