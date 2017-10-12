package com.loc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;

/* compiled from: Unknown */
public final class da {
    private RandomAccessFile a;
    private cd b;
    private String c;
    private File d;

    protected da(cd cdVar) {
        this.c = "";
        this.d = null;
        this.b = cdVar;
    }

    protected final synchronized void a(long j, byte[] bArr) {
        this.d = this.b.a(j);
        if (this.d != null) {
            try {
                this.a = new RandomAccessFile(this.d, "rw");
                byte[] bArr2 = new byte[this.b.a()];
                int readInt = this.a.read(bArr2) != -1 ? this.a.readInt() : 0;
                BitSet b = cd.b(bArr2);
                int a = (this.b.a() + 4) + (readInt * 1500);
                if (readInt >= 0) {
                    if (readInt <= (this.b.a() << 3)) {
                        this.a.seek((long) a);
                        byte[] a2 = cd.a(bArr);
                        this.a.writeInt(a2.length);
                        this.a.writeLong(j);
                        this.a.write(a2);
                        b.set(readInt, true);
                        this.a.seek(0);
                        this.a.write(cd.a(b));
                        readInt++;
                        if (readInt == (this.b.a() << 3)) {
                            readInt = 0;
                        }
                        this.a.writeInt(readInt);
                        if (!this.c.equalsIgnoreCase(this.d.getName())) {
                            this.c = this.d.getName();
                        }
                        this.d.length();
                        if (this.a != null) {
                            try {
                                this.a.close();
                            } catch (IOException e) {
                            }
                        }
                        this.d = null;
                        return;
                    }
                }
                this.a.close();
                this.d.delete();
                if (this.a != null) {
                    try {
                        this.a.close();
                    } catch (IOException e2) {
                    }
                }
            } catch (FileNotFoundException e3) {
                if (this.a != null) {
                    try {
                        this.a.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (IOException e5) {
                if (this.a != null) {
                    try {
                        this.a.close();
                    } catch (IOException e6) {
                    }
                }
            } catch (Throwable th) {
                if (this.a != null) {
                    try {
                        this.a.close();
                    } catch (IOException e7) {
                    }
                }
            }
        }
    }
}
