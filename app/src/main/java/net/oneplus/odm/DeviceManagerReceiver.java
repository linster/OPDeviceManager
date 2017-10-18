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
import java.util.Map;
import net.oneplus.odm.common.a;
import net.oneplus.odm.common.c;
import net.oneplus.odm.insight.MDMJobService;
import net.oneplus.odm.insight.tracker.AppTracker;
import net.oneplus.odm.insight.tracker.OSTracker;

public class DeviceManagerReceiver extends BroadcastReceiver {
    private Context bA;
    private b bB;
    private OSTracker bC;
    private Runnable by = new r(this);
    private Runnable bz = new s(this);

    private int ch(FileInputStream fileInputStream) {
        String str = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                String[] split = str.split(",|\\s+");
                int intValue = Integer.valueOf(split[4]).intValue() + (((Integer.valueOf(split[1]).intValue() + 0) + Integer.valueOf(split[2]).intValue()) + Integer.valueOf(split[3]).intValue());
                Log.v("DeviceManagerBootReceiver", "[Bridging] sum:" + intValue);
                return intValue;
            } else if (readLine.indexOf(",s1302") > 0) {
                str = readLine;
            }
        }
    }

    private void ci() {
        FileInputStream fileInputStream;
        FileNotFoundException e;
        IOException e2;
        Throwable th;
        Exception e3;
        File file = new File("/proc/interrupts");
        FileInputStream fileInputStream2 = null;
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    int ch = ch(fileInputStream);
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e4.getMessage());
                    }
                    if (ch > 2000) {
                        Log.v("DeviceManagerBootReceiver", "[Bridging] resultCount > 2000");
                        AppTracker appTracker = new AppTracker(this.bA);
                        Map hashMap = new HashMap();
                        hashMap.put("sum", String.valueOf(ch));
                        appTracker.onEvent("BridgingIssue", hashMap);
                    }
                    return;
                } catch (FileNotFoundException e5) {
                    e = e5;
                    fileInputStream2 = fileInputStream;
                    Log.e("DeviceManagerBootReceiver", "[Bridging] FileNotFoundException:" + e.getMessage());
                    try {
                        fileInputStream2.close();
                    } catch (IOException e22) {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e22.getMessage());
                    }
                    return;
                } catch (IOException e6) {
                    e22 = e6;
                    fileInputStream2 = fileInputStream;
                    try {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e22.getMessage());
                        try {
                            fileInputStream2.close();
                        } catch (IOException e222) {
                            Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e222.getMessage());
                        }
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            fileInputStream2.close();
                        } catch (IOException e42) {
                            Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e42.getMessage());
                        }
                        throw th;
                    }
                } catch (Exception e7) {
                    e3 = e7;
                    try {
                        Log.e("DeviceManagerBootReceiver", "[Bridging] Exception:" + e3.getMessage());
                        try {
                            fileInputStream.close();
                        } catch (IOException e2222) {
                            Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e2222.getMessage());
                        }
                        return;
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream2 = fileInputStream;
                        fileInputStream2.close();
                        throw th;
                    }
                }
            } catch (FileNotFoundException e8) {
                e = e8;
                Log.e("DeviceManagerBootReceiver", "[Bridging] FileNotFoundException:" + e.getMessage());
                fileInputStream2.close();
                return;
            } catch (IOException e9) {
                e2222 = e9;
                Log.e("DeviceManagerBootReceiver", "[Bridging] IOException:" + e2222.getMessage());
                fileInputStream2.close();
                return;
            } catch (Exception e10) {
                e3 = e10;
                fileInputStream = null;
                Log.e("DeviceManagerBootReceiver", "[Bridging] Exception:" + e3.getMessage());
                fileInputStream.close();
                return;
            }
        }
        Log.e("DeviceManagerBootReceiver", "[Bridging] File doesnt exists");
    }

    private void cj(c cVar) {
        BufferedReader bufferedReader;
        String readLine;
        Exception e;
        IOException e2;
        StringBuilder stringBuilder;
        Throwable th;
        File cn = cVar.cn();
        String str = "RecordAndroidErrorLog";
        try {
            bufferedReader = new BufferedReader(new FileReader(cn));
            while (true) {
                try {
                    readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.indexOf("kernel panic") > 0 || readLine.indexOf(cVar.co()) > 0) {
                        cVar.cs();
                    }
                    if (readLine.indexOf(cVar.cp()) > 0) {
                        cVar.ct();
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e22) {
                    Log.e(str, e22.getMessage());
                }
            }
        } catch (Exception e4) {
            e = e4;
            bufferedReader = null;
            try {
                Log.e(str, e.getMessage());
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e222) {
                        Log.e(str, e222.getMessage());
                    }
                }
                if (!cVar.cq()) {
                }
                Log.v(str, "There is an error happend cause this reboot");
                stringBuilder = new StringBuilder();
                try {
                    bufferedReader = new BufferedReader(new FileReader(cn));
                    while (true) {
                        try {
                            readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                break;
                            }
                            stringBuilder.append(readLine);
                            stringBuilder.append('\n');
                        } catch (IOException e5) {
                            e222 = e5;
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2222) {
                            Log.e(str, e2222.getMessage());
                        }
                    }
                } catch (IOException e6) {
                    e2222 = e6;
                    bufferedReader = null;
                    try {
                        Log.e(str, e2222.getMessage());
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e22222) {
                                Log.e(str, e22222.getMessage());
                            }
                        }
                        new HashMap().put("message", stringBuilder);
                        if (!cVar.cq()) {
                            Log.v(str, "Kernel Panic happen");
                            this.bC.onEvent("kernel_panic", null);
                            return;
                        } else if (!cVar.cr()) {
                            Log.v(str, "Hardware watch dog happen");
                            this.bC.onEvent("hardware_watchdog", null);
                            return;
                        } else {
                            return;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e7) {
                                Log.e(str, e7.getMessage());
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
                new HashMap().put("message", stringBuilder);
                if (!cVar.cq()) {
                    Log.v(str, "Kernel Panic happen");
                    this.bC.onEvent("kernel_panic", null);
                    return;
                } else if (!cVar.cr()) {
                    Log.v(str, "Hardware watch dog happen");
                    this.bC.onEvent("hardware_watchdog", null);
                    return;
                } else {
                    return;
                }
            } catch (Throwable th4) {
                th = th4;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e72) {
                        Log.e(str, e72.getMessage());
                    }
                }
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            bufferedReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            throw th;
        }
        if (cVar.cq() || cVar.cr()) {
            Log.v(str, "There is an error happend cause this reboot");
            stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new FileReader(cn));
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine != null) {
                    break;
                }
                stringBuilder.append(readLine);
                stringBuilder.append('\n');
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            new HashMap().put("message", stringBuilder);
            if (!cVar.cq()) {
                Log.v(str, "Kernel Panic happen");
                this.bC.onEvent("kernel_panic", null);
                return;
            } else if (!cVar.cr()) {
                Log.v(str, "Hardware watch dog happen");
                this.bC.onEvent("hardware_watchdog", null);
                return;
            } else {
                return;
            }
        }
        Log.d(str, "There is no error.");
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.oem.mdm.upload")) {
            Log.v("DeviceManagerBootReceiver", "Start to schedule MDM");
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            ComponentName componentName = new ComponentName(context, MDMJobService.class);
            c.V = 1000;
            jobScheduler.schedule(new Builder(999, componentName).setMinimumLatency(5000).build());
            return;
        }
        Intent intent2 = new Intent(context, DeviceManagerService.class);
        if (!a.am() || a.al()) {
            context.startService(intent2);
            this.bB = b.bT(context);
            this.bA = context;
            this.bC = new OSTracker(this.bA);
            c gVar = Build.PRODUCT.equals("OnePlus5") ? new g(this) : Build.PRODUCT.equals("OnePlus3T") ? new f(this) : Build.PRODUCT.equals("OnePlus3") ? new f(this) : Build.PRODUCT.equals("OnePlus2") ? new e(this) : new d(this);
            cj(gVar);
            new Thread(this.by).start();
            new Thread(this.bz).start();
            return;
        }
        Log.v("DeviceManagerBootReceiver", "Pass");
    }
}
