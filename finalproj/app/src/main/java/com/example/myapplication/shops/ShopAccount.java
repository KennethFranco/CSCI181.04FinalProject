package com.example.myapplication.shops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_shop_account)
public class ShopAccount extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    @ViewById(R.id.shopAccountName)
    EditText shopAccountN;

    @ViewById(R.id.shopAccountDescription)
    EditText shopAccountD;

    @ViewById(R.id.shopAccountUsername)
    EditText shopAccountU;

    @ViewById(R.id.shopAccountPassword)
    EditText shopAccountP;

    
    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        String uuid = prefs.getString("shopUUID", null);

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

        realm.beginTransaction();
        result2.setShopUsername(u);
        result2.setShopPassword(p);
        result2.setShopDescription(d);
        result2.setShopName(n);

        realm.commitTransaction();
        Toast t = Toast.makeText(this, "Successfully updated shop account details!", Toast.LENGTH_LONG);
        t.show();
        finish();
    }
}