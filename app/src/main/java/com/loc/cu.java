package com.loc;

import android.location.Location;
import android.telephony.CellLocation;

import java.util.List;

/* compiled from: Unknown */
public final class cu {
    private static int c;
    private static int d;
    private static float f;
    protected cy a;
    protected cv b;
    private cg e;

    static {
        c = 10;
        d = 100;
        f = 0.5f;
    }

    protected cu(cg cgVar) {
        this.a = new cy(this);
        this.b = new cv(this);
        this.e = cgVar;
    }

    protected static void a() {
    }

    protected static void a(int i) {
        c = i;
    }

    protected static void b(int i) {
        d = i;
    }

    protected final boolean a(Location location) {
        cw cwVar = null;
        boolean z = false;
        if (this.e == null) {
            return false;
        }
        List j = this.e.j();
        if (j == null || location == null) {
            return false;
        }
        "cell.list.size: " + j.size();
        if (j.size() >= 2) {
            cw cwVar2 = new cw((CellLocation) j.get(1));
            if (this.b.b != null) {
                boolean z2 = location.distanceTo(this.b.b) > ((float) d);
                if (!z2) {
                    cwVar = this.b.a;
                    z2 = cwVar2.e == cwVar.e && cwVar2.d == cwVar.d && cwVar2.c == cwVar.c && cwVar2.b == cwVar.b && cwVar2.a == cwVar.a;
                    z2 = !z2;
                }
                "collect cell?: " + z2;
                z = z2;
                cwVar = cwVar2;
            } else {
                cwVar = cwVar2;
                z = true;
            }
        }
        if (z) {
            this.b.a = cwVar;
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final boolean b(android.location.Location r14) {
        /*
        r13 = this;
        r4 = 1;
        r0 = 0;
        r3 = 0;
        r1 = r13.e;
        if (r1 == 0) goto L_0x0019;
    L_0x0007:
        r1 = r13.e;
        r1 = r1.a(r3);
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
        r1 = r13.a;
        r1 = r1.b;
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
        r1 = r13.a;
        r1 = r1.b;
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
        r1 = r13.a;
        r7 = r1.a;
        r8 = f;
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
        r1 = (com.loc.cx) r1;
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
        r0 = r13.a;
        r0 = r0.a;
        r0.clear();
        r4 = r1.size();
    L_0x00b2:
        if (r3 >= r4) goto L_0x0018;
    L_0x00b4:
        r0 = r13.a;
        r5 = r0.a;
        r6 = new com.loc.cx;
        r0 = r1.get(r3);
        r0 = (android.net.wifi.ScanResult) r0;
        r0 = r0.BSSID;
        r6.<init>(r0);
        r5.add(r6);
        r3 = r3 + 1;
        goto L_0x00b2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cu.b(android.location.Location):boolean");
    }
}
