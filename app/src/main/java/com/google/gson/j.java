package com.google.gson;

import com.google.gson.stream.a;
import com.google.gson.stream.b;

class j extends l {
    private l hi;

    j() {
    }

    public Object cR(b bVar) {
        if (this.hi != null) {
            return this.hi.cR(bVar);
        }
        throw new IllegalStateException();
    }

    public void cT(a aVar, Object obj) {
        if (this.hi != null) {
            this.hi.cT(aVar, obj);
            return;
        }
        throw new IllegalStateException();
    }

    public void hi(l lVar) {
        if (this.hi == null) {
            this.hi = lVar;
            return;
        }
        throw new AssertionError();
    }
}
