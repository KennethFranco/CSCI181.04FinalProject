package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_customer_shops)
public class CustomerShops extends AppCompatActivity {

    @AfterViews
    public void init(){

    }
}