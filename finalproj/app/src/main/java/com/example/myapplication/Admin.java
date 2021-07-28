package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Admin extends RealmObject {

    @PrimaryKey
    String uuid;

    String username;
    String password;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
