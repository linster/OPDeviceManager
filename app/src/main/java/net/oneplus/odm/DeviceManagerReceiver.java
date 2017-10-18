package net.oneplus.odm;

import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import net.oneplus.odm.common.Def;
import net.oneplus.odm.common.Utils;
import net.oneplus.odm.insight.MDMJobService;
import net.oneplus.odm.insight.tracker.AppTracker;
import net.oneplus.odm.insight.tracker.OSTracker;

public class DeviceManagerReceiver extends BroadcastReceiver {
    private Runnable mCheckBridgingTask;
    private Runnable mCheckDeviceReboot;
    private Context mContext;
    private DeviceManagerSetting mSettings;
    private OSTracker mTracker;

    interface DeviceError {
        File getLogFile();

        String getPanicKeyword();

        String getWatchDogKeyword();

        boolean isPanic();

        boolean isWatchDog();

        void setPanic();

        void setWatchDog();
    }

    class OnePlus2DeviceError implements DeviceError {
        private boolean hasPanic;
        private boolean hasWatchDog;
        String path;

        OnePlus2DeviceError() {
            this.path = "/sys/fs/pstore/console-ramoops-0";
            this.hasPanic = false;
            this.hasWatchDog = false;
        }

        public void setPanic() {
            this.hasPanic = true;
        }

        public boolean isPanic() {
            return this.hasPanic;
        }

        public String getPanicKeyword() {
            return "Kernel panic";
        }

        public void setWatchDog() {
            this.hasWatchDog = true;
        }

        public boolean isWatchDog() {
            return this.hasWatchDog;
        }

        public String getWatchDogKeyword() {
            return "Watchdog bark";
        }

        public File getLogFile() {
            return new File(this.path);
        }
    }

    class OnePlus3DeviceError implements DeviceError {
        private boolean hasPanic;
        private boolean hasWatchDog;
        String path;

        OnePlus3DeviceError() {
            this.path = "/sys/fs/pstore/console-ramoops";
            this.hasPanic = false;
            this.hasWatchDog = false;
        }

        public void setPanic() {
            this.hasPanic = true;
        }

        public boolean isPanic() {
            return this.hasPanic;
        }

        public String getPanicKeyword() {
            return "Kernel panic";
        }

        public void setWatchDog() {
            this.hasWatchDog = true;
        }

        public boolean isWatchDog() {
            return this.hasWatchDog;
        }

        public String getWatchDogKeyword() {
            return "Watchdog bark";
        }

        public File getLogFile() {
            return new File(this.path);
        }
    }

    class OnePlus5DeviceError implements DeviceError {
        private boolean hasPanic;
        private boolean hasWatchDog;
        String path;

        OnePlus5DeviceError() {
            this.path = "/sys/fs/pstore/console-ramoops-0";
            this.hasPanic = false;
            this.hasWatchDog = false;
        }

        public void setPanic() {
            this.hasPanic = true;
        }

        public boolean isPanic() {
            return this.hasPanic;
        }

        public String getPanicKeyword() {
            return "Kernel panic";
        }

        public void setWatchDog() {
            this.hasWatchDog = true;
        }

        public boolean isWatchDog() {
            return this.hasWatchDog;
        }

        public String getWatchDogKeyword() {
            return "Watchdog bark";
        }

        public File getLogFile() {
            return new File(this.path);
        }
    }

    class OnePlusDeviceError implements DeviceError {
        private boolean hasPanic;
        private boolean hasWatchDog;
        String path;

        OnePlusDeviceError() {
            this.path = "/proc/last_kmsg";
            this.hasPanic = false;
            this.hasWatchDog = false;
        }

        public void setPanic() {
            this.hasPanic = true;
        }

        public boolean isPanic() {
            return this.hasPanic;
        }

        public String getPanicKeyword() {
            return "Kernel panic";
        }

        public void setWatchDog() {
            this.hasWatchDog = true;
        }

        public boolean isWatchDog() {
            return this.hasWatchDog;
        }

        public String getWatchDogKeyword() {
            return "Watchdog bark";
        }

        public File getLogFile() {
            return new File(this.path);
        }
    }

    public DeviceManagerReceiver() {
        this.mCheckBridgingTask = new Runnable() {
            public void run() {
                DeviceManagerReceiver.this.checkBridging();
            }
        };
        this.mCheckDeviceReboot = new Runnable() {
            public void run() {
                Exception e;
                Throwable th;
                boolean firstBootStatusCheck;
                File rebootMarkFile;
                boolean longPressReboot = false;
                BufferedReader bufferedReader = null;
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(new File("/sys/pwr_on_off_reason/pwroff_reason")));
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            } else if (line.indexOf("Power-off reason: [13]") > 0) {
                                longPressReboot = true;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedReader = bufferedReader2;
                        }
                    }
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e3) {
                            Log.e("DeviceManagerBootReceiver", e3.getMessage());
                        }
                    }
                    bufferedReader = bufferedReader2;
                } catch (Exception e4) {
                    e = e4;
                    try {
                        Log.e("DeviceManagerBootReceiver", e.getMessage());
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e32) {
                                Log.e("DeviceManagerBootReceiver", e32.getMessage());
                            }
                        }
                        firstBootStatusCheck = DeviceManagerReceiver.this.mSettings.getPreference("first_boot_status_check", true);
                        rebootMarkFile = new File("/data/mdm/reboot_mark");
                        if (!rebootMarkFile.exists()) {
                        }
                        Log.v("DeviceManagerBootReceiver", "Device normal reboot");
                        if (rebootMarkFile.exists()) {
                            rebootMarkFile.delete();
                        }
                        DeviceManagerReceiver.this.mSettings.setPreference("first_boot_status_check", false);
                        return;
                    } catch (Throwable th3) {
                        th = th3;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e322) {
                                Log.e("DeviceManagerBootReceiver", e322.getMessage());
                            }
                        }
                        throw th;
                    }
                }
                firstBootStatusCheck = DeviceManagerReceiver.this.mSettings.getPreference("first_boot_status_check", true);
                rebootMarkFile = new File("/data/mdm/reboot_mark");
                if (rebootMarkFile.exists() || longPressReboot || firstBootStatusCheck) {
                    Log.v("DeviceManagerBootReceiver", "Device normal reboot");
                    if (rebootMarkFile.exists()) {
                        rebootMarkFile.delete();
                    }
                    DeviceManagerReceiver.this.mSettings.setPreference("first_boot_status_check", false);
                    return;
                }
                String isRoot = String.valueOf(Utils.isRooted());
                HashMap<String, String> detailEvent = new HashMap();
                detailEvent.put("isroot", isRoot);
                DeviceManagerReceiver.this.mTracker.onEvent("abnormal_reboot", detailEvent);
                Log.v("DeviceManagerBootReceiver", "Device abnormal reboot, remount:" + isRoot);
            }
        };
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.oem.mdm.upload")) {
            Log.v("DeviceManagerBootReceiver", "Start to schedule MDM");
            JobScheduler scheduler = (JobScheduler) context.getSystemService("jobscheduler");
            ComponentName component = new ComponentName(context, MDMJobService.class);
            Def.RANDOM_UPLOAD_DATA_DELAY = 1000;
            scheduler.schedule(new Builder(999, component).setMinimumLatency(5000).build());
            return;
        }
        Intent myIntent = new Intent(context, DeviceManagerService.class);
        if (!Utils.isDebugRom() || Utils.isDailyRom()) {
            DeviceError error;
            context.startService(myIntent);
            this.mSettings = DeviceManagerSetting.getInstance(context);
            this.mContext = context;
            this.mTracker = new OSTracker(this.mContext);
            if (Build.PRODUCT.equals("OnePlus5")) {
                error = new OnePlus5DeviceError();
            } else if (Build.PRODUCT.equals("OnePlus3T")) {
                error = new OnePlus3DeviceError();
            } else if (Build.PRODUCT.equals("OnePlus3")) {
                error = new OnePlus3DeviceError();
            } else if (Build.PRODUCT.equals("OnePlus2")) {
                error = new OnePlus2DeviceError();
            } else {
                error = new OnePlusDeviceError();
            }
            parsePstoreLog(error);
            new Thread(this.mCheckBridgingTask).start();
            new Thread(this.mCheckDeviceReboot).start();
            return;
        }
        Log.v("DeviceManagerBootReceiver", "Pass");
    }

    private void checkBridging() {
        IOException e;
        FileNotFoundException e2;
        Exception e3;
        Throwable th;
        File file = new File("/proc/interrupts");
        FileInputStream fileInputStream = null;
        if (file.exists()) {
            try {
                FileInputStream fin = new FileInputStream(file);
                try {
                    int resultCount = calBridgingResult(fin);
                    try {
                        fin.close();
                    } catch (IOException e4) {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e4.getMessage());
                    }
                    if (resultCount > 2000) {
                        Log.v("DeviceManagerBootReceiver", "[Bridging] resultCount > 2000");
                        AppTracker tracker = new AppTracker(this.mContext);
                        HashMap<String, String> data = new HashMap();
                        data.put("sum", String.valueOf(resultCount));
                        tracker.onEvent("BridgingIssue", data);
                    }
                    return;
                } catch (FileNotFoundException e5) {
                    e2 = e5;
                    fileInputStream = fin;
                    Log.e("DeviceManagerBootReceiver", "[Bridging] FileNotFoundException:" + e2.getMessage());
                    try {
                        fileInputStream.close();
                    } catch (IOException e42) {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e42.getMessage());
                    }
                    return;
                } catch (IOException e6) {
                    e42 = e6;
                    fileInputStream = fin;
                    Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e42.getMessage());
                    try {
                        fileInputStream.close();
                    } catch (IOException e422) {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e422.getMessage());
                    }
                    return;
                } catch (Exception e7) {
                    e3 = e7;
                    fileInputStream = fin;
                    try {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] Exception:" + e3.getMessage());
                        try {
                            fileInputStream.close();
                        } catch (IOException e4222) {
                            Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e4222.getMessage());
                        }
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            fileInputStream.close();
                        } catch (IOException e42222) {
                            Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e42222.getMessage());
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = fin;
                    fileInputStream.close();
                    throw th;
                }
            } catch (FileNotFoundException e8) {
                e2 = e8;
                Log.e("DeviceManagerBootReceiver", "[Bridging] FileNotFoundException:" + e2.getMessage());
                fileInputStream.close();
                return;
            } catch (IOException e9) {
                e42222 = e9;
                Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e42222.getMessage());
                fileInputStream.close();
                return;
            } catch (Exception e10) {
                e3 = e10;
                Log.e("DeviceManagerBootReceiver", "[Bridging] Exception:" + e3.getMessage());
                fileInputStream.close();
                return;
            }
        }
        Log.e("DeviceManagerBootReceiver", "[Bridging] File doesnt exists");
    }

    private int calBridgingResult(FileInputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String str = null;
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                String[] tmp = str.split(",|\\s+");
                int sum = (((Integer.valueOf(tmp[1]).intValue() + 0) + Integer.valueOf(tmp[2]).intValue()) + Integer.valueOf(tmp[3]).intValue()) + Integer.valueOf(tmp[4]).intValue();
                Log.v("DeviceManagerBootReceiver", "[Bridging] sum:" + sum);
                return sum;
            } else if (line.indexOf(",s1302") > 0) {
                str = line;
            }
        }
    }

    private void parsePstoreLog(DeviceError deviceError) {
        String line;
        Exception e;
        Throwable th;
        IOException e2;
        StringBuilder text;
        BufferedReader br;
        File file = deviceError.getLogFile();
        String tag = "RecordAndroidErrorLog";
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    line = bufferedReader2.readLine();
                    if (line == null) {
                        break;
                    }
                    if (line.indexOf("kernel panic") > 0 || line.indexOf(deviceError.getPanicKeyword()) > 0) {
                        deviceError.setPanic();
                    }
                    if (line.indexOf(deviceError.getWatchDogKeyword()) > 0) {
                        deviceError.setWatchDog();
                    }
                } catch (Exception e3) {
                    e = e3;
                    bufferedReader = bufferedReader2;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            }
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e22) {
                    Log.e(tag, e22.getMessage());
                }
            }
        } catch (Exception e4) {
            e = e4;
            try {
                Log.e(tag, e.getMessage());
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e222) {
                        Log.e(tag, e222.getMessage());
                    }
                }
                if (!deviceError.isPanic()) {
                }
                Log.v(tag, "There is an error happend cause this reboot");
                text = new StringBuilder();
                BufferedReader bufferedReader3 = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                    while (true) {
                        try {
                            line = br.readLine();
                            if (line != null) {
                                break;
                                if (br != null) {
                                    try {
                                        br.close();
                                    } catch (IOException e2222) {
                                        Log.e(tag, e2222.getMessage());
                                    }
                                }
                                new HashMap().put("message", text);
                                if (!deviceError.isPanic()) {
                                    Log.v(tag, "Kernel Panic happen");
                                    this.mTracker.onEvent("kernel_panic", null);
                                    return;
                                } else if (!deviceError.isWatchDog()) {
                                    Log.v(tag, "Hardware watch dog happen");
                                    this.mTracker.onEvent("hardware_watchdog", null);
                                    return;
                                } else {
                                    return;
                                }
                            }
                            text.append(line);
                            text.append('\n');
                        } catch (IOException e5) {
                            e2222 = e5;
                            bufferedReader3 = br;
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedReader3 = br;
                        }
                    }
                } catch (IOException e6) {
                    e2222 = e6;
                    try {
                        Log.e(tag, e2222.getMessage());
                        if (bufferedReader3 != null) {
                            try {
                                bufferedReader3.close();
                            } catch (IOException e22222) {
                                Log.e(tag, e22222.getMessage());
                            }
                        }
                        new HashMap().put("message", text);
                        if (!deviceError.isPanic()) {
                            Log.v(tag, "Kernel Panic happen");
                            this.mTracker.onEvent("kernel_panic", null);
                            return;
                        } else if (!deviceError.isWatchDog()) {
                            Log.v(tag, "Hardware watch dog happen");
                            this.mTracker.onEvent("hardware_watchdog", null);
                            return;
                        } else {
                            return;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        if (bufferedReader3 != null) {
                            try {
                                bufferedReader3.close();
                            } catch (IOException e222222) {
                                Log.e(tag, e222222.getMessage());
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th5) {
                th = th5;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e2222222) {
                        Log.e(tag, e2222222.getMessage());
                    }
                }
                throw th;
            }
        }
        if (deviceError.isPanic() || deviceError.isWatchDog()) {
            Log.v(tag, "There is an error happend cause this reboot");
            text = new StringBuilder();
            BufferedReader bufferedReader32 = null;
            br = new BufferedReader(new FileReader(file));
            while (true) {
                line = br.readLine();
                if (line != null) {
                    break;
                }
                text.append(line);
                text.append('\n');
            }
            if (br != null) {
                br.close();
            }
            new HashMap().put("message", text);
            if (!deviceError.isPanic()) {
                Log.v(tag, "Kernel Panic happen");
                this.mTracker.onEvent("kernel_panic", null);
                return;
            } else if (!deviceError.isWatchDog()) {
                Log.v(tag, "Hardware watch dog happen");
                this.mTracker.onEvent("hardware_watchdog", null);
                return;
            } else {
                return;
            }
        }
        Log.d(tag, "There is no error.");
    }
}
