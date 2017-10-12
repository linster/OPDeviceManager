package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Okio {
    private static final Logger logger;

    /* renamed from: okio.Okio.1 */
    static class AnonymousClass1 implements Sink {
        final /* synthetic */ OutputStream val$out;
        final /* synthetic */ Timeout val$timeout;

        AnonymousClass1(Timeout timeout, OutputStream outputStream) {
            this.val$timeout = timeout;
            this.val$out = outputStream;
        }

        public void write(Buffer source, long byteCount) throws IOException {
            Util.checkOffsetAndCount(source.size, 0, byteCount);
            while (true) {
                if ((byteCount <= 0 ? 1 : null) == null) {
                    this.val$timeout.throwIfReached();
                    Segment head = source.head;
                    int toCopy = (int) Math.min(byteCount, (long) (head.limit - head.pos));
                    this.val$out.write(head.data, head.pos, toCopy);
                    head.pos += toCopy;
                    byteCount -= (long) toCopy;
                    source.size -= (long) toCopy;
                    if (head.pos == head.limit) {
                        source.head = head.pop();
                        SegmentPool.recycle(head);
                    }
                } else {
                    return;
                }
            }
        }

        public void flush() throws IOException {
            this.val$out.flush();
        }

        public void close() throws IOException {
            this.val$out.close();
        }

        public Timeout timeout() {
            return this.val$timeout;
        }

        public String toString() {
            return "sink(" + this.val$out + ")";
        }
    }

    /* renamed from: okio.Okio.2 */
    static class AnonymousClass2 implements Source {
        final /* synthetic */ InputStream val$in;
        final /* synthetic */ Timeout val$timeout;

        AnonymousClass2(Timeout timeout, InputStream inputStream) {
            this.val$timeout = timeout;
            this.val$in = inputStream;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if ((byteCount >= 0 ? 1 : 0) == 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (byteCount == 0) {
                return 0;
            } else {
                this.val$timeout.throwIfReached();
                Segment tail = sink.writableSegment(1);
                int bytesRead = this.val$in.read(tail.data, tail.limit, (int) Math.min(byteCount, (long) (2048 - tail.limit)));
                if (bytesRead == -1) {
                    return -1;
                }
                tail.limit += bytesRead;
                sink.size += (long) bytesRead;
                return (long) bytesRead;
            }
        }

        public void close() throws IOException {
            this.val$in.close();
        }

        public Timeout timeout() {
            return this.val$timeout;
        }

        public String toString() {
            return "source(" + this.val$in + ")";
        }
    }

    /* renamed from: okio.Okio.3 */
    static class AnonymousClass3 extends AsyncTimeout {
        final /* synthetic */ Socket val$socket;

        AnonymousClass3(Socket socket) {
            this.val$socket = socket;
        }

        protected void timedOut() {
            try {
                this.val$socket.close();
            } catch (Exception e) {
                Okio.logger.log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, e);
            }
        }
    }

    static {
        logger = Logger.getLogger(Okio.class);
    }

    private Okio() {
    }

    public static BufferedSource buffer(Source source) {
        if (source != null) {
            return new RealBufferedSource(source);
        }
        throw new IllegalArgumentException("source == null");
    }

    public static BufferedSink buffer(Sink sink) {
        if (sink != null) {
            return new RealBufferedSink(sink);
        }
        throw new IllegalArgumentException("sink == null");
    }

    public static Sink sink(OutputStream out) {
        return sink(out, new Timeout());
    }

    private static Sink sink(OutputStream out, Timeout timeout) {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        } else if (timeout != null) {
            return new AnonymousClass1(timeout, out);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket != null) {
            AsyncTimeout timeout = timeout(socket);
            return timeout.sink(sink(socket.getOutputStream(), timeout));
        }
        throw new IllegalArgumentException("socket == null");
    }

    public static Source source(InputStream in) {
        return source(in, new Timeout());
    }

    private static Source source(InputStream in, Timeout timeout) {
        if (in == null) {
            throw new IllegalArgumentException("in == null");
        } else if (timeout != null) {
            return new AnonymousClass2(timeout, in);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file != null) {
            return source(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Source source(Socket socket) throws IOException {
        if (socket != null) {
            AsyncTimeout timeout = timeout(socket);
            return timeout.source(source(socket.getInputStream(), timeout));
        }
        throw new IllegalArgumentException("socket == null");
    }

    private static AsyncTimeout timeout(Socket socket) {
        return new AnonymousClass3(socket);
    }
}
