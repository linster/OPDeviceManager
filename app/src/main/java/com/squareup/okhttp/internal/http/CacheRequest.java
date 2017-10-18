package com.squareup.okhttp.internal.http;

import okio.n;

public interface CacheRequest {
    void abort();

    n body();
}
