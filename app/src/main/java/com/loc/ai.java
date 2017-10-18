package com.loc;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;
import java.util.Locale;

public class ai {
    public static Proxy oo(Context context) {
        try {
            return VERSION.SDK_INT < 11 ? op(context) : os(context, new URI("http://restapi.amap.com"));
        } catch (Throwable e) {
            D.mF(e, "ProxyUtil", "getProxy");
            return null;
        } catch (Throwable e2) {
            D.mF(e2, "ProxyUtil", "getProxy");
            return null;
        }
    }

    private static Proxy op(Context context) {
        String or;
        int ot;
        int i;
        Cursor cursor;
        int i2;
        Throwable th;
        String toLowerCase;
        int i3;
        Object obj;
        Cursor cursor2;
        int i4 = 80;
        int i5 = -1;
        Object obj2 = null;
        if (bK.xP(context) == 0) {
            Cursor query;
            String string;
            try {
                query = context.getContentResolver().query(Uri.parse("content://telephony/carriers/preferapn"), null, null, null, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            string = query.getString(query.getColumnIndex("apn"));
                            if (string != null) {
                                string = string.toLowerCase(Locale.US);
                            }
                            if (string != null && string.contains("ctwap")) {
                                or = or();
                                ot = ot();
                                try {
                                    Object obj3;
                                    if (TextUtils.isEmpty(or)) {
                                        obj3 = null;
                                        or = null;
                                    } else if (or.equals("null")) {
                                        obj3 = null;
                                        or = null;
                                    } else {
                                        i = 1;
                                    }
                                    if (obj3 == null) {
                                        try {
                                            or = "10.0.0.200";
                                        } catch (Throwable e) {
                                            Throwable th2 = e;
                                            cursor = query;
                                            i2 = ot;
                                            th = th2;
                                            try {
                                                D.mF(th, "ProxyUtil", "getHostProxy");
                                                string = bK.xR(context);
                                                if (string != null) {
                                                    i4 = i2;
                                                } else {
                                                    toLowerCase = string.toLowerCase(Locale.US);
                                                    string = or();
                                                    i2 = ot();
                                                    if (toLowerCase.indexOf("ctwap") == -1) {
                                                        if (!(TextUtils.isEmpty(string) || string.equals("null"))) {
                                                            i3 = 1;
                                                            or = string;
                                                        }
                                                        if (obj2 == null) {
                                                            or = "10.0.0.200";
                                                        }
                                                        if (i2 != -1) {
                                                            i4 = i2;
                                                        }
                                                    } else if (toLowerCase.indexOf("wap") != -1) {
                                                        i4 = i2;
                                                    } else {
                                                        if (TextUtils.isEmpty(string)) {
                                                            obj = null;
                                                            string = or;
                                                        } else if (string.equals("null")) {
                                                            i2 = 1;
                                                        } else {
                                                            obj = null;
                                                            string = or;
                                                        }
                                                        if (obj == null) {
                                                            string = "10.0.0.200";
                                                        }
                                                        or = string;
                                                    }
                                                }
                                                if (cursor != null) {
                                                    try {
                                                        cursor.close();
                                                    } catch (Throwable th3) {
                                                        D.mF(th3, "ProxyUtil", "getHostProxy2");
                                                        th3.printStackTrace();
                                                    }
                                                }
                                                if (oq(or, i4)) {
                                                    return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                                }
                                                return null;
                                            } catch (Throwable th4) {
                                                th3 = th4;
                                                query = cursor;
                                                if (query != null) {
                                                    try {
                                                        query.close();
                                                    } catch (Throwable e2) {
                                                        D.mF(e2, "ProxyUtil", "getHostProxy2");
                                                        e2.printStackTrace();
                                                    }
                                                }
                                                throw th3;
                                            }
                                        } catch (Throwable e22) {
                                            i5 = ot;
                                            th3 = e22;
                                            try {
                                                D.mF(th3, "ProxyUtil", "getHostProxy1");
                                                th3.printStackTrace();
                                                if (query != null) {
                                                    i4 = i5;
                                                } else {
                                                    try {
                                                        query.close();
                                                    } catch (Throwable th32) {
                                                        D.mF(th32, "ProxyUtil", "getHostProxy2");
                                                        th32.printStackTrace();
                                                    }
                                                    i4 = i5;
                                                }
                                                if (oq(or, i4)) {
                                                    return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                                }
                                                return null;
                                            } catch (Throwable th5) {
                                                th32 = th5;
                                                if (query != null) {
                                                    query.close();
                                                }
                                                throw th32;
                                            }
                                        }
                                    }
                                    if (ot == -1) {
                                        ot = 80;
                                    }
                                    i4 = ot;
                                    if (query != null) {
                                        try {
                                            query.close();
                                        } catch (Throwable th322) {
                                            D.mF(th322, "ProxyUtil", "getHostProxy2");
                                            th322.printStackTrace();
                                        }
                                    }
                                } catch (Throwable e222) {
                                    or = null;
                                    cursor2 = query;
                                    i2 = ot;
                                    th322 = e222;
                                    cursor = cursor2;
                                    D.mF(th322, "ProxyUtil", "getHostProxy");
                                    string = bK.xR(context);
                                    if (string != null) {
                                        i4 = i2;
                                    } else {
                                        toLowerCase = string.toLowerCase(Locale.US);
                                        string = or();
                                        i2 = ot();
                                        if (toLowerCase.indexOf("ctwap") == -1) {
                                            i3 = 1;
                                            or = string;
                                            if (obj2 == null) {
                                                or = "10.0.0.200";
                                            }
                                            if (i2 != -1) {
                                                i4 = i2;
                                            }
                                        } else if (toLowerCase.indexOf("wap") != -1) {
                                            i4 = i2;
                                        } else {
                                            if (TextUtils.isEmpty(string)) {
                                                obj = null;
                                                string = or;
                                            } else if (string.equals("null")) {
                                                obj = null;
                                                string = or;
                                            } else {
                                                i2 = 1;
                                            }
                                            if (obj == null) {
                                                string = "10.0.0.200";
                                            }
                                            or = string;
                                        }
                                    }
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (oq(or, i4)) {
                                        return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                    }
                                    return null;
                                } catch (Throwable e2222) {
                                    i5 = ot;
                                    or = null;
                                    th322 = e2222;
                                    D.mF(th322, "ProxyUtil", "getHostProxy1");
                                    th322.printStackTrace();
                                    if (query != null) {
                                        i4 = i5;
                                    } else {
                                        query.close();
                                        i4 = i5;
                                    }
                                    if (oq(or, i4)) {
                                        return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                    }
                                    return null;
                                }
                                if (oq(or, i4)) {
                                    return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                }
                            }
                            if (string != null) {
                                if (string.contains("wap")) {
                                    or = or();
                                    i = ot();
                                    try {
                                        Object obj4;
                                        if (TextUtils.isEmpty(or)) {
                                            obj4 = null;
                                            or = null;
                                        } else if (or.equals("null")) {
                                            obj4 = null;
                                            or = null;
                                        } else {
                                            ot = 1;
                                        }
                                        if (obj4 == null) {
                                            try {
                                                or = "10.0.0.172";
                                            } catch (SecurityException e3) {
                                                th322 = e3;
                                                cursor2 = query;
                                                i2 = i;
                                                cursor = cursor2;
                                                D.mF(th322, "ProxyUtil", "getHostProxy");
                                                string = bK.xR(context);
                                                if (string != null) {
                                                    i4 = i2;
                                                } else {
                                                    toLowerCase = string.toLowerCase(Locale.US);
                                                    string = or();
                                                    i2 = ot();
                                                    if (toLowerCase.indexOf("ctwap") == -1) {
                                                        i3 = 1;
                                                        or = string;
                                                        if (obj2 == null) {
                                                            or = "10.0.0.200";
                                                        }
                                                        if (i2 != -1) {
                                                            i4 = i2;
                                                        }
                                                    } else if (toLowerCase.indexOf("wap") != -1) {
                                                        i4 = i2;
                                                    } else {
                                                        if (TextUtils.isEmpty(string)) {
                                                            obj = null;
                                                            string = or;
                                                        } else if (string.equals("null")) {
                                                            obj = null;
                                                            string = or;
                                                        } else {
                                                            i2 = 1;
                                                        }
                                                        if (obj == null) {
                                                            string = "10.0.0.200";
                                                        }
                                                        or = string;
                                                    }
                                                }
                                                if (cursor != null) {
                                                    cursor.close();
                                                }
                                                if (oq(or, i4)) {
                                                    return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                                }
                                                return null;
                                            } catch (Throwable th6) {
                                                th322 = th6;
                                                i5 = i;
                                                D.mF(th322, "ProxyUtil", "getHostProxy1");
                                                th322.printStackTrace();
                                                if (query != null) {
                                                    i4 = i5;
                                                } else {
                                                    query.close();
                                                    i4 = i5;
                                                }
                                                if (oq(or, i4)) {
                                                    return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                                }
                                                return null;
                                            }
                                        }
                                        if (i != -1) {
                                            i4 = i;
                                        }
                                        if (query != null) {
                                            query.close();
                                        }
                                    } catch (SecurityException e4) {
                                        th322 = e4;
                                        or = null;
                                        int i6 = i;
                                        cursor = query;
                                        i2 = i6;
                                        D.mF(th322, "ProxyUtil", "getHostProxy");
                                        string = bK.xR(context);
                                        if (string != null) {
                                            toLowerCase = string.toLowerCase(Locale.US);
                                            string = or();
                                            i2 = ot();
                                            if (toLowerCase.indexOf("ctwap") == -1) {
                                                i3 = 1;
                                                or = string;
                                                if (obj2 == null) {
                                                    or = "10.0.0.200";
                                                }
                                                if (i2 != -1) {
                                                    i4 = i2;
                                                }
                                            } else if (toLowerCase.indexOf("wap") != -1) {
                                                if (TextUtils.isEmpty(string)) {
                                                    obj = null;
                                                    string = or;
                                                } else if (string.equals("null")) {
                                                    i2 = 1;
                                                } else {
                                                    obj = null;
                                                    string = or;
                                                }
                                                if (obj == null) {
                                                    string = "10.0.0.200";
                                                }
                                                or = string;
                                            } else {
                                                i4 = i2;
                                            }
                                        } else {
                                            i4 = i2;
                                        }
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        if (oq(or, i4)) {
                                            return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                        }
                                        return null;
                                    } catch (Throwable th7) {
                                        th322 = th7;
                                        i5 = i;
                                        or = null;
                                        D.mF(th322, "ProxyUtil", "getHostProxy1");
                                        th322.printStackTrace();
                                        if (query != null) {
                                            query.close();
                                            i4 = i5;
                                        } else {
                                            i4 = i5;
                                        }
                                        if (oq(or, i4)) {
                                            return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                        }
                                        return null;
                                    }
                                    if (oq(or, i4)) {
                                        return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                                    }
                                }
                            }
                            i4 = -1;
                            or = null;
                            if (query != null) {
                                query.close();
                            }
                            if (oq(or, i4)) {
                                return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                            }
                        }
                    } catch (SecurityException e5) {
                        th322 = e5;
                        cursor = query;
                        or = null;
                        i2 = -1;
                        D.mF(th322, "ProxyUtil", "getHostProxy");
                        string = bK.xR(context);
                        if (string != null) {
                            toLowerCase = string.toLowerCase(Locale.US);
                            string = or();
                            i2 = ot();
                            if (toLowerCase.indexOf("ctwap") == -1) {
                                i3 = 1;
                                or = string;
                                if (obj2 == null) {
                                    or = "10.0.0.200";
                                }
                                if (i2 != -1) {
                                    i4 = i2;
                                }
                            } else if (toLowerCase.indexOf("wap") != -1) {
                                if (TextUtils.isEmpty(string)) {
                                    obj = null;
                                    string = or;
                                } else if (string.equals("null")) {
                                    i2 = 1;
                                } else {
                                    obj = null;
                                    string = or;
                                }
                                if (obj == null) {
                                    string = "10.0.0.200";
                                }
                                or = string;
                            } else {
                                i4 = i2;
                            }
                        } else {
                            i4 = i2;
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (oq(or, i4)) {
                            return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                        }
                        return null;
                    } catch (Throwable th8) {
                        th322 = th8;
                        or = null;
                        D.mF(th322, "ProxyUtil", "getHostProxy1");
                        th322.printStackTrace();
                        if (query != null) {
                            query.close();
                            i4 = i5;
                        } else {
                            i4 = i5;
                        }
                        if (oq(or, i4)) {
                            return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                        }
                        return null;
                    }
                }
                i4 = -1;
                or = null;
                if (query != null) {
                    query.close();
                }
            } catch (SecurityException e6) {
                th322 = e6;
                cursor = null;
                i2 = -1;
                or = null;
                D.mF(th322, "ProxyUtil", "getHostProxy");
                string = bK.xR(context);
                if (string != null) {
                    toLowerCase = string.toLowerCase(Locale.US);
                    string = or();
                    i2 = ot();
                    if (toLowerCase.indexOf("ctwap") == -1) {
                        i3 = 1;
                        or = string;
                        if (obj2 == null) {
                            or = "10.0.0.200";
                        }
                        if (i2 != -1) {
                            i4 = i2;
                        }
                    } else if (toLowerCase.indexOf("wap") != -1) {
                        if (TextUtils.isEmpty(string)) {
                            obj = null;
                            string = or;
                        } else if (string.equals("null")) {
                            i2 = 1;
                        } else {
                            obj = null;
                            string = or;
                        }
                        if (obj == null) {
                            string = "10.0.0.200";
                        }
                        or = string;
                    } else {
                        i4 = i2;
                    }
                } else {
                    i4 = i2;
                }
                if (cursor != null) {
                    cursor.close();
                }
                if (oq(or, i4)) {
                    return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                }
                return null;
            } catch (Throwable th9) {
                th322 = th9;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th322;
            }
            try {
                if (oq(or, i4)) {
                    return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(or, i4));
                }
            } catch (Throwable th3222) {
                D.mF(th3222, "ProxyUtil", "getHostProxy2");
                th3222.printStackTrace();
            }
        }
        return null;
    }

    private static boolean oq(String str, int i) {
        return (str == null || str.length() <= 0 || i == -1) ? false : true;
    }

    private static String or() {
        String str = null;
        try {
            str = android.net.Proxy.getDefaultHost();
        } catch (Throwable th) {
            D.mF(th, "ProxyUtil", "getDefHost");
        }
        return str != null ? str : "null";
    }

    private static Proxy os(Context context, URI uri) {
        if (bK.xP(context) == 0) {
            try {
                Proxy proxy;
                List select = ProxySelector.getDefault().select(uri);
                if (select == null || select.isEmpty()) {
                    proxy = null;
                } else {
                    proxy = (Proxy) select.get(0);
                    if (proxy == null || proxy.type() == Type.DIRECT) {
                        proxy = null;
                    }
                }
                return proxy;
            } catch (Throwable th) {
                D.mF(th, "ProxyUtil", "getProxySelectorCfg");
            }
        }
        return null;
    }

    private static int ot() {
        int i = -1;
        try {
            i = android.net.Proxy.getDefaultPort();
        } catch (Throwable th) {
            D.mF(th, "ProxyUtil", "getDefPort");
        }
        return i;
    }
}
