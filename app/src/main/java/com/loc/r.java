package com.loc;

import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;

public class r {
    private AmapLoc kV = null;
    private String kW = null;
    final /* synthetic */ aT kX;

    protected r(aT aTVar) {
        this.kX = aTVar;
    }

    public AmapLoc mh() {
        return this.kV;
    }

    public void mi(AmapLoc amapLoc) {
        this.kV = amapLoc;
    }

    public String mj() {
        return this.kW;
    }

    public void mk(String str) {
        if (TextUtils.isEmpty(str)) {
            this.kW = null;
        } else {
            this.kW = str.replace("##", "#");
        }
    }
}
