package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

    @ViewById(R.id.shopLoginRegisterLink)
    TextView shopOwner_register;

    @ViewById(R.id.shopOwner_editUsername)
    EditText username;

    @ViewById(R.id.shopOwner_editPassword)
    EditText password;

    @ViewById(R.id.shopAccountRememberMe)
    CheckBox rememberMe;

    @ViewById(R.id.shopOwner_login)
    Button shopOwnerLoginB;

    @ViewById(R.id.shopClearButton)
    Button shopClearB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void init() {

        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();


        String uuid = prefs.getString("shopUUID", null);
        Boolean rememberValue = prefs.getBoolean("shoprV", false);

        System.out.println(rememberValue);
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

                shopClearB.setTextColor(Color.parseColor("#ffffff"));
                shopClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                shopClearB.setEnabled(true);
            }

        } else{
            shopOwnerLoginB.setEnabled(false);
            shopOwnerLoginB.setTextColor(Color.parseColor("#8b8b8b"));
            shopOwnerLoginB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));

            shopClearB.setEnabled(false);
            shopClearB.setTextColor(Color.parseColor("#8b8b8b"));
            shopClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        }

        if (username.getText().toString().equals("") || password.getText().toString().equals("")){
            shopOwnerLoginB.setEnabled(false);
            shopOwnerLoginB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            shopOwnerLoginB.setTextColor(Color.parseColor("#8b8b8b"));
        }
        else{
            shopOwnerLoginB.setEnabled(true);
            shopOwnerLoginB.setTextColor(Color.parseColor("#ffffff"));
            shopOwnerLoginB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || (password.getText().toString().equals(""))){
                    shopOwnerLoginB.setEnabled(false);
                    shopOwnerLoginB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopOwnerLoginB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    shopOwnerLoginB.setEnabled(true);
                    shopOwnerLoginB.setTextColor(Color.parseColor("#ffffff"));
                    shopOwnerLoginB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
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
                    shopOwnerLoginB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopOwnerLoginB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    shopOwnerLoginB.setEnabled(true);
                    shopOwnerLoginB.setTextColor(Color.parseColor("#ffffff"));
                    shopOwnerLoginB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
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
                    finish();
                    ShopAccount_.intent(this).start();
                } else{
                    finish();
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
    @Click(R.id.shopLoginRegisterLink)
    public void ShopRegister() {
        finish();
        ShopRegister_.intent(this).start();
    }

    @Click(R.id.shopLoginBackLink)
    public void back(){
        finish();
        MainActivity_.intent(this).start();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Click(R.id.shopClearButton)
    public void ShopClear(){
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove("shopUUID");
        Boolean checker = false;
        edit.putBoolean("shoprV", checker);

        Boolean print = prefs.getBoolean("shoprV", false);
        System.out.println(print);

        edit.apply();
        Toast t = Toast.makeText(this, "Successfully cleared credentials.", Toast.LENGTH_LONG);
        username.setText("");
        password.setText("");
        rememberMe.setChecked(false);

        shopClearB.setEnabled(false);
        shopClearB.setTextColor(Color.parseColor("#8b8b8b"));
        shopClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        t.show();
    }
}