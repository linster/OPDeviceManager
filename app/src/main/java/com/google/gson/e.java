package com.google.gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class e extends b implements Iterable {
    private final List gO = new ArrayList();

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof e)) {
                return false;
            }
            if (!((e) obj).gO.equals(this.gO)) {
                return false;
            }
        }
        return true;
    }

    public String gu() {
        if (this.gO.size() == 1) {
            return ((b) this.gO.get(0)).gu();
        }
        throw new IllegalStateException();
    }

    public long gv() {
        if (this.gO.size() == 1) {
            return ((b) this.gO.get(0)).gv();
        }
        throw new IllegalStateException();
    }

    public void gy(b bVar) {
        Object obj;
        if (bVar == null) {
            obj = g.gR;
        }
        this.gO.add(obj);
    }

    public int hashCode() {
        return this.gO.hashCode();
    }

    public Iterator iterator() {
        return this.gO.iterator();
    }
}
