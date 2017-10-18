package com.loc;

import android.content.Context;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class aw extends Thread implements ah {
    private static String nK = "sodownload";
    private static String nL = "sofail";
    private be nD = new be(this.nE);
    private bD nE;
    private RandomAccessFile nF;
    private String nG;
    private String nH;
    private String nI;
    private Context nJ;

    public aw(Context context, String str, String str2, String str3) {
        this.nJ = context;
        this.nI = str3;
        this.nG = pf(context, str + "temp.so");
        this.nH = pf(context, "libwgs2gcj.so");
        this.nE = new bD(str2);
    }

    public static String pf(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "libso" + File.separator + str;
    }

    private static String pg(Context context, String str) {
        return pf(context, str);
    }

    private void pi() {
        File file = new File(this.nG);
        if (file.exists()) {
            file.delete();
        }
    }

    public void ok(byte[] bArr, long j) {
        try {
            if (this.nF == null) {
                File file = new File(this.nG);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.nF = new RandomAccessFile(file, "rw");
            }
        } catch (FileNotFoundException e) {
            pi();
            e.printStackTrace();
        } catch (Throwable th) {
            pi();
            th.printStackTrace();
            return;
        }
        try {
            this.nF.seek(j);
            this.nF.write(bArr);
        } catch (IOException e2) {
            pi();
            e2.printStackTrace();
        }
    }

    public void ol() {
        pi();
    }

    public void om() {
        try {
            if (this.nF != null) {
                this.nF.close();
            }
            if (J.mZ(this.nG).equalsIgnoreCase(this.nI)) {
                new File(this.nG).renameTo(new File(this.nH));
                au.oZ(this.nJ, new u(nK, "1.0.0", "sodownload_1.0.0").mm(new String[0]).mn());
                return;
            }
            pi();
            au.oZ(this.nJ, new u(nL, "1.0.0", "sodownload_1.0.0").mm(new String[0]).mn());
        } catch (Throwable th) {
            pi();
            File file = new File(this.nH);
            if (file.exists()) {
                file.delete();
            }
            try {
                au.oZ(this.nJ, new u(nL, "1.0.0", "sodownload_1.0.0").mm(new String[0]).mn());
            } catch (i e) {
                e.printStackTrace();
            }
            th.printStackTrace();
        }
    }

    public void on(Throwable th) {
        try {
            if (this.nF != null) {
                this.nF.close();
            }
            pi();
            File file = new File(pg(this.nJ, "tempfile"));
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                file.createNewFile();
            }
        } catch (Throwable e) {
            D.mF(e, "SDKCoordinatorDownload", "onException");
        } catch (Throwable e2) {
            D.mF(e2, "SDKCoordinatorDownload", "onException");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void ph() {
        /*
        r2 = this;
        r0 = r2.nE;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = r2.nE;
        r0 = r0.md();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x0004;
    L_0x0011:
        r0 = r2.nE;
        r0 = r0.md();
        r1 = "libJni_wgs2gcj.so";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x0004;
    L_0x0020:
        r0 = r2.nE;
        r0 = r0.md();
        r1 = android.os.Build.CPU_ABI;
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x0004;
    L_0x002e:
        r0 = new java.io.File;
        r1 = r2.nH;
        r0.<init>(r1);
        r0 = r0.exists();
        if (r0 != 0) goto L_0x003f;
    L_0x003b:
        r2.start();
        return;
    L_0x003f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.ph():void");
    }

    public void run() {
        try {
            File file = new File(pg(this.nJ, "tempfile"));
            if (file.exists()) {
                au.oZ(this.nJ, new u(nL, "1.0.0", "sodownload_1.0.0").mm(new String[0]).mn());
                file.delete();
            }
            this.nD.tz(this);
        } catch (Throwable th) {
            th.printStackTrace();
            pi();
        }
    }
}
