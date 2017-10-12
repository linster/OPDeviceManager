package com.loc;

import android.content.Context;
import android.text.TextUtils;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DynamicClassLoader */
class af extends ClassLoader {
    private static af c;
    volatile boolean a;
    private final Context b;
    private final Map<String, Class<?>> d;
    private DexFile e;
    private String f;
    private s g;

    /* compiled from: DynamicClassLoader */
    /* renamed from: com.loc.af.1 */
    static class AnonymousClass1 extends Thread {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        AnonymousClass1(Context context, String str, String str2) {
            this.a = context;
            this.b = str;
            this.c = str2;
        }

        public void run() {
            try {
                af.c.a(this.a, this.b, this.c);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    static {
        c = null;
    }

    boolean a() {
        return this.e != null;
    }

    private void c() {
        d();
        this.d.clear();
    }

    private void d() {
        if (this.e != null) {
            try {
                this.e.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void a(String str, String str2) {
        try {
            this.d.clear();
            d();
            this.e = DexFile.loadDex(str, str2, 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static synchronized af a(Context context, s sVar, String str, String str2, String str3, ClassLoader classLoader) {
        synchronized (af.class) {
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str2)) {
                    ag.a(context, sVar);
                    File file = new File(str);
                    if (file.exists()) {
                        if (c == null) {
                            new Date().getTime();
                            try {
                                c = new af(context.getApplicationContext(), classLoader);
                                c.g = sVar;
                                c.a(str, str2 + File.separator + ae.a(file.getName()));
                            } catch (Throwable th) {
                                th.printStackTrace();
                            }
                            new Date().getTime();
                            new AnonymousClass1(context, str, str2).start();
                        }
                        af afVar = c;
                        return afVar;
                    }
                    ae.b(context, sVar.a(), sVar.b());
                    return null;
                }
            }
            return null;
        }
    }

    static synchronized void b(Context context, s sVar, String str, String str2, String str3, ClassLoader classLoader) {
        synchronized (af.class) {
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str2)) {
                    ag.a(context, sVar);
                    try {
                        File file = new File(str);
                        if (file.exists()) {
                            if (c != null) {
                                c.c();
                            }
                            c = new af(context.getApplicationContext(), classLoader);
                            c.g = sVar;
                            c.a(context, str, str2);
                            c.a(str, str2 + File.separator + ae.a(file.getName()));
                            return;
                        }
                        ae.b(context, sVar.a(), sVar.b());
                        return;
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean a(x xVar, s sVar, String str) {
        return ae.a(xVar, ae.b(sVar.a(), sVar.b()), str, sVar);
    }

    private boolean a(x xVar, String str, String str2) {
        String a = ae.a(this.b, str);
        if (ae.a(xVar, str, a, this.g)) {
            return true;
        }
        if (a.a(xVar, str) != null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.f)) {
            a.a(xVar, new a(str, p.a(a), this.g.a(), this.g.b(), str2).a("useodex").a(), ai.b(str));
        }
        return true;
    }

    private void a(x xVar, String str) {
        aj a = a.a(xVar, str);
        if (a != null) {
            this.f = a.e();
        }
    }

    private void a(Context context, String str, String str2) {
        new Date().getTime();
        try {
            x xVar = new x(context, ah.c());
            File file = new File(str);
            a(xVar, file.getName());
            if (!a(xVar, this.g, file.getAbsolutePath())) {
                this.a = false;
                ae.b(this.b, xVar, file.getName());
                Object a = ae.a(this.b, xVar, this.g);
                if (!TextUtils.isEmpty(a)) {
                    this.f = a;
                    a(str, str2 + File.separator + ae.a(file.getName()));
                    this.a = true;
                }
            }
            if (file.exists()) {
                String str3 = str2 + File.separator + ae.a(file.getName());
                File file2 = new File(str3);
                if (!file2.exists()) {
                    a(str, str2 + File.separator + ae.a(file.getName()));
                    c.a(file2, str3, this.f, xVar);
                } else if (!a(xVar, ae.a(file.getName()), this.f)) {
                    a(str, str2 + File.separator + ae.a(file.getName()));
                    a(file2, str3, this.f, xVar);
                }
                new Date().getTime();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void a(File file, String str, String str2, x xVar) {
        if (!TextUtils.isEmpty(this.f)) {
            String a = p.a(str);
            String name = file.getName();
            a.a(xVar, new a(name, a, this.g.a(), this.g.b(), str2).a("useodex").a(), ai.b(name));
        }
    }

    private af(Context context, ClassLoader classLoader) {
        super(classLoader);
        this.d = new HashMap();
        this.e = null;
        this.a = true;
        this.b = context;
    }

    protected Class<?> findClass(String str) throws ClassNotFoundException {
        try {
            if (this.e != null) {
                Class<?> cls = (Class) this.d.get(str);
                if (cls != null) {
                    return cls;
                }
                cls = this.e.loadClass(str, this);
                this.d.put(str, cls);
                if (cls != null) {
                    return cls;
                }
                throw new ClassNotFoundException(str);
            }
            throw new ClassNotFoundException(str);
        } catch (Throwable th) {
            th.printStackTrace();
            ClassNotFoundException classNotFoundException = new ClassNotFoundException(str);
        }
    }
}
