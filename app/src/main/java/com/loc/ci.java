package com.loc;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;

/* compiled from: Unknown */
final class ci extends PhoneStateListener {
    private /* synthetic */ cg a;

    private ci(cg cgVar) {
        this.a = cgVar;
    }

    public final void onCellLocationChanged(CellLocation cellLocation) {
        try {
            this.a.w = System.currentTimeMillis();
            this.a.A = cellLocation;
            super.onCellLocationChanged(cellLocation);
        } catch (Exception e) {
        }
    }

    public final void onServiceStateChanged(ServiceState serviceState) {
        try {
            if (serviceState.getState() != 0) {
                this.a.n = false;
            } else {
                this.a.n = true;
                String[] a = cg.b(this.a.e);
                this.a.r = Integer.parseInt(a[0]);
                this.a.s = Integer.parseInt(a[1]);
            }
            super.onServiceStateChanged(serviceState);
        } catch (Exception e) {
        }
    }

    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        try {
            if (this.a.l) {
                this.a.m = signalStrength.getCdmaDbm();
            } else {
                this.a.m = signalStrength.getGsmSignalStrength();
                if (this.a.m != 99) {
                    this.a.m = (this.a.m * 2) - 113;
                } else {
                    this.a.m = -1;
                }
            }
            super.onSignalStrengthsChanged(signalStrength);
        } catch (Exception e) {
        }
    }
}
