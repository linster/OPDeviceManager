package com.loc;

import android.text.TextUtils;

import java.util.zip.CRC32;

/* compiled from: Req */
public class bo {
    public String A;
    public String B;
    public String C;
    public String D;
    public String E;
    public String F;
    public byte[] G;
    public String a;
    public short b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p;
    public String q;
    public String r;
    public String s;
    public String t;
    public String u;
    public String v;
    public String w;
    public String x;
    public String y;
    public String z;

    public bo() {
        this.a = "1";
        this.b = (short) 0;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = null;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
    }

    public byte[] a() {
        Object bytes;
        int length;
        Object e;
        b();
        int i = 3072;
        if (this.G != null) {
            i = (this.G.length + 1) + 3072;
        }
        Object obj = new byte[i];
        obj[0] = (byte) Byte.parseByte(this.a);
        Object b = br.b(this.b);
        System.arraycopy(b, 0, obj, 1, b.length);
        i = b.length + 1;
        try {
            bytes = this.c.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e2) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.d.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e3) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.o.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e4) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.e.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e5) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.f.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e6) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.g.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e7) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.u.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e8) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.h.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e9) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.p.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e10) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.q.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e11) {
            obj[i] = null;
            i++;
        }
        if (TextUtils.isEmpty(this.t)) {
            obj[i] = null;
            i++;
        } else {
            bytes = b(this.t);
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        try {
            bytes = this.v.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e12) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.w.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e13) {
            obj[i] = null;
            i++;
        }
        try {
            bytes = this.x.getBytes("GBK");
            obj[i] = (byte) ((byte) bytes.length);
            i++;
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        } catch (Exception e14) {
            obj[i] = null;
            i++;
        }
        obj[i] = (byte) Byte.parseByte(this.y);
        i++;
        obj[i] = (byte) Byte.parseByte(this.j);
        i++;
        if (this.j.equals("1")) {
            obj[i] = (byte) Byte.parseByte(this.k);
            i++;
        }
        if (this.j.equals("1") || this.j.equals("2")) {
            bytes = br.c(Integer.parseInt(this.l));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        if (this.j.equals("1") || this.j.equals("2")) {
            bytes = br.c(Integer.parseInt(this.m));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        if (this.j.equals("1") || this.j.equals("2")) {
            bytes = br.e(this.n);
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
        }
        obj[i] = (byte) Byte.parseByte(this.z);
        i++;
        if (this.z.equals("1")) {
            bytes = br.e(a("mcc"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.e(a("mnc"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.e(a("lac"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.f(a("cellid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            length = bytes.length + i;
            i = Integer.parseInt(a("signal"));
            if (i > 127) {
                i = 0;
            } else if (i < -128) {
                i = 0;
            }
            obj[length] = (byte) ((byte) i);
            i = length + 1;
            if (this.B.length() != 0) {
                int length2 = this.B.split("\\*").length;
                obj[i] = (byte) ((byte) length2);
                i++;
                length = 0;
                while (length < length2) {
                    e = br.e(a("lac", length));
                    System.arraycopy(e, 0, obj, i, e.length);
                    i += e.length;
                    e = br.f(a("cellid", length));
                    System.arraycopy(e, 0, obj, i, e.length);
                    int length3 = e.length + i;
                    i = Integer.parseInt(a("signal", length));
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
        } else if (this.z.equals("2")) {
            bytes = br.e(a("mcc"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.e(a("sid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.e(a("nid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.e(a("bid"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.f(a("lon"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            i += bytes.length;
            bytes = br.f(a("lat"));
            System.arraycopy(bytes, 0, obj, i, bytes.length);
            length = bytes.length + i;
            i = Integer.parseInt(a("signal"));
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
        if (this.C.length() != 0) {
            obj[i] = 1;
            i++;
            try {
                String[] split = this.C.split(",");
                e = b(split[0]);
                System.arraycopy(e, 0, obj, i, e.length);
                i += e.length;
                try {
                    e = split[2].getBytes("GBK");
                    obj[i] = (byte) ((byte) e.length);
                    i++;
                    System.arraycopy(e, 0, obj, i, e.length);
                    i += e.length;
                } catch (Exception e15) {
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
            } catch (Exception e16) {
                bytes = b("00:00:00:00:00:00");
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
        String[] split2 = this.D.split("\\*");
        if (TextUtils.isEmpty(this.D) || split2.length == 0) {
            obj[i] = null;
            i++;
        } else {
            obj[i] = (byte) ((byte) split2.length);
            length = i + 1;
            for (String split3 : split2) {
                String[] split4 = split3.split(",");
                Object b2 = b(split4[0]);
                System.arraycopy(b2, 0, obj, length, b2.length);
                length += b2.length;
                try {
                    b2 = split4[2].getBytes("GBK");
                    obj[length] = (byte) ((byte) b2.length);
                    length++;
                    System.arraycopy(b2, 0, obj, length, b2.length);
                    length += b2.length;
                } catch (Exception e17) {
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
            b = br.b(Integer.parseInt(this.E));
            System.arraycopy(b, 0, obj, length, b.length);
            i = b.length + length;
        }
        try {
            bytes = this.F.getBytes("GBK");
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
        } catch (Exception e18) {
            obj[i] = null;
            i++;
        }
        if (this.G == null) {
            length = 0;
        } else {
            length = this.G.length;
        }
        e = br.b(length);
        System.arraycopy(e, 0, obj, i, e.length);
        i += e.length;
        if (length > 0) {
            System.arraycopy(this.G, 0, obj, i, this.G.length);
            i += this.G.length;
        }
        bytes = new byte[i];
        System.arraycopy(obj, 0, bytes, 0, i);
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        e = br.a(crc32.getValue());
        byte[] bArr = new byte[(e.length + i)];
        System.arraycopy(bytes, 0, bArr, 0, i);
        System.arraycopy(e, 0, bArr, i, e.length);
        i = e.length;
        a(bArr, 0);
        return bArr;
    }

    private byte[] b(String str) {
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

    public String a(String str) {
        if (!this.A.contains(str + ">")) {
            return "0";
        }
        int indexOf = this.A.indexOf(str + ">");
        return this.A.substring((indexOf + str.length()) + 1, this.A.indexOf("</" + str));
    }

    private String a(String str, int i) {
        String[] split = this.B.split("\\*")[i].split(",");
        if (str.equals("lac")) {
            return split[0];
        }
        if (str.equals("cellid")) {
            return split[1];
        }
        if (str.equals("signal")) {
            return split[2];
        }
        return null;
    }

    public void b() {
        if (TextUtils.isEmpty(this.a)) {
            this.a = "";
        }
        if (TextUtils.isEmpty(this.c)) {
            this.c = "";
        }
        if (TextUtils.isEmpty(this.d)) {
            this.d = "";
        }
        if (TextUtils.isEmpty(this.e)) {
            this.e = "";
        }
        if (TextUtils.isEmpty(this.f)) {
            this.f = "";
        }
        if (TextUtils.isEmpty(this.g)) {
            this.g = "";
        }
        if (TextUtils.isEmpty(this.h)) {
            this.h = "";
        }
        if (TextUtils.isEmpty(this.i)) {
            this.i = "";
        }
        if (TextUtils.isEmpty(this.j)) {
            this.j = "0";
        } else if (!(this.j.equals("1") || this.j.equals("2"))) {
            this.j = "0";
        }
        if (TextUtils.isEmpty(this.k)) {
            this.k = "0";
        } else if (!(this.k.equals("0") || this.k.equals("1"))) {
            this.k = "0";
        }
        if (TextUtils.isEmpty(this.l)) {
            this.l = "";
        }
        if (TextUtils.isEmpty(this.m)) {
            this.m = "";
        }
        if (TextUtils.isEmpty(this.n)) {
            this.n = "";
        }
        if (TextUtils.isEmpty(this.o)) {
            this.o = "";
        }
        if (TextUtils.isEmpty(this.p)) {
            this.p = "";
        }
        if (TextUtils.isEmpty(this.q)) {
            this.q = "";
        }
        if (TextUtils.isEmpty(this.r)) {
            this.r = "";
        }
        if (TextUtils.isEmpty(this.s)) {
            this.s = "";
        }
        if (TextUtils.isEmpty(this.t)) {
            this.t = "";
        }
        if (TextUtils.isEmpty(this.u)) {
            this.u = "";
        }
        if (TextUtils.isEmpty(this.v)) {
            this.v = "";
        }
        if (TextUtils.isEmpty(this.w)) {
            this.w = "";
        }
        if (TextUtils.isEmpty(this.x)) {
            this.x = "";
        }
        if (TextUtils.isEmpty(this.y)) {
            this.y = "0";
        } else if (!(this.y.equals("1") || this.y.equals("2"))) {
            this.y = "0";
        }
        if (TextUtils.isEmpty(this.z)) {
            this.z = "0";
        } else if (!(this.z.equals("1") || this.z.equals("2"))) {
            this.z = "0";
        }
        if (TextUtils.isEmpty(this.A)) {
            this.A = "";
        }
        if (TextUtils.isEmpty(this.B)) {
            this.B = "";
        }
        if (TextUtils.isEmpty(this.C)) {
            this.C = "";
        }
        if (TextUtils.isEmpty(this.D)) {
            this.D = "";
        }
        if (TextUtils.isEmpty(this.E)) {
            this.E = "";
        }
        if (TextUtils.isEmpty(this.F)) {
            this.F = "";
        }
        if (this.G == null) {
            this.G = new byte[0];
        }
    }

    public static void a(byte[] bArr, int i) {
    }
}
