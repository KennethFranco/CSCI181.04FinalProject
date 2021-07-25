package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

@EActivity(R.layout.activity_view_products)
public class ViewProducts extends AppCompatActivity {

    Realm realm;

    @ViewById
    RecyclerView products_recyclerView;

    @ViewById(R.id.Products_add)
    Button add;

    @AfterViews
    public void init() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        products_recyclerView.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String prefsID = prefs.getString("shopUUID", null);

        // query by shop UUID
        RealmResults<Products> list = realm.where(Products.class).equalTo("shop_uuid", prefsID).findAll();

        System.out.println(list);
        System.out.println(prefsID);
        System.out.println(realm.where(Products.class).findAll());


        ProductsAdapter adapter = new ProductsAdapter(this, list, true);
        products_recyclerView.setAdapter(adapter);
        // prefs id e283d75d-a3f2-4f08-b1b2-69667b8022b0
        // products shop_uuid

    }

    @Click(R.id.Products_add)
    public void AddProduct() {
        AddProduct_.intent(this).start();
    }

    public void edit (Products u){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("uuid", u.getUuid());
        edit.apply();

//        Edit_.intent(this).start();
    }

    public void delete (Products u){
        if (u.isValid())
        {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Are you sure?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realm.beginTransaction();
                    u.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(ViewProducts.this, "User deleted", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ViewProducts.this, "Deletion cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
        }
    }
}