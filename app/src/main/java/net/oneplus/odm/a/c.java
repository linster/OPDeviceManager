package net.oneplus.odm.a;

import com.squareup.okhttp.ResponseBody;
import java.io.InputStream;
import retrofit.mime.TypedInput;

class c implements TypedInput {
    ResponseBody e;
    final /* synthetic */ b f;

    c(b bVar, ResponseBody responseBody) {
        this.f = bVar;
        this.e = responseBody;
    }

    public InputStream in() {
        return this.e.byteStream();
    }

    public long length() {
        return 0;
    }

    public String mimeType() {
        return null;
    }
}
