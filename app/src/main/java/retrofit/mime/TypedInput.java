package retrofit.mime;

import java.io.InputStream;

public interface TypedInput {
    InputStream in();

    long length();

    String mimeType();
}
