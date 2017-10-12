package com.loc;

import java.net.Proxy;

/* compiled from: DownloadManager */
public class an {
    private ao a;
    private aq b;

    /* compiled from: DownloadManager */
    public interface a {
        void a(Throwable th);

        void a(byte[] bArr, long j);

        void b();

        void c();
    }

    public an(aq aqVar) {
        this(aqVar, 0, -1);
    }

    public an(aq aqVar, long j, long j2) {
        Proxy proxy = null;
        this.b = aqVar;
        if (aqVar.c != null) {
            proxy = aqVar.c;
        }
        this.a = new ao(this.b.a, this.b.b, proxy);
        this.a.b(j2);
        this.a.a(j);
    }

    public void a(a aVar) {
        this.a.a(this.b.c(), this.b.a(), this.b.b(), aVar);
    }
}
