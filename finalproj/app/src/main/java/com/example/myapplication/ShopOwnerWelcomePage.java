package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_shop_owner_welcome_page)
public class ShopOwnerWelcomePage extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    @ViewById
    Button shopOwner_register;

    @ViewById(R.id.shopOwner_editUsername)
    EditText username;

    @ViewById(R.id.shopOwner_editPassword)
    EditText password;

    @ViewById(R.id.shopAccountRememberMe)
    CheckBox rememberMe;

    @ViewById(R.id.shopOwner_login)
    Button shopOwnerLoginB;

    @AfterViews
    public void init() {

        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();


        String uuid = prefs.getString("shopUUID", null);
        Boolean rememberValue = prefs.getBoolean("shoprV", false);

        if (rememberValue == true){
            Shops result = realm.where(Shops.class)
                    .equalTo("uuid", ""+uuid)
                    .findFirst();

            if (result != null){
                String u = result.getShopUsername();
                String p = result.getShopPassword();

                username.setText(u);
                password.setText(p);
                rememberMe.setChecked(true);
            }

        }

        if (username.getText().toString().equals("") || password.getText().toString().equals("")){
            shopOwnerLoginB.setEnabled(false);
        }
        else{
            shopOwnerLoginB.setEnabled(true);
        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || (password.getText().toString().equals(""))){
                    shopOwnerLoginB.setEnabled(false);
                } else {
                    shopOwnerLoginB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || (username.getText().toString().equals(""))){
                    shopOwnerLoginB.setEnabled(false);
                } else {
                    shopOwnerLoginB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Click(R.id.shopOwner_login)
    public void ShopLogin() {
        String checkUname = username.getText().toString();
        String checkPword = password.getText().toString();
        boolean rememberValue = rememberMe.isChecked();

        Shops username = realm.where(Shops.class).equalTo("shopUsername", checkUname).findFirst();

        System.out.println(username);
        if (username != null){
            if (username.getShopPassword().equals(checkPword)){

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                String uuid = prefs.getString("shopUUID", null);
                System.out.println(uuid);

                Shops result = realm.where(Shops.class)
                        .equalTo("uuid", ""+uuid)
                        .findFirst();
                SharedPreferences.Editor edit = prefs.edit();

                edit.putString("shopUUID", username.getUuid());
                edit.putBoolean("shoprV", rememberValue);
                edit.apply();

                System.out.println(result.getShopUsername());
                System.out.println(result.getShopPassword());
                System.out.println(result.getUuid());
                System.out.println(result.getFirstTime());
                if (result.getFirstTime()==true){
                    Toast t = Toast.makeText(this, "Thank you for signing up with us! Please fill up these account details in order to start adding products!", Toast.LENGTH_LONG);
                    t.show();
                    ShopAccount_.intent(this).start();
                } else{
                    ShopHomeScreen_.intent(this).start();
                }

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