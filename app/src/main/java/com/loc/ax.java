package com.loc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

class ax implements ServiceConnection {
    final /* synthetic */ bO nM;
    final /* synthetic */ aV nN;

    ax(aV aVVar, bO bOVar) {
        this.nN = aVVar;
        this.nM = bOVar;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.nN.qW = bk.vm(iBinder);
        this.nM.ne(0);
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.nN.qW = null;
    }
}
