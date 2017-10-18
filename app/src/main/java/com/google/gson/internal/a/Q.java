package com.google.gson.internal.a;

import com.google.gson.b;
import com.google.gson.d;
import com.google.gson.e;
import com.google.gson.f;
import com.google.gson.g;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.l;
import com.google.gson.stream.a;
import java.util.Iterator;
import java.util.Map.Entry;

class Q extends l {
    Q() {
    }

    public b cR(com.google.gson.stream.b bVar) {
        b eVar;
        switch (M.dy[bVar.df().ordinal()]) {
            case 1:
                return new f(new LazilyParsedNumber(bVar.dk()));
            case 2:
                return new f(Boolean.valueOf(bVar.dl()));
            case 3:
                return new f(bVar.dk());
            case 4:
                bVar.dm();
                return g.gR;
            case 5:
                eVar = new e();
                bVar.db();
                while (bVar.hasNext()) {
                    eVar.gy(cR(bVar));
                }
                bVar.dc();
                return eVar;
            case 6:
                eVar = new d();
                bVar.dd();
                while (bVar.hasNext()) {
                    eVar.gw(bVar.dj(), cR(bVar));
                }
                bVar.de();
                return eVar;
            default:
                throw new IllegalArgumentException();
        }
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        eh(aVar, (b) obj);
    }

    public void eh(a aVar, b bVar) {
        if (bVar == null || bVar.gq()) {
            aVar.dD();
        } else if (bVar.gp()) {
            f gt = bVar.gt();
            if (gt.gC()) {
                aVar.dG(gt.gD());
            } else if (gt.gz()) {
                aVar.dE(gt.gB());
            } else {
                aVar.dC(gt.gu());
            }
        } else if (bVar.gn()) {
            aVar.dx();
            Iterator it = bVar.gs().iterator();
            while (it.hasNext()) {
                eh(aVar, (b) it.next());
            }
            aVar.dy();
        } else if (bVar.go()) {
            aVar.dz();
            for (Entry entry : bVar.gr().entrySet()) {
                aVar.dB((String) entry.getKey());
                eh(aVar, (b) entry.getValue());
            }
            aVar.dA();
        } else {
            throw new IllegalArgumentException("Couldn't write " + bVar.getClass());
        }
    }
}
