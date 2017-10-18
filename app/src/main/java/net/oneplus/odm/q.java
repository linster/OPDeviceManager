package net.oneplus.odm;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class q implements Creator {
    q() {
    }

    public DeviceManagerJob createFromParcel(Parcel parcel) {
        return new DeviceManagerJob(parcel);
    }

    public DeviceManagerJob[] newArray(int i) {
        return null;
    }
}
