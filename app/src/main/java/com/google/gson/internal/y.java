package com.google.gson.internal;

import java.util.AbstractSet;
import java.util.Iterator;

final class y extends AbstractSet {
    final /* synthetic */ LinkedTreeMap fN;

    y(LinkedTreeMap linkedTreeMap) {
        this.fN = linkedTreeMap;
    }

    public void clear() {
        this.fN.clear();
    }

    public boolean contains(Object obj) {
        return this.fN.containsKey(obj);
    }

    public Iterator iterator() {
        return new I(this);
    }

    public boolean remove(Object obj) {
        return this.fN.eP(obj) != null;
    }

    public int size() {
        return this.fN.size;
    }
}
