package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/* compiled from: Unknown */
final class by implements Serializable {
    protected byte a = (byte) 0;
    protected ArrayList b = new ArrayList();
    private byte c = (byte) 3;

    by() {
    }

    protected final Boolean mD(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.a);
            for (int i = 0; i < this.b.size(); i++) {
                bz bzVar = (bz) this.b.get(i);
                dataOutputStream.writeByte(bzVar.a);
                Object obj = new byte[bzVar.a];
                System.arraycopy(bzVar.b, 0, obj, 0, bzVar.a >= bzVar.b.length ? bzVar.b.length : bzVar.a);
                dataOutputStream.write(obj);
                dataOutputStream.writeDouble(bzVar.c);
                dataOutputStream.writeInt(bzVar.d);
                dataOutputStream.writeInt(bzVar.e);
                dataOutputStream.writeDouble(bzVar.f);
                dataOutputStream.writeByte(bzVar.g);
                dataOutputStream.writeByte(bzVar.h);
                obj = new byte[bzVar.h];
                System.arraycopy(bzVar.i, 0, obj, 0, bzVar.h >= bzVar.i.length ? bzVar.i.length : bzVar.h);
                dataOutputStream.write(obj);
                dataOutputStream.writeByte(bzVar.j);
            }
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }
}
