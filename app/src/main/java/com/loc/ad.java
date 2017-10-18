package com.loc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

class ad extends TimerTask {
    final /* synthetic */ e mL;

    ad(e eVar) {
        this.mL = eVar;
    }

    public void run() {
        if (az.nW[1] <= 2000) {
            int i;
            Thread.currentThread().setPriority(1);
            if (bq.wb(this.mL.jh, "fetchoffdatamobile")) {
                try {
                    boolean equals = "1".equals(this.mL.jh.getString("fetchoffdatamobile"));
                } catch (Exception e) {
                    i = 0;
                }
            } else {
                i = 0;
            }
            if (bN.tL) {
                ArrayList tv = bb.ts().tv();
                if (tv != null) {
                    int size = tv.size();
                    if (size > 0) {
                        if (this.mL.jP == null) {
                            this.mL.jP = this.mL.iR(true);
                        }
                        int i2 = 0;
                        while (i2 < size && i2 < 20) {
                            az.py(this.mL.iY, this.mL.jP, ((af) tv.get(i2)).of(), 1, 0, i == 0, true);
                            i2++;
                        }
                    }
                }
                this.mL.jL();
                try {
                    ArrayList nB = S.nu().nB(this.mL.iY, 1);
                    if (nB != null && nB.size() > 0) {
                        Iterator it = nB.iterator();
                        while (it.hasNext()) {
                            az.pH(this.mL.jP, (String) it.next(), 1, 0);
                        }
                    }
                } catch (Exception e2) {
                }
            }
            return;
        }
        this.mL.jI();
    }
}
