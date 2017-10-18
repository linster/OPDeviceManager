package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.b;
import com.google.gson.f;
import com.google.gson.i;
import com.google.gson.internal.k;
import com.google.gson.internal.n;
import com.google.gson.internal.t;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class m extends l {
    private final l cU;
    private final l cV;
    private final n cW;
    final /* synthetic */ E cX;

    public m(E e, i iVar, Type type, l lVar, Type type2, l lVar2, n nVar) {
        this.cX = e;
        this.cU = new u(iVar, lVar, type);
        this.cV = new u(iVar, lVar2, type2);
        this.cW = nVar;
    }

    private String du(b bVar) {
        if (bVar.gp()) {
            f gt = bVar.gt();
            if (gt.gC()) {
                return String.valueOf(gt.gD());
            }
            if (gt.gz()) {
                return Boolean.toString(gt.gB());
            }
            if (gt.gE()) {
                return gt.gu();
            }
            throw new AssertionError();
        } else if (bVar.gq()) {
            return "null";
        } else {
            throw new AssertionError();
        }
    }

    public Map cR(com.google.gson.stream.b bVar) {
        JsonToken df = bVar.df();
        if (df != JsonToken.NULL) {
            Map map = (Map) this.cW.eY();
            Object cR;
            if (df != JsonToken.BEGIN_ARRAY) {
                bVar.dd();
                while (bVar.hasNext()) {
                    t.fH.ff(bVar);
                    cR = this.cU.cR(bVar);
                    if (map.put(cR, this.cV.cR(bVar)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + cR);
                    }
                }
                bVar.de();
            } else {
                bVar.db();
                while (bVar.hasNext()) {
                    bVar.db();
                    cR = this.cU.cR(bVar);
                    if (map.put(cR, this.cV.cR(bVar)) == null) {
                        bVar.dc();
                    } else {
                        throw new JsonSyntaxException("duplicate key: " + cR);
                    }
                }
                bVar.dc();
            }
            return map;
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        dt(aVar, (Map) obj);
    }

    public void dt(a aVar, Map map) {
        int i = 0;
        if (map == null) {
            aVar.dD();
        } else if (this.cX.dv) {
            List arrayList = new ArrayList(map.size());
            List arrayList2 = new ArrayList(map.size());
            int i2 = 0;
            for (Entry entry : map.entrySet()) {
                b hj = this.cU.hj(entry.getKey());
                arrayList.add(hj);
                arrayList2.add(entry.getValue());
                int i3 = (hj.gn() || hj.go()) ? 1 : 0;
                i2 = i3 | i2;
            }
            if (i2 == 0) {
                aVar.dz();
                while (i < arrayList.size()) {
                    aVar.dB(du((b) arrayList.get(i)));
                    this.cV.cT(aVar, arrayList2.get(i));
                    i++;
                }
                aVar.dA();
            } else {
                aVar.dx();
                while (i < arrayList.size()) {
                    aVar.dx();
                    k.fc((b) arrayList.get(i), aVar);
                    this.cV.cT(aVar, arrayList2.get(i));
                    aVar.dy();
                    i++;
                }
                aVar.dy();
            }
        } else {
            aVar.dz();
            for (Entry entry2 : map.entrySet()) {
                aVar.dB(String.valueOf(entry2.getKey()));
                this.cV.cT(aVar, entry2.getValue());
            }
            aVar.dA();
        }
    }
}
