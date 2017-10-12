package com.loc;

import android.content.Context;
import android.os.Build.VERSION;
import com.loc.an.a;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/* compiled from: DexDownLoad */
public class aa extends Thread implements a {
    private ab a;
    private an b;
    private s c;
    private String d;
    private RandomAccessFile e;
    private String f;
    private Context g;
    private String h;
    private String i;
    private String j;
    private String k;
    private int l;
    private int m;

    public aa(Context context, ab abVar, s sVar) {
        try {
            this.g = context.getApplicationContext();
            this.c = sVar;
            if (abVar != null) {
                this.a = abVar;
                this.b = new an(new ad(this.a));
                String[] split = this.a.a().split("/");
                this.f = split[split.length - 1];
                split = this.f.split("_");
                this.h = split[0];
                this.i = abVar.c();
                this.j = split[1];
                this.l = Integer.parseInt(split[3]);
                this.m = Integer.parseInt(split[4].split("\\.")[0]);
                this.k = abVar.b();
                this.d = ae.a(context, this.f);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a() {
        try {
            start();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void run() {
        try {
            if (e()) {
                this.b.a(this);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private boolean a(x xVar, aj ajVar, String str, String str2, String str3, String str4) {
        if ("errorstatus".equals(ajVar.f())) {
            if (!new File(ae.a(this.g, this.c.a(), this.c.b())).exists()) {
                ae.a(this.g, xVar, this.c);
                af.b(this.g, this.c, ae.a(this.g, this.h, this.c.b()), ae.a(this.g), null, this.g.getClassLoader());
            }
            return true;
        } else if (!new File(this.d).exists()) {
            return false;
        } else {
            List c = xVar.c(ai.a(ae.b(str, str2), str, str2, str3), new ai());
            if (c != null && c.size() > 0) {
                return true;
            }
            try {
                ae.a(this.g, xVar, this.c, new a(ae.b(str, this.c.b()), str4, str, str2, str3).a("usedex").a(), this.d);
                af.b(this.g, this.c, ae.a(this.g, this.h, this.c.b()), ae.a(this.g), null, this.g.getClassLoader());
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

    private boolean a(String str, String str2, String str3, String str4, String str5) {
        x xVar = new x(this.g, ah.c());
        try {
            List c = xVar.c(ai.b(str3, "usedex"), new ai());
            if (c != null) {
                if (c.size() > 0 && ak.a(((aj) c.get(0)).e(), this.j) > 0) {
                    return true;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        aj a = a.a(xVar, str);
        if (a == null) {
            return false;
        }
        return a(xVar, a, str3, str4, str2, str5);
    }

    private boolean a(String str, String str2) {
        boolean z = false;
        if (this.c == null) {
            return false;
        }
        if (this.c.a().equals(str) && this.c.b().equals(str2)) {
            z = true;
        }
        return z;
    }

    private boolean d() {
        return VERSION.SDK_INT >= this.m && VERSION.SDK_INT <= this.l;
    }

    private boolean a(Context context) {
        return n.h(context) == 1;
    }

    private boolean e() {
        try {
            if (!a(this.h, this.i) || a(this.f, this.j, this.h, this.i, this.k) || !a(this.g) || !d()) {
                return false;
            }
            a(this.c.a());
            return true;
        } catch (Throwable th) {
            ae.a(th);
            th.printStackTrace();
            return false;
        }
    }

    private void a(String str) {
        x xVar = new x(this.g, ah.c());
        List c = xVar.c(ai.b(str, "copy"), new ai());
        ae.a(c);
        if (c != null && c.size() > 1) {
            int size = c.size();
            for (int i = 1; i < size; i++) {
                ae.a(this.g, xVar, ((aj) c.get(i)).a());
            }
        }
    }

    public void a(byte[] bArr, long j) {
        try {
            if (this.e == null) {
                File file = new File(this.d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.e = new RandomAccessFile(file, "rw");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
            return;
        }
        try {
            this.e.seek(j);
            this.e.write(bArr);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void a(Throwable th) {
        try {
            if (this.e != null) {
                this.e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public void c() {
        try {
            if (this.e != null) {
                this.e.close();
                String b = this.a.b();
                if (ae.a(this.d, b)) {
                    String c = this.a.c();
                    x xVar = new x(this.g, ah.c());
                    a.a(xVar, new a(this.f, b, this.h, c, this.j).a("copy").a(), ai.a(this.f, this.h, c, this.j));
                    try {
                        ae.a(this.g, xVar, this.c, new a(ae.b(this.h, this.c.b()), b, this.h, c, this.j).a("usedex").a(), this.d);
                        af.b(this.g, this.c, ae.a(this.g, this.h, this.c.b()), ae.a(this.g), null, this.g.getClassLoader());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
                try {
                    new File(this.d).delete();
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

    public void b() {
    }
}
