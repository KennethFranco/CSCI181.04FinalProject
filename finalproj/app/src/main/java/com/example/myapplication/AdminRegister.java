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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;

@EActivity(R.layout.activity_admin_register)
public class AdminRegister extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    @ViewById(R.id.editText_AdminRegisterUname)
    EditText uname;

    @ViewById(R.id.editText_AdminRegisterPword)
    EditText pword;

    @ViewById(R.id.editText_AdminRegisterConfirm)
    EditText confirm;

    @ViewById(R.id.adminConfirmRegister)
    Button register;

    @ViewById(R.id.adminRegisterBackLink)
    TextView cancel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        if (uname.getText().toString().equals("") || pword.getText().toString().equals("") || confirm.getText().toString().equals(""))
        {
            register.setEnabled(false);
            register.setTextColor(Color.parseColor("#8b8b8b"));
            register.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        }
        else{
            register.setEnabled(true);
        }

        uname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || pword.getText().toString().equals("") || confirm.getText().toString().equals("")){
                    register.setEnabled(false);
                    register.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    register.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    register.setTextColor(Color.parseColor("#ffffff"));
                    register.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    register.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || uname.getText().toString().equals("") || confirm.getText().toString().equals("")){
                    register.setEnabled(false);
                    register.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    register.setTextColor(Color.parseColor("#8b8b8b"));
                } else
                {
                    register.setTextColor(Color.parseColor("#ffffff"));
                    register.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    register.setEnabled(true);
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
                if(s.toString().trim().length()==0 || uname.getText().toString().equals("") || pword.getText().toString().equals("")){
                    register.setEnabled(false);
                    register.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    register.setTextColor(Color.parseColor("#8b8b8b"));
                } else
                {
                    register.setTextColor(Color.parseColor("#ffffff"));
                    register.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    register.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Click(R.id.adminConfirmRegister)
    public void Register() {
        realm = Realm.getDefaultInstance();
        String checkUname = uname.getText().toString();
        String checkPassword = pword.getText().toString();
        String checkConfirm = confirm.getText().toString();
        String uuid = UUID.randomUUID().toString();

        Admin result = realm.where(Admin.class).equalTo("username", checkUname).findFirst();

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

            Admin newAdmin = new Admin();
            newAdmin.setUuid(uuid);
            newAdmin.setUsername(checkUname);
            newAdmin.setPassword(checkPassword);

            long count = 0;

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newAdmin);
            realm.commitTransaction();

            count = realm.where(Admin.class).count();

            Toast t = Toast.makeText(this, "Login Saved, Total Admins: " + count, Toast.LENGTH_LONG);
            t.show();

            finish();
            AdminLogin_.intent(this).start();

        }
        else {
            Toast t = Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Click(R.id.adminRegisterBackLink)
    public void Cancel() {
        finish();
        MainActivity_.intent(this).start();
    }

    @Click(R.id.adminRegisterLoginLink)
    public void Login() {
        finish();
        AdminLogin_.intent(this).start();
    }
}