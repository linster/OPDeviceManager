package com.amap.api.location;

import com.loc.H;

public class e implements Cloneable {
    private boolean iA = false;
    private boolean iB = false;
    private long it = 2000;
    private long iu = ((long) H.lM);
    private boolean iv = false;
    private boolean iw = false;
    private boolean ix = true;
    private boolean iy = true;
    private AMapLocationClientOption$AMapLocationMode iz = AMapLocationClientOption$AMapLocationMode.Hight_Accuracy;

    private e ip(e eVar) {
        this.it = eVar.it;
        this.iv = eVar.iv;
        this.iz = eVar.iz;
        this.iw = eVar.iw;
        this.iA = eVar.iA;
        this.iB = eVar.iB;
        this.ix = eVar.ix;
        this.iy = eVar.iy;
        this.iu = eVar.iu;
        return this;
    }

    public e clone() {
        return new e().ip(this);
    }

    public boolean ib() {
        return this.iw;
    }

    public void ic(boolean z) {
        this.iw = z;
    }

    public long id() {
        return this.it;
    }

    public boolean ie() {
        return this.iv;
    }

    public e if(boolean z) {
        this.iv = z;
        return this;
    }

    public boolean ig() {
        return this.ix;
    }

    public e ih(boolean z) {
        this.ix = z;
        return this;
    }

    public boolean ii() {
        return this.iy;
    }

    public void ij(boolean z) {
        this.iy = z;
    }

    public AMapLocationClientOption$AMapLocationMode ik() {
        return this.iz;
    }

    public e il(AMapLocationClientOption$AMapLocationMode aMapLocationClientOption$AMapLocationMode) {
        this.iz = aMapLocationClientOption$AMapLocationMode;
        return this;
    }

    public boolean im() {
        return this.iA;
    }

    public boolean in() {
        return this.iB;
    }

    public long io() {
        return this.iu;
    }
}
