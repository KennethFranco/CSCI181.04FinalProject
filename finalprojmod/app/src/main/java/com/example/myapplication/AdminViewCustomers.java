package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_admin_view_customers)
public class AdminViewCustomers extends AppCompatActivity {
    Realm realm;

    @ViewById(R.id.admin_CustomerRecyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.customerAdminHomeLink)
    TextView customerAdminHomeL;

    @AfterViews
    public void init() {
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        customerAdminHomeL.setPaintFlags(customerAdminHomeL.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();

        RealmResults<Users> list = realm.where(Users.class).findAll();

        UsersAdapter adapter = new UsersAdapter(this, list, true);
        recyclerView.setAdapter(adapter);
    }

    public void ClearUser(Users u) {

        if (u.isValid())
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Are you sure?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realm.beginTransaction();
                    u.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(AdminViewCustomers.this, "User deleted", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AdminViewCustomers.this, "Deletion cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
        }

    }

    @Click(R.id.customerAdminHomeLink)
    public void back(){
        finish();
        AdminWelcome_.intent(this).start();
    }
}