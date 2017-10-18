package com.loc;

import java.util.ArrayList;
import java.util.Iterator;

class ag {
    private ArrayList b;
    final /* synthetic */ bg mR;

    public ag(bg bgVar) {
        this.mR = bgVar;
        this.b = null;
        this.b = new ArrayList();
    }

    private void oh(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(i));
        arrayList.add(Integer.valueOf(i2));
        this.mR.sn[i][i2] = (short) -1;
        this.mR.sn[i2][i] = (short) -1;
        ArrayList arrayList2 = new ArrayList();
        int i3 = 0;
        while (i3 < this.mR.sm.size()) {
            if (!(this.mR.sn[i][i3] == (short) 0 || this.mR.sn[i2][i3] == (short) 0)) {
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
                this.mR.sn[intValue][num.intValue()] = (short) -1;
                this.mR.sn[num.intValue()][intValue] = (short) -1;
            }
            arrayList.add(Integer.valueOf(intValue));
            int i4 = 0;
            while (i4 < arrayList2.size()) {
                if (this.mR.sn[intValue][((Integer) arrayList2.get(i4)).intValue()] != (short) 0) {
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

    public void oi() {
        int i;
        int size = this.mR.sm.size();
        for (i = 0; i < size; i++) {
            for (int i2 = 0; i2 < size; i2++) {
                if (this.mR.sn[i][i2] > (short) 0) {
                    oh(i, i2);
                }
            }
        }
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = 1;
            i = 0;
            while (i < size) {
                if (this.mR.sn[i3][i] <= (short) 0) {
                    if (this.mR.sn[i3][i] < (short) 0) {
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
