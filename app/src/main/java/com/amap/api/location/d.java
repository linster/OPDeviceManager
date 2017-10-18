package com.amap.api.location;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class d extends Handler {
    b is = null;

    public d(b bVar) {
        this.is = bVar;
    }

    public d(b bVar, Looper looper) {
        super(looper);
        this.is = bVar;
    }

    public void handleMessage(Message message) {
        try {
            super.handleMessage(message);
            switch (message.arg1) {
                case 1:
                    try {
                        this.is.ik = (e) message.obj;
                        this.is.ij.hR(this.is.ik);
                        return;
                    } catch (Throwable th) {
                        return;
                    }
                case 2:
                    try {
                        this.is.il = (a) message.obj;
                        this.is.ij.hS(this.is.il);
                        return;
                    } catch (Throwable th2) {
                        return;
                    }
                case 3:
                    try {
                        this.is.ij.hT();
                        return;
                    } catch (Throwable th3) {
                        return;
                    }
                case 4:
                    try {
                        this.is.ij.hU();
                        return;
                    } catch (Throwable th4) {
                        return;
                    }
                case 5:
                    try {
                        this.is.il = (a) message.obj;
                        this.is.ij.ia(this.is.il);
                        return;
                    } catch (Throwable th5) {
                        return;
                    }
                case 6:
                    try {
                        this.is.ij.hV(this.is.iq, this.is.in, this.is.io, this.is.ip, this.is.j, this.is.ir);
                        return;
                    } catch (Throwable th6) {
                        return;
                    }
                case 7:
                    try {
                        this.is.ij.hX((PendingIntent) message.obj);
                        return;
                    } catch (Throwable th7) {
                        return;
                    }
                case 8:
                    try {
                        this.is.ij.hY();
                        return;
                    } catch (Throwable th8) {
                        return;
                    }
                case 9:
                    try {
                        this.is.ij.hZ();
                        return;
                    } catch (Throwable th9) {
                        return;
                    }
                case 10:
                    try {
                        this.is.ij.hW((PendingIntent) message.obj, this.is.iq);
                        return;
                    } catch (Throwable th10) {
                        return;
                    }
                case 11:
                    try {
                        this.is.ij.onDestroy();
                        this.is.ij = null;
                        this.is = null;
                        return;
                    } catch (Throwable th11) {
                        return;
                    }
                default:
                    return;
            }
        } catch (Throwable th12) {
        }
    }
}
