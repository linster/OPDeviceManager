package net.oneplus.odm.insight;

import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import net.oneplus.odm.common.c;

public class e {
    private static e aK = null;
    private Context aC;
    private Handler aD;
    private JobScheduler aE;
    private Builder aF;
    Runnable aG = new k(this);
    private ComponentName aH;
    private Builder aI;
    private Builder aJ;
    private ComponentName aL;
    private boolean aM = false;

    private e(Context context) {
        this.aC = context;
        this.aD = new Handler();
        this.aE = (JobScheduler) this.aC.getSystemService("jobscheduler");
        this.aL = new ComponentName(context, MDMJobService.class);
        this.aJ = new Builder(102, this.aL);
        this.aI = new Builder(104, this.aL);
        this.aH = new ComponentName(context, LogParserJobService.class);
        this.aF = new Builder(103, this.aH);
        this.aC.startService(new Intent(context, MDMJobService.class));
    }

    public static e bq(Context context) {
        if (aK == null) {
            aK = new e(context);
        }
        return aK;
    }

    public void br() {
        this.aE.schedule(this.aJ.setPeriodic(c.aa).setRequiresCharging(false).build());
        this.aD.postDelayed(this.aG, 300000);
        this.aM = true;
    }

    public void bs() {
        this.aE.cancel(102);
        this.aM = false;
    }
}
