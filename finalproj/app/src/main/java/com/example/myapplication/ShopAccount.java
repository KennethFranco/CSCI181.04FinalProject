package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_shop_account)
public class ShopAccount extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    @ViewById(R.id.shopAccountName)
    EditText shopAccountN;

    @ViewById(R.id.shopAccountDescription)
    EditText shopAccountD;

    @ViewById(R.id.shopAccountUsername)
    EditText shopAccountU;

    @ViewById(R.id.shopAccountPassword)
    EditText shopAccountP;

    @ViewById(R.id.shopAccountSaveButton)
    Button shopAccountSaveB;

    
    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        String uuid = prefs.getString("shopUUID", null);

        Shops result= realm.where(Shops.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        String aN = result.getShopName();
        String aD = result.getShopDescription();
        String aU = result.getShopUsername();
        String aP = result.getShopPassword();

        shopAccountN.setText(aN);
        shopAccountD.setText(aD);
        shopAccountU.setText(aU);
        shopAccountP.setText(aP);

        if (shopAccountN.getText().toString().equals("") || shopAccountD.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
            shopAccountSaveB.setEnabled(false);
        } else{
            shopAccountSaveB.setEnabled(true);
        }

        shopAccountN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountD.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                } else {
                    shopAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        shopAccountD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountN.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                } else {
                    shopAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        shopAccountU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountD.getText().toString().equals("") || shopAccountN.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                } else {
                    shopAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        shopAccountP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountD.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountN.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                } else {
                    shopAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }

    @Click(R.id.shopAccountSaveButton)
    public void save(){
        String u = shopAccountU.getText().toString();
        String p = shopAccountP.getText().toString();
        String n = shopAccountN.getText().toString();
        String d = shopAccountD.getText().toString();

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("shopUUID", null);
        Shops result2 = realm.where(Shops.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        Shops result3 = realm.where(Shops.class).equalTo("shopUsername",u).findFirst();

        if (result3 != null){
            if (result2.getShopUsername().equals(u)){
                realm.beginTransaction();
                result2.setShopUsername(u);
                result2.setShopPassword(p);
                result2.setShopDescription(d);
                result2.setShopName(n);
                result2.setFirstTime(false);
                realm.commitTransaction();
                Toast t = Toast.makeText(this, "Successfully updated shop account details!", Toast.LENGTH_LONG);
                t.show();
                ShopHomeScreen_.intent(this).start();
            }
            else{
                Toast t = Toast.makeText(this, "This username has already been taken! Please choose a new one.", Toast.LENGTH_LONG);
                t.show();
            }
        }
        else{
            realm.beginTransaction();
            result2.setShopUsername(u);
            result2.setShopPassword(p);
            result2.setShopDescription(d);
            result2.setShopName(n);
            result2.setFirstTime(false);
            realm.commitTransaction();
            Toast t = Toast.makeText(this, "Successfully updated shop account details!", Toast.LENGTH_LONG);
            t.show();
            ShopHomeScreen_.intent(this).start();
        }

    }
}