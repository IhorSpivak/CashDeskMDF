package com.example.cashdesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Order {

    @SerializedName("status")
    private String status;

    @SerializedName("partner_name")
    private String partner_name;

    @SerializedName("is_today")
    private Boolean is_today;

    @SerializedName("can_cancelled")
    private Boolean can_cancelled;

    @SerializedName("order_id")
    private Integer order_id;

    @SerializedName("discount")
    private Integer discount;

    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("departure_date")
    private String departure_date;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("price")
    private String price;

    @SerializedName("amount")
    private String amount;

    @SerializedName("discount_title")
    private String discount_title;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("time_warning")
    private String time_warning;

    @SerializedName("products")
    @Expose
    private List<Product> products = null;


    @SerializedName("comments")
    @Expose
    private List<Comments> comments = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount_title() {
        return discount_title;
    }

    public void setDiscount_title(String discount_title) {
        this.discount_title = discount_title;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public Boolean getIs_today() {
        return is_today;
    }

    public void setIs_today(Boolean is_today) {
        this.is_today = is_today;
    }

    public Boolean getCan_cancelled() {
        return can_cancelled;
    }

    public void setCan_cancelled(Boolean can_cancelled) {
        this.can_cancelled = can_cancelled;
    }

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTime_warning() {
        return time_warning;
    }

    public void setTime_warning(String time_warning) {
        this.time_warning = time_warning;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }
}
