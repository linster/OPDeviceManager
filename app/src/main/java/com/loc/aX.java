package com.loc;

import android.text.TextUtils;
import java.net.Proxy;
import java.util.Map;

public abstract class aX {
    int a = 20000;
    int b = 20000;
    Proxy rk = null;

    public abstract Map mb();

    public abstract Map mc();

    public abstract String md();

    public byte[] mg() {
        return null;
    }

    String th() {
        byte[] mg = mg();
        if (mg == null || mg.length == 0) {
            return md();
        }
        Map mc = mc();
        if (mc == null) {
            return md();
        }
        String xr = bG.xr(mc);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(md()).append("?").append(xr);
        return stringBuffer.toString();
    }

    byte[] ti() {
        byte[] mg = mg();
        if (mg != null && mg.length != 0) {
            return mg;
        }
        String xr = bG.xr(mc());
        try {
            return TextUtils.isEmpty(xr) ? mg : xr.getBytes("UTF-8");
        } catch (Throwable e) {
            byte[] bytes = xr.getBytes();
            D.mF(e, "Request", "getConnectionDatas");
            return bytes;
        }
    }

    public final void tj(int i) {
        this.a = i;
    }

    public final void tk(int i) {
        this.b = i;
    }

    public final void tl(Proxy proxy) {
        this.rk = proxy;
    }
}
