package com.example.myapplication.shops;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_add_product)
public class EditProduct extends AppCompatActivity {

    @AfterViews
    public void init(){

    }
}