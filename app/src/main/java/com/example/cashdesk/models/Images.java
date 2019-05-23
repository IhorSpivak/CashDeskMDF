package com.example.cashdesk.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Images {
    @SerializedName("small")
    private String small;

    @SerializedName("big")
    private String big;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }
}
