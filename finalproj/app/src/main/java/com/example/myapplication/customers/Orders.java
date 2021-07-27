package com.example.myapplication.customers;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Orders extends RealmObject {
    @PrimaryKey
    private String uuid;

    private String order_name;
    private Double order_price;
    private String qty;

    private String shop_uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getShop_uuid() {
        return shop_uuid;
    }

    public void setShop_uuid(String shop_uuid) {
        this.shop_uuid = shop_uuid;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public Orders() {}

    public Orders(String uuid, String order_name, Double order_price, String qty, String shop_uuid) {
        this.uuid = uuid;
        this.order_name = order_name;
        this.order_price = order_price;
        this.qty = qty;
        this.shop_uuid = shop_uuid;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "uuid='" + uuid + '\'' +
                ", order_name='" + order_name + '\'' +
                ", order_price=" + order_price +
                ", qty='" + qty + '\'' +
                ", shop_uuid='" + shop_uuid + '\'' +
                '}';
    }
}
