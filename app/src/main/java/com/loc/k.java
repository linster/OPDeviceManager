package com.loc;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class k {
    static String lA(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "dex";
    }

    static void lB(Context context, ae aeVar, String str) {
        lC(context, aeVar, str);
    }

    private static void lC(Context context, ae aeVar, String str) {
        File file = new File(lz(context, str));
        if (file.exists()) {
            file.delete();
        }
        aeVar.nW(bP.yj(str), new bP());
    }

    static void lD(Context context, String str, String str2) {
        new aJ(context, str, str2).start();
    }

    static void lE(Context context, ae aeVar, String str) {
        lC(context, aeVar, str);
        lC(context, aeVar, lv(str));
    }

    static void lF(ae aeVar, Context context, x xVar) {
        p bPVar = new bP();
        String mz = xVar.mz();
        String lx = lx(mz, xVar.mA());
        bo qd = aC.qd(aeVar, lx);
        if (qd != null) {
            lE(context, aeVar, lx);
            List oe = aeVar.oe(bP.yg(mz, qd.vx()), bPVar);
            if (oe != null && oe.size() > 0) {
                bo boVar = (bo) oe.get(0);
                boVar.vz("errorstatus");
                aC.qc(aeVar, boVar, bP.yj(boVar.vt()));
                File file = new File(lz(context, boVar.vt()));
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    static void lG(Context context, ae aeVar, x xVar, bo boVar, String str) {
        FileOutputStream fileOutputStream;
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        FileOutputStream fileOutputStream2 = null;
        InputStream fileInputStream;
        try {
            String mz = xVar.mz();
            lE(context, aeVar, boVar.vt());
            fileInputStream = new FileInputStream(new File(str));
            try {
                fileOutputStream = new FileOutputStream(new File(ly(context, mz, xVar.mA())), true);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    aC.qc(aeVar, boVar, bP.yj(boVar.vt()));
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e4) {
                    e2 = e4;
                    fileOutputStream2 = fileInputStream;
                    try {
                        throw e2;
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileOutputStream2;
                        fileOutputStream2 = fileOutputStream;
                    }
                } catch (IOException e5) {
                    e32 = e5;
                    fileOutputStream2 = fileOutputStream;
                    throw e32;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream2 = fileOutputStream;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e62) {
                            e62.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e7) {
                e2 = e7;
                fileOutputStream = null;
                Object obj = fileInputStream;
                throw e2;
            } catch (IOException e8) {
                e32 = e8;
                throw e32;
            } catch (Throwable th4) {
                th = th4;
                throw th;
            }
        } catch (FileNotFoundException e9) {
            e2 = e9;
            fileOutputStream = null;
            throw e2;
        } catch (IOException e10) {
            e32 = e10;
            fileInputStream = null;
            throw e32;
        } catch (Throwable th5) {
            th = th5;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            throw th;
        }
    }

    static boolean lH(Context context, ae aeVar, String str, x xVar) {
        return lI(aeVar, str, lz(context, str), xVar);
    }

    static boolean lI(ae aeVar, String str, String str2, x xVar) {
        bo qd = aC.qd(aeVar, str);
        return qd != null && xVar.mA().equals(qd.vw()) && lw(str2, qd.vu());
    }

    static String lJ(Context context, ae aeVar, x xVar) {
        List oe = aeVar.oe(bP.yh(xVar.mz(), "copy"), new bP());
        if (oe == null || oe.size() == 0) {
            return null;
        }
        String vx;
        lK(oe);
        for (int i = 0; i < oe.size(); i++) {
            bo boVar = (bo) oe.get(i);
            if (lH(context, aeVar, boVar.vt(), xVar)) {
                try {
                    lG(context, aeVar, xVar, new E(lx(xVar.mz(), xVar.mA()), boVar.vu(), xVar.mz(), xVar.mA(), boVar.vx()).mH("usedex").mI(), lz(context, boVar.vt()));
                    vx = boVar.vx();
                    break;
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else {
                lB(context, aeVar, boVar.vt());
            }
        }
        vx = null;
        return vx;
    }

    static void lK(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int i2 = i + 1; i2 < list.size(); i2++) {
                bo boVar = (bo) list.get(i);
                bo boVar2 = (bo) list.get(i2);
                if (U.nC(boVar2.vx(), boVar.vx()) > 0) {
                    list.set(i, boVar2);
                    list.set(i2, boVar);
                }
            }
        }
    }

    static void lL(Throwable th) {
        ar.oV(ar.oT() + File.separator + "dynamiclog.txt", bw.xa(th), false);
    }

    static String lv(String str) {
        return str + ".dex";
    }

    static boolean lw(String str, String str2) {
        String mZ = J.mZ(str);
        return mZ != null && mZ.equalsIgnoreCase(str2);
    }

    static String lx(String str, String str2) {
        return J.na(str + str2) + ".jar";
    }

    static String ly(Context context, String str, String str2) {
        return lz(context, lx(str, str2));
    }

    static String lz(Context context, String str) {
        return lA(context) + File.separator + str;
    }
}
