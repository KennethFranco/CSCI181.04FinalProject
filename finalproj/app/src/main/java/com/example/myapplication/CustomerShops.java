package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;

import com.example.myapplication.CustomerShopsProducts_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_customer_shops)
public class CustomerShops extends AppCompatActivity {

    SharedPreferences prefs;
    Realm realm;
    @ViewById(R.id.shopsRecyclerView)
    RecyclerView shopsRV;

    @AfterViews
    public void init(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        shopsRV.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();
        RealmResults<Shops> list = realm.where(Shops.class).findAll();
        ShopsAdapter adapter = new ShopsAdapter(this, list, true);
        shopsRV.setAdapter(adapter);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

    }

    public void c(Shops u) {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        String uuid = u.getUuid();
        System.out.println(uuid);

        edit.putString("viewShopUUID", uuid);
        edit.apply();

        CustomerShopsProducts_.intent(this).start();
    }
}