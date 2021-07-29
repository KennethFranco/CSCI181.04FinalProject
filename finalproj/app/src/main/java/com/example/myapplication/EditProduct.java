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

@EActivity(R.layout.activity_edit_product)
public class EditProduct extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    @ViewById(R.id.editProductName)
    EditText editPN;

    @ViewById(R.id.editProductDescription)
    EditText editPD;

    @ViewById(R.id.editProductPrice)
    EditText editPP;

    @ViewById(R.id.editProductBackButton)
    Button editProductBackB;

    @ViewById(R.id.editProductSubmitChangesButton)
    Button editProductSubmitB;


    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        SharedPreferences.Editor edit = prefs.edit();

        String product_uuid = prefs.getString("productUUID", null);
        System.out.println(product_uuid);
        Products result= realm.where(Products.class)
                .equalTo("uuid", product_uuid)
                .findFirst();

        System.out.println(result);

        editPN.setText(result.getProduct_name());
        editPD.setText(result.getProduct_description());
        editPP.setText(String.valueOf(result.getProduct_price()));

        if (editPN.getText().toString().equals("") || editPD.getText().toString().equals("") || editPP.getText().toString().equals("")){
            editProductSubmitB.setEnabled(false);
        }
        else{
            editProductSubmitB.setEnabled(true);
        }

        editPN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || editPD.getText().toString().equals("") || editPP.getText().toString().equals("")){
                    editProductSubmitB.setEnabled(false);
                } else {
                    editProductSubmitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || editPN.getText().toString().equals("") || editPP.getText().toString().equals("")){
                    editProductSubmitB.setEnabled(false);
                } else {
                    editProductSubmitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || editPN.getText().toString().equals("") || editPN.getText().toString().equals("")){
                    editProductSubmitB.setEnabled(false);
                } else {
                    editProductSubmitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Click(R.id.editProductSubmitChangesButton)
    public void submit(){
        String n = editPN.getText().toString();
        String d = editPD.getText().toString();
        String p = editPP.getText().toString();

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("productUUID", null);
//        result2 is for getting the uuid and using it to set
        Products result2 = realm.where(Products.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        Products result3 = realm.where(Products.class).equalTo("product_name",n).findFirst();
//        result3 is for checking if the username is already in the database

        if (result3 != null){
            if (result2.getProduct_name().equals(n)){
                realm.beginTransaction();
                System.out.println(n);
                result2.setProduct_name(n);
                result2.setProduct_description(d);
                result2.setProduct_price(p);
                realm.commitTransaction();
                Toast t = Toast.makeText(this, "Successfully updated product details!", Toast.LENGTH_LONG);
                t.show();
                finish();
                ViewProducts_.intent(this).start();
            }
            else{
                Toast t = Toast.makeText(this, "This product name has already been taken! Please choose a new one.", Toast.LENGTH_LONG);
                t.show();
            }
        } else{
            realm.beginTransaction();
            result2.setProduct_name(n);
            result2.setProduct_description(d);
            result2.setProduct_price(p);
            realm.commitTransaction();
            Toast t = Toast.makeText(this, "Successfully updated product details!", Toast.LENGTH_LONG);
            t.show();
            finish();
            ViewProducts_.intent(this).start();
        }
    }

    @Click(R.id.editProductBackButton)
    public void back(){
        finish();
        ViewProducts_.intent(this).start();
    }
}