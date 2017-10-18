package net.oneplus.odm.insight;

final class j implements Runnable {
    final /* synthetic */ MDMJobService bc;
    final /* synthetic */ boolean bd;

    j(MDMJobService mDMJobService, boolean z) {
        this.bc = mDMJobService;
        this.bd = z;
    }

    public void run() {
        new a(this.bc, this.bd).executeOnExecutor(MDMJobService.ac, new Void[0]);
    }
}
