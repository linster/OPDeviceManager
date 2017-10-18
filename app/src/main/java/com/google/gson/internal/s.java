package com.google.gson.internal;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.stream.b;

class s extends l {
    private l fB;
    final /* synthetic */ boolean fC;
    final /* synthetic */ boolean fD;
    final /* synthetic */ i fE;
    final /* synthetic */ a fF;
    final /* synthetic */ b fG;

    s(b bVar, boolean z, boolean z2, i iVar, a aVar) {
        this.fG = bVar;
        this.fC = z;
        this.fD = z2;
        this.fE = iVar;
        this.fF = aVar;
    }

    private l delegate() {
        l lVar = this.fB;
        if (lVar != null) {
            return lVar;
        }
        lVar = this.fE.gU(this.fG, this.fF);
        this.fB = lVar;
        return lVar;
    }

    public Object cR(b bVar) {
        if (!this.fC) {
            return delegate().cR(bVar);
        }
        bVar.dq();
        return null;
    }

    public void cT(com.google.gson.stream.a aVar, Object obj) {
        if (this.fD) {
            aVar.dD();
        } else {
            delegate().cT(aVar, obj);
        }
    }
}
