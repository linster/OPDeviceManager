package com.loc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* compiled from: BinaryRandomAccessFile */
public class be {
    private ByteArrayInputStream a;
    private long b;
    private boolean c;
    private RandomAccessFile d;
    private boolean e;
    private final byte[] f;
    private a g;
    private String h;

    /* compiled from: BinaryRandomAccessFile */
    public static class a {
        public boolean a;
        public boolean b;

        public a() {
            this.a = true;
            this.b = true;
        }
    }

    public be(File file, a aVar) throws IOException, FileNotFoundException, OutOfMemoryError {
        this.c = false;
        this.d = null;
        this.e = false;
        this.f = new byte[8];
        this.h = null;
        if (aVar != null) {
            if (!aVar.a) {
                this.d = new RandomAccessFile(file, "r");
                this.c = true;
            } else {
                byte[] a = br.a(file);
                this.a = new ByteArrayInputStream(a);
                this.b = (long) a.length;
                this.c = false;
                this.h = file.getAbsolutePath();
            }
            this.g = aVar;
        }
    }

    public boolean a() {
        if (this.g != null) {
            return this.g.a;
        }
        return false;
    }

    public void b() throws IOException {
        synchronized (this) {
            if (this.c) {
                if (this.d != null) {
                    this.d.close();
                    this.d = null;
                }
            } else if (this.a != null) {
                this.a.close();
                this.a = null;
            }
            this.e = true;
        }
    }

    protected void finalize() throws Throwable {
        b();
        super.finalize();
    }

    public void a(long j) throws IOException {
        Object obj = null;
        if (j >= 0) {
            obj = 1;
        }
        if (obj == null) {
            throw new IOException("offset < 0: " + j);
        }
        h();
        if (this.c) {
            this.d.seek(j);
            return;
        }
        this.a.reset();
        this.a.skip(j);
    }

    public final long c() throws IOException {
        h();
        if (this.c) {
            return this.d.readLong();
        }
        this.a.read(this.f);
        return br.b(this.f);
    }

    public final int d() throws IOException {
        h();
        if (this.c) {
            return this.d.readUnsignedShort();
        }
        this.a.read(this.f, 0, 2);
        return br.c(this.f);
    }

    public final int e() throws IOException {
        h();
        if (this.c) {
            return this.d.readInt();
        }
        this.a.read(this.f, 0, 4);
        return br.d(this.f);
    }

    public final int f() throws IOException {
        h();
        if (this.c) {
            return this.d.readUnsignedByte();
        }
        return this.a.read();
    }

    public long g() throws IOException {
        if (this.e) {
            throw new IOException("file closed");
        } else if (this.c) {
            return this.d.length();
        } else {
            return this.b;
        }
    }

    private void h() throws IOException {
        if (this.e) {
            throw new IOException("file closed");
        }
    }
}
