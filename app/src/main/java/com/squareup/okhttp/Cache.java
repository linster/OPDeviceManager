package com.squareup.okhttp;

import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.okhttp.internal.DiskLruCache.Editor;
import com.squareup.okhttp.internal.DiskLruCache.Snapshot;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheRequest;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.StatusLine;
import com.squareup.okhttp.internal.io.FileSystem;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import okio.ByteString;
import okio.a;
import okio.b;
import okio.f;
import okio.j;
import okio.k;
import okio.n;
import okio.u;
import okio.v;

public final class Cache {
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;
    private final DiskLruCache cache;
    private int hitCount;
    final InternalCache internalCache = new InternalCache() {
        public Response get(Request request) {
            return Cache.this.get(request);
        }

        public CacheRequest put(Response response) {
            return Cache.this.put(response);
        }

        public void remove(Request request) {
            Cache.this.remove(request);
        }

        public void trackConditionalCacheHit() {
            Cache.this.trackConditionalCacheHit();
        }

        public void trackResponse(CacheStrategy cacheStrategy) {
            Cache.this.trackResponse(cacheStrategy);
        }

        public void update(Response response, Response response2) {
            Cache.this.update(response, response2);
        }
    };
    private int networkCount;
    private int requestCount;
    private int writeAbortCount;
    private int writeSuccessCount;

    final class CacheRequestImpl implements CacheRequest {
        private n body;
        private n cacheOut;
        private boolean done;
        private final Editor editor;

        public CacheRequestImpl(final Editor editor) {
            this.editor = editor;
            this.cacheOut = editor.newSink(Cache.ENTRY_BODY);
            this.body = new f(this.cacheOut, Cache.this) {
                public void close() {
                    synchronized (Cache.this) {
                        if (CacheRequestImpl.this.done) {
                            return;
                        }
                        CacheRequestImpl.this.done = true;
                        Cache.this.writeSuccessCount = Cache.this.writeSuccessCount + Cache.ENTRY_BODY;
                        super.close();
                        editor.commit();
                    }
                }
            };
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void abort() {
            /*
            r2 = this;
            r1 = com.squareup.okhttp.Cache.this;
            monitor-enter(r1);
            r0 = r2.done;	 Catch:{ all -> 0x001d }
            if (r0 != 0) goto L_0x001b;
        L_0x0007:
            r0 = 1;
            r2.done = r0;	 Catch:{ all -> 0x001d }
            r0 = com.squareup.okhttp.Cache.this;	 Catch:{ all -> 0x001d }
            r0.writeAbortCount = r0.writeAbortCount + com.squareup.okhttp.Cache.ENTRY_BODY;	 Catch:{ all -> 0x001d }
            monitor-exit(r1);	 Catch:{ all -> 0x001d }
            r0 = r2.cacheOut;
            com.squareup.okhttp.internal.Util.closeQuietly(r0);
            r0 = r2.editor;	 Catch:{ IOException -> 0x0020 }
            r0.abort();	 Catch:{ IOException -> 0x0020 }
        L_0x001a:
            return;
        L_0x001b:
            monitor-exit(r1);	 Catch:{ all -> 0x001d }
            return;
        L_0x001d:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x001d }
            throw r0;
        L_0x0020:
            r0 = move-exception;
            goto L_0x001a;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.Cache.CacheRequestImpl.abort():void");
        }

        public n body() {
            return this.body;
        }
    }

    class CacheResponseBody extends ResponseBody {
        private final a bodySource;
        private final String contentLength;
        private final String contentType;
        private final Snapshot snapshot;

        public CacheResponseBody(final Snapshot snapshot, String str, String str2) {
            this.snapshot = snapshot;
            this.contentType = str;
            this.contentLength = str2;
            this.bodySource = j.AE(new u(snapshot.getSource(Cache.ENTRY_BODY)) {
                public void close() {
                    snapshot.close();
                    super.close();
                }
            });
        }

        public long contentLength() {
            long j = -1;
            try {
                if (this.contentLength != null) {
                    j = Long.parseLong(this.contentLength);
                }
                return j;
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        public MediaType contentType() {
            return this.contentType == null ? null : MediaType.parse(this.contentType);
        }

        public a source() {
            return this.bodySource;
        }
    }

    final class Entry {
        private final int code;
        private final Handshake handshake;
        private final String message;
        private final Protocol protocol;
        private final String requestMethod;
        private final Headers responseHeaders;
        private final String url;
        private final Headers varyHeaders;

        public Entry(Response response) {
            this.url = response.request().urlString();
            this.varyHeaders = OkHeaders.varyHeaders(response);
            this.requestMethod = response.request().method();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.responseHeaders = response.headers();
            this.handshake = response.handshake();
        }

        public Entry(v vVar) {
            int i = Cache.ENTRY_METADATA;
            try {
                a AE = j.AE(vVar);
                this.url = AE.zW();
                this.requestMethod = AE.zW();
                Builder builder = new Builder();
                int access$1000 = Cache.readInt(AE);
                for (int i2 = Cache.ENTRY_METADATA; i2 < access$1000; i2 += Cache.ENTRY_BODY) {
                    builder.addLenient(AE.zW());
                }
                this.varyHeaders = builder.build();
                StatusLine parse = StatusLine.parse(AE.zW());
                this.protocol = parse.protocol;
                this.code = parse.code;
                this.message = parse.message;
                Builder builder2 = new Builder();
                int access$10002 = Cache.readInt(AE);
                while (i < access$10002) {
                    builder2.addLenient(AE.zW());
                    i += Cache.ENTRY_BODY;
                }
                this.responseHeaders = builder2.build();
                if (isHttps()) {
                    String zW = AE.zW();
                    if (zW.length() <= 0) {
                        this.handshake = Handshake.get(AE.zW(), readCertificateList(AE), readCertificateList(AE));
                    } else {
                        throw new IOException("expected \"\" but was \"" + zW + "\"");
                    }
                }
                this.handshake = null;
                vVar.close();
            } catch (Throwable th) {
                vVar.close();
            }
        }

        private boolean isHttps() {
            return this.url.startsWith("https://");
        }

        private List readCertificateList(a aVar) {
            int access$1000 = Cache.readInt(aVar);
            if (access$1000 == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                List arrayList = new ArrayList(access$1000);
                for (int i = Cache.ENTRY_METADATA; i < access$1000; i += Cache.ENTRY_BODY) {
                    String zW = aVar.zW();
                    k kVar = new k();
                    kVar.zZ(ByteString.Ax(zW));
                    arrayList.add(instance.generateCertificate(kVar.zY()));
                }
                return arrayList;
            } catch (CertificateException e) {
                throw new IOException(e.getMessage());
            }
        }

        private void writeCertList(b bVar, List list) {
            try {
                bVar.Ag((long) list.size());
                bVar.Ad(10);
                int size = list.size();
                for (int i = Cache.ENTRY_METADATA; i < size; i += Cache.ENTRY_BODY) {
                    bVar.Ac(ByteString.Ar(((Certificate) list.get(i)).getEncoded()).Au());
                    bVar.Ad(10);
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.getMessage());
            }
        }

        public boolean matches(Request request, Response response) {
            return this.url.equals(request.urlString()) && this.requestMethod.equals(request.method()) && OkHeaders.varyMatches(response, this.varyHeaders, request);
        }

        public Response response(Request request, Snapshot snapshot) {
            String str = this.responseHeaders.get("Content-Type");
            String str2 = this.responseHeaders.get("Content-Length");
            return new Response.Builder().request(new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build()).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, str, str2)).handshake(this.handshake).build();
        }

        public void writeTo(Editor editor) {
            int i;
            int i2 = Cache.ENTRY_METADATA;
            b AF = j.AF(editor.newSink(Cache.ENTRY_METADATA));
            AF.Ac(this.url);
            AF.Ad(10);
            AF.Ac(this.requestMethod);
            AF.Ad(10);
            AF.Ag((long) this.varyHeaders.size());
            AF.Ad(10);
            int size = this.varyHeaders.size();
            for (i = Cache.ENTRY_METADATA; i < size; i += Cache.ENTRY_BODY) {
                AF.Ac(this.varyHeaders.name(i));
                AF.Ac(": ");
                AF.Ac(this.varyHeaders.value(i));
                AF.Ad(10);
            }
            AF.Ac(new StatusLine(this.protocol, this.code, this.message).toString());
            AF.Ad(10);
            AF.Ag((long) this.responseHeaders.size());
            AF.Ad(10);
            i = this.responseHeaders.size();
            while (i2 < i) {
                AF.Ac(this.responseHeaders.name(i2));
                AF.Ac(": ");
                AF.Ac(this.responseHeaders.value(i2));
                AF.Ad(10);
                i2 += Cache.ENTRY_BODY;
            }
            if (isHttps()) {
                AF.Ad(10);
                AF.Ac(this.handshake.cipherSuite());
                AF.Ad(10);
                writeCertList(AF, this.handshake.peerCertificates());
                writeCertList(AF, this.handshake.localCertificates());
            }
            AF.close();
        }
    }

    public Cache(File file, long j) {
        this.cache = DiskLruCache.create(FileSystem.SYSTEM, file, VERSION, ENTRY_COUNT, j);
    }

    private void abortQuietly(Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException e) {
            }
        }
    }

    private CacheRequest put(Response response) {
        Editor edit;
        String method = response.request().method();
        if (HttpMethod.invalidatesCache(response.request().method())) {
            try {
                remove(response.request());
            } catch (IOException e) {
            }
            return null;
        } else if (!method.equals("GET") || OkHeaders.hasVaryAll(response)) {
            return null;
        } else {
            Entry entry = new Entry(response);
            try {
                edit = this.cache.edit(urlToKey(response.request()));
                if (edit == null) {
                    return null;
                }
                try {
                    entry.writeTo(edit);
                    return new CacheRequestImpl(edit);
                } catch (IOException e2) {
                    abortQuietly(edit);
                    return null;
                }
            } catch (IOException e3) {
                edit = null;
                abortQuietly(edit);
                return null;
            }
        }
    }

    private static int readInt(a aVar) {
        Object obj = ENTRY_BODY;
        try {
            long zR = aVar.zR();
            String zW = aVar.zW();
            if ((zR < 0 ? ENTRY_BODY : ENTRY_METADATA) == null) {
                if (zR <= 2147483647L) {
                    obj = ENTRY_METADATA;
                }
                if (obj == null && zW.isEmpty()) {
                    return (int) zR;
                }
            }
            throw new IOException("expected an int but was \"" + zR + zW + "\"");
        } catch (NumberFormatException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void remove(Request request) {
        this.cache.remove(urlToKey(request));
    }

    private synchronized void trackConditionalCacheHit() {
        this.hitCount += ENTRY_BODY;
    }

    private synchronized void trackResponse(CacheStrategy cacheStrategy) {
        this.requestCount += ENTRY_BODY;
        if (cacheStrategy.networkRequest != null) {
            this.networkCount += ENTRY_BODY;
        } else if (cacheStrategy.cacheResponse != null) {
            this.hitCount += ENTRY_BODY;
        }
    }

    private void update(Response response, Response response2) {
        Entry entry = new Entry(response2);
        Editor edit;
        try {
            edit = ((CacheResponseBody) response.body()).snapshot.edit();
            if (edit != null) {
                try {
                    entry.writeTo(edit);
                    edit.commit();
                } catch (IOException e) {
                    abortQuietly(edit);
                }
            }
        } catch (IOException e2) {
            edit = null;
            abortQuietly(edit);
        }
    }

    private static String urlToKey(Request request) {
        return Util.md5Hex(request.urlString());
    }

    public void close() {
        this.cache.close();
    }

    public void delete() {
        this.cache.delete();
    }

    public void evictAll() {
        this.cache.evictAll();
    }

    public void flush() {
        this.cache.flush();
    }

    Response get(Request request) {
        try {
            Closeable closeable = this.cache.get(urlToKey(request));
            if (closeable == null) {
                return null;
            }
            try {
                Entry entry = new Entry(closeable.getSource(ENTRY_METADATA));
                Response response = entry.response(request, closeable);
                if (entry.matches(request, response)) {
                    return response;
                }
                Util.closeQuietly(response.body());
                return null;
            } catch (IOException e) {
                Util.closeQuietly(closeable);
                return null;
            }
        } catch (IOException e2) {
            return null;
        }
    }

    public File getDirectory() {
        return this.cache.getDirectory();
    }

    public synchronized int getHitCount() {
        return this.hitCount;
    }

    public long getMaxSize() {
        return this.cache.getMaxSize();
    }

    public synchronized int getNetworkCount() {
        return this.networkCount;
    }

    public synchronized int getRequestCount() {
        return this.requestCount;
    }

    public long getSize() {
        return this.cache.size();
    }

    public synchronized int getWriteAbortCount() {
        return this.writeAbortCount;
    }

    public synchronized int getWriteSuccessCount() {
        return this.writeSuccessCount;
    }

    public boolean isClosed() {
        return this.cache.isClosed();
    }

    public Iterator urls() {
        return new Iterator() {
            boolean canRemove;
            final Iterator delegate = Cache.this.cache.snapshots();
            String nextUrl;

            public boolean hasNext() {
                if (this.nextUrl != null) {
                    return true;
                }
                this.canRemove = false;
                while (this.delegate.hasNext()) {
                    Snapshot snapshot = (Snapshot) this.delegate.next();
                    try {
                        this.nextUrl = j.AE(snapshot.getSource(Cache.ENTRY_METADATA)).zW();
                        snapshot.close();
                        return true;
                    } catch (IOException e) {
                        snapshot.close();
                    } catch (Throwable th) {
                        snapshot.close();
                    }
                }
                return false;
            }

            public String next() {
                if (hasNext()) {
                    String str = this.nextUrl;
                    this.nextUrl = null;
                    this.canRemove = true;
                    return str;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                if (this.canRemove) {
                    this.delegate.remove();
                    return;
                }
                throw new IllegalStateException("remove() before next()");
            }
        };
    }
}
