package com.loc;

import java.util.HashMap;
import java.util.Map;

class bI extends aX {
    private Map tC = new HashMap();
    private String tD;
    private Map tE = new HashMap();

    bI() {
    }

    public Map mb() {
        return this.tC;
    }

    public Map mc() {
        return this.tE;
    }

    public String md() {
        return this.tD;
    }

    void xA(String str) {
        this.tD = str;
    }

    void xB(Map map) {
        this.tE.clear();
        this.tE.putAll(map);
    }

    void xz(Map map) {
        this.tC.clear();
        this.tC.putAll(map);
    }
}
