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

@EActivity(R.layout.activity_customer_orders)
public class CustomerOrders extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    @ViewById(R.id.customerOrdersRecyclerView)
    RecyclerView rV;

    @ViewById(R.id.customerOrdersHomeLink)
    TextView customerOrdersHomeL;

    @AfterViews
    public void init(){
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        customerOrdersHomeL.setPaintFlags(customerOrdersHomeL.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", null);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rV.setLayoutManager(mLayoutManager);

        RealmResults<Orders> list = realm.where(Orders.class).equalTo("customer_uuid", uuid).findAll();
        OrdersAdapter adapter = new OrdersAdapter(this, list, true);
        rV.setAdapter(adapter);
    }


   @Click(R.id.customerOrdersHomeLink)
   public void back(){
        finish();
        CustomerHome_.intent(this).start();
   }
}