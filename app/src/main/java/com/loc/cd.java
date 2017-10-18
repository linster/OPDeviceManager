package com.loc;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.NeighboringCellInfo;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/* compiled from: Unknown */
public class cd {
    private Context a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;

    protected cd(Context context) {
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.a = context;
        a(768);
        b(768);
    }

    private static int a(int i, int i2) {
        return i >= i2 ? i2 : i;
    }

    protected static bv a(Location location, cg cgVar, int i, byte b, long j, boolean z) {
        bv bvVar = new bv();
        if (i <= 0 || i > 3 || cgVar == null) {
            return null;
        }
        int i2;
        int i3;
        Object obj = (i == 1 || i == 3) ? 1 : null;
        Object obj2 = (i == 2 || i == 3) ? 1 : null;
        Object bytes = cgVar.o().getBytes();
        System.arraycopy(bytes, 0, bvVar.c, 0, a(bytes.length, bvVar.c.length));
        bytes = cgVar.f().getBytes();
        System.arraycopy(bytes, 0, bvVar.g, 0, a(bytes.length, bvVar.g.length));
        bytes = cgVar.g().getBytes();
        System.arraycopy(bytes, 0, bvVar.a, 0, a(bytes.length, bvVar.a.length));
        bytes = cgVar.h().getBytes();
        System.arraycopy(bytes, 0, bvVar.b, 0, a(bytes.length, bvVar.b.length));
        bvVar.d = (short) ((short) cgVar.p());
        bvVar.e = (short) ((short) cgVar.q());
        bvVar.f = (byte) ((byte) cgVar.r());
        bytes = cgVar.s().getBytes();
        System.arraycopy(bytes, 0, bvVar.h, 0, a(bytes.length, bvVar.h.length));
        long j2 = j / 1000;
        bytes = (location != null && cgVar.e()) ? 1 : null;
        bt btVar;
        if (bytes != null) {
            btVar = new bt();
            btVar.b = (int) j2;
            bu buVar = new bu();
            buVar.a = (int) (location.getLongitude() * 1000000.0d);
            buVar.b = (int) (location.getLatitude() * 1000000.0d);
            buVar.c = (int) location.getAltitude();
            buVar.d = (int) location.getAccuracy();
            buVar.e = (int) location.getSpeed();
            buVar.f = (short) ((short) ((int) location.getBearing()));
            if (!Build.MODEL.equals("sdk")) {
                if (!cg.b(cgVar.x()) || !bw.b) {
                    buVar.g = (byte) 0;
                    buVar.h = (byte) b;
                    if (buVar.d > 25) {
                        buVar.h = (byte) 5;
                    }
                    buVar.i = System.currentTimeMillis();
                    buVar.j = cgVar.n();
                    btVar.c = buVar;
                    i2 = 1;
                    bvVar.j.add(btVar);
                }
            }
            buVar.g = (byte) 1;
            buVar.h = (byte) b;
            if (buVar.d > 25) {
                buVar.h = (byte) 5;
            }
            buVar.i = System.currentTimeMillis();
            buVar.j = cgVar.n();
            btVar.c = buVar;
            i2 = 1;
            bvVar.j.add(btVar);
        } else if (!z) {
            return null;
        } else {
            btVar = new bt();
            btVar.b = (int) j2;
            by byVar = new by();
            byVar.a = (byte) cgVar.w();
            for (i2 = 0; i2 < byVar.a; i2++) {
                bz bzVar = new bz();
                bzVar.a = (byte) ((byte) cgVar.b(i2).length());
                System.arraycopy(cgVar.b(i2).getBytes(), 0, bzVar.b, 0, bzVar.a);
                bzVar.c = cgVar.c(i2);
                bzVar.d = cgVar.d(i2);
                bzVar.e = cgVar.e(i2);
                bzVar.f = cgVar.f(i2);
                bzVar.g = (byte) cgVar.g(i2);
                bzVar.h = (byte) ((byte) cgVar.h(i2).length());
                System.arraycopy(cgVar.h(i2).getBytes(), 0, bzVar.i, 0, bzVar.h);
                bzVar.j = (byte) cgVar.i(i2);
                byVar.b.add(bzVar);
            }
            btVar.g = byVar;
            i2 = 1;
            bvVar.j.add(btVar);
        }
        if (!(!cgVar.c() || cgVar.i() || obj == null || z)) {
            bt btVar2 = new bt();
            btVar2.b = (int) j2;
            db dbVar = new db();
            List a = cgVar.a(location.getSpeed());
            if (a != null && a.size() >= 3) {
                dbVar.a = (short) ((short) ((Integer) a.get(0)).intValue());
                dbVar.b = ((Integer) a.get(1)).intValue();
            }
            dbVar.c = (byte) cgVar.k();
            List l = cgVar.l();
            dbVar.d = (byte) ((byte) l.size());
            for (i3 = 0; i3 < l.size(); i3++) {
                cf cfVar = new cf();
                cfVar.a = (short) ((short) ((NeighboringCellInfo) l.get(i3)).getLac());
                cfVar.b = ((NeighboringCellInfo) l.get(i3)).getCid();
                cfVar.c = (byte) ((byte) ((NeighboringCellInfo) l.get(i3)).getRssi());
                dbVar.e.add(cfVar);
            }
            btVar2.d = dbVar;
            i2 = 2;
            bvVar.j.add(btVar2);
        }
        i3 = i2;
        if (cgVar.c() && cgVar.i() && obj != null && !z) {
            bt btVar3 = new bt();
            btVar3.b = (int) j2;
            ce ceVar = new ce();
            List b2 = cgVar.b(location.getSpeed());
            if (b2 != null && b2.size() >= 6) {
                ceVar.a = ((Integer) b2.get(3)).intValue();
                ceVar.b = ((Integer) b2.get(4)).intValue();
                ceVar.c = (short) ((short) ((Integer) b2.get(0)).intValue());
                ceVar.d = (short) ((short) ((Integer) b2.get(1)).intValue());
                ceVar.e = ((Integer) b2.get(2)).intValue();
                ceVar.f = (byte) cgVar.k();
            }
            btVar3.e = ceVar;
            i3++;
            bvVar.j.add(btVar3);
        }
        if (!(!cgVar.d() || obj2 == null || z)) {
            btVar2 = new bt();
            ca caVar = new ca();
            List t = cgVar.t();
            btVar2.b = (int) (((Long) t.get(0)).longValue() / 1000);
            caVar.a = (byte) ((byte) (t.size() - 1));
            for (int i4 = 1; i4 < t.size(); i4++) {
                List list = (List) t.get(i4);
                if (list != null && list.size() >= 3) {
                    cb cbVar = new cb();
                    obj2 = ((String) list.get(0)).getBytes();
                    System.arraycopy(obj2, 0, cbVar.a, 0, a(obj2.length, cbVar.a.length));
                    cbVar.b = (short) ((short) ((Integer) list.get(1)).intValue());
                    bytes = ((String) list.get(2)).getBytes();
                    System.arraycopy(bytes, 0, cbVar.c, 0, a(bytes.length, cbVar.c.length));
                    caVar.b.add(cbVar);
                }
            }
            btVar2.f = caVar;
            i3++;
            bvVar.j.add(btVar2);
        }
        bvVar.i = (short) ((short) i3);
        return (i3 < 2 && !z) ? null : bvVar;
    }

    protected static File a(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/files/"));
    }

    public static Object a(Object obj, String str, Object... objArr) {
        Class cls = obj.getClass();
        Class[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(obj, objArr);
    }

    private static ArrayList a(File[] fileArr) {
        int i = 0;
        ArrayList arrayList = new ArrayList();
        while (i < fileArr.length) {
            if (fileArr[i].isFile() && fileArr[i].getName().length() == 10 && TextUtils.isDigitsOnly(fileArr[i].getName())) {
                arrayList.add(fileArr[i]);
            }
            i++;
        }
        return arrayList;
    }

    protected static byte[] a(BitSet bitSet) {
        byte[] bArr = new byte[(bitSet.size() / 8)];
        for (int i = 0; i < bitSet.size(); i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) ((byte) (((!bitSet.get(i) ? 0 : 1) << (7 - (i % 8))) | bArr[i2]));
        }
        return bArr;
    }

    protected static byte[] a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (Exception e) {
            return bArr2;
        }
    }

    protected static byte[] a(byte[] bArr, int i) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        int indexOf = new String(bArr).indexOf(0);
        if (indexOf <= 0) {
            i = 1;
        } else if (indexOf + 1 <= i) {
            i = indexOf + 1;
        }
        Object obj = new byte[i];
        System.arraycopy(bArr, 0, obj, 0, i);
        obj[i - 1] = null;
        return obj;
    }

    public static int b(Object obj, String str, Object... objArr) {
        Class cls = obj.getClass();
        Class[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return ((Integer) declaredMethod.invoke(obj, objArr)).intValue();
    }

    protected static BitSet b(byte[] bArr) {
        BitSet bitSet = new BitSet(bArr.length << 3);
        int i = 0;
        for (byte b : bArr) {
            int i2 = 7;
            while (i2 >= 0) {
                int i3 = i + 1;
                bitSet.set(i, ((b & (1 << i2)) >> i2) == 1);
                i2--;
                i = i3;
            }
        }
        return bitSet;
    }

    private File c(long j) {
        boolean z = false;
        if (Process.myUid() == 1000) {
            return null;
        }
        File file;
        boolean equals;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = z;
        }
        if (c() && !r0) {
            file = null;
        } else {
            if (!(d() > ((long) (this.d / 2)) ? true : z)) {
                return null;
            }
            File file2 = new File(a(this.a).getPath() + File.separator + "carrierdata");
            if (!file2.exists() || !file2.isDirectory()) {
                file2.mkdirs();
            }
            file = new File(file2.getPath() + File.separator + j);
            try {
                z = file.createNewFile();
            } catch (IOException e2) {
            }
        }
        return !z ? null : file;
    }

    protected static boolean c() {
        if (VERSION.SDK_INT >= 9) {
            try {
                return ((Boolean) Environment.class.getMethod("isExternalStorageRemovable", null).invoke(null, null)).booleanValue();
            } catch (Exception e) {
            }
        }
        return true;
    }

    protected static long d() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
    }

    private File e() {
        if (Process.myUid() == 1000) {
            return null;
        }
        File file;
        boolean equals;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!c() || r0) {
            File file2 = new File(a(this.a).getPath() + File.separator + "carrierdata");
            if (file2.exists() && file2.isDirectory()) {
                File[] listFiles = file2.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    ArrayList a = a(listFiles);
                    if (a.size() == 1) {
                        if ((((File) a.get(0)).length() >= ((long) this.f) ? 1 : 0) == 0) {
                            file = (File) a.get(0);
                            return file;
                        }
                    } else if (a.size() >= 2) {
                        file = (File) a.get(0);
                        File file3 = (File) a.get(1);
                        if (file.getName().compareTo(file3.getName()) <= 0) {
                            file = file3;
                        }
                        return file;
                    }
                }
            }
        }
        file = null;
        return file;
    }

    private int f() {
        boolean equals;
        int i = 0;
        if (Process.myUid() == 1000) {
            return 0;
        }
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!c() || r0) {
            File file = new File(a(this.a).getPath() + File.separator + "carrierdata");
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    ArrayList a = a(listFiles);
                    if (a.size() == 1) {
                        if (((File) a.get(0)).length() > 0) {
                            i = 1;
                        }
                        i = i == 0 ? 10 : 1;
                    } else if (a.size() >= 2) {
                        i = 2;
                    }
                }
            }
        }
        return i;
    }

    private File g() {
        if (Process.myUid() == 1000) {
            return null;
        }
        File a;
        boolean equals;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!c() || r0) {
            a = a(this.a);
            if (a != null) {
                File file = new File(a.getPath() + File.separator + "carrierdata");
                if (file.exists() && file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null && listFiles.length > 0) {
                        ArrayList a2 = a(listFiles);
                        if (a2.size() >= 2) {
                            a = (File) a2.get(0);
                            File file2 = (File) a2.get(1);
                            if (a.getName().compareTo(file2.getName()) > 0) {
                                a = file2;
                            }
                            return a;
                        }
                    }
                }
            }
        }
        a = null;
        return a;
    }

    protected int a() {
        return this.b;
    }

    protected synchronized File a(long j) {
        File e;
        e = e();
        if (e == null) {
            e = c(j);
        }
        return e;
    }

    protected void a(int i) {
        this.b = i;
        this.d = (((this.b << 3) * 1500) + this.b) + 4;
        if (this.b == 256 || this.b == 768) {
            this.f = this.d / 100;
            return;
        }
        if (this.b == 8736) {
            this.f = this.d - 5000;
        }
    }

    protected File b() {
        return g();
    }

    protected void b(int i) {
        this.c = i;
        this.e = (((this.c << 3) * 1000) + this.c) + 4;
        this.g = this.e;
    }

    protected synchronized boolean b(long j) {
        int f = f();
        return f != 0 ? f != 1 ? f == 2 : c(j) != null : false;
    }
}
