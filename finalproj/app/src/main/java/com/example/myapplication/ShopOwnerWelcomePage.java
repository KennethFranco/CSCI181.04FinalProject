package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_shop_owner_welcome_page)
public class ShopOwnerWelcomePage extends AppCompatActivity {

    @ViewById
    Button shopOwner_register;

    @Click(R.id.shopOwner_register)
    public void ShopRegister() {
        ShopRegister_.intent(this).start();
    }
}