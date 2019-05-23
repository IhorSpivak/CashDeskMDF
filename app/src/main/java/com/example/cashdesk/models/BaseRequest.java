package com.example.cashdesk.models;

import com.example.cashdesk.helper.Const;
import com.google.gson.annotations.SerializedName;

public class BaseRequest<T> {

    public BaseRequest(T body) {
        this.body = body;
//        this.action = action;

    }

    public BaseRequest() {
//        this.action = action;
//        this.body = (T) new StoreRequest();
//        init();
    }


//    @SerializedName("action")
//    private String action;

    @SerializedName("access_key")
    private final String auth_key = Const.ACCESS_KEY;

//    @SerializedName("auth_key")
//    private final String user_auth_key = PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER);


    @SerializedName("body")
    private T body;


    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

//    public void setAction(String action) {
//        this.action = action;
//    }




}

