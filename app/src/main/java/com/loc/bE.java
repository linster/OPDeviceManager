package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;

class bE extends BroadcastReceiver {
    final /* synthetic */ e ts;

    private bE(e eVar) {
        this.ts = eVar;
    }

    public void onReceive(Context context, Intent intent) {
        Collection collection = null;
        if (context != null && intent != null) {
            try {
                String action = intent.getAction();
                if (!TextUtils.isEmpty(action)) {
                    aQ jP = this.ts.ja;
                    if (!action.equals("android.net.wifi.SCAN_RESULTS")) {
                        if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            if (this.ts.ja != null) {
                                int i = 4;
                                try {
                                    i = jP.rO();
                                } catch (Exception e) {
                                }
                                if (this.ts.jd == null) {
                                    this.ts.jd = new ArrayList();
                                }
                                switch (i) {
                                    case 0:
                                        this.ts.je();
                                        break;
                                    case 1:
                                        this.ts.je();
                                        break;
                                    case 2:
                                    case 3:
                                        break;
                                    case 4:
                                        this.ts.je();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else if (action.equals("android.intent.action.SCREEN_ON")) {
                            this.ts.jY = true;
                        } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                            this.ts.jY = false;
                            if (this.ts.jx != null) {
                                this.ts.jl();
                            }
                        } else if (!action.equals("android.intent.action.AIRPLANE_MODE")) {
                            if (action.equals("android.location.GPS_FIX_CHANGE")) {
                                this.ts.jX = true;
                            } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE") && this.ts.jv()) {
                                this.ts.iK(true, 2);
                            }
                        }
                    } else if (jP != null) {
                        try {
                            collection = jP.rM();
                        } catch (Exception e2) {
                        }
                        if (collection != null) {
                            synchronized (this.ts.jU) {
                                this.ts.jd.clear();
                                this.ts.jd.addAll(collection);
                            }
                        }
                        this.ts.jp = bq.vM();
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
