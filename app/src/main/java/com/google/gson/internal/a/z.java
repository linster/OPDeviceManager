package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class z extends l {
    public static final m do = new A();
    private final DateFormat dp = new SimpleDateFormat("hh:mm:ss a");

    public synchronized Time cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return new Time(this.dp.parse(bVar.dk()).getTime());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        dX(aVar, (Time) obj);
    }

    public synchronized void dX(a aVar, Time time) {
        String str = null;
        synchronized (this) {
            if (time != null) {
                str = this.dp.format(time);
            }
            aVar.dC(str);
        }
    }
}
