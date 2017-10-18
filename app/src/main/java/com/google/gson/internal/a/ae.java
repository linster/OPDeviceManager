package com.google.gson.internal.a;

import com.google.gson.i;
import com.google.gson.internal.n;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.lang.reflect.Type;
import java.util.Collection;

final class ae extends l {
    private final l eL;
    private final n eM;

    public ae(i iVar, Type type, l lVar, n nVar) {
        this.eL = new u(iVar, lVar, type);
        this.eM = nVar;
    }

    public Collection cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            Collection collection = (Collection) this.eM.eY();
            bVar.db();
            while (bVar.hasNext()) {
                collection.add(this.eL.cR(bVar));
            }
            bVar.dc();
            return collection;
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ex(aVar, (Collection) obj);
    }

    public void ex(a aVar, Collection collection) {
        if (collection != null) {
            aVar.dx();
            for (Object cT : collection) {
                this.eL.cT(aVar, cT);
            }
            aVar.dy();
            return;
        }
        aVar.dD();
    }
}
