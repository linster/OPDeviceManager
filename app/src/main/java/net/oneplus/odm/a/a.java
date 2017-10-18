package net.oneplus.odm.a;

import android.os.Build;
import android.util.Log;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.i;
import com.google.gson.n;
import net.oneplus.odm.data.api.OnePlus;
import net.oneplus.odm.data.api.b;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

public class a {
    private Client a;
    private RestAdapter b;
    private i gson;

    public a(String str) {
        this.gson = new n().hk(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).hl(b.class, new net.oneplus.odm.data.api.a()).hm();
        this.b = new Builder().setEndpoint(str).setLogLevel(LogLevel.HEADERS).setConverter(new GsonConverter(this.gson)).build();
    }

    public a(Client client, String str) {
        try {
            this.a = client;
        } catch (Exception e) {
            Log.e("DeviceManagerUploader", e.getMessage());
        }
        if (client == null) {
            throw new Exception("SecureUploadClient is null");
        }
        this.gson = new n().hk(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).hl(b.class, new net.oneplus.odm.data.api.a()).hm();
        this.b = new Builder().setClient(this.a).setEndpoint(str).setLogLevel(LogLevel.HEADERS).setConverter(new GsonConverter(this.gson)).build();
    }

    public Response a(TypedByteArray typedByteArray, String str) {
        return ((OnePlus) this.b.create(OnePlus.class)).sendData(typedByteArray, str, String.valueOf(System.currentTimeMillis()), net.oneplus.odm.common.a.O(), net.oneplus.odm.common.a.P(), Build.DISPLAY);
    }

    public b b(String str, String str2, String str3, String str4) {
        return ((OnePlus) this.b.create(OnePlus.class)).sendTokenRequest(str, str2, str3, str4);
    }
}
