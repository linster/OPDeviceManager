package net.oneplus.odm.b;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption$AMapLocationMode;
import com.amap.api.location.e;

public class b {
    private c A = null;
    private Context B = null;
    private Handler C;
    private AMapLocation D = null;
    private com.amap.api.location.b E = null;
    private LocationManager F;
    private Handler G;
    private net.oneplus.odm.b H = null;
    private Runnable I = new d(this);

    public b(Context context) {
        this.H = net.oneplus.odm.b.bT(context);
        this.B = context;
        this.F = (LocationManager) this.B.getSystemService("location");
    }

    private void A(boolean z) {
        if (this.G != null) {
            Message message = new Message();
            if (z) {
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", this.D.getLatitude());
                bundle.putDouble("longitude", this.D.getLongitude());
                bundle.putString("location_detail", this.D.hB());
                message.setData(bundle);
                this.G.sendMessage(message);
            } else {
                message.what = 0;
                this.G.sendMessage(message);
            }
        }
    }

    private void B(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            this.D = aMapLocation;
        }
        A(true);
    }

    public void x() {
        if (this.B != null) {
            try {
                Log.i("LocationProvider", "startLocation");
                if (this.F == null) {
                    this.F = (LocationManager) this.B.getSystemService("location");
                }
                this.E = new com.amap.api.location.b(this.B);
                this.A = new c(this);
                this.E.hS(this.A);
                e eVar = new e();
                eVar.ih(true);
                eVar.if(true);
                eVar.ij(false);
                eVar.ic(false);
                boolean contains = this.F.isProviderEnabled("network") ? this.F.getAllProviders().contains("network") : false;
                boolean contains2 = this.F.isProviderEnabled("gps") ? this.F.getAllProviders().contains("gps") : false;
                if (contains && contains2) {
                    Log.v("LocationProvider", "use Hight_Accuracy");
                    eVar.il(AMapLocationClientOption$AMapLocationMode.Hight_Accuracy);
                } else if (contains) {
                    Log.v("LocationProvider", "use Battery_Saving");
                    eVar.il(AMapLocationClientOption$AMapLocationMode.Battery_Saving);
                } else {
                    Log.v("LocationProvider", "use Battery_Saving");
                    eVar.il(AMapLocationClientOption$AMapLocationMode.Battery_Saving);
                }
                this.E.hR(eVar);
                this.E.hT();
            } catch (NullPointerException e) {
                Log.e("LocationProvider", e.getMessage());
            } catch (RuntimeException e2) {
                Log.e("LocationProvider", e2.getMessage());
            }
            this.C = new Handler();
            this.C.postDelayed(this.I, 20000);
        }
    }

    public void y() {
        Log.i("LocationProvider", "stopLocation");
        try {
            if (this.E != null) {
                this.E.ia(this.A);
                this.E.hU();
                this.E.onDestroy();
            }
            if (this.A != null) {
                this.A = null;
            }
            if (this.C != null) {
                this.C.removeCallbacks(this.I);
                this.C = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void z(Handler handler) {
        this.G = handler;
    }
}
