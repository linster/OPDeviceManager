package net.oneplus.odm;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;

final class u implements OnAccountsUpdateListener {
    final /* synthetic */ DeviceManagerService cI;

    u(DeviceManagerService deviceManagerService) {
        this.cI = deviceManagerService;
    }

    public void onAccountsUpdated(Account[] accountArr) {
        this.cI.cw();
    }
}
