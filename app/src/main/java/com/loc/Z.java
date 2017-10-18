package com.loc;

import android.location.Location;
import android.telephony.CellLocation;
import java.util.List;

/* compiled from: Unknown */
public final class Z {
    private static int c = 10;
    private static int d = 100;
    private static float mF = 0.5f;
    private bf mE;
    protected bA mG = new bA(this);
    protected av mH = new av(this);

    protected Z(bf bfVar) {
        this.mE = bfVar;
    }

    protected static void nO() {
    }

    protected static void nQ(int i) {
        c = i;
    }

    protected static void nR(int i) {
        d = i;
    }

    protected final boolean nP(Location location) {
        aH aHVar = null;
        boolean z = false;
        if (this.mE == null) {
            return false;
        }
        List tL = this.mE.tL();
        if (tL == null || location == null) {
            return false;
        }
        "cell.list.size: " + tL.size();
        if (tL.size() >= 2) {
            aH aHVar2 = new aH((CellLocation) tL.get(1));
            if (this.mH.nC != null) {
                boolean z2 = location.distanceTo(this.mH.nC) > ((float) d);
                if (!z2) {
                    aHVar = this.mH.nB;
                    z2 = aHVar2.e == aHVar.e && aHVar2.d == aHVar.d && aHVar2.c == aHVar.c && aHVar2.b == aHVar.b && aHVar2.a == aHVar.a;
                    z2 = !z2;
                }
                "collect cell?: " + z2;
                z = z2;
                aHVar = aHVar2;
            } else {
                aHVar = aHVar2;
                z = true;
            }
        }
        if (z) {
            this.mH.nB = aHVar;
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final boolean nS(android.location.Location r14) {
        /*
        r13 = this;
        r4 = 1;
        r0 = 0;
        r3 = 0;
        r1 = r13.mE;
        if (r1 == 0) goto L_0x0019;
    L_0x0007:
        r1 = r13.mE;
        r1 = r1.tM(r3);
        r2 = r1.size();
        r5 = 2;
        if (r2 >= r5) goto L_0x001a;
    L_0x0014:
        r1 = r0;
        r2 = r3;
    L_0x0016:
        if (r2 != 0) goto L_0x00a7;
    L_0x0018:
        return r2;
    L_0x0019:
        return r3;
    L_0x001a:
        r0 = r1.get(r4);
        r0 = (java.util.List) r0;
        r1 = r13.mG;
        r1 = r1.tm;
        if (r1 == 0) goto L_0x002b;
    L_0x0026:
        if (r0 != 0) goto L_0x002f;
    L_0x0028:
        r1 = r0;
        r2 = r3;
        goto L_0x0016;
    L_0x002b:
        r1 = r4;
    L_0x002c:
        r2 = r1;
        r1 = r0;
        goto L_0x0016;
    L_0x002f:
        r1 = r0.size();
        if (r1 <= 0) goto L_0x0028;
    L_0x0035:
        r1 = r13.mG;
        r1 = r1.tm;
        r1 = r14.distanceTo(r1);
        r2 = c;
        r2 = (float) r2;
        r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1));
        if (r1 <= 0) goto L_0x0054;
    L_0x0044:
        r1 = r4;
    L_0x0045:
        if (r1 != 0) goto L_0x002c;
    L_0x0047:
        r1 = r13.mG;
        r7 = r1.tl;
        r8 = mF;
        if (r0 != 0) goto L_0x0056;
    L_0x004f:
        r1 = r3;
    L_0x0050:
        if (r1 == 0) goto L_0x002b;
    L_0x0052:
        r1 = r3;
        goto L_0x002c;
    L_0x0054:
        r1 = r3;
        goto L_0x0045;
    L_0x0056:
        if (r7 == 0) goto L_0x004f;
    L_0x0058:
        if (r0 == 0) goto L_0x004f;
    L_0x005a:
        if (r7 == 0) goto L_0x004f;
    L_0x005c:
        r9 = r0.size();
        r10 = r7.size();
        r1 = r9 + r10;
        r11 = (float) r1;
        if (r9 == 0) goto L_0x007c;
    L_0x0069:
        if (r9 == 0) goto L_0x004f;
    L_0x006b:
        if (r10 == 0) goto L_0x004f;
    L_0x006d:
        r6 = r3;
        r2 = r3;
    L_0x006f:
        if (r6 < r9) goto L_0x007f;
    L_0x0071:
        r1 = r2 << 1;
        r1 = (float) r1;
        r2 = r11 * r8;
        r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1));
        if (r1 < 0) goto L_0x004f;
    L_0x007a:
        r1 = r4;
        goto L_0x0050;
    L_0x007c:
        if (r10 == 0) goto L_0x007a;
    L_0x007e:
        goto L_0x0069;
    L_0x007f:
        r1 = r0.get(r6);
        r1 = (android.net.wifi.ScanResult) r1;
        r12 = r1.BSSID;
        if (r12 != 0) goto L_0x008f;
    L_0x0089:
        r1 = r2;
    L_0x008a:
        r2 = r6 + 1;
        r6 = r2;
        r2 = r1;
        goto L_0x006f;
    L_0x008f:
        r5 = r3;
    L_0x0090:
        if (r5 >= r10) goto L_0x0089;
    L_0x0092:
        r1 = r7.get(r5);
        r1 = (com.loc.bi) r1;
        r1 = r1.a;
        r1 = r12.equals(r1);
        if (r1 != 0) goto L_0x00a4;
    L_0x00a0:
        r1 = r5 + 1;
        r5 = r1;
        goto L_0x0090;
    L_0x00a4:
        r1 = r2 + 1;
        goto L_0x008a;
    L_0x00a7:
        r0 = r13.mG;
        r0 = r0.tl;
        r0.clear();
        r4 = r1.size();
    L_0x00b2:
        if (r3 >= r4) goto L_0x0018;
    L_0x00b4:
        r0 = r13.mG;
        r5 = r0.tl;
        r6 = new com.loc.bi;
        r0 = r1.get(r3);
        r0 = (android.net.wifi.ScanResult) r0;
        r0 = r0.BSSID;
        r6.<init>(r0);
        r5.add(r6);
        r3 = r3 + 1;
        goto L_0x00b2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.Z.nS(android.location.Location):boolean");
    }
}
