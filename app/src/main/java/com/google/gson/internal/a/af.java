package com.google.gson.internal.a;

import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class af extends l {
    public static final m eN = new n();
    private final Class eO;
    private final l eP;

    public af(i iVar, l lVar, Class cls) {
        this.eP = new u(iVar, lVar, cls);
        this.eO = cls;
    }

    public Object cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            List arrayList = new ArrayList();
            bVar.db();
            while (bVar.hasNext()) {
                arrayList.add(this.eP.cR(bVar));
            }
            bVar.dc();
            Object newInstance = Array.newInstance(this.eO, arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                Array.set(newInstance, i, arrayList.get(i));
            }
            return newInstance;
        }
        bVar.dm();
        return null;
    }

    public void cT(a aVar, Object obj) {
        if (obj != null) {
            aVar.dx();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                this.eP.cT(aVar, Array.get(obj, i));
            }
            aVar.dy();
            return;
        }
        aVar.dD();
    }
}
