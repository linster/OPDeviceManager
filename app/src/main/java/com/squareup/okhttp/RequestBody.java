package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okio.BufferedSink;
import okio.Okio;

public abstract class RequestBody {

    /* renamed from: com.squareup.okhttp.RequestBody.1 */
    static class AnonymousClass1 extends RequestBody {
        final /* synthetic */ int val$byteCount;
        final /* synthetic */ byte[] val$content;
        final /* synthetic */ MediaType val$contentType;
        final /* synthetic */ int val$offset;

        AnonymousClass1(MediaType mediaType, int i, byte[] bArr, int i2) {
            this.val$contentType = mediaType;
            this.val$byteCount = i;
            this.val$content = bArr;
            this.val$offset = i2;
        }

        public MediaType contentType() {
            return this.val$contentType;
        }

        public long contentLength() {
            return (long) this.val$byteCount;
        }

        public void writeTo(BufferedSink sink) throws IOException {
            sink.write(this.val$content, this.val$offset, this.val$byteCount);
        }
    }

    /* renamed from: com.squareup.okhttp.RequestBody.2 */
    static class AnonymousClass2 extends RequestBody {
        final /* synthetic */ MediaType val$contentType;
        final /* synthetic */ File val$file;

        AnonymousClass2(MediaType mediaType, File file) {
            this.val$contentType = mediaType;
            this.val$file = file;
        }

        public MediaType contentType() {
            return this.val$contentType;
        }

        public long contentLength() {
            return this.val$file.length();
        }

        public void writeTo(BufferedSink sink) throws IOException {
            Closeable closeable = null;
            try {
                closeable = Okio.source(this.val$file);
                sink.writeAll(closeable);
            } finally {
                Util.closeQuietly(closeable);
            }
        }
    }

    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink bufferedSink) throws IOException;

    public long contentLength() throws IOException {
        return -1;
    }

    public static RequestBody create(MediaType contentType, String content) {
        Charset charset = Util.UTF_8;
        if (contentType != null) {
            charset = contentType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                contentType = MediaType.parse(contentType + "; charset=utf-8");
            }
        }
        return create(contentType, content.getBytes(charset));
    }

    public static RequestBody create(MediaType contentType, byte[] content) {
        return create(contentType, content, 0, content.length);
    }

    public static RequestBody create(MediaType contentType, byte[] content, int offset, int byteCount) {
        if (content != null) {
            Util.checkOffsetAndCount((long) content.length, (long) offset, (long) byteCount);
            return new AnonymousClass1(contentType, byteCount, content, offset);
        }
        throw new NullPointerException("content == null");
    }

    public static RequestBody create(MediaType contentType, File file) {
        if (file != null) {
            return new AnonymousClass2(contentType, file);
        }
        throw new NullPointerException("content == null");
    }
}
