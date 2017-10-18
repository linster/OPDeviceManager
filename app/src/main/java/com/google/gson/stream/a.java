package com.google.gson.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class a implements Closeable, Flushable {
    private static final String[] gd = new String[128];
    private static final String[] ge = gd;
    private final Writer gf;
    private int[] gg = new int[32];
    private int gh = 0;
    private String gi;
    private String gj;
    private boolean gk;
    private boolean gl;
    private String gm;
    private boolean gn;

    static {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 > 31) {
                gd[34] = "\\\"";
                gd[92] = "\\\\";
                gd[9] = "\\t";
                gd[8] = "\\b";
                gd[10] = "\\n";
                gd[13] = "\\r";
                gd[12] = "\\f";
                ge[60] = "\\u003c";
                ge[62] = "\\u003e";
                ge[38] = "\\u0026";
                ge[61] = "\\u003d";
                i2 = ge;
                i = 39;
                i2[39] = "\\u0027";
            }
            gd[i2] = String.format("\\u%04x", new Object[]{Integer.valueOf(i2)});
            i2++;
        }
    }

    public a(Writer writer) {
        fE(6);
        this.gj = ":";
        this.gn = true;
        if (writer != null) {
            this.gf = writer;
            return;
        }
        throw new NullPointerException("out == null");
    }

    private a fC(int i, String str) {
        fL(true);
        fE(i);
        this.gf.write(str);
        return this;
    }

    private a fD(int i, int i2, String str) {
        int fF = fF();
        if (fF != i2 && fF != i) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.gm == null) {
            this.gh--;
            if (fF == i2) {
                fJ();
            }
            this.gf.write(str);
            return this;
        } else {
            throw new IllegalStateException("Dangling name: " + this.gm);
        }
    }

    private void fE(int i) {
        if (this.gh == this.gg.length) {
            Object obj = new int[(this.gh * 2)];
            System.arraycopy(this.gg, 0, obj, 0, this.gh);
            this.gg = obj;
        }
        int[] iArr = this.gg;
        int i2 = this.gh;
        this.gh = i2 + 1;
        iArr[i2] = i;
    }

    private int fF() {
        if (this.gh != 0) {
            return this.gg[this.gh - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private void fG(int i) {
        this.gg[this.gh - 1] = i;
    }

    private void fH() {
        if (this.gm != null) {
            fK();
            fI(this.gm);
            this.gm = null;
        }
    }

    private void fI(String str) {
        int i = 0;
        String[] strArr = !this.gl ? gd : ge;
        this.gf.write("\"");
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            String str2;
            if (charAt >= '') {
                if (charAt == ' ') {
                    str2 = "\\u2028";
                } else if (charAt == ' ') {
                    str2 = "\\u2029";
                }
                if (i < i2) {
                    this.gf.write(str, i, i2 - i);
                }
                this.gf.write(str2);
                i = i2 + 1;
            } else {
                str2 = strArr[charAt];
                if (str2 == null) {
                }
                if (i < i2) {
                    this.gf.write(str, i, i2 - i);
                }
                this.gf.write(str2);
                i = i2 + 1;
            }
        }
        if (i < length) {
            this.gf.write(str, i, length - i);
        }
        this.gf.write("\"");
    }

    private void fJ() {
        if (this.gi != null) {
            this.gf.write("\n");
            int i = this.gh;
            for (int i2 = 1; i2 < i; i2++) {
                this.gf.write(this.gi);
            }
        }
    }

    private void fK() {
        int fF = fF();
        if (fF == 5) {
            this.gf.write(44);
        } else if (fF != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        fJ();
        fG(4);
    }

    private void fL(boolean z) {
        switch (fF()) {
            case 1:
                fG(2);
                fJ();
                return;
            case 2:
                this.gf.append(',');
                fJ();
                return;
            case 4:
                this.gf.append(this.gj);
                fG(5);
                return;
            case 6:
                break;
            case 7:
                if (!this.gk) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
                break;
            default:
                throw new IllegalStateException("Nesting problem.");
        }
        if (this.gk || z) {
            fG(7);
            return;
        }
        throw new IllegalStateException("JSON must start with an array or an object.");
    }

    public void close() {
        this.gf.close();
        int i = this.gh;
        if (i <= 1) {
            if (i == 1) {
                if (this.gg[i - 1] == 7) {
                }
            }
            this.gh = 0;
            return;
        }
        throw new IOException("Incomplete document");
    }

    public a dA() {
        return fD(3, 5, "}");
    }

    public a dB(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (this.gm != null) {
            throw new IllegalStateException();
        } else if (this.gh != 0) {
            this.gm = str;
            return this;
        } else {
            throw new IllegalStateException("JsonWriter is closed.");
        }
    }

    public a dC(String str) {
        if (str == null) {
            return dD();
        }
        fH();
        fL(false);
        fI(str);
        return this;
    }

    public a dD() {
        if (this.gm != null) {
            if (this.gn) {
                fH();
            } else {
                this.gm = null;
                return this;
            }
        }
        fL(false);
        this.gf.write("null");
        return this;
    }

    public a dE(boolean z) {
        fH();
        fL(false);
        this.gf.write(!z ? "false" : "true");
        return this;
    }

    public a dF(long j) {
        fH();
        fL(false);
        this.gf.write(Long.toString(j));
        return this;
    }

    public a dG(Number number) {
        if (number == null) {
            return dD();
        }
        fH();
        String obj = number.toString();
        if (!this.gk) {
            if (obj.equals("-Infinity") || obj.equals("Infinity") || obj.equals("NaN")) {
                throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
            }
        }
        fL(false);
        this.gf.append(obj);
        return this;
    }

    public a dx() {
        fH();
        return fC(1, "[");
    }

    public a dy() {
        return fD(1, 2, "]");
    }

    public a dz() {
        fH();
        return fC(3, "{");
    }

    public final void fA(boolean z) {
        this.gn = z;
    }

    public final boolean fB() {
        return this.gn;
    }

    public void flush() {
        if (this.gh != 0) {
            this.gf.flush();
            return;
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    public final void fv(String str) {
        if (str.length() != 0) {
            this.gi = str;
            this.gj = ": ";
            return;
        }
        this.gi = null;
        this.gj = ":";
    }

    public final void fw(boolean z) {
        this.gk = z;
    }

    public boolean fx() {
        return this.gk;
    }

    public final void fy(boolean z) {
        this.gl = z;
    }

    public final boolean fz() {
        return this.gl;
    }
}
