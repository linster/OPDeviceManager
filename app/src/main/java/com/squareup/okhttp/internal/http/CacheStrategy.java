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

    public static class Factory {
        private int ageSeconds;
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

        public Factory(long nowMillis, Request request, Response cacheResponse) {
            this.ageSeconds = -1;
            this.nowMillis = nowMillis;
            this.request = request;
            this.cacheResponse = cacheResponse;
            if (cacheResponse != null) {
                Headers headers = cacheResponse.headers();
                int size = headers.size();
                for (int i = 0; i < size; i++) {
                    String fieldName = headers.name(i);
                    String value = headers.value(i);
                    if ("Date".equalsIgnoreCase(fieldName)) {
                        this.servedDate = HttpDate.parse(value);
                        this.servedDateString = value;
                    } else if ("Expires".equalsIgnoreCase(fieldName)) {
                        this.expires = HttpDate.parse(value);
                    } else if ("Last-Modified".equalsIgnoreCase(fieldName)) {
                        this.lastModified = HttpDate.parse(value);
                        this.lastModifiedString = value;
                    } else if ("ETag".equalsIgnoreCase(fieldName)) {
                        this.etag = value;
                    } else if ("Age".equalsIgnoreCase(fieldName)) {
                        this.ageSeconds = HeaderParser.parseSeconds(value, -1);
                    } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.sentRequestMillis = Long.parseLong(value);
                    } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.receivedResponseMillis = Long.parseLong(value);
                    }
                }
            }
        }

        public CacheStrategy get() {
            CacheStrategy candidate = getCandidate();
            if (candidate.networkRequest != null && this.request.cacheControl().onlyIfCached()) {
                return new CacheStrategy(null, null);
            }
            return candidate;
        }

        private CacheStrategy getCandidate() {
            if (this.cacheResponse != null) {
                if (this.request.isHttps()) {
                    if (this.cacheResponse.handshake() == null) {
                        return new CacheStrategy(null, null);
                    }
                }
                if (CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                    CacheControl requestCaching = this.request.cacheControl();
                    if (!requestCaching.noCache()) {
                        if (!hasConditions(this.request)) {
                            long ageMillis = cacheResponseAge();
                            long freshMillis = computeFreshnessLifetime();
                            if (requestCaching.maxAgeSeconds() != -1) {
                                freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis((long) requestCaching.maxAgeSeconds()));
                            }
                            long minFreshMillis = 0;
                            if (requestCaching.minFreshSeconds() != -1) {
                                minFreshMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.minFreshSeconds());
                            }
                            long maxStaleMillis = 0;
                            CacheControl responseCaching = this.cacheResponse.cacheControl();
                            if (!(responseCaching.mustRevalidate() || requestCaching.maxStaleSeconds() == -1)) {
                                maxStaleMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.maxStaleSeconds());
                            }
                            if (!responseCaching.noCache()) {
                                if ((ageMillis + minFreshMillis >= freshMillis + maxStaleMillis ? 1 : null) == null) {
                                    Builder builder = this.cacheResponse.newBuilder();
                                    if ((ageMillis + minFreshMillis < freshMillis ? 1 : null) == null) {
                                        builder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                                    }
                                    if ((ageMillis <= 86400000 ? 1 : null) == null && isFreshnessLifetimeHeuristic()) {
                                        builder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                                    }
                                    return new CacheStrategy(builder.build(), null);
                                }
                            }
                            Request.Builder conditionalRequestBuilder = this.request.newBuilder();
                            if (this.etag != null) {
                                conditionalRequestBuilder.header("If-None-Match", this.etag);
                            } else if (this.lastModified != null) {
                                conditionalRequestBuilder.header("If-Modified-Since", this.lastModifiedString);
                            } else if (this.servedDate != null) {
                                conditionalRequestBuilder.header("If-Modified-Since", this.servedDateString);
                            }
                            Request conditionalRequest = conditionalRequestBuilder.build();
                            CacheStrategy cacheStrategy;
                            if (hasConditions(conditionalRequest)) {
                                cacheStrategy = new CacheStrategy(this.cacheResponse, null);
                            } else {
                                cacheStrategy = new CacheStrategy(null, null);
                            }
                            return r17;
                        }
                    }
                    return new CacheStrategy(null, null);
                }
                return new CacheStrategy(null, null);
            }
            return new CacheStrategy(null, null);
        }

        private long computeFreshnessLifetime() {
            Object obj = 1;
            long j = 0;
            CacheControl responseCaching = this.cacheResponse.cacheControl();
            if (responseCaching.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis((long) responseCaching.maxAgeSeconds());
            }
            long delta;
            if (this.expires != null) {
                delta = this.expires.getTime() - (this.servedDate == null ? this.receivedResponseMillis : this.servedDate.getTime());
                if (delta > 0) {
                    obj = null;
                }
                if (obj != null) {
                    delta = 0;
                }
                return delta;
            } else if (this.lastModified == null || this.cacheResponse.request().url().getQuery() != null) {
                return 0;
            } else {
                delta = (this.servedDate == null ? this.sentRequestMillis : this.servedDate.getTime()) - this.lastModified.getTime();
                if (delta > 0) {
                    obj = null;
                }
                if (obj == null) {
                    j = delta / 10;
                }
                return j;
            }
        }

        private long cacheResponseAge() {
            long receivedAge;
            long apparentReceivedAge = 0;
            if (this.servedDate != null) {
                apparentReceivedAge = Math.max(0, this.receivedResponseMillis - this.servedDate.getTime());
            }
            if (this.ageSeconds == -1) {
                receivedAge = apparentReceivedAge;
            } else {
                receivedAge = Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis((long) this.ageSeconds));
            }
            return (receivedAge + (this.receivedResponseMillis - this.sentRequestMillis)) + (this.nowMillis - this.receivedResponseMillis);
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        private static boolean hasConditions(Request request) {
            return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
        }
    }

    private CacheStrategy(Request networkRequest, Response cacheResponse) {
        this.networkRequest = networkRequest;
        this.cacheResponse = cacheResponse;
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
