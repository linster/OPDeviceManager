package com.loc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.a;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.util.Iterator;

public class d extends Handler {
    aB iX = null;

    public d(aB aBVar) {
        this.iX = aBVar;
    }

    public d(aB aBVar, Looper looper) {
        super(looper);
        this.iX = aBVar;
    }

    public void handleMessage(Message message) {
        AMapLocation mS;
        AMapLocation aMapLocation;
        Message obtain;
        Iterator it;
        super.handleMessage(message);
        aB aBVar;
        switch (message.what) {
            case 1:
                try {
                    Bundle data = message.getData();
                    data.setClassLoader(AmapLoc.class.getClassLoader());
                    mS = H.mS((AmapLoc) data.getParcelable("location"));
                    try {
                        mS.setProvider("lbs");
                        aMapLocation = mS;
                    } catch (Throwable th) {
                        aMapLocation = mS;
                        if (this.iX.oh != null) {
                            this.iX.oh.qk(aMapLocation);
                        }
                        if (!"gps".equals(aMapLocation.getProvider())) {
                            if (this.iX.ob.ie()) {
                                this.iX.hU();
                            }
                            obtain = Message.obtain();
                            obtain.what = 6;
                            if (this.iX.on != null) {
                                this.iX.on.send(obtain);
                            }
                        }
                        if (!this.iX.of) {
                            if (this.iX.om != null) {
                                this.iX.om.nk(aMapLocation);
                            }
                            it = this.iX.og.iterator();
                            while (it.hasNext()) {
                                ((a) it.next()).E(aMapLocation);
                            }
                        }
                        try {
                            if (this.iX.ob.ie()) {
                                this.iX.hU();
                            }
                        } catch (Throwable th2) {
                        }
                        try {
                            obtain = Message.obtain();
                            obtain.what = 6;
                            if (this.iX.on != null) {
                                this.iX.on.send(obtain);
                            }
                        } catch (Throwable th3) {
                        }
                    }
                } catch (Throwable th4) {
                    mS = null;
                    aMapLocation = mS;
                    if (this.iX.oh != null) {
                        this.iX.oh.qk(aMapLocation);
                    }
                    if ("gps".equals(aMapLocation.getProvider())) {
                        if (this.iX.ob.ie()) {
                            this.iX.hU();
                        }
                        obtain = Message.obtain();
                        obtain.what = 6;
                        if (this.iX.on != null) {
                            this.iX.on.send(obtain);
                        }
                    }
                    if (this.iX.of) {
                        if (this.iX.om != null) {
                            this.iX.om.nk(aMapLocation);
                        }
                        it = this.iX.og.iterator();
                        while (it.hasNext()) {
                            ((a) it.next()).E(aMapLocation);
                        }
                    }
                    if (this.iX.ob.ie()) {
                        this.iX.hU();
                    }
                    obtain = Message.obtain();
                    obtain.what = 6;
                    if (this.iX.on != null) {
                        this.iX.on.send(obtain);
                    }
                }
            case 2:
                try {
                    mS = (AMapLocation) message.obj;
                    try {
                        if (mS.getErrorCode() == 0) {
                            this.iX.os = bq.vM();
                            this.iX.ov = true;
                        }
                        aMapLocation = mS;
                    } catch (Throwable th5) {
                        aMapLocation = mS;
                        if (this.iX.oh != null) {
                            this.iX.oh.qk(aMapLocation);
                        }
                        if ("gps".equals(aMapLocation.getProvider())) {
                            if (this.iX.ob.ie()) {
                                this.iX.hU();
                            }
                            obtain = Message.obtain();
                            obtain.what = 6;
                            if (this.iX.on != null) {
                                this.iX.on.send(obtain);
                            }
                        }
                        if (this.iX.of) {
                            if (this.iX.om != null) {
                                this.iX.om.nk(aMapLocation);
                            }
                            it = this.iX.og.iterator();
                            while (it.hasNext()) {
                                ((a) it.next()).E(aMapLocation);
                            }
                        }
                        if (this.iX.ob.ie()) {
                            this.iX.hU();
                        }
                        obtain = Message.obtain();
                        obtain.what = 6;
                        if (this.iX.on != null) {
                            this.iX.on.send(obtain);
                        }
                    }
                } catch (Throwable th6) {
                    mS = null;
                    aMapLocation = mS;
                    if (this.iX.oh != null) {
                        this.iX.oh.qk(aMapLocation);
                    }
                    if ("gps".equals(aMapLocation.getProvider())) {
                        if (this.iX.ob.ie()) {
                            this.iX.hU();
                        }
                        obtain = Message.obtain();
                        obtain.what = 6;
                        if (this.iX.on != null) {
                            this.iX.on.send(obtain);
                        }
                    }
                    if (this.iX.of) {
                        if (this.iX.om != null) {
                            this.iX.om.nk(aMapLocation);
                        }
                        it = this.iX.og.iterator();
                        while (it.hasNext()) {
                            ((a) it.next()).E(aMapLocation);
                        }
                    }
                    if (this.iX.ob.ie()) {
                        this.iX.hU();
                    }
                    obtain = Message.obtain();
                    obtain.what = 6;
                    if (this.iX.on != null) {
                        this.iX.on.send(obtain);
                    }
                }
            case 100:
                try {
                    this.iX.pS();
                } catch (Throwable th7) {
                }
                return;
            case 101:
                try {
                    obtain = Message.obtain();
                    obtain.what = 2;
                    if (this.iX.on == null) {
                        aBVar = this.iX;
                        aBVar.ou++;
                        if (this.iX.ou < 10) {
                            this.iX.oc.sendEmptyMessageDelayed(101, 50);
                        }
                    } else {
                        this.iX.ou = 0;
                        this.iX.on.send(obtain);
                    }
                } catch (RemoteException e) {
                }
                return;
            case 102:
                try {
                    obtain = Message.obtain();
                    obtain.what = 3;
                    if (this.iX.on == null) {
                        aBVar = this.iX;
                        aBVar.ou++;
                        if (this.iX.ou < 10) {
                            this.iX.oc.sendEmptyMessageDelayed(102, 50);
                        }
                    } else {
                        this.iX.ou = 0;
                        this.iX.on.send(obtain);
                    }
                } catch (RemoteException e2) {
                }
                return;
            default:
                return;
        }
        try {
            if (this.iX.oh != null) {
                this.iX.oh.qk(aMapLocation);
            }
            if ("gps".equals(aMapLocation.getProvider()) && !this.iX.pQ()) {
                if (this.iX.ob.ie()) {
                    this.iX.hU();
                }
                obtain = Message.obtain();
                obtain.what = 6;
                if (this.iX.on != null) {
                    this.iX.on.send(obtain);
                }
            }
            if (this.iX.of) {
                if (this.iX.om != null) {
                    this.iX.om.nk(aMapLocation);
                }
                it = this.iX.og.iterator();
                while (it.hasNext()) {
                    ((a) it.next()).E(aMapLocation);
                }
            }
            if (this.iX.ob.ie()) {
                this.iX.hU();
            }
            obtain = Message.obtain();
            obtain.what = 6;
            if (this.iX.on != null) {
                this.iX.on.send(obtain);
            }
        } catch (Throwable th8) {
        }
    }
}
