package com.loc;

import java.util.List;

class aC {
    static void qc(ae aeVar, bo boVar, String str) {
        p bPVar = new bP();
        bPVar.yf(boVar);
        aeVar.nZ(bPVar, str);
    }

    static bo qd(ae aeVar, String str) {
        List oe = aeVar.oe(bP.yj(str), new bP());
        return (oe != null && oe.size() > 0) ? (bo) oe.get(0) : null;
    }
}
