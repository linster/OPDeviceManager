package net.oneplus.odm.insight;

final class i implements Runnable {
    final /* synthetic */ MDMJobService ba;
    final /* synthetic */ boolean bb;

    i(MDMJobService mDMJobService, boolean z) {
        this.ba = mDMJobService;
        this.bb = z;
    }

    public void run() {
        new b(this.ba, this.bb).executeOnExecutor(MDMJobService.ac, new Void[0]);
    }
}
