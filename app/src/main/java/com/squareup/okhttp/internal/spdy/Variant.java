package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import okio.a;
import okio.b;

public interface Variant {
    Protocol getProtocol();

    FrameReader newReader(a aVar, boolean z);

    FrameWriter newWriter(b bVar, boolean z);
}
