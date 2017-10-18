package com.amap.api.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;
import com.loc.H;
import com.loc.au;
import com.loc.bB;
import com.loc.br;

public class APSService extends Service {
    Messenger iG;
    f iH;

    public IBinder onBind(Intent intent) {
        try {
            Object stringExtra = intent.getStringExtra("apiKey");
            if (!TextUtils.isEmpty(stringExtra)) {
                au.pb(stringExtra);
            }
            this.iG = new Messenger(this.iH.iq());
            return this.iG.getBinder();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void onCreate() {
        try {
            this.iH = (f) bB.xi(this, H.mY("2.2.0"), "com.amap.api.location.APSServiceWrapper", br.class, new Class[]{Context.class}, new Object[]{this});
            this.iH.onCreate();
            super.onCreate();
        } catch (Throwable th) {
            th.printStackTrace();
            this.iH = new br(this);
        }
    }

    public void onDestroy() {
        try {
            this.iH.onDestroy();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            this.iH.onStartCommand(intent, i, i2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return super.onStartCommand(intent, i, i2);
    }
}
