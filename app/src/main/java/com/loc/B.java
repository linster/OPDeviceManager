package com.loc;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

class B implements bL {
    private IBinder lp;

    B(IBinder iBinder) {
        this.lp = iBinder;
    }

    public IBinder asBinder() {
        return this.lp;
    }

    public int mE(Bundle bundle) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.amap.api.service.locationprovider.ILocationProviderService");
            if (bundle == null) {
                obtain.writeInt(0);
            } else {
                obtain.writeInt(1);
                bundle.writeToParcel(obtain, 0);
            }
            this.lp.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            int readInt = obtain2.readInt();
            if (obtain2.readInt() != 0) {
                bundle.readFromParcel(obtain2);
            }
            obtain2.recycle();
            obtain.recycle();
            return readInt;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
