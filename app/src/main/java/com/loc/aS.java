package com.loc;

import android.text.TextUtils;
import java.util.zip.CRC32;

public class aS {
    public String a = "1";
    public short b = (short) 0;
    public String qA = null;
    public String qB = null;
    public String qC = null;
    public String qD = null;
    public String qE = null;
    public String qF = null;
    public String qG = null;
    public byte[] qH = null;
    public String qd = null;
    public String qe = null;
    public String qf = null;
    public String qg = null;
    public String qh = null;
    public String qi = null;
    public String qj = null;
    public String qk = null;
    public String ql = null;
    public String qm = null;
    public String qn = null;
    public String qo = null;
    public String qp = null;
    public String qq = null;
    public String qr = null;
    public String qs = null;
    public String qt = null;
    public String qu = null;
    public String qv = null;
    public String qw = null;
    public String qx = null;
    public String qy = null;
    public String qz = null;

    private byte[] rW(String str) {
        String[] split = str.split(":");
        if (split == null || split.length != 6) {
            String[] strArr = new String[6];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = "0";
            }
            split = strArr;
        }
        byte[] bArr = new byte[6];
        for (int i2 = 0; i2 < split.length; i2++) {
            if (split[i2].length() > 2) {
                split[i2] = split[i2].substring(0, 2);
            }
            bArr[i2] = (byte) ((byte) Integer.parseInt(split[i2], 16));
        }
        return bArr;
    }

    private String rY(String str, int i) {
        String[] split = this.qC.split("\\*")[i].split(",");
        return !str.equals("lac") ? !str.equals("cellid") ? !str.equals("signal") ? null : split[2] : split[1] : split[0];
    }

    public static void sa(byte[] bArr, int i) {
    }

    public byte[] rV() {
        Object bytes;
        int length;
        Object wr;
        rZ();
        int i = 3072;
        if (this.qH != null) {
            i = (this.qH.length + 1) + 3072;
        }
        Object obj = new byte[i];
        obj[0] = (byte) Byte.parseByte(this.a);
        Object ws = bq.ws(this.b);
        System.arraycopy(ws, 0, obj, 1, ws.length);
        i = ws.length + 1;
        try {
            bytes = this.qd.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qe.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e2) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qp.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e3) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qf.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e4) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qg.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e5) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qh.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e6) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qv.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e7) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qi.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e8) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qq.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e9) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qr.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e10) {
            obj[i] = null;
            i++;
        }
        if (TextUtils.isEmpty(this.qu)) {
            obj[i] = null;
            i++;
        } else {
            bytes = rW(this.qu);
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        try {
            bytes = this.qw.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e11) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qx.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e12) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.qy.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e13) {
            obj[i] = null;
            i++;
        }
        obj[i] = (byte) Byte.parseByte(this.qz);
        i++;
        obj[i] = (byte) Byte.parseByte(this.qk);
        i++;
        if (this.qk.equals("1")) {
            obj[i] = (byte) Byte.parseByte(this.ql);
            i++;
        }
        if (this.qk.equals("1") || this.qk.equals("2")) {
            bytes = bq.wv(Integer.parseInt(this.qm));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        if (this.qk.equals("1") || this.qk.equals("2")) {
            bytes = bq.wv(Integer.parseInt(this.qn));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        if (this.qk.equals("1") || this.qk.equals("2")) {
            bytes = bq.wr(this.qo);
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        obj[i] = (byte) Byte.parseByte(this.qA);
        i++;
        if (this.qA.equals("1")) {
            bytes = bq.wr(rX("mcc"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wr(rX("mnc"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wr(rX("lac"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wu(rX("cellid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            length = bytes.length + i;
            i = Integer.parseInt(rX("signal"));
            if (i > 127) {
                i = 0;
            } else if (i < -128) {
                i = 0;
            }
            obj[length] = (byte) ((byte) i);
            i = length + 1;
            if (this.qC.length() != 0) {
                int length2 = this.qC.split("\\*").length;
                obj[i] = (byte) ((byte) length2);
                i++;
                length = 0;
                while (length < length2) {
                    wr = bq.wr(rY("lac", length));
                    System.arraycopy(wr, 0, obj, i, wr.length);
                    i += wr.length;
                    wr = bq.wu(rY("cellid", length));
                    System.arraycopy(wr, 0, obj, i, wr.length);
                    int length3 = wr.length + i;
                    i = Integer.parseInt(rY("signal", length));
                    if (i > 127) {
                        i = 0;
                    } else if (i < -128) {
                        i = 0;
                    }
                    obj[length3] = (byte) ((byte) i);
                    length++;
                    i = length3 + 1;
                }
            } else {
                obj[i] = null;
                i++;
            }
        } else if (this.qA.equals("2")) {
            bytes = bq.wr(rX("mcc"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wr(rX("sid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wr(rX("nid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wr(rX("bid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wu(rX("lon"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = bq.wu(rX("lat"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            length = bytes.length + i;
            i = Integer.parseInt(rX("signal"));
            if (i > 127) {
                i = 0;
            } else if (i < -128) {
                i = 0;
            }
            obj[length] = (byte) ((byte) i);
            i = length + 1;
            obj[i] = null;
            i++;
        }
        if (this.qD.length() != 0) {
            obj[i] = 1;
            i++;
            try {
                String[] split = this.qD.split(",");
                wr = rW(split[0]);
                System.arraycopy(wr, 0, obj, i, wr.length);
                i += wr.length;
                try {
                    wr = split[2].getBytes("GBK");
                    obj[i] = (byte) ((byte) wr.length);
                    i++;
                    System.arraycopy(wr, 0, obj, i, wr.length);
                    i += wr.length;
                } catch (Exception e14) {
                    obj[i] = (byte) 0;
                    i++;
                }
                length = Integer.parseInt(split[1]);
                if (length > 127) {
                    length = 0;
                } else if (length < -128) {
                    length = 0;
                }
                obj[i] = (byte) Byte.parseByte(String.valueOf(length));
                i++;
            } catch (Exception e15) {
                bytes = rW("00:00:00:00:00:00");
                System.arraycopy(bytes, 0, obj, i, bytes.length);
                i += bytes.length;
                obj[i] = null;
                i++;
                obj[i] = (byte) Byte.parseByte("0");
                i++;
            }
        } else {
            obj[i] = null;
            i++;
        }
        String[] split2 = this.qE.split("\\*");
        if (TextUtils.isEmpty(this.qE) || split2.length == 0) {
            obj[i] = null;
            i++;
        } else {
            obj[i] = (byte) ((byte) split2.length);
            length = i + 1;
            for (String split3 : split2) {
                String[] split4 = split3.split(",");
                Object rW = rW(split4[0]);
                System.arraycopy(rW, 0, obj, length, rW.length);
                length += rW.length;
                try {
                    rW = split4[2].getBytes("GBK");
                    obj[length] = (byte) ((byte) rW.length);
                    length++;
                    System.arraycopy(rW, 0, obj, length, rW.length);
                    length += rW.length;
                } catch (Exception e16) {
                    obj[length] = null;
                    length++;
                }
                length3 = Integer.parseInt(split4[1]);
                if (length3 > 127) {
                    length3 = 0;
                } else if (length3 < -128) {
                    length3 = 0;
                }
                obj[length] = (byte) Byte.parseByte(String.valueOf(length3));
                length++;
            }
            ws = bq.ws(Integer.parseInt(this.qF));
            System.arraycopy(ws, 0, obj, length, ws.length);
            i = ws.length + length;
        }
        try {
            bytes = this.qG.getBytes("GBK");
            if (bytes.length > 127) {
                bytes = null;
            } else if (bytes.length < -128) {
                bytes = null;
            }
            if (bytes != null) {
                obj[i] = (byte) ((byte) bytes.length);
                i++;
                System.arraycopy(bytes, 0, obj, i, bytes.length);
                i += bytes.length;
            } else {
                obj[i] = (byte) 0;
                i++;
            }
        } catch (Exception e17) {
            obj[i] = null;
            i++;
        }
        length = this.qH == null ? 0 : this.qH.length;
        wr = bq.ws(length);
        System.arraycopy(wr, 0, obj, i, wr.length);
        i += wr.length;
        if (length > 0) {
            System.arraycopy(this.qH, 0, obj, i, this.qH.length);
            i += this.qH.length;
        }
        bytes = new byte[i];
        System.arraycopy(obj, 0, bytes, 0, i);
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        wr = bq.wq(crc32.getValue());
        Object obj2 = new byte[(wr.length + i)];
        System.arraycopy(bytes, 0, obj2, 0, i);
        System.arraycopy(wr, 0, obj2, i, wr.length);
        i = wr.length;
        sa(obj2, 0);
        return obj2;
    }

    public String rX(String str) {
        if (!this.qB.contains(str + ">")) {
            return "0";
        }
        int indexOf = this.qB.indexOf(str + ">");
        return this.qB.substring((indexOf + str.length()) + 1, this.qB.indexOf("</" + str));
    }

    public void rZ() {
        if (TextUtils.isEmpty(this.a)) {
            this.a = "";
        }
        if (TextUtils.isEmpty(this.qd)) {
            this.qd = "";
        }
        if (TextUtils.isEmpty(this.qe)) {
            this.qe = "";
        }
        if (TextUtils.isEmpty(this.qf)) {
            this.qf = "";
        }
        if (TextUtils.isEmpty(this.qg)) {
            this.qg = "";
        }
        if (TextUtils.isEmpty(this.qh)) {
            this.qh = "";
        }
        if (TextUtils.isEmpty(this.qi)) {
            this.qi = "";
        }
        if (TextUtils.isEmpty(this.qj)) {
            this.qj = "";
        }
        if (TextUtils.isEmpty(this.qk)) {
            this.qk = "0";
        } else if (!(this.qk.equals("1") || this.qk.equals("2"))) {
            this.qk = "0";
        }
        if (TextUtils.isEmpty(this.ql)) {
            this.ql = "0";
        } else if (!(this.ql.equals("0") || this.ql.equals("1"))) {
            this.ql = "0";
        }
        if (TextUtils.isEmpty(this.qm)) {
            this.qm = "";
        }
        if (TextUtils.isEmpty(this.qn)) {
            this.qn = "";
        }
        if (TextUtils.isEmpty(this.qo)) {
            this.qo = "";
        }
        if (TextUtils.isEmpty(this.qp)) {
            this.qp = "";
        }
        if (TextUtils.isEmpty(this.qq)) {
            this.qq = "";
        }
        if (TextUtils.isEmpty(this.qr)) {
            this.qr = "";
        }
        if (TextUtils.isEmpty(this.qs)) {
            this.qs = "";
        }
        if (TextUtils.isEmpty(this.qt)) {
            this.qt = "";
        }
        if (TextUtils.isEmpty(this.qu)) {
            this.qu = "";
        }
        if (TextUtils.isEmpty(this.qv)) {
            this.qv = "";
        }
        if (TextUtils.isEmpty(this.qw)) {
            this.qw = "";
        }
        if (TextUtils.isEmpty(this.qx)) {
            this.qx = "";
        }
        if (TextUtils.isEmpty(this.qy)) {
            this.qy = "";
        }
        if (TextUtils.isEmpty(this.qz)) {
            this.qz = "0";
        } else if (!(this.qz.equals("1") || this.qz.equals("2"))) {
            this.qz = "0";
        }
        if (TextUtils.isEmpty(this.qA)) {
            this.qA = "0";
        } else if (!(this.qA.equals("1") || this.qA.equals("2"))) {
            this.qA = "0";
        }
        if (TextUtils.isEmpty(this.qB)) {
            this.qB = "";
        }
        if (TextUtils.isEmpty(this.qC)) {
            this.qC = "";
        }
        if (TextUtils.isEmpty(this.qD)) {
            this.qD = "";
        }
        if (TextUtils.isEmpty(this.qE)) {
            this.qE = "";
        }
        if (TextUtils.isEmpty(this.qF)) {
            this.qF = "";
        }
        if (TextUtils.isEmpty(this.qG)) {
            this.qG = "";
        }
        if (this.qH == null) {
            this.qH = new byte[0];
        }
    }
}
