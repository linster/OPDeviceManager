package com.google.gson.internal;

import java.util.Map.Entry;

final class c implements Entry {
    c eZ;
    c fa;
    c fb;
    c fc;
    c fd;
    final Object fe;
    Object ff;
    int fg;

    c() {
        this.fe = null;
        this.fd = this;
        this.fc = this;
    }

    c(c cVar, Object obj, c cVar2, c cVar3) {
        this.eZ = cVar;
        this.fe = obj;
        this.fg = 1;
        this.fc = cVar2;
        this.fd = cVar3;
        cVar3.fc = this;
        cVar2.fd = this;
    }

    public c eU() {
        for (c cVar = this.fa; cVar != null; cVar = cVar.fa) {
            this = cVar;
        }
        return this;
    }

    public c eV() {
        for (c cVar = this.fb; cVar != null; cVar = cVar.fb) {
            this = cVar;
        }
        return this;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r4) {
        /*
        r3 = this;
        r0 = 0;
        r1 = r4 instanceof java.util.Map.Entry;
        if (r1 != 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        r4 = (java.util.Map.Entry) r4;
        r1 = r3.fe;
        if (r1 == 0) goto L_0x0019;
    L_0x000c:
        r1 = r3.fe;
        r2 = r4.getKey();
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x001f;
    L_0x0018:
        return r0;
    L_0x0019:
        r1 = r4.getKey();
        if (r1 != 0) goto L_0x0018;
    L_0x001f:
        r1 = r3.ff;
        if (r1 == 0) goto L_0x0031;
    L_0x0023:
        r1 = r3.ff;
        r2 = r4.getValue();
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0018;
    L_0x002f:
        r0 = 1;
        goto L_0x0018;
    L_0x0031:
        r1 = r4.getValue();
        if (r1 == 0) goto L_0x002f;
    L_0x0037:
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.c.equals(java.lang.Object):boolean");
    }

    public Object getKey() {
        return this.fe;
    }

    public Object getValue() {
        return this.ff;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.fe != null ? this.fe.hashCode() : 0;
        if (this.ff != null) {
            i = this.ff.hashCode();
        }
        return hashCode ^ i;
    }

    public Object setValue(Object obj) {
        Object obj2 = this.ff;
        this.ff = obj;
        return obj2;
    }

    public String toString() {
        return this.fe + "=" + this.ff;
    }
}
