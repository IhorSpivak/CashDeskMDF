package com.example.cashdesk.models;

import com.google.gson.annotations.SerializedName;

public class ReesonResponse {
    @SerializedName("id")
    private Integer id;

    @SerializedName("reason")
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
