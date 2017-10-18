package okio;

import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class k implements a, b, Cloneable {
    private static final byte[] uH = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
    long size;
    p uI;

    public k AN(k kVar, long j, long j2) {
        if (kVar != null) {
            r.checkOffsetAndCount(this.size, j, j2);
            if (j2 == 0) {
                return this;
            }
            kVar.size += j2;
            p pVar = this.uI;
            while (true) {
                if ((j < ((long) (pVar.limit - pVar.pos)) ? 1 : null) != null) {
                    break;
                }
                j -= (long) (pVar.limit - pVar.pos);
                pVar = pVar.uR;
            }
            p pVar2 = pVar;
            while (true) {
                if ((j2 <= 0 ? 1 : null) != null) {
                    return this;
                }
                pVar = new p(pVar2);
                pVar.pos = (int) (((long) pVar.pos) + j);
                pVar.limit = Math.min(pVar.pos + ((int) j2), pVar.limit);
                if (kVar.uI != null) {
                    kVar.uI.uS.Bc(pVar);
                } else {
                    pVar.uS = pVar;
                    pVar.uR = pVar;
                    kVar.uI = pVar;
                }
                j2 -= (long) (pVar.limit - pVar.pos);
                pVar2 = pVar2.uR;
                j = 0;
            }
        } else {
            throw new IllegalArgumentException("out == null");
        }
    }

    public long AO() {
        long j = this.size;
        if (j == 0) {
            return 0;
        }
        p pVar = this.uI.uS;
        if (pVar.limit < 2048 && pVar.uQ) {
            j -= (long) (pVar.limit - pVar.pos);
        }
        return j;
    }

    public byte AP(long j) {
        r.checkOffsetAndCount(this.size, j, 1);
        p pVar = this.uI;
        while (true) {
            int i = pVar.limit - pVar.pos;
            if ((j >= ((long) i) ? 1 : null) == null) {
                return pVar.data[pVar.pos + ((int) j)];
            }
            j -= (long) i;
            pVar = pVar.uR;
        }
    }

    public String AQ() {
        try {
            return AS(this.size, r.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String AR(long j) {
        return AS(j, r.UTF_8);
    }

    public String AS(long j, Charset charset) {
        r.checkOffsetAndCount(this.size, 0, j);
        if (charset != null) {
            if ((j <= 2147483647L ? 1 : null) == null) {
                throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
            } else if (j == 0) {
                return "";
            } else {
                p pVar = this.uI;
                if ((((long) pVar.pos) + j <= ((long) pVar.limit) ? 1 : null) == null) {
                    return new String(zV(j), charset);
                }
                String str = new String(pVar.data, pVar.pos, (int) j, charset);
                pVar.pos = (int) (((long) pVar.pos) + j);
                this.size -= j;
                if (pVar.pos == pVar.limit) {
                    this.uI = pVar.Bb();
                    e.Aq(pVar);
                }
                return str;
            }
        }
        throw new IllegalArgumentException("charset == null");
    }

    String AT(long j) {
        if ((j <= 0 ? 1 : null) == null && AP(j - 1) == (byte) 13) {
            String AR = AR(j - 1);
            skip(2);
            return AR;
        }
        AR = AR(j);
        skip(1);
        return AR;
    }

    public void AU(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            int read = read(bArr, i, bArr.length - i);
            if (read != -1) {
                i += read;
            } else {
                throw new EOFException();
            }
        }
    }

    public k AV(String str, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(r.UTF_8)) {
            return Ac(str);
        } else {
            byte[] bytes = str.getBytes(charset);
            return write(bytes, 0, bytes.length);
        }
    }

    p AW(int i) {
        if (i < 1 || i > 2048) {
            throw new IllegalArgumentException();
        } else if (this.uI != null) {
            r0 = this.uI.uS;
            if (r0.limit + i > 2048 || !r0.uQ) {
                r0 = r0.Bc(e.Ap());
            }
            return r0;
        } else {
            this.uI = e.Ap();
            r0 = this.uI;
            p pVar = this.uI;
            p pVar2 = this.uI;
            pVar.uS = pVar2;
            r0.uR = pVar2;
            return pVar2;
        }
    }

    public long AX(byte b, long j) {
        if ((j >= 0 ? 1 : null) == null) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        p pVar = this.uI;
        if (pVar == null) {
            return -1;
        }
        long j2 = 0;
        do {
            int i = pVar.limit - pVar.pos;
            if ((j < ((long) i) ? 1 : null) == null) {
                j -= (long) i;
            } else {
                byte[] bArr = pVar.data;
                long j3 = ((long) pVar.pos) + j;
                long j4 = (long) pVar.limit;
                while (true) {
                    if ((j3 >= j4 ? 1 : null) != null) {
                        break;
                    } else if (bArr[(int) j3] == b) {
                        return (j2 + j3) - ((long) pVar.pos);
                    } else {
                        j3++;
                    }
                }
                j = 0;
            }
            j2 += (long) i;
            pVar = pVar.uR;
        } while (pVar != this.uI);
        return -1;
    }

    public k Aa(byte[] bArr) {
        if (bArr != null) {
            return write(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    public long Ab(v vVar) {
        if (vVar != null) {
            long j = 0;
            while (true) {
                long read = vVar.read(this, 2048);
                if (read == -1) {
                    return j;
                }
                j += read;
            }
        } else {
            throw new IllegalArgumentException("source == null");
        }
    }

    public k Ac(String str) {
        if (str != null) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int i2;
                char charAt = str.charAt(i);
                if (charAt < '') {
                    p AW = AW(1);
                    byte[] bArr = AW.data;
                    int i3 = AW.limit - i;
                    int min = Math.min(length, 2048 - i3);
                    i2 = i + 1;
                    bArr[i + i3] = (byte) ((byte) charAt);
                    while (i2 < min) {
                        charAt = str.charAt(i2);
                        if (charAt >= '') {
                            break;
                        }
                        i = i2 + 1;
                        bArr[i2 + i3] = (byte) ((byte) charAt);
                        i2 = i;
                    }
                    i = (i2 + i3) - AW.limit;
                    AW.limit += i;
                    this.size += (long) i;
                } else if (charAt < 'ࠀ') {
                    Ad((charAt >> 6) | 192);
                    Ad((charAt & 63) | 128);
                    i2 = i + 1;
                } else if (charAt >= '?' && charAt <= '?') {
                    i2 = i + 1 >= length ? 0 : str.charAt(i + 1);
                    if (charAt <= '?' && i2 >= 56320 && i2 <= 57343) {
                        i2 = ((i2 & -56321) | ((charAt & -55297) << 10)) + 65536;
                        Ad((i2 >> 18) | 240);
                        Ad(((i2 >> 12) & 63) | 128);
                        Ad(((i2 >> 6) & 63) | 128);
                        Ad((i2 & 63) | 128);
                        i2 = i + 2;
                    } else {
                        Ad(63);
                        i++;
                    }
                } else {
                    Ad((charAt >> 12) | 224);
                    Ad(((charAt >> 6) & 63) | 128);
                    Ad((charAt & 63) | 128);
                    i2 = i + 1;
                }
                i = i2;
            }
            return this;
        }
        throw new IllegalArgumentException("string == null");
    }

    public k Ad(int i) {
        p AW = AW(1);
        byte[] bArr = AW.data;
        int i2 = AW.limit;
        AW.limit = i2 + 1;
        bArr[i2] = (byte) ((byte) i);
        this.size++;
        return this;
    }

    public k Ae(int i) {
        p AW = AW(2);
        byte[] bArr = AW.data;
        int i2 = AW.limit;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((byte) ((i >>> 8) & 255));
        i2 = i3 + 1;
        bArr[i3] = (byte) ((byte) (i & 255));
        AW.limit = i2;
        this.size += 2;
        return this;
    }

    public k Af(int i) {
        p AW = AW(4);
        byte[] bArr = AW.data;
        int i2 = AW.limit;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((byte) ((i >>> 24) & 255));
        i2 = i3 + 1;
        bArr[i3] = (byte) ((byte) ((i >>> 16) & 255));
        i3 = i2 + 1;
        bArr[i2] = (byte) ((byte) ((i >>> 8) & 255));
        i2 = i3 + 1;
        bArr[i3] = (byte) ((byte) (i & 255));
        AW.limit = i2;
        this.size += 4;
        return this;
    }

    public k Ag(long j) {
        if (j == 0) {
            return Ad(48);
        }
        long j2;
        int i;
        if ((j >= 0 ? 1 : null) == null) {
            j2 = -j;
            if ((j2 >= 0 ? 1 : null) == null) {
                return Ac("-9223372036854775808");
            }
            int i2 = 1;
        } else {
            Object obj = null;
            j2 = j;
        }
        if ((j2 >= 100000000 ? 1 : null) == null) {
            if ((j2 >= 10000 ? 1 : null) == null) {
                if ((j2 >= 100 ? 1 : null) == null) {
                    i = ((j2 > 10 ? 1 : (j2 == 10 ? 0 : -1)) >= 0 ? 1 : null) == null ? 1 : 2;
                } else {
                    i = ((j2 > 1000 ? 1 : (j2 == 1000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 3 : 4;
                }
            } else {
                if ((j2 >= 1000000 ? 1 : null) == null) {
                    i = ((j2 > 100000 ? 1 : (j2 == 100000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 5 : 6;
                } else {
                    i = ((j2 > 10000000 ? 1 : (j2 == 10000000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 7 : 8;
                }
            }
        } else {
            if ((j2 >= 1000000000000L ? 1 : null) == null) {
                if ((j2 >= 10000000000L ? 1 : null) == null) {
                    i = ((j2 > 1000000000 ? 1 : (j2 == 1000000000 ? 0 : -1)) >= 0 ? 1 : null) == null ? 9 : 10;
                } else {
                    i = ((j2 > 100000000000L ? 1 : (j2 == 100000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 11 : 12;
                }
            } else {
                if ((j2 >= 1000000000000000L ? 1 : null) == null) {
                    if ((j2 >= 10000000000000L ? 1 : null) == null) {
                        i = 13;
                    } else {
                        i = ((j2 > 100000000000000L ? 1 : (j2 == 100000000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 14 : 15;
                    }
                } else {
                    if ((j2 >= 100000000000000000L ? 1 : null) == null) {
                        i = ((j2 > 10000000000000000L ? 1 : (j2 == 10000000000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 16 : 17;
                    } else {
                        i = ((j2 > 1000000000000000000L ? 1 : (j2 == 1000000000000000000L ? 0 : -1)) >= 0 ? 1 : null) == null ? 18 : 19;
                    }
                }
            }
        }
        if (obj != null) {
            i++;
        }
        p AW = AW(i);
        byte[] bArr = AW.data;
        int i3 = AW.limit + i;
        while (j2 != 0) {
            i3--;
            bArr[i3] = (byte) uH[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (obj != null) {
            bArr[i3 - 1] = (byte) 45;
        }
        AW.limit += i;
        this.size = ((long) i) + this.size;
        return this;
    }

    public k Ah(long j) {
        if (j == 0) {
            return Ad(48);
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        p AW = AW(numberOfTrailingZeros);
        byte[] bArr = AW.data;
        int i = AW.limit;
        for (int i2 = (AW.limit + numberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = (byte) uH[(int) (15 & j)];
            j >>>= 4;
        }
        AW.limit += numberOfTrailingZeros;
        this.size = ((long) numberOfTrailingZeros) + this.size;
        return this;
    }

    public k Ai() {
        return this;
    }

    public b Aj() {
        return this;
    }

    public OutputStream Ak() {
        return new w(this);
    }

    public void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public k clone() {
        k kVar = new k();
        if (this.size == 0) {
            return kVar;
        }
        kVar.uI = new p(this.uI);
        p pVar = kVar.uI;
        p pVar2 = kVar.uI;
        p pVar3 = kVar.uI;
        pVar2.uS = pVar3;
        pVar.uR = pVar3;
        for (pVar = this.uI.uR; pVar != this.uI; pVar = pVar.uR) {
            kVar.uI.uS.Bc(new p(pVar));
        }
        kVar.size = this.size;
        return kVar;
    }

    public void close() {
    }

    public boolean equals(Object obj) {
        long j = 0;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        if (this.size != kVar.size) {
            return false;
        }
        if (this.size == 0) {
            return true;
        }
        p pVar = this.uI;
        p pVar2 = kVar.uI;
        int i = pVar.pos;
        int i2 = pVar2.pos;
        while (true) {
            if (j >= this.size) {
                return true;
            }
            long min = (long) Math.min(pVar.limit - i, pVar2.limit - i2);
            int i3 = 0;
            while (true) {
                if (((long) i3) >= min) {
                    break;
                }
                int i4 = i + 1;
                byte b = pVar.data[i];
                i = i2 + 1;
                if (b != pVar2.data[i2]) {
                    return false;
                }
                i3++;
                i2 = i;
                i = i4;
            }
            if (i == pVar.limit) {
                pVar = pVar.uR;
                i = pVar.pos;
            }
            if (i2 == pVar2.limit) {
                pVar2 = pVar2.uR;
                i2 = pVar2.pos;
            }
            j += min;
        }
    }

    public void flush() {
    }

    public int hashCode() {
        p pVar = this.uI;
        if (pVar == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = pVar.pos;
            while (i2 < pVar.limit) {
                int i3 = pVar.data[i2] + (i * 31);
                i2++;
                i = i3;
            }
            pVar = pVar.uR;
        } while (pVar != this.uI);
        return i;
    }

    public int read(byte[] bArr, int i, int i2) {
        r.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
        p pVar = this.uI;
        if (pVar == null) {
            return -1;
        }
        int min = Math.min(i2, pVar.limit - pVar.pos);
        System.arraycopy(pVar.data, pVar.pos, bArr, i, min);
        pVar.pos += min;
        this.size -= (long) min;
        if (pVar.pos == pVar.limit) {
            this.uI = pVar.Bb();
            e.Aq(pVar);
        }
        return min;
    }

    public long read(k kVar, long j) {
        Object obj = 1;
        if (kVar != null) {
            if ((j >= 0 ? 1 : null) == null) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.size == 0) {
                return -1;
            } else {
                if (j > this.size) {
                    obj = null;
                }
                if (obj == null) {
                    j = this.size;
                }
                kVar.write(this, j);
                return j;
            }
        }
        throw new IllegalArgumentException("sink == null");
    }

    public byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        p pVar = this.uI;
        int i = pVar.pos;
        int i2 = pVar.limit;
        int i3 = i + 1;
        byte b = pVar.data[i];
        this.size--;
        if (i3 != i2) {
            pVar.pos = i3;
        } else {
            this.uI = pVar.Bb();
            e.Aq(pVar);
        }
        return b;
    }

    public ByteString readByteString() {
        return new ByteString(zU());
    }

    public long size() {
        return this.size;
    }

    public void skip(long j) {
        while (true) {
            if ((j <= 0 ? 1 : null) != null) {
                return;
            }
            if (this.uI != null) {
                int min = (int) Math.min(j, (long) (this.uI.limit - this.uI.pos));
                this.size -= (long) min;
                j -= (long) min;
                p pVar = this.uI;
                pVar.pos = min + pVar.pos;
                if (this.uI.pos == this.uI.limit) {
                    p pVar2 = this.uI;
                    this.uI = pVar2.Bb();
                    e.Aq(pVar2);
                }
            } else {
                throw new EOFException();
            }
        }
    }

    public g timeout() {
        return g.NONE;
    }

    public String toString() {
        if (this.size == 0) {
            return "Buffer[size=0]";
        }
        if ((this.size > 16 ? 1 : 0) == 0) {
            ByteString readByteString = clone().readByteString();
            return String.format("Buffer[size=%s data=%s]", new Object[]{Long.valueOf(this.size), readByteString.Ay()});
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(this.uI.data, this.uI.pos, this.uI.limit - this.uI.pos);
            for (p pVar = this.uI.uR; pVar != this.uI; pVar = pVar.uR) {
                instance.update(pVar.data, pVar.pos, pVar.limit - pVar.pos);
            }
            return String.format("Buffer[size=%s md5=%s]", new Object[]{Long.valueOf(this.size), ByteString.Ar(instance.digest()).Ay()});
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    public k write(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            r.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
            int i3 = i + i2;
            while (i < i3) {
                p AW = AW(1);
                int min = Math.min(i3 - i, 2048 - AW.limit);
                System.arraycopy(bArr, i, AW.data, AW.limit, min);
                i += min;
                AW.limit = min + AW.limit;
            }
            this.size += (long) i2;
            return this;
        }
        throw new IllegalArgumentException("source == null");
    }

    public void write(k kVar, long j) {
        if (kVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (kVar != this) {
            r.checkOffsetAndCount(kVar.size, 0, j);
            while (true) {
                if ((j <= 0 ? 1 : null) == null) {
                    p pVar;
                    if ((j >= ((long) (kVar.uI.limit - kVar.uI.pos)) ? 1 : null) == null) {
                        pVar = this.uI == null ? null : this.uI.uS;
                        if (pVar != null && pVar.uQ) {
                            if (((((long) pVar.limit) + j) - ((long) (!pVar.uP ? pVar.pos : 0)) > 2048 ? 1 : null) == null) {
                                kVar.uI.Bf(pVar, (int) j);
                                kVar.size -= j;
                                this.size += j;
                                return;
                            }
                        }
                        kVar.uI = kVar.uI.Bd((int) j);
                    }
                    pVar = kVar.uI;
                    long j2 = (long) (pVar.limit - pVar.pos);
                    kVar.uI = pVar.Bb();
                    if (this.uI != null) {
                        this.uI.uS.Bc(pVar).Be();
                    } else {
                        this.uI = pVar;
                        pVar = this.uI;
                        p pVar2 = this.uI;
                        p pVar3 = this.uI;
                        pVar2.uS = pVar3;
                        pVar.uR = pVar3;
                    }
                    kVar.size -= j2;
                    this.size += j2;
                    j -= j2;
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("source == this");
        }
    }

    public k zK() {
        return this;
    }

    public boolean zL() {
        return this.size == 0;
    }

    public void zM(long j) {
        if ((this.size >= j ? 1 : null) == null) {
            throw new EOFException();
        }
    }

    public short zN() {
        if ((this.size >= 2 ? 1 : null) == null) {
            throw new IllegalStateException("size < 2: " + this.size);
        }
        p pVar = this.uI;
        int i = pVar.pos;
        int i2 = pVar.limit;
        if (i2 - i < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        byte[] bArr = pVar.data;
        int i3 = i + 1;
        int i4 = i3 + 1;
        i = ((bArr[i] & 255) << 8) | (bArr[i3] & 255);
        this.size -= 2;
        if (i4 != i2) {
            pVar.pos = i4;
        } else {
            this.uI = pVar.Bb();
            e.Aq(pVar);
        }
        return (short) i;
    }

    public short zO() {
        return r.Bi(zN());
    }

    public int zP() {
        if ((this.size >= 4 ? 1 : null) == null) {
            throw new IllegalStateException("size < 4: " + this.size);
        }
        p pVar = this.uI;
        int i = pVar.pos;
        int i2 = pVar.limit;
        if (i2 - i < 4) {
            return ((((readByte() & 255) << 24) | ((readByte() & 255) << 16)) | ((readByte() & 255) << 8)) | (readByte() & 255);
        }
        byte[] bArr = pVar.data;
        int i3 = i + 1;
        int i4 = i3 + 1;
        i = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
        i3 = i4 + 1;
        i |= (bArr[i4] & 255) << 8;
        i4 = i3 + 1;
        i |= bArr[i3] & 255;
        this.size -= 4;
        if (i4 != i2) {
            pVar.pos = i4;
        } else {
            this.uI = pVar.Bb();
            e.Aq(pVar);
        }
        return i;
    }

    public int zQ() {
        return r.Bj(zP());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long zR() {
        /*
        r18 = this;
        r0 = r18;
        r2 = r0.size;
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 != 0) goto L_0x0013;
    L_0x000a:
        r2 = new java.lang.IllegalStateException;
        r3 = "size == 0";
        r2.<init>(r3);
        throw r2;
    L_0x0013:
        r8 = 0;
        r6 = 0;
        r5 = 0;
        r4 = 0;
        r2 = -7;
    L_0x001a:
        r0 = r18;
        r11 = r0.uI;
        r12 = r11.data;
        r7 = r11.pos;
        r13 = r11.limit;
        r10 = r7;
    L_0x0025:
        if (r10 < r13) goto L_0x003b;
    L_0x0027:
        if (r10 == r13) goto L_0x00d5;
    L_0x0029:
        r11.pos = r10;
    L_0x002b:
        if (r4 == 0) goto L_0x00e2;
    L_0x002d:
        r0 = r18;
        r2 = r0.size;
        r6 = (long) r6;
        r2 = r2 - r6;
        r0 = r18;
        r0.size = r2;
        if (r5 != 0) goto L_0x003a;
    L_0x0039:
        r8 = -r8;
    L_0x003a:
        return r8;
    L_0x003b:
        r14 = r12[r10];
        r7 = 48;
        if (r14 >= r7) goto L_0x0049;
    L_0x0041:
        r7 = 45;
        if (r14 == r7) goto L_0x00b0;
    L_0x0045:
        if (r6 == 0) goto L_0x00b7;
    L_0x0047:
        r4 = 1;
        goto L_0x0027;
    L_0x0049:
        r7 = 57;
        if (r14 > r7) goto L_0x0041;
    L_0x004d:
        r15 = 48 - r14;
        r16 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r7 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
        if (r7 >= 0) goto L_0x009b;
    L_0x0058:
        r7 = 1;
    L_0x0059:
        if (r7 != 0) goto L_0x006e;
    L_0x005b:
        r16 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r7 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
        if (r7 != 0) goto L_0x00a3;
    L_0x0064:
        r0 = (long) r15;
        r16 = r0;
        r7 = (r16 > r2 ? 1 : (r16 == r2 ? 0 : -1));
        if (r7 < 0) goto L_0x009d;
    L_0x006b:
        r7 = 1;
    L_0x006c:
        if (r7 != 0) goto L_0x00a3;
    L_0x006e:
        r2 = new okio.k;
        r2.<init>();
        r2 = r2.Ag(r8);
        r2 = r2.Ad(r14);
        if (r5 == 0) goto L_0x009f;
    L_0x007d:
        r3 = new java.lang.NumberFormatException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Number too large: ";
        r4 = r4.append(r5);
        r2 = r2.AQ();
        r2 = r4.append(r2);
        r2 = r2.toString();
        r3.<init>(r2);
        throw r3;
    L_0x009b:
        r7 = 0;
        goto L_0x0059;
    L_0x009d:
        r7 = 0;
        goto L_0x006c;
    L_0x009f:
        r2.readByte();
        goto L_0x007d;
    L_0x00a3:
        r16 = 10;
        r8 = r8 * r16;
        r14 = (long) r15;
        r8 = r8 + r14;
    L_0x00a9:
        r7 = r10 + 1;
        r6 = r6 + 1;
        r10 = r7;
        goto L_0x0025;
    L_0x00b0:
        if (r6 != 0) goto L_0x0045;
    L_0x00b2:
        r5 = 1;
        r14 = 1;
        r2 = r2 - r14;
        goto L_0x00a9;
    L_0x00b7:
        r2 = new java.lang.NumberFormatException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Expected leading [0-9] or '-' character but was 0x";
        r3 = r3.append(r4);
        r4 = java.lang.Integer.toHexString(r14);
        r3 = r3.append(r4);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
    L_0x00d5:
        r7 = r11.Bb();
        r0 = r18;
        r0.uI = r7;
        okio.e.Aq(r11);
        goto L_0x002b;
    L_0x00e2:
        r0 = r18;
        r7 = r0.uI;
        if (r7 == 0) goto L_0x002d;
    L_0x00e8:
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.k.zR():long");
    }

    public long zS() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        long j = 0;
        int i = 0;
        Object obj = null;
        while (true) {
            p pVar = this.uI;
            byte[] bArr = pVar.data;
            int i2 = pVar.pos;
            int i3 = pVar.limit;
            int i4 = i2;
            while (i4 < i3) {
                byte b = bArr[i4];
                if (b >= (byte) 48 && b <= (byte) 57) {
                    i2 = b - 48;
                } else if (b >= (byte) 97 && b <= (byte) 102) {
                    i2 = (b - 97) + 10;
                } else if (b >= (byte) 65 && b <= (byte) 70) {
                    i2 = (b - 65) + 10;
                } else if (i != 0) {
                    obj = 1;
                    if (i4 == i3) {
                        pVar.pos = i4;
                    } else {
                        this.uI = pVar.Bb();
                        e.Aq(pVar);
                    }
                    if (!(obj == null || this.uI == null)) {
                    }
                } else {
                    throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(b));
                }
                if ((-1152921504606846976L & j) != 0) {
                    throw new NumberFormatException("Number too large: " + new k().Ah(j).Ad((int) b).AQ());
                }
                i++;
                i4++;
                j = ((long) i2) | (j << 4);
            }
            if (i4 == i3) {
                this.uI = pVar.Bb();
                e.Aq(pVar);
            } else {
                pVar.pos = i4;
            }
            if (obj == null) {
                break;
            }
        }
        this.size -= (long) i;
        return j;
    }

    public ByteString zT(long j) {
        return new ByteString(zV(j));
    }

    public byte[] zU() {
        try {
            return zV(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] zV(long j) {
        r.checkOffsetAndCount(this.size, 0, j);
        if ((j <= 2147483647L ? 1 : null) == null) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        byte[] bArr = new byte[((int) j)];
        AU(bArr);
        return bArr;
    }

    public String zW() {
        long zX = zX((byte) 10);
        if (zX != -1) {
            return AT(zX);
        }
        k kVar = new k();
        AN(kVar, 0, Math.min(32, this.size));
        throw new EOFException("\\n not found: size=" + size() + " content=" + kVar.readByteString().Ay() + "...");
    }

    public long zX(byte b) {
        return AX(b, 0);
    }

    public InputStream zY() {
        return new h(this);
    }

    public k zZ(ByteString byteString) {
        if (byteString != null) {
            byteString.AC(this);
            return this;
        }
        throw new IllegalArgumentException("byteString == null");
    }
}
