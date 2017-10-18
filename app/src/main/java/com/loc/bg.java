package com.loc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.autonavi.aps.amapapi.model.AmapLoc;

import org.json.JSONObject;

/* compiled from: DB */
public class bg {
    private static bg a;
    private String b;
    private String c;

    public bg() {
        this.b = "2.0.201501131131".replace(".", "");
        this.c = null;
    }

    static {
        a = null;
    }

    public static synchronized bg a() {
        bg bgVar;
        synchronized (bg.class) {
            if (a == null) {
                a = new bg();
            }
            bgVar = a;
        }
        return bgVar;
    }

    public void a(String str, AmapLoc amapLoc, StringBuilder stringBuilder, Context context) throws Exception {
        if (context != null) {
            int indexOf;
            if (this.c == null) {
                this.c = bd.a("MD5", context.getPackageName());
            }
            JSONObject jSONObject = new JSONObject();
            if (str.contains("#reversegeo")) {
                indexOf = str.indexOf("#reversegeo");
                String substring = str.substring(0, indexOf);
                str = substring + str.substring(indexOf + "#reversegeo".length());
            }
            String substring2 = str.substring(str.lastIndexOf("#") + 1);
            if (substring2.equals("cgi")) {
                jSONObject.put("cgi", str.substring(0, str.length() - (("network".length() + 2) + "cgi".length())));
            } else if (!(TextUtils.isEmpty(stringBuilder) || stringBuilder.indexOf("access") == -1)) {
                Object substring3;
                indexOf = substring2.length() + ("network".length() + 2);
                jSONObject.put("cgi", str.substring(0, str.length() - indexOf));
                String[] split = stringBuilder.toString().split(",access");
                if (split[0].contains("#")) {
                    substring3 = split[0].substring(split[0].lastIndexOf("#") + 1);
                } else {
                    substring3 = split[0];
                }
                jSONObject.put("mmac", substring3);
            }
            if (br.a(jSONObject, "cgi") || br.a(jSONObject, "mmac")) {
                StringBuilder stringBuilder2 = new StringBuilder();
                SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                stringBuilder2.append("CREATE TABLE IF NOT EXISTS ").append("hist");
                stringBuilder2.append(this.b);
                stringBuilder2.append(" (feature VARCHAR PRIMARY KEY, nb VARCHAR, loc VARCHAR, time VARCHAR);");
                openOrCreateDatabase.execSQL(stringBuilder2.toString());
                stringBuilder2.delete(0, stringBuilder2.length());
                stringBuilder2.append("REPLACE INTO ");
                stringBuilder2.append("hist").append(this.b);
                stringBuilder2.append(" VALUES (?, ?, ?, ?)");
                Object[] objArr = new Object[]{bd.c(jSONObject.toString().getBytes("UTF-8"), this.c), bd.c(stringBuilder.toString().getBytes("UTF-8"), this.c), bd.c(amapLoc.A().getBytes("UTF-8"), this.c), Long.valueOf(amapLoc.i())};
                for (int i = 0; i < objArr.length - 1; i++) {
                    objArr[i] = o.a((byte[]) objArr[i]);
                }
                openOrCreateDatabase.execSQL(stringBuilder2.toString(), objArr);
                stringBuilder2.delete(0, stringBuilder2.length());
                stringBuilder2.append("SELECT COUNT(*) AS total FROM ");
                stringBuilder2.append("hist").append(this.b).append(";");
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
    public synchronized void a(android.content.Context r11) throws java.lang.Exception {
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
        r0 = r10.a(r8, r0);	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x007a;
    L_0x0017:
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r9.<init>();	 Catch:{ all -> 0x0088 }
        r0 = "SELECT feature, nb, loc FROM ";
        r9.append(r0);	 Catch:{ all -> 0x0088 }
        r0 = "hist";
        r0 = r9.append(r0);	 Catch:{ all -> 0x0088 }
        r3 = r10.b;	 Catch:{ all -> 0x0088 }
        r0.append(r3);	 Catch:{ all -> 0x0088 }
        r4 = com.loc.br.a();	 Catch:{ all -> 0x0088 }
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
        r0 = r10.c;	 Catch:{ all -> 0x0088 }
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
        r0 = com.loc.bd.a(r0, r3);	 Catch:{ all -> 0x0088 }
        r10.c = r0;	 Catch:{ all -> 0x0088 }
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
        r0 = com.loc.o.a(r0);	 Catch:{ all -> 0x0088 }
        r1 = new org.json.JSONObject;	 Catch:{ all -> 0x0088 }
        r4 = new java.lang.String;	 Catch:{ all -> 0x0088 }
        r5 = r10.c;	 Catch:{ all -> 0x0088 }
        r0 = com.loc.bd.d(r0, r5);	 Catch:{ all -> 0x0088 }
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
        r0 = com.loc.br.a(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x01c8;
    L_0x00fe:
        r0 = 2;
        r0 = r7.getString(r0);	 Catch:{ all -> 0x0088 }
        r4 = com.loc.o.a(r0);	 Catch:{ all -> 0x0088 }
        r0 = new org.json.JSONObject;	 Catch:{ all -> 0x0088 }
        r5 = new java.lang.String;	 Catch:{ all -> 0x0088 }
        r6 = r10.c;	 Catch:{ all -> 0x0088 }
        r4 = com.loc.bd.d(r4, r6);	 Catch:{ all -> 0x0088 }
        r6 = "UTF-8";
        r5.<init>(r4, r6);	 Catch:{ all -> 0x0088 }
        r0.<init>(r5);	 Catch:{ all -> 0x0088 }
        r4 = "type";
        r4 = com.loc.br.a(r0, r4);	 Catch:{ all -> 0x0088 }
        if (r4 != 0) goto L_0x01e1;
    L_0x0123:
        r6 = r3 + 1;
        r3 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ all -> 0x0088 }
        r3.<init>(r0);	 Catch:{ all -> 0x0088 }
        r0 = "";
        r0 = "mmac";
        r0 = com.loc.br.a(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x01ec;
    L_0x0136:
        r0 = "cgi";
        r0 = com.loc.br.a(r1, r0);	 Catch:{ all -> 0x0088 }
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
        r0 = com.loc.br.a(r1, r0);	 Catch:{ all -> 0x0088 }
        if (r0 != 0) goto L_0x0194;
    L_0x016e:
        r0 = new org.json.JSONObject;	 Catch:{ all -> 0x0088 }
        r4 = 2;
        r4 = r7.getString(r4);	 Catch:{ all -> 0x0088 }
        r0.<init>(r4);	 Catch:{ all -> 0x0088 }
        r4 = "type";
        r4 = com.loc.br.a(r0, r4);	 Catch:{ all -> 0x0088 }
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
        r0 = com.loc.o.a(r0);	 Catch:{ all -> 0x0088 }
        r4 = new java.lang.String;	 Catch:{ all -> 0x0088 }
        r5 = r10.c;	 Catch:{ all -> 0x0088 }
        r0 = com.loc.bd.d(r0, r5);	 Catch:{ all -> 0x0088 }
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
        r0 = com.loc.br.a(r1, r0);	 Catch:{ all -> 0x0088 }
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
        r1 = r3.n();	 Catch:{ Exception -> 0x02ed }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x02ed }
        if (r1 == 0) goto L_0x02c7;
    L_0x0252:
        r1 = r0;
        r0 = com.loc.bf.a();	 Catch:{ all -> 0x0088 }
        r5 = 0;
        r4 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x0088 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bg.a(android.content.Context):void");
    }

    public void a(Context context, int i) throws Exception {
        String[] strArr = null;
        if (context != null) {
            SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
            if (a(openOrCreateDatabase, "hist")) {
                String str;
                switch (i) {
                    case 1:
                        str = "time<?";
                        strArr = new String[]{String.valueOf(br.a() - 259200000)};
                        break;
                    case 2:
                        str = "1";
                        break;
                    default:
                        str = null;
                        break;
                }
                try {
                    openOrCreateDatabase.delete("hist" + this.b, str, strArr);
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
    private boolean a(android.database.sqlite.SQLiteDatabase r8, java.lang.String r9) {
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
        r5 = r7.b;	 Catch:{ Exception -> 0x0054, all -> 0x005e }
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
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bg.a(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
    }

    public synchronized void a(Context context, String str, String str2, long j) throws Exception {
        Throwable th;
        Cursor cursor = null;
        synchronized (this) {
            if (TextUtils.isEmpty(str) || context == null) {
                return;
            }
            String c = br.c(str);
            String c2 = br.c(str2);
            SQLiteDatabase openOrCreateDatabase;
            try {
                int i;
                StringBuilder stringBuilder = new StringBuilder();
                openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                try {
                    stringBuilder.append("CREATE TABLE IF NOT EXISTS ").append("hm");
                    stringBuilder.append(this.b);
                    stringBuilder.append(" (hash VARCHAR PRIMARY KEY, num INTEGER, extra VARCHAR, time VARCHAR);");
                    openOrCreateDatabase.execSQL(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append("SELECT num FROM ").append("hm");
                    stringBuilder.append(this.b);
                    stringBuilder.append(" WHERE hash = '").append(c).append("';");
                    cursor = openOrCreateDatabase.rawQuery(stringBuilder.toString(), null);
                } catch (SQLiteException e) {
                    Object message = e.getMessage();
                    if (!(TextUtils.isEmpty(message) || message.contains("no such table"))) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    openOrCreateDatabase.close();
                    throw th;
                }
                if (cursor != null && cursor.moveToNext()) {
                    i = cursor.getInt(0);
                } else {
                    i = 0;
                }
                if (i <= 0) {
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append("REPLACE INTO ");
                    stringBuilder.append("hm").append(this.b);
                    stringBuilder.append(" VALUES (?, ?, ?, ?)");
                    openOrCreateDatabase.execSQL(stringBuilder.toString(), new Object[]{c, Integer.valueOf(1), c2, Long.valueOf(j)});
                } else {
                    i++;
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("num", Integer.valueOf(i));
                    contentValues.put("extra", c2);
                    contentValues.put("time", Long.valueOf(j));
                    openOrCreateDatabase.update("hm" + this.b, contentValues, "hash = '" + c + "'", null);
                }
                if (cursor != null) {
                    cursor.close();
                }
                stringBuilder.delete(0, stringBuilder.length());
                if (openOrCreateDatabase != null) {
                    if (openOrCreateDatabase.isOpen()) {
                        openOrCreateDatabase.close();
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                Object obj = cursor;
                if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                    openOrCreateDatabase.close();
                }
                throw th;
            }
        }
    }

    public synchronized void b(Context context) throws Exception {
        Throwable th;
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor = null;
        synchronized (this) {
            if (av.a && context != null) {
                try {
                    SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                    Cursor rawQuery;
                    try {
                        if (a(openOrCreateDatabase, "hm")) {
                            long a = br.a() - 1209600000;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("SELECT hash, num, extra, time FROM ");
                            stringBuilder.append("hm").append(this.b);
                            stringBuilder.append(" WHERE time > ").append(a);
                            stringBuilder.append(" ORDER BY num DESC LIMIT 0,");
                            stringBuilder.append(500).append(";");
                            rawQuery = openOrCreateDatabase.rawQuery(stringBuilder.toString(), null);
                            stringBuilder.delete(0, stringBuilder.length());
                            if (rawQuery != null) {
                                rawQuery.moveToFirst();
                                int i = 0;
                                while (true) {
                                    i++;
                                    String string = rawQuery.getString(0);
                                    int i2 = rawQuery.getInt(1);
                                    String string2 = rawQuery.getString(2);
                                    long j = rawQuery.getLong(3);
                                    if (!string2.startsWith("{")) {
                                        string = br.d(string);
                                        string2 = br.d(string2);
                                    }
                                    bi.a().a(context, string, string2, i2, j, false);
                                    if (!rawQuery.moveToNext()) {
                                        break;
                                    }
                                }
                            }
                            if (rawQuery != null) {
                                rawQuery.close();
                            }
                            if (openOrCreateDatabase != null) {
                                if (openOrCreateDatabase.isOpen()) {
                                    openOrCreateDatabase.close();
                                }
                            }
                            return;
                        }
                        if (openOrCreateDatabase != null) {
                            if (openOrCreateDatabase.isOpen()) {
                                openOrCreateDatabase.close();
                            }
                        }
                    } catch (SQLiteException e) {
                        Object message = e.getMessage();
                        rawQuery = (!TextUtils.isEmpty(message) && message.contains("no such table")) ? cursor : cursor;
                    } catch (Throwable th2) {
                        th = th2;
                        sQLiteDatabase = openOrCreateDatabase;
                        sQLiteDatabase.close();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    Object obj = cursor;
                    if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                        sQLiteDatabase.close();
                    }
                    throw th;
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.ArrayList<java.lang.String> b(android.content.Context r11, int r12) throws java.lang.Exception {
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
        r0 = r10.a(r1, r0);	 Catch:{ all -> 0x004f }
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
        r6 = r10.b;	 Catch:{ SQLiteException -> 0x00d0 }
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
        r6 = com.loc.br.a();	 Catch:{ all -> 0x004f }
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
        r0 = com.loc.br.d(r0);	 Catch:{ SQLiteException -> 0x00d0 }
        com.loc.br.d(r5);	 Catch:{ SQLiteException -> 0x00d0 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bg.b(android.content.Context, int):java.util.ArrayList<java.lang.String>");
    }
}
