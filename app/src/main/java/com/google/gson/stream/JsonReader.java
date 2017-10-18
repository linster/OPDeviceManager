package com.google.gson.stream;

import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class JsonReader implements Closeable {
    private static final char[] NON_EXECUTE_PREFIX;
    private final char[] buffer;
    private final Reader in;
    private boolean lenient;
    private int limit;
    private int lineNumber;
    private int lineStart;
    private int[] pathIndices;
    private String[] pathNames;
    private int peeked;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int pos;
    private int[] stack;
    private int stackSize;

    static {
        NON_EXECUTE_PREFIX = ")]}'\n";
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() {
            public void promoteNameToValue(JsonReader reader) throws IOException {
                if (reader instanceof JsonTreeReader) {
                    ((JsonTreeReader) reader).promoteNameToValue();
                    return;
                }
                int p = reader.peeked;
                if (p == 0) {
                    p = reader.doPeek();
                }
                if (p == 13) {
                    reader.peeked = 9;
                } else if (p == 12) {
                    reader.peeked = 8;
                } else if (p != 14) {
                    throw new IllegalStateException("Expected a name but was " + reader.peek() + " " + " at line " + reader.getLineNumber() + " column " + reader.getColumnNumber() + " path " + reader.getPath());
                } else {
                    reader.peeked = 10;
                }
            }
        };
    }

    public JsonReader(Reader in) {
        this.lenient = false;
        this.buffer = new char[1024];
        this.pos = 0;
        this.limit = 0;
        this.lineNumber = 0;
        this.lineStart = 0;
        this.peeked = 0;
        this.stack = new int[32];
        this.stackSize = 0;
        int[] iArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr[i] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        if (in != null) {
            this.in = in;
            return;
        }
        throw new NullPointerException("in == null");
    }

    public final void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    public void beginArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p != 3) {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        push(1);
        this.pathIndices[this.stackSize - 1] = 0;
        this.peeked = 0;
    }

    public void endArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p != 4) {
            throw new IllegalStateException("Expected END_ARRAY but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.stackSize--;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        this.peeked = 0;
    }

    public void beginObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p != 1) {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        push(3);
        this.peeked = 0;
    }

    public void endObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p != 2) {
            throw new IllegalStateException("Expected END_OBJECT but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.stackSize--;
        this.pathNames[this.stackSize] = null;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        this.peeked = 0;
    }

    public boolean hasNext() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 2 || p == 4) {
            return false;
        }
        return true;
    }

    public JsonToken peek() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        switch (p) {
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

    private int doPeek() throws IOException {
        int peekStack = this.stack[this.stackSize - 1];
        if (peekStack == 1) {
            this.stack[this.stackSize - 1] = 2;
        } else if (peekStack == 2) {
            switch (nextNonWhitespace(true)) {
                case 44:
                    break;
                case 59:
                    checkLenient();
                    break;
                case 93:
                    this.peeked = 4;
                    return 4;
                default:
                    throw syntaxError("Unterminated array");
            }
        } else if (peekStack == 3 || peekStack == 5) {
            this.stack[this.stackSize - 1] = 4;
            if (peekStack == 5) {
                switch (nextNonWhitespace(true)) {
                    case 44:
                        break;
                    case 59:
                        checkLenient();
                        break;
                    case 125:
                        this.peeked = 2;
                        return 2;
                    default:
                        throw syntaxError("Unterminated object");
                }
            }
            int c = nextNonWhitespace(true);
            switch (c) {
                case 34:
                    this.peeked = 13;
                    return 13;
                case 39:
                    checkLenient();
                    this.peeked = 12;
                    return 12;
                case 125:
                    if (peekStack == 5) {
                        throw syntaxError("Expected name");
                    }
                    this.peeked = 2;
                    return 2;
                default:
                    checkLenient();
                    this.pos--;
                    if (isLiteral((char) c)) {
                        this.peeked = 14;
                        return 14;
                    }
                    throw syntaxError("Expected name");
            }
        } else if (peekStack == 4) {
            this.stack[this.stackSize - 1] = 5;
            switch (nextNonWhitespace(true)) {
                case 58:
                    break;
                case 61:
                    checkLenient();
                    if (this.pos < this.limit || fillBuffer(1)) {
                        if (this.buffer[this.pos] == '>') {
                            this.pos++;
                            break;
                        }
                    }
                    break;
                break;
                default:
                    throw syntaxError("Expected ':'");
            }
        } else if (peekStack == 6) {
            if (this.lenient) {
                consumeNonExecutePrefix();
            }
            this.stack[this.stackSize - 1] = 7;
        } else if (peekStack != 7) {
            if (peekStack == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        } else if (nextNonWhitespace(false) != -1) {
            checkLenient();
            this.pos--;
        } else {
            this.peeked = 17;
            return 17;
        }
        switch (nextNonWhitespace(true)) {
            case 34:
                if (this.stackSize == 1) {
                    checkLenient();
                }
                this.peeked = 9;
                return 9;
            case 39:
                checkLenient();
                this.peeked = 8;
                return 8;
            case 44:
            case 59:
                break;
            case 91:
                this.peeked = 3;
                return 3;
            case 93:
                if (peekStack == 1) {
                    this.peeked = 4;
                    return 4;
                }
                break;
            case 123:
                this.peeked = 1;
                return 1;
            default:
                this.pos--;
                if (this.stackSize == 1) {
                    checkLenient();
                }
                int result = peekKeyword();
                if (result != 0) {
                    return result;
                }
                result = peekNumber();
                if (result != 0) {
                    return result;
                }
                if (isLiteral(this.buffer[this.pos])) {
                    checkLenient();
                    this.peeked = 10;
                    return 10;
                }
                throw syntaxError("Expected value");
        }
        if (peekStack == 1 || peekStack == 2) {
            checkLenient();
            this.pos--;
            this.peeked = 7;
            return 7;
        }
        throw syntaxError("Unexpected value");
    }

    private int peekKeyword() throws IOException {
        String keyword;
        int peeking;
        char c = this.buffer[this.pos];
        String keywordUpper;
        if (c == 't' || c == 'T') {
            keyword = "true";
            keywordUpper = "TRUE";
            peeking = 5;
        } else if (c == 'f' || c == 'F') {
            keyword = "false";
            keywordUpper = "FALSE";
            peeking = 6;
        } else if (c != 'n' && c != 'N') {
            return 0;
        } else {
            keyword = "null";
            keywordUpper = "NULL";
            peeking = 7;
        }
        int length = keyword.length();
        int i = 1;
        while (i < length) {
            if (this.pos + i >= this.limit && !fillBuffer(i + 1)) {
                return 0;
            }
            c = this.buffer[this.pos + i];
            if (c != keyword.charAt(i) && c != keywordUpper.charAt(i)) {
                return 0;
            }
            i++;
        }
        if (this.pos + length < this.limit || fillBuffer(length + 1)) {
            if (isLiteral(this.buffer[this.pos + length])) {
                return 0;
            }
        }
        this.pos += length;
        this.peeked = peeking;
        return peeking;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int peekNumber() throws java.io.IOException {
        /*
        r18 = this;
        r0 = r18;
        r2 = r0.buffer;
        r0 = r18;
        r9 = r0.pos;
        r0 = r18;
        r6 = r0.limit;
        r12 = 0;
        r8 = 0;
        r4 = 1;
        r7 = 0;
        r5 = 0;
    L_0x0012:
        r14 = r9 + r5;
        if (r14 == r6) goto L_0x002b;
    L_0x0016:
        r14 = r9 + r5;
        r3 = r2[r14];
        switch(r3) {
            case 43: goto L_0x0051;
            case 45: goto L_0x0043;
            case 46: goto L_0x0062;
            case 69: goto L_0x0058;
            case 101: goto L_0x0058;
            default: goto L_0x001d;
        };
    L_0x001d:
        r14 = 48;
        if (r3 >= r14) goto L_0x0069;
    L_0x0021:
        r0 = r18;
        r14 = r0.isLiteral(r3);
        if (r14 == 0) goto L_0x00c3;
    L_0x0029:
        r14 = 0;
        return r14;
    L_0x002b:
        r14 = r2.length;
        if (r5 == r14) goto L_0x0041;
    L_0x002e:
        r14 = r5 + 1;
        r0 = r18;
        r14 = r0.fillBuffer(r14);
        if (r14 == 0) goto L_0x00c3;
    L_0x0038:
        r0 = r18;
        r9 = r0.pos;
        r0 = r18;
        r6 = r0.limit;
        goto L_0x0016;
    L_0x0041:
        r14 = 0;
        return r14;
    L_0x0043:
        if (r7 == 0) goto L_0x004a;
    L_0x0045:
        r14 = 5;
        if (r7 == r14) goto L_0x004f;
    L_0x0048:
        r14 = 0;
        return r14;
    L_0x004a:
        r8 = 1;
        r7 = 1;
    L_0x004c:
        r5 = r5 + 1;
        goto L_0x0012;
    L_0x004f:
        r7 = 6;
        goto L_0x004c;
    L_0x0051:
        r14 = 5;
        if (r7 == r14) goto L_0x0056;
    L_0x0054:
        r14 = 0;
        return r14;
    L_0x0056:
        r7 = 6;
        goto L_0x004c;
    L_0x0058:
        r14 = 2;
        if (r7 != r14) goto L_0x005d;
    L_0x005b:
        r7 = 5;
        goto L_0x004c;
    L_0x005d:
        r14 = 4;
        if (r7 == r14) goto L_0x005b;
    L_0x0060:
        r14 = 0;
        return r14;
    L_0x0062:
        r14 = 2;
        if (r7 == r14) goto L_0x0067;
    L_0x0065:
        r14 = 0;
        return r14;
    L_0x0067:
        r7 = 3;
        goto L_0x004c;
    L_0x0069:
        r14 = 57;
        if (r3 > r14) goto L_0x0021;
    L_0x006d:
        r14 = 1;
        if (r7 != r14) goto L_0x0076;
    L_0x0070:
        r14 = r3 + -48;
        r14 = -r14;
        r12 = (long) r14;
        r7 = 2;
        goto L_0x004c;
    L_0x0076:
        if (r7 == 0) goto L_0x0070;
    L_0x0078:
        r14 = 2;
        if (r7 == r14) goto L_0x0083;
    L_0x007b:
        r14 = 3;
        if (r7 == r14) goto L_0x00bd;
    L_0x007e:
        r14 = 5;
        if (r7 != r14) goto L_0x00bf;
    L_0x0081:
        r7 = 7;
        goto L_0x004c;
    L_0x0083:
        r14 = 0;
        r14 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r14 != 0) goto L_0x008b;
    L_0x0089:
        r14 = 0;
        return r14;
    L_0x008b:
        r14 = 10;
        r14 = r14 * r12;
        r16 = r3 + -48;
        r0 = r16;
        r0 = (long) r0;
        r16 = r0;
        r10 = r14 - r16;
        r14 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r14 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r14 <= 0) goto L_0x00b7;
    L_0x00a0:
        r14 = 1;
    L_0x00a1:
        if (r14 != 0) goto L_0x00b3;
    L_0x00a3:
        r14 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r14 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r14 != 0) goto L_0x00bb;
    L_0x00ac:
        r14 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r14 < 0) goto L_0x00b9;
    L_0x00b0:
        r14 = 1;
    L_0x00b1:
        if (r14 != 0) goto L_0x00bb;
    L_0x00b3:
        r14 = 1;
    L_0x00b4:
        r4 = r4 & r14;
        r12 = r10;
        goto L_0x004c;
    L_0x00b7:
        r14 = 0;
        goto L_0x00a1;
    L_0x00b9:
        r14 = 0;
        goto L_0x00b1;
    L_0x00bb:
        r14 = 0;
        goto L_0x00b4;
    L_0x00bd:
        r7 = 4;
        goto L_0x004c;
    L_0x00bf:
        r14 = 6;
        if (r7 == r14) goto L_0x0081;
    L_0x00c2:
        goto L_0x004c;
    L_0x00c3:
        r14 = 2;
        if (r7 == r14) goto L_0x00d6;
    L_0x00c6:
        r14 = 2;
        if (r7 != r14) goto L_0x00f9;
    L_0x00c9:
        r0 = r18;
        r0.peekedNumberLength = r5;
        r14 = 16;
        r0 = r18;
        r0.peeked = r14;
        r14 = 16;
        return r14;
    L_0x00d6:
        if (r4 == 0) goto L_0x00c6;
    L_0x00d8:
        r14 = -9223372036854775808;
        r14 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r14 != 0) goto L_0x00e0;
    L_0x00de:
        if (r8 == 0) goto L_0x00c6;
    L_0x00e0:
        if (r8 != 0) goto L_0x00e3;
    L_0x00e2:
        r12 = -r12;
    L_0x00e3:
        r0 = r18;
        r0.peekedLong = r12;
        r0 = r18;
        r14 = r0.pos;
        r14 = r14 + r5;
        r0 = r18;
        r0.pos = r14;
        r14 = 15;
        r0 = r18;
        r0.peeked = r14;
        r14 = 15;
        return r14;
    L_0x00f9:
        r14 = 4;
        if (r7 == r14) goto L_0x00c9;
    L_0x00fc:
        r14 = 7;
        if (r7 == r14) goto L_0x00c9;
    L_0x00ff:
        r14 = 0;
        return r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.peekNumber():int");
    }

    private boolean isLiteral(char c) throws IOException {
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
                checkLenient();
                break;
            default:
                return true;
        }
        return false;
    }

    public String nextName() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 14) {
            result = nextUnquotedValue();
        } else if (p == 12) {
            result = nextQuotedValue('\'');
        } else if (p != 13) {
            throw new IllegalStateException("Expected a name but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        } else {
            result = nextQuotedValue('\"');
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = result;
        return result;
    }

    public String nextString() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 10) {
            result = nextUnquotedValue();
        } else if (p == 8) {
            result = nextQuotedValue('\'');
        } else if (p == 9) {
            result = nextQuotedValue('\"');
        } else if (p == 11) {
            result = this.peekedString;
            this.peekedString = null;
        } else if (p == 15) {
            result = Long.toString(this.peekedLong);
        } else if (p != 16) {
            throw new IllegalStateException("Expected a string but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        } else {
            result = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return result;
    }

    public boolean nextBoolean() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        int[] iArr;
        int i;
        if (p == 5) {
            this.peeked = 0;
            iArr = this.pathIndices;
            i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        } else if (p != 6) {
            throw new IllegalStateException("Expected a boolean but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        } else {
            this.peeked = 0;
            iArr = this.pathIndices;
            i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return false;
        }
    }

    public void nextNull() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p != 7) {
            throw new IllegalStateException("Expected null but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
    }

    public double nextDouble() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p != 15) {
            if (p == 16) {
                this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
                this.pos += this.peekedNumberLength;
            } else if (p == 8 || p == 9) {
                char c;
                if (p != 8) {
                    c = '\"';
                } else {
                    c = '\'';
                }
                this.peekedString = nextQuotedValue(c);
            } else if (p == 10) {
                this.peekedString = nextUnquotedValue();
            } else if (p != 11) {
                throw new IllegalStateException("Expected a double but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
            }
            this.peeked = 11;
            double result = Double.parseDouble(this.peekedString);
            if (!this.lenient) {
                if (Double.isNaN(result) || Double.isInfinite(result)) {
                    throw new MalformedJsonException("JSON forbids NaN and infinities: " + result + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
                }
            }
            this.peekedString = null;
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return result;
        }
        this.peeked = 0;
        iArr = this.pathIndices;
        i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return (double) this.peekedLong;
    }

    public long nextLong() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        int[] iArr;
        int i;
        if (p == 15) {
            this.peeked = 0;
            iArr = this.pathIndices;
            i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        } else if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
            this.peeked = 11;
            double asDouble = Double.parseDouble(this.peekedString);
            result = (long) asDouble;
            if (((double) result) != asDouble) {
                throw new NumberFormatException("Expected a long but was " + this.peekedString + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
            }
            this.peekedString = null;
            this.peeked = 0;
            iArr = this.pathIndices;
            i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return result;
        } else if (p == 8 || p == 9) {
            this.peekedString = nextQuotedValue(p != 8 ? '\"' : '\'');
            try {
                result = Long.parseLong(this.peekedString);
                this.peeked = 0;
                iArr = this.pathIndices;
                i = this.stackSize - 1;
                iArr[i] = iArr[i] + 1;
                return result;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected a long but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
    }

    private String nextQuotedValue(char quote) throws IOException {
        char[] buffer = this.buffer;
        StringBuilder builder = new StringBuilder();
        do {
            int p = this.pos;
            int l = this.limit;
            int start = p;
            int p2 = p;
            while (p2 < l) {
                p = p2 + 1;
                char c = buffer[p2];
                if (c != quote) {
                    if (c == '\\') {
                        this.pos = p;
                        builder.append(buffer, start, (p - start) - 1);
                        builder.append(readEscapeCharacter());
                        p = this.pos;
                        l = this.limit;
                        start = p;
                    } else if (c == '\n') {
                        this.lineNumber++;
                        this.lineStart = p;
                    }
                    p2 = p;
                } else {
                    this.pos = p;
                    builder.append(buffer, start, (p - start) - 1);
                    return builder.toString();
                }
            }
            builder.append(buffer, start, p2 - start);
            this.pos = p2;
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private String nextUnquotedValue() throws IOException {
        StringBuilder builder = null;
        int i = 0;
        while (true) {
            String result;
            if (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
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
                        checkLenient();
                        break;
                    default:
                        i++;
                        continue;
                }
            } else if (i >= this.buffer.length) {
                if (builder == null) {
                    builder = new StringBuilder();
                }
                builder.append(this.buffer, this.pos, i);
                this.pos += i;
                i = 0;
                if (fillBuffer(1)) {
                }
            } else if (fillBuffer(i + 1)) {
            }
            if (builder != null) {
                builder.append(this.buffer, this.pos, i);
                result = builder.toString();
            } else {
                result = new String(this.buffer, this.pos, i);
            }
            this.pos += i;
            return result;
        }
    }

    private void skipQuotedValue(char quote) throws IOException {
        char[] buffer = this.buffer;
        do {
            int p = this.pos;
            int l = this.limit;
            int p2 = p;
            while (p2 < l) {
                p = p2 + 1;
                char c = buffer[p2];
                if (c != quote) {
                    if (c == '\\') {
                        this.pos = p;
                        readEscapeCharacter();
                        p = this.pos;
                        l = this.limit;
                    } else if (c == '\n') {
                        this.lineNumber++;
                        this.lineStart = p;
                    }
                    p2 = p;
                } else {
                    this.pos = p;
                    return;
                }
            }
            this.pos = p2;
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int i = 0;
            while (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
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
                        checkLenient();
                        break;
                    default:
                        i++;
                }
                this.pos += i;
                return;
            }
            this.pos += i;
        } while (fillBuffer(1));
    }

    public int nextInt() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        int result;
        int[] iArr;
        int i;
        if (p == 15) {
            result = (int) this.peekedLong;
            if (this.peekedLong != ((long) result)) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
            }
            this.peeked = 0;
            iArr = this.pathIndices;
            i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return result;
        } else if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
            this.peeked = 11;
            double asDouble = Double.parseDouble(this.peekedString);
            result = (int) asDouble;
            if (((double) result) != asDouble) {
                throw new NumberFormatException("Expected an int but was " + this.peekedString + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
            }
            this.peekedString = null;
            this.peeked = 0;
            iArr = this.pathIndices;
            i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return result;
        } else if (p == 8 || p == 9) {
            this.peekedString = nextQuotedValue(p != 8 ? '\"' : '\'');
            try {
                result = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                iArr = this.pathIndices;
                i = this.stackSize - 1;
                iArr[i] = iArr[i] + 1;
                return result;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected an int but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
    }

    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    public void skipValue() throws IOException {
        int count = 0;
        do {
            int p = this.peeked;
            if (p == 0) {
                p = doPeek();
            }
            if (p == 3) {
                push(1);
                count++;
            } else if (p == 1) {
                push(3);
                count++;
            } else if (p == 4) {
                this.stackSize--;
                count--;
            } else if (p == 2) {
                this.stackSize--;
                count--;
            } else if (p == 14 || p == 10) {
                skipUnquotedValue();
            } else if (p == 8 || p == 12) {
                skipQuotedValue('\'');
            } else if (p == 9 || p == 13) {
                skipQuotedValue('\"');
            } else if (p == 16) {
                this.pos += this.peekedNumberLength;
            }
            this.peeked = 0;
        } while (count != 0);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        this.pathNames[this.stackSize - 1] = "null";
    }

    private void push(int newTop) {
        if (this.stackSize == this.stack.length) {
            int[] newStack = new int[(this.stackSize * 2)];
            int[] newPathIndices = new int[(this.stackSize * 2)];
            String[] newPathNames = new String[(this.stackSize * 2)];
            System.arraycopy(this.stack, 0, newStack, 0, this.stackSize);
            System.arraycopy(this.pathIndices, 0, newPathIndices, 0, this.stackSize);
            System.arraycopy(this.pathNames, 0, newPathNames, 0, this.stackSize);
            this.stack = newStack;
            this.pathIndices = newPathIndices;
            this.pathNames = newPathNames;
        }
        int[] iArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr[i] = newTop;
    }

    private boolean fillBuffer(int minimum) throws IOException {
        char[] buffer = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit == this.pos) {
            this.limit = 0;
        } else {
            this.limit -= this.pos;
            System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
        }
        this.pos = 0;
        do {
            int total = this.in.read(buffer, this.limit, buffer.length - this.limit);
            if (total == -1) {
                return false;
            }
            this.limit += total;
            if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && buffer[0] == '\ufeff') {
                this.pos++;
                this.lineStart++;
                minimum++;
            }
        } while (this.limit < minimum);
        return true;
    }

    private int getLineNumber() {
        return this.lineNumber + 1;
    }

    private int getColumnNumber() {
        return (this.pos - this.lineStart) + 1;
    }

    private int nextNonWhitespace(boolean throwOnEof) throws IOException {
        char[] buffer = this.buffer;
        int p = this.pos;
        int l = this.limit;
        while (true) {
            if (p == l) {
                this.pos = p;
                if (fillBuffer(1)) {
                    p = this.pos;
                    l = this.limit;
                } else if (!throwOnEof) {
                    return -1;
                } else {
                    throw new EOFException("End of input at line " + getLineNumber() + " column " + getColumnNumber());
                }
            }
            int p2 = p + 1;
            int c = buffer[p];
            if (c == 10) {
                this.lineNumber++;
                this.lineStart = p2;
                p = p2;
            } else if (c == 32 || c == 13) {
                p = p2;
            } else if (c == 9) {
                p = p2;
            } else if (c == 47) {
                this.pos = p2;
                if (p2 == l) {
                    this.pos--;
                    boolean charsLoaded = fillBuffer(2);
                    this.pos++;
                    if (!charsLoaded) {
                        return c;
                    }
                }
                checkLenient();
                switch (buffer[this.pos]) {
                    case '*':
                        this.pos++;
                        if (skipTo("*/")) {
                            p = this.pos + 2;
                            l = this.limit;
                            break;
                        }
                        throw syntaxError("Unterminated comment");
                    case '/':
                        this.pos++;
                        skipToEndOfLine();
                        p = this.pos;
                        l = this.limit;
                        break;
                    default:
                        return c;
                }
            } else if (c != 35) {
                this.pos = p2;
                return c;
            } else {
                this.pos = p2;
                checkLenient();
                skipToEndOfLine();
                p = this.pos;
                l = this.limit;
            }
        }
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws IOException {
        char c;
        do {
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                c = cArr[i];
                if (c == '\n') {
                    this.lineNumber++;
                    this.lineStart = this.pos;
                    return;
                }
            } else {
                return;
            }
        } while (c != '\r');
    }

    private boolean skipTo(String toFind) throws IOException {
        while (true) {
            if (this.pos + toFind.length() > this.limit && !fillBuffer(toFind.length())) {
                return false;
            }
            if (this.buffer[this.pos] != '\n') {
                int c = 0;
                while (c < toFind.length()) {
                    if (this.buffer[this.pos + c] == toFind.charAt(c)) {
                        c++;
                    }
                }
                return true;
            }
            this.lineNumber++;
            this.lineStart = this.pos + 1;
            this.pos++;
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " at line " + getLineNumber() + " column " + getColumnNumber();
    }

    public String getPath() {
        StringBuilder result = new StringBuilder().append('$');
        int size = this.stackSize;
        for (int i = 0; i < size; i++) {
            switch (this.stack[i]) {
                case 1:
                case 2:
                    result.append('[').append(this.pathIndices[i]).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    result.append('.');
                    if (this.pathNames[i] == null) {
                        break;
                    }
                    result.append(this.pathNames[i]);
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        char escaped = cArr[i];
        switch (escaped) {
            case '\n':
                this.lineNumber++;
                this.lineStart = this.pos;
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
                if (this.pos + 4 > this.limit && !fillBuffer(4)) {
                    throw syntaxError("Unterminated escape sequence");
                }
                char result = '\u0000';
                int i2 = this.pos;
                int end = i2 + 4;
                while (i2 < end) {
                    char c = this.buffer[i2];
                    result = (char) (result << 4);
                    if (c >= '0' && c <= '9') {
                        result = (char) ((c - 48) + result);
                    } else if (c >= 'a' && c <= 'f') {
                        result = (char) (((c - 97) + 10) + result);
                    } else if (c >= 'A' && c <= 'F') {
                        result = (char) (((c - 65) + 10) + result);
                    } else {
                        throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                    }
                    i2++;
                }
                this.pos += 4;
                return result;
        }
        return escaped;
    }

    private IOException syntaxError(String message) throws IOException {
        throw new MalformedJsonException(message + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    private void consumeNonExecutePrefix() throws IOException {
        nextNonWhitespace(true);
        this.pos--;
        if (this.pos + NON_EXECUTE_PREFIX.length <= this.limit || fillBuffer(NON_EXECUTE_PREFIX.length)) {
            int i = 0;
            while (i < NON_EXECUTE_PREFIX.length) {
                if (this.buffer[this.pos + i] == NON_EXECUTE_PREFIX[i]) {
                    i++;
                } else {
                    return;
                }
            }
            this.pos += NON_EXECUTE_PREFIX.length;
        }
    }
}
