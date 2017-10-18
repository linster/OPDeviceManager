package com.loc;

import android.content.Context;
import android.text.TextUtils;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class an extends ClassLoader {
    private static an ng = null;
    private final Context nf;
    private final Map nh = new HashMap();
    private DexFile ni = null;
    volatile boolean nj = true;
    private String nk;
    private x nl;

    private an(Context context, ClassLoader classLoader) {
        super(classLoader);
        this.nf = context;
    }

    private void oE() {
        oF();
        this.nh.clear();
    }

    private void oF() {
        if (this.ni != null) {
            try {
                this.ni.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void oG(String str, String str2) {
        try {
            this.nh.clear();
            oF();
            this.ni = DexFile.loadDex(str, str2, 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized com.loc.an oH(android.content.Context r5, com.loc.x r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, java.lang.ClassLoader r10) {
        /*
        r3 = 0;
        r1 = com.loc.an.class;
        monitor-enter(r1);
        r0 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x0081 }
        if (r0 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r1);
        return r3;
    L_0x000c:
        r0 = android.text.TextUtils.isEmpty(r8);	 Catch:{ all -> 0x0081 }
        if (r0 != 0) goto L_0x000a;
    L_0x0012:
        com.loc.R.nt(r5, r6);	 Catch:{ all -> 0x0081 }
        r0 = new java.io.File;	 Catch:{ all -> 0x0081 }
        r0.<init>(r7);	 Catch:{ all -> 0x0081 }
        r2 = r0.exists();	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x0028;
    L_0x0020:
        r2 = ng;	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x0035;
    L_0x0024:
        r0 = ng;	 Catch:{ all -> 0x0081 }
        monitor-exit(r1);
        return r0;
    L_0x0028:
        r0 = r6.mz();	 Catch:{ all -> 0x0081 }
        r2 = r6.mA();	 Catch:{ all -> 0x0081 }
        com.loc.k.lD(r5, r0, r2);	 Catch:{ all -> 0x0081 }
        monitor-exit(r1);
        return r3;
    L_0x0035:
        r2 = new java.util.Date;	 Catch:{ all -> 0x0081 }
        r2.<init>();	 Catch:{ all -> 0x0081 }
        r2.getTime();	 Catch:{ all -> 0x0081 }
        r2 = new com.loc.an;	 Catch:{ Throwable -> 0x0084 }
        r3 = r5.getApplicationContext();	 Catch:{ Throwable -> 0x0084 }
        r2.<init>(r3, r10);	 Catch:{ Throwable -> 0x0084 }
        ng = r2;	 Catch:{ Throwable -> 0x0084 }
        r2 = ng;	 Catch:{ Throwable -> 0x0084 }
        r2.nl = r6;	 Catch:{ Throwable -> 0x0084 }
        r2 = ng;	 Catch:{ Throwable -> 0x0084 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0084 }
        r3.<init>();	 Catch:{ Throwable -> 0x0084 }
        r3 = r3.append(r8);	 Catch:{ Throwable -> 0x0084 }
        r4 = java.io.File.separator;	 Catch:{ Throwable -> 0x0084 }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0084 }
        r0 = r0.getName();	 Catch:{ Throwable -> 0x0084 }
        r0 = com.loc.k.lv(r0);	 Catch:{ Throwable -> 0x0084 }
        r0 = r3.append(r0);	 Catch:{ Throwable -> 0x0084 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0084 }
        r2.oG(r7, r0);	 Catch:{ Throwable -> 0x0084 }
    L_0x0070:
        r0 = new java.util.Date;	 Catch:{ all -> 0x0081 }
        r0.<init>();	 Catch:{ all -> 0x0081 }
        r0.getTime();	 Catch:{ all -> 0x0081 }
        r0 = new com.loc.bs;	 Catch:{ all -> 0x0081 }
        r0.<init>(r5, r7, r8);	 Catch:{ all -> 0x0081 }
        r0.start();	 Catch:{ all -> 0x0081 }
        goto L_0x0024;
    L_0x0081:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
    L_0x0084:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0081 }
        goto L_0x0070;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.an.oH(android.content.Context, com.loc.x, java.lang.String, java.lang.String, java.lang.String, java.lang.ClassLoader):com.loc.an");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized void oI(android.content.Context r5, com.loc.x r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, java.lang.ClassLoader r10) {
        /*
        r1 = com.loc.an.class;
        monitor-enter(r1);
        r0 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x0075 }
        if (r0 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r1);
        return;
    L_0x000b:
        r0 = android.text.TextUtils.isEmpty(r8);	 Catch:{ all -> 0x0075 }
        if (r0 != 0) goto L_0x0009;
    L_0x0011:
        com.loc.R.nt(r5, r6);	 Catch:{ all -> 0x0075 }
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x0070 }
        r0.<init>(r7);	 Catch:{ Throwable -> 0x0070 }
        r2 = r0.exists();	 Catch:{ Throwable -> 0x0070 }
        if (r2 == 0) goto L_0x005d;
    L_0x001f:
        r2 = ng;	 Catch:{ Throwable -> 0x0070 }
        if (r2 != 0) goto L_0x006a;
    L_0x0023:
        r2 = new com.loc.an;	 Catch:{ Throwable -> 0x0070 }
        r3 = r5.getApplicationContext();	 Catch:{ Throwable -> 0x0070 }
        r2.<init>(r3, r10);	 Catch:{ Throwable -> 0x0070 }
        ng = r2;	 Catch:{ Throwable -> 0x0070 }
        r2 = ng;	 Catch:{ Throwable -> 0x0070 }
        r2.nl = r6;	 Catch:{ Throwable -> 0x0070 }
        r2 = ng;	 Catch:{ Throwable -> 0x0070 }
        r2.oM(r5, r7, r8);	 Catch:{ Throwable -> 0x0070 }
        r2 = ng;	 Catch:{ Throwable -> 0x0070 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0070 }
        r3.<init>();	 Catch:{ Throwable -> 0x0070 }
        r3 = r3.append(r8);	 Catch:{ Throwable -> 0x0070 }
        r4 = java.io.File.separator;	 Catch:{ Throwable -> 0x0070 }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0070 }
        r0 = r0.getName();	 Catch:{ Throwable -> 0x0070 }
        r0 = com.loc.k.lv(r0);	 Catch:{ Throwable -> 0x0070 }
        r0 = r3.append(r0);	 Catch:{ Throwable -> 0x0070 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0070 }
        r2.oG(r7, r0);	 Catch:{ Throwable -> 0x0070 }
    L_0x005b:
        monitor-exit(r1);
        return;
    L_0x005d:
        r0 = r6.mz();	 Catch:{ Throwable -> 0x0070 }
        r2 = r6.mA();	 Catch:{ Throwable -> 0x0070 }
        com.loc.k.lD(r5, r0, r2);	 Catch:{ Throwable -> 0x0070 }
        monitor-exit(r1);
        return;
    L_0x006a:
        r2 = ng;	 Catch:{ Throwable -> 0x0070 }
        r2.oE();	 Catch:{ Throwable -> 0x0070 }
        goto L_0x0023;
    L_0x0070:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0075 }
        goto L_0x005b;
    L_0x0075:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.an.oI(android.content.Context, com.loc.x, java.lang.String, java.lang.String, java.lang.String, java.lang.ClassLoader):void");
    }

    private boolean oJ(ae aeVar, x xVar, String str) {
        return k.lI(aeVar, k.lx(xVar.mz(), xVar.mA()), str, xVar);
    }

    private boolean oK(ae aeVar, String str, String str2) {
        String lz = k.lz(this.nf, str);
        if (k.lI(aeVar, str, lz, this.nl)) {
            return true;
        }
        if (aC.qd(aeVar, str) != null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.nk)) {
            aC.qc(aeVar, new E(str, J.mZ(lz), this.nl.mz(), this.nl.mA(), str2).mH("useodex").mI(), bP.yj(str));
        }
        return true;
    }

    private void oL(ae aeVar, String str) {
        bo qd = aC.qd(aeVar, str);
        if (qd != null) {
            this.nk = qd.vx();
        }
    }

    private void oM(Context context, String str, String str2) {
        new Date().getTime();
        try {
            ae aeVar = new ae(context, v.mu());
            File file = new File(str);
            oL(aeVar, file.getName());
            if (!oJ(aeVar, this.nl, file.getAbsolutePath())) {
                this.nj = false;
                k.lE(this.nf, aeVar, file.getName());
                Object lJ = k.lJ(this.nf, aeVar, this.nl);
                if (!TextUtils.isEmpty(lJ)) {
                    this.nk = lJ;
                    oG(str, str2 + File.separator + k.lv(file.getName()));
                    this.nj = true;
                }
            }
            if (file.exists()) {
                String str3 = str2 + File.separator + k.lv(file.getName());
                File file2 = new File(str3);
                if (!file2.exists()) {
                    oG(str, str2 + File.separator + k.lv(file.getName()));
                    ng.oN(file2, str3, this.nk, aeVar);
                } else if (!oK(aeVar, k.lv(file.getName()), this.nk)) {
                    oG(str, str2 + File.separator + k.lv(file.getName()));
                    oN(file2, str3, this.nk, aeVar);
                }
                new Date().getTime();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void oN(File file, String str, String str2, ae aeVar) {
        if (!TextUtils.isEmpty(this.nk)) {
            String mZ = J.mZ(str);
            String name = file.getName();
            aC.qc(aeVar, new E(name, mZ, this.nl.mz(), this.nl.mA(), str2).mH("useodex").mI(), bP.yj(name));
        }
    }

    protected Class findClass(String str) {
        try {
            if (this.ni != null) {
                Class cls = (Class) this.nh.get(str);
                if (cls != null) {
                    return cls;
                }
                cls = this.ni.loadClass(str, this);
                this.nh.put(str, cls);
                if (cls != null) {
                    return cls;
                }
                throw new ClassNotFoundException(str);
            }
            throw new ClassNotFoundException(str);
        } catch (Throwable th) {
            th.printStackTrace();
            ClassNotFoundException classNotFoundException = new ClassNotFoundException(str);
        }
    }

    boolean oD() {
        return this.ni != null;
    }
}
