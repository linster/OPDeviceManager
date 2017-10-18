package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.sql.Timestamp;
import java.util.Date;

class N extends l {
    final /* synthetic */ L dA;
    final /* synthetic */ l dz;

    N(L l, l lVar) {
        this.dA = l;
        this.dz = lVar;
    }

    public Timestamp cR(b bVar) {
        Date date = (Date) this.dz.cR(bVar);
        return date == null ? null : new Timestamp(date.getTime());
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ee(aVar, (Timestamp) obj);
    }

    public void ee(a aVar, Timestamp timestamp) {
        this.dz.cT(aVar, timestamp);
    }
}
