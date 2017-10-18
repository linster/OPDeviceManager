package com.loc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.autonavi.aps.amapapi.model.AmapLoc;

class aZ extends Handler {
    br rm = null;
    AmapLoc rn = null;
    private boolean ro = true;
    final /* synthetic */ br rp;

    public aZ(br brVar, br brVar2) {
        this.rp = brVar;
        this.rm = brVar2;
    }

    public void handleMessage(Message message) {
        Object obj = 1;
        try {
            switch (message.what) {
                case 1:
                    try {
                        synchronized (this.rp.sX) {
                            boolean z;
                            Bundle data = message.getData();
                            this.rp.sP = data.getBoolean("isfirst");
                            Messenger messenger = message.replyTo;
                            long vM = bq.vM();
                            boolean z2 = data.getBoolean("isNeedAddress");
                            if (z2 != this.ro) {
                                this.rm.ta = 0;
                            }
                            this.ro = z2;
                            if (this.rp.sZ != null && this.rp.sZ.yw() == 0) {
                                if (vM - this.rm.ta < 1800) {
                                    obj = null;
                                }
                                if (obj == null) {
                                    Message obtain = Message.obtain();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("location", this.rp.sZ);
                                    obtain.setData(bundle);
                                    obtain.what = 1;
                                    messenger.send(obtain);
                                    z = data.getBoolean("wifiactivescan");
                                    this.rp.sJ = data.getBoolean("isKillProcess");
                                    vM = data.getLong("httptimeout");
                                    if (this.rp.tb != null) {
                                        this.rp.tb.put("reversegeo", z2 ? "0" : "1");
                                        this.rp.tb.put("wifiactivescan", z ? "0" : "1");
                                        this.rp.tb.put("httptimeout", vM);
                                    }
                                    this.rm.wE(this.rp.tb);
                                }
                            }
                            if (!this.rp.sQ.contains(messenger)) {
                                this.rp.sQ.add(messenger);
                            }
                            this.rp.sW = true;
                            this.rm.sX.notify();
                            z = data.getBoolean("wifiactivescan");
                            this.rp.sJ = data.getBoolean("isKillProcess");
                            vM = data.getLong("httptimeout");
                            if (this.rp.tb != null) {
                                if (z2) {
                                }
                                this.rp.tb.put("reversegeo", z2 ? "0" : "1");
                                if (z) {
                                }
                                this.rp.tb.put("wifiactivescan", z ? "0" : "1");
                                this.rp.tb.put("httptimeout", vM);
                            }
                            this.rm.wE(this.rp.tb);
                        }
                        break;
                    } catch (RemoteException e) {
                        break;
                    } catch (Throwable th) {
                        break;
                    }
                case 2:
                    this.rp.wF();
                    break;
                case 3:
                    this.rp.wG();
                    break;
                case 4:
                    synchronized (this.rp.sX) {
                        if (f.ki()) {
                            if (this.rp.sR < f.kj()) {
                                this.rp.sR = this.rp.sR + 1;
                                this.rp.sW = true;
                                this.rm.sX.notify();
                                this.rp.sN.sendEmptyMessageDelayed(4, 2000);
                            }
                        }
                    }
                    break;
                case 5:
                    synchronized (this.rp.sX) {
                        if (f.kk()) {
                            if (f.kl() > 2) {
                                this.rp.sW = true;
                                if (f.km()) {
                                    this.rm.sX.notify();
                                } else if (!bq.wh(this.rp.sI)) {
                                    this.rm.sX.notify();
                                }
                                this.rp.sN.sendEmptyMessageDelayed(5, (long) (f.kl() * 1000));
                            }
                        }
                    }
                    break;
                case 6:
                    synchronized (this.rp.sX) {
                        this.rp.wJ();
                    }
                    break;
            }
            super.handleMessage(message);
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }
}
