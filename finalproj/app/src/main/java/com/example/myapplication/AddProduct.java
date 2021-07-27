package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_add_product)
public class AddProduct extends AppCompatActivity {

    Realm realm;

    SharedPreferences prefs;

    @ViewById
    Button addproduct;

    @ViewById(R.id.editText_ProductName)
    EditText prod_name;

    @ViewById(R.id.editText_ProductNumber)
    EditText prod_num;

    @ViewById(R.id.addProductDescription)
    EditText addProductD;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

    }


    @Click(R.id.addproduct)
    public void AddProduct() {

        String name = prod_name.getText().toString();
        String price = prod_num.getText().toString();
        String description = addProductD.getText().toString();
        String prod_uuid = UUID.randomUUID().toString();

        Products result = realm.where(Products.class).equalTo("product_name", name).findFirst();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String prefsID = prefs.getString("shopUUID", null);

//        valueUUID is shops uuid
        Shops valueUUID = realm.where(Shops.class).equalTo("uuid", prefsID).findFirst();

        System.out.println(result);

        if (name.equals("")){
            Toast t = Toast.makeText(this, "Name cannot be left blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (price.equals("")) {
            Toast t = Toast.makeText(this, "Price cannot be left blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if(result == null) {
            Products newProduct = new Products();
            newProduct.setUuid(prod_uuid);
            newProduct.setProduct_name(name);
            newProduct.setProduct_price(price);
            newProduct.setProduct_description(description);

            newProduct.setShop_name(valueUUID.getShopName());
            newProduct.setShop_uuid(valueUUID.getUuid());



            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newProduct);
            realm.commitTransaction();

            Toast t = Toast.makeText(this, "Product Saved", Toast.LENGTH_LONG);
            t.show();

            finish();
        }
        else{
            Toast t = Toast.makeText(this, "Product already exists", Toast.LENGTH_LONG);
            t.show();
        }

    }
}