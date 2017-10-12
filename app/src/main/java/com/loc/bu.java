package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/* compiled from: Unknown */
final class bu implements Serializable {
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected short f;
    protected byte g;
    protected byte h;
    protected long i;
    protected long j;
    private byte k;

    bu() {
        this.k = (byte) 1;
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = (short) 0;
        this.g = (byte) 0;
        this.h = (byte) 0;
        this.i = 0;
        this.j = 0;
    }

    protected final Boolean a(DataOutputStream dataOutputStream) {
        Boolean valueOf = Boolean.valueOf(false);
        if (dataOutputStream == null) {
            return valueOf;
        }
        try {
            dataOutputStream.writeByte(this.k);
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeShort(this.f);
            dataOutputStream.writeByte(this.g);
            dataOutputStream.writeByte(this.h);
            dataOutputStream.writeLong(this.i);
            dataOutputStream.writeLong(this.j);
            valueOf = Boolean.valueOf(true);
        } catch (IOException e) {
        }
        return valueOf;
    }
}
