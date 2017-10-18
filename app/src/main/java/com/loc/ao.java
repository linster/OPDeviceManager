package com.loc;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.e;

public class ao {
    Handler nm;
    Context nn;
    LocationManager no;
    e np;
    long nq = 1000;
    long nr = bq.vM();
    LocationListener ns = new bM(this);

    public ao(Context context, d dVar) {
        this.nn = context;
        this.nm = dVar;
        this.no = (LocationManager) this.nn.getSystemService("location");
    }

    public void oQ(e eVar) {
        this.np = eVar;
        oS(this.np.id(), 0.0f);
    }

    public void oR() {
        if (!(this.no == null || this.ns == null)) {
            this.no.removeUpdates(this.ns);
        }
    }

    void oS(long j, float f) {
        try {
            Looper myLooper = Looper.myLooper();
            if (myLooper == null) {
                myLooper = this.nn.getMainLooper();
            }
            this.nq = j;
            this.no.requestLocationUpdates("gps", 1000, f, this.ns, myLooper);
        } catch (Throwable e) {
            bq.vC(e);
            e.printStackTrace();
            Message obtain = Message.obtain();
            AMapLocation aMapLocation = new AMapLocation("");
            aMapLocation.setProvider("gps");
            aMapLocation.hD(12);
            aMapLocation.hA(1);
            obtain.what = 2;
            obtain.obj = aMapLocation;
            if (this.nm != null) {
                this.nm.sendMessage(obtain);
            }
        } catch (Throwable e2) {
            bq.vC(e2);
        }
    }
}
