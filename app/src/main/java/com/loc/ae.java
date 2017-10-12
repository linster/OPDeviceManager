package com.loc;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: DexFileManager */
class ae {

    /* compiled from: DexFileManager */
    /* renamed from: com.loc.ae.1 */
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
                x xVar = new x(this.a, ah.c());
                List<aj> c = xVar.c(ai.a(this.b), new ai());
                if (c != null && c.size() > 0) {
                    for (aj ajVar : c) {
                        if (!this.c.equalsIgnoreCase(ajVar.d())) {
                            ae.a(this.a, xVar, ajVar.a());
                        }
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* compiled from: DexFileManager */
    static class a {
        static void a(x xVar, aj ajVar, String str) {
            y aiVar = new ai();
            aiVar.a(ajVar);
            xVar.a(aiVar, str);
        }

        static aj a(x xVar, String str) {
            List c = xVar.c(ai.b(str), new ai());
            if (c != null && c.size() > 0) {
                return (aj) c.get(0);
            }
            return null;
        }
    }

    static String a(String str) {
        return str + ".dex";
    }

    static boolean a(String str, String str2) {
        String a = p.a(str);
        if (a != null && a.equalsIgnoreCase(str2)) {
            return true;
        }
        return false;
    }

    static String b(String str, String str2) {
        return p.b(str + str2) + ".jar";
    }

    static String a(Context context, String str, String str2) {
        return a(context, b(str, str2));
    }

    static String a(Context context, String str) {
        return a(context) + File.separator + str;
    }

    static String a(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "dex";
    }

    static void a(Context context, x xVar, String str) {
        c(context, xVar, str);
    }

    private static void c(Context context, x xVar, String str) {
        File file = new File(a(context, str));
        if (file.exists()) {
            file.delete();
        }
        xVar.a(ai.b(str), new ai());
    }

    static void b(Context context, String str, String str2) {
        new AnonymousClass1(context, str, str2).start();
    }

    static void b(Context context, x xVar, String str) {
        c(context, xVar, str);
        c(context, xVar, a(str));
    }

    static void a(x xVar, Context context, s sVar) {
        y aiVar = new ai();
        String a = sVar.a();
        String b = b(a, sVar.b());
        aj a2 = a.a(xVar, b);
        if (a2 != null) {
            b(context, xVar, b);
            List c = xVar.c(ai.a(a, a2.e()), aiVar);
            if (c != null && c.size() > 0) {
                aj ajVar = (aj) c.get(0);
                ajVar.a("errorstatus");
                a.a(xVar, ajVar, ai.b(ajVar.a()));
                File file = new File(a(context, ajVar.a()));
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    static void a(Context context, x xVar, s sVar, aj ajVar, String str) throws Throwable {
        InputStream fileInputStream;
        FileOutputStream fileOutputStream;
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        FileOutputStream fileOutputStream2 = null;
        try {
            String a = sVar.a();
            b(context, xVar, ajVar.a());
            fileInputStream = new FileInputStream(new File(str));
            try {
                fileOutputStream = new FileOutputStream(new File(a(context, a, sVar.b())), true);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    a.a(xVar, ajVar, ai.b(ajVar.a()));
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

    static boolean a(Context context, x xVar, String str, s sVar) {
        return a(xVar, str, a(context, str), sVar);
    }

    static boolean a(x xVar, String str, String str2, s sVar) {
        aj a = a.a(xVar, str);
        if (a != null && sVar.b().equals(a.d()) && a(str2, a.b())) {
            return true;
        }
        return false;
    }

    static String a(Context context, x xVar, s sVar) {
        List c = xVar.c(ai.b(sVar.a(), "copy"), new ai());
        if (c == null || c.size() == 0) {
            return null;
        }
        String e;
        a(c);
        for (int i = 0; i < c.size(); i++) {
            aj ajVar = (aj) c.get(i);
            if (a(context, xVar, ajVar.a(), sVar)) {
                try {
                    a(context, xVar, sVar, new a(b(sVar.a(), sVar.b()), ajVar.b(), sVar.a(), sVar.b(), ajVar.e()).a("usedex").a(), a(context, ajVar.a()));
                    e = ajVar.e();
                    break;
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else {
                a(context, xVar, ajVar.a());
            }
        }
        e = null;
        return e;
    }

    static void a(List<aj> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int i2 = i + 1; i2 < list.size(); i2++) {
                aj ajVar = (aj) list.get(i);
                aj ajVar2 = (aj) list.get(i2);
                if (ak.a(ajVar2.e(), ajVar.e()) > 0) {
                    list.set(i, ajVar2);
                    list.set(i2, ajVar);
                }
            }
        }
    }

    static void a(Throwable th) {
        al.a(al.a() + File.separator + "dynamiclog.txt", t.a(th), false);
    }
}
