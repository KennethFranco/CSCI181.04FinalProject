package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
    }

    @Click(R.id.viewAllCustomers)
    public void ViewCustomers() {
        AdminViewCustomers_.intent(this).start();
    }

    @Click(R.id.viewAllShops)
    public void ViewShops() {
        AdminViewShops_.intent(this).start();
    }

    @Click(R.id.adminHomeScreenBackButton)
    public void back(){
        Toast t = Toast.makeText(this, "See you next time!", Toast.LENGTH_LONG);
        t.show();
        finish();
        MainActivity_.intent(this).start();
    }
}