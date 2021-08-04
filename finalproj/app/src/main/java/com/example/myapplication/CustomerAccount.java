package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_customer_account2)
public class CustomerAccount extends AppCompatActivity {

    SharedPreferences prefs;
    Realm realm;

    @ViewById(R.id.customerAccountUsername)
    EditText customerAccountU;

    @ViewById(R.id.customerAccountPassword)
    EditText customerAccountP;

    @ViewById(R.id.customerAccountContactNumber)
    EditText customerAccountCN;

    @ViewById(R.id.customerAccountFullName)
    EditText customerAccountFN;

    @ViewById(R.id.customerAccountAddress)
    EditText customerAccountA;

    @ViewById(R.id.customerAccountSaveButton)
    Button customerAccountSaveB;

    @ViewById(R.id.customerAccountBackButton)
    TextView customerAccountBackB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();


        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("uuid", null);



        Users result= realm.where(Users.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        System.out.println(result);

        Boolean checker = prefs.getBoolean("userFT", true);

        String username = result.getUsername();
        String password = result.getPassword();
        String address = result.getAddress();
        String contactNumber = result.getContactNumber();
        String fullName = result.getFullName();

        customerAccountU.setText(username);
        customerAccountP.setText(password);
        customerAccountA.setText(address);
        customerAccountCN.setText(contactNumber);
        customerAccountFN.setText(fullName);

        Users checker2 = realm.where(Users.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        Boolean checker3 = checker2.getFirstTime();



        if (customerAccountFN.getText().toString().equals("") || customerAccountCN.getText().toString().equals("") || customerAccountA.getText().toString().equals("") || customerAccountU.getText().toString().equals("") || customerAccountP.getText().toString().equals(""))
        {
            customerAccountSaveB.setEnabled(false);
            customerAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
            customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        }
        else{

            customerAccountSaveB.setEnabled(true);
            customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
            customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        }

        customerAccountFN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerAccountCN.getText().toString().equals("") || customerAccountA.getText().toString().equals("") || customerAccountU.getText().toString().equals("") || customerAccountP.getText().toString().equals("")){
                    customerAccountSaveB.setEnabled(false);
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    customerAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    customerAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerAccountFN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerAccountCN.getText().toString().equals("") || customerAccountA.getText().toString().equals("") || customerAccountU.getText().toString().equals("") || customerAccountP.getText().toString().equals("")){
                    customerAccountSaveB.setEnabled(false);
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    customerAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    customerAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerAccountCN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerAccountFN.getText().toString().equals("") || customerAccountA.getText().toString().equals("") || customerAccountU.getText().toString().equals("") || customerAccountP.getText().toString().equals("")){
                    customerAccountSaveB.setEnabled(false);
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    customerAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    customerAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerAccountA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerAccountCN.getText().toString().equals("") || customerAccountFN.getText().toString().equals("") || customerAccountU.getText().toString().equals("") || customerAccountP.getText().toString().equals("")){
                    customerAccountSaveB.setEnabled(false);
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    customerAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    customerAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerAccountU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerAccountCN.getText().toString().equals("") || customerAccountA.getText().toString().equals("") || customerAccountFN.getText().toString().equals("") || customerAccountP.getText().toString().equals("")){
                    customerAccountSaveB.setEnabled(false);
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    customerAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    customerAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerAccountP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerAccountCN.getText().toString().equals("") || customerAccountA.getText().toString().equals("") || customerAccountU.getText().toString().equals("") || customerAccountFN.getText().toString().equals("")){
                    customerAccountSaveB.setEnabled(false);
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    customerAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    customerAccountSaveB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Click(R.id.customerAccountSaveButton)
    public void save(){

        int checker = 0;

        if (checker == 0){
            String u = customerAccountU.getText().toString();
            String p = customerAccountP.getText().toString();
            String a = customerAccountA.getText().toString();
            String fN = customerAccountFN.getText().toString();
            String cN = customerAccountCN.getText().toString();

            prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();

            String uuid = prefs.getString("uuid", null);
            Users result2 = realm.where(Users.class)
                    .equalTo("uuid", ""+uuid)
                    .findFirst();

            Users result3 = realm.where(Users.class).equalTo("username",u).findFirst();

//            System.out.println(result3.getUsername());
//            System.out.println(u);

            if (result3 != null){
                if (result2.getUsername().equals(u)){
//                    makes it so that user can set same username for themselves
                    realm.beginTransaction();
                    result2.setUsername(u);
                    result2.setPassword(p);
                    result2.setContactNumber(cN);
                    result2.setAddress(a);
                    result2.setFullName(fN);
                    result2.setFirstTime(false);
                    edit.putString("contactNumber", cN);
                    edit.putString("address", a);
                    edit.putString("fullName", fN);
                    edit.apply();
                    realm.commitTransaction();
                    Toast t = Toast.makeText(this, "Successfully updated account details!", Toast.LENGTH_LONG);
                    t.show();
                    finish();
                    CustomerHome_.intent(this).start();

                    customerAccountBackB.setEnabled(true);
                    customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
                else{
                    Toast t = Toast.makeText(this, "This username has already been taken! Please choose a new one.", Toast.LENGTH_LONG);
                    t.show();
                }
            }
            else{
                realm.beginTransaction();
                result2.setUsername(u);
                result2.setPassword(p);
                result2.setContactNumber(cN);
                result2.setAddress(a);
                result2.setFullName(fN);
                result2.setFirstTime(false);
                edit.putString("contactNumber", cN);
                edit.putString("address", a);
                edit.putString("fullName", fN);
                edit.apply();
                realm.commitTransaction();
                Toast t = Toast.makeText(this, "Successfully updated account details!", Toast.LENGTH_LONG);
                t.show();
                finish();
                CustomerHome_.intent(this).start();
                customerAccountBackB.setEnabled(true);
                customerAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                customerAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
            }

        }
        else{
            Toast t = Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_LONG);
            t.show();
        }




    }

    @Click(R.id.customerAccountBackButton)
    public void back(){
        String uuid = prefs.getString("uuid", null);
        Users checker2 = realm.where(Users.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        Boolean checker3 = checker2.getFirstTime();

        if (checker3 == true){
            finish();
            CustomerLogin_.intent(this).start();
        } else{
            finish();
            CustomerHome_.intent(this).start();
        }

    }
}