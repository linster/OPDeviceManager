package com.loc;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class bH {
    private int b;
    private int c;
    private final LinkedHashMap tB;

    public bH(int i) {
        if (i > 0) {
            this.c = i;
            this.tB = new LinkedHashMap(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    private void xw(int i) {
        while (true) {
            Object key;
            Object value;
            synchronized (this) {
                if (this.b < 0) {
                    break;
                }
                if (this.tB.isEmpty()) {
                    if (this.b != 0) {
                        break;
                    }
                }
                if (this.b > i) {
                    Entry entry = null;
                    for (Entry entry2 : this.tB.entrySet()) {
                    }
                    if (entry != null) {
                        key = entry.getKey();
                        value = entry.getValue();
                        this.tB.remove(key);
                        this.b -= xx(key, value);
                    } else {
                        return;
                    }
                }
                return;
            }
            vs(true, key, value, null);
        }
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    }

    private int xx(Object obj, Object obj2) {
        int vr = vr(obj, obj2);
        if (vr >= 0) {
            return vr;
        }
        throw new IllegalStateException("Negative size: " + obj + "=" + obj2);
    }

    protected int vr(Object obj, Object obj2) {
        return 1;
    }

    protected void vs(boolean z, Object obj, Object obj2, Object obj3) {
    }

    public final Object xs(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            throw new NullPointerException("key == null || value == null");
        }
        Object put;
        synchronized (this) {
            this.b += xx(obj, obj2);
            put = this.tB.put(obj, obj2);
            if (put != null) {
                this.b -= xx(obj, put);
            }
        }
        if (put != null) {
            vs(false, obj, put, obj2);
        }
        xw(this.c);
        return put;
    }

    protected Object xt(Object obj) {
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object xu(java.lang.Object r5) {
        /*
        r4 = this;
        r1 = 0;
        if (r5 == 0) goto L_0x002e;
    L_0x0003:
        monitor-enter(r4);
        r0 = r4.tB;	 Catch:{ all -> 0x0039 }
        r0 = r0.get(r5);	 Catch:{ all -> 0x0039 }
        if (r0 != 0) goto L_0x0037;
    L_0x000c:
        monitor-exit(r4);	 Catch:{ all -> 0x0039 }
        r0 = r4.xt(r5);
        if (r0 == 0) goto L_0x003c;
    L_0x0013:
        monitor-enter(r4);
        r1 = r4.tB;	 Catch:{ all -> 0x0043 }
        r1 = r1.put(r5, r0);	 Catch:{ all -> 0x0043 }
        if (r1 != 0) goto L_0x003d;
    L_0x001c:
        r2 = r4.b;	 Catch:{ all -> 0x0043 }
        r3 = r4.xx(r5, r0);	 Catch:{ all -> 0x0043 }
        r2 = r2 + r3;
        r4.b = r2;	 Catch:{ all -> 0x0043 }
    L_0x0025:
        monitor-exit(r4);	 Catch:{ all -> 0x0043 }
        if (r1 != 0) goto L_0x0046;
    L_0x0028:
        r1 = r4.c;
        r4.xw(r1);
        return r0;
    L_0x002e:
        r0 = new java.lang.NullPointerException;
        r1 = "key == null";
        r0.<init>(r1);
        throw r0;
    L_0x0037:
        monitor-exit(r4);	 Catch:{ all -> 0x0039 }
        return r0;
    L_0x0039:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0039 }
        throw r0;
    L_0x003c:
        return r1;
    L_0x003d:
        r2 = r4.tB;	 Catch:{ all -> 0x0043 }
        r2.put(r5, r1);	 Catch:{ all -> 0x0043 }
        goto L_0x0025;
    L_0x0043:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0043 }
        throw r0;
    L_0x0046:
        r2 = 0;
        r4.vs(r2, r5, r0, r1);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bH.xu(java.lang.Object):java.lang.Object");
    }

    public final Object xv(Object obj) {
        if (obj != null) {
            Object remove;
            synchronized (this) {
                remove = this.tB.remove(obj);
                if (remove != null) {
                    this.b -= xx(obj, remove);
                }
            }
            if (remove != null) {
                vs(false, obj, remove, null);
            }
            return remove;
        }
        throw new NullPointerException("key == null");
    }

    public final void xy() {
        xw(-1);
    }
}
