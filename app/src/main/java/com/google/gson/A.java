package com.google.gson;

import com.google.gson.b.a;
import com.google.gson.internal.g;

class A implements m {
    private final a hQ;
    private final boolean hR;
    private final Class hS;
    private final p hT;
    private final a hU;

    private A(Object obj, a aVar, boolean z, Class cls) {
        a aVar2 = null;
        this.hT = !(obj instanceof p) ? null : (p) obj;
        if (obj instanceof a) {
            aVar2 = (a) obj;
        }
        this.hU = aVar2;
        boolean z2 = (this.hT == null && this.hU == null) ? false : true;
        g.fa(z2);
        this.hQ = aVar;
        this.hR = z;
        this.hS = cls;
    }

    public l cX(i iVar, a aVar) {
        boolean z = false;
        if (this.hQ == null) {
            z = this.hS.isAssignableFrom(aVar.getRawType());
        } else {
            if (!this.hQ.equals(aVar)) {
                if (this.hR) {
                    if (this.hQ.fs() != aVar.getRawType()) {
                    }
                }
            }
            z = true;
        }
        return !z ? null : new w(this.hT, this.hU, iVar, aVar, this, null);
    }
}
