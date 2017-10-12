package net.oneplus.odm;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DeviceManagerJob implements Parcelable {
    public static final Creator CREATOR;
    private boolean mActivated;
    private long mInterval;
    private String mLabel;
    private PendingIntent mOperation;
    private long mPeriodTime;
    private int mType;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mInterval);
        dest.writeString(this.mLabel);
        dest.writeLong(this.mPeriodTime);
        dest.writeInt(this.mType);
        dest.writeParcelable(this.mOperation, 0);
    }

    public DeviceManagerJob(Parcel parcel) {
        this.mInterval = parcel.readLong();
        this.mLabel = parcel.readString();
        this.mPeriodTime = parcel.readLong();
        this.mType = parcel.readInt();
        this.mOperation = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
    }

    static {
        CREATOR = new Creator() {
            public DeviceManagerJob createFromParcel(Parcel in) {
                return new DeviceManagerJob(in);
            }

            public DeviceManagerJob[] newArray(int size) {
                return null;
            }
        };
    }

    public DeviceManagerJob(String label, long interval, int wakeUpType, PendingIntent operation) {
        this.mLabel = label;
        this.mInterval = interval;
        this.mType = wakeUpType;
        this.mOperation = operation;
    }

    public long getPeriodTime() {
        return this.mPeriodTime;
    }

    protected void setActivated(boolean status) {
        this.mActivated = status;
    }

    public long getInterval() {
        return this.mInterval;
    }

    public boolean getActivatedStatus() {
        return this.mActivated;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public int getType() {
        return this.mType;
    }

    public PendingIntent getOperation() {
        return this.mOperation;
    }
}
