package com.amap.api.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;

import com.loc.ac;
import com.loc.b;
import com.loc.c;
import com.loc.k;

public class APSService extends Service {
    Messenger a;
    APSServiceBase b;

    public IBinder onBind(Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("apiKey");
            if (!TextUtils.isEmpty(stringExtra)) {
                k.a(stringExtra);
            }
            this.a = new Messenger(this.b.getHandler());
            return this.a.getBinder();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void onCreate() {
        try {
            this.b = (APSServiceBase) ac.a(this, c.a("2.2.0"), "com.amap.api.location.APSServiceWrapper", b.class, new Class[]{Context.class}, new Object[]{this});
            this.b.onCreate();
            super.onCreate();
        } catch (Throwable th) {
            th.printStackTrace();
            this.b = new b(this);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            this.b.onStartCommand(intent, i, i2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return super.onStartCommand(intent, i, i2);
    }

    public void onDestroy() {
        try {
            this.b.onDestroy();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        super.onDestroy();
    }
}
