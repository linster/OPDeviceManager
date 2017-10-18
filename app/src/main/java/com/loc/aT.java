package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONObject;

public class aT {
    private static aT qI = null;
    private Hashtable qJ = new Hashtable();
    private long qK = 0;
    private boolean qL = false;

    private aT() {
    }

    public static synchronized aT sb() {
        aT aTVar;
        synchronized (aT.class) {
            if (qI == null) {
                qI = new aT();
            }
            aTVar = qI;
        }
        return aTVar;
    }

    private synchronized r sh(StringBuilder stringBuilder, String str) {
        if (!this.qJ.isEmpty()) {
            if (!TextUtils.isEmpty(stringBuilder)) {
                if (!this.qJ.containsKey(str)) {
                    return null;
                }
                r rVar;
                Hashtable hashtable = new Hashtable();
                Hashtable hashtable2 = new Hashtable();
                Hashtable hashtable3 = new Hashtable();
                ArrayList arrayList = (ArrayList) this.qJ.get(str);
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    rVar = (r) arrayList.get(size);
                    if (!TextUtils.isEmpty(rVar.mj())) {
                        Object obj;
                        String str2;
                        if (si(rVar.mj(), stringBuilder)) {
                            if (sl(rVar.mj(), stringBuilder)) {
                                break;
                            }
                            int i = 1;
                        } else {
                            obj = null;
                        }
                        sj(rVar.mj(), hashtable);
                        sj(stringBuilder.toString(), hashtable2);
                        hashtable3.clear();
                        for (String str22 : hashtable.keySet()) {
                            hashtable3.put(str22, "");
                        }
                        for (String str222 : hashtable2.keySet()) {
                            hashtable3.put(str222, "");
                        }
                        Set keySet = hashtable3.keySet();
                        double[] dArr = new double[keySet.size()];
                        double[] dArr2 = new double[keySet.size()];
                        Iterator it = keySet.iterator();
                        int i2 = 0;
                        while (it != null && it.hasNext()) {
                            str222 = (String) it.next();
                            dArr[i2] = !hashtable.containsKey(str222) ? 0.0d : 1.0d;
                            dArr2[i2] = !hashtable2.containsKey(str222) ? 0.0d : 1.0d;
                            i2++;
                        }
                        keySet.clear();
                        double[] sk = sk(dArr, dArr2);
                        if (sk[0] < 0.800000011920929d && sk[1] < 0.618d) {
                            if (obj != null && sk[0] >= 0.618d) {
                                break;
                            }
                        }
                        break;
                    }
                }
                rVar = null;
                hashtable.clear();
                hashtable2.clear();
                hashtable3.clear();
                return rVar;
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean si(java.lang.String r5, java.lang.StringBuilder r6) {
        /*
        r4 = this;
        r3 = 0;
        r0 = android.text.TextUtils.isEmpty(r5);
        if (r0 == 0) goto L_0x0008;
    L_0x0007:
        return r3;
    L_0x0008:
        r0 = android.text.TextUtils.isEmpty(r6);
        if (r0 != 0) goto L_0x0007;
    L_0x000e:
        r0 = ",access";
        r0 = r5.contains(r0);
        if (r0 != 0) goto L_0x0018;
    L_0x0017:
        return r3;
    L_0x0018:
        r0 = ",access";
        r0 = r6.indexOf(r0);
        r1 = -1;
        if (r0 == r1) goto L_0x0017;
    L_0x0022:
        r0 = ",access";
        r0 = r5.split(r0);
        r1 = r0[r3];
        r2 = "#";
        r1 = r1.contains(r2);
        if (r1 != 0) goto L_0x0059;
    L_0x0034:
        r0 = r0[r3];
    L_0x0036:
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 != 0) goto L_0x006b;
    L_0x003c:
        r1 = r6.toString();
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r0 = r2.append(r0);
        r2 = ",access";
        r0 = r0.append(r2);
        r0 = r0.toString();
        r0 = r1.contains(r0);
        return r0;
    L_0x0059:
        r1 = r0[r3];
        r0 = r0[r3];
        r2 = "#";
        r0 = r0.lastIndexOf(r2);
        r0 = r0 + 1;
        r0 = r1.substring(r0);
        goto L_0x0036;
    L_0x006b:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aT.si(java.lang.String, java.lang.StringBuilder):boolean");
    }

    private void sj(String str, Hashtable hashtable) {
        if (!TextUtils.isEmpty(str)) {
            hashtable.clear();
            for (Object obj : str.split("#")) {
                if (!(TextUtils.isEmpty(obj) || obj.contains("|"))) {
                    hashtable.put(obj, "");
                }
            }
        }
    }

    private double[] sk(double[] dArr, double[] dArr2) {
        int i;
        double[] dArr3 = new double[3];
        double d = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        int i2 = 0;
        int i3 = 0;
        for (i = 0; i < dArr.length; i++) {
            d2 += dArr[i] * dArr[i];
            d3 += dArr2[i] * dArr2[i];
            d += dArr[i] * dArr2[i];
            if (dArr2[i] == 1.0d) {
                i2++;
                if (dArr[i] == 1.0d) {
                    i3++;
                }
            }
        }
        dArr3[0] = d / (Math.sqrt(d3) * Math.sqrt(d2));
        dArr3[1] = (((double) i3) * 1.0d) / ((double) i2);
        dArr3[2] = (double) i3;
        for (i = 0; i < dArr3.length - 1; i++) {
            if (dArr3[i] > 1.0d) {
                dArr3[i] = 1.0d;
            }
            dArr3[i] = Double.parseDouble(bq.wx(Double.valueOf(dArr3[i]), "#.00"));
        }
        return dArr3;
    }

    public synchronized void sc(String str, StringBuilder stringBuilder, AmapLoc amapLoc, Context context, boolean z) {
        int i = 0;
        synchronized (this) {
            if (!sf(str, amapLoc)) {
                return;
            } else if (amapLoc.yW().equals("mem")) {
                return;
            } else if (amapLoc.yW().equals("file")) {
                return;
            } else if (amapLoc.yY().equals("-3")) {
                return;
            } else {
                if (se()) {
                    sg();
                }
                JSONObject zE = amapLoc.zE();
                if (bq.wb(zE, "offpct")) {
                    zE.remove("offpct");
                    amapLoc.zF(zE);
                }
                if (str.contains("wifi")) {
                    if (TextUtils.isEmpty(stringBuilder)) {
                        return;
                    }
                    if (amapLoc.yN() >= 300.0f) {
                        for (String contains : stringBuilder.toString().split("#")) {
                            if (contains.contains(",")) {
                                i++;
                            }
                        }
                        if (i >= 8) {
                            return;
                        }
                    } else if (amapLoc.yN() <= 10.0f) {
                        return;
                    }
                    if (str.contains("cgiwifi") && !TextUtils.isEmpty(amapLoc.zB())) {
                        String replace = str.replace("cgiwifi", "cgi");
                        AmapLoc zC = amapLoc.zC();
                        if (bq.vH(zC)) {
                            sc(replace, new StringBuilder(), zC, context, true);
                        }
                    }
                } else if (str.contains("cgi")) {
                    if (stringBuilder.indexOf(",") != -1) {
                        return;
                    } else if (amapLoc.yY().equals("4")) {
                        return;
                    }
                }
                if (bq.vH(sd(str, stringBuilder))) {
                    return;
                }
                this.qK = bq.vM();
                r rVar = new r(this);
                rVar.mi(amapLoc);
                rVar.mk(!TextUtils.isEmpty(stringBuilder) ? stringBuilder.toString() : null);
                if (this.qJ.containsKey(str)) {
                    ((ArrayList) this.qJ.get(str)).add(rVar);
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(rVar);
                    this.qJ.put(str, arrayList);
                }
                if (z) {
                    try {
                        S.nu().nv(str, amapLoc, stringBuilder, context);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.autonavi.aps.amapapi.model.AmapLoc sd(java.lang.String r4, java.lang.StringBuilder r5) {
        /*
        r3 = this;
        r1 = 0;
        monitor-enter(r3);
        r0 = "gps";
        r0 = r4.contains(r0);	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x003c;
    L_0x000b:
        r0 = r3.se();	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x003e;
    L_0x0011:
        r0 = r3.qJ;	 Catch:{ all -> 0x00a9 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x0043;
    L_0x0019:
        r0 = "found#⊗";
        r0 = "cgiwifi";
        r0 = r4.contains(r0);	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x0045;
    L_0x0025:
        r0 = "wifi";
        r0 = r4.contains(r0);	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x004f;
    L_0x002e:
        r0 = "cgi";
        r0 = r4.contains(r0);	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x0059;
    L_0x0037:
        r0 = r1;
    L_0x0038:
        if (r0 != 0) goto L_0x0078;
    L_0x003a:
        monitor-exit(r3);
        return r1;
    L_0x003c:
        monitor-exit(r3);
        return r1;
    L_0x003e:
        r3.sg();	 Catch:{ all -> 0x00a9 }
        monitor-exit(r3);
        return r1;
    L_0x0043:
        monitor-exit(r3);
        return r1;
    L_0x0045:
        r0 = r3.sh(r5, r4);	 Catch:{ all -> 0x00a9 }
        if (r0 == 0) goto L_0x0038;
    L_0x004b:
        r2 = "found#cgiwifi";
        goto L_0x0038;
    L_0x004f:
        r0 = r3.sh(r5, r4);	 Catch:{ all -> 0x00a9 }
        if (r0 == 0) goto L_0x0038;
    L_0x0055:
        r2 = "found#wifi";
        goto L_0x0038;
    L_0x0059:
        r0 = r3.qJ;	 Catch:{ all -> 0x00a9 }
        r0 = r0.containsKey(r4);	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x0068;
    L_0x0061:
        r0 = r1;
    L_0x0062:
        if (r0 == 0) goto L_0x0038;
    L_0x0064:
        r2 = "found#cgi";
        goto L_0x0038;
    L_0x0068:
        r0 = r3.qJ;	 Catch:{ all -> 0x00a9 }
        r0 = r0.get(r4);	 Catch:{ all -> 0x00a9 }
        r0 = (java.util.ArrayList) r0;	 Catch:{ all -> 0x00a9 }
        r2 = 0;
        r0 = r0.get(r2);	 Catch:{ all -> 0x00a9 }
        r0 = (com.loc.r) r0;	 Catch:{ all -> 0x00a9 }
        goto L_0x0062;
    L_0x0078:
        r2 = r0.mh();	 Catch:{ all -> 0x00a9 }
        r2 = com.loc.bq.vH(r2);	 Catch:{ all -> 0x00a9 }
        if (r2 == 0) goto L_0x003a;
    L_0x0082:
        r1 = r0.mh();	 Catch:{ all -> 0x00a9 }
        r2 = "mem";
        r1.yX(r2);	 Catch:{ all -> 0x00a9 }
        r1 = com.loc.H.lJ;	 Catch:{ all -> 0x00a9 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ all -> 0x00a9 }
        if (r1 != 0) goto L_0x009a;
    L_0x0094:
        r0 = r0.mh();	 Catch:{ all -> 0x00a9 }
        monitor-exit(r3);
        return r0;
    L_0x009a:
        r1 = r0.mh();	 Catch:{ all -> 0x00a9 }
        r1 = r1.zz();	 Catch:{ all -> 0x00a9 }
        r1 = java.lang.String.valueOf(r1);	 Catch:{ all -> 0x00a9 }
        com.loc.H.lJ = r1;	 Catch:{ all -> 0x00a9 }
        goto L_0x0094;
    L_0x00a9:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aT.sd(java.lang.String, java.lang.StringBuilder):com.autonavi.aps.amapapi.model.AmapLoc");
    }

    public boolean se() {
        long vM = bq.vM() - this.qK;
        if (this.qK == 0) {
            return false;
        }
        if (this.qJ.size() > 360) {
            return true;
        }
        return !((vM > 36000000 ? 1 : (vM == 36000000 ? 0 : -1)) <= 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sf(java.lang.String r4, com.autonavi.aps.amapapi.model.AmapLoc r5) {
        /*
        r3 = this;
        r1 = 0;
        r0 = android.text.TextUtils.isEmpty(r4);
        if (r0 == 0) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = com.loc.bq.vH(r5);
        if (r0 == 0) goto L_0x0007;
    L_0x000e:
        r0 = "#";
        r0 = r4.startsWith(r0);
        if (r0 != 0) goto L_0x0022;
    L_0x0017:
        r0 = 1;
        r2 = "network";
        r2 = r4.contains(r2);
        if (r2 == 0) goto L_0x0023;
    L_0x0021:
        return r0;
    L_0x0022:
        return r1;
    L_0x0023:
        r0 = r1;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aT.sf(java.lang.String, com.autonavi.aps.amapapi.model.AmapLoc):boolean");
    }

    public void sg() {
        this.qK = 0;
        if (!this.qJ.isEmpty()) {
            this.qJ.clear();
        }
        this.qL = false;
    }

    public boolean sl(String str, StringBuilder stringBuilder) {
        String[] split = str.split("#");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < split.length) {
            if (split[i].contains(",nb") || split[i].contains(",access")) {
                arrayList.add(split[i]);
            }
            i++;
        }
        String[] split2 = stringBuilder.toString().split("#");
        i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < split2.length) {
            if (split2[i].contains(",nb") || split2[i].contains(",access")) {
                i2++;
                if (arrayList.contains(split2[i])) {
                    i3++;
                }
            }
            i++;
        }
        return ((double) (i3 * 2)) >= ((double) (arrayList.size() + i2)) * 0.618d;
    }

    public void sm(Context context) {
        if (!this.qL) {
            bq.vM();
            try {
                S.nu().nw(context);
            } catch (Exception e) {
            }
            bq.vM();
            this.qL = true;
        }
    }
}
