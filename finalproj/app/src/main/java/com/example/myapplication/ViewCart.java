package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.time.Instant;
import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_view_cart)
public class ViewCart extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    double sum;
    int counter;
    String combination;

    @ViewById(R.id.viewCartRecyclerView)
    RecyclerView rV;

    @ViewById(R.id.viewCartQuantity)
    TextView viewCartQ;

    @ViewById(R.id.viewCartProductName)
    TextView viewCartPN;

    @ViewById(R.id.viewCartShopName)
    TextView viewCartSN;

    @ViewById(R.id.viewCartTotalPrice)
    TextView viewCartTP;

    @ViewById(R.id.viewCartAddQtyButton)
    Button viewCartAddB;

    @ViewById(R.id.viewCartMinusQtyButton)
    Button viewCartMinusB;

    @ViewById(R.id.viewCartDeleteButton)
    Button viewCartDeleteB;

    @ViewById(R.id.viewCartFinalPrice)
    TextView viewCartFP;

    @AfterViews
    public void init(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", null);

        realm = Realm.getDefaultInstance();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rV.setLayoutManager(mLayoutManager);

        RealmResults<Cart> list = realm.where(Cart.class).equalTo("user_uuid", uuid).findAll();
        CartAdapter adapter = new CartAdapter(this, list, true);
        rV.setAdapter(adapter);

        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();

        sum = list2.sum("total_price").longValue();

        String message = "Final Price: "+sum;
        viewCartFP.setText(message);
        counter = 1;
        combination = "";
    }

    @Click(R.id.viewCartAddButton)
    public void finish(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String uuid = prefs.getString("uuid", null);
        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();

        for (Cart c:list2){
            if (counter==1){
                combination = ""+c.getProduct_name().toString() + " x" + c.getTotal_price().toString();
                counter+=1;
            } else{
                combination = combination + ", " + c.getProduct_name().toString() + " x" + c.getTotal_price().toString();
            }
        }

        System.out.println(uuid);
        System.out.println(mydate);
        System.out.println(combination);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirmation of Order");
        alert.setMessage("Your order will be processed shortly after submission. Submit order?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realm.beginTransaction();
                Orders newOrder = new Orders();
                newOrder.setCustomer_uuid(uuid);
                newOrder.setDate(mydate);
                newOrder.setOrder_name(combination);
                newOrder.setOrder_price(sum);
                newOrder.setUuid(UUID.randomUUID().toString());
                realm.copyToRealmOrUpdate(newOrder);
                list2.deleteAllFromRealm();
                realm.commitTransaction();
                Toast.makeText(ViewCart.this, "Order successfully created! Cart cleared. Shop with us again!", Toast.LENGTH_LONG).show();
                String message = "Final Price: "+sum;
                viewCartFP.setText(message);
                }
            });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViewCart.this, "Deletion cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
    }


    public void add(Cart u)
    {
        int qty = u.getQuantity();
        Double totalPrice = u.getTotal_price();
        Double individualPrice = u.getIndividual_price();

        qty+=1;
        totalPrice+=individualPrice;

        realm.beginTransaction();
        u.setQuantity(qty);
        u.setTotal_price(totalPrice);
        realm.commitTransaction();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", null);
        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();
        sum = list2.sum("total_price").longValue();
        String message = "Final Price: "+sum;
        viewCartFP.setText(message);

    }

    public void minus(Cart u){
        int qty = u.getQuantity();
        Double totalPrice = u.getTotal_price();
        Double individualPrice = u.getIndividual_price();

        qty-=1;
        totalPrice-=individualPrice;

        realm.beginTransaction();
        u.setQuantity(qty);
        u.setTotal_price(totalPrice);
        realm.commitTransaction();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuid", null);
        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();
        sum = list2.sum("total_price").longValue();
        String message = "Final Price: "+sum;
        viewCartFP.setText(message);
    }

    public void delete(Cart u){

    }
}
