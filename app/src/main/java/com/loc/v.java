package com.loc;

import android.database.sqlite.SQLiteDatabase;

public class v implements P {
    private static v lf;

    private v() {
    }

    public static synchronized v mu() {
        v vVar;
        synchronized (v.class) {
            if (lf == null) {
                lf = new v();
            }
            vVar = lf;
        }
        return vVar;
    }

    public String mv() {
        return "dynamicamapfile.db";
    }

    public int mw() {
        return 1;
    }

    public void mx(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS file (_id integer primary key autoincrement, sdkname  varchar(20), filename varchar(100),md5 varchar(20),version varchar(20),dynamicversion varchar(20),status varchar(20),reservedfield varchar(20));");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void my(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
