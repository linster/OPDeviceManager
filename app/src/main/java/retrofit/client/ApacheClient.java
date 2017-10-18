package retrofit.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class ApacheClient implements Client {
    private final HttpClient client;

    class GenericEntityHttpRequest extends HttpEntityEnclosingRequestBase {
        private final String method;

        GenericEntityHttpRequest(Request request) {
            this.method = request.getMethod();
            setURI(URI.create(request.getUrl()));
            for (Header header : request.getHeaders()) {
                addHeader(new BasicHeader(header.getName(), header.getValue()));
            }
            setEntity(new TypedOutputEntity(request.getBody()));
        }

        public String getMethod() {
            return this.method;
        }
    }

    class GenericHttpRequest extends HttpRequestBase {
        private final String method;

        public GenericHttpRequest(Request request) {
            this.method = request.getMethod();
            setURI(URI.create(request.getUrl()));
            for (Header header : request.getHeaders()) {
                addHeader(new BasicHeader(header.getName(), header.getValue()));
            }
        }

        public String getMethod() {
            return this.method;
        }
    }

    class TypedOutputEntity extends AbstractHttpEntity {
        final TypedOutput typedOutput;

        TypedOutputEntity(TypedOutput typedOutput) {
            this.typedOutput = typedOutput;
            setContentType(typedOutput.mimeType());
        }

        public InputStream getContent() {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.typedOutput.writeTo(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        }

        public long getContentLength() {
            return this.typedOutput.length();
        }

        public boolean isRepeatable() {
            return true;
        }

        public boolean isStreaming() {
            return false;
        }

        public void writeTo(OutputStream outputStream) {
            this.typedOutput.writeTo(outputStream);
        }
    }

    public ApacheClient() {
        this(createDefaultClient());
    }

    public ApacheClient(HttpClient httpClient) {
        this.client = httpClient;
    }

    private static HttpClient createDefaultClient() {
        HttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 15000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
        return new DefaultHttpClient(basicHttpParams);
    }

    static HttpUriRequest createRequest(Request request) {
        return request.getBody() == null ? new GenericHttpRequest(request) : new GenericEntityHttpRequest(request);
    }

    static Response parseResponse(String str, HttpResponse httpResponse) {
        TypedInput typedInput = null;
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        String reasonPhrase = statusLine.getReasonPhrase();
        List arrayList = new ArrayList();
        String str2 = "application/octet-stream";
        for (Header header : httpResponse.getAllHeaders()) {
            String name = header.getName();
            String value = header.getValue();
            if ("Content-Type".equalsIgnoreCase(name)) {
                str2 = value;
            }
            arrayList.add(new Header(name, value));
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            typedInput = new TypedByteArray(str2, EntityUtils.toByteArray(entity));
        }
        return new Response(str, statusCode, reasonPhrase, arrayList, typedInput);
    }

    protected HttpResponse execute(HttpClient httpClient, HttpUriRequest httpUriRequest) {
        return httpClient.execute(httpUriRequest);
    }

    public Response execute(Request request) {
        return parseResponse(request.getUrl(), execute(this.client, createRequest(request)));
    }
}
