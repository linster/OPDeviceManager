package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class D extends l {
    D() {
    }

    public Number cR(b bVar) {
        JsonToken df = bVar.df();
        switch (M.dy[df.ordinal()]) {
            case 1:
                return new LazilyParsedNumber(bVar.dk());
            case 4:
                bVar.dm();
                return null;
            default:
                throw new JsonSyntaxException("Expecting number, got: " + df);
        }
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        dY(aVar, (Number) obj);
    }

    public void dY(a aVar, Number number) {
        aVar.dG(number);
    }
}
