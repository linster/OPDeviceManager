package net.oneplus.odm.insight;

import android.os.AsyncTask;
import java.util.ArrayList;

class b extends AsyncTask {
    private boolean ar = false;
    private ArrayList as = new ArrayList();
    final /* synthetic */ MDMJobService at;

    public b(MDMJobService mDMJobService, boolean z) {
        this.at = mDMJobService;
        this.ar = z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.Object bd(java.lang.Void... r9) {
        /*
        r8 = this;
        r2 = 0;
        r0 = r8.at;
        r0.aA();
        r0 = r8.at;
        r0 = r0.an;
        if (r0 != 0) goto L_0x001d;
    L_0x000e:
        r0 = "MDMJob";
        r1 = "Server is fail.";
        android.util.Log.e(r0, r1);
        r0 = r8.at;
        r0.ay();
        return r2;
    L_0x001d:
        r0 = r8.at;
        r0 = r0.ai;
        r0 = r0.G();
        if (r0 != 0) goto L_0x0038;
    L_0x0029:
        r0 = "MDMJob";
        r1 = "Cursor is null.";
        android.util.Log.i(r0, r1);
        r0 = r8.at;
        r0.ay();
        return r2;
    L_0x0038:
        r1 = r0.getCount();
        if (r1 != 0) goto L_0x0050;
    L_0x003e:
        r0.close();
        r0 = "MDMJob";
        r1 = "No event.";
        android.util.Log.i(r0, r1);
        r0 = r8.at;
        r0.ay();
        return r2;
    L_0x0050:
        r0.moveToFirst();
    L_0x0053:
        r1 = r8.at;	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r1 = r1.ao;	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r3 = "#ODM_OAUTH_TOKEN#";
        r4 = 0;
        r3 = r1.bU(r3, r4);	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r1 = new net.oneplus.odm.analytics.OneplusAnalyticsPayload;	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r4 = 0;
        r4 = r0.getInt(r4);	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r5 = 1;
        r5 = r0.getString(r5);	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r6 = 2;
        r6 = r0.getString(r6);	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r7 = "1000000001";
        r1.<init>(r4, r5, r6, r7);	 Catch:{ JSONException -> 0x00eb, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r4 = new net.oneplus.odm.data.b;	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r5 = r8.at;	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r5 = r5.ag;	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r6 = r1.getCategory();	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r4.<init>(r5, r6);	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r4.m(r1);	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r5 = r8.at;	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r6 = 0;
        r5.aL(r4, r3, r6);	 Catch:{ JSONException -> 0x0125, RetrofitError -> 0x00c6, Exception -> 0x00a1 }
        r1 = r0.moveToNext();
        if (r1 != 0) goto L_0x0053;
    L_0x0096:
        if (r0 == 0) goto L_0x009b;
    L_0x0098:
        r0.close();
    L_0x009b:
        r0 = r8.at;
        r0.ay();
        return r2;
    L_0x00a1:
        r0 = move-exception;
        r1 = "MDMJob";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Exception:";
        r3 = r3.append(r4);
        r0 = r0.getMessage();
        r0 = r3.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
        r0 = r8.at;
        r0.ay();
        return r2;
    L_0x00c6:
        r0 = move-exception;
        r1 = "MDMJob";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "RetrofitError:";
        r3 = r3.append(r4);
        r0 = r0.getMessage();
        r0 = r3.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
        r0 = r8.at;
        r0.ay();
        return r2;
    L_0x00eb:
        r0 = move-exception;
        r1 = r2;
    L_0x00ed:
        r3 = "MDMJob";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "JSONException:";
        r4 = r4.append(r5);
        r0 = r0.getMessage();
        r0 = r4.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r3, r0);
        if (r1 == 0) goto L_0x011f;
    L_0x010d:
        r0 = "MDMJob";
        r3 = "Remove error payload.";
        android.util.Log.e(r0, r3);
        r0 = r8.at;
        r1 = r1.getId();
        r0.aI(r1);
    L_0x011f:
        r0 = r8.at;
        r0.ay();
        return r2;
    L_0x0125:
        r0 = move-exception;
        goto L_0x00ed;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.odm.insight.b.bd(java.lang.Void[]):java.lang.Object");
    }

    protected /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        return bd((Void[]) objArr);
    }
}
