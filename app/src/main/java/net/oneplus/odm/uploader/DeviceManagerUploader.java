package net.oneplus.odm.uploader;

import android.os.Build;
import android.util.Log;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.oneplus.odm.common.Utils;
import net.oneplus.odm.data.api.OnePlus;
import net.oneplus.odm.data.api.TokenResponse;
import net.oneplus.odm.data.api.TokenResponseDeserializer;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

public class DeviceManagerUploader {
    private Gson gson;
    private Client mClient;
    private RestAdapter mRestAdapter;

    public DeviceManagerUploader(Client client, String url) throws Exception {
        try {
            this.mClient = client;
        } catch (Exception e) {
            Log.e("DeviceManagerUploader", e.getMessage());
        }
        if (client == null) {
            throw new Exception("SecureUploadClient is null");
        }
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapter(TokenResponse.class, new TokenResponseDeserializer()).create();
        this.mRestAdapter = new Builder().setClient(this.mClient).setEndpoint(url).setLogLevel(LogLevel.HEADERS).setConverter(new GsonConverter(this.gson)).build();
    }

    public DeviceManagerUploader(String url) {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapter(TokenResponse.class, new TokenResponseDeserializer()).create();
        this.mRestAdapter = new Builder().setEndpoint(url).setLogLevel(LogLevel.HEADERS).setConverter(new GsonConverter(this.gson)).build();
    }

    public Response postData(TypedByteArray data, String token) {
        return ((OnePlus) this.mRestAdapter.create(OnePlus.class)).sendData(data, token, String.valueOf(System.currentTimeMillis()), Utils.getTimezone(), Utils.getUUID(), Build.DISPLAY);
    }

    public TokenResponse requestToken(String id, String type, String secret, String scope) {
        return ((OnePlus) this.mRestAdapter.create(OnePlus.class)).sendTokenRequest(id, type, secret, scope);
    }
}
