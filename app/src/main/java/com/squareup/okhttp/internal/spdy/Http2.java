package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.ByteString;
import okio.a;
import okio.b;
import okio.g;
import okio.k;
import okio.v;

public final class Http2 implements Variant {
    private static final ByteString CONNECTION_PREFACE = ByteString.As("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    static final byte FLAG_ACK = (byte) 1;
    static final byte FLAG_COMPRESSED = (byte) 32;
    static final byte FLAG_END_HEADERS = (byte) 4;
    static final byte FLAG_END_PUSH_PROMISE = (byte) 4;
    static final byte FLAG_END_STREAM = (byte) 1;
    static final byte FLAG_NONE = (byte) 0;
    static final byte FLAG_PADDED = (byte) 8;
    static final byte FLAG_PRIORITY = (byte) 32;
    static final int INITIAL_MAX_FRAME_SIZE = 16384;
    static final byte TYPE_CONTINUATION = (byte) 9;
    static final byte TYPE_DATA = (byte) 0;
    static final byte TYPE_GOAWAY = (byte) 7;
    static final byte TYPE_HEADERS = (byte) 1;
    static final byte TYPE_PING = (byte) 6;
    static final byte TYPE_PRIORITY = (byte) 2;
    static final byte TYPE_PUSH_PROMISE = (byte) 5;
    static final byte TYPE_RST_STREAM = (byte) 3;
    static final byte TYPE_SETTINGS = (byte) 4;
    static final byte TYPE_WINDOW_UPDATE = (byte) 8;
    private static final Logger logger = Logger.getLogger(FrameLogger.class);

    final class ContinuationSource implements v {
        byte flags;
        int left;
        int length;
        short padding;
        private final a source;
        int streamId;

        public ContinuationSource(a aVar) {
            this.source = aVar;
        }

        private void readContinuationHeader() {
            int i = this.streamId;
            int access$300 = Http2.readMedium(this.source);
            this.left = access$300;
            this.length = access$300;
            byte readByte = (byte) (this.source.readByte() & 255);
            this.flags = (byte) ((byte) (this.source.readByte() & 255));
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, readByte, this.flags));
            }
            this.streamId = this.source.zP() & Integer.MAX_VALUE;
            if (readByte != Http2.TYPE_CONTINUATION) {
                throw Http2.ioException("%s != TYPE_CONTINUATION", Byte.valueOf(readByte));
            } else if (this.streamId != i) {
                throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        public void close() {
        }

        public long read(k kVar, long j) {
            while (this.left == 0) {
                this.source.skip((long) this.padding);
                this.padding = (short) 0;
                if ((this.flags & 4) != 0) {
                    return -1;
                }
                readContinuationHeader();
            }
            long read = this.source.read(kVar, Math.min(j, (long) this.left));
            if (read == -1) {
                return -1;
            }
            this.left = (int) (((long) this.left) - read);
            return read;
        }

        public g timeout() {
            return this.source.timeout();
        }
    }

    final class FrameLogger {
        private static final String[] BINARY = new String[256];
        private static final String[] FLAGS = new String[64];
        private static final String[] TYPES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};

        static {
            int i = 2;
            int i2 = 32;
            int i3 = 4;
            int i4 = 1;
            int i5 = 0;
            int i6 = 0;
            while (true) {
                if (i6 >= BINARY.length) {
                    int i7;
                    FLAGS[i5] = "";
                    FLAGS[i4] = "END_STREAM";
                    int[] iArr = new int[i4];
                    iArr[i5] = i4;
                    FLAGS[8] = "PADDED";
                    int length = iArr.length;
                    for (i6 = i5; i6 < length; i6++) {
                        int i8 = iArr[i6];
                        String[] strArr = FLAGS;
                        i7 = i8 | 8;
                        StringBuilder stringBuilder = new StringBuilder();
                        String str = "|PADDED";
                        strArr[i7] = FLAGS[i8];
                    }
                    FLAGS[i3] = "END_HEADERS";
                    FLAGS[i2] = "PRIORITY";
                    FLAGS[36] = "END_HEADERS|PRIORITY";
                    int[] iArr2 = new int[]{i3, i2, 36};
                    int length2 = iArr2.length;
                    for (length = i5; length < length2; length++) {
                        i7 = iArr2[length];
                        int length3 = iArr.length;
                        for (i6 = i5; i6 < length3; i6++) {
                            int i9 = iArr[i6];
                            String[] strArr2 = FLAGS;
                            i3 = i9 | i7;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            String str2 = FLAGS[i9];
                            str2 = FLAGS[i7];
                            strArr2[i3] = stringBuilder2;
                            i4 = FLAGS;
                            i3 = (i9 | i7) | 8;
                            stringBuilder2 = new StringBuilder();
                            i = FLAGS;
                            Object obj = i[i9];
                            String str3 = FLAGS[i7];
                            i2 = "|PADDED";
                            i4[i3] = obj;
                        }
                    }
                    while (true) {
                        i6 = FLAGS.length;
                        if (i5 >= i6) {
                            break;
                        }
                        if (FLAGS[i5] == null) {
                            FLAGS[i5] = BINARY[i5];
                        }
                        i5++;
                    }
                }
                String[] strArr3 = BINARY;
                Object[] objArr = new Object[i4];
                objArr[i5] = Integer.toBinaryString(i6);
                strArr3[i6] = String.format("%8s", objArr);
                i6++;
            }
        }

        FrameLogger() {
        }

        static String formatFlags(byte b, byte b2) {
            if (b2 == (byte) 0) {
                return "";
            }
            switch (b) {
                case (byte) 2:
                case (byte) 3:
                case (byte) 7:
                case (byte) 8:
                    return BINARY[b2];
                case (byte) 4:
                case (byte) 6:
                    return b2 != (byte) 1 ? BINARY[b2] : "ACK";
                default:
                    String str = b2 >= FLAGS.length ? BINARY[b2] : FLAGS[b2];
                    return (b == Http2.TYPE_PUSH_PROMISE && (b2 & 4) != 0) ? str.replace("HEADERS", "PUSH_PROMISE") : (b == (byte) 0 && (b2 & 32) != 0) ? str.replace("PRIORITY", "COMPRESSED") : str;
            }
        }

        static String formatHeader(boolean z, int i, int i2, byte b, byte b2) {
            String format = b >= TYPES.length ? String.format("0x%02x", new Object[]{Byte.valueOf(b)}) : TYPES[b];
            String formatFlags = formatFlags(b, b2);
            String str = "%s 0x%08x %5d %-13s %s";
            Object[] objArr = new Object[5];
            objArr[0] = !z ? ">>" : "<<";
            objArr[1] = Integer.valueOf(i);
            objArr[2] = Integer.valueOf(i2);
            objArr[3] = format;
            objArr[4] = formatFlags;
            return String.format(str, objArr);
        }
    }

    final class Reader implements FrameReader {
        private final boolean client;
        private final ContinuationSource continuation = new ContinuationSource(this.source);
        final Reader hpackReader;
        private final a source;

        Reader(a aVar, int i, boolean z) {
            this.source = aVar;
            this.client = z;
            this.hpackReader = new Reader(i, this.continuation);
        }

        private void readData(Handler handler, int i, byte b, int i2) {
            short s = (short) 1;
            short s2 = (short) 0;
            boolean z = (b & 1) != 0;
            if ((b & 32) == 0) {
                s = (short) 0;
            }
            if (s == (short) 0) {
                if ((b & 8) != 0) {
                    s2 = (short) (this.source.readByte() & 255);
                }
                handler.data(z, i2, this.source, Http2.lengthWithoutPadding(i, b, s2));
                this.source.skip((long) s2);
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        }

        private void readGoAway(Handler handler, int i, byte b, int i2) {
            if (i < 8) {
                throw Http2.ioException("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i));
            } else if (i2 == 0) {
                int zP = this.source.zP();
                int i3 = i - 8;
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(this.source.zP());
                if (fromHttp2 != null) {
                    ByteString byteString = ByteString.uD;
                    if (i3 > 0) {
                        byteString = this.source.zT((long) i3);
                    }
                    handler.goAway(zP, fromHttp2, byteString);
                    return;
                }
                throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(r0));
            } else {
                throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            }
        }

        private List readHeaderBlock(int i, short s, byte b, int i2) {
            ContinuationSource continuationSource = this.continuation;
            this.continuation.left = i;
            continuationSource.length = i;
            this.continuation.padding = (short) s;
            this.continuation.flags = (byte) b;
            this.continuation.streamId = i2;
            this.hpackReader.readHeaders();
            return this.hpackReader.getAndResetHeaderList();
        }

        private void readHeaders(Handler handler, int i, byte b, int i2) {
            if (i2 != 0) {
                boolean z = (b & 1) != 0;
                short readByte = (b & 8) == 0 ? (short) 0 : (short) (this.source.readByte() & 255);
                if ((b & 32) != 0) {
                    readPriority(handler, i2);
                    i -= 5;
                }
                handler.headers(false, z, i2, -1, readHeaderBlock(Http2.lengthWithoutPadding(i, b, readByte), readByte, b, i2), HeadersMode.HTTP_20_HEADERS);
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }

        private void readPing(Handler handler, int i, byte b, int i2) {
            boolean z = false;
            if (i != 8) {
                throw Http2.ioException("TYPE_PING length != 8: %s", Integer.valueOf(i));
            } else if (i2 == 0) {
                int zP = this.source.zP();
                int zP2 = this.source.zP();
                if ((b & 1) != 0) {
                    z = true;
                }
                handler.ping(z, zP, zP2);
            } else {
                throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
            }
        }

        private void readPriority(Handler handler, int i) {
            boolean z = false;
            int zP = this.source.zP();
            if ((Integer.MIN_VALUE & zP) != 0) {
                z = true;
            }
            handler.priority(i, zP & Integer.MAX_VALUE, (this.source.readByte() & 255) + 1, z);
        }

        private void readPriority(Handler handler, int i, byte b, int i2) {
            if (i != 5) {
                throw Http2.ioException("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i));
            } else if (i2 != 0) {
                readPriority(handler, i2);
            } else {
                throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
        }

        private void readPushPromise(Handler handler, int i, byte b, int i2) {
            short s = (short) 0;
            if (i2 != 0) {
                if ((b & 8) != 0) {
                    s = (short) (this.source.readByte() & 255);
                }
                handler.pushPromise(i2, this.source.zP() & Integer.MAX_VALUE, readHeaderBlock(Http2.lengthWithoutPadding(i - 4, b, s), s, b, i2));
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }

        private void readRstStream(Handler handler, int i, byte b, int i2) {
            if (i != 4) {
                throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i));
            } else if (i2 != 0) {
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(this.source.zP());
                if (fromHttp2 != null) {
                    handler.rstStream(i2, fromHttp2);
                } else {
                    throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(r0));
                }
            } else {
                throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
        }

        private void readSettings(Handler handler, int i, byte b, int i2) {
            if (i2 != 0) {
                throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((b & 1) == 0) {
                if (i % 6 == 0) {
                    Settings settings = new Settings();
                    for (int i3 = 0; i3 < i; i3 += 6) {
                        int zN = this.source.zN();
                        int zP = this.source.zP();
                        switch (zN) {
                            case 1:
                            case 6:
                                break;
                            case 2:
                                if (!(zP == 0 || zP == 1)) {
                                    throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                                }
                            case 3:
                                zN = 4;
                                break;
                            case 4:
                                zN = 7;
                                if (zP >= 0) {
                                    break;
                                }
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                            case 5:
                                if (zP >= Http2.INITIAL_MAX_FRAME_SIZE && zP <= 16777215) {
                                    break;
                                }
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(zP));
                            default:
                                throw Http2.ioException("PROTOCOL_ERROR invalid settings id: %s", Short.valueOf(zN));
                        }
                        settings.set(zN, 0, zP);
                    }
                    handler.settings(false, settings);
                    if (settings.getHeaderTableSize() >= 0) {
                        this.hpackReader.headerTableSizeSetting(settings.getHeaderTableSize());
                    }
                    return;
                }
                throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i));
            } else if (i == 0) {
                handler.ackSettings();
            } else {
                throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
            }
        }

        private void readWindowUpdate(Handler handler, int i, byte b, int i2) {
            if (i == 4) {
                long zP = ((long) this.source.zP()) & 2147483647L;
                if (zP == 0) {
                    throw Http2.ioException("windowSizeIncrement was 0", Long.valueOf(zP));
                } else {
                    handler.windowUpdate(i2, zP);
                    return;
                }
            }
            throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i));
        }

        public void close() {
            this.source.close();
        }

        public boolean nextFrame(Handler handler) {
            try {
                this.source.zM(9);
                int access$300 = Http2.readMedium(this.source);
                if (access$300 >= 0 && access$300 <= Http2.INITIAL_MAX_FRAME_SIZE) {
                    byte readByte = (byte) (this.source.readByte() & 255);
                    byte readByte2 = (byte) (this.source.readByte() & 255);
                    int zP = this.source.zP() & Integer.MAX_VALUE;
                    if (Http2.logger.isLoggable(Level.FINE)) {
                        Http2.logger.fine(FrameLogger.formatHeader(true, zP, access$300, readByte, readByte2));
                    }
                    switch (readByte) {
                        case (byte) 0:
                            readData(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 1:
                            readHeaders(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 2:
                            readPriority(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 3:
                            readRstStream(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 4:
                            readSettings(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 5:
                            readPushPromise(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 6:
                            readPing(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 7:
                            readGoAway(handler, access$300, readByte2, zP);
                            break;
                        case (byte) 8:
                            readWindowUpdate(handler, access$300, readByte2, zP);
                            break;
                        default:
                            this.source.skip((long) access$300);
                            break;
                    }
                    return true;
                }
                throw Http2.ioException("FRAME_SIZE_ERROR: %s", Integer.valueOf(access$300));
            } catch (IOException e) {
                return false;
            }
        }

        public void readConnectionPreface() {
            if (!this.client) {
                ByteString zT = this.source.zT((long) Http2.CONNECTION_PREFACE.size());
                if (Http2.logger.isLoggable(Level.FINE)) {
                    Http2.logger.fine(String.format("<< CONNECTION %s", new Object[]{zT.Ay()}));
                }
                if (!Http2.CONNECTION_PREFACE.equals(zT)) {
                    throw Http2.ioException("Expected a connection header but was %s", zT.At());
                }
            }
        }
    }

    final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final k hpackBuffer = new k();
        private final Writer hpackWriter = new Writer(this.hpackBuffer);
        private int maxFrameSize = Http2.INITIAL_MAX_FRAME_SIZE;
        private final b sink;

        Writer(b bVar, boolean z) {
            this.sink = bVar;
            this.client = z;
        }

        private void writeContinuationFrames(int i, long j) {
            while (true) {
                if ((j <= 0 ? 1 : null) == null) {
                    int min = (int) Math.min((long) this.maxFrameSize, j);
                    j -= (long) min;
                    frameHeader(i, min, Http2.TYPE_CONTINUATION, j == 0 ? (byte) 4 : (byte) 0);
                    this.sink.write(this.hpackBuffer, (long) min);
                } else {
                    return;
                }
            }
        }

        public synchronized void ackSettings(Settings settings) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.maxFrameSize = settings.getMaxFrameSize(this.maxFrameSize);
            frameHeader(0, 0, (byte) 4, (byte) 1);
            this.sink.flush();
        }

        public synchronized void close() {
            this.closed = true;
            this.sink.close();
        }

        public synchronized void connectionPreface() {
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.client) {
                if (Http2.logger.isLoggable(Level.FINE)) {
                    Http2.logger.fine(String.format(">> CONNECTION %s", new Object[]{Http2.CONNECTION_PREFACE.Ay()}));
                }
                this.sink.Aa(Http2.CONNECTION_PREFACE.AB());
                this.sink.flush();
            }
        }

        public synchronized void data(boolean z, int i, k kVar, int i2) {
            byte b = (byte) 0;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (z) {
                    b = (byte) 1;
                }
                dataFrame(i, b, kVar, i2);
            }
        }

        void dataFrame(int i, byte b, k kVar, int i2) {
            frameHeader(i, i2, (byte) 0, b);
            if (i2 > 0) {
                this.sink.write(kVar, (long) i2);
            }
        }

        public synchronized void flush() {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
        }

        void frameHeader(int i, int i2, byte b, byte b2) {
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.formatHeader(false, i, i2, b, b2));
            }
            if (i2 > this.maxFrameSize) {
                throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(this.maxFrameSize), Integer.valueOf(i2));
            } else if ((Integer.MIN_VALUE & i) == 0) {
                Http2.writeMedium(this.sink, i2);
                this.sink.Ad(b & 255);
                this.sink.Ad(b2 & 255);
                this.sink.Af(Integer.MAX_VALUE & i);
            } else {
                throw Http2.illegalArgument("reserved bit set: %s", Integer.valueOf(i));
            }
        }

        public synchronized void goAway(int i, ErrorCode errorCode, byte[] bArr) {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.httpCode != -1) {
                frameHeader(0, bArr.length + 8, Http2.TYPE_GOAWAY, (byte) 0);
                this.sink.Af(i);
                this.sink.Af(errorCode.httpCode);
                if (bArr.length > 0) {
                    this.sink.Aa(bArr);
                }
                this.sink.flush();
            } else {
                throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
            }
        }

        public synchronized void headers(int i, List list) {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(false, i, list);
        }

        void headers(boolean z, int i, List list) {
            byte b = (byte) 0;
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.hpackBuffer.size() != 0) {
                throw new IllegalStateException();
            } else {
                this.hpackWriter.writeHeaders(list);
                long size = this.hpackBuffer.size();
                int min = (int) Math.min((long) this.maxFrameSize, size);
                byte b2 = size == ((long) min) ? (byte) 4 : (byte) 0;
                if (z) {
                    b2 = (byte) (b2 | 1);
                }
                frameHeader(i, min, (byte) 1, b2);
                this.sink.write(this.hpackBuffer, (long) min);
                if (size <= ((long) min)) {
                    b = (byte) 1;
                }
                if (b == (byte) 0) {
                    writeContinuationFrames(i, size - ((long) min));
                }
            }
        }

        public int maxDataLength() {
            return this.maxFrameSize;
        }

        public synchronized void ping(boolean z, int i, int i2) {
            byte b = (byte) 0;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (z) {
                    b = (byte) 1;
                }
                frameHeader(0, 8, Http2.TYPE_PING, b);
                this.sink.Af(i);
                this.sink.Af(i2);
                this.sink.flush();
            }
        }

        public synchronized void pushPromise(int i, int i2, List list) {
            Object obj = null;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (this.hpackBuffer.size() != 0) {
                    throw new IllegalStateException();
                } else {
                    this.hpackWriter.writeHeaders(list);
                    long size = this.hpackBuffer.size();
                    int min = (int) Math.min((long) (this.maxFrameSize - 4), size);
                    frameHeader(i, min + 4, Http2.TYPE_PUSH_PROMISE, size == ((long) min) ? (byte) 4 : (byte) 0);
                    this.sink.Af(Integer.MAX_VALUE & i2);
                    this.sink.write(this.hpackBuffer, (long) min);
                    if (size <= ((long) min)) {
                        obj = 1;
                    }
                    if (obj == null) {
                        writeContinuationFrames(i, size - ((long) min));
                    }
                }
            }
        }

        public synchronized void rstStream(int i, ErrorCode errorCode) {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode != -1) {
                frameHeader(i, 4, Http2.TYPE_RST_STREAM, (byte) 0);
                this.sink.Af(errorCode.httpCode);
                this.sink.flush();
            } else {
                throw new IllegalArgumentException();
            }
        }

        public synchronized void settings(Settings settings) {
            int i = 0;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                frameHeader(0, settings.size() * 6, (byte) 4, (byte) 0);
                while (i < 10) {
                    if (settings.isSet(i)) {
                        int i2 = i != 4 ? i != 7 ? i : 4 : 3;
                        this.sink.Ae(i2);
                        this.sink.Af(settings.get(i));
                    }
                    i++;
                }
                this.sink.flush();
            }
        }

        public synchronized void synReply(boolean z, int i, List list) {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(z, i, list);
        }

        public synchronized void synStream(boolean z, boolean z2, int i, int i2, List list) {
            if (z2) {
                throw new UnsupportedOperationException();
            } else if (this.closed) {
                throw new IOException("closed");
            } else {
                headers(z, i, list);
            }
        }

        public synchronized void windowUpdate(int i, long j) {
            Object obj = 1;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (j != 0) {
                    if (j > 2147483647L) {
                        obj = null;
                    }
                    if (obj != null) {
                        frameHeader(i, 4, (byte) 8, (byte) 0);
                        this.sink.Af((int) j);
                        this.sink.flush();
                    }
                }
                throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j));
            }
        }
    }

    private static IllegalArgumentException illegalArgument(String str, Object... objArr) {
        throw new IllegalArgumentException(String.format(str, objArr));
    }

    private static IOException ioException(String str, Object... objArr) {
        throw new IOException(String.format(str, objArr));
    }

    private static int lengthWithoutPadding(int i, byte b, short s) {
        if ((b & 8) != 0) {
            short s2 = i - 1;
        }
        if (s <= s2) {
            return (short) (s2 - s);
        }
        throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(s2));
    }

    private static int readMedium(a aVar) {
        return (((aVar.readByte() & 255) << 16) | ((aVar.readByte() & 255) << 8)) | (aVar.readByte() & 255);
    }

    private static void writeMedium(b bVar, int i) {
        bVar.Ad((i >>> 16) & 255);
        bVar.Ad((i >>> 8) & 255);
        bVar.Ad(i & 255);
    }

    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public FrameReader newReader(a aVar, boolean z) {
        return new Reader(aVar, 4096, z);
    }

    public FrameWriter newWriter(b bVar, boolean z) {
        return new Writer(bVar, z);
    }
}
