package com.loc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Looper;
import android.os.Messenger;
import com.amap.api.fence.Fence;
import com.amap.api.location.APSService;
import com.amap.api.location.a;
import com.amap.api.location.c;
import com.amap.api.location.e;
import java.util.ArrayList;

public class aB implements c {
    private final int nY = 10000;
    private final int nZ = 30000;
    private Context oa;
    e ob;
    d oc;
    ao od = null;
    private boolean oe = false;
    private boolean of = true;
    ArrayList og = new ArrayList();
    aD oh;
    boolean oi = false;
    private long oj = 0;
    private boolean ok = true;
    private boolean ol = false;
    N om;
    Messenger on = null;
    Messenger oo = null;
    aO op;
    private ServiceConnection oq = new bF(this);
    int or = 0;
    long os = 0;
    long ot = 0;
    int ou = 0;
    boolean ov = false;

    public aB(Context context) {
        this.oa = context;
        pN();
        this.om = N.nj(context);
        if (Looper.myLooper() != null) {
            this.oc = new d(this);
        } else {
            this.oc = new d(this, this.oa.getMainLooper());
        }
        this.oo = new Messenger(this.oc);
        this.od = new ao(context, this.oc);
        try {
            this.oh = new aD(context);
        } catch (Throwable th) {
            bq.vC(th);
            th.printStackTrace();
        }
    }

    private void pN() {
        try {
            Intent intent = new Intent(this.oa, APSService.class);
            intent.putExtra("apiKey", H.lC);
            this.oa.bindService(intent, this.oq, 1);
        } catch (Throwable th) {
            bq.vC(th);
            th.printStackTrace();
        }
    }

    private void pO() {
        if (this.op == null) {
            this.op = new aO(this, "locationThread");
            this.op.start();
        }
    }

    private boolean pQ() {
        return !(((bq.vM() - this.os) > 10000 ? 1 : ((bq.vM() - this.os) == 10000 ? 0 : -1)) <= 0);
    }

    private boolean pR() {
        long vM = bq.vM();
        if (this.ot == 0) {
            return false;
        }
        return !(((vM - this.ot) > 30000 ? 1 : ((vM - this.ot) == 30000 ? 0 : -1)) <= 0);
    }

    private void pS() {
        Object obj = 1;
        Object obj2 = null;
        try {
            if (this.oa.checkCallingOrSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") == 0) {
                int i = 1;
            } else if (!(this.oa instanceof Activity)) {
                obj = null;
            }
            if (obj == null) {
                pT();
                return;
            }
            Builder builder = new Builder(this.oa);
            builder.setMessage(f.ko());
            if (!"".equals(f.kp())) {
                if (f.kp() != null) {
                    builder.setPositiveButton(f.kp(), new m(this));
                }
            }
            builder.setNegativeButton(f.kq(), new bd(this));
            AlertDialog create = builder.create();
            if (obj2 != null) {
                create.getWindow().setType(2003);
            }
            create.setCanceledOnTouchOutside(false);
            create.show();
        } catch (Throwable th) {
            pT();
        }
    }

    private void pT() {
        Intent intent;
        try {
            intent = new Intent();
            intent.setComponent(new ComponentName("com.autonavi.minimap", f.kt()));
            intent.setFlags(268435456);
            intent.setData(Uri.parse(f.kr()));
            this.oa.startActivity(intent);
        } catch (Throwable th) {
        }
    }

    public void hR(e eVar) {
        this.ob = eVar;
    }

    public void hS(a aVar) {
        if (aVar != null) {
            if (this.og == null) {
                this.og = new ArrayList();
            }
            if (!this.og.contains(aVar)) {
                this.og.add(aVar);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("listener参数不能为null");
    }

    public void hT() {
        if (this.ob == null) {
            this.ob = new e();
        }
        this.of = false;
        switch (aa.mI[this.ob.ik().ordinal()]) {
            case 1:
                pO();
                this.od.oR();
                this.ol = false;
                return;
            case 2:
                if (!this.ol) {
                    this.od.oQ(this.ob);
                    this.ol = true;
                }
                pP();
                return;
            case 3:
                pO();
                if (!this.ol) {
                    this.od.oQ(this.ob);
                    this.ol = true;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void hU() {
        this.ok = true;
        pP();
        this.od.oR();
        this.ol = false;
        this.ov = false;
        this.oe = false;
        this.ou = 0;
        this.ot = 0;
        this.of = true;
    }

    public void hV(String str, double d, double d2, float f, long j, PendingIntent pendingIntent) {
        Fence fence = new Fence();
        fence.iL = str;
        fence.iM = d;
        fence.c = d2;
        fence.iN = f;
        fence.iK = pendingIntent;
        fence.iv(j);
        if (this.oh != null) {
            this.oh.qf(fence, fence.iK);
        }
    }

    public void hW(PendingIntent pendingIntent, String str) {
        if (this.oh != null) {
            this.oh.qh(pendingIntent, str);
        }
    }

    public void hX(PendingIntent pendingIntent) {
        if (this.oh != null) {
            this.oh.qg(pendingIntent);
        }
    }

    public void hY() {
        if (this.oc != null) {
            this.oc.sendEmptyMessage(101);
        }
    }

    public void hZ() {
        if (this.oc != null) {
            this.oc.sendEmptyMessage(102);
        }
    }

    public void ia(a aVar) {
        if (!this.og.isEmpty() && this.og.contains(aVar)) {
            this.og.remove(aVar);
        }
        if (this.og.isEmpty()) {
            hU();
        }
    }

    public void onDestroy() {
        hU();
        if (this.oh != null) {
            this.oh.qn();
        }
        if (this.oq != null) {
            this.oa.unbindService(this.oq);
        }
        if (this.og != null) {
            this.og.clear();
            this.og = null;
        }
        this.oq = null;
        if (this.oc != null) {
            this.oc.removeCallbacksAndMessages(null);
        }
    }

    void pP() {
        if (this.op != null) {
            this.op.pK = false;
            this.op.interrupt();
        }
        this.or = 0;
        this.op = null;
    }
}
