package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_customer_welcome)
public class CustomerWelcome extends AppCompatActivity {

    @ViewById(R.id.customerRegisterButton)
    Button customerRB;

    @ViewById(R.id.customerLoginButton)
    Button customerLB;

    @AfterViews
    public void init(){

    }

    @Click(R.id.customerRegisterButton)
    public void customerRegister(){
        CustomerRegister_.intent(this).start();
    }

    @Click(R.id.customerLoginButton)
    public void customerLogin(){
        CustomerLogin_.intent(this).start();
    }
}