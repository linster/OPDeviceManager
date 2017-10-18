package net.oneplus.odm;

import java.io.File;

class g implements c {
    private boolean bP = false;
    private boolean bQ = false;
    String bR = "/sys/fs/pstore/console-ramoops-0";
    final /* synthetic */ DeviceManagerReceiver bS;

    g(DeviceManagerReceiver deviceManagerReceiver) {
        this.bS = deviceManagerReceiver;
    }

    public File cn() {
        return new File(this.bR);
    }

    public String co() {
        return "Kernel panic";
    }

    public String cp() {
        return "Watchdog bark";
    }

    public boolean cq() {
        return this.bP;
    }

    public boolean cr() {
        return this.bQ;
    }

    public void cs() {
        this.bP = true;
    }

    public void ct() {
        this.bQ = true;
    }
}
