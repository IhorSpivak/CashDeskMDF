package com.example.cashdesk.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("auth_key")
    private String auth_key;

    @SerializedName("locale")
    private String locale;

    @SerializedName("type")
    private String typeSHop;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getTypeSHop() {
        return typeSHop;
    }

    public void setTypeSHop(String typeSHop) {
        this.typeSHop = typeSHop;
    }
}
