package com.google.gson.stream;

import com.google.gson.internal.a.k;
import com.google.gson.internal.t;

class c extends t {
    c() {
    }

    public void ff(b bVar) {
        if (bVar instanceof k) {
            ((k) bVar).dr();
            return;
        }
        int gi = bVar.gu;
        if (gi == 0) {
            gi = bVar.fO();
        }
        if (gi == 13) {
            bVar.gu = 9;
        } else if (gi == 12) {
            bVar.gu = 8;
        } else if (gi != 14) {
            throw new IllegalStateException("Expected a name but was " + bVar.df() + " " + " at line " + bVar.fY() + " column " + bVar.fZ() + " path " + bVar.ge());
        } else {
            bVar.gu = 10;
        }
    }
}
