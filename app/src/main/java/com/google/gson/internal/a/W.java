package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class W extends l {
    public static final m dJ = new J();
    private final DateFormat dK = new SimpleDateFormat("MMM d, yyyy");

    public synchronized Date cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            try {
                return new Date(this.dK.parse(bVar.dk()).getTime());
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        eo(aVar, (Date) obj);
    }

    public synchronized void eo(a aVar, Date date) {
        String str = null;
        synchronized (this) {
            if (date != null) {
                str = this.dK.format(date);
            }
            aVar.dC(str);
        }
    }
}
