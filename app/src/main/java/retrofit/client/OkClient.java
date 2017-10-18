package retrofit.client;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okio.b;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class OkClient implements Client {
    private final OkHttpClient client;

    class AnonymousClass1 extends RequestBody {
        final /* synthetic */ TypedOutput val$body;
        final /* synthetic */ MediaType val$mediaType;

        AnonymousClass1(MediaType mediaType, TypedOutput typedOutput) {
            this.val$mediaType = mediaType;
            this.val$body = typedOutput;
        }

        public long contentLength() {
            return this.val$body.length();
        }

        public MediaType contentType() {
            return this.val$mediaType;
        }

        public void writeTo(b bVar) {
            this.val$body.writeTo(bVar.Ak());
        }
    }

    class AnonymousClass2 implements TypedInput {
        final /* synthetic */ ResponseBody val$body;

        AnonymousClass2(ResponseBody responseBody) {
            this.val$body = responseBody;
        }

        public InputStream in() {
            return this.val$body.byteStream();
        }

        public long length() {
            return this.val$body.contentLength();
        }

        public String mimeType() {
            MediaType contentType = this.val$body.contentType();
            return contentType != null ? contentType.toString() : null;
        }
    }

    public OkClient() {
        this(generateDefaultOkHttp());
    }

    public OkClient(OkHttpClient okHttpClient) {
        if (okHttpClient != null) {
            this.client = okHttpClient;
            return;
        }
        throw new NullPointerException("client == null");
    }

    private static List createHeaders(Headers headers) {
        int size = headers.size();
        List arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new Header(headers.name(i), headers.value(i)));
        }
        return arrayList;
    }

    static Request createRequest(Request request) {
        Builder method = new Builder().url(request.getUrl()).method(request.getMethod(), createRequestBody(request.getBody()));
        List headers = request.getHeaders();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            Header header = (Header) headers.get(i);
            String value = header.getValue();
            if (value == null) {
                value = "";
            }
            method.addHeader(header.getName(), value);
        }
        return method.build();
    }

    private static RequestBody createRequestBody(TypedOutput typedOutput) {
        return typedOutput != null ? new AnonymousClass1(MediaType.parse(typedOutput.mimeType()), typedOutput) : null;
    }

    private static TypedInput createResponseBody(ResponseBody responseBody) {
        return responseBody.contentLength() == 0 ? null : new AnonymousClass2(responseBody);
    }

    private static OkHttpClient generateDefaultOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(20000, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    static Response parseResponse(Response response) {
        return new Response(response.request().urlString(), response.code(), response.message(), createHeaders(response.headers()), createResponseBody(response.body()));
    }

    public Response execute(Request request) {
        return parseResponse(this.client.newCall(createRequest(request)).execute());
    }
}
