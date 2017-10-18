package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpMethod;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public final class Request {
    private final RequestBody body;
    private volatile CacheControl cacheControl;
    private final Headers headers;
    private final String method;
    private final Object tag;
    private volatile URI uri;
    private volatile URL url;
    private final String urlString;

    public class Builder {
        private RequestBody body;
        private com.squareup.okhttp.Headers.Builder headers;
        private String method;
        private Object tag;
        private URL url;
        private String urlString;

        public Builder() {
            this.method = "GET";
            this.headers = new com.squareup.okhttp.Headers.Builder();
        }

        private Builder(Request request) {
            this.urlString = request.urlString;
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        public Builder addHeader(String str, String str2) {
            this.headers.add(str, str2);
            return this;
        }

        public Request build() {
            if (this.urlString != null) {
                return new Request();
            }
            throw new IllegalStateException("url == null");
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String cacheControl2 = cacheControl.toString();
            return !cacheControl2.isEmpty() ? header("Cache-Control", cacheControl2) : removeHeader("Cache-Control");
        }

        public Builder delete() {
            return method("DELETE", null);
        }

        public Builder delete(RequestBody requestBody) {
            return method("DELETE", requestBody);
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder head() {
            return method("HEAD", null);
        }

        public Builder header(String str, String str2) {
            this.headers.set(str, str2);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder method(String str, RequestBody requestBody) {
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            } else if (requestBody == null || HttpMethod.permitsRequestBody(str)) {
                if (requestBody == null && HttpMethod.permitsRequestBody(str)) {
                    requestBody = RequestBody.create(null, Util.EMPTY_BYTE_ARRAY);
                }
                this.method = str;
                this.body = requestBody;
                return this;
            } else {
                throw new IllegalArgumentException("method " + str + " must not have a request body.");
            }
        }

        public Builder patch(RequestBody requestBody) {
            return method("PATCH", requestBody);
        }

        public Builder post(RequestBody requestBody) {
            return method("POST", requestBody);
        }

        public Builder put(RequestBody requestBody) {
            return method("PUT", requestBody);
        }

        public Builder removeHeader(String str) {
            this.headers.removeAll(str);
            return this;
        }

        public Builder tag(Object obj) {
            this.tag = obj;
            return this;
        }

        public Builder url(String str) {
            if (str != null) {
                this.urlString = str;
                this.url = null;
                return this;
            }
            throw new IllegalArgumentException("url == null");
        }

        public Builder url(URL url) {
            if (url != null) {
                this.url = url;
                this.urlString = url.toString();
                return this;
            }
            throw new IllegalArgumentException("url == null");
        }
    }

    private Request(Builder builder) {
        this.urlString = builder.urlString;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.tag = builder.tag == null ? this : builder.tag;
        this.url = builder.url;
    }

    public RequestBody body() {
        return this.body;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        cacheControl = CacheControl.parse(this.headers);
        this.cacheControl = cacheControl;
        return cacheControl;
    }

    public String header(String str) {
        return this.headers.get(str);
    }

    public Headers headers() {
        return this.headers;
    }

    public List headers(String str) {
        return this.headers.values(str);
    }

    public boolean isHttps() {
        return url().getProtocol().equals("https");
    }

    public String method() {
        return this.method;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public Object tag() {
        return this.tag;
    }

    public String toString() {
        return "Request{method=" + this.method + ", url=" + this.urlString + ", tag=" + (this.tag == this ? null : this.tag) + '}';
    }

    public URI uri() {
        try {
            URI uri = this.uri;
            if (uri != null) {
                return uri;
            }
            uri = Platform.get().toUriLenient(url());
            this.uri = uri;
            return uri;
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage());
        }
    }

    public URL url() {
        try {
            URL url = this.url;
            if (url != null) {
                return url;
            }
            url = new URL(this.urlString);
            this.url = url;
            return url;
        } catch (Throwable e) {
            throw new RuntimeException("Malformed URL: " + this.urlString, e);
        }
    }

    public String urlString() {
        return this.urlString;
    }
}
