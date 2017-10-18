package com.loc;

import android.content.Context;
import android.text.TextUtils;

import com.autonavi.aps.amapapi.model.AmapLoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONObject;

/* compiled from: HeatMap */
public class bi {
    private static bi a;
    private Hashtable<String, JSONObject> b;
    private boolean c;

    static {
        a = null;
    }

    private bi() {
        this.b = new Hashtable();
        this.c = false;
    }

    public static synchronized bi a() {
        bi biVar;
        synchronized (bi.class) {
            if (a == null) {
                a = new bi();
            }
            biVar = a;
        }
        return biVar;
    }

    public synchronized void a(Context context, String str, String str2, int i, long j, boolean z) {
        if (context != null) {
            if (!TextUtils.isEmpty(str)) {
                if (av.a) {
                    JSONObject jSONObject = (JSONObject) this.b.get(str);
                    if (jSONObject == null) {
                        jSONObject = new JSONObject();
                    }
                    try {
                        jSONObject.put("x", str2);
                        jSONObject.put("time", j);
                        if (this.b.containsKey(str)) {
                            jSONObject.put("num", jSONObject.getInt("num") + i);
                        } else {
                            jSONObject.put("num", i);
                        }
                    } catch (Exception e) {
                    }
                    this.b.put(str, jSONObject);
                    if (z) {
                        try {
                            bg.a().a(context, str, str2, j);
                        } catch (Exception e2) {
                        }
                    }
                }
            }
        }
    }

    public synchronized void a(Context context, String str, AmapLoc amapLoc) {
        String str2 = null;
        synchronized (this) {
            if (br.a(amapLoc) && context != null) {
                if (av.a) {
                    if (this.b.size() > 500) {
                        str2 = ay.a(amapLoc.g(), amapLoc.f());
                        if (!this.b.containsKey(str2)) {
                            return;
                        }
                    }
                    if (str2 == null) {
                        str2 = ay.a(amapLoc.g(), amapLoc.f());
                    }
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("key", str);
                        jSONObject.put("lat", amapLoc.g());
                        jSONObject.put("lon", amapLoc.f());
                        a(context, str2, jSONObject.toString(), 1, br.a(), true);
                    } catch (Exception e) {
                    }
                    return;
                }
            }
        }
    }

    public synchronized ArrayList<bh> b() {
        ArrayList<bh> arrayList = new ArrayList();
        if (this.b.isEmpty()) {
            return arrayList;
        }
        Hashtable hashtable = this.b;
        ArrayList arrayList2 = new ArrayList(hashtable.keySet());
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                JSONObject jSONObject = (JSONObject) hashtable.get(str);
                int i = jSONObject.getInt("num");
                String string = jSONObject.getString("x");
                long j = jSONObject.getLong("time");
                if (i >= 120) {
                    arrayList.add(new bh(str, j, i, string));
                }
            } catch (Exception e) {
            }
        }
        Collections.sort(arrayList, new Comparator<bh>() {
            final /* synthetic */ bi a;

            {
                this.a = r1;
            }

            public /* synthetic */ int compare(Object obj, Object obj2) {
                return a((bh) obj, (bh) obj2);
            }

            public int a(bh bhVar, bh bhVar2) {
                return bhVar2.b() - bhVar.b();
            }
        });
        arrayList2.clear();
        return arrayList;
    }

    private void d() {
        if (!this.b.isEmpty()) {
            this.b.clear();
        }
    }

    public void a(Context context) {
        if (av.a && !this.c) {
            br.b();
            try {
                bg.a().b(context);
            } catch (Exception e) {
            }
            br.b();
            this.c = true;
        }
    }

    public void c() {
        a().d();
        this.c = false;
    }
}
