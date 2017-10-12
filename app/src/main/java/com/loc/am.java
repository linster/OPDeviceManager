package com.loc;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;

/* compiled from: BaseNetManager */
public class am {
    private static am a;

    public static am a() {
        if (a == null) {
            a = new am();
        }
        return a;
    }

    public HttpURLConnection a(aq aqVar, boolean z) throws i {
        i e;
        Proxy proxy = null;
        try {
            ao aoVar;
            c(aqVar);
            if (aqVar.c != null) {
                proxy = aqVar.c;
            }
            if (z) {
                aoVar = new ao(aqVar.a, aqVar.b, proxy, true);
            } else {
                aoVar = new ao(aqVar.a, aqVar.b, proxy, false);
            }
            HttpURLConnection a = aoVar.a(aqVar.d(), aqVar.a(), true);
            byte[] e2 = aqVar.e();
            if (e2 != null && e2.length > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(a.getOutputStream());
                dataOutputStream.write(e2);
                dataOutputStream.close();
            }
            a.connect();
            return a;
        } catch (i e3) {
            throw e3;
        } catch (Throwable th) {
            th.printStackTrace();
            e3 = new i("\u672a\u77e5\u7684\u9519\u8bef");
        }
    }

    public byte[] a(aq aqVar) throws i {
        i e;
        try {
            ar b = b(aqVar, true);
            if (b == null) {
                return null;
            }
            return b.a;
        } catch (i e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = new i("\u672a\u77e5\u7684\u9519\u8bef");
        }
    }

    public byte[] b(aq aqVar) throws i {
        i e;
        try {
            ar b = b(aqVar, false);
            if (b == null) {
                return null;
            }
            return b.a;
        } catch (i e2) {
            throw e2;
        } catch (Throwable th) {
            v.a(th, "BaseNetManager", "makeSyncPostRequest");
            e2 = new i("\u672a\u77e5\u7684\u9519\u8bef");
        }
    }

    protected void c(aq aqVar) throws i {
        if (aqVar == null) {
            throw new i("requeust is null");
        } else if (aqVar.c() == null || "".equals(aqVar.c())) {
            throw new i("request url is empty");
        }
    }

    protected ar b(aq aqVar, boolean z) throws i {
        i e;
        Proxy proxy = null;
        try {
            c(aqVar);
            if (aqVar.c != null) {
                proxy = aqVar.c;
            }
            return new ao(aqVar.a, aqVar.b, proxy, z).a(aqVar.d(), aqVar.a(), aqVar.e());
        } catch (i e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            e2 = new i("\u672a\u77e5\u7684\u9519\u8bef");
        }
    }
}
