package com.squareup.okhttp.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length = this.dn.length();
    private int pos;

    public DistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
    }

    private String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        while (this.pos < this.length) {
            char[] cArr;
            int i;
            switch (this.chars[this.pos]) {
                case ' ':
                    this.cur = this.end;
                    this.pos++;
                    cArr = this.chars;
                    i = this.end;
                    this.end = i + 1;
                    cArr[i] = ' ';
                    while (this.pos < this.length && this.chars[this.pos] == ' ') {
                        cArr = this.chars;
                        i = this.end;
                        this.end = i + 1;
                        cArr[i] = ' ';
                        this.pos++;
                    }
                    if (this.pos != this.length && this.chars[this.pos] != ',' && this.chars[this.pos] != '+' && this.chars[this.pos] != ';') {
                        break;
                    }
                    return new String(this.chars, this.beg, this.cur - this.beg);
                    break;
                case '+':
                case ',':
                case ';':
                    return new String(this.chars, this.beg, this.end - this.beg);
                case '\\':
                    cArr = this.chars;
                    i = this.end;
                    this.end = i + 1;
                    cArr[i] = (char) getEscaped();
                    this.pos++;
                    break;
                default:
                    cArr = this.chars;
                    i = this.end;
                    this.end = i + 1;
                    cArr[i] = (char) this.chars[this.pos];
                    this.pos++;
                    break;
            }
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private int getByte(int i) {
        if (i + 1 < this.length) {
            int i2;
            int i3;
            char c = this.chars[i];
            if (c >= '0' && c <= '9') {
                i2 = c - 48;
            } else if (c >= 'a' && c <= 'f') {
                i2 = c - 87;
            } else if (c >= 'A' && c <= 'F') {
                i2 = c - 55;
            } else {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
            char c2 = this.chars[i + 1];
            if (c2 >= '0' && c2 <= '9') {
                i3 = c2 - 48;
            } else if (c2 >= 'a' && c2 <= 'f') {
                i3 = c2 - 87;
            } else if (c2 >= 'A' && c2 <= 'F') {
                i3 = c2 - 55;
            } else {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
            return (i2 << 4) + i3;
        }
        throw new IllegalStateException("Malformed DN: " + this.dn);
    }

    private char getEscaped() {
        this.pos++;
        if (this.pos != this.length) {
            switch (this.chars[this.pos]) {
                case ' ':
                case '\"':
                case '#':
                case '%':
                case '*':
                case '+':
                case ',':
                case ';':
                case '<':
                case '=':
                case '>':
                case '\\':
                case '_':
                    return this.chars[this.pos];
                default:
                    return getUTF8();
            }
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private char getUTF8() {
        int i = getByte(this.pos);
        this.pos++;
        if (i < 128) {
            return (char) i;
        }
        if (i < 192 || i > 247) {
            return '?';
        }
        int i2;
        if (i <= 223) {
            i2 = 1;
            i &= 31;
        } else if (i > 239) {
            i2 = 3;
            i &= 7;
        } else {
            i2 = 2;
            i &= 15;
        }
        int i3 = i;
        for (i = 0; i < i2; i++) {
            this.pos++;
            if (this.pos == this.length || this.chars[this.pos] != '\\') {
                return '?';
            }
            this.pos++;
            int i4 = getByte(this.pos);
            this.pos++;
            if ((i4 & 192) != 128) {
                return '?';
            }
            i3 = (i3 << 6) + (i4 & 63);
        }
        return (char) i3;
    }

    private String hexAV() {
        if (this.pos + 4 < this.length) {
            int i;
            byte[] bArr;
            int i2;
            int i3;
            this.beg = this.pos;
            this.pos++;
            while (this.pos != this.length && this.chars[this.pos] != '+' && this.chars[this.pos] != ',' && this.chars[this.pos] != ';') {
                if (this.chars[this.pos] != ' ') {
                    if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
                        char[] cArr = this.chars;
                        i = this.pos;
                        cArr[i] = (char) ((char) (cArr[i] + 32));
                    }
                    this.pos++;
                } else {
                    this.end = this.pos;
                    this.pos++;
                    while (this.pos < this.length && this.chars[this.pos] == ' ') {
                        this.pos++;
                    }
                    i = this.end - this.beg;
                    if (i >= 5 && (i & 1) != 0) {
                        bArr = new byte[(i / 2)];
                        i2 = this.beg + 1;
                        for (i3 = 0; i3 < bArr.length; i3++) {
                            bArr[i3] = (byte) ((byte) getByte(i2));
                            i2 += 2;
                        }
                        return new String(this.chars, this.beg, i);
                    }
                    throw new IllegalStateException("Unexpected end of DN: " + this.dn);
                }
            }
            this.end = this.pos;
            i = this.end - this.beg;
            if (i >= 5) {
                bArr = new byte[(i / 2)];
                i2 = this.beg + 1;
                for (i3 = 0; i3 < bArr.length; i3++) {
                    bArr[i3] = (byte) ((byte) getByte(i2));
                    i2 += 2;
                }
                return new String(this.chars, this.beg, i);
            }
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos;
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
            this.pos++;
        }
        if (this.pos < this.length) {
            this.end = this.pos;
            if (this.chars[this.pos] == ' ') {
                while (this.pos < this.length && this.chars[this.pos] != '=') {
                    if (this.chars[this.pos] != ' ') {
                        break;
                    }
                    this.pos++;
                }
                if (this.chars[this.pos] != '=' || this.pos == this.length) {
                    throw new IllegalStateException("Unexpected end of DN: " + this.dn);
                }
            }
            this.pos++;
            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                this.pos++;
            }
            if (this.end - this.beg > 4 && this.chars[this.beg + 3] == '.') {
                if (this.chars[this.beg] == 'O' || this.chars[this.beg] == 'o') {
                    if (this.chars[this.beg + 1] == 'I' || this.chars[this.beg + 1] == 'i') {
                        if (this.chars[this.beg + 2] == 'D' || this.chars[this.beg + 2] == 'd') {
                            this.beg += 4;
                        }
                    }
                }
            }
            return new String(this.chars, this.beg, this.end - this.beg);
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private String quotedAV() {
        this.pos++;
        this.beg = this.pos;
        this.end = this.beg;
        while (this.pos != this.length) {
            if (this.chars[this.pos] != '\"') {
                if (this.chars[this.pos] != '\\') {
                    this.chars[this.end] = (char) this.chars[this.pos];
                } else {
                    this.chars[this.end] = (char) getEscaped();
                }
                this.pos++;
                this.end++;
            } else {
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    public String findMostSpecific(String str) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        String nextAT = nextAT();
        if (nextAT == null) {
            return null;
        }
        do {
            String str2 = "";
            if (this.pos == this.length) {
                return null;
            }
            switch (this.chars[this.pos]) {
                case '\"':
                    str2 = quotedAV();
                    break;
                case '#':
                    str2 = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    str2 = escapedAV();
                    break;
            }
            if (str.equalsIgnoreCase(nextAT)) {
                return str2;
            }
            if (this.pos >= this.length) {
                return null;
            }
            if (this.chars[this.pos] == ',' || this.chars[this.pos] == ';' || this.chars[this.pos] == '+') {
                this.pos++;
                nextAT = nextAT();
            } else {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
        } while (nextAT != null);
        throw new IllegalStateException("Malformed DN: " + this.dn);
    }
}
