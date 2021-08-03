package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_view_cart)
public class ViewCart extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    double sum;
    int counter;
    double catcherPrice;
    String combination;
    List<String> test = new ArrayList<>();
    List<String> test2 = new ArrayList<>();
    List<String> test3 = new ArrayList<>();
    List<String> test4 = new ArrayList<>();


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

    @ViewById(R.id.customerCartClearButton)
    Button customerCartClearB;

    @ViewById(R.id.viewCartAddButton)
    Button viewCartSubmitB;

    @ViewById(R.id.customerCartHomeLink)
    TextView customerCartHomeL;


    @AfterViews
    public void init(){
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        customerCartHomeL.setPaintFlags(customerCartHomeL.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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
        String uuidChecker = prefs.getString("uuid", null);
        RealmResults<Cart> list5 = realm.where(Cart.class).equalTo("user_uuid",uuidChecker).findAll();
        customerCartClearB.setEnabled(false);
        viewCartSubmitB.setEnabled(false);

        if (list5.isEmpty()==false){
            customerCartClearB.setEnabled(true);
            viewCartSubmitB.setEnabled(true);
        }
    }

    @Click(R.id.viewCartAddButton)
    public void submit(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String uuid = prefs.getString("uuid", null);
        RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();

        for (Cart c:list2){
            if (counter==1){
                combination = ""+c.getProduct_name().toString() + " x" + String.valueOf(c.getQuantity());
                counter+=1;

                Double priceZ = c.getTotal_price();
                test.add(c.getProduct_uuid());
                test2.add(String.valueOf(c.getQuantity()));
                test3.add(String.valueOf(c.getTotal_price()));

                test4.add(c.getProduct_uuid());
                test4.add(String.valueOf(c.getQuantity()));
                test4.add(String.valueOf(c.getTotal_price()));

                System.out.println(test);
            } else{
                combination = combination + ", " + c.getProduct_name().toString() + " x" + String.valueOf(c.getQuantity());
                test.add(c.getProduct_uuid());
                System.out.println(test);
            }
        }

        System.out.println(uuid);
        System.out.println(mydate);
        System.out.println(combination);
        System.out.println(test);



        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirmation of Order");
        alert.setMessage("Your order will be processed shortly after submission. Submit order?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RealmResults<Cart> list3 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();
                for (Cart c: list3){
                    String d = c.getProduct_uuid();
                    System.out.println(d);
                    System.out.println("ditolods");

                    Products result = realm.where(Products.class)
                            .equalTo("uuid", ""+d)
                            .findFirst();
                    System.out.println(result);
                    System.out.println(result.getTotalPrice());
                    int q = c.getQuantity();
                    Double tp = c.getTotal_price();
                    System.out.println(q);
                    System.out.println(tp);
                    int newQ = result.getTotalQty() + q;
                    Double newtp = result.getTotalPrice() + tp;

                    System.out.println(newQ);
                    System.out.println(newtp);


                    realm.beginTransaction();
                    result.setTotalPrice(newtp);
                    result.setTotalQty(newQ);
                    realm.commitTransaction();

                    System.out.println(q);
                    System.out.println(tp);
                    System.out.println(newQ);
                    System.out.println(newtp);
                }

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
                sum = 0.0;
                customerCartClearB.setEnabled(false);
                viewCartSubmitB.setEnabled(false);
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

    @Click(R.id.customerCartHomeLink)
    public void back(){
        finish();
        CustomerHome_.intent(this).start();
    }

    @Click(R.id.customerCartClearButton)

    public void clear(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Clearing Cart");
        alert.setMessage("Are you sure you want to clear your cart?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                String uuid = prefs.getString("uuid", null);
                RealmResults<Cart> list4 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();
                realm.beginTransaction();
                list4.deleteAllFromRealm();
                realm.commitTransaction();
                Toast.makeText(ViewCart.this, "Cart cleared.", Toast.LENGTH_SHORT).show();
                customerCartClearB.setEnabled(false);
                viewCartSubmitB.setEnabled(false);
                sum = 0.0;
                String message = "Final Price: "+sum;
                viewCartFP.setText(message);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViewCart.this, "Cart clearing cancelled", Toast.LENGTH_SHORT).show();
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

        if (qty!=1){
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

    }

    public void delete(Cart u){
        if (u.isValid())
        {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Are you sure you want to remove this item from your cart?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realm.beginTransaction();
                    u.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(ViewCart.this, "Item removed from cart.", Toast.LENGTH_SHORT).show();
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    String uuid = prefs.getString("uuid", null);
                    RealmResults<Cart> list2 = realm.where(Cart.class).equalTo("user_uuid",uuid).findAll();
                    sum = list2.sum("total_price").longValue();
                    String message = "Final Price: "+sum;
                    viewCartFP.setText(message);

                    String uuidChecker = prefs.getString("uuid", null);
                    RealmResults<Cart> list5 = realm.where(Cart.class).equalTo("user_uuid",uuidChecker).findAll();
                    customerCartClearB.setEnabled(false);
                    viewCartSubmitB.setEnabled(false);

                    if (list5.isEmpty()==false){
                        customerCartClearB.setEnabled(true);
                        viewCartSubmitB.setEnabled(true);
                    }
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
    }
}
