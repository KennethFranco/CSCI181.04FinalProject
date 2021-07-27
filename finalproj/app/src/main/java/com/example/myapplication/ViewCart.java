package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_view_cart)
public class ViewCart extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    double sum;

    @ViewById(R.id.viewCartRecyclerView)
    RecyclerView rV;

    @ViewById(R.id.viewCartQuantity)
    TextView viewCartQ;

    @ViewById(R.id.viewCartProductName)
    TextView viewCartPN;

    @ViewById(R.id.viewCartShopName)
    TextView viewCartSN;

    @ViewById(R.id.viewCartTotalPrice)
    TextView viewCartTP;

    @ViewById(R.id.viewCartAddQtyButton)
    Button viewCartAddB;

    @ViewById(R.id.viewCartMinusQtyButton)
    Button viewCartMinusB;

    @ViewById(R.id.viewCartDeleteButton)
    Button viewCartDeleteB;

    @ViewById(R.id.viewCartFinalPrice)
    TextView viewCartFP;

    @AfterViews
    public void init(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", null);

        realm = Realm.getDefaultInstance();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rV.setLayoutManager(mLayoutManager);

        RealmResults<Cart> list = realm.where(Cart.class).equalTo("user_uuid", uuid).findAll();
        CartAdapter adapter = new CartAdapter(this, list, true);
        rV.setAdapter(adapter);

        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();

        sum = list2.sum("total_price").longValue();

        viewCartFP.setText(String.valueOf(sum));
    }

    public void add(Cart u)
    {
        int qty = u.getQuantity();
        Double totalPrice = u.getTotal_price();
        Double individualPrice = u.getIndividual_price();

        qty+=1;
        totalPrice+=individualPrice;

        realm.beginTransaction();
        u.setQuantity(qty);
        u.setTotal_price(totalPrice);
        realm.commitTransaction();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", null);
        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();
        sum = list2.sum("total_price").longValue();
        viewCartFP.setText(String.valueOf(sum));
    }

    public void minus(Cart u){
        int qty = u.getQuantity();
        Double totalPrice = u.getTotal_price();
        Double individualPrice = u.getIndividual_price();

        qty-=1;
        totalPrice-=individualPrice;

        realm.beginTransaction();
        u.setQuantity(qty);
        u.setTotal_price(totalPrice);
        realm.commitTransaction();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", null);
        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();
        sum = list2.sum("total_price").longValue();
        viewCartFP.setText(String.valueOf(sum));
    }

    public void delete(Cart u){

    }
}