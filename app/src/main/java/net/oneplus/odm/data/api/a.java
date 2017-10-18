package net.oneplus.odm.data.api;

import com.google.gson.b;
import com.google.gson.c;
import com.google.gson.d;
import java.lang.reflect.Type;

public class a implements com.google.gson.a {
    public b c(b bVar, Type type, c cVar) {
        b bVar2 = new b();
        d dVar = (d) bVar;
        if (dVar.gx("error")) {
            bVar2.d(dVar.get("error").gu());
            bVar2.e(dVar.get("error_description").gu());
        } else {
            bVar2.f(dVar.get("access_token").gu());
            bVar2.g(dVar.get("token_type").gu());
            bVar2.h(dVar.get("expires_in").gv());
            bVar2.i(dVar.get("scope").gu());
        }
        return bVar2;
    }
}
