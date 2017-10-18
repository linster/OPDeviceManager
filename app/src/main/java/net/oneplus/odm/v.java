package net.oneplus.odm;

import android.accounts.Account;
import android.os.Bundle;
import android.util.Log;

final class v implements Runnable {
    final /* synthetic */ DeviceManagerService cJ;
    final /* synthetic */ Account[] cK;

    v(DeviceManagerService deviceManagerService, Account[] accountArr) {
        this.cJ = deviceManagerService;
        this.cK = accountArr;
    }

    public void run() {
        try {
            this.cJ.cc.bX("oneplus_account", ((Bundle) this.cJ.bV.getAuthToken(this.cK[0], "com.oneplus.account", false, null, null).getResult()).getString("email", ""));
        } catch (Exception e) {
            this.cJ.cc.bX("oneplus_account", "");
            Log.e(DeviceManagerService.bT, e.getMessage());
        }
    }
}
