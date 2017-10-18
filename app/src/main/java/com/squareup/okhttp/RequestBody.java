package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.File;
import java.nio.charset.Charset;
import okio.b;
import okio.j;

public abstract class RequestBody {

    class AnonymousClass1 extends RequestBody {
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

        public long contentLength() {
            return (long) this.val$byteCount;
        }

        public MediaType contentType() {
            return this.val$contentType;
        }

        public void writeTo(b bVar) {
            bVar.write(this.val$content, this.val$offset, this.val$byteCount);
        }
    }

    class AnonymousClass2 extends RequestBody {
        final /* synthetic */ MediaType val$contentType;
        final /* synthetic */ File val$file;

        AnonymousClass2(MediaType mediaType, File file) {
            this.val$contentType = mediaType;
            this.val$file = file;
        }

        public long contentLength() {
            return this.val$file.length();
        }

        public MediaType contentType() {
            return this.val$contentType;
        }

        public void writeTo(b bVar) {
            Closeable closeable = null;
            try {
                closeable = j.source(this.val$file);
                bVar.Ab(closeable);
            } finally {
                Util.closeQuietly(closeable);
            }
        }
    }

    public static RequestBody create(MediaType mediaType, File file) {
        if (file != null) {
            return new AnonymousClass2(mediaType, file);
        }
        throw new NullPointerException("content == null");
    }

    public static RequestBody create(MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null) {
            charset = mediaType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                mediaType = MediaType.parse(mediaType + "; charset=utf-8");
            }
        }
        return create(mediaType, str.getBytes(charset));
    }

    public static RequestBody create(MediaType mediaType, byte[] bArr) {
        return create(mediaType, bArr, 0, bArr.length);
    }

    public static RequestBody create(MediaType mediaType, byte[] bArr, int i, int i2) {
        if (bArr != null) {
            Util.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
            return new AnonymousClass1(mediaType, i2, bArr, i);
        }
        throw new NullPointerException("content == null");
    }

    public long contentLength() {
        return -1;
    }

    public abstract MediaType contentType();

    public abstract void writeTo(b bVar);
}
