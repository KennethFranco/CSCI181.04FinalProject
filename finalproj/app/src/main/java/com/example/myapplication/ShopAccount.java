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

@EActivity(R.layout.activity_shop_account)
public class ShopAccount extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    byte[] shop_photo;

    Boolean wasChanged;

    @ViewById(R.id.shopAccountName)
    EditText shopAccountN;

    @ViewById(R.id.shopAccountDescription)
    EditText shopAccountD;

    @ViewById(R.id.shopAccountUsername)
    EditText shopAccountU;

    @ViewById(R.id.shopAccountPassword)
    EditText shopAccountP;

    @ViewById(R.id.shopAccountSaveButton)
    Button shopAccountSaveB;

    @ViewById(R.id.shopAccountHomeLink)
    TextView shopAccountBackB;

    @ViewById(R.id.addShopPhoto)
    ImageButton addShopPhoto;

    @ViewById(R.id.imageView2)
    ImageView shopPhoto;

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
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        // check if savedImage.jpeg exists in path
        // load via picasso if exists
        File getImageDir = getExternalCacheDir();
        String uuid = prefs.getString("shopUUID", null);

        Shops result2 = realm.where(Shops.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        if (result2.getImagePath() != null){
            File savedImage = new File(getImageDir, result2.getImagePath());
            if (savedImage.exists()) {
                refreshImageView(savedImage);
            } else {
                Picasso.get()
                        .load(R.drawable.ic_launcher_background)
                        .into(shopPhoto);
            }
        }



        Shops result= realm.where(Shops.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        String aN = result.getShopName();
        String aD = result.getShopDescription();
        String aU = result.getShopUsername();
        String aP = result.getShopPassword();

        shopAccountN.setText(aN);
        shopAccountD.setText(aD);
        shopAccountU.setText(aU);
        shopAccountP.setText(aP);

        if (shopAccountN.getText().toString().equals("") || shopAccountD.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
            shopAccountSaveB.setEnabled(false);
            shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            shopAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
        } else{
            shopAccountSaveB.setEnabled(true);
            shopAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
            shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        }

        shopAccountN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountD.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    shopAccountSaveB.setEnabled(true);
                    shopAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        shopAccountD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountN.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    shopAccountSaveB.setEnabled(true);
                    shopAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        shopAccountU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountD.getText().toString().equals("") || shopAccountN.getText().toString().equals("") || shopAccountP.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    shopAccountSaveB.setEnabled(true);
                    shopAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        shopAccountP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || shopAccountD.getText().toString().equals("") || shopAccountU.getText().toString().equals("") || shopAccountN.getText().toString().equals("")){
                    shopAccountSaveB.setEnabled(false);
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                    shopAccountSaveB.setTextColor(Color.parseColor("#8b8b8b"));
                } else {
                    shopAccountSaveB.setEnabled(true);
                    shopAccountSaveB.setTextColor(Color.parseColor("#ffffff"));
                    shopAccountSaveB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }

    public static int REQUEST_CODE_IMAGE_SCREEN = 0;

    @Click(R.id.addShopPhoto)
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

                    shop_photo = jpeg;

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
                .into(shopPhoto);
    }

    @Click(R.id.shopAccountSaveButton)
    public void save(){
        String u = shopAccountU.getText().toString();
        String p = shopAccountP.getText().toString();
        String n = shopAccountN.getText().toString();
        String d = shopAccountD.getText().toString();

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("shopUUID", null);
        Shops result2 = realm.where(Shops.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        Shops result3 = realm.where(Shops.class).equalTo("shopUsername",u).findFirst();

        if (result3 != null){
            if (result2.getShopUsername().equals(u)){
                realm.beginTransaction();
                result2.setShopUsername(u);
                result2.setShopPassword(p);
                result2.setShopDescription(d);
                result2.setShopName(n);
                result2.setFirstTime(false);

                File getImageDir = getExternalCacheDir();

                if (result2.getImagePath() != null){
                    File savedImage = new File(getImageDir, result2.getImagePath());
                }

                if (wasChanged == true) {
                    try {
                        result2.setImagePath(System.currentTimeMillis() + ".jpeg");

                        File image = saveFile(shop_photo, result2.getImagePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                realm.commitTransaction();
                Toast t = Toast.makeText(this, "Successfully updated shop account details!", Toast.LENGTH_LONG);
                t.show();
                ShopHomeScreen_.intent(this).start();
            }
            else{
                Toast t = Toast.makeText(this, "This username has already been taken! Please choose a new one.", Toast.LENGTH_LONG);
                t.show();
            }
        }
        else{
            realm.beginTransaction();
            result2.setShopUsername(u);
            result2.setShopPassword(p);
            result2.setShopDescription(d);
            result2.setShopName(n);
            result2.setFirstTime(false);

            File getImageDir = getExternalCacheDir();

            if (result2.getImagePath() != null){
                File savedImage = new File(getImageDir, result2.getImagePath());
            }

            if (wasChanged == true) {
                try {
                    result2.setImagePath(System.currentTimeMillis() + ".jpeg");

                    File image = saveFile(shop_photo, result2.getImagePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            realm.commitTransaction();
            Toast t = Toast.makeText(this, "Successfully updated shop account details!", Toast.LENGTH_LONG);
            t.show();
            ShopHomeScreen_.intent(this).start();
        }

    }

    @Click(R.id.shopAccountHomeLink)
    public void back(){
        String uuid = prefs.getString("shopUUID", null);
        Shops result2 = realm.where(Shops.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        Boolean checker = result2.getFirstTime();

        if (checker==true){
            finish();
            ShopOwnerWelcomePage_.intent(this).start();
        } else{
            finish();
            ShopHomeScreen_.intent(this).start();
        }
    }
}