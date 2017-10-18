package com.loc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

class O implements Comparable {
    private ArrayList b;
    private ArrayList mg;
    private double mh;
    private String mi;
    final /* synthetic */ bg mj;

    public O(bg bgVar) {
        this.mj = bgVar;
        this.b = new ArrayList();
        this.mg = new ArrayList();
        this.mh = 0.0d;
        this.mi = "";
        this.mh = 0.0d;
        this.mi = "";
    }

    public /* synthetic */ int compareTo(Object obj) {
        return nm((O) obj);
    }

    public int nm(O o) {
        double d = o.mh - this.mh;
        return d > 0.0d ? 1 : d == 0.0d ? 0 : -1;
    }

    public void nn() {
        double size = (double) this.mg.size();
        Iterator it = this.b.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            d = (((n) it.next()).e <= 1 ? 1.0d : 0.6d) + d;
        }
        this.mh = (d * 3.0d) + size;
        this.mh += ((d * 3.0d) + 0.1d) * (size + 0.1d);
    }

    public void no(n nVar) {
        if (nVar.e == 0) {
            this.mg.add(nVar);
        } else if (nVar.e > 0) {
            this.b.add(nVar);
        }
        if ("0".equals(this.mi)) {
            this.mi = nVar.kP;
        }
    }

    public n np() {
        double d = 0.0d;
        int i = !this.b.isEmpty() ? 3 : 0;
        if (this.mg.isEmpty()) {
            Iterator it = this.b.iterator();
            if (!it.hasNext()) {
                return null;
            }
            n nVar = (n) it.next();
            if (nVar.e != 1) {
                int i2 = nVar.c <= 0 ? 0 : nVar.c;
                return new n(this.mj, (nVar.kN + 0.0d) / 1.0d, (0.0d + nVar.kO) / 1.0d, i2 <= 5000 ? i2 : 5000, this.mi, this.mh, 2);
            }
            return new n(this.mj, nVar.kN, nVar.kO, nVar.c, this.mi, this.mh, 1);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = this.mg.iterator();
        double d2 = 0.0d;
        double d3 = 0.0d;
        while (it2.hasNext()) {
            n nVar2 = (n) it2.next();
            arrayList.add(Integer.valueOf(nVar2.c));
            d += nVar2.kN;
            d3 = 1.0d + d3;
            d2 = nVar2.kO + d2;
        }
        Collections.sort(arrayList);
        i2 = arrayList.size() != 1 ? ((Integer) arrayList.get(arrayList.size() / 2)).intValue() : ((Integer) arrayList.get(0)).intValue();
        int i3 = this.mg.size() != 1 ? i2 <= 500 ? i2 >= 30 ? i2 : 30 : 300 : 500;
        return new n(this.mj, d / d3, d2 / d3, i3, this.mi, this.mh, i);
    }
}
