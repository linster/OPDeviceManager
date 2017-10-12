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
    private final int[] values;

    public Settings() {
        this.values = new int[FLOW_CONTROL_OPTIONS];
    }

    void clear() {
        this.persisted = 0;
        this.persistValue = 0;
        this.set = 0;
        Arrays.fill(this.values, 0);
    }

    Settings set(int id, int idFlags, int value) {
        if (id >= this.values.length) {
            return this;
        }
        int bit = UPLOAD_BANDWIDTH << id;
        this.set |= bit;
        if ((idFlags & UPLOAD_BANDWIDTH) == 0) {
            this.persistValue &= bit ^ -1;
        } else {
            this.persistValue |= bit;
        }
        if ((idFlags & PERSISTED) == 0) {
            this.persisted &= bit ^ -1;
        } else {
            this.persisted |= bit;
        }
        this.values[id] = value;
        return this;
    }

    boolean isSet(int id) {
        if ((this.set & (UPLOAD_BANDWIDTH << id)) == 0) {
            return false;
        }
        return true;
    }

    int get(int id) {
        return this.values[id];
    }

    int flags(int id) {
        int result = 0;
        if (isPersisted(id)) {
            result = PERSISTED;
        }
        if (persistValue(id)) {
            return result | UPLOAD_BANDWIDTH;
        }
        return result;
    }

    int size() {
        return Integer.bitCount(this.set);
    }

    int getUploadBandwidth(int defaultValue) {
        return (this.set & PERSISTED) == 0 ? defaultValue : this.values[UPLOAD_BANDWIDTH];
    }

    int getHeaderTableSize() {
        return (this.set & PERSISTED) == 0 ? -1 : this.values[UPLOAD_BANDWIDTH];
    }

    int getDownloadBandwidth(int defaultValue) {
        return (this.set & MAX_CONCURRENT_STREAMS) == 0 ? defaultValue : this.values[PERSISTED];
    }

    boolean getEnablePush(boolean defaultValue) {
        boolean z = (this.set & MAX_CONCURRENT_STREAMS) == 0 ? !defaultValue ? false : UPLOAD_BANDWIDTH : this.values[PERSISTED];
        return z == UPLOAD_BANDWIDTH;
    }

    int getRoundTripTime(int defaultValue) {
        return (this.set & CLIENT_CERTIFICATE_VECTOR_SIZE) == 0 ? defaultValue : this.values[ROUND_TRIP_TIME];
    }

    int getMaxConcurrentStreams(int defaultValue) {
        return (this.set & 16) == 0 ? defaultValue : this.values[MAX_CONCURRENT_STREAMS];
    }

    int getCurrentCwnd(int defaultValue) {
        return (this.set & 32) == 0 ? defaultValue : this.values[MAX_FRAME_SIZE];
    }

    int getMaxFrameSize(int defaultValue) {
        return (this.set & 32) == 0 ? defaultValue : this.values[MAX_FRAME_SIZE];
    }

    int getDownloadRetransRate(int defaultValue) {
        return (this.set & 64) == 0 ? defaultValue : this.values[MAX_HEADER_LIST_SIZE];
    }

    int getMaxHeaderListSize(int defaultValue) {
        return (this.set & 64) == 0 ? defaultValue : this.values[MAX_HEADER_LIST_SIZE];
    }

    int getInitialWindowSize(int defaultValue) {
        return (this.set & 128) == 0 ? defaultValue : this.values[INITIAL_WINDOW_SIZE];
    }

    int getClientCertificateVectorSize(int defaultValue) {
        return (this.set & 256) == 0 ? defaultValue : this.values[CLIENT_CERTIFICATE_VECTOR_SIZE];
    }

    boolean isFlowControlDisabled() {
        return (((this.set & 1024) == 0 ? 0 : this.values[FLOW_CONTROL_OPTIONS]) & UPLOAD_BANDWIDTH) != 0;
    }

    boolean persistValue(int id) {
        if ((this.persistValue & (UPLOAD_BANDWIDTH << id)) == 0) {
            return false;
        }
        return true;
    }

    boolean isPersisted(int id) {
        if ((this.persisted & (UPLOAD_BANDWIDTH << id)) == 0) {
            return false;
        }
        return true;
    }

    void merge(Settings other) {
        for (int i = 0; i < FLOW_CONTROL_OPTIONS; i += UPLOAD_BANDWIDTH) {
            if (other.isSet(i)) {
                set(i, other.flags(i), other.get(i));
            }
        }
    }
}
