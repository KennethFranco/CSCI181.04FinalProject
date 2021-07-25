package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Products extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String product_name;
    private Double product_price;

    private String shop_name;
    private String shop_uuid;

    public Products() {}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_uuid() {
        return shop_uuid;
    }

    public void setShop_uuid(String shop_uuid) {
        this.shop_uuid = shop_uuid;
    }

    public Products(String uuid, String username, Double price) {
        this.uuid = uuid;
        this.product_name = username;
        this.product_price = price;
    }

    @Override
    public String toString() {
        return "Shops [" +
                "UUID = " + uuid + "'\'" +
                ", Shop Name = " + shop_name + "'\'" +
                ", Product Name = " + product_name + "]" +
                ", Product Price = " + product_price + "'\'";
    }
}
