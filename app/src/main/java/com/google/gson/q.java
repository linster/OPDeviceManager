package com.google.gson;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class q implements p, a {
    private final DateFormat hB;
    private final DateFormat hC;
    private final DateFormat hD;

    q() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    public q(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    q(String str) {
        this(new SimpleDateFormat(str, Locale.US), new SimpleDateFormat(str));
    }

    q(DateFormat dateFormat, DateFormat dateFormat2) {
        this.hB = dateFormat;
        this.hC = dateFormat2;
        this.hD = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.hD.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private Date hs(b bVar) {
        Date parse;
        synchronized (this.hC) {
            try {
                parse = this.hC.parse(bVar.gu());
            } catch (ParseException e) {
                try {
                    return this.hB.parse(bVar.gu());
                } catch (Throwable e2) {
                    throw new JsonSyntaxException(bVar.gu(), e2);
                } catch (ParseException e3) {
                    return this.hD.parse(bVar.gu());
                }
            }
        }
        return parse;
    }

    public Date c(b bVar, Type type, c cVar) {
        if (bVar instanceof f) {
            Date hs = hs(bVar);
            if (type == Date.class) {
                return hs;
            }
            if (type == Timestamp.class) {
                return new Timestamp(hs.getTime());
            }
            if (type == java.sql.Date.class) {
                return new java.sql.Date(hs.getTime());
            }
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + type);
        }
        throw new JsonParseException("The date should be a string value");
    }

    public /* bridge */ /* synthetic */ b hq(Object obj, Type type, k kVar) {
        return hr((Date) obj, type, kVar);
    }

    public b hr(Date date, Type type, k kVar) {
        b fVar;
        synchronized (this.hC) {
            fVar = new f(this.hB.format(date));
        }
        return fVar;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(q.class.getSimpleName());
        stringBuilder.append('(').append(this.hC.getClass().getSimpleName()).append(')');
        return stringBuilder.toString();
    }
}
