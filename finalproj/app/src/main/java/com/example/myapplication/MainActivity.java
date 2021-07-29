package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.customerButton)
    Button customerB;

    @ViewById
    Button shopButton;

    @ViewById(R.id.adminButton)
    Button admin;


    @AfterViews
    // NOTE: @AfterViews will not be put here since we want permissions asked first
    public void init() {

    }




//    private void refreshImageView(File savedImage) {
//
//
//        // this will put the image saved to the file system to the imageview
//        Picasso.get()
//                .load(savedImage)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .into(imageView);
//    }

    @Click(R.id.customerButton)
    public void customer() {
        CustomerWelcome_.intent(this).start();
    }

    @Click(R.id.shopButton)
    public void shopOwner() {
        ShopOwnerWelcomePage_.intent(this).start();
    }

    @Click(R.id.adminButton)
    public void admin() {
        AdminLogin_.intent(this).start();
    }
}