package com.squareup.okhttp.internal.spdy;

import java.util.List;
import okio.a;

public interface PushObserver {
    public static final PushObserver CANCEL = new PushObserver() {
        public boolean onData(int i, a aVar, int i2, boolean z) {
            aVar.skip((long) i2);
            return true;
        }

        public boolean onHeaders(int i, List list, boolean z) {
            return true;
        }

        public boolean onRequest(int i, List list) {
            return true;
        }

        public void onReset(int i, ErrorCode errorCode) {
        }
    };

    boolean onData(int i, a aVar, int i2, boolean z);

    boolean onHeaders(int i, List list, boolean z);

    boolean onRequest(int i, List list);

    void onReset(int i, ErrorCode errorCode);
}
