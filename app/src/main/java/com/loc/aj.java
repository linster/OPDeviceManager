package com.loc;

import java.util.Locale;

public class aj {
    public String a = "";
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public String mS = "";
    public int mT = 0;
    public int mU = 0;
    public int mV = 0;
    public int mW = 0;
    public int mX = -113;
    public int mY = 9;

    protected aj(int i) {
        this.mY = i;
    }

    public boolean ou(aj ajVar) {
        if (ajVar == null) {
            return false;
        }
        switch (ajVar.mY) {
            case 1:
                return this.mY == 1 && ajVar.c == this.c && ajVar.d == this.d && ajVar.mS != null && ajVar.mS.equals(this.mS);
            case 2:
                return this.mY == 2 && ajVar.mW == this.mW && ajVar.mV == this.mV && ajVar.mU == this.mU;
            default:
                return false;
        }
    }

    public String toString() {
        String str = "unknown";
        switch (this.mY) {
            case 1:
                return String.format(Locale.US, "GSM lac=%d, cid=%d, mnc=%s", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), this.mS});
            case 2:
                return String.format(Locale.US, "CDMA bid=%d, nid=%d, sid=%d", new Object[]{Integer.valueOf(this.mW), Integer.valueOf(this.mV), Integer.valueOf(this.mU)});
            default:
                return str;
        }
    }
}
