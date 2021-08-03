package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_customer_register)
public class CustomerRegister extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    @ViewById(R.id.customerRegisterUsername)
    EditText customerRegisterU;

    @ViewById(R.id.customerRegisterPassword)
    EditText customerRegisterP;

    @ViewById(R.id.customerRegisterConfirmPassword)
    EditText customerRegisterCP;

    @ViewById(R.id.customerRegisterSigninButton)
    Button customerRegisterSigninB;

    @ViewById(R.id.customerRegisterLoginLink)
    TextView customerRegisterLoginL;

    @ViewById(R.id.customerRegisterBackLink)
    TextView customerRegisterCancelL;

    @AfterViews
    public void init(){
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        customerRegisterCancelL.setPaintFlags(customerRegisterCancelL.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        customerRegisterLoginL.setPaintFlags(customerRegisterLoginL.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        if (customerRegisterU.getText().toString().equals("") || customerRegisterP.getText().toString().equals("") || customerRegisterCP.getText().toString().equals(""))
        {
            customerRegisterSigninB.setEnabled(false);
        }
        else{
            customerRegisterSigninB.setEnabled(true);
        }

        customerRegisterU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerRegisterP.getText().toString().equals("") || customerRegisterCP.getText().toString().equals("")){
                    customerRegisterSigninB.setEnabled(false);
                } else {
                    customerRegisterSigninB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerRegisterP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerRegisterU.getText().toString().equals("") || customerRegisterCP.getText().toString().equals("")){
                    customerRegisterSigninB.setEnabled(false);
                } else
                {
                    customerRegisterSigninB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerRegisterCP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || customerRegisterU.getText().toString().equals("") || customerRegisterP.getText().toString().equals("")){
                    customerRegisterSigninB.setEnabled(false);
                } else
                {
                    customerRegisterSigninB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Click(R.id.customerRegisterSigninButton)
    public void signin(){
        String checkUsername = customerRegisterU.getText().toString();
        String pw1 = customerRegisterP.getText().toString();
        String pw2 = customerRegisterCP.getText().toString();

        if (checkUsername.equals("")) {
            Toast t = Toast.makeText(this, "Name must not be blank", Toast.LENGTH_LONG);
            t.show();
        } else if(pw1.equals(pw2)) {
            RealmResults<Users> result = realm.where(Users.class).equalTo("username", ""+checkUsername).findAll();

            if (result.isEmpty()){
                Users u = new Users();
                u.setUuid(UUID.randomUUID().toString());
                u.setUsername(checkUsername);
                u.setPassword(pw1);
                u.setAddress("");
                u.setFullName("");
                u.setContactNumber("");

                u.setFirstTime(true);
                prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putBoolean("userFT", true);
                edit.putString("uuid", u.getUuid());
                edit.apply();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(u);
                realm.commitTransaction();
                long count = realm.where(Users.class).count();
                Toast t = Toast.makeText(this, "Successfully created an account! You may now login!"+count, Toast.LENGTH_LONG);
                t.show();
                finish();
                CustomerLogin_.intent(this).start();
            }
            else{
                Toast t = Toast.makeText(this, "The username you are trying to use already exists. Please login if you already have an account.", Toast.LENGTH_LONG);
                t.show();
            }

        } else {
            Toast t = Toast.makeText(this, "Confirm password does not match", Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Click(R.id.customerRegisterLoginLink)
    public void cancel(){
        finish();
        CustomerLogin_.intent(this).start();
    }

    @Click(R.id.customerRegisterBackLink)
    public void back(){
        finish();
        MainActivity_.intent(this).start();
    }
}