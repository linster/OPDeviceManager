package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Encrypt */
public class bd {
    private static final char[] a;
    private static final byte[] b;
    private static final IvParameterSpec c;

    static {
        a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        b = new byte[]{(byte) 0, (byte) 1, (byte) 1, (byte) 2, (byte) 3, (byte) 5, (byte) 8, (byte) 13, (byte) 8, (byte) 7, (byte) 6, (byte) 5, (byte) 4, (byte) 3, (byte) 2, (byte) 1};
        c = new IvParameterSpec(b);
    }

    public static synchronized byte[] a(byte[] bArr, String str) throws Exception {
        byte[] doFinal;
        int i = 0;
        synchronized (bd.class) {
            Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(o.a(str)));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePrivate);
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 0;
            while (length - i > 0) {
                if (length - i <= 245) {
                    doFinal = instance.doFinal(bArr, i, length - i);
                } else {
                    doFinal = instance.doFinal(bArr, i, 245);
                }
                byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                i = i2 + 1;
                int i3 = i;
                i *= 245;
                i2 = i3;
            }
            doFinal = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        return doFinal;
    }

    public static synchronized byte[] b(byte[] bArr, String str) throws Exception {
        byte[] doFinal;
        int i = 0;
        synchronized (bd.class) {
            Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(o.a(str)));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(2, generatePrivate);
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 0;
            while (length - i > 0) {
                if (length - i <= 256) {
                    doFinal = instance.doFinal(bArr, i, length - i);
                } else {
                    doFinal = instance.doFinal(bArr, i, 256);
                }
                byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                i = i2 + 1;
                int i3 = i;
                i *= 256;
                i2 = i3;
            }
            doFinal = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        return doFinal;
    }

    public static byte[] c(byte[] bArr, String str) {
        try {
            Key b = b(str);
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(a());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            try {
                instance.init(1, b, ivParameterSpec);
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return instance.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] d(byte[] bArr, String str) {
        try {
            Key b = b(str);
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(a());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            try {
                instance.init(2, b, ivParameterSpec);
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return instance.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static SecretKeySpec b(String str) {
        byte[] bytes;
        if (str == null) {
            str = "";
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        stringBuffer.append(str);
        while (stringBuffer.length() < 16) {
            stringBuffer.append("0");
        }
        if (stringBuffer.length() > 16) {
            stringBuffer.setLength(16);
        }
        try {
            bytes = stringBuffer.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = null;
        }
        return new SecretKeySpec(bytes, "AES");
    }

    public static String a(String str) {
        if (str != null) {
            try {
                if (str.length() != 0) {
                    return a("MD5", a("SHA1", str) + str);
                }
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String a(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        try {
            return b(p.a(str2.getBytes("UTF-8"), str));
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static String b(byte[] bArr) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length * 2);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(a[(bArr[i] >> 4) & 15]);
            stringBuilder.append(a[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, new SecretKeySpec(bArr, "AES"), c);
        return instance.doFinal(bArr2);
    }

    private static byte[] a() {
        return t.a();
    }

    public static String a(byte[] bArr) {
        try {
            Object obj = new byte[16];
            Object obj2 = new byte[(bArr.length - 16)];
            System.arraycopy(bArr, 0, obj, 0, 16);
            System.arraycopy(bArr, 16, obj2, 0, bArr.length - 16);
            Key secretKeySpec = new SecretKeySpec(obj, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, new IvParameterSpec(t.a()));
            return new String(instance.doFinal(obj2), "UTF-8");
        } catch (Throwable e) {
            br.a(e);
            e.printStackTrace();
            return null;
        }
    }
}
