package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.io.File;
import java.util.UUID;

import io.realm.Realm;

@EActivity(R.layout.activity_customer_shops_specific_product)
public class CustomerShopsSpecificProduct extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    String price;
    int count;
    @ViewById(R.id.specificProductName)
    TextView pName;

    @ViewById(R.id.specificProductPrice)
    TextView pPrice;

    @ViewById(R.id.specificProductDescription)
    TextView pDescription;

    @ViewById(R.id.specificProductQuantity)
    TextView pQuantity;

    @ViewById(R.id.specificProductAddButton)
    ImageButton pAddButton;

    @ViewById(R.id.specificProductMinusButton)
    ImageButton pMinusButton;

    @ViewById(R.id.specificProductPhoto)
    ImageView pPhoto;

    @ViewById(R.id.specificProductExitButton)
    ImageButton pExitButton;

    @ViewById(R.id.specificProductAddToCartButton)
    Button pAddCartButton;

    @AfterViews
    public void init(){



        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("productUUID", null);

        System.out.println(uuid);

        Products result= realm.where(Products.class)
                .equalTo("uuid", uuid)
                .findFirst();

        String name = result.getProduct_name();
        price = result.getProduct_price();

        String pricePHP = "PHP "+price;
        String description = result.getProduct_description();

        pName.setText(name);
        pPrice.setText(pricePHP);
        pDescription.setText(description);

        File getImageDir = getExternalCacheDir();

        File file = new File(getImageDir, result.getImagePath());

        Picasso.get()
                .load(file)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(pPhoto);

    }

    @Click(R.id.specificProductAddButton)
    public void add(){
        count  +=1;
        String newCount = ""+count;
        pQuantity.setText(newCount);
    }

    @Click(R.id.specificProductMinusButton)
    public void minus(){
        if (count != 0 ){
            count-=1;
            String newCount = ""+count;
            pQuantity.setText(newCount);
        }
    }

    @Click(R.id.specificProductExitButton)
    public void exit(){
        finish();
    }

    @Click(R.id.specificProductAddToCartButton)
    public void cart(){
        if (count != 0){
            realm = Realm.getDefaultInstance();

            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            String uuid = prefs.getString("productUUID", null);


            Products result= realm.where(Products.class)
                    .equalTo("uuid", uuid)
                    .findFirst();

            String shopUUID = result.getShop_uuid();

            Shops result2= realm.where(Shops.class)
                    .equalTo("uuid", shopUUID)
                    .findFirst();

            String shopName = result2.getShopName();
            String productName = result.getProduct_name();

            Double individiualPrice = Double.parseDouble(price);
            String qtyvalue= pQuantity.getText().toString();
            int qty = Integer.parseInt(qtyvalue);
            Double totalProductPrice = qty*individiualPrice;
            String randomUUID = UUID.randomUUID().toString();

            System.out.println(individiualPrice);
            System.out.println(qty);
            System.out.println(totalProductPrice);
            System.out.println(shopName);
            System.out.println(productName);


            Cart newCart = new Cart();
            newCart.setIndividual_price(individiualPrice);
            newCart.setProduct_name(productName);
            newCart.setShop_name(shopName);
            newCart.setQuantity(qty);
            newCart.setTotal_price(totalProductPrice);
            String uuid2 = prefs.getString("uuid", null);
            newCart.setUser_uuid(uuid2);
            newCart.setUuid(randomUUID);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newCart);
            realm.commitTransaction();

            String a = "Added "+productName + " x"+qty + " to Cart.";
            Toast t = Toast.makeText(this, ""+a, Toast.LENGTH_LONG);
            t.show();

            finish();
        }
    }
}