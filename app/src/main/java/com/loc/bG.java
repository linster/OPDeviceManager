package com.loc;

import android.os.Build.VERSION;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class bG {
    private static g tu;
    private int b;
    private int c;
    private long i;
    private HostnameVerifier tA;
    private boolean tv;
    private SSLContext tw;
    private Proxy tx;
    private volatile boolean ty;
    private long tz;

    bG(int i, int i2, Proxy proxy) {
        this(i, i2, proxy, false);
    }

    bG(int i, int i2, Proxy proxy, boolean z) {
        this.ty = false;
        this.tz = -1;
        this.i = 0;
        this.tA = new y(this);
        this.b = i;
        this.c = i2;
        this.tx = proxy;
        this.tv = z;
        if (z) {
            try {
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init(null, null, null);
                this.tw = instance;
            } catch (Throwable e) {
                D.mF(e, "HttpUrlUtil", "HttpUrlUtil");
            } catch (Throwable e2) {
                D.mF(e2, "HttpUtil", "HttpUtil");
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.loc.ac xp(java.net.HttpURLConnection r10) {
        /*
        r9 = this;
        r2 = 0;
        r5 = r10.getHeaderFields();	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r0 = r10.getResponseCode();	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r1) goto L_0x0054;
    L_0x000d:
        r4 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r4.<init>();	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r3 = r10.getInputStream();	 Catch:{ IOException -> 0x0186, all -> 0x0177 }
        r1 = new java.io.PushbackInputStream;	 Catch:{ IOException -> 0x018b, all -> 0x017c }
        r0 = 2;
        r1.<init>(r3, r0);	 Catch:{ IOException -> 0x018b, all -> 0x017c }
        r0 = 2;
        r0 = new byte[r0];	 Catch:{ IOException -> 0x00a4, all -> 0x0180 }
        r1.read(r0);	 Catch:{ IOException -> 0x00a4, all -> 0x0180 }
        r1.unread(r0);	 Catch:{ IOException -> 0x00a4, all -> 0x0180 }
        r6 = 0;
        r6 = r0[r6];	 Catch:{ IOException -> 0x00a4, all -> 0x0180 }
        r7 = 31;
        if (r6 == r7) goto L_0x0091;
    L_0x002c:
        r2 = r1;
    L_0x002d:
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = new byte[r0];	 Catch:{ IOException -> 0x00a4 }
    L_0x0031:
        r6 = r2.read(r0);	 Catch:{ IOException -> 0x00a4 }
        r7 = -1;
        if (r6 != r7) goto L_0x009f;
    L_0x0038:
        r0 = tu;	 Catch:{ IOException -> 0x00a4 }
        if (r0 != 0) goto L_0x00a6;
    L_0x003c:
        r0 = new com.loc.ac;	 Catch:{ IOException -> 0x00a4 }
        r0.<init>();	 Catch:{ IOException -> 0x00a4 }
        r6 = r4.toByteArray();	 Catch:{ IOException -> 0x00a4 }
        r0.a = r6;	 Catch:{ IOException -> 0x00a4 }
        r0.mK = r5;	 Catch:{ IOException -> 0x00a4 }
        if (r4 != 0) goto L_0x00ac;
    L_0x004b:
        if (r3 != 0) goto L_0x00be;
    L_0x004d:
        if (r1 != 0) goto L_0x00d1;
    L_0x004f:
        if (r2 != 0) goto L_0x00e5;
    L_0x0051:
        if (r10 != 0) goto L_0x00f9;
    L_0x0053:
        return r0;
    L_0x0054:
        r1 = new com.loc.i;	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r3.<init>();	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r4 = "网络异常原因：";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r4 = r10.getResponseMessage();	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r4 = " 网络异常状态码：";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r0 = r3.append(r0);	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r0 = r0.toString();	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        r1.<init>(r0);	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
        throw r1;	 Catch:{ IOException -> 0x007d, all -> 0x0171 }
    L_0x007d:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
    L_0x0081:
        throw r0;	 Catch:{ all -> 0x0082 }
    L_0x0082:
        r0 = move-exception;
        r8 = r1;
        r1 = r2;
        r2 = r8;
    L_0x0086:
        if (r4 != 0) goto L_0x010d;
    L_0x0088:
        if (r3 != 0) goto L_0x0121;
    L_0x008a:
        if (r2 != 0) goto L_0x0135;
    L_0x008c:
        if (r1 != 0) goto L_0x0149;
    L_0x008e:
        if (r10 != 0) goto L_0x015d;
    L_0x0090:
        throw r0;
    L_0x0091:
        r6 = 1;
        r0 = r0[r6];	 Catch:{ IOException -> 0x00a4, all -> 0x0180 }
        r6 = -117; // 0xffffffffffffff8b float:NaN double:NaN;
        if (r0 != r6) goto L_0x002c;
    L_0x0098:
        r0 = new java.util.zip.GZIPInputStream;	 Catch:{ IOException -> 0x00a4, all -> 0x0180 }
        r0.<init>(r1);	 Catch:{ IOException -> 0x00a4, all -> 0x0180 }
        r2 = r0;
        goto L_0x002d;
    L_0x009f:
        r7 = 0;
        r4.write(r0, r7, r6);	 Catch:{ IOException -> 0x00a4 }
        goto L_0x0031;
    L_0x00a4:
        r0 = move-exception;
        goto L_0x0081;
    L_0x00a6:
        r0 = tu;	 Catch:{ IOException -> 0x00a4 }
        r0.kD();	 Catch:{ IOException -> 0x00a4 }
        goto L_0x003c;
    L_0x00ac:
        r4.close();	 Catch:{ IOException -> 0x00b0 }
        goto L_0x004b;
    L_0x00b0:
        r4 = move-exception;
        r5 = "HttpUrlUtil";
        r6 = "parseResult";
        com.loc.D.mF(r4, r5, r6);
        r4.printStackTrace();
        goto L_0x004b;
    L_0x00be:
        r3.close();	 Catch:{ Exception -> 0x00c2 }
        goto L_0x004d;
    L_0x00c2:
        r3 = move-exception;
        r4 = "HttpUrlUtil";
        r5 = "parseResult";
        com.loc.D.mF(r3, r4, r5);
        r3.printStackTrace();
        goto L_0x004d;
    L_0x00d1:
        r1.close();	 Catch:{ Exception -> 0x00d6 }
        goto L_0x004f;
    L_0x00d6:
        r1 = move-exception;
        r3 = "HttpUrlUtil";
        r4 = "parseResult";
        com.loc.D.mF(r1, r3, r4);
        r1.printStackTrace();
        goto L_0x004f;
    L_0x00e5:
        r2.close();	 Catch:{ Exception -> 0x00ea }
        goto L_0x0051;
    L_0x00ea:
        r1 = move-exception;
        r2 = "HttpUrlUtil";
        r3 = "parseResult";
        com.loc.D.mF(r1, r2, r3);
        r1.printStackTrace();
        goto L_0x0051;
    L_0x00f9:
        r10.disconnect();	 Catch:{ Throwable -> 0x00fe }
        goto L_0x0053;
    L_0x00fe:
        r1 = move-exception;
        r2 = "HttpUrlUtil";
        r3 = "parseResult";
        com.loc.D.mF(r1, r2, r3);
        r1.printStackTrace();
        goto L_0x0053;
    L_0x010d:
        r4.close();	 Catch:{ IOException -> 0x0112 }
        goto L_0x0088;
    L_0x0112:
        r4 = move-exception;
        r5 = "HttpUrlUtil";
        r6 = "parseResult";
        com.loc.D.mF(r4, r5, r6);
        r4.printStackTrace();
        goto L_0x0088;
    L_0x0121:
        r3.close();	 Catch:{ Exception -> 0x0126 }
        goto L_0x008a;
    L_0x0126:
        r3 = move-exception;
        r4 = "HttpUrlUtil";
        r5 = "parseResult";
        com.loc.D.mF(r3, r4, r5);
        r3.printStackTrace();
        goto L_0x008a;
    L_0x0135:
        r2.close();	 Catch:{ Exception -> 0x013a }
        goto L_0x008c;
    L_0x013a:
        r2 = move-exception;
        r3 = "HttpUrlUtil";
        r4 = "parseResult";
        com.loc.D.mF(r2, r3, r4);
        r2.printStackTrace();
        goto L_0x008c;
    L_0x0149:
        r1.close();	 Catch:{ Exception -> 0x014e }
        goto L_0x008e;
    L_0x014e:
        r1 = move-exception;
        r2 = "HttpUrlUtil";
        r3 = "parseResult";
        com.loc.D.mF(r1, r2, r3);
        r1.printStackTrace();
        goto L_0x008e;
    L_0x015d:
        r10.disconnect();	 Catch:{ Throwable -> 0x0162 }
        goto L_0x0090;
    L_0x0162:
        r1 = move-exception;
        r2 = "HttpUrlUtil";
        r3 = "parseResult";
        com.loc.D.mF(r1, r2, r3);
        r1.printStackTrace();
        goto L_0x0090;
    L_0x0171:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        goto L_0x0086;
    L_0x0177:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        goto L_0x0086;
    L_0x017c:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0086;
    L_0x0180:
        r0 = move-exception;
        r8 = r1;
        r1 = r2;
        r2 = r8;
        goto L_0x0086;
    L_0x0186:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        goto L_0x0081;
    L_0x018b:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0081;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bG.xp(java.net.HttpURLConnection):com.loc.ac");
    }

    private void xq(Map map, HttpURLConnection httpURLConnection) {
        if (map != null) {
            for (String str : map.keySet()) {
                httpURLConnection.addRequestProperty(str, (String) map.get(str));
            }
        }
        try {
            httpURLConnection.addRequestProperty("csid", UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
        } catch (Throwable th) {
            D.mF(th, "HttpUrlUtil", "addHeaders");
        }
        httpURLConnection.setConnectTimeout(this.b);
        httpURLConnection.setReadTimeout(this.c);
    }

    static String xr(Map map) {
        if (map == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (str2 == null) {
                str2 = "";
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(str));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(str2));
        }
        return stringBuilder.toString();
    }

    void xk(long j) {
        this.i = j;
    }

    void xl(long j) {
        this.tz = j;
    }

    void xm(String str, Map map, Map map2, ah ahVar) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        if (ahVar != null) {
            try {
                String xr = xr(map2);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str);
                if (xr != null) {
                    stringBuffer.append("?").append(xr);
                }
                httpURLConnection = xo(stringBuffer.toString(), map, false);
                httpURLConnection.setRequestProperty("RANGE", "bytes=" + this.i + "-");
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (((responseCode == 206 ? 0 : 1) & (responseCode == 200 ? 0 : 1)) != 0) {
                    ahVar.on(new i("网络异常原因：" + httpURLConnection.getResponseMessage() + " 网络异常状态码：" + responseCode));
                }
                inputStream = httpURLConnection.getInputStream();
                Object obj = new byte[1024];
                while (!this.ty) {
                    responseCode = inputStream.read(obj, 0, 1024);
                    if (responseCode <= 0) {
                        break;
                    }
                    if (this.tz != -1) {
                        if ((this.i >= this.tz ? 1 : null) != null) {
                            break;
                        }
                    }
                    if (responseCode != 1024) {
                        Object obj2 = new byte[responseCode];
                        System.arraycopy(obj, 0, obj2, 0, responseCode);
                        ahVar.ok(obj2, this.i);
                    } else {
                        ahVar.ok(obj, this.i);
                    }
                    this.i = ((long) responseCode) + this.i;
                }
                if (this.ty) {
                    ahVar.ol();
                } else {
                    ahVar.om();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        D.mF(e, "HttpUrlUtil", "makeDownloadGetRequest");
                    } catch (Throwable e2) {
                        e2.printStackTrace();
                        D.mF(e2, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e22) {
                        e22.printStackTrace();
                        D.mF(e22, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
            } catch (Throwable e222) {
                ahVar.on(e222);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e2222) {
                        e2222.printStackTrace();
                        D.mF(e2222, "HttpUrlUtil", "makeDownloadGetRequest");
                    } catch (Throwable e22222) {
                        e22222.printStackTrace();
                        D.mF(e22222, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Throwable e222222) {
                ahVar.on(e222222);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e2222222) {
                        e2222222.printStackTrace();
                        D.mF(e2222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    } catch (Throwable e22222222) {
                        e22222222.printStackTrace();
                        D.mF(e22222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Throwable e222222222) {
                ahVar.on(e222222222);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e2222222222) {
                        e2222222222.printStackTrace();
                        D.mF(e2222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    } catch (Throwable e22222222222) {
                        e22222222222.printStackTrace();
                        D.mF(e22222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Throwable e222222222222) {
                ahVar.on(e222222222222);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e2222222222222) {
                        e2222222222222.printStackTrace();
                        D.mF(e2222222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    } catch (Throwable e22222222222222) {
                        e22222222222222.printStackTrace();
                        D.mF(e22222222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Throwable e222222222222222) {
                ahVar.on(e222222222222222);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e2222222222222222) {
                        e2222222222222222.printStackTrace();
                        D.mF(e2222222222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    } catch (Throwable e22222222222222222) {
                        e22222222222222222.printStackTrace();
                        D.mF(e22222222222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Throwable e222222222222222222) {
                ahVar.on(e222222222222222222);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e2222222222222222222) {
                        e2222222222222222222.printStackTrace();
                        D.mF(e2222222222222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    } catch (Throwable e22222222222222222222) {
                        e22222222222222222222.printStackTrace();
                        D.mF(e22222222222222222222, "HttpUrlUtil", "makeDownloadGetRequest");
                    }
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Throwable e222222222222222222222) {
                e222222222222222222222.printStackTrace();
                D.mF(e222222222222222222222, "HttpUrlUtil", "makeDownloadGetRequest");
            }
        }
    }

    ac xn(String str, Map map, byte[] bArr) {
        try {
            HttpURLConnection xo = xo(str, map, true);
            if (bArr != null && bArr.length > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(xo.getOutputStream());
                dataOutputStream.write(bArr);
                dataOutputStream.close();
            }
            xo.connect();
            return xp(xo);
        } catch (ConnectException e) {
            e.printStackTrace();
            throw new i("http连接失败 - ConnectionException");
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            throw new i("url异常 - MalformedURLException");
        } catch (UnknownHostException e3) {
            e3.printStackTrace();
            throw new i("未知主机 - UnKnowHostException");
        } catch (SocketException e4) {
            e4.printStackTrace();
            throw new i("socket 连接异常 - SocketException");
        } catch (SocketTimeoutException e5) {
            e5.printStackTrace();
            throw new i("socket 连接超时 - SocketTimeoutException");
        } catch (IOException e6) {
            e6.printStackTrace();
            throw new i("IO 操作异常 - IOException");
        } catch (Throwable th) {
            D.mF(th, "HttpUrlUtil", "makePostReqeust");
            i iVar = new i("未知的错误");
        }
    }

    HttpURLConnection xo(String str, Map map, boolean z) {
        bK.xH();
        URL url = new URL(str);
        HttpURLConnection openConnection = this.tx == null ? (HttpURLConnection) url.openConnection() : url.openConnection(this.tx);
        if (this.tv) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) openConnection;
            httpsURLConnection.setSSLSocketFactory(this.tw.getSocketFactory());
            httpsURLConnection.setHostnameVerifier(this.tA);
        } else {
            openConnection = openConnection;
        }
        if (VERSION.SDK != null && VERSION.SDK_INT > 13) {
            openConnection.setRequestProperty("Connection", "close");
        }
        xq(map, openConnection);
        if (z) {
            openConnection.setRequestMethod("POST");
            openConnection.setUseCaches(false);
            openConnection.setDoInput(true);
            openConnection.setDoOutput(true);
        } else {
            openConnection.setRequestMethod("GET");
            openConnection.setDoInput(true);
        }
        return openConnection;
    }
}
