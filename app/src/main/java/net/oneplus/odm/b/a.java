package net.oneplus.odm.b;

import android.content.Context;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

public class a {
    private static final String x = a.class;
    private net.oneplus.odm.data.a y;
    private TelephonyManager z;

    private void s(CellInfo cellInfo) {
        CellIdentityCdma cellIdentity = ((CellInfoCdma) cellInfo).getCellIdentity();
        String networkOperator = this.z.getNetworkOperator();
        if (networkOperator == null || networkOperator.equals("")) {
            Log.e(x, "Error networkOperator:" + networkOperator);
            this.y = null;
        } else {
            try {
                this.y = new net.oneplus.odm.data.a("cdma", Integer.parseInt(networkOperator.substring(0, 3)), Integer.parseInt(networkOperator.substring(3)), cellIdentity.getNetworkId(), cellIdentity.getBasestationId(), Integer.MAX_VALUE);
            } catch (StringIndexOutOfBoundsException e) {
                Log.e(x, "Error networkOperator:" + networkOperator);
                Log.e(x, e.getMessage());
                this.y = null;
            }
        }
        Log.d(x, cellIdentity.toString());
    }

    private void t(CellInfo cellInfo) {
        CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
        this.y = new net.oneplus.odm.data.a("gsm", cellIdentity.getMcc(), cellIdentity.getMnc(), cellIdentity.getLac(), cellIdentity.getCid(), Integer.MAX_VALUE);
        Log.d(x, cellIdentity.toString());
    }

    private void u(CellInfo cellInfo) {
        CellIdentityLte cellIdentity = ((CellInfoLte) cellInfo).getCellIdentity();
        this.y = new net.oneplus.odm.data.a("lte", cellIdentity.getMcc(), cellIdentity.getMnc(), cellIdentity.getTac(), cellIdentity.getCi(), cellIdentity.getPci());
        Log.d(x, cellIdentity.toString());
    }

    private void v(CellInfo cellInfo) {
        CellIdentityWcdma cellIdentity = ((CellInfoWcdma) cellInfo).getCellIdentity();
        this.y = new net.oneplus.odm.data.a("wcdma", cellIdentity.getMcc(), cellIdentity.getMnc(), cellIdentity.getLac(), cellIdentity.getCid(), cellIdentity.getPsc());
        Log.d(x, cellIdentity.toString());
    }

    public net.oneplus.odm.data.a w(Context context) {
        this.z = (TelephonyManager) context.getSystemService("phone");
        Iterable<CellInfo> allCellInfo = this.z.getAllCellInfo();
        if (allCellInfo == null) {
            return null;
        }
        for (CellInfo cellInfo : allCellInfo) {
            if (cellInfo.isRegistered()) {
                if (cellInfo instanceof CellInfoGsm) {
                    t(cellInfo);
                } else if (cellInfo instanceof CellInfoCdma) {
                    s(cellInfo);
                } else if (cellInfo instanceof CellInfoLte) {
                    u(cellInfo);
                } else if (cellInfo instanceof CellInfoWcdma) {
                    v(cellInfo);
                }
            }
        }
        return this.y;
    }
}
