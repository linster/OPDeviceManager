package com.loc;

import android.content.Context;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.autonavi.aps.amapapi.model.AmapLoc;
import com.loc.au.c;
import com.loc.be.a;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

/* compiled from: Off */
public class az {
    public static Hashtable<String, Long> a;
    public static Hashtable<String, String> b;
    public static int[] c;
    static int d;
    private static volatile String e;
    private static bj f;
    private static TelephonyManager g;

    static {
        e = null;
        a = new Hashtable();
        f = new bj();
        b = new Hashtable();
        g = null;
        c = new int[]{0, 0};
        d = 213891;
    }

    public static String[] a(double d, double d2, String str) {
        String a;
        int i;
        String[] strArr = new String[50];
        if (TextUtils.isEmpty(str)) {
            a = ay.a(d, d2);
            str = ay.a(d, d2);
        } else {
            a = str;
        }
        strArr[0] = a;
        strArr[25] = str;
        String[] a2 = ay.a(a);
        for (i = 1; i < 25; i++) {
            strArr[i] = a2[i - 1];
        }
        a2 = ay.a(str);
        for (i = 26; i < 50; i++) {
            strArr[i] = a2[i - 26];
        }
        return strArr;
    }

    public static AmapLoc a(double[] dArr, String str, String str2, String str3, int i, Context context, int[] iArr) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        if (str2.contains("gps")) {
            return null;
        }
        String[] a;
        int i2;
        int i3;
        int i4;
        int i5;
        AmapLoc a2;
        String stringBuilder;
        int indexOf;
        String str4;
        bj bjVar;
        be beVar;
        Object obj;
        File file;
        be beVar2;
        a aVar;
        int i6;
        long c;
        long g;
        int i7;
        Object obj2;
        Object obj3;
        int i8;
        double[] a3;
        Entry entry;
        Iterator it;
        int a4 = a(str2);
        String a5 = a(a4, str2);
        Hashtable hashtable = new Hashtable();
        a(a4, str2, str3, hashtable);
        Hashtable hashtable2 = new Hashtable();
        a(str3, hashtable2);
        StringBuilder c2 = c();
        if (dArr != null) {
            a = a(dArr[0], dArr[1], str);
        } else {
            a = a(0.0d, 0.0d, str);
        }
        int length = a.length / 2;
        if (1 <= i) {
            if (i > 3) {
            }
            c.p = hashtable.size();
            i2 = 0;
            i3 = 0;
            i4 = 0;
            while (i4 < a.length) {
                if (i4 < length) {
                    if (i3 <= 0 && !hashtable.isEmpty()) {
                    }
                    if (i == 1) {
                        if (i4 != 0) {
                            if (i4 != 0 && i4 != 25) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                                i4++;
                                i5 = i3;
                                i3 = i2;
                                i2 = i5;
                            } else if (i4 > 25) {
                            }
                        }
                        c2.delete(0, c2.length());
                        a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                        if (br.a(a2)) {
                            return a2;
                        }
                        return null;
                    } else if (i == 2) {
                        if (i4 > 8 && i4 < 25) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                            i4++;
                            i5 = i3;
                            i3 = i2;
                            i2 = i5;
                        } else if (i4 > 33) {
                            c2.delete(0, c2.length());
                            a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                            if (br.a(a2)) {
                                return null;
                            }
                            return a2;
                        }
                    }
                    stringBuilder = c2.toString();
                    if (i4 < length) {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    } else {
                        switch (a4) {
                            case 1:
                                iArr[0] = 0;
                                iArr[1] = 0;
                                break;
                            case 2:
                                break;
                            default:
                                iArr[0] = 0;
                                iArr[1] = 0;
                                break;
                        }
                        stringBuilder = stringBuilder + a5 + File.separator;
                        if (a[i4].startsWith("-")) {
                            stringBuilder = stringBuilder + a[i4].substring(0, 3) + ",";
                        } else {
                            stringBuilder = stringBuilder + a[i4].substring(0, 4) + ",";
                        }
                        indexOf = a[i4].indexOf(",") + 1;
                        if (a[i4].substring(indexOf, indexOf + 1).startsWith("-")) {
                            stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 3);
                        } else {
                            stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 4);
                        }
                        str4 = (stringBuilder + File.separator) + a[i4];
                        if (str4.equals(e)) {
                            bjVar = f;
                            beVar = (be) bjVar.b(str4);
                            obj = null;
                            file = new File(str4);
                            if (beVar == null) {
                                obj = 1;
                                beVar2 = beVar;
                            } else if (!file.getParentFile().exists()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else if (file.isDirectory()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else if (file.exists()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else {
                                aVar = new a();
                                try {
                                    beVar = new be(file, aVar);
                                } catch (FileNotFoundException e) {
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                } catch (Throwable th) {
                                    beVar = null;
                                }
                                beVar2 = beVar;
                            }
                            i6 = 0;
                            if (beVar2 == null) {
                                try {
                                    beVar2.a(0);
                                    c = beVar2.c();
                                    if (i4 < length) {
                                        i6 = beVar2.d();
                                    }
                                    try {
                                        g = beVar2.g();
                                        i7 = 8;
                                        if (i4 < length) {
                                            i7 = ((i6 * 4) + 2) + 8;
                                        }
                                        if ((c + 7776000000L < br.a() ? 1 : null) != null) {
                                            if (beVar2 != null) {
                                                if (obj != null) {
                                                    try {
                                                        beVar2.b();
                                                    } catch (Exception e2) {
                                                    }
                                                } else {
                                                    bjVar.c(str4);
                                                }
                                            }
                                            file.delete();
                                            a.remove(a[i4]);
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else {
                                            if ((g > 8 ? 1 : null) == null) {
                                                if ((g - ((long) i7)) % 16 == 0) {
                                                    obj2 = (i4 < length && !hashtable.isEmpty() && i3 < c.p) ? 1 : null;
                                                    obj3 = (i4 >= length && !hashtable2.isEmpty() && i2 < 15) ? 1 : null;
                                                    if (obj2 == null) {
                                                        i8 = i3;
                                                    } else {
                                                        try {
                                                            i8 = i3;
                                                            for (Entry entry2 : hashtable.entrySet()) {
                                                                try {
                                                                    a3 = a(i7, beVar2, ((String) entry2.getKey()).toString(), 0);
                                                                    if (a3 == null) {
                                                                        i6 = i8;
                                                                    } else {
                                                                        i8++;
                                                                        hashtable.put(((String) entry2.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry2.getKey()).toString())));
                                                                        if (i8 < c.p) {
                                                                            i6 = i8;
                                                                        }
                                                                    }
                                                                    i8 = i6;
                                                                } catch (Throwable th2) {
                                                                }
                                                            }
                                                        } catch (Throwable th3) {
                                                            i8 = i3;
                                                        }
                                                    }
                                                    if (obj3 != null) {
                                                        it = hashtable2.entrySet().iterator();
                                                        while (it != null && it.hasNext()) {
                                                            entry2 = (Entry) it.next();
                                                            a3 = a(i7, beVar2, ((String) entry2.getKey()).toString(), 1);
                                                            if (a3 == null) {
                                                                i6 = i2;
                                                            } else {
                                                                i2++;
                                                                hashtable2.put(((String) entry2.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry2.getKey()).toString())));
                                                                if (i2 < 15) {
                                                                    i6 = i2;
                                                                }
                                                            }
                                                            i2 = i6;
                                                        }
                                                    }
                                                    i3 = i8;
                                                    if (beVar2 == null) {
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    } else if (!beVar2.a()) {
                                                        try {
                                                            beVar2.b();
                                                        } catch (Exception e3) {
                                                        }
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    } else if (obj != null) {
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    } else {
                                                        bjVar.b(str4, beVar2);
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    }
                                                }
                                            }
                                            if (beVar2 != null) {
                                                try {
                                                    beVar2.b();
                                                } catch (Exception e4) {
                                                }
                                            }
                                            file.delete();
                                            a.remove(a[i4]);
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        }
                                    } catch (Exception e5) {
                                        if (obj != null) {
                                            bjVar.c(str4);
                                        }
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    }
                                } catch (Exception e6) {
                                    if (obj != null) {
                                        bjVar.c(str4);
                                    }
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                }
                            } else {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            }
                        } else {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        }
                    }
                    i4++;
                    i5 = i3;
                    i3 = i2;
                    i2 = i5;
                }
                if (i4 >= length) {
                    if (i2 <= 0 && !hashtable2.isEmpty()) {
                    }
                    if (i == 1) {
                        if (i4 != 0) {
                            if (i4 != 0) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                                i4++;
                                i5 = i3;
                                i3 = i2;
                                i2 = i5;
                            }
                            if (i4 > 25) {
                            }
                        }
                        c2.delete(0, c2.length());
                        a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                        if (br.a(a2)) {
                            return null;
                        }
                        return a2;
                    } else if (i == 2) {
                        if (i4 > 8) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                            i4++;
                            i5 = i3;
                            i3 = i2;
                            i2 = i5;
                        }
                        if (i4 > 33) {
                            c2.delete(0, c2.length());
                            a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                            if (br.a(a2)) {
                                return a2;
                            }
                            return null;
                        }
                    }
                    stringBuilder = c2.toString();
                    if (i4 < length) {
                        switch (a4) {
                            case 1:
                                iArr[0] = 0;
                                iArr[1] = 0;
                                break;
                            case 2:
                                break;
                            default:
                                iArr[0] = 0;
                                iArr[1] = 0;
                                break;
                        }
                        stringBuilder = stringBuilder + a5 + File.separator;
                        if (a[i4].startsWith("-")) {
                            stringBuilder = stringBuilder + a[i4].substring(0, 4) + ",";
                        } else {
                            stringBuilder = stringBuilder + a[i4].substring(0, 3) + ",";
                        }
                        indexOf = a[i4].indexOf(",") + 1;
                        if (a[i4].substring(indexOf, indexOf + 1).startsWith("-")) {
                            stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 4);
                        } else {
                            stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 3);
                        }
                        str4 = (stringBuilder + File.separator) + a[i4];
                        if (str4.equals(e)) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        } else {
                            bjVar = f;
                            beVar = (be) bjVar.b(str4);
                            obj = null;
                            file = new File(str4);
                            if (beVar == null) {
                                obj = 1;
                                beVar2 = beVar;
                            } else if (!file.getParentFile().exists()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else if (file.isDirectory()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else if (file.exists()) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else {
                                aVar = new a();
                                beVar = new be(file, aVar);
                                beVar2 = beVar;
                            }
                            i6 = 0;
                            if (beVar2 == null) {
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else {
                                beVar2.a(0);
                                c = beVar2.c();
                                if (i4 < length) {
                                    i6 = beVar2.d();
                                }
                                g = beVar2.g();
                                i7 = 8;
                                if (i4 < length) {
                                    i7 = ((i6 * 4) + 2) + 8;
                                }
                                if (c + 7776000000L < br.a()) {
                                }
                                if ((c + 7776000000L < br.a() ? 1 : null) != null) {
                                    if (g > 8) {
                                    }
                                    if ((g > 8 ? 1 : null) == null) {
                                        if ((g - ((long) i7)) % 16 == 0) {
                                            if (i4 < length) {
                                                if (i4 >= length) {
                                                    if (obj2 == null) {
                                                        i8 = i3;
                                                        for (Entry entry22 : hashtable.entrySet()) {
                                                            a3 = a(i7, beVar2, ((String) entry22.getKey()).toString(), 0);
                                                            if (a3 == null) {
                                                                i8++;
                                                                hashtable.put(((String) entry22.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry22.getKey()).toString())));
                                                                if (i8 < c.p) {
                                                                    i6 = i8;
                                                                }
                                                            } else {
                                                                i6 = i8;
                                                            }
                                                            i8 = i6;
                                                        }
                                                    } else {
                                                        i8 = i3;
                                                    }
                                                    if (obj3 != null) {
                                                        it = hashtable2.entrySet().iterator();
                                                        while (it != null) {
                                                            entry22 = (Entry) it.next();
                                                            a3 = a(i7, beVar2, ((String) entry22.getKey()).toString(), 1);
                                                            if (a3 == null) {
                                                                i2++;
                                                                hashtable2.put(((String) entry22.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry22.getKey()).toString())));
                                                                if (i2 < 15) {
                                                                    i6 = i2;
                                                                }
                                                            } else {
                                                                i6 = i2;
                                                            }
                                                            i2 = i6;
                                                        }
                                                    }
                                                    i3 = i8;
                                                    if (beVar2 == null) {
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    } else if (!beVar2.a()) {
                                                        beVar2.b();
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    } else if (obj != null) {
                                                        bjVar.b(str4, beVar2);
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    } else {
                                                        i5 = i2;
                                                        i2 = i3;
                                                        i3 = i5;
                                                    }
                                                }
                                                if (obj2 == null) {
                                                    i8 = i3;
                                                } else {
                                                    i8 = i3;
                                                    for (Entry entry222 : hashtable.entrySet()) {
                                                        a3 = a(i7, beVar2, ((String) entry222.getKey()).toString(), 0);
                                                        if (a3 == null) {
                                                            i6 = i8;
                                                        } else {
                                                            i8++;
                                                            hashtable.put(((String) entry222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry222.getKey()).toString())));
                                                            if (i8 < c.p) {
                                                                i6 = i8;
                                                            }
                                                        }
                                                        i8 = i6;
                                                    }
                                                }
                                                if (obj3 != null) {
                                                    it = hashtable2.entrySet().iterator();
                                                    while (it != null) {
                                                        entry222 = (Entry) it.next();
                                                        a3 = a(i7, beVar2, ((String) entry222.getKey()).toString(), 1);
                                                        if (a3 == null) {
                                                            i6 = i2;
                                                        } else {
                                                            i2++;
                                                            hashtable2.put(((String) entry222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry222.getKey()).toString())));
                                                            if (i2 < 15) {
                                                                i6 = i2;
                                                            }
                                                        }
                                                        i2 = i6;
                                                    }
                                                }
                                                i3 = i8;
                                                if (beVar2 == null) {
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else if (!beVar2.a()) {
                                                    beVar2.b();
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else if (obj != null) {
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else {
                                                    bjVar.b(str4, beVar2);
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                }
                                            }
                                            if (i4 >= length) {
                                                if (obj2 == null) {
                                                    i8 = i3;
                                                    for (Entry entry2222 : hashtable.entrySet()) {
                                                        a3 = a(i7, beVar2, ((String) entry2222.getKey()).toString(), 0);
                                                        if (a3 == null) {
                                                            i8++;
                                                            hashtable.put(((String) entry2222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry2222.getKey()).toString())));
                                                            if (i8 < c.p) {
                                                                i6 = i8;
                                                            }
                                                        } else {
                                                            i6 = i8;
                                                        }
                                                        i8 = i6;
                                                    }
                                                } else {
                                                    i8 = i3;
                                                }
                                                if (obj3 != null) {
                                                    it = hashtable2.entrySet().iterator();
                                                    while (it != null) {
                                                        entry2222 = (Entry) it.next();
                                                        a3 = a(i7, beVar2, ((String) entry2222.getKey()).toString(), 1);
                                                        if (a3 == null) {
                                                            i2++;
                                                            hashtable2.put(((String) entry2222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry2222.getKey()).toString())));
                                                            if (i2 < 15) {
                                                                i6 = i2;
                                                            }
                                                        } else {
                                                            i6 = i2;
                                                        }
                                                        i2 = i6;
                                                    }
                                                }
                                                i3 = i8;
                                                if (beVar2 == null) {
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else if (!beVar2.a()) {
                                                    beVar2.b();
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else if (obj != null) {
                                                    bjVar.b(str4, beVar2);
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else {
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                }
                                            }
                                            if (obj2 == null) {
                                                i8 = i3;
                                            } else {
                                                i8 = i3;
                                                for (Entry entry22222 : hashtable.entrySet()) {
                                                    a3 = a(i7, beVar2, ((String) entry22222.getKey()).toString(), 0);
                                                    if (a3 == null) {
                                                        i6 = i8;
                                                    } else {
                                                        i8++;
                                                        hashtable.put(((String) entry22222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry22222.getKey()).toString())));
                                                        if (i8 < c.p) {
                                                            i6 = i8;
                                                        }
                                                    }
                                                    i8 = i6;
                                                }
                                            }
                                            if (obj3 != null) {
                                                it = hashtable2.entrySet().iterator();
                                                while (it != null) {
                                                    entry22222 = (Entry) it.next();
                                                    a3 = a(i7, beVar2, ((String) entry22222.getKey()).toString(), 1);
                                                    if (a3 == null) {
                                                        i6 = i2;
                                                    } else {
                                                        i2++;
                                                        hashtable2.put(((String) entry22222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry22222.getKey()).toString())));
                                                        if (i2 < 15) {
                                                            i6 = i2;
                                                        }
                                                    }
                                                    i2 = i6;
                                                }
                                            }
                                            i3 = i8;
                                            if (beVar2 == null) {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (!beVar2.a()) {
                                                beVar2.b();
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (obj != null) {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else {
                                                bjVar.b(str4, beVar2);
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            }
                                        }
                                    }
                                    if (beVar2 != null) {
                                        beVar2.b();
                                    }
                                    file.delete();
                                    a.remove(a[i4]);
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                } else {
                                    if (beVar2 != null) {
                                        if (obj != null) {
                                            bjVar.c(str4);
                                        } else {
                                            beVar2.b();
                                        }
                                    }
                                    file.delete();
                                    a.remove(a[i4]);
                                    i5 = i2;
                                    i2 = i3;
                                    i3 = i5;
                                }
                            }
                        }
                    } else {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    }
                    i4++;
                    i5 = i3;
                    i3 = i2;
                    i2 = i5;
                }
                if (i4 >= length && i3 <= 0) {
                }
                if (i == 1) {
                    if (i4 != 0) {
                        if (i4 != 0) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                            i4++;
                            i5 = i3;
                            i3 = i2;
                            i2 = i5;
                        }
                        if (i4 > 25) {
                        }
                    }
                    c2.delete(0, c2.length());
                    a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                    if (br.a(a2)) {
                        return a2;
                    }
                    return null;
                } else if (i == 2) {
                    if (i4 > 8) {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                        i4++;
                        i5 = i3;
                        i3 = i2;
                        i2 = i5;
                    }
                    if (i4 > 33) {
                        c2.delete(0, c2.length());
                        a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                        if (br.a(a2)) {
                            return null;
                        }
                        return a2;
                    }
                }
                stringBuilder = c2.toString();
                if (i4 < length) {
                    i5 = i2;
                    i2 = i3;
                    i3 = i5;
                } else {
                    switch (a4) {
                        case 1:
                            iArr[0] = 0;
                            iArr[1] = 0;
                            break;
                        case 2:
                            break;
                        default:
                            iArr[0] = 0;
                            iArr[1] = 0;
                            break;
                    }
                    stringBuilder = stringBuilder + a5 + File.separator;
                    if (a[i4].startsWith("-")) {
                        stringBuilder = stringBuilder + a[i4].substring(0, 3) + ",";
                    } else {
                        stringBuilder = stringBuilder + a[i4].substring(0, 4) + ",";
                    }
                    indexOf = a[i4].indexOf(",") + 1;
                    if (a[i4].substring(indexOf, indexOf + 1).startsWith("-")) {
                        stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 3);
                    } else {
                        stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 4);
                    }
                    str4 = (stringBuilder + File.separator) + a[i4];
                    if (str4.equals(e)) {
                        bjVar = f;
                        beVar = (be) bjVar.b(str4);
                        obj = null;
                        file = new File(str4);
                        if (beVar == null) {
                            obj = 1;
                            beVar2 = beVar;
                        } else if (!file.getParentFile().exists()) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        } else if (file.isDirectory()) {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        } else if (file.exists()) {
                            aVar = new a();
                            beVar = new be(file, aVar);
                            beVar2 = beVar;
                        } else {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        }
                        i6 = 0;
                        if (beVar2 == null) {
                            beVar2.a(0);
                            c = beVar2.c();
                            if (i4 < length) {
                                i6 = beVar2.d();
                            }
                            g = beVar2.g();
                            i7 = 8;
                            if (i4 < length) {
                                i7 = ((i6 * 4) + 2) + 8;
                            }
                            if (c + 7776000000L < br.a()) {
                            }
                            if ((c + 7776000000L < br.a() ? 1 : null) != null) {
                                if (beVar2 != null) {
                                    if (obj != null) {
                                        beVar2.b();
                                    } else {
                                        bjVar.c(str4);
                                    }
                                }
                                file.delete();
                                a.remove(a[i4]);
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            } else {
                                if (g > 8) {
                                }
                                if ((g > 8 ? 1 : null) == null) {
                                    if ((g - ((long) i7)) % 16 == 0) {
                                        if (i4 < length) {
                                            if (i4 >= length) {
                                                if (obj2 == null) {
                                                    i8 = i3;
                                                    for (Entry entry222222 : hashtable.entrySet()) {
                                                        a3 = a(i7, beVar2, ((String) entry222222.getKey()).toString(), 0);
                                                        if (a3 == null) {
                                                            i8++;
                                                            hashtable.put(((String) entry222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry222222.getKey()).toString())));
                                                            if (i8 < c.p) {
                                                                i6 = i8;
                                                            }
                                                        } else {
                                                            i6 = i8;
                                                        }
                                                        i8 = i6;
                                                    }
                                                } else {
                                                    i8 = i3;
                                                }
                                                if (obj3 != null) {
                                                    it = hashtable2.entrySet().iterator();
                                                    while (it != null) {
                                                        entry222222 = (Entry) it.next();
                                                        a3 = a(i7, beVar2, ((String) entry222222.getKey()).toString(), 1);
                                                        if (a3 == null) {
                                                            i2++;
                                                            hashtable2.put(((String) entry222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry222222.getKey()).toString())));
                                                            if (i2 < 15) {
                                                                i6 = i2;
                                                            }
                                                        } else {
                                                            i6 = i2;
                                                        }
                                                        i2 = i6;
                                                    }
                                                }
                                                i3 = i8;
                                                if (beVar2 == null) {
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else if (!beVar2.a()) {
                                                    beVar2.b();
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else if (obj != null) {
                                                    bjVar.b(str4, beVar2);
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                } else {
                                                    i5 = i2;
                                                    i2 = i3;
                                                    i3 = i5;
                                                }
                                            }
                                            if (obj2 == null) {
                                                i8 = i3;
                                            } else {
                                                i8 = i3;
                                                for (Entry entry2222222 : hashtable.entrySet()) {
                                                    a3 = a(i7, beVar2, ((String) entry2222222.getKey()).toString(), 0);
                                                    if (a3 == null) {
                                                        i6 = i8;
                                                    } else {
                                                        i8++;
                                                        hashtable.put(((String) entry2222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry2222222.getKey()).toString())));
                                                        if (i8 < c.p) {
                                                            i6 = i8;
                                                        }
                                                    }
                                                    i8 = i6;
                                                }
                                            }
                                            if (obj3 != null) {
                                                it = hashtable2.entrySet().iterator();
                                                while (it != null) {
                                                    entry2222222 = (Entry) it.next();
                                                    a3 = a(i7, beVar2, ((String) entry2222222.getKey()).toString(), 1);
                                                    if (a3 == null) {
                                                        i6 = i2;
                                                    } else {
                                                        i2++;
                                                        hashtable2.put(((String) entry2222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry2222222.getKey()).toString())));
                                                        if (i2 < 15) {
                                                            i6 = i2;
                                                        }
                                                    }
                                                    i2 = i6;
                                                }
                                            }
                                            i3 = i8;
                                            if (beVar2 == null) {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (!beVar2.a()) {
                                                beVar2.b();
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (obj != null) {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else {
                                                bjVar.b(str4, beVar2);
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            }
                                        }
                                        if (i4 >= length) {
                                            if (obj2 == null) {
                                                i8 = i3;
                                                for (Entry entry22222222 : hashtable.entrySet()) {
                                                    a3 = a(i7, beVar2, ((String) entry22222222.getKey()).toString(), 0);
                                                    if (a3 == null) {
                                                        i8++;
                                                        hashtable.put(((String) entry22222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry22222222.getKey()).toString())));
                                                        if (i8 < c.p) {
                                                            i6 = i8;
                                                        }
                                                    } else {
                                                        i6 = i8;
                                                    }
                                                    i8 = i6;
                                                }
                                            } else {
                                                i8 = i3;
                                            }
                                            if (obj3 != null) {
                                                it = hashtable2.entrySet().iterator();
                                                while (it != null) {
                                                    entry22222222 = (Entry) it.next();
                                                    a3 = a(i7, beVar2, ((String) entry22222222.getKey()).toString(), 1);
                                                    if (a3 == null) {
                                                        i2++;
                                                        hashtable2.put(((String) entry22222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry22222222.getKey()).toString())));
                                                        if (i2 < 15) {
                                                            i6 = i2;
                                                        }
                                                    } else {
                                                        i6 = i2;
                                                    }
                                                    i2 = i6;
                                                }
                                            }
                                            i3 = i8;
                                            if (beVar2 == null) {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (!beVar2.a()) {
                                                beVar2.b();
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (obj != null) {
                                                bjVar.b(str4, beVar2);
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            }
                                        }
                                        if (obj2 == null) {
                                            i8 = i3;
                                        } else {
                                            i8 = i3;
                                            for (Entry entry222222222 : hashtable.entrySet()) {
                                                a3 = a(i7, beVar2, ((String) entry222222222.getKey()).toString(), 0);
                                                if (a3 == null) {
                                                    i6 = i8;
                                                } else {
                                                    i8++;
                                                    hashtable.put(((String) entry222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry222222222.getKey()).toString())));
                                                    if (i8 < c.p) {
                                                        i6 = i8;
                                                    }
                                                }
                                                i8 = i6;
                                            }
                                        }
                                        if (obj3 != null) {
                                            it = hashtable2.entrySet().iterator();
                                            while (it != null) {
                                                entry222222222 = (Entry) it.next();
                                                a3 = a(i7, beVar2, ((String) entry222222222.getKey()).toString(), 1);
                                                if (a3 == null) {
                                                    i6 = i2;
                                                } else {
                                                    i2++;
                                                    hashtable2.put(((String) entry222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry222222222.getKey()).toString())));
                                                    if (i2 < 15) {
                                                        i6 = i2;
                                                    }
                                                }
                                                i2 = i6;
                                            }
                                        }
                                        i3 = i8;
                                        if (beVar2 == null) {
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else if (!beVar2.a()) {
                                            beVar2.b();
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else if (obj != null) {
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else {
                                            bjVar.b(str4, beVar2);
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        }
                                    }
                                }
                                if (beVar2 != null) {
                                    beVar2.b();
                                }
                                file.delete();
                                a.remove(a[i4]);
                                i5 = i2;
                                i2 = i3;
                                i3 = i5;
                            }
                        } else {
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        }
                    } else {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    }
                }
                i4++;
                i5 = i3;
                i3 = i2;
                i2 = i5;
            }
            c2.delete(0, c2.length());
            a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
            if (br.a(a2)) {
                return null;
            }
            return a2;
        }
        i = 1;
        c.p = hashtable.size();
        i2 = 0;
        i3 = 0;
        i4 = 0;
        while (i4 < a.length) {
            if (i4 < length) {
            }
            if (i4 >= length) {
            }
            if (i == 1) {
                if (i4 != 0) {
                    if (i4 != 0) {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                        i4++;
                        i5 = i3;
                        i3 = i2;
                        i2 = i5;
                    }
                    if (i4 > 25) {
                    }
                }
                c2.delete(0, c2.length());
                a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                if (br.a(a2)) {
                    return a2;
                }
                return null;
            } else if (i == 2) {
                if (i4 > 8) {
                    i5 = i2;
                    i2 = i3;
                    i3 = i5;
                    i4++;
                    i5 = i3;
                    i3 = i2;
                    i2 = i5;
                }
                if (i4 > 33) {
                    c2.delete(0, c2.length());
                    a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
                    if (br.a(a2)) {
                        return null;
                    }
                    return a2;
                }
            }
            stringBuilder = c2.toString();
            if (i4 < length) {
                switch (a4) {
                    case 1:
                        iArr[0] = 0;
                        iArr[1] = 0;
                        break;
                    case 2:
                        break;
                    default:
                        iArr[0] = 0;
                        iArr[1] = 0;
                        break;
                }
                stringBuilder = stringBuilder + a5 + File.separator;
                if (a[i4].startsWith("-")) {
                    stringBuilder = stringBuilder + a[i4].substring(0, 4) + ",";
                } else {
                    stringBuilder = stringBuilder + a[i4].substring(0, 3) + ",";
                }
                indexOf = a[i4].indexOf(",") + 1;
                if (a[i4].substring(indexOf, indexOf + 1).startsWith("-")) {
                    stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 4);
                } else {
                    stringBuilder = stringBuilder + a[i4].substring(indexOf, indexOf + 3);
                }
                str4 = (stringBuilder + File.separator) + a[i4];
                if (str4.equals(e)) {
                    i5 = i2;
                    i2 = i3;
                    i3 = i5;
                } else {
                    bjVar = f;
                    beVar = (be) bjVar.b(str4);
                    obj = null;
                    file = new File(str4);
                    if (beVar == null) {
                        obj = 1;
                        beVar2 = beVar;
                    } else if (!file.getParentFile().exists()) {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    } else if (file.isDirectory()) {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    } else if (file.exists()) {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    } else {
                        aVar = new a();
                        beVar = new be(file, aVar);
                        beVar2 = beVar;
                    }
                    i6 = 0;
                    if (beVar2 == null) {
                        i5 = i2;
                        i2 = i3;
                        i3 = i5;
                    } else {
                        beVar2.a(0);
                        c = beVar2.c();
                        if (i4 < length) {
                            i6 = beVar2.d();
                        }
                        g = beVar2.g();
                        i7 = 8;
                        if (i4 < length) {
                            i7 = ((i6 * 4) + 2) + 8;
                        }
                        if (c + 7776000000L < br.a()) {
                        }
                        if ((c + 7776000000L < br.a() ? 1 : null) != null) {
                            if (g > 8) {
                            }
                            if ((g > 8 ? 1 : null) == null) {
                                if ((g - ((long) i7)) % 16 == 0) {
                                    if (i4 < length) {
                                        if (i4 >= length) {
                                            if (obj2 == null) {
                                                i8 = i3;
                                                for (Entry entry2222222222 : hashtable.entrySet()) {
                                                    a3 = a(i7, beVar2, ((String) entry2222222222.getKey()).toString(), 0);
                                                    if (a3 == null) {
                                                        i8++;
                                                        hashtable.put(((String) entry2222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry2222222222.getKey()).toString())));
                                                        if (i8 < c.p) {
                                                            i6 = i8;
                                                        }
                                                    } else {
                                                        i6 = i8;
                                                    }
                                                    i8 = i6;
                                                }
                                            } else {
                                                i8 = i3;
                                            }
                                            if (obj3 != null) {
                                                it = hashtable2.entrySet().iterator();
                                                while (it != null) {
                                                    entry2222222222 = (Entry) it.next();
                                                    a3 = a(i7, beVar2, ((String) entry2222222222.getKey()).toString(), 1);
                                                    if (a3 == null) {
                                                        i2++;
                                                        hashtable2.put(((String) entry2222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry2222222222.getKey()).toString())));
                                                        if (i2 < 15) {
                                                            i6 = i2;
                                                        }
                                                    } else {
                                                        i6 = i2;
                                                    }
                                                    i2 = i6;
                                                }
                                            }
                                            i3 = i8;
                                            if (beVar2 == null) {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (!beVar2.a()) {
                                                beVar2.b();
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else if (obj != null) {
                                                bjVar.b(str4, beVar2);
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            } else {
                                                i5 = i2;
                                                i2 = i3;
                                                i3 = i5;
                                            }
                                        }
                                        if (obj2 == null) {
                                            i8 = i3;
                                        } else {
                                            i8 = i3;
                                            for (Entry entry22222222222 : hashtable.entrySet()) {
                                                a3 = a(i7, beVar2, ((String) entry22222222222.getKey()).toString(), 0);
                                                if (a3 == null) {
                                                    i6 = i8;
                                                } else {
                                                    i8++;
                                                    hashtable.put(((String) entry22222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry22222222222.getKey()).toString())));
                                                    if (i8 < c.p) {
                                                        i6 = i8;
                                                    }
                                                }
                                                i8 = i6;
                                            }
                                        }
                                        if (obj3 != null) {
                                            it = hashtable2.entrySet().iterator();
                                            while (it != null) {
                                                entry22222222222 = (Entry) it.next();
                                                a3 = a(i7, beVar2, ((String) entry22222222222.getKey()).toString(), 1);
                                                if (a3 == null) {
                                                    i6 = i2;
                                                } else {
                                                    i2++;
                                                    hashtable2.put(((String) entry22222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry22222222222.getKey()).toString())));
                                                    if (i2 < 15) {
                                                        i6 = i2;
                                                    }
                                                }
                                                i2 = i6;
                                            }
                                        }
                                        i3 = i8;
                                        if (beVar2 == null) {
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else if (!beVar2.a()) {
                                            beVar2.b();
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else if (obj != null) {
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else {
                                            bjVar.b(str4, beVar2);
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        }
                                    }
                                    if (i4 >= length) {
                                        if (obj2 == null) {
                                            i8 = i3;
                                            for (Entry entry222222222222 : hashtable.entrySet()) {
                                                a3 = a(i7, beVar2, ((String) entry222222222222.getKey()).toString(), 0);
                                                if (a3 == null) {
                                                    i8++;
                                                    hashtable.put(((String) entry222222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry222222222222.getKey()).toString())));
                                                    if (i8 < c.p) {
                                                        i6 = i8;
                                                    }
                                                } else {
                                                    i6 = i8;
                                                }
                                                i8 = i6;
                                            }
                                        } else {
                                            i8 = i3;
                                        }
                                        if (obj3 != null) {
                                            it = hashtable2.entrySet().iterator();
                                            while (it != null) {
                                                entry222222222222 = (Entry) it.next();
                                                a3 = a(i7, beVar2, ((String) entry222222222222.getKey()).toString(), 1);
                                                if (a3 == null) {
                                                    i2++;
                                                    hashtable2.put(((String) entry222222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry222222222222.getKey()).toString())));
                                                    if (i2 < 15) {
                                                        i6 = i2;
                                                    }
                                                } else {
                                                    i6 = i2;
                                                }
                                                i2 = i6;
                                            }
                                        }
                                        i3 = i8;
                                        if (beVar2 == null) {
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else if (!beVar2.a()) {
                                            beVar2.b();
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else if (obj != null) {
                                            bjVar.b(str4, beVar2);
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        } else {
                                            i5 = i2;
                                            i2 = i3;
                                            i3 = i5;
                                        }
                                    }
                                    if (obj2 == null) {
                                        i8 = i3;
                                    } else {
                                        i8 = i3;
                                        for (Entry entry2222222222222 : hashtable.entrySet()) {
                                            a3 = a(i7, beVar2, ((String) entry2222222222222.getKey()).toString(), 0);
                                            if (a3 == null) {
                                                i6 = i8;
                                            } else {
                                                i8++;
                                                hashtable.put(((String) entry2222222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable.get(((String) entry2222222222222.getKey()).toString())));
                                                if (i8 < c.p) {
                                                    i6 = i8;
                                                }
                                            }
                                            i8 = i6;
                                        }
                                    }
                                    if (obj3 != null) {
                                        it = hashtable2.entrySet().iterator();
                                        while (it != null) {
                                            entry2222222222222 = (Entry) it.next();
                                            a3 = a(i7, beVar2, ((String) entry2222222222222.getKey()).toString(), 1);
                                            if (a3 == null) {
                                                i6 = i2;
                                            } else {
                                                i2++;
                                                hashtable2.put(((String) entry2222222222222.getKey()).toString(), ((a3[0] + "|" + a3[1]) + "|" + a3[2] + "|") + ((String) hashtable2.get(((String) entry2222222222222.getKey()).toString())));
                                                if (i2 < 15) {
                                                    i6 = i2;
                                                }
                                            }
                                            i2 = i6;
                                        }
                                    }
                                    i3 = i8;
                                    if (beVar2 == null) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else if (!beVar2.a()) {
                                        beVar2.b();
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else if (obj != null) {
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    } else {
                                        bjVar.b(str4, beVar2);
                                        i5 = i2;
                                        i2 = i3;
                                        i3 = i5;
                                    }
                                }
                            }
                            if (beVar2 != null) {
                                beVar2.b();
                            }
                            file.delete();
                            a.remove(a[i4]);
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        } else {
                            if (beVar2 != null) {
                                if (obj != null) {
                                    bjVar.c(str4);
                                } else {
                                    beVar2.b();
                                }
                            }
                            file.delete();
                            a.remove(a[i4]);
                            i5 = i2;
                            i2 = i3;
                            i3 = i5;
                        }
                    }
                }
            } else {
                i5 = i2;
                i2 = i3;
                i3 = i5;
            }
            i4++;
            i5 = i3;
            i3 = i2;
            i2 = i5;
        }
        c2.delete(0, c2.length());
        a2 = a(hashtable, hashtable2, iArr[0], iArr[1]);
        if (br.a(a2)) {
            return a2;
        }
        return null;
    }

    private static int a(String str) {
        if (TextUtils.isEmpty(str) || !str.contains("cgi")) {
            return 9;
        }
        String[] split = str.split("#");
        if (split.length == 7) {
            return 1;
        }
        if (split.length == 8) {
            return 2;
        }
        return 9;
    }

    private static String a(int i, String str) {
        String[] split = str.split("#");
        switch (i) {
            case 1:
                return split[1] + "_" + split[2];
            case 2:
                return split[3];
            default:
                return null;
        }
    }

    private static void a(int i, String str, String str2, Hashtable<String, String> hashtable) {
        String[] split = str.split("#");
        switch (i) {
            case 1:
                hashtable.put(split[3] + "|" + split[4], "access");
                if (!TextUtils.isEmpty(str2) && str2.split("#").length <= 0) {
                }
            case 2:
                hashtable.put(split[3] + "|" + split[4] + "|" + split[5], "access");
            default:
        }
    }

    private static void a(String str, Hashtable<String, String> hashtable) {
        if (!TextUtils.isEmpty(str)) {
            String[] strArr = new String[2];
            for (String str2 : str.split("#")) {
                if (str2.contains(",")) {
                    String[] split = str2.split(",");
                    hashtable.put(split[0], split[1]);
                }
            }
        }
    }

    private static AmapLoc a(Hashtable<String, String> hashtable, Hashtable<String, String> hashtable2, int i, int i2) {
        ArrayList a;
        AmapLoc amapLoc;
        au auVar = new au();
        try {
            String str;
            if (!hashtable.isEmpty()) {
                for (Entry value : hashtable.entrySet()) {
                    int i3;
                    str = (String) value.getValue();
                    if (str.contains("access")) {
                        i3 = 1;
                    } else {
                        i3 = 0;
                    }
                    if (str.contains("|")) {
                        int i4;
                        String substring = str.substring(0, str.lastIndexOf("|"));
                        if (i3 == 0) {
                            i4 = 2;
                        } else {
                            i4 = 1;
                        }
                        try {
                            auVar.a(i4, substring);
                        } catch (Throwable th) {
                        }
                    }
                }
            }
            if (!hashtable2.isEmpty()) {
                for (Entry value2 : hashtable2.entrySet()) {
                    str = (String) value2.getValue();
                    if (str.contains("|")) {
                        try {
                            auVar.a(0, str.substring(0, str.lastIndexOf("|")));
                        } catch (Throwable th2) {
                        }
                    }
                }
            }
        } catch (Throwable th3) {
        }
        try {
            a = auVar.a((double) i2, (double) i);
        } catch (Throwable th4) {
            a = null;
        }
        if (a == null || a.isEmpty()) {
            amapLoc = null;
        } else {
            c cVar = (c) a.get(0);
            AmapLoc amapLoc2 = new AmapLoc();
            amapLoc2.c("network");
            amapLoc2.b(cVar.a);
            amapLoc2.a(cVar.b);
            amapLoc2.a((float) cVar.c);
            amapLoc2.k(cVar.d);
            amapLoc2.v("0");
            amapLoc2.a(br.a());
            a.clear();
            amapLoc = amapLoc2;
        }
        if (!br.a(amapLoc)) {
            return null;
        }
        amapLoc.f("file");
        return amapLoc;
    }

    public static boolean a(Context context, String str, String str2, int i, int i2, boolean z, boolean z2) {
        if (!a(context, str, i, false, z)) {
            return false;
        }
        if (i2 == 0) {
            return a(context, str, str2, i, z2);
        }
        int i3;
        if (i2 != 1) {
            i3 = 24;
        } else {
            i3 = 8;
        }
        String[] a = a(0.0d, 0.0d, str2);
        for (int i4 = 1; i4 < i3; i4++) {
            a(context, str, a[i4], i, z2);
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(android.content.Context r11, java.lang.String r12, java.lang.String r13, int r14, boolean r15) {
        /*
        r0 = 2;
        r6 = new java.lang.String[r0];
        r0 = a(r12, r13, r14, r6);
        if (r0 == 0) goto L_0x0065;
    L_0x0009:
        r0 = a;
        r1 = 1;
        r1 = r6[r1];
        r0 = r0.containsKey(r1);
        if (r0 != 0) goto L_0x0067;
    L_0x0014:
        r1 = 0;
        r4 = 0;
        r3 = 0;
        r0 = 0;
        com.loc.br.b();	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r2 = new java.util.HashMap;	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r2.<init>();	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r5 = "v";
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r7 = java.lang.String.valueOf(r7);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r2.put(r5, r7);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r5 = com.loc.bl.a(r11);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r7 = "https://offline.aps.amap.com/LoadOfflineData/getData";
        r8 = 0;
        r8 = r6[r8];	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r9 = "UTF-8";
        r8 = r8.getBytes(r9);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        r2 = r5.a(r11, r7, r2, r8);	 Catch:{ UnknownHostException -> 0x02ed, SocketException -> 0x02d8, SocketTimeoutException -> 0x02c0, EOFException -> 0x022e, Exception -> 0x024c, all -> 0x026a }
        if (r2 == 0) goto L_0x0093;
    L_0x0043:
        r0 = r2.getResponseCode();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 == r5) goto L_0x009d;
    L_0x004b:
        r5 = 404; // 0x194 float:5.66E-43 double:1.996E-321;
        if (r0 == r5) goto L_0x0304;
    L_0x004f:
        r0 = r3;
        r3 = r1;
        r1 = r4;
    L_0x0052:
        com.loc.br.b();	 Catch:{ UnknownHostException -> 0x00d1, SocketException -> 0x02e7, SocketTimeoutException -> 0x02d2, EOFException -> 0x02ba, Exception -> 0x02a7, all -> 0x0296 }
        if (r0 != 0) goto L_0x01eb;
    L_0x0057:
        if (r1 != 0) goto L_0x01f3;
    L_0x0059:
        if (r2 != 0) goto L_0x01fb;
    L_0x005b:
        r1 = r3;
    L_0x005c:
        r0 = e;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x0288;
    L_0x0064:
        return r1;
    L_0x0065:
        r0 = 0;
        return r0;
    L_0x0067:
        r0 = a;
        r1 = 1;
        r1 = r6[r1];
        r0 = r0.get(r1);
        r0 = (java.lang.Long) r0;
        r0 = r0.longValue();
        r2 = com.loc.br.a();
        r0 = r2 - r0;
        r2 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 < 0) goto L_0x0088;
    L_0x0083:
        r0 = 1;
    L_0x0084:
        if (r0 != 0) goto L_0x008a;
    L_0x0086:
        r0 = 0;
        return r0;
    L_0x0088:
        r0 = 0;
        goto L_0x0084;
    L_0x008a:
        r0 = a;
        r1 = 1;
        r1 = r6[r1];
        r0.remove(r1);
        goto L_0x0014;
    L_0x0093:
        if (r2 != 0) goto L_0x0097;
    L_0x0095:
        r0 = 0;
        return r0;
    L_0x0097:
        r2.disconnect();	 Catch:{ Exception -> 0x009b }
        goto L_0x0095;
    L_0x009b:
        r0 = move-exception;
        goto L_0x0095;
    L_0x009d:
        r5 = 0;
        r0 = r2.getHeaderFields();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0 = r0.entrySet();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r7 = r0.iterator();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
    L_0x00aa:
        r0 = r7.hasNext();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        if (r0 != 0) goto L_0x00e2;
    L_0x00b0:
        r0 = r5;
    L_0x00b1:
        r5 = 260; // 0x104 float:3.64E-43 double:1.285E-321;
        if (r0 == r5) goto L_0x0107;
    L_0x00b5:
        r0 = a;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r5 = 1;
        r5 = r6[r5];	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r8 = com.loc.br.a();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r7 = java.lang.Long.valueOf(r8);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0.put(r5, r7);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0 = r3;
        r3 = r1;
        r1 = r4;
    L_0x00c8:
        if (r15 == 0) goto L_0x0052;
    L_0x00ca:
        r4 = 1;
        r4 = r6[r4];	 Catch:{ UnknownHostException -> 0x00d1, SocketException -> 0x02e7, SocketTimeoutException -> 0x02d2, EOFException -> 0x02ba, Exception -> 0x02a7, all -> 0x0296 }
        b(r4);	 Catch:{ UnknownHostException -> 0x00d1, SocketException -> 0x02e7, SocketTimeoutException -> 0x02d2, EOFException -> 0x02ba, Exception -> 0x02a7, all -> 0x0296 }
        goto L_0x0052;
    L_0x00d1:
        r4 = move-exception;
        r10 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r10;
    L_0x00d6:
        if (r1 != 0) goto L_0x0203;
    L_0x00d8:
        if (r2 != 0) goto L_0x020b;
    L_0x00da:
        if (r0 == 0) goto L_0x005b;
    L_0x00dc:
        r0.disconnect();	 Catch:{ Exception -> 0x0213 }
    L_0x00df:
        r1 = r3;
        goto L_0x005c;
    L_0x00e2:
        r0 = r7.next();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r8 = "code";
        r9 = r0.getKey();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r8 = r8.equals(r9);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        if (r8 == 0) goto L_0x00aa;
    L_0x00f5:
        r0 = r0.getValue();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0 = (java.util.List) r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r5 = 0;
        r0 = r0.get(r5);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0 = (java.lang.String) r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0 = java.lang.Integer.parseInt(r0);	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        goto L_0x00b1;
    L_0x0107:
        r0 = 1;
        r0 = r6[r0];	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        e = r0;	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r0 = 1;
        r5 = r2.getInputStream();	 Catch:{ UnknownHostException -> 0x02f4, SocketException -> 0x02de, SocketTimeoutException -> 0x02c6, EOFException -> 0x02ac, Exception -> 0x029b, all -> 0x028d }
        r4 = new java.util.zip.GZIPInputStream;	 Catch:{ UnknownHostException -> 0x02fc, SocketException -> 0x02e3, SocketTimeoutException -> 0x02cb, EOFException -> 0x02b0, Exception -> 0x029f, all -> 0x0291 }
        r4.<init>(r5);	 Catch:{ UnknownHostException -> 0x02fc, SocketException -> 0x02e3, SocketTimeoutException -> 0x02cb, EOFException -> 0x02b0, Exception -> 0x029f, all -> 0x0291 }
        r3 = new java.io.File;	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r7 = 1;
        r7 = r6[r7];	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r3.<init>(r7);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r7 = r3.exists();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        if (r7 != 0) goto L_0x012b;
    L_0x0124:
        if (r0 != 0) goto L_0x0133;
    L_0x0126:
        r0 = r1;
    L_0x0127:
        r1 = r5;
        r3 = r0;
        r0 = r4;
        goto L_0x00c8;
    L_0x012b:
        r7 = r3.delete();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        if (r7 != 0) goto L_0x0124;
    L_0x0131:
        r0 = 0;
        goto L_0x0124;
    L_0x0133:
        r0 = r3.getParentFile();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r7 = r0.exists();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        if (r7 == 0) goto L_0x019d;
    L_0x013d:
        r0 = new java.io.FileOutputStream;	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r0.<init>(r3);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r3 = new java.io.BufferedOutputStream;	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r7 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r3.<init>(r0, r7);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r0 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r0 = new byte[r0];	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
    L_0x014d:
        r7 = 0;
        r8 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r7 = r4.read(r0, r7, r8);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r8 = -1;
        if (r7 != r8) goto L_0x01a8;
    L_0x0157:
        r3.flush();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r3.close();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        r0 = 1;
        r1 = a;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = 1;
        r3 = r6[r3];	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r8 = com.loc.br.a();	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r7 = java.lang.Long.valueOf(r8);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r1.put(r3, r7);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r8 = 0;
        r1 = "yyyyMMdd";
        r1 = com.loc.br.a(r8, r1);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = c;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r7 = 0;
        r3 = r3[r7];	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = java.lang.String.valueOf(r3);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = r1.equals(r3);	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        if (r3 != 0) goto L_0x01bc;
    L_0x0186:
        r3 = c;	 Catch:{ Exception -> 0x01cd, UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, all -> 0x0294 }
        r7 = 0;
        r1 = java.lang.Integer.parseInt(r1);	 Catch:{ Exception -> 0x01cd, UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, all -> 0x0294 }
        r3[r7] = r1;	 Catch:{ Exception -> 0x01cd, UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, all -> 0x0294 }
    L_0x018f:
        r1 = c;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = 1;
        r7 = 1;
        r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        goto L_0x0127;
    L_0x0196:
        r1 = move-exception;
        r1 = r4;
        r3 = r0;
        r0 = r2;
        r2 = r5;
        goto L_0x00d6;
    L_0x019d:
        r0.mkdirs();	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        goto L_0x013d;
    L_0x01a1:
        r0 = move-exception;
        r0 = r2;
        r3 = r1;
        r2 = r5;
        r1 = r4;
        goto L_0x00d6;
    L_0x01a8:
        r8 = 0;
        r3.write(r0, r8, r7);	 Catch:{ UnknownHostException -> 0x01a1, SocketException -> 0x01ad, SocketTimeoutException -> 0x02cf, EOFException -> 0x02b3, Exception -> 0x02a2, all -> 0x0294 }
        goto L_0x014d;
    L_0x01ad:
        r0 = move-exception;
    L_0x01ae:
        if (r4 != 0) goto L_0x0216;
    L_0x01b0:
        if (r5 != 0) goto L_0x021c;
    L_0x01b2:
        if (r2 == 0) goto L_0x005c;
    L_0x01b4:
        r2.disconnect();	 Catch:{ Exception -> 0x01b9 }
        goto L_0x005c;
    L_0x01b9:
        r0 = move-exception;
        goto L_0x005c;
    L_0x01bc:
        r1 = c;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = 1;
        r7 = c;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r8 = 1;
        r7 = r7[r8];	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r7 = r7 + 1;
        r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        goto L_0x0127;
    L_0x01ca:
        r1 = move-exception;
        r1 = r0;
        goto L_0x01ae;
    L_0x01cd:
        r1 = move-exception;
        r1 = c;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = 0;
        r7 = 0;
        r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r1 = c;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        r3 = 1;
        r7 = 0;
        r1[r3] = r7;	 Catch:{ UnknownHostException -> 0x0196, SocketException -> 0x01ca, SocketTimeoutException -> 0x01db, EOFException -> 0x02b6, Exception -> 0x02a4, all -> 0x0294 }
        goto L_0x018f;
    L_0x01db:
        r1 = move-exception;
        r1 = r0;
    L_0x01dd:
        if (r4 != 0) goto L_0x0222;
    L_0x01df:
        if (r5 != 0) goto L_0x0228;
    L_0x01e1:
        if (r2 == 0) goto L_0x005c;
    L_0x01e3:
        r2.disconnect();	 Catch:{ Exception -> 0x01e8 }
        goto L_0x005c;
    L_0x01e8:
        r0 = move-exception;
        goto L_0x005c;
    L_0x01eb:
        r0.close();	 Catch:{ Exception -> 0x01f0 }
        goto L_0x0057;
    L_0x01f0:
        r0 = move-exception;
        goto L_0x0057;
    L_0x01f3:
        r1.close();	 Catch:{ Exception -> 0x01f8 }
        goto L_0x0059;
    L_0x01f8:
        r0 = move-exception;
        goto L_0x0059;
    L_0x01fb:
        r2.disconnect();	 Catch:{ Exception -> 0x0200 }
        goto L_0x00df;
    L_0x0200:
        r0 = move-exception;
        goto L_0x00df;
    L_0x0203:
        r1.close();	 Catch:{ Exception -> 0x0208 }
        goto L_0x00d8;
    L_0x0208:
        r1 = move-exception;
        goto L_0x00d8;
    L_0x020b:
        r2.close();	 Catch:{ Exception -> 0x0210 }
        goto L_0x00da;
    L_0x0210:
        r1 = move-exception;
        goto L_0x00da;
    L_0x0213:
        r0 = move-exception;
        goto L_0x00df;
    L_0x0216:
        r4.close();	 Catch:{ Exception -> 0x021a }
        goto L_0x01b0;
    L_0x021a:
        r0 = move-exception;
        goto L_0x01b0;
    L_0x021c:
        r5.close();	 Catch:{ Exception -> 0x0220 }
        goto L_0x01b2;
    L_0x0220:
        r0 = move-exception;
        goto L_0x01b2;
    L_0x0222:
        r4.close();	 Catch:{ Exception -> 0x0226 }
        goto L_0x01df;
    L_0x0226:
        r0 = move-exception;
        goto L_0x01df;
    L_0x0228:
        r5.close();	 Catch:{ Exception -> 0x022c }
        goto L_0x01e1;
    L_0x022c:
        r0 = move-exception;
        goto L_0x01e1;
    L_0x022e:
        r2 = move-exception;
        r2 = r0;
        r5 = r4;
        r4 = r3;
    L_0x0232:
        if (r4 != 0) goto L_0x0240;
    L_0x0234:
        if (r5 != 0) goto L_0x0246;
    L_0x0236:
        if (r2 == 0) goto L_0x005c;
    L_0x0238:
        r2.disconnect();	 Catch:{ Exception -> 0x023d }
        goto L_0x005c;
    L_0x023d:
        r0 = move-exception;
        goto L_0x005c;
    L_0x0240:
        r4.close();	 Catch:{ Exception -> 0x0244 }
        goto L_0x0234;
    L_0x0244:
        r0 = move-exception;
        goto L_0x0234;
    L_0x0246:
        r5.close();	 Catch:{ Exception -> 0x024a }
        goto L_0x0236;
    L_0x024a:
        r0 = move-exception;
        goto L_0x0236;
    L_0x024c:
        r2 = move-exception;
        r2 = r0;
        r5 = r4;
        r4 = r3;
    L_0x0250:
        if (r4 != 0) goto L_0x025e;
    L_0x0252:
        if (r5 != 0) goto L_0x0264;
    L_0x0254:
        if (r2 == 0) goto L_0x005c;
    L_0x0256:
        r2.disconnect();	 Catch:{ Exception -> 0x025b }
        goto L_0x005c;
    L_0x025b:
        r0 = move-exception;
        goto L_0x005c;
    L_0x025e:
        r4.close();	 Catch:{ Exception -> 0x0262 }
        goto L_0x0252;
    L_0x0262:
        r0 = move-exception;
        goto L_0x0252;
    L_0x0264:
        r5.close();	 Catch:{ Exception -> 0x0268 }
        goto L_0x0254;
    L_0x0268:
        r0 = move-exception;
        goto L_0x0254;
    L_0x026a:
        r1 = move-exception;
        r2 = r0;
        r5 = r4;
        r4 = r3;
        r0 = r1;
    L_0x026f:
        if (r4 != 0) goto L_0x0276;
    L_0x0271:
        if (r5 != 0) goto L_0x027c;
    L_0x0273:
        if (r2 != 0) goto L_0x0282;
    L_0x0275:
        throw r0;
    L_0x0276:
        r4.close();	 Catch:{ Exception -> 0x027a }
        goto L_0x0271;
    L_0x027a:
        r1 = move-exception;
        goto L_0x0271;
    L_0x027c:
        r5.close();	 Catch:{ Exception -> 0x0280 }
        goto L_0x0273;
    L_0x0280:
        r1 = move-exception;
        goto L_0x0273;
    L_0x0282:
        r2.disconnect();	 Catch:{ Exception -> 0x0286 }
        goto L_0x0275;
    L_0x0286:
        r1 = move-exception;
        goto L_0x0275;
    L_0x0288:
        r0 = 0;
        e = r0;
        goto L_0x0064;
    L_0x028d:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x026f;
    L_0x0291:
        r0 = move-exception;
        r4 = r3;
        goto L_0x026f;
    L_0x0294:
        r0 = move-exception;
        goto L_0x026f;
    L_0x0296:
        r3 = move-exception;
        r4 = r0;
        r5 = r1;
        r0 = r3;
        goto L_0x026f;
    L_0x029b:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x0250;
    L_0x029f:
        r0 = move-exception;
        r4 = r3;
        goto L_0x0250;
    L_0x02a2:
        r0 = move-exception;
        goto L_0x0250;
    L_0x02a4:
        r1 = move-exception;
        r1 = r0;
        goto L_0x0250;
    L_0x02a7:
        r4 = move-exception;
        r4 = r0;
        r5 = r1;
        r1 = r3;
        goto L_0x0250;
    L_0x02ac:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x0232;
    L_0x02b0:
        r0 = move-exception;
        r4 = r3;
        goto L_0x0232;
    L_0x02b3:
        r0 = move-exception;
        goto L_0x0232;
    L_0x02b6:
        r1 = move-exception;
        r1 = r0;
        goto L_0x0232;
    L_0x02ba:
        r4 = move-exception;
        r4 = r0;
        r5 = r1;
        r1 = r3;
        goto L_0x0232;
    L_0x02c0:
        r2 = move-exception;
        r2 = r0;
        r5 = r4;
        r4 = r3;
        goto L_0x01dd;
    L_0x02c6:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x01dd;
    L_0x02cb:
        r0 = move-exception;
        r4 = r3;
        goto L_0x01dd;
    L_0x02cf:
        r0 = move-exception;
        goto L_0x01dd;
    L_0x02d2:
        r4 = move-exception;
        r4 = r0;
        r5 = r1;
        r1 = r3;
        goto L_0x01dd;
    L_0x02d8:
        r2 = move-exception;
        r2 = r0;
        r5 = r4;
        r4 = r3;
        goto L_0x01ae;
    L_0x02de:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x01ae;
    L_0x02e3:
        r0 = move-exception;
        r4 = r3;
        goto L_0x01ae;
    L_0x02e7:
        r4 = move-exception;
        r4 = r0;
        r5 = r1;
        r1 = r3;
        goto L_0x01ae;
    L_0x02ed:
        r2 = move-exception;
        r2 = r4;
        r10 = r3;
        r3 = r1;
        r1 = r10;
        goto L_0x00d6;
    L_0x02f4:
        r0 = move-exception;
        r0 = r2;
        r2 = r4;
        r10 = r3;
        r3 = r1;
        r1 = r10;
        goto L_0x00d6;
    L_0x02fc:
        r0 = move-exception;
        r0 = r2;
        r2 = r5;
        r10 = r3;
        r3 = r1;
        r1 = r10;
        goto L_0x00d6;
    L_0x0304:
        r0 = r3;
        r3 = r1;
        r1 = r4;
        goto L_0x0052;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.az.a(android.content.Context, java.lang.String, java.lang.String, int, boolean):boolean");
    }

    public static void a() {
        f.a();
        a.clear();
        b.clear();
        c[0] = 0;
        c[1] = 0;
    }

    private static double[] a(int i, be beVar, String str, int i2) {
        String str2;
        int i3;
        int[] iArr = null;
        if (i2 != 0) {
            str2 = "wifi";
        } else {
            String[] split = str.split("\\|");
            iArr = new int[split.length];
            for (i3 = 0; i3 < split.length; i3++) {
                iArr[i3] = Integer.parseInt(split[i3]);
            }
            str2 = split.length != 2 ? "cdma" : "gsm";
        }
        try {
            if ((beVar.g() <= ((long) i) ? 1 : null) != null) {
                return null;
            }
            beVar.a((long) i);
            i3 = a(i, beVar, str, iArr, i, ((int) beVar.g()) - 16, str2, 0);
            if (i3 == -1) {
                return null;
            }
            beVar.a((long) (i3 + 6));
            double[] dArr = new double[3];
            try {
                dArr[0] = ((double) a(beVar.e())) / 1000000.0d;
                dArr[1] = ((double) a(beVar.e())) / 1000000.0d;
                dArr[2] = (double) beVar.d();
                if (!br.a(dArr[1])) {
                    return null;
                }
                if (br.b(dArr[0])) {
                    return dArr;
                }
                return null;
            } catch (Throwable th) {
                return dArr;
            }
        } catch (Throwable th2) {
            return null;
        }
    }

    private static int a(int i, be beVar, String str, int[] iArr, int i2, int i3, String str2, int i4) {
        int i5 = i4 + 1;
        if (i5 > 25) {
            return -1;
        }
        int i6 = (((((i2 + i3) / 2) - i) / 16) * 16) + i;
        int a = a(beVar, str, iArr, i6, str2);
        if (i2 == i6 && i6 == i3) {
            if (a != 0) {
                i2 = -1;
            }
            return i2;
        } else if (a == Integer.MAX_VALUE) {
            return -1;
        } else {
            if (a == 0) {
                return i6;
            }
            if (a < 0) {
                return a(i, beVar, str, iArr, i2, i6, str2, i5);
            }
            return a(i, beVar, str, iArr, i6 + 16, i3, str2, i5);
        }
    }

    private static int a(be beVar, String str, int[] iArr, int i, String str2) {
        try {
            beVar.a((long) i);
            int i2;
            int i3;
            if (str2.equals("gsm")) {
                i2 = iArr[0];
                i3 = iArr[1];
                int d = beVar.d();
                int e = beVar.e();
                if (i2 < d) {
                    return -1;
                }
                if (i2 > d) {
                    return 1;
                }
                if (i3 >= e) {
                    return i3 <= e ? 0 : 1;
                } else {
                    return -1;
                }
            } else if (str2.equals("cdma")) {
                r2 = new int[]{iArr[0], iArr[1], iArr[2]};
                int[] iArr2 = new int[3];
                for (i2 = 0; i2 < 3; i2++) {
                    iArr2[i2] = beVar.d();
                    if (r2[i2] < iArr2[i2]) {
                        return -1;
                    }
                    if (r2[i2] > iArr2[i2]) {
                        return 1;
                    }
                }
                return 0;
            } else {
                if (str2.equals("wifi")) {
                    byte[] b = br.b(str);
                    int[] iArr3 = new int[6];
                    i3 = 0;
                    while (i3 < 6) {
                        iArr3[i3] = b[i3] >= null ? b[i3] : b[i3] + 256;
                        i3++;
                    }
                    r2 = new int[6];
                    for (i2 = 0; i2 < 6; i2++) {
                        r2[i2] = beVar.f();
                        if (iArr3[i2] < r2[i2]) {
                            return -1;
                        }
                        if (iArr3[i2] > r2[i2]) {
                            return 1;
                        }
                    }
                    return 0;
                }
                return Integer.MAX_VALUE;
            }
        } catch (Exception e2) {
        }
    }

    private static void b(String str) {
        Throwable th;
        RandomAccessFile randomAccessFile = null;
        if (!b.containsKey(str) || TextUtils.isEmpty((CharSequence) b.get(str))) {
            File file = new File(str);
            if (file.exists() && file.isFile()) {
                RandomAccessFile randomAccessFile2;
                try {
                    randomAccessFile2 = new RandomAccessFile(file, "r");
                    try {
                        randomAccessFile2.seek(8);
                        int readUnsignedShort = randomAccessFile2.readUnsignedShort();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < readUnsignedShort; i++) {
                            int readInt = randomAccessFile2.readInt();
                            if (stringBuilder.indexOf("," + readInt) == -1) {
                                stringBuilder.append(",").append(readInt);
                            }
                            if (i == readUnsignedShort - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        b.put(str, stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                        if (randomAccessFile2 != null) {
                            try {
                                randomAccessFile2.close();
                            } catch (Exception e) {
                            }
                        }
                    } catch (FileNotFoundException e2) {
                    } catch (Exception e3) {
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        randomAccessFile = randomAccessFile2;
                        th = th3;
                    }
                } catch (FileNotFoundException e4) {
                    randomAccessFile2 = null;
                    if (randomAccessFile2 != null) {
                        try {
                            randomAccessFile2.close();
                        } catch (Exception e5) {
                        }
                    }
                } catch (Exception e6) {
                    randomAccessFile2 = null;
                    if (randomAccessFile2 != null) {
                        try {
                            randomAccessFile2.close();
                        } catch (Exception e7) {
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        } catch (Exception e8) {
                        }
                    }
                    throw th;
                }
            }
        }
    }

    public static ArrayList<String> a(String str, boolean z) {
        ArrayList<String> arrayList = null;
        if (b.isEmpty()) {
            return null;
        }
        int a = a(str);
        String[] split = str.split("#");
        switch (a) {
            case 1:
            case 2:
                ArrayList<String> arrayList2 = null;
                for (String str2 : b.keySet()) {
                    if (((String) b.get(str2)).contains("," + split[3] + ",")) {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                        }
                        arrayList2.add(str2);
                        if (z) {
                            return arrayList2;
                        }
                        arrayList = arrayList2;
                    } else {
                        arrayList = arrayList2;
                    }
                    arrayList2 = arrayList;
                }
                arrayList = arrayList2;
                break;
        }
        return arrayList;
    }

    public static boolean b() {
        return !b.isEmpty();
    }

    public static boolean a(String str, String str2, int i, int i2) {
        boolean z = true;
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        if (i2 == 0) {
            String a = a(str, str2, i);
            if (a != null) {
                File file = new File(a);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                if (b.containsKey(a)) {
                    b.remove(a);
                }
                if (a.containsKey(a)) {
                    a.remove(a);
                }
            }
            return true;
        } else if (i2 != 1 && i2 != 2) {
            return false;
        } else {
            String[] a2 = a(0.0d, 0.0d, str2);
            boolean z2 = i2 != 1 ? i2 != 2 ? false : true : true;
            if (i == 1) {
                z = false;
            } else if (i != 2) {
                return false;
            }
            Hashtable hashtable = b;
            Hashtable hashtable2 = a;
            for (z = 
            /* Method generation error in method: com.loc.az.a(java.lang.String, java.lang.String, int, int):boolean
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r0_2 'z' boolean) = (r0_0 'z' boolean), (r0_1 'z' boolean) binds: {(r0_1 'z' boolean)=B:30:0x0060, (r0_0 'z' boolean)=B:9:0x001c} in method: com.loc.az.a(java.lang.String, java.lang.String, int, int):boolean
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:225)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:128)
	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:146)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:124)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:177)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:324)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:263)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:226)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:116)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:81)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: jadx.core.utils.exceptions.CodegenException: Unknown instruction: PHI in method: com.loc.az.a(java.lang.String, java.lang.String, int, int):boolean
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:512)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:219)
	... 26 more
 */

            private static String a (String str, String str2,int i){
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    return null;
                }
                StringBuilder c = c();
                int indexOf;
                switch (i) {
                    case 1:
                        c.append(a(a(str), str)).append(File.separator);
                        if (str2.startsWith("-")) {
                            c.append(str2.substring(0, 4));
                        } else {
                            c.append(str2.substring(0, 3));
                        }
                        c.append(",");
                        indexOf = str2.indexOf(",") + 1;
                        if (str2.substring(indexOf, indexOf + 1).startsWith("-")) {
                            c.append(str2.substring(indexOf, indexOf + 4));
                        } else {
                            c.append(str2.substring(indexOf, indexOf + 3));
                        }
                        c.append(File.separator).append(str2);
                        break;
                    case 2:
                        c.append("wifi").append(File.separator);
                        c.append(str2.substring(0, 3)).append(",");
                        indexOf = str2.indexOf(",") + 1;
                        c.append(str2.substring(indexOf, indexOf + 3));
                        c.append(File.separator).append(str2);
                        break;
                    default:
                        return null;
                }
                return c.toString();
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
        private static boolean a (java.lang.String r10, java.lang.String r11,int r12, java.
        lang.String[] r13){
                /*
                r4 = 0;
                r7 = 3;
                r9 = 1;
                r2 = 0;
                r8 = 0;
                r0 = android.text.TextUtils.isEmpty(r10);
                if (r0 == 0) goto L_0x000d;
            L_0x000c:
                return r8;
            L_0x000d:
                r0 = android.text.TextUtils.isEmpty(r11);
                if (r0 != 0) goto L_0x000c;
            L_0x0013:
                if (r13 != 0) goto L_0x0016;
            L_0x0015:
                return r8;
            L_0x0016:
                r0 = r13.length;
                r1 = 2;
                if (r0 != r1) goto L_0x0015;
            L_0x001a:
                r1 = c();
                switch(r12) {
                    case 1: goto L_0x0022;
                    case 2: goto L_0x00f1;
                    default: goto L_0x0021;
                };
            L_0x0021:
                return r8;
            L_0x0022:
                r0 = a(r10);
                r0 = a(r0, r10);
                r3 = r1.append(r0);
                r6 = java.io.File.separator;
                r3.append(r6);
                r3 = "-";
                r3 = r11.startsWith(r3);
                if (r3 != 0) goto L_0x00dc;
            L_0x003c:
                r3 = r11.substring(r8, r7);
                r1.append(r3);
            L_0x0043:
                r3 = ",";
                r1.append(r3);
                r3 = ",";
                r3 = r11.indexOf(r3);
                r3 = r3 + 1;
                r6 = r3 + 1;
                r6 = r11.substring(r3, r6);
                r7 = "-";
                r6 = r6.startsWith(r7);
                if (r6 != 0) goto L_0x00e6;
            L_0x0061:
                r6 = r3 + 3;
                r3 = r11.substring(r3, r6);
                r1.append(r3);
            L_0x006a:
                r3 = java.io.File.separator;
                r3 = r1.append(r3);
                r3.append(r11);
            L_0x0073:
                r3 = r1.toString();
                r13[r9] = r3;
                r3 = r1.length();
                r1.delete(r8, r3);
                r3 = new java.io.File;
                r1 = r13[r9];
                r3.<init>(r1);
                r1 = r3.exists();
                if (r1 != 0) goto L_0x0128;
            L_0x008d:
                r2 = r4;
            L_0x008e:
                r1 = new org.json.JSONObject;
                r1.<init>();
                r4 = "v";
                r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
                r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x016b }
                r1.put(r4, r5);	 Catch:{ Exception -> 0x016b }
                r4 = "geohash";
                r1.put(r4, r11);	 Catch:{ Exception -> 0x016b }
                r4 = "t";
                r2 = java.lang.String.valueOf(r2);	 Catch:{ Exception -> 0x016b }
                r1.put(r4, r2);	 Catch:{ Exception -> 0x016b }
                r2 = "type";
                r1.put(r2, r0);	 Catch:{ Exception -> 0x016b }
                r0 = "imei";
                r2 = com.loc.c.c;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
                r0 = "imsi";
                r2 = com.loc.c.d;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
                r0 = "src";
                r2 = com.loc.c.f;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
                r0 = "license";
                r2 = com.loc.c.g;	 Catch:{ Exception -> 0x016b }
                r1.put(r0, r2);	 Catch:{ Exception -> 0x016b }
            L_0x00d5:
                r0 = r1.toString();
                r13[r8] = r0;
                return r9;
            L_0x00dc:
                r3 = 4;
                r3 = r11.substring(r8, r3);
                r1.append(r3);
                goto L_0x0043;
            L_0x00e6:
                r6 = r3 + 4;
                r3 = r11.substring(r3, r6);
                r1.append(r3);
                goto L_0x006a;
            L_0x00f1:
                r0 = "wifi";
                r3 = r1.append(r0);
                r6 = java.io.File.separator;
                r3.append(r6);
                r3 = r11.substring(r8, r7);
                r3 = r1.append(r3);
                r6 = ",";
                r3.append(r6);
                r3 = ",";
                r3 = r11.indexOf(r3);
                r3 = r3 + 1;
                r6 = r3 + 3;
                r3 = r11.substring(r3, r6);
                r1.append(r3);
                r3 = java.io.File.separator;
                r3 = r1.append(r3);
                r3.append(r11);
                goto L_0x0073;
            L_0x0128:
                r1 = r3.isFile();
                if (r1 == 0) goto L_0x008d;
            L_0x012e:
                r1 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x0149, Exception -> 0x0158, all -> 0x0161 }
                r6 = "r";
                r1.<init>(r3, r6);	 Catch:{ FileNotFoundException -> 0x0149, Exception -> 0x0158, all -> 0x0161 }
                r2 = 0;
                r1.seek(r2);	 Catch:{ FileNotFoundException -> 0x0174, Exception -> 0x0171, all -> 0x016e }
                r2 = r1.readLong();	 Catch:{ FileNotFoundException -> 0x0174, Exception -> 0x0171, all -> 0x016e }
                if (r1 == 0) goto L_0x008e;
            L_0x0141:
                r1.close();	 Catch:{ Exception -> 0x0146 }
                goto L_0x008e;
            L_0x0146:
                r1 = move-exception;
                goto L_0x008e;
            L_0x0149:
                r1 = move-exception;
                r1 = r2;
            L_0x014b:
                if (r1 != 0) goto L_0x0150;
            L_0x014d:
                r2 = r4;
                goto L_0x008e;
            L_0x0150:
                r1.close();	 Catch:{ Exception -> 0x0154 }
                goto L_0x014d;
            L_0x0154:
                r1 = move-exception;
            L_0x0155:
                r2 = r4;
                goto L_0x008e;
            L_0x0158:
                r1 = move-exception;
            L_0x0159:
                if (r2 == 0) goto L_0x014d;
            L_0x015b:
                r2.close();	 Catch:{ Exception -> 0x015f }
                goto L_0x014d;
            L_0x015f:
                r1 = move-exception;
                goto L_0x0155;
            L_0x0161:
                r0 = move-exception;
            L_0x0162:
                if (r2 != 0) goto L_0x0165;
            L_0x0164:
                throw r0;
            L_0x0165:
                r2.close();	 Catch:{ Exception -> 0x0169 }
                goto L_0x0164;
            L_0x0169:
                r1 = move-exception;
                goto L_0x0164;
            L_0x016b:
                r0 = move-exception;
                goto L_0x00d5;
            L_0x016e:
                r0 = move-exception;
                r2 = r1;
                goto L_0x0162;
            L_0x0171:
                r2 = move-exception;
                r2 = r1;
                goto L_0x0159;
            L_0x0174:
                r2 = move-exception;
                goto L_0x014b;
                */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.az.a(java.lang.String, java.lang.String, int, java.lang.String[]):boolean");
        }

        private static boolean a (Context context, String str,int i, boolean z, boolean z2){
            boolean z3 = z ? i >= 25 : i != 1;
            if (!str.contains("cgi") && z3) {
                return false;
            }
            if ((!str.contains("wifi") && !z3) || c[1] > 2000) {
                return false;
            }
            NetworkInfo c = br.c(context);
            if (bl.a(c) == -1) {
                return false;
            }
            if (c.getType() != 1 && z2) {
                return false;
            }
            if (!(c.getType() == 1 || z2 || g != null)) {
                g = (TelephonyManager) br.a(context, "phone");
            }
            return true;
        }

        private static StringBuilder c () {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(br.e());
            stringBuilder.append("offline").append(File.separator);
            stringBuilder.append(br.j()).append(File.separator).append("s").append(File.separator);
            return stringBuilder;
        }

        static int a ( int i){
            int i2 = 0;
            int[] iArr = new int[32];
            int i3 = 0;
            while (i2 < 4) {
                iArr[i2] = (i >> (i2 * 8)) & 255;
                iArr[i2] = ((iArr[i2] << 4) & 240) + ((iArr[i2] >> 4) & 15);
                i3 += (iArr[i2] & 255) << ((3 - i2) * 8);
                i2++;
            }
            return d + i3;
        }
    }
