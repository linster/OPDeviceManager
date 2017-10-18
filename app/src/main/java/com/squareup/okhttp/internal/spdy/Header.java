package com.squareup.okhttp.internal.spdy;

import okio.ByteString;

public final class Header {
    public static final ByteString RESPONSE_STATUS = ByteString.As(":status");
    public static final ByteString TARGET_AUTHORITY = ByteString.As(":authority");
    public static final ByteString TARGET_HOST = ByteString.As(":host");
    public static final ByteString TARGET_METHOD = ByteString.As(":method");
    public static final ByteString TARGET_PATH = ByteString.As(":path");
    public static final ByteString TARGET_SCHEME = ByteString.As(":scheme");
    public static final ByteString VERSION = ByteString.As(":version");
    final int hpackSize;
    public final ByteString name;
    public final ByteString value;

    public Header(String str, String str2) {
        this(ByteString.As(str), ByteString.As(str2));
    }

    public Header(ByteString byteString, String str) {
        this(byteString, ByteString.As(str));
    }

    public Header(ByteString byteString, ByteString byteString2) {
        this.name = byteString;
        this.value = byteString2;
        this.hpackSize = (byteString.size() + 32) + byteString2.size();
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Header)) {
            return false;
        }
        Header header = (Header) obj;
        if (this.name.equals(header.name) && this.value.equals(header.value)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((this.name.hashCode() + 527) * 31) + this.value.hashCode();
    }

    public String toString() {
        return String.format("%s: %s", new Object[]{this.name.At(), this.value.At()});
    }
}
