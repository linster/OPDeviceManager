package com.amap.api.fence;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.loc.bq;

public class Fence implements Parcelable {
    public static final Creator iS = new a();
    public double c;
    private long i;
    public PendingIntent iK;
    public String iL;
    public double iM;
    public float iN;
    public long iO;
    private int iP;
    public int iQ;
    public long iR;

    public Fence() {
        this.iK = null;
        this.iL = null;
        this.c = 0.0d;
        this.iM = 0.0d;
        this.iN = 0.0f;
        this.iO = -1;
        this.i = -1;
        this.iP = 3;
        this.iQ = -1;
        this.iR = -1;
    }

    private Fence(Parcel parcel) {
        this.iK = null;
        this.iL = null;
        this.c = 0.0d;
        this.iM = 0.0d;
        this.iN = 0.0f;
        this.iO = -1;
        this.i = -1;
        this.iP = 3;
        this.iQ = -1;
        this.iR = -1;
        if (parcel != null) {
            this.iL = parcel.readString();
            this.c = parcel.readDouble();
            this.iM = parcel.readDouble();
            this.iN = parcel.readFloat();
            this.iO = parcel.readLong();
            this.i = parcel.readLong();
            this.iP = parcel.readInt();
            this.iQ = parcel.readInt();
            this.iR = parcel.readLong();
        }
    }

    public int describeContents() {
        return 0;
    }

    public int it() {
        return this.iP;
    }

    public long iu() {
        return this.iO;
    }

    public void iv(long j) {
        if ((j >= 0 ? 1 : null) == null) {
            this.iO = -1;
        } else {
            this.iO = bq.vM() + j;
        }
    }

    public long iw() {
        return this.i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.iL);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.iM);
        parcel.writeFloat(this.iN);
        parcel.writeLong(this.iO);
        parcel.writeLong(this.i);
        parcel.writeInt(this.iP);
        parcel.writeInt(this.iQ);
        parcel.writeLong(this.iR);
    }
}
