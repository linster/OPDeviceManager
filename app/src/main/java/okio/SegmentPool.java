package okio;

final class SegmentPool {
    static long byteCount;
    static Segment next;

    private SegmentPool() {
    }

    static Segment take() {
        synchronized (SegmentPool.class) {
            if (next == null) {
                return new Segment();
            }
            Segment result = next;
            next = result.next;
            result.next = null;
            byteCount -= 2048;
            return result;
        }
    }

    static void recycle(Segment segment) {
        Object obj = null;
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException();
        } else if (!segment.shared) {
            synchronized (SegmentPool.class) {
                if (byteCount + 2048 <= 65536) {
                    obj = 1;
                }
                if (obj == null) {
                    return;
                }
                byteCount += 2048;
                segment.next = next;
                segment.limit = 0;
                segment.pos = 0;
                next = segment;
            }
        }
    }
}
