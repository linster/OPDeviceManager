package com.loc;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import org.json.JSONObject;

public class bb {
    private static bb ry = null;
    private boolean rA = false;
    private Hashtable rz = new Hashtable();

    private bb() {
    }

    public static synchronized bb ts() {
        bb bbVar;
        synchronized (bb.class) {
            if (ry == null) {
                ry = new bb();
            }
            bbVar = ry;
        }
        return bbVar;
    }

    private void tw() {
        if (!this.rz.isEmpty()) {
            this.rz.clear();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void tt(android.content.Context r8, java.lang.String r9, java.lang.String r10, int r11, long r12, boolean r14) {
        /*
        r7 = this;
        monitor-enter(r7);
        if (r8 != 0) goto L_0x0005;
    L_0x0003:
        monitor-exit(r7);
        return;
    L_0x0005:
        r0 = android.text.TextUtils.isEmpty(r9);	 Catch:{ all -> 0x0044 }
        if (r0 != 0) goto L_0x0003;
    L_0x000b:
        r0 = com.loc.bN.tL;	 Catch:{ all -> 0x0044 }
        if (r0 == 0) goto L_0x003c;
    L_0x000f:
        r0 = r7.rz;	 Catch:{ all -> 0x0044 }
        r0 = r0.get(r9);	 Catch:{ all -> 0x0044 }
        r0 = (org.json.JSONObject) r0;	 Catch:{ all -> 0x0044 }
        if (r0 == 0) goto L_0x003e;
    L_0x0019:
        r1 = "x";
        r0.put(r1, r10);	 Catch:{ Exception -> 0x0056 }
        r1 = "time";
        r0.put(r1, r12);	 Catch:{ Exception -> 0x0056 }
        r1 = r7.rz;	 Catch:{ Exception -> 0x0056 }
        r1 = r1.containsKey(r9);	 Catch:{ Exception -> 0x0056 }
        if (r1 != 0) goto L_0x0047;
    L_0x002d:
        r1 = "num";
        r0.put(r1, r11);	 Catch:{ Exception -> 0x0056 }
    L_0x0033:
        r1 = r7.rz;	 Catch:{ all -> 0x0044 }
        r1.put(r9, r0);	 Catch:{ all -> 0x0044 }
        if (r14 != 0) goto L_0x0058;
    L_0x003a:
        monitor-exit(r7);
        return;
    L_0x003c:
        monitor-exit(r7);
        return;
    L_0x003e:
        r0 = new org.json.JSONObject;	 Catch:{ all -> 0x0044 }
        r0.<init>();	 Catch:{ all -> 0x0044 }
        goto L_0x0019;
    L_0x0044:
        r0 = move-exception;
        monitor-exit(r7);
        throw r0;
    L_0x0047:
        r1 = "num";
        r2 = "num";
        r2 = r0.getInt(r2);	 Catch:{ Exception -> 0x0056 }
        r2 = r2 + r11;
        r0.put(r1, r2);	 Catch:{ Exception -> 0x0056 }
        goto L_0x0033;
    L_0x0056:
        r1 = move-exception;
        goto L_0x0033;
    L_0x0058:
        r0 = com.loc.S.nu();	 Catch:{ Exception -> 0x0064 }
        r1 = r8;
        r2 = r9;
        r3 = r10;
        r4 = r12;
        r0.nz(r1, r2, r3, r4);	 Catch:{ Exception -> 0x0064 }
        goto L_0x003a;
    L_0x0064:
        r0 = move-exception;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bb.tt(android.content.Context, java.lang.String, java.lang.String, int, long, boolean):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void tu(android.content.Context r10, java.lang.String r11, com.autonavi.aps.amapapi.model.AmapLoc r12) {
        /*
        r9 = this;
        r3 = 0;
        monitor-enter(r9);
        r0 = com.loc.bq.vH(r12);	 Catch:{ all -> 0x006f }
        if (r0 != 0) goto L_0x000a;
    L_0x0008:
        monitor-exit(r9);
        return;
    L_0x000a:
        if (r10 == 0) goto L_0x0008;
    L_0x000c:
        r0 = com.loc.bN.tL;	 Catch:{ all -> 0x006f }
        if (r0 == 0) goto L_0x0008;
    L_0x0010:
        r0 = r9.rz;	 Catch:{ all -> 0x006f }
        r0 = r0.size();	 Catch:{ all -> 0x006f }
        r1 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r0 > r1) goto L_0x004c;
    L_0x001a:
        if (r3 == 0) goto L_0x0062;
    L_0x001c:
        r0 = new org.json.JSONObject;	 Catch:{ all -> 0x006f }
        r0.<init>();	 Catch:{ all -> 0x006f }
        r1 = "key";
        r0.put(r1, r11);	 Catch:{ Exception -> 0x0072 }
        r1 = "lat";
        r4 = r12.yJ();	 Catch:{ Exception -> 0x0072 }
        r0.put(r1, r4);	 Catch:{ Exception -> 0x0072 }
        r1 = "lon";
        r4 = r12.yG();	 Catch:{ Exception -> 0x0072 }
        r0.put(r1, r4);	 Catch:{ Exception -> 0x0072 }
        r4 = r0.toString();	 Catch:{ Exception -> 0x0072 }
        r5 = 1;
        r6 = com.loc.bq.vL();	 Catch:{ Exception -> 0x0072 }
        r8 = 1;
        r1 = r9;
        r2 = r10;
        r1.tt(r2, r3, r4, r5, r6, r8);	 Catch:{ Exception -> 0x0072 }
    L_0x004a:
        monitor-exit(r9);
        return;
    L_0x004c:
        r0 = r12.yJ();	 Catch:{ all -> 0x006f }
        r2 = r12.yG();	 Catch:{ all -> 0x006f }
        r3 = com.loc.al.ov(r0, r2);	 Catch:{ all -> 0x006f }
        r0 = r9.rz;	 Catch:{ all -> 0x006f }
        r0 = r0.containsKey(r3);	 Catch:{ all -> 0x006f }
        if (r0 != 0) goto L_0x001a;
    L_0x0060:
        monitor-exit(r9);
        return;
    L_0x0062:
        r0 = r12.yJ();	 Catch:{ all -> 0x006f }
        r2 = r12.yG();	 Catch:{ all -> 0x006f }
        r3 = com.loc.al.ov(r0, r2);	 Catch:{ all -> 0x006f }
        goto L_0x001c;
    L_0x006f:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
    L_0x0072:
        r0 = move-exception;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bb.tu(android.content.Context, java.lang.String, com.autonavi.aps.amapapi.model.AmapLoc):void");
    }

    public synchronized ArrayList tv() {
        ArrayList arrayList = new ArrayList();
        if (this.rz.isEmpty()) {
            return arrayList;
        }
        Hashtable hashtable = this.rz;
        ArrayList arrayList2 = new ArrayList(hashtable.keySet());
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                JSONObject jSONObject = (JSONObject) hashtable.get(str);
                int i = jSONObject.getInt("num");
                String string = jSONObject.getString("x");
                long j = jSONObject.getLong("time");
                if (i >= 120) {
                    arrayList.add(new af(str, j, i, string));
                }
            } catch (Exception e) {
            }
        }
        Collections.sort(arrayList, new bh(this));
        arrayList2.clear();
        return arrayList;
    }

    public void tx(Context context) {
        if (bN.tL && !this.rA) {
            bq.vM();
            try {
                S.nu().nA(context);
            } catch (Exception e) {
            }
            bq.vM();
            this.rA = true;
        }
    }

    public void ty() {
        ts().tw();
        this.rA = false;
    }
}
