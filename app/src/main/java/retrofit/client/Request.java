package retrofit.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.mime.TypedOutput;

public final class Request {
    private final TypedOutput body;
    private final List<Header> headers;
    private final String method;
    private final String url;

    public Request(String method, String url, List<Header> headers, TypedOutput body) {
        if (method == null) {
            throw new NullPointerException("Method must not be null.");
        } else if (url != null) {
            this.method = method;
            this.url = url;
            if (headers != null) {
                this.headers = Collections.unmodifiableList(new ArrayList(headers));
            } else {
                this.headers = Collections.emptyList();
            }
            this.body = body;
        } else {
            throw new NullPointerException("URL must not be null.");
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getUrl() {
        return this.url;
    }

    public List<Header> getHeaders() {
        return this.headers;
    }

    public TypedOutput getBody() {
        return this.body;
    }
}
