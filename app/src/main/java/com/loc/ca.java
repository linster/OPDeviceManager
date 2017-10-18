package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/* compiled from: Unknown */
final class ca implements Serializable {
    protected byte a = (byte) 0;
    protected ArrayList b = new ArrayList();
    private byte c = (byte) 8;

    ca() {
    }

    protected final Boolean rC(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.a);
            for (byte b = (byte) 0; b < this.a; b++) {
                cb cbVar = (cb) this.b.get(b);
                dataOutputStream.write(cbVar.a);
                dataOutputStream.writeShort(cbVar.b);
                dataOutputStream.write(aW.sL(cbVar.c, cbVar.c.length));
            }
            return Boolean.valueOf(true);
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
    }
}
