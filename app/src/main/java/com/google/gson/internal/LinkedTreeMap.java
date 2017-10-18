package com.google.gson.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public final class LinkedTreeMap extends AbstractMap implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Comparator eY = new d();
    Comparator comparator;
    private o entrySet;
    final c header;
    private y keySet;
    int modCount;
    c root;
    int size;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 0;
        r1 = com.google.gson.internal.LinkedTreeMap.class;
        if (r1 == 0) goto L_0x0011;
    L_0x0007:
        $assertionsDisabled = r0;
        r0 = new com.google.gson.internal.d;
        r0.<init>();
        eY = r0;
    L_0x0011:
        r0 = 1;
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.LinkedTreeMap.<clinit>():void");
    }

    public LinkedTreeMap() {
        this(eY);
    }

    public LinkedTreeMap(Comparator comparator) {
        this.size = 0;
        this.modCount = 0;
        this.header = new c();
        if (comparator == null) {
            comparator = eY;
        }
        this.comparator = comparator;
    }

    private void eQ(c cVar, c cVar2) {
        c cVar3 = cVar.eZ;
        cVar.eZ = null;
        if (cVar2 != null) {
            cVar2.eZ = cVar3;
        }
        if (cVar3 == null) {
            this.root = cVar2;
        } else if (cVar3.fa == cVar) {
            cVar3.fa = cVar2;
        } else if ($assertionsDisabled || cVar3.fb == cVar) {
            cVar3.fb = cVar2;
        } else {
            throw new AssertionError();
        }
    }

    private void eR(c cVar, boolean z) {
        while (cVar != null) {
            c cVar2 = cVar.fa;
            c cVar3 = cVar.fb;
            int i = cVar2 == null ? 0 : cVar2.fg;
            int i2 = cVar3 == null ? 0 : cVar3.fg;
            int i3 = i - i2;
            c cVar4;
            c cVar5;
            if (i3 == -2) {
                cVar4 = cVar3.fa;
                cVar5 = cVar3.fb;
                i = (cVar4 == null ? 0 : cVar4.fg) - (cVar5 == null ? 0 : cVar5.fg);
                if (i != -1) {
                    if (i == 0) {
                        if (z) {
                        }
                    }
                    if ($assertionsDisabled || i == 1) {
                        eT(cVar3);
                        eS(cVar);
                        if (!z) {
                            return;
                        }
                    } else {
                        throw new AssertionError();
                    }
                }
                eS(cVar);
                if (!z) {
                    return;
                }
            } else if (i3 == 2) {
                cVar4 = cVar2.fa;
                cVar5 = cVar2.fb;
                i = (cVar4 == null ? 0 : cVar4.fg) - (cVar5 == null ? 0 : cVar5.fg);
                if (i != 1) {
                    if (i == 0) {
                        if (z) {
                        }
                    }
                    if ($assertionsDisabled || i == -1) {
                        eS(cVar2);
                        eT(cVar);
                        if (!z) {
                            return;
                        }
                    } else {
                        throw new AssertionError();
                    }
                }
                eT(cVar);
                if (!z) {
                    return;
                }
            } else if (i3 == 0) {
                cVar.fg = i + 1;
                if (z) {
                    return;
                }
            } else if ($assertionsDisabled || i3 == -1 || i3 == 1) {
                cVar.fg = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            cVar = cVar.eZ;
        }
    }

    private void eS(c cVar) {
        int i = 0;
        c cVar2 = cVar.fa;
        c cVar3 = cVar.fb;
        c cVar4 = cVar3.fa;
        c cVar5 = cVar3.fb;
        cVar.fb = cVar4;
        if (cVar4 != null) {
            cVar4.eZ = cVar;
        }
        eQ(cVar, cVar3);
        cVar3.fa = cVar;
        cVar.eZ = cVar3;
        cVar.fg = Math.max(cVar2 == null ? 0 : cVar2.fg, cVar4 == null ? 0 : cVar4.fg) + 1;
        int i2 = cVar.fg;
        if (cVar5 != null) {
            i = cVar5.fg;
        }
        cVar3.fg = Math.max(i2, i) + 1;
    }

    private void eT(c cVar) {
        int i = 0;
        c cVar2 = cVar.fa;
        c cVar3 = cVar.fb;
        c cVar4 = cVar2.fa;
        c cVar5 = cVar2.fb;
        cVar.fa = cVar5;
        if (cVar5 != null) {
            cVar5.eZ = cVar;
        }
        eQ(cVar, cVar2);
        cVar2.fb = cVar;
        cVar.eZ = cVar2;
        cVar.fg = Math.max(cVar3 == null ? 0 : cVar3.fg, cVar5 == null ? 0 : cVar5.fg) + 1;
        int i2 = cVar.fg;
        if (cVar4 != null) {
            i = cVar4.fg;
        }
        cVar2.fg = Math.max(i2, i) + 1;
    }

    private boolean equal(Object obj, Object obj2) {
        if (obj != obj2) {
            if (obj == null) {
                return false;
            }
            if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    private Object writeReplace() {
        return new LinkedHashMap(this);
    }

    public void clear() {
        this.root = null;
        this.size = 0;
        this.modCount++;
        c cVar = this.header;
        cVar.fd = cVar;
        cVar.fc = cVar;
    }

    public boolean containsKey(Object obj) {
        return eM(obj) != null;
    }

    c eL(Object obj, boolean z) {
        int i = 0;
        Comparator comparator = this.comparator;
        c cVar = this.root;
        if (cVar != null) {
            Comparable comparable = comparator != eY ? null : (Comparable) obj;
            c cVar2 = cVar;
            while (true) {
                i = comparable == null ? comparator.compare(obj, cVar2.fe) : comparable.compareTo(cVar2.fe);
                if (i != 0) {
                    cVar = i >= 0 ? cVar2.fb : cVar2.fa;
                    if (cVar == null) {
                        break;
                    }
                    cVar2 = cVar;
                } else {
                    return cVar2;
                }
            }
            cVar = cVar2;
        }
        if (!z) {
            return null;
        }
        c cVar3;
        c cVar4 = this.header;
        if (cVar != null) {
            cVar3 = new c(cVar, obj, cVar4, cVar4.fd);
            if (i >= 0) {
                cVar.fb = cVar3;
            } else {
                cVar.fa = cVar3;
            }
            eR(cVar, true);
        } else if (comparator == eY && !(obj instanceof Comparable)) {
            throw new ClassCastException(obj.getClass().getName() + " is not Comparable");
        } else {
            cVar3 = new c(cVar, obj, cVar4, cVar4.fd);
            this.root = cVar3;
        }
        this.size++;
        this.modCount++;
        return cVar3;
    }

    c eM(Object obj) {
        c cVar = null;
        if (obj != null) {
            try {
                cVar = eL(obj, false);
            } catch (ClassCastException e) {
                return cVar;
            }
        }
        return cVar;
    }

    c eN(Entry entry) {
        Object obj = null;
        c eM = eM(entry.getKey());
        if (eM != null && equal(eM.ff, entry.getValue())) {
            obj = 1;
        }
        return obj == null ? null : eM;
    }

    void eO(c cVar, boolean z) {
        int i = 0;
        if (z) {
            cVar.fd.fc = cVar.fc;
            cVar.fc.fd = cVar.fd;
        }
        c cVar2 = cVar.fa;
        c cVar3 = cVar.fb;
        c cVar4 = cVar.eZ;
        if (cVar2 == null || cVar3 == null) {
            if (cVar2 != null) {
                eQ(cVar, cVar2);
                cVar.fa = null;
            } else if (cVar3 == null) {
                eQ(cVar, null);
            } else {
                eQ(cVar, cVar3);
                cVar.fb = null;
            }
            eR(cVar4, false);
            this.size--;
            this.modCount++;
            return;
        }
        int i2;
        cVar2 = cVar2.fg <= cVar3.fg ? cVar3.eU() : cVar2.eV();
        eO(cVar2, false);
        cVar4 = cVar.fa;
        if (cVar4 == null) {
            i2 = 0;
        } else {
            i2 = cVar4.fg;
            cVar2.fa = cVar4;
            cVar4.eZ = cVar2;
            cVar.fa = null;
        }
        cVar4 = cVar.fb;
        if (cVar4 != null) {
            i = cVar4.fg;
            cVar2.fb = cVar4;
            cVar4.eZ = cVar2;
            cVar.fb = null;
        }
        cVar2.fg = Math.max(i2, i) + 1;
        eQ(cVar, cVar2);
    }

    c eP(Object obj) {
        c eM = eM(obj);
        if (eM != null) {
            eO(eM, true);
        }
        return eM;
    }

    public Set entrySet() {
        Set set = this.entrySet;
        if (set != null) {
            return set;
        }
        set = new o(this);
        this.entrySet = set;
        return set;
    }

    public Object get(Object obj) {
        c eM = eM(obj);
        return eM == null ? null : eM.ff;
    }

    public Set keySet() {
        Set set = this.keySet;
        if (set != null) {
            return set;
        }
        set = new y(this);
        this.keySet = set;
        return set;
    }

    public Object put(Object obj, Object obj2) {
        if (obj != null) {
            c eL = eL(obj, true);
            Object obj3 = eL.ff;
            eL.ff = obj2;
            return obj3;
        }
        throw new NullPointerException("key == null");
    }

    public Object remove(Object obj) {
        c eP = eP(obj);
        return eP == null ? null : eP.ff;
    }

    public int size() {
        return this.size;
    }
}
