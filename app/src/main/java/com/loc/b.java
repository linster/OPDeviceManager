package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import com.amap.api.location.APSServiceBase;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: APSServiceCore */
public class b implements APSServiceBase {
    Context a;
    boolean b;
    String c;
    b d;
    a e;
    boolean f;
    Vector<Messenger> g;
    volatile boolean h;
    boolean i;
    Object j;
    Looper k;
    AmapLoc l;
    long m;
    JSONObject n;
    AmapLoc o;
    ServerSocket p;
    boolean q;
    Socket r;
    boolean s;
    c t;
    private volatile boolean u;
    private boolean v;
    private int w;
    private boolean x;
    private boolean y;
    private as z;

    /* compiled from: APSServiceCore */
    class a extends Thread {
        final /* synthetic */ b a;

        a(b bVar) {
            this.a = bVar;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r7 = this;
            r1 = 0;
            r0 = r7.a;	 Catch:{ Throwable -> 0x0015, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r0.c();	 Catch:{ Throwable -> 0x0015, RemoteException -> 0x0034, InterruptedException -> 0x005b }
        L_0x0006:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.h;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r0 != 0) goto L_0x0043;
        L_0x000c:
            r0 = r7.a;
            r0 = r0.g();
            if (r0 == 0) goto L_0x01e7;
        L_0x0014:
            return;
        L_0x0015:
            r0 = move-exception;
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3.<init>();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2.l = r3;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r2.l;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3 = 9;
            r2.b(r3);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r2.l;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.getMessage();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2.b(r0);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            goto L_0x0006;
        L_0x0034:
            r0 = move-exception;
            r0 = r7.a;
            r0 = r0.g();
            if (r0 != 0) goto L_0x0014;
        L_0x003d:
            r0 = r7.a;
            r0.i();
            goto L_0x0014;
        L_0x0043:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.i;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r0 != 0) goto L_0x0071;
        L_0x0049:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r0.j;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            monitor-enter(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r7.a;	 Catch:{ all -> 0x0058 }
            r0 = r0.g();	 Catch:{ all -> 0x0058 }
            if (r0 != 0) goto L_0x01de;
        L_0x0056:
            monitor-exit(r2);	 Catch:{ all -> 0x0058 }
            goto L_0x0006;
        L_0x0058:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0058 }
            throw r0;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        L_0x005b:
            r0 = move-exception;
            r0 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x013d }
            r0.interrupt();	 Catch:{ all -> 0x013d }
            r0 = r7.a;
            r0 = r0.g();
            if (r0 != 0) goto L_0x0014;
        L_0x006b:
            r0 = r7.a;
            r0.i();
            goto L_0x0014;
        L_0x0071:
            r0 = r7.a;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r2 = r7.a;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r3 = r7.a;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r3 = r3.f;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r2 = r2.a(r3);	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r0.l = r2;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r0 = r7.a;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r0 = r0.z;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            if (r0 != 0) goto L_0x0106;
        L_0x0087:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3 = r0.j;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            monitor-enter(r3);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r0 = r0.l;	 Catch:{ all -> 0x015b }
            if (r0 != 0) goto L_0x0147;
        L_0x0092:
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r2 = 0;
            r0.i = r2;	 Catch:{ all -> 0x015b }
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r0 = r0.g;	 Catch:{ all -> 0x015b }
            if (r0 != 0) goto L_0x015e;
        L_0x009d:
            r0 = r1;
        L_0x009e:
            monitor-exit(r3);	 Catch:{ all -> 0x015b }
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2.a();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2.b();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = com.loc.bp.q();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r2 != 0) goto L_0x01a6;
        L_0x00af:
            r0 = com.loc.bp.a();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r0 != 0) goto L_0x01bb;
        L_0x00b5:
            r0 = com.loc.bp.d();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r0 != 0) goto L_0x01c6;
        L_0x00bb:
            r0 = com.loc.bp.f();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r0 == 0) goto L_0x0006;
        L_0x00c1:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.y;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r0 != 0) goto L_0x0006;
        L_0x00c9:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = 1;
            r0.y = r2;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.d;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = 5;
            r0.sendEmptyMessage(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            goto L_0x0006;
        L_0x00d9:
            r0 = move-exception;
            r1 = r7.a;	 Catch:{ all -> 0x013d }
            r2 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ all -> 0x013d }
            r2.<init>();	 Catch:{ all -> 0x013d }
            r1.l = r2;	 Catch:{ all -> 0x013d }
            r1 = r7.a;	 Catch:{ all -> 0x013d }
            r1 = r1.l;	 Catch:{ all -> 0x013d }
            r2 = 8;
            r1.b(r2);	 Catch:{ all -> 0x013d }
            r1 = r7.a;	 Catch:{ all -> 0x013d }
            r1 = r1.l;	 Catch:{ all -> 0x013d }
            r0 = r0.getMessage();	 Catch:{ all -> 0x013d }
            r1.b(r0);	 Catch:{ all -> 0x013d }
            r0 = r7.a;
            r0 = r0.g();
            if (r0 != 0) goto L_0x0014;
        L_0x00ff:
            r0 = r7.a;
            r0.i();
            goto L_0x0014;
        L_0x0106:
            r0 = r7.a;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r2 = r7.a;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r2 = r2.z;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r3 = r7.a;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r3 = r3.l;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r4 = 0;
            r4 = new java.lang.String[r4];	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r2 = r2.a(r3, r4);	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            r0.l = r2;	 Catch:{ Throwable -> 0x011d, RemoteException -> 0x0034, InterruptedException -> 0x005b }
            goto L_0x0087;
        L_0x011d:
            r0 = move-exception;
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3.<init>();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2.l = r3;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r2.l;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3 = 8;
            r2.b(r3);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = r2.l;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.getMessage();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2.b(r0);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            goto L_0x0087;
        L_0x013d:
            r0 = move-exception;
            r1 = r7.a;
            r1 = r1.g();
            if (r1 == 0) goto L_0x01ee;
        L_0x0146:
            throw r0;
        L_0x0147:
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r0 = r0.l;	 Catch:{ all -> 0x015b }
            r0 = r0.a();	 Catch:{ all -> 0x015b }
            if (r0 != 0) goto L_0x0092;
        L_0x0151:
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r4 = com.loc.br.b();	 Catch:{ all -> 0x015b }
            r0.m = r4;	 Catch:{ all -> 0x015b }
            goto L_0x0092;
        L_0x015b:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x015b }
            throw r0;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
        L_0x015e:
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r0 = r0.g;	 Catch:{ all -> 0x015b }
            r0 = r0.size();	 Catch:{ all -> 0x015b }
            if (r0 <= 0) goto L_0x009d;
        L_0x0168:
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r0 = r0.g;	 Catch:{ all -> 0x015b }
            r0 = r0.size();	 Catch:{ all -> 0x015b }
            r2 = r0;
            r0 = r1;
        L_0x0172:
            if (r2 <= 0) goto L_0x009e;
        L_0x0174:
            r4 = android.os.Message.obtain();	 Catch:{ all -> 0x015b }
            r0 = new android.os.Bundle;	 Catch:{ all -> 0x015b }
            r0.<init>();	 Catch:{ all -> 0x015b }
            r5 = "location";
            r6 = r7.a;	 Catch:{ all -> 0x015b }
            r6 = r6.l;	 Catch:{ all -> 0x015b }
            r0.putParcelable(r5, r6);	 Catch:{ all -> 0x015b }
            r4.setData(r0);	 Catch:{ all -> 0x015b }
            r0 = 1;
            r4.what = r0;	 Catch:{ all -> 0x015b }
            r0 = r7.a;	 Catch:{ all -> 0x015b }
            r0 = r0.g;	 Catch:{ all -> 0x015b }
            r5 = 0;
            r0 = r0.get(r5);	 Catch:{ all -> 0x015b }
            r0 = (android.os.Messenger) r0;	 Catch:{ all -> 0x015b }
            r0.send(r4);	 Catch:{ all -> 0x015b }
            r4 = r7.a;	 Catch:{ all -> 0x015b }
            r4 = r4.g;	 Catch:{ all -> 0x015b }
            r5 = 0;
            r4.remove(r5);	 Catch:{ all -> 0x015b }
            r2 = r2 + -1;
            goto L_0x0172;
        L_0x01a6:
            if (r0 == 0) goto L_0x00af;
        L_0x01a8:
            r2 = "0";
            com.loc.bp.a(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = android.os.Message.obtain();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r3 = 100;
            r2.what = r3;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0.send(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            goto L_0x00af;
        L_0x01bb:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.z;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0.a();	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            goto L_0x00b5;
        L_0x01c6:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.x;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            if (r0 != 0) goto L_0x00bb;
        L_0x01ce:
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = 1;
            r0.x = r2;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r7.a;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r0 = r0.d;	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            r2 = 4;
            r0.sendEmptyMessage(r2);	 Catch:{ RemoteException -> 0x0034, InterruptedException -> 0x005b, Throwable -> 0x00d9 }
            goto L_0x00bb;
        L_0x01de:
            r0 = r7.a;	 Catch:{ all -> 0x0058 }
            r0 = r0.j;	 Catch:{ all -> 0x0058 }
            r0.wait();	 Catch:{ all -> 0x0058 }
            goto L_0x0056;
        L_0x01e7:
            r0 = r7.a;
            r0.i();
            goto L_0x0014;
        L_0x01ee:
            r1 = r7.a;
            r1.i();
            goto L_0x0146;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.b.a.run():void");
        }
    }

    /* compiled from: APSServiceCore */
    class b extends Handler {
        b a;
        AmapLoc b;
        final /* synthetic */ b c;
        private boolean d;

        public b(b bVar, b bVar2) {
            this.c = bVar;
            this.a = null;
            this.b = null;
            this.d = true;
            this.a = bVar2;
        }

        public void handleMessage(Message message) {
            Object obj = 1;
            try {
                switch (message.what) {
                    case 1:
                        try {
                            synchronized (this.c.j) {
                                boolean z;
                                JSONObject jSONObject;
                                String str;
                                Bundle data = message.getData();
                                this.c.f = data.getBoolean("isfirst");
                                Messenger messenger = message.replyTo;
                                long b = br.b();
                                boolean z2 = data.getBoolean("isNeedAddress");
                                if (z2 != this.d) {
                                    this.a.m = 0;
                                    break;
                                }
                                this.d = z2;
                                if (this.c.l != null && this.c.l.a() == 0) {
                                    if (b - this.a.m < 1800) {
                                        obj = null;
                                    }
                                    if (obj == null) {
                                        Message obtain = Message.obtain();
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("location", this.c.l);
                                        obtain.setData(bundle);
                                        obtain.what = 1;
                                        messenger.send(obtain);
                                        z = data.getBoolean("wifiactivescan");
                                        this.c.b = data.getBoolean("isKillProcess");
                                        b = data.getLong("httptimeout");
                                        if (this.c.n != null) {
                                            jSONObject = this.c.n;
                                            str = "reversegeo";
                                            if (z2) {
                                                obj = "0";
                                            } else {
                                                obj = "1";
                                            }
                                            jSONObject.put(str, obj);
                                            jSONObject = this.c.n;
                                            str = "wifiactivescan";
                                            if (z) {
                                                obj = "0";
                                            } else {
                                                obj = "1";
                                            }
                                            jSONObject.put(str, obj);
                                            this.c.n.put("httptimeout", b);
                                        }
                                        this.a.a(this.c.n);
                                    }
                                }
                                if (!this.c.g.contains(messenger)) {
                                    this.c.g.add(messenger);
                                    break;
                                }
                                this.c.i = true;
                                this.a.j.notify();
                                z = data.getBoolean("wifiactivescan");
                                this.c.b = data.getBoolean("isKillProcess");
                                b = data.getLong("httptimeout");
                                if (this.c.n != null) {
                                    jSONObject = this.c.n;
                                    str = "reversegeo";
                                    if (z2) {
                                        obj = "1";
                                    } else {
                                        obj = "0";
                                    }
                                    jSONObject.put(str, obj);
                                    jSONObject = this.c.n;
                                    str = "wifiactivescan";
                                    if (z) {
                                        obj = "1";
                                    } else {
                                        obj = "0";
                                    }
                                    jSONObject.put(str, obj);
                                    this.c.n.put("httptimeout", b);
                                }
                                this.a.a(this.c.n);
                                break;
                            }
                            break;
                        } catch (RemoteException e) {
                            break;
                        } catch (Throwable th) {
                            break;
                        }
                    case 2:
                        this.c.d();
                        break;
                    case 3:
                        this.c.e();
                        break;
                    case 4:
                        synchronized (this.c.j) {
                            if (bp.d()) {
                                if (this.c.w < bp.e()) {
                                    this.c.w = this.c.w + 1;
                                    this.c.i = true;
                                    this.a.j.notify();
                                    this.c.d.sendEmptyMessageDelayed(4, 2000);
                                }
                                break;
                            }
                            break;
                        }
                        break;
                    case 5:
                        synchronized (this.c.j) {
                            if (bp.f()) {
                                if (bp.g() > 2) {
                                    this.c.i = true;
                                    if (bp.h()) {
                                        this.a.j.notify();
                                    } else if (!br.d(this.c.a)) {
                                        this.a.j.notify();
                                    }
                                    this.c.d.sendEmptyMessageDelayed(5, (long) (bp.g() * 1000));
                                }
                                break;
                            }
                            break;
                        }
                        break;
                    case 6:
                        synchronized (this.c.j) {
                            this.c.f();
                            break;
                        }
                        break;
                }
                super.handleMessage(message);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    /* compiled from: APSServiceCore */
    class c extends Thread {
        final /* synthetic */ b a;

        c(b bVar) {
            this.a = bVar;
        }

        public void run() {
            try {
                if (!this.a.u) {
                    this.a.h();
                }
                if (!this.a.q) {
                    this.a.q = true;
                    this.a.p = new ServerSocket(43689);
                }
                while (this.a.q) {
                    this.a.r = this.a.p.accept();
                    this.a.a(this.a.r);
                }
            } catch (Throwable th) {
            }
            super.run();
        }
    }

    public b(Context context) {
        this.b = false;
        this.u = false;
        this.v = false;
        this.c = null;
        this.d = new b(this, this);
        this.e = null;
        this.f = false;
        this.g = new Vector();
        this.w = 0;
        this.x = false;
        this.y = false;
        this.z = null;
        this.h = false;
        this.i = false;
        this.j = new Object();
        this.m = br.b();
        this.n = new JSONObject();
        this.p = null;
        this.q = false;
        this.r = null;
        this.s = false;
        this.a = context.getApplicationContext();
    }

    public Handler getHandler() {
        return this.d;
    }

    public void onCreate() {
        try {
            this.z = new as();
            this.c = this.a.getApplicationContext().getPackageName();
            this.h = true;
            this.e = new a(this);
            this.e.setName("serviceThread");
            this.e.start();
        } catch (Throwable th) {
        }
    }

    private boolean g() {
        boolean z;
        synchronized (this.j) {
            z = this.h;
        }
        return z;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 0;
    }

    public AmapLoc a(boolean z) throws Exception {
        return this.z.a(z);
    }

    public void a() {
        try {
            this.z.g();
        } catch (Throwable th) {
        }
    }

    public void b() {
        try {
            if (!this.v) {
                this.v = true;
                this.z.b(this.a);
            }
        } catch (Throwable th) {
        }
    }

    public void c() {
        try {
            if (!this.u) {
                h();
            }
        } catch (Throwable th) {
            this.u = false;
        }
    }

    private void h() {
        s sVar = null;
        Looper.prepare();
        this.k = Looper.myLooper();
        c.a(this.a);
        this.z.a(this.a);
        if (this.z != null) {
            this.z.a("api_serverSDK_130905##S128DF1572465B890OE3F7A13167KLEI##" + j.b(this.a) + "##" + j.e(this.a));
        }
        try {
            sVar = new com.loc.s.a("loc", "2.2.0", "AMAP_Location_SDK_Android 2.2.0").a(c.b()).a();
        } catch (i e) {
        }
        try {
            String a = l.a(this.a, sVar, null, true);
            try {
                this.n.put("key", j.e(this.a));
                this.n.put("X-INFO", a);
                this.n.put("User-Agent", "AMAP_Location_SDK_Android 2.2.0");
                this.n.put("netloc", "0");
                this.n.put("gpsstatus", "0");
                this.n.put("nbssid", "0");
                if (!this.n.has("reversegeo")) {
                    this.n.put("reversegeo", "0");
                }
                if (!this.n.has("wait1stwifi")) {
                    this.n.put("wait1stwifi", "0");
                }
                this.n.put("autoup", "0");
                this.n.put("upcolmobile", 1);
                this.n.put("enablegetreq", 1);
                this.n.put("wifiactivescan", 1);
            } catch (JSONException e2) {
            }
            if (this.z != null) {
                this.z.a(this.n);
            }
            this.u = true;
        } catch (Throwable th) {
        }
    }

    private void a(JSONObject jSONObject) {
        try {
            if (this.z != null) {
                this.z.a(jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    public synchronized void d() {
        try {
            if (!this.s) {
                this.t = new c(this);
                this.t.start();
                this.s = true;
            }
        } catch (Throwable th) {
        }
    }

    public synchronized void e() {
        try {
            if (this.p != null) {
                this.p.close();
            }
            if (this.r != null) {
                this.r.close();
            }
        } catch (IOException e) {
        }
        try {
            this.p = null;
            this.t = null;
            this.s = false;
            this.q = false;
        } catch (Throwable th) {
        }
    }

    private void a(Socket socket) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        PrintStream printStream;
        Throwable th;
        PrintStream printStream2;
        String str = null;
        if (socket != null) {
            int i = 30000;
            try {
                String str2 = "jsonp1";
                System.currentTimeMillis();
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    try {
                        int i2;
                        String str3;
                        String str4;
                        int i3;
                        long currentTimeMillis;
                        AmapLoc amapLoc;
                        PrintStream printStream3;
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (readLine.length() > 0) {
                                String[] split = readLine.split(" ");
                                if (split != null && split.length > 1) {
                                    split = split[1].split("\\?");
                                    if (split != null && split.length > 1) {
                                        String[] split2 = split[1].split("&");
                                        if (split2 != null && split2.length > 0) {
                                            for (String split3 : split2) {
                                                String[] split4 = split3.split("=");
                                                if (split4 != null && split4.length > 1) {
                                                    if ("to".equals(split4[0])) {
                                                        try {
                                                            i = Integer.parseInt(split4[1]);
                                                        } catch (Exception e) {
                                                            c.k = i2;
                                                        } catch (Throwable th2) {
                                                            th = th2;
                                                        }
                                                    }
                                                    if ("callback".equals(split4[0])) {
                                                        str2 = split4[1];
                                                    }
                                                    if ("_".equals(split4[0])) {
                                                        Long.parseLong(split4[1]);
                                                    }
                                                }
                                            }
                                            str4 = str2;
                                            i3 = i;
                                            str3 = str4;
                                            i2 = c.k;
                                            c.k = i3;
                                            currentTimeMillis = System.currentTimeMillis();
                                            if (this.o != null) {
                                                if ((currentTimeMillis - this.o.i() <= 5000 ? 1 : null) == null) {
                                                }
                                                if (str == null) {
                                                    if (this.o == null) {
                                                        amapLoc = this.o;
                                                        str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                                    } else {
                                                        str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                                    }
                                                    if (br.d(this.a)) {
                                                        str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                                    }
                                                }
                                                printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                                printStream3.println("HTTP/1.0 200 OK");
                                                printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                                printStream3.println();
                                                printStream3.println(str);
                                                printStream3.close();
                                                bufferedReader.close();
                                                socket.close();
                                            }
                                            if (!br.d(this.a)) {
                                                this.o = this.z.a(false);
                                                if (this.o.a() != 0) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':" + this.o.a() + ",'error':'" + this.o.c() + "'})";
                                                }
                                                c.k = i2;
                                            }
                                            if (str == null) {
                                                if (this.o == null) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                                } else {
                                                    amapLoc = this.o;
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                                }
                                                if (br.d(this.a)) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                                }
                                            }
                                            printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                            printStream3.println("HTTP/1.0 200 OK");
                                            printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                            printStream3.println();
                                            printStream3.println(str);
                                            printStream3.close();
                                            bufferedReader.close();
                                            socket.close();
                                        }
                                        str4 = str2;
                                        i3 = 30000;
                                        str3 = str4;
                                        i2 = c.k;
                                        c.k = i3;
                                        currentTimeMillis = System.currentTimeMillis();
                                        if (this.o != null) {
                                            if (currentTimeMillis - this.o.i() <= 5000) {
                                            }
                                            if ((currentTimeMillis - this.o.i() <= 5000 ? 1 : null) == null) {
                                            }
                                            if (str == null) {
                                                if (this.o == null) {
                                                    amapLoc = this.o;
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                                } else {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                                }
                                                if (br.d(this.a)) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                                }
                                            }
                                            printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                            printStream3.println("HTTP/1.0 200 OK");
                                            printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                            printStream3.println();
                                            printStream3.println(str);
                                            printStream3.close();
                                            bufferedReader.close();
                                            socket.close();
                                        }
                                        if (br.d(this.a)) {
                                            this.o = this.z.a(false);
                                            if (this.o.a() != 0) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':" + this.o.a() + ",'error':'" + this.o.c() + "'})";
                                            }
                                            c.k = i2;
                                        }
                                        if (str == null) {
                                            if (this.o == null) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                            } else {
                                                amapLoc = this.o;
                                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                            }
                                            if (br.d(this.a)) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                            }
                                        }
                                        printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                        printStream3.println("HTTP/1.0 200 OK");
                                        printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                        printStream3.println();
                                        printStream3.println(str);
                                        printStream3.close();
                                        bufferedReader.close();
                                        socket.close();
                                    }
                                    str4 = str2;
                                    i3 = 30000;
                                    str3 = str4;
                                    i2 = c.k;
                                    c.k = i3;
                                    currentTimeMillis = System.currentTimeMillis();
                                    if (this.o != null) {
                                        if (currentTimeMillis - this.o.i() <= 5000) {
                                        }
                                        if ((currentTimeMillis - this.o.i() <= 5000 ? 1 : null) == null) {
                                        }
                                        if (str == null) {
                                            if (this.o == null) {
                                                amapLoc = this.o;
                                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                            } else {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                            }
                                            if (br.d(this.a)) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                            }
                                        }
                                        printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                        printStream3.println("HTTP/1.0 200 OK");
                                        printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                        printStream3.println();
                                        printStream3.println(str);
                                        printStream3.close();
                                        bufferedReader.close();
                                        socket.close();
                                    }
                                    if (br.d(this.a)) {
                                        this.o = this.z.a(false);
                                        if (this.o.a() != 0) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':" + this.o.a() + ",'error':'" + this.o.c() + "'})";
                                        }
                                        c.k = i2;
                                    }
                                    if (str == null) {
                                        if (this.o == null) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                        } else {
                                            amapLoc = this.o;
                                            str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                        }
                                        if (br.d(this.a)) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                        }
                                    }
                                    printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                    printStream3.println("HTTP/1.0 200 OK");
                                    printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                    printStream3.println();
                                    printStream3.println(str);
                                    printStream3.close();
                                    bufferedReader.close();
                                    socket.close();
                                }
                                str4 = str2;
                                i3 = 30000;
                                str3 = str4;
                                i2 = c.k;
                                c.k = i3;
                                currentTimeMillis = System.currentTimeMillis();
                                if (this.o != null) {
                                    if (currentTimeMillis - this.o.i() <= 5000) {
                                    }
                                    if ((currentTimeMillis - this.o.i() <= 5000 ? 1 : null) == null) {
                                    }
                                    if (str == null) {
                                        if (this.o == null) {
                                            amapLoc = this.o;
                                            str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                        } else {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                        }
                                        if (br.d(this.a)) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                        }
                                    }
                                    printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                    printStream3.println("HTTP/1.0 200 OK");
                                    printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                    printStream3.println();
                                    printStream3.println(str);
                                    printStream3.close();
                                    bufferedReader.close();
                                    socket.close();
                                }
                                if (br.d(this.a)) {
                                    this.o = this.z.a(false);
                                    if (this.o.a() != 0) {
                                        str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':" + this.o.a() + ",'error':'" + this.o.c() + "'})";
                                    }
                                    c.k = i2;
                                }
                                if (str == null) {
                                    if (this.o == null) {
                                        str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                    } else {
                                        amapLoc = this.o;
                                        str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                    }
                                    if (br.d(this.a)) {
                                        str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                    }
                                }
                                printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                printStream3.println("HTTP/1.0 200 OK");
                                printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                printStream3.println();
                                printStream3.println(str);
                                printStream3.close();
                                bufferedReader.close();
                                socket.close();
                            }
                        }
                        str4 = str2;
                        i3 = 30000;
                        str3 = str4;
                        i2 = c.k;
                        c.k = i3;
                        currentTimeMillis = System.currentTimeMillis();
                        if (this.o != null) {
                            if (currentTimeMillis - this.o.i() <= 5000) {
                            }
                            if ((currentTimeMillis - this.o.i() <= 5000 ? 1 : null) == null) {
                            }
                            if (str == null) {
                                if (this.o == null) {
                                    amapLoc = this.o;
                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                } else {
                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                                }
                                if (br.d(this.a)) {
                                    str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                                }
                            }
                            printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                            printStream3.println("HTTP/1.0 200 OK");
                            printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                            printStream3.println();
                            printStream3.println(str);
                            printStream3.close();
                            bufferedReader.close();
                            socket.close();
                        }
                        if (br.d(this.a)) {
                            this.o = this.z.a(false);
                            if (this.o.a() != 0) {
                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':" + this.o.a() + ",'error':'" + this.o.c() + "'})";
                            }
                            c.k = i2;
                        }
                        if (str == null) {
                            if (this.o == null) {
                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':8,'error':'unknown error'})";
                            } else {
                                amapLoc = this.o;
                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':0,'error':'','location':{'y':" + amapLoc.g() + ",'precision':" + amapLoc.h() + ",'x':" + amapLoc.f() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                            }
                            if (br.d(this.a)) {
                                str = str3 + "&&" + str3 + "({'package':'" + this.c + "','error_code':36,'error':'app is background'})";
                            }
                        }
                        printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                        printStream3.println("HTTP/1.0 200 OK");
                        printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                        printStream3.println();
                        printStream3.println(str);
                        printStream3.close();
                        try {
                            bufferedReader.close();
                            socket.close();
                        } catch (Exception e2) {
                        }
                    } catch (Throwable th22) {
                        th = th22;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    Object obj = str;
                    printStream2 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                    printStream2.println("HTTP/1.0 200 OK");
                    printStream2.println("Content-Length:" + str.getBytes("UTF-8").length);
                    printStream2.println();
                    printStream2.println(str);
                    printStream2.close();
                    bufferedReader.close();
                    socket.close();
                    throw th;
                }
            } catch (Exception e3) {
                try {
                    bufferedReader.close();
                    socket.close();
                } catch (Exception e4) {
                }
            } catch (Exception e5) {
                try {
                    bufferedReader2.close();
                    socket.close();
                } catch (Exception e6) {
                }
            } catch (Exception e7) {
                try {
                    bufferedReader.close();
                    socket.close();
                } catch (Exception e8) {
                }
            } catch (Throwable th4) {
                th4.printStackTrace();
            }
        }
    }

    private void i() {
        e();
        this.u = false;
        this.v = false;
        this.x = false;
        this.y = false;
        this.w = 0;
        this.z.b();
        this.g.clear();
        if (this.b) {
            Process.killProcess(Process.myPid());
        }
        if (this.d != null) {
            this.d.removeCallbacksAndMessages(null);
        }
    }

    public void onDestroy() {
        try {
            synchronized (this.j) {
                this.h = false;
                this.j.notify();
            }
        } catch (Throwable th) {
        }
    }

    public void f() {
        try {
            if (this.z != null) {
                this.z.h();
            }
        } catch (Throwable th) {
        }
    }
}
