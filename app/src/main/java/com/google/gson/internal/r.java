package com.google.gson.internal;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class r implements Iterator {
    final /* synthetic */ LinkedTreeMap fA;
    c fx;
    c fy;
    int fz;

    private r(LinkedTreeMap linkedTreeMap) {
        this.fA = linkedTreeMap;
        this.fx = this.fA.header.fc;
        this.fy = null;
        this.fz = this.fA.modCount;
    }

    final c fe() {
        c cVar = this.fx;
        if (cVar == this.fA.header) {
            throw new NoSuchElementException();
        } else if (this.fA.modCount == this.fz) {
            this.fx = cVar.fc;
            this.fy = cVar;
            return cVar;
        } else {
            throw new ConcurrentModificationException();
        }
    }

    public final boolean hasNext() {
        return this.fx != this.fA.header;
    }

    public final void remove() {
        if (this.fy != null) {
            this.fA.eO(this.fy, true);
            this.fy = null;
            this.fz = this.fA.modCount;
            return;
        }
        throw new IllegalStateException();
    }
}
