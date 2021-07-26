package com.example.myapplication.shops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.shops.ShopAccount_;
import com.example.myapplication.shops.ViewProducts_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_shop_home_screen)
public class ShopHomeScreen extends AppCompatActivity {

    Realm realm ;

    @ViewById(R.id.ViewProducts)
    Button viewProducts;

    @ViewById(R.id.ViewAccountDetails_Shop)
    Button account;

    @ViewById(R.id.ShopHomeWelcome)
    TextView welcome_msg;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String valueUUID = prefs.getString("shopUUID", null);

        Shops uuid = realm.where(Shops.class).equalTo("uuid", valueUUID).findFirst();

        if(uuid.getShop() == null) {
            welcome_msg.setText("Welcome " + uuid.getShopUsername() + "!" + " Please go to accounts to set your account details.");
        }
        else{
            welcome_msg.setText("Welcome back, " + uuid.getShop() + "!");
        }
    }

    @Click(R.id.ViewProducts)
    public void ViewProducts() {
        ViewProducts_.intent(this).start();
    }

    @Click(R.id.shopsAccountButton)
    public void account(){
        ShopAccount_.intent(this).start();
    }
}