package net.oneplus.odm.insight;

import android.os.AsyncTask;

class a extends AsyncTask {
    private boolean ap = false;
    final /* synthetic */ MDMJobService aq;

    public a(MDMJobService mDMJobService, boolean z) {
        this.aq = mDMJobService;
        this.ap = z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.Object bc(java.lang.Void... r9) {
        /*
        r8 = this;
        r3 = 0;
        r0 = r8.aq;
        r0.aA();
        r0 = r8.aq;
        r0 = r0.an;
        if (r0 != 0) goto L_0x001d;
    L_0x000e:
        r0 = "MDMJob";
        r1 = "Server is fail.";
        android.util.Log.e(r0, r1);
        r0 = r8.aq;
        r0.ax();
        return r3;
    L_0x001d:
        r0 = r8.aq;
        r0 = r0.ah;
        r4 = r0.K();
        if (r4 != 0) goto L_0x0038;
    L_0x0029:
        r0 = "MDMJob";
        r1 = "Cursor is null.";
        android.util.Log.i(r0, r1);
        r0 = r8.aq;
        r0.ax();
        return r3;
    L_0x0038:
        r0 = r4.getCount();
        if (r0 != 0) goto L_0x0050;
    L_0x003e:
        r0 = "MDMJob";
        r1 = "No event.";
        android.util.Log.i(r0, r1);
        r4.close();
        r0 = r8.aq;
        r0.ax();
        return r3;
    L_0x0050:
        r4.moveToFirst();
    L_0x0053:
        r2 = new net.oneplus.odm.analytics.OneplusAnalyticsPayload;	 Catch:{ JSONException -> 0x01ea, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = 0;
        r0 = r4.getInt(r0);	 Catch:{ JSONException -> 0x01ea, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = 1;
        r1 = r4.getString(r1);	 Catch:{ JSONException -> 0x01ea, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = 2;
        r5 = r4.getString(r5);	 Catch:{ JSONException -> 0x01ea, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r6 = 3;
        r6 = r4.getString(r6);	 Catch:{ JSONException -> 0x01ea, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r2.<init>(r0, r1, r5, r6);	 Catch:{ JSONException -> 0x01ea, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = r2.getTimestamp();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r0 - r6;
        r6 = 889032704; // 0x34fd9000 float:4.7229696E-7 double:4.39240517E-315;
        r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
        if (r0 <= 0) goto L_0x00a3;
    L_0x0080:
        r0 = "MDMJob";
        r1 = "Clean expire data";
        android.util.Log.d(r0, r1);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r2.getId();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0.aJ(r1);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
    L_0x0092:
        r0 = r4.moveToNext();
        if (r0 != 0) goto L_0x0053;
    L_0x0098:
        if (r4 == 0) goto L_0x009d;
    L_0x009a:
        r4.close();
    L_0x009d:
        r0 = r8.aq;
        r0.ax();
        return r3;
    L_0x00a3:
        r0 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r0.length();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = 5;
        if (r0 >= r1) goto L_0x00fb;
    L_0x00ae:
        r0 = "MDMJob";
        r1 = "Clean old data.";
        android.util.Log.d(r0, r1);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r2.getId();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0.aJ(r1);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        goto L_0x0092;
    L_0x00c1:
        r0 = move-exception;
        r1 = r2;
    L_0x00c3:
        r2 = "MDMJob";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "JSONException:";
        r4 = r4.append(r5);
        r0 = r0.getMessage();
        r0 = r4.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r2, r0);
        if (r1 == 0) goto L_0x00f5;
    L_0x00e3:
        r0 = "MDMJob";
        r2 = "Remove error payload.";
        android.util.Log.e(r0, r2);
        r0 = r8.aq;
        r1 = r1.getId();
        r0.aJ(r1);
    L_0x00f5:
        r0 = r8.aq;
        r0.ax();
        return r3;
    L_0x00fb:
        r0 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r0.ae;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r0.containsKey(r1);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        if (r0 == 0) goto L_0x01a4;
    L_0x010b:
        r0 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r0.ae;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r0.get(r1);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = (net.oneplus.odm.data.b) r0;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0.m(r2);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = r0.n();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r1.ad;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r6 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r7 = 100;
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r1.getOrDefault(r6, r7);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = (java.lang.Integer) r1;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r1.intValue();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        if (r5 < r1) goto L_0x0092;
    L_0x013e:
        r1 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r1.ao;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = "#ODM_OAUTH_TOKEN#";
        r6 = 0;
        r1 = r1.bU(r5, r6);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = "MDMJob";
        r6 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r6.<init>();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r7 = "Send ";
        r6 = r6.append(r7);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r7 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r6 = r6.append(r7);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r6 = r6.toString();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        android.util.Log.v(r5, r6);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r6 = 1;
        r5.aL(r0, r1, r6);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0 = r0.ae;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0.remove(r1);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        goto L_0x0092;
    L_0x017f:
        r0 = move-exception;
        r1 = "MDMJob";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r4 = "Retrofit:";
        r2 = r2.append(r4);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
        r0 = r8.aq;
        r0.ax();
        return r3;
    L_0x01a4:
        r0 = new net.oneplus.odm.data.b;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r1.ag;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0.<init>(r1, r5);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r0.m(r2);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r8.aq;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1 = r1.ae;	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r5 = r2.getCategory();	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        r1.put(r5, r0);	 Catch:{ JSONException -> 0x00c1, RetrofitError -> 0x017f, Exception -> 0x01c5 }
        goto L_0x0092;
    L_0x01c5:
        r0 = move-exception;
        r1 = "MDMJob";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r4 = "Exception:";
        r2 = r2.append(r4);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
        r0 = r8.aq;
        r0.ax();
        return r3;
    L_0x01ea:
        r0 = move-exception;
        r1 = r3;
        goto L_0x00c3;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.odm.insight.a.bc(java.lang.Void[]):java.lang.Object");
    }

    protected /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        return bc((Void[]) objArr);
    }
}
