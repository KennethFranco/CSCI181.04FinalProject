package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ShopHomeScreen_;
import com.example.myapplication.ShopRegister_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_shop_owner_welcome_page)
public class ShopOwnerWelcomePage extends AppCompatActivity {

    Realm realm;

    @ViewById
    Button shopOwner_register;

    @ViewById(R.id.shopOwner_editUsername)
    EditText username;

    @ViewById(R.id.shopOwner_editPassword)
    EditText password;

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
    }

    @Click(R.id.shopOwner_login)
    public void ShopLogin() {
        String checkUname = username.getText().toString();
        String checkPword = password.getText().toString();

        Shops username = realm.where(Shops.class).equalTo("shopUsername", checkUname).findFirst();

        System.out.println(username);
        if (username != null){
            if (username.getShopPassword().equals(checkPword)){

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("shopUUID", username.getUuid());
                edit.apply();

                ShopHomeScreen_.intent(this).start();
            }
            else {
                Toast t = Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG);
                t.show();
            }
        }
        else if (username == null) {
            Toast t = Toast.makeText(this, "Username is incorrect", Toast.LENGTH_LONG);
            t.show();
        }
    }
    @Click(R.id.shopOwner_register)
    public void ShopRegister() {
        ShopRegister_.intent(this).start();
    }
}