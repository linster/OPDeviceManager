package com.google.gson.internal.a;

import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.net.InetAddress;

class h extends l {
    h() {
    }

    public InetAddress cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            return InetAddress.getByName(bVar.dk());
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        cZ(aVar, (InetAddress) obj);
    }

    public void cZ(a aVar, InetAddress inetAddress) {
        String str = null;
        if (inetAddress != null) {
            str = inetAddress.getHostAddress();
        }
        aVar.dC(str);
    }
}
