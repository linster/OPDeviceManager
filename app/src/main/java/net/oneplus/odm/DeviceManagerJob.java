package net.oneplus.odm;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DeviceManagerJob implements Parcelable {
    public static final Creator br = new q();
    private boolean bs;
    private long bt;
    private String bu;
    private PendingIntent bv;
    private long bw;
    private int bx;

    public DeviceManagerJob(Parcel parcel) {
        this.bt = parcel.readLong();
        this.bu = parcel.readString();
        this.bw = parcel.readLong();
        this.bx = parcel.readInt();
        this.bv = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
    }

    public DeviceManagerJob(String str, long j, int i, PendingIntent pendingIntent) {
        this.bu = str;
        this.bt = j;
        this.bx = i;
        this.bv = pendingIntent;
    }

    public PendingIntent ca() {
        return this.bv;
    }

    public int cb() {
        return this.bx;
    }

    public long cc() {
        return this.bt;
    }

    public long cd() {
        return this.bw;
    }

    public String ce() {
        return this.bu;
    }

    public boolean cf() {
        return this.bs;
    }

    protected void cg(boolean z) {
        this.bs = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.bt);
        parcel.writeString(this.bu);
        parcel.writeLong(this.bw);
        parcel.writeInt(this.bx);
        parcel.writeParcelable(this.bv, 0);
    }
}
