package net.oneplus.odm.uploader;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.oneplus.odm.common.Def;
import net.oneplus.odm.common.Utils;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class CustomClient implements Client {
    private static final MediaType TYPE;
    private Context mContext;

    private class TransferBodyToStreamTypedInput implements TypedInput {
        ResponseBody mBody;

        TransferBodyToStreamTypedInput(ResponseBody body) {
            this.mBody = body;
        }

        public String mimeType() {
            return null;
        }

        public long length() {
            return 0;
        }

        public InputStream in() throws IOException {
            return this.mBody.byteStream();
        }
    }

    static {
        TYPE = MediaType.parse("application/x-www-form-urlencoded;");
    }

    public CustomClient(Context context) {
        this.mContext = context;
    }

    public Response execute(Request originRequest) throws IOException {
        if (originRequest == null) {
            Log.e("CustomClient", "Request is null.");
            return null;
        }
        int i;
        RequestBody requestBody;
        TypedOutput originRequestBodyTypeOutput = originRequest.getBody();
        OutputStream originRequestBodyOutputStream = new ByteArrayOutputStream();
        originRequestBodyTypeOutput.writeTo(originRequestBodyOutputStream);
        byte[] originRequestBodyBytes = originRequestBodyOutputStream.toByteArray();
        Map okHeader = new HashMap();
        List<Header> originHeaders = originRequest.getHeaders();
        for (i = 0; i < originHeaders.size(); i++) {
            okHeader.put(((Header) originHeaders.get(i)).getName(), ((Header) originHeaders.get(i)).getValue());
        }
        MediaType contentType = MediaType.parse("OneplusSpecialContentType");
        if (Def.USE_GZIP) {
            String dataStr = new String(originRequestBodyBytes);
            if (dataStr == null || dataStr.equals("")) {
                throw new IOException("data is null");
            }
            requestBody = RequestBody.create(contentType, Utils.compress(dataStr));
        } else {
            requestBody = RequestBody.create(contentType, originRequestBodyBytes);
        }
        if (requestBody != null) {
            com.squareup.okhttp.Response originResponse = new OkHttpClient().newCall(new Builder().url(originRequest.getUrl()).post(requestBody).headers(Headers.of(okHeader)).build()).execute();
            Headers originResponseHeaders = originResponse.headers();
            List<Header> newResponseHeaders = new ArrayList();
            for (i = 0; i < originResponseHeaders.size(); i++) {
                newResponseHeaders.add(new Header(originResponseHeaders.name(i), originResponseHeaders.value(i)));
            }
            return new Response(originResponse.request().url().toString(), originResponse.code(), originResponse.message(), newResponseHeaders, new TransferBodyToStreamTypedInput(originResponse.body()));
        }
        throw new IOException("requestBody is null");
    }
}
