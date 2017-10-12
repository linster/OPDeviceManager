package com.amap.api.location;

import android.content.Intent;
import android.os.Handler;

public interface APSServiceBase {
    Handler getHandler();

    void onCreate();

    void onDestroy();

    int onStartCommand(Intent intent, int i, int i2);
}
