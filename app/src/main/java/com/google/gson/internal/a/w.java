package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class w extends l {
    w() {
    }

    public StringBuffer cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return new StringBuffer(bVar.dk());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        dW(aVar, (StringBuffer) obj);
    }

    public void dW(a aVar, StringBuffer stringBuffer) {
        String str = null;
        if (stringBuffer != null) {
            str = stringBuffer.toString();
        }
        aVar.dC(str);
    }
}
