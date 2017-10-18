package com.loc;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.DPoint;
import com.loc.a.b;

/* compiled from: GPSLocation */
public class e {
    Handler a;
    Context b;
    LocationManager c;
    AMapLocationClientOption d;
    long e;
    long f;
    LocationListener g;

    public e(Context context, b bVar) {
        this.e = 1000;
        this.f = br.b();
        this.g = new LocationListener() {
            final /* synthetic */ e a;

            {
                this.a = r1;
            }

            public void onLocationChanged(Location location) {
                Object obj = 1;
                if (location != null) {
                    try {
                        Object aMapLocation;
                        Bundle extras = location.getExtras();
                        if (extras == null || extras.getInt("satellites") <= 0) {
                            if (!this.a.d.isMockEnable()) {
                                return;
                            }
                        }
                        if (c.a(location.getLatitude(), location.getLongitude())) {
                            DPoint a = g.a(this.a.b, location.getLongitude(), location.getLatitude());
                            aMapLocation = new AMapLocation(location);
                            aMapLocation.setLocationType(1);
                            aMapLocation.setLatitude(a.getLatitude());
                            aMapLocation.setLongitude(a.getLongitude());
                        } else {
                            aMapLocation = new AMapLocation(location);
                            aMapLocation.setLocationType(1);
                        }
                        if (br.b() - this.a.f > this.a.e) {
                            obj = null;
                        }
                        if (obj == null) {
                            Message message = new Message();
                            message.obj = aMapLocation;
                            message.what = 2;
                            if (this.a.a != null) {
                                this.a.a.sendMessage(message);
                            }
                            this.a.f = br.b();
                        }
                    } catch (Throwable th) {
                        br.a(th);
                    }
                }
            }

            public void onProviderDisabled(String str) {
                try {
                    if ("gps".equals(str)) {
                        this.a.a.sendEmptyMessage(3);
                    }
                } catch (Throwable th) {
                    br.a(th);
                }
            }

            public void onProviderEnabled(String str) {
            }

            public void onStatusChanged(String str, int i, Bundle bundle) {
                if (i == 0 || i == 1) {
                    try {
                        this.a.a.sendEmptyMessage(3);
                    } catch (Throwable th) {
                        br.a(th);
                    }
                }
            }
        };
        this.b = context;
        this.a = bVar;
        this.c = (LocationManager) this.b.getSystemService("location");
    }

    public void a(AMapLocationClientOption aMapLocationClientOption) {
        this.d = aMapLocationClientOption;
        a(this.d.getInterval(), 0.0f);
    }

    public void a() {
        if (!(this.c == null || this.g == null)) {
            this.c.removeUpdates(this.g);
        }
    }

    void a(long j, float f) {
        try {
            Looper myLooper = Looper.myLooper();
            if (myLooper == null) {
                myLooper = this.b.getMainLooper();
            }
            this.e = j;
            this.c.requestLocationUpdates("gps", 1000, f, this.g, myLooper);
        } catch (Throwable e) {
            br.a(e);
            e.printStackTrace();
            Message obtain = Message.obtain();
            AMapLocation aMapLocation = new AMapLocation("");
            aMapLocation.setProvider("gps");
            aMapLocation.setErrorCode(12);
            aMapLocation.setLocationType(1);
            obtain.what = 2;
            obtain.obj = aMapLocation;
            if (this.a != null) {
                this.a.sendMessage(obtain);
            }
        } catch (Throwable e2) {
            br.a(e2);
        }
    }
}
