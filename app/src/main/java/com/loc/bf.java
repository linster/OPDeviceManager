package com.loc;

import android.content.Context;
import android.text.TextUtils;

import com.autonavi.aps.amapapi.model.AmapLoc;

import java.util.ArrayList;
import java.util.Hashtable;

/* compiled from: Cache */
public class bf {
    private static bf a;
    private Hashtable<String, ArrayList<a>> b;
    private long c;
    private boolean d;

    /* compiled from: Cache */
    public class a {
        final /* synthetic */ bf a;
        private AmapLoc b;
        private String c;

        protected a(bf bfVar) {
            this.a = bfVar;
            this.b = null;
            this.c = null;
        }

        public AmapLoc a() {
            return this.b;
        }

        public void a(AmapLoc amapLoc) {
            this.b = amapLoc;
        }

        public String b() {
            return this.c;
        }

        public void a(String str) {
            if (TextUtils.isEmpty(str)) {
                this.c = null;
            } else {
                this.c = str.replace("##", "#");
            }
        }
    }

    static {
        a = null;
    }

    private bf() {
        this.b = new Hashtable();
        this.c = 0;
        this.d = false;
    }

    public static synchronized bf a() {
        bf bfVar;
        synchronized (bf.class) {
            if (a == null) {
                a = new bf();
            }
            bfVar = a;
        }
        return bfVar;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r7, java.lang.StringBuilder r8, com.autonavi.aps.amapapi.model.AmapLoc r9, android.content.Context r10, boolean r11) {
        /*
        r6 = this;
        r0 = 0;
        monitor-enter(r6);
        r1 = r6.a(r7, r9);	 Catch:{ all -> 0x009e }
        if (r1 == 0) goto L_0x0092;
    L_0x0008:
        r1 = r9.j();	 Catch:{ all -> 0x009e }
        r2 = "mem";
        r1 = r1.equals(r2);	 Catch:{ all -> 0x009e }
        if (r1 != 0) goto L_0x0094;
    L_0x0015:
        r1 = r9.j();	 Catch:{ all -> 0x009e }
        r2 = "file";
        r1 = r1.equals(r2);	 Catch:{ all -> 0x009e }
        if (r1 != 0) goto L_0x0096;
    L_0x0022:
        r1 = r9.k();	 Catch:{ all -> 0x009e }
        r2 = "-3";
        r1 = r1.equals(r2);	 Catch:{ all -> 0x009e }
        if (r1 != 0) goto L_0x0098;
    L_0x002f:
        r1 = r6.b();	 Catch:{ all -> 0x009e }
        if (r1 != 0) goto L_0x009a;
    L_0x0035:
        r1 = r9.z();	 Catch:{ all -> 0x009e }
        r2 = "offpct";
        r2 = com.loc.br.a(r1, r2);	 Catch:{ all -> 0x009e }
        if (r2 != 0) goto L_0x00a1;
    L_0x0042:
        r1 = "wifi";
        r1 = r7.contains(r1);	 Catch:{ all -> 0x009e }
        if (r1 != 0) goto L_0x00ab;
    L_0x004b:
        r0 = "cgi";
        r0 = r7.contains(r0);	 Catch:{ all -> 0x009e }
        if (r0 != 0) goto L_0x0123;
    L_0x0054:
        r0 = r6.a(r7, r8);	 Catch:{ all -> 0x009e }
        r0 = com.loc.br.a(r0);	 Catch:{ all -> 0x009e }
        if (r0 != 0) goto L_0x013e;
    L_0x005e:
        r0 = com.loc.br.b();	 Catch:{ all -> 0x009e }
        r6.c = r0;	 Catch:{ all -> 0x009e }
        r1 = new com.loc.bf$a;	 Catch:{ all -> 0x009e }
        r1.<init>(r6);	 Catch:{ all -> 0x009e }
        r1.a(r9);	 Catch:{ all -> 0x009e }
        r0 = android.text.TextUtils.isEmpty(r8);	 Catch:{ all -> 0x009e }
        if (r0 != 0) goto L_0x0140;
    L_0x0072:
        r0 = r8.toString();	 Catch:{ all -> 0x009e }
    L_0x0076:
        r1.a(r0);	 Catch:{ all -> 0x009e }
        r0 = r6.b;	 Catch:{ all -> 0x009e }
        r0 = r0.containsKey(r7);	 Catch:{ all -> 0x009e }
        if (r0 != 0) goto L_0x0143;
    L_0x0081:
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x009e }
        r0.<init>();	 Catch:{ all -> 0x009e }
        r0.add(r1);	 Catch:{ all -> 0x009e }
        r1 = r6.b;	 Catch:{ all -> 0x009e }
        r1.put(r7, r0);	 Catch:{ all -> 0x009e }
    L_0x008e:
        if (r11 != 0) goto L_0x0150;
    L_0x0090:
        monitor-exit(r6);
        return;
    L_0x0092:
        monitor-exit(r6);
        return;
    L_0x0094:
        monitor-exit(r6);
        return;
    L_0x0096:
        monitor-exit(r6);
        return;
    L_0x0098:
        monitor-exit(r6);
        return;
    L_0x009a:
        r6.c();	 Catch:{ all -> 0x009e }
        goto L_0x0035;
    L_0x009e:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x00a1:
        r2 = "offpct";
        r1.remove(r2);	 Catch:{ all -> 0x009e }
        r9.a(r1);	 Catch:{ all -> 0x009e }
        goto L_0x0042;
    L_0x00ab:
        r1 = android.text.TextUtils.isEmpty(r8);	 Catch:{ all -> 0x009e }
        if (r1 != 0) goto L_0x0102;
    L_0x00b1:
        r1 = r9.h();	 Catch:{ all -> 0x009e }
        r2 = 1133903872; // 0x43960000 float:300.0 double:5.60222949E-315;
        r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1));
        if (r1 < 0) goto L_0x0117;
    L_0x00bb:
        r1 = r8.toString();	 Catch:{ all -> 0x009e }
        r2 = "#";
        r2 = r1.split(r2);	 Catch:{ all -> 0x009e }
        r3 = r2.length;	 Catch:{ all -> 0x009e }
        r1 = r0;
    L_0x00c8:
        if (r1 < r3) goto L_0x0104;
    L_0x00ca:
        r1 = 8;
        if (r0 >= r1) goto L_0x0115;
    L_0x00ce:
        r0 = "cgiwifi";
        r0 = r7.contains(r0);	 Catch:{ all -> 0x009e }
        if (r0 == 0) goto L_0x0054;
    L_0x00d7:
        r0 = r9.x();	 Catch:{ all -> 0x009e }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x009e }
        if (r0 != 0) goto L_0x0054;
    L_0x00e1:
        r0 = "cgiwifi";
        r1 = "cgi";
        r1 = r7.replace(r0, r1);	 Catch:{ all -> 0x009e }
        r3 = r9.y();	 Catch:{ all -> 0x009e }
        r0 = com.loc.br.a(r3);	 Catch:{ all -> 0x009e }
        if (r0 == 0) goto L_0x0054;
    L_0x00f5:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x009e }
        r2.<init>();	 Catch:{ all -> 0x009e }
        r5 = 1;
        r0 = r6;
        r4 = r10;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x009e }
        goto L_0x0054;
    L_0x0102:
        monitor-exit(r6);
        return;
    L_0x0104:
        r4 = r2[r1];	 Catch:{ all -> 0x009e }
        r5 = ",";
        r4 = r4.contains(r5);	 Catch:{ all -> 0x009e }
        if (r4 != 0) goto L_0x0112;
    L_0x010f:
        r1 = r1 + 1;
        goto L_0x00c8;
    L_0x0112:
        r0 = r0 + 1;
        goto L_0x010f;
    L_0x0115:
        monitor-exit(r6);
        return;
    L_0x0117:
        r0 = r9.h();	 Catch:{ all -> 0x009e }
        r1 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 > 0) goto L_0x00ce;
    L_0x0121:
        monitor-exit(r6);
        return;
    L_0x0123:
        r0 = ",";
        r0 = r8.indexOf(r0);	 Catch:{ all -> 0x009e }
        r1 = -1;
        if (r0 != r1) goto L_0x013c;
    L_0x012d:
        r0 = r9.k();	 Catch:{ all -> 0x009e }
        r1 = "4";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x009e }
        if (r0 == 0) goto L_0x0054;
    L_0x013a:
        monitor-exit(r6);
        return;
    L_0x013c:
        monitor-exit(r6);
        return;
    L_0x013e:
        monitor-exit(r6);
        return;
    L_0x0140:
        r0 = 0;
        goto L_0x0076;
    L_0x0143:
        r0 = r6.b;	 Catch:{ all -> 0x009e }
        r0 = r0.get(r7);	 Catch:{ all -> 0x009e }
        r0 = (java.util.ArrayList) r0;	 Catch:{ all -> 0x009e }
        r0.add(r1);	 Catch:{ all -> 0x009e }
        goto L_0x008e;
    L_0x0150:
        r0 = com.loc.bg.a();	 Catch:{ Exception -> 0x0159 }
        r0.a(r7, r9, r8, r10);	 Catch:{ Exception -> 0x0159 }
        goto L_0x0090;
    L_0x0159:
        r0 = move-exception;
        goto L_0x0090;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.a(java.lang.String, java.lang.StringBuilder, com.autonavi.aps.amapapi.model.AmapLoc, android.content.Context, boolean):void");
    }

    public synchronized AmapLoc a(String str, StringBuilder stringBuilder) {
        if (str.contains("gps")) {
            return null;
        }
        if (b()) {
            c();
            return null;
        } else if (this.b.isEmpty()) {
            return null;
        } else {
            a a;
            String str2 = "found#\u2297";
            String str3;
            if (str.contains("cgiwifi")) {
                a = a(stringBuilder, str);
                if (a != null) {
                    str3 = "found#cgiwifi";
                }
            } else if (str.contains("wifi")) {
                a = a(stringBuilder, str);
                if (a != null) {
                    str3 = "found#wifi";
                }
            } else if (str.contains("cgi")) {
                if (this.b.containsKey(str)) {
                    a = (a) ((ArrayList) this.b.get(str)).get(0);
                } else {
                    a = null;
                }
                if (a != null) {
                    str3 = "found#cgi";
                }
            } else {
                a = null;
            }
            if (a == null || !br.a(a.a())) {
                return null;
            }
            a.a().f("mem");
            if (TextUtils.isEmpty(c.h)) {
                c.h = String.valueOf(a.a().w());
            }
            return a.a();
        }
    }

    public boolean b() {
        long b = br.b() - this.c;
        if (this.c == 0) {
            return false;
        }
        if (this.b.size() > 360) {
            return true;
        }
        boolean z;
        if (b <= 36000000) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.lang.String r4, com.autonavi.aps.amapapi.model.AmapLoc r5) {
        /*
        r3 = this;
        r1 = 0;
        r0 = android.text.TextUtils.isEmpty(r4);
        if (r0 == 0) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = com.loc.br.a(r5);
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
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.a(java.lang.String, com.autonavi.aps.amapapi.model.AmapLoc):boolean");
    }

    public void c() {
        this.c = 0;
        if (!this.b.isEmpty()) {
            this.b.clear();
        }
        this.d = false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.loc.bf.a a(java.lang.StringBuilder r19, java.lang.String r20) {
        /*
        r18 = this;
        monitor-enter(r18);
        r0 = r18;
        r2 = r0.b;	 Catch:{ all -> 0x0108 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0108 }
        if (r2 == 0) goto L_0x000e;
    L_0x000b:
        r2 = 0;
        monitor-exit(r18);
        return r2;
    L_0x000e:
        r2 = android.text.TextUtils.isEmpty(r19);	 Catch:{ all -> 0x0108 }
        if (r2 != 0) goto L_0x000b;
    L_0x0014:
        r0 = r18;
        r2 = r0.b;	 Catch:{ all -> 0x0108 }
        r0 = r20;
        r2 = r2.containsKey(r0);	 Catch:{ all -> 0x0108 }
        if (r2 == 0) goto L_0x0051;
    L_0x0020:
        r5 = 0;
        r11 = new java.util.Hashtable;	 Catch:{ all -> 0x0108 }
        r11.<init>();	 Catch:{ all -> 0x0108 }
        r12 = new java.util.Hashtable;	 Catch:{ all -> 0x0108 }
        r12.<init>();	 Catch:{ all -> 0x0108 }
        r13 = new java.util.Hashtable;	 Catch:{ all -> 0x0108 }
        r13.<init>();	 Catch:{ all -> 0x0108 }
        r0 = r18;
        r2 = r0.b;	 Catch:{ all -> 0x0108 }
        r0 = r20;
        r2 = r2.get(r0);	 Catch:{ all -> 0x0108 }
        r2 = (java.util.ArrayList) r2;	 Catch:{ all -> 0x0108 }
        r3 = r2.size();	 Catch:{ all -> 0x0108 }
        r3 = r3 + -1;
        r10 = r3;
    L_0x0043:
        if (r10 >= 0) goto L_0x0054;
    L_0x0045:
        r3 = r5;
    L_0x0046:
        r11.clear();	 Catch:{ all -> 0x0108 }
        r12.clear();	 Catch:{ all -> 0x0108 }
        r13.clear();	 Catch:{ all -> 0x0108 }
        monitor-exit(r18);
        return r3;
    L_0x0051:
        r2 = 0;
        monitor-exit(r18);
        return r2;
    L_0x0054:
        r3 = r2.get(r10);	 Catch:{ all -> 0x0108 }
        r3 = (com.loc.bf.a) r3;	 Catch:{ all -> 0x0108 }
        r4 = r3.b();	 Catch:{ all -> 0x0108 }
        r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ all -> 0x0108 }
        if (r4 != 0) goto L_0x00e4;
    L_0x0064:
        r4 = 0;
        r6 = r3.b();	 Catch:{ all -> 0x0108 }
        r0 = r18;
        r1 = r19;
        r6 = r0.c(r6, r1);	 Catch:{ all -> 0x0108 }
        if (r6 != 0) goto L_0x00e9;
    L_0x0073:
        r9 = r4;
    L_0x0074:
        r4 = r3.b();	 Catch:{ all -> 0x0108 }
        r0 = r18;
        r0.a(r4, r11);	 Catch:{ all -> 0x0108 }
        r4 = r19.toString();	 Catch:{ all -> 0x0108 }
        r0 = r18;
        r0.a(r4, r12);	 Catch:{ all -> 0x0108 }
        r13.clear();	 Catch:{ all -> 0x0108 }
        r4 = r11.keySet();	 Catch:{ all -> 0x0108 }
        r6 = r4.iterator();	 Catch:{ all -> 0x0108 }
    L_0x0091:
        r4 = r6.hasNext();	 Catch:{ all -> 0x0108 }
        if (r4 != 0) goto L_0x00fb;
    L_0x0097:
        r4 = r12.keySet();	 Catch:{ all -> 0x0108 }
        r6 = r4.iterator();	 Catch:{ all -> 0x0108 }
    L_0x009f:
        r4 = r6.hasNext();	 Catch:{ all -> 0x0108 }
        if (r4 != 0) goto L_0x010b;
    L_0x00a5:
        r14 = r13.keySet();	 Catch:{ all -> 0x0108 }
        r4 = r14.size();	 Catch:{ all -> 0x0108 }
        r15 = new double[r4];	 Catch:{ all -> 0x0108 }
        r4 = r14.size();	 Catch:{ all -> 0x0108 }
        r0 = new double[r4];	 Catch:{ all -> 0x0108 }
        r16 = r0;
        r4 = 0;
        r17 = r14.iterator();	 Catch:{ all -> 0x0108 }
        r8 = r4;
    L_0x00bd:
        if (r17 != 0) goto L_0x0118;
    L_0x00bf:
        r14.clear();	 Catch:{ all -> 0x0108 }
        r0 = r18;
        r1 = r16;
        r4 = r0.a(r15, r1);	 Catch:{ all -> 0x0108 }
        r6 = 0;
        r6 = r4[r6];	 Catch:{ all -> 0x0108 }
        r14 = 4605380979056443392; // 0x3fe99999a0000000 float:-1.0842022E-19 double:0.800000011920929;
        r6 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1));
        if (r6 >= 0) goto L_0x0046;
    L_0x00d6:
        r6 = 1;
        r6 = r4[r6];	 Catch:{ all -> 0x0108 }
        r14 = 4603741668684706349; // 0x3fe3c6a7ef9db22d float:-9.76091E28 double:0.618;
        r6 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1));
        if (r6 >= 0) goto L_0x0046;
    L_0x00e2:
        if (r9 != 0) goto L_0x0142;
    L_0x00e4:
        r3 = r10 + -1;
        r10 = r3;
        goto L_0x0043;
    L_0x00e9:
        r4 = 1;
        r6 = r3.b();	 Catch:{ all -> 0x0108 }
        r0 = r18;
        r1 = r19;
        r6 = r0.b(r6, r1);	 Catch:{ all -> 0x0108 }
        if (r6 != 0) goto L_0x0046;
    L_0x00f8:
        r9 = r4;
        goto L_0x0074;
    L_0x00fb:
        r4 = r6.next();	 Catch:{ all -> 0x0108 }
        r4 = (java.lang.String) r4;	 Catch:{ all -> 0x0108 }
        r7 = "";
        r13.put(r4, r7);	 Catch:{ all -> 0x0108 }
        goto L_0x0091;
    L_0x0108:
        r2 = move-exception;
        monitor-exit(r18);
        throw r2;
    L_0x010b:
        r4 = r6.next();	 Catch:{ all -> 0x0108 }
        r4 = (java.lang.String) r4;	 Catch:{ all -> 0x0108 }
        r7 = "";
        r13.put(r4, r7);	 Catch:{ all -> 0x0108 }
        goto L_0x009f;
    L_0x0118:
        r4 = r17.hasNext();	 Catch:{ all -> 0x0108 }
        if (r4 == 0) goto L_0x00bf;
    L_0x011e:
        r4 = r17.next();	 Catch:{ all -> 0x0108 }
        r4 = (java.lang.String) r4;	 Catch:{ all -> 0x0108 }
        r6 = r11.containsKey(r4);	 Catch:{ all -> 0x0108 }
        if (r6 != 0) goto L_0x013c;
    L_0x012a:
        r6 = 0;
    L_0x012c:
        r15[r8] = r6;	 Catch:{ all -> 0x0108 }
        r4 = r12.containsKey(r4);	 Catch:{ all -> 0x0108 }
        if (r4 != 0) goto L_0x013f;
    L_0x0134:
        r6 = 0;
    L_0x0136:
        r16[r8] = r6;	 Catch:{ all -> 0x0108 }
        r4 = r8 + 1;
        r8 = r4;
        goto L_0x00bd;
    L_0x013c:
        r6 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        goto L_0x012c;
    L_0x013f:
        r6 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        goto L_0x0136;
    L_0x0142:
        r6 = 0;
        r6 = r4[r6];	 Catch:{ all -> 0x0108 }
        r8 = 4603741668684706349; // 0x3fe3c6a7ef9db22d float:-9.76091E28 double:0.618;
        r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r4 < 0) goto L_0x00e4;
    L_0x014e:
        goto L_0x0046;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.a(java.lang.StringBuilder, java.lang.String):com.loc.bf$a");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean c(java.lang.String r5, java.lang.StringBuilder r6) {
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
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.c(java.lang.String, java.lang.StringBuilder):boolean");
    }

    private void a(String str, Hashtable<String, String> hashtable) {
        if (!TextUtils.isEmpty(str)) {
            hashtable.clear();
            for (Object obj : str.split("#")) {
                if (!(TextUtils.isEmpty(obj) || obj.contains("|"))) {
                    hashtable.put(obj, "");
                }
            }
        }
    }

    private double[] a(double[] dArr, double[] dArr2) {
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
            dArr3[i] = Double.parseDouble(br.a(Double.valueOf(dArr3[i]), "#.00"));
        }
        return dArr3;
    }

    public boolean b(String str, StringBuilder stringBuilder) {
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
        if (((double) (i3 * 2)) >= ((double) (arrayList.size() + i2)) * 0.618d) {
            return true;
        }
        return false;
    }

    public void a(Context context) {
        if (!this.d) {
            br.b();
            try {
                bg.a().a(context);
            } catch (Exception e) {
            }
            br.b();
            this.d = true;
        }
    }
}
