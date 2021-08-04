package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;

@EActivity(R.layout.activity_edit_product)
public class EditProduct extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    Boolean wasChanged;

    @ViewById(R.id.editProductName)
    EditText editPN;

    @ViewById(R.id.editProductDescription)
    EditText editPD;

    @ViewById(R.id.editProductPrice)
    EditText editPP;

    @ViewById(R.id.product_back3)
    TextView editProductBackB;

    @ViewById(R.id.editProductSubmitChangesButton)
    Button editProductSubmitB;

    @ViewById(R.id.editProduct_ProductImage)
    ImageView product_image;

    @ViewById(R.id.editProduct_EditImage)
    ImageButton edit_image;

    byte[] byte_image;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void checkPermissions() {

        // REQUEST PERMISSIONS for Android 6+
        // THESE PERMISSIONS SHOULD MATCH THE ONES IN THE MANIFEST
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA

                )

                .withListener(new BaseMultiplePermissionsListener() {
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // all permissions accepted proceed
                            init();
                        } else {
                            // notify about permissions
                            toastRequirePermissions();
                        }
                    }
                })
                .check();

    }

    public void toastRequirePermissions() {
        Toast.makeText(this, "You must provide permissions for app to run", Toast.LENGTH_LONG).show();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(){
        wasChanged = false;
        realm = Realm.getDefaultInstance();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        SharedPreferences.Editor edit = prefs.edit();

        String product_uuid = prefs.getString("productUUID", null);
        System.out.println(product_uuid);
        Products result= realm.where(Products.class)
                .equalTo("uuid", product_uuid)
                .findFirst();

        File getImageDir = getExternalCacheDir();
        String uuid = prefs.getString("shopUUID", null);

        if (result.getImagePath() != null){
            File savedImage = new File(getImageDir, result.getImagePath());
            if (savedImage.exists()) {
                refreshImageView(savedImage);
            } else {
                Picasso.get()
                        .load(R.drawable.ic_launcher_background)
                        .into(product_image);
            }
        }

        editPN.setText(result.getProduct_name());
        editPD.setText(result.getProduct_description());
        editPP.setText(String.valueOf(result.getProduct_price()));

        if (editPN.getText().toString().equals("") || editPD.getText().toString().equals("") || editPP.getText().toString().equals("")){
            editProductSubmitB.setEnabled(false);
            editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            editProductSubmitB.setTextColor(Color.parseColor("#8b8b8b"));
        }
        else{
            editProductSubmitB.setEnabled(true);
            editProductSubmitB.setTextColor(Color.parseColor("#ffffff"));
            editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        }

        editPN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || editPD.getText().toString().equals("") || editPP.getText().toString().equals("")){
                    editProductSubmitB.setEnabled(false);
                    editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    editProductSubmitB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    editProductSubmitB.setEnabled(true);
                    editProductSubmitB.setTextColor(Color.parseColor("#ffffff"));
                    editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || editPN.getText().toString().equals("") || editPP.getText().toString().equals("")){
                    editProductSubmitB.setEnabled(false);
                    editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    editProductSubmitB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    editProductSubmitB.setEnabled(true);
                    editProductSubmitB.setTextColor(Color.parseColor("#ffffff"));
                    editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || editPN.getText().toString().equals("") || editPN.getText().toString().equals("")){
                    editProductSubmitB.setEnabled(false);
                    editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    editProductSubmitB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    editProductSubmitB.setEnabled(true);
                    editProductSubmitB.setTextColor(Color.parseColor("#ffffff"));
                    editProductSubmitB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static int REQUEST_CODE_IMAGE_SCREEN = 0;

    @Click(R.id.editProduct_EditImage)
    public void SelectPhoto() {
        AddProductPhoto_.intent(this).startForResult(REQUEST_CODE_IMAGE_SCREEN);
    }

    // SINCE WE USE startForResult(), code will trigger this once the next screen calls finish()
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_SCREEN) {
            if (responseCode == AddProductPhoto_.RESULT_CODE_IMAGE_TAKEN) {
                // receieve the raw JPEG data from ImageActivity
                // this can be saved to a file or save elsewhere like Realm or online
                byte[] jpeg = data.getByteArrayExtra("rawJpeg");

                try {
                    // save rawImage to file
                    File savedImage = refreshFile(jpeg);

                    byte_image = jpeg;

                    wasChanged = true;

                    // load file to the image view via picasso
                    refreshImageView(savedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private File refreshFile(byte[] jpeg) throws IOException {
        // this is the root directory for the images
        File getImageDir = getExternalCacheDir();

        // just a sample, normally you have a diff image name each time
        File savedImage = new File(getImageDir, "savedImage.jpeg");

        FileOutputStream fos = new FileOutputStream(savedImage);
        fos.write(jpeg);
        fos.close();
        return savedImage;
    }

    private File saveFile(byte[] jpeg, String name) throws IOException {
        // this is the root directory for the images
        File getImageDir = getExternalCacheDir();

        // just a sample, normally you have a diff image name each time
        File savedImage = new File(getImageDir, name);

        FileOutputStream fos = new FileOutputStream(savedImage);
        fos.write(jpeg);
        fos.close();
        return savedImage;
    }


    private void refreshImageView(File savedImage) {


        // this will put the image saved to the file system to the imageview
        Picasso.get()
                .load(savedImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(product_image);
    }

    @Click(R.id.editProductSubmitChangesButton)
    public void submit(){
        String n = editPN.getText().toString();
        String d = editPD.getText().toString();
        String p = editPP.getText().toString();

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("productUUID", null);
//        result2 is for getting the uuid and using it to set
        Products result2 = realm.where(Products.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        Products result3 = realm.where(Products.class).equalTo("product_name",n).findFirst();
//        result3 is for checking if the username is already in the database

        if (result3 != null){
            if (result2.getProduct_name().equals(n)){
                realm.beginTransaction();
                System.out.println(n);
                result2.setProduct_name(n);
                result2.setProduct_description(d);
                result2.setProduct_price(p);

                File getImageDir = getExternalCacheDir();

                if (result2.getImagePath() != null){
                    File savedImage = new File(getImageDir, result2.getImagePath());
                }

                if (wasChanged == true) {
                    try {
                        result2.setImagePath(System.currentTimeMillis() + ".jpeg");

                        File image = saveFile(byte_image, result2.getImagePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                realm.commitTransaction();
                Toast t = Toast.makeText(this, "Successfully updated product details!", Toast.LENGTH_LONG);
                t.show();
                finish();
                ViewProducts_.intent(this).start();
            }
            else{
                Toast t = Toast.makeText(this, "This product name has already been taken! Please choose a new one.", Toast.LENGTH_LONG);
                t.show();
            }
        } else{
            realm.beginTransaction();
            result2.setProduct_name(n);
            result2.setProduct_description(d);
            result2.setProduct_price(p);

            File getImageDir = getExternalCacheDir();

            if (result2.getImagePath() != null){
                File savedImage = new File(getImageDir, result2.getImagePath());
            }

            if (wasChanged == true) {
                try {
                    result2.setImagePath(System.currentTimeMillis() + ".jpeg");

                    File image = saveFile(byte_image, result2.getImagePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            realm.commitTransaction();
            Toast t = Toast.makeText(this, "Successfully updated product details!", Toast.LENGTH_LONG);
            t.show();
            finish();
            ViewProducts_.intent(this).start();
        }
    }

    @Click(R.id.product_back3)
    public void back(){
        finish();
        ViewProducts_.intent(this).start();
    }
}