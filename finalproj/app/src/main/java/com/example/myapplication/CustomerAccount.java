package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_customer_account2)
public class CustomerAccount extends AppCompatActivity {

    SharedPreferences prefs;
    Realm realm;

    @ViewById(R.id.customerAccountUsername)
    EditText customerAccountU;

    @ViewById(R.id.customerAccountPassword)
    EditText customerAccountP;

    @ViewById(R.id.customerAccountContactNumber)
    EditText customerAccountCN;

    @ViewById(R.id.customerAccountFullName)
    EditText customerAccountFN;

    @ViewById(R.id.customerAccountAddress)
    EditText customerAccountA;

    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("uuid", null);

        Users result= realm.where(Users.class)
                .equalTo("uuid", ""+uuid)
                .findFirst();

        String username = result.getUsername();
        String password = result.getPassword();
        String address = result.getAddress();
        String contactNumber = result.getContactNumber();
        String fullName = result.getFullName();

        customerAccountU.setText(username);
        customerAccountP.setText(password);
        customerAccountA.setText(address);
        customerAccountCN.setText(contactNumber);

        customerAccountFN.setText(fullName);

    }

    @Click(R.id.customerAccountSaveButton)
    public void save(){

        int checker = 0;

        if (checker == 0){
            String u = customerAccountU.getText().toString();
            String p = customerAccountP.getText().toString();
            String a = customerAccountA.getText().toString();
            String fN = customerAccountFN.getText().toString();
            String cN = customerAccountCN.getText().toString();

            prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();

            String uuid = prefs.getString("uuid", null);
            Users result2 = realm.where(Users.class)
                    .equalTo("uuid", ""+uuid)
                    .findFirst();

            realm.beginTransaction();
            result2.setUsername(u);
            result2.setPassword(p);
            result2.setContactNumber(cN);
            result2.setAddress(a);
            result2.setFullName(fN);

            edit.putString("contactNumber", cN);
            edit.putString("address", a);
            edit.putString("fullName", fN);
            edit.apply();
            realm.commitTransaction();
            Toast t = Toast.makeText(this, "Successfully updated account details!", Toast.LENGTH_LONG);
            t.show();
            finish();

        }
        else{
            Toast t = Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_LONG);
            t.show();
        }




    }
}