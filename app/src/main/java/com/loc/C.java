package com.loc;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;

class C extends PhoneStateListener {
    final /* synthetic */ h lq;

    C(h hVar) {
        this.lq = hVar;
    }

    public void onCellLocationChanged(CellLocation cellLocation) {
        try {
            if (this.lq.kP(cellLocation)) {
                this.lq.kC = cellLocation;
            }
        } catch (Throwable th) {
        }
    }

    public void onServiceStateChanged(ServiceState serviceState) {
        try {
            switch (serviceState.getState()) {
                case 0:
                    this.lq.lb();
                    return;
                case 1:
                    this.lq.le();
                    return;
                default:
                    return;
            }
        } catch (Throwable th) {
        }
    }

    public void onSignalStrengthChanged(int i) {
        int i2 = -113;
        try {
            switch (this.lq.b) {
                case 1:
                    i2 = bq.vI(i);
                    break;
                case 2:
                    i2 = bq.vI(i);
                    break;
            }
            this.lq.ld(i2);
        } catch (Throwable th) {
        }
    }

    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        if (signalStrength != null) {
            int i = -113;
            try {
                switch (this.lq.b) {
                    case 1:
                        i = bq.vI(signalStrength.getGsmSignalStrength());
                        break;
                    case 2:
                        i = signalStrength.getCdmaDbm();
                        break;
                }
                this.lq.ld(i);
            } catch (Throwable th) {
            }
        }
    }
}
