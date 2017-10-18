package com.loc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.amap.api.fence.Fence;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.APSService;
import com.amap.api.location.LocationManagerBase;
import com.autonavi.aps.amapapi.model.AmapLoc;
import com.squareup.okhttp.internal.http.StatusLine;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: AMapLocationManager */
public class a implements LocationManagerBase {
    AMapLocationClientOption a;
    b b;
    e c;
    ArrayList<AMapLocationListener> d;
    d e;
    boolean f;
    f g;
    Messenger h;
    Messenger i;
    a j;
    int k;
    long l;
    long m;
    int n;
    boolean o;
    private final int p;
    private final int q;
    private Context r;
    private boolean s;
    private boolean t;
    private long u;
    private boolean v;
    private boolean w;
    private ServiceConnection x;

    /* compiled from: AMapLocationManager */
    /* renamed from: com.loc.a.4 */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = null;

        static {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.loc.a.4.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:113)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:263)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:367)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:357)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:103)
	... 6 more
*/
            /*
            r0 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.values();
            r0 = r0.length;
            r0 = new int[r0];
            a = r0;
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r1 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Battery_Saving;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r1 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Device_Sensors;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x0025 }
            r1 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;	 Catch:{ NoSuchFieldError -> 0x0025 }
            r2 = 3;	 Catch:{ NoSuchFieldError -> 0x0025 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0025 }
        L_0x0025:
            r0 = move-exception;
            goto L_0x0024;
        L_0x0027:
            r0 = move-exception;
            goto L_0x001b;
        L_0x0029:
            r0 = move-exception;
            goto L_0x0012;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.a.4.<clinit>():void");
        }
    }

    /* compiled from: AMapLocationManager */
    class a extends Thread {
        boolean a;
        final /* synthetic */ a b;

        public a(a aVar, String str) {
            this.b = aVar;
            super(str);
            this.a = false;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r10 = this;
            r8 = 0;
            r0 = 1;
            r6 = 0;
            r10.a = r0;
        L_0x0006:
            r0 = r10.a;	 Catch:{ Throwable -> 0x00be }
            if (r0 != 0) goto L_0x0010;
        L_0x000a:
            r0 = r10.b;
            r0.s = r6;
            return;
        L_0x0010:
            r0 = java.lang.Thread.interrupted();	 Catch:{ Throwable -> 0x00be }
            if (r0 != 0) goto L_0x000a;
        L_0x0016:
            r0 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;	 Catch:{ Throwable -> 0x00be }
            r1 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r1 = r1.a;	 Catch:{ Throwable -> 0x00be }
            r1 = r1.getLocationMode();	 Catch:{ Throwable -> 0x00be }
            r0 = r0.equals(r1);	 Catch:{ Throwable -> 0x00be }
            if (r0 != 0) goto L_0x00c7;
        L_0x0026:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.a;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.isGpsFirst();	 Catch:{ Throwable -> 0x00be }
            if (r0 != 0) goto L_0x00ed;
        L_0x0030:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.h;	 Catch:{ Throwable -> 0x00be }
            if (r0 == 0) goto L_0x0118;
        L_0x0036:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r2 = 0;
            r0.m = r2;	 Catch:{ Throwable -> 0x00be }
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r1 = 1;
            r0.s = r1;	 Catch:{ Throwable -> 0x00be }
            r0 = android.os.Message.obtain();	 Catch:{ Throwable -> 0x00be }
            r1 = 1;
            r0.what = r1;	 Catch:{ Throwable -> 0x00be }
            r1 = new android.os.Bundle;	 Catch:{ Throwable -> 0x00be }
            r1.<init>();	 Catch:{ Throwable -> 0x00be }
            r2 = "isfirst";
            r3 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.v;	 Catch:{ Throwable -> 0x00be }
            r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
            r2 = "wifiactivescan";
            r3 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.a;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.isWifiActiveScan();	 Catch:{ Throwable -> 0x00be }
            r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
            r2 = "isNeedAddress";
            r3 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.a;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.isNeedAddress();	 Catch:{ Throwable -> 0x00be }
            r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
            r2 = "isKillProcess";
            r3 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.a;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.isKillProcess();	 Catch:{ Throwable -> 0x00be }
            r1.putBoolean(r2, r3);	 Catch:{ Throwable -> 0x00be }
            r2 = "httptimeout";
            r3 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r3 = r3.a;	 Catch:{ Throwable -> 0x00be }
            r4 = r3.getHttpTimeOut();	 Catch:{ Throwable -> 0x00be }
            r1.putLong(r2, r4);	 Catch:{ Throwable -> 0x00be }
            r0.setData(r1);	 Catch:{ Throwable -> 0x00be }
            r1 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r1 = r1.i;	 Catch:{ Throwable -> 0x00be }
            r0.replyTo = r1;	 Catch:{ Throwable -> 0x00be }
            r1 = r10.b;	 Catch:{ Throwable -> 0x016f }
            r1 = r1.h;	 Catch:{ Throwable -> 0x016f }
            if (r1 != 0) goto L_0x0166;
        L_0x00a1:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r1 = 0;
            r0.v = r1;	 Catch:{ Throwable -> 0x00be }
            r0 = r10.b;	 Catch:{ InterruptedException -> 0x00b4 }
            r0 = r0.a;	 Catch:{ InterruptedException -> 0x00b4 }
            r0 = r0.getInterval();	 Catch:{ InterruptedException -> 0x00b4 }
            java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x00b4 }
            goto L_0x0006;
        L_0x00b4:
            r0 = move-exception;
            r0 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00be }
            r0.interrupt();	 Catch:{ Throwable -> 0x00be }
            goto L_0x0006;
        L_0x00be:
            r0 = move-exception;
            com.loc.br.a(r0);
            r0.printStackTrace();
            goto L_0x000a;
        L_0x00c7:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.d();	 Catch:{ Throwable -> 0x00be }
            if (r0 != 0) goto L_0x0026;
        L_0x00cf:
            r0 = r10.b;	 Catch:{ InterruptedException -> 0x00e0 }
            r0 = r0.a;	 Catch:{ InterruptedException -> 0x00e0 }
            r0 = r0.isOnceLocation();	 Catch:{ InterruptedException -> 0x00e0 }
            if (r0 != 0) goto L_0x0107;
        L_0x00d9:
            r0 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
            java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x00e0 }
            goto L_0x0006;
        L_0x00e0:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00be }
            r1.interrupt();	 Catch:{ Throwable -> 0x00be }
            com.loc.br.a(r0);	 Catch:{ Throwable -> 0x00be }
            goto L_0x0006;
        L_0x00ed:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.a;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.isOnceLocation();	 Catch:{ Throwable -> 0x00be }
            if (r0 == 0) goto L_0x0030;
        L_0x00f7:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.e();	 Catch:{ Throwable -> 0x00be }
            if (r0 != 0) goto L_0x0030;
        L_0x00ff:
            r0 = r10.b;	 Catch:{ Throwable -> 0x00be }
            r0 = r0.o;	 Catch:{ Throwable -> 0x00be }
            if (r0 == 0) goto L_0x00cf;
        L_0x0105:
            goto L_0x0030;
        L_0x0107:
            r0 = r10.b;	 Catch:{ InterruptedException -> 0x00e0 }
            r0 = r0.m;	 Catch:{ InterruptedException -> 0x00e0 }
            r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
            if (r0 != 0) goto L_0x00d9;
        L_0x010f:
            r0 = r10.b;	 Catch:{ InterruptedException -> 0x00e0 }
            r2 = com.loc.br.b();	 Catch:{ InterruptedException -> 0x00e0 }
            r0.m = r2;	 Catch:{ InterruptedException -> 0x00e0 }
            goto L_0x00d9;
        L_0x0118:
            r0 = r10.b;	 Catch:{ InterruptedException -> 0x012f }
            r1 = r0.k;	 Catch:{ InterruptedException -> 0x012f }
            r1 = r1 + 1;
            r0.k = r1;	 Catch:{ InterruptedException -> 0x012f }
            r0 = r10.b;	 Catch:{ InterruptedException -> 0x012f }
            r0 = r0.k;	 Catch:{ InterruptedException -> 0x012f }
            r1 = 40;
            if (r0 > r1) goto L_0x0132;
        L_0x0128:
            r0 = 50;
            java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x012f }
            goto L_0x0030;
        L_0x012f:
            r0 = move-exception;
            goto L_0x0030;
        L_0x0132:
            r0 = android.os.Message.obtain();	 Catch:{ InterruptedException -> 0x012f }
            r1 = new android.os.Bundle;	 Catch:{ InterruptedException -> 0x012f }
            r1.<init>();	 Catch:{ InterruptedException -> 0x012f }
            r2 = new com.autonavi.aps.amapapi.model.AmapLoc;	 Catch:{ InterruptedException -> 0x012f }
            r2.<init>();	 Catch:{ InterruptedException -> 0x012f }
            r3 = 10;
            r2.b(r3);	 Catch:{ InterruptedException -> 0x012f }
            r3 = "\u8bf7\u68c0\u67e5\u914d\u7f6e\u6587\u4ef6\u662f\u5426\u914d\u7f6e\u670d\u52a1";
            r2.b(r3);	 Catch:{ InterruptedException -> 0x012f }
            r3 = "location";
            r1.putParcelable(r3, r2);	 Catch:{ InterruptedException -> 0x012f }
            r0.setData(r1);	 Catch:{ InterruptedException -> 0x012f }
            r1 = 1;
            r0.what = r1;	 Catch:{ InterruptedException -> 0x012f }
            r1 = r10.b;	 Catch:{ InterruptedException -> 0x012f }
            r1 = r1.b;	 Catch:{ InterruptedException -> 0x012f }
            if (r1 == 0) goto L_0x0036;
        L_0x015d:
            r1 = r10.b;	 Catch:{ InterruptedException -> 0x012f }
            r1 = r1.b;	 Catch:{ InterruptedException -> 0x012f }
            r1.sendMessage(r0);	 Catch:{ InterruptedException -> 0x012f }
            goto L_0x0036;
        L_0x0166:
            r1 = r10.b;	 Catch:{ Throwable -> 0x016f }
            r1 = r1.h;	 Catch:{ Throwable -> 0x016f }
            r1.send(r0);	 Catch:{ Throwable -> 0x016f }
            goto L_0x00a1;
        L_0x016f:
            r0 = move-exception;
            com.loc.br.a(r0);	 Catch:{ Throwable -> 0x00be }
            r0.printStackTrace();	 Catch:{ Throwable -> 0x00be }
            goto L_0x00a1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.a.a.run():void");
        }
    }

    /* compiled from: AMapLocationManager */
    public static class b extends Handler {
        a a;

        public b(a aVar, Looper looper) {
            super(looper);
            this.a = null;
            this.a = aVar;
        }

        public b(a aVar) {
            this.a = null;
            this.a = aVar;
        }

        public void handleMessage(Message message) {
            AMapLocation a;
            AMapLocation aMapLocation;
            Message obtain;
            Iterator it;
            super.handleMessage(message);
            a aVar;
            switch (message.what) {
                case 1:
                    try {
                        Bundle data = message.getData();
                        data.setClassLoader(AmapLoc.class.getClassLoader());
                        a = c.a((AmapLoc) data.getParcelable("location"));
                        try {
                            a.setProvider("lbs");
                            aMapLocation = a;
                        } catch (Throwable th) {
                            aMapLocation = a;
                            if (this.a.e != null) {
                                this.a.e.a(aMapLocation);
                            }
                            if (!"gps".equals(aMapLocation.getProvider())) {
                                if (this.a.a.isOnceLocation()) {
                                    this.a.stopLocation();
                                }
                                obtain = Message.obtain();
                                obtain.what = 6;
                                if (this.a.h != null) {
                                    this.a.h.send(obtain);
                                }
                            }
                            if (!this.a.t) {
                                if (this.a.g != null) {
                                    this.a.g.a(aMapLocation);
                                }
                                it = this.a.d.iterator();
                                while (it.hasNext()) {
                                    ((AMapLocationListener) it.next()).onLocationChanged(aMapLocation);
                                }
                            }
                            try {
                                if (this.a.a.isOnceLocation()) {
                                    this.a.stopLocation();
                                }
                            } catch (Throwable th2) {
                            }
                            try {
                                obtain = Message.obtain();
                                obtain.what = 6;
                                if (this.a.h != null) {
                                    this.a.h.send(obtain);
                                }
                            } catch (Throwable th3) {
                            }
                        }
                    } catch (Throwable th4) {
                        a = null;
                        aMapLocation = a;
                        if (this.a.e != null) {
                            this.a.e.a(aMapLocation);
                        }
                        if ("gps".equals(aMapLocation.getProvider())) {
                            if (this.a.a.isOnceLocation()) {
                                this.a.stopLocation();
                            }
                            obtain = Message.obtain();
                            obtain.what = 6;
                            if (this.a.h != null) {
                                this.a.h.send(obtain);
                            }
                        }
                        if (this.a.t) {
                            if (this.a.g != null) {
                                this.a.g.a(aMapLocation);
                            }
                            it = this.a.d.iterator();
                            while (it.hasNext()) {
                                ((AMapLocationListener) it.next()).onLocationChanged(aMapLocation);
                            }
                        }
                        if (this.a.a.isOnceLocation()) {
                            this.a.stopLocation();
                        }
                        obtain = Message.obtain();
                        obtain.what = 6;
                        if (this.a.h != null) {
                            this.a.h.send(obtain);
                        }
                    }
                case 2:
                    try {
                        a = (AMapLocation) message.obj;
                        try {
                            if (a.getErrorCode() == 0) {
                                this.a.l = br.b();
                                this.a.o = true;
                            }
                            aMapLocation = a;
                        } catch (Throwable th5) {
                            aMapLocation = a;
                            if (this.a.e != null) {
                                this.a.e.a(aMapLocation);
                            }
                            if ("gps".equals(aMapLocation.getProvider())) {
                                if (this.a.a.isOnceLocation()) {
                                    this.a.stopLocation();
                                }
                                obtain = Message.obtain();
                                obtain.what = 6;
                                if (this.a.h != null) {
                                    this.a.h.send(obtain);
                                }
                            }
                            if (this.a.t) {
                                if (this.a.g != null) {
                                    this.a.g.a(aMapLocation);
                                }
                                it = this.a.d.iterator();
                                while (it.hasNext()) {
                                    ((AMapLocationListener) it.next()).onLocationChanged(aMapLocation);
                                }
                            }
                            if (this.a.a.isOnceLocation()) {
                                this.a.stopLocation();
                            }
                            obtain = Message.obtain();
                            obtain.what = 6;
                            if (this.a.h != null) {
                                this.a.h.send(obtain);
                            }
                        }
                    } catch (Throwable th6) {
                        a = null;
                        aMapLocation = a;
                        if (this.a.e != null) {
                            this.a.e.a(aMapLocation);
                        }
                        if ("gps".equals(aMapLocation.getProvider())) {
                            if (this.a.a.isOnceLocation()) {
                                this.a.stopLocation();
                            }
                            obtain = Message.obtain();
                            obtain.what = 6;
                            if (this.a.h != null) {
                                this.a.h.send(obtain);
                            }
                        }
                        if (this.a.t) {
                            if (this.a.g != null) {
                                this.a.g.a(aMapLocation);
                            }
                            it = this.a.d.iterator();
                            while (it.hasNext()) {
                                ((AMapLocationListener) it.next()).onLocationChanged(aMapLocation);
                            }
                        }
                        if (this.a.a.isOnceLocation()) {
                            this.a.stopLocation();
                        }
                        obtain = Message.obtain();
                        obtain.what = 6;
                        if (this.a.h != null) {
                            this.a.h.send(obtain);
                        }
                    }
                case StatusLine.HTTP_CONTINUE /*100*/:
                    try {
                        this.a.f();
                    } catch (Throwable th7) {
                    }
                    return;
                case 101:
                    try {
                        obtain = Message.obtain();
                        obtain.what = 2;
                        if (this.a.h == null) {
                            aVar = this.a;
                            aVar.n++;
                            if (this.a.n < 10) {
                                this.a.b.sendEmptyMessageDelayed(101, 50);
                            }
                        } else {
                            this.a.n = 0;
                            this.a.h.send(obtain);
                        }
                    } catch (RemoteException e) {
                    }
                    return;
                case 102:
                    try {
                        obtain = Message.obtain();
                        obtain.what = 3;
                        if (this.a.h == null) {
                            aVar = this.a;
                            aVar.n++;
                            if (this.a.n < 10) {
                                this.a.b.sendEmptyMessageDelayed(102, 50);
                            }
                        } else {
                            this.a.n = 0;
                            this.a.h.send(obtain);
                        }
                    } catch (RemoteException e2) {
                    }
                    return;
                default:
                    return;
            }
            try {
                if (this.a.e != null) {
                    this.a.e.a(aMapLocation);
                }
                if ("gps".equals(aMapLocation.getProvider()) && !this.a.d()) {
                    if (this.a.a.isOnceLocation()) {
                        this.a.stopLocation();
                    }
                    obtain = Message.obtain();
                    obtain.what = 6;
                    if (this.a.h != null) {
                        this.a.h.send(obtain);
                    }
                }
                if (this.a.t) {
                    if (this.a.g != null) {
                        this.a.g.a(aMapLocation);
                    }
                    it = this.a.d.iterator();
                    while (it.hasNext()) {
                        ((AMapLocationListener) it.next()).onLocationChanged(aMapLocation);
                    }
                }
                if (this.a.a.isOnceLocation()) {
                    this.a.stopLocation();
                }
                obtain = Message.obtain();
                obtain.what = 6;
                if (this.a.h != null) {
                    this.a.h.send(obtain);
                }
            } catch (Throwable th8) {
            }
        }
    }

    public a(Context context) {
        this.p = 10000;
        this.q = 30000;
        this.c = null;
        this.s = false;
        this.t = true;
        this.d = new ArrayList();
        this.f = false;
        this.u = 0;
        this.v = true;
        this.w = false;
        this.h = null;
        this.i = null;
        this.x = new ServiceConnection() {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void onServiceDisconnected(ComponentName componentName) {
                this.a.h = null;
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a.h = new Messenger(iBinder);
            }
        };
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.o = false;
        this.r = context;
        b();
        this.g = f.a(context);
        if (Looper.myLooper() != null) {
            this.b = new b(this);
        } else {
            this.b = new b(this, this.r.getMainLooper());
        }
        this.i = new Messenger(this.b);
        this.c = new e(context, this.b);
        try {
            this.e = new d(context);
        } catch (Throwable th) {
            br.a(th);
            th.printStackTrace();
        }
    }

    private void b() {
        try {
            Intent intent = new Intent(this.r, APSService.class);
            intent.putExtra("apiKey", c.b);
            this.r.bindService(intent, this.x, 1);
        } catch (Throwable th) {
            br.a(th);
            th.printStackTrace();
        }
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        this.a = aMapLocationClientOption;
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener != null) {
            if (this.d == null) {
                this.d = new ArrayList();
            }
            if (!this.d.contains(aMapLocationListener)) {
                this.d.add(aMapLocationListener);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("listener\u53c2\u6570\u4e0d\u80fd\u4e3anull");
    }

    private void c() {
        if (this.j == null) {
            this.j = new a(this, "locationThread");
            this.j.start();
        }
    }

    public void startLocation() {
        if (this.a == null) {
            this.a = new AMapLocationClientOption();
        }
        this.t = false;
        switch (AnonymousClass4.a[this.a.getLocationMode().ordinal()]) {
            case 1:
                c();
                this.c.a();
                this.w = false;
            case 2:
                if (!this.w) {
                    this.c.a(this.a);
                    this.w = true;
                }
                a();
            case 3:
                c();
                if (!this.w) {
                    this.c.a(this.a);
                    this.w = true;
                }
            default:
        }
    }

    public void stopLocation() {
        this.v = true;
        a();
        this.c.a();
        this.w = false;
        this.o = false;
        this.s = false;
        this.n = 0;
        this.m = 0;
        this.t = true;
    }

    void a() {
        if (this.j != null) {
            this.j.a = false;
            this.j.interrupt();
        }
        this.k = 0;
        this.j = null;
    }

    public void onDestroy() {
        stopLocation();
        if (this.e != null) {
            this.e.a();
        }
        if (this.x != null) {
            this.r.unbindService(this.x);
        }
        if (this.d != null) {
            this.d.clear();
            this.d = null;
        }
        this.x = null;
        if (this.b != null) {
            this.b.removeCallbacksAndMessages(null);
        }
    }

    public void addGeoFenceAlert(String str, double d, double d2, float f, long j, PendingIntent pendingIntent) {
        Fence fence = new Fence();
        fence.b = str;
        fence.d = d;
        fence.c = d2;
        fence.e = f;
        fence.a = pendingIntent;
        fence.a(j);
        if (this.e != null) {
            this.e.a(fence, fence.a);
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent, String str) {
        if (this.e != null) {
            this.e.a(pendingIntent, str);
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent) {
        if (this.e != null) {
            this.e.a(pendingIntent);
        }
    }

    public void startAssistantLocation() {
        if (this.b != null) {
            this.b.sendEmptyMessage(101);
        }
    }

    public void stopAssistantLocation() {
        if (this.b != null) {
            this.b.sendEmptyMessage(102);
        }
    }

    private boolean d() {
        boolean z;
        if (br.b() - this.l <= 10000) {
            z = true;
        } else {
            z = false;
        }
        return !z;
    }

    private boolean e() {
        long b = br.b();
        if (this.m == 0) {
            return false;
        }
        boolean z;
        if (b - this.m <= 30000) {
            z = true;
        } else {
            z = false;
        }
        return !z;
    }

    private void f() {
        Object obj = 1;
        Object obj2 = null;
        try {
            if (this.r.checkCallingOrSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") == 0) {
                int i = 1;
            } else if (!(this.r instanceof Activity)) {
                obj = null;
            }
            if (obj == null) {
                g();
                return;
            }
            Builder builder = new Builder(this.r);
            builder.setMessage(bp.j());
            if (!"".equals(bp.k())) {
                if (bp.k() != null) {
                    builder.setPositiveButton(bp.k(), new OnClickListener() {
                        final /* synthetic */ a a;

                        {
                            this.a = r1;
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            this.a.g();
                            dialogInterface.cancel();
                        }
                    });
                }
            }
            builder.setNegativeButton(bp.l(), new OnClickListener() {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog create = builder.create();
            if (obj2 != null) {
                create.getWindow().setType(2003);
            }
            create.setCanceledOnTouchOutside(false);
            create.show();
        } catch (Throwable th) {
            g();
        }
    }

    private void g() {
        Intent intent;
        try {
            intent = new Intent();
            intent.setComponent(new ComponentName("com.autonavi.minimap", bp.o()));
            intent.setFlags(268435456);
            intent.setData(Uri.parse(bp.m()));
            this.r.startActivity(intent);
        } catch (Throwable th) {
        }
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        if (!this.d.isEmpty() && this.d.contains(aMapLocationListener)) {
            this.d.remove(aMapLocationListener);
        }
        if (this.d.isEmpty()) {
            stopLocation();
        }
    }
}
