package com.loc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class aR extends SQLiteOpenHelper {
    private P qc;

    public aR(Context context, String str, CursorFactory cursorFactory, int i, P p) {
        super(context, str, cursorFactory, i);
        this.qc = p;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.qc.mx(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        this.qc.my(sQLiteDatabase, i, i2);
    }
}
