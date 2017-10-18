package com.loc;

import android.content.Context;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class az {
    static int d = 213891;
    private static volatile String nR = null;
    public static Hashtable nS = new Hashtable();
    private static bn nT = new bn();
    public static Hashtable nU = new Hashtable();
    private static TelephonyManager nV = null;
    public static int[] nW = new int[]{0, 0};

    public static void pA() {
        nT.xy();
        nS.clear();
        nU.clear();
        nW[0] = 0;
        nW[1] = 0;
    }

    private static double[] pB(int i, j jVar, String str, int i2) {
        String str2;
        int i3;
        int[] iArr = null;
        if (i2 != 0) {
            str2 = "wifi";
        } else {
            String[] split = str.split("\\|");
            iArr = new int[split.length];
            for (i3 = 0; i3 < split.length; i3++) {
                iArr[i3] = Integer.parseInt(split[i3]);
            }
            str2 = split.length != 2 ? "cdma" : "gsm";
        }
        try {
            if ((jVar.lt() <= ((long) i) ? 1 : null) != null) {
                return null;
            }
            jVar.lo((long) i);
            i3 = pC(i, jVar, str, iArr, i, ((int) jVar.lt()) - 16, str2, 0);
            if (i3 == -1) {
                return null;
            }
            jVar.lo((long) (i3 + 6));
            double[] dArr = new double[3];
            try {
                dArr[0] = ((double) pM(jVar.lr())) / 1000000.0d;
                dArr[1] = ((double) pM(jVar.lr())) / 1000000.0d;
                dArr[2] = (double) jVar.lq();
                return bq.vE(dArr[1]) ? !bq.vF(dArr[0]) ? null : dArr : null;
            } catch (Throwable th) {
                return dArr;
            }
        } catch (Throwable th2) {
            return null;
        }
    }

    private static int pC(int i, j jVar, String str, int[] iArr, int i2, int i3, String str2, int i4) {
        int i5 = i4 + 1;
        if (i5 > 25) {
            return -1;
        }
        int i6 = (((((i2 + i3) / 2) - i) / 16) * 16) + i;
        int pD = pD(jVar, str, iArr, i6, str2);
        if (i2 == i6 && i6 == i3) {
            if (pD != 0) {
                i2 = -1;
            }
            return i2;
        } else if (pD == Integer.MAX_VALUE) {
            return -1;
        } else {
            if (pD == 0) {
                return i6;
            }
            if (pD < 0) {
                return pC(i, jVar, str, iArr, i2, i6, str2, i5);
            }
            return pC(i, jVar, str, iArr, i6 + 16, i3, str2, i5);
        }
    }

    private static int pD(j jVar, String str, int[] iArr, int i, String str2) {
        try {
            jVar.lo((long) i);
            int i2;
            int i3;
            if (str2.equals("gsm")) {
                i2 = iArr[0];
                i3 = iArr[1];
                int lq = jVar.lq();
                int lr = jVar.lr();
                return i2 >= lq ? i2 <= lq ? i3 >= lr ? i3 <= lr ? 0 : 1 : -1 : 1 : -1;
            } else if (str2.equals("cdma")) {
                r2 = new int[]{iArr[0], iArr[1], iArr[2]};
                int[] iArr2 = new int[3];
                for (i2 = 0; i2 < 3; i2++) {
                    iArr2[i2] = jVar.lq();
                    if (r2[i2] < iArr2[i2]) {
                        return -1;
                    }
                    if (r2[i2] > iArr2[i2]) {
                        return 1;
                    }
                }
                return 0;
            } else {
                if (str2.equals("wifi")) {
                    byte[] wm = bq.wm(str);
                    int[] iArr3 = new int[6];
                    i3 = 0;
                    while (i3 < 6) {
                        iArr3[i3] = wm[i3] >= (byte) 0 ? wm[i3] : wm[i3] + 256;
                        i3++;
                    }
                    r2 = new int[6];
                    for (i2 = 0; i2 < 6; i2++) {
                        r2[i2] = jVar.ls();
                        if (iArr3[i2] < r2[i2]) {
                            return -1;
                        }
                        if (iArr3[i2] > r2[i2]) {
                            return 1;
                        }
                    }
                    return 0;
                }
                return Integer.MAX_VALUE;
            }
        } catch (Exception e) {
        }
    }

    private static void pE(String str) {
        RandomAccessFile randomAccessFile;
        Throwable th;
        RandomAccessFile randomAccessFile2 = null;
        if (!nU.containsKey(str) || TextUtils.isEmpty((CharSequence) nU.get(str))) {
            File file = new File(str);
            if (file.exists() && file.isFile()) {
                try {
                    randomAccessFile = new RandomAccessFile(file, "r");
                    try {
                        randomAccessFile.seek(8);
                        int readUnsignedShort = randomAccessFile.readUnsignedShort();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < readUnsignedShort; i++) {
                            int readInt = randomAccessFile.readInt();
                            if (stringBuilder.indexOf("," + readInt) == -1) {
                                stringBuilder.append(",").append(readInt);
                            }
                            if (i == readUnsignedShort - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        nU.put(str, stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close();
                            } catch (Exception e) {
                            }
                        }
                    } catch (FileNotFoundException e2) {
                    } catch (Exception e3) {
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        randomAccessFile2 = randomAccessFile;
                        th = th3;
                    }
                } catch (FileNotFoundException e4) {
                    randomAccessFile = null;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        } catch (Exception e5) {
                        }
                    }
                } catch (Exception e6) {
                    randomAccessFile = null;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        } catch (Exception e7) {
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (randomAccessFile2 != null) {
                        try {
                            randomAccessFile2.close();
                        } catch (Exception e8) {
                        }
                    }
                    throw th;
                }
            }
        }
    }

    public static ArrayList pF(String str, boolean z) {
        ArrayList arrayList = null;
        if (nU.isEmpty()) {
            return null;
        }
        int pt = pt(str);
        String[] split = str.split("#");
        switch (pt) {
            case 1:
            case 2:
                ArrayList arrayList2 = null;
                for (String str2 : nU.keySet()) {
                    if (((String) nU.get(str2)).contains("," + split[3] + ",")) {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                        }
                        arrayList2.add(str2);
                        if (z) {
                            return arrayList2;
                        }
                        arrayList = arrayList2;
                    } else {
                        arrayList = arrayList2;
                    }
                    arrayList2 = arrayList;
                }
                arrayList = arrayList2;
                break;
        }
        return arrayList;
    }

    public static boolean pG() {
        return !nU.isEmpty();
    }

    public static boolean pH(String str, String str2, int i, int i2) {
        boolean z = true;
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        if (i2 == 0) {
            String pI = pI(str, str2, i);
            if (pI != null) {
                File file = new File(pI);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                if (nU.containsKey(pI)) {
                    nU.remove(pI);
                }
                if (nS.containsKey(pI)) {
                    nS.remove(pI);
                }
            }
            return true;
        } else if (i2 != 1 && i2 != 2) {
            return false;
        } else {
            String[] pr = pr(0.0d, 0.0d, str2);
            boolean z2 = i2 != 1 ? i2 != 2 ? false : true : true;
            if (i == 1) {
                z = false;
            } else if (i != 2) {
                return false;
            }
            Hashtable hashtable = nU;
            Hashtable hashtable2 = nS;
            for (z = 
/*
Method generation error in method: com.loc.az.pH(java.lang.String, java.lang.String, int, int):boolean
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r0_2 'z' boolean) = (r0_0 'z' boolean), (r0_1 'z' boolean) binds: {(r0_0 'z' boolean)=B:9:0x001c, (r0_1 'z' boolean)=B:30:0x0060} in method: com.loc.az.pH(java.lang.String, java.lang.String, int, int):boolean
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:128)
	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:146)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:124)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 26 more

*/

            private static String pI(String str, String str2, int i) {
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    return null;
                }
                StringBuilder pL = pL();
                int indexOf;
                switch (i) {
                    case 1:
                        pL.append(pu(pt(str), str)).append(File.separator);
                        if (str2.startsWith("-")) {
                            pL.append(str2.substring(0, 4));
                        } else {
                            pL.append(str2.substring(0, 3));
                        }
                        pL.append(",");
                        indexOf = str2.indexOf(",") + 1;
                        if (str2.substring(indexOf, indexOf + 1).startsWith("-")) {
                            pL.append(str2.substring(indexOf, indexOf + 4));
                        } else {
                            pL.append(str2.substring(indexOf, indexOf + 3));
                        }
                        pL.append(File.separator).append(str2);
                        break;
                    case 2:
                        pL.append("wifi").append(File.separator);
                        pL.append(str2.substring(0, 3)).append(",");
                        indexOf = str2.indexOf(",") + 1;
                        pL.append(str2.substring(indexOf, indexOf + 3));
                        pL.append(File.separator).append(str2);
                        break;
                    default:
                        return null;
                }
                return pL.toString();
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            private static boolean pJ(java.lang.String r10, java.lang.String r11, int r12, java.lang.String[] r13) {
                /*
                r4 = 0;
                r7 = 3;
                r9 = 1;
                r2 = 0;
                r8 = 0;
                r0 = android.text.TextUtils.isEmpty(r10);
                if (r0 == 0) goto L_0x000d;
            L_0x000c:
                return r8;
            L_0x000d:
                r0 = android.text.TextUtils.isEmpty(r11);
                if (r0 != 0) goto L_0x000c;
            L_0x0013:
                if (r13 != 0) goto L_0x0016;
            L_0x0015:
                return r8;
            L_0x0016:
                r0 = r13.length;
                r1 = 2;
                if (r0 != r1) goto L_0x0015;
            L_0x001a:
                r1 = pL();
                switch(r12) {
                    case 1: goto L_0x0022;
                    case 2: goto L_0x00f1;
                    default: goto L_0x0021;
                };
            L_0x0021:
                return r8;
            L_0x0022:
                r0 = pt(r10);
                r0 = pu(r0, r10);
                r3 = r1.append(r0);
                r6 = java.io.File.separator;
                r3.append(r6);
                r3 = "-";
                r3 = r11.startsWith(r3);
                if (r3 != 0) goto L_0x00dc;
            L_0x003c:
                r3 = r11.substring(r8, r7);
                r1.append(r3);
            L_0x0043:
                r3 = ",";
                r1.append(r3);
                r3 = ",";
                r3 = r11.indexOf(r3);
                r3 = r3 + 1;
                r6 = r3 + 1;
                r6 = r11.substring(r3, r6);
                r7 = "-";
                r6 = r6.startsWith(r7);
                if (r6 != 0) goto L_0x00e6;
            L_0x0061:
                r6 = r3 + 3;
                r3 = r11.substring(r3, r6);
                r1.append(r3);
            L_0x006a:
                r3 = java.io.File.separator;
                r3 = r1.append(r3);
                r3.append(r11);
            L_0x0073:
                r3 = r1.toString();
                r13[r9] = r3;
                r3 = r1.length();
                r1.delete(r8, r3);
                r3 = new java.io.File;
                r1 = r13[r9];
                r3.<init>(r1);
                r1 = r3.exists();
                if (r1 != 0) goto L_0x0128;
            L_0x008d:
                r2 = r4;
            L_0x008e:
                r1 = new org.json.JSONObject;
                r1.<init>();
                r4 = "v";
                r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
                r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x016b }
                r1.put(r4, r5);	 Catch:{ Exception -> 0x016b }
                r4 = "geohash";
                r1.put(r4, r11);	 Catch:{ Exception -> 0x016b }
                r4 = "t";
                r2 = java.lang.String.valueOf(r2);	 Catch:{ Exception -> 0x016b }
                r1.put(r4, r2);	 Catch:{ Exception -> 0x016b }
                r2 = "type";
                r1.put(r2, r0);	 Catch:{ Exception -> 0x016b }
                r0 = "imei";
                r2 = com.loc.H.lE;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
                r0 = "imsi";
                r2 = com.loc.H.lF;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
                r0 = "src";
                r2 = com.loc.H.lH;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
                r0 = "license";
                r2 = com.loc.H.lI;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
            L_0x00d5:
                r0 = r1.toString();
                r13[r8] = r0;
                return r9;
            L_0x00dc:
                r3 = 4;
                r3 = r11.substring(r8, r3);
                r1.append(r3);
                goto L_0x0043;
            L_0x00e6:
                r6 = r3 + 4;
                r3 = r11.substring(r3, r6);
                r1.append(r3);
                goto L_0x006a;
            L_0x00f1:
                r0 = "wifi";
                r3 = r1.append(r0);
                r6 = java.io.File.separator;
                r3.append(r6);
                r3 = r11.substring(r8, r7);
                r3 = r1.append(r3);
                r6 = ",";
                r3.append(r6);
                r3 = ",";
                r3 = r11.indexOf(r3);
                r3 = r3 + 1;
                r6 = r3 + 3;
                r3 = r11.substring(r3, r6);
                r1.append(r3);
                r3 = java.io.File.separator;
                r3 = r1.append(r3);
                r3.append(r11);
                goto L_0x0073;
            L_0x0128:
                r1 = r3.isFile();
                if (r1 == 0) goto L_0x008d;
            L_0x012e:
                r1 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x0149, Exception -> 0x0158, all -> 0x0161 }
                r6 = "r";
                r1.<init>(r3, r6);	 Catch:{ FileNotFoundException -> 0x0149, Exception -> 0x0158, all -> 0x0161 }
                r2 = 0;
                r1.seek(r2);	 Catch:{ FileNotFoundException -> 0x0174, Exception -> 0x0171, all -> 0x016e }
                r2 = r1.readLong();	 Catch:{ FileNotFoundException -> 0x0174, Exception -> 0x0171, all -> 0x016e }
                if (r1 == 0) goto L_0x008e;
            L_0x0141:
                r1.close();	 Catch:{ Exception -> 0x0146 }
                goto L_0x008e;
            L_0x0146:
                r1 = move-exception;
                goto L_0x008e;
            L_0x0149:
                r1 = move-exception;
                r1 = r2;
            L_0x014b:
                if (r1 != 0) goto L_0x0150;
            L_0x014d:
                r2 = r4;
                goto L_0x008e;
            L_0x0150:
                r1.close();	 Catch:{ Exception -> 0x0154 }
                goto L_0x014d;
            L_0x0154:
                r1 = move-exception;
            L_0x0155:
                r2 = r4;
                goto L_0x008e;
            L_0x0158:
                r1 = move-exception;
            L_0x0159:
                if (r2 == 0) goto L_0x014d;
            L_0x015b:
                r2.close();	 Catch:{ Exception -> 0x015f }
                goto L_0x014d;
            L_0x015f:
                r1 = move-exception;
                goto L_0x0155;
            L_0x0161:
                r0 = move-exception;
            L_0x0162:
                if (r2 != 0) goto L_0x0165;
            L_0x0164:
                throw r0;
            L_0x0165:
                r2.close();	 Catch:{ Exception -> 0x0169 }
                goto L_0x0164;
            L_0x0169:
                r1 = move-exception;
                goto L_0x0164;
            L_0x016b:
                r0 = move-exception;
                goto L_0x00d5;
            L_0x016e:
                r0 = move-exception;
                r2 = r1;
                goto L_0x0162;
            L_0x0171:
                r2 = move-exception;
                r2 = r1;
                goto L_0x0159;
            L_0x0174:
                r2 = move-exception;
                goto L_0x014b;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.loc.az.pJ(java.lang.String, java.lang.String, int, java.lang.String[]):boolean");
            }

            private static boolean pK(Context context, String str, int i, boolean z, boolean z2) {
                boolean z3 = z ? i >= 25 : i != 1;
                if (!str.contains("cgi") && z3) {
                    return false;
                }
                if ((!str.contains("wifi") && !z3) || nW[1] > 2000) {
                    return false;
                }
                NetworkInfo vY = bq.vY(context);
                if (ba.tp(vY) == -1) {
                    return false;
                }
                if (vY.getType() != 1 && z2) {
                    return false;
                }
                if (!(vY.getType() == 1 || z2 || nV != null)) {
                    nV = (TelephonyManager) bq.vQ(context, "phone");
                }
                return true;
            }

            private static StringBuilder pL() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(bq.vU());
                stringBuilder.append("offline").append(File.separator);
                stringBuilder.append(bq.we()).append(File.separator).append("s").append(File.separator);
                return stringBuilder;
            }

            static int pM(int i) {
                int i2 = 0;
                int[] iArr = new int[32];
                int i3 = 0;
                while (i2 < 4) {
                    iArr[i2] = (i >> (i2 * 8)) & 255;
                    iArr[i2] = ((iArr[i2] << 4) & 240) + ((iArr[i2] >> 4) & 15);
                    i3 += (iArr[i2] & 255) << ((3 - i2) * 8);
                    i2++;
                }
                return d + i3;
            }

            public static String[] pr(double d, double d2, String str) {
                String ov;
                int i;
                String[] strArr = new String[50];
                if (TextUtils.isEmpty(str)) {
                    ov = al.ov(d, d2);
                    str = al.ov(d, d2);
                } else {
                    ov = str;
                }
                strArr[0] = ov;
                strArr[25] = str;
                String[] ox = al.ox(ov);
                for (i = 1; i < 25; i++) {
                    strArr[i] = ox[i - 1];
                }
                ox = al.ox(str);
                for (i = 26; i < 50; i++) {
                    strArr[i] = ox[i - 26];
                }
                return strArr;
            }

            public static AmapLoc ps(double[] dArr, String str, String str2, String str3, int i, Context context, int[] iArr) {
                if (TextUtils.isEmpty(str2)) {
                    return null;
                }
                if (str2.contains("gps")) {
                    return null;
                }
                int i2;
                int i3;
                int i4;
                int i5;
                AmapLoc px;
                String stringBuilder;
                int indexOf;
                String str4;
                bn bnVar;
                j jVar;
                Object obj;
                File file;
                j jVar2;
                V v;
                int i6;
                long lp;
                long lt;
                int i7;
                int pt = pt(str2);
                String pu = pu(pt, str2);
                Hashtable hashtable = new Hashtable();
                pv(pt, str2, str3, hashtable);
                Hashtable hashtable2 = new Hashtable();
                pw(str3, hashtable2);
                StringBuilder pL = pL();
                String[] pr = dArr != null ? pr(dArr[0], dArr[1], str) : pr(0.0d, 0.0d, str);
                int length = pr.length / 2;
                if (1 <= i) {
                    if (i > 3) {
                    }
                    H.lR = hashtable.size();
                    i2 = 0;
                    i3 = 0;
                    i4 = 0;
                    while (i4 < pr.length) {
                        if (i4 < length) {
                            if (i3 <= 0 && !hashtable.isEmpty()) {
                            }
                            if (i == 1) {
                                if (i4 != 0) {
                                    if (i4 != 0 && i4 != 25) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                        i4++;
                                        i5 = i3;
                                        i3 = i2;
                                        i2 = i5;
                                    } else if (i4 > 25) {
                                    }
                                }
                                pL.delete(0, pL.length());
                                px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                                if (bq.vH(px)) {
                                }
                            } else if (i == 2) {
                                if (i4 > 8 && i4 < 25) {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                    i4++;
                                    i5 = i3;
                                    i3 = i2;
                                    i2 = i5;
                                } else if (i4 > 33) {
                                    pL.delete(0, pL.length());
                                    px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                                    return bq.vH(px) ? null : px;
                                }
                            }
                            stringBuilder = pL.toString();
                            if (i4 < length) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else {
                                switch (pt) {
                                    case 1:
                                        iArr[0] = 0;
                                        iArr[1] = 0;
                                        break;
                                    case 2:
                                        break;
                                    default:
                                        iArr[0] = 0;
                                        iArr[1] = 0;
                                        break;
                                }
                                stringBuilder = stringBuilder + pu + File.separator;
                                stringBuilder = pr[i4].startsWith("-") ? stringBuilder + pr[i4].substring(0, 3) + "," : stringBuilder + pr[i4].substring(0, 4) + ",";
                                indexOf = pr[i4].indexOf(",") + 1;
                                stringBuilder = pr[i4].substring(indexOf, indexOf + 1).startsWith("-") ? stringBuilder + pr[i4].substring(indexOf, indexOf + 3) : stringBuilder + pr[i4].substring(indexOf, indexOf + 4);
                                str4 = (stringBuilder + File.separator) + pr[i4];
                                if (str4.equals(nR)) {
                                    bnVar = nT;
                                    jVar = (j) bnVar.xu(str4);
                                    obj = null;
                                    file = new File(str4);
                                    if (jVar == null) {
                                        obj = 1;
                                        jVar2 = jVar;
                                    } else if (!file.getParentFile().exists()) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else if (file.isDirectory()) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else if (file.exists()) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else {
                                        v = new V();
                                        try {
                                            jVar = new j(file, v);
                                        } catch (FileNotFoundException e) {
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } catch (Throwable th) {
                                            jVar = null;
                                        }
                                        jVar2 = jVar;
                                    }
                                    i6 = 0;
                                    if (jVar2 == null) {
                                        try {
                                            jVar2.lo(0);
                                            lp = jVar2.lp();
                                            if (i4 < length) {
                                                i6 = jVar2.lq();
                                            }
                                            try {
                                                lt = jVar2.lt();
                                                i7 = 8;
                                                if (i4 < length) {
                                                    i7 = ((i6 * 4) + 2) + 8;
                                                }
                                                if ((lp + 7776000000L < bq.vL() ? 1 : null) != null) {
                                                    if (jVar2 != null) {
                                                        if (obj != null) {
                                                            try {
                                                                jVar2.ln();
                                                            } catch (Exception e2) {
                                                            }
                                                        } else {
                                                            bnVar.xv(str4);
                                                        }
                                                    }
                                                    file.delete();
                                                    nS.remove(pr[i4]);
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else {
                                                    if ((lt > 8 ? 1 : null) == null || (lt - ((long) i7)) % 16 != 0) {
                                                        if (jVar2 != null) {
                                                            try {
                                                                jVar2.ln();
                                                            } catch (Exception e3) {
                                                            }
                                                        }
                                                        file.delete();
                                                        nS.remove(pr[i4]);
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    } else {
                                                        int i8;
                                                        double[] pB;
                                                        Entry entry;
                                                        Object obj2 = (i4 < length && !hashtable.isEmpty() && i3 < H.lR) ? 1 : null;
                                                        Object obj3 = (i4 >= length && !hashtable2.isEmpty() && i2 < 15) ? 1 : null;
                                                        if (obj2 == null) {
                                                            i8 = i3;
                                                        } else {
                                                            try {
                                                                i8 = i3;
                                                                for (Entry entry2 : hashtable.entrySet()) {
                                                                    try {
                                                                        pB = pB(i7, jVar2, ((String) entry2.getKey()).toString(), 0);
                                                                        if (pB == null) {
                                                                            i6 = i8;
                                                                        } else {
                                                                            i8++;
                                                                            hashtable.put(((String) entry2.getKey()).toString(), ((pB[0] + "|" + pB[1]) + "|" + pB[2] + "|") + ((String) hashtable.get(((String) entry2.getKey()).toString())));
                                                                            if (i8 < H.lR) {
                                                                                i6 = i8;
                                                                            }
                                                                        }
                                                                        i8 = i6;
                                                                    } catch (Throwable th2) {
                                                                    }
                                                                }
                                                            } catch (Throwable th3) {
                                                                i8 = i3;
                                                            }
                                                        }
                                                        if (obj3 != null) {
                                                            Iterator it = hashtable2.entrySet().iterator();
                                                            while (it != null && it.hasNext()) {
                                                                entry2 = (Entry) it.next();
                                                                pB = pB(i7, jVar2, ((String) entry2.getKey()).toString(), 1);
                                                                if (pB == null) {
                                                                    i6 = i2;
                                                                } else {
                                                                    i2++;
                                                                    hashtable2.put(((String) entry2.getKey()).toString(), ((pB[0] + "|" + pB[1]) + "|" + pB[2] + "|") + ((String) hashtable2.get(((String) entry2.getKey()).toString())));
                                                                    if (i2 < 15) {
                                                                        i6 = i2;
                                                                    }
                                                                }
                                                                i2 = i6;
                                                            }
                                                        }
                                                        i3 = i8;
                                                        if (jVar2 == null) {
                                                            i5 = i2;
                                                            i2 = i3;
                                                            i3 = i5;
                                                        } else if (!jVar2.lm()) {
                                                            try {
                                                                jVar2.ln();
                                                            } catch (Exception e4) {
                                                            }
                                                            i5 = i2;
                                                            i2 = i3;
                                                            i3 = i5;
                                                        } else if (obj != null) {
                                                            i5 = i2;
                                                            i2 = i3;
                                                            i3 = i5;
                                                        } else {
                                                            bnVar.xs(str4, jVar2);
                                                            i5 = i2;
                                                            i2 = i3;
                                                            i3 = i5;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e5) {
                                                if (obj != null) {
                                                    bnVar.xv(str4);
                                                }
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            }
                                        } catch (Exception e6) {
                                            if (obj != null) {
                                                bnVar.xv(str4);
                                            }
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        }
                                    } else {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    }
                                } else {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                }
                            }
                            i4++;
                            i5 = i3;
                            i3 = i2;
                            i2 = i5;
                        }
                        if (i4 >= length) {
                            if (i2 <= 0 && !hashtable2.isEmpty()) {
                            }
                            if (i == 1) {
                                if (i4 != 0) {
                                    if (i4 != 0) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                        i4++;
                                        i5 = i3;
                                        i3 = i2;
                                        i2 = i5;
                                    }
                                    if (i4 > 25) {
                                    }
                                }
                                pL.delete(0, pL.length());
                                px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                                if (bq.vH(px)) {
                                }
                            } else if (i == 2) {
                                if (i4 > 8) {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                    i4++;
                                    i5 = i3;
                                    i3 = i2;
                                    i2 = i5;
                                }
                                if (i4 > 33) {
                                    pL.delete(0, pL.length());
                                    px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                                    if (bq.vH(px)) {
                                    }
                                }
                            }
                            stringBuilder = pL.toString();
                            if (i4 < length) {
                                switch (pt) {
                                    case 1:
                                        iArr[0] = 0;
                                        iArr[1] = 0;
                                        break;
                                    case 2:
                                        break;
                                    default:
                                        iArr[0] = 0;
                                        iArr[1] = 0;
                                        break;
                                }
                                stringBuilder = stringBuilder + pu + File.separator;
                                if (pr[i4].startsWith("-")) {
                                }
                                indexOf = pr[i4].indexOf(",") + 1;
                                if (pr[i4].substring(indexOf, indexOf + 1).startsWith("-")) {
                                }
                                str4 = (stringBuilder + File.separator) + pr[i4];
                                if (str4.equals(nR)) {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                } else {
                                    bnVar = nT;
                                    jVar = (j) bnVar.xu(str4);
                                    obj = null;
                                    file = new File(str4);
                                    if (jVar == null) {
                                        obj = 1;
                                        jVar2 = jVar;
                                    } else if (!file.getParentFile().exists()) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else if (file.isDirectory()) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else if (file.exists()) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else {
                                        v = new V();
                                        jVar = new j(file, v);
                                        jVar2 = jVar;
                                    }
                                    i6 = 0;
                                    if (jVar2 == null) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else {
                                        jVar2.lo(0);
                                        lp = jVar2.lp();
                                        if (i4 < length) {
                                            i6 = jVar2.lq();
                                        }
                                        lt = jVar2.lt();
                                        i7 = 8;
                                        if (i4 < length) {
                                            i7 = ((i6 * 4) + 2) + 8;
                                        }
                                        if (lp + 7776000000L < bq.vL()) {
                                        }
                                        if ((lp + 7776000000L < bq.vL() ? 1 : null) != null) {
                                            if (lt > 8) {
                                            }
                                            if ((lt > 8 ? 1 : null) == null) {
                                            }
                                            if (jVar2 != null) {
                                                jVar2.ln();
                                            }
                                            file.delete();
                                            nS.remove(pr[i4]);
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else {
                                            if (jVar2 != null) {
                                                if (obj != null) {
                                                    bnVar.xv(str4);
                                                } else {
                                                    jVar2.ln();
                                                }
                                            }
                                            file.delete();
                                            nS.remove(pr[i4]);
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        }
                                    }
                                }
                            } else {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            }
                            i4++;
                            i5 = i3;
                            i3 = i2;
                            i2 = i5;
                        }
                        if (i4 >= length && i3 <= 0) {
                        }
                        if (i == 1) {
                            if (i4 != 0) {
                                if (i4 != 0) {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                    i4++;
                                    i5 = i3;
                                    i3 = i2;
                                    i2 = i5;
                                }
                                if (i4 > 25) {
                                }
                            }
                            pL.delete(0, pL.length());
                            px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                            if (bq.vH(px)) {
                            }
                        } else if (i == 2) {
                            if (i4 > 8) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                                i4++;
                                i5 = i3;
                                i3 = i2;
                                i2 = i5;
                            }
                            if (i4 > 33) {
                                pL.delete(0, pL.length());
                                px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                                if (bq.vH(px)) {
                                }
                            }
                        }
                        stringBuilder = pL.toString();
                        if (i4 < length) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        } else {
                            switch (pt) {
                                case 1:
                                    iArr[0] = 0;
                                    iArr[1] = 0;
                                    break;
                                case 2:
                                    break;
                                default:
                                    iArr[0] = 0;
                                    iArr[1] = 0;
                                    break;
                            }
                            stringBuilder = stringBuilder + pu + File.separator;
                            if (pr[i4].startsWith("-")) {
                            }
                            indexOf = pr[i4].indexOf(",") + 1;
                            if (pr[i4].substring(indexOf, indexOf + 1).startsWith("-")) {
                            }
                            str4 = (stringBuilder + File.separator) + pr[i4];
                            if (str4.equals(nR)) {
                                bnVar = nT;
                                jVar = (j) bnVar.xu(str4);
                                obj = null;
                                file = new File(str4);
                                if (jVar == null) {
                                    obj = 1;
                                    jVar2 = jVar;
                                } else if (!file.getParentFile().exists()) {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                } else if (file.isDirectory()) {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                } else if (file.exists()) {
                                    v = new V();
                                    jVar = new j(file, v);
                                    jVar2 = jVar;
                                } else {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                }
                                i6 = 0;
                                if (jVar2 == null) {
                                    jVar2.lo(0);
                                    lp = jVar2.lp();
                                    if (i4 < length) {
                                        i6 = jVar2.lq();
                                    }
                                    lt = jVar2.lt();
                                    i7 = 8;
                                    if (i4 < length) {
                                        i7 = ((i6 * 4) + 2) + 8;
                                    }
                                    if (lp + 7776000000L < bq.vL()) {
                                    }
                                    if ((lp + 7776000000L < bq.vL() ? 1 : null) != null) {
                                        if (jVar2 != null) {
                                            if (obj != null) {
                                                jVar2.ln();
                                            } else {
                                                bnVar.xv(str4);
                                            }
                                        }
                                        file.delete();
                                        nS.remove(pr[i4]);
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else {
                                        if (lt > 8) {
                                        }
                                        if ((lt > 8 ? 1 : null) == null) {
                                        }
                                        if (jVar2 != null) {
                                            jVar2.ln();
                                        }
                                        file.delete();
                                        nS.remove(pr[i4]);
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    }
                                } else {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                }
                            } else {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            }
                        }
                        i4++;
                        i5 = i3;
                        i3 = i2;
                        i2 = i5;
                    }
                    pL.delete(0, pL.length());
                    px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                    if (bq.vH(px)) {
                    }
                }
                i = 1;
                H.lR = hashtable.size();
                i2 = 0;
                i3 = 0;
                i4 = 0;
                while (i4 < pr.length) {
                    if (i4 < length) {
                    }
                    if (i4 >= length) {
                    }
                    if (i == 1) {
                        if (i4 != 0) {
                            if (i4 != 0) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                                i4++;
                                i5 = i3;
                                i3 = i2;
                                i2 = i5;
                            }
                            if (i4 > 25) {
                            }
                        }
                        pL.delete(0, pL.length());
                        px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                        if (bq.vH(px)) {
                        }
                    } else if (i == 2) {
                        if (i4 > 8) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                            i4++;
                            i5 = i3;
                            i3 = i2;
                            i2 = i5;
                        }
                        if (i4 > 33) {
                            pL.delete(0, pL.length());
                            px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                            if (bq.vH(px)) {
                            }
                        }
                    }
                    stringBuilder = pL.toString();
                    if (i4 < length) {
                        switch (pt) {
                            case 1:
                                iArr[0] = 0;
                                iArr[1] = 0;
                                break;
                            case 2:
                                break;
                            default:
                                iArr[0] = 0;
                                iArr[1] = 0;
                                break;
                        }
                        stringBuilder = stringBuilder + pu + File.separator;
                        if (pr[i4].startsWith("-")) {
                        }
                        indexOf = pr[i4].indexOf(",") + 1;
                        if (pr[i4].substring(indexOf, indexOf + 1).startsWith("-")) {
                        }
                        str4 = (stringBuilder + File.separator) + pr[i4];
                        if (str4.equals(nR)) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        } else {
                            bnVar = nT;
                            jVar = (j) bnVar.xu(str4);
                            obj = null;
                            file = new File(str4);
                            if (jVar == null) {
                                obj = 1;
                                jVar2 = jVar;
                            } else if (!file.getParentFile().exists()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else if (file.isDirectory()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else if (file.exists()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else {
                                v = new V();
                                jVar = new j(file, v);
                                jVar2 = jVar;
                            }
                            i6 = 0;
                            if (jVar2 == null) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else {
                                jVar2.lo(0);
                                lp = jVar2.lp();
                                if (i4 < length) {
                                    i6 = jVar2.lq();
                                }
                                lt = jVar2.lt();
                                i7 = 8;
                                if (i4 < length) {
                                    i7 = ((i6 * 4) + 2) + 8;
                                }
                                if (lp + 7776000000L < bq.vL()) {
                                }
                                if ((lp + 7776000000L < bq.vL() ? 1 : null) != null) {
                                    if (lt > 8) {
                                    }
                                    if ((lt > 8 ? 1 : null) == null) {
                                    }
                                    if (jVar2 != null) {
                                        jVar2.ln();
                                    }
                                    file.delete();
                                    nS.remove(pr[i4]);
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                } else {
                                    if (jVar2 != null) {
                                        if (obj != null) {
                                            bnVar.xv(str4);
                                        } else {
                                            jVar2.ln();
                                        }
                                    }
                                    file.delete();
                                    nS.remove(pr[i4]);
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                }
                            }
                        }
                    } else {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    }
                    i4++;
                    i5 = i3;
                    i3 = i2;
                    i2 = i5;
                }
                pL.delete(0, pL.length());
                px = px(hashtable, hashtable2, iArr[0], iArr[1]);
                if (bq.vH(px)) {
                }
            }

            private static int pt(String str) {
                if (TextUtils.isEmpty(str) || !str.contains("cgi")) {
                    return 9;
                }
                String[] split = str.split("#");
                return split.length != 7 ? split.length == 8 ? 2 : 9 : 1;
            }

            private static String pu(int i, String str) {
                String[] split = str.split("#");
                switch (i) {
                    case 1:
                        return split[1] + "_" + split[2];
                    case 2:
                        return split[3];
                    default:
                        return null;
                }
            }

            private static void pv(int i, String str, String str2, Hashtable hashtable) {
                String[] split = str.split("#");
                switch (i) {
                    case 1:
                        hashtable.put(split[3] + "|" + split[4], "access");
                        if (!TextUtils.isEmpty(str2) && str2.split("#").length <= 0) {
                            return;
                        }
                        return;
                    case 2:
                        hashtable.put(split[3] + "|" + split[4] + "|" + split[5], "access");
                        return;
                    default:
                        return;
                }
            }

            private static void pw(String str, Hashtable hashtable) {
                if (!TextUtils.isEmpty(str)) {
                    String[] strArr = new String[2];
                    for (String str2 : str.split("#")) {
                        if (str2.contains(",")) {
                            String[] split = str2.split(",");
                            hashtable.put(split[0], split[1]);
                        }
                    }
                }
            }

            private static AmapLoc px(Hashtable hashtable, Hashtable hashtable2, int i, int i2) {
                ArrayList va;
                AmapLoc amapLoc;
                bg bgVar = new bg();
                try {
                    String str;
                    if (!hashtable.isEmpty()) {
                        for (Entry value : hashtable.entrySet()) {
                            str = (String) value.getValue();
                            int i3 = !str.contains("access") ? 0 : 1;
                            if (str.contains("|")) {
                                try {
                                    bgVar.uZ(i3 == 0 ? 2 : 1, str.substring(0, str.lastIndexOf("|")));
                                } catch (Throwable th) {
                                }
                            }
                        }
                    }
                    if (!hashtable2.isEmpty()) {
                        for (Entry value2 : hashtable2.entrySet()) {
                            str = (String) value2.getValue();
                            if (str.contains("|")) {
                                try {
                                    bgVar.uZ(0, str.substring(0, str.lastIndexOf("|")));
                                } catch (Throwable th2) {
                                }
                            }
                        }
                    }
                } catch (Throwable th3) {
                }
                try {
                    va = bgVar.va((double) i2, (double) i);
                } catch (Throwable th4) {
                    va = null;
                }
                if (va == null || va.isEmpty()) {
                    amapLoc = null;
                } else {
                    n nVar = (n) va.get(0);
                    AmapLoc amapLoc2 = new AmapLoc();
                    amapLoc2.yF("network");
                    amapLoc2.yK(nVar.kN);
                    amapLoc2.yH(nVar.kO);
                    amapLoc2.yO((float) nVar.c);
                    amapLoc2.zh(nVar.kP);
                    amapLoc2.zA("0");
                    amapLoc2.yV(bq.vL());
                    va.clear();
                    amapLoc = amapLoc2;
                }
                if (!bq.vH(amapLoc)) {
                    return null;
                }
                amapLoc.yX("file");
                return amapLoc;
            }

            public static boolean py(Context context, String str, String str2, int i, int i2, boolean z, boolean z2) {
                if (!pK(context, str, i, false, z)) {
                    return false;
                }
                if (i2 == 0) {
                    return pz(context, str, str2, i, z2);
                }
                int i3 = i2 != 1 ? 24 : 8;
                String[] pr = pr(0.0d, 0.0d, str2);
                for (int i4 = 1; i4 < i3; i4++) {
                    pz(context, str, pr[i4], i, z2);
                }
                return true;
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            private static boolean pz(android.content.Context r11, java.lang.String r12, java.lang.String r13, int r14, boolean r15) {
                /*
                r0 = 2;
                r6 = new java.lang.String[r0];
                r0 = pJ(r12, r13, r14, r6);
                if (r0 == 0) goto L_0x0065;
            L_0x0009:
                r0 = nS;
                r1 = 1;
                r1 = r6[r1];
                r0 = r0.containsKey(r1);
                if (r0 != 0) goto L_0x0067;
            L_0x0014:
                r1 = 0;
                r4 = 0;
                r3 = 0;
                r0 = 0;
                com.loc.bq.vM();	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r2 = new java.util.HashMap;	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r2.<init>();	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r5 = "v";
                r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
                r7 = java.lang.String.valueOf(r7);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r2.put(r5, r7);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r5 = com.loc.ba.tm(r11);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r7 = "https://offline.aps.amap.com/LoadOfflineData/getData";
                r8 = 0;
                r8 = r6[r8];	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r9 = "UTF-8";
                r8 = r8.getBytes(r9);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                r2 = r5.tr(r11, r7, r2, r8);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
                if (r2 == 0) goto L_0x0093;
            L_0x0043:
                r0 = r2.getResponseCode();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                if (r0 == r5) goto L_0x009d;
            L_0x004b:
                r5 = 404; // 0x194 float:5.66E-43 double:1.996E-321;
                if (r0 == r5) goto L_0x0304;
            L_0x004f:
                r0 = r3;
                r3 = r1;
                r1 = r4;
            L_0x0052:
                com.loc.bq.vM();	 Catch:{ UnknownHostException -> 0x00d1, SocketException -> 0x02e7, SocketTimeoutException -> 0x02d2, EOFException -> 0x02ba, Exception -> 0x02a7, all -> 0x0296 }
                if (r0 != 0) goto L_0x01eb;
            L_0x0057:
                if (r1 != 0) goto L_0x01f3;
            L_0x0059:
                if (r2 != 0) goto L_0x01fb;
            L_0x005b:
                r1 = r3;
            L_0x005c:
                r0 = nR;
                r0 = android.text.TextUtils.isEmpty(r0);
                if (r0 == 0) goto L_0x0288;
            L_0x0064:
                return r1;
            L_0x0065:
                r0 = 0;
                return r0;
            L_0x0067:
                r0 = nS;
                r1 = 1;
                r1 = r6[r1];
                r0 = r0.get(r1);
                r0 = (java.lang.Long) r0;
                r0 = r0.longValue();
                r2 = com.loc.bq.vL();
                r0 = r2 - r0;
                r2 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
                r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
                if (r0 < 0) goto L_0x0088;
            L_0x0083:
                r0 = 1;
            L_0x0084:
                if (r0 != 0) goto L_0x008a;
            L_0x0086:
                r0 = 0;
                return r0;
            L_0x0088:
                r0 = 0;
                goto L_0x0084;
            L_0x008a:
                r0 = nS;
                r1 = 1;
                r1 = r6[r1];
                r0.remove(r1);
                goto L_0x0014;
            L_0x0093:
                if (r2 != 0) goto L_0x0097;
            L_0x0095:
                r0 = 0;
                return r0;
            L_0x0097:
                r2.disconnect();	 Catch:{ Exception -> 0x009b }
                goto L_0x0095;
            L_0x009b:
                r0 = move-exception;
                goto L_0x0095;
            L_0x009d:
                r5 = 0;
                r0 = r2.getHeaderFields();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0 = r0.entrySet();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r7 = r0.iterator();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
            L_0x00aa:
                r0 = r7.hasNext();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                if (r0 != 0) goto L_0x00e2;
            L_0x00b0:
                r0 = r5;
            L_0x00b1:
                r5 = 260; // 0x104 float:3.64E-43 double:1.285E-321;
                if (r0 == r5) goto L_0x0107;
            L_0x00b5:
                r0 = nS;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r5 = 1;
                r5 = r6[r5];	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r8 = com.loc.bq.vL();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r7 = java.lang.Long.valueOf(r8);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0.put(r5, r7);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0 = r3;
                r3 = r1;
                r1 = r4;
            L_0x00c8:
                if (r15 == 0) goto L_0x0052;
            L_0x00ca:
                r4 = 1;
                r4 = r6[r4];	 Catch:{ UnknownHostException -> 0x00d1, SocketException -> 0x02e7, SocketTimeoutException -> 0x02d2, EOFException -> 0x02ba, Exception -> 0x02a7, all -> 0x0296 }
                pE(r4);	 Catch:{ UnknownHostException -> 0x00d1, SocketException -> 0x02e7, SocketTimeoutException -> 0x02d2, EOFException -> 0x02ba, Exception -> 0x02a7, all -> 0x0296 }
                goto L_0x0052;
            L_0x00d1:
                r4 = move-exception;
                r10 = r2;
                r2 = r1;
                r1 = r0;
                r0 = r10;
            L_0x00d6:
                if (r1 != 0) goto L_0x0203;
            L_0x00d8:
                if (r2 != 0) goto L_0x020b;
            L_0x00da:
                if (r0 == 0) goto L_0x005b;
            L_0x00dc:
                r0.disconnect();	 Catch:{ Exception -> 0x0213 }
            L_0x00df:
                r1 = r3;
                goto L_0x005c;
            L_0x00e2:
                r0 = r7.next();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0 = (java.util.Map.Entry) r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r8 = "code";
                r9 = r0.getKey();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r8 = r8.equals(r9);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                if (r8 == 0) goto L_0x00aa;
            L_0x00f5:
                r0 = r0.getValue();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0 = (java.util.List) r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r5 = 0;
                r0 = r0.get(r5);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0 = (java.lang.String) r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0 = java.lang.Integer.parseInt(r0);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                goto L_0x00b1;
            L_0x0107:
                r0 = 1;
                r0 = r6[r0];	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                nR = r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r0 = 1;
                r5 = r2.getInputStream();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
                r4 = new java.util.zip.GZIPInputStream;	 Catch:{ UnknownHostException -> 0x02fc, SocketException -> 0x02e3, SocketTimeoutException -> 0x02cb, EOFException -> 0x02b0, Exception -> 0x029f, all -> 0x0291 }
                r4.<init>(r5);	 Catch:{ UnknownHostException -> 0x02fc, SocketException -> 0x02e3, SocketTimeoutException -> 0x02cb, EOFException -> 0x02b0, Exception -> 0x029f, all -> 0x0291 }
                r3 = new java.io.File;	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r7 = 1;
                r7 = r6[r7];	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r3.<init>(r7);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r7 = r3.exists();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                if (r7 != 0) goto L_0x012b;
            L_0x0124:
                if (r0 != 0) goto L_0x0133;
            L_0x0126:
                r0 = r1;
            L_0x0127:
                r1 = r5;
                r3 = r0;
                r0 = r4;
                goto L_0x00c8;
            L_0x012b:
                r7 = r3.delete();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                if (r7 != 0) goto L_0x0124;
            L_0x0131:
                r0 = 0;
                goto L_0x0124;
            L_0x0133:
                r0 = r3.getParentFile();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r7 = r0.exists();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                if (r7 == 0) goto L_0x019d;
            L_0x013d:
                r0 = new java.io.FileOutputStream;	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r0.<init>(r3);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r3 = new java.io.BufferedOutputStream;	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r7 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
                r3.<init>(r0, r7);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r0 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
                r0 = new byte[r0];	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
            L_0x014d:
                r7 = 0;
                r8 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
                r7 = r4.read(r0, r7, r8);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r8 = -1;
                if (r7 != r8) goto L_0x01a8;
            L_0x0157:
                r3.flush();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r3.close();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                r0 = 1;
                r1 = nS;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = 1;
                r3 = r6[r3];	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r8 = com.loc.bq.vL();	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r7 = java.lang.Long.valueOf(r8);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r1.put(r3, r7);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r8 = 0;
                r1 = "yyyyMMdd";
                r1 = com.loc.bq.wt(r8, r1);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = nW;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r7 = 0;
                r3 = r3[r7];	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = java.lang.String.valueOf(r3);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = r1.equals(r3);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                if (r3 != 0) goto L_0x01bc;
            L_0x0186:
                r3 = nW;	 Catch:{ Exception -> 0x01cd, UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, all -> 0x0294 }
                r7 = 0;
                r1 = java.lang.Integer.parseInt(r1);	 Catch:{ Exception -> 0x01cd, UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, all -> 0x0294 }
                r3[r7] = r1;	 Catch:{ Exception -> 0x01cd, UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, all -> 0x0294 }
            L_0x018f:
                r1 = nW;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = 1;
                r7 = 1;
                r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                goto L_0x0127;
            L_0x0196:
                r1 = move-exception;
                r1 = r4;
                r3 = r0;
                r0 = r2;
                r2 = r5;
                goto L_0x00d6;
            L_0x019d:
                r0.mkdirs();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                goto L_0x013d;
            L_0x01a1:
                r0 = move-exception;
                r0 = r2;
                r3 = r1;
                r2 = r5;
                r1 = r4;
                goto L_0x00d6;
            L_0x01a8:
                r8 = 0;
                r3.write(r0, r8, r7);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
                goto L_0x014d;
            L_0x01ad:
                r0 = move-exception;
            L_0x01ae:
                if (r4 != 0) goto L_0x0216;
            L_0x01b0:
                if (r5 != 0) goto L_0x021c;
            L_0x01b2:
                if (r2 == 0) goto L_0x005c;
            L_0x01b4:
                r2.disconnect();	 Catch:{ Exception -> 0x01b9 }
                goto L_0x005c;
            L_0x01b9:
                r0 = move-exception;
                goto L_0x005c;
            L_0x01bc:
                r1 = nW;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = 1;
                r7 = nW;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r8 = 1;
                r7 = r7[r8];	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r7 = r7 + 1;
                r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                goto L_0x0127;
            L_0x01ca:
                r1 = move-exception;
                r1 = r0;
                goto L_0x01ae;
            L_0x01cd:
                r1 = move-exception;
                r1 = nW;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = 0;
                r7 = 0;
                r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r1 = nW;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                r3 = 1;
                r7 = 0;
                r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
                goto L_0x018f;
            L_0x01db:
                r1 = move-exception;
                r1 = r0;
            L_0x01dd:
                if (r4 != 0) goto L_0x0222;
            L_0x01df:
                if (r5 != 0) goto L_0x0228;
            L_0x01e1:
                if (r2 == 0) goto L_0x005c;
            L_0x01e3:
                r2.disconnect();	 Catch:{ Exception -> 0x01e8 }
                goto L_0x005c;
            L_0x01e8:
                r0 = move-exception;
                goto L_0x005c;
            L_0x01eb:
                r0.close();	 Catch:{ Exception -> 0x01f0 }
                goto L_0x0057;
            L_0x01f0:
                r0 = move-exception;
                goto L_0x0057;
            L_0x01f3:
                r1.close();	 Catch:{ Exception -> 0x01f8 }
                goto L_0x0059;
            L_0x01f8:
                r0 = move-exception;
                goto L_0x0059;
            L_0x01fb:
                r2.disconnect();	 Catch:{ Exception -> 0x0200 }
                goto L_0x00df;
            L_0x0200:
                r0 = move-exception;
                goto L_0x00df;
            L_0x0203:
                r1.close();	 Catch:{ Exception -> 0x0208 }
                goto L_0x00d8;
            L_0x0208:
                r1 = move-exception;
                goto L_0x00d8;
            L_0x020b:
                r2.close();	 Catch:{ Exception -> 0x0210 }
                goto L_0x00da;
            L_0x0210:
                r1 = move-exception;
                goto L_0x00da;
            L_0x0213:
                r0 = move-exception;
                goto L_0x00df;
            L_0x0216:
                r4.close();	 Catch:{ Exception -> 0x021a }
                goto L_0x01b0;
            L_0x021a:
                r0 = move-exception;
                goto L_0x01b0;
            L_0x021c:
                r5.close();	 Catch:{ Exception -> 0x0220 }
                goto L_0x01b2;
            L_0x0220:
                r0 = move-exception;
                goto L_0x01b2;
            L_0x0222:
                r4.close();	 Catch:{ Exception -> 0x0226 }
                goto L_0x01df;
            L_0x0226:
                r0 = move-exception;
                goto L_0x01df;
            L_0x0228:
                r5.close();	 Catch:{ Exception -> 0x022c }
                goto L_0x01e1;
            L_0x022c:
                r0 = move-exception;
                goto L_0x01e1;
            L_0x022e:
                r2 = move-exception;
                r2 = r0;
                r5 = r4;
                r4 = r3;
            L_0x0232:
                if (r4 != 0) goto L_0x0240;
            L_0x0234:
                if (r5 != 0) goto L_0x0246;
            L_0x0236:
                if (r2 == 0) goto L_0x005c;
            L_0x0238:
                r2.disconnect();	 Catch:{ Exception -> 0x023d }
                goto L_0x005c;
            L_0x023d:
                r0 = move-exception;
                goto L_0x005c;
            L_0x0240:
                r4.close();	 Catch:{ Exception -> 0x0244 }
                goto L_0x0234;
            L_0x0244:
                r0 = move-exception;
                goto L_0x0234;
            L_0x0246:
                r5.close();	 Catch:{ Exception -> 0x024a }
                goto L_0x0236;
            L_0x024a:
                r0 = move-exception;
                goto L_0x0236;
            L_0x024c:
                r2 = move-exception;
                r2 = r0;
                r5 = r4;
                r4 = r3;
            L_0x0250:
                if (r4 != 0) goto L_0x025e;
            L_0x0252:
                if (r5 != 0) goto L_0x0264;
            L_0x0254:
                if (r2 == 0) goto L_0x005c;
            L_0x0256:
                r2.disconnect();	 Catch:{ Exception -> 0x025b }
                goto L_0x005c;
            L_0x025b:
                r0 = move-exception;
                goto L_0x005c;
            L_0x025e:
                r4.close();	 Catch:{ Exception -> 0x0262 }
                goto L_0x0252;
            L_0x0262:
                r0 = move-exception;
                goto L_0x0252;
            L_0x0264:
                r5.close();	 Catch:{ Exception -> 0x0268 }
                goto L_0x0254;
            L_0x0268:
                r0 = move-exception;
                goto L_0x0254;
            L_0x026a:
                r1 = move-exception;
                r2 = r0;
                r5 = r4;
                r4 = r3;
                r0 = r1;
            L_0x026f:
                if (r4 != 0) goto L_0x0276;
            L_0x0271:
                if (r5 != 0) goto L_0x027c;
            L_0x0273:
                if (r2 != 0) goto L_0x0282;
            L_0x0275:
                throw r0;
            L_0x0276:
                r4.close();	 Catch:{ Exception -> 0x027a }
                goto L_0x0271;
            L_0x027a:
                r1 = move-exception;
                goto L_0x0271;
            L_0x027c:
                r5.close();	 Catch:{ Exception -> 0x0280 }
                goto L_0x0273;
            L_0x0280:
                r1 = move-exception;
                goto L_0x0273;
            L_0x0282:
                r2.disconnect();	 Catch:{ Exception -> 0x0286 }
                goto L_0x0275;
            L_0x0286:
                r1 = move-exception;
                goto L_0x0275;
            L_0x0288:
                r0 = 0;
                nR = r0;
                goto L_0x0064;
            L_0x028d:
                r0 = move-exception;
                r5 = r4;
                r4 = r3;
                goto L_0x026f;
            L_0x0291:
                r0 = move-exception;
                r4 = r3;
                goto L_0x026f;
            L_0x0294:
                r0 = move-exception;
                goto L_0x026f;
            L_0x0296:
                r3 = move-exception;
                r4 = r0;
                r5 = r1;
                r0 = r3;
                goto L_0x026f;
            L_0x029b:
                r0 = move-exception;
                r5 = r4;
                r4 = r3;
                goto L_0x0250;
            L_0x029f:
                r0 = move-exception;
                r4 = r3;
                goto L_0x0250;
            L_0x02a2:
                r0 = move-exception;
                goto L_0x0250;
            L_0x02a4:
                r1 = move-exception;
                r1 = r0;
                goto L_0x0250;
            L_0x02a7:
                r4 = move-exception;
                r4 = r0;
                r5 = r1;
                r1 = r3;
                goto L_0x0250;
            L_0x02ac:
                r0 = move-exception;
                r5 = r4;
                r4 = r3;
                goto L_0x0232;
            L_0x02b0:
                r0 = move-exception;
                r4 = r3;
                goto L_0x0232;
            L_0x02b3:
                r0 = move-exception;
                goto L_0x0232;
            L_0x02b6:
                r1 = move-exception;
                r1 = r0;
                goto L_0x0232;
            L_0x02ba:
                r4 = move-exception;
                r4 = r0;
                r5 = r1;
                r1 = r3;
                goto L_0x0232;
            L_0x02c0:
                r2 = move-exception;
                r2 = r0;
                r5 = r4;
                r4 = r3;
                goto L_0x01dd;
            L_0x02c6:
                r0 = move-exception;
                r5 = r4;
                r4 = r3;
                goto L_0x01dd;
            L_0x02cb:
                r0 = move-exception;
                r4 = r3;
                goto L_0x01dd;
            L_0x02cf:
                r0 = move-exception;
                goto L_0x01dd;
            L_0x02d2:
                r4 = move-exception;
                r4 = r0;
                r5 = r1;
                r1 = r3;
                goto L_0x01dd;
            L_0x02d8:
                r2 = move-exception;
                r2 = r0;
                r5 = r4;
                r4 = r3;
                goto L_0x01ae;
            L_0x02de:
                r0 = move-exception;
                r5 = r4;
                r4 = r3;
                goto L_0x01ae;
            L_0x02e3:
                r0 = move-exception;
                r4 = r3;
                goto L_0x01ae;
            L_0x02e7:
                r4 = move-exception;
                r4 = r0;
                r5 = r1;
                r1 = r3;
                goto L_0x01ae;
            L_0x02ed:
                r2 = move-exception;
                r2 = r4;
                r10 = r3;
                r3 = r1;
                r1 = r10;
                goto L_0x00d6;
            L_0x02f4:
                r0 = move-exception;
                r0 = r2;
                r2 = r4;
                r10 = r3;
                r3 = r1;
                r1 = r10;
                goto L_0x00d6;
            L_0x02fc:
                r0 = move-exception;
                r0 = r2;
                r2 = r5;
                r10 = r3;
                r3 = r1;
                r1 = r10;
                goto L_0x00d6;
            L_0x0304:
                r0 = r3;
                r3 = r1;
                r1 = r4;
                goto L_0x0052;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.loc.az.pz(android.content.Context, java.lang.String, java.lang.String, int, boolean):boolean");
            }
        }
