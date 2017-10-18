package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.stream.b;
import java.lang.reflect.Field;

class ad extends p {
    final l eF = this.eK.dT(this.eG, this.eH, this.eI);
    final /* synthetic */ i eG;
    final /* synthetic */ Field eH;
    final /* synthetic */ a eI;
    final /* synthetic */ boolean eJ;
    final /* synthetic */ v eK;

    ad(v vVar, String str, boolean z, boolean z2, i iVar, Field field, a aVar, boolean z3) {
        this.eK = vVar;
        this.eG = iVar;
        this.eH = field;
        this.eI = aVar;
        this.eJ = z3;
        super(str, z, z2);
    }

    public boolean dH(Object obj) {
        boolean z = false;
        if (!this.dd) {
            return false;
        }
        if (this.eH.get(obj) != obj) {
            z = true;
        }
        return z;
    }

    void dI(com.google.gson.stream.a aVar, Object obj) {
        new u(this.eG, this.eF, this.eI.fs()).cT(aVar, this.eH.get(obj));
    }

    void dJ(b bVar, Object obj) {
        Object cR = this.eF.cR(bVar);
        if (cR != null || !this.eJ) {
            this.eH.set(obj, cR);
        }
    }
}
