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
public class aW {
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private Context rh;
    private int ri = 0;
    private int rj = 0;

    protected aW(Context context) {
        this.rh = context;
        sM(768);
        sN(768);
    }

    protected static byte[] sL(byte[] bArr, int i) {
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

    private File sS() {
        boolean equals;
        if (Process.myUid() == 1000) {
            return null;
        }
        File file;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!tc() || r0) {
            File file2 = new File(te(this.rh).getPath() + File.separator + "carrierdata");
            if (file2.exists() && file2.isDirectory()) {
                File[] listFiles = file2.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    ArrayList sT = sT(listFiles);
                    if (sT.size() == 1) {
                        if ((((File) sT.get(0)).length() >= ((long) this.ri) ? 1 : 0) == 0) {
                            file = (File) sT.get(0);
                            return file;
                        }
                    } else if (sT.size() >= 2) {
                        file = (File) sT.get(0);
                        File file3 = (File) sT.get(1);
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

    private static ArrayList sT(File[] fileArr) {
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

    private int sU() {
        int i = 0;
        if (Process.myUid() == 1000) {
            return 0;
        }
        boolean equals;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!tc() || r0) {
            File file = new File(te(this.rh).getPath() + File.separator + "carrierdata");
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    ArrayList sT = sT(listFiles);
                    if (sT.size() == 1) {
                        if (((File) sT.get(0)).length() > 0) {
                            i = 1;
                        }
                        i = i == 0 ? 10 : 1;
                    } else if (sT.size() >= 2) {
                        i = 2;
                    }
                }
            }
        }
        return i;
    }

    private File sV() {
        if (Process.myUid() == 1000) {
            return null;
        }
        File te;
        boolean equals;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!tc() || r0) {
            te = te(this.rh);
            if (te != null) {
                File file = new File(te.getPath() + File.separator + "carrierdata");
                if (file.exists() && file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null && listFiles.length > 0) {
                        ArrayList sT = sT(listFiles);
                        if (sT.size() >= 2) {
                            te = (File) sT.get(0);
                            File file2 = (File) sT.get(1);
                            if (te.getName().compareTo(file2.getName()) > 0) {
                                te = file2;
                            }
                            return te;
                        }
                    }
                }
            }
        }
        te = null;
        return te;
    }

    private File sW(long j) {
        boolean equals;
        boolean z = false;
        if (Process.myUid() == 1000) {
            return null;
        }
        File file;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = z;
        }
        if (tc() && !r0) {
            file = null;
        } else {
            if (!(td() > ((long) (this.d / 2)) ? true : z)) {
                return null;
            }
            File file2 = new File(te(this.rh).getPath() + File.separator + "carrierdata");
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

    protected static byte[] sX(byte[] bArr) {
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

    protected static bv sY(Location location, bf bfVar, int i, byte b, long j, boolean z) {
        bv bvVar = new bv();
        if (i <= 0 || i > 3 || bfVar == null) {
            return null;
        }
        int i2;
        int i3;
        Object obj = (i == 1 || i == 3) ? 1 : null;
        Object obj2 = (i == 2 || i == 3) ? 1 : null;
        Object bytes = bfVar.tR().getBytes();
        System.arraycopy(bytes, 0, bvVar.c, 0, sZ(bytes.length, bvVar.c.length));
        bytes = bfVar.tH().getBytes();
        System.arraycopy(bytes, 0, bvVar.g, 0, sZ(bytes.length, bvVar.g.length));
        bytes = bfVar.tI().getBytes();
        System.arraycopy(bytes, 0, bvVar.a, 0, sZ(bytes.length, bvVar.a.length));
        bytes = bfVar.tJ().getBytes();
        System.arraycopy(bytes, 0, bvVar.b, 0, sZ(bytes.length, bvVar.b.length));
        bvVar.d = (short) ((short) bfVar.tS());
        bvVar.e = (short) ((short) bfVar.tT());
        bvVar.f = (byte) ((byte) bfVar.tU());
        bytes = bfVar.tV().getBytes();
        System.arraycopy(bytes, 0, bvVar.h, 0, sZ(bytes.length, bvVar.h.length));
        long j2 = j / 1000;
        bytes = (location != null && bfVar.tG()) ? 1 : null;
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
                if (!bf.uy(bfVar.ux()) || !aF.oF) {
                    buVar.g = (byte) 0;
                    buVar.h = (byte) b;
                    if (buVar.d > 25) {
                        buVar.h = (byte) 5;
                    }
                    buVar.i = System.currentTimeMillis();
                    buVar.j = bfVar.tQ();
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
            buVar.j = bfVar.tQ();
            btVar.c = buVar;
            i2 = 1;
            bvVar.j.add(btVar);
        } else if (!z) {
            return null;
        } else {
            btVar = new bt();
            btVar.b = (int) j2;
            by byVar = new by();
            byVar.a = (byte) bfVar.uc();
            for (byte b2 = (byte) 0; b2 < byVar.a; b2++) {
                bz bzVar = new bz();
                bzVar.a = (byte) ((byte) bfVar.ud(b2).length());
                System.arraycopy(bfVar.ud(b2).getBytes(), 0, bzVar.b, 0, bzVar.a);
                bzVar.c = bfVar.ue(b2);
                bzVar.d = bfVar.uf(b2);
                bzVar.e = bfVar.ug(b2);
                bzVar.f = bfVar.uh(b2);
                bzVar.g = (byte) bfVar.ui(b2);
                bzVar.h = (byte) ((byte) bfVar.uj(b2).length());
                System.arraycopy(bfVar.uj(b2).getBytes(), 0, bzVar.i, 0, bzVar.h);
                bzVar.j = (byte) bfVar.uk(b2);
                byVar.b.add(bzVar);
            }
            btVar.g = byVar;
            i2 = 1;
            bvVar.j.add(btVar);
        }
        if (!(!bfVar.tE() || bfVar.tK() || obj == null || z)) {
            bt btVar2 = new bt();
            btVar2.b = (int) j2;
            db dbVar = new db();
            List tW = bfVar.tW(location.getSpeed());
            if (tW != null && tW.size() >= 3) {
                dbVar.a = (short) ((short) ((Integer) tW.get(0)).intValue());
                dbVar.b = ((Integer) tW.get(1)).intValue();
            }
            dbVar.c = (byte) bfVar.tN();
            List tO = bfVar.tO();
            dbVar.d = (byte) ((byte) tO.size());
            for (i3 = 0; i3 < tO.size(); i3++) {
                cf cfVar = new cf();
                cfVar.a = (short) ((short) ((NeighboringCellInfo) tO.get(i3)).getLac());
                cfVar.b = ((NeighboringCellInfo) tO.get(i3)).getCid();
                cfVar.c = (byte) ((byte) ((NeighboringCellInfo) tO.get(i3)).getRssi());
                dbVar.e.add(cfVar);
            }
            btVar2.d = dbVar;
            i2 = 2;
            bvVar.j.add(btVar2);
        }
        i3 = i2;
        if (bfVar.tE() && bfVar.tK() && obj != null && !z) {
            bt btVar3 = new bt();
            btVar3.b = (int) j2;
            ce ceVar = new ce();
            List tX = bfVar.tX(location.getSpeed());
            if (tX != null && tX.size() >= 6) {
                ceVar.a = ((Integer) tX.get(3)).intValue();
                ceVar.b = ((Integer) tX.get(4)).intValue();
                ceVar.c = (short) ((short) ((Integer) tX.get(0)).intValue());
                ceVar.d = (short) ((short) ((Integer) tX.get(1)).intValue());
                ceVar.e = ((Integer) tX.get(2)).intValue();
                ceVar.f = (byte) bfVar.tN();
            }
            btVar3.e = ceVar;
            i3++;
            bvVar.j.add(btVar3);
        }
        if (!(!bfVar.tF() || obj2 == null || z)) {
            btVar2 = new bt();
            ca caVar = new ca();
            List tY = bfVar.tY();
            btVar2.b = (int) (((Long) tY.get(0)).longValue() / 1000);
            caVar.a = (byte) ((byte) (tY.size() - 1));
            for (int i4 = 1; i4 < tY.size(); i4++) {
                List list = (List) tY.get(i4);
                if (list != null && list.size() >= 3) {
                    cb cbVar = new cb();
                    obj2 = ((String) list.get(0)).getBytes();
                    System.arraycopy(obj2, 0, cbVar.a, 0, sZ(obj2.length, cbVar.a.length));
                    cbVar.b = (short) ((short) ((Integer) list.get(1)).intValue());
                    bytes = ((String) list.get(2)).getBytes();
                    System.arraycopy(bytes, 0, cbVar.c, 0, sZ(bytes.length, cbVar.c.length));
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

    private static int sZ(int i, int i2) {
        return i >= i2 ? i2 : i;
    }

    protected static byte[] ta(BitSet bitSet) {
        byte[] bArr = new byte[(bitSet.size() / 8)];
        for (int i = 0; i < bitSet.size(); i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) ((byte) (((!bitSet.get(i) ? 0 : 1) << (7 - (i % 8))) | bArr[i2]));
        }
        return bArr;
    }

    protected static BitSet tb(byte[] bArr) {
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

    protected static boolean tc() {
        if (VERSION.SDK_INT >= 9) {
            try {
                return ((Boolean) Environment.class.getMethod("isExternalStorageRemovable", null).invoke(null, null)).booleanValue();
            } catch (Exception e) {
            }
        }
        return true;
    }

    protected static long td() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
    }

    protected static File te(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/files/"));
    }

    public static Object tf(Object obj, String str, Object... objArr) {
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

    public static int tg(Object obj, String str, Object... objArr) {
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

    protected void sM(int i) {
        this.b = i;
        this.d = (((this.b << 3) * 1500) + this.b) + 4;
        if (this.b == 256 || this.b == 768) {
            this.ri = this.d / 100;
            return;
        }
        if (this.b == 8736) {
            this.ri = this.d - 5000;
        }
    }

    protected void sN(int i) {
        this.c = i;
        this.e = (((this.c << 3) * 1000) + this.c) + 4;
        this.rj = this.e;
    }

    protected int sO() {
        return this.b;
    }

    protected synchronized File sP(long j) {
        File sS;
        sS = sS();
        if (sS == null) {
            sS = sW(j);
        }
        return sS;
    }

    protected synchronized boolean sQ(long j) {
        int sU = sU();
        return sU != 0 ? sU != 1 ? sU == 2 : sW(j) != null : false;
    }

    protected File sR() {
        return sV();
    }
}
