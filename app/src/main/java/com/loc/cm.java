package com.loc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.text.SimpleDateFormat;

/* compiled from: Unknown */
final class cm implements LocationListener {
    private /* synthetic */ bw a;

    cm(bw bwVar) {
        this.a = bwVar;
    }

    private static boolean a(Location location) {
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
            if (location != null && a(location)) {
                if (location.getSpeed() > ((float) bw.e)) {
                    cu.a(bw.h);
                    cu.b(bw.h * 10);
                } else if (location.getSpeed() > ((float) bw.d)) {
                    cu.a(bw.g);
                    cu.b(bw.g * 10);
                } else {
                    cu.a(bw.f);
                    cu.b(bw.f * 10);
                }
                this.a.v.a();
                a(location);
                if (this.a.v.a() && a(location)) {
                    location.setTime(System.currentTimeMillis());
                    bw.a(this.a, location, 0, currentTimeMillis);
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
