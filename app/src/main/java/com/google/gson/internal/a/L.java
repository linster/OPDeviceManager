package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import java.sql.Timestamp;
import java.util.Date;

class L implements m {
    L() {
    }

    public l cX(i iVar, a aVar) {
        return aVar.getRawType() == Timestamp.class ? new N(this, iVar.gV(Date.class)) : null;
    }
}
