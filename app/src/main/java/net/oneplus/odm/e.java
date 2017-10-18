package net.oneplus.odm;

import java.io.File;

class e implements c {
    private boolean bH = false;
    private boolean bI = false;
    String bJ = "/sys/fs/pstore/console-ramoops-0";
    final /* synthetic */ DeviceManagerReceiver bK;

    e(DeviceManagerReceiver deviceManagerReceiver) {
        this.bK = deviceManagerReceiver;
    }

    public File cn() {
        return new File(this.bJ);
    }

    public String co() {
        return "Kernel panic";
    }

    public String cp() {
        return "Watchdog bark";
    }

    public boolean cq() {
        return this.bH;
    }

    public boolean cr() {
        return this.bI;
    }

    public void cs() {
        this.bH = true;
    }

    public void ct() {
        this.bI = true;
    }
}
