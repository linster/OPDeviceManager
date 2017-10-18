package com.google.gson.internal.a;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class l extends com.google.gson.l {
    l() {
    }

    public Class cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ds(aVar, (Class) obj);
    }

    public void ds(a aVar, Class cls) {
        if (cls != null) {
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + cls.getName() + ". Forgot to register a type adapter?");
        }
        aVar.dD();
    }
}
