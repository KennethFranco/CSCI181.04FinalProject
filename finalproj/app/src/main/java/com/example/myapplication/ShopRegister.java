package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;

@EActivity(R.layout.activity_shop_register)
public class ShopRegister extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    @ViewById(R.id.editText_ShopRegisterUsername)
    EditText username;

    @ViewById(R.id.editText_ShopRegisterPassword)
    EditText password;

    @ViewById(R.id.editText_ShopRegisterConfirm)
    EditText confirm;

    @ViewById(R.id.shopRegisterButton)
    Button shopRegisterB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void init() {

        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        if (username.getText().toString().equals("") || password.getText().toString().equals("")){
            shopRegisterB.setEnabled(false);
            shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            shopRegisterB.setTextColor(Color.parseColor("#8b8b8b"));
        }
        else{
            shopRegisterB.setEnabled(true);
            shopRegisterB.setTextColor(Color.parseColor("#ffffff"));
            shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || password.getText().toString().equals("") || confirm.getText().toString().equals("")){
                    shopRegisterB.setEnabled(false);
                    shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopRegisterB.setTextColor(Color.parseColor("#8b8b8b"));
                } else
                {
                    shopRegisterB.setEnabled(true);
                    shopRegisterB.setTextColor(Color.parseColor("#ffffff"));
                    shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
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
                if(s.toString().trim().length()==0 || username.getText().toString().equals("") || confirm.getText().toString().equals("")){
                    shopRegisterB.setEnabled(false);
                    shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopRegisterB.setTextColor(Color.parseColor("#8b8b8b"));
                } else
                {
                    shopRegisterB.setEnabled(true);
                    shopRegisterB.setTextColor(Color.parseColor("#ffffff"));
                    shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || password.getText().toString().equals("") || username.getText().toString().equals("")){
                    shopRegisterB.setEnabled(false);
                    shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopRegisterB.setTextColor(Color.parseColor("#8b8b8b"));
                } else
                {
                    shopRegisterB.setEnabled(true);
                    shopRegisterB.setTextColor(Color.parseColor("#ffffff"));
                    shopRegisterB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Click(R.id.shopRegisterButton)
    public void Register() {
        String checkUname = username.getText().toString();
        String checkPassword = password.getText().toString();
        String checkConfirm = confirm.getText().toString();
        String uuid = UUID.randomUUID().toString();

        Shops result = realm.where(Shops.class).equalTo("shopUsername", checkUname).findFirst();

        if (checkUname.equals("")){
            Toast t = Toast.makeText(this, "Name cannot be left blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (checkPassword.equals("") && checkConfirm.equals("")){
            Toast t = Toast.makeText(this, "Please put a password", Toast.LENGTH_LONG);
            t.show();
        }
        else if (result != null) {
            Toast t = Toast.makeText(this, "This account already exists", Toast.LENGTH_LONG);
            t.show();
        }
        else if ((result == null) && (checkPassword.equals(checkConfirm))){
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();

            Shops newShop = new Shops();
            newShop.setUuid(uuid);
            newShop.setShopUsername(checkUname);
            newShop.setShopPassword(checkPassword);
            newShop.setShopName("");
            newShop.setShopDescription("");
            newShop.setFirstTime(true);

            System.out.println(checkUname);
            System.out.println(checkPassword);
            System.out.println(uuid);
            edit.putString("shopUUID", uuid);
            edit.apply();
            System.out.println(newShop.getFirstTime());

            long count = 0;

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newShop);
            realm.commitTransaction();

            Toast t = Toast.makeText(this, "Login Saved!", Toast.LENGTH_LONG);
            t.show();

            finish();
            ShopOwnerWelcomePage_.intent(this).start();

        }
        else {
            Toast t = Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Click(R.id.shopRegisterBackLink)
    public void CancelRegister() {
        finish();
        ShopOwnerWelcomePage_.intent(this).start();
    }

    @Click(R.id.shopRegisterLoginLink)
    public void Login() {
        finish();
        ShopOwnerWelcomePage_.intent(this).start();
    }
}