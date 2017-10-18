package com.loc;

class aO extends Thread {
    boolean pK = false;
    final /* synthetic */ aB pL;

    public aO(aB aBVar, String str) {
        this.pL = aBVar;
        super(str);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r10 = this;
        r8 = 0;
        r0 = 1;
        r6 = 0;
        r10.pK = r0;
    L_0x0006:
        r0 = r10.pK;	 Catch:{ Throwable -> 0x00be }
        if (r0 != 0) goto L_0x0010;
    L_0x000a:
        r0 = r10.pL;
        r0.oe = r6;
        return;
    L_0x0010:
        r0 = java.lang.Thread.interrupted();	 Catch:{ Throwable -> 0x00be }
        if (r0 != 0) goto L_0x000a;
    L_0x0016:
        r0 = com.amap.api.location.AMapLocationClientOption$AMapLocationMode.Hight_Accuracy;	 Catch:{ Throwable -> 0x00be }
        r1 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r1 = r1.ob;	 Catch:{ Throwable -> 0x00be }
        r1 = r1.ik();	 Catch:{ Throwable -> 0x00be }
        r0 = r0.equals(r1);	 Catch:{ Throwable -> 0x00be }
        if (r0 != 0) goto L_0x00c7;
    L_0x0026:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.ob;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.in();	 Catch:{ Throwable -> 0x00be }
        if (r0 != 0) goto L_0x00ed;
    L_0x0030:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.on;	 Catch:{ Throwable -> 0x00be }
        if (r0 == 0) goto L_0x0118;
    L_0x0036:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r2 = 0;
        r0.ot = r2;	 Catch:{ Throwable -> 0x00be }
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r1 = 1;
        r0.oe = r1;	 Catch:{ Throwable -> 0x00be }
        r0 = android.os.Message.obtain();	 Catch:{ Throwable -> 0x00be }
        r1 = 1;
        r0.what = r1;	 Catch:{ Throwable -> 0x00be }
        r1 = new android.os.Bundle;	 Catch:{ Throwable -> 0x00be }
        r1.<init>();	 Catch:{ Throwable -> 0x00be }
        r2 = "isfirst";
        r3 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.ok;	 Catch:{ Throwable -> 0x00be }
        r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
        r2 = "wifiactivescan";
        r3 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.ob;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.ii();	 Catch:{ Throwable -> 0x00be }
        r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
        r2 = "isNeedAddress";
        r3 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.ob;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.ig();	 Catch:{ Throwable -> 0x00be }
        r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
        r2 = "isKillProcess";
        r3 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.ob;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.im();	 Catch:{ Throwable -> 0x00be }
        r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
        r2 = "httptimeout";
        r3 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r3 = r3.ob;	 Catch:{ Throwable -> 0x00be }
        r4 = r3.io();	 Catch:{ Throwable -> 0x00be }
        r1.putLong(r2, r4);	 Catch:{ Throwable -> 0x00be }
        r0.setData(r1);	 Catch:{ Throwable -> 0x00be }
        r1 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r1 = r1.oo;	 Catch:{ Throwable -> 0x00be }
        r0.replyTo = r1;	 Catch:{ Throwable -> 0x00be }
        r1 = r10.pL;	 Catch:{ Throwable -> 0x016f }
        r1 = r1.on;	 Catch:{ Throwable -> 0x016f }
        if (r1 != 0) goto L_0x0166;
    L_0x00a1:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r1 = 0;
        r0.ok = r1;	 Catch:{ Throwable -> 0x00be }
        r0 = r10.pL;	 Catch:{ InterruptedException -> 0x00b4 }
        r0 = r0.ob;	 Catch:{ InterruptedException -> 0x00b4 }
        r0 = r0.id();	 Catch:{ InterruptedException -> 0x00b4 }
        java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x00b4 }
        goto L_0x0006;
    L_0x00b4:
        r0 = move-exception;
        r0 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00be }
        r0.interrupt();	 Catch:{ Throwable -> 0x00be }
        goto L_0x0006;
    L_0x00be:
        r0 = move-exception;
        com.loc.bq.vC(r0);
        r0.printStackTrace();
        goto L_0x000a;
    L_0x00c7:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.pQ();	 Catch:{ Throwable -> 0x00be }
        if (r0 != 0) goto L_0x0026;
    L_0x00cf:
        r0 = r10.pL;	 Catch:{ InterruptedException -> 0x00e0 }
        r0 = r0.ob;	 Catch:{ InterruptedException -> 0x00e0 }
        r0 = r0.ie();	 Catch:{ InterruptedException -> 0x00e0 }
        if (r0 != 0) goto L_0x0107;
    L_0x00d9:
        r0 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x00e0 }
        goto L_0x0006;
    L_0x00e0:
        r0 = move-exception;
        r1 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00be }
        r1.interrupt();	 Catch:{ Throwable -> 0x00be }
        com.loc.bq.vC(r0);	 Catch:{ Throwable -> 0x00be }
        goto L_0x0006;
    L_0x00ed:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.ob;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.ie();	 Catch:{ Throwable -> 0x00be }
        if (r0 == 0) goto L_0x0030;
    L_0x00f7:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.pR();	 Catch:{ Throwable -> 0x00be }
        if (r0 != 0) goto L_0x0030;
    L_0x00ff:
        r0 = r10.pL;	 Catch:{ Throwable -> 0x00be }
        r0 = r0.ov;	 Catch:{ Throwable -> 0x00be }
        if (r0 == 0) goto L_0x00cf;
    L_0x0105:
        goto L_0x0030;
    L_0x0107:
        r0 = r10.pL;	 Catch:{ InterruptedException -> 0x00e0 }
        r0 = r0.ot;	 Catch:{ InterruptedException -> 0x00e0 }
        r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r0 != 0) goto L_0x00d9;
    L_0x010f:
        r0 = r10.pL;	 Catch:{ InterruptedException -> 0x00e0 }
        r2 = com.loc.bq.vM();	 Catch:{ InterruptedException -> 0x00e0 }
        r0.ot = r2;	 Catch:{ InterruptedException -> 0x00e0 }
        goto L_0x00d9;
    L_0x0118:
        r0 = r10.pL;	 Catch:{ InterruptedException -> 0x012f }
        r1 = r0.or;	 Catch:{ InterruptedException -> 0x012f }
        r1 = r1 + 1;
        r0.or = r1;	 Catch:{ InterruptedException -> 0x012f }
        r0 = r10.pL;	 Catch:{ InterruptedException -> 0x012f }
        r0 = r0.or;	 Catch:{ InterruptedException -> 0x012f }
        r1 = 40;
        if (r0 > r1) goto L_0x0132;
    L_0x0128:
        r0 = 50;
        java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x012f }
        goto L_0x0030;
    L_0x012f:
        r0 = move-exception;
        goto L_0x0030;
    L_0x0132:
        r0 = android.os.Message.obtain();	 Catch:{ InterruptedException -> 0x012f }
        r1 = new android.os.Bundle;	 Catch:{ InterruptedException -> 0x012f }
        r1.<init>();	 Catch:{ InterruptedException -> 0x012f }
        r2 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ InterruptedException -> 0x012f }
        r2.<init>();	 Catch:{ InterruptedException -> 0x012f }
        r3 = 10;
        r2.yz(r3);	 Catch:{ InterruptedException -> 0x012f }
        r3 = "请检查配置文件是否配置服务";
        r2.yD(r3);	 Catch:{ InterruptedException -> 0x012f }
        r3 = "location";
        r1.putParcelable(r3, r2);	 Catch:{ InterruptedException -> 0x012f }
        r0.setData(r1);	 Catch:{ InterruptedException -> 0x012f }
        r1 = 1;
        r0.what = r1;	 Catch:{ InterruptedException -> 0x012f }
        r1 = r10.pL;	 Catch:{ InterruptedException -> 0x012f }
        r1 = r1.oc;	 Catch:{ InterruptedException -> 0x012f }
        if (r1 == 0) goto L_0x0036;
    L_0x015d:
        r1 = r10.pL;	 Catch:{ InterruptedException -> 0x012f }
        r1 = r1.oc;	 Catch:{ InterruptedException -> 0x012f }
        r1.sendMessage(r0);	 Catch:{ InterruptedException -> 0x012f }
        goto L_0x0036;
    L_0x0166:
        r1 = r10.pL;	 Catch:{ Throwable -> 0x016f }
        r1 = r1.on;	 Catch:{ Throwable -> 0x016f }
        r1.send(r0);	 Catch:{ Throwable -> 0x016f }
        goto L_0x00a1;
    L_0x016f:
        r0 = move-exception;
        com.loc.bq.vC(r0);	 Catch:{ Throwable -> 0x00be }
        r0.printStackTrace();	 Catch:{ Throwable -> 0x00be }
        goto L_0x00a1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aO.run():void");
    }
}
