package com.loc;

import android.content.Context;
import java.util.List;

class aJ extends Thread {
    final /* synthetic */ Context pC;
    final /* synthetic */ String pD;
    final /* synthetic */ String pE;

    aJ(Context context, String str, String str2) {
        this.pC = context;
        this.pD = str;
        this.pE = str2;
    }

    public void run() {
        try {
            ae aeVar = new ae(this.pC, v.mu());
            List<bo> oe = aeVar.oe(bP.yi(this.pD), new bP());
            if (oe != null && oe.size() > 0) {
                for (bo boVar : oe) {
                    if (!this.pE.equalsIgnoreCase(boVar.vw())) {
                        k.lB(this.pC, aeVar, boVar.vt());
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
