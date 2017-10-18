package com.squareup.okhttp.internal.spdy;

import java.io.Closeable;
import java.util.List;
import okio.k;

public interface FrameWriter extends Closeable {
    void ackSettings(Settings settings);

    void connectionPreface();

    void data(boolean z, int i, k kVar, int i2);

    void flush();

    void goAway(int i, ErrorCode errorCode, byte[] bArr);

    void headers(int i, List list);

    int maxDataLength();

    void ping(boolean z, int i, int i2);

    void pushPromise(int i, int i2, List list);

    void rstStream(int i, ErrorCode errorCode);

    void settings(Settings settings);

    void synReply(boolean z, int i, List list);

    void synStream(boolean z, boolean z2, int i, int i2, List list);

    void windowUpdate(int i, long j);
}
