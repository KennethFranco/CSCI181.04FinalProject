package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_admin_shops_products)
public class AdminShopsProducts extends AppCompatActivity {

    Realm realm;

    @ViewById(R.id.admin_ViewShopsProductsRV)
    RecyclerView rV;

    @ViewById(R.id.adminMenuHomeLink)
    TextView adminMenuHomeL;

    @AfterViews
    public void init(){
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        adminMenuHomeL.setPaintFlags(adminMenuHomeL.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rV.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String prefsID = prefs.getString("adminviewShopUUID", null);

        // query by shop UUID
        RealmResults<Products> list = realm.where(Products.class).equalTo("shop_uuid", prefsID).findAll();

        System.out.println(realm.where(Products.class).findAll());


        AdminShopsProductsAdapter adapter = new AdminShopsProductsAdapter(this, list, true);
        rV.setAdapter(adapter);

    }

    @Click(R.id.adminMenuHomeLink)
    public void back(){
        finish();
        AdminViewShops_.intent(this).start();
    }
}