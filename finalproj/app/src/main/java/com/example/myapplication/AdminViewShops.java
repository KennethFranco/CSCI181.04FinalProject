package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_admin_view_shops)
public class AdminViewShops extends AppCompatActivity {

    Realm realm;

    @ViewById(R.id.admin_ViewShops)
    RecyclerView recyclerView;

    @AfterViews
    public void init() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();

        RealmResults<Shops> list = realm.where(Shops.class).findAll();

        AdminShopsAdapter adapter = new AdminShopsAdapter(this, list, true);
        recyclerView.setAdapter(adapter);
    }

    public void view_shops(Shops s) {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("adminviewShopUUID", s.getUuid());
        edit.apply();

        AdminShopsProducts_.intent(this).start();
    }

    public void clear_shops(Shops s) {
        if (s.isValid())
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Are you sure?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realm.beginTransaction();
                    s.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(AdminViewShops.this, "User deleted", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AdminViewShops.this, "Deletion cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
        }

    }

    @Click(R.id.shopsAdminHomeLink)
    public void exit(){
        finish();
        AdminWelcome_.intent(this).start();
    }
}