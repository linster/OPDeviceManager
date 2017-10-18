package com.loc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

class bF implements ServiceConnection {
    final /* synthetic */ aB tt;

    bF(aB aBVar) {
        this.tt = aBVar;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.tt.on = new Messenger(iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.tt.on = null;
    }
}
