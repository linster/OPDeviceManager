package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.Deflater;
import okio.ByteString;
import okio.a;
import okio.b;
import okio.j;
import okio.k;
import okio.z;

public final class Spdy3 implements Variant {
    static final byte[] DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.";
    static final int FLAG_FIN = 1;
    static final int FLAG_UNIDIRECTIONAL = 2;
    static final int TYPE_DATA = 0;
    static final int TYPE_GOAWAY = 7;
    static final int TYPE_HEADERS = 8;
    static final int TYPE_PING = 6;
    static final int TYPE_RST_STREAM = 3;
    static final int TYPE_SETTINGS = 4;
    static final int TYPE_SYN_REPLY = 2;
    static final int TYPE_SYN_STREAM = 1;
    static final int TYPE_WINDOW_UPDATE = 9;
    static final int VERSION = 3;

    final class Reader implements FrameReader {
        private final boolean client;
        private final NameValueBlockReader headerBlockReader = new NameValueBlockReader(this.source);
        private final a source;

        Reader(a aVar, boolean z) {
            this.source = aVar;
            this.client = z;
        }

        private static IOException ioException(String str, Object... objArr) {
            throw new IOException(String.format(str, objArr));
        }

        private void readGoAway(Handler handler, int i, int i2) {
            if (i2 == Spdy3.TYPE_HEADERS) {
                int zP = this.source.zP() & Integer.MAX_VALUE;
                ErrorCode fromSpdyGoAway = ErrorCode.fromSpdyGoAway(this.source.zP());
                if (fromSpdyGoAway != null) {
                    handler.goAway(zP, fromSpdyGoAway, ByteString.uD);
                    return;
                } else {
                    throw ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(r1));
                }
            }
            throw ioException("TYPE_GOAWAY length: %d != 8", Integer.valueOf(i2));
        }

        private void readHeaders(Handler handler, int i, int i2) {
            handler.headers(false, false, this.source.zP() & Integer.MAX_VALUE, -1, this.headerBlockReader.readNameValueBlock(i2 - 4), HeadersMode.SPDY_HEADERS);
        }

        private void readPing(Handler handler, int i, int i2) {
            boolean z = true;
            if (i2 == Spdy3.TYPE_SETTINGS) {
                int zP = this.source.zP();
                if (this.client != ((zP & 1) != 1 ? Spdy3.TYPE_DATA : true)) {
                    z = false;
                }
                handler.ping(z, zP, Spdy3.TYPE_DATA);
                return;
            }
            throw ioException("TYPE_PING length: %d != 4", Integer.valueOf(i2));
        }

        private void readRstStream(Handler handler, int i, int i2) {
            if (i2 == Spdy3.TYPE_HEADERS) {
                int zP = this.source.zP() & Integer.MAX_VALUE;
                ErrorCode fromSpdy3Rst = ErrorCode.fromSpdy3Rst(this.source.zP());
                if (fromSpdy3Rst != null) {
                    handler.rstStream(zP, fromSpdy3Rst);
                    return;
                } else {
                    throw ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(r1));
                }
            }
            throw ioException("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(i2));
        }

        private void readSettings(Handler handler, int i, int i2) {
            boolean z = false;
            int zP = this.source.zP();
            if (i2 == (zP * Spdy3.TYPE_HEADERS) + Spdy3.TYPE_SETTINGS) {
                Settings settings = new Settings();
                for (int i3 = Spdy3.TYPE_DATA; i3 < zP; i3++) {
                    int zP2 = this.source.zP();
                    int i4 = (-16777216 & zP2) >>> 24;
                    settings.set(zP2 & 16777215, i4, this.source.zP());
                }
                if ((i & 1) != 0) {
                    z = true;
                }
                handler.settings(z, settings);
                return;
            }
            throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(i2), Integer.valueOf(zP));
        }

        private void readSynReply(Handler handler, int i, int i2) {
            handler.headers(false, (i & 1) != 0, this.source.zP() & Integer.MAX_VALUE, -1, this.headerBlockReader.readNameValueBlock(i2 - 4), HeadersMode.SPDY_REPLY);
        }

        private void readSynStream(Handler handler, int i, int i2) {
            boolean z = true;
            int zP = this.source.zP() & Integer.MAX_VALUE;
            int zP2 = this.source.zP() & Integer.MAX_VALUE;
            this.source.zN();
            List readNameValueBlock = this.headerBlockReader.readNameValueBlock(i2 - 10);
            boolean z2 = (i & 1) != 0;
            if ((i & 2) == 0) {
                z = false;
            }
            handler.headers(z, z2, zP, zP2, readNameValueBlock, HeadersMode.SPDY_SYN_STREAM);
        }

        private void readWindowUpdate(Handler handler, int i, int i2) {
            if (i2 == Spdy3.TYPE_HEADERS) {
                int zP = this.source.zP() & Integer.MAX_VALUE;
                long zP2 = (long) (this.source.zP() & Integer.MAX_VALUE);
                if (zP2 == 0) {
                    throw ioException("windowSizeIncrement was 0", Long.valueOf(zP2));
                } else {
                    handler.windowUpdate(zP, zP2);
                    return;
                }
            }
            throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(i2));
        }

        public void close() {
            this.headerBlockReader.close();
        }

        public boolean nextFrame(Handler handler) {
            boolean z = false;
            try {
                int zP = this.source.zP();
                int zP2 = this.source.zP();
                int i = (-16777216 & zP2) >>> 24;
                zP2 &= 16777215;
                int i2;
                if ((Integer.MIN_VALUE & zP) != 0) {
                    i2 = (2147418112 & zP) >>> 16;
                    int i3 = 65535 & zP;
                    if (i2 == 3) {
                        switch (i3) {
                            case 1:
                                readSynStream(handler, i, zP2);
                                return true;
                            case 2:
                                readSynReply(handler, i, zP2);
                                return true;
                            case 3:
                                readRstStream(handler, i, zP2);
                                return true;
                            case Spdy3.TYPE_SETTINGS /*4*/:
                                readSettings(handler, i, zP2);
                                return true;
                            case Spdy3.TYPE_PING /*6*/:
                                readPing(handler, i, zP2);
                                return true;
                            case Spdy3.TYPE_GOAWAY /*7*/:
                                readGoAway(handler, i, zP2);
                                return true;
                            case Spdy3.TYPE_HEADERS /*8*/:
                                readHeaders(handler, i, zP2);
                                return true;
                            case Spdy3.TYPE_WINDOW_UPDATE /*9*/:
                                readWindowUpdate(handler, i, zP2);
                                return true;
                            default:
                                this.source.skip((long) zP2);
                                return true;
                        }
                    }
                    throw new ProtocolException("version != 3: " + i2);
                }
                i2 = Integer.MAX_VALUE & zP;
                if ((i & 1) != 0) {
                    z = true;
                }
                handler.data(z, i2, this.source, zP2);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        public void readConnectionPreface() {
        }
    }

    final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final k headerBlockBuffer = new k();
        private final b headerBlockOut;
        private final b sink;

        Writer(b bVar, boolean z) {
            this.sink = bVar;
            this.client = z;
            Deflater deflater = new Deflater();
            deflater.setDictionary(Spdy3.DICTIONARY);
            this.headerBlockOut = j.AF(new z(this.headerBlockBuffer, deflater));
        }

        private void writeNameValueBlockToBuffer(List list) {
            if (this.headerBlockBuffer.size() != 0) {
                throw new IllegalStateException();
            }
            this.headerBlockOut.Af(list.size());
            int size = list.size();
            for (int i = Spdy3.TYPE_DATA; i < size; i++) {
                ByteString byteString = ((Header) list.get(i)).name;
                this.headerBlockOut.Af(byteString.size());
                this.headerBlockOut.zZ(byteString);
                byteString = ((Header) list.get(i)).value;
                this.headerBlockOut.Af(byteString.size());
                this.headerBlockOut.zZ(byteString);
            }
            this.headerBlockOut.flush();
        }

        public void ackSettings(Settings settings) {
        }

        public synchronized void close() {
            this.closed = true;
            Util.closeAll(this.sink, this.headerBlockOut);
        }

        public synchronized void connectionPreface() {
        }

        public synchronized void data(boolean z, int i, k kVar, int i2) {
            int i3 = Spdy3.TYPE_DATA;
            synchronized (this) {
                if (z) {
                    i3 = 1;
                }
                sendDataFrame(i, i3, kVar, i2);
            }
        }

        public synchronized void flush() {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
        }

        public synchronized void goAway(int i, ErrorCode errorCode, byte[] bArr) {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyGoAwayCode != -1) {
                this.sink.Af(-2147287033);
                this.sink.Af(Spdy3.TYPE_HEADERS);
                this.sink.Af(i);
                this.sink.Af(errorCode.spdyGoAwayCode);
                this.sink.flush();
            } else {
                throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
            }
        }

        public synchronized void headers(int i, List list) {
            if (this.closed) {
                throw new IOException("closed");
            }
            writeNameValueBlockToBuffer(list);
            int size = (int) (this.headerBlockBuffer.size() + 4);
            this.sink.Af(-2147287032);
            this.sink.Af((size & 16777215) | Spdy3.TYPE_DATA);
            this.sink.Af(Integer.MAX_VALUE & i);
            this.sink.Ab(this.headerBlockBuffer);
        }

        public int maxDataLength() {
            return 16383;
        }

        public synchronized void ping(boolean z, int i, int i2) {
            boolean z2 = false;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (this.client != ((i & 1) != 1 ? Spdy3.TYPE_DATA : true)) {
                    z2 = true;
                }
                if (z == z2) {
                    this.sink.Af(-2147287034);
                    this.sink.Af(Spdy3.TYPE_SETTINGS);
                    this.sink.Af(i);
                    this.sink.flush();
                } else {
                    throw new IllegalArgumentException("payload != reply");
                }
            }
        }

        public void pushPromise(int i, int i2, List list) {
        }

        public synchronized void rstStream(int i, ErrorCode errorCode) {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode != -1) {
                this.sink.Af(-2147287037);
                this.sink.Af(Spdy3.TYPE_HEADERS);
                this.sink.Af(Integer.MAX_VALUE & i);
                this.sink.Af(errorCode.spdyRstCode);
                this.sink.flush();
            } else {
                throw new IllegalArgumentException();
            }
        }

        void sendDataFrame(int i, int i2, k kVar, int i3) {
            Object obj = null;
            if (this.closed) {
                throw new IOException("closed");
            }
            if (((long) i3) <= 16777215) {
                obj = 1;
            }
            if (obj == null) {
                throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + i3);
            }
            this.sink.Af(Integer.MAX_VALUE & i);
            this.sink.Af(((i2 & 255) << 24) | (16777215 & i3));
            if (i3 > 0) {
                this.sink.write(kVar, (long) i3);
            }
        }

        public synchronized void settings(Settings settings) {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                int size = settings.size();
                int i = (size * Spdy3.TYPE_HEADERS) + Spdy3.TYPE_SETTINGS;
                this.sink.Af(-2147287036);
                this.sink.Af((i & 16777215) | Spdy3.TYPE_DATA);
                this.sink.Af(size);
                for (int i2 = Spdy3.TYPE_DATA; i2 <= 10; i2++) {
                    if (settings.isSet(i2)) {
                        this.sink.Af(((settings.flags(i2) & 255) << 24) | (i2 & 16777215));
                        this.sink.Af(settings.get(i2));
                    }
                }
                this.sink.flush();
            }
        }

        public synchronized void synReply(boolean z, int i, List list) {
            int i2 = Spdy3.TYPE_DATA;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                writeNameValueBlockToBuffer(list);
                if (z) {
                    i2 = 1;
                }
                int size = (int) (this.headerBlockBuffer.size() + 4);
                this.sink.Af(-2147287038);
                this.sink.Af(((i2 & 255) << 24) | (size & 16777215));
                this.sink.Af(Integer.MAX_VALUE & i);
                this.sink.Ab(this.headerBlockBuffer);
                this.sink.flush();
            }
        }

        public synchronized void synStream(boolean z, boolean z2, int i, int i2, List list) {
            int i3 = 1;
            int i4 = Spdy3.TYPE_DATA;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                writeNameValueBlockToBuffer(list);
                int size = (int) (this.headerBlockBuffer.size() + 10);
                if (!z) {
                    i3 = Spdy3.TYPE_DATA;
                }
                if (z2) {
                    i4 = 2;
                }
                i4 |= i3;
                this.sink.Af(-2147287039);
                this.sink.Af(((i4 & 255) << 24) | (size & 16777215));
                this.sink.Af(i & Integer.MAX_VALUE);
                this.sink.Af(i2 & Integer.MAX_VALUE);
                this.sink.Ae(Spdy3.TYPE_DATA);
                this.sink.Ab(this.headerBlockBuffer);
                this.sink.flush();
            }
        }

        public synchronized void windowUpdate(int i, long j) {
            Object obj = null;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (j != 0) {
                    if (j <= 2147483647L) {
                        obj = 1;
                    }
                    if (obj != null) {
                        this.sink.Af(-2147287031);
                        this.sink.Af(Spdy3.TYPE_HEADERS);
                        this.sink.Af(i);
                        this.sink.Af((int) j);
                        this.sink.flush();
                    }
                }
                throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + j);
            }
        }
    }

    static {
        try {
            Charset charset = Util.UTF_8;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public Protocol getProtocol() {
        return Protocol.SPDY_3;
    }

    public FrameReader newReader(a aVar, boolean z) {
        return new Reader(aVar, z);
    }

    public FrameWriter newWriter(b bVar, boolean z) {
        return new Writer(bVar, z);
    }
}
