package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class GzipSource implements Source {
    private final CRC32 crc;
    private final Inflater inflater;
    private final InflaterSource inflaterSource;
    private int section;
    private final BufferedSource source;

    public GzipSource(Source source) {
        this.section = 0;
        this.crc = new CRC32();
        if (source != null) {
            this.inflater = new Inflater(true);
            this.source = Okio.buffer(source);
            this.inflaterSource = new InflaterSource(this.source, this.inflater);
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    public long read(Buffer sink, long byteCount) throws IOException {
        int i = 0;
        if (byteCount >= 0) {
            i = 1;
        }
        if (i == 0) {
            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
        } else if (byteCount == 0) {
            return 0;
        } else {
            if (this.section == 0) {
                consumeHeader();
                this.section = 1;
            }
            if (this.section == 1) {
                long offset = sink.size;
                long result = this.inflaterSource.read(sink, byteCount);
                if (result != -1) {
                    updateCrc(sink, offset, result);
                    return result;
                }
                this.section = 2;
            }
            if (this.section == 2) {
                consumeTrailer();
                this.section = 3;
                if (!this.source.exhausted()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    private void consumeHeader() throws IOException {
        boolean fhcrc;
        long index;
        this.source.require(10);
        byte flags = this.source.buffer().getByte(3);
        if (((flags >> 1) & 1) != 1) {
            fhcrc = false;
        } else {
            fhcrc = true;
        }
        if (fhcrc) {
            updateCrc(this.source.buffer(), 0, 10);
        }
        checkEqual("ID1ID2", 8075, this.source.readShort());
        this.source.skip(8);
        if (((flags >> 2) & 1) == 1) {
            this.source.require(2);
            if (fhcrc) {
                updateCrc(this.source.buffer(), 0, 2);
            }
            int xlen = this.source.buffer().readShortLe();
            this.source.require((long) xlen);
            if (fhcrc) {
                updateCrc(this.source.buffer(), 0, (long) xlen);
            }
            this.source.skip((long) xlen);
        }
        if (((flags >> 3) & 1) == 1) {
            index = this.source.indexOf((byte) 0);
            if (index == -1) {
                throw new EOFException();
            }
            if (fhcrc) {
                updateCrc(this.source.buffer(), 0, 1 + index);
            }
            this.source.skip(1 + index);
        }
        if (((flags >> 4) & 1) == 1) {
            index = this.source.indexOf((byte) 0);
            if (index == -1) {
                throw new EOFException();
            }
            if (fhcrc) {
                updateCrc(this.source.buffer(), 0, 1 + index);
            }
            this.source.skip(1 + index);
        }
        if (fhcrc) {
            checkEqual("FHCRC", this.source.readShortLe(), (short) ((int) this.crc.getValue()));
            this.crc.reset();
        }
    }

    private void consumeTrailer() throws IOException {
        checkEqual("CRC", this.source.readIntLe(), (int) this.crc.getValue());
        checkEqual("ISIZE", this.source.readIntLe(), this.inflater.getTotalOut());
    }

    public Timeout timeout() {
        return this.source.timeout();
    }

    public void close() throws IOException {
        this.inflaterSource.close();
    }

    private void updateCrc(Buffer buffer, long offset, long byteCount) {
        Segment s = buffer.head;
        while (true) {
            if ((offset < ((long) (s.limit - s.pos)) ? 1 : null) != null) {
                break;
            }
            offset -= (long) (s.limit - s.pos);
            s = s.next;
        }
        while (true) {
            if ((byteCount <= 0 ? 1 : null) == null) {
                int pos = (int) (((long) s.pos) + offset);
                int toUpdate = (int) Math.min((long) (s.limit - pos), byteCount);
                this.crc.update(s.data, pos, toUpdate);
                byteCount -= (long) toUpdate;
                offset = 0;
                s = s.next;
            } else {
                return;
            }
        }
    }

    private void checkEqual(String name, int expected, int actual) throws IOException {
        if (actual != expected) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{name, Integer.valueOf(actual), Integer.valueOf(expected)}));
        }
    }
}
