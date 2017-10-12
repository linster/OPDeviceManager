package com.loc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.zip.GZIPInputStream;

/* compiled from: Unknown */
public final class cz {
    private RandomAccessFile a;
    private cd b;
    private File c;

    protected cz(cd cdVar) {
        this.c = null;
        this.b = cdVar;
    }

    private static byte a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] bArr3 = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = gZIPInputStream.read(bArr3, 0, bArr3.length);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr3, 0, read);
            }
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            gZIPInputStream.close();
            byteArrayInputStream.close();
        } catch (Exception e) {
        }
        return bArr2[0];
    }

    private static int a(int i, int i2, int i3) {
        int i4 = ((i3 - 1) * 1500) + i;
        while (i4 >= i2) {
            i4 -= 1500;
        }
        return i4;
    }

    private int a(BitSet bitSet) {
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                return this.b.a() + ((i * 1500) + 4);
            }
        }
        return 0;
    }

    private ArrayList a(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        while (i <= i2) {
            try {
                this.a.seek((long) i);
                int readInt = this.a.readInt();
                this.a.readLong();
                if (readInt <= 0 || readInt > 1500) {
                    return null;
                }
                byte[] bArr = new byte[readInt];
                this.a.read(bArr);
                byte a = a(bArr);
                if (a != 3 && a != 4 && a != 41) {
                    return null;
                }
                arrayList.add(bArr);
                i += 1500;
            } catch (IOException e) {
            }
        }
        return arrayList;
    }

    private BitSet b() {
        BitSet bitSet = null;
        byte[] bArr = new byte[this.b.a()];
        try {
            this.a.read(bArr);
            bitSet = cd.b(bArr);
        } catch (IOException e) {
        }
        return bitSet;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final int a() {
        /*
        r4 = this;
        r0 = 0;
        monitor-enter(r4);
        r1 = r4.b;	 Catch:{ all -> 0x009c }
        r1 = r1.b();	 Catch:{ all -> 0x009c }
        r4.c = r1;	 Catch:{ all -> 0x009c }
        r1 = r4.c;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        if (r1 != 0) goto L_0x0017;
    L_0x000e:
        r1 = r4.a;	 Catch:{ all -> 0x009c }
        if (r1 != 0) goto L_0x0066;
    L_0x0012:
        r1 = 0;
        r4.c = r1;	 Catch:{ all -> 0x009c }
        monitor-exit(r4);	 Catch:{ all -> 0x009c }
        return r0;
    L_0x0017:
        r1 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r2 = r4.b;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r2 = r2.b();	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r3 = "rw";
        r1.<init>(r2, r3);	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r4.a = r1;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r1 = r4.b;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r1 = r1.a();	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r1 = new byte[r1];	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r2 = com.loc.cg.c;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        if (r2 != 0) goto L_0x004c;
    L_0x0033:
        r2 = r4.a;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r2.read(r1);	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r2 = com.loc.cd.b(r1);	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        r1 = r0;
    L_0x003d:
        r3 = r2.size();	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        if (r1 >= r3) goto L_0x000e;
    L_0x0043:
        r3 = r2.get(r1);	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        if (r3 != 0) goto L_0x0063;
    L_0x0049:
        r1 = r1 + 1;
        goto L_0x003d;
    L_0x004c:
        r2 = r4.a;	 Catch:{ FileNotFoundException -> 0x006e, IOException -> 0x007b, NullPointerException -> 0x0088, all -> 0x0096 }
        if (r2 == 0) goto L_0x0033;
    L_0x0050:
        r2 = r4.a;	 Catch:{ IOException -> 0x00a7, FileNotFoundException -> 0x006e, NullPointerException -> 0x0088, all -> 0x0096 }
        r2.close();	 Catch:{ IOException -> 0x00a7, FileNotFoundException -> 0x006e, NullPointerException -> 0x0088, all -> 0x0096 }
        r1 = r4.a;	 Catch:{ all -> 0x009c }
        if (r1 != 0) goto L_0x005b;
    L_0x0059:
        monitor-exit(r4);	 Catch:{ all -> 0x009c }
        return r0;
    L_0x005b:
        r1 = r4.a;	 Catch:{ IOException -> 0x0061 }
        r1.close();	 Catch:{ IOException -> 0x0061 }
        goto L_0x0059;
    L_0x0061:
        r1 = move-exception;
        goto L_0x0059;
    L_0x0063:
        r0 = r0 + 1;
        goto L_0x0049;
    L_0x0066:
        r1 = r4.a;	 Catch:{ IOException -> 0x006c }
        r1.close();	 Catch:{ IOException -> 0x006c }
        goto L_0x0012;
    L_0x006c:
        r1 = move-exception;
        goto L_0x0012;
    L_0x006e:
        r1 = move-exception;
        r1 = r4.a;	 Catch:{ all -> 0x009c }
        if (r1 == 0) goto L_0x0012;
    L_0x0073:
        r1 = r4.a;	 Catch:{ IOException -> 0x0079 }
        r1.close();	 Catch:{ IOException -> 0x0079 }
        goto L_0x0012;
    L_0x0079:
        r1 = move-exception;
        goto L_0x0012;
    L_0x007b:
        r1 = move-exception;
        r1 = r4.a;	 Catch:{ all -> 0x009c }
        if (r1 == 0) goto L_0x0012;
    L_0x0080:
        r1 = r4.a;	 Catch:{ IOException -> 0x0086 }
        r1.close();	 Catch:{ IOException -> 0x0086 }
        goto L_0x0012;
    L_0x0086:
        r1 = move-exception;
        goto L_0x0012;
    L_0x0088:
        r1 = move-exception;
        r1 = r4.a;	 Catch:{ all -> 0x009c }
        if (r1 == 0) goto L_0x0012;
    L_0x008d:
        r1 = r4.a;	 Catch:{ IOException -> 0x0093 }
        r1.close();	 Catch:{ IOException -> 0x0093 }
        goto L_0x0012;
    L_0x0093:
        r1 = move-exception;
        goto L_0x0012;
    L_0x0096:
        r0 = move-exception;
        r1 = r4.a;	 Catch:{ all -> 0x009c }
        if (r1 != 0) goto L_0x009f;
    L_0x009b:
        throw r0;	 Catch:{ all -> 0x009c }
    L_0x009c:
        r0 = move-exception;
        monitor-exit(r4);
        throw r0;
    L_0x009f:
        r1 = r4.a;	 Catch:{ IOException -> 0x00a5 }
        r1.close();	 Catch:{ IOException -> 0x00a5 }
        goto L_0x009b;
    L_0x00a5:
        r1 = move-exception;
        goto L_0x009b;
    L_0x00a7:
        r2 = move-exception;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cz.a():int");
    }

    protected final synchronized cc a(int i) {
        cc ccVar;
        if (this.b == null) {
            return null;
        }
        synchronized (this) {
            this.c = this.b.b();
            if (this.c != null) {
                try {
                    this.a = new RandomAccessFile(this.c, "rw");
                    if (cg.c) {
                        if (this.a != null) {
                            try {
                                this.a.close();
                                if (this.a != null) {
                                    try {
                                        this.a.close();
                                    } catch (Exception e) {
                                    }
                                }
                                return null;
                            } catch (IOException e2) {
                            }
                        }
                    }
                    BitSet b = b();
                    if (b != null) {
                        int a = a(b);
                        ArrayList a2 = a(a, a(a, (int) this.c.length(), i));
                        if (a2 != null) {
                            ccVar = new cc(this.c, a2, new int[]{((a - this.b.a()) - 4) / 1500, ((r2 - this.b.a()) - 4) / 1500});
                            if (this.a != null) {
                                try {
                                    this.a.close();
                                } catch (Exception e3) {
                                }
                            }
                            if (ccVar != null) {
                                if (ccVar.c() > 100 && ccVar.c() < 5242880) {
                                    return ccVar;
                                }
                            }
                            this.c.delete();
                            this.c = null;
                            return null;
                        }
                        this.c.delete();
                        if (this.a != null) {
                            try {
                                this.a.close();
                            } catch (Exception e4) {
                            }
                        }
                        return null;
                    }
                    this.c.delete();
                    if (this.a != null) {
                        try {
                            this.a.close();
                        } catch (Exception e5) {
                        }
                    }
                    return null;
                } catch (FileNotFoundException e6) {
                    if (this.a != null) {
                        try {
                            this.a.close();
                        } catch (Exception e7) {
                        }
                    }
                    ccVar = null;
                    if (ccVar != null) {
                        return ccVar;
                    }
                    this.c.delete();
                    this.c = null;
                    return null;
                } catch (Exception e8) {
                    if (this.a != null) {
                        try {
                            this.a.close();
                        } catch (Exception e9) {
                        }
                    }
                    ccVar = null;
                    if (ccVar != null) {
                        return ccVar;
                    }
                    this.c.delete();
                    this.c = null;
                    return null;
                } catch (Throwable th) {
                    if (this.a != null) {
                        try {
                            this.a.close();
                        } catch (Exception e10) {
                        }
                    }
                }
            } else {
                return null;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final synchronized void a(com.loc.cc r5) {
        /*
        r4 = this;
        r0 = 0;
        monitor-enter(r4);
        monitor-enter(r4);	 Catch:{ all -> 0x00ac }
        r1 = r5.a;	 Catch:{ all -> 0x00a9 }
        r4.c = r1;	 Catch:{ all -> 0x00a9 }
        r1 = r4.c;	 Catch:{ all -> 0x00a9 }
        if (r1 == 0) goto L_0x0042;
    L_0x000b:
        r1 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r2 = r4.c;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r3 = "rw";
        r1.<init>(r2, r3);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r4.a = r1;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r1 = r4.b;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r1 = r1.a();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r1 = new byte[r1];	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r2 = com.loc.cg.c;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        if (r2 != 0) goto L_0x0045;
    L_0x0023:
        r2 = r4.a;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r2.read(r1);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r0 = com.loc.cd.b(r1);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r1 = r5.b();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        if (r1 != 0) goto L_0x005d;
    L_0x0032:
        r1 = r4.a;	 Catch:{ all -> 0x00a9 }
        if (r1 != 0) goto L_0x008e;
    L_0x0036:
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x00b7;
    L_0x003c:
        r0 = 0;
        r4.c = r0;	 Catch:{ all -> 0x00a9 }
        monitor-exit(r4);	 Catch:{ all -> 0x00a9 }
        monitor-exit(r4);
        return;
    L_0x0042:
        monitor-exit(r4);	 Catch:{ all -> 0x00a9 }
        monitor-exit(r4);
        return;
    L_0x0045:
        r2 = r4.a;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        if (r2 == 0) goto L_0x0023;
    L_0x0049:
        r2 = r4.a;	 Catch:{ IOException -> 0x00bd, FileNotFoundException -> 0x007a, all -> 0x00a3 }
        r2.close();	 Catch:{ IOException -> 0x00bd, FileNotFoundException -> 0x007a, all -> 0x00a3 }
        r0 = r4.a;	 Catch:{ all -> 0x00a9 }
        if (r0 != 0) goto L_0x0055;
    L_0x0052:
        monitor-exit(r4);	 Catch:{ all -> 0x00a9 }
        monitor-exit(r4);
        return;
    L_0x0055:
        r0 = r4.a;	 Catch:{ IOException -> 0x005b }
        r0.close();	 Catch:{ IOException -> 0x005b }
        goto L_0x0052;
    L_0x005b:
        r0 = move-exception;
        goto L_0x0052;
    L_0x005d:
        r1 = r5.b;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r2 = 0;
        r1 = r1[r2];	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
    L_0x0062:
        r2 = r5.b;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r3 = 1;
        r2 = r2[r3];	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        if (r1 <= r2) goto L_0x0087;
    L_0x0069:
        r1 = r4.a;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r2 = 0;
        r1.seek(r2);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r1 = r4.a;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r2 = com.loc.cd.a(r0);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r1.write(r2);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        goto L_0x0032;
    L_0x007a:
        r1 = move-exception;
        r1 = r4.a;	 Catch:{ all -> 0x00a9 }
        if (r1 == 0) goto L_0x0036;
    L_0x007f:
        r1 = r4.a;	 Catch:{ IOException -> 0x0085 }
        r1.close();	 Catch:{ IOException -> 0x0085 }
        goto L_0x0036;
    L_0x0085:
        r1 = move-exception;
        goto L_0x0036;
    L_0x0087:
        r2 = 0;
        r0.set(r1, r2);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0096, all -> 0x00a3 }
        r1 = r1 + 1;
        goto L_0x0062;
    L_0x008e:
        r1 = r4.a;	 Catch:{ IOException -> 0x0094 }
        r1.close();	 Catch:{ IOException -> 0x0094 }
        goto L_0x0036;
    L_0x0094:
        r1 = move-exception;
        goto L_0x0036;
    L_0x0096:
        r1 = move-exception;
        r1 = r4.a;	 Catch:{ all -> 0x00a9 }
        if (r1 == 0) goto L_0x0036;
    L_0x009b:
        r1 = r4.a;	 Catch:{ IOException -> 0x00a1 }
        r1.close();	 Catch:{ IOException -> 0x00a1 }
        goto L_0x0036;
    L_0x00a1:
        r1 = move-exception;
        goto L_0x0036;
    L_0x00a3:
        r0 = move-exception;
        r1 = r4.a;	 Catch:{ all -> 0x00a9 }
        if (r1 != 0) goto L_0x00af;
    L_0x00a8:
        throw r0;	 Catch:{ all -> 0x00a9 }
    L_0x00a9:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x00ac }
        throw r0;	 Catch:{ all -> 0x00ac }
    L_0x00ac:
        r0 = move-exception;
        monitor-exit(r4);
        throw r0;
    L_0x00af:
        r1 = r4.a;	 Catch:{ IOException -> 0x00b5 }
        r1.close();	 Catch:{ IOException -> 0x00b5 }
        goto L_0x00a8;
    L_0x00b5:
        r1 = move-exception;
        goto L_0x00a8;
    L_0x00b7:
        r0 = r4.c;	 Catch:{ all -> 0x00a9 }
        r0.delete();	 Catch:{ all -> 0x00a9 }
        goto L_0x003c;
    L_0x00bd:
        r2 = move-exception;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cz.a(com.loc.cc):void");
    }
}
