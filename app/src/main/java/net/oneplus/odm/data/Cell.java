package net.oneplus.odm.data;

import com.google.gson.annotations.SerializedName;

public class Cell {
    @SerializedName("cid")
    private int mCid;
    @SerializedName("lac")
    private int mLac;
    @SerializedName("mcc")
    private int mMcc;
    @SerializedName("mnc")
    private int mMnc;
    @SerializedName("psc")
    private int mPsc;
    @SerializedName("type")
    private String mType;

    public Cell(String type, int mcc, int mnc, int lac, int cid, int psc) {
        this.mType = type;
        this.mMnc = mnc;
        this.mMcc = mcc;
        this.mLac = lac;
        this.mCid = cid;
        this.mPsc = psc;
    }

    public int getMnc() {
        return this.mMnc;
    }

    public int getMcc() {
        return this.mMcc;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }
}
