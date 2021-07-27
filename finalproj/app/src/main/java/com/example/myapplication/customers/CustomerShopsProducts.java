package com.example.myapplication.customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.shops.Products;
import com.example.myapplication.R;
import com.example.myapplication.shops.ShopProductsAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_customer_shops_products)
public class CustomerShopsProducts extends AppCompatActivity {

    Realm realm;

    @ViewById(R.id.shopProductsRecyclerView)
    RecyclerView rV;

    @ViewById(R.id.shopProductName)
    TextView prodName;

    @ViewById(R.id.shopProductPrice)
    TextView prodPrice;

    @ViewById(R.id.addToCartButton)
    Button addToCart;

    @ViewById(R.id.addItem)
    ImageButton addItem;

    @ViewById(R.id.removeItem)
    ImageButton removeItem;

    @ViewById(R.id.editTextNumber)
    EditText editQty;

    @AfterViews
    public void init(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rV.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String prefsID = prefs.getString("viewShopUUID", null);

        // query by shop UUID
        RealmResults<Products> list = realm.where(Products.class).equalTo("shop_uuid", prefsID).findAll();

        ShopProductsAdapter adapter = new ShopProductsAdapter(this, list, true);
        rV.setAdapter(adapter);

    }

    public void addItem(Products p){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String prefsID = prefs.getString("viewShopUUID", null);
//        String qty = editQty.getText().toString();
        String order_uuid = UUID.randomUUID().toString();

        realm = Realm.getDefaultInstance();

        Orders newOrder = new Orders();
        newOrder.setUuid(order_uuid);
        newOrder.setOrder_name(p.getProduct_name());
//        newOrder.setQty(qty);
        newOrder.setShop_uuid(prefsID);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(newOrder);
        realm.commitTransaction();

        System.out.println(newOrder);
    }

    @Click(R.id.addToCartButton)
    public void AddToCart() {


    }


}