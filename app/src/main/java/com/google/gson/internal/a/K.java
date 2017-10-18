package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;

class K extends l {
    K() {
    }

    public Character cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            String dk = bVar.dk();
            if (dk.length() == 1) {
                return Character.valueOf(dk.charAt(0));
            }
            throw new JsonSyntaxException("Expecting character, got: " + dk);
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        ed(aVar, (Character) obj);
    }

    public void ed(a aVar, Character ch) {
        String str = null;
        if (ch != null) {
            str = String.valueOf(ch);
        }
        aVar.dC(str);
    }
}
