package com.squareup.okhttp.internal;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.v;

public final class Util {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    class AnonymousClass1 implements ThreadFactory {
        final /* synthetic */ boolean val$daemon;
        final /* synthetic */ String val$name;

        AnonymousClass1(String str, boolean z) {
            this.val$name = str;
            this.val$daemon = z;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, this.val$name);
            thread.setDaemon(this.val$daemon);
            return thread;
        }
    }

    private Util() {
    }

    public static void checkOffsetAndCount(long j, long j2, long j3) {
        Object obj = 1;
        if (((j2 | j3) < 0 ? 1 : null) == null) {
            if ((j2 > j ? 1 : null) == null) {
                if (j - j2 < j3) {
                    obj = null;
                }
                if (obj != null) {
                    return;
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void closeAll(Closeable closeable, Closeable closeable2) {
        Object obj = null;
        try {
            closeable.close();
        } catch (Throwable th) {
            obj = th;
        }
        try {
            closeable2.close();
        } catch (Throwable th2) {
            if (obj == null) {
                Throwable th3 = th2;
            }
        }
        if (obj == null) {
            return;
        }
        if (obj instanceof IOException) {
            throw ((IOException) obj);
        } else if (obj instanceof RuntimeException) {
            throw ((RuntimeException) obj);
        } else if (obj instanceof Error) {
            throw ((Error) obj);
        } else {
            throw new AssertionError(obj);
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    public static void closeQuietly(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    public static boolean discard(v vVar, int i, TimeUnit timeUnit) {
        try {
            return skipAll(vVar, i, timeUnit);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean equal(Object obj, Object obj2) {
        if (obj != obj2) {
            if (obj == null) {
                return false;
            }
            if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    public static int getDefaultPort(String str) {
        return !"http".equals(str) ? !"https".equals(str) ? -1 : 443 : 80;
    }

    private static int getEffectivePort(String str, int i) {
        return i == -1 ? getDefaultPort(str) : i;
    }

    public static int getEffectivePort(URI uri) {
        return getEffectivePort(uri.getScheme(), uri.getPort());
    }

    public static int getEffectivePort(URL url) {
        return getEffectivePort(url.getProtocol(), url.getPort());
    }

    public static List immutableList(List list) {
        return Collections.unmodifiableList(new ArrayList(list));
    }

    public static List immutableList(Object... objArr) {
        return Collections.unmodifiableList(Arrays.asList((Object[]) objArr.clone()));
    }

    public static Map immutableMap(Map map) {
        return Collections.unmodifiableMap(new LinkedHashMap(map));
    }

    private static List intersect(Object[] objArr, Object[] objArr2) {
        List arrayList = new ArrayList();
        for (Object obj : objArr) {
            for (Object obj2 : objArr2) {
                if (obj.equals(obj2)) {
                    arrayList.add(obj2);
                    break;
                }
            }
        }
        return arrayList;
    }

    public static Object[] intersect(Class cls, Object[] objArr, Object[] objArr2) {
        List intersect = intersect(objArr, objArr2);
        return intersect.toArray((Object[]) Array.newInstance(cls, intersect.size()));
    }

    public static String md5Hex(String str) {
        try {
            return ByteString.Ar(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"))).Ay();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static ByteString sha1(ByteString byteString) {
        try {
            return ByteString.Ar(MessageDigest.getInstance("SHA-1").digest(byteString.AB()));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static String shaBase64(String str) {
        try {
            return ByteString.Ar(MessageDigest.getInstance("SHA-1").digest(str.getBytes("UTF-8"))).Au();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static boolean skipAll(okio.v r13, int r14, java.util.concurrent.TimeUnit r15) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0068 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r12 = 0;
        r2 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r6 = java.lang.System.nanoTime();
        r0 = r13.timeout();
        r0 = r0.hasDeadline();
        if (r0 != 0) goto L_0x0048;
    L_0x0014:
        r0 = r2;
    L_0x0015:
        r4 = r13.timeout();
        r8 = (long) r14;
        r8 = r15.toNanos(r8);
        r8 = java.lang.Math.min(r0, r8);
        r8 = r8 + r6;
        r4.deadlineNanoTime(r8);
        r4 = new okio.k;	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
        r4.<init>();	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
    L_0x002b:
        r8 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
        r8 = r13.read(r4, r8);	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
        r10 = -1;	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
        r5 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
        if (r5 == 0) goto L_0x0052;	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
    L_0x0037:
        r4.clear();	 Catch:{ InterruptedIOException -> 0x003b, all -> 0x0071 }
        goto L_0x002b;
    L_0x003b:
        r4 = move-exception;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x0068;
    L_0x0040:
        r0 = r13.timeout();
        r0.clearDeadline();
    L_0x0047:
        return r12;
    L_0x0048:
        r0 = r13.timeout();
        r0 = r0.deadlineNanoTime();
        r0 = r0 - r6;
        goto L_0x0015;
    L_0x0052:
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x005f;
    L_0x0056:
        r0 = r13.timeout();
        r0.clearDeadline();
    L_0x005d:
        r0 = 1;
        return r0;
    L_0x005f:
        r2 = r13.timeout();
        r0 = r0 + r6;
        r2.deadlineNanoTime(r0);
        goto L_0x005d;
    L_0x0068:
        r2 = r13.timeout();
        r0 = r0 + r6;
        r2.deadlineNanoTime(r0);
        goto L_0x0047;
    L_0x0071:
        r4 = move-exception;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x007e;
    L_0x0076:
        r0 = r13.timeout();
        r0.clearDeadline();
    L_0x007d:
        throw r4;
    L_0x007e:
        r2 = r13.timeout();
        r0 = r0 + r6;
        r2.deadlineNanoTime(r0);
        goto L_0x007d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.Util.skipAll(okio.v, int, java.util.concurrent.TimeUnit):boolean");
    }

    public static ThreadFactory threadFactory(String str, boolean z) {
        return new AnonymousClass1(str, z);
    }
}
