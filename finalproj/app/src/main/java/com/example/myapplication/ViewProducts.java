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

    @ViewById(R.id.Products_clearall)
    Button clearAll;

    @AfterViews
    public void init() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        products_recyclerView.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String prefsID = prefs.getString("shopUUID", null);

        RealmResults<Products> list = realm.where(Products.class).equalTo("shop_uuid", prefsID).findAll();


        System.out.println(list);
        System.out.println(prefsID);
        System.out.println(realm.where(Products.class).findAll());


        Boolean checker = list.isEmpty();
        if (checker==true){
            clearAll.setEnabled(false);
        } else{
            clearAll.setEnabled(true);
        }

        ProductsAdapter adapter = new ProductsAdapter(this, list, true);
        products_recyclerView.setAdapter(adapter);
        // prefs id e283d75d-a3f2-4f08-b1b2-69667b8022b0
        // products shop_uuid

    }

    @Click(R.id.Products_clearall)
    public void clear(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirmation");
        alert.setMessage("Are you sure you want to clear all your products?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                String prefsID = prefs.getString("shopUUID", null);
                RealmResults<Products> list = realm.where(Products.class).equalTo("shop_uuid", prefsID).findAll();
                realm.beginTransaction();
                list.deleteAllFromRealm();
                realm.commitTransaction();
                Toast.makeText(ViewProducts.this, "Deleted all products in Shop.", Toast.LENGTH_SHORT).show();
                clearAll.setEnabled(false);
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

    @Click(R.id.Products_add)
    public void AddProduct() {
        finish();
        AddProduct_.intent(this).start();
    }

    public void edit (Products u){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("productUUID", u.getUuid());

        RealmResults<Cart> list = realm.where(Cart.class).equalTo("product_uuid", u.getUuid()).findAll();
        System.out.println(list);

        Boolean checker = list.isEmpty();

        if (checker == true){
            System.out.println(u.getUuid());
            edit.apply();
            finish();
            EditProduct_.intent(this).start();
        } else{
            Toast.makeText(ViewProducts.this, "You cannot edit this product for it is currently present in some customers' carts! Please wait until they do not have this in their carts anymore.", Toast.LENGTH_LONG).show();
        }

    }

    @Click(R.id.shopProductsBackLink)
    public void back(){
        finish();
        ShopHomeScreen_.intent(this).start();
    }

    public void delete (Products u){
        RealmResults<Cart> list = realm.where(Cart.class).equalTo("product_uuid", u.getUuid()).findAll();
        System.out.println(list);

        Boolean checker = list.isEmpty();

        if (checker == true){
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
                        Toast.makeText(ViewProducts.this, "Product deleted", Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                        String prefsID = prefs.getString("shopUUID", null);

                        RealmResults<Products> list = realm.where(Products.class).equalTo("shop_uuid", prefsID).findAll();


                        System.out.println(list);
                        System.out.println(prefsID);
                        System.out.println(realm.where(Products.class).findAll());


                        Boolean checker = list.isEmpty();
                        if (checker==true){
                            clearAll.setEnabled(false);
                        } else{
                            clearAll.setEnabled(true);
                        }
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
        } else{
            Toast.makeText(ViewProducts.this, "You cannot delete this product for it is currently present in some customers' carts! Please wait until they do not have this in their carts anymore.", Toast.LENGTH_LONG).show();
        }

    }
}