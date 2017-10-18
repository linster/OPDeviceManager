package com.loc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import org.json.JSONObject;

public class S {
    private static S mo = null;
    private String mp = "2.0.201501131131".replace(".", "");
    private String mq = null;

    public static synchronized S nu() {
        S s;
        synchronized (S.class) {
            if (mo == null) {
                mo = new S();
            }
            s = mo;
        }
        return s;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean ny(android.database.sqlite.SQLiteDatabase r8, java.lang.String r9) {
        /*
        r7 = this;
        r2 = 1;
        r0 = 0;
        r1 = 0;
        r3 = android.text.TextUtils.isEmpty(r9);
        if (r3 != 0) goto L_0x003f;
    L_0x0009:
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r3.<init>();	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r4 = "SELECT count(*) as c FROM sqlite_master WHERE type = 'table' AND name = '";
        r3.append(r4);	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r4 = r9.trim();	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r4 = r3.append(r4);	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r5 = r7.mp;	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r5 = "' ";
        r4.append(r5);	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r4 = r3.toString();	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        r5 = 0;
        r0 = r8.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0054, all -> 0x005e }
        if (r0 != 0) goto L_0x0040;
    L_0x0033:
        r4 = 0;
        r5 = r3.length();	 Catch:{ Exception -> 0x0054, all -> 0x0069 }
        r3.delete(r4, r5);	 Catch:{ Exception -> 0x0054, all -> 0x0069 }
        if (r0 != 0) goto L_0x004f;
    L_0x003d:
        r0 = r1;
    L_0x003e:
        return r0;
    L_0x003f:
        return r1;
    L_0x0040:
        r4 = r0.moveToFirst();	 Catch:{ Exception -> 0x0054, all -> 0x0069 }
        if (r4 == 0) goto L_0x0033;
    L_0x0046:
        r4 = 0;
        r4 = r0.getInt(r4);	 Catch:{ Exception -> 0x0054, all -> 0x0069 }
        if (r4 <= 0) goto L_0x0033;
    L_0x004d:
        r1 = r2;
        goto L_0x0033;
    L_0x004f:
        r0.close();
        r0 = r1;
        goto L_0x003e;
    L_0x0054:
        r1 = move-exception;
        if (r0 != 0) goto L_0x0059;
    L_0x0057:
        r0 = r2;
        goto L_0x003e;
    L_0x0059:
        r0.close();
        r0 = r2;
        goto L_0x003e;
    L_0x005e:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0062:
        if (r1 != 0) goto L_0x0065;
    L_0x0064:
        throw r0;
    L_0x0065:
        r1.close();
        goto L_0x0064;
    L_0x0069:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0062;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.S.ny(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void nA(android.content.Context r12) {
        /*
        r11 = this;
        r2 = 0;
        r1 = 0;
        monitor-enter(r11);
        r0 = com.loc.bN.tL;	 Catch:{ all -> 0x0085 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r11);
        return;
    L_0x0009:
        if (r12 == 0) goto L_0x0007;
    L_0x000b:
        r0 = "hmdb";
        r3 = 0;
        r4 = 0;
        r9 = r12.openOrCreateDatabase(r0, r3, r4);	 Catch:{ all -> 0x00f4 }
        r0 = "hm";
        r0 = r11.ny(r9, r0);	 Catch:{ all -> 0x0080 }
        if (r0 == 0) goto L_0x0072;
    L_0x001d:
        r4 = com.loc.bq.vL();	 Catch:{ all -> 0x0080 }
        r6 = 1209600000; // 0x48190800 float:156704.0 double:5.97621805E-315;
        r4 = r4 - r6;
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0080 }
        r3.<init>();	 Catch:{ all -> 0x0080 }
        r0 = "SELECT hash, num, extra, time FROM ";
        r3.append(r0);	 Catch:{ all -> 0x0080 }
        r0 = "hm";
        r0 = r3.append(r0);	 Catch:{ all -> 0x0080 }
        r6 = r11.mp;	 Catch:{ all -> 0x0080 }
        r0.append(r6);	 Catch:{ all -> 0x0080 }
        r0 = " WHERE time > ";
        r0 = r3.append(r0);	 Catch:{ all -> 0x0080 }
        r0.append(r4);	 Catch:{ all -> 0x0080 }
        r0 = " ORDER BY num DESC LIMIT 0,";
        r3.append(r0);	 Catch:{ all -> 0x0080 }
        r0 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0 = r3.append(r0);	 Catch:{ all -> 0x0080 }
        r4 = ";";
        r0.append(r4);	 Catch:{ all -> 0x0080 }
        r0 = r3.toString();	 Catch:{ SQLiteException -> 0x0088 }
        r4 = 0;
        r2 = r9.rawQuery(r0, r4);	 Catch:{ SQLiteException -> 0x0088 }
        r10 = r2;
    L_0x0062:
        r0 = 0;
        r2 = r3.length();	 Catch:{ all -> 0x0080 }
        r3.delete(r0, r2);	 Catch:{ all -> 0x0080 }
        if (r10 != 0) goto L_0x00a0;
    L_0x006c:
        if (r10 != 0) goto L_0x00dc;
    L_0x006e:
        if (r9 != 0) goto L_0x00e0;
    L_0x0070:
        monitor-exit(r11);
        return;
    L_0x0072:
        if (r9 != 0) goto L_0x0076;
    L_0x0074:
        monitor-exit(r11);
        return;
    L_0x0076:
        r0 = r9.isOpen();	 Catch:{ all -> 0x0080 }
        if (r0 == 0) goto L_0x0074;
    L_0x007c:
        r9.close();	 Catch:{ all -> 0x0080 }
        goto L_0x0074;
    L_0x0080:
        r0 = move-exception;
        r1 = r9;
    L_0x0082:
        if (r1 != 0) goto L_0x00ea;
    L_0x0084:
        throw r0;	 Catch:{ all -> 0x0085 }
    L_0x0085:
        r0 = move-exception;
        monitor-exit(r11);
        throw r0;
    L_0x0088:
        r0 = move-exception;
        r0 = r0.getMessage();	 Catch:{ all -> 0x0080 }
        r4 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0080 }
        if (r4 == 0) goto L_0x0095;
    L_0x0093:
        r10 = r2;
        goto L_0x0062;
    L_0x0095:
        r4 = "no such table";
        r0 = r0.contains(r4);	 Catch:{ all -> 0x0080 }
        if (r0 == 0) goto L_0x0093;
    L_0x009e:
        r10 = r2;
        goto L_0x0062;
    L_0x00a0:
        r10.moveToFirst();	 Catch:{ all -> 0x0080 }
        r0 = r1;
    L_0x00a4:
        r0 = r0 + 1;
        r1 = 0;
        r3 = r10.getString(r1);	 Catch:{ all -> 0x0080 }
        r1 = 1;
        r5 = r10.getInt(r1);	 Catch:{ all -> 0x0080 }
        r1 = 2;
        r4 = r10.getString(r1);	 Catch:{ all -> 0x0080 }
        r1 = 3;
        r6 = r10.getLong(r1);	 Catch:{ all -> 0x0080 }
        r1 = "{";
        r1 = r4.startsWith(r1);	 Catch:{ all -> 0x0080 }
        if (r1 == 0) goto L_0x00d3;
    L_0x00c3:
        r1 = com.loc.bb.ts();	 Catch:{ all -> 0x0080 }
        r8 = 0;
        r2 = r12;
        r1.tt(r2, r3, r4, r5, r6, r8);	 Catch:{ all -> 0x0080 }
        r1 = r10.moveToNext();	 Catch:{ all -> 0x0080 }
        if (r1 == 0) goto L_0x006c;
    L_0x00d2:
        goto L_0x00a4;
    L_0x00d3:
        r3 = com.loc.bq.wp(r3);	 Catch:{ all -> 0x0080 }
        r4 = com.loc.bq.wp(r4);	 Catch:{ all -> 0x0080 }
        goto L_0x00c3;
    L_0x00dc:
        r10.close();	 Catch:{ all -> 0x0080 }
        goto L_0x006e;
    L_0x00e0:
        r0 = r9.isOpen();	 Catch:{ all -> 0x0085 }
        if (r0 == 0) goto L_0x0070;
    L_0x00e6:
        r9.close();	 Catch:{ all -> 0x0085 }
        goto L_0x0070;
    L_0x00ea:
        r2 = r1.isOpen();	 Catch:{ all -> 0x0085 }
        if (r2 == 0) goto L_0x0084;
    L_0x00f0:
        r1.close();	 Catch:{ all -> 0x0085 }
        goto L_0x0084;
    L_0x00f4:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0082;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.S.nA(android.content.Context):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.ArrayList nB(android.content.Context r11, int r12) {
        /*
        r10 = this;
        r2 = 0;
        monitor-enter(r10);
        if (r11 == 0) goto L_0x003f;
    L_0x0004:
        r0 = "hmdb";
        r1 = 0;
        r3 = 0;
        r1 = r11.openOrCreateDatabase(r0, r1, r3);	 Catch:{ all -> 0x010a }
        r0 = "hm";
        r0 = r10.ny(r1, r0);	 Catch:{ all -> 0x004f }
        if (r0 == 0) goto L_0x0041;
    L_0x0016:
        r4 = new java.util.ArrayList;	 Catch:{ all -> 0x004f }
        r4.<init>();	 Catch:{ all -> 0x004f }
        switch(r12) {
            case 1: goto L_0x0056;
            case 2: goto L_0x006c;
            default: goto L_0x001e;
        };
    L_0x001e:
        r0 = r2;
        r3 = r2;
    L_0x0020:
        r5 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x00d0 }
        r5.<init>();	 Catch:{ SQLiteException -> 0x00d0 }
        r6 = "hm";
        r5 = r5.append(r6);	 Catch:{ SQLiteException -> 0x00d0 }
        r6 = r10.mp;	 Catch:{ SQLiteException -> 0x00d0 }
        r5 = r5.append(r6);	 Catch:{ SQLiteException -> 0x00d0 }
        r5 = r5.toString();	 Catch:{ SQLiteException -> 0x00d0 }
        if (r0 != 0) goto L_0x0072;
    L_0x0038:
        r1.delete(r5, r3, r0);	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x003b:
        if (r1 != 0) goto L_0x00f4;
    L_0x003d:
        monitor-exit(r10);
        return r2;
    L_0x003f:
        monitor-exit(r10);
        return r2;
    L_0x0041:
        if (r1 != 0) goto L_0x0045;
    L_0x0043:
        monitor-exit(r10);
        return r2;
    L_0x0045:
        r0 = r1.isOpen();	 Catch:{ all -> 0x004f }
        if (r0 == 0) goto L_0x0043;
    L_0x004b:
        r1.close();	 Catch:{ all -> 0x004f }
        goto L_0x0043;
    L_0x004f:
        r0 = move-exception;
    L_0x0050:
        if (r1 != 0) goto L_0x00ff;
    L_0x0052:
        throw r0;	 Catch:{ all -> 0x0053 }
    L_0x0053:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x0056:
        r3 = "time<?";
        r0 = 1;
        r0 = new java.lang.String[r0];	 Catch:{ all -> 0x004f }
        r5 = 0;
        r6 = com.loc.bq.vL();	 Catch:{ all -> 0x004f }
        r8 = 1209600000; // 0x48190800 float:156704.0 double:5.97621805E-315;
        r6 = r6 - r8;
        r6 = java.lang.String.valueOf(r6);	 Catch:{ all -> 0x004f }
        r0[r5] = r6;	 Catch:{ all -> 0x004f }
        goto L_0x0020;
    L_0x006c:
        r0 = "1";
        r3 = r0;
        r0 = r2;
        goto L_0x0020;
    L_0x0072:
        r3 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x00d0 }
        r3.<init>();	 Catch:{ SQLiteException -> 0x00d0 }
        r6 = "SELECT hash, num, extra FROM ";
        r3.append(r6);	 Catch:{ SQLiteException -> 0x00d0 }
        r3.append(r5);	 Catch:{ SQLiteException -> 0x00d0 }
        r5 = " WHERE time < ";
        r5 = r3.append(r5);	 Catch:{ SQLiteException -> 0x00d0 }
        r6 = 0;
        r0 = r0[r6];	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r5.append(r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r5 = ";";
        r0.append(r5);	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r3.toString();	 Catch:{ SQLiteException -> 0x00d0 }
        r3 = 0;
        r3 = r1.rawQuery(r0, r3);	 Catch:{ SQLiteException -> 0x00d0 }
        if (r3 != 0) goto L_0x00a5;
    L_0x009f:
        if (r3 != 0) goto L_0x00e6;
    L_0x00a1:
        if (r1 != 0) goto L_0x00ea;
    L_0x00a3:
        monitor-exit(r10);
        return r4;
    L_0x00a5:
        r0 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x00d0 }
        if (r0 == 0) goto L_0x009f;
    L_0x00ab:
        r0 = 0;
        r0 = r3.getString(r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r5 = 2;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x00d0 }
        r6 = "{";
        r6 = r5.startsWith(r6);	 Catch:{ SQLiteException -> 0x00d0 }
        if (r6 == 0) goto L_0x00c8;
    L_0x00be:
        r4.add(r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r3.moveToNext();	 Catch:{ SQLiteException -> 0x00d0 }
        if (r0 == 0) goto L_0x009f;
    L_0x00c7:
        goto L_0x00ab;
    L_0x00c8:
        r0 = com.loc.bq.wp(r0);	 Catch:{ SQLiteException -> 0x00d0 }
        com.loc.bq.wp(r5);	 Catch:{ SQLiteException -> 0x00d0 }
        goto L_0x00be;
    L_0x00d0:
        r0 = move-exception;
        r0 = r0.getMessage();	 Catch:{ all -> 0x004f }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x004f }
        if (r3 != 0) goto L_0x003b;
    L_0x00db:
        r3 = "no such table";
        r0 = r0.contains(r3);	 Catch:{ all -> 0x004f }
        if (r0 != 0) goto L_0x003b;
    L_0x00e4:
        goto L_0x003b;
    L_0x00e6:
        r3.close();	 Catch:{ SQLiteException -> 0x00d0 }
        goto L_0x00a1;
    L_0x00ea:
        r0 = r1.isOpen();	 Catch:{ all -> 0x0053 }
        if (r0 == 0) goto L_0x00a3;
    L_0x00f0:
        r1.close();	 Catch:{ all -> 0x0053 }
        goto L_0x00a3;
    L_0x00f4:
        r0 = r1.isOpen();	 Catch:{ all -> 0x0053 }
        if (r0 == 0) goto L_0x003d;
    L_0x00fa:
        r1.close();	 Catch:{ all -> 0x0053 }
        goto L_0x003d;
    L_0x00ff:
        r2 = r1.isOpen();	 Catch:{ all -> 0x0053 }
        if (r2 == 0) goto L_0x0052;
    L_0x0105:
        r1.close();	 Catch:{ all -> 0x0053 }
        goto L_0x0052;
    L_0x010a:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.S.nB(android.content.Context, int):java.util.ArrayList");
    }

    public void nv(String str, AmapLoc amapLoc, StringBuilder stringBuilder, Context context) {
        if (context != null) {
            if (this.mq == null) {
                this.mq = bQ.yr("MD5", context.getPackageName());
            }
            JSONObject jSONObject = new JSONObject();
            if (str.contains("#reversegeo")) {
                int indexOf = str.indexOf("#reversegeo");
                str = str.substring(0, indexOf) + str.substring(indexOf + "#reversegeo".length());
            }
            String substring = str.substring(str.lastIndexOf("#") + 1);
            if (substring.equals("cgi")) {
                jSONObject.put("cgi", str.substring(0, str.length() - (("network".length() + 2) + "cgi".length())));
            } else if (!(TextUtils.isEmpty(stringBuilder) || stringBuilder.indexOf("access") == -1)) {
                jSONObject.put("cgi", str.substring(0, str.length() - (substring.length() + ("network".length() + 2))));
                String[] split = stringBuilder.toString().split(",access");
                jSONObject.put("mmac", !split[0].contains("#") ? split[0] : split[0].substring(split[0].lastIndexOf("#") + 1));
            }
            if (bq.wb(jSONObject, "cgi") || bq.wb(jSONObject, "mmac")) {
                StringBuilder stringBuilder2 = new StringBuilder();
                SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                stringBuilder2.append("CREATE TABLE IF NOT EXISTS ").append("hist");
                stringBuilder2.append(this.mp);
                stringBuilder2.append(" (feature VARCHAR PRIMARY KEY, nb VARCHAR, loc VARCHAR, time VARCHAR);");
                openOrCreateDatabase.execSQL(stringBuilder2.toString());
                stringBuilder2.delete(0, stringBuilder2.length());
                stringBuilder2.append("REPLACE INTO ");
                stringBuilder2.append("hist").append(this.mp);
                stringBuilder2.append(" VALUES (?, ?, ?, ?)");
                Object[] objArr = new Object[]{bQ.yn(jSONObject.toString().getBytes("UTF-8"), this.mq), bQ.yn(stringBuilder.toString().getBytes("UTF-8"), this.mq), bQ.yn(amapLoc.zG().getBytes("UTF-8"), this.mq), Long.valueOf(amapLoc.yU())};
                for (int i = 0; i < objArr.length - 1; i++) {
                    objArr[i] = aG.ro((byte[]) objArr[i]);
                }
                openOrCreateDatabase.execSQL(stringBuilder2.toString(), objArr);
                stringBuilder2.delete(0, stringBuilder2.length());
                stringBuilder2.append("SELECT COUNT(*) AS total FROM ");
                stringBuilder2.append("hist").append(this.mp).append(";");
                Cursor rawQuery = openOrCreateDatabase.rawQuery(stringBuilder2.toString(), null);
                if (rawQuery != null && rawQuery.moveToFirst()) {
                    rawQuery.getInt(0);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                stringBuilder2.delete(0, stringBuilder2.length());
                if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                    openOrCreateDatabase.close();
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void nw(android.content.Context r11) {
        /*
        r10 = this;
        r2 = 0;
        r1 = 0;
        monitor-enter(r10);
        if (r11 == 0) goto L_0x0078;
    L_0x0005:
        r0 = "hmdb";
        r3 = 0;
        r4 = 0;
        r8 = r11.openOrCreateDatabase(r0, r3, r4);	 Catch:{ all -> 0x0088 }
        r0 = "hist";
        r0 = r10.ny(r8, r0);	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x007a;
    L_0x0017:
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r9.<init>();	 Catch:{ all -> 0x0088 }
        r0 = "SELECT feature, nb, loc FROM ";
        r9.append(r0);	 Catch:{ all -> 0x0088 }
        r0 = "hist";
        r0 = r9.append(r0);	 Catch:{ all -> 0x0088 }
        r3 = r10.mp;	 Catch:{ all -> 0x0088 }
        r0.append(r3);	 Catch:{ all -> 0x0088 }
        r4 = com.loc.bq.vL();	 Catch:{ all -> 0x0088 }
        r6 = 259200000; // 0xf731400 float:1.1984677E-29 double:1.280618154E-315;
        r4 = r4 - r6;
        r0 = " WHERE time > ";
        r0 = r9.append(r0);	 Catch:{ all -> 0x0088 }
        r0.append(r4);	 Catch:{ all -> 0x0088 }
        r0 = " ORDER BY time ASC";
        r0 = r9.append(r0);	 Catch:{ all -> 0x0088 }
        r3 = ";";
        r0.append(r3);	 Catch:{ all -> 0x0088 }
        r0 = r9.toString();	 Catch:{ SQLiteException -> 0x008b }
        r3 = 0;
        r0 = r8.rawQuery(r0, r3);	 Catch:{ SQLiteException -> 0x008b }
        r7 = r0;
    L_0x0057:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r2.<init>();	 Catch:{ all -> 0x0088 }
        r0 = r10.mq;	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x00a3;
    L_0x0060:
        if (r7 != 0) goto L_0x00b1;
    L_0x0062:
        r0 = 0;
        r1 = r2.length();	 Catch:{ all -> 0x0088 }
        r2.delete(r0, r1);	 Catch:{ all -> 0x0088 }
        if (r7 != 0) goto L_0x02dd;
    L_0x006c:
        r0 = 0;
        r1 = r9.length();	 Catch:{ all -> 0x0088 }
        r9.delete(r0, r1);	 Catch:{ all -> 0x0088 }
        if (r8 != 0) goto L_0x02e2;
    L_0x0076:
        monitor-exit(r10);
        return;
    L_0x0078:
        monitor-exit(r10);
        return;
    L_0x007a:
        if (r8 != 0) goto L_0x007e;
    L_0x007c:
        monitor-exit(r10);
        return;
    L_0x007e:
        r0 = r8.isOpen();	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x007c;
    L_0x0084:
        r8.close();	 Catch:{ all -> 0x0088 }
        goto L_0x007c;
    L_0x0088:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x008b:
        r0 = move-exception;
        r0 = r0.getMessage();	 Catch:{ all -> 0x0088 }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0088 }
        if (r3 == 0) goto L_0x0098;
    L_0x0096:
        r7 = r2;
        goto L_0x0057;
    L_0x0098:
        r3 = "no such table";
        r0 = r0.contains(r3);	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x0096;
    L_0x00a1:
        r7 = r2;
        goto L_0x0057;
    L_0x00a3:
        r0 = "MD5";
        r3 = r11.getPackageName();	 Catch:{ all -> 0x0088 }
        r0 = com.loc.bQ.yr(r0, r3);	 Catch:{ all -> 0x0088 }
        r10.mq = r0;	 Catch:{ all -> 0x0088 }
        goto L_0x0060;
    L_0x00b1:
        r0 = r7.moveToFirst();	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x0062;
    L_0x00b7:
        r3 = r1;
    L_0x00b8:
        r0 = 0;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r1 = "{";
        r0 = r0.startsWith(r1);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x0148;
    L_0x00c6:
        r0 = 0;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r0 = com.loc.aG.rm(r0);	 Catch:{ all -> 0x0088 }
        r1 = new org.json.JSONObject;	 Catch:{ all -> 0x0088 }
        r4 = new java.lang.String;	 Catch:{ all -> 0x0088 }
        r5 = r10.mq;	 Catch:{ all -> 0x0088 }
        r0 = com.loc.bQ.yo(r0, r5);	 Catch:{ all -> 0x0088 }
        r5 = "UTF-8";
        r4.<init>(r0, r5);	 Catch:{ all -> 0x0088 }
        r1.<init>(r4);	 Catch:{ all -> 0x0088 }
        r0 = 0;
        r4 = r2.length();	 Catch:{ all -> 0x0088 }
        r2.delete(r0, r4);	 Catch:{ all -> 0x0088 }
        r0 = 1;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x01ac;
    L_0x00f5:
        r0 = "mmac";
        r0 = com.loc.bq.wb(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x01c8;
    L_0x00fe:
        r0 = 2;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r4 = com.loc.aG.rm(r0);	 Catch:{ all -> 0x0088 }
        r0 = new org.json.JSONObject;	 Catch:{ all -> 0x0088 }
        r5 = new java.lang.String;	 Catch:{ all -> 0x0088 }
        r6 = r10.mq;	 Catch:{ all -> 0x0088 }
        r4 = com.loc.bQ.yo(r4, r6);	 Catch:{ all -> 0x0088 }
        r6 = "UTF-8";
        r5.<init>(r4, r6);	 Catch:{ all -> 0x0088 }
        r0.<init>(r5);	 Catch:{ all -> 0x0088 }
        r4 = "type";
        r4 = com.loc.bq.wb(r0, r4);	 Catch:{ all -> 0x0088 }
        if (r4 != 0) goto L_0x01e1;
    L_0x0123:
        r6 = r3 + 1;
        r3 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ all -> 0x0088 }
        r3.<init>(r0);	 Catch:{ all -> 0x0088 }
        r0 = "";
        r0 = "mmac";
        r0 = com.loc.bq.wb(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x01ec;
    L_0x0136:
        r0 = "cgi";
        r0 = com.loc.bq.wb(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x0273;
    L_0x013f:
        r0 = r7.moveToNext();	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x0062;
    L_0x0145:
        r3 = r6;
        goto L_0x00b8;
    L_0x0148:
        r1 = new org.json.JSONObject;	 Catch:{ all -> 0x0088 }
        r0 = 0;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r1.<init>(r0);	 Catch:{ all -> 0x0088 }
        r0 = 0;
        r4 = r2.length();	 Catch:{ all -> 0x0088 }
        r2.delete(r0, r4);	 Catch:{ all -> 0x0088 }
        r0 = 1;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x018b;
    L_0x0165:
        r0 = "mmac";
        r0 = com.loc.bq.wb(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x0194;
    L_0x016e:
        r0 = new org.json.JSONObject;	 Catch:{ all -> 0x0088 }
        r4 = 2;
        r4 = r7.getString(r4);	 Catch:{ all -> 0x0088 }
        r0.<init>(r4);	 Catch:{ all -> 0x0088 }
        r4 = "type";
        r4 = com.loc.bq.wb(r0, r4);	 Catch:{ all -> 0x0088 }
        if (r4 == 0) goto L_0x0123;
    L_0x0181:
        r4 = "type";
        r5 = "new";
        r0.put(r4, r5);	 Catch:{ all -> 0x0088 }
        goto L_0x0123;
    L_0x018b:
        r0 = 1;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r2.append(r0);	 Catch:{ all -> 0x0088 }
        goto L_0x016e;
    L_0x0194:
        r0 = "#";
        r0 = r2.append(r0);	 Catch:{ all -> 0x0088 }
        r4 = "mmac";
        r4 = r1.getString(r4);	 Catch:{ all -> 0x0088 }
        r0.append(r4);	 Catch:{ all -> 0x0088 }
        r0 = ",access";
        r2.append(r0);	 Catch:{ all -> 0x0088 }
        goto L_0x016e;
    L_0x01ac:
        r0 = 1;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r0 = com.loc.aG.rm(r0);	 Catch:{ all -> 0x0088 }
        r4 = new java.lang.String;	 Catch:{ all -> 0x0088 }
        r5 = r10.mq;	 Catch:{ all -> 0x0088 }
        r0 = com.loc.bQ.yo(r0, r5);	 Catch:{ all -> 0x0088 }
        r5 = "UTF-8";
        r4.<init>(r0, r5);	 Catch:{ all -> 0x0088 }
        r2.append(r4);	 Catch:{ all -> 0x0088 }
        goto L_0x00fe;
    L_0x01c8:
        r0 = "#";
        r0 = r2.append(r0);	 Catch:{ all -> 0x0088 }
        r4 = "mmac";
        r4 = r1.getString(r4);	 Catch:{ all -> 0x0088 }
        r0.append(r4);	 Catch:{ all -> 0x0088 }
        r0 = ",access";
        r2.append(r0);	 Catch:{ all -> 0x0088 }
        goto L_0x00fe;
    L_0x01e1:
        r4 = "type";
        r5 = "new";
        r0.put(r4, r5);	 Catch:{ all -> 0x0088 }
        goto L_0x0123;
    L_0x01ec:
        r0 = "cgi";
        r0 = com.loc.bq.wb(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x0136;
    L_0x01f5:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r0.<init>();	 Catch:{ all -> 0x0088 }
        r4 = "cgi";
        r4 = r1.getString(r4);	 Catch:{ all -> 0x0088 }
        r0 = r0.append(r4);	 Catch:{ all -> 0x0088 }
        r4 = "#";
        r0 = r0.append(r4);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r4.<init>();	 Catch:{ all -> 0x0088 }
        r0 = r4.append(r0);	 Catch:{ all -> 0x0088 }
        r4 = "network#";
        r0 = r0.append(r4);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
        r4 = "cgi";
        r1 = r1.getString(r4);	 Catch:{ all -> 0x0088 }
        r4 = "#";
        r1 = r1.contains(r4);	 Catch:{ all -> 0x0088 }
        if (r1 != 0) goto L_0x025e;
    L_0x0234:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r1.<init>();	 Catch:{ all -> 0x0088 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0088 }
        r1 = "wifi";
        r0 = r0.append(r1);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
    L_0x0248:
        r1 = r3.ze();	 Catch:{ Exception -> 0x02ed }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x02ed }
        if (r1 == 0) goto L_0x02c7;
    L_0x0252:
        r1 = r0;
        r0 = com.loc.aT.sb();	 Catch:{ all -> 0x0088 }
        r5 = 0;
        r4 = r11;
        r0.sc(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x0088 }
        goto L_0x013f;
    L_0x025e:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r1.<init>();	 Catch:{ all -> 0x0088 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0088 }
        r1 = "cgiwifi";
        r0 = r0.append(r1);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
        goto L_0x0248;
    L_0x0273:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r0.<init>();	 Catch:{ all -> 0x0088 }
        r4 = "cgi";
        r4 = r1.getString(r4);	 Catch:{ all -> 0x0088 }
        r0 = r0.append(r4);	 Catch:{ all -> 0x0088 }
        r4 = "#";
        r0 = r0.append(r4);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r4.<init>();	 Catch:{ all -> 0x0088 }
        r0 = r4.append(r0);	 Catch:{ all -> 0x0088 }
        r4 = "network#";
        r0 = r0.append(r4);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
        r4 = "cgi";
        r1 = r1.getString(r4);	 Catch:{ all -> 0x0088 }
        r4 = "#";
        r1 = r1.contains(r4);	 Catch:{ all -> 0x0088 }
        if (r1 == 0) goto L_0x013f;
    L_0x02b2:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r1.<init>();	 Catch:{ all -> 0x0088 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0088 }
        r1 = "cgi";
        r0 = r0.append(r1);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
        goto L_0x0248;
    L_0x02c7:
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x02ed }
        r1.<init>();	 Catch:{ Exception -> 0x02ed }
        r1 = r1.append(r0);	 Catch:{ Exception -> 0x02ed }
        r4 = "#reversegeo";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x02ed }
        r0 = r1.toString();	 Catch:{ Exception -> 0x02ed }
        goto L_0x0252;
    L_0x02dd:
        r7.close();	 Catch:{ all -> 0x0088 }
        goto L_0x006c;
    L_0x02e2:
        r0 = r8.isOpen();	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x0076;
    L_0x02e8:
        r8.close();	 Catch:{ all -> 0x0088 }
        goto L_0x0076;
    L_0x02ed:
        r1 = move-exception;
        goto L_0x0252;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.S.nw(android.content.Context):void");
    }

    public void nx(Context context, int i) {
        String[] strArr = null;
        if (context != null) {
            SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
            if (ny(openOrCreateDatabase, "hist")) {
                String str;
                switch (i) {
                    case 1:
                        str = "time<?";
                        strArr = new String[]{String.valueOf(bq.vL() - 259200000)};
                        break;
                    case 2:
                        str = "1";
                        break;
                    default:
                        str = null;
                        break;
                }
                try {
                    openOrCreateDatabase.delete("hist" + this.mp, str, strArr);
                } catch (SQLiteException e) {
                    Object message = e.getMessage();
                    if (!(TextUtils.isEmpty(message) || message.contains("no such table"))) {
                    }
                }
                if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                    openOrCreateDatabase.close();
                }
                return;
            }
            if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                openOrCreateDatabase.close();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void nz(android.content.Context r9, java.lang.String r10, java.lang.String r11, long r12) {
        /*
        r8 = this;
        r2 = 0;
        r3 = 0;
        monitor-enter(r8);
        r0 = android.text.TextUtils.isEmpty(r10);	 Catch:{ all -> 0x0145 }
        if (r0 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r8);
        return;
    L_0x000b:
        if (r9 == 0) goto L_0x0009;
    L_0x000d:
        r4 = com.loc.bq.wn(r10);	 Catch:{ all -> 0x0145 }
        r5 = com.loc.bq.wn(r11);	 Catch:{ all -> 0x0145 }
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0162 }
        r6.<init>();	 Catch:{ all -> 0x0162 }
        r0 = "hmdb";
        r1 = 0;
        r7 = 0;
        r1 = r9.openOrCreateDatabase(r0, r1, r7);	 Catch:{ all -> 0x0162 }
        r0 = "CREATE TABLE IF NOT EXISTS ";
        r0 = r6.append(r0);	 Catch:{ all -> 0x0141 }
        r7 = "hm";
        r0.append(r7);	 Catch:{ all -> 0x0141 }
        r0 = r8.mp;	 Catch:{ all -> 0x0141 }
        r6.append(r0);	 Catch:{ all -> 0x0141 }
        r0 = " (hash VARCHAR PRIMARY KEY, num INTEGER, extra VARCHAR, time VARCHAR);";
        r6.append(r0);	 Catch:{ all -> 0x0141 }
        r0 = r6.toString();	 Catch:{ all -> 0x0141 }
        r1.execSQL(r0);	 Catch:{ all -> 0x0141 }
        r0 = 0;
        r7 = r6.length();	 Catch:{ all -> 0x0141 }
        r6.delete(r0, r7);	 Catch:{ all -> 0x0141 }
        r0 = "SELECT num FROM ";
        r0 = r6.append(r0);	 Catch:{ all -> 0x0141 }
        r7 = "hm";
        r0.append(r7);	 Catch:{ all -> 0x0141 }
        r0 = r8.mp;	 Catch:{ all -> 0x0141 }
        r6.append(r0);	 Catch:{ all -> 0x0141 }
        r0 = " WHERE hash = '";
        r0 = r6.append(r0);	 Catch:{ all -> 0x0141 }
        r0 = r0.append(r4);	 Catch:{ all -> 0x0141 }
        r7 = "';";
        r0.append(r7);	 Catch:{ all -> 0x0141 }
        r0 = r6.toString();	 Catch:{ SQLiteException -> 0x00c8 }
        r7 = 0;
        r2 = r1.rawQuery(r0, r7);	 Catch:{ SQLiteException -> 0x00c8 }
    L_0x0076:
        if (r2 != 0) goto L_0x00dd;
    L_0x0078:
        r0 = r3;
    L_0x0079:
        if (r0 > 0) goto L_0x00e9;
    L_0x007b:
        r0 = 0;
        r3 = r6.length();	 Catch:{ all -> 0x0141 }
        r6.delete(r0, r3);	 Catch:{ all -> 0x0141 }
        r0 = "REPLACE INTO ";
        r6.append(r0);	 Catch:{ all -> 0x0141 }
        r0 = "hm";
        r0 = r6.append(r0);	 Catch:{ all -> 0x0141 }
        r3 = r8.mp;	 Catch:{ all -> 0x0141 }
        r0.append(r3);	 Catch:{ all -> 0x0141 }
        r0 = " VALUES (?, ?, ?, ?)";
        r6.append(r0);	 Catch:{ all -> 0x0141 }
        r0 = 4;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x0141 }
        r3 = 0;
        r0[r3] = r4;	 Catch:{ all -> 0x0141 }
        r3 = 1;
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x0141 }
        r0[r3] = r4;	 Catch:{ all -> 0x0141 }
        r3 = 2;
        r0[r3] = r5;	 Catch:{ all -> 0x0141 }
        r3 = 3;
        r4 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0141 }
        r0[r3] = r4;	 Catch:{ all -> 0x0141 }
        r3 = r6.toString();	 Catch:{ all -> 0x0141 }
        r1.execSQL(r3, r0);	 Catch:{ all -> 0x0141 }
    L_0x00ba:
        if (r2 != 0) goto L_0x0148;
    L_0x00bc:
        r0 = 0;
        r2 = r6.length();	 Catch:{ all -> 0x0141 }
        r6.delete(r0, r2);	 Catch:{ all -> 0x0141 }
        if (r1 != 0) goto L_0x014d;
    L_0x00c6:
        monitor-exit(r8);
        return;
    L_0x00c8:
        r0 = move-exception;
        r0 = r0.getMessage();	 Catch:{ all -> 0x0141 }
        r7 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0141 }
        if (r7 != 0) goto L_0x0076;
    L_0x00d3:
        r7 = "no such table";
        r0 = r0.contains(r7);	 Catch:{ all -> 0x0141 }
        if (r0 != 0) goto L_0x0076;
    L_0x00dc:
        goto L_0x0076;
    L_0x00dd:
        r0 = r2.moveToNext();	 Catch:{ all -> 0x0141 }
        if (r0 == 0) goto L_0x0078;
    L_0x00e3:
        r0 = 0;
        r0 = r2.getInt(r0);	 Catch:{ all -> 0x0141 }
        goto L_0x0079;
    L_0x00e9:
        r0 = r0 + 1;
        r3 = new android.content.ContentValues;	 Catch:{ all -> 0x0141 }
        r3.<init>();	 Catch:{ all -> 0x0141 }
        r7 = "num";
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ all -> 0x0141 }
        r3.put(r7, r0);	 Catch:{ all -> 0x0141 }
        r0 = "extra";
        r3.put(r0, r5);	 Catch:{ all -> 0x0141 }
        r0 = "time";
        r5 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0141 }
        r3.put(r0, r5);	 Catch:{ all -> 0x0141 }
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0141 }
        r0.<init>();	 Catch:{ all -> 0x0141 }
        r5 = "hash = '";
        r0 = r0.append(r5);	 Catch:{ all -> 0x0141 }
        r0 = r0.append(r4);	 Catch:{ all -> 0x0141 }
        r4 = "'";
        r0 = r0.append(r4);	 Catch:{ all -> 0x0141 }
        r0 = r0.toString();	 Catch:{ all -> 0x0141 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0141 }
        r4.<init>();	 Catch:{ all -> 0x0141 }
        r5 = "hm";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0141 }
        r5 = r8.mp;	 Catch:{ all -> 0x0141 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0141 }
        r4 = r4.toString();	 Catch:{ all -> 0x0141 }
        r5 = 0;
        r1.update(r4, r3, r0, r5);	 Catch:{ all -> 0x0141 }
        goto L_0x00ba;
    L_0x0141:
        r0 = move-exception;
    L_0x0142:
        if (r1 != 0) goto L_0x0158;
    L_0x0144:
        throw r0;	 Catch:{ all -> 0x0145 }
    L_0x0145:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
    L_0x0148:
        r2.close();	 Catch:{ all -> 0x0141 }
        goto L_0x00bc;
    L_0x014d:
        r0 = r1.isOpen();	 Catch:{ all -> 0x0145 }
        if (r0 == 0) goto L_0x00c6;
    L_0x0153:
        r1.close();	 Catch:{ all -> 0x0145 }
        goto L_0x00c6;
    L_0x0158:
        r2 = r1.isOpen();	 Catch:{ all -> 0x0145 }
        if (r2 == 0) goto L_0x0144;
    L_0x015e:
        r1.close();	 Catch:{ all -> 0x0145 }
        goto L_0x0144;
    L_0x0162:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0142;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.S.nz(android.content.Context, java.lang.String, java.lang.String, long):void");
    }
}
