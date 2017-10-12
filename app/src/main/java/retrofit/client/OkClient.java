package retrofit.client;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class OkClient implements Client {
    private final OkHttpClient client;

    /* renamed from: retrofit.client.OkClient.1 */
    static class AnonymousClass1 extends RequestBody {
        final /* synthetic */ TypedOutput val$body;
        final /* synthetic */ MediaType val$mediaType;

        AnonymousClass1(MediaType mediaType, TypedOutput typedOutput) {
            this.val$mediaType = mediaType;
            this.val$body = typedOutput;
        }

        public MediaType contentType() {
            return this.val$mediaType;
        }

        public void writeTo(BufferedSink sink) throws IOException {
            this.val$body.writeTo(sink.outputStream());
        }

        public long contentLength() {
            return this.val$body.length();
        }
    }

    /* renamed from: retrofit.client.OkClient.2 */
    static class AnonymousClass2 implements TypedInput {
        final /* synthetic */ ResponseBody val$body;

        AnonymousClass2(ResponseBody responseBody) {
            this.val$body = responseBody;
        }

        public String mimeType() {
            MediaType mediaType = this.val$body.contentType();
            if (mediaType != null) {
                return mediaType.toString();
            }
            return null;
        }

        public long length() {
            return this.val$body.contentLength();
        }

        public InputStream in() throws IOException {
            return this.val$body.byteStream();
        }
    }

    private static OkHttpClient generateDefaultOkHttp() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(20000, TimeUnit.MILLISECONDS);
        return client;
    }

    public OkClient() {
        this(generateDefaultOkHttp());
    }

    public OkClient(OkHttpClient client) {
        if (client != null) {
            this.client = client;
            return;
        }
        throw new NullPointerException("client == null");
    }

    public Response execute(Request request) throws IOException {
        return parseResponse(this.client.newCall(createRequest(request)).execute());
    }

    static Request createRequest(Request request) {
        Builder builder = new Builder().url(request.getUrl()).method(request.getMethod(), createRequestBody(request.getBody()));
        List<Header> headers = request.getHeaders();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            Header header = (Header) headers.get(i);
            String value = header.getValue();
            if (value == null) {
                value = "";
            }
            builder.addHeader(header.getName(), value);
        }
        return builder.build();
    }

    static Response parseResponse(Response response) {
        return new Response(response.request().urlString(), response.code(), response.message(), createHeaders(response.headers()), createResponseBody(response.body()));
    }

    private static RequestBody createRequestBody(TypedOutput body) {
        if (body != null) {
            return new AnonymousClass1(MediaType.parse(body.mimeType()), body);
        }
        return null;
    }

    private static TypedInput createResponseBody(ResponseBody body) {
        if (body.contentLength() == 0) {
            return null;
        }
        return new AnonymousClass2(body);
    }

    private static List<Header> createHeaders(Headers headers) {
        int size = headers.size();
        List<Header> headerList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            headerList.add(new Header(headers.name(i), headers.value(i)));
        }
        return headerList;
    }
}
