package net.oneplus.odm.data.api;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.mime.TypedByteArray;

public interface OnePlus {
    @Headers({"Content-Type: OneplusSpecialContentType"})
    @POST("/cloud/push")
    Response sendData(@Body TypedByteArray typedByteArray, @Header("access_token") String str, @Header("createTime") String str2, @Header("timeZone") String str3, @Header("requestId") String str4, @Header("version") String str5);

    @FormUrlEncoded
    @POST("/oauth/token")
    b sendTokenRequest(@Field(encodeValue = false, value = "client_id") String str, @Field(encodeValue = false, value = "grant_type") String str2, @Field(encodeValue = false, value = "client_secret") String str3, @Field(encodeValue = false, value = "scope") String str4);
}
