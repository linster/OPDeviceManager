package com.loc;

class aq extends Thread {
    final /* synthetic */ br nt;

    aq(br brVar) {
        this.nt = brVar;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r7 = this;
        r1 = 0;
        r0 = r7.nt;	 Catch:{ Throwable -> 0x0015, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r0.wC();	 Catch:{ Throwable -> 0x0015, RemoteException -> 0x0034, InterruptedException -> 0x005b }
    L_0x0006:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.sV;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r0 != 0) goto L_0x0043;
    L_0x000c:
        r0 = r7.nt;
        r0 = r0.wy();
        if (r0 == 0) goto L_0x01e7;
    L_0x0014:
        return;
    L_0x0015:
        r0 = move-exception;
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3.<init>();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2.sZ = r3;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r2.sZ;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3 = 9;
        r2.yz(r3);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r2.sZ;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.getMessage();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2.yD(r0);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        goto L_0x0006;
    L_0x0034:
        r0 = move-exception;
        r0 = r7.nt;
        r0 = r0.wy();
        if (r0 != 0) goto L_0x0014;
    L_0x003d:
        r0 = r7.nt;
        r0.wI();
        goto L_0x0014;
    L_0x0043:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.sW;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r0 != 0) goto L_0x0071;
    L_0x0049:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r0.sX;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        monitor-enter(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r7.nt;	 Catch:{ all -> 0x0058 }
        r0 = r0.wy();	 Catch:{ all -> 0x0058 }
        if (r0 != 0) goto L_0x01de;
    L_0x0056:
        monitor-exit(r2);	 Catch:{ all -> 0x0058 }
        goto L_0x0006;
    L_0x0058:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0058 }
        throw r0;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
    L_0x005b:
        r0 = move-exception;
        r0 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x013d }
        r0.interrupt();	 Catch:{ all -> 0x013d }
        r0 = r7.nt;
        r0 = r0.wy();
        if (r0 != 0) goto L_0x0014;
    L_0x006b:
        r0 = r7.nt;
        r0.wI();
        goto L_0x0014;
    L_0x0071:
        r0 = r7.nt;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r2 = r7.nt;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r3 = r7.nt;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r3 = r3.sP;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r2 = r2.wz(r3);	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r0.sZ = r2;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r0 = r7.nt;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r0 = r0.sU;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        if (r0 != 0) goto L_0x0106;
    L_0x0087:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3 = r0.sX;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        monitor-enter(r3);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r0 = r0.sZ;	 Catch:{ all -> 0x015b }
        if (r0 != 0) goto L_0x0147;
    L_0x0092:
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r2 = 0;
        r0.sW = r2;	 Catch:{ all -> 0x015b }
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r0 = r0.sQ;	 Catch:{ all -> 0x015b }
        if (r0 != 0) goto L_0x015e;
    L_0x009d:
        r0 = r1;
    L_0x009e:
        monitor-exit(r3);	 Catch:{ all -> 0x015b }
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2.wA();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2.wB();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = com.loc.f.ky();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r2 != 0) goto L_0x01a6;
    L_0x00af:
        r0 = com.loc.f.kf();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r0 != 0) goto L_0x01bb;
    L_0x00b5:
        r0 = com.loc.f.ki();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r0 != 0) goto L_0x01c6;
    L_0x00bb:
        r0 = com.loc.f.kk();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r0 == 0) goto L_0x0006;
    L_0x00c1:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.sT;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r0 != 0) goto L_0x0006;
    L_0x00c9:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = 1;
        r0.sT = r2;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.sN;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = 5;
        r0.sendEmptyMessage(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        goto L_0x0006;
    L_0x00d9:
        r0 = move-exception;
        r1 = r7.nt;	 Catch:{ all -> 0x013d }
        r2 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ all -> 0x013d }
        r2.<init>();	 Catch:{ all -> 0x013d }
        r1.sZ = r2;	 Catch:{ all -> 0x013d }
        r1 = r7.nt;	 Catch:{ all -> 0x013d }
        r1 = r1.sZ;	 Catch:{ all -> 0x013d }
        r2 = 8;
        r1.yz(r2);	 Catch:{ all -> 0x013d }
        r1 = r7.nt;	 Catch:{ all -> 0x013d }
        r1 = r1.sZ;	 Catch:{ all -> 0x013d }
        r0 = r0.getMessage();	 Catch:{ all -> 0x013d }
        r1.yD(r0);	 Catch:{ all -> 0x013d }
        r0 = r7.nt;
        r0 = r0.wy();
        if (r0 != 0) goto L_0x0014;
    L_0x00ff:
        r0 = r7.nt;
        r0.wI();
        goto L_0x0014;
    L_0x0106:
        r0 = r7.nt;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r2 = r7.nt;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r2 = r2.sU;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r3 = r7.nt;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r3 = r3.sZ;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r4 = 0;
        r4 = new java.lang.String[r4];	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r2 = r2.iL(r3, r4);	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        r0.sZ = r2;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        goto L_0x0087;
    L_0x011d:
        r0 = move-exception;
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3.<init>();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2.sZ = r3;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r2.sZ;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3 = 8;
        r2.yz(r3);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = r2.sZ;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.getMessage();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2.yD(r0);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        goto L_0x0087;
    L_0x013d:
        r0 = move-exception;
        r1 = r7.nt;
        r1 = r1.wy();
        if (r1 == 0) goto L_0x01ee;
    L_0x0146:
        throw r0;
    L_0x0147:
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r0 = r0.sZ;	 Catch:{ all -> 0x015b }
        r0 = r0.yw();	 Catch:{ all -> 0x015b }
        if (r0 != 0) goto L_0x0092;
    L_0x0151:
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r4 = com.loc.bq.vM();	 Catch:{ all -> 0x015b }
        r0.ta = r4;	 Catch:{ all -> 0x015b }
        goto L_0x0092;
    L_0x015b:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x015b }
        throw r0;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
    L_0x015e:
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r0 = r0.sQ;	 Catch:{ all -> 0x015b }
        r0 = r0.size();	 Catch:{ all -> 0x015b }
        if (r0 <= 0) goto L_0x009d;
    L_0x0168:
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r0 = r0.sQ;	 Catch:{ all -> 0x015b }
        r0 = r0.size();	 Catch:{ all -> 0x015b }
        r2 = r0;
        r0 = r1;
    L_0x0172:
        if (r2 <= 0) goto L_0x009e;
    L_0x0174:
        r4 = android.os.Message.obtain();	 Catch:{ all -> 0x015b }
        r0 = new android.os.Bundle;	 Catch:{ all -> 0x015b }
        r0.<init>();	 Catch:{ all -> 0x015b }
        r5 = "location";
        r6 = r7.nt;	 Catch:{ all -> 0x015b }
        r6 = r6.sZ;	 Catch:{ all -> 0x015b }
        r0.putParcelable(r5, r6);	 Catch:{ all -> 0x015b }
        r4.setData(r0);	 Catch:{ all -> 0x015b }
        r0 = 1;
        r4.what = r0;	 Catch:{ all -> 0x015b }
        r0 = r7.nt;	 Catch:{ all -> 0x015b }
        r0 = r0.sQ;	 Catch:{ all -> 0x015b }
        r5 = 0;
        r0 = r0.get(r5);	 Catch:{ all -> 0x015b }
        r0 = (android.os.Messenger) r0;	 Catch:{ all -> 0x015b }
        r0.send(r4);	 Catch:{ all -> 0x015b }
        r4 = r7.nt;	 Catch:{ all -> 0x015b }
        r4 = r4.sQ;	 Catch:{ all -> 0x015b }
        r5 = 0;
        r4.remove(r5);	 Catch:{ all -> 0x015b }
        r2 = r2 + -1;
        goto L_0x0172;
    L_0x01a6:
        if (r0 == 0) goto L_0x00af;
    L_0x01a8:
        r2 = "0";
        com.loc.f.kz(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = android.os.Message.obtain();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r3 = 100;
        r2.what = r3;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0.send(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        goto L_0x00af;
    L_0x01bb:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.sU;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0.iJ();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        goto L_0x00b5;
    L_0x01c6:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.sS;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        if (r0 != 0) goto L_0x00bb;
    L_0x01ce:
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = 1;
        r0.sS = r2;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r7.nt;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r0 = r0.sN;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        r2 = 4;
        r0.sendEmptyMessage(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        goto L_0x00bb;
    L_0x01de:
        r0 = r7.nt;	 Catch:{ all -> 0x0058 }
        r0 = r0.sX;	 Catch:{ all -> 0x0058 }
        r0.wait();	 Catch:{ all -> 0x0058 }
        goto L_0x0056;
    L_0x01e7:
        r0 = r7.nt;
        r0.wI();
        goto L_0x0014;
    L_0x01ee:
        r1 = r7.nt;
        r1.wI();
        goto L_0x0146;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aq.run():void");
    }
}
