package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.j;
import okio.n;

public final class SpdyTransport implements Transport {
    private static final List HTTP_2_PROHIBITED_HEADERS = Util.immutableList(ByteString.As("connection"), ByteString.As("host"), ByteString.As("keep-alive"), ByteString.As("proxy-connection"), ByteString.As("te"), ByteString.As("transfer-encoding"), ByteString.As("encoding"), ByteString.As("upgrade"));
    private static final List SPDY_3_PROHIBITED_HEADERS = Util.immutableList(ByteString.As("connection"), ByteString.As("host"), ByteString.As("keep-alive"), ByteString.As("proxy-connection"), ByteString.As("transfer-encoding"));
    private final HttpEngine httpEngine;
    private final SpdyConnection spdyConnection;
    private SpdyStream stream;

    public SpdyTransport(HttpEngine httpEngine, SpdyConnection spdyConnection) {
        this.httpEngine = httpEngine;
        this.spdyConnection = spdyConnection;
    }

    private static boolean isProhibitedHeader(Protocol protocol, ByteString byteString) {
        if (protocol == Protocol.SPDY_3) {
            return SPDY_3_PROHIBITED_HEADERS.contains(byteString);
        }
        if (protocol == Protocol.HTTP_2) {
            return HTTP_2_PROHIBITED_HEADERS.contains(byteString);
        }
        throw new AssertionError(protocol);
    }

    private static String joinOnNull(String str, String str2) {
        return '\u0000' + str2;
    }

    public static Builder readNameValueBlock(List list, Protocol protocol) {
        String str = null;
        String str2 = "HTTP/1.1";
        Headers.Builder builder = new Headers.Builder();
        builder.set(OkHeaders.SELECTED_PROTOCOL, protocol.toString());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ByteString byteString = ((Header) list.get(i)).name;
            String At = ((Header) list.get(i)).value.At();
            int i2 = 0;
            while (i2 < At.length()) {
                int indexOf = At.indexOf(0, i2);
                if (indexOf == -1) {
                    indexOf = At.length();
                }
                String substring = At.substring(i2, indexOf);
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    str = substring;
                } else if (byteString.equals(Header.VERSION)) {
                    str2 = substring;
                } else if (!isProhibitedHeader(protocol, byteString)) {
                    builder.add(byteString.At(), substring);
                }
                i2 = indexOf + 1;
            }
        }
        if (str != null) {
            StatusLine parse = StatusLine.parse(str2 + " " + str);
            return new Builder().protocol(protocol).code(parse.code).message(parse.message).headers(builder.build());
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    public static List writeNameValueBlock(Request request, Protocol protocol, String str) {
        Headers headers = request.headers();
        List arrayList = new ArrayList(headers.size() + 10);
        arrayList.add(new Header(Header.TARGET_METHOD, request.method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(request.url())));
        String hostHeader = HttpEngine.hostHeader(request.url());
        if (Protocol.SPDY_3 == protocol) {
            arrayList.add(new Header(Header.VERSION, str));
            arrayList.add(new Header(Header.TARGET_HOST, hostHeader));
        } else if (Protocol.HTTP_2 != protocol) {
            throw new AssertionError();
        } else {
            arrayList.add(new Header(Header.TARGET_AUTHORITY, hostHeader));
        }
        arrayList.add(new Header(Header.TARGET_SCHEME, request.url().getProtocol()));
        Set linkedHashSet = new LinkedHashSet();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            ByteString As = ByteString.As(headers.name(i).toLowerCase(Locale.US));
            String value = headers.value(i);
            if (!isProhibitedHeader(protocol, As) && !As.equals(Header.TARGET_METHOD) && !As.equals(Header.TARGET_PATH) && !As.equals(Header.TARGET_SCHEME) && !As.equals(Header.TARGET_AUTHORITY) && !As.equals(Header.TARGET_HOST) && !As.equals(Header.VERSION)) {
                if (!linkedHashSet.add(As)) {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        if (((Header) arrayList.get(i2)).name.equals(As)) {
                            arrayList.set(i2, new Header(As, joinOnNull(((Header) arrayList.get(i2)).value.At(), value)));
                            break;
                        }
                    }
                } else {
                    arrayList.add(new Header(As, value));
                }
            }
        }
        return arrayList;
    }

    public boolean canReuseConnection() {
        return true;
    }

    public n createRequestBody(Request request, long j) {
        return this.stream.getSink();
    }

    public void disconnect(HttpEngine httpEngine) {
        if (this.stream != null) {
            this.stream.close(ErrorCode.CANCEL);
        }
    }

    public void finishRequest() {
        this.stream.getSink().close();
    }

    public ResponseBody openResponseBody(Response response) {
        return new RealResponseBody(response.headers(), j.AE(this.stream.getSource()));
    }

    public Builder readResponseHeaders() {
        return readNameValueBlock(this.stream.getResponseHeaders(), this.spdyConnection.getProtocol());
    }

    public void releaseConnectionOnIdle() {
    }

    public void writeRequestBody(RetryableSink retryableSink) {
        retryableSink.writeToSocket(this.stream.getSink());
    }

    public void writeRequestHeaders(Request request) {
        if (this.stream == null) {
            this.httpEngine.writingRequestHeaders();
            this.stream = this.spdyConnection.newStream(writeNameValueBlock(request, this.spdyConnection.getProtocol(), RequestLine.version(this.httpEngine.getConnection().getProtocol())), this.httpEngine.permitsRequestBody(), true);
            this.stream.readTimeout().timeout((long) this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
        }
    }
}
