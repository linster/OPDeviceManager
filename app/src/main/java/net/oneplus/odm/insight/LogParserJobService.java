package net.oneplus.odm.insight;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.oneplus.odm.insight.tracker.OSTracker;

public class LogParserJobService extends JobService {
    private static final Uri MDM_DB_URI;
    private static int WORKING_TASK_COUNT;
    private ContentResolver mContentResolver;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private String mMDMLogFile;
    private String mMDMRebootLogFile;
    private JobParameters mParams;
    private String mWatchDogLogFile;

    /* renamed from: net.oneplus.odm.insight.LogParserJobService.1 */
    class AnonymousClass1 extends Handler {
        AnonymousClass1(Looper $anonymous0) {
            super($anonymous0);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        LogParserJobService.this.mContentResolver.insert(LogParserJobService.MDM_DB_URI, msg.obj);
                    } catch (IllegalArgumentException e) {
                        Log.e("LogParserJobService", e.getMessage());
                    }
                case 2:
                    Log.v("LogParserJobService", "Finish service");
                    LogParserJobService.this.jobFinished(LogParserJobService.this.mParams, false);
                case 3:
                    new OSTracker(LogParserJobService.this.getApplicationContext()).onEvent(msg.getData().getString("tag"), null);
                default:
            }
        }
    }

    private class ErrorLogParserTask implements Runnable {
        private File file;

        ErrorLogParserTask(File f) {
            this.file = f;
        }

        public void run() {
            InputStreamReader inputStreamReader;
            FileNotFoundException e;
            Throwable th;
            IOException e2;
            FileInputStream fileInputStream;
            try {
                BufferedReader bufferedReader;
                FileInputStream inputStream = new FileInputStream(this.file);
                try {
                    inputStreamReader = new InputStreamReader(inputStream);
                } catch (FileNotFoundException e3) {
                    e = e3;
                    try {
                        Log.e("LogParserJobService", e.getMessage());
                        if (this.file.exists()) {
                            this.file.delete();
                        }
                        LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                        if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                            LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (this.file.exists()) {
                            this.file.delete();
                        }
                        LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                        if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                            LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e2 = e4;
                    fileInputStream = inputStream;
                    Log.e("LogParserJobService", e2.getMessage());
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                    throw th;
                }
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                } catch (FileNotFoundException e5) {
                    e = e5;
                    fileInputStream = inputStream;
                    Log.e("LogParserJobService", e.getMessage());
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                } catch (IOException e6) {
                    e2 = e6;
                    fileInputStream = inputStream;
                    Log.e("LogParserJobService", e2.getMessage());
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                } catch (Throwable th4) {
                    th = th4;
                    fileInputStream = inputStream;
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                    throw th;
                }
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuilder.append(line);
                    }
                    inputStream.close();
                    String log = stringBuilder.toString();
                    if (log != null) {
                        Message msg = LogParserJobService.this.mHandler.obtainMessage(3);
                        Bundle bundle = new Bundle();
                        bundle.putString("tag", this.file.getName());
                        bundle.putString("log", log);
                        msg.setData(bundle);
                        LogParserJobService.this.mHandler.sendMessage(msg);
                    }
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                } catch (FileNotFoundException e7) {
                    e = e7;
                    fileInputStream = inputStream;
                    Log.e("LogParserJobService", e.getMessage());
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                } catch (IOException e8) {
                    e2 = e8;
                    InputStreamReader inputStreamReader2 = inputStreamReader;
                    fileInputStream = inputStream;
                    Log.e("LogParserJobService", e2.getMessage());
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                } catch (Throwable th5) {
                    th = th5;
                    if (this.file.exists()) {
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                    throw th;
                }
            } catch (FileNotFoundException e9) {
                e = e9;
                Log.e("LogParserJobService", e.getMessage());
                if (this.file.exists()) {
                    this.file.delete();
                }
                LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                    LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                }
            } catch (IOException e10) {
                e2 = e10;
                Log.e("LogParserJobService", e2.getMessage());
                if (this.file.exists()) {
                    this.file.delete();
                }
                LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                if (LogParserJobService.WORKING_TASK_COUNT != 0) {
                    LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                }
            }
        }
    }

    private class GetLogTask implements Runnable {
        private GetLogTask() {
        }

        public void run() {
            LogParserJobService.this.checkMDMLogSize();
            File mdmLogRebootFile = new File(LogParserJobService.this.mMDMRebootLogFile);
            if (mdmLogRebootFile.exists()) {
                Log.v("LogParserJobService", "mdmLogRebootFile exists");
                new Thread(new LogParserTask(mdmLogRebootFile)).start();
                LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT + 1;
            }
            LogParserJobService.this.getMDMLog();
            File mdmLogFile = new File(LogParserJobService.this.mMDMLogFile);
            if (mdmLogFile.exists()) {
                Log.v("LogParserJobService", "mdmLogFile exists");
                new Thread(new LogParserTask(mdmLogFile)).start();
                LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT + 1;
            }
            LogParserJobService.this.clearMDMLog();
            File watchDogLogFile = new File(LogParserJobService.this.mWatchDogLogFile);
            if (watchDogLogFile.exists()) {
                Log.v("LogParserJobService", "Parse watchdog log");
                new Thread(new ErrorLogParserTask(watchDogLogFile)).start();
                LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT + 1;
            }
            if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                Log.v("LogParserJobService", "no task, close service");
                LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
            }
        }
    }

    private class LogParserTask implements Runnable {
        private File file;
        private Process process;

        LogParserTask(File f) {
            this.file = f;
        }

        public void run() {
            InputStream inputStream;
            IOException e;
            Throwable th;
            InputStream inputStream2 = null;
            long eventCount = 0;
            if (this.process != null) {
                Log.v("LogParserJobService", "Parse buffer");
                inputStream = this.process.getInputStream();
            } else {
                inputStream = null;
            }
            try {
                if (this.file != null) {
                    Log.v("LogParserJobService", "Parse file");
                    inputStream2 = new FileInputStream(this.file);
                } else {
                    inputStream2 = inputStream;
                }
            } catch (IOException e2) {
                e = e2;
                inputStream2 = inputStream;
                try {
                    Log.e("LogParserJobService", "IOException:" + e.getMessage());
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e3) {
                            Log.e("LogParserJobService", e3.getMessage());
                            LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                            if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                                LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                                return;
                            }
                            return;
                        } catch (Throwable th2) {
                            LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                            if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                                LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                            }
                        }
                    }
                    if (this.file != null) {
                        Log.v("LogParserJobService", "mdm log file delete");
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                        return;
                    }
                    return;
                } catch (Throwable th3) {
                    th = th3;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e32) {
                            Log.e("LogParserJobService", e32.getMessage());
                            LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                            if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                                LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                            }
                        } catch (Throwable th4) {
                            LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                            if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                                LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                            }
                        }
                    }
                    if (this.file != null) {
                        Log.v("LogParserJobService", "mdm log file delete");
                        this.file.delete();
                    }
                    LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                    if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                        LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                inputStream2 = inputStream;
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (this.file != null) {
                    Log.v("LogParserJobService", "mdm log file delete");
                    this.file.delete();
                }
                LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                    LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                }
                throw th;
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream2));
                ContentValues values = null;
                while (true) {
                    try {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        String[] split = line.split("(: ')|(', ')");
                        int splitLength = split.length;
                        if (split.length >= 4) {
                            Message msg = LogParserJobService.this.mHandler.obtainMessage(1);
                            ContentValues values2 = new ContentValues();
                            values2.put("OP_payload", split[splitLength - 3]);
                            values2.put("OP_payload_timestamp", split[splitLength - 2]);
                            values2.put("OP_payload_category", split[splitLength - 1]);
                            msg.obj = values2;
                            LogParserJobService.this.mHandler.sendMessage(msg);
                            eventCount++;
                            values = values2;
                        }
                    } catch (IOException e4) {
                        e32 = e4;
                    } catch (Throwable th6) {
                        th = th6;
                    }
                }
                Log.v("LogParserJobService", "Add Event:" + eventCount);
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException e322) {
                        Log.e("LogParserJobService", e322.getMessage());
                        LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                        if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                            LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                        }
                    } catch (Throwable th7) {
                        LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                        if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                            LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                        }
                    }
                }
                if (this.file != null) {
                    Log.v("LogParserJobService", "mdm log file delete");
                    this.file.delete();
                }
                LogParserJobService.WORKING_TASK_COUNT = LogParserJobService.WORKING_TASK_COUNT - 1;
                if (LogParserJobService.WORKING_TASK_COUNT == 0) {
                    LogParserJobService.this.mHandler.sendMessage(LogParserJobService.this.mHandler.obtainMessage(2));
                }
            } catch (IOException e5) {
                e322 = e5;
            }
        }
    }

    public LogParserJobService() {
        this.mMDMRebootLogFile = "/data/mdm/mdm_tmp_log";
        this.mMDMLogFile = "/data/mdm/mdm_tmp2_log";
        this.mWatchDogLogFile = "/data/mdm/system_server_watchdog";
    }

    static {
        MDM_DB_URI = Uri.parse("content://net.oneplus.odm.provider.PayloadProvider/OP_payload_upload_table");
        WORKING_TASK_COUNT = 0;
    }

    public boolean onStartJob(JobParameters params) {
        Log.v("LogParserJobService", "Start");
        this.mParams = params;
        new Thread(new GetLogTask()).start();
        return true;
    }

    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void onCreate() {
        super.onCreate();
        this.mHandlerThread = new HandlerThread("LogParserThread", 10);
        this.mHandlerThread.start();
        this.mContentResolver = getApplicationContext().getContentResolver();
        initHandler();
        setMDMLogSize();
    }

    private void initHandler() {
        this.mHandler = new AnonymousClass1(this.mHandlerThread.getLooper());
    }

    private void getMDMLog() {
        String[] cmd = new String[]{"logcat", "-b", "mdm", "-d", "-f", this.mMDMLogFile};
        long time = System.currentTimeMillis();
        Log.v("LogParserJobService", "start getMDMLog");
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (IOException e) {
            Log.e("LogParserJobService", e.getMessage());
        } catch (InterruptedException e2) {
            Log.e("LogParserJobService", e2.getMessage());
        }
        Log.v("LogParserJobService", "end getMDMLog:" + String.valueOf(System.currentTimeMillis() - time));
    }

    private Process setMDMLogSize() {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{"logcat", "-b", "mdm", "-G", "3M"});
        } catch (IOException e) {
            Log.e("LogParserJobService", e.getMessage());
        }
        return p;
    }

    private void checkMDMLogSize() {
        IOException e;
        InputStreamReader inputStreamReader;
        Throwable th;
        InputStream inputStream = null;
        try {
            inputStream = Runtime.getRuntime().exec(new String[]{"logcat", "-b", "mdm", "-g"}).getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            try {
                BufferedReader br = new BufferedReader(isr);
                while (true) {
                    try {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        Log.v("LogParserJobService", line);
                    } catch (IOException e2) {
                        e = e2;
                        inputStreamReader = isr;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        Log.e("LogParserJobService", e3.getMessage());
                    }
                }
            } catch (IOException e4) {
                e3 = e4;
                try {
                    Log.e("LogParserJobService", e3.getMessage());
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e32) {
                            Log.e("LogParserJobService", e32.getMessage());
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e322) {
                            Log.e("LogParserJobService", e322.getMessage());
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                inputStreamReader = isr;
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        } catch (IOException e5) {
            e322 = e5;
            Log.e("LogParserJobService", e322.getMessage());
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private Process clearMDMLog() {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{"logcat", "-b", "mdm", "-c"});
        } catch (IOException e) {
            Log.e("LogParserJobService", e.getMessage());
        }
        return p;
    }
}
