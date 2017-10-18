package com.loc;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class e {
    public static final StringBuilder jI = new StringBuilder();
    int e = -1;
    private Context iY = null;
    private ConnectivityManager iZ = null;
    private int[] jA = new int[]{0, 0, 0};
    private String jB = null;
    private String jC = null;
    private long jD = 0;
    private long jE = 0;
    private String jF = null;
    private aj jG = null;
    ba jH = null;
    aV jJ;
    boolean jK = false;
    private AmapLoc jL = null;
    private String jM = null;
    private Timer jN = null;
    private TimerTask jO = null;
    private String jP = null;
    private int jQ = 0;
    private int jR = 0;
    AmapLoc jS = null;
    private String jT = "-1";
    Object jU = new Object();
    public boolean jV = false;
    int jW = 12;
    boolean jX = false;
    boolean jY = true;
    L jZ = new L(this);
    private aQ ja = null;
    private h jb;
    private ArrayList jc = new ArrayList();
    private ArrayList jd = new ArrayList();
    private HashMap je = new HashMap();
    private bE jf = new bE();
    private WifiInfo jg = null;
    private JSONObject jh = null;
    private AmapLoc ji = null;
    private long jj = 0;
    private long jk = 0;
    private long jl = 0;
    private boolean jm = false;
    private boolean jn = false;
    private long jo = 0;
    private long jp = 0;
    private int jq = 0;
    private String jr = "00:00:00:00:00:00";
    private String js = null;
    private aS jt = null;
    private Timer ju = null;
    private TimerTask jv = null;
    private int jw = 0;
    private aF jx = null;
    private am jy = null;
    public boolean jz = false;

    private void iO() {
        try {
            this.ja = new aQ(this.iY, (WifiManager) bq.vQ(this.iY, "wifi"), this.jh);
            this.iZ = (ConnectivityManager) bq.vQ(this.iY, "connectivity");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter.addAction("android.location.GPS_FIX_CHANGE");
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.iY.registerReceiver(this.jf, intentFilter);
            jf();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void iP() {
        this.jb = new h(this.iY, this.jh);
        this.jb.kL();
    }

    private boolean iQ(long j) {
        if (bq.vM() - j >= 800) {
            return false;
        }
        long j2 = 0;
        if (bq.vH(this.ji)) {
            j2 = bq.vL() - this.ji.yU();
        }
        return (j2 > 10000 ? 1 : (j2 == 10000 ? 0 : -1)) <= 0;
    }

    private boolean iS(WifiInfo wifiInfo) {
        return (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getBSSID())) ? false : wifiInfo.getSSID() != null ? !wifiInfo.getBSSID().equals("00:00:00:00:00:00") ? !wifiInfo.getBSSID().contains(" :") ? !TextUtils.isEmpty(wifiInfo.getSSID()) : false : false : false;
    }

    private StringBuilder iU(Object obj) {
        StringBuilder stringBuilder = new StringBuilder();
        String str = "0";
        String str2 = "0";
        String str3 = "0";
        String str4 = "0";
        String str5 = "0";
        String str6 = H.lL;
        H.lE = "888888888888888";
        H.lF = "888888888888888";
        H.lG = "";
        int wa = bq.wa(-32768, 32767);
        String str7 = "";
        String str8 = "";
        String str9 = "";
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        CharSequence stringBuilder4 = new StringBuilder();
        h hVar = this.jb;
        int kG = hVar.kG();
        TelephonyManager kI = hVar.kI();
        ArrayList kE = hVar.kE();
        String str10 = kG != 2 ? str : "1";
        if (kI != null) {
            if (TextUtils.isEmpty(H.lE)) {
                H.lE = "888888888888888";
                try {
                    H.lE = bK.xT(this.iY);
                } catch (Exception e) {
                }
            } else if ("888888888888888".equals(H.lE)) {
                H.lE = "888888888888888";
                try {
                    H.lE = bK.xT(this.iY);
                } catch (Exception e2) {
                }
            }
            if (TextUtils.isEmpty(H.lE)) {
                H.lE = "888888888888888";
            }
            if (TextUtils.isEmpty(H.lF)) {
                H.lF = "888888888888888";
                try {
                    H.lF = kI.getSubscriberId();
                } catch (Exception e3) {
                }
            } else if ("888888888888888".equals(H.lF)) {
                H.lF = "888888888888888";
                try {
                    H.lF = kI.getSubscriberId();
                } catch (Exception e4) {
                }
            }
            if (TextUtils.isEmpty(H.lF)) {
                H.lF = "888888888888888";
            }
        }
        NetworkInfo networkInfo = null;
        try {
            networkInfo = this.iZ.getActiveNetworkInfo();
        } catch (Exception e5) {
        }
        if (bq.wf(networkInfo) == -1) {
            this.jg = null;
        } else {
            str8 = bq.wg(kI);
            if (jg()) {
                if (iS(this.jg)) {
                    str = "2";
                    if (jg()) {
                        je();
                        str7 = str8;
                        str8 = str;
                    } else {
                        str7 = str8;
                        str8 = str;
                    }
                }
            }
            str = "1";
            if (jg()) {
                je();
                str7 = str8;
                str8 = str;
            } else {
                str7 = str8;
                str8 = str;
            }
        }
        jw();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"");
        stringBuilder.append("GBK").append("\"?>");
        stringBuilder.append("<Cell_Req ver=\"3.0\"><HDR version=\"3.0\" cdma=\"");
        stringBuilder.append(str10);
        stringBuilder.append("\" gtype=\"").append(str2);
        if (str2.equals("1")) {
            stringBuilder.append("\" gmock=\"").append(!this.jn ? "0" : "1");
        }
        stringBuilder.append("\" glong=\"").append(str3);
        stringBuilder.append("\" glat=\"").append(str4);
        stringBuilder.append("\" precision=\"").append(str5);
        stringBuilder.append("\"><src>").append(H.lH);
        stringBuilder.append("</src><license>").append(H.lI);
        stringBuilder.append("</license><key>").append(str6);
        stringBuilder.append("</key><clientid>").append(H.lK);
        stringBuilder.append("</clientid><imei>").append(H.lE);
        stringBuilder.append("</imei><imsi>").append(H.lF);
        stringBuilder.append("</imsi><reqid>").append(wa);
        stringBuilder.append("</reqid><smac>").append(this.jr);
        stringBuilder.append("</smac><sdkv>").append(iN());
        stringBuilder.append("</sdkv><corv>").append(jx());
        stringBuilder.append("</corv><poiid>").append(this.js);
        stringBuilder.append("</poiid></HDR><DRR phnum=\"").append(H.lG);
        stringBuilder.append("\" nettype=\"").append(str7);
        stringBuilder.append("\" inftype=\"").append(str8).append("\">");
        if (!kE.isEmpty()) {
            StringBuilder stringBuilder5 = new StringBuilder();
            aj ajVar;
            switch (kG) {
                case 1:
                    jK();
                    ajVar = (aj) kE.get(0);
                    stringBuilder5.delete(0, stringBuilder5.length());
                    stringBuilder5.append("<mcc>").append(ajVar.a).append("</mcc>");
                    stringBuilder5.append("<mnc>").append(ajVar.mS).append("</mnc>");
                    stringBuilder5.append("<lac>").append(ajVar.c).append("</lac>");
                    stringBuilder5.append("<cellid>").append(ajVar.d);
                    stringBuilder5.append("</cellid>");
                    stringBuilder5.append("<signal>").append(ajVar.mX);
                    stringBuilder5.append("</signal>");
                    String stringBuilder6 = stringBuilder5.toString();
                    for (int i = 1; i < kE.size(); i++) {
                        ajVar = (aj) kE.get(i);
                        stringBuilder2.append(ajVar.c).append(",");
                        stringBuilder2.append(ajVar.d).append(",");
                        stringBuilder2.append(ajVar.mX);
                        if (i < kE.size() - 1) {
                            stringBuilder2.append("*");
                        }
                    }
                    str = stringBuilder6;
                    break;
                case 2:
                    ajVar = (aj) kE.get(0);
                    stringBuilder5.delete(0, stringBuilder5.length());
                    stringBuilder5.append("<mcc>").append(ajVar.a).append("</mcc>");
                    stringBuilder5.append("<sid>").append(ajVar.mU).append("</sid>");
                    stringBuilder5.append("<nid>").append(ajVar.mV).append("</nid>");
                    stringBuilder5.append("<bid>").append(ajVar.mW).append("</bid>");
                    if (ajVar.mT > 0 && ajVar.e > 0) {
                        this.jQ = ajVar.mT;
                        this.jR = ajVar.e;
                        stringBuilder5.append("<lon>").append(ajVar.mT).append("</lon>");
                        stringBuilder5.append("<lat>").append(ajVar.e).append("</lat>");
                    } else {
                        jK();
                    }
                    stringBuilder5.append("<signal>").append(ajVar.mX).append("</signal>");
                    str = stringBuilder5.toString();
                    break;
                default:
                    jK();
                    str = str9;
                    break;
            }
            stringBuilder5.delete(0, stringBuilder5.length());
            str9 = str;
        }
        if (jg()) {
            int i2;
            if (iS(this.jg)) {
                stringBuilder4.append(this.jg.getBSSID()).append(",");
                int rssi = this.jg.getRssi();
                if (rssi < -128) {
                    rssi = 0;
                } else if (rssi > 127) {
                    rssi = 0;
                }
                stringBuilder4.append(rssi).append(",");
                str = this.jg.getSSID();
                i2 = 32;
                try {
                    i2 = this.jg.getSSID().getBytes("UTF-8").length;
                } catch (Exception e6) {
                }
                if (i2 >= 32) {
                    str = "unkwn";
                }
                stringBuilder4.append(str.replace("*", "."));
            }
            List list = this.jc;
            int min = Math.min(list.size(), 15);
            for (i2 = 0; i2 < min; i2++) {
                ScanResult scanResult = (ScanResult) list.get(i2);
                stringBuilder3.append(scanResult.BSSID).append(",");
                stringBuilder3.append(scanResult.level).append(",");
                stringBuilder3.append(scanResult.SSID).append("*");
            }
        } else {
            je();
        }
        stringBuilder.append(str9);
        stringBuilder.append(String.format(Locale.US, "<nb>%s</nb>", new Object[]{stringBuilder2}));
        if (stringBuilder3.length() != 0) {
            stringBuilder3.deleteCharAt(stringBuilder3.length() - 1);
            stringBuilder.append("<macs>");
            stringBuilder.append(String.format(Locale.US, "<![CDATA[%s]]>", new Object[]{stringBuilder3}));
            stringBuilder.append("</macs>");
            stringBuilder.append("<macsage>").append(bq.vM() - this.jp);
            stringBuilder.append("</macsage>");
        } else {
            stringBuilder3.append(stringBuilder4);
            stringBuilder.append("<macs>");
            stringBuilder.append(String.format(Locale.US, "<![CDATA[%s]]>", new Object[]{stringBuilder4}));
            stringBuilder.append("</macs>");
        }
        stringBuilder.append("<mmac>");
        stringBuilder.append(String.format(Locale.US, "<![CDATA[%s]]>", new Object[]{stringBuilder4}));
        stringBuilder.append("</mmac>").append("</DRR></Cell_Req>");
        jc(stringBuilder);
        Object obj2 = null;
        if (bq.wb(this.jh, "reversegeo")) {
            try {
                if (this.jh.getString("reversegeo").equals("1")) {
                    obj2 = 1;
                }
            } catch (Exception e7) {
            }
        }
        if (obj2 != null) {
            this.jt.b = (short) 0;
        } else {
            this.jt.b = (short) 2;
        }
        if (bq.wb(this.jh, "multi")) {
            try {
                if (this.jh.getString("multi").equals("1")) {
                    this.jt.b = (short) 1;
                }
            } catch (Exception e8) {
            }
        }
        this.jt.qd = H.lH;
        this.jt.qe = H.lI;
        this.jt.qg = bq.vV();
        this.jt.qh = "android" + bq.vW();
        if (TextUtils.isEmpty(H.lN)) {
            H.lN = bq.vX(this.iY);
        }
        this.jt.qi = H.lN;
        this.jt.qj = str10;
        this.jt.qk = str2;
        this.jt.ql = !this.jn ? "0" : "1";
        this.jt.qm = str3;
        this.jt.qn = str4;
        this.jt.qo = str5;
        this.jt.qp = str6;
        this.jt.qq = H.lE;
        this.jt.qr = H.lF;
        this.jt.qt = String.valueOf(wa);
        this.jt.qu = this.jr;
        this.jt.qw = iN();
        this.jt.qx = jx();
        this.jt.qG = this.js;
        this.jt.qv = H.lG;
        this.jt.qy = str7;
        this.jt.qz = str8;
        this.jt.qA = String.valueOf(kG);
        this.jt.qB = str9;
        this.jt.qC = stringBuilder2.toString();
        this.jt.qE = stringBuilder3.toString();
        this.jt.qF = String.valueOf(bq.vM() - this.jp);
        this.jt.qD = stringBuilder4.toString();
        stringBuilder2.delete(0, stringBuilder2.length());
        stringBuilder3.delete(0, stringBuilder3.length());
        stringBuilder4.delete(0, stringBuilder4.length());
        return stringBuilder;
    }

    private void iV() {
        Object obj = 1;
        long vM = bq.vM();
        if (ja()) {
            List list = this.jd;
            if ((vM - this.jk < 10000 ? 1 : null) == null) {
                synchronized (this.jU) {
                    list.clear();
                }
            }
            jf();
            if (vM - this.jk >= 10000) {
                obj = null;
            }
            if (obj == null) {
                for (int i = 20; i > 0 && list.isEmpty(); i--) {
                    try {
                        Thread.sleep(150);
                    } catch (Exception e) {
                    }
                }
            }
            synchronized (this.jU) {
            }
            if (list.isEmpty() && this.ja != null) {
                Collection rM = this.ja.rM();
                if (rM != null) {
                    list.addAll(rM);
                }
            }
        }
    }

    private boolean iY(int i) {
        int i2 = 20;
        try {
            i2 = WifiManager.calculateSignalLevel(i, 20);
        } catch (ArithmeticException e) {
        }
        return i2 >= 1;
    }

    private void jA(SharedPreferences sharedPreferences) {
        int i = 0;
        if (this.iY != null) {
            SharedPreferences sharedPreferences2 = this.iY.getSharedPreferences("pref", 0);
            if (sharedPreferences2 != null && sharedPreferences2.contains("coluphist")) {
                try {
                    String[] split = aG.ro(sharedPreferences2.getString("coluphist", null).getBytes("UTF-8")).split(",");
                    while (i < 3) {
                        this.jA[i] = Integer.parseInt(split[i]);
                        i++;
                    }
                } catch (Exception e) {
                    sharedPreferences2.edit().remove("coluphist").commit();
                }
            }
        }
    }

    private void jB() {
        if (this.iY != null && this.jA[0] != 0) {
            SharedPreferences sharedPreferences = this.iY.getSharedPreferences("pref", 0);
            if (sharedPreferences != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int append : this.jA) {
                    stringBuilder.append(append).append(",");
                }
                try {
                    stringBuilder.deleteCharAt(this.jA.length - 1);
                    sharedPreferences.edit().putString("coluphist", aG.ro(stringBuilder.toString().getBytes("UTF-8")));
                } catch (Exception e) {
                }
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
    }

    private AmapLoc jE() {
        AmapLoc amapLoc;
        aj ajVar = null;
        if (jI.length() > 0) {
            jI.delete(0, jI.length());
        }
        try {
            if (!this.jm) {
                this.jb.kJ();
                this.jb.kH();
                ajVar = this.jb.kF();
            }
            ArrayList arrayList = this.jc;
            if (arrayList != null && arrayList.isEmpty()) {
                this.jp = bq.vM();
                Collection rM = this.ja.rM();
                if (rM != null) {
                    arrayList.addAll(rM);
                }
            }
            iX();
        } catch (Throwable th) {
        }
        String iR = iR(false);
        if (TextUtils.isEmpty(iR)) {
            amapLoc = new AmapLoc();
            amapLoc.yz(this.jW);
            amapLoc.yD(jI.toString());
        } else {
            String str;
            boolean z;
            try {
                String string;
                str = "";
                if (bq.wb(this.jh, "reversegeo")) {
                    string = this.jh.getString("reversegeo");
                    z = string.equals("1");
                } else {
                    string = str;
                    z = false;
                }
                try {
                    if (!this.jT.equals(string)) {
                        this.jT = string;
                        this.jF = null;
                        this.jj = 0;
                        aI.rp().rq();
                    }
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                z = false;
            }
            str = !z ? iR : iR + "#reversegeo";
            StringBuilder iT = iT(true);
            AmapLoc sd = aT.sb().sd(str, iT);
            if (bq.vH(sd)) {
                sd.yy(4);
                this.ji = sd;
                jF();
                return sd;
            }
            sd = jb(iZ(), false, true);
            if (bq.vH(sd)) {
                sd.yX("new");
                this.jF = iT.toString();
                this.jG = ajVar;
                this.jj = bq.vM();
                this.ji = sd;
                aT.sb().sc(str, iT, this.ji, this.iY, true);
                jF();
                amapLoc = sd;
            } else {
                amapLoc = jG(iR, iT.toString());
                if (!bq.vH(amapLoc)) {
                    return sd;
                }
                this.jF = iT.toString();
                amapLoc.yX("file");
                amapLoc.yy(8);
                amapLoc.yD("离线定位结果，在线定位失败原因:" + sd.yC());
                this.ji = amapLoc;
            }
        }
        return amapLoc;
    }

    private void jF() {
        this.jL = null;
        this.jM = null;
    }

    private AmapLoc jG(String str, String str2) {
        int i = 0;
        if (!f.kn()) {
            return null;
        }
        if (str != null && str.equals(this.jM) && this.jL != null) {
            return this.jL;
        }
        jH();
        ArrayList tv = bb.ts().tv();
        try {
            int i2;
            AmapLoc jJ;
            if (az.pG()) {
                ArrayList pF = az.pF(str, false);
                if (pF != null) {
                    int size = pF.size();
                    for (i2 = 0; i2 < size; i2++) {
                        String str3 = (String) pF.get(i2);
                        jJ = jJ(str, str2, null, str3.substring(str3.lastIndexOf(File.separator) + 1, str3.length()), 0);
                        if (bq.vH(jJ)) {
                            this.jM = str;
                            this.jL = jJ;
                            return jJ;
                        }
                    }
                }
            }
            i2 = tv.size();
            if (i2 != 0) {
                while (i < i2) {
                    jJ = jJ(str, str2, null, ((af) tv.get(i)).of(), 0);
                    if (bq.vH(jJ)) {
                        this.jM = str;
                        this.jL = jJ;
                        return jJ;
                    }
                    i++;
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }

    private void jH() {
        if (!f.kn()) {
            jI();
        } else if (az.nW[1] > 2000) {
            jI();
        } else if (this.jN == null || this.jO == null) {
            this.jO = new ad(this);
            this.jN = new Timer("T-O", false);
            this.jN.schedule(this.jO, 0, 60000);
        }
    }

    private void jI() {
        if (this.jO != null) {
            this.jO.cancel();
            this.jO = null;
        }
        if (this.jN != null) {
            this.jN.cancel();
            this.jN.purge();
            this.jN = null;
        }
    }

    private AmapLoc jJ(String str, String str2, double[] dArr, String str3, int i) {
        if (!bq.ww()) {
            return null;
        }
        double[] dArr2;
        if (TextUtils.isEmpty(str3)) {
            if (dArr == null) {
                dArr = jM();
            }
            if (dArr[0] == 0.0d || dArr[1] == 0.0d) {
                return null;
            }
            dArr2 = dArr;
        } else {
            dArr2 = dArr;
        }
        bq.vM();
        String str4 = str3;
        String str5 = str;
        String str6 = str2;
        int i2 = i;
        AmapLoc ps = az.ps(dArr2, str4, str5, str6, i2, this.iY, new int[]{this.jR, this.jQ});
        bq.vM();
        return ps;
    }

    private void jK() {
        this.jQ = 0;
        this.jR = 0;
    }

    private void jL() {
        if (this.iY != null && az.nW[0] != 0) {
            SharedPreferences sharedPreferences = this.iY.getSharedPreferences("pref", 0);
            if (sharedPreferences != null) {
                StringBuilder stringBuilder = new StringBuilder();
                String str = "activityoffdl";
                for (int append : az.nW) {
                    stringBuilder.append(append).append(",");
                }
                try {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    sharedPreferences.edit().putString(str, bq.wn(stringBuilder.toString())).commit();
                } catch (Exception e) {
                }
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
    }

    private double[] jM() {
        double[] dArr = new double[2];
        if (bq.vH(this.ji)) {
            dArr[0] = this.ji.yJ();
            dArr[1] = this.ji.yG();
        } else if (bq.vH(this.jS)) {
            dArr[0] = this.jS.yJ();
            dArr[1] = this.jS.yG();
        } else {
            dArr[0] = 0.0d;
            dArr[1] = 0.0d;
        }
        return dArr;
    }

    private void jN(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            String str = "activityoffdl";
            if (sharedPreferences.contains(str)) {
                try {
                    String[] split = bq.wp(sharedPreferences.getString(str, null)).split(",");
                    for (int i = 0; i < 2; i++) {
                        az.nW[i] = Integer.parseInt(split[i]);
                    }
                } catch (Exception e) {
                    sharedPreferences.edit().remove(str).commit();
                }
            }
        }
    }

    private boolean ja() {
        boolean z = true;
        if (!TextUtils.isEmpty(this.js)) {
            return true;
        }
        if (!jg()) {
            z = false;
        } else if (this.jo != 0) {
            if (bq.vM() - this.jo >= 3000) {
                if (!(bq.vM() - this.jp >= 1500)) {
                    z = false;
                }
            } else {
                z = false;
            }
        }
        return z;
    }

    private AmapLoc jb(String str, boolean z, boolean z2) {
        AmapLoc amapLoc;
        if (this.iY == null) {
            jI.append("context is null");
            amapLoc = new AmapLoc();
            amapLoc.yz(1);
            amapLoc.yD(jI.toString());
            return amapLoc;
        } else if (str == null || str.length() == 0) {
            amapLoc = new AmapLoc();
            amapLoc.yz(3);
            amapLoc.yD(jI.toString());
            return amapLoc;
        } else {
            amapLoc = new AmapLoc();
            M m = new M();
            try {
                byte[] tn = this.jH.tn(this.iY, this.jh, this.jt, H.mP());
                if (tn != null) {
                    bq.vL();
                    String str2 = new String(tn, "UTF-8");
                    if (str2.contains("\"status\":\"0\"")) {
                        return m.ni(str2);
                    }
                    String yv = bQ.yv(tn);
                    if (yv != null) {
                        str2 = "";
                        ba.tq(this.jh);
                        amapLoc = m.nh(yv);
                        if (bq.vH(amapLoc)) {
                            if (amapLoc.zE() == null) {
                            }
                            if (amapLoc.yw() == 0 && amapLoc.yx() == 0) {
                                if ("-5".equals(amapLoc.yY()) || "1".equals(amapLoc.yY()) || "2".equals(amapLoc.yY()) || "14".equals(amapLoc.yY()) || "24".equals(amapLoc.yY()) || "-1".equals(amapLoc.yY())) {
                                    amapLoc.yy(5);
                                } else {
                                    amapLoc.yy(6);
                                }
                                amapLoc.yD(amapLoc.yY());
                            }
                            return amapLoc;
                        } else if (amapLoc == null) {
                            amapLoc = new AmapLoc();
                            amapLoc.yz(6);
                            jI.append("location is null");
                            amapLoc.yD(jI.toString());
                            return amapLoc;
                        } else {
                            this.jB = amapLoc.za();
                            amapLoc.yz(6);
                            jI.append("location faile retype:" + amapLoc.yY() + " rdesc:" + this.jB);
                            amapLoc.yD(jI.toString());
                            return amapLoc;
                        }
                    }
                    amapLoc = new AmapLoc();
                    amapLoc.yz(5);
                    jI.append("decrypt response data error");
                    amapLoc.yD(jI.toString());
                    return amapLoc;
                }
                amapLoc = new AmapLoc();
                amapLoc.yz(4);
                jI.append("please check the network");
                amapLoc.yD(jI.toString());
                return amapLoc;
            } catch (Throwable th) {
                th.printStackTrace();
                amapLoc = new AmapLoc();
                amapLoc.yz(4);
                jI.append("please check the network");
                amapLoc.yD(jI.toString());
                return amapLoc;
            }
        }
    }

    private void jc(StringBuilder stringBuilder) {
        int i = 0;
        if (stringBuilder != null) {
            String[] strArr = new String[]{" phnum=\"\"", " nettype=\"\"", " nettype=\"UNKWN\"", " inftype=\"\"", "<macs><![CDATA[]]></macs>", "<nb></nb>", "<mmac><![CDATA[]]></mmac>", " gtype=\"0\"", " gmock=\"0\"", " glong=\"0.0\"", " glat=\"0.0\"", " precision=\"0.0\"", " glong=\"0\"", " glat=\"0\"", " precision=\"0\"", "<smac>null</smac>", "<smac>00:00:00:00:00:00</smac>", "<imei>000000000000000</imei>", "<imsi>000000000000000</imsi>", "<mcc>000</mcc>", "<mcc>0</mcc>", "<lac>0</lac>", "<cellid>0</cellid>", "<key></key>", "<poiid></poiid>", "<poiid>null</poiid>"};
            int length = strArr.length;
            while (i < length) {
                String str = strArr[i];
                while (stringBuilder.indexOf(str) != -1) {
                    int indexOf = stringBuilder.indexOf(str);
                    stringBuilder.delete(indexOf, str.length() + indexOf);
                }
                i++;
            }
            while (stringBuilder.indexOf("*<") != -1) {
                stringBuilder.deleteCharAt(stringBuilder.indexOf("*<"));
            }
        }
    }

    private boolean jd() {
        boolean z = true;
        if (this.jj == 0) {
            return true;
        }
        if (bq.vM() - this.jj <= 20000) {
            z = false;
        }
        return z;
    }

    private void je() {
        this.jp = 0;
        this.jc.clear();
        this.jg = null;
        synchronized (this.jU) {
            this.jd.clear();
            this.je.clear();
        }
    }

    private void jf() {
        boolean z = false;
        if (jg()) {
            boolean z2;
            if (bq.vR() >= 18 || bq.vR() <= 3) {
                z2 = false;
            } else if (bq.wb(this.jh, "wifiactivescan")) {
                try {
                    z2 = "1".equals(this.jh.getString("wifiactivescan"));
                } catch (Exception e) {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (z2) {
                try {
                    z = this.ja.rQ();
                    if (z) {
                        this.jo = bq.vM();
                    }
                } catch (Exception e2) {
                }
            }
            if (!z) {
                try {
                    if (this.ja.rP()) {
                        this.jo = bq.vM();
                    }
                } catch (Exception e3) {
                }
            }
        }
    }

    private boolean jg() {
        return this.ja == null ? false : this.ja.rS();
    }

    private boolean jh() {
        return this.jx != null;
    }

    private boolean ji() {
        boolean z = false;
        try {
            if (jh()) {
                z = this.jx.qt();
            }
        } catch (Exception e) {
        }
        return z;
    }

    private void jj() {
        int i;
        if (bq.wb(this.jh, "coll")) {
            try {
                i = !this.jh.getString("coll").equals("0") ? 1 : 0;
            } catch (Exception e) {
                i = 1;
            }
        } else {
            i = 1;
        }
        if (i == 0) {
            jl();
        } else if (ji()) {
            bq.vB("col|al start");
        } else {
            try {
                this.jx.qy(H.lP * 1000);
                jt();
                jk();
                this.jx.qq();
            } catch (Exception e2) {
            }
        }
    }

    private void jk() {
        if (!jh()) {
            return;
        }
        if (!jh() || this.jx.qx() <= 0) {
            try {
                if (jh()) {
                    if (!this.jx.qw()) {
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void jl() {
        if (ji()) {
            H.lP = 20;
            try {
                this.jx.qs();
            } catch (Exception e) {
            }
            return;
        }
        bq.vB("col|⊗ need stop");
    }

    private void jm(SharedPreferences sharedPreferences) {
        String str = null;
        if (this.iY != null && sharedPreferences != null) {
            String str2 = "smac";
            if (sharedPreferences.contains(str2)) {
                try {
                    str = aG.ro(sharedPreferences.getString(str2, null).getBytes("UTF-8"));
                } catch (Exception e) {
                    sharedPreferences.edit().remove(str2).commit();
                }
            }
            if (!(TextUtils.isEmpty(str) || str.equals("00:00:00:00:00:00"))) {
                this.jr = str;
            }
        }
    }

    private void jn() {
        Object obj = null;
        if (this.iY != null && !TextUtils.isEmpty(this.jr)) {
            SharedPreferences sharedPreferences = this.iY.getSharedPreferences("pref", 0);
            try {
                obj = aG.ro(this.jr.getBytes("UTF-8"));
            } catch (Exception e) {
            }
            if (!TextUtils.isEmpty(obj)) {
                sharedPreferences.edit().putString("smac", obj).commit();
            }
        }
    }

    private void jo() {
        H.lH = "";
        H.lI = "";
        H.lK = "";
    }

    private void jp() {
        Object obj = null;
        List list = this.jd;
        try {
            if (bq.wb(this.jh, "wait1stwifi")) {
                obj = this.jh.getString("wait1stwifi");
            }
            if (TextUtils.isEmpty(obj) || !obj.equals("1")) {
                return;
            }
        } catch (Exception e) {
        }
        synchronized (this.jU) {
            list.clear();
        }
        jf();
        for (int i = 20; i > 0 && list.isEmpty(); i--) {
            try {
                Thread.sleep(150);
            } catch (Exception e2) {
            }
        }
        synchronized (this.jU) {
        }
        if (list.isEmpty() && this.ja != null) {
            list.addAll(this.ja.rM());
        }
    }

    private void jq(int i) {
        int i2 = 70254591;
        if (jh()) {
            try {
                jt();
                switch (i) {
                    case 1:
                        i2 = 674234367;
                        break;
                    case 2:
                        if (!jv()) {
                            i2 = 674234367;
                            break;
                        } else {
                            i2 = 2083520511;
                            break;
                        }
                }
                this.jx.qv(null, ju(1, i2, 1));
                this.jy = this.jx.qu();
                if (this.jy != null) {
                    byte[] oz = this.jy.oz();
                    Object to = this.jH.to(oz, this.iY, "http://cgicol.amap.com/collection/writedata?ver=v1.0_ali&", false);
                    if (jh()) {
                        if (!TextUtils.isEmpty(to) && to.equals("true")) {
                            this.jx.qv(this.jy, ju(1, i2, 1));
                            String wt = bq.wt(0, "yyyyMMdd");
                            if (wt.equals(String.valueOf(this.jA[0]))) {
                                int[] iArr = this.jA;
                                iArr[1] = oz.length + iArr[1];
                            } else {
                                try {
                                    this.jA[0] = Integer.parseInt(wt);
                                } catch (Exception e) {
                                    this.jA[0] = 0;
                                    this.jA[1] = 0;
                                    this.jA[2] = 0;
                                }
                                this.jA[1] = oz.length;
                            }
                            this.jA[2] = this.jA[2] + 1;
                            jB();
                        } else {
                            this.jw++;
                            this.jx.qv(this.jy, ju(1, i2, 0));
                        }
                    }
                }
                jk();
                if (jh() && this.jx.qx() == 0) {
                    js();
                }
                if (this.jw >= 3) {
                    js();
                }
            } catch (Exception e2) {
            }
        }
    }

    private void jr(int i) {
        jk();
        if (!(bq.vM() - this.jl >= 45000)) {
            return;
        }
        if (!jh() || this.jx.qx() >= 20) {
            if (this.jv == null) {
                this.jv = new ab(this, i);
            }
            if (this.ju == null) {
                this.ju = new Timer("T-U", false);
                this.ju.schedule(this.jv, 2000, 2000);
            }
        }
    }

    private void js() {
        if (this.jv != null) {
            this.jv.cancel();
            this.jv = null;
        }
        if (this.ju != null) {
            this.ju.cancel();
            this.ju.purge();
            this.ju = null;
        }
    }

    private void jt() {
        if (jh()) {
            try {
                this.jx.qp(768);
            } catch (Exception e) {
            }
        }
    }

    private String ju(int i, int i2, int i3) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("e", i);
        jSONObject.put("d", i2);
        jSONObject.put("u", i3);
        return jSONObject.toString();
    }

    private boolean jv() {
        return (this.ja == null || this.iZ == null) ? false : this.ja.rR(this.iZ);
    }

    private void jw() {
        if (bq.wb(this.jh, "poiid")) {
            try {
                String string = this.jh.getString("poiid");
                if (TextUtils.isEmpty(string)) {
                    this.js = null;
                    return;
                } else if (string.length() <= 32) {
                    this.js = string;
                    return;
                } else {
                    this.js = null;
                    return;
                }
            } catch (Exception e) {
                return;
            }
        }
        this.js = null;
    }

    private String jx() {
        String str = null;
        try {
            str = aF.qz("version");
        } catch (Exception e) {
        }
        return str;
    }

    private void jy() {
        if (this.ja != null && this.iY != null && this.jz) {
            this.ja.rT(this.jz);
        }
    }

    private boolean jz() {
        if (this.iY == null) {
            jI.append("context is null");
            return false;
        } else if (TextUtils.isEmpty(H.lH)) {
            jI.append("src is null");
            return false;
        } else if (!TextUtils.isEmpty(H.lI)) {
            return true;
        } else {
            jI.append("license is null");
            return false;
        }
    }

    public synchronized void iF(Context context) {
        if (context != null) {
            if (TextUtils.isEmpty(H.lN)) {
                H.lN = bq.vX(context);
            }
            if (this.iY == null) {
                this.iY = context.getApplicationContext();
                this.jH = ba.tm(context);
                iO();
                iP();
                H.lQ = true;
                this.jt = new aS();
                try {
                    this.jx = aF.qo(this.iY);
                } catch (Exception e) {
                }
                this.jl = bq.vM();
                this.jb.kH();
                bq.vL();
                aT.sb().sm(context);
                bb.ts().tx(context);
                this.jV = true;
            }
        }
    }

    public void iG(String str) {
        if (!TextUtils.isEmpty(str) && str.contains("##")) {
            String[] split = str.split("##");
            if (split.length == 4) {
                H.lH = split[0];
                H.lI = split[1];
                H.lK = split[2];
                H.lL = split[3];
                return;
            }
            jo();
            return;
        }
        jo();
    }

    public void iH(JSONObject jSONObject) {
        this.jh = jSONObject;
        if (bq.wb(jSONObject, "collwifiscan")) {
            try {
                Object string = jSONObject.getString("collwifiscan");
                if (TextUtils.isEmpty(string)) {
                    H.lP = 20;
                } else {
                    H.lP = Integer.parseInt(string) / 1000;
                }
                if (ji()) {
                    this.jx.qy(H.lP * 1000);
                }
            } catch (Exception e) {
            }
        }
        if (this.jb != null) {
            this.jb.kR(jSONObject);
        }
        if (this.ja != null) {
            this.ja.rL(jSONObject);
        }
    }

    public synchronized AmapLoc iI(boolean z) {
        if (jI.length() > 0) {
            jI.delete(0, jI.length());
        }
        AmapLoc amapLoc;
        if (jz()) {
            this.jq++;
            bq.vL();
            this.jm = bq.vN(this.iY);
            if (z) {
                return jE();
            }
            if (this.jq == 2) {
                jk();
                jy();
                if (this.iY != null) {
                    SharedPreferences sharedPreferences = this.iY.getSharedPreferences("pref", 0);
                    jA(sharedPreferences);
                    jN(sharedPreferences);
                    jm(sharedPreferences);
                }
                jH();
            }
            if (this.jq == 1 && jg()) {
                if (this.jd.isEmpty()) {
                    this.jp = bq.vM();
                    Collection rM = this.ja.rM();
                    synchronized (this.jU) {
                        if (!(this.jd == null || rM == null)) {
                            this.jd.addAll(rM);
                        }
                    }
                }
                jp();
            }
            Object obj = null;
            String str = "";
            try {
                if (bq.wb(this.jh, "reversegeo")) {
                    str = this.jh.getString("reversegeo");
                    obj = !str.equals("1") ? null : 1;
                }
            } catch (Exception e) {
            }
            Object obj2 = obj;
            if (!this.jT.equals(str)) {
                this.jT = str;
                this.jF = null;
                this.jj = 0;
                aI.rp().rq();
            }
            if (iQ(this.jj) && bq.vH(this.ji)) {
                this.ji.yy(2);
                return this.ji;
            }
            this.jb.kJ();
            if (!z) {
                iV();
                this.jk = bq.vM();
            }
            try {
                iW();
                iX();
            } catch (Throwable th) {
            }
            String iR = iR(false);
            if (TextUtils.isEmpty(iR)) {
                if (f.kx()) {
                    if (!this.jK) {
                        jC();
                    }
                    for (int i = 4; i > 0 && this.e != 0; i--) {
                        SystemClock.sleep(500);
                    }
                    if (this.e == 0) {
                        this.ji = this.jJ.sI();
                        if (this.ji != null) {
                            return this.ji;
                        }
                    }
                }
                amapLoc = new AmapLoc();
                amapLoc.yz(this.jW);
                amapLoc.yD(jI.toString());
                return amapLoc;
            }
            Object obj3;
            boolean jd;
            Object obj4;
            Object obj5;
            boolean sl;
            String str2;
            AmapLoc amapLoc2;
            AmapLoc amapLoc3;
            String str3 = "";
            StringBuilder iT = iT(false);
            aj ajVar = null;
            if (!this.jm) {
                ajVar = this.jb.kF();
            }
            if (!(ajVar == null && this.jG == null)) {
                if (this.jG == null || !this.jG.ou(ajVar)) {
                    obj3 = 1;
                    jd = jd();
                    if (this.ji != null) {
                        obj4 = null;
                    } else {
                        obj5 = (this.ji.yN() > 299.0f || this.jc.size() <= 5) ? null : 1;
                        obj4 = obj5;
                    }
                    if (this.ji != null && this.jF != null && obj4 == null && r1 == null) {
                        sl = aT.sb().sl(this.jF, iT);
                        if (!sl) {
                            if (this.jE != 0) {
                                if ((bq.vM() - this.jE < 3000 ? 1 : null) == null) {
                                }
                            }
                        }
                        if (this.jb.kO(this.jm)) {
                            this.jb.kL();
                        }
                        if (bq.vH(this.ji)) {
                            this.ji.yX("mem");
                            this.ji.yy(2);
                            return this.ji;
                        }
                    }
                    sl = false;
                    if (sl) {
                        this.jE = bq.vM();
                    } else {
                        this.jE = 0;
                    }
                    if (this.jC != null && !iR.equals(this.jC)) {
                        if ((bq.vL() - this.jD < 3000 ? 1 : null) != null) {
                            str2 = this.jC;
                        } else {
                            this.jD = bq.vL();
                            this.jC = iR;
                            str2 = iR;
                        }
                    } else if (this.jC == null) {
                        this.jD = bq.vL();
                        str2 = iR;
                    } else {
                        this.jD = bq.vL();
                        this.jC = iR;
                        str2 = iR;
                    }
                    amapLoc2 = null;
                    str = obj2 == null ? str2 : str2 + "#reversegeo";
                    if (obj4 == null && !jd) {
                        amapLoc2 = aT.sb().sd(str, iT);
                    }
                    if ((jd && !bq.vH(amapLoc2)) || obj4 != null) {
                        this.ji = jb(iZ(), false, false);
                        if (bq.vH(this.ji)) {
                            this.ji.yX("new");
                            this.jF = iT.toString();
                            this.jG = ajVar;
                            this.jj = bq.vM();
                            jF();
                        }
                    } else if (jd) {
                        this.ji = jb(iZ(), false, false);
                        if (bq.vH(this.ji)) {
                            this.jF = iT.toString();
                            this.jG = ajVar;
                            this.jj = bq.vM();
                            jF();
                        }
                    } else {
                        amapLoc2.yy(4);
                        this.ji = amapLoc2;
                        jF();
                    }
                    aT.sb().sc(str, iT, this.ji, this.iY, true);
                    bb.ts().tu(this.iY, str2, this.ji);
                    if (!bq.vH(this.ji)) {
                        amapLoc = jG(str2, iT.toString());
                        if (bq.vH(amapLoc)) {
                            this.jF = iT.toString();
                            amapLoc3 = this.ji;
                            this.ji = amapLoc;
                            this.ji.yy(8);
                            this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                        }
                    }
                    iT.delete(0, iT.length());
                    return this.ji;
                }
            }
            obj3 = null;
            jd = jd();
            if (this.ji != null) {
                if (this.ji.yN() > 299.0f) {
                }
                obj4 = obj5;
            } else {
                obj4 = null;
            }
            if (this.ji != null) {
                sl = aT.sb().sl(this.jF, iT);
                if (sl) {
                    if (this.jE != 0) {
                        if (bq.vM() - this.jE < 3000) {
                        }
                        if ((bq.vM() - this.jE < 3000 ? 1 : null) == null) {
                        }
                    }
                    if (sl) {
                        this.jE = bq.vM();
                    } else {
                        this.jE = 0;
                    }
                    if (this.jC != null) {
                        if (bq.vL() - this.jD < 3000) {
                        }
                        if ((bq.vL() - this.jD < 3000 ? 1 : null) != null) {
                            this.jD = bq.vL();
                            this.jC = iR;
                            str2 = iR;
                        } else {
                            str2 = this.jC;
                        }
                        amapLoc2 = null;
                        if (obj2 == null) {
                        }
                        amapLoc2 = aT.sb().sd(str, iT);
                        if (!jd) {
                            this.ji = jb(iZ(), false, false);
                            if (bq.vH(this.ji)) {
                                this.ji.yX("new");
                                this.jF = iT.toString();
                                this.jG = ajVar;
                                this.jj = bq.vM();
                                jF();
                            }
                            aT.sb().sc(str, iT, this.ji, this.iY, true);
                            bb.ts().tu(this.iY, str2, this.ji);
                            if (bq.vH(this.ji)) {
                                amapLoc = jG(str2, iT.toString());
                                if (bq.vH(amapLoc)) {
                                    this.jF = iT.toString();
                                    amapLoc3 = this.ji;
                                    this.ji = amapLoc;
                                    this.ji.yy(8);
                                    this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                                }
                            }
                            iT.delete(0, iT.length());
                            return this.ji;
                        }
                        this.ji = jb(iZ(), false, false);
                        if (bq.vH(this.ji)) {
                            this.ji.yX("new");
                            this.jF = iT.toString();
                            this.jG = ajVar;
                            this.jj = bq.vM();
                            jF();
                        }
                        aT.sb().sc(str, iT, this.ji, this.iY, true);
                        bb.ts().tu(this.iY, str2, this.ji);
                        if (bq.vH(this.ji)) {
                            amapLoc = jG(str2, iT.toString());
                            if (bq.vH(amapLoc)) {
                                this.jF = iT.toString();
                                amapLoc3 = this.ji;
                                this.ji = amapLoc;
                                this.ji.yy(8);
                                this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                            }
                        }
                        iT.delete(0, iT.length());
                        return this.ji;
                    }
                    if (this.jC == null) {
                        this.jD = bq.vL();
                        this.jC = iR;
                        str2 = iR;
                    } else {
                        this.jD = bq.vL();
                        str2 = iR;
                    }
                    amapLoc2 = null;
                    if (obj2 == null) {
                    }
                    amapLoc2 = aT.sb().sd(str, iT);
                    if (jd) {
                        this.ji = jb(iZ(), false, false);
                        if (bq.vH(this.ji)) {
                            this.ji.yX("new");
                            this.jF = iT.toString();
                            this.jG = ajVar;
                            this.jj = bq.vM();
                            jF();
                        }
                        aT.sb().sc(str, iT, this.ji, this.iY, true);
                        bb.ts().tu(this.iY, str2, this.ji);
                        if (bq.vH(this.ji)) {
                            amapLoc = jG(str2, iT.toString());
                            if (bq.vH(amapLoc)) {
                                this.jF = iT.toString();
                                amapLoc3 = this.ji;
                                this.ji = amapLoc;
                                this.ji.yy(8);
                                this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                            }
                        }
                        iT.delete(0, iT.length());
                        return this.ji;
                    }
                    this.ji = jb(iZ(), false, false);
                    if (bq.vH(this.ji)) {
                        this.ji.yX("new");
                        this.jF = iT.toString();
                        this.jG = ajVar;
                        this.jj = bq.vM();
                        jF();
                    }
                    aT.sb().sc(str, iT, this.ji, this.iY, true);
                    bb.ts().tu(this.iY, str2, this.ji);
                    if (bq.vH(this.ji)) {
                        amapLoc = jG(str2, iT.toString());
                        if (bq.vH(amapLoc)) {
                            this.jF = iT.toString();
                            amapLoc3 = this.ji;
                            this.ji = amapLoc;
                            this.ji.yy(8);
                            this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                        }
                    }
                    iT.delete(0, iT.length());
                    return this.ji;
                }
                if (this.jb.kO(this.jm)) {
                    this.jb.kL();
                }
                if (bq.vH(this.ji)) {
                    this.ji.yX("mem");
                    this.ji.yy(2);
                    return this.ji;
                }
                if (sl) {
                    this.jE = 0;
                } else {
                    this.jE = bq.vM();
                }
                if (this.jC != null) {
                    if (bq.vL() - this.jD < 3000) {
                    }
                    if ((bq.vL() - this.jD < 3000 ? 1 : null) != null) {
                        str2 = this.jC;
                    } else {
                        this.jD = bq.vL();
                        this.jC = iR;
                        str2 = iR;
                    }
                    amapLoc2 = null;
                    if (obj2 == null) {
                    }
                    amapLoc2 = aT.sb().sd(str, iT);
                    if (jd) {
                        this.ji = jb(iZ(), false, false);
                        if (bq.vH(this.ji)) {
                            this.ji.yX("new");
                            this.jF = iT.toString();
                            this.jG = ajVar;
                            this.jj = bq.vM();
                            jF();
                        }
                        aT.sb().sc(str, iT, this.ji, this.iY, true);
                        bb.ts().tu(this.iY, str2, this.ji);
                        if (bq.vH(this.ji)) {
                            amapLoc = jG(str2, iT.toString());
                            if (bq.vH(amapLoc)) {
                                this.jF = iT.toString();
                                amapLoc3 = this.ji;
                                this.ji = amapLoc;
                                this.ji.yy(8);
                                this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                            }
                        }
                        iT.delete(0, iT.length());
                        return this.ji;
                    }
                    this.ji = jb(iZ(), false, false);
                    if (bq.vH(this.ji)) {
                        this.ji.yX("new");
                        this.jF = iT.toString();
                        this.jG = ajVar;
                        this.jj = bq.vM();
                        jF();
                    }
                    aT.sb().sc(str, iT, this.ji, this.iY, true);
                    bb.ts().tu(this.iY, str2, this.ji);
                    if (bq.vH(this.ji)) {
                        amapLoc = jG(str2, iT.toString());
                        if (bq.vH(amapLoc)) {
                            this.jF = iT.toString();
                            amapLoc3 = this.ji;
                            this.ji = amapLoc;
                            this.ji.yy(8);
                            this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                        }
                    }
                    iT.delete(0, iT.length());
                    return this.ji;
                }
                if (this.jC == null) {
                    this.jD = bq.vL();
                    str2 = iR;
                } else {
                    this.jD = bq.vL();
                    this.jC = iR;
                    str2 = iR;
                }
                amapLoc2 = null;
                if (obj2 == null) {
                }
                amapLoc2 = aT.sb().sd(str, iT);
                if (jd) {
                    this.ji = jb(iZ(), false, false);
                    if (bq.vH(this.ji)) {
                        this.ji.yX("new");
                        this.jF = iT.toString();
                        this.jG = ajVar;
                        this.jj = bq.vM();
                        jF();
                    }
                    aT.sb().sc(str, iT, this.ji, this.iY, true);
                    bb.ts().tu(this.iY, str2, this.ji);
                    if (bq.vH(this.ji)) {
                        amapLoc = jG(str2, iT.toString());
                        if (bq.vH(amapLoc)) {
                            this.jF = iT.toString();
                            amapLoc3 = this.ji;
                            this.ji = amapLoc;
                            this.ji.yy(8);
                            this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                        }
                    }
                    iT.delete(0, iT.length());
                    return this.ji;
                }
                this.ji = jb(iZ(), false, false);
                if (bq.vH(this.ji)) {
                    this.ji.yX("new");
                    this.jF = iT.toString();
                    this.jG = ajVar;
                    this.jj = bq.vM();
                    jF();
                }
                aT.sb().sc(str, iT, this.ji, this.iY, true);
                bb.ts().tu(this.iY, str2, this.ji);
                if (bq.vH(this.ji)) {
                    amapLoc = jG(str2, iT.toString());
                    if (bq.vH(amapLoc)) {
                        this.jF = iT.toString();
                        amapLoc3 = this.ji;
                        this.ji = amapLoc;
                        this.ji.yy(8);
                        this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                    }
                }
                iT.delete(0, iT.length());
                return this.ji;
            }
            sl = false;
            if (sl) {
                this.jE = bq.vM();
            } else {
                this.jE = 0;
            }
            if (this.jC != null) {
                if (bq.vL() - this.jD < 3000) {
                }
                if ((bq.vL() - this.jD < 3000 ? 1 : null) != null) {
                    this.jD = bq.vL();
                    this.jC = iR;
                    str2 = iR;
                } else {
                    str2 = this.jC;
                }
                amapLoc2 = null;
                if (obj2 == null) {
                }
                amapLoc2 = aT.sb().sd(str, iT);
                if (jd) {
                    this.ji = jb(iZ(), false, false);
                    if (bq.vH(this.ji)) {
                        this.ji.yX("new");
                        this.jF = iT.toString();
                        this.jG = ajVar;
                        this.jj = bq.vM();
                        jF();
                    }
                    aT.sb().sc(str, iT, this.ji, this.iY, true);
                    bb.ts().tu(this.iY, str2, this.ji);
                    if (bq.vH(this.ji)) {
                        amapLoc = jG(str2, iT.toString());
                        if (bq.vH(amapLoc)) {
                            this.jF = iT.toString();
                            amapLoc3 = this.ji;
                            this.ji = amapLoc;
                            this.ji.yy(8);
                            this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                        }
                    }
                    iT.delete(0, iT.length());
                    return this.ji;
                }
                this.ji = jb(iZ(), false, false);
                if (bq.vH(this.ji)) {
                    this.ji.yX("new");
                    this.jF = iT.toString();
                    this.jG = ajVar;
                    this.jj = bq.vM();
                    jF();
                }
                aT.sb().sc(str, iT, this.ji, this.iY, true);
                bb.ts().tu(this.iY, str2, this.ji);
                if (bq.vH(this.ji)) {
                    amapLoc = jG(str2, iT.toString());
                    if (bq.vH(amapLoc)) {
                        this.jF = iT.toString();
                        amapLoc3 = this.ji;
                        this.ji = amapLoc;
                        this.ji.yy(8);
                        this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                    }
                }
                iT.delete(0, iT.length());
                return this.ji;
            }
            if (this.jC == null) {
                this.jD = bq.vL();
                this.jC = iR;
                str2 = iR;
            } else {
                this.jD = bq.vL();
                str2 = iR;
            }
            amapLoc2 = null;
            if (obj2 == null) {
            }
            amapLoc2 = aT.sb().sd(str, iT);
            if (jd) {
                this.ji = jb(iZ(), false, false);
                if (bq.vH(this.ji)) {
                    this.ji.yX("new");
                    this.jF = iT.toString();
                    this.jG = ajVar;
                    this.jj = bq.vM();
                    jF();
                }
                aT.sb().sc(str, iT, this.ji, this.iY, true);
                bb.ts().tu(this.iY, str2, this.ji);
                if (bq.vH(this.ji)) {
                    amapLoc = jG(str2, iT.toString());
                    if (bq.vH(amapLoc)) {
                        this.jF = iT.toString();
                        amapLoc3 = this.ji;
                        this.ji = amapLoc;
                        this.ji.yy(8);
                        this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                    }
                }
                iT.delete(0, iT.length());
                return this.ji;
            }
            this.ji = jb(iZ(), false, false);
            if (bq.vH(this.ji)) {
                this.ji.yX("new");
                this.jF = iT.toString();
                this.jG = ajVar;
                this.jj = bq.vM();
                jF();
            }
            aT.sb().sc(str, iT, this.ji, this.iY, true);
            bb.ts().tu(this.iY, str2, this.ji);
            if (bq.vH(this.ji)) {
                amapLoc = jG(str2, iT.toString());
                if (bq.vH(amapLoc)) {
                    this.jF = iT.toString();
                    amapLoc3 = this.ji;
                    this.ji = amapLoc;
                    this.ji.yy(8);
                    this.ji.yD("离线定位，在线定位失败原因:" + amapLoc3.yC());
                }
            }
            iT.delete(0, iT.length());
            return this.ji;
        }
        amapLoc = new AmapLoc();
        amapLoc.yz(1);
        amapLoc.yD(jI.toString());
        return amapLoc;
    }

    public void iJ() {
        Object obj = 1;
        if (f.kf()) {
            if (bq.vM() - f.kh() < f.kg()) {
                obj = null;
            }
            if (obj == null && this.ji != null) {
                if (this.ji.yx() == 2 || this.ji.yx() == 4) {
                    try {
                        iR(false);
                        iT(true);
                        jb(iZ(), false, true);
                    } catch (Throwable e) {
                        bq.vC(e);
                    }
                }
            }
        }
    }

    public int iK(boolean z, int i) {
        if (z) {
            jr(i);
        } else {
            js();
        }
        return !jh() ? -1 : this.jx.qx();
    }

    public AmapLoc iL(AmapLoc amapLoc, String... strArr) {
        return (strArr == null || strArr.length == 0) ? aI.rp().rr(amapLoc) : !strArr[0].equals("shake") ? !strArr[0].equals("fusion") ? amapLoc : aI.rp().rt(amapLoc) : aI.rp().rr(amapLoc);
    }

    public synchronized void iM() {
        this.jV = false;
        H.lQ = false;
        jl();
        this.jx = null;
        this.jy = null;
        this.jF = null;
        jF();
        if (this.jJ != null) {
            this.jJ.sE();
            this.jJ = null;
            this.jK = false;
            this.e = -1;
        }
        js();
        try {
            S.nu().nx(this.iY, 1);
        } catch (Exception e) {
        }
        aI.rp().rq();
        bq.wd();
        try {
            if (this.iY != null) {
                this.iY.unregisterReceiver(this.jf);
            }
            this.jf = null;
        } catch (Exception e2) {
            this.jf = null;
        } catch (Throwable th) {
            this.jf = null;
        }
        if (this.jb != null) {
            this.jb.kM();
        }
        aT.sb().sg();
        bb.ts().ty();
        az.pA();
        jI();
        this.jj = 0;
        this.jD = 0;
        je();
        this.ji = null;
        this.iY = null;
    }

    public String iN() {
        return "2.2.0";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String iR(boolean r9) {
        /*
        r8 = this;
        r3 = 1;
        r2 = 0;
        monitor-enter(r8);
        r0 = r8.jm;	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x0041;
    L_0x0007:
        r0 = r8.jb;	 Catch:{ all -> 0x0047 }
        r0.kN();	 Catch:{ all -> 0x0047 }
    L_0x000c:
        r0 = "";
        r1 = "";
        r4 = "network";
        r1 = r8.jg();	 Catch:{ all -> 0x0047 }
        if (r1 != 0) goto L_0x004a;
    L_0x001b:
        r8.je();	 Catch:{ all -> 0x0047 }
    L_0x001e:
        r1 = "";
        r1 = r8.jb;	 Catch:{ all -> 0x0047 }
        r1 = r1.kG();	 Catch:{ all -> 0x0047 }
        r5 = r8.jb;	 Catch:{ all -> 0x0047 }
        r5 = r5.kE();	 Catch:{ all -> 0x0047 }
        r6 = r8.jc;	 Catch:{ all -> 0x0047 }
        if (r5 != 0) goto L_0x0053;
    L_0x0031:
        if (r6 != 0) goto L_0x0070;
    L_0x0033:
        r1 = jI;	 Catch:{ all -> 0x0047 }
        r2 = "⊗ lstCgi & ⊗ wifis";
        r1.append(r2);	 Catch:{ all -> 0x0047 }
        r1 = 12;
        r8.jW = r1;	 Catch:{ all -> 0x0047 }
        monitor-exit(r8);
        return r0;
    L_0x0041:
        r0 = r8.jb;	 Catch:{ all -> 0x0047 }
        r0.kK();	 Catch:{ all -> 0x0047 }
        goto L_0x000c;
    L_0x0047:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
    L_0x004a:
        r1 = r8.ja;	 Catch:{ all -> 0x0047 }
        r1 = r1.rN();	 Catch:{ all -> 0x0047 }
        r8.jg = r1;	 Catch:{ all -> 0x0047 }
        goto L_0x001e;
    L_0x0053:
        r7 = r5.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r7 != 0) goto L_0x0031;
    L_0x0059:
        switch(r1) {
            case 1: goto L_0x0077;
            case 2: goto L_0x00e0;
            case 9: goto L_0x0156;
            default: goto L_0x005c;
        };	 Catch:{ all -> 0x0047 }
    L_0x005c:
        r1 = 11;
        r8.jW = r1;	 Catch:{ all -> 0x0047 }
        r1 = jI;	 Catch:{ all -> 0x0047 }
        r2 = "get cgi failure";
        r1.append(r2);	 Catch:{ all -> 0x0047 }
    L_0x0068:
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0047 }
        if (r1 == 0) goto L_0x01ff;
    L_0x006e:
        monitor-exit(r8);
        return r0;
    L_0x0070:
        r7 = r6.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r7 != 0) goto L_0x0033;
    L_0x0076:
        goto L_0x0059;
    L_0x0077:
        r1 = r5.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r1 != 0) goto L_0x0068;
    L_0x007d:
        r0 = 0;
        r0 = r5.get(r0);	 Catch:{ all -> 0x0047 }
        r0 = (com.loc.aj) r0;	 Catch:{ all -> 0x0047 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0047 }
        r1.<init>();	 Catch:{ all -> 0x0047 }
        r2 = r0.a;	 Catch:{ all -> 0x0047 }
        r2 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r3 = "#";
        r2.append(r3);	 Catch:{ all -> 0x0047 }
        r2 = r0.mS;	 Catch:{ all -> 0x0047 }
        r2 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r3 = "#";
        r2.append(r3);	 Catch:{ all -> 0x0047 }
        r2 = r0.c;	 Catch:{ all -> 0x0047 }
        r2 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r3 = "#";
        r2.append(r3);	 Catch:{ all -> 0x0047 }
        r0 = r0.d;	 Catch:{ all -> 0x0047 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0047 }
        r2 = "#";
        r0.append(r2);	 Catch:{ all -> 0x0047 }
        r0 = r1.append(r4);	 Catch:{ all -> 0x0047 }
        r2 = "#";
        r0.append(r2);	 Catch:{ all -> 0x0047 }
        r0 = r6.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x00d4;
    L_0x00c9:
        r0 = "cgiwifi";
    L_0x00cc:
        r1.append(r0);	 Catch:{ all -> 0x0047 }
        r0 = r1.toString();	 Catch:{ all -> 0x0047 }
        goto L_0x0068;
    L_0x00d4:
        r0 = r8.jg;	 Catch:{ all -> 0x0047 }
        r0 = r8.iS(r0);	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x00c9;
    L_0x00dc:
        r0 = "cgi";
        goto L_0x00cc;
    L_0x00e0:
        r1 = r5.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r1 != 0) goto L_0x0068;
    L_0x00e6:
        r0 = 0;
        r0 = r5.get(r0);	 Catch:{ all -> 0x0047 }
        r0 = (com.loc.aj) r0;	 Catch:{ all -> 0x0047 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0047 }
        r1.<init>();	 Catch:{ all -> 0x0047 }
        r2 = r0.a;	 Catch:{ all -> 0x0047 }
        r2 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r3 = "#";
        r2.append(r3);	 Catch:{ all -> 0x0047 }
        r2 = r0.mS;	 Catch:{ all -> 0x0047 }
        r2 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r3 = "#";
        r2.append(r3);	 Catch:{ all -> 0x0047 }
        r2 = r0.mU;	 Catch:{ all -> 0x0047 }
        r2 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r3 = "#";
        r2.append(r3);	 Catch:{ all -> 0x0047 }
        r2 = r0.mV;	 Catch:{ all -> 0x0047 }
        r2 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r3 = "#";
        r2.append(r3);	 Catch:{ all -> 0x0047 }
        r0 = r0.mW;	 Catch:{ all -> 0x0047 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0047 }
        r2 = "#";
        r0.append(r2);	 Catch:{ all -> 0x0047 }
        r0 = r1.append(r4);	 Catch:{ all -> 0x0047 }
        r2 = "#";
        r0.append(r2);	 Catch:{ all -> 0x0047 }
        r0 = r6.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x014a;
    L_0x013e:
        r0 = "cgiwifi";
    L_0x0141:
        r1.append(r0);	 Catch:{ all -> 0x0047 }
        r0 = r1.toString();	 Catch:{ all -> 0x0047 }
        goto L_0x0068;
    L_0x014a:
        r0 = r8.jg;	 Catch:{ all -> 0x0047 }
        r0 = r8.iS(r0);	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x013e;
    L_0x0152:
        r0 = "cgi";
        goto L_0x0141;
    L_0x0156:
        r0 = r6.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x0189;
    L_0x015c:
        r1 = r3;
    L_0x015d:
        if (r9 == 0) goto L_0x0193;
    L_0x015f:
        r0 = java.util.Locale.US;	 Catch:{ all -> 0x0047 }
        r2 = "#%s#";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0047 }
        r5 = 0;
        r3[r5] = r4;	 Catch:{ all -> 0x0047 }
        r0 = java.lang.String.format(r0, r2, r3);	 Catch:{ all -> 0x0047 }
        if (r1 != 0) goto L_0x01e9;
    L_0x0170:
        r1 = "network";
        r1 = r4.equals(r1);	 Catch:{ all -> 0x0047 }
        if (r1 == 0) goto L_0x0068;
    L_0x0179:
        r0 = "";
        r1 = 2;
        r8.jW = r1;	 Catch:{ all -> 0x0047 }
        r1 = jI;	 Catch:{ all -> 0x0047 }
        r2 = "is network & no wifi";
        r1.append(r2);	 Catch:{ all -> 0x0047 }
        goto L_0x0068;
    L_0x0189:
        r0 = r8.jg;	 Catch:{ all -> 0x0047 }
        r0 = r8.iS(r0);	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x015c;
    L_0x0191:
        r1 = r2;
        goto L_0x015d;
    L_0x0193:
        r0 = r8.jg;	 Catch:{ all -> 0x0047 }
        r0 = r8.iS(r0);	 Catch:{ all -> 0x0047 }
        if (r0 != 0) goto L_0x01cb;
    L_0x019b:
        r0 = r6.size();	 Catch:{ all -> 0x0047 }
        if (r0 != r3) goto L_0x015f;
    L_0x01a1:
        r0 = 2;
        r8.jW = r0;	 Catch:{ all -> 0x0047 }
        r0 = r8.jg;	 Catch:{ all -> 0x0047 }
        r0 = r8.iS(r0);	 Catch:{ all -> 0x0047 }
        if (r0 == 0) goto L_0x01de;
    L_0x01ac:
        r0 = 0;
        r0 = r6.get(r0);	 Catch:{ all -> 0x0047 }
        r0 = (android.net.wifi.ScanResult) r0;	 Catch:{ all -> 0x0047 }
        r0 = r0.BSSID;	 Catch:{ all -> 0x0047 }
        r3 = r8.jg;	 Catch:{ all -> 0x0047 }
        r3 = r3.getBSSID();	 Catch:{ all -> 0x0047 }
        r0 = r3.equals(r0);	 Catch:{ all -> 0x0047 }
        if (r0 == 0) goto L_0x015f;
    L_0x01c1:
        r0 = jI;	 Catch:{ all -> 0x0047 }
        r1 = "same access wifi & around wifi 1";
        r0.append(r1);	 Catch:{ all -> 0x0047 }
        r1 = r2;
        goto L_0x015f;
    L_0x01cb:
        r0 = r6.isEmpty();	 Catch:{ all -> 0x0047 }
        if (r0 == 0) goto L_0x019b;
    L_0x01d1:
        r0 = 2;
        r8.jW = r0;	 Catch:{ all -> 0x0047 }
        r0 = jI;	 Catch:{ all -> 0x0047 }
        r1 = "⊗ around wifi(s) & has access wifi";
        r0.append(r1);	 Catch:{ all -> 0x0047 }
        r1 = r2;
        goto L_0x015f;
    L_0x01de:
        r0 = jI;	 Catch:{ all -> 0x0047 }
        r1 = "⊗ access wifi & around wifi 1";
        r0.append(r1);	 Catch:{ all -> 0x0047 }
        r1 = r2;
        goto L_0x015f;
    L_0x01e9:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0047 }
        r1.<init>();	 Catch:{ all -> 0x0047 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0047 }
        r1 = "wifi";
        r0 = r0.append(r1);	 Catch:{ all -> 0x0047 }
        r0 = r0.toString();	 Catch:{ all -> 0x0047 }
        goto L_0x0068;
    L_0x01ff:
        r1 = "#";
        r1 = r0.startsWith(r1);	 Catch:{ all -> 0x0047 }
        if (r1 == 0) goto L_0x021f;
    L_0x0208:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0047 }
        r1.<init>();	 Catch:{ all -> 0x0047 }
        r2 = com.loc.bq.we();	 Catch:{ all -> 0x0047 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0047 }
        r0 = r0.toString();	 Catch:{ all -> 0x0047 }
        goto L_0x006e;
    L_0x021f:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0047 }
        r1.<init>();	 Catch:{ all -> 0x0047 }
        r2 = "#";
        r1 = r1.append(r2);	 Catch:{ all -> 0x0047 }
        r0 = r1.append(r0);	 Catch:{ all -> 0x0047 }
        r0 = r0.toString();	 Catch:{ all -> 0x0047 }
        goto L_0x0208;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.e.iR(boolean):java.lang.String");
    }

    public synchronized StringBuilder iT(boolean z) {
        StringBuilder stringBuilder;
        Object obj = null;
        synchronized (this) {
            h hVar = this.jb;
            if (this.jm) {
                hVar.kK();
            }
            stringBuilder = new StringBuilder(700);
            int kG = hVar.kG();
            ArrayList kE = hVar.kE();
            switch (kG) {
                case 1:
                    for (kG = 1; kG < kE.size(); kG++) {
                        stringBuilder.append("#").append(((aj) kE.get(kG)).mS);
                        stringBuilder.append("|").append(((aj) kE.get(kG)).c);
                        stringBuilder.append("|").append(((aj) kE.get(kG)).d);
                    }
                    break;
            }
            if (((!z && TextUtils.isEmpty(this.jr)) || this.jr.equals("00:00:00:00:00:00")) && this.jg != null) {
                this.jr = this.jg.getMacAddress();
                jn();
                if (TextUtils.isEmpty(this.jr)) {
                    this.jr = "00:00:00:00:00:00";
                }
            }
            if (jg()) {
                Object obj2;
                String str = "";
                if (iS(this.jg)) {
                    String bssid = this.jg.getBSSID();
                } else {
                    obj2 = str;
                }
                List list = this.jc;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    str = "nb";
                    if (obj2.equals(((ScanResult) list.get(i)).BSSID)) {
                        str = "access";
                        int i2 = 1;
                    }
                    stringBuilder.append(String.format(Locale.US, "#%s,%s", new Object[]{((ScanResult) list.get(i)).BSSID, str}));
                }
                if (obj == null && !TextUtils.isEmpty(obj2)) {
                    stringBuilder.append("#").append(obj2);
                    stringBuilder.append(",access");
                }
            } else {
                je();
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(0);
            }
        }
        return stringBuilder;
    }

    public synchronized void iW() {
        List list = this.jc;
        Collection collection = this.jd;
        list.clear();
        synchronized (this.jU) {
            list.addAll(collection);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void iX() {
        /*
        r10 = this;
        r3 = 32;
        r2 = 1;
        r1 = 0;
        monitor-enter(r10);
        r0 = r10.jc;	 Catch:{ all -> 0x00d9 }
        if (r0 != 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r10);
        return;
    L_0x000b:
        r0 = r10.jc;	 Catch:{ all -> 0x00d9 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00d9 }
        if (r0 != 0) goto L_0x0009;
    L_0x0013:
        r4 = com.loc.bq.vM();	 Catch:{ all -> 0x00d9 }
        r6 = r10.jp;	 Catch:{ all -> 0x00d9 }
        r4 = r4 - r6;
        r6 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
        r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r0 > 0) goto L_0x006a;
    L_0x0021:
        r0 = r2;
    L_0x0022:
        if (r0 != 0) goto L_0x0027;
    L_0x0024:
        r10.je();	 Catch:{ all -> 0x00d9 }
    L_0x0027:
        r0 = com.loc.bq.vZ();	 Catch:{ all -> 0x00d9 }
        r4 = r10.jh;	 Catch:{ all -> 0x00d9 }
        r5 = "nbssid";
        r4 = com.loc.bq.wb(r4, r5);	 Catch:{ all -> 0x00d9 }
        if (r4 != 0) goto L_0x006c;
    L_0x0036:
        r4 = r0;
    L_0x0037:
        r5 = new java.util.Hashtable;	 Catch:{ all -> 0x00d9 }
        r5.<init>();	 Catch:{ all -> 0x00d9 }
        r6 = r10.jc;	 Catch:{ all -> 0x00d9 }
        r7 = r6.size();	 Catch:{ all -> 0x00d9 }
        r2 = r1;
    L_0x0043:
        if (r2 < r7) goto L_0x0099;
    L_0x0045:
        r0 = new java.util.TreeMap;	 Catch:{ all -> 0x00d9 }
        r1 = java.util.Collections.reverseOrder();	 Catch:{ all -> 0x00d9 }
        r0.<init>(r1);	 Catch:{ all -> 0x00d9 }
        r0.putAll(r5);	 Catch:{ all -> 0x00d9 }
        r6.clear();	 Catch:{ all -> 0x00d9 }
        r1 = r0.keySet();	 Catch:{ all -> 0x00d9 }
        r1 = r1.iterator();	 Catch:{ all -> 0x00d9 }
    L_0x005c:
        r2 = r1.hasNext();	 Catch:{ all -> 0x00d9 }
        if (r2 != 0) goto L_0x0100;
    L_0x0062:
        r5.clear();	 Catch:{ all -> 0x00d9 }
        r0.clear();	 Catch:{ all -> 0x00d9 }
        monitor-exit(r10);
        return;
    L_0x006a:
        r0 = r1;
        goto L_0x0022;
    L_0x006c:
        r4 = r10.jh;	 Catch:{ Exception -> 0x0096 }
        r5 = "nbssid";
        r4 = r4.getString(r5);	 Catch:{ Exception -> 0x0096 }
        r5 = "1";
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x0096 }
        if (r4 != 0) goto L_0x0092;
    L_0x007e:
        r2 = r10.jh;	 Catch:{ Exception -> 0x0096 }
        r4 = "nbssid";
        r2 = r2.getString(r4);	 Catch:{ Exception -> 0x0096 }
        r4 = "0";
        r2 = r2.equals(r4);	 Catch:{ Exception -> 0x0096 }
        if (r2 != 0) goto L_0x0094;
    L_0x0090:
        r4 = r0;
        goto L_0x0037;
    L_0x0092:
        r0 = r2;
        goto L_0x0090;
    L_0x0094:
        r0 = r1;
        goto L_0x0090;
    L_0x0096:
        r2 = move-exception;
        r4 = r0;
        goto L_0x0037;
    L_0x0099:
        r0 = r6.get(r2);	 Catch:{ all -> 0x00d9 }
        r0 = (android.net.wifi.ScanResult) r0;	 Catch:{ all -> 0x00d9 }
        r1 = com.loc.bq.wc(r0);	 Catch:{ all -> 0x00d9 }
        if (r1 == 0) goto L_0x00c2;
    L_0x00a5:
        r1 = 20;
        if (r7 > r1) goto L_0x00c7;
    L_0x00a9:
        r1 = r0.SSID;	 Catch:{ all -> 0x00d9 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ all -> 0x00d9 }
        if (r1 == 0) goto L_0x00d0;
    L_0x00b1:
        r1 = "unkwn";
        r0.SSID = r1;	 Catch:{ all -> 0x00d9 }
    L_0x00b6:
        r1 = r0.level;	 Catch:{ all -> 0x00d9 }
        r1 = r1 * 30;
        r1 = r1 + r2;
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ all -> 0x00d9 }
        r5.put(r1, r0);	 Catch:{ all -> 0x00d9 }
    L_0x00c2:
        r1 = r2 + 1;
        r2 = r1;
        goto L_0x0043;
    L_0x00c7:
        r1 = r0.level;	 Catch:{ all -> 0x00d9 }
        r1 = r10.iY(r1);	 Catch:{ all -> 0x00d9 }
        if (r1 == 0) goto L_0x00c2;
    L_0x00cf:
        goto L_0x00a9;
    L_0x00d0:
        if (r4 != 0) goto L_0x00dc;
    L_0x00d2:
        r1 = java.lang.String.valueOf(r2);	 Catch:{ all -> 0x00d9 }
        r0.SSID = r1;	 Catch:{ all -> 0x00d9 }
        goto L_0x00b6;
    L_0x00d9:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x00dc:
        r1 = r0.SSID;	 Catch:{ all -> 0x00d9 }
        r8 = "*";
        r9 = ".";
        r1 = r1.replace(r8, r9);	 Catch:{ all -> 0x00d9 }
        r0.SSID = r1;	 Catch:{ all -> 0x00d9 }
        r1 = r0.SSID;	 Catch:{ Exception -> 0x00fd }
        r8 = "UTF-8";
        r1 = r1.getBytes(r8);	 Catch:{ Exception -> 0x00fd }
        r1 = r1.length;	 Catch:{ Exception -> 0x00fd }
    L_0x00f4:
        if (r1 < r3) goto L_0x00b6;
    L_0x00f6:
        r1 = java.lang.String.valueOf(r2);	 Catch:{ all -> 0x00d9 }
        r0.SSID = r1;	 Catch:{ all -> 0x00d9 }
        goto L_0x00b6;
    L_0x00fd:
        r1 = move-exception;
        r1 = r3;
        goto L_0x00f4;
    L_0x0100:
        r2 = r1.next();	 Catch:{ all -> 0x00d9 }
        r2 = r0.get(r2);	 Catch:{ all -> 0x00d9 }
        r6.add(r2);	 Catch:{ all -> 0x00d9 }
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.e.iX():void");
    }

    public synchronized String iZ() {
        if (this.jb.kO(this.jm)) {
            this.jb.kL();
        }
        try {
            StringBuilder iU = iU(null);
            if (iU == null) {
                jI.append("get parames is null");
                return null;
            }
            return iU.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            jI.append("get parames error:" + th.getMessage());
            return null;
        }
    }

    public synchronized void jC() {
        if (this.jq >= 1) {
            if (!this.jK) {
                if (this.jJ == null) {
                    this.jJ = new aV(this.iY.getApplicationContext());
                    this.jJ.sF(this.jZ);
                }
                if (!this.jK && f.kx()) {
                    try {
                        if (this.jJ != null) {
                            this.jJ.sG();
                        }
                        this.jK = true;
                    } catch (Throwable th) {
                        this.jK = true;
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public synchronized void jD(Context context) {
        try {
            if (au.a == -1) {
                au.pa(this.iY, H.mY("2.2.0"));
                f.ke(context);
            }
        } catch (Throwable th) {
        }
    }

    public void jO() {
        if (this.jY && !ji()) {
            jj();
        }
    }
}
