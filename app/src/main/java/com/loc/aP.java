package com.loc;

import android.content.Context;
import android.os.Build.VERSION;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class aP extends Thread implements ah {
    private a pM;
    private be pN;
    private x pO;
    private String pP;
    private RandomAccessFile pQ;
    private String pR;
    private Context pS;
    private String pT;
    private String pU;
    private String pV;
    private String pW;
    private int pX;
    private int pY;

    public aP(Context context, a aVar, x xVar) {
        try {
            this.pS = context.getApplicationContext();
            this.pO = xVar;
            if (aVar != null) {
                this.pM = aVar;
                this.pN = new be(new bc(this.pM));
                String[] split = this.pM.iz().split("/");
                this.pR = split[split.length - 1];
                split = this.pR.split("_");
                this.pT = split[0];
                this.pU = aVar.iB();
                this.pV = split[1];
                this.pX = Integer.parseInt(split[3]);
                this.pY = Integer.parseInt(split[4].split("\\.")[0]);
                this.pW = aVar.iA();
                this.pP = k.lz(context, this.pR);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private boolean rE(ae aeVar, bo boVar, String str, String str2, String str3, String str4) {
        if ("errorstatus".equals(boVar.vy())) {
            if (!new File(k.ly(this.pS, this.pO.mz(), this.pO.mA())).exists()) {
                k.lJ(this.pS, aeVar, this.pO);
                an.oI(this.pS, this.pO, k.ly(this.pS, this.pT, this.pO.mA()), k.lA(this.pS), null, this.pS.getClassLoader());
            }
            return true;
        } else if (!new File(this.pP).exists()) {
            return false;
        } else {
            List oe = aeVar.oe(bP.yk(k.lx(str, str2), str, str2, str3), new bP());
            if (oe != null && oe.size() > 0) {
                return true;
            }
            try {
                k.lG(this.pS, aeVar, this.pO, new E(k.lx(str, this.pO.mA()), str4, str, str2, str3).mH("usedex").mI(), this.pP);
                an.oI(this.pS, this.pO, k.ly(this.pS, this.pT, this.pO.mA()), k.lA(this.pS), null, this.pS.getClassLoader());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return true;
        }
    }

    private boolean rF(String str, String str2, String str3, String str4, String str5) {
        ae aeVar = new ae(this.pS, v.mu());
        try {
            List oe = aeVar.oe(bP.yh(str3, "usedex"), new bP());
            if (oe != null) {
                if (oe.size() > 0 && U.nC(((bo) oe.get(0)).vx(), this.pV) > 0) {
                    return true;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        bo qd = aC.qd(aeVar, str);
        return qd == null ? false : rE(aeVar, qd, str3, str4, str2, str5);
    }

    private boolean rG(String str, String str2) {
        boolean z = false;
        if (this.pO == null) {
            return false;
        }
        if (this.pO.mz().equals(str) && this.pO.mA().equals(str2)) {
            z = true;
        }
        return z;
    }

    private boolean rH() {
        return VERSION.SDK_INT >= this.pY && VERSION.SDK_INT <= this.pX;
    }

    private boolean rI(Context context) {
        return bK.xP(context) == 1;
    }

    private boolean rJ() {
        try {
            if (!rG(this.pT, this.pU) || rF(this.pR, this.pV, this.pT, this.pU, this.pW) || !rI(this.pS) || !rH()) {
                return false;
            }
            rK(this.pO.mz());
            return true;
        } catch (Throwable th) {
            k.lL(th);
            th.printStackTrace();
            return false;
        }
    }

    private void rK(String str) {
        ae aeVar = new ae(this.pS, v.mu());
        List oe = aeVar.oe(bP.yh(str, "copy"), new bP());
        k.lK(oe);
        if (oe != null && oe.size() > 1) {
            int size = oe.size();
            for (int i = 1; i < size; i++) {
                k.lB(this.pS, aeVar, ((bo) oe.get(i)).vt());
            }
        }
    }

    public void ok(byte[] bArr, long j) {
        try {
            if (this.pQ == null) {
                File file = new File(this.pP);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.pQ = new RandomAccessFile(file, "rw");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
            return;
        }
        try {
            this.pQ.seek(j);
            this.pQ.write(bArr);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void ol() {
    }

    public void om() {
        try {
            if (this.pQ != null) {
                this.pQ.close();
                String iA = this.pM.iA();
                if (k.lw(this.pP, iA)) {
                    String iB = this.pM.iB();
                    ae aeVar = new ae(this.pS, v.mu());
                    aC.qc(aeVar, new E(this.pR, iA, this.pT, iB, this.pV).mH("copy").mI(), bP.yk(this.pR, this.pT, iB, this.pV));
                    try {
                        k.lG(this.pS, aeVar, this.pO, new E(k.lx(this.pT, this.pO.mA()), iA, this.pT, iB, this.pV).mH("usedex").mI(), this.pP);
                        an.oI(this.pS, this.pO, k.ly(this.pS, this.pT, this.pO.mA()), k.lA(this.pS), null, this.pS.getClassLoader());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
                try {
                    new File(this.pP).delete();
                } catch (Throwable th2) {
                    th2.printStackTrace();
                }
            }
        } catch (IOException e22) {
            e22.printStackTrace();
        } catch (Throwable th22) {
            th22.printStackTrace();
        }
    }

    public void on(Throwable th) {
        try {
            if (this.pQ != null) {
                this.pQ.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public void rD() {
        try {
            start();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void run() {
        try {
            if (rJ()) {
                this.pN.tz(this);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
