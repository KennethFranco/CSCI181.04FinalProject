package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.Notification;
import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

@EActivity(R.layout.activity_customer_login)
public class CustomerLogin extends AppCompatActivity {

    SharedPreferences prefs;

    Realm realm;
    int buttonCounter;
    Boolean firstTime = false;

    String field1;
    String field2;
    @ViewById(R.id.customerLoginUsername)
    EditText customerLoginU;

    @ViewById(R.id.customerLoginPassword)
    EditText customerLoginP;

    @ViewById(R.id.customerLoginRememerMe)
    CheckBox customerLoginRemember;

    @ViewById(R.id.customerLoginSigninButton)
    Button customerLoginSigninB;

    @ViewById(R.id.customerLoginBackLink)
    TextView customerLoginCancelB;

    @ViewById(R.id.customerLoginClearButton)
    Button customerLoginClearB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("uuid", null);
        Boolean rememberValue = prefs.getBoolean("rV", false);
        if (rememberValue == true){
            Users result = realm.where(Users.class)
                    .equalTo("uuid", ""+uuid)
                    .findFirst();

            if (result != null){
                String u = result.getUsername();
                String p = result.getPassword();

                customerLoginU.setText(u);
                customerLoginP.setText(p);
                customerLoginRemember.setChecked(true);

                customerLoginClearB.setTextColor(Color.parseColor("#ffffff"));
                customerLoginClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                customerLoginClearB.setEnabled(true);
            }
            customerLoginSigninB.setEnabled(true);
        } else{
            customerLoginSigninB.setEnabled(false);
            customerLoginSigninB.setTextColor(Color.parseColor("#8b8b8b"));
            customerLoginSigninB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));

            customerLoginClearB.setEnabled(false);
            customerLoginClearB.setTextColor(Color.parseColor("#8b8b8b"));
            customerLoginClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        }


        customerLoginU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || (customerLoginP.getText().toString().equals(""))){
                    customerLoginSigninB.setEnabled(false);
                    customerLoginSigninB.setTextColor(Color.parseColor("#8b8b8b"));
                    customerLoginSigninB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                } else {
                    customerLoginSigninB.setTextColor(Color.parseColor("#ffffff"));
                    customerLoginSigninB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    customerLoginSigninB.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerLoginP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerLoginU.getText().toString().equals("")){
                    customerLoginSigninB.setEnabled(false);
                    customerLoginSigninB.setTextColor(Color.parseColor("#8b8b8b"));
                    customerLoginSigninB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                } else
                    {
                    customerLoginSigninB.setEnabled(true);
                    customerLoginSigninB.setTextColor(Color.parseColor("#ffffff"));
                    customerLoginSigninB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Click(R.id.customerLoginClearButton)
    public void clear(){
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove("uuid");
        edit.apply();
        Toast t = Toast.makeText(this, "Successfully cleared credentials.", Toast.LENGTH_LONG);
        customerLoginU.setText("");
        customerLoginP.setText("");
        customerLoginRemember.setChecked(false);

        customerLoginClearB.setEnabled(false);
        customerLoginClearB.setTextColor(Color.parseColor("#8b8b8b"));
        customerLoginClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        t.show();
    }

    @Click(R.id.customerLoginSigninButton)
    public void signin(){
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String u = customerLoginU.getText().toString();
        String p = customerLoginP.getText().toString();
        boolean rememberValue = customerLoginRemember.isChecked();

        Users result = realm.where(Users.class)
                .equalTo("username", ""+u)
                .findFirst();

        if (result == null){
            Toast t = Toast.makeText(this, "No user found, please sign up.", Toast.LENGTH_LONG);
            t.show();
        }
        else if (p.equals(result.getPassword())){
            edit.putString("uuid", result.getUuid());
            edit.putBoolean("rV", rememberValue);
            edit.apply();

            Boolean checker =  result.getFirstTime();

            if (checker == true){
                finish();
                CustomerAccount_.intent(this).start();
                Toast t = Toast.makeText(this, "Thank you for registering with us! Please fill up your account details to begin shopping!", Toast.LENGTH_LONG);
                t.show();
            } else{
                finish();
                Toast t = Toast.makeText(this, "Welcome back!", Toast.LENGTH_LONG);
                t.show();
                CustomerHome_.intent(this).start();
            }

        }
        else{
            Toast t = Toast.makeText(this, "Invalid password given.", Toast.LENGTH_LONG);
            t.show();
        }

    }

    @Click(R.id.customerLoginRegisterLink)
    public void cancel(){
        finish();
        CustomerRegister_.intent(this).start();
    }

    @Click(R.id.customerLoginBackLink)
    public void back(){
        finish();
        MainActivity_.intent(this).start();
    }

}