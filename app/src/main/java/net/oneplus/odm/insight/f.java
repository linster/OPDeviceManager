package net.oneplus.odm.insight;

import android.util.Log;
import java.io.File;

class f implements Runnable {
    final /* synthetic */ LogParserJobService aW;

    private f(LogParserJobService logParserJobService) {
        this.aW = logParserJobService;
    }

    public void run() {
        this.aW.bv();
        File file = new File(this.aW.aT);
        if (file.exists()) {
            Log.v("LogParserJobService", "mdmLogRebootFile exists");
            new Thread(new h(this.aW, file)).start();
            LogParserJobService.aO = LogParserJobService.aO + 1;
        }
        this.aW.bx();
        file = new File(this.aW.aS);
        if (file.exists()) {
            Log.v("LogParserJobService", "mdmLogFile exists");
            new Thread(new h(this.aW, file)).start();
            LogParserJobService.aO = LogParserJobService.aO + 1;
        }
        this.aW.bw();
        file = new File(this.aW.aV);
        if (file.exists()) {
            Log.v("LogParserJobService", "Parse watchdog log");
            new Thread(new g(this.aW, file)).start();
            LogParserJobService.aO = LogParserJobService.aO + 1;
        }
        if (LogParserJobService.aO == 0) {
            Log.v("LogParserJobService", "no task, close service");
            this.aW.aQ.sendMessage(this.aW.aQ.obtainMessage(2));
        }
    }
}
