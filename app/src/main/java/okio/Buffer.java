package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Buffer implements BufferedSource, BufferedSink, Cloneable {
    private static final byte[] DIGITS;
    Segment head;
    long size;

    static {
        DIGITS = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
    }

    public long size() {
        return this.size;
    }

    public Buffer buffer() {
        return this;
    }

    public OutputStream outputStream() {
        return new OutputStream() {
            public void write(int b) {
                Buffer.this.writeByte((byte) b);
            }

            public void write(byte[] data, int offset, int byteCount) {
                Buffer.this.write(data, offset, byteCount);
            }

            public void flush() {
            }

            public void close() {
            }

            public String toString() {
                return this + ".outputStream()";
            }
        };
    }

    public Buffer emitCompleteSegments() {
        return this;
    }

    public BufferedSink emit() {
        return this;
    }

    public boolean exhausted() {
        return this.size == 0;
    }

    public void require(long byteCount) throws EOFException {
        if ((this.size >= byteCount ? 1 : null) == null) {
            throw new EOFException();
        }
    }

    public InputStream inputStream() {
        return new InputStream() {
            public int read() {
                return ((Buffer.this.size > 0 ? 1 : (Buffer.this.size == 0 ? 0 : -1)) <= 0 ? 1 : null) == null ? Buffer.this.readByte() & 255 : -1;
            }

            public int read(byte[] sink, int offset, int byteCount) {
                return Buffer.this.read(sink, offset, byteCount);
            }

            public int available() {
                return (int) Math.min(Buffer.this.size, 2147483647L);
            }

            public void close() {
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }
        };
    }

    public Buffer copyTo(Buffer out, long offset, long byteCount) {
        if (out != null) {
            Util.checkOffsetAndCount(this.size, offset, byteCount);
            if (byteCount == 0) {
                return this;
            }
            out.size += byteCount;
            Segment s = this.head;
            while (true) {
                if ((offset < ((long) (s.limit - s.pos)) ? 1 : null) != null) {
                    break;
                }
                offset -= (long) (s.limit - s.pos);
                s = s.next;
            }
            while (true) {
                if ((byteCount <= 0 ? 1 : null) != null) {
                    return this;
                }
                Segment copy = new Segment(s);
                copy.pos = (int) (((long) copy.pos) + offset);
                copy.limit = Math.min(copy.pos + ((int) byteCount), copy.limit);
                if (out.head != null) {
                    out.head.prev.push(copy);
                } else {
                    copy.prev = copy;
                    copy.next = copy;
                    out.head = copy;
                }
                byteCount -= (long) (copy.limit - copy.pos);
                offset = 0;
                s = s.next;
            }
        } else {
            throw new IllegalArgumentException("out == null");
        }
    }

    public long completeSegmentByteCount() {
        long result = this.size;
        if (result == 0) {
            return 0;
        }
        Segment tail = this.head.prev;
        if (tail.limit < 2048 && tail.owner) {
            result -= (long) (tail.limit - tail.pos);
        }
        return result;
    }

    public byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        int pos2 = pos + 1;
        byte b = segment.data[pos];
        this.size--;
        if (pos2 != limit) {
            segment.pos = pos2;
        } else {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return b;
    }

    public byte getByte(long pos) {
        Util.checkOffsetAndCount(this.size, pos, 1);
        Segment s = this.head;
        while (true) {
            int segmentByteCount = s.limit - s.pos;
            if ((pos >= ((long) segmentByteCount) ? 1 : null) == null) {
                return s.data[s.pos + ((int) pos)];
            }
            pos -= (long) segmentByteCount;
            s = s.next;
        }
    }

    public short readShort() {
        if ((this.size >= 2 ? 1 : null) == null) {
            throw new IllegalStateException("size < 2: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        pos = pos2 + 1;
        int s = ((data[pos] & 255) << 8) | (data[pos2] & 255);
        this.size -= 2;
        if (pos != limit) {
            segment.pos = pos;
        } else {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return (short) s;
    }

    public int readInt() {
        if ((this.size >= 4 ? 1 : null) == null) {
            throw new IllegalStateException("size < 4: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 4) {
            return ((((readByte() & 255) << 24) | ((readByte() & 255) << 16)) | ((readByte() & 255) << 8)) | (readByte() & 255);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        pos = pos2 + 1;
        pos2 = pos + 1;
        pos = pos2 + 1;
        int i = ((((data[pos] & 255) << 24) | ((data[pos2] & 255) << 16)) | ((data[pos] & 255) << 8)) | (data[pos2] & 255);
        this.size -= 4;
        if (pos != limit) {
            segment.pos = pos;
        } else {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return i;
    }

    public short readShortLe() {
        return Util.reverseBytesShort(readShort());
    }

    public int readIntLe() {
        return Util.reverseBytesInt(readInt());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readDecimalLong() {
        /*
        r22 = this;
        r0 = r22;
        r0 = r0.size;
        r18 = r0;
        r20 = 0;
        r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1));
        if (r18 != 0) goto L_0x0015;
    L_0x000c:
        r18 = new java.lang.IllegalStateException;
        r19 = "size == 0";
        r18.<init>(r19);
        throw r18;
    L_0x0015:
        r16 = 0;
        r14 = 0;
        r10 = 0;
        r8 = 0;
        r12 = -7;
    L_0x001c:
        r0 = r22;
        r15 = r0.head;
        r6 = r15.data;
        r11 = r15.pos;
        r9 = r15.limit;
    L_0x0026:
        if (r11 < r9) goto L_0x0047;
    L_0x0028:
        if (r11 == r9) goto L_0x00f4;
    L_0x002a:
        r15.pos = r11;
    L_0x002c:
        if (r8 == 0) goto L_0x0103;
    L_0x002e:
        r0 = r22;
        r0 = r0.size;
        r18 = r0;
        r0 = (long) r14;
        r20 = r0;
        r18 = r18 - r20;
        r0 = r18;
        r2 = r22;
        r2.size = r0;
        if (r10 != 0) goto L_0x0046;
    L_0x0041:
        r0 = r16;
        r0 = -r0;
        r16 = r0;
    L_0x0046:
        return r16;
    L_0x0047:
        r4 = r6[r11];
        r18 = 48;
        r0 = r18;
        if (r4 >= r0) goto L_0x0059;
    L_0x004f:
        r18 = 45;
        r0 = r18;
        if (r4 == r0) goto L_0x00ce;
    L_0x0055:
        if (r14 == 0) goto L_0x00d6;
    L_0x0057:
        r8 = 1;
        goto L_0x0028;
    L_0x0059:
        r18 = 57;
        r0 = r18;
        if (r4 > r0) goto L_0x004f;
    L_0x005f:
        r7 = 48 - r4;
        r18 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r18 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r18 >= 0) goto L_0x00b5;
    L_0x006a:
        r18 = 1;
    L_0x006c:
        if (r18 != 0) goto L_0x0082;
    L_0x006e:
        r18 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r18 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r18 != 0) goto L_0x00bf;
    L_0x0077:
        r0 = (long) r7;
        r18 = r0;
        r18 = (r18 > r12 ? 1 : (r18 == r12 ? 0 : -1));
        if (r18 < 0) goto L_0x00b8;
    L_0x007e:
        r18 = 1;
    L_0x0080:
        if (r18 != 0) goto L_0x00bf;
    L_0x0082:
        r18 = new okio.Buffer;
        r18.<init>();
        r0 = r18;
        r1 = r16;
        r18 = r0.writeDecimalLong(r1);
        r0 = r18;
        r5 = r0.writeByte(r4);
        if (r10 == 0) goto L_0x00bb;
    L_0x0097:
        r18 = new java.lang.NumberFormatException;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "Number too large: ";
        r19 = r19.append(r20);
        r20 = r5.readUtf8();
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.<init>(r19);
        throw r18;
    L_0x00b5:
        r18 = 0;
        goto L_0x006c;
    L_0x00b8:
        r18 = 0;
        goto L_0x0080;
    L_0x00bb:
        r5.readByte();
        goto L_0x0097;
    L_0x00bf:
        r18 = 10;
        r16 = r16 * r18;
        r0 = (long) r7;
        r18 = r0;
        r16 = r16 + r18;
    L_0x00c8:
        r11 = r11 + 1;
        r14 = r14 + 1;
        goto L_0x0026;
    L_0x00ce:
        if (r14 != 0) goto L_0x0055;
    L_0x00d0:
        r10 = 1;
        r18 = 1;
        r12 = r12 - r18;
        goto L_0x00c8;
    L_0x00d6:
        r18 = new java.lang.NumberFormatException;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "Expected leading [0-9] or '-' character but was 0x";
        r19 = r19.append(r20);
        r20 = java.lang.Integer.toHexString(r4);
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.<init>(r19);
        throw r18;
    L_0x00f4:
        r18 = r15.pop();
        r0 = r18;
        r1 = r22;
        r1.head = r0;
        okio.SegmentPool.recycle(r15);
        goto L_0x002c;
    L_0x0103:
        r0 = r22;
        r0 = r0.head;
        r18 = r0;
        if (r18 == 0) goto L_0x002e;
    L_0x010b:
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readDecimalLong():long");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readHexadecimalUnsignedLong() {
        /*
        r18 = this;
        r0 = r18;
        r14 = r0.size;
        r16 = 0;
        r11 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r11 != 0) goto L_0x0013;
    L_0x000a:
        r11 = new java.lang.IllegalStateException;
        r14 = "size == 0";
        r11.<init>(r14);
        throw r11;
    L_0x0013:
        r12 = 0;
        r9 = 0;
        r6 = 0;
    L_0x0017:
        r0 = r18;
        r10 = r0.head;
        r4 = r10.data;
        r8 = r10.pos;
        r7 = r10.limit;
    L_0x0021:
        if (r8 < r7) goto L_0x0037;
    L_0x0023:
        if (r8 == r7) goto L_0x00bd;
    L_0x0025:
        r10.pos = r8;
    L_0x0027:
        if (r6 == 0) goto L_0x00ca;
    L_0x0029:
        r0 = r18;
        r14 = r0.size;
        r0 = (long) r9;
        r16 = r0;
        r14 = r14 - r16;
        r0 = r18;
        r0.size = r14;
        return r12;
    L_0x0037:
        r2 = r4[r8];
        r11 = 48;
        if (r2 >= r11) goto L_0x0049;
    L_0x003d:
        r11 = 97;
        if (r2 >= r11) goto L_0x0083;
    L_0x0041:
        r11 = 65;
        if (r2 >= r11) goto L_0x008c;
    L_0x0045:
        if (r9 == 0) goto L_0x0095;
    L_0x0047:
        r6 = 1;
        goto L_0x0023;
    L_0x0049:
        r11 = 57;
        if (r2 > r11) goto L_0x003d;
    L_0x004d:
        r5 = r2 + -48;
    L_0x004f:
        r14 = -1152921504606846976; // 0xf000000000000000 float:0.0 double:-3.105036184601418E231;
        r14 = r14 & r12;
        r16 = 0;
        r11 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r11 == 0) goto L_0x00b3;
    L_0x0058:
        r11 = new okio.Buffer;
        r11.<init>();
        r11 = r11.writeHexadecimalUnsignedLong(r12);
        r3 = r11.writeByte(r2);
        r11 = new java.lang.NumberFormatException;
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = "Number too large: ";
        r14 = r14.append(r15);
        r15 = r3.readUtf8();
        r14 = r14.append(r15);
        r14 = r14.toString();
        r11.<init>(r14);
        throw r11;
    L_0x0083:
        r11 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        if (r2 > r11) goto L_0x0041;
    L_0x0087:
        r11 = r2 + -97;
        r5 = r11 + 10;
        goto L_0x004f;
    L_0x008c:
        r11 = 70;
        if (r2 > r11) goto L_0x0045;
    L_0x0090:
        r11 = r2 + -65;
        r5 = r11 + 10;
        goto L_0x004f;
    L_0x0095:
        r11 = new java.lang.NumberFormatException;
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = "Expected leading [0-9a-fA-F] character but was 0x";
        r14 = r14.append(r15);
        r15 = java.lang.Integer.toHexString(r2);
        r14 = r14.append(r15);
        r14 = r14.toString();
        r11.<init>(r14);
        throw r11;
    L_0x00b3:
        r11 = 4;
        r12 = r12 << r11;
        r14 = (long) r5;
        r12 = r12 | r14;
        r8 = r8 + 1;
        r9 = r9 + 1;
        goto L_0x0021;
    L_0x00bd:
        r11 = r10.pop();
        r0 = r18;
        r0.head = r11;
        okio.SegmentPool.recycle(r10);
        goto L_0x0027;
    L_0x00ca:
        r0 = r18;
        r11 = r0.head;
        if (r11 == 0) goto L_0x0029;
    L_0x00d0:
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readHexadecimalUnsignedLong():long");
    }

    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    public ByteString readByteString(long byteCount) throws EOFException {
        return new ByteString(readByteArray(byteCount));
    }

    public String readUtf8() {
        try {
            return readString(this.size, Util.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readUtf8(long byteCount) throws EOFException {
        return readString(byteCount, Util.UTF_8);
    }

    public String readString(long byteCount, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        if (charset != null) {
            Object obj;
            if (byteCount <= 2147483647L) {
                obj = 1;
            } else {
                obj = null;
            }
            if (obj == null) {
                throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
            } else if (byteCount == 0) {
                return "";
            } else {
                Segment s = this.head;
                if (((long) s.pos) + byteCount <= ((long) s.limit)) {
                    obj = 1;
                } else {
                    obj = null;
                }
                if (obj == null) {
                    return new String(readByteArray(byteCount), charset);
                }
                String result = new String(s.data, s.pos, (int) byteCount, charset);
                s.pos = (int) (((long) s.pos) + byteCount);
                this.size -= byteCount;
                if (s.pos == s.limit) {
                    this.head = s.pop();
                    SegmentPool.recycle(s);
                }
                return result;
            }
        }
        throw new IllegalArgumentException("charset == null");
    }

    public String readUtf8LineStrict() throws EOFException {
        long newline = indexOf((byte) 10);
        if (newline != -1) {
            return readUtf8Line(newline);
        }
        Buffer data = new Buffer();
        copyTo(data, 0, Math.min(32, this.size));
        throw new EOFException("\\n not found: size=" + size() + " content=" + data.readByteString().hex() + "...");
    }

    String readUtf8Line(long newline) throws EOFException {
        if ((newline <= 0 ? 1 : null) == null && getByte(newline - 1) == 13) {
            String result = readUtf8(newline - 1);
            skip(2);
            return result;
        }
        result = readUtf8(newline);
        skip(1);
        return result;
    }

    public byte[] readByteArray() {
        try {
            return readByteArray(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] readByteArray(long byteCount) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        if ((byteCount <= 2147483647L ? 1 : null) == null) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
        }
        byte[] result = new byte[((int) byteCount)];
        readFully(result);
        return result;
    }

    public void readFully(byte[] sink) throws EOFException {
        int offset = 0;
        while (offset < sink.length) {
            int read = read(sink, offset, sink.length - offset);
            if (read != -1) {
                offset += read;
            } else {
                throw new EOFException();
            }
        }
    }

    public int read(byte[] sink, int offset, int byteCount) {
        Util.checkOffsetAndCount((long) sink.length, (long) offset, (long) byteCount);
        Segment s = this.head;
        if (s == null) {
            return -1;
        }
        int toCopy = Math.min(byteCount, s.limit - s.pos);
        System.arraycopy(s.data, s.pos, sink, offset, toCopy);
        s.pos += toCopy;
        this.size -= (long) toCopy;
        if (s.pos == s.limit) {
            this.head = s.pop();
            SegmentPool.recycle(s);
        }
        return toCopy;
    }

    public void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public void skip(long byteCount) throws EOFException {
        while (true) {
            if ((byteCount <= 0 ? 1 : null) == null) {
                if (this.head == null) {
                    break;
                }
                int toSkip = (int) Math.min(byteCount, (long) (this.head.limit - this.head.pos));
                this.size -= (long) toSkip;
                byteCount -= (long) toSkip;
                Segment segment = this.head;
                segment.pos += toSkip;
                if (this.head.pos == this.head.limit) {
                    Segment toRecycle = this.head;
                    this.head = toRecycle.pop();
                    SegmentPool.recycle(toRecycle);
                }
            } else {
                return;
            }
        }
        throw new EOFException();
    }

    public Buffer write(ByteString byteString) {
        if (byteString != null) {
            byteString.write(this);
            return this;
        }
        throw new IllegalArgumentException("byteString == null");
    }

    public Buffer writeUtf8(String string) {
        if (string != null) {
            int length = string.length();
            int i = 0;
            while (i < length) {
                int i2;
                int c = string.charAt(i);
                if (c < 128) {
                    Segment tail = writableSegment(1);
                    byte[] data = tail.data;
                    int segmentOffset = tail.limit - i;
                    int runLimit = Math.min(length, 2048 - segmentOffset);
                    i2 = i + 1;
                    data[segmentOffset + i] = (byte) ((byte) c);
                    i = i2;
                    while (i < runLimit) {
                        c = string.charAt(i);
                        if (c >= 128) {
                            break;
                        }
                        i2 = i + 1;
                        data[segmentOffset + i] = (byte) ((byte) c);
                        i = i2;
                    }
                    int runSize = (i + segmentOffset) - tail.limit;
                    tail.limit += runSize;
                    this.size += (long) runSize;
                    i2 = i;
                } else if (c < 2048) {
                    writeByte((c >> 6) | 192);
                    writeByte((c & 63) | 128);
                    i2 = i + 1;
                } else if (c >= 55296 && c <= 57343) {
                    int low;
                    if (i + 1 >= length) {
                        low = 0;
                    } else {
                        low = string.charAt(i + 1);
                    }
                    if (c <= 56319 && low >= 56320 && low <= 57343) {
                        int codePoint = 65536 + (((-55297 & c) << 10) | (-56321 & low));
                        writeByte((codePoint >> 18) | 240);
                        writeByte(((codePoint >> 12) & 63) | 128);
                        writeByte(((codePoint >> 6) & 63) | 128);
                        writeByte((codePoint & 63) | 128);
                        i2 = i + 2;
                    } else {
                        writeByte(63);
                        i++;
                    }
                } else {
                    writeByte((c >> 12) | 224);
                    writeByte(((c >> 6) & 63) | 128);
                    writeByte((c & 63) | 128);
                    i2 = i + 1;
                }
                i = i2;
            }
            return this;
        }
        throw new IllegalArgumentException("string == null");
    }

    public Buffer writeString(String string, Charset charset) {
        if (string == null) {
            throw new IllegalArgumentException("string == null");
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(Util.UTF_8)) {
            return writeUtf8(string);
        } else {
            byte[] data = string.getBytes(charset);
            return write(data, 0, data.length);
        }
    }

    public Buffer write(byte[] source) {
        if (source != null) {
            return write(source, 0, source.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    public Buffer write(byte[] source, int offset, int byteCount) {
        if (source != null) {
            Util.checkOffsetAndCount((long) source.length, (long) offset, (long) byteCount);
            int limit = offset + byteCount;
            while (offset < limit) {
                Segment tail = writableSegment(1);
                int toCopy = Math.min(limit - offset, 2048 - tail.limit);
                System.arraycopy(source, offset, tail.data, tail.limit, toCopy);
                offset += toCopy;
                tail.limit += toCopy;
            }
            this.size += (long) byteCount;
            return this;
        }
        throw new IllegalArgumentException("source == null");
    }

    public long writeAll(Source source) throws IOException {
        if (source != null) {
            long totalBytesRead = 0;
            while (true) {
                long readCount = source.read(this, 2048);
                if (readCount == -1) {
                    return totalBytesRead;
                }
                totalBytesRead += readCount;
            }
        } else {
            throw new IllegalArgumentException("source == null");
        }
    }

    public Buffer writeByte(int b) {
        Segment tail = writableSegment(1);
        byte[] bArr = tail.data;
        int i = tail.limit;
        tail.limit = i + 1;
        bArr[i] = (byte) ((byte) b);
        this.size++;
        return this;
    }

    public Buffer writeShort(int s) {
        Segment tail = writableSegment(2);
        byte[] data = tail.data;
        int i = tail.limit;
        int i2 = i + 1;
        data[i] = (byte) ((byte) ((s >>> 8) & 255));
        i = i2 + 1;
        data[i2] = (byte) ((byte) (s & 255));
        tail.limit = i;
        this.size += 2;
        return this;
    }

    public Buffer writeInt(int i) {
        Segment tail = writableSegment(4);
        byte[] data = tail.data;
        int i2 = tail.limit;
        int i3 = i2 + 1;
        data[i2] = (byte) ((byte) ((i >>> 24) & 255));
        i2 = i3 + 1;
        data[i3] = (byte) ((byte) ((i >>> 16) & 255));
        i3 = i2 + 1;
        data[i2] = (byte) ((byte) ((i >>> 8) & 255));
        i2 = i3 + 1;
        data[i3] = (byte) ((byte) (i & 255));
        tail.limit = i2;
        this.size += 4;
        return this;
    }

    public Buffer writeDecimalLong(long v) {
        if (v == 0) {
            return writeByte(48);
        }
        Object obj;
        int width;
        boolean negative = false;
        if ((v >= 0 ? 1 : null) == null) {
            v = -v;
            if ((v >= 0 ? 1 : null) == null) {
                return writeUtf8("-9223372036854775808");
            }
            negative = true;
        }
        if (v >= 100000000) {
            obj = 1;
        } else {
            obj = null;
        }
        if (obj == null) {
            if (v >= 10000) {
                obj = 1;
            } else {
                obj = null;
            }
            if (obj == null) {
                if (v >= 100) {
                    obj = 1;
                } else {
                    obj = null;
                }
                if (obj == null) {
                    if (v >= 10) {
                        obj = 1;
                    } else {
                        obj = null;
                    }
                    width = obj == null ? 1 : 2;
                } else {
                    width = ((v > 1000 ? 1 : (v == 1000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 3 : 4;
                }
            } else {
                if ((v >= 1000000 ? 1 : null) == null) {
                    width = ((v > 100000 ? 1 : (v == 100000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 5 : 6;
                } else {
                    width = ((v > 10000000 ? 1 : (v == 10000000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 7 : 8;
                }
            }
        } else {
            if ((v >= 1000000000000L ? 1 : null) == null) {
                if ((v >= 10000000000L ? 1 : null) == null) {
                    width = ((v > 1000000000 ? 1 : (v == 1000000000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 9 : 10;
                } else {
                    width = ((v > 100000000000L ? 1 : (v == 100000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 11 : 12;
                }
            } else {
                if ((v >= 1000000000000000L ? 1 : null) == null) {
                    if ((v >= 10000000000000L ? 1 : null) == null) {
                        width = 13;
                    } else {
                        width = ((v > 100000000000000L ? 1 : (v == 100000000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 14 : 15;
                    }
                } else {
                    if ((v >= 100000000000000000L ? 1 : null) == null) {
                        width = ((v > 10000000000000000L ? 1 : (v == 10000000000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 16 : 17;
                    } else {
                        width = ((v > 1000000000000000000L ? 1 : (v == 1000000000000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 18 : 19;
                    }
                }
            }
        }
        if (negative) {
            width++;
        }
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int pos = tail.limit + width;
        while (v != 0) {
            pos--;
            data[pos] = (byte) DIGITS[(int) (v % 10)];
            v /= 10;
        }
        if (negative) {
            data[pos - 1] = (byte) 45;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    public Buffer writeHexadecimalUnsignedLong(long v) {
        if (v == 0) {
            return writeByte(48);
        }
        int width = (Long.numberOfTrailingZeros(Long.highestOneBit(v)) / 4) + 1;
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int start = tail.limit;
        for (int pos = (tail.limit + width) - 1; pos >= start; pos--) {
            data[pos] = (byte) DIGITS[(int) (15 & v)];
            v >>>= 4;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    Segment writableSegment(int minimumCapacity) {
        if (minimumCapacity < 1 || minimumCapacity > 2048) {
            throw new IllegalArgumentException();
        } else if (this.head != null) {
            Segment tail = this.head.prev;
            if (tail.limit + minimumCapacity > 2048 || !tail.owner) {
                tail = tail.push(SegmentPool.take());
            }
            return tail;
        } else {
            this.head = SegmentPool.take();
            Segment segment = this.head;
            Segment segment2 = this.head;
            Segment segment3 = this.head;
            segment2.prev = segment3;
            segment.next = segment3;
            return segment3;
        }
    }

    public void write(Buffer source, long byteCount) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        } else if (source != this) {
            Util.checkOffsetAndCount(source.size, 0, byteCount);
            while (true) {
                if ((byteCount <= 0 ? 1 : null) == null) {
                    if ((byteCount >= ((long) (source.head.limit - source.head.pos)) ? 1 : null) == null) {
                        Segment tail = this.head == null ? null : this.head.prev;
                        if (tail != null && tail.owner) {
                            Object obj;
                            if ((byteCount + ((long) tail.limit)) - ((long) (!tail.shared ? tail.pos : 0)) > 2048) {
                                obj = 1;
                            } else {
                                obj = null;
                            }
                            if (obj == null) {
                                source.head.writeTo(tail, (int) byteCount);
                                source.size -= byteCount;
                                this.size += byteCount;
                                return;
                            }
                        }
                        source.head = source.head.split((int) byteCount);
                    }
                    Segment segmentToMove = source.head;
                    long movedByteCount = (long) (segmentToMove.limit - segmentToMove.pos);
                    source.head = segmentToMove.pop();
                    if (this.head != null) {
                        this.head.prev.push(segmentToMove).compact();
                    } else {
                        this.head = segmentToMove;
                        Segment segment = this.head;
                        Segment segment2 = this.head;
                        Segment segment3 = this.head;
                        segment2.prev = segment3;
                        segment.next = segment3;
                    }
                    source.size -= movedByteCount;
                    this.size += movedByteCount;
                    byteCount -= movedByteCount;
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("source == this");
        }
    }

    public long read(Buffer sink, long byteCount) {
        Object obj = 1;
        if (sink != null) {
            Object obj2;
            if (byteCount >= 0) {
                obj2 = 1;
            } else {
                obj2 = null;
            }
            if (obj2 == null) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.size == 0) {
                return -1;
            } else {
                if (byteCount > this.size) {
                    obj = null;
                }
                if (obj == null) {
                    byteCount = this.size;
                }
                sink.write(this, byteCount);
                return byteCount;
            }
        }
        throw new IllegalArgumentException("sink == null");
    }

    public long indexOf(byte b) {
        return indexOf(b, 0);
    }

    public long indexOf(byte b, long fromIndex) {
        if ((fromIndex >= 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment s = this.head;
        if (s == null) {
            return -1;
        }
        long offset = 0;
        do {
            int segmentByteCount = s.limit - s.pos;
            if ((fromIndex < ((long) segmentByteCount) ? 1 : null) == null) {
                fromIndex -= (long) segmentByteCount;
            } else {
                byte[] data = s.data;
                long pos = ((long) s.pos) + fromIndex;
                long limit = (long) s.limit;
                while (true) {
                    if ((pos >= limit ? 1 : null) != null) {
                        break;
                    } else if (data[(int) pos] == b) {
                        return (offset + pos) - ((long) s.pos);
                    } else {
                        pos++;
                    }
                }
                fromIndex = 0;
            }
            offset += (long) segmentByteCount;
            s = s.next;
        } while (s != this.head);
        return -1;
    }

    public void flush() {
    }

    public void close() {
    }

    public Timeout timeout() {
        return Timeout.NONE;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Buffer)) {
            return false;
        }
        Buffer that = (Buffer) o;
        if (this.size != that.size) {
            return false;
        }
        if (this.size == 0) {
            return true;
        }
        Segment sa = this.head;
        Segment sb = that.head;
        int posA = sa.pos;
        int posB = sb.pos;
        long pos = 0;
        while (true) {
            if ((pos >= this.size ? 1 : null) != null) {
                return true;
            }
            long count = (long) Math.min(sa.limit - posA, sb.limit - posB);
            int i = 0;
            int posB2 = posB;
            int posA2 = posA;
            while (true) {
                if ((((long) i) >= count ? 1 : null) != null) {
                    break;
                }
                posA = posA2 + 1;
                posB = posB2 + 1;
                if (sa.data[posA2] != sb.data[posB2]) {
                    return false;
                }
                i++;
                posB2 = posB;
                posA2 = posA;
            }
            if (posA2 != sa.limit) {
                posA = posA2;
            } else {
                sa = sa.next;
                posA = sa.pos;
            }
            if (posB2 != sb.limit) {
                posB = posB2;
            } else {
                sb = sb.next;
                posB = sb.pos;
            }
            pos += count;
        }
    }

    public int hashCode() {
        Segment s = this.head;
        if (s == null) {
            return 0;
        }
        int result = 1;
        do {
            for (int pos = s.pos; pos < s.limit; pos++) {
                result = (result * 31) + s.data[pos];
            }
            s = s.next;
        } while (s != this.head);
        return result;
    }

    public String toString() {
        if (this.size == 0) {
            return "Buffer[size=0]";
        }
        int i;
        if (this.size > 16) {
            i = 1;
        } else {
            i = 0;
        }
        if (i == 0) {
            ByteString data = clone().readByteString();
            return String.format("Buffer[size=%s data=%s]", new Object[]{Long.valueOf(this.size), data.hex()});
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
            for (Segment s = this.head.next; s != this.head; s = s.next) {
                md5.update(s.data, s.pos, s.limit - s.pos);
            }
            return String.format("Buffer[size=%s md5=%s]", new Object[]{Long.valueOf(this.size), ByteString.of(md5.digest()).hex()});
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    public Buffer clone() {
        Buffer result = new Buffer();
        if (this.size == 0) {
            return result;
        }
        result.head = new Segment(this.head);
        Segment segment = result.head;
        Segment segment2 = result.head;
        Segment segment3 = result.head;
        segment2.prev = segment3;
        segment.next = segment3;
        for (Segment s = this.head.next; s != this.head; s = s.next) {
            result.head.prev.push(new Segment(s));
        }
        result.size = this.size;
        return result;
    }
}
