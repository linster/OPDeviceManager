package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HeaderParser;

import java.util.concurrent.TimeUnit;

public final class CacheControl {
    public static final CacheControl FORCE_CACHE;
    public static final CacheControl FORCE_NETWORK;
    String headerValue;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    public static final class Builder {
        int maxAgeSeconds;
        int maxStaleSeconds;
        int minFreshSeconds;
        boolean noCache;
        boolean noStore;
        boolean noTransform;
        boolean onlyIfCached;

        public Builder() {
            this.maxAgeSeconds = -1;
            this.maxStaleSeconds = -1;
            this.minFreshSeconds = -1;
        }

        public Builder noCache() {
            this.noCache = true;
            return this;
        }

        public Builder noStore() {
            this.noStore = true;
            return this;
        }

        public Builder maxAge(int maxAge, TimeUnit timeUnit) {
            Object obj = null;
            if (maxAge >= 0) {
                long maxAgeSecondsLong = timeUnit.toSeconds((long) maxAge);
                if (maxAgeSecondsLong <= 2147483647L) {
                    obj = 1;
                }
                this.maxAgeSeconds = obj == null ? Integer.MAX_VALUE : (int) maxAgeSecondsLong;
                return this;
            }
            throw new IllegalArgumentException("maxAge < 0: " + maxAge);
        }

        public Builder maxStale(int maxStale, TimeUnit timeUnit) {
            Object obj = null;
            if (maxStale >= 0) {
                long maxStaleSecondsLong = timeUnit.toSeconds((long) maxStale);
                if (maxStaleSecondsLong <= 2147483647L) {
                    obj = 1;
                }
                this.maxStaleSeconds = obj == null ? Integer.MAX_VALUE : (int) maxStaleSecondsLong;
                return this;
            }
            throw new IllegalArgumentException("maxStale < 0: " + maxStale);
        }

        public Builder minFresh(int minFresh, TimeUnit timeUnit) {
            Object obj = null;
            if (minFresh >= 0) {
                long minFreshSecondsLong = timeUnit.toSeconds((long) minFresh);
                if (minFreshSecondsLong <= 2147483647L) {
                    obj = 1;
                }
                this.minFreshSeconds = obj == null ? Integer.MAX_VALUE : (int) minFreshSecondsLong;
                return this;
            }
            throw new IllegalArgumentException("minFresh < 0: " + minFresh);
        }

        public Builder onlyIfCached() {
            this.onlyIfCached = true;
            return this;
        }

        public Builder noTransform() {
            this.noTransform = true;
            return this;
        }

        public CacheControl build() {
            return new CacheControl();
        }
    }

    static {
        FORCE_NETWORK = new Builder();
        Object builder = new Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        FORCE_CACHE = builder;
    }

    private CacheControl(boolean noCache, boolean noStore, int maxAgeSeconds, int sMaxAgeSeconds, boolean isPrivate, boolean isPublic, boolean mustRevalidate, int maxStaleSeconds, int minFreshSeconds, boolean onlyIfCached, boolean noTransform, String headerValue) {
        this.noCache = noCache;
        this.noStore = noStore;
        this.maxAgeSeconds = maxAgeSeconds;
        this.sMaxAgeSeconds = sMaxAgeSeconds;
        this.isPrivate = isPrivate;
        this.isPublic = isPublic;
        this.mustRevalidate = mustRevalidate;
        this.maxStaleSeconds = maxStaleSeconds;
        this.minFreshSeconds = minFreshSeconds;
        this.onlyIfCached = onlyIfCached;
        this.noTransform = noTransform;
        this.headerValue = headerValue;
    }

    private CacheControl(Builder builder) {
        this.noCache = builder.noCache;
        this.noStore = builder.noStore;
        this.maxAgeSeconds = builder.maxAgeSeconds;
        this.sMaxAgeSeconds = -1;
        this.isPrivate = false;
        this.isPublic = false;
        this.mustRevalidate = false;
        this.maxStaleSeconds = builder.maxStaleSeconds;
        this.minFreshSeconds = builder.minFreshSeconds;
        this.onlyIfCached = builder.onlyIfCached;
        this.noTransform = builder.noTransform;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public boolean noTransform() {
        return this.noTransform;
    }

    public static CacheControl parse(Headers headers) {
        boolean noCache = false;
        boolean noStore = false;
        int maxAgeSeconds = -1;
        int sMaxAgeSeconds = -1;
        boolean isPrivate = false;
        boolean isPublic = false;
        boolean mustRevalidate = false;
        int maxStaleSeconds = -1;
        int minFreshSeconds = -1;
        boolean onlyIfCached = false;
        boolean noTransform = false;
        boolean canUseHeaderValue = true;
        String headerValue = null;
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            int pos;
            int tokenStart;
            String directive;
            int parameterStart;
            String substring;
            if (name.equalsIgnoreCase("Cache-Control")) {
                if (headerValue == null) {
                    headerValue = value;
                } else {
                    canUseHeaderValue = false;
                }
                pos = 0;
                while (pos < value.length()) {
                    tokenStart = pos;
                    pos = HeaderParser.skipUntil(value, pos, "=,;");
                    directive = value.substring(tokenStart, pos).trim();
                    if (pos == value.length()) {
                        pos = HeaderParser.skipWhitespace(value, pos + 1);
                        if (pos < value.length()) {
                            pos++;
                            parameterStart = pos;
                            pos = HeaderParser.skipUntil(value, pos, "\"");
                            substring = value.substring(parameterStart, pos);
                            pos++;
                            if ("no-cache".equalsIgnoreCase(directive)) {
                                noCache = true;
                            } else if ("no-store".equalsIgnoreCase(directive)) {
                                noStore = true;
                            } else if ("max-age".equalsIgnoreCase(directive)) {
                                maxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                            } else if ("s-maxage".equalsIgnoreCase(directive)) {
                                sMaxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                            } else if ("private".equalsIgnoreCase(directive)) {
                                isPrivate = true;
                            } else if ("public".equalsIgnoreCase(directive)) {
                                isPublic = true;
                            } else if ("must-revalidate".equalsIgnoreCase(directive)) {
                                mustRevalidate = true;
                            } else if ("max-stale".equalsIgnoreCase(directive)) {
                                maxStaleSeconds = HeaderParser.parseSeconds(substring, Integer.MAX_VALUE);
                            } else if ("min-fresh".equalsIgnoreCase(directive)) {
                                minFreshSeconds = HeaderParser.parseSeconds(substring, -1);
                            } else if ("only-if-cached".equalsIgnoreCase(directive)) {
                                onlyIfCached = true;
                            } else if (!"no-transform".equalsIgnoreCase(directive)) {
                                noTransform = true;
                            }
                        }
                        parameterStart = pos;
                        pos = HeaderParser.skipUntil(value, pos, ",;");
                        substring = value.substring(parameterStart, pos).trim();
                        if ("no-cache".equalsIgnoreCase(directive)) {
                            noCache = true;
                        } else if ("no-store".equalsIgnoreCase(directive)) {
                            noStore = true;
                        } else if ("max-age".equalsIgnoreCase(directive)) {
                            maxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                        } else if ("s-maxage".equalsIgnoreCase(directive)) {
                            sMaxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                        } else if ("private".equalsIgnoreCase(directive)) {
                            isPrivate = true;
                        } else if ("public".equalsIgnoreCase(directive)) {
                            isPublic = true;
                        } else if ("must-revalidate".equalsIgnoreCase(directive)) {
                            mustRevalidate = true;
                        } else if ("max-stale".equalsIgnoreCase(directive)) {
                            maxStaleSeconds = HeaderParser.parseSeconds(substring, Integer.MAX_VALUE);
                        } else if ("min-fresh".equalsIgnoreCase(directive)) {
                            minFreshSeconds = HeaderParser.parseSeconds(substring, -1);
                        } else if ("only-if-cached".equalsIgnoreCase(directive)) {
                            onlyIfCached = true;
                        } else if (!"no-transform".equalsIgnoreCase(directive)) {
                            noTransform = true;
                        }
                    }
                    pos++;
                    substring = null;
                    if ("no-cache".equalsIgnoreCase(directive)) {
                        noCache = true;
                    } else if ("no-store".equalsIgnoreCase(directive)) {
                        noStore = true;
                    } else if ("max-age".equalsIgnoreCase(directive)) {
                        maxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                    } else if ("s-maxage".equalsIgnoreCase(directive)) {
                        sMaxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                    } else if ("private".equalsIgnoreCase(directive)) {
                        isPrivate = true;
                    } else if ("public".equalsIgnoreCase(directive)) {
                        isPublic = true;
                    } else if ("must-revalidate".equalsIgnoreCase(directive)) {
                        mustRevalidate = true;
                    } else if ("max-stale".equalsIgnoreCase(directive)) {
                        maxStaleSeconds = HeaderParser.parseSeconds(substring, Integer.MAX_VALUE);
                    } else if ("min-fresh".equalsIgnoreCase(directive)) {
                        minFreshSeconds = HeaderParser.parseSeconds(substring, -1);
                    } else if ("only-if-cached".equalsIgnoreCase(directive)) {
                        onlyIfCached = true;
                    } else if (!"no-transform".equalsIgnoreCase(directive)) {
                        noTransform = true;
                    }
                }
            } else {
                if (name.equalsIgnoreCase("Pragma")) {
                    canUseHeaderValue = false;
                    pos = 0;
                    while (pos < value.length()) {
                        tokenStart = pos;
                        pos = HeaderParser.skipUntil(value, pos, "=,;");
                        directive = value.substring(tokenStart, pos).trim();
                        if (pos == value.length() || value.charAt(pos) == ',' || value.charAt(pos) == ';') {
                            pos++;
                            substring = null;
                        } else {
                            pos = HeaderParser.skipWhitespace(value, pos + 1);
                            if (pos < value.length() && value.charAt(pos) == '\"') {
                                pos++;
                                parameterStart = pos;
                                pos = HeaderParser.skipUntil(value, pos, "\"");
                                substring = value.substring(parameterStart, pos);
                                pos++;
                            } else {
                                parameterStart = pos;
                                pos = HeaderParser.skipUntil(value, pos, ",;");
                                substring = value.substring(parameterStart, pos).trim();
                            }
                        }
                        if ("no-cache".equalsIgnoreCase(directive)) {
                            noCache = true;
                        } else if ("no-store".equalsIgnoreCase(directive)) {
                            noStore = true;
                        } else if ("max-age".equalsIgnoreCase(directive)) {
                            maxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                        } else if ("s-maxage".equalsIgnoreCase(directive)) {
                            sMaxAgeSeconds = HeaderParser.parseSeconds(substring, -1);
                        } else if ("private".equalsIgnoreCase(directive)) {
                            isPrivate = true;
                        } else if ("public".equalsIgnoreCase(directive)) {
                            isPublic = true;
                        } else if ("must-revalidate".equalsIgnoreCase(directive)) {
                            mustRevalidate = true;
                        } else if ("max-stale".equalsIgnoreCase(directive)) {
                            maxStaleSeconds = HeaderParser.parseSeconds(substring, Integer.MAX_VALUE);
                        } else if ("min-fresh".equalsIgnoreCase(directive)) {
                            minFreshSeconds = HeaderParser.parseSeconds(substring, -1);
                        } else if ("only-if-cached".equalsIgnoreCase(directive)) {
                            onlyIfCached = true;
                        } else if (!"no-transform".equalsIgnoreCase(directive)) {
                            noTransform = true;
                        }
                    }
                }
            }
        }
        if (!canUseHeaderValue) {
            headerValue = null;
        }
        return new CacheControl(noCache, noStore, maxAgeSeconds, sMaxAgeSeconds, isPrivate, isPublic, mustRevalidate, maxStaleSeconds, minFreshSeconds, onlyIfCached, noTransform, headerValue);
    }

    public String toString() {
        String str = this.headerValue;
        if (str != null) {
            return str;
        }
        str = headerValue();
        this.headerValue = str;
        return str;
    }

    private String headerValue() {
        StringBuilder result = new StringBuilder();
        if (this.noCache) {
            result.append("no-cache, ");
        }
        if (this.noStore) {
            result.append("no-store, ");
        }
        if (this.maxAgeSeconds != -1) {
            result.append("max-age=").append(this.maxAgeSeconds).append(", ");
        }
        if (this.sMaxAgeSeconds != -1) {
            result.append("s-maxage=").append(this.sMaxAgeSeconds).append(", ");
        }
        if (this.isPrivate) {
            result.append("private, ");
        }
        if (this.isPublic) {
            result.append("public, ");
        }
        if (this.mustRevalidate) {
            result.append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            result.append("max-stale=").append(this.maxStaleSeconds).append(", ");
        }
        if (this.minFreshSeconds != -1) {
            result.append("min-fresh=").append(this.minFreshSeconds).append(", ");
        }
        if (this.onlyIfCached) {
            result.append("only-if-cached, ");
        }
        if (this.noTransform) {
            result.append("no-transform, ");
        }
        if (result.length() == 0) {
            return "";
        }
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }
}
