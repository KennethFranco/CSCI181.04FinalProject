package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Shops extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String shopUsername;
    private String shopPassword;

    private String shop;

    public Shops() {}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getShopUsername() {
        return shopUsername;
    }

    public void setShopUsername(String shopUsername) {
        this.shopUsername = shopUsername;
    }

    public String getShopPassword() {
        return shopPassword;
    }

    public void setShopPassword(String shopPassword) {
        this.shopPassword = shopPassword;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Shops(String uuid, String username, String password) {
        this.uuid = uuid;
        this.shopUsername = username;
        this.shopPassword = password;
    }

    @Override
    public String toString() {
        return "Shops [" +
                "UUID = " + uuid + "'\'" +
                ", Shop Name = " + shopUsername + "'\'" +
                ", Shop Password = " + shopPassword + "]";
    }
}
