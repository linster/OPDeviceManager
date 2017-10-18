package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import java.util.Date;

class t implements m {
    t() {
    }

    public l cX(i iVar, a aVar) {
        return aVar.getRawType() != Date.class ? null : new U();
    }
}
