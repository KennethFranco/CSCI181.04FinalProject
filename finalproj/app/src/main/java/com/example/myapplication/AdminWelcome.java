package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_admin_welcome)
public class AdminWelcome extends AppCompatActivity {

    @ViewById(R.id.viewAllCustomers)
    Button viewCustomers;

    @ViewById(R.id.viewAllShops)
    Button viewShops;

    @AfterViews
    public void init() {

    }

    @Click(R.id.viewAllCustomers)
    public void ViewCustomers() {
        finish();
        AdminViewCustomers_.intent(this).start();
    }

    @Click(R.id.viewAllShops)
    public void ViewShops() {
        finish();
        AdminViewShops_.intent(this).start();
    }

    @Click(R.id.adminHomeScreenBackButton)
    public void exit(){
        finish();
        MainActivity_.intent(this).start();
        Toast t = Toast.makeText(this, "You've been signed out", Toast.LENGTH_LONG);
        t.show();
    }
}