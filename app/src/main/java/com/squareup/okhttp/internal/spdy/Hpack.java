package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.ByteString;
import okio.a;
import okio.j;
import okio.k;
import okio.v;

final class Hpack {
    private static final Map NAME_TO_FIRST_INDEX = nameToFirstIndex();
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_5_BITS = 31;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    private static final Header[] STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};

    final class Reader {
        Header[] dynamicTable = new Header[8];
        int dynamicTableByteCount = 0;
        int headerCount = 0;
        private final List headerList = new ArrayList();
        private int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        int nextHeaderIndex = (this.dynamicTable.length - 1);
        private final a source;

        Reader(int i, v vVar) {
            this.headerTableSizeSetting = i;
            this.maxDynamicTableByteCount = i;
            this.source = j.AE(vVar);
        }

        private void adjustDynamicTableByteCount() {
            if (this.maxDynamicTableByteCount < this.dynamicTableByteCount) {
                if (this.maxDynamicTableByteCount != 0) {
                    evictToRecoverBytes(this.dynamicTableByteCount - this.maxDynamicTableByteCount);
                } else {
                    clearDynamicTable();
                }
            }
        }

        private void clearDynamicTable() {
            this.headerList.clear();
            Arrays.fill(this.dynamicTable, null);
            this.nextHeaderIndex = this.dynamicTable.length - 1;
            this.headerCount = 0;
            this.dynamicTableByteCount = 0;
        }

        private int dynamicTableIndex(int i) {
            return (this.nextHeaderIndex + 1) + i;
        }

        private int evictToRecoverBytes(int i) {
            int i2 = 0;
            if (i > 0) {
                int length = this.dynamicTable.length;
                while (true) {
                    length--;
                    if (length >= this.nextHeaderIndex && i > 0) {
                        i -= this.dynamicTable[length].hpackSize;
                        this.dynamicTableByteCount -= this.dynamicTable[length].hpackSize;
                        this.headerCount--;
                        i2++;
                    }
                }
                System.arraycopy(this.dynamicTable, this.nextHeaderIndex + 1, this.dynamicTable, (this.nextHeaderIndex + 1) + i2, this.headerCount);
                this.nextHeaderIndex += i2;
            }
            return i2;
        }

        private ByteString getName(int i) {
            return !isStaticHeader(i) ? this.dynamicTable[dynamicTableIndex(i - Hpack.STATIC_HEADER_TABLE.length)].name : Hpack.STATIC_HEADER_TABLE[i].name;
        }

        private void insertIntoDynamicTable(int i, Header header) {
            this.headerList.add(header);
            int i2 = header.hpackSize;
            if (i != -1) {
                i2 -= this.dynamicTable[dynamicTableIndex(i)].hpackSize;
            }
            if (i2 <= this.maxDynamicTableByteCount) {
                int evictToRecoverBytes = evictToRecoverBytes((this.dynamicTableByteCount + i2) - this.maxDynamicTableByteCount);
                if (i != -1) {
                    this.dynamicTable[(evictToRecoverBytes + dynamicTableIndex(i)) + i] = header;
                } else {
                    if (this.headerCount + 1 > this.dynamicTable.length) {
                        Object obj = new Header[(this.dynamicTable.length * 2)];
                        System.arraycopy(this.dynamicTable, 0, obj, this.dynamicTable.length, this.dynamicTable.length);
                        this.nextHeaderIndex = this.dynamicTable.length - 1;
                        this.dynamicTable = obj;
                    }
                    evictToRecoverBytes = this.nextHeaderIndex;
                    this.nextHeaderIndex = evictToRecoverBytes - 1;
                    this.dynamicTable[evictToRecoverBytes] = header;
                    this.headerCount++;
                }
                this.dynamicTableByteCount = i2 + this.dynamicTableByteCount;
                return;
            }
            clearDynamicTable();
        }

        private boolean isStaticHeader(int i) {
            return i >= 0 && i <= Hpack.STATIC_HEADER_TABLE.length - 1;
        }

        private int readByte() {
            return this.source.readByte() & 255;
        }

        private void readIndexedHeader(int i) {
            if (isStaticHeader(i)) {
                this.headerList.add(Hpack.STATIC_HEADER_TABLE[i]);
                return;
            }
            int dynamicTableIndex = dynamicTableIndex(i - Hpack.STATIC_HEADER_TABLE.length);
            if (dynamicTableIndex >= 0 && dynamicTableIndex <= this.dynamicTable.length - 1) {
                this.headerList.add(this.dynamicTable[dynamicTableIndex]);
                return;
            }
            throw new IOException("Header index too large " + (i + 1));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int i) {
            insertIntoDynamicTable(-1, new Header(getName(i), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() {
            insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(readByteString()), readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int i) {
            this.headerList.add(new Header(getName(i), readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() {
            this.headerList.add(new Header(Hpack.checkLowercase(readByteString()), readByteString()));
        }

        public List getAndResetHeaderList() {
            List arrayList = new ArrayList(this.headerList);
            this.headerList.clear();
            return arrayList;
        }

        void headerTableSizeSetting(int i) {
            this.headerTableSizeSetting = i;
            this.maxDynamicTableByteCount = i;
            adjustDynamicTableByteCount();
        }

        int maxDynamicTableByteCount() {
            return this.maxDynamicTableByteCount;
        }

        ByteString readByteString() {
            Object obj = null;
            int readByte = readByte();
            if ((readByte & 128) == 128) {
                obj = 1;
            }
            readByte = readInt(readByte, Hpack.PREFIX_7_BITS);
            return obj == null ? this.source.zT((long) readByte) : ByteString.Ar(Huffman.get().decode(this.source.zV((long) readByte)));
        }

        void readHeaders() {
            while (!this.source.zL()) {
                int readByte = this.source.readByte() & 255;
                if (readByte == 128) {
                    throw new IOException("index == 0");
                } else if ((readByte & 128) == 128) {
                    readIndexedHeader(readInt(readByte, Hpack.PREFIX_7_BITS) - 1);
                } else if (readByte == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((readByte & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(readByte, Hpack.PREFIX_6_BITS) - 1);
                } else if ((readByte & 32) == 32) {
                    this.maxDynamicTableByteCount = readInt(readByte, Hpack.PREFIX_5_BITS);
                    if (this.maxDynamicTableByteCount >= 0 && this.maxDynamicTableByteCount <= this.headerTableSizeSetting) {
                        adjustDynamicTableByteCount();
                    } else {
                        throw new IOException("Invalid dynamic table size update " + this.maxDynamicTableByteCount);
                    }
                } else if (readByte == 16 || readByte == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(readInt(readByte, Hpack.PREFIX_4_BITS) - 1);
                }
            }
        }

        int readInt(int i, int i2) {
            int i3 = 0;
            int i4 = i & i2;
            if (i4 < i2) {
                return i4;
            }
            while (true) {
                i4 = readByte();
                if ((i4 & 128) == 0) {
                    return (i4 << i3) + i2;
                }
                i2 += (i4 & Hpack.PREFIX_7_BITS) << i3;
                i3 += 7;
            }
        }
    }

    final class Writer {
        private final k out;

        Writer(k kVar) {
            this.out = kVar;
        }

        void writeByteString(ByteString byteString) {
            writeInt(byteString.size(), Hpack.PREFIX_7_BITS, 0);
            this.out.zZ(byteString);
        }

        void writeHeaders(List list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ByteString AA = ((Header) list.get(i)).name.AA();
                Integer num = (Integer) Hpack.NAME_TO_FIRST_INDEX.get(AA);
                if (num == null) {
                    this.out.Ad(0);
                    writeByteString(AA);
                    writeByteString(((Header) list.get(i)).value);
                } else {
                    writeInt(num.intValue() + 1, Hpack.PREFIX_4_BITS, 0);
                    writeByteString(((Header) list.get(i)).value);
                }
            }
        }

        void writeInt(int i, int i2, int i3) {
            if (i >= i2) {
                this.out.Ad(i3 | i2);
                int i4 = i - i2;
                while (i4 >= 128) {
                    this.out.Ad((i4 & Hpack.PREFIX_7_BITS) | 128);
                    i4 >>>= 7;
                }
                this.out.Ad(i4);
                return;
            }
            this.out.Ad(i3 | i);
        }
    }

    private Hpack() {
    }

    private static ByteString checkLowercase(ByteString byteString) {
        int size = byteString.size();
        for (int i = 0; i < size; i++) {
            byte b = byteString.getByte(i);
            if (b >= (byte) 65 && b <= (byte) 90) {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + byteString.At());
            }
        }
        return byteString;
    }

    private static Map nameToFirstIndex() {
        Map linkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        for (int i = 0; i < STATIC_HEADER_TABLE.length; i++) {
            if (!linkedHashMap.containsKey(STATIC_HEADER_TABLE[i].name)) {
                linkedHashMap.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }
}
