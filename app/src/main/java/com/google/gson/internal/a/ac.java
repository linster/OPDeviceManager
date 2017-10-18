package com.google.gson.internal.a;

import com.google.gson.b;
import com.google.gson.b.a;
import com.google.gson.l;
import com.google.gson.m;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.BitSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

public final class ac {
    public static final l dN = new l();
    public static final m dO = et(Class.class, dN);
    public static final l dP = new G();
    public static final m dQ = et(BitSet.class, dP);
    public static final l dR = new q();
    public static final l dS = new g();
    public static final m dT = eu(Boolean.TYPE, Boolean.class, dR);
    public static final l dU = new Z();
    public static final m dV = eu(Byte.TYPE, Byte.class, dU);
    public static final l dW = new O();
    public static final m dX = eu(Short.TYPE, Short.class, dW);
    public static final l dY = new a();
    public static final m dZ = eu(Integer.TYPE, Integer.class, dY);
    public static final l eA = new b();
    public static final m eB = et(Locale.class, eA);
    public static final l eC = new Q();
    public static final m eD = ew(b.class, eC);
    public static final m eE = new H();
    public static final l ea = new T();
    public static final l eb = new P();
    public static final l ec = new j();
    public static final l ed = new D();
    public static final m ee = et(Number.class, ed);
    public static final l ef = new K();
    public static final m eg = eu(Character.TYPE, Character.class, ef);
    public static final l eh = new Y();
    public static final l ei = new c();
    public static final l ej = new S();
    public static final m ek = et(String.class, eh);
    public static final l el = new I();
    public static final m em = et(StringBuilder.class, el);
    public static final l en = new w();
    public static final m eo = et(StringBuffer.class, en);
    public static final l ep = new e();
    public static final m eq = et(URL.class, ep);
    public static final l er = new ag();
    public static final m es = et(URI.class, er);
    public static final l et = new h();
    public static final m eu = ew(InetAddress.class, et);
    public static final l ev = new aa();
    public static final m ew = et(UUID.class, ev);
    public static final m ex = new L();
    public static final l ey = new s();
    public static final m ez = ev(Calendar.class, GregorianCalendar.class, ey);

    private ac() {
    }

    public static m es(a aVar, l lVar) {
        return new X(aVar, lVar);
    }

    public static m et(Class cls, l lVar) {
        return new f(cls, lVar);
    }

    public static m eu(Class cls, Class cls2, l lVar) {
        return new C(cls, cls2, lVar);
    }

    public static m ev(Class cls, Class cls2, l lVar) {
        return new i(cls, cls2, lVar);
    }

    public static m ew(Class cls, l lVar) {
        return new y(cls, lVar);
    }
}
