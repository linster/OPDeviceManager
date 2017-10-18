package net.oneplus.odm.insight;

final class k implements Runnable {
    final /* synthetic */ e be;

    k(e eVar) {
        this.be = eVar;
    }

    public void run() {
        this.be.aE.schedule(this.be.aF.setPeriodic(7200000).build());
    }
}
