package com.loc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;

/* compiled from: Unknown */
public final class bC {
    private RandomAccessFile tn;
    private aW to;
    private String tp = "";
    private File tq = null;

    protected bC(aW aWVar) {
        this.to = aWVar;
    }

    protected final synchronized void xj(long j, byte[] bArr) {
        this.tq = this.to.sP(j);
        if (this.tq != null) {
            try {
                this.tn = new RandomAccessFile(this.tq, "rw");
                byte[] bArr2 = new byte[this.to.sO()];
                int readInt = this.tn.read(bArr2) != -1 ? this.tn.readInt() : 0;
                BitSet tb = aW.tb(bArr2);
                int sO = (this.to.sO() + 4) + (readInt * 1500);
                if (readInt >= 0) {
                    if (readInt <= (this.to.sO() << 3)) {
                        this.tn.seek((long) sO);
                        byte[] sX = aW.sX(bArr);
                        this.tn.writeInt(sX.length);
                        this.tn.writeLong(j);
                        this.tn.write(sX);
                        tb.set(readInt, true);
                        this.tn.seek(0);
                        this.tn.write(aW.ta(tb));
                        readInt++;
                        if (readInt == (this.to.sO() << 3)) {
                            readInt = 0;
                        }
                        this.tn.writeInt(readInt);
                        if (!this.tp.equalsIgnoreCase(this.tq.getName())) {
                            this.tp = this.tq.getName();
                        }
                        this.tq.length();
                        if (this.tn != null) {
                            try {
                                this.tn.close();
                            } catch (IOException e) {
                            }
                        }
                        this.tq = null;
                        return;
                    }
                }
                this.tn.close();
                this.tq.delete();
                if (this.tn != null) {
                    try {
                        this.tn.close();
                    } catch (IOException e2) {
                    }
                }
            } catch (FileNotFoundException e3) {
                if (this.tn != null) {
                    try {
                        this.tn.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (IOException e5) {
                if (this.tn != null) {
                    try {
                        this.tn.close();
                    } catch (IOException e6) {
                    }
                }
            } catch (Throwable th) {
                if (this.tn != null) {
                    try {
                        this.tn.close();
                    } catch (IOException e7) {
                    }
                }
            }
        }
    }
}
