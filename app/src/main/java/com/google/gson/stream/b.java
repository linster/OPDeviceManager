package com.google.gson.stream;

import com.google.gson.internal.t;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class b implements Closeable {
    private static final char[] go = ")]}'\n";
    private String[] gA;
    private int[] gB;
    private final Reader gp;
    private boolean gq = false;
    private final char[] gr = new char[1024];
    private int gs = 0;
    private int gt = 0;
    private int gu = 0;
    private long gv;
    private int gw;
    private String gx;
    private int[] gy = new int[32];
    private int gz = 0;
    private int limit = 0;
    private int pos = 0;

    static {
        t.fH = new c();
    }

    public b(Reader reader) {
        int[] iArr = this.gy;
        int i = this.gz;
        this.gz = i + 1;
        iArr[i] = 6;
        this.gA = new String[32];
        this.gB = new int[32];
        if (reader != null) {
            this.gp = reader;
            return;
        }
        throw new NullPointerException("in == null");
    }

    private int fO() {
        int i = this.gy[this.gz - 1];
        if (i == 1) {
            this.gy[this.gz - 1] = 2;
        } else if (i == 2) {
            switch (ga(true)) {
                case 44:
                    break;
                case 59:
                    gb();
                    break;
                case 93:
                    this.gu = 4;
                    return 4;
                default:
                    throw gg("Unterminated array");
            }
        } else if (i == 3 || i == 5) {
            this.gy[this.gz - 1] = 4;
            if (i == 5) {
                switch (ga(true)) {
                    case 44:
                        break;
                    case 59:
                        gb();
                        break;
                    case 125:
                        this.gu = 2;
                        return 2;
                    default:
                        throw gg("Unterminated object");
                }
            }
            int ga = ga(true);
            switch (ga) {
                case 34:
                    this.gu = 13;
                    return 13;
                case 39:
                    gb();
                    this.gu = 12;
                    return 12;
                case 125:
                    if (i == 5) {
                        throw gg("Expected name");
                    }
                    this.gu = 2;
                    return 2;
                default:
                    gb();
                    this.pos--;
                    if (fR((char) ga)) {
                        this.gu = 14;
                        return 14;
                    }
                    throw gg("Expected name");
            }
        } else if (i == 4) {
            this.gy[this.gz - 1] = 5;
            switch (ga(true)) {
                case 58:
                    break;
                case 61:
                    gb();
                    if (this.pos < this.limit || fX(1)) {
                        if (this.gr[this.pos] == '>') {
                            this.pos++;
                            break;
                        }
                    }
                    break;
                    break;
                default:
                    throw gg("Expected ':'");
            }
        } else if (i == 6) {
            if (this.gq) {
                gh();
            }
            this.gy[this.gz - 1] = 7;
        } else if (i != 7) {
            if (i == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        } else if (ga(false) != -1) {
            gb();
            this.pos--;
        } else {
            this.gu = 17;
            return 17;
        }
        switch (ga(true)) {
            case 34:
                if (this.gz == 1) {
                    gb();
                }
                this.gu = 9;
                return 9;
            case 39:
                gb();
                this.gu = 8;
                return 8;
            case 44:
            case 59:
                break;
            case 91:
                this.gu = 3;
                return 3;
            case 93:
                if (i == 1) {
                    this.gu = 4;
                    return 4;
                }
                break;
            case 123:
                this.gu = 1;
                return 1;
            default:
                this.pos--;
                if (this.gz == 1) {
                    gb();
                }
                i = fP();
                if (i != 0) {
                    return i;
                }
                i = fQ();
                if (i != 0) {
                    return i;
                }
                if (fR(this.gr[this.pos])) {
                    gb();
                    this.gu = 10;
                    return 10;
                }
                throw gg("Expected value");
        }
        if (i == 1 || i == 2) {
            gb();
            this.pos--;
            this.gu = 7;
            return 7;
        }
        throw gg("Unexpected value");
    }

    private int fP() {
        String str;
        int i;
        char c = this.gr[this.pos];
        String str2;
        if (c == 't' || c == 'T') {
            str = "true";
            str2 = "TRUE";
            i = 5;
        } else if (c == 'f' || c == 'F') {
            str = "false";
            str2 = "FALSE";
            i = 6;
        } else if (c != 'n' && c != 'N') {
            return 0;
        } else {
            str = "null";
            str2 = "NULL";
            i = 7;
        }
        int length = str.length();
        int i2 = 1;
        while (i2 < length) {
            if (this.pos + i2 >= this.limit && !fX(i2 + 1)) {
                return 0;
            }
            char c2 = this.gr[this.pos + i2];
            if (c2 != str.charAt(i2) && c2 != r1.charAt(i2)) {
                return 0;
            }
            i2++;
        }
        if (this.pos + length < this.limit || fX(length + 1)) {
            if (fR(this.gr[this.pos + length])) {
                return 0;
            }
        }
        this.pos += length;
        this.gu = i;
        return i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int fQ() {
        /*
        r15 = this;
        r11 = r15.gr;
        r2 = r15.pos;
        r1 = r15.limit;
        r6 = 0;
        r5 = 0;
        r4 = 1;
        r3 = 0;
        r0 = 0;
        r10 = r0;
        r0 = r1;
        r1 = r2;
    L_0x000f:
        r2 = r1 + r10;
        if (r2 == r0) goto L_0x0026;
    L_0x0013:
        r2 = r1 + r10;
        r2 = r11[r2];
        switch(r2) {
            case 43: goto L_0x004f;
            case 45: goto L_0x0038;
            case 46: goto L_0x0064;
            case 69: goto L_0x0058;
            case 101: goto L_0x0058;
            default: goto L_0x001a;
        };
    L_0x001a:
        r8 = 48;
        if (r2 >= r8) goto L_0x006d;
    L_0x001e:
        r0 = r15.fR(r2);
        if (r0 == 0) goto L_0x00d1;
    L_0x0024:
        r0 = 0;
        return r0;
    L_0x0026:
        r0 = r11.length;
        if (r10 == r0) goto L_0x0036;
    L_0x0029:
        r0 = r10 + 1;
        r0 = r15.fX(r0);
        if (r0 == 0) goto L_0x00d1;
    L_0x0031:
        r1 = r15.pos;
        r0 = r15.limit;
        goto L_0x0013;
    L_0x0036:
        r0 = 0;
        return r0;
    L_0x0038:
        if (r3 == 0) goto L_0x003f;
    L_0x003a:
        r2 = 5;
        if (r3 == r2) goto L_0x004b;
    L_0x003d:
        r0 = 0;
        return r0;
    L_0x003f:
        r3 = 1;
        r2 = 1;
        r14 = r4;
        r4 = r3;
        r3 = r14;
    L_0x0044:
        r5 = r10 + 1;
        r10 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        goto L_0x000f;
    L_0x004b:
        r2 = 6;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x004f:
        r2 = 5;
        if (r3 == r2) goto L_0x0054;
    L_0x0052:
        r0 = 0;
        return r0;
    L_0x0054:
        r2 = 6;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x0058:
        r2 = 2;
        if (r3 != r2) goto L_0x005f;
    L_0x005b:
        r2 = 5;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x005f:
        r2 = 4;
        if (r3 == r2) goto L_0x005b;
    L_0x0062:
        r0 = 0;
        return r0;
    L_0x0064:
        r2 = 2;
        if (r3 == r2) goto L_0x0069;
    L_0x0067:
        r0 = 0;
        return r0;
    L_0x0069:
        r2 = 3;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x006d:
        r8 = 57;
        if (r2 > r8) goto L_0x001e;
    L_0x0071:
        r8 = 1;
        if (r3 != r8) goto L_0x007c;
    L_0x0074:
        r2 = r2 + -48;
        r2 = -r2;
        r6 = (long) r2;
        r2 = 2;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x007c:
        if (r3 == 0) goto L_0x0074;
    L_0x007e:
        r8 = 2;
        if (r3 == r8) goto L_0x008b;
    L_0x0081:
        r2 = 3;
        if (r3 == r2) goto L_0x00c4;
    L_0x0084:
        r2 = 5;
        if (r3 != r2) goto L_0x00c9;
    L_0x0087:
        r2 = 7;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x008b:
        r8 = 0;
        r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r8 != 0) goto L_0x0093;
    L_0x0091:
        r0 = 0;
        return r0;
    L_0x0093:
        r8 = 10;
        r8 = r8 * r6;
        r2 = r2 + -48;
        r12 = (long) r2;
        r8 = r8 - r12;
        r12 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r2 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r2 <= 0) goto L_0x00be;
    L_0x00a3:
        r2 = 1;
    L_0x00a4:
        if (r2 != 0) goto L_0x00b6;
    L_0x00a6:
        r12 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r2 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r2 != 0) goto L_0x00c2;
    L_0x00af:
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 < 0) goto L_0x00c0;
    L_0x00b3:
        r2 = 1;
    L_0x00b4:
        if (r2 != 0) goto L_0x00c2;
    L_0x00b6:
        r2 = 1;
    L_0x00b7:
        r2 = r2 & r4;
        r4 = r5;
        r6 = r8;
        r14 = r3;
        r3 = r2;
        r2 = r14;
        goto L_0x0044;
    L_0x00be:
        r2 = 0;
        goto L_0x00a4;
    L_0x00c0:
        r2 = 0;
        goto L_0x00b4;
    L_0x00c2:
        r2 = 0;
        goto L_0x00b7;
    L_0x00c4:
        r2 = 4;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x00c9:
        r2 = 6;
        if (r3 == r2) goto L_0x0087;
    L_0x00cc:
        r2 = r3;
        r3 = r4;
        r4 = r5;
        goto L_0x0044;
    L_0x00d1:
        r0 = 2;
        if (r3 == r0) goto L_0x00e0;
    L_0x00d4:
        r0 = 2;
        if (r3 != r0) goto L_0x00fb;
    L_0x00d7:
        r15.gw = r10;
        r0 = 16;
        r15.gu = r0;
        r0 = 16;
        return r0;
    L_0x00e0:
        if (r4 == 0) goto L_0x00d4;
    L_0x00e2:
        r0 = -9223372036854775808;
        r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1));
        if (r0 != 0) goto L_0x00ea;
    L_0x00e8:
        if (r5 == 0) goto L_0x00d4;
    L_0x00ea:
        if (r5 != 0) goto L_0x00ed;
    L_0x00ec:
        r6 = -r6;
    L_0x00ed:
        r15.gv = r6;
        r0 = r15.pos;
        r0 = r0 + r10;
        r15.pos = r0;
        r0 = 15;
        r15.gu = r0;
        r0 = 15;
        return r0;
    L_0x00fb:
        r0 = 4;
        if (r3 == r0) goto L_0x00d7;
    L_0x00fe:
        r0 = 7;
        if (r3 == r0) goto L_0x00d7;
    L_0x0101:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.b.fQ():int");
    }

    private boolean fR(char c) {
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                break;
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                gb();
                break;
            default:
                return true;
        }
        return false;
    }

    private String fS(char c) {
        char[] cArr = this.gr;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int i = this.pos;
            int i2 = this.limit;
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                char c2 = cArr[i3];
                if (c2 != c) {
                    if (c2 == '\\') {
                        this.pos = i4;
                        stringBuilder.append(cArr, i, (i4 - i) - 1);
                        stringBuilder.append(gf());
                        i = this.pos;
                        i2 = this.limit;
                        i4 = i;
                    } else if (c2 == '\n') {
                        this.gs++;
                        this.gt = i4;
                    }
                    i3 = i4;
                } else {
                    this.pos = i4;
                    stringBuilder.append(cArr, i, (i4 - i) - 1);
                    return stringBuilder.toString();
                }
            }
            stringBuilder.append(cArr, i, i3 - i);
            this.pos = i3;
        } while (fX(1));
        throw gg("Unterminated string");
    }

    private String fT() {
        int i = 0;
        StringBuilder stringBuilder = null;
        int i2 = 0;
        while (true) {
            String stringBuilder2;
            if (this.pos + i2 < this.limit) {
                switch (this.gr[this.pos + i2]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        gb();
                        break;
                    default:
                        i2++;
                        continue;
                }
                i = i2;
            } else if (i2 >= this.gr.length) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.append(this.gr, this.pos, i2);
                this.pos = i2 + this.pos;
                if (fX(1)) {
                    i2 = 0;
                }
            } else if (!fX(i2 + 1)) {
                i = i2;
            }
            if (stringBuilder != null) {
                stringBuilder.append(this.gr, this.pos, i);
                stringBuilder2 = stringBuilder.toString();
            } else {
                stringBuilder2 = new String(this.gr, this.pos, i);
            }
            this.pos = i + this.pos;
            return stringBuilder2;
        }
    }

    private void fU(char c) {
        char[] cArr = this.gr;
        do {
            int i = this.pos;
            int i2 = this.limit;
            int i3 = i;
            while (i3 < i2) {
                i = i3 + 1;
                char c2 = cArr[i3];
                if (c2 != c) {
                    if (c2 == '\\') {
                        this.pos = i;
                        gf();
                        i = this.pos;
                        i2 = this.limit;
                    } else if (c2 == '\n') {
                        this.gs++;
                        this.gt = i;
                    }
                    i3 = i;
                } else {
                    this.pos = i;
                    return;
                }
            }
            this.pos = i3;
        } while (fX(1));
        throw gg("Unterminated string");
    }

    private void fV() {
        do {
            int i = 0;
            while (this.pos + i < this.limit) {
                switch (this.gr[this.pos + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        gb();
                        break;
                    default:
                        i++;
                }
                this.pos = i + this.pos;
                return;
            }
            this.pos = i + this.pos;
        } while (fX(1));
    }

    private void fW(int i) {
        if (this.gz == this.gy.length) {
            Object obj = new int[(this.gz * 2)];
            Object obj2 = new int[(this.gz * 2)];
            Object obj3 = new String[(this.gz * 2)];
            System.arraycopy(this.gy, 0, obj, 0, this.gz);
            System.arraycopy(this.gB, 0, obj2, 0, this.gz);
            System.arraycopy(this.gA, 0, obj3, 0, this.gz);
            this.gy = obj;
            this.gB = obj2;
            this.gA = obj3;
        }
        int[] iArr = this.gy;
        int i2 = this.gz;
        this.gz = i2 + 1;
        iArr[i2] = i;
    }

    private boolean fX(int i) {
        Object obj = this.gr;
        this.gt -= this.pos;
        if (this.limit == this.pos) {
            this.limit = 0;
        } else {
            this.limit -= this.pos;
            System.arraycopy(obj, this.pos, obj, 0, this.limit);
        }
        this.pos = 0;
        do {
            int read = this.gp.read(obj, this.limit, obj.length - this.limit);
            if (read == -1) {
                return false;
            }
            this.limit = read + this.limit;
            if (this.gs == 0 && this.gt == 0 && this.limit > 0 && obj[0] == '﻿') {
                this.pos++;
                this.gt++;
                i++;
            }
        } while (this.limit < i);
        return true;
    }

    private int fY() {
        return this.gs + 1;
    }

    private int fZ() {
        return (this.pos - this.gt) + 1;
    }

    private int ga(boolean z) {
        char[] cArr = this.gr;
        int i = this.pos;
        int i2 = this.limit;
        while (true) {
            if (i == i2) {
                this.pos = i;
                if (fX(1)) {
                    i = this.pos;
                    i2 = this.limit;
                } else if (!z) {
                    return -1;
                } else {
                    throw new EOFException("End of input at line " + fY() + " column " + fZ());
                }
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c == '\n') {
                this.gs++;
                this.gt = i3;
                i = i3;
            } else if (c == ' ' || c == '\r') {
                i = i3;
            } else if (c == '\t') {
                i = i3;
            } else if (c == '/') {
                this.pos = i3;
                if (i3 == i2) {
                    this.pos--;
                    boolean fX = fX(2);
                    this.pos++;
                    if (!fX) {
                        return c;
                    }
                }
                gb();
                switch (cArr[this.pos]) {
                    case '*':
                        this.pos++;
                        if (gd("*/")) {
                            i = this.pos + 2;
                            i2 = this.limit;
                            break;
                        }
                        throw gg("Unterminated comment");
                    case '/':
                        this.pos++;
                        gc();
                        i = this.pos;
                        i2 = this.limit;
                        break;
                    default:
                        return c;
                }
            } else if (c != '#') {
                this.pos = i3;
                return c;
            } else {
                this.pos = i3;
                gb();
                gc();
                i = this.pos;
                i2 = this.limit;
            }
        }
    }

    private void gb() {
        if (!this.gq) {
            throw gg("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void gc() {
        char c;
        do {
            if (this.pos < this.limit || fX(1)) {
                char[] cArr = this.gr;
                int i = this.pos;
                this.pos = i + 1;
                c = cArr[i];
                if (c == '\n') {
                    this.gs++;
                    this.gt = this.pos;
                    return;
                }
            } else {
                return;
            }
        } while (c != '\r');
    }

    private boolean gd(String str) {
        while (true) {
            if (this.pos + str.length() > this.limit && !fX(str.length())) {
                return false;
            }
            if (this.gr[this.pos] != '\n') {
                int i = 0;
                while (i < str.length()) {
                    if (this.gr[this.pos + i] == str.charAt(i)) {
                        i++;
                    }
                }
                return true;
            }
            this.gs++;
            this.gt = this.pos + 1;
            this.pos++;
        }
    }

    private char gf() {
        if (this.pos == this.limit && !fX(1)) {
            throw gg("Unterminated escape sequence");
        }
        char[] cArr = this.gr;
        int i = this.pos;
        this.pos = i + 1;
        char c = cArr[i];
        switch (c) {
            case '\n':
                this.gs++;
                this.gt = this.pos;
                break;
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            case 'u':
                if (this.pos + 4 > this.limit && !fX(4)) {
                    throw gg("Unterminated escape sequence");
                }
                int i2 = this.pos;
                i = i2 + 4;
                int i3 = i2;
                c = '\u0000';
                for (int i4 = i3; i4 < i; i4++) {
                    char c2 = this.gr[i4];
                    c = (char) (c << 4);
                    if (c2 >= '0' && c2 <= '9') {
                        c = (char) (c + (c2 - 48));
                    } else if (c2 >= 'a' && c2 <= 'f') {
                        c = (char) (c + ((c2 - 97) + 10));
                    } else if (c2 >= 'A' && c2 <= 'F') {
                        c = (char) (c + ((c2 - 65) + 10));
                    } else {
                        throw new NumberFormatException("\\u" + new String(this.gr, this.pos, 4));
                    }
                }
                this.pos += 4;
                return c;
        }
        return c;
    }

    private IOException gg(String str) {
        throw new MalformedJsonException(str + " at line " + fY() + " column " + fZ() + " path " + ge());
    }

    private void gh() {
        int i = 0;
        ga(true);
        this.pos--;
        if (this.pos + go.length <= this.limit || fX(go.length)) {
            while (i < go.length) {
                if (this.gr[this.pos + i] == go[i]) {
                    i++;
                } else {
                    return;
                }
            }
            this.pos += go.length;
        }
    }

    public void close() {
        this.gu = 0;
        this.gy[0] = 8;
        this.gz = 1;
        this.gp.close();
    }

    public void db() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i != 3) {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        }
        fW(1);
        this.gB[this.gz - 1] = 0;
        this.gu = 0;
    }

    public void dc() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i != 4) {
            throw new IllegalStateException("Expected END_ARRAY but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        }
        this.gz--;
        int[] iArr = this.gB;
        int i2 = this.gz - 1;
        iArr[i2] = iArr[i2] + 1;
        this.gu = 0;
    }

    public void dd() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i != 1) {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        }
        fW(3);
        this.gu = 0;
    }

    public void de() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i != 2) {
            throw new IllegalStateException("Expected END_OBJECT but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        }
        this.gz--;
        this.gA[this.gz] = null;
        int[] iArr = this.gB;
        int i2 = this.gz - 1;
        iArr[i2] = iArr[i2] + 1;
        this.gu = 0;
    }

    public JsonToken df() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        switch (i) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonToken.STRING;
            case 12:
            case 13:
            case 14:
                return JsonToken.NAME;
            case 15:
            case 16:
                return JsonToken.NUMBER;
            case 17:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public String dj() {
        String fT;
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i == 14) {
            fT = fT();
        } else if (i == 12) {
            fT = fS('\'');
        } else if (i != 13) {
            throw new IllegalStateException("Expected a name but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        } else {
            fT = fS('\"');
        }
        this.gu = 0;
        this.gA[this.gz - 1] = fT;
        return fT;
    }

    public String dk() {
        String fT;
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i == 10) {
            fT = fT();
        } else if (i == 8) {
            fT = fS('\'');
        } else if (i == 9) {
            fT = fS('\"');
        } else if (i == 11) {
            fT = this.gx;
            this.gx = null;
        } else if (i == 15) {
            fT = Long.toString(this.gv);
        } else if (i != 16) {
            throw new IllegalStateException("Expected a string but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        } else {
            fT = new String(this.gr, this.pos, this.gw);
            this.pos += this.gw;
        }
        this.gu = 0;
        int[] iArr = this.gB;
        int i2 = this.gz - 1;
        iArr[i2] = iArr[i2] + 1;
        return fT;
    }

    public boolean dl() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        int[] iArr;
        int i2;
        if (i == 5) {
            this.gu = 0;
            iArr = this.gB;
            i2 = this.gz - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        } else if (i != 6) {
            throw new IllegalStateException("Expected a boolean but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        } else {
            this.gu = 0;
            iArr = this.gB;
            i2 = this.gz - 1;
            iArr[i2] = iArr[i2] + 1;
            return false;
        }
    }

    public void dm() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i != 7) {
            throw new IllegalStateException("Expected null but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        }
        this.gu = 0;
        int[] iArr = this.gB;
        int i2 = this.gz - 1;
        iArr[i2] = iArr[i2] + 1;
    }

    public double dn() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        if (i != 15) {
            if (i == 16) {
                this.gx = new String(this.gr, this.pos, this.gw);
                this.pos += this.gw;
            } else if (i == 8 || i == 9) {
                this.gx = fS(i != 8 ? '\"' : '\'');
            } else if (i == 10) {
                this.gx = fT();
            } else if (i != 11) {
                throw new IllegalStateException("Expected a double but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
            }
            this.gu = 11;
            double parseDouble = Double.parseDouble(this.gx);
            if (!this.gq) {
                if (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble)) {
                    throw new MalformedJsonException("JSON forbids NaN and infinities: " + parseDouble + " at line " + fY() + " column " + fZ() + " path " + ge());
                }
            }
            this.gx = null;
            this.gu = 0;
            int[] iArr = this.gB;
            int i2 = this.gz - 1;
            iArr[i2] = iArr[i2] + 1;
            return parseDouble;
        }
        this.gu = 0;
        int[] iArr2 = this.gB;
        int i3 = this.gz - 1;
        iArr2[i3] = iArr2[i3] + 1;
        return (double) this.gv;
    }

    public long do() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        int[] iArr;
        int i2;
        if (i == 15) {
            this.gu = 0;
            iArr = this.gB;
            i2 = this.gz - 1;
            iArr[i2] = iArr[i2] + 1;
            return this.gv;
        } else if (i == 16) {
            this.gx = new String(this.gr, this.pos, this.gw);
            this.pos += this.gw;
            this.gu = 11;
            double parseDouble = Double.parseDouble(this.gx);
            long j = (long) parseDouble;
            if (((double) j) != parseDouble) {
                throw new NumberFormatException("Expected a long but was " + this.gx + " at line " + fY() + " column " + fZ() + " path " + ge());
            }
            this.gx = null;
            this.gu = 0;
            iArr = this.gB;
            i2 = this.gz - 1;
            iArr[i2] = iArr[i2] + 1;
            return j;
        } else if (i == 8 || i == 9) {
            this.gx = fS(i != 8 ? '\"' : '\'');
            try {
                long parseLong = Long.parseLong(this.gx);
                this.gu = 0;
                int[] iArr2 = this.gB;
                int i3 = this.gz - 1;
                iArr2[i3] = iArr2[i3] + 1;
                return parseLong;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected a long but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        }
    }

    public int dp() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        int[] iArr;
        int i2;
        if (i == 15) {
            i = (int) this.gv;
            if (this.gv != ((long) i)) {
                throw new NumberFormatException("Expected an int but was " + this.gv + " at line " + fY() + " column " + fZ() + " path " + ge());
            }
            this.gu = 0;
            iArr = this.gB;
            i2 = this.gz - 1;
            iArr[i2] = iArr[i2] + 1;
            return i;
        } else if (i == 16) {
            this.gx = new String(this.gr, this.pos, this.gw);
            this.pos += this.gw;
            this.gu = 11;
            double parseDouble = Double.parseDouble(this.gx);
            i2 = (int) parseDouble;
            if (((double) i2) != parseDouble) {
                throw new NumberFormatException("Expected an int but was " + this.gx + " at line " + fY() + " column " + fZ() + " path " + ge());
            }
            this.gx = null;
            this.gu = 0;
            int[] iArr2 = this.gB;
            int i3 = this.gz - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return i2;
        } else if (i == 8 || i == 9) {
            this.gx = fS(i != 8 ? '\"' : '\'');
            try {
                i = Integer.parseInt(this.gx);
                this.gu = 0;
                iArr = this.gB;
                i2 = this.gz - 1;
                iArr[i2] = iArr[i2] + 1;
                return i;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected an int but was " + df() + " at line " + fY() + " column " + fZ() + " path " + ge());
        }
    }

    public void dq() {
        int i = 0;
        do {
            int i2 = this.gu;
            if (i2 == 0) {
                i2 = fO();
            }
            if (i2 == 3) {
                fW(1);
                i++;
            } else if (i2 == 1) {
                fW(3);
                i++;
            } else if (i2 == 4) {
                this.gz--;
                i--;
            } else if (i2 == 2) {
                this.gz--;
                i--;
            } else if (i2 == 14 || i2 == 10) {
                fV();
            } else if (i2 == 8 || i2 == 12) {
                fU('\'');
            } else if (i2 == 9 || i2 == 13) {
                fU('\"');
            } else if (i2 == 16) {
                this.pos += this.gw;
            }
            this.gu = 0;
        } while (i != 0);
        int[] iArr = this.gB;
        int i3 = this.gz - 1;
        iArr[i3] = iArr[i3] + 1;
        this.gA[this.gz - 1] = "null";
    }

    public final void fM(boolean z) {
        this.gq = z;
    }

    public final boolean fN() {
        return this.gq;
    }

    public String ge() {
        StringBuilder append = new StringBuilder().append('$');
        int i = this.gz;
        for (int i2 = 0; i2 < i; i2++) {
            switch (this.gy[i2]) {
                case 1:
                case 2:
                    append.append('[').append(this.gB[i2]).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    append.append('.');
                    if (this.gA[i2] == null) {
                        break;
                    }
                    append.append(this.gA[i2]);
                    break;
                default:
                    break;
            }
        }
        return append.toString();
    }

    public boolean hasNext() {
        int i = this.gu;
        if (i == 0) {
            i = fO();
        }
        return (i == 2 || i == 4) ? false : true;
    }

    public String toString() {
        return getClass().getSimpleName() + " at line " + fY() + " column " + fZ();
    }
}
