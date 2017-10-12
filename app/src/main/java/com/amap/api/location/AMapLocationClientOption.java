package com.amap.api.location;

import com.loc.c;

public class AMapLocationClientOption implements Cloneable {
    private long a;
    private long b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private AMapLocationMode g;
    private boolean h;
    private boolean i;

    public enum AMapLocationMode {
        Battery_Saving,
        Device_Sensors,
        Hight_Accuracy
    }

    public AMapLocationClientOption() {
        this.a = 2000;
        this.b = (long) c.k;
        this.c = false;
        this.d = false;
        this.e = true;
        this.f = true;
        this.g = AMapLocationMode.Hight_Accuracy;
        this.h = false;
        this.i = false;
    }

    public boolean isMockEnable() {
        return this.d;
    }

    public void setMockEnable(boolean z) {
        this.d = z;
    }

    public long getInterval() {
        return this.a;
    }

    public boolean isOnceLocation() {
        return this.c;
    }

    public AMapLocationClientOption setOnceLocation(boolean z) {
        this.c = z;
        return this;
    }

    public boolean isNeedAddress() {
        return this.e;
    }

    public AMapLocationClientOption setNeedAddress(boolean z) {
        this.e = z;
        return this;
    }

    public boolean isWifiActiveScan() {
        return this.f;
    }

    public void setWifiActiveScan(boolean z) {
        this.f = z;
    }

    public AMapLocationMode getLocationMode() {
        return this.g;
    }

    public AMapLocationClientOption setLocationMode(AMapLocationMode aMapLocationMode) {
        this.g = aMapLocationMode;
        return this;
    }

    public boolean isKillProcess() {
        return this.h;
    }

    public boolean isGpsFirst() {
        return this.i;
    }

    public AMapLocationClientOption clone() {
        return new AMapLocationClientOption().a(this);
    }

    public long getHttpTimeOut() {
        return this.b;
    }

    private AMapLocationClientOption a(AMapLocationClientOption aMapLocationClientOption) {
        this.a = aMapLocationClientOption.a;
        this.c = aMapLocationClientOption.c;
        this.g = aMapLocationClientOption.g;
        this.d = aMapLocationClientOption.d;
        this.h = aMapLocationClientOption.h;
        this.i = aMapLocationClientOption.i;
        this.e = aMapLocationClientOption.e;
        this.f = aMapLocationClientOption.f;
        this.b = aMapLocationClientOption.b;
        return this;
    }
}
