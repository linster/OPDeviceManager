package com.loc;

import java.util.TimerTask;

/* compiled from: Unknown */
final class aA extends TimerTask {
    private /* synthetic */ F nX;

    aA(F f) {
        this.nX = f;
    }

    public final void run() {
        try {
            if (bf.sj && this.nX.lx.rI != null) {
                bf.uv(this.nX.lx.rI);
            }
        } catch (Exception e) {
        }
    }
}
