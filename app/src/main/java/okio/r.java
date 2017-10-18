package okio;

import java.nio.charset.Charset;

final class r {
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private r() {
    }

    public static short Bi(short s) {
        int i = 65535 & s;
        return (short) (((i & 255) << 8) | ((65280 & i) >>> 8));
    }

    public static int Bj(int i) {
        return ((((-16777216 & i) >>> 24) | ((16711680 & i) >>> 8)) | ((65280 & i) << 8)) | ((i & 255) << 24);
    }

    public static void Bk(Throwable th) {
        Bl(th);
    }

    private static void Bl(Throwable th) {
        throw th;
    }

    public static boolean Bm(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            if (bArr[i4 + i] != bArr2[i4 + i2]) {
                return false;
            }
        }
        return true;
    }

    public static void checkOffsetAndCount(long j, long j2, long j3) {
        if (((j2 | j3) < 0 ? 1 : 0) == 0) {
            if ((j2 > j ? 1 : 0) == 0) {
                if ((j - j2 >= j3 ? 1 : 0) != 0) {
                    return;
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", new Object[]{Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3)}));
    }
}
