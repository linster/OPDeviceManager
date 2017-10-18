package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: Unknown */
public class am {
    protected File nb;
    private ArrayList nc;
    protected int[] nd;
    private boolean ne = false;

    protected am(File file, ArrayList arrayList, int[] iArr) {
        this.nb = file;
        this.nc = arrayList;
        this.nd = iArr;
    }

    public void oA(boolean z) {
        this.ne = z;
    }

    protected final boolean oB() {
        return this.ne;
    }

    protected final int oC() {
        if (this.nc == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.nc.size(); i2++) {
            i += this.nc.get(i2) == null ? 0 : ((byte[]) this.nc.get(i2)).length;
        }
        return i;
    }

    public byte[] oz() {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        Iterator it = this.nc.iterator();
        while (it.hasNext()) {
            byte[] bArr = (byte[]) it.next();
            try {
                dataOutputStream.writeInt(bArr.length);
                dataOutputStream.write(bArr);
            } catch (IOException e) {
            }
        }
        try {
            byteArrayOutputStream.close();
            dataOutputStream.close();
        } catch (IOException e2) {
        }
        return byteArrayOutputStream.toByteArray();
    }
}
