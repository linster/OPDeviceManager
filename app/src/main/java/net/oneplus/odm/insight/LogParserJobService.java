package net.oneplus.odm.insight;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LogParserJobService extends JobService {
    private static final Uri aN = Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table");
    private static int aO = 0;
    private ContentResolver aP;
    private Handler aQ;
    private HandlerThread aR;
    private String aS = "/data/mdm/mdm_tmp2_log";
    private String aT = "/data/mdm/mdm_tmp_log";
    private JobParameters aU;
    private String aV = "/data/mdm/system_server_watchdog";

    private void bv() {
        InputStream inputStream = null;
        try {
            inputStream = Runtime.getRuntime().exec(new String[]{"logcat", "-b", "mdm", "-g"}).getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                Log.v("LogParserJobService", readLine);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("LogParserJobService", e.getMessage());
                }
            }
        } catch (IOException e2) {
            Log.e("LogParserJobService", e2.getMessage());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e22) {
                    Log.e("LogParserJobService", e22.getMessage());
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    Log.e("LogParserJobService", e3.getMessage());
                }
            }
        }
    }

    private Process bw() {
        try {
            return Runtime.getRuntime().exec(new String[]{"logcat", "-b", "mdm", "-c"});
        } catch (IOException e) {
            Log.e("LogParserJobService", e.getMessage());
            return null;
        }
    }

    private void bx() {
        String[] strArr = new String[]{"logcat", "-b", "mdm", "-d", "-f", this.aS};
        long currentTimeMillis = System.currentTimeMillis();
        Log.v("LogParserJobService", "start getMDMLog");
        try {
            Runtime.getRuntime().exec(strArr).waitFor();
        } catch (IOException e) {
            Log.e("LogParserJobService", e.getMessage());
        } catch (InterruptedException e2) {
            Log.e("LogParserJobService", e2.getMessage());
        }
        Log.v("LogParserJobService", "end getMDMLog:" + String.valueOf(System.currentTimeMillis() - currentTimeMillis));
    }

    private void by() {
        this.aQ = new l(this, this.aR.getLooper());
    }

    private Process bz() {
        try {
            return Runtime.getRuntime().exec(new String[]{"logcat", "-b", "mdm", "-G", "3M"});
        } catch (IOException e) {
            Log.e("LogParserJobService", e.getMessage());
            return null;
        }
    }

    public void onCreate() {
        super.onCreate();
        this.aR = new HandlerThread("LogParserThread", 10);
        this.aR.start();
        this.aP = getApplicationContext().getContentResolver();
        by();
        bz();
    }

    public boolean onStartJob(JobParameters jobParameters) {
        Log.v("LogParserJobService", "Start");
        this.aU = jobParameters;
        new Thread(new f()).start();
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
