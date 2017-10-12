package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/* compiled from: Unknown */
final class bv implements Serializable {
    protected byte[] a;
    protected byte[] b;
    protected byte[] c;
    protected short d;
    protected short e;
    protected byte f;
    protected byte[] g;
    protected byte[] h;
    protected short i;
    protected ArrayList j;
    private byte k;
    private short l;

    bv() {
        this.k = (byte) 41;
        this.l = (short) 0;
        this.a = new byte[16];
        this.b = new byte[16];
        this.c = new byte[16];
        this.d = (short) 0;
        this.e = (short) 0;
        this.f = (byte) 0;
        this.g = new byte[16];
        this.h = new byte[32];
        this.i = (short) 0;
        this.j = new ArrayList();
    }

    private Boolean a(DataOutputStream dataOutputStream) {
        Boolean.valueOf(true);
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream2.flush();
            dataOutputStream2.write(this.a);
            dataOutputStream2.write(this.b);
            dataOutputStream2.write(this.c);
            dataOutputStream2.writeShort(this.d);
            dataOutputStream2.writeShort(this.e);
            dataOutputStream2.writeByte(this.f);
            this.g[15] = (byte) 0;
            dataOutputStream2.write(cd.a(this.g, this.g.length));
            this.h[31] = (byte) 0;
            dataOutputStream2.write(cd.a(this.h, this.h.length));
            dataOutputStream2.writeShort(this.i);
            for (short s = (short) 0; s < this.i; s = (short) (s + 1)) {
                OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream3 = new DataOutputStream(byteArrayOutputStream2);
                dataOutputStream3.flush();
                bt btVar = (bt) this.j.get(s);
                if (!(btVar.c == null || btVar.c.a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (btVar.d != null) {
                    if (!btVar.d.a(dataOutputStream3).booleanValue()) {
                        Boolean.valueOf(false);
                    }
                }
                if (!(btVar.e == null || btVar.e.a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (!(btVar.f == null || btVar.f.a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (!(btVar.g == null || btVar.g.a(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                btVar.a = (short) Integer.valueOf(byteArrayOutputStream2.size() + 4).shortValue();
                dataOutputStream2.writeShort(btVar.a);
                dataOutputStream2.writeInt(btVar.b);
                dataOutputStream2.write(byteArrayOutputStream2.toByteArray());
            }
            this.l = (short) Integer.valueOf(byteArrayOutputStream.size()).shortValue();
            dataOutputStream.writeByte(this.k);
            dataOutputStream.writeShort(this.l);
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }

    protected final byte[] a() {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(new DataOutputStream(byteArrayOutputStream));
        return byteArrayOutputStream.toByteArray();
    }
}
