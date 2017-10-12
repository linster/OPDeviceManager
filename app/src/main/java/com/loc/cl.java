package com.loc;

import java.util.TimerTask;

/* compiled from: Unknown */
final class cl extends TimerTask {
    private /* synthetic */ ck a;

    cl(ck ckVar) {
        this.a = ckVar;
    }

    public final void run() {
        try {
            if (cg.b && this.a.a.g != null) {
                cg.a(this.a.a.g);
            }
        } catch (Exception e) {
        }
    }
}
