package com.loc;

import java.util.TimerTask;

class ab extends TimerTask {
    final /* synthetic */ int a;
    final /* synthetic */ e mJ;

    ab(e eVar, int i) {
        this.mJ = eVar;
        this.a = i;
    }

    public void run() {
        Thread.currentThread().setPriority(1);
        this.mJ.jq(this.a);
    }
}
