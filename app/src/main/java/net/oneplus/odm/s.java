package net.oneplus.odm;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.oneplus.odm.common.a;

final class s implements Runnable {
    final /* synthetic */ DeviceManagerReceiver cG;

    s(DeviceManagerReceiver deviceManagerReceiver) {
        this.cG = deviceManagerReceiver;
    }

    public void run() {
        boolean z;
        boolean z2;
        Exception exception;
        Throwable th;
        boolean bV;
        File file;
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(new File("/sys/pwr_on_off_reason/pwroff_reason")));
            z = false;
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    } else if (readLine.indexOf("Power-off reason: [13]") > 0) {
                        z = true;
                    }
                } catch (Exception e) {
                    Exception exception2 = e;
                    bufferedReader = bufferedReader2;
                    z2 = z;
                    exception = exception2;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            }
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e2) {
                    Log.e("DeviceManagerBootReceiver", e2.getMessage());
                }
            }
        } catch (Exception e3) {
            exception = e3;
            z2 = false;
            try {
                Log.e("DeviceManagerBootReceiver", exception.getMessage());
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e4) {
                        Log.e("DeviceManagerBootReceiver", e4.getMessage());
                    }
                }
                z = z2;
                bV = this.cG.bB.bV("first_boot_status_check", true);
                file = new File("/data/mdm/reboot_mark");
                if (!file.exists()) {
                }
                Log.v("DeviceManagerBootReceiver", "Device normal reboot");
                if (file.exists()) {
                    file.delete();
                }
                this.cG.bB.bY("first_boot_status_check", false);
                return;
            } catch (Throwable th3) {
                th = th3;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e5) {
                        Log.e("DeviceManagerBootReceiver", e5.getMessage());
                    }
                }
                throw th;
            }
        }
        bV = this.cG.bB.bV("first_boot_status_check", true);
        file = new File("/data/mdm/reboot_mark");
        if (file.exists() || r0 || bV) {
            Log.v("DeviceManagerBootReceiver", "Device normal reboot");
            if (file.exists()) {
                file.delete();
            }
            this.cG.bB.bY("first_boot_status_check", false);
            return;
        }
        String valueOf = String.valueOf(a.ap());
        Map hashMap = new HashMap();
        hashMap.put("isroot", valueOf);
        this.cG.bC.onEvent("abnormal_reboot", hashMap);
        Log.v("DeviceManagerBootReceiver", "Device abnormal reboot, remount:" + valueOf);
    }
}
