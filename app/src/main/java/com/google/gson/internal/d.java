package com.google.gson.internal;

import java.util.Comparator;

class d implements Comparator {
    d() {
    }

    public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        return eW((Comparable) obj, (Comparable) obj2);
    }

    public int eW(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }
}
