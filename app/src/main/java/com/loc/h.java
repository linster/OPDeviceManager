package com.loc;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class h {
    private int b = 9;
    private int d = -113;
    private JSONObject kA;
    private PhoneStateListener kB;
    private CellLocation kC;
    private Context kv;
    private ArrayList kw = new ArrayList();
    private TelephonyManager kx;
    private Object ky;
    private long kz = 0;

    public h(Context context, JSONObject jSONObject) {
        if (context != null) {
            lb();
            this.kx = (TelephonyManager) bq.vQ(context, "phone");
            this.kA = jSONObject;
            this.kv = context;
        } else {
            lb();
            this.kx = (TelephonyManager) bq.vQ(context, "phone");
            this.kA = jSONObject;
            this.kv = context;
        }
        try {
            this.b = bq.vK(this.kx.getCellLocation(), context);
        } catch (Exception e) {
            this.b = 9;
        }
        lc();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void kS() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.kv;	 Catch:{ all -> 0x0035 }
        r0 = com.loc.bq.vN(r0);	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x001e;
    L_0x0009:
        r0 = r2.kC;	 Catch:{ all -> 0x0035 }
        r0 = r2.kP(r0);	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x003d;
    L_0x0011:
        r0 = r2.kC;	 Catch:{ all -> 0x0035 }
        r1 = r2.kv;	 Catch:{ all -> 0x0035 }
        r0 = com.loc.bq.vK(r0, r1);	 Catch:{ all -> 0x0035 }
        switch(r0) {
            case 1: goto L_0x003f;
            case 2: goto L_0x0045;
            default: goto L_0x001c;
        };
    L_0x001c:
        monitor-exit(r2);
        return;
    L_0x001e:
        r0 = r2.kx;	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x0009;
    L_0x0022:
        r0 = r2.kV();	 Catch:{ all -> 0x0035 }
        r1 = r2.kP(r0);	 Catch:{ all -> 0x0035 }
        if (r1 == 0) goto L_0x0038;
    L_0x002c:
        r1 = r2.kP(r0);	 Catch:{ all -> 0x0035 }
        if (r1 == 0) goto L_0x0009;
    L_0x0032:
        r2.kC = r0;	 Catch:{ all -> 0x0035 }
        goto L_0x0009;
    L_0x0035:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
    L_0x0038:
        r0 = r2.kW();	 Catch:{ all -> 0x0035 }
        goto L_0x002c;
    L_0x003d:
        monitor-exit(r2);
        return;
    L_0x003f:
        r0 = r2.kC;	 Catch:{ all -> 0x0035 }
        r2.kT(r0);	 Catch:{ all -> 0x0035 }
        goto L_0x001c;
    L_0x0045:
        r0 = r2.kC;	 Catch:{ all -> 0x0035 }
        r2.kU(r0);	 Catch:{ all -> 0x0035 }
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.h.kS():void");
    }

    private void kT(CellLocation cellLocation) {
        if (cellLocation != null && this.kx != null) {
            this.kw.clear();
            if (kP(cellLocation)) {
                this.b = 1;
                this.kw.add(kZ(cellLocation));
                List<NeighboringCellInfo> neighboringCellInfo = this.kx.getNeighboringCellInfo();
                if (neighboringCellInfo != null && !neighboringCellInfo.isEmpty()) {
                    for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                        if (kQ(neighboringCellInfo2)) {
                            aj la = la(neighboringCellInfo2);
                            if (!(la == null || this.kw.contains(la))) {
                                this.kw.add(la);
                            }
                        }
                    }
                }
            }
        }
    }

    private void kU(CellLocation cellLocation) {
        Object obj = null;
        if (cellLocation != null) {
            this.kw.clear();
            if (bq.vR() >= 5) {
                try {
                    if (this.ky != null) {
                        Object obj2;
                        try {
                            Field declaredField = cellLocation.getClass().getDeclaredField("mGsmCellLoc");
                            if (!declaredField.isAccessible()) {
                                declaredField.setAccessible(true);
                            }
                            GsmCellLocation gsmCellLocation = (GsmCellLocation) declaredField.get(cellLocation);
                            if (gsmCellLocation != null && kP(gsmCellLocation)) {
                                kT(gsmCellLocation);
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
                    if (kP(cellLocation)) {
                        this.b = 2;
                        String[] vJ = bq.vJ(this.kx);
                        aj ajVar = new aj(2);
                        ajVar.a = vJ[0];
                        ajVar.mS = vJ[1];
                        ajVar.mU = bJ.xF(cellLocation, "getSystemId", new Object[0]);
                        ajVar.mV = bJ.xF(cellLocation, "getNetworkId", new Object[0]);
                        ajVar.mW = bJ.xF(cellLocation, "getBaseStationId", new Object[0]);
                        ajVar.mX = this.d;
                        ajVar.e = bJ.xF(cellLocation, "getBaseStationLatitude", new Object[0]);
                        ajVar.mT = bJ.xF(cellLocation, "getBaseStationLongitude", new Object[0]);
                        if (ajVar.e >= 0) {
                            if (ajVar.mT >= 0) {
                                if (ajVar.e == Integer.MAX_VALUE) {
                                    ajVar.e = 0;
                                    ajVar.mT = 0;
                                } else if (ajVar.mT == Integer.MAX_VALUE) {
                                    ajVar.e = 0;
                                    ajVar.mT = 0;
                                } else if (ajVar.e == ajVar.mT && ajVar.e > 0) {
                                    ajVar.e = 0;
                                    ajVar.mT = 0;
                                } else {
                                    int i2 = 1;
                                }
                                if (obj == null) {
                                }
                                if (!this.kw.contains(ajVar)) {
                                    this.kw.add(ajVar);
                                }
                            }
                        }
                        ajVar.e = 0;
                        ajVar.mT = 0;
                        if (obj == null) {
                        }
                        if (this.kw.contains(ajVar)) {
                            this.kw.add(ajVar);
                        }
                    }
                } catch (Exception e2) {
                }
            }
        }
    }

    private CellLocation kV() {
        CellLocation cellLocation = null;
        TelephonyManager telephonyManager = this.kx;
        if (telephonyManager == null) {
            return cellLocation;
        }
        CellLocation cellLocation2;
        try {
            cellLocation2 = telephonyManager.getCellLocation();
        } catch (Exception e) {
            cellLocation2 = cellLocation;
        }
        if (kP(cellLocation2)) {
            return cellLocation2;
        }
        try {
            cellLocation2 = kY((List) bJ.xE(telephonyManager, "getAllCellInfo", new Object[0]));
        } catch (NoSuchMethodException e2) {
        } catch (Exception e3) {
        }
        if (kP(cellLocation2)) {
            return cellLocation2;
        }
        Object xE;
        try {
            xE = bJ.xE(telephonyManager, "getCellLocationExt", Integer.valueOf(1));
            if (xE != null) {
                cellLocation2 = (CellLocation) xE;
            }
        } catch (NoSuchMethodException e4) {
        } catch (Exception e5) {
        }
        if (kP(cellLocation2)) {
            return cellLocation2;
        }
        try {
            xE = bJ.xE(telephonyManager, "getCellLocationGemini", Integer.valueOf(1));
            if (xE != null) {
                cellLocation2 = (CellLocation) xE;
            }
        } catch (NoSuchMethodException e6) {
        } catch (Exception e7) {
        }
        return !kP(cellLocation2) ? cellLocation2 : cellLocation2;
    }

    private CellLocation kW() {
        CellLocation xE;
        List list;
        CellLocation cellLocation = null;
        Object obj = this.ky;
        if (obj == null) {
            return null;
        }
        try {
            Class kX = kX();
            if (kX.isInstance(obj)) {
                Object cast = kX.cast(obj);
                String str = "getCellLocation";
                try {
                    xE = bJ.xE(cast, str, new Object[0]);
                } catch (NoSuchMethodException e) {
                    xE = null;
                    if (xE == null) {
                        try {
                            xE = bJ.xE(cast, str, Integer.valueOf(1));
                        } catch (NoSuchMethodException e2) {
                        } catch (Exception e3) {
                        }
                    }
                    if (xE == null) {
                        try {
                            xE = bJ.xE(cast, "getCellLocationGemini", Integer.valueOf(1));
                        } catch (NoSuchMethodException e4) {
                        } catch (Exception e5) {
                        }
                    }
                    if (xE == null) {
                        try {
                            list = (List) bJ.xE(cast, "getAllCellInfo", new Object[0]);
                        } catch (NoSuchMethodException e6) {
                            list = null;
                            xE = kY(list);
                            if (xE == null) {
                            }
                            if (xE != null) {
                                cellLocation = xE;
                            }
                            return cellLocation;
                        } catch (Exception e7) {
                            list = null;
                            xE = kY(list);
                            if (xE == null) {
                            }
                            if (xE != null) {
                                cellLocation = xE;
                            }
                            return cellLocation;
                        }
                        xE = kY(list);
                        if (xE == null) {
                        }
                    }
                    if (xE != null) {
                        cellLocation = xE;
                    }
                    return cellLocation;
                } catch (Exception e8) {
                    xE = null;
                    if (xE == null) {
                        xE = bJ.xE(cast, str, Integer.valueOf(1));
                    }
                    if (xE == null) {
                        xE = bJ.xE(cast, "getCellLocationGemini", Integer.valueOf(1));
                    }
                    if (xE == null) {
                        list = (List) bJ.xE(cast, "getAllCellInfo", new Object[0]);
                        xE = kY(list);
                        if (xE == null) {
                        }
                    }
                    if (xE != null) {
                        cellLocation = xE;
                    }
                    return cellLocation;
                }
                if (xE == null) {
                    xE = bJ.xE(cast, str, Integer.valueOf(1));
                }
                if (xE == null) {
                    xE = bJ.xE(cast, "getCellLocationGemini", Integer.valueOf(1));
                }
                if (xE == null) {
                    list = (List) bJ.xE(cast, "getAllCellInfo", new Object[0]);
                    xE = kY(list);
                    if (xE == null) {
                    }
                }
            } else {
                xE = null;
            }
            if (xE != null) {
                cellLocation = xE;
            }
        } catch (Exception e9) {
        }
        return cellLocation;
    }

    private Class kX() {
        String str;
        Class cls = null;
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        switch (lf()) {
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

    private CellLocation kY(List list) {
        CellLocation cellLocation;
        CellLocation cellLocation2;
        Object obj;
        int i;
        if (list == null || list.isEmpty()) {
            return null;
        }
        int i2;
        CellLocation cdmaCellLocation;
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
                            obj3 = bJ.xE(obj2, "getCellIdentity", new Object[0]);
                            if (obj3 != null) {
                                if (i2 == 4) {
                                    cdmaCellLocation = new CdmaCellLocation();
                                    try {
                                        cdmaCellLocation.setCellLocationData(bJ.xF(obj3, "getBasestationId", new Object[0]), bJ.xF(obj3, "getLatitude", new Object[0]), bJ.xF(obj3, "getLongitude", new Object[0]), bJ.xF(obj3, "getSystemId", new Object[0]), bJ.xF(obj3, "getNetworkId", new Object[0]));
                                        cellLocation4 = cdmaCellLocation;
                                    } catch (Exception e) {
                                        cellLocation4 = cdmaCellLocation;
                                        i = i2;
                                    }
                                } else if (i2 != 3) {
                                    r3 = bJ.xF(obj3, "getLac", new Object[0]);
                                    r2 = bJ.xF(obj3, "getCid", new Object[0]);
                                    cdmaCellLocation = new GsmCellLocation();
                                    try {
                                        cdmaCellLocation.setLacAndCid(r3, r2);
                                        cellLocation3 = cdmaCellLocation;
                                    } catch (Exception e2) {
                                        cellLocation3 = cdmaCellLocation;
                                        i = i2;
                                    }
                                } else {
                                    r3 = bJ.xF(obj3, "getTac", new Object[0]);
                                    r2 = bJ.xF(obj3, "getCi", new Object[0]);
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

    private aj kZ(CellLocation cellLocation) {
        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
        aj ajVar = new aj(1);
        String[] vJ = bq.vJ(this.kx);
        ajVar.a = vJ[0];
        ajVar.mS = vJ[1];
        ajVar.c = gsmCellLocation.getLac();
        ajVar.d = gsmCellLocation.getCid();
        ajVar.mX = this.d;
        return ajVar;
    }

    private aj la(NeighboringCellInfo neighboringCellInfo) {
        if (bq.vR() < 5) {
            return null;
        }
        try {
            aj ajVar = new aj(1);
            String[] vJ = bq.vJ(this.kx);
            ajVar.a = vJ[0];
            ajVar.mS = vJ[1];
            ajVar.c = bJ.xF(neighboringCellInfo, "getLac", new Object[0]);
            ajVar.d = neighboringCellInfo.getCid();
            ajVar.mX = bq.vI(neighboringCellInfo.getRssi());
            return ajVar;
        } catch (Exception e) {
            return null;
        }
    }

    private void lb() {
        Object obj = 1;
        JSONObject jSONObject = this.kA;
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
            this.kz = bq.vM();
        }
    }

    private void lc() {
        int i = 0;
        this.kB = new C(this);
        String str = "android.telephony.PhoneStateListener";
        String str2 = "";
        if (bq.vR() >= 7) {
            try {
                i = bJ.xD(str, "LISTEN_SIGNAL_STRENGTHS");
            } catch (Exception e) {
            }
        } else {
            try {
                i = bJ.xD(str, "LISTEN_SIGNAL_STRENGTH");
            } catch (Exception e2) {
            }
        }
        if (i != 0) {
            try {
                this.kx.listen(this.kB, i | 16);
            } catch (Exception e3) {
            }
        } else {
            this.kx.listen(this.kB, 16);
        }
        try {
            switch (lf()) {
                case 0:
                    this.ky = bq.vQ(this.kv, "phone2");
                    return;
                case 1:
                    this.ky = bq.vQ(this.kv, "phone_msim");
                    return;
                case 2:
                    this.ky = bq.vQ(this.kv, "phone2");
                    return;
                default:
                    return;
            }
        } catch (Throwable th) {
        }
    }

    private void ld(int i) {
        if (i != -113) {
            this.d = i;
            switch (this.b) {
                case 1:
                case 2:
                    if (!this.kw.isEmpty()) {
                        try {
                            ((aj) this.kw.get(0)).mX = this.d;
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

    private void le() {
        this.kC = null;
        this.b = 9;
        this.kw.clear();
    }

    public static int lf() {
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

    public ArrayList kE() {
        return this.kw;
    }

    public aj kF() {
        ArrayList arrayList = this.kw;
        return arrayList.size() < 1 ? null : (aj) arrayList.get(0);
    }

    public int kG() {
        return this.b;
    }

    public CellLocation kH() {
        CellLocation cellLocation = null;
        if (this.kx == null) {
            return null;
        }
        try {
            cellLocation = this.kx.getCellLocation();
            if (kP(cellLocation)) {
                this.kC = cellLocation;
            }
        } catch (Exception e) {
        }
        return cellLocation;
    }

    public TelephonyManager kI() {
        return this.kx;
    }

    public void kJ() {
        kS();
    }

    public void kK() {
        le();
    }

    public void kL() {
        lb();
    }

    public void kM() {
        if (!(this.kx == null || this.kB == null)) {
            try {
                this.kx.listen(this.kB, 0);
            } catch (Exception e) {
            }
        }
        this.kw.clear();
        this.d = -113;
        this.kx = null;
        this.ky = null;
    }

    public void kN() {
        switch (this.b) {
            case 1:
                if (this.kw.isEmpty()) {
                    this.b = 9;
                    return;
                }
                return;
            case 2:
                if (this.kw.isEmpty()) {
                    this.b = 9;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean kO(boolean z) {
        if (z || this.kz == 0) {
            return false;
        }
        return ((bq.vM() - this.kz) > 30000 ? 1 : ((bq.vM() - this.kz) == 30000 ? 0 : -1)) >= 0;
    }

    public boolean kP(CellLocation cellLocation) {
        boolean z = false;
        if (cellLocation == null) {
            return false;
        }
        boolean z2 = true;
        switch (bq.vK(cellLocation, this.kv)) {
            case 1:
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                if (!(gsmCellLocation.getLac() == -1 || gsmCellLocation.getLac() == 0 || gsmCellLocation.getLac() > 65535 || gsmCellLocation.getCid() == -1 || gsmCellLocation.getCid() == 0 || gsmCellLocation.getCid() == 65535 || gsmCellLocation.getCid() >= 268435455)) {
                    z = true;
                    break;
                }
            case 2:
                try {
                    if (bJ.xF(cellLocation, "getSystemId", new Object[0]) <= 0) {
                        z2 = false;
                    } else if (bJ.xF(cellLocation, "getNetworkId", new Object[0]) < 0) {
                        z2 = false;
                    } else if (bJ.xF(cellLocation, "getBaseStationId", new Object[0]) < 0) {
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

    public boolean kQ(NeighboringCellInfo neighboringCellInfo) {
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

    public void kR(JSONObject jSONObject) {
        this.kA = jSONObject;
    }
}
