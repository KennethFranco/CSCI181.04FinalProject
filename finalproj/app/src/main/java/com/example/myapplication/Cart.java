package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cart  extends RealmObject {
    @PrimaryKey
    private String uuid;

    private String product_name;
    private String shop_name;
    private int quantity;
    private Double individual_price;
    private Double total_price;
    private String user_uuid;
    private String product_uuid;

    public Cart(String uuid, String product_name, String shop_name, int quantity, Double individual_price, Double total_price, String user_uuid, String product_uuid) {
        this.uuid = uuid;
        this.product_name = product_name;
        this.shop_name = shop_name;
        this.quantity = quantity;
        this.individual_price = individual_price;
        this.total_price = total_price;
        this.user_uuid = user_uuid;
        this.product_uuid = product_uuid;
    }

    public String getProduct_uuid() {
        return product_uuid;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "uuid='" + uuid + '\'' +
                ", product_name='" + product_name + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", quantity=" + quantity +
                ", individual_price=" + individual_price +
                ", total_price=" + total_price +
                ", user_uuid='" + user_uuid + '\'' +
                ", product_uuid='" + product_uuid + '\'' +
                '}';
    }

    public void setProduct_uuid(String product_uuid) {
        this.product_uuid = product_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public Cart(String uuid, String product_name, String shop_name, int quantity, Double individual_price, Double total_price, String user_uuid) {
        this.uuid = uuid;
        this.product_name = product_name;
        this.shop_name = shop_name;
        this.quantity = quantity;
        this.individual_price = individual_price;
        this.total_price = total_price;
        this.user_uuid = user_uuid;
    }

    public Cart() {}

    public Cart(String uuid, String product_name, String shop_name, int quantity, Double individual_price, Double total_price) {
        this.uuid = uuid;
        this.product_name = product_name;
        this.shop_name = shop_name;
        this.quantity = quantity;
        this.individual_price = individual_price;
        this.total_price = total_price;
    }

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

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getIndividual_price() {
        return individual_price;
    }

    public void setIndividual_price(Double individual_price) {
        this.individual_price = individual_price;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}
