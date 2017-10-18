package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;
import java.util.Set;

public final class d extends b {
    private final LinkedTreeMap gN = new LinkedTreeMap();

    public Set entrySet() {
        return this.gN.entrySet();
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof d)) {
                return false;
            }
            if (!((d) obj).gN.equals(this.gN)) {
                return false;
            }
        }
        return true;
    }

    public b get(String str) {
        return (b) this.gN.get(str);
    }

    public void gw(String str, b bVar) {
        Object obj;
        if (bVar == null) {
            obj = g.gR;
        }
        this.gN.put(str, obj);
    }

    public boolean gx(String str) {
        return this.gN.containsKey(str);
    }

    public int hashCode() {
        return this.gN.hashCode();
    }
}
