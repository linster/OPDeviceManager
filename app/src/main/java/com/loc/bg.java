package com.loc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;

public class bg {
    private final int a = 128;
    private ArrayList b = new ArrayList();
    private ArrayList sm = new ArrayList();
    private short[][] sn = ((short[][]) Array.newInstance(Short.TYPE, new int[]{128, 128}));
    private ag so = new ag(this);
    private final String sp = "0";

    public void uZ(int i, String str) {
        int size = this.sm.size();
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\\|");
        if (stringTokenizer.countTokens() >= 3) {
            ArrayList arrayList = new ArrayList();
            while (stringTokenizer.hasMoreElements()) {
                arrayList.add(stringTokenizer.nextToken());
            }
            this.sm.add(new n(this, Double.parseDouble((String) arrayList.get(0)), Double.parseDouble((String) arrayList.get(1)), Double.valueOf(Double.parseDouble((String) arrayList.get(2))).intValue(), i));
            if (this.sm.size() <= 128) {
                for (int i2 = 0; i2 < size; i2++) {
                    for (int i3 = size; i3 < this.sm.size(); i3++) {
                        if (((n) this.sm.get(i2)).lN((n) this.sm.get(i3))) {
                            this.sn[i2][i3] = (short) 1;
                            this.sn[i3][i2] = (short) 1;
                        }
                    }
                }
                arrayList.clear();
                return;
            }
            throw new Exception("Atomic Pos Larger Than MAXN 512!");
        }
    }

    public ArrayList va(double d, double d2) {
        this.so.oi();
        ArrayList arrayList = new ArrayList();
        Iterator it = this.so.b.iterator();
        while (it.hasNext()) {
            ArrayList arrayList2 = (ArrayList) it.next();
            O o = new O(this);
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                o.no((n) this.sm.get(((Integer) it2.next()).intValue()));
            }
            o.nn();
            this.b.add(o);
        }
        if (this.b.isEmpty()) {
            return null;
        }
        Collections.sort(this.b);
        if (!(d == 0.0d || d2 == 0.0d)) {
            n nVar = new n(this, d2, d);
            for (int i = 0; i < this.b.size(); i++) {
                n np = ((O) this.b.get(i)).np();
                double lQ = nVar.lO(np);
                if (np.e <= 0) {
                    if (lQ < 10000.0d && lQ > 2.0d) {
                        ((O) this.b.get(i)).mh = ((O) this.b.get(i)).mh * 5.0d;
                    }
                } else if (lQ < 7000.0d && lQ > 2.0d) {
                    ((O) this.b.get(i)).mh = ((O) this.b.get(i)).mh * 5.0d;
                }
            }
            Collections.sort(this.b);
        }
        for (int i2 = 0; i2 < this.b.size() && arrayList.size() < 5; i2++) {
            arrayList.add(((O) this.b.get(i2)).np());
        }
        return arrayList;
    }
}
