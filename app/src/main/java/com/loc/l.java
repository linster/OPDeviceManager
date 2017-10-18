package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Map;

/* compiled from: ClientInfo */
public class l {
    public static String a(Context context, s sVar, Map<String, String> map, boolean z) {
        try {
            byte[] a;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            a(byteArrayOutputStream, n.l(context));
            a(byteArrayOutputStream, n.d(context));
            String a2 = n.a(context);
            if (a2 == null) {
                a2 = "";
            }
            a(byteArrayOutputStream, a2);
            a(byteArrayOutputStream, j.b(context));
            a(byteArrayOutputStream, Build.MODEL);
            a(byteArrayOutputStream, Build.MANUFACTURER);
            a(byteArrayOutputStream, Build.DEVICE);
            a(byteArrayOutputStream, j.a(context));
            a(byteArrayOutputStream, j.c(context));
            a(byteArrayOutputStream, String.valueOf(VERSION.SDK_INT));
            a(byteArrayOutputStream, n.m(context));
            a(byteArrayOutputStream, n.k(context));
            a(byteArrayOutputStream, n.h(context) + "");
            a(byteArrayOutputStream, n.g(context) + "");
            a(byteArrayOutputStream, n.n(context));
            a(byteArrayOutputStream, n.f(context));
            if (z) {
                a(byteArrayOutputStream, "");
            } else {
                a(byteArrayOutputStream, n.c(context));
            }
            if (z) {
                a(byteArrayOutputStream, "");
            } else {
                a(byteArrayOutputStream, n.b(context));
            }
            if (z) {
                a(byteArrayOutputStream, "");
                a(byteArrayOutputStream, "");
            } else {
                String[] e = n.e(context);
                a(byteArrayOutputStream, e[0]);
                a(byteArrayOutputStream, e[1]);
            }
            Object a3 = t.a(byteArrayOutputStream.toByteArray());
            Key a4 = t.a(context);
            if (a3.length <= 117) {
                a = o.a(a3, a4);
            } else {
                Object obj = new byte[117];
                System.arraycopy(a3, 0, obj, 0, 117);
                obj = o.a(obj, a4);
                a = new byte[((a3.length + 128) - 117)];
                System.arraycopy(obj, 0, a, 0, 128);
                System.arraycopy(a3, 117, a, 128, a3.length - 117);
            }
            return o.a(a);
        } catch (Throwable th) {
            v.a(th, "CInfo", "InitXInfo");
            return null;
        }
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            a(byteArrayOutputStream, (byte) 0, new byte[0]);
            return;
        }
        byte length;
        if (str.getBytes().length <= 255) {
            length = (byte) str.getBytes().length;
        } else {
            length = (byte) -1;
        }
        try {
            a(byteArrayOutputStream, length, str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            a(byteArrayOutputStream, length, str.getBytes());
        }
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, byte b, byte[] bArr) {
        int i = 0;
        try {
            byteArrayOutputStream.write(new byte[]{(byte) b});
            int i2 = b <= null ? 0 : 1;
            if ((b & 255) < 255) {
                i = 1;
            }
            if ((i & i2) != 0) {
                byteArrayOutputStream.write(bArr);
            } else if ((b & 255) == 255) {
                byteArrayOutputStream.write(bArr, 0, 255);
            }
        } catch (Throwable e) {
            v.a(e, "CInfo", "writeField");
        }
    }

    public static String a() {
        String str = null;
        try {
            str = String.valueOf(System.currentTimeMillis());
            int length = str.length();
            str = str.substring(0, length - 2) + "1" + str.substring(length - 1);
        } catch (Throwable th) {
            v.a(th, "CInfo", "getTS");
        }
        return str;
    }

    public static String a(Context context, String str, String str2) {
        try {
            return p.b(j.d(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            v.a(th, "CInfo", "Scode");
            return null;
        }
    }
}
