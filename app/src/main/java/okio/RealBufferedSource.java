package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

final class RealBufferedSource implements BufferedSource {
    public final Buffer buffer;
    private boolean closed;
    public final Source source;

    public RealBufferedSource(Source source, Buffer buffer) {
        if (source != null) {
            this.buffer = buffer;
            this.source = source;
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    public RealBufferedSource(Source source) {
        this(source, new Buffer());
    }

    public Buffer buffer() {
        return this.buffer;
    }

    public long read(Buffer sink, long byteCount) throws IOException {
        Object obj = null;
        if (sink != null) {
            if (byteCount >= 0) {
                obj = 1;
            }
            if (obj == null) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.buffer.size == 0 && this.source.read(this.buffer, 2048) == -1) {
                return -1;
            } else {
                return this.buffer.read(sink, Math.min(byteCount, this.buffer.size));
            }
        }
        throw new IllegalArgumentException("sink == null");
    }

    public boolean exhausted() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        } else if (this.buffer.exhausted() && this.source.read(this.buffer, 2048) == -1) {
            return true;
        } else {
            return false;
        }
    }

    public void require(long byteCount) throws IOException {
        if (!request(byteCount)) {
            throw new EOFException();
        }
    }

    public boolean request(long byteCount) throws IOException {
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
        } else if (this.closed) {
            throw new IllegalStateException("closed");
        } else {
            do {
                boolean z;
                if (this.buffer.size >= byteCount) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return true;
                }
            } while (this.source.read(this.buffer, 2048) != -1);
            return false;
        }
    }

    public byte readByte() throws IOException {
        require(1);
        return this.buffer.readByte();
    }

    public ByteString readByteString(long byteCount) throws IOException {
        require(byteCount);
        return this.buffer.readByteString(byteCount);
    }

    public byte[] readByteArray() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteArray();
    }

    public byte[] readByteArray(long byteCount) throws IOException {
        require(byteCount);
        return this.buffer.readByteArray(byteCount);
    }

    public String readUtf8LineStrict() throws IOException {
        long newline = indexOf((byte) 10);
        if (newline != -1) {
            return this.buffer.readUtf8Line(newline);
        }
        Buffer data = new Buffer();
        this.buffer.copyTo(data, 0, Math.min(32, this.buffer.size()));
        throw new EOFException("\\n not found: size=" + this.buffer.size() + " content=" + data.readByteString().hex() + "...");
    }

    public short readShort() throws IOException {
        require(2);
        return this.buffer.readShort();
    }

    public short readShortLe() throws IOException {
        require(2);
        return this.buffer.readShortLe();
    }

    public int readInt() throws IOException {
        require(4);
        return this.buffer.readInt();
    }

    public int readIntLe() throws IOException {
        require(4);
        return this.buffer.readIntLe();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readDecimalLong() throws java.io.IOException {
        /*
        r8 = this;
        r1 = 0;
    L_0x0001:
        r2 = r1 + 1;
        r2 = (long) r2;
        r2 = r8.request(r2);
        if (r2 == 0) goto L_0x0017;
    L_0x000a:
        r2 = r8.buffer;
        r4 = (long) r1;
        r0 = r2.getByte(r4);
        r2 = 48;
        if (r0 >= r2) goto L_0x0020;
    L_0x0015:
        if (r1 == 0) goto L_0x0027;
    L_0x0017:
        if (r1 == 0) goto L_0x002c;
    L_0x0019:
        r2 = r8.buffer;
        r2 = r2.readDecimalLong();
        return r2;
    L_0x0020:
        r2 = 57;
        if (r0 > r2) goto L_0x0015;
    L_0x0024:
        r1 = r1 + 1;
        goto L_0x0001;
    L_0x0027:
        r2 = 45;
        if (r0 != r2) goto L_0x0017;
    L_0x002b:
        goto L_0x0024;
    L_0x002c:
        r2 = new java.lang.NumberFormatException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Expected leading [0-9] or '-' character but was 0x";
        r3 = r3.append(r4);
        r4 = r8.buffer;
        r6 = 0;
        r4 = r4.getByte(r6);
        r4 = java.lang.Integer.toHexString(r4);
        r3 = r3.append(r4);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.RealBufferedSource.readDecimalLong():long");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readHexadecimalUnsignedLong() throws java.io.IOException {
        /*
        r8 = this;
        r1 = 0;
    L_0x0001:
        r2 = r1 + 1;
        r2 = (long) r2;
        r2 = r8.request(r2);
        if (r2 == 0) goto L_0x001d;
    L_0x000a:
        r2 = r8.buffer;
        r4 = (long) r1;
        r0 = r2.getByte(r4);
        r2 = 48;
        if (r0 >= r2) goto L_0x0026;
    L_0x0015:
        r2 = 97;
        if (r0 >= r2) goto L_0x002d;
    L_0x0019:
        r2 = 65;
        if (r0 >= r2) goto L_0x0032;
    L_0x001d:
        if (r1 == 0) goto L_0x0037;
    L_0x001f:
        r2 = r8.buffer;
        r2 = r2.readHexadecimalUnsignedLong();
        return r2;
    L_0x0026:
        r2 = 57;
        if (r0 > r2) goto L_0x0015;
    L_0x002a:
        r1 = r1 + 1;
        goto L_0x0001;
    L_0x002d:
        r2 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        if (r0 > r2) goto L_0x0019;
    L_0x0031:
        goto L_0x002a;
    L_0x0032:
        r2 = 70;
        if (r0 > r2) goto L_0x001d;
    L_0x0036:
        goto L_0x002a;
    L_0x0037:
        r2 = new java.lang.NumberFormatException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Expected leading [0-9a-fA-F] character but was 0x";
        r3 = r3.append(r4);
        r4 = r8.buffer;
        r6 = 0;
        r4 = r4.getByte(r6);
        r4 = java.lang.Integer.toHexString(r4);
        r3 = r3.append(r4);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.RealBufferedSource.readHexadecimalUnsignedLong():long");
    }

    public void skip(long byteCount) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            Object obj;
            if (byteCount <= 0) {
                obj = 1;
            } else {
                obj = null;
            }
            if (obj == null) {
                if (this.buffer.size == 0 && this.source.read(this.buffer, 2048) == -1) {
                    break;
                }
                long toSkip = Math.min(byteCount, this.buffer.size());
                this.buffer.skip(toSkip);
                byteCount -= toSkip;
            } else {
                return;
            }
        }
        throw new EOFException();
    }

    public long indexOf(byte b) throws IOException {
        return indexOf(b, 0);
    }

    public long indexOf(byte b, long fromIndex) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        do {
            Object obj;
            if (fromIndex < this.buffer.size) {
                obj = 1;
            } else {
                obj = null;
            }
            if (obj != null) {
                do {
                    long index = this.buffer.indexOf(b, fromIndex);
                    if (index != -1) {
                        return index;
                    }
                    fromIndex = this.buffer.size;
                } while (this.source.read(this.buffer, 2048) != -1);
                return -1;
            }
        } while (this.source.read(this.buffer, 2048) != -1);
        return -1;
    }

    public InputStream inputStream() {
        return new InputStream() {
            public int read() throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                } else if (RealBufferedSource.this.buffer.size == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048) == -1) {
                    return -1;
                } else {
                    return RealBufferedSource.this.buffer.readByte() & 255;
                }
            }

            public int read(byte[] data, int offset, int byteCount) throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                Util.checkOffsetAndCount((long) data.length, (long) offset, (long) byteCount);
                if (RealBufferedSource.this.buffer.size == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.read(data, offset, byteCount);
            }

            public int available() throws IOException {
                if (!RealBufferedSource.this.closed) {
                    return (int) Math.min(RealBufferedSource.this.buffer.size, 2147483647L);
                }
                throw new IOException("closed");
            }

            public void close() throws IOException {
                RealBufferedSource.this.close();
            }

            public String toString() {
                return RealBufferedSource.this + ".inputStream()";
            }
        };
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.source.close();
            this.buffer.clear();
        }
    }

    public Timeout timeout() {
        return this.source.timeout();
    }

    public String toString() {
        return "buffer(" + this.source + ")";
    }
}
