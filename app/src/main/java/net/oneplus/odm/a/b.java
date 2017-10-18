package net.oneplus.odm.a;

import android.content.Context;
import android.util.Log;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.oneplus.odm.common.a;
import net.oneplus.odm.common.c;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedOutput;

public class b implements Client {
    private static final MediaType c = MediaType.parse("application/x-www-form-urlencoded;");
    private Context d;

    public b(Context context) {
        this.d = context;
    }

    public Response execute(Request request) {
        int i = 0;
        if (request == null) {
            Log.e("CustomClient", "Request is null.");
            return null;
        }
        RequestBody create;
        TypedOutput body = request.getBody();
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        body.writeTo(byteArrayOutputStream);
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        Map hashMap = new HashMap();
        List headers = request.getHeaders();
        for (int i2 = 0; i2 < headers.size(); i2++) {
            hashMap.put(((Header) headers.get(i2)).getName(), ((Header) headers.get(i2)).getValue());
        }
        MediaType parse = MediaType.parse("OneplusSpecialContentType");
        if (c.ab) {
            String str = new String(toByteArray);
            if (str == null || str.equals("")) {
                throw new IOException("data is null");
            }
            create = RequestBody.create(parse, a.Q(str));
        } else {
            create = RequestBody.create(parse, toByteArray);
        }
        if (create != null) {
            com.squareup.okhttp.Response execute = new OkHttpClient().newCall(new Builder().url(request.getUrl()).post(create).headers(Headers.of(hashMap)).build()).execute();
            Headers headers2 = execute.headers();
            List arrayList = new ArrayList();
            while (i < headers2.size()) {
                arrayList.add(new Header(headers2.name(i), headers2.value(i)));
                i++;
            }
            return new Response(execute.request().url().toString(), execute.code(), execute.message(), arrayList, new c(this, execute.body()));
        }
        throw new IOException("requestBody is null");
    }
}
