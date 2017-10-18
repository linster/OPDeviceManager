package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.n;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.util.Map;

public final class R extends l {
    private final n dB;
    private final Map dC;

    private R(n nVar, Map map) {
        this.dB = nVar;
        this.dC = map;
    }

    public Object cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            Object eY = this.dB.eY();
            try {
                bVar.dd();
                while (bVar.hasNext()) {
                    p pVar = (p) this.dC.get(bVar.dj());
                    if (pVar != null) {
                        if (pVar.de) {
                            pVar.dJ(bVar, eY);
                        }
                    }
                    bVar.dq();
                }
                bVar.de();
                return eY;
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }
        bVar.dm();
        return null;
    }

    public void cT(a aVar, Object obj) {
        if (obj != null) {
            aVar.dz();
            try {
                for (p pVar : this.dC.values()) {
                    if (pVar.dH(obj)) {
                        aVar.dB(pVar.name);
                        pVar.dI(aVar, obj);
                    }
                }
                aVar.dA();
                return;
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }
        aVar.dD();
    }
}
