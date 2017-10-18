package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    public class Factory {
        private int ageSeconds = -1;
        final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        final long nowMillis;
        private long receivedResponseMillis;
        final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        public Factory(long j, Request request, Response response) {
            this.nowMillis = j;
            this.request = request;
            this.cacheResponse = response;
            if (response != null) {
                Headers headers = response.headers();
                int size = headers.size();
                for (int i = 0; i < size; i++) {
                    String name = headers.name(i);
                    String value = headers.value(i);
                    if ("Date".equalsIgnoreCase(name)) {
                        this.servedDate = HttpDate.parse(value);
                        this.servedDateString = value;
                    } else if ("Expires".equalsIgnoreCase(name)) {
                        this.expires = HttpDate.parse(value);
                    } else if ("Last-Modified".equalsIgnoreCase(name)) {
                        this.lastModified = HttpDate.parse(value);
                        this.lastModifiedString = value;
                    } else if ("ETag".equalsIgnoreCase(name)) {
                        this.etag = value;
                    } else if ("Age".equalsIgnoreCase(name)) {
                        this.ageSeconds = HeaderParser.parseSeconds(value, -1);
                    } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(name)) {
                        this.sentRequestMillis = Long.parseLong(value);
                    } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(name)) {
                        this.receivedResponseMillis = Long.parseLong(value);
                    }
                }
            }
        }

        private long cacheResponseAge() {
            long j = 0;
            if (this.servedDate != null) {
                j = Math.max(0, this.receivedResponseMillis - this.servedDate.getTime());
            }
            if (this.ageSeconds != -1) {
                j = Math.max(j, TimeUnit.SECONDS.toMillis((long) this.ageSeconds));
            }
            return (j + (this.receivedResponseMillis - this.sentRequestMillis)) + (this.nowMillis - this.receivedResponseMillis);
        }

        private long computeFreshnessLifetime() {
            Object obj = 1;
            long j = 0;
            CacheControl cacheControl = this.cacheResponse.cacheControl();
            if (cacheControl.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis((long) cacheControl.maxAgeSeconds());
            }
            if (this.expires != null) {
                long time = this.expires.getTime() - (this.servedDate == null ? this.receivedResponseMillis : this.servedDate.getTime());
                if (time > 0) {
                    obj = null;
                }
                if (obj != null) {
                    time = 0;
                }
                return time;
            } else if (this.lastModified == null || this.cacheResponse.request().url().getQuery() != null) {
                return 0;
            } else {
                long time2 = (this.servedDate == null ? this.sentRequestMillis : this.servedDate.getTime()) - this.lastModified.getTime();
                if ((time2 <= 0 ? 1 : null) == null) {
                    j = time2 / 10;
                }
                return j;
            }
        }

        private CacheStrategy getCandidate() {
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            CacheControl cacheControl = this.request.cacheControl();
            if (cacheControl.noCache() || hasConditions(this.request)) {
                return new CacheStrategy(this.request, null);
            }
            long cacheResponseAge = cacheResponseAge();
            long computeFreshnessLifetime = computeFreshnessLifetime();
            if (cacheControl.maxAgeSeconds() != -1) {
                computeFreshnessLifetime = Math.min(computeFreshnessLifetime, TimeUnit.SECONDS.toMillis((long) cacheControl.maxAgeSeconds()));
            }
            long j = 0;
            if (cacheControl.minFreshSeconds() != -1) {
                j = TimeUnit.SECONDS.toMillis((long) cacheControl.minFreshSeconds());
            }
            long j2 = 0;
            CacheControl cacheControl2 = this.cacheResponse.cacheControl();
            if (!(cacheControl2.mustRevalidate() || cacheControl.maxStaleSeconds() == -1)) {
                j2 = TimeUnit.SECONDS.toMillis((long) cacheControl.maxStaleSeconds());
            }
            if (!cacheControl2.noCache()) {
                if ((cacheResponseAge + j >= j2 + computeFreshnessLifetime ? 1 : null) == null) {
                    Builder newBuilder = this.cacheResponse.newBuilder();
                    if ((j + cacheResponseAge < computeFreshnessLifetime ? 1 : null) == null) {
                        newBuilder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                    }
                    if ((cacheResponseAge <= 86400000 ? 1 : null) == null && isFreshnessLifetimeHeuristic()) {
                        newBuilder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new CacheStrategy(null, newBuilder.build());
                }
            }
            Request.Builder newBuilder2 = this.request.newBuilder();
            if (this.etag != null) {
                newBuilder2.header("If-None-Match", this.etag);
            } else if (this.lastModified != null) {
                newBuilder2.header("If-Modified-Since", this.lastModifiedString);
            } else if (this.servedDate != null) {
                newBuilder2.header("If-Modified-Since", this.servedDateString);
            }
            Request build = newBuilder2.build();
            return !hasConditions(build) ? new CacheStrategy(build, null) : new CacheStrategy(build, this.cacheResponse);
        }

        private static boolean hasConditions(Request request) {
            return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        public CacheStrategy get() {
            CacheStrategy candidate = getCandidate();
            return (candidate.networkRequest != null && this.request.cacheControl().onlyIfCached()) ? new CacheStrategy(null, null) : candidate;
        }
    }

    private CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isCacheable(com.squareup.okhttp.Response r3, com.squareup.okhttp.Request r4) {
        /*
        r0 = 0;
        r1 = r3.code();
        switch(r1) {
            case 200: goto L_0x0012;
            case 203: goto L_0x0012;
            case 204: goto L_0x0012;
            case 300: goto L_0x0012;
            case 301: goto L_0x0012;
            case 302: goto L_0x0009;
            case 307: goto L_0x0009;
            case 308: goto L_0x0012;
            case 404: goto L_0x0012;
            case 405: goto L_0x0012;
            case 410: goto L_0x0012;
            case 414: goto L_0x0012;
            case 501: goto L_0x0012;
            default: goto L_0x0008;
        };
    L_0x0008:
        return r0;
    L_0x0009:
        r1 = "Expires";
        r1 = r3.header(r1);
        if (r1 == 0) goto L_0x001d;
    L_0x0012:
        r1 = r3.cacheControl();
        r1 = r1.noStore();
        if (r1 == 0) goto L_0x003d;
    L_0x001c:
        return r0;
    L_0x001d:
        r1 = r3.cacheControl();
        r1 = r1.maxAgeSeconds();
        r2 = -1;
        if (r1 != r2) goto L_0x0012;
    L_0x0028:
        r1 = r3.cacheControl();
        r1 = r1.isPublic();
        if (r1 != 0) goto L_0x0012;
    L_0x0032:
        r1 = r3.cacheControl();
        r1 = r1.isPrivate();
        if (r1 != 0) goto L_0x0012;
    L_0x003c:
        goto L_0x0008;
    L_0x003d:
        r1 = r4.cacheControl();
        r1 = r1.noStore();
        if (r1 != 0) goto L_0x001c;
    L_0x0047:
        r0 = 1;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.CacheStrategy.isCacheable(com.squareup.okhttp.Response, com.squareup.okhttp.Request):boolean");
    }
}
