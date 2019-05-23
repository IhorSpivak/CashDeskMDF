package com.example.cashdesk.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Comments {

    @SerializedName("user_id")
    private Integer user_id;

    @SerializedName("author")
    private String author;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("message")
    private String message;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
