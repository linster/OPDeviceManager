package com.loc;

import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: Unknown */
public final class bp implements Listener, NmeaListener {
    private String sA = null;
    private String sB = null;
    private String sC = null;
    private /* synthetic */ aF sD;
    private long sw = 0;
    private long sx = 0;
    private boolean sy = false;
    private List sz = new ArrayList();

    protected bp(aF aFVar) {
        this.sD = aFVar;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onGpsStatusChanged(int r9) {
        /*
        r8 = this;
        r7 = 4;
        r1 = 0;
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oQ;	 Catch:{ Exception -> 0x00ba }
        if (r0 == 0) goto L_0x000e;
    L_0x000a:
        switch(r9) {
            case 2: goto L_0x0131;
            case 3: goto L_0x000d;
            case 4: goto L_0x000f;
            default: goto L_0x000d;
        };	 Catch:{ Exception -> 0x00ba }
    L_0x000d:
        return;
    L_0x000e:
        return;
    L_0x000f:
        r0 = com.loc.aF.oE;	 Catch:{ Exception -> 0x00ba }
        if (r0 == 0) goto L_0x0095;
    L_0x0013:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.pg;	 Catch:{ Exception -> 0x00ba }
        if (r0 == 0) goto L_0x00a8;
    L_0x001b:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oQ;	 Catch:{ Exception -> 0x00ba }
        r2 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r2 = r2.pg;	 Catch:{ Exception -> 0x00ba }
        r0.getGpsStatus(r2);	 Catch:{ Exception -> 0x00ba }
    L_0x002a:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.pg;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.getSatellites();	 Catch:{ Exception -> 0x00ba }
        r4 = r0.iterator();	 Catch:{ Exception -> 0x00ba }
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r2 = 0;
        r0.ph = 0;	 Catch:{ Exception -> 0x00ba }
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r2 = 0;
        r0.pi = 0;	 Catch:{ Exception -> 0x00ba }
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r2 = new java.util.HashMap;	 Catch:{ Exception -> 0x00ba }
        r2.<init>();	 Catch:{ Exception -> 0x00ba }
        r0.pj = r2;	 Catch:{ Exception -> 0x00ba }
        r3 = r1;
        r2 = r1;
    L_0x0050:
        r0 = r4.hasNext();	 Catch:{ Exception -> 0x00ba }
        if (r0 != 0) goto L_0x00bc;
    L_0x0056:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oM;	 Catch:{ Exception -> 0x00ba }
        r4 = -1;
        if (r0 != r4) goto L_0x00ec;
    L_0x005f:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0.oM = r2;	 Catch:{ Exception -> 0x00ba }
        if (r2 < r7) goto L_0x0104;
    L_0x0066:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oR;	 Catch:{ Exception -> 0x00ba }
        if (r0 != 0) goto L_0x0117;
    L_0x006e:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0.pk = r1;	 Catch:{ Exception -> 0x00ba }
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r1 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r1 = r1.pj;	 Catch:{ Exception -> 0x00ba }
        r0.qB(r1);	 Catch:{ Exception -> 0x00ba }
        r0 = com.loc.aF.oE;	 Catch:{ Exception -> 0x00ba }
        if (r0 != 0) goto L_0x0122;
    L_0x0082:
        r0 = 3;
        if (r2 <= r0) goto L_0x0123;
    L_0x0085:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oQ;	 Catch:{ Exception -> 0x00ba }
        r1 = "gps";
        r0 = r0.getLastKnownLocation(r1);	 Catch:{ Exception -> 0x00ba }
        if (r0 != 0) goto L_0x0129;
    L_0x0094:
        return;
    L_0x0095:
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ba }
        r4 = r8.sw;	 Catch:{ Exception -> 0x00ba }
        r2 = r2 - r4;
        r4 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 < 0) goto L_0x00a6;
    L_0x00a2:
        r0 = 1;
    L_0x00a3:
        if (r0 != 0) goto L_0x0013;
    L_0x00a5:
        return;
    L_0x00a6:
        r0 = r1;
        goto L_0x00a3;
    L_0x00a8:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r2 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r2 = r2.oQ;	 Catch:{ Exception -> 0x00ba }
        r3 = 0;
        r2 = r2.getGpsStatus(r3);	 Catch:{ Exception -> 0x00ba }
        r0.pg = r2;	 Catch:{ Exception -> 0x00ba }
        goto L_0x002a;
    L_0x00ba:
        r0 = move-exception;
        goto L_0x0094;
    L_0x00bc:
        r0 = r4.next();	 Catch:{ Exception -> 0x00ba }
        r0 = (android.location.GpsSatellite) r0;	 Catch:{ Exception -> 0x00ba }
        r3 = r3 + 1;
        r5 = r0.usedInFix();	 Catch:{ Exception -> 0x00ba }
        if (r5 != 0) goto L_0x00e9;
    L_0x00ca:
        r5 = r0.getSnr();	 Catch:{ Exception -> 0x00ba }
        r6 = 0;
        r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1));
        if (r5 <= 0) goto L_0x00d5;
    L_0x00d3:
        r1 = r1 + 1;
    L_0x00d5:
        r0 = r0.getSnr();	 Catch:{ Exception -> 0x00ba }
        r5 = com.loc.aF.pu;	 Catch:{ Exception -> 0x00ba }
        r5 = (float) r5;	 Catch:{ Exception -> 0x00ba }
        r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1));
        if (r0 < 0) goto L_0x0050;
    L_0x00e2:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0.pi = r0.pi + 1;	 Catch:{ Exception -> 0x00ba }
        goto L_0x0050;
    L_0x00e9:
        r2 = r2 + 1;
        goto L_0x00ca;
    L_0x00ec:
        if (r2 >= r7) goto L_0x00fa;
    L_0x00ee:
        if (r2 >= r7) goto L_0x006e;
    L_0x00f0:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oM;	 Catch:{ Exception -> 0x00ba }
        if (r0 >= r7) goto L_0x005f;
    L_0x00f8:
        goto L_0x006e;
    L_0x00fa:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oM;	 Catch:{ Exception -> 0x00ba }
        if (r0 >= r7) goto L_0x00ee;
    L_0x0102:
        goto L_0x005f;
    L_0x0104:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oR;	 Catch:{ Exception -> 0x00ba }
        if (r0 == 0) goto L_0x006e;
    L_0x010c:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oR;	 Catch:{ Exception -> 0x00ba }
        r0.ub();	 Catch:{ Exception -> 0x00ba }
        goto L_0x006e;
    L_0x0117:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r0 = r0.oR;	 Catch:{ Exception -> 0x00ba }
        r0.ua();	 Catch:{ Exception -> 0x00ba }
        goto L_0x006e;
    L_0x0122:
        return;
    L_0x0123:
        r0 = 15;
        if (r3 > r0) goto L_0x0085;
    L_0x0127:
        goto L_0x000d;
    L_0x0129:
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ba }
        r8.sw = r0;	 Catch:{ Exception -> 0x00ba }
        goto L_0x0094;
    L_0x0131:
        r0 = r8.sD;	 Catch:{ Exception -> 0x00ba }
        r1 = 0;
        r0.pk = r1;	 Catch:{ Exception -> 0x00ba }
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bp.onGpsStatusChanged(int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onNmeaReceived(long r4, java.lang.String r6) {
        /*
        r3 = this;
        r0 = com.loc.aF.oE;	 Catch:{ Exception -> 0x0040 }
        if (r0 == 0) goto L_0x0007;
    L_0x0004:
        if (r6 != 0) goto L_0x0008;
    L_0x0006:
        return;
    L_0x0007:
        return;
    L_0x0008:
        r0 = "";
        r0 = r6.equals(r0);	 Catch:{ Exception -> 0x0040 }
        if (r0 != 0) goto L_0x0006;
    L_0x0011:
        r0 = r6.length();	 Catch:{ Exception -> 0x0040 }
        r1 = 9;
        if (r0 >= r1) goto L_0x001a;
    L_0x0019:
        return;
    L_0x001a:
        r0 = r6.length();	 Catch:{ Exception -> 0x0040 }
        r1 = 150; // 0x96 float:2.1E-43 double:7.4E-322;
        if (r0 > r1) goto L_0x0019;
    L_0x0022:
        r0 = r3.sD;	 Catch:{ Exception -> 0x0040 }
        r0 = r0.pb;	 Catch:{ Exception -> 0x0040 }
        if (r0 != 0) goto L_0x002b;
    L_0x002a:
        return;
    L_0x002b:
        r0 = r3.sD;	 Catch:{ Exception -> 0x0040 }
        r0 = r0.pb;	 Catch:{ Exception -> 0x0040 }
        r1 = r3.sD;	 Catch:{ Exception -> 0x0040 }
        r1 = r1.pb;	 Catch:{ Exception -> 0x0040 }
        r2 = 1;
        r1 = r1.obtainMessage(r2, r6);	 Catch:{ Exception -> 0x0040 }
        r0.sendMessage(r1);	 Catch:{ Exception -> 0x0040 }
        goto L_0x002a;
    L_0x0040:
        r0 = move-exception;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bp.onNmeaReceived(long, java.lang.String):void");
    }

    public final void vA(String str) {
        if (!(System.currentTimeMillis() - this.sx <= 400) && this.sy && this.sz.size() > 0) {
            try {
                W w = new W(this.sz, this.sA, null, this.sC);
                if (w.nE()) {
                    this.sD.pl = aF.ri(this.sD, w, this.sD.pi);
                    if (this.sD.pl > 0) {
                        aF.rk(this.sD, String.format(Locale.CHINA, "&nmea=%.1f|%.1f&g_tp=%d", new Object[]{Double.valueOf(w.nG()), Double.valueOf(w.nF()), Integer.valueOf(this.sD.pl)}));
                    }
                } else {
                    this.sD.pl = 0;
                }
            } catch (Exception e) {
                this.sD.pl = 0;
            }
            this.sz.clear();
            this.sC = null;
            this.sB = null;
            this.sA = null;
            this.sy = false;
        }
        if (str.startsWith("$GPGGA")) {
            this.sy = true;
            this.sA = str.trim();
        } else if (str.startsWith("$GPGSV")) {
            this.sz.add(str.trim());
        } else if (str.startsWith("$GPGSA")) {
            this.sC = str.trim();
        }
        this.sx = System.currentTimeMillis();
    }
}
