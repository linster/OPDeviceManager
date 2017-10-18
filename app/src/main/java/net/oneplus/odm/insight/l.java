package net.oneplus.odm.insight;

import android.content.ContentValues;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import net.oneplus.odm.insight.tracker.OSTracker;

final class l extends Handler {
    final /* synthetic */ LogParserJobService bf;

    l(LogParserJobService logParserJobService, Looper looper) {
        this.bf = logParserJobService;
        super(looper);
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                try {
                    this.bf.aP.insert(LogParserJobService.aN, (ContentValues) message.obj);
                    return;
                } catch (IllegalArgumentException e) {
                    Log.e("LogParserJobService", e.getMessage());
                    return;
                }
            case 2:
                Log.v("LogParserJobService", "Finish service");
                this.bf.jobFinished(this.bf.aU, false);
                return;
            case 3:
                new OSTracker(this.bf.getApplicationContext()).onEvent(message.getData().getString("tag"), null);
                return;
            default:
                return;
        }
    }
}
