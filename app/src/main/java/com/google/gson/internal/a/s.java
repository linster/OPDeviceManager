package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.util.Calendar;
import java.util.GregorianCalendar;

class s extends l {
    s() {
    }

    public Calendar cR(b bVar) {
        int i = 0;
        if (bVar.df() != JsonToken.NULL) {
            bVar.dd();
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (bVar.df() != JsonToken.END_OBJECT) {
                String dj = bVar.dj();
                int dp = bVar.dp();
                if ("year".equals(dj)) {
                    i6 = dp;
                } else if ("month".equals(dj)) {
                    i5 = dp;
                } else if ("dayOfMonth".equals(dj)) {
                    i4 = dp;
                } else if ("hourOfDay".equals(dj)) {
                    i3 = dp;
                } else if ("minute".equals(dj)) {
                    i2 = dp;
                } else if ("second".equals(dj)) {
                    i = dp;
                }
            }
            bVar.de();
            return new GregorianCalendar(i6, i5, i4, i3, i2, i);
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        dM(aVar, (Calendar) obj);
    }

    public void dM(a aVar, Calendar calendar) {
        if (calendar != null) {
            aVar.dz();
            aVar.dB("year");
            aVar.dF((long) calendar.get(1));
            aVar.dB("month");
            aVar.dF((long) calendar.get(2));
            aVar.dB("dayOfMonth");
            aVar.dF((long) calendar.get(5));
            aVar.dB("hourOfDay");
            aVar.dF((long) calendar.get(11));
            aVar.dB("minute");
            aVar.dF((long) calendar.get(12));
            aVar.dB("second");
            aVar.dF((long) calendar.get(13));
            aVar.dA();
            return;
        }
        aVar.dD();
    }
}
