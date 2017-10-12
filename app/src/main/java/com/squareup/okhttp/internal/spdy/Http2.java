package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Http2 implements Variant {
    private static final ByteString CONNECTION_PREFACE;
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
    private static final Logger logger;

    static final class ContinuationSource implements Source {
        byte flags;
        int left;
        int length;
        short padding;
        private final BufferedSource source;
        int streamId;

        public ContinuationSource(BufferedSource source) {
            this.source = source;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            while (this.left == 0) {
                this.source.skip((long) this.padding);
                this.padding = (short) 0;
                if ((this.flags & 4) != 0) {
                    return -1;
                }
                readContinuationHeader();
            }
            long read = this.source.read(sink, Math.min(byteCount, (long) this.left));
            if (read == -1) {
                return -1;
            }
            this.left = (int) (((long) this.left) - read);
            return read;
        }

        public Timeout timeout() {
            return this.source.timeout();
        }

        public void close() throws IOException {
        }

        private void readContinuationHeader() throws IOException {
            int previousStreamId = this.streamId;
            int access$300 = Http2.readMedium(this.source);
            this.left = access$300;
            this.length = access$300;
            byte type = (byte) (this.source.readByte() & 255);
            this.flags = (byte) ((byte) (this.source.readByte() & 255));
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, type, this.flags));
            }
            this.streamId = this.source.readInt() & Integer.MAX_VALUE;
            if (type != 9) {
                throw Http2.ioException("%s != TYPE_CONTINUATION", Byte.valueOf(type));
            } else if (this.streamId != previousStreamId) {
                throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }
    }

    static final class FrameLogger {
        private static final String[] BINARY;
        private static final String[] FLAGS;
        private static final String[] TYPES;

        FrameLogger() {
        }

        static String formatHeader(boolean inbound, int streamId, int length, byte type, byte flags) {
            String formattedType;
            String str;
            if (type >= TYPES.length) {
                formattedType = String.format("0x%02x", new Object[]{Byte.valueOf(type)});
            } else {
                formattedType = TYPES[type];
            }
            String formattedFlags = formatFlags(type, flags);
            String str2 = "%s 0x%08x %5d %-13s %s";
            Object[] objArr = new Object[5];
            if (inbound) {
                str = "<<";
            } else {
                str = ">>";
            }
            objArr[0] = str;
            objArr[1] = Integer.valueOf(streamId);
            objArr[2] = Integer.valueOf(length);
            objArr[3] = formattedType;
            objArr[4] = formattedFlags;
            return String.format(str2, objArr);
        }

        static String formatFlags(byte type, byte flags) {
            if (flags == null) {
                return "";
            }
            switch (type) {
                case (byte) 2:
                case (byte) 3:
                case (byte) 7:
                case (byte) 8:
                    return BINARY[flags];
                case (byte) 4:
                case (byte) 6:
                    return flags != 1 ? BINARY[flags] : "ACK";
                default:
                    String result = flags >= FLAGS.length ? BINARY[flags] : FLAGS[flags];
                    if (type == 5 && (flags & 4) != 0) {
                        return result.replace("HEADERS", "PUSH_PROMISE");
                    }
                    if (type == null && (flags & 32) != 0) {
                        return result.replace("PRIORITY", "COMPRESSED");
                    }
                    return result;
            }
        }

        static {
            int i = 4;
            int i2 = 1;
            int i3 = 0;
            TYPES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
            FLAGS = new String[64];
            BINARY = new String[256];
            int i4 = 0;
            while (true) {
                String[] strArr;
                if (i4 >= BINARY.length) {
                    int i5;
                    int prefixFlag;
                    int i6;
                    FLAGS[i3] = "";
                    FLAGS[i2] = "END_STREAM";
                    int[] prefixFlags = new int[i2];
                    prefixFlags[i3] = i2;
                    FLAGS[8] = "PADDED";
                    int length = prefixFlags.length;
                    for (i5 = i3; i5 < length; i5++) {
                        prefixFlag = prefixFlags[i5];
                        String[] strArr2 = FLAGS;
                        i6 = prefixFlag | 8;
                        StringBuilder stringBuilder = new StringBuilder();
                        String str = FLAGS[prefixFlag];
                        str = "|PADDED";
                        strArr2[i6] = stringBuilder;
                    }
                    FLAGS[i] = "END_HEADERS";
                    FLAGS[32] = "PRIORITY";
                    FLAGS[36] = "END_HEADERS|PRIORITY";
                    int[] frameFlags = new int[]{i, 32, 36};
                    int length2 = frameFlags.length;
                    for (length = i3; length < length2; length++) {
                        int frameFlag = frameFlags[length];
                        i6 = prefixFlags.length;
                        for (i5 = i3; i5 < i6; i5++) {
                            prefixFlag = prefixFlags[i5];
                            String[] strArr3 = FLAGS;
                            int i7 = prefixFlag | frameFlag;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            String str2 = FLAGS[prefixFlag];
                            str2 = FLAGS[frameFlag];
                            strArr3[i7] = stringBuilder2;
                            strArr3 = FLAGS;
                            i7 = (prefixFlag | frameFlag) | 8;
                            i2 = new StringBuilder();
                            str2 = FLAGS[prefixFlag];
                            str2 = FLAGS[frameFlag];
                            i = "|PADDED";
                            strArr3[i7] = i2;
                        }
                    }
                    i4 = 0;
                    while (i4 < FLAGS.length) {
                        if (FLAGS[i4] == null) {
                            strArr = FLAGS;
                            i3 = BINARY[i4];
                            strArr[i4] = i3;
                        }
                        i4++;
                    }
                }
                strArr = BINARY;
                Object[] objArr = new Object[i2];
                objArr[i3] = Integer.toBinaryString(i4);
                strArr[i4] = String.format("%8s", objArr);
                i4++;
            }
        }
    }

    static final class Reader implements FrameReader {
        private final boolean client;
        private final ContinuationSource continuation;
        final Reader hpackReader;
        private final BufferedSource source;

        Reader(BufferedSource source, int headerTableSize, boolean client) {
            this.source = source;
            this.client = client;
            this.continuation = new ContinuationSource(this.source);
            this.hpackReader = new Reader(headerTableSize, this.continuation);
        }

        public void readConnectionPreface() throws IOException {
            if (!this.client) {
                ByteString connectionPreface = this.source.readByteString((long) Http2.CONNECTION_PREFACE.size());
                if (Http2.logger.isLoggable(Level.FINE)) {
                    Http2.logger.fine(String.format("<< CONNECTION %s", new Object[]{connectionPreface.hex()}));
                }
                if (!Http2.CONNECTION_PREFACE.equals(connectionPreface)) {
                    throw Http2.ioException("Expected a connection header but was %s", connectionPreface.utf8());
                }
            }
        }

        public boolean nextFrame(Handler handler) throws IOException {
            try {
                this.source.require(9);
                int length = Http2.readMedium(this.source);
                if (length >= 0 && length <= Http2.INITIAL_MAX_FRAME_SIZE) {
                    byte type = (byte) (this.source.readByte() & 255);
                    byte flags = (byte) (this.source.readByte() & 255);
                    int streamId = this.source.readInt() & Integer.MAX_VALUE;
                    if (Http2.logger.isLoggable(Level.FINE)) {
                        Http2.logger.fine(FrameLogger.formatHeader(true, streamId, length, type, flags));
                    }
                    switch (type) {
                        case (byte) 0:
                            readData(handler, length, flags, streamId);
                            break;
                        case (byte) 1:
                            readHeaders(handler, length, flags, streamId);
                            break;
                        case (byte) 2:
                            readPriority(handler, length, flags, streamId);
                            break;
                        case (byte) 3:
                            readRstStream(handler, length, flags, streamId);
                            break;
                        case (byte) 4:
                            readSettings(handler, length, flags, streamId);
                            break;
                        case (byte) 5:
                            readPushPromise(handler, length, flags, streamId);
                            break;
                        case (byte) 6:
                            readPing(handler, length, flags, streamId);
                            break;
                        case (byte) 7:
                            readGoAway(handler, length, flags, streamId);
                            break;
                        case (byte) 8:
                            readWindowUpdate(handler, length, flags, streamId);
                            break;
                        default:
                            this.source.skip((long) length);
                            break;
                    }
                    return true;
                }
                throw Http2.ioException("FRAME_SIZE_ERROR: %s", Integer.valueOf(length));
            } catch (IOException e) {
                return false;
            }
        }

        private void readHeaders(Handler handler, int length, byte flags, int streamId) throws IOException {
            if (streamId != 0) {
                boolean endStream;
                if ((flags & 1) == 0) {
                    endStream = false;
                } else {
                    endStream = true;
                }
                short padding = (flags & 8) == 0 ? (short) 0 : (short) (this.source.readByte() & 255);
                if ((flags & 32) != 0) {
                    readPriority(handler, streamId);
                    length -= 5;
                }
                handler.headers(false, endStream, streamId, -1, readHeaderBlock(Http2.lengthWithoutPadding(length, flags, padding), padding, flags, streamId), HeadersMode.HTTP_20_HEADERS);
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }

        private List<Header> readHeaderBlock(int length, short padding, byte flags, int streamId) throws IOException {
            ContinuationSource continuationSource = this.continuation;
            this.continuation.left = length;
            continuationSource.length = length;
            this.continuation.padding = (short) padding;
            this.continuation.flags = (byte) flags;
            this.continuation.streamId = streamId;
            this.hpackReader.readHeaders();
            return this.hpackReader.getAndResetHeaderList();
        }

        private void readData(Handler handler, int length, byte flags, int streamId) throws IOException {
            boolean inFinished;
            boolean gzipped;
            if ((flags & 1) == 0) {
                inFinished = false;
            } else {
                inFinished = true;
            }
            if ((flags & 32) == 0) {
                gzipped = false;
            } else {
                gzipped = true;
            }
            if (gzipped) {
                throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            short padding = (flags & 8) == 0 ? (short) 0 : (short) (this.source.readByte() & 255);
            handler.data(inFinished, streamId, this.source, Http2.lengthWithoutPadding(length, flags, padding));
            this.source.skip((long) padding);
        }

        private void readPriority(Handler handler, int length, byte flags, int streamId) throws IOException {
            if (length != 5) {
                throw Http2.ioException("TYPE_PRIORITY length: %d != 5", Integer.valueOf(length));
            } else if (streamId != 0) {
                readPriority(handler, streamId);
            } else {
                throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
        }

        private void readPriority(Handler handler, int streamId) throws IOException {
            boolean exclusive = false;
            int w1 = this.source.readInt();
            if ((Integer.MIN_VALUE & w1) != 0) {
                exclusive = true;
            }
            handler.priority(streamId, w1 & Integer.MAX_VALUE, (this.source.readByte() & 255) + 1, exclusive);
        }

        private void readRstStream(Handler handler, int length, byte flags, int streamId) throws IOException {
            if (length != 4) {
                throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(length));
            } else if (streamId != 0) {
                ErrorCode errorCode = ErrorCode.fromHttp2(this.source.readInt());
                if (errorCode != null) {
                    handler.rstStream(streamId, errorCode);
                } else {
                    throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
                }
            } else {
                throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
        }

        private void readSettings(Handler handler, int length, byte flags, int streamId) throws IOException {
            if (streamId != 0) {
                throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((flags & 1) == 0) {
                if (length % 6 == 0) {
                    Settings settings = new Settings();
                    for (int i = 0; i < length; i += 6) {
                        short id = this.source.readShort();
                        int value = this.source.readInt();
                        switch (id) {
                            case (short) 1:
                            case (short) 6:
                                break;
                            case (short) 2:
                                if (!(value == 0 || value == 1)) {
                                    throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                                }
                            case (short) 3:
                                id = (short) 4;
                                break;
                            case (short) 4:
                                id = (short) 7;
                                if (value >= 0) {
                                    break;
                                }
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                            case (short) 5:
                                if (value >= Http2.INITIAL_MAX_FRAME_SIZE && value <= 16777215) {
                                    break;
                                }
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(value));
                            default:
                                throw Http2.ioException("PROTOCOL_ERROR invalid settings id: %s", Short.valueOf(id));
                        }
                        settings.set(id, 0, value);
                    }
                    handler.settings(false, settings);
                    if (settings.getHeaderTableSize() >= 0) {
                        this.hpackReader.headerTableSizeSetting(settings.getHeaderTableSize());
                    }
                    return;
                }
                throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(length));
            } else if (length == 0) {
                handler.ackSettings();
            } else {
                throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
            }
        }

        private void readPushPromise(Handler handler, int length, byte flags, int streamId) throws IOException {
            short padding = (short) 0;
            if (streamId != 0) {
                if ((flags & 8) != 0) {
                    padding = (short) (this.source.readByte() & 255);
                }
                handler.pushPromise(streamId, this.source.readInt() & Integer.MAX_VALUE, readHeaderBlock(Http2.lengthWithoutPadding(length - 4, flags, padding), padding, flags, streamId));
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }

        private void readPing(Handler handler, int length, byte flags, int streamId) throws IOException {
            boolean ack = false;
            if (length != 8) {
                throw Http2.ioException("TYPE_PING length != 8: %s", Integer.valueOf(length));
            } else if (streamId == 0) {
                int payload1 = this.source.readInt();
                int payload2 = this.source.readInt();
                if ((flags & 1) != 0) {
                    ack = true;
                }
                handler.ping(ack, payload1, payload2);
            } else {
                throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
            }
        }

        private void readGoAway(Handler handler, int length, byte flags, int streamId) throws IOException {
            if (length < 8) {
                throw Http2.ioException("TYPE_GOAWAY length < 8: %s", Integer.valueOf(length));
            } else if (streamId == 0) {
                int lastStreamId = this.source.readInt();
                int opaqueDataLength = length - 8;
                ErrorCode errorCode = ErrorCode.fromHttp2(this.source.readInt());
                if (errorCode != null) {
                    ByteString debugData = ByteString.EMPTY;
                    if (opaqueDataLength > 0) {
                        debugData = this.source.readByteString((long) opaqueDataLength);
                    }
                    handler.goAway(lastStreamId, errorCode, debugData);
                    return;
                }
                throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
            } else {
                throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            }
        }

        private void readWindowUpdate(Handler handler, int length, byte flags, int streamId) throws IOException {
            if (length == 4) {
                long increment = ((long) this.source.readInt()) & 2147483647L;
                if (increment == 0) {
                    throw Http2.ioException("windowSizeIncrement was 0", Long.valueOf(increment));
                } else {
                    handler.windowUpdate(streamId, increment);
                    return;
                }
            }
            throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(length));
        }

        public void close() throws IOException {
            this.source.close();
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer hpackBuffer;
        private final Writer hpackWriter;
        private int maxFrameSize;
        private final BufferedSink sink;

        Writer(BufferedSink sink, boolean client) {
            this.sink = sink;
            this.client = client;
            this.hpackBuffer = new Buffer();
            this.hpackWriter = new Writer(this.hpackBuffer);
            this.maxFrameSize = Http2.INITIAL_MAX_FRAME_SIZE;
        }

        public synchronized void flush() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
        }

        public synchronized void ackSettings(Settings peerSettings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.maxFrameSize = peerSettings.getMaxFrameSize(this.maxFrameSize);
            frameHeader(0, 0, Http2.TYPE_SETTINGS, Http2.TYPE_HEADERS);
            this.sink.flush();
        }

        public synchronized void connectionPreface() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.client) {
                if (Http2.logger.isLoggable(Level.FINE)) {
                    Http2.logger.fine(String.format(">> CONNECTION %s", new Object[]{Http2.CONNECTION_PREFACE.hex()}));
                }
                this.sink.write(Http2.CONNECTION_PREFACE.toByteArray());
                this.sink.flush();
                return;
            } else {
                return;
            }
        }

        public synchronized void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock) throws IOException {
            if (inFinished) {
                throw new UnsupportedOperationException();
            } else if (this.closed) {
                throw new IOException("closed");
            } else {
                headers(outFinished, streamId, headerBlock);
            }
        }

        public synchronized void synReply(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(outFinished, streamId, headerBlock);
        }

        public synchronized void headers(int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(false, streamId, headerBlock);
        }

        public synchronized void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) throws IOException {
            Object obj = null;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (this.hpackBuffer.size() != 0) {
                    throw new IllegalStateException();
                } else {
                    byte flags;
                    this.hpackWriter.writeHeaders(requestHeaders);
                    long byteCount = this.hpackBuffer.size();
                    int length = (int) Math.min((long) (this.maxFrameSize - 4), byteCount);
                    if (byteCount == ((long) length)) {
                        flags = Http2.TYPE_SETTINGS;
                    } else {
                        flags = Http2.TYPE_DATA;
                    }
                    frameHeader(streamId, length + 4, Http2.TYPE_PUSH_PROMISE, flags);
                    this.sink.writeInt(Integer.MAX_VALUE & promisedStreamId);
                    this.sink.write(this.hpackBuffer, (long) length);
                    if (byteCount <= ((long) length)) {
                        obj = 1;
                    }
                    if (obj == null) {
                        writeContinuationFrames(streamId, byteCount - ((long) length));
                    }
                }
            }
        }

        void headers(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
            Object obj = null;
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.hpackBuffer.size() != 0) {
                throw new IllegalStateException();
            } else {
                byte flags;
                this.hpackWriter.writeHeaders(headerBlock);
                long byteCount = this.hpackBuffer.size();
                int length = (int) Math.min((long) this.maxFrameSize, byteCount);
                if (byteCount == ((long) length)) {
                    flags = Http2.TYPE_SETTINGS;
                } else {
                    flags = Http2.TYPE_DATA;
                }
                if (outFinished) {
                    flags = (byte) (flags | 1);
                }
                frameHeader(streamId, length, Http2.TYPE_HEADERS, flags);
                this.sink.write(this.hpackBuffer, (long) length);
                if (byteCount <= ((long) length)) {
                    obj = Http2.TYPE_HEADERS;
                }
                if (obj == null) {
                    writeContinuationFrames(streamId, byteCount - ((long) length));
                }
            }
        }

        private void writeContinuationFrames(int streamId, long byteCount) throws IOException {
            while (true) {
                Object obj;
                if (byteCount <= 0) {
                    obj = 1;
                } else {
                    obj = null;
                }
                if (obj == null) {
                    byte b;
                    int length = (int) Math.min((long) this.maxFrameSize, byteCount);
                    byteCount -= (long) length;
                    if (byteCount == 0) {
                        b = Http2.TYPE_SETTINGS;
                    } else {
                        b = Http2.TYPE_DATA;
                    }
                    frameHeader(streamId, length, Http2.TYPE_CONTINUATION, b);
                    this.sink.write(this.hpackBuffer, (long) length);
                } else {
                    return;
                }
            }
        }

        public synchronized void rstStream(int streamId, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode != -1) {
                frameHeader(streamId, 4, Http2.TYPE_RST_STREAM, Http2.TYPE_DATA);
                this.sink.writeInt(errorCode.httpCode);
                this.sink.flush();
            } else {
                throw new IllegalArgumentException();
            }
        }

        public int maxDataLength() {
            return this.maxFrameSize;
        }

        public synchronized void data(boolean outFinished, int streamId, Buffer source, int byteCount) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            byte flags = Http2.TYPE_DATA;
            if (outFinished) {
                flags = (byte) 1;
            }
            dataFrame(streamId, flags, source, byteCount);
        }

        void dataFrame(int streamId, byte flags, Buffer buffer, int byteCount) throws IOException {
            frameHeader(streamId, byteCount, Http2.TYPE_DATA, flags);
            if (byteCount > 0) {
                this.sink.write(buffer, (long) byteCount);
            }
        }

        public synchronized void settings(Settings settings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, settings.size() * 6, Http2.TYPE_SETTINGS, Http2.TYPE_DATA);
            for (int i = 0; i < 10; i++) {
                if (settings.isSet(i)) {
                    int id = i;
                    if (id == 4) {
                        id = 3;
                    } else if (id == 7) {
                        id = 4;
                    }
                    this.sink.writeShort(id);
                    this.sink.writeInt(settings.get(i));
                }
            }
            this.sink.flush();
        }

        public synchronized void ping(boolean ack, int payload1, int payload2) throws IOException {
            byte flags = Http2.TYPE_DATA;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (ack) {
                    flags = Http2.TYPE_HEADERS;
                }
                frameHeader(0, 8, Http2.TYPE_PING, flags);
                this.sink.writeInt(payload1);
                this.sink.writeInt(payload2);
                this.sink.flush();
            }
        }

        public synchronized void goAway(int lastGoodStreamId, ErrorCode errorCode, byte[] debugData) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.httpCode != -1) {
                frameHeader(0, debugData.length + 8, Http2.TYPE_GOAWAY, Http2.TYPE_DATA);
                this.sink.writeInt(lastGoodStreamId);
                this.sink.writeInt(errorCode.httpCode);
                if (debugData.length > 0) {
                    this.sink.write(debugData);
                }
                this.sink.flush();
            } else {
                throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
            }
        }

        public synchronized void windowUpdate(int streamId, long windowSizeIncrement) throws IOException {
            Object obj = 1;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (windowSizeIncrement != 0) {
                    if (windowSizeIncrement > 2147483647L) {
                        obj = null;
                    }
                    if (obj != null) {
                        frameHeader(streamId, 4, Http2.TYPE_WINDOW_UPDATE, Http2.TYPE_DATA);
                        this.sink.writeInt((int) windowSizeIncrement);
                        this.sink.flush();
                    }
                }
                throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(windowSizeIncrement));
            }
        }

        public synchronized void close() throws IOException {
            this.closed = true;
            this.sink.close();
        }

        void frameHeader(int streamId, int length, byte type, byte flags) throws IOException {
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.formatHeader(false, streamId, length, type, flags));
            }
            if (length > this.maxFrameSize) {
                throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(this.maxFrameSize), Integer.valueOf(length));
            } else if ((Integer.MIN_VALUE & streamId) == 0) {
                Http2.writeMedium(this.sink, length);
                this.sink.writeByte(type & 255);
                this.sink.writeByte(flags & 255);
                this.sink.writeInt(Integer.MAX_VALUE & streamId);
            } else {
                throw Http2.illegalArgument("reserved bit set: %s", Integer.valueOf(streamId));
            }
        }
    }

    static {
        logger = Logger.getLogger(FrameLogger.class);
        CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    }

    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public FrameReader newReader(BufferedSource source, boolean client) {
        return new Reader(source, 4096, client);
    }

    public FrameWriter newWriter(BufferedSink sink, boolean client) {
        return new Writer(sink, client);
    }

    private static IllegalArgumentException illegalArgument(String message, Object... args) {
        throw new IllegalArgumentException(String.format(message, args));
    }

    private static IOException ioException(String message, Object... args) throws IOException {
        throw new IOException(String.format(message, args));
    }

    private static int lengthWithoutPadding(int length, byte flags, short padding) throws IOException {
        if ((flags & 8) != 0) {
            short length2 = length - 1;
        }
        if (padding <= length2) {
            return (short) (length2 - padding);
        }
        throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(padding), Integer.valueOf(length2));
    }

    private static int readMedium(BufferedSource source) throws IOException {
        return (((source.readByte() & 255) << 16) | ((source.readByte() & 255) << 8)) | (source.readByte() & 255);
    }

    private static void writeMedium(BufferedSink sink, int i) throws IOException {
        sink.writeByte((i >>> 16) & 255);
        sink.writeByte((i >>> 8) & 255);
        sink.writeByte(i & 255);
    }
}
