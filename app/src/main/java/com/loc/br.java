package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import com.amap.api.location.f;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

public class br implements f {
    Context sI;
    boolean sJ = false;
    private volatile boolean sK = false;
    private boolean sL = false;
    String sM = null;
    aZ sN = new aZ(this, this);
    aq sO = null;
    boolean sP = false;
    Vector sQ = new Vector();
    private int sR = 0;
    private boolean sS = false;
    private boolean sT = false;
    private e sU = null;
    volatile boolean sV = false;
    boolean sW = false;
    Object sX = new Object();
    Looper sY;
    AmapLoc sZ;
    long ta = bq.vM();
    JSONObject tb = new JSONObject();
    AmapLoc tc;
    ServerSocket td = null;
    boolean te = false;
    Socket tf = null;
    boolean tg = false;
    t th;

    public br(Context context) {
        this.sI = context.getApplicationContext();
    }

    private void wD() {
        x xVar = null;
        Looper.prepare();
        this.sY = Looper.myLooper();
        H.mT(this.sI);
        this.sU.iF(this.sI);
        if (this.sU != null) {
            this.sU.iG("api_serverSDK_130905##S128DF1572465B890OE3F7A13167KLEI##" + Y.nI(this.sI) + "##" + Y.nN(this.sI));
        }
        try {
            xVar = new u("loc", "2.2.0", "AMAP_Location_SDK_Android 2.2.0").mm(H.mQ()).mn();
        } catch (i e) {
        }
        try {
            String rx = aN.rx(this.sI, xVar, null, true);
            try {
                this.tb.put("key", Y.nN(this.sI));
                this.tb.put("X-INFO", rx);
                this.tb.put("User-Agent", "AMAP_Location_SDK_Android 2.2.0");
                this.tb.put("netloc", "0");
                this.tb.put("gpsstatus", "0");
                this.tb.put("nbssid", "0");
                if (!this.tb.has("reversegeo")) {
                    this.tb.put("reversegeo", "0");
                }
                if (!this.tb.has("wait1stwifi")) {
                    this.tb.put("wait1stwifi", "0");
                }
                this.tb.put("autoup", "0");
                this.tb.put("upcolmobile", 1);
                this.tb.put("enablegetreq", 1);
                this.tb.put("wifiactivescan", 1);
            } catch (JSONException e2) {
            }
            if (this.sU != null) {
                this.sU.iH(this.tb);
            }
            this.sK = true;
        } catch (Throwable th) {
        }
    }

    private void wE(JSONObject jSONObject) {
        try {
            if (this.sU != null) {
                this.sU.iH(jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    private void wH(Socket socket) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        PrintStream printStream;
        Throwable th;
        PrintStream printStream2;
        String str = null;
        if (socket != null) {
            int i = 30000;
            try {
                String str2 = "jsonp1";
                System.currentTimeMillis();
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    try {
                        int i2;
                        String str3;
                        String str4;
                        int i3;
                        long currentTimeMillis;
                        AmapLoc amapLoc;
                        PrintStream printStream3;
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (readLine.length() > 0) {
                                String[] split = readLine.split(" ");
                                if (split != null && split.length > 1) {
                                    split = split[1].split("\\?");
                                    if (split != null && split.length > 1) {
                                        String[] split2 = split[1].split("&");
                                        if (split2 != null && split2.length > 0) {
                                            for (String split3 : split2) {
                                                String[] split4 = split3.split("=");
                                                if (split4 != null && split4.length > 1) {
                                                    if ("to".equals(split4[0])) {
                                                        try {
                                                            i = Integer.parseInt(split4[1]);
                                                        } catch (Exception e) {
                                                            H.lM = i2;
                                                        } catch (Throwable th2) {
                                                            th = th2;
                                                        }
                                                    }
                                                    if ("callback".equals(split4[0])) {
                                                        str2 = split4[1];
                                                    }
                                                    if ("_".equals(split4[0])) {
                                                        Long.parseLong(split4[1]);
                                                    }
                                                }
                                            }
                                            str4 = str2;
                                            i3 = i;
                                            str3 = str4;
                                            i2 = H.lM;
                                            H.lM = i3;
                                            currentTimeMillis = System.currentTimeMillis();
                                            if (this.tc != null) {
                                                if ((currentTimeMillis - this.tc.yU() <= 5000 ? 1 : null) == null) {
                                                }
                                                if (str == null) {
                                                    if (this.tc == null) {
                                                        amapLoc = this.tc;
                                                        str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                                    } else {
                                                        str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                                    }
                                                    if (bq.wh(this.sI)) {
                                                        str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                                    }
                                                }
                                                printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                                printStream3.println("HTTP/1.0 200 OK");
                                                printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                                printStream3.println();
                                                printStream3.println(str);
                                                printStream3.close();
                                                bufferedReader.close();
                                                socket.close();
                                            }
                                            if (!bq.wh(this.sI)) {
                                                this.tc = this.sU.iI(false);
                                                if (this.tc.yw() != 0) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':" + this.tc.yw() + ",'error':'" + this.tc.yA() + "'})";
                                                }
                                                H.lM = i2;
                                            }
                                            if (str == null) {
                                                if (this.tc == null) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                                } else {
                                                    amapLoc = this.tc;
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                                }
                                                if (bq.wh(this.sI)) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                                }
                                            }
                                            printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                            printStream3.println("HTTP/1.0 200 OK");
                                            printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                            printStream3.println();
                                            printStream3.println(str);
                                            printStream3.close();
                                            bufferedReader.close();
                                            socket.close();
                                        }
                                        str4 = str2;
                                        i3 = 30000;
                                        str3 = str4;
                                        i2 = H.lM;
                                        H.lM = i3;
                                        currentTimeMillis = System.currentTimeMillis();
                                        if (this.tc != null) {
                                            if (currentTimeMillis - this.tc.yU() <= 5000) {
                                            }
                                            if ((currentTimeMillis - this.tc.yU() <= 5000 ? 1 : null) == null) {
                                            }
                                            if (str == null) {
                                                if (this.tc == null) {
                                                    amapLoc = this.tc;
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                                } else {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                                }
                                                if (bq.wh(this.sI)) {
                                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                                }
                                            }
                                            printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                            printStream3.println("HTTP/1.0 200 OK");
                                            printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                            printStream3.println();
                                            printStream3.println(str);
                                            printStream3.close();
                                            bufferedReader.close();
                                            socket.close();
                                        }
                                        if (bq.wh(this.sI)) {
                                            this.tc = this.sU.iI(false);
                                            if (this.tc.yw() != 0) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':" + this.tc.yw() + ",'error':'" + this.tc.yA() + "'})";
                                            }
                                            H.lM = i2;
                                        }
                                        if (str == null) {
                                            if (this.tc == null) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                            } else {
                                                amapLoc = this.tc;
                                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                            }
                                            if (bq.wh(this.sI)) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                            }
                                        }
                                        printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                        printStream3.println("HTTP/1.0 200 OK");
                                        printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                        printStream3.println();
                                        printStream3.println(str);
                                        printStream3.close();
                                        bufferedReader.close();
                                        socket.close();
                                    }
                                    str4 = str2;
                                    i3 = 30000;
                                    str3 = str4;
                                    i2 = H.lM;
                                    H.lM = i3;
                                    currentTimeMillis = System.currentTimeMillis();
                                    if (this.tc != null) {
                                        if (currentTimeMillis - this.tc.yU() <= 5000) {
                                        }
                                        if ((currentTimeMillis - this.tc.yU() <= 5000 ? 1 : null) == null) {
                                        }
                                        if (str == null) {
                                            if (this.tc == null) {
                                                amapLoc = this.tc;
                                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                            } else {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                            }
                                            if (bq.wh(this.sI)) {
                                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                            }
                                        }
                                        printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                        printStream3.println("HTTP/1.0 200 OK");
                                        printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                        printStream3.println();
                                        printStream3.println(str);
                                        printStream3.close();
                                        bufferedReader.close();
                                        socket.close();
                                    }
                                    if (bq.wh(this.sI)) {
                                        this.tc = this.sU.iI(false);
                                        if (this.tc.yw() != 0) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':" + this.tc.yw() + ",'error':'" + this.tc.yA() + "'})";
                                        }
                                        H.lM = i2;
                                    }
                                    if (str == null) {
                                        if (this.tc == null) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                        } else {
                                            amapLoc = this.tc;
                                            str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                        }
                                        if (bq.wh(this.sI)) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                        }
                                    }
                                    printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                    printStream3.println("HTTP/1.0 200 OK");
                                    printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                    printStream3.println();
                                    printStream3.println(str);
                                    printStream3.close();
                                    bufferedReader.close();
                                    socket.close();
                                }
                                str4 = str2;
                                i3 = 30000;
                                str3 = str4;
                                i2 = H.lM;
                                H.lM = i3;
                                currentTimeMillis = System.currentTimeMillis();
                                if (this.tc != null) {
                                    if (currentTimeMillis - this.tc.yU() <= 5000) {
                                    }
                                    if ((currentTimeMillis - this.tc.yU() <= 5000 ? 1 : null) == null) {
                                    }
                                    if (str == null) {
                                        if (this.tc == null) {
                                            amapLoc = this.tc;
                                            str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                        } else {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                        }
                                        if (bq.wh(this.sI)) {
                                            str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                        }
                                    }
                                    printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                    printStream3.println("HTTP/1.0 200 OK");
                                    printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                    printStream3.println();
                                    printStream3.println(str);
                                    printStream3.close();
                                    bufferedReader.close();
                                    socket.close();
                                }
                                if (bq.wh(this.sI)) {
                                    this.tc = this.sU.iI(false);
                                    if (this.tc.yw() != 0) {
                                        str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':" + this.tc.yw() + ",'error':'" + this.tc.yA() + "'})";
                                    }
                                    H.lM = i2;
                                }
                                if (str == null) {
                                    if (this.tc == null) {
                                        str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                    } else {
                                        amapLoc = this.tc;
                                        str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                    }
                                    if (bq.wh(this.sI)) {
                                        str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                    }
                                }
                                printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                                printStream3.println("HTTP/1.0 200 OK");
                                printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                                printStream3.println();
                                printStream3.println(str);
                                printStream3.close();
                                bufferedReader.close();
                                socket.close();
                            }
                        }
                        str4 = str2;
                        i3 = 30000;
                        str3 = str4;
                        i2 = H.lM;
                        H.lM = i3;
                        currentTimeMillis = System.currentTimeMillis();
                        if (this.tc != null) {
                            if (currentTimeMillis - this.tc.yU() <= 5000) {
                            }
                            if ((currentTimeMillis - this.tc.yU() <= 5000 ? 1 : null) == null) {
                            }
                            if (str == null) {
                                if (this.tc == null) {
                                    amapLoc = this.tc;
                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                                } else {
                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                                }
                                if (bq.wh(this.sI)) {
                                    str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                                }
                            }
                            printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                            printStream3.println("HTTP/1.0 200 OK");
                            printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                            printStream3.println();
                            printStream3.println(str);
                            printStream3.close();
                            bufferedReader.close();
                            socket.close();
                        }
                        if (bq.wh(this.sI)) {
                            this.tc = this.sU.iI(false);
                            if (this.tc.yw() != 0) {
                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':" + this.tc.yw() + ",'error':'" + this.tc.yA() + "'})";
                            }
                            H.lM = i2;
                        }
                        if (str == null) {
                            if (this.tc == null) {
                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':8,'error':'unknown error'})";
                            } else {
                                amapLoc = this.tc;
                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':0,'error':'','location':{'y':" + amapLoc.yJ() + ",'precision':" + amapLoc.yN() + ",'x':" + amapLoc.yG() + "},'version_code':'" + "2.2.0" + "','version':'" + "2.2.0" + "'})";
                            }
                            if (bq.wh(this.sI)) {
                                str = str3 + "&&" + str3 + "({'package':'" + this.sM + "','error_code':36,'error':'app is background'})";
                            }
                        }
                        printStream3 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                        printStream3.println("HTTP/1.0 200 OK");
                        printStream3.println("Content-Length:" + str.getBytes("UTF-8").length);
                        printStream3.println();
                        printStream3.println(str);
                        printStream3.close();
                        try {
                            bufferedReader.close();
                            socket.close();
                        } catch (Exception e2) {
                        }
                    } catch (Throwable th22) {
                        th = th22;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    Object obj = str;
                    printStream2 = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                    printStream2.println("HTTP/1.0 200 OK");
                    printStream2.println("Content-Length:" + str.getBytes("UTF-8").length);
                    printStream2.println();
                    printStream2.println(str);
                    printStream2.close();
                    bufferedReader.close();
                    socket.close();
                    throw th;
                }
            } catch (Exception e3) {
                try {
                    bufferedReader.close();
                    socket.close();
                } catch (Exception e4) {
                }
            } catch (Exception e5) {
                try {
                    bufferedReader2.close();
                    socket.close();
                } catch (Exception e6) {
                }
            } catch (Exception e7) {
                try {
                    bufferedReader.close();
                    socket.close();
                } catch (Exception e8) {
                }
            } catch (Throwable th4) {
                th4.printStackTrace();
            }
        }
    }

    private void wI() {
        wG();
        this.sK = false;
        this.sL = false;
        this.sS = false;
        this.sT = false;
        this.sR = 0;
        this.sU.iM();
        this.sQ.clear();
        if (this.sJ) {
            Process.killProcess(Process.myPid());
        }
        if (this.sN != null) {
            this.sN.removeCallbacksAndMessages(null);
        }
    }

    private boolean wy() {
        boolean z;
        synchronized (this.sX) {
            z = this.sV;
        }
        return z;
    }

    public Handler iq() {
        return this.sN;
    }

    public void onCreate() {
        try {
            this.sU = new e();
            this.sM = this.sI.getApplicationContext().getPackageName();
            this.sV = true;
            this.sO = new aq(this);
            this.sO.setName("serviceThread");
            this.sO.start();
        } catch (Throwable th) {
        }
    }

    public void onDestroy() {
        try {
            synchronized (this.sX) {
                this.sV = false;
                this.sX.notify();
            }
        } catch (Throwable th) {
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 0;
    }

    public void wA() {
        try {
            this.sU.jC();
        } catch (Throwable th) {
        }
    }

    public void wB() {
        try {
            if (!this.sL) {
                this.sL = true;
                this.sU.jD(this.sI);
            }
        } catch (Throwable th) {
        }
    }

    public void wC() {
        try {
            if (!this.sK) {
                wD();
            }
        } catch (Throwable th) {
            this.sK = false;
        }
    }

    public synchronized void wF() {
        try {
            if (!this.tg) {
                this.th = new t(this);
                this.th.start();
                this.tg = true;
            }
        } catch (Throwable th) {
        }
    }

    public synchronized void wG() {
        try {
            if (this.td != null) {
                this.td.close();
            }
            if (this.tf != null) {
                this.tf.close();
            }
        } catch (IOException e) {
        }
        try {
            this.td = null;
            this.th = null;
            this.tg = false;
            this.te = false;
        } catch (Throwable th) {
        }
    }

    public void wJ() {
        try {
            if (this.sU != null) {
                this.sU.jO();
            }
        } catch (Throwable th) {
        }
    }

    public AmapLoc wz(boolean z) {
        return this.sU.iI(z);
    }
}
