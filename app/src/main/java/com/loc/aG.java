package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;

public class aG {
    private static final byte[] b = new byte[128];
    private static final char[] px = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    static {
        int i = 0;
        char c = '0';
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

    static byte[] rl(byte[] bArr, Key key) {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(1, key);
        return instance.doFinal(bArr);
    }

    public static byte[] rm(String str) {
        int i = 0;
        if (str == null) {
            return new byte[0];
        }
        byte[] bytes;
        try {
            bytes = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            bytes = str.getBytes();
        }
        int length = bytes.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
        while (i < length) {
            while (true) {
                int i2 = i + 1;
                byte b = b[bytes[i]];
                if (i2 < length && b == (byte) -1) {
                    i = i2;
                }
            }
            if (b == (byte) -1) {
                break;
            }
            while (true) {
                i = i2 + 1;
                byte b2 = b[bytes[i2]];
                if (i < length && b2 == (byte) -1) {
                    i2 = i;
                }
            }
            if (b2 == (byte) -1) {
                break;
            }
            byteArrayOutputStream.write((b << 2) | ((b2 & 48) >>> 4));
            while (true) {
                i2 = i + 1;
                byte b3 = bytes[i];
                if (b3 == (byte) 61) {
                    return byteArrayOutputStream.toByteArray();
                }
                b = b[b3];
                if (i2 < length && b == (byte) -1) {
                    i = i2;
                }
            }
            if (b == (byte) -1) {
                break;
            }
            byteArrayOutputStream.write(((b2 & 15) << 4) | ((b & 60) >>> 2));
            while (true) {
                i = i2 + 1;
                byte b4 = bytes[i2];
                if (b4 == (byte) 61) {
                    return byteArrayOutputStream.toByteArray();
                }
                b4 = b[b4];
                if (i < length && b4 == (byte) -1) {
                    i2 = i;
                }
            }
            if (b4 == (byte) -1) {
                break;
            }
            byteArrayOutputStream.write(b4 | ((b & 3) << 6));
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static String rn(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            int i3 = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(px[i3 >>> 2]);
                stringBuffer.append(px[(i3 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i4 = i2 + 1;
            i2 = bArr[i2] & 255;
            if (i4 == length) {
                stringBuffer.append(px[i3 >>> 2]);
                stringBuffer.append(px[((i3 & 3) << 4) | ((i2 & 240) >>> 4)]);
                stringBuffer.append(px[(i2 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            i = i4 + 1;
            i4 = bArr[i4] & 255;
            stringBuffer.append(px[i3 >>> 2]);
            stringBuffer.append(px[((i3 & 3) << 4) | ((i2 & 240) >>> 4)]);
            stringBuffer.append(px[((i2 & 15) << 2) | ((i4 & 192) >>> 6)]);
            stringBuffer.append(px[i4 & 63]);
        }
        return stringBuffer.toString();
    }

    public static String ro(byte[] bArr) {
        try {
            return rn(bArr);
        } catch (Throwable th) {
            D.mF(th, "Encrypt", "encodeBase64");
            return null;
        }
    }
}
