package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okio.ByteString;
import okio.b;
import okio.k;

public final class MultipartBuilder {
    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
    private static final byte[] COLONSPACE = new byte[]{(byte) 58, (byte) 32};
    private static final byte[] CRLF = new byte[]{(byte) 13, (byte) 10};
    private static final byte[] DASHDASH = new byte[]{(byte) 45, (byte) 45};
    public static final MediaType DIGEST = MediaType.parse("multipart/digest");
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
    private final ByteString boundary;
    private final List partBodies;
    private final List partHeaders;
    private MediaType type;

    final class MultipartRequestBody extends RequestBody {
        private final ByteString boundary;
        private long contentLength = -1;
        private final MediaType contentType;
        private final List partBodies;
        private final List partHeaders;

        public MultipartRequestBody(MediaType mediaType, ByteString byteString, List list, List list2) {
            if (mediaType != null) {
                this.boundary = byteString;
                this.contentType = MediaType.parse(mediaType + "; boundary=" + byteString.At());
                this.partHeaders = Util.immutableList(list);
                this.partBodies = Util.immutableList(list2);
                return;
            }
            throw new NullPointerException("type == null");
        }

        private long writeOrCountBytes(b bVar, boolean z) {
            k kVar;
            long j = 0;
            if (z) {
                k kVar2 = new k();
                kVar = kVar2;
                Object obj = kVar2;
            } else {
                kVar = null;
            }
            int size = this.partHeaders.size();
            int i = 0;
            while (i < size) {
                Headers headers = (Headers) this.partHeaders.get(i);
                RequestBody requestBody = (RequestBody) this.partBodies.get(i);
                bVar.Aa(MultipartBuilder.DASHDASH);
                bVar.zZ(this.boundary);
                bVar.Aa(MultipartBuilder.CRLF);
                if (headers != null) {
                    int size2 = headers.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        bVar.Ac(headers.name(i2)).Aa(MultipartBuilder.COLONSPACE).Ac(headers.value(i2)).Aa(MultipartBuilder.CRLF);
                    }
                }
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    bVar.Ac("Content-Type: ").Ac(contentType.toString()).Aa(MultipartBuilder.CRLF);
                }
                long contentLength = requestBody.contentLength();
                if (contentLength != -1) {
                    bVar.Ac("Content-Length: ").Ag(contentLength).Aa(MultipartBuilder.CRLF);
                } else if (z) {
                    kVar.clear();
                    return -1;
                }
                bVar.Aa(MultipartBuilder.CRLF);
                if (z) {
                    contentLength += j;
                } else {
                    ((RequestBody) this.partBodies.get(i)).writeTo(bVar);
                    contentLength = j;
                }
                bVar.Aa(MultipartBuilder.CRLF);
                i++;
                j = contentLength;
            }
            bVar.Aa(MultipartBuilder.DASHDASH);
            bVar.zZ(this.boundary);
            bVar.Aa(MultipartBuilder.DASHDASH);
            bVar.Aa(MultipartBuilder.CRLF);
            if (z) {
                j += kVar.size();
                kVar.clear();
            }
            return j;
        }

        public long contentLength() {
            long j = this.contentLength;
            if (j != -1) {
                return j;
            }
            j = writeOrCountBytes(null, true);
            this.contentLength = j;
            return j;
        }

        public MediaType contentType() {
            return this.contentType;
        }

        public void writeTo(b bVar) {
            writeOrCountBytes(bVar, false);
        }
    }

    public MultipartBuilder() {
        this(UUID.randomUUID().toString());
    }

    public MultipartBuilder(String str) {
        this.type = MIXED;
        this.partHeaders = new ArrayList();
        this.partBodies = new ArrayList();
        this.boundary = ByteString.As(str);
    }

    private static StringBuilder appendQuotedString(StringBuilder stringBuilder, String str) {
        stringBuilder.append('\"');
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            switch (charAt) {
                case '\n':
                    stringBuilder.append("%0A");
                    break;
                case '\r':
                    stringBuilder.append("%0D");
                    break;
                case '\"':
                    stringBuilder.append("%22");
                    break;
                default:
                    stringBuilder.append(charAt);
                    break;
            }
        }
        stringBuilder.append('\"');
        return stringBuilder;
    }

    public MultipartBuilder addFormDataPart(String str, String str2) {
        return addFormDataPart(str, null, RequestBody.create(null, str2));
    }

    public MultipartBuilder addFormDataPart(String str, String str2, RequestBody requestBody) {
        if (str != null) {
            StringBuilder stringBuilder = new StringBuilder("form-data; name=");
            appendQuotedString(stringBuilder, str);
            if (str2 != null) {
                stringBuilder.append("; filename=");
                appendQuotedString(stringBuilder, str2);
            }
            return addPart(Headers.of("Content-Disposition", stringBuilder.toString()), requestBody);
        }
        throw new NullPointerException("name == null");
    }

    public MultipartBuilder addPart(Headers headers, RequestBody requestBody) {
        if (requestBody == null) {
            throw new NullPointerException("body == null");
        } else if (headers != null && headers.get("Content-Type") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Type");
        } else if (headers == null || headers.get("Content-Length") == null) {
            this.partHeaders.add(headers);
            this.partBodies.add(requestBody);
            return this;
        } else {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
    }

    public MultipartBuilder addPart(RequestBody requestBody) {
        return addPart(null, requestBody);
    }

    public RequestBody build() {
        if (!this.partHeaders.isEmpty()) {
            return new MultipartRequestBody(this.type, this.boundary, this.partHeaders, this.partBodies);
        }
        throw new IllegalStateException("Multipart body must have at least one part.");
    }

    public MultipartBuilder type(MediaType mediaType) {
        if (mediaType == null) {
            throw new NullPointerException("type == null");
        } else if (mediaType.type().equals("multipart")) {
            this.type = mediaType;
            return this;
        } else {
            throw new IllegalArgumentException("multipart != " + mediaType);
        }
    }
}
