package retrofit.client;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class UrlConnectionClient implements Client {
    private static final int CHUNK_SIZE = 4096;

    class TypedInputStream implements TypedInput {
        private final long length;
        private final String mimeType;
        private final InputStream stream;

        private TypedInputStream(String str, long j, InputStream inputStream) {
            this.mimeType = str;
            this.length = j;
            this.stream = inputStream;
        }

        public InputStream in() {
            return this.stream;
        }

        public long length() {
            return this.length;
        }

        public String mimeType() {
            return this.mimeType;
        }
    }

    public Response execute(Request request) {
        HttpURLConnection openConnection = openConnection(request);
        prepareRequest(openConnection, request);
        return readResponse(openConnection);
    }

    protected HttpURLConnection openConnection(Request request) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(20000);
        return httpURLConnection;
    }

    void prepareRequest(HttpURLConnection httpURLConnection, Request request) {
        httpURLConnection.setRequestMethod(request.getMethod());
        httpURLConnection.setDoInput(true);
        for (Header header : request.getHeaders()) {
            httpURLConnection.addRequestProperty(header.getName(), header.getValue());
        }
        TypedOutput body = request.getBody();
        if (body != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", body.mimeType());
            long length = body.length();
            if (length != -1) {
                httpURLConnection.setFixedLengthStreamingMode((int) length);
                httpURLConnection.addRequestProperty("Content-Length", String.valueOf(length));
            } else {
                httpURLConnection.setChunkedStreamingMode(CHUNK_SIZE);
            }
            body.writeTo(httpURLConnection.getOutputStream());
        }
    }

    Response readResponse(HttpURLConnection httpURLConnection) {
        int responseCode = httpURLConnection.getResponseCode();
        String responseMessage = httpURLConnection.getResponseMessage();
        if (responseMessage == null) {
            responseMessage = "";
        }
        String str = responseMessage;
        List arrayList = new ArrayList();
        for (Entry entry : httpURLConnection.getHeaderFields().entrySet()) {
            String str2 = (String) entry.getKey();
            for (String responseMessage2 : (List) entry.getValue()) {
                arrayList.add(new Header(str2, responseMessage2));
            }
        }
        return new Response(httpURLConnection.getURL().toString(), responseCode, str, arrayList, new TypedInputStream(httpURLConnection.getContentType(), (long) httpURLConnection.getContentLength(), responseCode < 400 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()));
    }
}
