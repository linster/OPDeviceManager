package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Process;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.json.JSONObject;

/* compiled from: Unknown */
public final class aU {
    private int c = 1270;
    private int d = 310;
    private int e = 4;
    private long j = 0;
    private Context qM = null;
    private boolean qN = true;
    private int qO = 200;
    private int qP = 1;
    private int qQ = 0;
    private int qR = 0;
    private c qS = null;

    private aU(Context context) {
        this.qM = context;
    }

    private static byte[] sA(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) ((byte) ((int) ((j >> (i << 3)) & 255)));
        }
        return bArr;
    }

    private static int sB(byte[] bArr, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < 4) {
            i3 += (bArr[i2 + i] & 255) << (i2 << 3);
            i2++;
        }
        return i3;
    }

    private static long sC(byte[] bArr, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < 8) {
            i3 += (bArr[i2 + 14] & 255) << (i2 << 3);
            i2++;
        }
        return (long) i3;
    }

    private void sD() {
        long currentTimeMillis = System.currentTimeMillis() + 28800000;
        if ((currentTimeMillis - this.j <= 86400000 ? 1 : 0) == 0) {
            this.j = (currentTimeMillis / 86400000) * 86400000;
            this.qQ = 0;
            this.qR = 0;
        }
    }

    protected static aU sw(Context context) {
        Throwable th;
        FileInputStream fileInputStream = null;
        aU aUVar = new aU(context);
        aUVar.qQ = 0;
        aUVar.qR = 0;
        aUVar.j = ((System.currentTimeMillis() + 28800000) / 86400000) * 86400000;
        FileInputStream fileInputStream2;
        try {
            fileInputStream2 = new FileInputStream(new File(sy(context) + File.separator + "data_carrier_status"));
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[32];
                while (true) {
                    int read = fileInputStream2.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                if (toByteArray != null) {
                    if (toByteArray.length >= 22) {
                        aUVar.qN = toByteArray[0] != (byte) 0;
                        aUVar.c = (toByteArray[1] * 10) << 10;
                        aUVar.d = (toByteArray[2] * 10) << 10;
                        aUVar.e = toByteArray[3];
                        aUVar.qO = toByteArray[4] * 10;
                        aUVar.qP = toByteArray[5];
                        long sC = sC(toByteArray, 14);
                        if ((aUVar.j - sC >= 86400000 ? 1 : 0) == 0) {
                            aUVar.j = sC;
                            aUVar.qQ = sB(toByteArray, 6);
                            aUVar.qR = sB(toByteArray, 10);
                        }
                    }
                }
                byteArrayOutputStream.close();
                try {
                    fileInputStream2.close();
                } catch (Exception e) {
                }
            } catch (Exception e2) {
            } catch (Throwable th2) {
                Throwable th3 = th2;
                fileInputStream = fileInputStream2;
                th = th3;
            }
        } catch (Exception e3) {
            fileInputStream2 = null;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e4) {
                }
            }
            return aUVar;
        } catch (Throwable th4) {
            th = th4;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e5) {
                }
            }
            throw th;
        }
        return aUVar;
    }

    private static String sy(Context context) {
        File file = null;
        boolean z = false;
        if (Process.myUid() != 1000) {
            file = aW.te(context);
        }
        try {
            z = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
        }
        if (z || !aW.tc()) {
            if (file != null) {
                return file.getPath();
            }
        }
        return context.getFilesDir().getPath();
    }

    private static byte[] sz(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) ((byte) (i >> (i2 << 3)));
        }
        return bArr;
    }

    protected final boolean sn() {
        sD();
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.qM.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected()) ? activeNetworkInfo.getType() != 1 ? this.qN && this.qR < this.d : this.qN && this.qQ < this.c : this.qN;
    }

    protected final int so() {
        return this.e;
    }

    protected final int sp() {
        return this.qO;
    }

    protected final int sq() {
        return this.qP;
    }

    protected final int sr() {
        sD();
        return this.qQ;
    }

    protected final void ss(int i) {
        sD();
        if (i < 0) {
            i = 0;
        }
        this.qQ = i;
    }

    protected final int st() {
        sD();
        return this.qR;
    }

    protected final void su(int i) {
        sD();
        if (i < 0) {
            i = 0;
        }
        this.qR = i;
    }

    protected final void sv(c cVar) {
        this.qS = cVar;
    }

    protected final boolean sx(String str) {
        boolean z;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        Throwable th;
        int i = 0;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("e")) {
                this.qN = jSONObject.getInt("e") != 0;
            }
            if (jSONObject.has("d")) {
                int i2 = jSONObject.getInt("d");
                this.c = ((i2 & 127) * 10) << 10;
                this.d = (((i2 & 3968) >> 7) * 10) << 10;
                this.e = (520192 & i2) >> 12;
                this.qO = ((66584576 & i2) >> 19) * 10;
                this.qP = (i2 & 2080374784) >> 26;
                if (this.qP == 31) {
                    this.qP = 1500;
                }
                if (this.qS != null) {
                    this.qS.iE();
                }
            }
            if (jSONObject.has("u") && jSONObject.getInt("u") != 0) {
                z = true;
                sD();
                fileOutputStream = new FileOutputStream(new File(sy(this.qM) + File.separator + "data_carrier_status"));
                byte[] sz = sz(this.qQ);
                byte[] sz2 = sz(this.qR);
                byte[] sA = sA(this.j);
                byte[] bArr = new byte[22];
                if (this.qN) {
                    i = 1;
                }
                bArr[0] = (byte) ((byte) i);
                bArr[1] = (byte) ((byte) (this.c / 10240));
                bArr[2] = (byte) ((byte) (this.d / 10240));
                bArr[3] = (byte) ((byte) this.e);
                bArr[4] = (byte) ((byte) (this.qO / 10));
                bArr[5] = (byte) ((byte) this.qP);
                bArr[6] = (byte) sz[0];
                bArr[7] = (byte) sz[1];
                bArr[8] = (byte) sz[2];
                bArr[9] = (byte) sz[3];
                bArr[10] = (byte) sz2[0];
                bArr[11] = (byte) sz2[1];
                bArr[12] = (byte) sz2[2];
                bArr[13] = (byte) sz2[3];
                bArr[14] = (byte) sA[0];
                bArr[15] = (byte) sA[1];
                bArr[16] = (byte) sA[2];
                bArr[17] = (byte) sA[3];
                bArr[18] = (byte) sA[4];
                bArr[19] = (byte) sA[5];
                bArr[20] = (byte) sA[6];
                bArr[21] = (byte) sA[7];
                fileOutputStream.write(bArr);
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                }
                return z;
            }
        } catch (Exception e2) {
        }
        z = false;
        try {
            sD();
            fileOutputStream = new FileOutputStream(new File(sy(this.qM) + File.separator + "data_carrier_status"));
            try {
                byte[] sz3 = sz(this.qQ);
                byte[] sz22 = sz(this.qR);
                byte[] sA2 = sA(this.j);
                byte[] bArr2 = new byte[22];
                if (this.qN) {
                    i = 1;
                }
                bArr2[0] = (byte) ((byte) i);
                bArr2[1] = (byte) ((byte) (this.c / 10240));
                bArr2[2] = (byte) ((byte) (this.d / 10240));
                bArr2[3] = (byte) ((byte) this.e);
                bArr2[4] = (byte) ((byte) (this.qO / 10));
                bArr2[5] = (byte) ((byte) this.qP);
                bArr2[6] = (byte) sz3[0];
                bArr2[7] = (byte) sz3[1];
                bArr2[8] = (byte) sz3[2];
                bArr2[9] = (byte) sz3[3];
                bArr2[10] = (byte) sz22[0];
                bArr2[11] = (byte) sz22[1];
                bArr2[12] = (byte) sz22[2];
                bArr2[13] = (byte) sz22[3];
                bArr2[14] = (byte) sA2[0];
                bArr2[15] = (byte) sA2[1];
                bArr2[16] = (byte) sA2[2];
                bArr2[17] = (byte) sA2[3];
                bArr2[18] = (byte) sA2[4];
                bArr2[19] = (byte) sA2[5];
                bArr2[20] = (byte) sA2[6];
                bArr2[21] = (byte) sA2[7];
                fileOutputStream.write(bArr2);
                fileOutputStream.close();
            } catch (Exception e3) {
                fileOutputStream2 = fileOutputStream;
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (Exception e4) {
                    }
                }
                return z;
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e5) {
                    }
                }
                throw th;
            }
        } catch (Exception e6) {
            fileOutputStream2 = null;
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            return z;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
        return z;
    }
}
