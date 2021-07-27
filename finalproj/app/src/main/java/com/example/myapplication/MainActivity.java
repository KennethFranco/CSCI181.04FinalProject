package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.CustomerWelcome_;
import com.example.myapplication.ShopOwnerWelcomePage_;

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