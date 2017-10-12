package com.amap.api.location;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.loc.ac;
import com.loc.c;

public class AMapLocationClient implements LocationManagerBase {
    a a;
    Context b;
    LocationManagerBase c;
    AMapLocationClientOption d;
    AMapLocationListener e;
    AMapLocationClient f;
    double g;
    double h;
    float i;
    long j;
    String k;
    PendingIntent l;

    static class a extends Handler {
        AMapLocationClient a;

        public a(AMapLocationClient aMapLocationClient, Looper looper) {
            super(looper);
            this.a = null;
            this.a = aMapLocationClient;
        }

        public a(AMapLocationClient aMapLocationClient) {
            this.a = null;
            this.a = aMapLocationClient;
        }

        public void handleMessage(Message message) {
            try {
                super.handleMessage(message);
                switch (message.arg1) {
                    case 1:
                        try {
                            this.a.d = (AMapLocationClientOption) message.obj;
                            this.a.c.setLocationOption(this.a.d);
                        } catch (Throwable th) {
                        }
                    case 2:
                        try {
                            this.a.e = (AMapLocationListener) message.obj;
                            this.a.c.setLocationListener(this.a.e);
                        } catch (Throwable th2) {
                        }
                    case 3:
                        try {
                            this.a.c.startLocation();
                        } catch (Throwable th3) {
                        }
                    case 4:
                        try {
                            this.a.c.stopLocation();
                        } catch (Throwable th4) {
                        }
                    case 5:
                        try {
                            this.a.e = (AMapLocationListener) message.obj;
                            this.a.c.unRegisterLocationListener(this.a.e);
                        } catch (Throwable th5) {
                        }
                    case 6:
                        try {
                            this.a.c.addGeoFenceAlert(this.a.k, this.a.g, this.a.h, this.a.i, this.a.j, this.a.l);
                        } catch (Throwable th6) {
                        }
                    case 7:
                        try {
                            this.a.c.removeGeoFenceAlert((PendingIntent) message.obj);
                        } catch (Throwable th7) {
                        }
                    case 8:
                        try {
                            this.a.c.startAssistantLocation();
                        } catch (Throwable th8) {
                        }
                    case 9:
                        try {
                            this.a.c.stopAssistantLocation();
                        } catch (Throwable th9) {
                        }
                    case 10:
                        try {
                            this.a.c.removeGeoFenceAlert((PendingIntent) message.obj, this.a.k);
                        } catch (Throwable th10) {
                        }
                    case 11:
                        try {
                            this.a.c.onDestroy();
                            this.a.c = null;
                            this.a = null;
                        } catch (Throwable th11) {
                        }
                    default:
                }
            } catch (Throwable th12) {
            }
        }
    }

    public AMapLocationClient(Context context) {
        if (context != null) {
            try {
                this.b = context.getApplicationContext();
                this.f = new AMapLocationClient(this.b, true);
                if (Looper.myLooper() != null) {
                    this.a = new a(this.f);
                    return;
                } else {
                    this.a = new a(this.f, this.b.getMainLooper());
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        throw new IllegalArgumentException("Context\u53c2\u6570\u4e0d\u80fd\u4e3anull");
    }

    private AMapLocationClient(Context context, boolean z) {
        try {
            this.b = context;
            Context context2 = context;
            this.c = (LocationManagerBase) ac.a(context2, c.a("2.2.0"), "com.amap.api.location.LocationManagerWrapper", com.loc.a.class, new Class[]{Context.class}, new Object[]{context});
        } catch (Throwable th) {
            this.c = new com.loc.a(context);
        }
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        if (aMapLocationClientOption != null) {
            try {
                Message obtain = Message.obtain();
                obtain.arg1 = 1;
                obtain.obj = aMapLocationClientOption;
                this.a.sendMessage(obtain);
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        throw new IllegalArgumentException("LocationManagerOption\u53c2\u6570\u4e0d\u80fd\u4e3anull");
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener != null) {
            try {
                Message obtain = Message.obtain();
                obtain.arg1 = 2;
                obtain.obj = aMapLocationListener;
                this.a.sendMessage(obtain);
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        throw new IllegalArgumentException("listener\u53c2\u6570\u4e0d\u80fd\u4e3anull");
    }

    public void startLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 3;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void stopLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 4;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void addGeoFenceAlert(String str, double d, double d2, float f, long j, PendingIntent pendingIntent) {
        try {
            Message obtain = Message.obtain();
            this.f.k = str;
            this.f.g = d;
            this.f.h = d2;
            this.f.i = f;
            this.f.l = pendingIntent;
            this.f.j = j;
            obtain.arg1 = 6;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent, String str) {
        try {
            Message obtain = Message.obtain();
            this.f.k = str;
            obtain.obj = pendingIntent;
            obtain.arg1 = 10;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent) {
        try {
            Message obtain = Message.obtain();
            obtain.obj = pendingIntent;
            obtain.arg1 = 7;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void startAssistantLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 8;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void stopAssistantLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 9;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 5;
            obtain.obj = aMapLocationListener;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void onDestroy() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 11;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
