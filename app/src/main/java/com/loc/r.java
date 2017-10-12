package com.loc;

import android.content.Context;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

/* compiled from: SDKCoordinatorDownload */
public class r extends Thread implements com.loc.an.a {
    private static String h;
    private static String i;
    private an a;
    private a b;
    private RandomAccessFile c;
    private String d;
    private String e;
    private String f;
    private Context g;

    /* compiled from: SDKCoordinatorDownload */
    private static class a extends aq {
        private String d;

        a(String str) {
            this.d = str;
        }

        public Map<String, String> a() {
            return null;
        }

        public Map<String, String> b() {
            return null;
        }

        public String c() {
            return this.d;
        }
    }

    static {
        h = "sodownload";
        i = "sofail";
    }

    public r(Context context, String str, String str2, String str3) {
        this.g = context;
        this.f = str3;
        this.d = a(context, str + "temp.so");
        this.e = a(context, "libwgs2gcj.so");
        this.b = new a(str2);
        this.a = new an(this.b);
    }

    public static String a(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "libso" + File.separator + str;
    }

    private static String b(Context context, String str) {
        return a(context, str);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
        r2 = this;
        r0 = r2.b;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = r2.b;
        r0 = r0.c();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x0004;
    L_0x0011:
        r0 = r2.b;
        r0 = r0.c();
        r1 = "libJni_wgs2gcj.so";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x0004;
    L_0x0020:
        r0 = r2.b;
        r0 = r0.c();
        r1 = android.os.Build.CPU_ABI;
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x0004;
    L_0x002e:
        r0 = new java.io.File;
        r1 = r2.e;
        r0.<init>(r1);
        r0 = r0.exists();
        if (r0 != 0) goto L_0x003f;
    L_0x003b:
        r2.start();
        return;
    L_0x003f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.r.a():void");
    }

    public void run() {
        try {
            File file = new File(b(this.g, "tempfile"));
            if (file.exists()) {
                k.a(this.g, new com.loc.s.a(i, "1.0.0", "sodownload_1.0.0").a(new String[0]).a());
                file.delete();
            }
            this.a.a(this);
        } catch (Throwable th) {
            th.printStackTrace();
            d();
        }
    }

    public void a(byte[] bArr, long j) {
        try {
            if (this.c == null) {
                File file = new File(this.d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.c = new RandomAccessFile(file, "rw");
            }
        } catch (FileNotFoundException e) {
            d();
            e.printStackTrace();
        } catch (Throwable th) {
            d();
            th.printStackTrace();
            return;
        }
        try {
            this.c.seek(j);
            this.c.write(bArr);
        } catch (IOException e2) {
            d();
            e2.printStackTrace();
        }
    }

    public void b() {
        d();
    }

    public void c() {
        try {
            if (this.c != null) {
                this.c.close();
            }
            if (p.a(this.d).equalsIgnoreCase(this.f)) {
                new File(this.d).renameTo(new File(this.e));
                k.a(this.g, new com.loc.s.a(h, "1.0.0", "sodownload_1.0.0").a(new String[0]).a());
                return;
            }
            d();
            k.a(this.g, new com.loc.s.a(i, "1.0.0", "sodownload_1.0.0").a(new String[0]).a());
        } catch (Throwable th) {
            d();
            File file = new File(this.e);
            if (file.exists()) {
                file.delete();
            }
            try {
                k.a(this.g, new com.loc.s.a(i, "1.0.0", "sodownload_1.0.0").a(new String[0]).a());
            } catch (i e) {
                e.printStackTrace();
            }
            th.printStackTrace();
        }
    }

    public void a(Throwable th) {
        try {
            if (this.c != null) {
                this.c.close();
            }
            d();
            File file = new File(b(this.g, "tempfile"));
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                file.createNewFile();
            }
        } catch (Throwable e) {
            v.a(e, "SDKCoordinatorDownload", "onException");
        } catch (Throwable e2) {
            v.a(e2, "SDKCoordinatorDownload", "onException");
        }
    }

    private void d() {
        File file = new File(this.d);
        if (file.exists()) {
            file.delete();
        }
    }
}
