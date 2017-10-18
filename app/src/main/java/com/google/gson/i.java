package com.google.gson;

import com.google.gson.internal.a;
import com.google.gson.internal.a.B;
import com.google.gson.internal.a.E;
import com.google.gson.internal.a.F;
import com.google.gson.internal.a.U;
import com.google.gson.internal.a.W;
import com.google.gson.internal.a.ac;
import com.google.gson.internal.a.af;
import com.google.gson.internal.a.r;
import com.google.gson.internal.a.v;
import com.google.gson.internal.a.z;
import com.google.gson.internal.b;
import com.google.gson.internal.k;
import com.google.gson.stream.JsonToken;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class i {
    private final ThreadLocal gY;
    private final Map gZ;
    private final List ha;
    private final a hb;
    private final boolean hc;
    private final boolean hd;
    private final boolean he;
    private final boolean hf;
    final c hg;
    final k hh;

    public i() {
        this(b.eR, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }

    i(b bVar, h hVar, Map map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, LongSerializationPolicy longSerializationPolicy, List list) {
        this.gY = new ThreadLocal();
        this.gZ = Collections.synchronizedMap(new HashMap());
        this.hg = new y(this);
        this.hh = new x(this);
        this.hb = new a(map);
        this.hc = z;
        this.he = z3;
        this.hd = z4;
        this.hf = z5;
        List arrayList = new ArrayList();
        arrayList.add(ac.eD);
        arrayList.add(F.dw);
        arrayList.add(bVar);
        arrayList.addAll(list);
        arrayList.add(ac.ek);
        arrayList.add(ac.dZ);
        arrayList.add(ac.dT);
        arrayList.add(ac.dV);
        arrayList.add(ac.dX);
        arrayList.add(ac.eu(Long.TYPE, Long.class, gS(longSerializationPolicy)));
        arrayList.add(ac.eu(Double.TYPE, Double.class, gP(z6)));
        arrayList.add(ac.eu(Float.TYPE, Float.class, gQ(z6)));
        arrayList.add(ac.ee);
        arrayList.add(ac.eg);
        arrayList.add(ac.em);
        arrayList.add(ac.eo);
        arrayList.add(ac.et(BigDecimal.class, ac.ei));
        arrayList.add(ac.et(BigInteger.class, ac.ej));
        arrayList.add(ac.eq);
        arrayList.add(ac.es);
        arrayList.add(ac.ew);
        arrayList.add(ac.eB);
        arrayList.add(ac.eu);
        arrayList.add(ac.dQ);
        arrayList.add(U.dD);
        arrayList.add(ac.ez);
        arrayList.add(z.do);
        arrayList.add(W.dJ);
        arrayList.add(ac.ex);
        arrayList.add(af.eN);
        arrayList.add(ac.dO);
        arrayList.add(new B(this.hb));
        arrayList.add(new E(this.hb, z2));
        arrayList.add(new r(this.hb));
        arrayList.add(ac.eE);
        arrayList.add(new v(this.hb, hVar, bVar));
        this.ha = Collections.unmodifiableList(arrayList);
    }

    private l gP(boolean z) {
        return !z ? new u(this) : ac.ec;
    }

    private l gQ(boolean z) {
        return !z ? new v(this) : ac.eb;
    }

    private void gR(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private l gS(LongSerializationPolicy longSerializationPolicy) {
        return longSerializationPolicy != LongSerializationPolicy.DEFAULT ? new s(this) : ac.ea;
    }

    private com.google.gson.stream.a hc(Writer writer) {
        if (this.he) {
            writer.write(")]}'\n");
        }
        com.google.gson.stream.a aVar = new com.google.gson.stream.a(writer);
        if (this.hf) {
            aVar.fv("  ");
        }
        aVar.fA(this.hc);
        return aVar;
    }

    private static void hf(Object obj, com.google.gson.stream.b bVar) {
        if (obj != null) {
            try {
                if (bVar.df() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            } catch (Throwable e2) {
                throw new JsonIOException(e2);
            }
        }
    }

    public l gT(com.google.gson.b.a aVar) {
        Object obj = null;
        l lVar = (l) this.gZ.get(aVar);
        if (lVar != null) {
            return lVar;
        }
        Map map;
        Map map2 = (Map) this.gY.get();
        if (map2 != null) {
            map = map2;
        } else {
            HashMap hashMap = new HashMap();
            this.gY.set(hashMap);
            Object obj2 = hashMap;
            int i = 1;
        }
        j jVar = (j) map.get(aVar);
        if (jVar != null) {
            return jVar;
        }
        try {
            j jVar2 = new j();
            map.put(aVar, jVar2);
            for (m cX : this.ha) {
                lVar = cX.cX(this, aVar);
                if (lVar != null) {
                    jVar2.hi(lVar);
                    this.gZ.put(aVar, lVar);
                    return lVar;
                }
            }
            throw new IllegalArgumentException("GSON cannot handle " + aVar);
        } finally {
            map.remove(aVar);
            if (obj != null) {
                this.gY.remove();
            }
        }
    }

    public l gU(m mVar, com.google.gson.b.a aVar) {
        Object obj = null;
        if (!this.ha.contains(mVar)) {
            int i = 1;
        }
        Object obj2 = obj;
        for (m mVar2 : this.ha) {
            if (obj2 != null) {
                l cX = mVar2.cX(this, aVar);
                if (cX != null) {
                    return cX;
                }
            } else if (mVar2 == mVar) {
                obj2 = 1;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + aVar);
    }

    public l gV(Class cls) {
        return gT(com.google.gson.b.a.fu(cls));
    }

    public String gW(Object obj) {
        return obj != null ? gX(obj, obj.getClass()) : ha(g.gR);
    }

    public String gX(Object obj, Type type) {
        Object stringWriter = new StringWriter();
        gY(obj, type, stringWriter);
        return stringWriter.toString();
    }

    public void gY(Object obj, Type type, Appendable appendable) {
        try {
            gZ(obj, type, hc(k.fd(appendable)));
        } catch (Throwable e) {
            throw new JsonIOException(e);
        }
    }

    public void gZ(Object obj, Type type, com.google.gson.stream.a aVar) {
        l gT = gT(com.google.gson.b.a.ft(type));
        boolean fx = aVar.fx();
        aVar.fw(true);
        boolean fz = aVar.fz();
        aVar.fy(this.hd);
        boolean fB = aVar.fB();
        aVar.fA(this.hc);
        try {
            gT.cT(aVar, obj);
            aVar.fw(fx);
            aVar.fy(fz);
            aVar.fA(fB);
        } catch (Throwable e) {
            throw new JsonIOException(e);
        } catch (Throwable th) {
            aVar.fw(fx);
            aVar.fy(fz);
            aVar.fA(fB);
        }
    }

    public String ha(b bVar) {
        Object stringWriter = new StringWriter();
        hb(bVar, stringWriter);
        return stringWriter.toString();
    }

    public void hb(b bVar, Appendable appendable) {
        try {
            hd(bVar, hc(k.fd(appendable)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void hd(b bVar, com.google.gson.stream.a aVar) {
        boolean fx = aVar.fx();
        aVar.fw(true);
        boolean fz = aVar.fz();
        aVar.fy(this.hd);
        boolean fB = aVar.fB();
        aVar.fA(this.hc);
        try {
            k.fc(bVar, aVar);
            aVar.fw(fx);
            aVar.fy(fz);
            aVar.fA(fB);
        } catch (Throwable e) {
            throw new JsonIOException(e);
        } catch (Throwable th) {
            aVar.fw(fx);
            aVar.fy(fz);
            aVar.fA(fB);
        }
    }

    public Object he(Reader reader, Type type) {
        com.google.gson.stream.b bVar = new com.google.gson.stream.b(reader);
        Object hg = hg(bVar, type);
        hf(hg, bVar);
        return hg;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object hg(com.google.gson.stream.b r5, java.lang.reflect.Type r6) {
        /*
        r4 = this;
        r2 = 1;
        r1 = 0;
        r3 = r5.fN();
        r5.fM(r2);
        r5.df();	 Catch:{ EOFException -> 0x001c, IllegalStateException -> 0x0030, IOException -> 0x0037 }
        r0 = com.google.gson.b.a.ft(r6);	 Catch:{ EOFException -> 0x003e, IllegalStateException -> 0x0030, IOException -> 0x0037 }
        r0 = r4.gT(r0);	 Catch:{ EOFException -> 0x003e, IllegalStateException -> 0x0030, IOException -> 0x0037 }
        r0 = r0.cR(r5);	 Catch:{ EOFException -> 0x003e, IllegalStateException -> 0x0030, IOException -> 0x0037 }
        r5.fM(r3);
        return r0;
    L_0x001c:
        r0 = move-exception;
        r1 = r2;
    L_0x001e:
        if (r1 != 0) goto L_0x002b;
    L_0x0020:
        r1 = new com.google.gson.JsonSyntaxException;	 Catch:{ all -> 0x0026 }
        r1.<init>(r0);	 Catch:{ all -> 0x0026 }
        throw r1;	 Catch:{ all -> 0x0026 }
    L_0x0026:
        r0 = move-exception;
        r5.fM(r3);
        throw r0;
    L_0x002b:
        r5.fM(r3);
        r0 = 0;
        return r0;
    L_0x0030:
        r0 = move-exception;
        r1 = new com.google.gson.JsonSyntaxException;	 Catch:{ all -> 0x0026 }
        r1.<init>(r0);	 Catch:{ all -> 0x0026 }
        throw r1;	 Catch:{ all -> 0x0026 }
    L_0x0037:
        r0 = move-exception;
        r1 = new com.google.gson.JsonSyntaxException;	 Catch:{ all -> 0x0026 }
        r1.<init>(r0);	 Catch:{ all -> 0x0026 }
        throw r1;	 Catch:{ all -> 0x0026 }
    L_0x003e:
        r0 = move-exception;
        goto L_0x001e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.i.hg(com.google.gson.stream.b, java.lang.reflect.Type):java.lang.Object");
    }

    public String toString() {
        return "{serializeNulls:" + this.hc + "factories:" + this.ha + ",instanceCreators:" + this.hb + "}";
    }
}
