package com.loc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class j {
    private ByteArrayInputStream kD;
    private long kE;
    private boolean kF = false;
    private RandomAccessFile kG = null;
    private boolean kH = false;
    private final byte[] kI = new byte[8];
    private V kJ;
    private String kK = null;

    public j(File file, V v) {
        if (v != null) {
            if (!v.ms) {
                this.kG = new RandomAccessFile(file, "r");
                this.kF = true;
            } else {
                byte[] wl = bq.wl(file);
                this.kD = new ByteArrayInputStream(wl);
                this.kE = (long) wl.length;
                this.kF = false;
                this.kK = file.getAbsolutePath();
            }
            this.kJ = v;
        }
    }

    private void lu() {
        if (this.kH) {
            throw new IOException("file closed");
        }
    }

    protected void finalize() {
        ln();
        super.finalize();
    }

    public boolean lm() {
        return this.kJ != null ? this.kJ.ms : false;
    }

    public void ln() {
        synchronized (this) {
            if (this.kF) {
                if (this.kG != null) {
                    this.kG.close();
                    this.kG = null;
                }
            } else if (this.kD != null) {
                this.kD.close();
                this.kD = null;
            }
            this.kH = true;
        }
    }

    public void lo(long j) {
        Object obj = null;
        if (j >= 0) {
            obj = 1;
        }
        if (obj == null) {
            throw new IOException("offset < 0: " + j);
        }
        lu();
        if (this.kF) {
            this.kG.seek(j);
            return;
        }
        this.kD.reset();
        this.kD.skip(j);
    }

    public final long lp() {
        lu();
        if (this.kF) {
            return this.kG.readLong();
        }
        this.kD.read(this.kI);
        return bq.wi(this.kI);
    }

    public final int lq() {
        lu();
        if (this.kF) {
            return this.kG.readUnsignedShort();
        }
        this.kD.read(this.kI, 0, 2);
        return bq.wj(this.kI);
    }

    public final int lr() {
        lu();
        if (this.kF) {
            return this.kG.readInt();
        }
        this.kD.read(this.kI, 0, 4);
        return bq.wk(this.kI);
    }

    public final int ls() {
        lu();
        return !this.kF ? this.kD.read() : this.kG.readUnsignedByte();
    }

    public long lt() {
        if (!this.kH) {
            return !this.kF ? this.kE : this.kG.length();
        } else {
            throw new IOException("file closed");
        }
    }
}
