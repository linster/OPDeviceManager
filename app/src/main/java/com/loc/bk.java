package com.loc;

import java.util.LinkedHashMap;

/* compiled from: SimpleLruCache */
public class bk<K, V> {
    private final LinkedHashMap<K, V> a;
    private int b;
    private int c;

    public bk(int i) {
        if (i > 0) {
            this.c = i;
            this.a = new LinkedHashMap(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public final V b(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        V put;
        synchronized (this) {
            this.b += c(k, v);
            put = this.a.put(k, v);
            if (put != null) {
                this.b -= c(k, put);
            }
        }
        if (put != null) {
            a(false, k, put, v);
        }
        a(this.c);
        return put;
    }

    protected V a(K k) {
        return null;
    }

    public final V b(K k) {
        if (k != null) {
            synchronized (this) {
                V v = this.a.get(k);
                if (v == null) {
                    v = a((Object) k);
                    if (v == null) {
                        return null;
                    }
                    V put;
                    synchronized (this) {
                        put = this.a.put(k, v);
                        if (put == null) {
                            this.b += c(k, v);
                        } else {
                            this.a.put(k, put);
                        }
                    }
                    if (put == null) {
                        a(this.c);
                        return v;
                    }
                    a(false, k, v, put);
                    return put;
                }
                return v;
            }
        }
        throw new NullPointerException("key == null");
    }

    public final V c(K k) {
        if (k != null) {
            V remove;
            synchronized (this) {
                remove = this.a.remove(k);
                if (remove != null) {
                    this.b -= c(k, remove);
                }
            }
            if (remove != null) {
                a(false, k, remove, null);
            }
            return remove;
        }
        throw new NullPointerException("key == null");
    }

    protected void a(boolean z, K k, V v, V v2) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r6) {
        /*
        r5 = this;
        r1 = 0;
    L_0x0001:
        monitor-enter(r5);
        r0 = r5.b;	 Catch:{ all -> 0x0028 }
        if (r0 >= 0) goto L_0x002b;
    L_0x0006:
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0028 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0028 }
        r1.<init>();	 Catch:{ all -> 0x0028 }
        r2 = r5.getClass();	 Catch:{ all -> 0x0028 }
        r2 = r2.getName();	 Catch:{ all -> 0x0028 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x0028 }
        r2 = ".sizeOf() is reporting inconsistent results!";
        r1 = r1.append(r2);	 Catch:{ all -> 0x0028 }
        r1 = r1.toString();	 Catch:{ all -> 0x0028 }
        r0.<init>(r1);	 Catch:{ all -> 0x0028 }
        throw r0;	 Catch:{ all -> 0x0028 }
    L_0x0028:
        r0 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x0028 }
        throw r0;
    L_0x002b:
        r0 = r5.a;	 Catch:{ all -> 0x0028 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0028 }
        if (r0 != 0) goto L_0x0066;
    L_0x0033:
        r0 = r5.b;	 Catch:{ all -> 0x0028 }
        if (r0 <= r6) goto L_0x006b;
    L_0x0037:
        r0 = r5.a;	 Catch:{ all -> 0x0028 }
        r0 = r0.entrySet();	 Catch:{ all -> 0x0028 }
        r2 = r0.iterator();	 Catch:{ all -> 0x0028 }
        r0 = r1;
    L_0x0042:
        r3 = r2.hasNext();	 Catch:{ all -> 0x0028 }
        if (r3 != 0) goto L_0x006d;
    L_0x0048:
        if (r0 == 0) goto L_0x0074;
    L_0x004a:
        r2 = r0.getKey();	 Catch:{ all -> 0x0028 }
        r0 = r0.getValue();	 Catch:{ all -> 0x0028 }
        r3 = r5.a;	 Catch:{ all -> 0x0028 }
        r3.remove(r2);	 Catch:{ all -> 0x0028 }
        r3 = r5.b;	 Catch:{ all -> 0x0028 }
        r4 = r5.c(r2, r0);	 Catch:{ all -> 0x0028 }
        r3 = r3 - r4;
        r5.b = r3;	 Catch:{ all -> 0x0028 }
        monitor-exit(r5);	 Catch:{ all -> 0x0028 }
        r3 = 1;
        r5.a(r3, r2, r0, r1);
        goto L_0x0001;
    L_0x0066:
        r0 = r5.b;	 Catch:{ all -> 0x0028 }
        if (r0 != 0) goto L_0x0006;
    L_0x006a:
        goto L_0x0033;
    L_0x006b:
        monitor-exit(r5);	 Catch:{ all -> 0x0028 }
    L_0x006c:
        return;
    L_0x006d:
        r0 = r2.next();	 Catch:{ all -> 0x0028 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ all -> 0x0028 }
        goto L_0x0042;
    L_0x0074:
        monitor-exit(r5);	 Catch:{ all -> 0x0028 }
        goto L_0x006c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bk.a(int):void");
    }

    private int c(K k, V v) {
        int a = a(k, v);
        if (a >= 0) {
            return a;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    protected int a(K k, V v) {
        return 1;
    }

    public final void a() {
        a(-1);
    }
}
