package com.squareup.okhttp.internal.spdy;

import java.io.Closeable;
import java.util.List;
import okio.ByteString;
import okio.a;

public interface FrameReader extends Closeable {

    public interface Handler {
        void ackSettings();

        void alternateService(int i, String str, ByteString byteString, String str2, int i2, long j);

        void data(boolean z, int i, a aVar, int i2);

        void goAway(int i, ErrorCode errorCode, ByteString byteString);

        void headers(boolean z, boolean z2, int i, int i2, List list, HeadersMode headersMode);

        void ping(boolean z, int i, int i2);

        void priority(int i, int i2, int i3, boolean z);

        void pushPromise(int i, int i2, List list);

        void rstStream(int i, ErrorCode errorCode);

        void settings(boolean z, Settings settings);

        void windowUpdate(int i, long j);
    }

    boolean nextFrame(Handler handler);

    void readConnectionPreface();
}
