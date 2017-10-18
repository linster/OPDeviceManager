package com.loc;

import java.util.Comparator;

class bh implements Comparator {
    final /* synthetic */ bb sq;

    bh(bb bbVar) {
        this.sq = bbVar;
    }

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return vd((af) obj, (af) obj2);
    }

    public int vd(af afVar, af afVar2) {
        return afVar2.og() - afVar.og();
    }
}
