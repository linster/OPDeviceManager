package net.oneplus.odm.data.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class TokenResponseDeserializer implements JsonDeserializer<TokenResponse> {
    public TokenResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TokenResponse response = new TokenResponse();
        JsonObject obj = (JsonObject) json;
        if (obj.has("error")) {
            response.setError(obj.get("error").getAsString());
            response.setDescription(obj.get("error_description").getAsString());
        } else {
            response.setToken(obj.get("access_token").getAsString());
            response.setType(obj.get("token_type").getAsString());
            response.setExpires(obj.get("expires_in").getAsLong());
            response.setScope(obj.get("scope").getAsString());
        }
        return response;
    }
}
