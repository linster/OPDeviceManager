package com.google.gson.internal.a;

import com.google.gson.i;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class F extends l {
    public static final m dw = new x();
    private final i dx;

    private F(i iVar) {
        this.dx = iVar;
    }

    public Object cR(b bVar) {
        switch (d.cL[bVar.df().ordinal()]) {
            case 1:
                List arrayList = new ArrayList();
                bVar.db();
                while (bVar.hasNext()) {
                    arrayList.add(cR(bVar));
                }
                bVar.dc();
                return arrayList;
            case 2:
                Map linkedTreeMap = new LinkedTreeMap();
                bVar.dd();
                while (bVar.hasNext()) {
                    linkedTreeMap.put(bVar.dj(), cR(bVar));
                }
                bVar.de();
                return linkedTreeMap;
            case 3:
                return bVar.dk();
            case 4:
                return Double.valueOf(bVar.dn());
            case 5:
                return Boolean.valueOf(bVar.dl());
            case 6:
                bVar.dm();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public void cT(a aVar, Object obj) {
        if (obj != null) {
            l gV = this.dx.gV(obj.getClass());
            if (gV instanceof F) {
                aVar.dz();
                aVar.dA();
                return;
            }
            gV.cT(aVar, obj);
            return;
        }
        aVar.dD();
    }
}
