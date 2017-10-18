package com.google.gson.internal.a;

import com.google.gson.b.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import java.sql.Time;

class A implements m {
    A() {
    }

    public l cX(i iVar, a aVar) {
        return aVar.getRawType() != Time.class ? null : new z();
    }
}
