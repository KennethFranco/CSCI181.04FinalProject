package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_add_product)
public class AddProduct extends AppCompatActivity {

    Realm realm;

    SharedPreferences prefs;

    byte[] product_image;

    @ViewById
    Button addproduct;

    @ViewById(R.id.product_back)
    TextView back;

    @ViewById(R.id.editText_ProductName)
    EditText prod_name;

    @ViewById(R.id.editText_ProductNumber)
    EditText prod_num;

    @ViewById(R.id.addProductDescription)
    EditText addProductD;

    @ViewById(R.id.product_selectPhoto)
    ImageButton selectPhoto;

    @ViewById(R.id.product_ImageView)
    ImageView productImage;

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

    public void init() {
        realm = Realm.getDefaultInstance();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);


        // check if savedImage.jpeg exists in path
        // load via picasso if exists

        File getImageDir = getExternalCacheDir();
        File savedImage = new File(getImageDir, "savedImage.jpeg");

        if (savedImage.exists()) {
//            refreshImageView(savedImage);
        } else {
            Picasso.get()
                    .load(R.drawable.ic_launcher_background)
                    .into(productImage);
        }

        if (prod_name.getText().toString().equals("") || prod_num.getText().toString().equals("") || addProductD.getText().toString().equals("")) {
            addproduct.setEnabled(false);
        } else {
            addproduct.setEnabled(true);
        }

        prod_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0 || prod_num.getText().toString().equals("") || addProductD.getText().toString().equals("")) {
                    addproduct.setEnabled(false);
                } else {
                    addproduct.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        prod_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0 || prod_name.getText().toString().equals("") || addProductD.getText().toString().equals("")) {
                    addproduct.setEnabled(false);
                } else {
                    addproduct.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addProductD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0 || prod_num.getText().toString().equals("") || prod_name.getText().toString().equals("")) {
                    addproduct.setEnabled(false);
                } else {
                    addproduct.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public static int REQUEST_CODE_IMAGE_SCREEN = 0;

    @Click(R.id.product_selectPhoto)
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

                    product_image = jpeg;

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
                .into(productImage);
    }

    // add product stuff
    @Click(R.id.addproduct)
    public void AddProduct() {

        String name = prod_name.getText().toString();
        String price = prod_num.getText().toString();
        String description = addProductD.getText().toString();
        String prod_uuid = UUID.randomUUID().toString();

        Products result = realm.where(Products.class).equalTo("product_name", name).findFirst();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String prefsID = prefs.getString("shopUUID", null);

//        valueUUID is shops uuid
        Shops valueUUID = realm.where(Shops.class).equalTo("uuid", prefsID).findFirst();

        System.out.println(result);

        if (name.equals("")) {
            Toast t = Toast.makeText(this, "Name cannot be left blank", Toast.LENGTH_LONG);
            t.show();
        } else if (price.equals("")) {
            Toast t = Toast.makeText(this, "Price cannot be left blank", Toast.LENGTH_LONG);
            t.show();
        } else if (result == null) {
            Products newProduct = new Products();
            newProduct.setUuid(prod_uuid);
            newProduct.setProduct_name(name);
            newProduct.setProduct_price(price);
            newProduct.setProduct_description(description);
            newProduct.setTotalQty(0);
            newProduct.setTotalPrice(0.0);
            newProduct.setShop_name(valueUUID.getShopName());
            newProduct.setShop_uuid(valueUUID.getUuid());


            try {
                newProduct.setImagePath(System.currentTimeMillis() + ".jpeg");

                File image = saveFile(product_image, newProduct.getImagePath());
            } catch (Exception e) {
                e.printStackTrace();
            }

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newProduct);
            realm.commitTransaction();

            Toast t = Toast.makeText(this, "Product Saved", Toast.LENGTH_LONG);
            t.show();

            ViewProducts_.intent(this).start();


        } else {

            Toast t = Toast.makeText(this, "Product already exists", Toast.LENGTH_LONG);
            t.show();
        }


    }

    @Click(R.id.product_back)
    public void back() {
        finish();
        ViewProducts_.intent(this).start();
    }
}
