package com.loc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import java.util.Map;

public class ae {
    private aR mM;
    private SQLiteDatabase mN;
    private P mO;

    public ae(Context context, P p) {
        try {
            this.mM = new aR(context, p.mv(), null, p.mw(), p);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.mO = p;
    }

    private SQLiteDatabase nT(boolean z) {
        try {
            this.mN = this.mM.getReadableDatabase();
        } catch (Throwable th) {
            if (z) {
                th.printStackTrace();
            } else {
                D.mF(th, "DBOperation", "getReadAbleDataBase");
            }
        }
        return this.mN;
    }

    private SQLiteDatabase nU(boolean z) {
        try {
            this.mN = this.mM.getWritableDatabase();
        } catch (Throwable th) {
            D.mF(th, "DBOperation", "getReadAbleDataBase");
        }
        return this.mN;
    }

    public static String nV(Map map) {
        if (map == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (String str : map.keySet()) {
            Object obj2;
            if (obj == null) {
                stringBuilder.append(" and ").append(str).append(" = '").append((String) map.get(str)).append("'");
                obj2 = obj;
            } else {
                stringBuilder.append(str).append(" = '").append((String) map.get(str)).append("'");
                obj2 = null;
            }
            obj = obj2;
        }
        return stringBuilder.toString();
    }

    private void oc(SQLiteDatabase sQLiteDatabase, p pVar) {
        if (pVar != null && sQLiteDatabase != null) {
            ContentValues lX = pVar.lX();
            if (lX != null && pVar.lZ() != null) {
                sQLiteDatabase.insert(pVar.lZ(), null, lX);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void nW(java.lang.String r5, com.loc.p r6) {
        /*
        r4 = this;
        r1 = r4.mO;
        monitor-enter(r1);
        r0 = r6.lZ();	 Catch:{ all -> 0x004e }
        if (r0 != 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r1);	 Catch:{ all -> 0x004e }
        return;
    L_0x000b:
        if (r5 == 0) goto L_0x0009;
    L_0x000d:
        r0 = r4.mN;	 Catch:{ all -> 0x004e }
        if (r0 != 0) goto L_0x002c;
    L_0x0011:
        r0 = 0;
        r0 = r4.nU(r0);	 Catch:{ all -> 0x004e }
        r4.mN = r0;	 Catch:{ all -> 0x004e }
    L_0x0018:
        r0 = r4.mN;	 Catch:{ all -> 0x004e }
        if (r0 == 0) goto L_0x0035;
    L_0x001c:
        r0 = r4.mN;	 Catch:{ Throwable -> 0x0037 }
        r2 = r6.lZ();	 Catch:{ Throwable -> 0x0037 }
        r3 = 0;
        r0.delete(r2, r5, r3);	 Catch:{ Throwable -> 0x0037 }
        r0 = r4.mN;	 Catch:{ all -> 0x004e }
        if (r0 != 0) goto L_0x0057;
    L_0x002a:
        monitor-exit(r1);	 Catch:{ all -> 0x004e }
        return;
    L_0x002c:
        r0 = r4.mN;	 Catch:{ all -> 0x004e }
        r0 = r0.isReadOnly();	 Catch:{ all -> 0x004e }
        if (r0 != 0) goto L_0x0011;
    L_0x0034:
        goto L_0x0018;
    L_0x0035:
        monitor-exit(r1);	 Catch:{ all -> 0x004e }
        return;
    L_0x0037:
        r0 = move-exception;
        r2 = "DataBase";
        r3 = "deleteData";
        com.loc.D.mF(r0, r2, r3);	 Catch:{ all -> 0x0051 }
        r0 = r4.mN;	 Catch:{ all -> 0x004e }
        if (r0 == 0) goto L_0x002a;
    L_0x0045:
        r0 = r4.mN;	 Catch:{ all -> 0x004e }
        r0.close();	 Catch:{ all -> 0x004e }
        r0 = 0;
        r4.mN = r0;	 Catch:{ all -> 0x004e }
        goto L_0x002a;
    L_0x004e:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x004e }
        throw r0;
    L_0x0051:
        r0 = move-exception;
        r2 = r4.mN;	 Catch:{ all -> 0x004e }
        if (r2 != 0) goto L_0x0060;
    L_0x0056:
        throw r0;	 Catch:{ all -> 0x004e }
    L_0x0057:
        r0 = r4.mN;	 Catch:{ all -> 0x004e }
        r0.close();	 Catch:{ all -> 0x004e }
        r0 = 0;
        r4.mN = r0;	 Catch:{ all -> 0x004e }
        goto L_0x002a;
    L_0x0060:
        r2 = r4.mN;	 Catch:{ all -> 0x004e }
        r2.close();	 Catch:{ all -> 0x004e }
        r2 = 0;
        r4.mN = r2;	 Catch:{ all -> 0x004e }
        goto L_0x0056;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ae.nW(java.lang.String, com.loc.p):void");
    }

    public void nX(String str, p pVar) {
        nY(str, pVar, false);
    }

    public void nY(String str, p pVar, boolean z) {
        synchronized (this.mO) {
            if (pVar == null || str == null || pVar.lZ() == null) {
                return;
            }
            ContentValues lX = pVar.lX();
            if (lX != null) {
                if (this.mN == null || this.mN.isReadOnly()) {
                    this.mN = nU(z);
                }
                if (this.mN != null) {
                    try {
                        this.mN.update(pVar.lZ(), lX, str, null);
                        if (this.mN != null) {
                            this.mN.close();
                            this.mN = null;
                        }
                    } catch (Throwable th) {
                        if (this.mN != null) {
                            this.mN.close();
                            this.mN = null;
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void nZ(p pVar, String str) {
        synchronized (this.mO) {
            List oe = oe(str, pVar);
            if (oe == null || oe.size() == 0) {
                oa(pVar);
            } else {
                nX(str, pVar);
            }
        }
    }

    public void oa(p pVar) {
        ob(pVar, false);
    }

    public void ob(p pVar, boolean z) {
        synchronized (this.mO) {
            if (this.mN == null || this.mN.isReadOnly()) {
                this.mN = nU(z);
            }
            if (this.mN != null) {
                try {
                    oc(this.mN, pVar);
                    if (this.mN != null) {
                        this.mN.close();
                        this.mN = null;
                    }
                } catch (Throwable th) {
                    if (this.mN != null) {
                        this.mN.close();
                        this.mN = null;
                    }
                }
            }
        }
    }

    public java.util.List od(java.lang.String r12, com.loc.p r13, boolean r14) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:50:0x006f
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r11 = this;
        r8 = 0;
        r9 = r11.mO;
        monitor-enter(r9);
        r10 = new java.util.ArrayList;	 Catch:{ all -> 0x001a }
        r10.<init>();	 Catch:{ all -> 0x001a }
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0013;	 Catch:{ all -> 0x001a }
    L_0x000d:
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x001d;	 Catch:{ all -> 0x001a }
    L_0x0011:
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        return r10;	 Catch:{ all -> 0x001a }
    L_0x0013:
        r0 = r11.nT(r14);	 Catch:{ all -> 0x001a }
        r11.mN = r0;	 Catch:{ all -> 0x001a }
        goto L_0x000d;	 Catch:{ all -> 0x001a }
    L_0x001a:
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        throw r0;
    L_0x001d:
        r0 = r13.lZ();	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0011;
    L_0x0023:
        if (r12 == 0) goto L_0x0011;
    L_0x0025:
        r0 = r11.mN;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r1 = r13.lZ();	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r2 = 0;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r4 = 0;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r5 = 0;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r6 = 0;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r7 = 0;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r3 = r12;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        if (r1 == 0) goto L_0x0045;
    L_0x0037:
        r0 = r1.moveToNext();	 Catch:{ Throwable -> 0x005d }
        if (r0 != 0) goto L_0x0055;
    L_0x003d:
        if (r1 != 0) goto L_0x00b5;
    L_0x003f:
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x00c7;	 Catch:{ all -> 0x001a }
    L_0x0043:
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        return r10;
    L_0x0045:
        r0 = r11.mN;	 Catch:{ Throwable -> 0x005d }
        r0.close();	 Catch:{ Throwable -> 0x005d }
        r0 = 0;	 Catch:{ Throwable -> 0x005d }
        r11.mN = r0;	 Catch:{ Throwable -> 0x005d }
        if (r1 != 0) goto L_0x008e;
    L_0x004f:
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x009f;	 Catch:{ all -> 0x001a }
    L_0x0053:
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        return r10;
    L_0x0055:
        r0 = r13.lY(r1);	 Catch:{ Throwable -> 0x005d }
        r10.add(r0);	 Catch:{ Throwable -> 0x005d }
        goto L_0x0037;
    L_0x005d:
        r0 = move-exception;
    L_0x005e:
        if (r14 == 0) goto L_0x007c;
    L_0x0060:
        if (r1 != 0) goto L_0x00df;
    L_0x0062:
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x0066:
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;	 Catch:{ all -> 0x001a }
        r11.mN = r0;	 Catch:{ all -> 0x001a }
        goto L_0x0043;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x0072:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0043;
    L_0x007c:
        r2 = "DataBase";	 Catch:{ all -> 0x0086 }
        r3 = "searchListData";	 Catch:{ all -> 0x0086 }
        com.loc.D.mF(r0, r2, r3);	 Catch:{ all -> 0x0086 }
        goto L_0x0060;
    L_0x0086:
        r0 = move-exception;
    L_0x0087:
        if (r1 != 0) goto L_0x00f1;
    L_0x0089:
        r1 = r11.mN;	 Catch:{ all -> 0x001a }
        if (r1 != 0) goto L_0x0102;	 Catch:{ all -> 0x001a }
    L_0x008d:
        throw r0;	 Catch:{ all -> 0x001a }
    L_0x008e:
        r1.close();	 Catch:{ all -> 0x001a }
        goto L_0x004f;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x004f;	 Catch:{ all -> 0x001a }
    L_0x0095:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x004f;	 Catch:{ all -> 0x001a }
    L_0x009f:
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;	 Catch:{ all -> 0x001a }
        r11.mN = r0;	 Catch:{ all -> 0x001a }
        goto L_0x0053;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0053;	 Catch:{ all -> 0x001a }
    L_0x00ab:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0053;	 Catch:{ all -> 0x001a }
    L_0x00b5:
        r1.close();	 Catch:{ all -> 0x001a }
        goto L_0x003f;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x003f;	 Catch:{ all -> 0x001a }
    L_0x00bc:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x003f;	 Catch:{ all -> 0x001a }
    L_0x00c7:
        r0 = r11.mN;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;	 Catch:{ all -> 0x001a }
        r11.mN = r0;	 Catch:{ all -> 0x001a }
        goto L_0x0043;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x00d4:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x00df:
        r1.close();	 Catch:{ all -> 0x001a }
        goto L_0x0062;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0062;	 Catch:{ all -> 0x001a }
    L_0x00e6:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0062;	 Catch:{ all -> 0x001a }
    L_0x00f1:
        r1.close();	 Catch:{ all -> 0x001a }
        goto L_0x0089;	 Catch:{ all -> 0x001a }
        r1 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0089;	 Catch:{ all -> 0x001a }
    L_0x00f8:
        r2 = "DataBase";	 Catch:{ all -> 0x001a }
        r3 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r1, r2, r3);	 Catch:{ all -> 0x001a }
        goto L_0x0089;	 Catch:{ all -> 0x001a }
    L_0x0102:
        r1 = r11.mN;	 Catch:{ all -> 0x001a }
        r1.close();	 Catch:{ all -> 0x001a }
        r1 = 0;	 Catch:{ all -> 0x001a }
        r11.mN = r1;	 Catch:{ all -> 0x001a }
        goto L_0x008d;	 Catch:{ all -> 0x001a }
        r1 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x008d;	 Catch:{ all -> 0x001a }
    L_0x010e:
        r2 = "DataBase";	 Catch:{ all -> 0x001a }
        r3 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.D.mF(r1, r2, r3);	 Catch:{ all -> 0x001a }
        goto L_0x008d;
    L_0x0119:
        r0 = move-exception;
        r1 = r8;
        goto L_0x0087;
    L_0x011d:
        r0 = move-exception;
        r1 = r8;
        goto L_0x005e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ae.od(java.lang.String, com.loc.p, boolean):java.util.List");
    }

    public List oe(String str, p pVar) {
        return od(str, pVar, false);
    }
}
