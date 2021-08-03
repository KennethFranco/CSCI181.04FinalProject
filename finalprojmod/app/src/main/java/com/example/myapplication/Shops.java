package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Shops extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String shopUsername;
    private String shopPassword;

    private String shop;
    private String shopDescription;
    private String shopName;
    private Boolean firstTime;

    @Override
    public String toString() {
        return "Shops{" +
                "uuid='" + uuid + '\'' +
                ", shopUsername='" + shopUsername + '\'' +
                ", shopPassword='" + shopPassword + '\'' +
                ", shop='" + shop + '\'' +
                ", shopDescription='" + shopDescription + '\'' +
                ", shopName='" + shopName + '\'' +
                ", firstTime=" + firstTime +
                '}';
    }

    public Boolean getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Boolean firstTime) {
        this.firstTime = firstTime;
    }

    public Shops(String uuid, String shopUsername, String shopPassword, String shop, String shopDescription, String shopName, Boolean firstTime) {
        this.uuid = uuid;
        this.shopUsername = shopUsername;
        this.shopPassword = shopPassword;
        this.shop = shop;
        this.shopDescription = shopDescription;
        this.shopName = shopName;
        this.firstTime = firstTime;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getShopName() {
        return shopName;
    }


    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

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

    public Shops(String uuid, String username, String password, String shopDescription, String shopName) {
        this.uuid = uuid;
        this.shopUsername = username;
        this.shopPassword = password;
        this.shopDescription = shopDescription;
        this.shopName = shopName;
    }

}
