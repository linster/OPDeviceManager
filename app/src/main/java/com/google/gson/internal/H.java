package com.google.gson.internal;

import java.io.Writer;

final class H extends Writer {
    private final Appendable fV;
    private final v fW;

    private H(Appendable appendable) {
        this.fW = new v();
        this.fV = appendable;
    }

    public void close() {
    }

    public void flush() {
    }

    public void write(int i) {
        this.fV.append((char) i);
    }

    public void write(char[] cArr, int i, int i2) {
        this.fW.chars = cArr;
        this.fV.append(this.fW, i, i + i2);
    }
}
