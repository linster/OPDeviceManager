package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;
import okio.a;

public final class RealResponseBody extends ResponseBody {
    private final Headers headers;
    private final a source;

    public RealResponseBody(Headers headers, a aVar) {
        this.headers = headers;
        this.source = aVar;
    }

    public long contentLength() {
        return OkHeaders.contentLength(this.headers);
    }

    public MediaType contentType() {
        String str = this.headers.get("Content-Type");
        return str == null ? null : MediaType.parse(str);
    }

    public a source() {
        return this.source;
    }
}
