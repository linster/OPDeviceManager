package com.loc;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: CgiManager */
public class bb {
    private Context a;
    private int b;
    private ArrayList<ba> c;
    private int d;
    private TelephonyManager e;
    private Object f;
    private long g;
    private JSONObject h;
    private PhoneStateListener i;
    private CellLocation j;

    public bb(Context context, JSONObject jSONObject) {
        this.b = 9;
        this.c = new ArrayList();
        this.d = -113;
        this.g = 0;
        if (context != null) {
            p();
            this.e = (TelephonyManager) br.a(context, "phone");
            this.h = jSONObject;
            this.a = context;
        } else {
            p();
            this.e = (TelephonyManager) br.a(context, "phone");
            this.h = jSONObject;
            this.a = context;
        }
        try {
            this.b = br.a(this.e.getCellLocation(), context);
        } catch (Exception e) {
            this.b = 9;
        }
        q();
    }

    public ArrayList<ba> a() {
        return this.c;
    }

    public ba b() {
        ArrayList arrayList = this.c;
        if (arrayList.size() < 1) {
            return null;
        }
        return (ba) arrayList.get(0);
    }

    public int c() {
        return this.b;
    }

    public CellLocation d() {
        CellLocation cellLocation = null;
        if (this.e == null) {
            return null;
        }
        try {
            cellLocation = this.e.getCellLocation();
            if (a(cellLocation)) {
                this.j = cellLocation;
            }
        } catch (Exception e) {
        }
        return cellLocation;
    }

    public TelephonyManager e() {
        return this.e;
    }

    public void f() {
        l();
    }

    public void g() {
        r();
    }

    public void h() {
        p();
    }

    public void i() {
        if (!(this.e == null || this.i == null)) {
            try {
                this.e.listen(this.i, 0);
            } catch (Exception e) {
            }
        }
        this.c.clear();
        this.d = -113;
        this.e = null;
        this.f = null;
    }

    public void j() {
        switch (this.b) {
            case 1:
                if (this.c.isEmpty()) {
                    this.b = 9;
                }
            case 2:
                if (this.c.isEmpty()) {
                    this.b = 9;
                }
            default:
        }
    }

    public boolean a(boolean z) {
        if (z || this.g == 0) {
            return false;
        }
        if (br.b() - this.g >= 30000) {
            return true;
        }
        return false;
    }

    public boolean a(CellLocation cellLocation) {
        boolean z = false;
        if (cellLocation == null) {
            return false;
        }
        boolean z2 = true;
        switch (br.a(cellLocation, this.a)) {
            case 1:
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                if (!(gsmCellLocation.getLac() == -1 || gsmCellLocation.getLac() == 0 || gsmCellLocation.getLac() > 65535 || gsmCellLocation.getCid() == -1 || gsmCellLocation.getCid() == 0 || gsmCellLocation.getCid() == 65535 || gsmCellLocation.getCid() >= 268435455)) {
                    z = true;
                    break;
                }
            case 2:
                try {
                    if (bq.b(cellLocation, "getSystemId", new Object[0]) <= 0) {
                        z2 = false;
                    } else if (bq.b(cellLocation, "getNetworkId", new Object[0]) < 0) {
                        z2 = false;
                    } else if (bq.b(cellLocation, "getBaseStationId", new Object[0]) < 0) {
                        z2 = false;
                    }
                    z = z2;
                    break;
                } catch (Exception e) {
                    z = true;
                    break;
                }
            default:
                z = true;
                break;
        }
        if (!z) {
            this.b = 9;
        }
        return z;
    }

    public boolean a(NeighboringCellInfo neighboringCellInfo) {
        if (neighboringCellInfo == null) {
            return false;
        }
        boolean z = true;
        if (neighboringCellInfo.getLac() == -1) {
            z = false;
        } else if (neighboringCellInfo.getLac() == 0) {
            z = false;
        } else if (neighboringCellInfo.getLac() > 65535) {
            z = false;
        } else if (neighboringCellInfo.getCid() == -1) {
            z = false;
        } else if (neighboringCellInfo.getCid() == 0) {
            z = false;
        } else if (neighboringCellInfo.getCid() == 65535) {
            z = false;
        } else if (neighboringCellInfo.getCid() >= 268435455) {
            z = false;
        }
        return z;
    }

    public void a(JSONObject jSONObject) {
        this.h = jSONObject;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void l() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.a;	 Catch:{ all -> 0x0035 }
        r0 = com.loc.br.a(r0);	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x001e;
    L_0x0009:
        r0 = r2.j;	 Catch:{ all -> 0x0035 }
        r0 = r2.a(r0);	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x003d;
    L_0x0011:
        r0 = r2.j;	 Catch:{ all -> 0x0035 }
        r1 = r2.a;	 Catch:{ all -> 0x0035 }
        r0 = com.loc.br.a(r0, r1);	 Catch:{ all -> 0x0035 }
        switch(r0) {
            case 1: goto L_0x003f;
            case 2: goto L_0x0045;
            default: goto L_0x001c;
        };
    L_0x001c:
        monitor-exit(r2);
        return;
    L_0x001e:
        r0 = r2.e;	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x0009;
    L_0x0022:
        r0 = r2.m();	 Catch:{ all -> 0x0035 }
        r1 = r2.a(r0);	 Catch:{ all -> 0x0035 }
        if (r1 == 0) goto L_0x0038;
    L_0x002c:
        r1 = r2.a(r0);	 Catch:{ all -> 0x0035 }
        if (r1 == 0) goto L_0x0009;
    L_0x0032:
        r2.j = r0;	 Catch:{ all -> 0x0035 }
        goto L_0x0009;
    L_0x0035:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
    L_0x0038:
        r0 = r2.n();	 Catch:{ all -> 0x0035 }
        goto L_0x002c;
    L_0x003d:
        monitor-exit(r2);
        return;
    L_0x003f:
        r0 = r2.j;	 Catch:{ all -> 0x0035 }
        r2.b(r0);	 Catch:{ all -> 0x0035 }
        goto L_0x001c;
    L_0x0045:
        r0 = r2.j;	 Catch:{ all -> 0x0035 }
        r2.c(r0);	 Catch:{ all -> 0x0035 }
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bb.l():void");
    }

    private void b(CellLocation cellLocation) {
        if (cellLocation != null && this.e != null) {
            this.c.clear();
            if (a(cellLocation)) {
                this.b = 1;
                this.c.add(d(cellLocation));
                List<NeighboringCellInfo> neighboringCellInfo = this.e.getNeighboringCellInfo();
                if (neighboringCellInfo != null && !neighboringCellInfo.isEmpty()) {
                    for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                        if (a(neighboringCellInfo2)) {
                            ba b = b(neighboringCellInfo2);
                            if (!(b == null || this.c.contains(b))) {
                                this.c.add(b);
                            }
                        }
                    }
                }
            }
        }
    }

    private void c(CellLocation cellLocation) {
        Object obj = null;
        if (cellLocation != null) {
            this.c.clear();
            if (br.c() >= 5) {
                try {
                    if (this.f != null) {
                        Object obj2;
                        try {
                            Field declaredField = cellLocation.getClass().getDeclaredField("mGsmCellLoc");
                            if (!declaredField.isAccessible()) {
                                declaredField.setAccessible(true);
                            }
                            CellLocation cellLocation2 = (GsmCellLocation) declaredField.get(cellLocation);
                            if (cellLocation2 != null && a(cellLocation2)) {
                                b(cellLocation2);
                                int i = 1;
                                if (obj2 != null) {
                                    return;
                                }
                            }
                            obj2 = null;
                            if (obj2 != null) {
                                return;
                            }
                        } catch (Exception e) {
                            obj2 = null;
                        }
                    }
                    if (a(cellLocation)) {
                        this.b = 2;
                        String[] a = br.a(this.e);
                        ba baVar = new ba(2);
                        baVar.a = a[0];
                        baVar.b = a[1];
                        baVar.g = bq.b(cellLocation, "getSystemId", new Object[0]);
                        baVar.h = bq.b(cellLocation, "getNetworkId", new Object[0]);
                        baVar.i = bq.b(cellLocation, "getBaseStationId", new Object[0]);
                        baVar.j = this.d;
                        baVar.e = bq.b(cellLocation, "getBaseStationLatitude", new Object[0]);
                        baVar.f = bq.b(cellLocation, "getBaseStationLongitude", new Object[0]);
                        if (baVar.e >= 0) {
                            if (baVar.f >= 0) {
                                if (baVar.e == Integer.MAX_VALUE) {
                                    baVar.e = 0;
                                    baVar.f = 0;
                                } else if (baVar.f == Integer.MAX_VALUE) {
                                    baVar.e = 0;
                                    baVar.f = 0;
                                } else if (baVar.e == baVar.f && baVar.e > 0) {
                                    baVar.e = 0;
                                    baVar.f = 0;
                                } else {
                                    int i2 = 1;
                                }
                                if (obj == null) {
                                }
                                if (!this.c.contains(baVar)) {
                                    this.c.add(baVar);
                                }
                            }
                        }
                        baVar.e = 0;
                        baVar.f = 0;
                        if (obj == null) {
                        }
                        if (this.c.contains(baVar)) {
                            this.c.add(baVar);
                        }
                    }
                } catch (Exception e2) {
                }
            }
        }
    }

    private CellLocation m() {
        CellLocation cellLocation = null;
        TelephonyManager telephonyManager = this.e;
        if (telephonyManager == null) {
            return cellLocation;
        }
        CellLocation cellLocation2;
        try {
            cellLocation2 = telephonyManager.getCellLocation();
        } catch (Exception e) {
            cellLocation2 = cellLocation;
        }
        if (a(cellLocation2)) {
            return cellLocation2;
        }
        try {
            cellLocation2 = a((List) bq.a(telephonyManager, "getAllCellInfo", new Object[0]));
        } catch (NoSuchMethodException e2) {
        } catch (Exception e3) {
        }
        if (a(cellLocation2)) {
            return cellLocation2;
        }
        Object a;
        try {
            a = bq.a(telephonyManager, "getCellLocationExt", Integer.valueOf(1));
            if (a != null) {
                cellLocation2 = (CellLocation) a;
            }
        } catch (NoSuchMethodException e4) {
        } catch (Exception e5) {
        }
        if (a(cellLocation2)) {
            return cellLocation2;
        }
        try {
            a = bq.a(telephonyManager, "getCellLocationGemini", Integer.valueOf(1));
            if (a != null) {
                cellLocation2 = (CellLocation) a;
            }
        } catch (NoSuchMethodException e6) {
        } catch (Exception e7) {
        }
        if (a(cellLocation2)) {
            return cellLocation2;
        }
        return cellLocation2;
    }

    private CellLocation n() {
        CellLocation a;
        List list;
        CellLocation cellLocation = null;
        Object obj = this.f;
        if (obj == null) {
            return null;
        }
        try {
            Class o = o();
            if (o.isInstance(obj)) {
                Object cast = o.cast(obj);
                String str = "getCellLocation";
                try {
                    a = bq.a(cast, str, new Object[0]);
                } catch (NoSuchMethodException e) {
                    a = null;
                    if (a == null) {
                        try {
                            a = bq.a(cast, str, Integer.valueOf(1));
                        } catch (NoSuchMethodException e2) {
                        } catch (Exception e3) {
                        }
                    }
                    if (a == null) {
                        try {
                            a = bq.a(cast, "getCellLocationGemini", Integer.valueOf(1));
                        } catch (NoSuchMethodException e4) {
                        } catch (Exception e5) {
                        }
                    }
                    if (a == null) {
                        try {
                            list = (List) bq.a(cast, "getAllCellInfo", new Object[0]);
                        } catch (NoSuchMethodException e6) {
                            list = null;
                            a = a(list);
                            if (a == null) {
                            }
                            if (a != null) {
                                cellLocation = a;
                            }
                            return cellLocation;
                        } catch (Exception e7) {
                            list = null;
                            a = a(list);
                            if (a == null) {
                            }
                            if (a != null) {
                                cellLocation = a;
                            }
                            return cellLocation;
                        }
                        a = a(list);
                        if (a == null) {
                        }
                    }
                    if (a != null) {
                        cellLocation = a;
                    }
                    return cellLocation;
                } catch (Exception e8) {
                    a = null;
                    if (a == null) {
                        a = bq.a(cast, str, Integer.valueOf(1));
                    }
                    if (a == null) {
                        a = bq.a(cast, "getCellLocationGemini", Integer.valueOf(1));
                    }
                    if (a == null) {
                        list = (List) bq.a(cast, "getAllCellInfo", new Object[0]);
                        a = a(list);
                        if (a == null) {
                        }
                    }
                    if (a != null) {
                        cellLocation = a;
                    }
                    return cellLocation;
                }
                if (a == null) {
                    a = bq.a(cast, str, Integer.valueOf(1));
                }
                if (a == null) {
                    a = bq.a(cast, "getCellLocationGemini", Integer.valueOf(1));
                }
                if (a == null) {
                    list = (List) bq.a(cast, "getAllCellInfo", new Object[0]);
                    a = a(list);
                    if (a == null) {
                    }
                }
            } else {
                a = null;
            }
            if (a != null) {
                cellLocation = a;
            }
        } catch (Exception e9) {
        }
        return cellLocation;
    }

    private Class<?> o() {
        String str;
        Class<?> cls = null;
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        switch (k()) {
            case 0:
                str = "android.telephony.TelephonyManager";
                break;
            case 1:
                str = "android.telephony.MSimTelephonyManager";
                break;
            case 2:
                str = "android.telephony.TelephonyManager2";
                break;
            default:
                str = cls;
                break;
        }
        try {
            cls = systemClassLoader.loadClass(str);
        } catch (Exception e) {
        }
        return cls;
    }

    private CellLocation a(List<?> list) {
        CellLocation cellLocation;
        CellLocation cellLocation2;
        Object obj;
        CellLocation cdmaCellLocation;
        int i;
        if (list == null || list.isEmpty()) {
            return null;
        }
        int i2;
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        int i3 = 0;
        CellLocation cellLocation3 = null;
        Object obj2 = null;
        CellLocation cellLocation4 = null;
        while (i3 < list.size()) {
            Object obj3 = list.get(i3);
            int i4;
            if (obj3 != null) {
                try {
                    Class loadClass = systemClassLoader.loadClass("android.telephony.CellInfoGsm");
                    Class loadClass2 = systemClassLoader.loadClass("android.telephony.CellInfoWcdma");
                    Class loadClass3 = systemClassLoader.loadClass("android.telephony.CellInfoLte");
                    Class loadClass4 = systemClassLoader.loadClass("android.telephony.CellInfoCdma");
                    i2 = !loadClass.isInstance(obj3) ? !loadClass2.isInstance(obj3) ? !loadClass3.isInstance(obj3) ? !loadClass4.isInstance(obj3) ? 0 : 4 : 3 : 2 : 1;
                    if (i2 <= 0) {
                        cellLocation = cellLocation4;
                        cellLocation2 = cellLocation3;
                        obj = i2;
                    } else {
                        obj2 = null;
                        if (i2 == 1) {
                            obj2 = loadClass.cast(obj3);
                        } else if (i2 == 2) {
                            obj2 = loadClass2.cast(obj3);
                        } else if (i2 == 3) {
                            obj2 = loadClass3.cast(obj3);
                        } else if (i2 == 4) {
                            obj2 = loadClass4.cast(obj3);
                        }
                        try {
                            obj3 = bq.a(obj2, "getCellIdentity", new Object[0]);
                            if (obj3 != null) {
                                if (i2 == 4) {
                                    cdmaCellLocation = new CdmaCellLocation();
                                    try {
                                        cdmaCellLocation.setCellLocationData(bq.b(obj3, "getBasestationId", new Object[0]), bq.b(obj3, "getLatitude", new Object[0]), bq.b(obj3, "getLongitude", new Object[0]), bq.b(obj3, "getSystemId", new Object[0]), bq.b(obj3, "getNetworkId", new Object[0]));
                                        cellLocation4 = cdmaCellLocation;
                                    } catch (Exception e) {
                                        cellLocation4 = cdmaCellLocation;
                                        i = i2;
                                    }
                                } else if (i2 != 3) {
                                    r3 = bq.b(obj3, "getLac", new Object[0]);
                                    r2 = bq.b(obj3, "getCid", new Object[0]);
                                    cdmaCellLocation = new GsmCellLocation();
                                    try {
                                        cdmaCellLocation.setLacAndCid(r3, r2);
                                        cellLocation3 = cdmaCellLocation;
                                    } catch (Exception e2) {
                                        cellLocation3 = cdmaCellLocation;
                                        i = i2;
                                    }
                                } else {
                                    r3 = bq.b(obj3, "getTac", new Object[0]);
                                    r2 = bq.b(obj3, "getCi", new Object[0]);
                                    cdmaCellLocation = new GsmCellLocation();
                                    cdmaCellLocation.setLacAndCid(r3, r2);
                                    cellLocation3 = cdmaCellLocation;
                                }
                                cdmaCellLocation = cellLocation3;
                                break;
                            }
                            cellLocation = cellLocation4;
                            cellLocation2 = cellLocation3;
                            i4 = i2;
                        } catch (Exception e3) {
                            i = i2;
                            cellLocation = cellLocation4;
                            cellLocation2 = cellLocation3;
                            obj = obj2;
                            i3++;
                            cellLocation3 = cellLocation2;
                            obj2 = obj;
                            cellLocation4 = cellLocation;
                        }
                    }
                } catch (Exception e4) {
                    cellLocation = cellLocation4;
                    cellLocation2 = cellLocation3;
                    obj = obj2;
                    i3++;
                    cellLocation3 = cellLocation2;
                    obj2 = obj;
                    cellLocation4 = cellLocation;
                }
            } else {
                cellLocation = cellLocation4;
                cellLocation2 = cellLocation3;
                i4 = obj2;
            }
            i3++;
            cellLocation3 = cellLocation2;
            obj2 = obj;
            cellLocation4 = cellLocation;
        }
        i2 = obj2;
        cdmaCellLocation = cellLocation3;
        if (i2 == 4) {
            cdmaCellLocation = cellLocation4;
        }
        return cdmaCellLocation;
    }

    private ba d(CellLocation cellLocation) {
        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
        ba baVar = new ba(1);
        String[] a = br.a(this.e);
        baVar.a = a[0];
        baVar.b = a[1];
        baVar.c = gsmCellLocation.getLac();
        baVar.d = gsmCellLocation.getCid();
        baVar.j = this.d;
        return baVar;
    }

    private ba b(NeighboringCellInfo neighboringCellInfo) {
        if (br.c() < 5) {
            return null;
        }
        try {
            ba baVar = new ba(1);
            String[] a = br.a(this.e);
            baVar.a = a[0];
            baVar.b = a[1];
            baVar.c = bq.b(neighboringCellInfo, "getLac", new Object[0]);
            baVar.d = neighboringCellInfo.getCid();
            baVar.j = br.a(neighboringCellInfo.getRssi());
            return baVar;
        } catch (Exception e) {
            return null;
        }
    }

    private void p() {
        Object obj = 1;
        JSONObject jSONObject = this.h;
        if (jSONObject != null) {
            try {
                if (jSONObject.getString("cellupdate").equals("0")) {
                    obj = null;
                }
            } catch (Exception e) {
            }
        }
        if (obj != null) {
            try {
                CellLocation.requestLocationUpdate();
            } catch (Exception e2) {
            }
            this.g = br.b();
        }
    }

    private void q() {
        int i = 0;
        this.i = new PhoneStateListener() {
            final /* synthetic */ bb a;

            {
                this.a = r1;
            }

            public void onCellLocationChanged(CellLocation cellLocation) {
                try {
                    if (this.a.a(cellLocation)) {
                        this.a.j = cellLocation;
                    }
                } catch (Throwable th) {
                }
            }

            public void onSignalStrengthChanged(int i) {
                int i2 = -113;
                try {
                    switch (this.a.b) {
                        case 1:
                            i2 = br.a(i);
                            break;
                        case 2:
                            i2 = br.a(i);
                            break;
                    }
                    this.a.a(i2);
                } catch (Throwable th) {
                }
            }

            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                if (signalStrength != null) {
                    int i = -113;
                    try {
                        switch (this.a.b) {
                            case 1:
                                i = br.a(signalStrength.getGsmSignalStrength());
                                break;
                            case 2:
                                i = signalStrength.getCdmaDbm();
                                break;
                        }
                        this.a.a(i);
                    } catch (Throwable th) {
                    }
                }
            }

            public void onServiceStateChanged(ServiceState serviceState) {
                try {
                    switch (serviceState.getState()) {
                        case 0:
                            this.a.p();
                        case 1:
                            this.a.r();
                        default:
                    }
                } catch (Throwable th) {
                }
            }
        };
        String str = "android.telephony.PhoneStateListener";
        String str2 = "";
        if (br.c() >= 7) {
            try {
                i = bq.b(str, "LISTEN_SIGNAL_STRENGTHS");
            } catch (Exception e) {
            }
        } else {
            try {
                i = bq.b(str, "LISTEN_SIGNAL_STRENGTH");
            } catch (Exception e2) {
            }
        }
        if (i != 0) {
            try {
                this.e.listen(this.i, i | 16);
            } catch (Exception e3) {
            }
        } else {
            this.e.listen(this.i, 16);
        }
        try {
            switch (k()) {
                case 0:
                    this.f = br.a(this.a, "phone2");
                case 1:
                    this.f = br.a(this.a, "phone_msim");
                case 2:
                    this.f = br.a(this.a, "phone2");
                default:
            }
        } catch (Throwable th) {
        }
    }

    private void a(int i) {
        if (i != -113) {
            this.d = i;
            switch (this.b) {
                case 1:
                case 2:
                    if (!this.c.isEmpty()) {
                        try {
                            ((ba) this.c.get(0)).j = this.d;
                            break;
                        } catch (Exception e) {
                            break;
                        }
                    }
                    break;
            }
            return;
        }
        this.d = -113;
    }

    private void r() {
        this.j = null;
        this.b = 9;
        this.c.clear();
    }

    public static int k() {
        int i = 0;
        try {
            Class.forName("android.telephony.MSimTelephonyManager");
            i = 1;
        } catch (Exception e) {
        }
        if (i != 0) {
            return i;
        }
        try {
            Class.forName("android.telephony.TelephonyManager2");
            return 2;
        } catch (Exception e2) {
            return i;
        }
    }
}
