package com.example.myapplication.customers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.customers.CustomerAccount_;
import com.example.myapplication.customers.CustomerShops_;
import com.example.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import io.realm.Realm;

@EActivity(R.layout.activity_customer_home)
public class CustomerHome extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
    }

    @Click(R.id.customerHomeAccountButton)
    public void account(){
        CustomerAccount_.intent(this).start();
    }

    @Click(R.id.customerHomeShopsButton)
    public void shops(){
        String address = prefs.getString("address", "");
        String fullName = prefs.getString("fullName", "");
        String contactNumber = prefs.getString("contactNumber", "");

        if ((address.equals("")) || (contactNumber.equals("")) || fullName.equals(""))
        {
            Toast t = Toast.makeText(this, "Please finish putting in your account details! You can find these under the Account tab.", Toast.LENGTH_LONG);
            t.show();
        }
        else{
            CustomerShops_.intent(this).start();
        }
    }


}