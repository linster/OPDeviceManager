package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import java.sql.Date;

class J implements m {
    J() {
    }

    public l cX(i iVar, a aVar) {
        return aVar.getRawType() != Date.class ? null : new W();
    }
}
