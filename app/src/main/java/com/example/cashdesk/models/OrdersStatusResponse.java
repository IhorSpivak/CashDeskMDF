package com.example.cashdesk.models;

import com.google.gson.annotations.SerializedName;

public class OrdersStatusResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
