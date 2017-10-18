package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import java.util.List;
import org.json.JSONObject;

public class aQ {
    private WifiManager pZ;
    private JSONObject qa;
    private Context qb;

    public aQ(Context context, WifiManager wifiManager, JSONObject jSONObject) {
        this.pZ = wifiManager;
        this.qa = jSONObject;
        this.qb = context;
    }

    private boolean rU(WifiInfo wifiInfo) {
        return (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getBSSID())) ? false : wifiInfo.getSSID() != null ? !wifiInfo.getBSSID().equals("00:00:00:00:00:00") ? !wifiInfo.getBSSID().contains(" :") ? !TextUtils.isEmpty(wifiInfo.getSSID()) : false : false : false;
    }

    public void rL(JSONObject jSONObject) {
        this.qa = jSONObject;
    }

    public List rM() {
        try {
            return this.pZ == null ? null : this.pZ.getScanResults();
        } catch (Throwable th) {
            return null;
        }
    }

    public WifiInfo rN() {
        return this.pZ == null ? null : this.pZ.getConnectionInfo();
    }

    public int rO() {
        return this.pZ == null ? 4 : this.pZ.getWifiState();
    }

    public boolean rP() {
        return this.pZ == null ? false : this.pZ.startScan();
    }

    public boolean rQ() {
        try {
            if (String.valueOf(bJ.xE(this.pZ, "startScanActive", new Object[0])).equals("true")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean rR(ConnectivityManager connectivityManager) {
        boolean z = false;
        WifiManager wifiManager = this.pZ;
        if (wifiManager == null) {
            return false;
        }
        if (rS()) {
            try {
                if (ba.tp(connectivityManager.getActiveNetworkInfo()) == 1 && rU(wifiManager.getConnectionInfo())) {
                    z = true;
                }
            } catch (Exception e) {
            }
        }
        return z;
    }

    public boolean rS() {
        boolean z = false;
        WifiManager wifiManager = this.pZ;
        if (wifiManager == null) {
            return z;
        }
        try {
            z = wifiManager.isWifiEnabled();
        } catch (Exception e) {
        }
        if (!z && bq.vR() > 17) {
            try {
                z = String.valueOf(bJ.xE(wifiManager, "isScanAlwaysAvailable", new Object[0])).equals("true");
            } catch (Exception e2) {
            }
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rT(boolean r8) {
        /*
        r7 = this;
        r4 = 2;
        r6 = 1;
        r5 = 0;
        r0 = r7.qb;
        r1 = r7.pZ;
        if (r1 != 0) goto L_0x000a;
    L_0x0009:
        return;
    L_0x000a:
        if (r0 == 0) goto L_0x0009;
    L_0x000c:
        if (r8 == 0) goto L_0x0009;
    L_0x000e:
        r1 = com.loc.bq.vR();
        r2 = 17;
        if (r1 <= r2) goto L_0x004b;
    L_0x0016:
        r1 = "autoenablewifialwaysscan";
        r2 = r7.qa;
        r2 = com.loc.bq.wb(r2, r1);
        if (r2 != 0) goto L_0x004c;
    L_0x0021:
        r1 = r0.getContentResolver();
        r2 = "android.provider.Settings$Global";
        r0 = new java.lang.Object[r4];
        r0[r5] = r1;
        r3 = "wifi_scan_always_enabled";
        r0[r6] = r3;
        r3 = new java.lang.Class[r4];
        r4 = android.content.ContentResolver.class;
        r3[r5] = r4;
        r4 = java.lang.String.class;
        r3[r6] = r4;
        r4 = "getInt";
        r0 = com.loc.bJ.xG(r2, r4, r0, r3);	 Catch:{ Exception -> 0x0089 }
        r0 = (java.lang.Integer) r0;	 Catch:{ Exception -> 0x0089 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x0089 }
        if (r0 == 0) goto L_0x005c;
    L_0x004a:
        return;
    L_0x004b:
        return;
    L_0x004c:
        r2 = "0";
        r3 = r7.qa;	 Catch:{ Exception -> 0x008b }
        r1 = r3.getString(r1);	 Catch:{ Exception -> 0x008b }
        r1 = r2.equals(r1);	 Catch:{ Exception -> 0x008b }
        if (r1 == 0) goto L_0x0021;
    L_0x005b:
        return;
    L_0x005c:
        r0 = 3;
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x0089 }
        r3 = 0;
        r0[r3] = r1;	 Catch:{ Exception -> 0x0089 }
        r1 = 1;
        r3 = "wifi_scan_always_enabled";
        r0[r1] = r3;	 Catch:{ Exception -> 0x0089 }
        r1 = 2;
        r3 = 1;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x0089 }
        r0[r1] = r3;	 Catch:{ Exception -> 0x0089 }
        r1 = 3;
        r1 = new java.lang.Class[r1];	 Catch:{ Exception -> 0x0089 }
        r3 = 0;
        r4 = android.content.ContentResolver.class;
        r1[r3] = r4;	 Catch:{ Exception -> 0x0089 }
        r3 = 1;
        r4 = java.lang.String.class;
        r1[r3] = r4;	 Catch:{ Exception -> 0x0089 }
        r3 = 2;
        r4 = java.lang.Integer.TYPE;	 Catch:{ Exception -> 0x0089 }
        r1[r3] = r4;	 Catch:{ Exception -> 0x0089 }
        r3 = "putInt";
        com.loc.bJ.xG(r2, r3, r0, r1);	 Catch:{ Exception -> 0x0089 }
        goto L_0x004a;
    L_0x0089:
        r0 = move-exception;
        goto L_0x004a;
    L_0x008b:
        r1 = move-exception;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aQ.rT(boolean):void");
    }
}
