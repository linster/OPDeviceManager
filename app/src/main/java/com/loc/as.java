package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.autonavi.aps.amapapi.model.AmapLoc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.json.JSONObject;

/* compiled from: APS */
public class as {
    public static final StringBuilder c;
    private long A;
    private boolean B;
    private boolean C;
    private long D;
    private long E;
    private int F;
    private String G;
    private String H;
    private bo I;
    private Timer J;
    private TimerTask K;
    private int L;
    private bw M;
    private cc N;
    private int[] O;
    private String P;
    private String Q;
    private long R;
    private long S;
    private String T;
    private ba U;
    private AmapLoc V;
    private String W;
    private Timer X;
    private TimerTask Y;
    private String Z;
    public boolean a;
    private int aa;
    private int ab;
    private String ac;
    bl b;
    at d;
    int e;
    boolean f;
    AmapLoc g;
    Object h;
    public boolean i;
    int j;
    boolean k;
    boolean l;
    a m;
    private Context n;
    private ConnectivityManager o;
    private bc p;
    private bb q;
    private ArrayList<ScanResult> r;
    private ArrayList<ScanResult> s;
    private HashMap<String, ArrayList<ScanResult>> t;
    private b u;
    private WifiInfo v;
    private JSONObject w;
    private AmapLoc x;
    private long y;
    private long z;

    /* compiled from: APS */
    /* renamed from: com.loc.as.1 */
    class AnonymousClass1 extends TimerTask {
        final /* synthetic */ int a;
        final /* synthetic */ as b;

        AnonymousClass1(as asVar, int i) {
            this.b = asVar;
            this.a = i;
        }

        public void run() {
            Thread.currentThread().setPriority(1);
            this.b.b(this.a);
        }
    }

    /* compiled from: APS */
    class a implements com.loc.at.a {
        final /* synthetic */ as a;

        a(as asVar) {
            this.a = asVar;
        }

        public void a(int i) {
            this.a.e = i;
        }
    }

    /* compiled from: APS */
    private class b extends BroadcastReceiver {
        final /* synthetic */ as a;

        private b(as asVar) {
            this.a = asVar;
        }

        public void onReceive(Context context, Intent intent) {
            Collection collection = null;
            if (context != null && intent != null) {
                try {
                    String action = intent.getAction();
                    if (!TextUtils.isEmpty(action)) {
                        bc a = this.a.p;
                        if (!action.equals("android.net.wifi.SCAN_RESULTS")) {
                            if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                                if (this.a.p != null) {
                                    int i = 4;
                                    try {
                                        i = a.c();
                                    } catch (Exception e) {
                                    }
                                    if (this.a.s == null) {
                                        this.a.s = new ArrayList();
                                    }
                                    switch (i) {
                                        case 0:
                                            this.a.n();
                                            break;
                                        case 1:
                                            this.a.n();
                                            break;
                                        case 2:
                                        case 3:
                                            break;
                                        case 4:
                                            this.a.n();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            } else if (action.equals("android.intent.action.SCREEN_ON")) {
                                this.a.l = true;
                            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                                this.a.l = false;
                                if (this.a.M != null) {
                                    this.a.u();
                                }
                            } else if (!action.equals("android.intent.action.AIRPLANE_MODE")) {
                                if (action.equals("android.location.GPS_FIX_CHANGE")) {
                                    this.a.k = true;
                                } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE") && this.a.A()) {
                                    this.a.a(true, 2);
                                }
                            }
                        } else if (a != null) {
                            try {
                                collection = a.a();
                            } catch (Exception e2) {
                            }
                            if (collection != null) {
                                synchronized (this.a.h) {
                                    this.a.s.clear();
                                    this.a.s.addAll(collection);
                                }
                            }
                            this.a.E = br.b();
                        }
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
    }

    static {
        c = new StringBuilder();
    }

    public as() {
        this.n = null;
        this.o = null;
        this.p = null;
        this.r = new ArrayList();
        this.s = new ArrayList();
        this.t = new HashMap();
        this.u = new b();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = false;
        this.C = false;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = "00:00:00:00:00:00";
        this.H = null;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = 0;
        this.M = null;
        this.N = null;
        this.a = false;
        this.O = new int[]{0, 0, 0};
        this.P = null;
        this.Q = null;
        this.R = 0;
        this.S = 0;
        this.T = null;
        this.U = null;
        this.b = null;
        this.e = -1;
        this.f = false;
        this.V = null;
        this.W = null;
        this.X = null;
        this.Y = null;
        this.Z = null;
        this.aa = 0;
        this.ab = 0;
        this.g = null;
        this.ac = "-1";
        this.h = new Object();
        this.i = false;
        this.j = 12;
        this.k = false;
        this.l = true;
        this.m = new a(this);
    }

    public synchronized void a(Context context) {
        if (context != null) {
            if (TextUtils.isEmpty(c.l)) {
                c.l = br.b(context);
            }
            if (this.n == null) {
                this.n = context.getApplicationContext();
                this.b = bl.a(context);
                i();
                j();
                c.o = true;
                this.I = new bo();
                try {
                    this.M = bw.a(this.n);
                } catch (Exception e) {
                }
                this.A = br.b();
                this.q.d();
                br.a();
                bf.a().a(context);
                bi.a().a(context);
                this.i = true;
            }
        }
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str) && str.contains("##")) {
            String[] split = str.split("##");
            if (split.length == 4) {
                c.f = split[0];
                c.g = split[1];
                c.i = split[2];
                c.j = split[3];
                return;
            }
            w();
            return;
        }
        w();
    }

    public void a(JSONObject jSONObject) {
        this.w = jSONObject;
        if (br.a(jSONObject, "collwifiscan")) {
            try {
                Object string = jSONObject.getString("collwifiscan");
                if (TextUtils.isEmpty(string)) {
                    c.n = 20;
                } else {
                    c.n = Integer.parseInt(string) / 1000;
                }
                if (r()) {
                    this.M.b(c.n * 1000);
                }
            } catch (Exception e) {
            }
        }
        if (this.q != null) {
            this.q.a(jSONObject);
        }
        if (this.p != null) {
            this.p.a(jSONObject);
        }
    }

    public synchronized AmapLoc a(boolean z) throws Exception {
        if (c.length() > 0) {
            c.delete(0, c.length());
        }
        AmapLoc amapLoc;
        if (E()) {
            this.F++;
            br.a();
            this.B = br.a(this.n);
            if (z) {
                return G();
            }
            if (this.F == 2) {
                t();
                D();
                if (this.n != null) {
                    SharedPreferences sharedPreferences = this.n.getSharedPreferences("pref", 0);
                    b(sharedPreferences);
                    c(sharedPreferences);
                    a(sharedPreferences);
                }
                I();
            }
            if (this.F == 1 && p()) {
                if (this.s.isEmpty()) {
                    this.E = br.b();
                    Collection a = this.p.a();
                    synchronized (this.h) {
                        if (!(this.s == null || a == null)) {
                            this.s.addAll(a);
                        }
                    }
                }
                x();
            }
            Object obj = null;
            String str = "";
            try {
                if (br.a(this.w, "reversegeo")) {
                    str = this.w.getString("reversegeo");
                    obj = !str.equals("1") ? null : 1;
                }
            } catch (Exception e) {
            }
            Object obj2 = obj;
            if (!this.ac.equals(str)) {
                this.ac = str;
                this.T = null;
                this.y = 0;
                aw.a().b();
            }
            if (a(this.y) && br.a(this.x)) {
                this.x.a(2);
                return this.x;
            }
            this.q.f();
            if (!z) {
                k();
                this.z = br.b();
            }
            try {
                d();
                e();
            } catch (Throwable th) {
            }
            String b = b(false);
            if (TextUtils.isEmpty(b)) {
                if (bp.p()) {
                    if (!this.f) {
                        g();
                    }
                    for (int i = 4; i > 0 && this.e != 0; i--) {
                        SystemClock.sleep(500);
                    }
                    if (this.e == 0) {
                        this.x = this.d.d();
                        if (this.x != null) {
                            return this.x;
                        }
                    }
                }
                amapLoc = new AmapLoc();
                amapLoc.b(this.j);
                amapLoc.b(c.toString());
                return amapLoc;
            }
            Object obj3;
            boolean m;
            Object obj4;
            Object obj5;
            boolean b2;
            String str2;
            AmapLoc amapLoc2;
            AmapLoc amapLoc3;
            String str3 = "";
            StringBuilder c = c(false);
            ba baVar = null;
            if (!this.B) {
                baVar = this.q.b();
            }
            if (!(baVar == null && this.U == null)) {
                if (this.U == null || !this.U.a(baVar)) {
                    obj3 = 1;
                    m = m();
                    if (this.x != null) {
                        obj4 = null;
                    } else {
                        obj5 = (this.x.h() > 299.0f || this.r.size() <= 5) ? null : 1;
                        obj4 = obj5;
                    }
                    if (this.x != null && this.T != null && obj4 == null && r1 == null) {
                        b2 = bf.a().b(this.T, c);
                        if (!b2) {
                            if (this.S != 0) {
                                if ((br.b() - this.S < 3000 ? 1 : null) == null) {
                                }
                            }
                        }
                        if (this.q.a(this.B)) {
                            this.q.h();
                        }
                        if (br.a(this.x)) {
                            this.x.f("mem");
                            this.x.a(2);
                            return this.x;
                        }
                    }
                    b2 = false;
                    if (b2) {
                        this.S = br.b();
                    } else {
                        this.S = 0;
                    }
                    if (this.Q != null && !b.equals(this.Q)) {
                        if ((br.a() - this.R < 3000 ? 1 : null) != null) {
                            str2 = this.Q;
                        } else {
                            this.R = br.a();
                            this.Q = b;
                            str2 = b;
                        }
                    } else if (this.Q == null) {
                        this.R = br.a();
                        str2 = b;
                    } else {
                        this.R = br.a();
                        this.Q = b;
                        str2 = b;
                    }
                    amapLoc2 = null;
                    if (obj2 == null) {
                        str = str2;
                    } else {
                        str = str2 + "#reversegeo";
                    }
                    if (obj4 == null && !m) {
                        amapLoc2 = bf.a().a(str, c);
                    }
                    if ((m && !br.a(amapLoc2)) || obj4 != null) {
                        this.x = a(f(), false, false);
                        if (br.a(this.x)) {
                            this.x.f("new");
                            this.T = c.toString();
                            this.U = baVar;
                            this.y = br.b();
                            H();
                        }
                    } else if (m) {
                        this.x = a(f(), false, false);
                        if (br.a(this.x)) {
                            this.T = c.toString();
                            this.U = baVar;
                            this.y = br.b();
                            H();
                        }
                    } else {
                        amapLoc2.a(4);
                        this.x = amapLoc2;
                        H();
                    }
                    bf.a().a(str, c, this.x, this.n, true);
                    bi.a().a(this.n, str2, this.x);
                    if (!br.a(this.x)) {
                        amapLoc = a(str2, c.toString());
                        if (br.a(amapLoc)) {
                            this.T = c.toString();
                            amapLoc3 = this.x;
                            this.x = amapLoc;
                            this.x.a(8);
                            this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                        }
                    }
                    c.delete(0, c.length());
                    return this.x;
                }
            }
            obj3 = null;
            m = m();
            if (this.x != null) {
                if (this.x.h() > 299.0f) {
                }
                obj4 = obj5;
            } else {
                obj4 = null;
            }
            if (this.x != null) {
                b2 = bf.a().b(this.T, c);
                if (b2) {
                    if (this.S != 0) {
                        if (br.b() - this.S < 3000) {
                        }
                        if ((br.b() - this.S < 3000 ? 1 : null) == null) {
                        }
                    }
                    if (b2) {
                        this.S = br.b();
                    } else {
                        this.S = 0;
                    }
                    if (this.Q != null) {
                        if (br.a() - this.R < 3000) {
                        }
                        if ((br.a() - this.R < 3000 ? 1 : null) != null) {
                            this.R = br.a();
                            this.Q = b;
                            str2 = b;
                        } else {
                            str2 = this.Q;
                        }
                        amapLoc2 = null;
                        if (obj2 == null) {
                            str = str2 + "#reversegeo";
                        } else {
                            str = str2;
                        }
                        amapLoc2 = bf.a().a(str, c);
                        if (!m) {
                            this.x = a(f(), false, false);
                            if (br.a(this.x)) {
                                this.x.f("new");
                                this.T = c.toString();
                                this.U = baVar;
                                this.y = br.b();
                                H();
                            }
                            bf.a().a(str, c, this.x, this.n, true);
                            bi.a().a(this.n, str2, this.x);
                            if (br.a(this.x)) {
                                amapLoc = a(str2, c.toString());
                                if (br.a(amapLoc)) {
                                    this.T = c.toString();
                                    amapLoc3 = this.x;
                                    this.x = amapLoc;
                                    this.x.a(8);
                                    this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                                }
                            }
                            c.delete(0, c.length());
                            return this.x;
                        }
                        this.x = a(f(), false, false);
                        if (br.a(this.x)) {
                            this.x.f("new");
                            this.T = c.toString();
                            this.U = baVar;
                            this.y = br.b();
                            H();
                        }
                        bf.a().a(str, c, this.x, this.n, true);
                        bi.a().a(this.n, str2, this.x);
                        if (br.a(this.x)) {
                            amapLoc = a(str2, c.toString());
                            if (br.a(amapLoc)) {
                                this.T = c.toString();
                                amapLoc3 = this.x;
                                this.x = amapLoc;
                                this.x.a(8);
                                this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                            }
                        }
                        c.delete(0, c.length());
                        return this.x;
                    }
                    if (this.Q == null) {
                        this.R = br.a();
                        this.Q = b;
                        str2 = b;
                    } else {
                        this.R = br.a();
                        str2 = b;
                    }
                    amapLoc2 = null;
                    if (obj2 == null) {
                        str = str2;
                    } else {
                        str = str2 + "#reversegeo";
                    }
                    amapLoc2 = bf.a().a(str, c);
                    if (m) {
                        this.x = a(f(), false, false);
                        if (br.a(this.x)) {
                            this.x.f("new");
                            this.T = c.toString();
                            this.U = baVar;
                            this.y = br.b();
                            H();
                        }
                        bf.a().a(str, c, this.x, this.n, true);
                        bi.a().a(this.n, str2, this.x);
                        if (br.a(this.x)) {
                            amapLoc = a(str2, c.toString());
                            if (br.a(amapLoc)) {
                                this.T = c.toString();
                                amapLoc3 = this.x;
                                this.x = amapLoc;
                                this.x.a(8);
                                this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                            }
                        }
                        c.delete(0, c.length());
                        return this.x;
                    }
                    this.x = a(f(), false, false);
                    if (br.a(this.x)) {
                        this.x.f("new");
                        this.T = c.toString();
                        this.U = baVar;
                        this.y = br.b();
                        H();
                    }
                    bf.a().a(str, c, this.x, this.n, true);
                    bi.a().a(this.n, str2, this.x);
                    if (br.a(this.x)) {
                        amapLoc = a(str2, c.toString());
                        if (br.a(amapLoc)) {
                            this.T = c.toString();
                            amapLoc3 = this.x;
                            this.x = amapLoc;
                            this.x.a(8);
                            this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                        }
                    }
                    c.delete(0, c.length());
                    return this.x;
                }
                if (this.q.a(this.B)) {
                    this.q.h();
                }
                if (br.a(this.x)) {
                    this.x.f("mem");
                    this.x.a(2);
                    return this.x;
                }
                if (b2) {
                    this.S = 0;
                } else {
                    this.S = br.b();
                }
                if (this.Q != null) {
                    if (br.a() - this.R < 3000) {
                    }
                    if ((br.a() - this.R < 3000 ? 1 : null) != null) {
                        str2 = this.Q;
                    } else {
                        this.R = br.a();
                        this.Q = b;
                        str2 = b;
                    }
                    amapLoc2 = null;
                    if (obj2 == null) {
                        str = str2 + "#reversegeo";
                    } else {
                        str = str2;
                    }
                    amapLoc2 = bf.a().a(str, c);
                    if (m) {
                        this.x = a(f(), false, false);
                        if (br.a(this.x)) {
                            this.x.f("new");
                            this.T = c.toString();
                            this.U = baVar;
                            this.y = br.b();
                            H();
                        }
                        bf.a().a(str, c, this.x, this.n, true);
                        bi.a().a(this.n, str2, this.x);
                        if (br.a(this.x)) {
                            amapLoc = a(str2, c.toString());
                            if (br.a(amapLoc)) {
                                this.T = c.toString();
                                amapLoc3 = this.x;
                                this.x = amapLoc;
                                this.x.a(8);
                                this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                            }
                        }
                        c.delete(0, c.length());
                        return this.x;
                    }
                    this.x = a(f(), false, false);
                    if (br.a(this.x)) {
                        this.x.f("new");
                        this.T = c.toString();
                        this.U = baVar;
                        this.y = br.b();
                        H();
                    }
                    bf.a().a(str, c, this.x, this.n, true);
                    bi.a().a(this.n, str2, this.x);
                    if (br.a(this.x)) {
                        amapLoc = a(str2, c.toString());
                        if (br.a(amapLoc)) {
                            this.T = c.toString();
                            amapLoc3 = this.x;
                            this.x = amapLoc;
                            this.x.a(8);
                            this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                        }
                    }
                    c.delete(0, c.length());
                    return this.x;
                }
                if (this.Q == null) {
                    this.R = br.a();
                    str2 = b;
                } else {
                    this.R = br.a();
                    this.Q = b;
                    str2 = b;
                }
                amapLoc2 = null;
                if (obj2 == null) {
                    str = str2;
                } else {
                    str = str2 + "#reversegeo";
                }
                amapLoc2 = bf.a().a(str, c);
                if (m) {
                    this.x = a(f(), false, false);
                    if (br.a(this.x)) {
                        this.x.f("new");
                        this.T = c.toString();
                        this.U = baVar;
                        this.y = br.b();
                        H();
                    }
                    bf.a().a(str, c, this.x, this.n, true);
                    bi.a().a(this.n, str2, this.x);
                    if (br.a(this.x)) {
                        amapLoc = a(str2, c.toString());
                        if (br.a(amapLoc)) {
                            this.T = c.toString();
                            amapLoc3 = this.x;
                            this.x = amapLoc;
                            this.x.a(8);
                            this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                        }
                    }
                    c.delete(0, c.length());
                    return this.x;
                }
                this.x = a(f(), false, false);
                if (br.a(this.x)) {
                    this.x.f("new");
                    this.T = c.toString();
                    this.U = baVar;
                    this.y = br.b();
                    H();
                }
                bf.a().a(str, c, this.x, this.n, true);
                bi.a().a(this.n, str2, this.x);
                if (br.a(this.x)) {
                    amapLoc = a(str2, c.toString());
                    if (br.a(amapLoc)) {
                        this.T = c.toString();
                        amapLoc3 = this.x;
                        this.x = amapLoc;
                        this.x.a(8);
                        this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                    }
                }
                c.delete(0, c.length());
                return this.x;
            }
            b2 = false;
            if (b2) {
                this.S = br.b();
            } else {
                this.S = 0;
            }
            if (this.Q != null) {
                if (br.a() - this.R < 3000) {
                }
                if ((br.a() - this.R < 3000 ? 1 : null) != null) {
                    this.R = br.a();
                    this.Q = b;
                    str2 = b;
                } else {
                    str2 = this.Q;
                }
                amapLoc2 = null;
                if (obj2 == null) {
                    str = str2 + "#reversegeo";
                } else {
                    str = str2;
                }
                amapLoc2 = bf.a().a(str, c);
                if (m) {
                    this.x = a(f(), false, false);
                    if (br.a(this.x)) {
                        this.x.f("new");
                        this.T = c.toString();
                        this.U = baVar;
                        this.y = br.b();
                        H();
                    }
                    bf.a().a(str, c, this.x, this.n, true);
                    bi.a().a(this.n, str2, this.x);
                    if (br.a(this.x)) {
                        amapLoc = a(str2, c.toString());
                        if (br.a(amapLoc)) {
                            this.T = c.toString();
                            amapLoc3 = this.x;
                            this.x = amapLoc;
                            this.x.a(8);
                            this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                        }
                    }
                    c.delete(0, c.length());
                    return this.x;
                }
                this.x = a(f(), false, false);
                if (br.a(this.x)) {
                    this.x.f("new");
                    this.T = c.toString();
                    this.U = baVar;
                    this.y = br.b();
                    H();
                }
                bf.a().a(str, c, this.x, this.n, true);
                bi.a().a(this.n, str2, this.x);
                if (br.a(this.x)) {
                    amapLoc = a(str2, c.toString());
                    if (br.a(amapLoc)) {
                        this.T = c.toString();
                        amapLoc3 = this.x;
                        this.x = amapLoc;
                        this.x.a(8);
                        this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                    }
                }
                c.delete(0, c.length());
                return this.x;
            }
            if (this.Q == null) {
                this.R = br.a();
                this.Q = b;
                str2 = b;
            } else {
                this.R = br.a();
                str2 = b;
            }
            amapLoc2 = null;
            if (obj2 == null) {
                str = str2;
            } else {
                str = str2 + "#reversegeo";
            }
            amapLoc2 = bf.a().a(str, c);
            if (m) {
                this.x = a(f(), false, false);
                if (br.a(this.x)) {
                    this.x.f("new");
                    this.T = c.toString();
                    this.U = baVar;
                    this.y = br.b();
                    H();
                }
                bf.a().a(str, c, this.x, this.n, true);
                bi.a().a(this.n, str2, this.x);
                if (br.a(this.x)) {
                    amapLoc = a(str2, c.toString());
                    if (br.a(amapLoc)) {
                        this.T = c.toString();
                        amapLoc3 = this.x;
                        this.x = amapLoc;
                        this.x.a(8);
                        this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                    }
                }
                c.delete(0, c.length());
                return this.x;
            }
            this.x = a(f(), false, false);
            if (br.a(this.x)) {
                this.x.f("new");
                this.T = c.toString();
                this.U = baVar;
                this.y = br.b();
                H();
            }
            bf.a().a(str, c, this.x, this.n, true);
            bi.a().a(this.n, str2, this.x);
            if (br.a(this.x)) {
                amapLoc = a(str2, c.toString());
                if (br.a(amapLoc)) {
                    this.T = c.toString();
                    amapLoc3 = this.x;
                    this.x = amapLoc;
                    this.x.a(8);
                    this.x.b("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + amapLoc3.d());
                }
            }
            c.delete(0, c.length());
            return this.x;
        }
        amapLoc = new AmapLoc();
        amapLoc.b(1);
        amapLoc.b(c.toString());
        return amapLoc;
    }

    public void a() {
        Object obj = 1;
        if (bp.a()) {
            if (br.b() - bp.c() < bp.b()) {
                obj = null;
            }
            if (obj == null && this.x != null) {
                if (this.x.b() == 2 || this.x.b() == 4) {
                    try {
                        b(false);
                        c(true);
                        a(f(), false, true);
                    } catch (Throwable e) {
                        br.a(e);
                    }
                }
            }
        }
    }

    public int a(boolean z, int i) {
        if (z) {
            c(i);
        } else {
            y();
        }
        return !q() ? -1 : this.M.g();
    }

    public AmapLoc a(AmapLoc amapLoc, String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return aw.a().a(amapLoc);
        }
        if (strArr[0].equals("shake")) {
            return aw.a().a(amapLoc);
        }
        if (strArr[0].equals("fusion")) {
            return aw.a().b(amapLoc);
        }
        return amapLoc;
    }

    public synchronized void b() {
        this.i = false;
        c.o = false;
        u();
        this.M = null;
        this.N = null;
        this.T = null;
        H();
        if (this.d != null) {
            this.d.a();
            this.d = null;
            this.f = false;
            this.e = -1;
        }
        y();
        try {
            bg.a().a(this.n, 1);
        } catch (Exception e) {
        }
        aw.a().b();
        br.i();
        try {
            if (this.n != null) {
                this.n.unregisterReceiver(this.u);
            }
            this.u = null;
        } catch (Exception e2) {
            this.u = null;
        } catch (Throwable th) {
            this.u = null;
        }
        if (this.q != null) {
            this.q.i();
        }
        bf.a().c();
        bi.a().c();
        az.a();
        J();
        this.y = 0;
        this.R = 0;
        n();
        this.x = null;
        this.n = null;
    }

    public String c() {
        return "2.2.0";
    }

    private void i() {
        try {
            this.p = new bc(this.n, (WifiManager) br.a(this.n, "wifi"), this.w);
            this.o = (ConnectivityManager) br.a(this.n, "connectivity");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter.addAction("android.location.GPS_FIX_CHANGE");
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.n.registerReceiver(this.u, intentFilter);
            o();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void j() {
        this.q = new bb(this.n, this.w);
        this.q.h();
    }

    private boolean a(long j) {
        if (br.b() - j >= 800) {
            return false;
        }
        boolean z;
        long j2 = 0;
        if (br.a(this.x)) {
            j2 = br.a() - this.x.i();
        }
        if (j2 <= 10000) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return true;
        }
        return false;
    }

    public synchronized String b(boolean z) {
        if (this.B) {
            this.q.g();
        } else {
            this.q.j();
        }
        String str = "";
        String str2 = "";
        String str3 = "network";
        if (p()) {
            this.v = this.p.b();
        } else {
            n();
        }
        str2 = "";
        int c = this.q.c();
        ArrayList a = this.q.a();
        List list = this.r;
        if (a == null || a.isEmpty()) {
            if (list != null) {
                if (!list.isEmpty()) {
                }
            }
            c.append("\u2297 lstCgi & \u2297 wifis");
            this.j = 12;
            return str;
        }
        ba baVar;
        StringBuilder stringBuilder;
        switch (c) {
            case 1:
                if (!a.isEmpty()) {
                    baVar = (ba) a.get(0);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(baVar.a).append("#");
                    stringBuilder.append(baVar.b).append("#");
                    stringBuilder.append(baVar.c).append("#");
                    stringBuilder.append(baVar.d).append("#");
                    stringBuilder.append(str3).append("#");
                    if (list.isEmpty() && !a(this.v)) {
                        str = "cgi";
                    } else {
                        str = "cgiwifi";
                    }
                    stringBuilder.append(str);
                    str = stringBuilder.toString();
                    break;
                }
                break;
            case 2:
                if (!a.isEmpty()) {
                    baVar = (ba) a.get(0);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(baVar.a).append("#");
                    stringBuilder.append(baVar.b).append("#");
                    stringBuilder.append(baVar.g).append("#");
                    stringBuilder.append(baVar.h).append("#");
                    stringBuilder.append(baVar.i).append("#");
                    stringBuilder.append(str3).append("#");
                    if (list.isEmpty() && !a(this.v)) {
                        str = "cgi";
                    } else {
                        str = "cgiwifi";
                    }
                    stringBuilder.append(str);
                    str = stringBuilder.toString();
                    break;
                }
                break;
            case 9:
                Object obj = (list.isEmpty() && !a(this.v)) ? null : 1;
                if (!z) {
                    if (a(this.v) && list.isEmpty()) {
                        this.j = 2;
                        c.append("\u2297 around wifi(s) & has access wifi");
                        obj = null;
                    } else if (list.size() == 1) {
                        this.j = 2;
                        if (a(this.v)) {
                            if (this.v.getBSSID().equals(((ScanResult) list.get(0)).BSSID)) {
                                c.append("same access wifi & around wifi 1");
                                obj = null;
                            }
                        } else {
                            c.append("\u2297 access wifi & around wifi 1");
                            obj = null;
                        }
                    }
                }
                str = String.format(Locale.US, "#%s#", new Object[]{str3});
                if (obj == null) {
                    if (str3.equals("network")) {
                        str = "";
                        this.j = 2;
                        c.append("is network & no wifi");
                        break;
                    }
                }
                str = str + "wifi";
                break;
            break;
            default:
                this.j = 11;
                c.append("get cgi failure");
                break;
        }
        if (!TextUtils.isEmpty(str)) {
            if (!str.startsWith("#")) {
                str = "#" + str;
            }
            str = br.j() + str;
        }
        return str;
    }

    private boolean a(WifiInfo wifiInfo) {
        if (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getBSSID())) {
            return false;
        }
        if (wifiInfo.getSSID() == null) {
            return false;
        }
        if (wifiInfo.getBSSID().equals("00:00:00:00:00:00")) {
            return false;
        }
        if (wifiInfo.getBSSID().contains(" :")) {
            return false;
        }
        if (TextUtils.isEmpty(wifiInfo.getSSID())) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.StringBuilder c(boolean r14) {
        /*
        r13 = this;
        r3 = 1;
        r2 = 0;
        monitor-enter(r13);
        r0 = r13.q;	 Catch:{ all -> 0x003d }
        r1 = r13.B;	 Catch:{ all -> 0x003d }
        if (r1 != 0) goto L_0x0039;
    L_0x0009:
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003d }
        r1 = 700; // 0x2bc float:9.81E-43 double:3.46E-321;
        r5.<init>(r1);	 Catch:{ all -> 0x003d }
        r1 = r0.c();	 Catch:{ all -> 0x003d }
        r4 = r0.a();	 Catch:{ all -> 0x003d }
        switch(r1) {
            case 1: goto L_0x0040;
            default: goto L_0x001b;
        };	 Catch:{ all -> 0x003d }
    L_0x001b:
        if (r14 == 0) goto L_0x0081;
    L_0x001d:
        r0 = r13.G;	 Catch:{ all -> 0x003d }
        r1 = "00:00:00:00:00:00";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x003d }
        if (r0 != 0) goto L_0x0089;
    L_0x0028:
        r0 = r13.p();	 Catch:{ all -> 0x003d }
        if (r0 != 0) goto L_0x00a6;
    L_0x002e:
        r13.n();	 Catch:{ all -> 0x003d }
    L_0x0031:
        r0 = r5.length();	 Catch:{ all -> 0x003d }
        if (r0 > 0) goto L_0x010c;
    L_0x0037:
        monitor-exit(r13);
        return r5;
    L_0x0039:
        r0.g();	 Catch:{ all -> 0x003d }
        goto L_0x0009;
    L_0x003d:
        r0 = move-exception;
        monitor-exit(r13);
        throw r0;
    L_0x0040:
        r1 = r3;
    L_0x0041:
        r0 = r4.size();	 Catch:{ all -> 0x003d }
        if (r1 >= r0) goto L_0x001b;
    L_0x0047:
        r0 = "#";
        r6 = r5.append(r0);	 Catch:{ all -> 0x003d }
        r0 = r4.get(r1);	 Catch:{ all -> 0x003d }
        r0 = (com.loc.ba) r0;	 Catch:{ all -> 0x003d }
        r0 = r0.b;	 Catch:{ all -> 0x003d }
        r6.append(r0);	 Catch:{ all -> 0x003d }
        r0 = "|";
        r6 = r5.append(r0);	 Catch:{ all -> 0x003d }
        r0 = r4.get(r1);	 Catch:{ all -> 0x003d }
        r0 = (com.loc.ba) r0;	 Catch:{ all -> 0x003d }
        r0 = r0.c;	 Catch:{ all -> 0x003d }
        r6.append(r0);	 Catch:{ all -> 0x003d }
        r0 = "|";
        r6 = r5.append(r0);	 Catch:{ all -> 0x003d }
        r0 = r4.get(r1);	 Catch:{ all -> 0x003d }
        r0 = (com.loc.ba) r0;	 Catch:{ all -> 0x003d }
        r0 = r0.d;	 Catch:{ all -> 0x003d }
        r6.append(r0);	 Catch:{ all -> 0x003d }
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0041;
    L_0x0081:
        r0 = r13.G;	 Catch:{ all -> 0x003d }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x003d }
        if (r0 == 0) goto L_0x001d;
    L_0x0089:
        r0 = r13.v;	 Catch:{ all -> 0x003d }
        if (r0 == 0) goto L_0x0028;
    L_0x008d:
        r0 = r13.v;	 Catch:{ all -> 0x003d }
        r0 = r0.getMacAddress();	 Catch:{ all -> 0x003d }
        r13.G = r0;	 Catch:{ all -> 0x003d }
        r13.v();	 Catch:{ all -> 0x003d }
        r0 = r13.G;	 Catch:{ all -> 0x003d }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x003d }
        if (r0 == 0) goto L_0x0028;
    L_0x00a0:
        r0 = "00:00:00:00:00:00";
        r13.G = r0;	 Catch:{ all -> 0x003d }
        goto L_0x0028;
    L_0x00a6:
        r0 = "";
        r1 = r13.v;	 Catch:{ all -> 0x003d }
        r1 = r13.a(r1);	 Catch:{ all -> 0x003d }
        if (r1 != 0) goto L_0x00d5;
    L_0x00b1:
        r1 = r0;
    L_0x00b2:
        r6 = r13.r;	 Catch:{ all -> 0x003d }
        r7 = r6.size();	 Catch:{ all -> 0x003d }
        r4 = r2;
    L_0x00b9:
        if (r4 < r7) goto L_0x00dd;
    L_0x00bb:
        if (r2 != 0) goto L_0x0031;
    L_0x00bd:
        r0 = android.text.TextUtils.isEmpty(r1);	 Catch:{ all -> 0x003d }
        if (r0 != 0) goto L_0x0031;
    L_0x00c3:
        r0 = "#";
        r0 = r5.append(r0);	 Catch:{ all -> 0x003d }
        r0.append(r1);	 Catch:{ all -> 0x003d }
        r0 = ",access";
        r5.append(r0);	 Catch:{ all -> 0x003d }
        goto L_0x0031;
    L_0x00d5:
        r0 = r13.v;	 Catch:{ all -> 0x003d }
        r0 = r0.getBSSID();	 Catch:{ all -> 0x003d }
        r1 = r0;
        goto L_0x00b2;
    L_0x00dd:
        r0 = r6.get(r4);	 Catch:{ all -> 0x003d }
        r0 = (android.net.wifi.ScanResult) r0;	 Catch:{ all -> 0x003d }
        r8 = r0.BSSID;	 Catch:{ all -> 0x003d }
        r0 = "nb";
        r9 = r1.equals(r8);	 Catch:{ all -> 0x003d }
        if (r9 != 0) goto L_0x0107;
    L_0x00ee:
        r9 = java.util.Locale.US;	 Catch:{ all -> 0x003d }
        r10 = "#%s,%s";
        r11 = 2;
        r11 = new java.lang.Object[r11];	 Catch:{ all -> 0x003d }
        r12 = 0;
        r11[r12] = r8;	 Catch:{ all -> 0x003d }
        r8 = 1;
        r11[r8] = r0;	 Catch:{ all -> 0x003d }
        r0 = java.lang.String.format(r9, r10, r11);	 Catch:{ all -> 0x003d }
        r5.append(r0);	 Catch:{ all -> 0x003d }
        r0 = r4 + 1;
        r4 = r0;
        goto L_0x00b9;
    L_0x0107:
        r0 = "access";
        r2 = r3;
        goto L_0x00ee;
    L_0x010c:
        r0 = 0;
        r5.deleteCharAt(r0);	 Catch:{ all -> 0x003d }
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.as.c(boolean):java.lang.StringBuilder");
    }

    private StringBuilder a(Object obj) {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = "0";
        String str3 = "0";
        String str4 = "0";
        String str5 = "0";
        String str6 = "0";
        String str7 = c.j;
        c.c = "888888888888888";
        c.d = "888888888888888";
        c.e = "";
        int a = br.a(-32768, 32767);
        String str8 = "";
        String str9 = "";
        String str10 = "";
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        CharSequence stringBuilder4 = new StringBuilder();
        bb bbVar = this.q;
        int c = bbVar.c();
        TelephonyManager e = bbVar.e();
        ArrayList a2 = bbVar.a();
        if (c != 2) {
            str = str2;
        } else {
            str = "1";
        }
        if (e != null) {
            if (TextUtils.isEmpty(c.c)) {
                c.c = "888888888888888";
                try {
                    c.c = n.l(this.n);
                } catch (Exception e2) {
                }
            } else if ("888888888888888".equals(c.c)) {
                c.c = "888888888888888";
                try {
                    c.c = n.l(this.n);
                } catch (Exception e3) {
                }
            }
            if (TextUtils.isEmpty(c.c)) {
                c.c = "888888888888888";
            }
            if (TextUtils.isEmpty(c.d)) {
                c.d = "888888888888888";
                try {
                    c.d = e.getSubscriberId();
                } catch (Exception e4) {
                }
            } else if ("888888888888888".equals(c.d)) {
                c.d = "888888888888888";
                try {
                    c.d = e.getSubscriberId();
                } catch (Exception e5) {
                }
            }
            if (TextUtils.isEmpty(c.d)) {
                c.d = "888888888888888";
            }
        }
        NetworkInfo networkInfo = null;
        try {
            networkInfo = this.o.getActiveNetworkInfo();
        } catch (Exception e6) {
        }
        if (br.a(networkInfo) == -1) {
            this.v = null;
        } else {
            str9 = br.b(e);
            if (p()) {
                if (a(this.v)) {
                    str2 = "2";
                    if (p()) {
                        n();
                        str8 = str9;
                        str9 = str2;
                    } else {
                        str8 = str9;
                        str9 = str2;
                    }
                }
            }
            str2 = "1";
            if (p()) {
                n();
                str8 = str9;
                str9 = str2;
            } else {
                str8 = str9;
                str9 = str2;
            }
        }
        B();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"");
        stringBuilder.append("GBK").append("\"?>");
        stringBuilder.append("<Cell_Req ver=\"3.0\"><HDR version=\"3.0\" cdma=\"");
        stringBuilder.append(str);
        stringBuilder.append("\" gtype=\"").append(str3);
        if (str3.equals("1")) {
            stringBuilder.append("\" gmock=\"").append(!this.C ? "0" : "1");
        }
        stringBuilder.append("\" glong=\"").append(str4);
        stringBuilder.append("\" glat=\"").append(str5);
        stringBuilder.append("\" precision=\"").append(str6);
        stringBuilder.append("\"><src>").append(c.f);
        stringBuilder.append("</src><license>").append(c.g);
        stringBuilder.append("</license><key>").append(str7);
        stringBuilder.append("</key><clientid>").append(c.i);
        stringBuilder.append("</clientid><imei>").append(c.c);
        stringBuilder.append("</imei><imsi>").append(c.d);
        stringBuilder.append("</imsi><reqid>").append(a);
        stringBuilder.append("</reqid><smac>").append(this.G);
        stringBuilder.append("</smac><sdkv>").append(c());
        stringBuilder.append("</sdkv><corv>").append(C());
        stringBuilder.append("</corv><poiid>").append(this.H);
        stringBuilder.append("</poiid></HDR><DRR phnum=\"").append(c.e);
        stringBuilder.append("\" nettype=\"").append(str8);
        stringBuilder.append("\" inftype=\"").append(str9).append("\">");
        if (!a2.isEmpty()) {
            StringBuilder stringBuilder5 = new StringBuilder();
            ba baVar;
            switch (c) {
                case 1:
                    K();
                    baVar = (ba) a2.get(0);
                    stringBuilder5.delete(0, stringBuilder5.length());
                    stringBuilder5.append("<mcc>").append(baVar.a).append("</mcc>");
                    stringBuilder5.append("<mnc>").append(baVar.b).append("</mnc>");
                    stringBuilder5.append("<lac>").append(baVar.c).append("</lac>");
                    stringBuilder5.append("<cellid>").append(baVar.d);
                    stringBuilder5.append("</cellid>");
                    stringBuilder5.append("<signal>").append(baVar.j);
                    stringBuilder5.append("</signal>");
                    String stringBuilder6 = stringBuilder5.toString();
                    for (int i = 1; i < a2.size(); i++) {
                        baVar = (ba) a2.get(i);
                        stringBuilder2.append(baVar.c).append(",");
                        stringBuilder2.append(baVar.d).append(",");
                        stringBuilder2.append(baVar.j);
                        if (i < a2.size() - 1) {
                            stringBuilder2.append("*");
                        }
                    }
                    str2 = stringBuilder6;
                    break;
                case 2:
                    baVar = (ba) a2.get(0);
                    stringBuilder5.delete(0, stringBuilder5.length());
                    stringBuilder5.append("<mcc>").append(baVar.a).append("</mcc>");
                    stringBuilder5.append("<sid>").append(baVar.g).append("</sid>");
                    stringBuilder5.append("<nid>").append(baVar.h).append("</nid>");
                    stringBuilder5.append("<bid>").append(baVar.i).append("</bid>");
                    if (baVar.f > 0 && baVar.e > 0) {
                        this.aa = baVar.f;
                        this.ab = baVar.e;
                        stringBuilder5.append("<lon>").append(baVar.f).append("</lon>");
                        stringBuilder5.append("<lat>").append(baVar.e).append("</lat>");
                    } else {
                        K();
                    }
                    stringBuilder5.append("<signal>").append(baVar.j).append("</signal>");
                    str2 = stringBuilder5.toString();
                    break;
                default:
                    K();
                    str2 = str10;
                    break;
            }
            stringBuilder5.delete(0, stringBuilder5.length());
            str10 = str2;
        }
        if (p()) {
            int i2;
            if (a(this.v)) {
                stringBuilder4.append(this.v.getBSSID()).append(",");
                int rssi = this.v.getRssi();
                if (rssi < -128) {
                    rssi = 0;
                } else if (rssi > 127) {
                    rssi = 0;
                }
                stringBuilder4.append(rssi).append(",");
                str2 = this.v.getSSID();
                i2 = 32;
                try {
                    i2 = this.v.getSSID().getBytes("UTF-8").length;
                } catch (Exception e7) {
                }
                if (i2 >= 32) {
                    str2 = "unkwn";
                }
                stringBuilder4.append(str2.replace("*", "."));
            }
            List list = this.r;
            int min = Math.min(list.size(), 15);
            for (i2 = 0; i2 < min; i2++) {
                ScanResult scanResult = (ScanResult) list.get(i2);
                stringBuilder3.append(scanResult.BSSID).append(",");
                stringBuilder3.append(scanResult.level).append(",");
                stringBuilder3.append(scanResult.SSID).append("*");
            }
        } else {
            n();
        }
        stringBuilder.append(str10);
        stringBuilder.append(String.format(Locale.US, "<nb>%s</nb>", new Object[]{stringBuilder2}));
        if (stringBuilder3.length() != 0) {
            stringBuilder3.deleteCharAt(stringBuilder3.length() - 1);
            stringBuilder.append("<macs>");
            stringBuilder.append(String.format(Locale.US, "<![CDATA[%s]]>", new Object[]{stringBuilder3}));
            stringBuilder.append("</macs>");
            stringBuilder.append("<macsage>").append(br.b() - this.E);
            stringBuilder.append("</macsage>");
        } else {
            stringBuilder3.append(stringBuilder4);
            stringBuilder.append("<macs>");
            stringBuilder.append(String.format(Locale.US, "<![CDATA[%s]]>", new Object[]{stringBuilder4}));
            stringBuilder.append("</macs>");
        }
        stringBuilder.append("<mmac>");
        stringBuilder.append(String.format(Locale.US, "<![CDATA[%s]]>", new Object[]{stringBuilder4}));
        stringBuilder.append("</mmac>").append("</DRR></Cell_Req>");
        a(stringBuilder);
        Object obj2 = null;
        if (br.a(this.w, "reversegeo")) {
            try {
                if (this.w.getString("reversegeo").equals("1")) {
                    obj2 = 1;
                }
            } catch (Exception e8) {
            }
        }
        if (obj2 != null) {
            this.I.b = (short) 0;
        } else {
            this.I.b = (short) 2;
        }
        if (br.a(this.w, "multi")) {
            try {
                if (this.w.getString("multi").equals("1")) {
                    this.I.b = (short) 1;
                }
            } catch (Exception e9) {
            }
        }
        this.I.c = c.f;
        this.I.d = c.g;
        this.I.f = br.f();
        this.I.g = "android" + br.g();
        if (TextUtils.isEmpty(c.l)) {
            c.l = br.b(this.n);
        }
        this.I.h = c.l;
        this.I.i = str;
        this.I.j = str3;
        bo boVar = this.I;
        if (this.C) {
            str2 = "1";
        } else {
            str2 = "0";
        }
        boVar.k = str2;
        this.I.l = str4;
        this.I.m = str5;
        this.I.n = str6;
        this.I.o = str7;
        this.I.p = c.c;
        this.I.q = c.d;
        this.I.s = String.valueOf(a);
        this.I.t = this.G;
        this.I.v = c();
        this.I.w = C();
        this.I.F = this.H;
        this.I.u = c.e;
        this.I.x = str8;
        this.I.y = str9;
        this.I.z = String.valueOf(c);
        this.I.A = str10;
        this.I.B = stringBuilder2.toString();
        this.I.D = stringBuilder3.toString();
        this.I.E = String.valueOf(br.b() - this.E);
        this.I.C = stringBuilder4.toString();
        stringBuilder2.delete(0, stringBuilder2.length());
        stringBuilder3.delete(0, stringBuilder3.length());
        stringBuilder4.delete(0, stringBuilder4.length());
        return stringBuilder;
    }

    private void k() {
        Object obj = 1;
        long b = br.b();
        if (l()) {
            List list = this.s;
            if ((b - this.z < 10000 ? 1 : null) == null) {
                synchronized (this.h) {
                    list.clear();
                }
            }
            o();
            if (b - this.z >= 10000) {
                obj = null;
            }
            if (obj == null) {
                for (int i = 20; i > 0 && list.isEmpty(); i--) {
                    try {
                        Thread.sleep(150);
                    } catch (Exception e) {
                    }
                }
            }
            synchronized (this.h) {
            }
            if (list.isEmpty() && this.p != null) {
                Collection a = this.p.a();
                if (a != null) {
                    list.addAll(a);
                }
            }
        }
    }

    public synchronized void d() {
        List list = this.r;
        Collection collection = this.s;
        list.clear();
        synchronized (this.h) {
            list.addAll(collection);
        }
    }

    public synchronized void e() {
        if (this.r != null) {
            if (!this.r.isEmpty()) {
                boolean z;
                if ((br.b() - this.E <= 3600000 ? 1 : null) == null) {
                    n();
                }
                boolean h = br.h();
                if (br.a(this.w, "nbssid")) {
                    try {
                        if (this.w.getString("nbssid").equals("1")) {
                            h = true;
                        } else if (this.w.getString("nbssid").equals("0")) {
                            h = false;
                        }
                        z = h;
                    } catch (Exception e) {
                        z = h;
                    }
                } else {
                    z = h;
                }
                Object hashtable = new Hashtable();
                List list = this.r;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ScanResult scanResult = (ScanResult) list.get(i);
                    if (br.a(scanResult)) {
                        if (size > 20) {
                            if (!a(scanResult.level)) {
                                continue;
                            }
                        }
                        if (TextUtils.isEmpty(scanResult.SSID)) {
                            scanResult.SSID = "unkwn";
                        } else if (z) {
                            int length;
                            scanResult.SSID = scanResult.SSID.replace("*", ".");
                            try {
                                length = scanResult.SSID.getBytes("UTF-8").length;
                            } catch (Exception e2) {
                                length = 32;
                            }
                            if (length >= 32) {
                                scanResult.SSID = String.valueOf(i);
                            }
                        } else {
                            scanResult.SSID = String.valueOf(i);
                        }
                        hashtable.put(Integer.valueOf((scanResult.level * 30) + i), scanResult);
                    }
                }
                TreeMap treeMap = new TreeMap(Collections.reverseOrder());
                treeMap.putAll(hashtable);
                list.clear();
                for (Object obj : treeMap.keySet()) {
                    list.add(treeMap.get(obj));
                }
                hashtable.clear();
                treeMap.clear();
            }
        }
    }

    private boolean a(int i) {
        int i2 = 20;
        try {
            i2 = WifiManager.calculateSignalLevel(i, 20);
        } catch (ArithmeticException e) {
        }
        if (i2 < 1) {
            return false;
        }
        return true;
    }

    public synchronized String f() {
        if (this.q.a(this.B)) {
            this.q.h();
        }
        try {
            StringBuilder a = a(null);
            if (a == null) {
                c.append("get parames is null");
                return null;
            }
            return a.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            c.append("get parames error:" + th.getMessage());
            return null;
        }
    }

    private boolean l() {
        boolean z = true;
        if (!TextUtils.isEmpty(this.H)) {
            return true;
        }
        if (!p()) {
            z = false;
        } else if (this.D != 0) {
            boolean z2;
            if (br.b() - this.D >= 3000) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (br.b() - this.E >= 1500) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    z = false;
                }
            } else {
                z = false;
            }
        }
        return z;
    }

    private AmapLoc a(String str, boolean z, boolean z2) throws Exception {
        AmapLoc amapLoc;
        if (this.n == null) {
            c.append("context is null");
            amapLoc = new AmapLoc();
            amapLoc.b(1);
            amapLoc.b(c.toString());
            return amapLoc;
        } else if (str == null || str.length() == 0) {
            amapLoc = new AmapLoc();
            amapLoc.b(3);
            amapLoc.b(c.toString());
            return amapLoc;
        } else {
            amapLoc = new AmapLoc();
            bn bnVar = new bn();
            try {
                byte[] a = this.b.a(this.n, this.w, this.I, c.a());
                if (a != null) {
                    br.a();
                    String str2 = new String(a, "UTF-8");
                    if (str2.contains("\"status\":\"0\"")) {
                        return bnVar.b(str2);
                    }
                    String a2 = bd.a(a);
                    if (a2 != null) {
                        str2 = "";
                        bl.a(this.w);
                        amapLoc = bnVar.a(a2);
                        if (br.a(amapLoc)) {
                            if (amapLoc.z() == null) {
                            }
                            if (amapLoc.a() == 0 && amapLoc.b() == 0) {
                                if ("-5".equals(amapLoc.k()) || "1".equals(amapLoc.k()) || "2".equals(amapLoc.k()) || "14".equals(amapLoc.k()) || "24".equals(amapLoc.k()) || "-1".equals(amapLoc.k())) {
                                    amapLoc.a(5);
                                } else {
                                    amapLoc.a(6);
                                }
                                amapLoc.b(amapLoc.k());
                            }
                            return amapLoc;
                        } else if (amapLoc == null) {
                            amapLoc = new AmapLoc();
                            amapLoc.b(6);
                            c.append("location is null");
                            amapLoc.b(c.toString());
                            return amapLoc;
                        } else {
                            this.P = amapLoc.l();
                            amapLoc.b(6);
                            c.append("location faile retype:" + amapLoc.k() + " rdesc:" + this.P);
                            amapLoc.b(c.toString());
                            return amapLoc;
                        }
                    }
                    amapLoc = new AmapLoc();
                    amapLoc.b(5);
                    c.append("decrypt response data error");
                    amapLoc.b(c.toString());
                    return amapLoc;
                }
                amapLoc = new AmapLoc();
                amapLoc.b(4);
                c.append("please check the network");
                amapLoc.b(c.toString());
                return amapLoc;
            } catch (Throwable th) {
                th.printStackTrace();
                amapLoc = new AmapLoc();
                amapLoc.b(4);
                c.append("please check the network");
                amapLoc.b(c.toString());
                return amapLoc;
            }
        }
    }

    private void a(StringBuilder stringBuilder) {
        int i = 0;
        if (stringBuilder != null) {
            String[] strArr = new String[]{" phnum=\"\"", " nettype=\"\"", " nettype=\"UNKWN\"", " inftype=\"\"", "<macs><![CDATA[]]></macs>", "<nb></nb>", "<mmac><![CDATA[]]></mmac>", " gtype=\"0\"", " gmock=\"0\"", " glong=\"0.0\"", " glat=\"0.0\"", " precision=\"0.0\"", " glong=\"0\"", " glat=\"0\"", " precision=\"0\"", "<smac>null</smac>", "<smac>00:00:00:00:00:00</smac>", "<imei>000000000000000</imei>", "<imsi>000000000000000</imsi>", "<mcc>000</mcc>", "<mcc>0</mcc>", "<lac>0</lac>", "<cellid>0</cellid>", "<key></key>", "<poiid></poiid>", "<poiid>null</poiid>"};
            int length = strArr.length;
            while (i < length) {
                String str = strArr[i];
                while (stringBuilder.indexOf(str) != -1) {
                    int indexOf = stringBuilder.indexOf(str);
                    stringBuilder.delete(indexOf, str.length() + indexOf);
                }
                i++;
            }
            while (stringBuilder.indexOf("*<") != -1) {
                stringBuilder.deleteCharAt(stringBuilder.indexOf("*<"));
            }
        }
    }

    private boolean m() {
        boolean z = true;
        if (this.y == 0) {
            return true;
        }
        if (br.b() - this.y <= 20000) {
            z = false;
        }
        return z;
    }

    private void n() {
        this.E = 0;
        this.r.clear();
        this.v = null;
        synchronized (this.h) {
            this.s.clear();
            this.t.clear();
        }
    }

    private void o() {
        boolean z = false;
        if (p()) {
            boolean z2;
            if (br.c() >= 18 || br.c() <= 3) {
                z2 = false;
            } else if (br.a(this.w, "wifiactivescan")) {
                try {
                    z2 = "1".equals(this.w.getString("wifiactivescan"));
                } catch (Exception e) {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (z2) {
                try {
                    z = this.p.e();
                    if (z) {
                        this.D = br.b();
                    }
                } catch (Exception e2) {
                }
            }
            if (!z) {
                try {
                    if (this.p.d()) {
                        this.D = br.b();
                    }
                } catch (Exception e3) {
                }
            }
        }
    }

    private boolean p() {
        if (this.p == null) {
            return false;
        }
        return this.p.f();
    }

    private boolean q() {
        return this.M != null;
    }

    private boolean r() {
        boolean z = false;
        try {
            if (q()) {
                z = this.M.d();
            }
        } catch (Exception e) {
        }
        return z;
    }

    private void s() {
        int i;
        if (br.a(this.w, "coll")) {
            try {
                i = !this.w.getString("coll").equals("0") ? 1 : 0;
            } catch (Exception e) {
                i = 1;
            }
        } else {
            i = 1;
        }
        if (i == 0) {
            u();
        } else if (r()) {
            br.a("col|al start");
        } else {
            try {
                this.M.b(c.n * 1000);
                z();
                t();
                this.M.a();
            } catch (Exception e2) {
            }
        }
    }

    private void t() {
        if (!q()) {
            return;
        }
        if (!q() || this.M.g() <= 0) {
            try {
                if (q()) {
                    if (!this.M.f()) {
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void u() {
        if (r()) {
            c.n = 20;
            try {
                this.M.c();
            } catch (Exception e) {
            }
            return;
        }
        br.a("col|\u2297 need stop");
    }

    private void a(SharedPreferences sharedPreferences) {
        String str = null;
        if (this.n != null && sharedPreferences != null) {
            String str2 = "smac";
            if (sharedPreferences.contains(str2)) {
                try {
                    str = o.a(sharedPreferences.getString(str2, null).getBytes("UTF-8"));
                } catch (Exception e) {
                    sharedPreferences.edit().remove(str2).commit();
                }
            }
            if (!(TextUtils.isEmpty(str) || str.equals("00:00:00:00:00:00"))) {
                this.G = str;
            }
        }
    }

    private void v() {
        Object obj = null;
        if (this.n != null && !TextUtils.isEmpty(this.G)) {
            SharedPreferences sharedPreferences = this.n.getSharedPreferences("pref", 0);
            try {
                obj = o.a(this.G.getBytes("UTF-8"));
            } catch (Exception e) {
            }
            if (!TextUtils.isEmpty(obj)) {
                sharedPreferences.edit().putString("smac", obj).commit();
            }
        }
    }

    private void w() {
        c.f = "";
        c.g = "";
        c.i = "";
    }

    private void x() {
        Object obj = null;
        List list = this.s;
        try {
            if (br.a(this.w, "wait1stwifi")) {
                obj = this.w.getString("wait1stwifi");
            }
            if (TextUtils.isEmpty(obj) || !obj.equals("1")) {
                return;
            }
        } catch (Exception e) {
        }
        synchronized (this.h) {
            list.clear();
        }
        o();
        for (int i = 20; i > 0 && list.isEmpty(); i--) {
            try {
                Thread.sleep(150);
            } catch (Exception e2) {
            }
        }
        synchronized (this.h) {
        }
        if (list.isEmpty() && this.p != null) {
            list.addAll(this.p.a());
        }
    }

    private void b(int i) {
        int i2 = 70254591;
        if (q()) {
            try {
                z();
                switch (i) {
                    case 1:
                        i2 = 674234367;
                        break;
                    case 2:
                        if (!A()) {
                            i2 = 674234367;
                            break;
                        } else {
                            i2 = 2083520511;
                            break;
                        }
                }
                this.M.a(null, a(1, i2, 1));
                this.N = this.M.e();
                if (this.N != null) {
                    byte[] a = this.N.a();
                    Object a2 = this.b.a(a, this.n, "http://cgicol.amap.com/collection/writedata?ver=v1.0_ali&", false);
                    if (q()) {
                        if (!TextUtils.isEmpty(a2) && a2.equals("true")) {
                            this.M.a(this.N, a(1, i2, 1));
                            String a3 = br.a(0, "yyyyMMdd");
                            if (a3.equals(String.valueOf(this.O[0]))) {
                                int[] iArr = this.O;
                                iArr[1] = a.length + iArr[1];
                            } else {
                                try {
                                    this.O[0] = Integer.parseInt(a3);
                                } catch (Exception e) {
                                    this.O[0] = 0;
                                    this.O[1] = 0;
                                    this.O[2] = 0;
                                }
                                this.O[1] = a.length;
                            }
                            this.O[2] = this.O[2] + 1;
                            F();
                        } else {
                            this.L++;
                            this.M.a(this.N, a(1, i2, 0));
                        }
                    }
                }
                t();
                if (q() && this.M.g() == 0) {
                    y();
                }
                if (this.L >= 3) {
                    y();
                }
            } catch (Exception e2) {
            }
        }
    }

    private void c(int i) {
        t();
        if (!(br.b() - this.A >= 45000)) {
            return;
        }
        if (!q() || this.M.g() >= 20) {
            if (this.K == null) {
                this.K = new AnonymousClass1(this, i);
            }
            if (this.J == null) {
                this.J = new Timer("T-U", false);
                this.J.schedule(this.K, 2000, 2000);
            }
        }
    }

    private void y() {
        if (this.K != null) {
            this.K.cancel();
            this.K = null;
        }
        if (this.J != null) {
            this.J.cancel();
            this.J.purge();
            this.J = null;
        }
    }

    private void z() {
        if (q()) {
            try {
                this.M.a(768);
            } catch (Exception e) {
            }
        }
    }

    private String a(int i, int i2, int i3) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("e", i);
        jSONObject.put("d", i2);
        jSONObject.put("u", i3);
        return jSONObject.toString();
    }

    private boolean A() {
        if (this.p == null || this.o == null) {
            return false;
        }
        return this.p.a(this.o);
    }

    private void B() {
        if (br.a(this.w, "poiid")) {
            try {
                String string = this.w.getString("poiid");
                if (TextUtils.isEmpty(string)) {
                    this.H = null;
                    return;
                } else if (string.length() <= 32) {
                    this.H = string;
                    return;
                } else {
                    this.H = null;
                    return;
                }
            } catch (Exception e) {
                return;
            }
        }
        this.H = null;
    }

    private String C() {
        String str = null;
        try {
            str = bw.a("version");
        } catch (Exception e) {
        }
        return str;
    }

    private void D() {
        if (this.p != null && this.n != null && this.a) {
            this.p.a(this.a);
        }
    }

    private boolean E() {
        if (this.n == null) {
            c.append("context is null");
            return false;
        } else if (TextUtils.isEmpty(c.f)) {
            c.append("src is null");
            return false;
        } else if (!TextUtils.isEmpty(c.g)) {
            return true;
        } else {
            c.append("license is null");
            return false;
        }
    }

    private void b(SharedPreferences sharedPreferences) {
        int i = 0;
        if (this.n != null) {
            SharedPreferences sharedPreferences2 = this.n.getSharedPreferences("pref", 0);
            if (sharedPreferences2 != null && sharedPreferences2.contains("coluphist")) {
                try {
                    String[] split = o.a(sharedPreferences2.getString("coluphist", null).getBytes("UTF-8")).split(",");
                    while (i < 3) {
                        this.O[i] = Integer.parseInt(split[i]);
                        i++;
                    }
                } catch (Exception e) {
                    sharedPreferences2.edit().remove("coluphist").commit();
                }
            }
        }
    }

    private void F() {
        if (this.n != null && this.O[0] != 0) {
            SharedPreferences sharedPreferences = this.n.getSharedPreferences("pref", 0);
            if (sharedPreferences != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int append : this.O) {
                    stringBuilder.append(append).append(",");
                }
                try {
                    stringBuilder.deleteCharAt(this.O.length - 1);
                    sharedPreferences.edit().putString("coluphist", o.a(stringBuilder.toString().getBytes("UTF-8")));
                } catch (Exception e) {
                }
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
    }

    public synchronized void g() {
        if (this.F >= 1) {
            if (!this.f) {
                if (this.d == null) {
                    this.d = new at(this.n.getApplicationContext());
                    this.d.a(this.m);
                }
                if (!this.f && bp.p()) {
                    try {
                        if (this.d != null) {
                            this.d.b();
                        }
                        this.f = true;
                    } catch (Throwable th) {
                        this.f = true;
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public synchronized void b(Context context) {
        try {
            if (k.a == -1) {
                k.b(this.n, c.a("2.2.0"));
                bp.a(context);
            }
        } catch (Throwable th) {
        }
    }

    private AmapLoc G() throws Exception {
        AmapLoc amapLoc;
        ba baVar = null;
        if (c.length() > 0) {
            c.delete(0, c.length());
        }
        try {
            if (!this.B) {
                this.q.f();
                this.q.d();
                baVar = this.q.b();
            }
            ArrayList arrayList = this.r;
            if (arrayList != null && arrayList.isEmpty()) {
                this.E = br.b();
                Collection a = this.p.a();
                if (a != null) {
                    arrayList.addAll(a);
                }
            }
            e();
        } catch (Throwable th) {
        }
        String b = b(false);
        if (TextUtils.isEmpty(b)) {
            amapLoc = new AmapLoc();
            amapLoc.b(this.j);
            amapLoc.b(c.toString());
        } else {
            String str;
            boolean z;
            try {
                String string;
                str = "";
                if (br.a(this.w, "reversegeo")) {
                    string = this.w.getString("reversegeo");
                    z = string.equals("1");
                } else {
                    string = str;
                    z = false;
                }
                try {
                    if (!this.ac.equals(string)) {
                        this.ac = string;
                        this.T = null;
                        this.y = 0;
                        aw.a().b();
                    }
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                z = false;
            }
            if (z) {
                str = b + "#reversegeo";
            } else {
                str = b;
            }
            StringBuilder c = c(true);
            AmapLoc a2 = bf.a().a(str, c);
            if (br.a(a2)) {
                a2.a(4);
                this.x = a2;
                H();
                return a2;
            }
            a2 = a(f(), false, true);
            if (br.a(a2)) {
                a2.f("new");
                this.T = c.toString();
                this.U = baVar;
                this.y = br.b();
                this.x = a2;
                bf.a().a(str, c, this.x, this.n, true);
                H();
                amapLoc = a2;
            } else {
                amapLoc = a(b, c.toString());
                if (!br.a(amapLoc)) {
                    return a2;
                }
                this.T = c.toString();
                amapLoc.f("file");
                amapLoc.a(8);
                amapLoc.b("\u79bb\u7ebf\u5b9a\u4f4d\u7ed3\u679c\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:" + a2.d());
                this.x = amapLoc;
            }
        }
        return amapLoc;
    }

    private void H() {
        this.V = null;
        this.W = null;
    }

    private AmapLoc a(String str, String str2) {
        int i = 0;
        if (!bp.i()) {
            return null;
        }
        if (str != null && str.equals(this.W) && this.V != null) {
            return this.V;
        }
        I();
        ArrayList b = bi.a().b();
        try {
            int i2;
            AmapLoc a;
            if (az.b()) {
                ArrayList a2 = az.a(str, false);
                if (a2 != null) {
                    int size = a2.size();
                    for (i2 = 0; i2 < size; i2++) {
                        String str3 = (String) a2.get(i2);
                        a = a(str, str2, null, str3.substring(str3.lastIndexOf(File.separator) + 1, str3.length()), 0);
                        if (br.a(a)) {
                            this.W = str;
                            this.V = a;
                            return a;
                        }
                    }
                }
            }
            i2 = b.size();
            if (i2 != 0) {
                while (i < i2) {
                    a = a(str, str2, null, ((bh) b.get(i)).a(), 0);
                    if (br.a(a)) {
                        this.W = str;
                        this.V = a;
                        return a;
                    }
                    i++;
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }

    private void I() {
        if (!bp.i()) {
            J();
        } else if (az.c[1] > 2000) {
            J();
        } else if (this.X == null || this.Y == null) {
            this.Y = new TimerTask() {
                final /* synthetic */ as a;

                {
                    this.a = r1;
                }

                public void run() {
                    if (az.c[1] <= 2000) {
                        int i;
                        Thread.currentThread().setPriority(1);
                        if (br.a(this.a.w, "fetchoffdatamobile")) {
                            try {
                                boolean equals = "1".equals(this.a.w.getString("fetchoffdatamobile"));
                            } catch (Exception e) {
                                i = 0;
                            }
                        } else {
                            i = 0;
                        }
                        if (av.a) {
                            ArrayList b = bi.a().b();
                            if (b != null) {
                                int size = b.size();
                                if (size > 0) {
                                    if (this.a.Z == null) {
                                        this.a.Z = this.a.b(true);
                                    }
                                    int i2 = 0;
                                    while (i2 < size && i2 < 20) {
                                        boolean z;
                                        bh bhVar = (bh) b.get(i2);
                                        Context j = this.a.n;
                                        String i3 = this.a.Z;
                                        String a = bhVar.a();
                                        if (i != 0) {
                                            z = false;
                                        } else {
                                            z = true;
                                        }
                                        az.a(j, i3, a, 1, 0, z, true);
                                        i2++;
                                    }
                                }
                            }
                            this.a.L();
                            try {
                                ArrayList b2 = bg.a().b(this.a.n, 1);
                                if (b2 != null && b2.size() > 0) {
                                    Iterator it = b2.iterator();
                                    while (it.hasNext()) {
                                        az.a(this.a.Z, (String) it.next(), 1, 0);
                                    }
                                }
                            } catch (Exception e2) {
                            }
                        }
                        return;
                    }
                    this.a.J();
                }
            };
            this.X = new Timer("T-O", false);
            this.X.schedule(this.Y, 0, 60000);
        }
    }

    private void J() {
        if (this.Y != null) {
            this.Y.cancel();
            this.Y = null;
        }
        if (this.X != null) {
            this.X.cancel();
            this.X.purge();
            this.X = null;
        }
    }

    private AmapLoc a(String str, String str2, double[] dArr, String str3, int i) {
        if (!br.k()) {
            return null;
        }
        double[] dArr2;
        if (TextUtils.isEmpty(str3)) {
            if (dArr == null) {
                dArr = M();
            }
            if (dArr[0] == 0.0d || dArr[1] == 0.0d) {
                return null;
            }
            dArr2 = dArr;
        } else {
            dArr2 = dArr;
        }
        br.b();
        String str4 = str3;
        String str5 = str;
        String str6 = str2;
        int i2 = i;
        AmapLoc a = az.a(dArr2, str4, str5, str6, i2, this.n, new int[]{this.ab, this.aa});
        br.b();
        return a;
    }

    private void K() {
        this.aa = 0;
        this.ab = 0;
    }

    private void L() {
        if (this.n != null && az.c[0] != 0) {
            SharedPreferences sharedPreferences = this.n.getSharedPreferences("pref", 0);
            if (sharedPreferences != null) {
                StringBuilder stringBuilder = new StringBuilder();
                String str = "activityoffdl";
                for (int append : az.c) {
                    stringBuilder.append(append).append(",");
                }
                try {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    sharedPreferences.edit().putString(str, br.c(stringBuilder.toString())).commit();
                } catch (Exception e) {
                }
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
    }

    private double[] M() {
        double[] dArr = new double[2];
        if (br.a(this.x)) {
            dArr[0] = this.x.g();
            dArr[1] = this.x.f();
        } else if (br.a(this.g)) {
            dArr[0] = this.g.g();
            dArr[1] = this.g.f();
        } else {
            dArr[0] = 0.0d;
            dArr[1] = 0.0d;
        }
        return dArr;
    }

    private void c(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            String str = "activityoffdl";
            if (sharedPreferences.contains(str)) {
                try {
                    String[] split = br.d(sharedPreferences.getString(str, null)).split(",");
                    for (int i = 0; i < 2; i++) {
                        az.c[i] = Integer.parseInt(split[i]);
                    }
                } catch (Exception e) {
                    sharedPreferences.edit().remove(str).commit();
                }
            }
        }
    }

    public void h() {
        if (this.l && !r()) {
            s();
        }
    }
}
