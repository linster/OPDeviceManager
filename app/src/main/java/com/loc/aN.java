package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Map;

public class aN {
    public static String rA() {
        String str = null;
        try {
            str = String.valueOf(System.currentTimeMillis());
            int length = str.length();
            str = str.substring(0, length - 2) + "1" + str.substring(length - 1);
        } catch (Throwable th) {
            D.mF(th, "CInfo", "getTS");
        }
        return str;
    }

    public static String rB(Context context, String str, String str2) {
        try {
            return J.na(Y.nK(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            D.mF(th, "CInfo", "Scode");
            return null;
        }
    }

    public static String rx(Context context, x xVar, Map map, boolean z) {
        try {
            byte[] rl;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ry(byteArrayOutputStream, bK.xT(context));
            ry(byteArrayOutputStream, bK.xL(context));
            String xI = bK.xI(context);
            if (xI == null) {
                xI = "";
            }
            ry(byteArrayOutputStream, xI);
            ry(byteArrayOutputStream, Y.nI(context));
            ry(byteArrayOutputStream, Build.MODEL);
            ry(byteArrayOutputStream, Build.MANUFACTURER);
            ry(byteArrayOutputStream, Build.DEVICE);
            ry(byteArrayOutputStream, Y.nH(context));
            ry(byteArrayOutputStream, Y.nJ(context));
            ry(byteArrayOutputStream, String.valueOf(VERSION.SDK_INT));
            ry(byteArrayOutputStream, bK.xU(context));
            ry(byteArrayOutputStream, bK.xS(context));
            ry(byteArrayOutputStream, bK.xP(context) + "");
            ry(byteArrayOutputStream, bK.xO(context) + "");
            ry(byteArrayOutputStream, bK.xV(context));
            ry(byteArrayOutputStream, bK.xN(context));
            if (z) {
                ry(byteArrayOutputStream, "");
            } else {
                ry(byteArrayOutputStream, bK.xK(context));
            }
            if (z) {
                ry(byteArrayOutputStream, "");
            } else {
                ry(byteArrayOutputStream, bK.xJ(context));
            }
            if (z) {
                ry(byteArrayOutputStream, "");
                ry(byteArrayOutputStream, "");
            } else {
                String[] xM = bK.xM(context);
                ry(byteArrayOutputStream, xM[0]);
                ry(byteArrayOutputStream, xM[1]);
            }
            Object xd = bw.xd(byteArrayOutputStream.toByteArray());
            Key xe = bw.xe(context);
            if (xd.length <= 117) {
                rl = aG.rl(xd, xe);
            } else {
                Object obj = new byte[117];
                System.arraycopy(xd, 0, obj, 0, 117);
                obj = aG.rl(obj, xe);
                rl = new byte[((xd.length + 128) - 117)];
                System.arraycopy(obj, 0, rl, 0, 128);
                System.arraycopy(xd, 117, rl, 128, xd.length - 117);
            }
            return aG.ro(rl);
        } catch (Throwable th) {
            D.mF(th, "CInfo", "InitXInfo");
            return null;
        }
    }

    private static void ry(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            rz(byteArrayOutputStream, (byte) 0, new byte[0]);
            return;
        }
        byte length = str.getBytes().length <= 255 ? (byte) str.getBytes().length : (byte) -1;
        try {
            rz(byteArrayOutputStream, length, str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            rz(byteArrayOutputStream, length, str.getBytes());
        }
    }

    private static void rz(ByteArrayOutputStream byteArrayOutputStream, byte b, byte[] bArr) {
        int i = 0;
        try {
            byteArrayOutputStream.write(new byte[]{(byte) b});
            int i2 = b <= (byte) 0 ? 0 : 1;
            if ((b & 255) < 255) {
                i = 1;
            }
            if ((i & i2) != 0) {
                byteArrayOutputStream.write(bArr);
            } else if ((b & 255) == 255) {
                byteArrayOutputStream.write(bArr, 0, 255);
            }
        } catch (Throwable e) {
            D.mF(e, "CInfo", "writeField");
        }
    }
}
