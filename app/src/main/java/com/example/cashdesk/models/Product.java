package com.example.cashdesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Product {

    @SerializedName("product_id")
    private Integer product_id;

    @SerializedName("image")
    private String image;

    @SerializedName("title")
    private String title;

    @SerializedName("sku")
    private String sku;

    @SerializedName("price")
    private String price;

    @SerializedName("quantity")
    private Integer quantity;

    @SerializedName("maxQuantity")
    private Integer maxQuantity;

    @SerializedName("is_obtainable")
    private Integer is_obtainable;

    @SerializedName("is_refused")
    private Integer is_refused;

    public Integer getIs_refused() {
        return is_refused;
    }

    public void setIs_refused(Integer is_refused) {
        this.is_refused = is_refused;
    }

    private boolean isChecked = false;

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    @SerializedName("images")
    @Expose
    private Images images = null;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIs_obtainable() {
        return is_obtainable;
    }

    public void setIs_obtainable(Integer is_obtainable) {
        this.is_obtainable = is_obtainable;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
}
