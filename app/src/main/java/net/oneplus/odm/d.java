package net.oneplus.odm;

import java.io.File;

class d implements c {
    private boolean bD = false;
    private boolean bE = false;
    String bF = "/proc/last_kmsg";
    final /* synthetic */ DeviceManagerReceiver bG;

    d(DeviceManagerReceiver deviceManagerReceiver) {
        this.bG = deviceManagerReceiver;
    }

    public File cn() {
        return new File(this.bF);
    }

    public String co() {
        return "Kernel panic";
    }

    public String cp() {
        return "Watchdog bark";
    }

    public boolean cq() {
        return this.bD;
    }

    public boolean cr() {
        return this.bE;
    }

    public void cs() {
        this.bD = true;
    }

    public void ct() {
        this.bE = true;
    }
}
