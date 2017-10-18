package com.google.gson.internal.a;

import com.google.gson.b;
import com.google.gson.d;
import com.google.gson.e;
import com.google.gson.f;
import com.google.gson.g;
import com.google.gson.stream.a;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class o extends a {
    private static final Writer cY = new ab();
    private static final f cZ = new f("closed");
    private final List da = new ArrayList();
    private String db;
    private b dc = g.gR;

    public o() {
        super(cY);
    }

    private b dv() {
        return (b) this.da.get(this.da.size() - 1);
    }

    private void dw(b bVar) {
        if (this.db != null) {
            if (!bVar.gq() || fB()) {
                ((d) dv()).gw(this.db, bVar);
            }
            this.db = null;
        } else if (this.da.isEmpty()) {
            this.dc = bVar;
        } else {
            b dv = dv();
            if (dv instanceof e) {
                ((e) dv).gy(bVar);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public void close() {
        if (this.da.isEmpty()) {
            this.da.add(cZ);
            return;
        }
        throw new IOException("Incomplete document");
    }

    public a dA() {
        if (this.da.isEmpty() || this.db != null) {
            throw new IllegalStateException();
        } else if (dv() instanceof d) {
            this.da.remove(this.da.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public a dB(String str) {
        if (this.da.isEmpty() || this.db != null) {
            throw new IllegalStateException();
        } else if (dv() instanceof d) {
            this.db = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public a dC(String str) {
        if (str == null) {
            return dD();
        }
        dw(new f(str));
        return this;
    }

    public a dD() {
        dw(g.gR);
        return this;
    }

    public a dE(boolean z) {
        dw(new f(Boolean.valueOf(z)));
        return this;
    }

    public a dF(long j) {
        dw(new f(Long.valueOf(j)));
        return this;
    }

    public a dG(Number number) {
        if (number == null) {
            return dD();
        }
        if (!fx()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        dw(new f(number));
        return this;
    }

    public a dx() {
        b eVar = new e();
        dw(eVar);
        this.da.add(eVar);
        return this;
    }

    public a dy() {
        if (this.da.isEmpty() || this.db != null) {
            throw new IllegalStateException();
        } else if (dv() instanceof e) {
            this.da.remove(this.da.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public a dz() {
        b dVar = new d();
        dw(dVar);
        this.da.add(dVar);
        return this;
    }

    public void flush() {
    }

    public b get() {
        if (this.da.isEmpty()) {
            return this.dc;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.da);
    }
}
