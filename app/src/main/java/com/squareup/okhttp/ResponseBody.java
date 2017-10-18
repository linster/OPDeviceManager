package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okio.a;
import okio.k;

public abstract class ResponseBody implements Closeable {
    private Reader reader;

    class AnonymousClass1 extends ResponseBody {
        final /* synthetic */ a val$content;
        final /* synthetic */ long val$contentLength;
        final /* synthetic */ MediaType val$contentType;

        AnonymousClass1(MediaType mediaType, long j, a aVar) {
            this.val$contentType = mediaType;
            this.val$contentLength = j;
            this.val$content = aVar;
        }

        public long contentLength() {
            return this.val$contentLength;
        }

        public MediaType contentType() {
            return this.val$contentType;
        }

        public a source() {
            return this.val$content;
        }
    }

    private Charset charset() {
        MediaType contentType = contentType();
        return contentType == null ? Util.UTF_8 : contentType.charset(Util.UTF_8);
    }

    public static ResponseBody create(MediaType mediaType, long j, a aVar) {
        if (aVar != null) {
            return new AnonymousClass1(mediaType, j, aVar);
        }
        throw new NullPointerException("source == null");
    }

    public static ResponseBody create(MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null) {
            charset = mediaType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                mediaType = MediaType.parse(mediaType + "; charset=utf-8");
            }
        }
        a AV = new k().AV(str, charset);
        return create(mediaType, AV.size(), AV);
    }

    public static ResponseBody create(MediaType mediaType, byte[] bArr) {
        return create(mediaType, (long) bArr.length, new k().Aa(bArr));
    }

    public final InputStream byteStream() {
        return source().zY();
    }

    public final byte[] bytes() {
        long contentLength = contentLength();
        if ((contentLength <= 2147483647L ? 1 : null) == null) {
            throw new IOException("Cannot buffer entire body for content length: " + contentLength);
        }
        Closeable source = source();
        try {
            byte[] zU = source.zU();
            if (contentLength == -1 || contentLength == ((long) zU.length)) {
                return zU;
            }
            throw new IOException("Content-Length and stream length disagree");
        } finally {
            Util.closeQuietly(source);
        }
    }

    public final Reader charStream() {
        Reader reader = this.reader;
        if (reader != null) {
            return reader;
        }
        reader = new InputStreamReader(byteStream(), charset());
        this.reader = reader;
        return reader;
    }

    public void close() {
        source().close();
    }

    public abstract long contentLength();

    public abstract MediaType contentType();

    public abstract a source();

    public final String string() {
        return new String(bytes(), charset().name());
    }
}
