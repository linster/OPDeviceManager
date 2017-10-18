package com.google.gson;

import com.google.gson.b.a;
import com.google.gson.internal.k;
import com.google.gson.stream.b;

final class w extends l {
    private final p hI;
    private final a hJ;
    private final i hK;
    private final a hL;
    private final m hM;
    private l hN;

    private w(p pVar, a aVar, i iVar, a aVar2, m mVar) {
        this.hI = pVar;
        this.hJ = aVar;
        this.hK = iVar;
        this.hL = aVar2;
        this.hM = mVar;
    }

    private l delegate() {
        l lVar = this.hN;
        if (lVar != null) {
            return lVar;
        }
        lVar = this.hK.gU(this.hM, this.hL);
        this.hN = lVar;
        return lVar;
    }

    public static m hx(a aVar, Object obj) {
        return new A(obj, aVar, false, null);
    }

    public static m hy(a aVar, Object obj) {
        return new A(obj, aVar, aVar.fs() == aVar.getRawType(), null);
    }

    public Object cR(b bVar) {
        if (this.hJ == null) {
            return delegate().cR(bVar);
        }
        b fb = k.fb(bVar);
        return !fb.gq() ? this.hJ.c(fb, this.hL.fs(), this.hK.hg) : null;
    }

    public void cT(com.google.gson.stream.a aVar, Object obj) {
        if (this.hI == null) {
            delegate().cT(aVar, obj);
        } else if (obj != null) {
            k.fc(this.hI.hq(obj, this.hL.fs(), this.hK.hh), aVar);
        } else {
            aVar.dD();
        }
    }
}
