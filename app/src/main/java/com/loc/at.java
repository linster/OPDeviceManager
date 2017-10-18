package com.loc;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;

/* compiled from: Unknown */
final class at extends PhoneStateListener {
    private /* synthetic */ bf nw;

    private at(bf bfVar) {
        this.nw = bfVar;
    }

    public final void onCellLocationChanged(CellLocation cellLocation) {
        try {
            this.nw.rY = System.currentTimeMillis();
            this.nw.sc = cellLocation;
            super.onCellLocationChanged(cellLocation);
        } catch (Exception e) {
        }
    }

    public final void onServiceStateChanged(ServiceState serviceState) {
        try {
            if (serviceState.getState() != 0) {
                this.nw.rP = false;
            } else {
                this.nw.rP = true;
                String[] uP = bf.tB(this.nw.rG);
                this.nw.rT = Integer.parseInt(uP[0]);
                this.nw.rU = Integer.parseInt(uP[1]);
            }
            super.onServiceStateChanged(serviceState);
        } catch (Exception e) {
        }
    }

    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        try {
            if (this.nw.rN) {
                this.nw.rO = signalStrength.getCdmaDbm();
            } else {
                this.nw.rO = signalStrength.getGsmSignalStrength();
                if (this.nw.rO != 99) {
                    this.nw.rO = (this.nw.rO * 2) - 113;
                } else {
                    this.nw.rO = -1;
                }
            }
            super.onSignalStrengthsChanged(signalStrength);
        } catch (Exception e) {
        }
    }
}
