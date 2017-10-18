package net.oneplus.odm;

final class r implements Runnable {
    final /* synthetic */ DeviceManagerReceiver cF;

    r(DeviceManagerReceiver deviceManagerReceiver) {
        this.cF = deviceManagerReceiver;
    }

    public void run() {
        this.cF.ci();
    }
}
