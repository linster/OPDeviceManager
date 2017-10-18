package net.oneplus.odm.b;

import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.a;

class c implements a {
    final /* synthetic */ b J;

    c(b bVar) {
        this.J = bVar;
    }

    public void E(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            this.J.B(aMapLocation);
        } else if (aMapLocation != null) {
            Log.e("LocationProvider", "error, code: " + aMapLocation.getErrorCode() + ", msg: " + aMapLocation.hE());
        }
    }
}
