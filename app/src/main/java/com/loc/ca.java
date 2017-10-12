package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/* compiled from: Unknown */
final class ca implements Serializable {
    protected byte a;
    protected ArrayList b;
    private byte c;

    ca() {
        this.c = (byte) 8;
        this.a = (byte) 0;
        this.b = new ArrayList();
    }

    protected final Boolean a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.a);
            for (byte b = (byte) 0; b < this.a; b++) {
                cb cbVar = (cb) this.b.get(b);
                dataOutputStream.write(cbVar.a);
                dataOutputStream.writeShort(cbVar.b);
                dataOutputStream.write(cd.a(cbVar.c, cbVar.c.length));
            }
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }
}
