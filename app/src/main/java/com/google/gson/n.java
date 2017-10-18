package com.google.gson;

import com.google.gson.b.a;
import com.google.gson.internal.a.ac;
import com.google.gson.internal.b;
import com.google.gson.internal.g;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class n {
    private boolean hA;
    private b hm = b.eR;
    private LongSerializationPolicy hn = LongSerializationPolicy.DEFAULT;
    private h ho = FieldNamingPolicy.IDENTITY;
    private final Map hp = new HashMap();
    private final List hq = new ArrayList();
    private final List hr = new ArrayList();
    private boolean hs;
    private String ht;
    private int hu = 2;
    private int hv = 2;
    private boolean hw;
    private boolean hx;
    private boolean hy = true;
    private boolean hz;

    private void hn(String str, int i, int i2, List list) {
        Object qVar;
        if (str != null && !"".equals(str.trim())) {
            qVar = new q(str);
        } else if (i != 2 && i2 != 2) {
            qVar = new q(i, i2);
        } else {
            return;
        }
        list.add(w.hx(a.fu(Date.class), qVar));
        list.add(w.hx(a.fu(Timestamp.class), qVar));
        list.add(w.hx(a.fu(java.sql.Date.class), qVar));
    }

    public n hk(FieldNamingPolicy fieldNamingPolicy) {
        this.ho = fieldNamingPolicy;
        return this;
    }

    public n hl(Type type, Object obj) {
        boolean z = false;
        if ((obj instanceof p) || (obj instanceof a) || (obj instanceof t) || (obj instanceof l)) {
            z = true;
        }
        g.fa(z);
        if (obj instanceof t) {
            this.hp.put(type, (t) obj);
        }
        if ((obj instanceof p) || (obj instanceof a)) {
            this.hq.add(w.hy(a.ft(type), obj));
        }
        if (obj instanceof l) {
            this.hq.add(ac.es(a.ft(type), (l) obj));
        }
        return this;
    }

    public i hm() {
        List arrayList = new ArrayList();
        arrayList.addAll(this.hq);
        Collections.reverse(arrayList);
        arrayList.addAll(this.hr);
        hn(this.ht, this.hu, this.hv, arrayList);
        return new i(this.hm, this.ho, this.hp, this.hs, this.hw, this.hA, this.hy, this.hz, this.hx, this.hn, arrayList);
    }
}
