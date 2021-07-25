package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_view_products)
public class ViewProducts extends AppCompatActivity {

    @AfterViews
    public void init() {

    }
}