package okio;

final class Segment {
    final byte[] data;
    int limit;
    Segment next;
    boolean owner;
    int pos;
    Segment prev;
    boolean shared;

    Segment() {
        this.data = new byte[2048];
        this.owner = true;
        this.shared = false;
    }

    Segment(Segment shareFrom) {
        this(shareFrom.data, shareFrom.pos, shareFrom.limit);
        shareFrom.shared = true;
    }

    Segment(byte[] data, int pos, int limit) {
        this.data = data;
        this.pos = pos;
        this.limit = limit;
        this.owner = false;
        this.shared = true;
    }

    public Segment pop() {
        Segment result = this.next == this ? null : this.next;
        this.prev.next = this.next;
        this.next.prev = this.prev;
        this.next = null;
        this.prev = null;
        return result;
    }

    public Segment push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        this.next.prev = segment;
        this.next = segment;
        return segment;
    }

    public Segment split(int byteCount) {
        if (byteCount > 0 && byteCount <= this.limit - this.pos) {
            Segment prefix = new Segment(this);
            prefix.limit = prefix.pos + byteCount;
            this.pos += byteCount;
            this.prev.push(prefix);
            return prefix;
        }
        throw new IllegalArgumentException();
    }

    public void compact() {
        int i = 0;
        if (this.prev == this) {
            throw new IllegalStateException();
        } else if (this.prev.owner) {
            int byteCount = this.limit - this.pos;
            int i2 = 2048 - this.prev.limit;
            if (!this.prev.shared) {
                i = this.prev.pos;
            }
            if (byteCount <= i2 + i) {
                writeTo(this.prev, byteCount);
                pop();
                SegmentPool.recycle(this);
            }
        }
    }

    public void writeTo(Segment sink, int byteCount) {
        if (sink.owner) {
            if (sink.limit + byteCount > 2048) {
                if (sink.shared) {
                    throw new IllegalArgumentException();
                } else if ((sink.limit + byteCount) - sink.pos <= 2048) {
                    System.arraycopy(sink.data, sink.pos, sink.data, 0, sink.limit - sink.pos);
                    sink.limit -= sink.pos;
                    sink.pos = 0;
                } else {
                    throw new IllegalArgumentException();
                }
            }
            System.arraycopy(this.data, this.pos, sink.data, sink.limit, byteCount);
            sink.limit += byteCount;
            this.pos += byteCount;
            return;
        }
        throw new IllegalArgumentException();
    }
}
