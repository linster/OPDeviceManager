package com.loc;

import com.autonavi.aps.amapapi.model.AmapLoc;

/* compiled from: LocFilter */
public class aw {
    private static aw a;
    private AmapLoc b;
    private long c;
    private long d;

    static {
        a = null;
    }

    private aw() {
        this.b = null;
        this.c = 0;
        this.d = 0;
    }

    public static synchronized aw a() {
        aw awVar;
        synchronized (aw.class) {
            if (a == null) {
                a = new aw();
            }
            awVar = a;
        }
        return awVar;
    }

    public synchronized void b() {
        this.b = null;
        this.c = 0;
        this.d = 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.autonavi.aps.amapapi.model.AmapLoc a(com.autonavi.aps.amapapi.model.AmapLoc r13) {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        r0 = com.loc.br.a(r0);	 Catch:{ all -> 0x018e }
        if (r0 != 0) goto L_0x0015;
    L_0x0009:
        r0 = com.loc.br.b();	 Catch:{ all -> 0x018e }
        r12.c = r0;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x0015:
        r0 = com.loc.br.a(r13);	 Catch:{ all -> 0x018e }
        if (r0 == 0) goto L_0x0009;
    L_0x001b:
        r0 = r13.i();	 Catch:{ all -> 0x018e }
        r2 = r12.b;	 Catch:{ all -> 0x018e }
        r2 = r2.i();	 Catch:{ all -> 0x018e }
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 != 0) goto L_0x0035;
    L_0x0029:
        r0 = r13.h();	 Catch:{ all -> 0x018e }
        r1 = 1133903872; // 0x43960000 float:300.0 double:5.60222949E-315;
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 >= 0) goto L_0x0035;
    L_0x0033:
        monitor-exit(r12);
        return r13;
    L_0x0035:
        r0 = r13.e();	 Catch:{ all -> 0x018e }
        r1 = "gps";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x018e }
        if (r0 != 0) goto L_0x00ac;
    L_0x0042:
        r0 = r13.w();	 Catch:{ all -> 0x018e }
        r1 = r12.b;	 Catch:{ all -> 0x018e }
        r1 = r1.w();	 Catch:{ all -> 0x018e }
        if (r0 != r1) goto L_0x00b8;
    L_0x004e:
        r0 = r13.v();	 Catch:{ all -> 0x018e }
        r1 = r12.b;	 Catch:{ all -> 0x018e }
        r1 = r1.v();	 Catch:{ all -> 0x018e }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x018e }
        if (r0 == 0) goto L_0x00c4;
    L_0x005e:
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        r1 = com.loc.br.a(r13, r0);	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        r2 = r0.h();	 Catch:{ all -> 0x018e }
        r3 = r13.h();	 Catch:{ all -> 0x018e }
        r4 = r3 - r2;
        r6 = com.loc.br.b();	 Catch:{ all -> 0x018e }
        r8 = r12.c;	 Catch:{ all -> 0x018e }
        r8 = r6 - r8;
        r0 = 1120534528; // 0x42ca0000 float:101.0 double:5.536176153E-315;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0088;
    L_0x007e:
        r0 = 1133871104; // 0x43958000 float:299.0 double:5.602067593E-315;
        r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x00da;
    L_0x0085:
        r0 = 1;
    L_0x0086:
        if (r0 != 0) goto L_0x0096;
    L_0x0088:
        r0 = 1133871104; // 0x43958000 float:299.0 double:5.602067593E-315;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x00f7;
    L_0x008f:
        r0 = 1133871104; // 0x43958000 float:299.0 double:5.602067593E-315;
        r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x00f7;
    L_0x0096:
        r0 = r12.d;	 Catch:{ all -> 0x018e }
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 != 0) goto L_0x00dc;
    L_0x009e:
        r12.d = r6;	 Catch:{ all -> 0x018e }
    L_0x00a0:
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        r0 = r12.c(r0);	 Catch:{ all -> 0x018e }
        r12.b = r0;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x00ac:
        r0 = com.loc.br.b();	 Catch:{ all -> 0x018e }
        r12.c = r0;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x00b8:
        r0 = com.loc.br.b();	 Catch:{ all -> 0x018e }
        r12.c = r0;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x00c4:
        r0 = r13.v();	 Catch:{ all -> 0x018e }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x018e }
        if (r0 != 0) goto L_0x005e;
    L_0x00ce:
        r0 = com.loc.br.b();	 Catch:{ all -> 0x018e }
        r12.c = r0;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x00da:
        r0 = 0;
        goto L_0x0086;
    L_0x00dc:
        r0 = r12.d;	 Catch:{ all -> 0x018e }
        r0 = r6 - r0;
        r2 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 > 0) goto L_0x00f5;
    L_0x00e6:
        r0 = 1;
    L_0x00e7:
        if (r0 != 0) goto L_0x00a0;
    L_0x00e9:
        r12.c = r6;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = 0;
        r12.d = r0;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x00f5:
        r0 = 0;
        goto L_0x00e7;
    L_0x00f7:
        r0 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0110;
    L_0x00fd:
        r0 = 1133871104; // 0x43958000 float:299.0 double:5.602067593E-315;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x0110;
    L_0x0104:
        r12.c = r6;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = 0;
        r12.d = r0;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x0110:
        r0 = 1133871104; // 0x43958000 float:299.0 double:5.602067593E-315;
        r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1));
        if (r0 > 0) goto L_0x011b;
    L_0x0117:
        r10 = 0;
        r12.d = r10;	 Catch:{ all -> 0x018e }
    L_0x011b:
        r0 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0159;
    L_0x0121:
        r0 = (double) r1;	 Catch:{ all -> 0x018e }
        r10 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
        if (r0 <= 0) goto L_0x0159;
    L_0x012b:
        r0 = -1013579776; // 0xffffffffc3960000 float:-300.0 double:NaN;
        r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r0 < 0) goto L_0x013d;
    L_0x0131:
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        r0 = r12.c(r0);	 Catch:{ all -> 0x018e }
        r12.b = r0;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x013d:
        r0 = r2 / r3;
        r1 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 < 0) goto L_0x014d;
    L_0x0145:
        r12.c = r6;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x014d:
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        r0 = r12.c(r0);	 Catch:{ all -> 0x018e }
        r12.b = r0;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x0159:
        r0 = 1133903872; // 0x43960000 float:300.0 double:5.60222949E-315;
        r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r0 < 0) goto L_0x0182;
    L_0x015f:
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0174;
    L_0x0165:
        r0 = 1;
    L_0x0166:
        if (r0 != 0) goto L_0x0176;
    L_0x0168:
        r0 = com.loc.br.b();	 Catch:{ all -> 0x018e }
        r12.c = r0;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x0174:
        r0 = 0;
        goto L_0x0166;
    L_0x0176:
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        r0 = r12.c(r0);	 Catch:{ all -> 0x018e }
        r12.b = r0;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x0182:
        r0 = com.loc.br.b();	 Catch:{ all -> 0x018e }
        r12.c = r0;	 Catch:{ all -> 0x018e }
        r12.b = r13;	 Catch:{ all -> 0x018e }
        r0 = r12.b;	 Catch:{ all -> 0x018e }
        monitor-exit(r12);
        return r0;
    L_0x018e:
        r0 = move-exception;
        monitor-exit(r12);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.a(com.autonavi.aps.amapapi.model.AmapLoc):com.autonavi.aps.amapapi.model.AmapLoc");
    }

    private AmapLoc c(AmapLoc amapLoc) {
        if (br.a(amapLoc)) {
            if (amapLoc.b() == 5 || amapLoc.b() == 6) {
                amapLoc.a(2);
            }
        }
        return amapLoc;
    }

    public AmapLoc b(AmapLoc amapLoc) {
        return amapLoc;
    }
}
