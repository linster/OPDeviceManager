package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

import java.lang.reflect.Method;

/* compiled from: LastLocationManager */
public class f {
    private static f e;
    Context a;
    SharedPreferences b;
    Editor c;
    private String d;
    private Method f;

    public static f a(Context context) {
        if (e == null) {
            e = new f(context);
        }
        return e;
    }

    private f(Context context) {
        this.d = null;
        this.b = null;
        this.c = null;
        this.a = context;
        if (this.d == null) {
            this.d = bd.a("MD5", this.a.getPackageName());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.amap.api.location.AMapLocation r5) {
        /*
        r4 = this;
        r3 = 0;
        r1 = 0;
        r0 = r4.a;
        if (r0 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = com.loc.br.a(r5);
        if (r0 == 0) goto L_0x0006;
    L_0x000d:
        r0 = r5.getLocationType();
        r2 = 2;
        if (r0 == r2) goto L_0x002d;
    L_0x0014:
        r0 = r4.b;
        if (r0 == 0) goto L_0x002e;
    L_0x0018:
        r0 = r4.c;
        if (r0 == 0) goto L_0x003a;
    L_0x001c:
        r0 = r5.toStr();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0026:
        r0 = android.text.TextUtils.isEmpty(r1);
        if (r0 == 0) goto L_0x005f;
    L_0x002c:
        return;
    L_0x002d:
        return;
    L_0x002e:
        r0 = r4.a;
        r2 = "pref";
        r0 = r0.getSharedPreferences(r2, r3);
        r4.b = r0;
        goto L_0x0018;
    L_0x003a:
        r0 = r4.b;
        r0 = r0.edit();
        r4.c = r0;
        goto L_0x001c;
    L_0x0043:
        r0 = r5.toStr();	 Catch:{ UnsupportedEncodingException -> 0x0059 }
        r2 = "UTF-8";
        r0 = r0.getBytes(r2);	 Catch:{ UnsupportedEncodingException -> 0x0059 }
        r2 = r4.d;	 Catch:{ UnsupportedEncodingException -> 0x0059 }
        r0 = com.loc.bd.c(r0, r2);	 Catch:{ UnsupportedEncodingException -> 0x0059 }
    L_0x0054:
        r1 = com.loc.o.a(r0);
        goto L_0x0026;
    L_0x0059:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x0054;
    L_0x005f:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = "lastfix";
        r0 = r0.append(r2);
        r2 = com.loc.c.g;
        r0 = r0.append(r2);
        r0 = r0.toString();
        r2 = r4.c;
        r2.putString(r0, r1);
        r0 = r4.c;
        r4.a(r0);
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.f.a(com.amap.api.location.AMapLocation):void");
    }

    private void a(Editor editor) {
        if (editor != null) {
            if (VERSION.SDK_INT < 9) {
                editor.commit();
            } else {
                try {
                    if (this.f == null) {
                        this.f = Editor.class.getDeclaredMethod("apply", new Class[0]);
                    }
                    this.f.invoke(editor, new Object[0]);
                } catch (Throwable th) {
                    th.printStackTrace();
                    editor.commit();
                }
            }
        }
    }
}
