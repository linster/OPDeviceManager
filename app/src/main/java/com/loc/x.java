package com.loc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

/* compiled from: DBOperation */
public class x {
    private z a;
    private SQLiteDatabase b;
    private w c;

    public <T> java.util.List<T> b(java.lang.String r12, com.loc.y<T> r13, boolean r14) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r11 = this;
        r8 = 0;
        r9 = r11.c;
        monitor-enter(r9);
        r10 = new java.util.ArrayList;	 Catch:{ all -> 0x001a }
        r10.<init>();	 Catch:{ all -> 0x001a }
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0013;	 Catch:{ all -> 0x001a }
    L_0x000d:
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x001d;	 Catch:{ all -> 0x001a }
    L_0x0011:
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        return r10;	 Catch:{ all -> 0x001a }
    L_0x0013:
        r0 = r11.a(r14);	 Catch:{ all -> 0x001a }
        r11.b = r0;	 Catch:{ all -> 0x001a }
        goto L_0x000d;	 Catch:{ all -> 0x001a }
    L_0x001a:
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        throw r0;
    L_0x001d:
        r0 = r13.b();	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0011;
    L_0x0023:
        if (r12 == 0) goto L_0x0011;
    L_0x0025:
        r0 = r11.b;	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
        r1 = r13.b();	 Catch:{ Throwable -> 0x011d, all -> 0x0119 }
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
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x00c7;	 Catch:{ all -> 0x001a }
    L_0x0043:
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        return r10;
    L_0x0045:
        r0 = r11.b;	 Catch:{ Throwable -> 0x005d }
        r0.close();	 Catch:{ Throwable -> 0x005d }
        r0 = 0;	 Catch:{ Throwable -> 0x005d }
        r11.b = r0;	 Catch:{ Throwable -> 0x005d }
        if (r1 != 0) goto L_0x008e;
    L_0x004f:
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x009f;	 Catch:{ all -> 0x001a }
    L_0x0053:
        monitor-exit(r9);	 Catch:{ all -> 0x001a }
        return r10;
    L_0x0055:
        r0 = r13.a(r1);	 Catch:{ Throwable -> 0x005d }
        r10.add(r0);	 Catch:{ Throwable -> 0x005d }
        goto L_0x0037;
    L_0x005d:
        r0 = move-exception;
    L_0x005e:
        if (r14 == 0) goto L_0x007c;
    L_0x0060:
        if (r1 != 0) goto L_0x00df;
    L_0x0062:
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x0066:
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;	 Catch:{ all -> 0x001a }
        r11.b = r0;	 Catch:{ all -> 0x001a }
        goto L_0x0043;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x0072:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.v.a(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0043;
    L_0x007c:
        r2 = "DataBase";	 Catch:{ all -> 0x0086 }
        r3 = "searchListData";	 Catch:{ all -> 0x0086 }
        com.loc.v.a(r0, r2, r3);	 Catch:{ all -> 0x0086 }
        goto L_0x0060;
    L_0x0086:
        r0 = move-exception;
    L_0x0087:
        if (r1 != 0) goto L_0x00f1;
    L_0x0089:
        r1 = r11.b;	 Catch:{ all -> 0x001a }
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
        com.loc.v.a(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x004f;	 Catch:{ all -> 0x001a }
    L_0x009f:
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;	 Catch:{ all -> 0x001a }
        r11.b = r0;	 Catch:{ all -> 0x001a }
        goto L_0x0053;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0053;	 Catch:{ all -> 0x001a }
    L_0x00ab:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.v.a(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0053;	 Catch:{ all -> 0x001a }
    L_0x00b5:
        r1.close();	 Catch:{ all -> 0x001a }
        goto L_0x003f;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x003f;	 Catch:{ all -> 0x001a }
    L_0x00bc:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.v.a(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x003f;	 Catch:{ all -> 0x001a }
    L_0x00c7:
        r0 = r11.b;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;	 Catch:{ all -> 0x001a }
        r11.b = r0;	 Catch:{ all -> 0x001a }
        goto L_0x0043;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x00d4:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.v.a(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0043;	 Catch:{ all -> 0x001a }
    L_0x00df:
        r1.close();	 Catch:{ all -> 0x001a }
        goto L_0x0062;	 Catch:{ all -> 0x001a }
        r0 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0062;	 Catch:{ all -> 0x001a }
    L_0x00e6:
        r1 = "DataBase";	 Catch:{ all -> 0x001a }
        r2 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.v.a(r0, r1, r2);	 Catch:{ all -> 0x001a }
        goto L_0x0062;	 Catch:{ all -> 0x001a }
    L_0x00f1:
        r1.close();	 Catch:{ all -> 0x001a }
        goto L_0x0089;	 Catch:{ all -> 0x001a }
        r1 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x0089;	 Catch:{ all -> 0x001a }
    L_0x00f8:
        r2 = "DataBase";	 Catch:{ all -> 0x001a }
        r3 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.v.a(r1, r2, r3);	 Catch:{ all -> 0x001a }
        goto L_0x0089;	 Catch:{ all -> 0x001a }
    L_0x0102:
        r1 = r11.b;	 Catch:{ all -> 0x001a }
        r1.close();	 Catch:{ all -> 0x001a }
        r1 = 0;	 Catch:{ all -> 0x001a }
        r11.b = r1;	 Catch:{ all -> 0x001a }
        goto L_0x008d;	 Catch:{ all -> 0x001a }
        r1 = move-exception;	 Catch:{ all -> 0x001a }
        if (r14 != 0) goto L_0x008d;	 Catch:{ all -> 0x001a }
    L_0x010e:
        r2 = "DataBase";	 Catch:{ all -> 0x001a }
        r3 = "searchListData";	 Catch:{ all -> 0x001a }
        com.loc.v.a(r1, r2, r3);	 Catch:{ all -> 0x001a }
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
        throw new UnsupportedOperationException("Method not decompiled: com.loc.x.b(java.lang.String, com.loc.y, boolean):java.util.List<T>");
    }

    public x(Context context, w wVar) {
        try {
            this.a = new z(context, wVar.a(), null, wVar.b(), wVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.c = wVar;
    }

    private SQLiteDatabase a(boolean z) {
        try {
            this.b = this.a.getReadableDatabase();
        } catch (Throwable th) {
            if (z) {
                th.printStackTrace();
            } else {
                v.a(th, "DBOperation", "getReadAbleDataBase");
            }
        }
        return this.b;
    }

    private SQLiteDatabase b(boolean z) {
        try {
            this.b = this.a.getWritableDatabase();
        } catch (Throwable th) {
            v.a(th, "DBOperation", "getReadAbleDataBase");
        }
        return this.b;
    }

    public static String a(Map<String, String> map) {
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

    public <T> void a(String str, y<T> yVar) {
        synchronized (this.c) {
            if (yVar.b() == null || str == null) {
                return;
            }
            if (this.b == null || this.b.isReadOnly()) {
                this.b = b(false);
            }
            if (this.b != null) {
                try {
                    this.b.delete(yVar.b(), str, null);
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                } catch (Throwable th) {
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                }
                return;
            }
        }
    }

    public <T> void b(String str, y<T> yVar) {
        a(str, yVar, false);
    }

    public <T> void a(String str, y<T> yVar, boolean z) {
        synchronized (this.c) {
            if (yVar == null || str == null || yVar.b() == null) {
                return;
            }
            ContentValues a = yVar.a();
            if (a != null) {
                if (this.b == null || this.b.isReadOnly()) {
                    this.b = b(z);
                }
                if (this.b != null) {
                    try {
                        this.b.update(yVar.b(), a, str, null);
                        if (this.b != null) {
                            this.b.close();
                            this.b = null;
                        }
                    } catch (Throwable th) {
                        if (this.b != null) {
                            this.b.close();
                            this.b = null;
                        }
                    }
                    return;
                }
                return;
            }
            return;
        }
    }

    public <T> void a(y<T> yVar, String str) {
        synchronized (this.c) {
            List c = c(str, yVar);
            if (c == null || c.size() == 0) {
                a((y) yVar);
            } else {
                b(str, yVar);
            }
        }
    }

    public <T> void a(y<T> yVar) {
        a((y) yVar, false);
    }

    public <T> void a(y<T> yVar, boolean z) {
        synchronized (this.c) {
            if (this.b == null || this.b.isReadOnly()) {
                this.b = b(z);
            }
            if (this.b != null) {
                try {
                    a(this.b, (y) yVar);
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                } catch (Throwable th) {
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                }
                return;
            }
        }
    }

    private <T> void a(SQLiteDatabase sQLiteDatabase, y<T> yVar) {
        if (yVar != null && sQLiteDatabase != null) {
            ContentValues a = yVar.a();
            if (a != null && yVar.b() != null) {
                sQLiteDatabase.insert(yVar.b(), null, a);
            }
        }
    }

    public <T> List<T> c(String str, y<T> yVar) {
        return b(str, yVar, false);
    }
}
