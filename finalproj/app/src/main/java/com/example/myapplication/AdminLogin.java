package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_admin_login)
public class AdminLogin extends AppCompatActivity {

    @ViewById(R.id.editText_AdminUsername)
    EditText uname;

    @ViewById(R.id.editText_AdminPassword)
    EditText pword;

    @ViewById(R.id.adminLoginButton)
    Button login;

    @ViewById(R.id.adminRegisterButton)
    Button register;

    @AfterViews
    public void init() {

    }

    @Click(R.id.adminRegisterButton)
    public void register() {
        AdminRegister_.intent(this).start();
    }

}