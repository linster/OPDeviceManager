package com.loc;

import android.telephony.CellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

/* compiled from: Unknown */
public final class cw {
    int a;
    int b;
    int c;
    int d;
    int e;

    cw(CellLocation cellLocation) {
        this.a = Integer.MAX_VALUE;
        this.b = Integer.MAX_VALUE;
        this.c = Integer.MAX_VALUE;
        this.d = Integer.MAX_VALUE;
        this.e = Integer.MAX_VALUE;
        if (cellLocation != null) {
            if (cellLocation instanceof GsmCellLocation) {
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                this.e = gsmCellLocation.getCid();
                this.d = gsmCellLocation.getLac();
            } else if (cellLocation instanceof CdmaCellLocation) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                this.c = cdmaCellLocation.getBaseStationId();
                this.b = cdmaCellLocation.getNetworkId();
                this.a = cdmaCellLocation.getSystemId();
            }
        }
    }
}
