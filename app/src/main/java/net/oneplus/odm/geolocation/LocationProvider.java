package net.oneplus.odm.geolocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

import net.oneplus.odm.DeviceManagerSetting;

public class LocationProvider {
    private OPAMapLocationListener mAMapLocationListener;
    private Context mContext;
    private Handler mHandler;
    private AMapLocation mLocation;
    private AMapLocationClient mLocationClient;
    private LocationManager mLocationManager;
    private Handler mMessageHandler;
    private DeviceManagerSetting mSettings;
    private Runnable mStopLocationTask;

    class OPAMapLocationListener implements AMapLocationListener {
        OPAMapLocationListener() {
        }

        public void onLocationChanged(AMapLocation location) {
            if (location != null && location.getErrorCode() == 0) {
                LocationProvider.this.updateLocation(location);
            } else if (location != null) {
                Log.e("LocationProvider", "error, code: " + location.getErrorCode() + ", msg: " + location.getErrorInfo());
            }
        }
    }

    public LocationProvider(Context context) {
        this.mLocationClient = null;
        this.mLocation = null;
        this.mContext = null;
        this.mSettings = null;
        this.mAMapLocationListener = null;
        this.mStopLocationTask = new Runnable() {
            public void run() {
                LocationProvider.this.stopLocation();
                LocationProvider.this.sendMessageFromHandler(false);
            }
        };
        this.mSettings = DeviceManagerSetting.getInstance(context);
        this.mContext = context;
        this.mLocationManager = (LocationManager) this.mContext.getSystemService("location");
    }

    public void setLocationMessageHandler(Handler handler) {
        this.mMessageHandler = handler;
    }

    public void startLocation() {
        if (this.mContext != null) {
            try {
                Log.i("LocationProvider", "startLocation");
                if (this.mLocationManager == null) {
                    this.mLocationManager = (LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
                }
                this.mLocationClient = new AMapLocationClient(this.mContext);
                this.mAMapLocationListener = new OPAMapLocationListener();
                this.mLocationClient.setLocationListener(this.mAMapLocationListener);
                AMapLocationClientOption locationOption = new AMapLocationClientOption();
                locationOption.setNeedAddress(true);
                locationOption.setOnceLocation(true);
                locationOption.setWifiActiveScan(false);
                locationOption.setMockEnable(false);
                boolean contains = this.mLocationManager.isProviderEnabled("network") ? this.mLocationManager.getAllProviders().contains("network") : false;
                boolean contains2 = this.mLocationManager.isProviderEnabled("gps") ? this.mLocationManager.getAllProviders().contains("gps") : false;
                if (contains && contains2) {
                    Log.v("LocationProvider", "use Hight_Accuracy");
                    locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
                } else if (contains) {
                    Log.v("LocationProvider", "use Battery_Saving");
                    locationOption.setLocationMode(AMapLocationMode.Battery_Saving);
                } else {
                    Log.v("LocationProvider", "use Battery_Saving");
                    locationOption.setLocationMode(AMapLocationMode.Battery_Saving);
                }
                this.mLocationClient.setLocationOption(locationOption);
                this.mLocationClient.startLocation();
            } catch (NullPointerException nullPointerException) {
                Log.e("LocationProvider", nullPointerException.getMessage());
            } catch (RuntimeException runtimeException) {
                Log.e("LocationProvider", runtimeException.getMessage());
            }
            this.mHandler = new Handler();
            this.mHandler.postDelayed(this.mStopLocationTask, 20000);
        }
    }

    public void stopLocation() {
        Log.i("LocationProvider", "stopLocation");
        try {
            if (this.mLocationClient != null) {
                this.mLocationClient.unRegisterLocationListener(this.mAMapLocationListener);
                this.mLocationClient.stopLocation();
                this.mLocationClient.onDestroy();
            }
            if (this.mAMapLocationListener != null) {
                this.mAMapLocationListener = null;
            }
            if (this.mHandler != null) {
                this.mHandler.removeCallbacks(this.mStopLocationTask);
                this.mHandler = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLocation(AMapLocation location) {
        if (location != null) {
            this.mLocation = location;
        }
        sendMessageFromHandler(true);
    }

    private void sendMessageFromHandler(boolean isGetLocationSuccess) {
        if (this.mMessageHandler != null) {
            Message msg = new Message();
            if (isGetLocationSuccess) {
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", this.mLocation.getLatitude());
                bundle.putDouble("longitude", this.mLocation.getLongitude());
                bundle.putString("location_detail", this.mLocation.getLocationDetail());
                msg.setData(bundle);
                this.mMessageHandler.sendMessage(msg);
            } else {
                msg.what = 0;
                this.mMessageHandler.sendMessage(msg);
            }
        }
    }

    public Location getLastKnownLocation() {
        if (this.mLocationManager == null) {
            return null;
        }
        return this.mLocationManager.getLastKnownLocation();
    }
}
