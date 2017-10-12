package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/* compiled from: Unknown */
final class ce implements Serializable {
    protected int a;
    protected int b;
    protected short c;
    protected short d;
    protected int e;
    protected byte f;
    private byte g;

    ce() {
        this.g = (byte) 4;
        this.a = 0;
        this.b = 0;
        this.c = (short) 0;
        this.d = (short) 0;
        this.e = 0;
        this.f = (byte) 0;
    }

    protected final Boolean a(DataOutputStream dataOutputStream) {
        Boolean valueOf = Boolean.valueOf(false);
        try {
            dataOutputStream.writeByte(this.g);
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeShort(this.c);
            dataOutputStream.writeShort(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeByte(this.f);
            valueOf = Boolean.valueOf(true);
        } catch (IOException e) {
        }
        return valueOf;
    }
}
