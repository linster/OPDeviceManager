package com.loc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;

/* compiled from: MPos */
public class au {
    private final int a;
    private ArrayList<a> b;
    private ArrayList<c> c;
    private short[][] d;
    private b e;
    private final String f;

    /* compiled from: MPos */
    class a implements Comparable<a> {
        final /* synthetic */ au a;
        private ArrayList<c> b;
        private ArrayList<c> c;
        private double d;
        private String e;

        public /* synthetic */ int compareTo(Object obj) {
            return a((a) obj);
        }

        public a(au auVar) {
            this.a = auVar;
            this.b = new ArrayList();
            this.c = new ArrayList();
            this.d = 0.0d;
            this.e = "";
            this.d = 0.0d;
            this.e = "";
        }

        public int a(a aVar) {
            double d = aVar.d - this.d;
            if (d > 0.0d) {
                return 1;
            }
            return d == 0.0d ? 0 : -1;
        }

        public void a() {
            double size = (double) this.c.size();
            Iterator it = this.b.iterator();
            double d = 0.0d;
            while (it.hasNext()) {
                double d2;
                if (((c) it.next()).e <= 1) {
                    d2 = 1.0d;
                } else {
                    d2 = 0.6d;
                }
                d = d2 + d;
            }
            this.d = (d * 3.0d) + size;
            this.d += ((d * 3.0d) + 0.1d) * (size + 0.1d);
        }

        public void a(c cVar) {
            if (cVar.e == 0) {
                this.c.add(cVar);
            } else if (cVar.e > 0) {
                this.b.add(cVar);
            }
            if ("0".equals(this.e)) {
                this.e = cVar.d;
            }
        }

        public c b() {
            double d = 0.0d;
            int i = !this.b.isEmpty() ? 3 : 0;
            int i2;
            int i3;
            if (this.c.isEmpty()) {
                Iterator it = this.b.iterator();
                if (!it.hasNext()) {
                    return null;
                }
                c cVar = (c) it.next();
                if (cVar.e != 1) {
                    if (cVar.c <= 0) {
                        i2 = 0;
                    } else {
                        i2 = cVar.c;
                    }
                    double d2 = 0.0d + cVar.b;
                    d = cVar.a + 0.0d;
                    if (i2 <= 5000) {
                        i3 = i2;
                    } else {
                        i3 = 5000;
                    }
                    return new c(this.a, d / 1.0d, d2 / 1.0d, i3, this.e, this.d, 2);
                }
                return new c(this.a, cVar.a, cVar.b, cVar.c, this.e, this.d, 1);
            }
            ArrayList arrayList = new ArrayList();
            Iterator it2 = this.c.iterator();
            double d3 = 0.0d;
            d2 = 0.0d;
            while (it2.hasNext()) {
                c cVar2 = (c) it2.next();
                arrayList.add(Integer.valueOf(cVar2.c));
                d += cVar2.a;
                d2 = 1.0d + d2;
                d3 = cVar2.b + d3;
            }
            Collections.sort(arrayList);
            if (arrayList.size() != 1) {
                i2 = ((Integer) arrayList.get(arrayList.size() / 2)).intValue();
            } else {
                i2 = ((Integer) arrayList.get(0)).intValue();
            }
            if (this.c.size() == 1) {
                i3 = 500;
            } else if (i2 > 500) {
                i3 = 300;
            } else if (i2 >= 30) {
                i3 = i2;
            } else {
                i3 = 30;
            }
            return new c(this.a, d / d2, d3 / d2, i3, this.e, this.d, i);
        }
    }

    /* compiled from: MPos */
    private class b {
        final /* synthetic */ au a;
        private ArrayList<ArrayList<Integer>> b;

        public b(au auVar) {
            this.a = auVar;
            this.b = null;
            this.b = new ArrayList();
        }

        private void a(int i, int i2) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i));
            arrayList.add(Integer.valueOf(i2));
            this.a.d[i][i2] = (short) -1;
            this.a.d[i2][i] = (short) -1;
            ArrayList arrayList2 = new ArrayList();
            int i3 = 0;
            while (i3 < this.a.c.size()) {
                if (!(this.a.d[i][i3] == (short) 0 || this.a.d[i2][i3] == (short) 0)) {
                    arrayList2.add(Integer.valueOf(i3));
                }
                i3++;
            }
            while (!arrayList2.isEmpty()) {
                int intValue = ((Integer) arrayList2.get(0)).intValue();
                arrayList2.remove(0);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    this.a.d[intValue][num.intValue()] = (short) -1;
                    this.a.d[num.intValue()][intValue] = (short) -1;
                }
                arrayList.add(Integer.valueOf(intValue));
                int i4 = 0;
                while (i4 < arrayList2.size()) {
                    if (this.a.d[intValue][((Integer) arrayList2.get(i4)).intValue()] != (short) 0) {
                        i3 = i4 + 1;
                    } else {
                        arrayList2.remove(i4);
                        i3 = i4;
                    }
                    i4 = i3;
                }
            }
            this.b.add(arrayList);
        }

        public void a() throws Exception {
            int i;
            int size = this.a.c.size();
            for (i = 0; i < size; i++) {
                for (int i2 = 0; i2 < size; i2++) {
                    if (this.a.d[i][i2] > (short) 0) {
                        a(i, i2);
                    }
                }
            }
            for (int i3 = 0; i3 < size; i3++) {
                Object obj = 1;
                i = 0;
                while (i < size) {
                    if (this.a.d[i3][i] <= (short) 0) {
                        if (this.a.d[i3][i] < (short) 0) {
                            obj = null;
                            break;
                        }
                        i++;
                    } else {
                        throw new Exception("non visited edge");
                    }
                }
                if (obj != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Integer.valueOf(i3));
                    this.b.add(arrayList);
                }
            }
        }
    }

    /* compiled from: MPos */
    public class c {
        public double a;
        public double b;
        public int c;
        public String d;
        public int e;
        final /* synthetic */ au f;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(com.loc.au.c r13) {
            /*
            r12 = this;
            r10 = 4662219572839972864; // 0x40b3880000000000 float:0.0 double:5000.0;
            r8 = 4647503709213818880; // 0x407f400000000000 float:0.0 double:500.0;
            r6 = 4609434218613702656; // 0x3ff8000000000000 float:0.0 double:1.5;
            r0 = 1;
            r1 = 0;
            r4 = r12.b(r13);
            r2 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
            if (r2 >= 0) goto L_0x0017;
        L_0x0016:
            return r0;
        L_0x0017:
            r2 = r12.e;
            if (r2 > 0) goto L_0x003d;
        L_0x001b:
            r2 = r12.e;
            if (r2 == 0) goto L_0x0062;
        L_0x001f:
            r2 = r12.e;
            if (r2 <= 0) goto L_0x0076;
        L_0x0023:
            r2 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
            if (r2 >= 0) goto L_0x0094;
        L_0x0027:
            r2 = r0;
        L_0x0028:
            if (r2 != 0) goto L_0x003b;
        L_0x002a:
            r2 = r12.c;
            r2 = (double) r2;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x0096;
        L_0x0031:
            r2 = r0;
        L_0x0032:
            if (r2 != 0) goto L_0x003b;
        L_0x0034:
            r2 = r13.c;
            r2 = (double) r2;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x003c;
        L_0x003b:
            r1 = r0;
        L_0x003c:
            return r1;
        L_0x003d:
            r2 = r13.e;
            if (r2 != 0) goto L_0x001b;
        L_0x0041:
            r2 = r12.e;
            if (r2 != r0) goto L_0x0067;
        L_0x0045:
            r2 = 4658815484840378368; // 0x40a7700000000000 float:0.0 double:3000.0;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x0072;
        L_0x004e:
            r2 = r12.c;
            r2 = (double) r2;
            r2 = r2 * r6;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x0070;
        L_0x0056:
            r2 = r0;
        L_0x0057:
            if (r2 != 0) goto L_0x0061;
        L_0x0059:
            r2 = r13.c;
            r2 = (double) r2;
            r2 = r2 * r6;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x0072;
        L_0x0061:
            return r0;
        L_0x0062:
            r2 = r13.e;
            if (r2 > 0) goto L_0x0041;
        L_0x0066:
            goto L_0x001f;
        L_0x0067:
            r2 = r13.e;
            if (r2 == r0) goto L_0x0045;
        L_0x006b:
            r2 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
            if (r2 >= 0) goto L_0x0074;
        L_0x006f:
            return r0;
        L_0x0070:
            r2 = r1;
            goto L_0x0057;
        L_0x0072:
            r0 = r1;
            goto L_0x0061;
        L_0x0074:
            r0 = r1;
            goto L_0x006f;
        L_0x0076:
            r2 = r13.e;
            if (r2 > 0) goto L_0x0023;
        L_0x007a:
            r2 = r12.c;
            r2 = (double) r2;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x0098;
        L_0x0081:
            r2 = r0;
        L_0x0082:
            if (r2 != 0) goto L_0x0092;
        L_0x0084:
            r2 = r13.c;
            r2 = (double) r2;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x009a;
        L_0x008b:
            r2 = r0;
        L_0x008c:
            if (r2 != 0) goto L_0x0092;
        L_0x008e:
            r2 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
            if (r2 >= 0) goto L_0x0093;
        L_0x0092:
            r1 = r0;
        L_0x0093:
            return r1;
        L_0x0094:
            r2 = r1;
            goto L_0x0028;
        L_0x0096:
            r2 = r1;
            goto L_0x0032;
        L_0x0098:
            r2 = r1;
            goto L_0x0082;
        L_0x009a:
            r2 = r1;
            goto L_0x008c;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.au.c.a(com.loc.au$c):boolean");
        }

        public c(au auVar, double d, double d2) {
            this.f = auVar;
            this.a = 0.0d;
            this.b = 0.0d;
            this.c = 0;
            this.d = "0";
            this.e = -1;
            this.a = d;
            this.b = d2;
        }

        public c(au auVar, double d, double d2, int i, int i2) {
            this.f = auVar;
            this.a = 0.0d;
            this.b = 0.0d;
            this.c = 0;
            this.d = "0";
            this.e = -1;
            this.a = d;
            this.b = d2;
            this.c = i;
            this.e = i2;
        }

        public c(au auVar, double d, double d2, int i, String str, double d3, int i2) {
            this.f = auVar;
            this.a = 0.0d;
            this.b = 0.0d;
            this.c = 0;
            this.d = "0";
            this.e = -1;
            this.a = d;
            this.b = d2;
            this.d = str;
            this.c = i;
            this.e = i2;
        }

        private double b(c cVar) {
            double d = (((90.0d - this.a) * 21412.0d) / 90.0d) + 6356725.0d;
            double cos = (Math.cos((this.a * 3.1415926535898d) / 180.0d) * d) * (((cVar.b - this.b) * 3.1415926535898d) / 180.0d);
            d *= ((cVar.a - this.a) * 3.1415926535898d) / 180.0d;
            return Math.sqrt((d * d) + (cos * cos));
        }
    }

    public au() {
        this.a = 128;
        this.b = new ArrayList();
        this.c = new ArrayList();
        this.d = (short[][]) Array.newInstance(Short.TYPE, new int[]{128, 128});
        this.e = new b(this);
        this.f = "0";
    }

    public void a(int i, String str) throws Exception {
        int size = this.c.size();
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\\|");
        if (stringTokenizer.countTokens() >= 3) {
            ArrayList arrayList = new ArrayList();
            while (stringTokenizer.hasMoreElements()) {
                arrayList.add(stringTokenizer.nextToken());
            }
            this.c.add(new c(this, Double.parseDouble((String) arrayList.get(0)), Double.parseDouble((String) arrayList.get(1)), Double.valueOf(Double.parseDouble((String) arrayList.get(2))).intValue(), i));
            if (this.c.size() <= 128) {
                for (int i2 = 0; i2 < size; i2++) {
                    for (int i3 = size; i3 < this.c.size(); i3++) {
                        if (((c) this.c.get(i2)).a((c) this.c.get(i3))) {
                            this.d[i2][i3] = (short) 1;
                            this.d[i3][i2] = (short) 1;
                        }
                    }
                }
                arrayList.clear();
                return;
            }
            throw new Exception("Atomic Pos Larger Than MAXN 512!");
        }
    }

    public ArrayList<c> a(double d, double d2) throws Exception {
        this.e.a();
        ArrayList<c> arrayList = new ArrayList();
        Iterator it = this.e.b.iterator();
        while (it.hasNext()) {
            ArrayList arrayList2 = (ArrayList) it.next();
            a aVar = new a(this);
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                aVar.a((c) this.c.get(((Integer) it2.next()).intValue()));
            }
            aVar.a();
            this.b.add(aVar);
        }
        if (this.b.isEmpty()) {
            return null;
        }
        Collections.sort(this.b);
        if (!(d == 0.0d || d2 == 0.0d)) {
            c cVar = new c(this, d2, d);
            for (int i = 0; i < this.b.size(); i++) {
                c b = ((a) this.b.get(i)).b();
                double b2 = cVar.b(b);
                if (b.e <= 0) {
                    if (b2 < 10000.0d && b2 > 2.0d) {
                        ((a) this.b.get(i)).d = ((a) this.b.get(i)).d * 5.0d;
                    }
                } else if (b2 < 7000.0d && b2 > 2.0d) {
                    ((a) this.b.get(i)).d = ((a) this.b.get(i)).d * 5.0d;
                }
            }
            Collections.sort(this.b);
        }
        for (int i2 = 0; i2 < this.b.size() && arrayList.size() < 5; i2++) {
            arrayList.add(((a) this.b.get(i2)).b());
        }
        return arrayList;
    }
}
