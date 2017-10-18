package net.oneplus.odm.geolocation;

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

import java.util.List;

import net.oneplus.odm.data.Cell;

public class CellProvider {
    private static final String TAG;
    private Cell mCell;
    private TelephonyManager mTelephonyManager;

    static {
        TAG = CellProvider.class;
    }

    public Cell getCellInfo(Context context) {
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        List<CellInfo> cellInfoList = this.mTelephonyManager.getAllCellInfo();
        if (cellInfoList == null) {
            return null;
        }
        for (CellInfo cellInfo : cellInfoList) {
            if (cellInfo.isRegistered()) {
                if (cellInfo instanceof CellInfoGsm) {
                    createGsmCell(cellInfo);
                } else if (cellInfo instanceof CellInfoCdma) {
                    createCdmaCell(cellInfo);
                } else if (cellInfo instanceof CellInfoLte) {
                    createLteCell(cellInfo);
                } else if (cellInfo instanceof CellInfoWcdma) {
                    createWcdmaCell(cellInfo);
                }
            }
        }
        return this.mCell;
    }

    private void createGsmCell(CellInfo cellInfo) {
        CellIdentityGsm cellIdentityGsm = ((CellInfoGsm) cellInfo).getCellIdentity();
        this.mCell = new Cell("gsm", cellIdentityGsm.getMcc(), cellIdentityGsm.getMnc(), cellIdentityGsm.getLac(), cellIdentityGsm.getCid(), Integer.MAX_VALUE);
        Log.d(TAG, cellIdentityGsm.toString());
    }

    private void createCdmaCell(CellInfo cellInfo) {
        CellIdentityCdma cellIdentityCdma = ((CellInfoCdma) cellInfo).getCellIdentity();
        String networkOperator = this.mTelephonyManager.getNetworkOperator();
        if (networkOperator == null || networkOperator.equals("")) {
            Log.e(TAG, "Error networkOperator:" + networkOperator);
            this.mCell = null;
        } else {
            try {
                this.mCell = new Cell("cdma", Integer.parseInt(networkOperator.substring(0, 3)), Integer.parseInt(networkOperator.substring(3)), cellIdentityCdma.getNetworkId(), cellIdentityCdma.getBasestationId(), Integer.MAX_VALUE);
            } catch (StringIndexOutOfBoundsException e) {
                Log.e(TAG, "Error networkOperator:" + networkOperator);
                Log.e(TAG, e.getMessage());
                this.mCell = null;
            }
        }
        Log.d(TAG, cellIdentityCdma.toString());
    }

    private void createLteCell(CellInfo cellInfo) {
        CellIdentityLte cellIdentityLte = ((CellInfoLte) cellInfo).getCellIdentity();
        this.mCell = new Cell("lte", cellIdentityLte.getMcc(), cellIdentityLte.getMnc(), cellIdentityLte.getTac(), cellIdentityLte.getCi(), cellIdentityLte.getPci());
        Log.d(TAG, cellIdentityLte.toString());
    }

    private void createWcdmaCell(CellInfo cellInfo) {
        CellIdentityWcdma cellIdentityWcdma = ((CellInfoWcdma) cellInfo).getCellIdentity();
        this.mCell = new Cell("wcdma", cellIdentityWcdma.getMcc(), cellIdentityWcdma.getMnc(), cellIdentityWcdma.getLac(), cellIdentityWcdma.getCid(), cellIdentityWcdma.getPsc());
        Log.d(TAG, cellIdentityWcdma.toString());
    }
}
