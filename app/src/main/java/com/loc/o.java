package com.loc;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;

public class o {
    private static o kR;

    public static o lR() {
        if (kR == null) {
            kR = new o();
        }
        return kR;
    }

    public HttpURLConnection lS(aX aXVar, boolean z) {
        i e;
        Proxy proxy = null;
        try {
            lV(aXVar);
            if (aXVar.rk != null) {
                proxy = aXVar.rk;
            }
            HttpURLConnection xo = (!z ? new bG(aXVar.a, aXVar.b, proxy, false) : new bG(aXVar.a, aXVar.b, proxy, true)).xo(aXVar.th(), aXVar.mb(), true);
            byte[] ti = aXVar.ti();
            if (ti != null && ti.length > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(xo.getOutputStream());
                dataOutputStream.write(ti);
                dataOutputStream.close();
            }
            xo.connect();
            return xo;
        } catch (i e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            e2 = new i("未知的错误");
        }
    }

    public byte[] lT(aX aXVar) {
        i e;
        try {
            ac lW = lW(aXVar, true);
            return lW == null ? null : lW.a;
        } catch (i e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = new i("未知的错误");
        }
    }

    public byte[] lU(aX aXVar) {
        i e;
        try {
            ac lW = lW(aXVar, false);
            return lW == null ? null : lW.a;
        } catch (i e2) {
            throw e2;
        } catch (Throwable th) {
            D.mF(th, "BaseNetManager", "makeSyncPostRequest");
            e2 = new i("未知的错误");
        }
    }

    protected void lV(aX aXVar) {
        if (aXVar == null) {
            throw new i("requeust is null");
        } else if (aXVar.md() == null || "".equals(aXVar.md())) {
            throw new i("request url is empty");
        }
    }

    protected ac lW(aX aXVar, boolean z) {
        i e;
        Proxy proxy = null;
        try {
            lV(aXVar);
            if (aXVar.rk != null) {
                proxy = aXVar.rk;
            }
            return new bG(aXVar.a, aXVar.b, proxy, z).xn(aXVar.th(), aXVar.mb(), aXVar.ti());
        } catch (i e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            e2 = new i("未知的错误");
        }
    }
}
