package net.oneplus.odm.data.api;

public class TokenResponse {
    String description;
    String error;
    long expires;
    String scope;
    String token;
    String type;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExpires() {
        return this.expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
