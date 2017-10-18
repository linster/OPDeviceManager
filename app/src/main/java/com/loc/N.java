package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import java.lang.reflect.Method;

public class N {
    private static N mc;
    Context ma;
    private String mb = null;
    SharedPreferences md = null;
    Editor me = null;
    private Method mf;

    private N(Context context) {
        this.ma = context;
        if (this.mb == null) {
            this.mb = bQ.yr("MD5", this.ma.getPackageName());
        }
    }

    public static N nj(Context context) {
        if (mc == null) {
            mc = new N(context);
        }
        return mc;
    }

    private void nl(Editor editor) {
        if (editor != null) {
            if (VERSION.SDK_INT < 9) {
                editor.commit();
            } else {
                try {
                    if (this.mf == null) {
                        this.mf = Editor.class.getDeclaredMethod("apply", new Class[0]);
                    }
                    this.mf.invoke(editor, new Object[0]);
                } catch (Throwable th) {
                    th.printStackTrace();
                    editor.commit();
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void nk(com.amap.api.location.AMapLocation r5) {
        /*
        r4 = this;
        r3 = 0;
        r1 = 0;
        r0 = r4.ma;
        if (r0 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = com.loc.bq.vG(r5);
        if (r0 == 0) goto L_0x0006;
    L_0x000d:
        r0 = r5.hz();
        r2 = 2;
        if (r0 == r2) goto L_0x002d;
    L_0x0014:
        r0 = r4.md;
        if (r0 == 0) goto L_0x002e;
    L_0x0018:
        r0 = r4.me;
        if (r0 == 0) goto L_0x003a;
    L_0x001c:
        r0 = r5.hP();
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
        r0 = r4.ma;
        r2 = "pref";
        r0 = r0.getSharedPreferences(r2, r3);
        r4.md = r0;
        goto L_0x0018;
    L_0x003a:
        r0 = r4.md;
        r0 = r0.edit();
        r4.me = r0;
        goto L_0x001c;
    L_0x0043:
        r0 = r5.hP();	 Catch:{ UnsupportedEncodingException -> 0x0059 }
        r2 = "UTF-8";
        r0 = r0.getBytes(r2);	 Catch:{ UnsupportedEncodingException -> 0x0059 }
        r2 = r4.mb;	 Catch:{ UnsupportedEncodingException -> 0x0059 }
        r0 = com.loc.bQ.yn(r0, r2);	 Catch:{ UnsupportedEncodingException -> 0x0059 }
    L_0x0054:
        r1 = com.loc.aG.ro(r0);
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
        r2 = com.loc.H.lI;
        r0 = r0.append(r2);
        r0 = r0.toString();
        r2 = r4.me;
        r2.putString(r0, r1);
        r0 = r4.me;
        r4.nl(r0);
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.N.nk(com.amap.api.location.AMapLocation):void");
    }
}
