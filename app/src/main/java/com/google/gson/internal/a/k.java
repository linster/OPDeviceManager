package com.google.gson.internal.a;

import com.google.gson.d;
import com.google.gson.e;
import com.google.gson.f;
import com.google.gson.g;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.b;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class k extends b {
    private static final Reader cR = new ah();
    private static final Object cS = new Object();
    private final List cT;

    private Object dg() {
        return this.cT.get(this.cT.size() - 1);
    }

    private Object dh() {
        return this.cT.remove(this.cT.size() - 1);
    }

    private void di(JsonToken jsonToken) {
        if (df() != jsonToken) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + df());
        }
    }

    public void close() {
        this.cT.clear();
        this.cT.add(cS);
    }

    public void db() {
        di(JsonToken.BEGIN_ARRAY);
        this.cT.add(((e) dg()).iterator());
    }

    public void dc() {
        di(JsonToken.END_ARRAY);
        dh();
        dh();
    }

    public void dd() {
        di(JsonToken.BEGIN_OBJECT);
        this.cT.add(((d) dg()).entrySet().iterator());
    }

    public void de() {
        di(JsonToken.END_OBJECT);
        dh();
        dh();
    }

    public JsonToken df() {
        if (this.cT.isEmpty()) {
            return JsonToken.END_DOCUMENT;
        }
        Object dg = dg();
        if (dg instanceof Iterator) {
            boolean z = this.cT.get(this.cT.size() - 2) instanceof d;
            Iterator it = (Iterator) dg;
            if (!it.hasNext()) {
                return !z ? JsonToken.END_ARRAY : JsonToken.END_OBJECT;
            } else if (z) {
                return JsonToken.NAME;
            } else {
                this.cT.add(it.next());
                return df();
            }
        } else if (dg instanceof d) {
            return JsonToken.BEGIN_OBJECT;
        } else {
            if (dg instanceof e) {
                return JsonToken.BEGIN_ARRAY;
            }
            if (dg instanceof f) {
                f fVar = (f) dg;
                if (fVar.gE()) {
                    return JsonToken.STRING;
                }
                if (fVar.gz()) {
                    return JsonToken.BOOLEAN;
                }
                if (fVar.gC()) {
                    return JsonToken.NUMBER;
                }
                throw new AssertionError();
            } else if (dg instanceof g) {
                return JsonToken.NULL;
            } else {
                if (dg != cS) {
                    throw new AssertionError();
                }
                throw new IllegalStateException("JsonReader is closed");
            }
        }
    }

    public String dj() {
        di(JsonToken.NAME);
        Entry entry = (Entry) ((Iterator) dg()).next();
        this.cT.add(entry.getValue());
        return (String) entry.getKey();
    }

    public String dk() {
        JsonToken df = df();
        if (df == JsonToken.STRING || df == JsonToken.NUMBER) {
            return ((f) dh()).gu();
        }
        throw new IllegalStateException("Expected " + JsonToken.STRING + " but was " + df);
    }

    public boolean dl() {
        di(JsonToken.BOOLEAN);
        return ((f) dh()).gB();
    }

    public void dm() {
        di(JsonToken.NULL);
        dh();
    }

    public double dn() {
        JsonToken df = df();
        if (df == JsonToken.NUMBER || df == JsonToken.STRING) {
            double gF = ((f) dg()).gF();
            if (!fN()) {
                if (Double.isNaN(gF) || Double.isInfinite(gF)) {
                    throw new NumberFormatException("JSON forbids NaN and infinities: " + gF);
                }
            }
            dh();
            return gF;
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + df);
    }

    public long do() {
        JsonToken df = df();
        if (df == JsonToken.NUMBER || df == JsonToken.STRING) {
            long gv = ((f) dg()).gv();
            dh();
            return gv;
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + df);
    }

    public int dp() {
        JsonToken df = df();
        if (df == JsonToken.NUMBER || df == JsonToken.STRING) {
            int gG = ((f) dg()).gG();
            dh();
            return gG;
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + df);
    }

    public void dq() {
        if (df() != JsonToken.NAME) {
            dh();
        } else {
            dj();
        }
    }

    public void dr() {
        di(JsonToken.NAME);
        Entry entry = (Entry) ((Iterator) dg()).next();
        this.cT.add(entry.getValue());
        this.cT.add(new f((String) entry.getKey()));
    }

    public boolean hasNext() {
        JsonToken df = df();
        return (df == JsonToken.END_OBJECT || df == JsonToken.END_ARRAY) ? false : true;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
