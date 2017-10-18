package com.amap.api.fence;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class a implements Creator {
    a() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ix(parcel);
    }

    public Fence ix(Parcel parcel) {
        return new Fence(parcel);
    }

    public Fence[] iy(int i) {
        return new Fence[i];
    }

    public /* synthetic */ Object[] newArray(int i) {
        return iy(i);
    }
}
