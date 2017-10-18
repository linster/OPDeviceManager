package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class U extends l {
    public static final m dD = new t();
    private final DateFormat dE = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat dF = DateFormat.getDateTimeInstance(2, 2);
    private final DateFormat dG = ek();

    private static DateFormat ek() {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    private synchronized Date el(String str) {
        try {
        } catch (ParseException e) {
            try {
                return this.dE.parse(str);
            } catch (ParseException e2) {
                try {
                    return this.dG.parse(str);
                } catch (Throwable e3) {
                    throw new JsonSyntaxException(str, e3);
                }
            }
        }
        return this.dF.parse(str);
    }

    public Date cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return el(bVar.dk());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        em(aVar, (Date) obj);
    }

    public synchronized void em(a aVar, Date date) {
        if (date != null) {
            aVar.dC(this.dE.format(date));
        } else {
            aVar.dD();
        }
    }
}
