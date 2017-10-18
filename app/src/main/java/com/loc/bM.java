package com.loc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Message;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.g;

class bM implements LocationListener {
    final /* synthetic */ ao tK;

    bM(ao aoVar) {
        this.tK = aoVar;
    }

    public void onLocationChanged(Location location) {
        Object obj = 1;
        if (location != null) {
            try {
                Object aMapLocation;
                Bundle extras = location.getExtras();
                if (extras == null || extras.getInt("satellites") <= 0) {
                    if (!this.tK.np.ib()) {
                        return;
                    }
                }
                if (H.mR(location.getLatitude(), location.getLongitude())) {
                    g rv = aL.rv(this.tK.nn, location.getLongitude(), location.getLatitude());
                    aMapLocation = new AMapLocation(location);
                    aMapLocation.hA(1);
                    aMapLocation.setLatitude(rv.is());
                    aMapLocation.setLongitude(rv.ir());
                } else {
                    aMapLocation = new AMapLocation(location);
                    aMapLocation.hA(1);
                }
                if (bq.vM() - this.tK.nr > this.tK.nq) {
                    obj = null;
                }
                if (obj == null) {
                    Message message = new Message();
                    message.obj = aMapLocation;
                    message.what = 2;
                    if (this.tK.nm != null) {
                        this.tK.nm.sendMessage(message);
                    }
                    this.tK.nr = bq.vM();
                }
            } catch (Throwable th) {
                bq.vC(th);
            }
        }
    }

    public void onProviderDisabled(String str) {
        try {
            if ("gps".equals(str)) {
                this.tK.nm.sendEmptyMessage(3);
            }
        } catch (Throwable th) {
            bq.vC(th);
        }
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        if (i == 0 || i == 1) {
            try {
                this.tK.nm.sendEmptyMessage(3);
            } catch (Throwable th) {
                bq.vC(th);
            }
        }
    }
}
