package com.google.gson.internal;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

class o extends AbstractSet {
    final /* synthetic */ LinkedTreeMap fq;

    o(LinkedTreeMap linkedTreeMap) {
        this.fq = linkedTreeMap;
    }

    public void clear() {
        this.fq.clear();
    }

    public boolean contains(Object obj) {
        return (obj instanceof Entry) && this.fq.eN((Entry) obj) != null;
    }

    public Iterator iterator() {
        return new u(this);
    }

    public boolean remove(Object obj) {
        if (!(obj instanceof Entry)) {
            return false;
        }
        c eN = this.fq.eN((Entry) obj);
        if (eN == null) {
            return false;
        }
        this.fq.eO(eN, true);
        return true;
    }

    public int size() {
        return this.fq.size;
    }
}
