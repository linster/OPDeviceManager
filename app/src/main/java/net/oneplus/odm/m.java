package net.oneplus.odm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import net.oneplus.odm.insight.c;
import org.json.JSONException;
import org.json.JSONObject;

class m extends Handler {
    JSONObject cp = new JSONObject();
    final /* synthetic */ DeviceManagerService cq;

    m(DeviceManagerService deviceManagerService, Looper looper) {
        this.cq = deviceManagerService;
        super(looper);
    }

    public void handleMessage(Message message) {
        this.cp = this.cq.cx();
        switch (message.what) {
            case 1:
                message.getData();
                this.cq.cc.bZ("grab_location_timestamp", System.currentTimeMillis());
                break;
        }
        if (this.cq.cc.bV("send_deviceinfo_immediately", false)) {
            this.cq.cc.bY("send_deviceinfo_immediately", false);
            try {
                this.cp.put("fl", "true");
            } catch (JSONException e) {
                Log.e(DeviceManagerService.bT, e.getMessage());
            }
            c cVar = new c(this.cq.getApplicationContext());
            this.cp = this.cq.cv(this.cp);
            cVar.be(this.cp);
        } else {
            this.cp = this.cq.cv(this.cp);
            this.cq.bX.F(this.cp.toString());
        }
        this.cq.cf.y();
        super.handleMessage(message);
    }
}
