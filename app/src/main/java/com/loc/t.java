package com.loc;

import java.net.ServerSocket;

class t extends Thread {
    final /* synthetic */ br kZ;

    t(br brVar) {
        this.kZ = brVar;
    }

    public void run() {
        try {
            if (!this.kZ.sK) {
                this.kZ.wD();
            }
            if (!this.kZ.te) {
                this.kZ.te = true;
                this.kZ.td = new ServerSocket(43689);
            }
            while (this.kZ.te) {
                this.kZ.tf = this.kZ.td.accept();
                this.kZ.wH(this.kZ.tf);
            }
        } catch (Throwable th) {
        }
        super.run();
    }
}
