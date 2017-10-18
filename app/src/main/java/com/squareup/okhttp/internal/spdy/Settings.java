package com.squareup.okhttp.internal.spdy;

import java.util.Arrays;

public final class Settings {
    static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
    static final int COUNT = 10;
    static final int CURRENT_CWND = 5;
    static final int DEFAULT_INITIAL_WINDOW_SIZE = 65536;
    static final int DOWNLOAD_BANDWIDTH = 2;
    static final int DOWNLOAD_RETRANS_RATE = 6;
    static final int ENABLE_PUSH = 2;
    static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 1;
    static final int FLOW_CONTROL_OPTIONS = 10;
    static final int FLOW_CONTROL_OPTIONS_DISABLED = 1;
    static final int HEADER_TABLE_SIZE = 1;
    static final int INITIAL_WINDOW_SIZE = 7;
    static final int MAX_CONCURRENT_STREAMS = 4;
    static final int MAX_FRAME_SIZE = 5;
    static final int MAX_HEADER_LIST_SIZE = 6;
    static final int PERSISTED = 2;
    static final int PERSIST_VALUE = 1;
    static final int ROUND_TRIP_TIME = 3;
    static final int UPLOAD_BANDWIDTH = 1;
    private int persistValue;
    private int persisted;
    private int set;
    private final int[] values = new int[10];

    void clear() {
        this.persisted = 0;
        this.persistValue = 0;
        this.set = 0;
        Arrays.fill(this.values, 0);
    }

    int flags(int i) {
        int i2 = 0;
        if (isPersisted(i)) {
            i2 = 2;
        }
        return !persistValue(i) ? i2 : i2 | 1;
    }

    int get(int i) {
        return this.values[i];
    }

    int getClientCertificateVectorSize(int i) {
        return (this.set & 256) == 0 ? i : this.values[CLIENT_CERTIFICATE_VECTOR_SIZE];
    }

    int getCurrentCwnd(int i) {
        return (this.set & 32) == 0 ? i : this.values[5];
    }

    int getDownloadBandwidth(int i) {
        return (this.set & MAX_CONCURRENT_STREAMS) == 0 ? i : this.values[2];
    }

    int getDownloadRetransRate(int i) {
        return (this.set & 64) == 0 ? i : this.values[6];
    }

    boolean getEnablePush(boolean z) {
        boolean z2 = (this.set & MAX_CONCURRENT_STREAMS) == 0 ? z : this.values[2];
        return z2;
    }

    int getHeaderTableSize() {
        return (this.set & 2) == 0 ? -1 : this.values[1];
    }

    int getInitialWindowSize(int i) {
        return (this.set & 128) == 0 ? i : this.values[INITIAL_WINDOW_SIZE];
    }

    int getMaxConcurrentStreams(int i) {
        return (this.set & 16) == 0 ? i : this.values[MAX_CONCURRENT_STREAMS];
    }

    int getMaxFrameSize(int i) {
        return (this.set & 32) == 0 ? i : this.values[5];
    }

    int getMaxHeaderListSize(int i) {
        return (this.set & 64) == 0 ? i : this.values[6];
    }

    int getRoundTripTime(int i) {
        return (this.set & CLIENT_CERTIFICATE_VECTOR_SIZE) == 0 ? i : this.values[ROUND_TRIP_TIME];
    }

    int getUploadBandwidth(int i) {
        return (this.set & 2) == 0 ? i : this.values[1];
    }

    boolean isFlowControlDisabled() {
        return (((this.set & 1024) == 0 ? 0 : this.values[10]) & 1) != 0;
    }

    boolean isPersisted(int i) {
        return ((1 << i) & this.persisted) != 0;
    }

    boolean isSet(int i) {
        return ((1 << i) & this.set) != 0;
    }

    void merge(Settings settings) {
        for (int i = 0; i < 10; i++) {
            if (settings.isSet(i)) {
                set(i, settings.flags(i), settings.get(i));
            }
        }
    }

    boolean persistValue(int i) {
        return ((1 << i) & this.persistValue) != 0;
    }

    Settings set(int i, int i2, int i3) {
        if (i >= this.values.length) {
            return this;
        }
        int i4 = 1 << i;
        this.set |= i4;
        if ((i2 & 1) == 0) {
            this.persistValue &= i4 ^ -1;
        } else {
            this.persistValue |= i4;
        }
        if ((i2 & 2) == 0) {
            this.persisted = (i4 ^ -1) & this.persisted;
        } else {
            this.persisted = i4 | this.persisted;
        }
        this.values[i] = i3;
        return this;
    }

    int size() {
        return Integer.bitCount(this.set);
    }
}
