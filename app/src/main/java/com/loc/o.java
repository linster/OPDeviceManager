package com.loc;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* compiled from: Encrypt */
public class o {
    private static final char[] a;
    private static final byte[] b;

    static {
        int i = 0;
        char c = '0';
        a = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        b = new byte[128];
        while (true) {
            if (i >= 128) {
                for (i = 65; i <= 90; i++) {
                    b[i] = (byte) ((byte) (i - 65));
                }
                for (i = 97; i <= 122; i++) {
                    b[i] = (byte) ((byte) ((i - 97) + 26));
                }
                int i2;
                while (i2 <= 57) {
                    b[i2] = (byte) ((byte) ((i2 - 48) + 52));
                    i2++;
                }
                b[43] = (byte) 62;
                c = b;
                i = 63;
                c[47] = 63;
            }
            b[i] = (byte) -1;
            i++;
        }
    }

    static byte[] a(byte[] bArr, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(1, key);
        return instance.doFinal(bArr);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.lang.String r9) {
        /*
        r8 = 61;
        r1 = 0;
        r7 = -1;
        if (r9 == 0) goto L_0x001a;
    L_0x0006:
        r0 = "UTF-8";
        r0 = r9.getBytes(r0);	 Catch:{ UnsupportedEncodingException -> 0x001d }
    L_0x000d:
        r3 = r0.length;
        r4 = new java.io.ByteArrayOutputStream;
        r4.<init>(r3);
    L_0x0013:
        if (r1 < r3) goto L_0x0026;
    L_0x0015:
        r0 = r4.toByteArray();
        return r0;
    L_0x001a:
        r0 = new byte[r1];
        return r0;
    L_0x001d:
        r0 = move-exception;
        r0 = r9.getBytes();
        goto L_0x000d;
    L_0x0023:
        if (r5 != r7) goto L_0x0030;
    L_0x0025:
        r1 = r2;
    L_0x0026:
        r5 = b;
        r2 = r1 + 1;
        r1 = r0[r1];
        r5 = r5[r1];
        if (r2 < r3) goto L_0x0023;
    L_0x0030:
        if (r5 == r7) goto L_0x0015;
    L_0x0032:
        r6 = b;
        r1 = r2 + 1;
        r2 = r0[r2];
        r6 = r6[r2];
        if (r1 < r3) goto L_0x0079;
    L_0x003c:
        if (r6 == r7) goto L_0x0015;
    L_0x003e:
        r2 = r5 << 2;
        r5 = r6 & 48;
        r5 = r5 >>> 4;
        r2 = r2 | r5;
        r4.write(r2);
    L_0x0048:
        r2 = r1 + 1;
        r1 = r0[r1];
        if (r1 == r8) goto L_0x007d;
    L_0x004e:
        r5 = b;
        r5 = r5[r1];
        if (r2 < r3) goto L_0x0082;
    L_0x0054:
        if (r5 == r7) goto L_0x0015;
    L_0x0056:
        r1 = r6 & 15;
        r1 = r1 << 4;
        r6 = r5 & 60;
        r6 = r6 >>> 2;
        r1 = r1 | r6;
        r4.write(r1);
    L_0x0062:
        r1 = r2 + 1;
        r2 = r0[r2];
        if (r2 == r8) goto L_0x0086;
    L_0x0068:
        r6 = b;
        r2 = r6[r2];
        if (r1 < r3) goto L_0x008b;
    L_0x006e:
        if (r2 == r7) goto L_0x0015;
    L_0x0070:
        r5 = r5 & 3;
        r5 = r5 << 6;
        r2 = r2 | r5;
        r4.write(r2);
        goto L_0x0013;
    L_0x0079:
        if (r6 != r7) goto L_0x003c;
    L_0x007b:
        r2 = r1;
        goto L_0x0032;
    L_0x007d:
        r0 = r4.toByteArray();
        return r0;
    L_0x0082:
        if (r5 != r7) goto L_0x0054;
    L_0x0084:
        r1 = r2;
        goto L_0x0048;
    L_0x0086:
        r0 = r4.toByteArray();
        return r0;
    L_0x008b:
        if (r2 != r7) goto L_0x006e;
    L_0x008d:
        r2 = r1;
        goto L_0x0062;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.o.a(java.lang.String):byte[]");
    }

    private static String b(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            int i3 = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(a[i3 >>> 2]);
                stringBuffer.append(a[(i3 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i4 = i2 + 1;
            i2 = bArr[i2] & 255;
            if (i4 == length) {
                stringBuffer.append(a[i3 >>> 2]);
                stringBuffer.append(a[((i3 & 3) << 4) | ((i2 & 240) >>> 4)]);
                stringBuffer.append(a[(i2 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            i = i4 + 1;
            i4 = bArr[i4] & 255;
            stringBuffer.append(a[i3 >>> 2]);
            stringBuffer.append(a[((i3 & 3) << 4) | ((i2 & 240) >>> 4)]);
            stringBuffer.append(a[((i2 & 15) << 2) | ((i4 & 192) >>> 6)]);
            stringBuffer.append(a[i4 & 63]);
        }
        return stringBuffer.toString();
    }

    public static String a(byte[] bArr) {
        try {
            return b(bArr);
        } catch (Throwable th) {
            v.a(th, "Encrypt", "encodeBase64");
            return null;
        }
    }
}
