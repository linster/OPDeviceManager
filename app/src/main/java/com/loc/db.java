package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/* compiled from: Unknown */
final class db implements Serializable {
    protected short a = (short) 0;
    protected int b = 0;
    protected byte c = (byte) 0;
    protected byte d = (byte) 0;
    protected ArrayList e = new ArrayList();
    private byte f = (byte) 2;

    db() {
    }

    protected final Boolean ll(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.f);
            dataOutputStream.writeShort(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.d);
            for (byte b = (byte) 0; b < this.d; b++) {
                dataOutputStream.writeShort(((cf) this.e.get(b)).a);
                dataOutputStream.writeInt(((cf) this.e.get(b)).b);
                dataOutputStream.writeByte(((cf) this.e.get(b)).c);
            }
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }
}
