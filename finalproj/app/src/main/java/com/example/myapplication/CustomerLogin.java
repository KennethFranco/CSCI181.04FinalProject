package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.CustomerHome_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_customer_login)
public class CustomerLogin extends AppCompatActivity {

    SharedPreferences prefs;

    Realm realm;
    @ViewById(R.id.customerLoginUsername)
    EditText customerLoginU;

    @ViewById(R.id.customerLoginPassword)
    EditText customerLoginP;

    @ViewById(R.id.customerLoginRememerMe)
    CheckBox customerLoginRemember;

    @ViewById(R.id.customerLoginSigninButton)
    Button customerLoginSigninB;

    @ViewById(R.id.customerLoginCancelButton)
    Button customerLoginCancelB;

    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("uuid", null);
        Boolean rememberValue = prefs.getBoolean("rV", false);
        if (rememberValue == true){
            Users result = realm.where(Users.class)
                    .equalTo("uuid", ""+uuid)
                    .findFirst();

            if (result != null){
                String u = result.getUsername();
                String p = result.getPassword();

                customerLoginU.setText(u);
                customerLoginP.setText(p);
                customerLoginRemember.setChecked(true);
            }

        }
    }

    @Click(R.id.customerLoginSigninButton)
    public void signin(){
        SharedPreferences.Editor edit = prefs.edit();
        String u = customerLoginU.getText().toString();
        String p = customerLoginP.getText().toString();
        boolean rememberValue = customerLoginRemember.isChecked();

        Users result = realm.where(Users.class)
                .equalTo("username", ""+u)
                .findFirst();

        if (result == null){
            Toast t = Toast.makeText(this, "No user found, please sign up.", Toast.LENGTH_LONG);
            t.show();
        }
        else if (p.equals(result.getPassword())){
            edit.putString("uuid", result.getUuid());
            edit.putBoolean("rV", rememberValue);
            edit.apply();
            CustomerHome_.intent(this).start();
        }
        else{
            Toast t = Toast.makeText(this, "Invalid password given.", Toast.LENGTH_LONG);
            t.show();
        }

    }

    @Click(R.id.customerLoginCancelButton)
    public void cancel(){
        finish();
    }

}