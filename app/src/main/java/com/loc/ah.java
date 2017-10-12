package com.loc;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: DynamicFileDBCreator */
public class ah implements w {
    private static ah a;

    public static synchronized ah c() {
        ah ahVar;
        synchronized (ah.class) {
            if (a == null) {
                a = new ah();
            }
            ahVar = a;
        }
        return ahVar;
    }

    private ah() {
    }

    public String a() {
        return "dynamicamapfile.db";
    }

    public int b() {
        return 1;
    }

    public void a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS file (_id integer primary key autoincrement, sdkname  varchar(20), filename varchar(100),md5 varchar(20),version varchar(20),dynamicversion varchar(20),status varchar(20),reservedfield varchar(20));");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
