package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Products extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String product_name;
    private String product_price;
    private String product_description;

    private String shop_name;

    private String imagePath;

    public String getProduct_description() {
        return product_description;
    }

    @Override
    public String toString() {
        return "Products{" +
                "uuid='" + uuid + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_price='" + product_price + '\'' +
                ", product_description='" + product_description + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_uuid='" + shop_uuid + '\'' +
                '}';
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

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

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
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

    public Products(String uuid, String product_name, String product_price, String shop_name, String shop_uuid, String product_description) {
        this.uuid = uuid;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_description = product_description;
        this.shop_name = shop_name;
        this.shop_uuid = shop_uuid;
    }

}
