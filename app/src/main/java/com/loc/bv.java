package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/* compiled from: Unknown */
final class bv implements Serializable {
    protected byte[] a = new byte[16];
    protected byte[] b = new byte[16];
    protected byte[] c = new byte[16];
    protected short d = (short) 0;
    protected short e = (short) 0;
    protected byte f = (byte) 0;
    protected byte[] g = new byte[16];
    protected byte[] h = new byte[32];
    protected short i = (short) 0;
    protected ArrayList j = new ArrayList();
    private byte k = (byte) 41;
    private short l = (short) 0;

    bv() {
    }

    private Boolean nf(DataOutputStream dataOutputStream) {
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
            dataOutputStream2.write(aW.sL(this.g, this.g.length));
            this.h[31] = (byte) 0;
            dataOutputStream2.write(aW.sL(this.h, this.h.length));
            dataOutputStream2.writeShort(this.i);
            for (short s = (short) 0; s < this.i; s = (short) (s + 1)) {
                OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream3 = new DataOutputStream(byteArrayOutputStream2);
                dataOutputStream3.flush();
                bt btVar = (bt) this.j.get(s);
                if (!(btVar.c == null || btVar.c.ns(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (btVar.d != null) {
                    if (!btVar.d.ll(dataOutputStream3).booleanValue()) {
                        Boolean.valueOf(false);
                    }
                }
                if (!(btVar.e == null || btVar.e.lM(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (!(btVar.f == null || btVar.f.rC(dataOutputStream3).booleanValue())) {
                    Boolean.valueOf(false);
                }
                if (!(btVar.g == null || btVar.g.mD(dataOutputStream3).booleanValue())) {
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

    protected final byte[] ng() {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        nf(new DataOutputStream(byteArrayOutputStream));
        return byteArrayOutputStream.toByteArray();
    }
}
