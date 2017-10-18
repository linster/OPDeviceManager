package com.loc;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class J {
    public static String mZ(String str) {
        FileInputStream fileInputStream;
        Throwable th;
        FileInputStream fileInputStream2 = null;
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
            fileInputStream = new FileInputStream(file);
            while (true) {
                try {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    instance.update(bArr, 0, read);
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            String xf = bw.xf(instance.digest());
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Throwable e) {
                    D.mF(e, "MD5", "getMd5FromFile");
                }
            }
            return xf;
        } catch (Throwable th3) {
            th = th3;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
    }

    public static String na(String str) {
        return str != null ? bw.xf(nc(str)) : null;
    }

    public static byte[] nb(byte[] bArr, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return instance.digest();
        } catch (Throwable e) {
            D.mF(e, "MD5", "getMd5Bytes");
            return null;
        } catch (Throwable e2) {
            D.mF(e2, "MD5", "getMd5Bytes1");
            return null;
        }
    }

    public static byte[] nc(String str) {
        try {
            return nd(str);
        } catch (Throwable e) {
            D.mF(e, "MD5", "getMd5Bytes");
            return new byte[0];
        } catch (Throwable e2) {
            D.mF(e2, "MD5", "getMd5Bytes");
            return new byte[0];
        } catch (Throwable e22) {
            D.mF(e22, "MD5", "getMd5Bytes");
            return new byte[0];
        }
    }

    private static byte[] nd(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(str.getBytes("UTF-8"));
        return instance.digest();
    }
}
