package okio;

import java.io.Closeable;
import java.io.Flushable;

public interface n extends Closeable, Flushable {
    void close();

    void flush();

    g timeout();

    void write(k kVar, long j);
}
