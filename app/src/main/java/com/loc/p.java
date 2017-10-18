package com.loc;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: MD5 */
public class p {
    public static String a(String str) {
        Throwable th;
        FileInputStream fileInputStream = null;
        FileInputStream fileInputStream2;
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            File file = new File(str);
            if (!file.isFile() || !file.exists()) {
                return null;
            }
            byte[] bArr = new byte[2048];
            MessageDigest instance = MessageDigest.getInstance("MD5");
            fileInputStream2 = new FileInputStream(file);
            while (true) {
                try {
                    int read = fileInputStream2.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    instance.update(bArr, 0, read);
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            String b = t.b(instance.digest());
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Throwable e) {
                    v.a(e, "MD5", "getMd5FromFile");
                }
            }
            return b;
        } catch (Throwable th3) {
            th = th3;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    public static String b(String str) {
        if (str != null) {
            return t.b(c(str));
        }
        return null;
    }

    public static byte[] a(byte[] bArr, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return instance.digest();
        } catch (Throwable e) {
            v.a(e, "MD5", "getMd5Bytes");
            return null;
        } catch (Throwable e2) {
            v.a(e2, "MD5", "getMd5Bytes1");
            return null;
        }
    }

    public static byte[] c(String str) {
        try {
            return d(str);
        } catch (Throwable e) {
            v.a(e, "MD5", "getMd5Bytes");
            return new byte[0];
        } catch (Throwable e2) {
            v.a(e2, "MD5", "getMd5Bytes");
            return new byte[0];
        } catch (Throwable e22) {
            v.a(e22, "MD5", "getMd5Bytes");
            return new byte[0];
        }
    }

    private static byte[] d(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(str.getBytes("UTF-8"));
        return instance.digest();
    }
}
