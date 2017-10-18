package com.loc;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public abstract class bk extends Binder implements bL {
    public static bL vm(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.amap.api.service.locationprovider.ILocationProviderService");
        return (queryLocalInterface != null && (queryLocalInterface instanceof bL)) ? (bL) queryLocalInterface : new B(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        Bundle bundle = null;
        switch (i) {
            case 1:
                parcel.enforceInterface("com.amap.api.service.locationprovider.ILocationProviderService");
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                int mE = mE(bundle);
                parcel2.writeNoException();
                parcel2.writeInt(mE);
                if (bundle == null) {
                    parcel2.writeInt(0);
                } else {
                    parcel2.writeInt(1);
                    bundle.writeToParcel(parcel2, 1);
                }
                return true;
            case 1598968902:
                parcel2.writeString("com.amap.api.service.locationprovider.ILocationProviderService");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
