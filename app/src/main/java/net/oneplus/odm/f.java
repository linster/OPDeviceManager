package net.oneplus.odm;

import java.io.File;

class f implements c {
    private boolean bL = false;
    private boolean bM = false;
    String bN = "/sys/fs/pstore/console-ramoops";
    final /* synthetic */ DeviceManagerReceiver bO;

    f(DeviceManagerReceiver deviceManagerReceiver) {
        this.bO = deviceManagerReceiver;
    }

    public File cn() {
        return new File(this.bN);
    }

    public String co() {
        return "Kernel panic";
    }

    public String cp() {
        return "Watchdog bark";
    }

    public boolean cq() {
        return this.bL;
    }

    public boolean cr() {
        return this.bM;
    }

    public void cs() {
        this.bL = true;
    }

    public void ct() {
        this.bM = true;
    }
}
