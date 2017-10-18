package com.loc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.text.SimpleDateFormat;

/* compiled from: Unknown */
final class s implements LocationListener {
    private /* synthetic */ aF kY;

    s(aF aFVar) {
        this.kY = aFVar;
    }

    private static boolean ml(Location location) {
        return location != null && "gps".equalsIgnoreCase(location.getProvider()) && location.getLatitude() > -90.0d && location.getLatitude() < 90.0d && location.getLongitude() > -180.0d && location.getLongitude() < 180.0d;
    }

    public final void onLocationChanged(Location location) {
        Object obj = null;
        try {
            long time = location.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.format(Long.valueOf(time));
            simpleDateFormat.format(Long.valueOf(currentTimeMillis));
            if (time > 0) {
                obj = 1;
            }
            if (obj != null) {
                currentTimeMillis = time;
            }
            if (location != null && ml(location)) {
                if (location.getSpeed() > ((float) aF.e)) {
                    Z.nQ(aF.oI);
                    Z.nR(aF.oI * 10);
                } else if (location.getSpeed() > ((float) aF.d)) {
                    Z.nQ(aF.oH);
                    Z.nR(aF.oH * 10);
                } else {
                    Z.nQ(aF.oG);
                    Z.nR(aF.oG * 10);
                }
                this.kY.oW.sn();
                ml(location);
                if (this.kY.oW.sn() && ml(location)) {
                    location.setTime(System.currentTimeMillis());
                    aF.qK(this.kY, location, 0, currentTimeMillis);
                }
            }
        } catch (Exception e) {
        }
    }

    public final void onProviderDisabled(String str) {
    }

    public final void onProviderEnabled(String str) {
    }

    public final void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
