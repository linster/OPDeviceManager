package com.loc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.fence.Fence;
import com.amap.api.location.AMapLocation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/* compiled from: FenceManager */
public class d {
    Context a;
    private Hashtable<PendingIntent, ArrayList<Fence>> b;

    public d(Context context) {
        this.b = new Hashtable();
        this.a = context;
    }

    private void a(PendingIntent pendingIntent, Fence fence, int i) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("fenceid", fence.b);
        bundle.putInt("event", i);
        intent.putExtras(bundle);
        try {
            pendingIntent.send(this.a, 0, intent);
        } catch (Exception e) {
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(com.amap.api.fence.Fence r7, android.app.PendingIntent r8) {
        /*
        r6 = this;
        r1 = 0;
        r3 = 0;
        if (r8 != 0) goto L_0x0005;
    L_0x0004:
        return r3;
    L_0x0005:
        if (r7 == 0) goto L_0x0004;
    L_0x0007:
        r0 = r7.b;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x0018;
    L_0x000f:
        r0 = r7.e;
        r2 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 >= 0) goto L_0x0019;
    L_0x0017:
        return r3;
    L_0x0018:
        return r3;
    L_0x0019:
        r0 = r7.e;
        r2 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 <= 0) goto L_0x0022;
    L_0x0021:
        return r3;
    L_0x0022:
        r0 = r6.b();
        if (r0 == 0) goto L_0x005e;
    L_0x0028:
        r0 = r7.a();
        if (r0 == 0) goto L_0x0067;
    L_0x002e:
        r0 = 7;
        r2 = r7.a();
        if (r2 > r0) goto L_0x0068;
    L_0x0035:
        r0 = r6.b;
        r0 = r0.entrySet();
        r4 = r0.iterator();
        r2 = r3;
    L_0x0040:
        if (r4 != 0) goto L_0x0069;
    L_0x0042:
        r0 = 20;
        if (r2 > r0) goto L_0x0082;
    L_0x0046:
        r0 = -1;
        r7.g = r0;
        r0 = r6.b();
        if (r0 == 0) goto L_0x0083;
    L_0x004f:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r0.add(r7);
        r1 = r6.b;
        r1.put(r8, r0);
    L_0x005c:
        r0 = 1;
        return r0;
    L_0x005e:
        r0 = r6.b;
        r0 = r0.containsKey(r8);
        if (r0 != 0) goto L_0x0028;
    L_0x0066:
        return r3;
    L_0x0067:
        return r3;
    L_0x0068:
        return r3;
    L_0x0069:
        r0 = r4.hasNext();
        if (r0 == 0) goto L_0x0042;
    L_0x006f:
        r0 = r4.next();
        r0 = (java.util.Map.Entry) r0;
        r0 = r0.getValue();
        r0 = (java.util.ArrayList) r0;
        r0 = r0.size();
        r0 = r0 + r2;
        r2 = r0;
        goto L_0x0040;
    L_0x0082:
        return r3;
    L_0x0083:
        r0 = r6.b;
        r0 = r0.get(r8);
        r0 = (java.util.ArrayList) r0;
        r3 = r0.iterator();
        r2 = r1;
    L_0x0090:
        r1 = r3.hasNext();
        if (r1 != 0) goto L_0x00a1;
    L_0x0096:
        if (r2 != 0) goto L_0x00b4;
    L_0x0098:
        r0.add(r7);
        r1 = r6.b;
        r1.put(r8, r0);
        goto L_0x005c;
    L_0x00a1:
        r1 = r3.next();
        r1 = (com.amap.api.fence.Fence) r1;
        r4 = r1.b;
        r5 = r7.b;
        r4 = r4.equals(r5);
        if (r4 != 0) goto L_0x00b2;
    L_0x00b1:
        r1 = r2;
    L_0x00b2:
        r2 = r1;
        goto L_0x0090;
    L_0x00b4:
        r0.remove(r2);
        goto L_0x0098;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a(com.amap.api.fence.Fence, android.app.PendingIntent):boolean");
    }

    public boolean a(PendingIntent pendingIntent) {
        if (pendingIntent == null || !this.b.containsKey(pendingIntent)) {
            return false;
        }
        List arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.b.get(pendingIntent)).iterator();
        while (it.hasNext()) {
            arrayList.add(((Fence) it.next()).b);
        }
        return a(arrayList);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.app.PendingIntent r3, java.lang.String r4) {
        /*
        r2 = this;
        r1 = 0;
        if (r3 != 0) goto L_0x0004;
    L_0x0003:
        return r1;
    L_0x0004:
        r0 = r2.b;
        r0 = r0.containsKey(r3);
        if (r0 == 0) goto L_0x0003;
    L_0x000c:
        r0 = android.text.TextUtils.isEmpty(r4);
        if (r0 != 0) goto L_0x001f;
    L_0x0012:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r0.add(r4);
        r0 = r2.a(r3, r0);
        return r0;
    L_0x001f:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a(android.app.PendingIntent, java.lang.String):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.app.PendingIntent r5, java.util.List<java.lang.String> r6) {
        /*
        r4 = this;
        r1 = 0;
        r0 = r4.b();
        if (r0 != 0) goto L_0x000a;
    L_0x0007:
        if (r6 != 0) goto L_0x000b;
    L_0x0009:
        return r1;
    L_0x000a:
        return r1;
    L_0x000b:
        r0 = r6.isEmpty();
        if (r0 != 0) goto L_0x0009;
    L_0x0011:
        r0 = r4.b;
        r0 = r0.containsKey(r5);
        if (r0 == 0) goto L_0x0028;
    L_0x0019:
        r0 = r4.b;
        r0 = r0.get(r5);
        r0 = (java.util.ArrayList) r0;
        r2 = r0.iterator();
    L_0x0025:
        if (r2 != 0) goto L_0x0029;
    L_0x0027:
        return r1;
    L_0x0028:
        return r1;
    L_0x0029:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x0027;
    L_0x002f:
        r0 = r2.next();
        r0 = (com.amap.api.fence.Fence) r0;
        r3 = r0.b;
        r3 = r6.contains(r3);
        if (r3 == 0) goto L_0x0043;
    L_0x003d:
        r2.remove();
        r0 = 1;
    L_0x0041:
        r1 = r0;
        goto L_0x0025;
    L_0x0043:
        r0 = r4.a(r0);
        if (r0 != 0) goto L_0x003d;
    L_0x0049:
        r0 = r1;
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a(android.app.PendingIntent, java.util.List):boolean");
    }

    public boolean a(List<String> list) {
        boolean z = false;
        if (b() || list == null || list.isEmpty()) {
            return false;
        }
        Iterator it = this.b.entrySet().iterator();
        while (it != null && it.hasNext()) {
            Entry entry = (Entry) it.next();
            Iterator it2 = ((ArrayList) entry.getValue()).iterator();
            while (it2 != null && it2.hasNext()) {
                boolean z2;
                Fence fence = (Fence) it2.next();
                if (list.contains(fence.b) || a(fence)) {
                    it2.remove();
                    z2 = true;
                } else {
                    z2 = z;
                }
                z = z2;
            }
            if (((ArrayList) entry.getValue()).isEmpty()) {
                it.remove();
            }
        }
        return z;
    }

    public void a(AMapLocation aMapLocation) {
        if (!b()) {
            Iterator it = this.b.entrySet().iterator();
            while (it != null && it.hasNext()) {
                Entry entry = (Entry) it.next();
                Iterator it2 = ((ArrayList) entry.getValue()).iterator();
                while (it2.hasNext()) {
                    Fence fence = (Fence) it2.next();
                    if (!a(fence)) {
                        Object obj;
                        float a = br.a(new double[]{fence.d, fence.c, aMapLocation.getLatitude(), aMapLocation.getLongitude()});
                        float accuracy = aMapLocation.getAccuracy();
                        if (accuracy >= 500.0f) {
                            a -= fence.e + 500.0f;
                        } else {
                            a -= accuracy + fence.e;
                        }
                        Object obj2 = null;
                        if (a > 0.0f) {
                            if (fence.g == 0) {
                                obj = null;
                            } else {
                                obj = 1;
                            }
                            fence.g = 0;
                        } else {
                            if (fence.g != 1) {
                                obj2 = 1;
                            }
                            fence.g = 1;
                            obj = obj2;
                        }
                        if (obj != null) {
                            switch (fence.g) {
                                case 0:
                                    fence.h = -1;
                                    if ((fence.a() & 2) != 2) {
                                        break;
                                    }
                                    a((PendingIntent) entry.getKey(), fence, 2);
                                    break;
                                case 1:
                                    fence.h = br.b();
                                    if ((fence.a() & 1) != 1) {
                                        break;
                                    }
                                    a((PendingIntent) entry.getKey(), fence, 1);
                                    break;
                                default:
                                    break;
                            }
                        } else if ((fence.a() & 4) == 4) {
                            if ((fence.h <= 0 ? 1 : null) == null) {
                                if ((br.b() - fence.h <= fence.c() ? 1 : null) == null) {
                                    fence.h = br.b();
                                    a((PendingIntent) entry.getKey(), fence, 4);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean b() {
        return this.b.isEmpty();
    }

    private boolean a(Fence fence) {
        if (fence.b() != -1) {
            if (!(fence.b() > br.b())) {
                return true;
            }
        }
        return false;
    }

    public void a() {
        this.b.clear();
    }
}
