package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.customerButton)
    Button customerB;

    @ViewById
    Button shopButton;



    @AfterViews
    public void init(){
    }

    @Click(R.id.customerButton)
    public void customer(){
        CustomerWelcome_.intent(this).start();
    }

    @Click(R.id.shopButton)
    public void shopOwner() {
        ShopOwnerWelcomePage_.intent(this).start();
    }
}