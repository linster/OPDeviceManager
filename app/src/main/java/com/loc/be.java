package com.loc;

import java.net.Proxy;

public class be {
    private bG rD;
    private aX rE;

    public be(aX aXVar) {
        this(aXVar, 0, -1);
    }

    public be(aX aXVar, long j, long j2) {
        Proxy proxy = null;
        this.rE = aXVar;
        if (aXVar.rk != null) {
            proxy = aXVar.rk;
        }
        this.rD = new bG(this.rE.a, this.rE.b, proxy);
        this.rD.xl(j2);
        this.rD.xk(j);
    }

    public void tz(ah ahVar) {
        this.rD.xm(this.rE.md(), this.rE.mb(), this.rE.mc(), ahVar);
    }
}
