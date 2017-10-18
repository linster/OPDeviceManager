package com.autonavi.aps.amapapi.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class a implements Creator {
    a() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zI(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zJ(i);
    }

    public AmapLoc zI(Parcel parcel) {
        return new AmapLoc(parcel);
    }

    public AmapLoc[] zJ(int i) {
        return null;
    }
}
