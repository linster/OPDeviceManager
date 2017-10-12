package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/* compiled from: Unknown */
final class db implements Serializable {
    protected short a;
    protected int b;
    protected byte c;
    protected byte d;
    protected ArrayList e;
    private byte f;

    db() {
        this.f = (byte) 2;
        this.a = (short) 0;
        this.b = 0;
        this.c = (byte) 0;
        this.d = (byte) 0;
        this.e = new ArrayList();
    }

    protected final Boolean a(DataOutputStream dataOutputStream) {
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
