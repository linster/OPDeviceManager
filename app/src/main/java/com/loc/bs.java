package com.loc;

import android.content.Context;

class bs extends Thread {
    final /* synthetic */ Context ti;
    final /* synthetic */ String tj;
    final /* synthetic */ String tk;

    bs(Context context, String str, String str2) {
        this.ti = context;
        this.tj = str;
        this.tk = str2;
    }

    public void run() {
        try {
            an.ng.oM(this.ti, this.tj, this.tk);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
