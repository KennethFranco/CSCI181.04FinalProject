package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.CustomerLogin_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_customer_register)
public class CustomerRegister extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    @ViewById(R.id.customerRegisterUsername)
    EditText customerRegisterU;

    @ViewById(R.id.customerRegisterPassword)
    EditText customerRegisterP;

    @ViewById(R.id.customerRegisterConfirmPassword)
    EditText customerRegisterCP;

    @ViewById(R.id.customerRegisterSigninButton)
    Button customerRegisterSigninB;

    @ViewById(R.id.customerRegisterCancelButton)
    Button customerRegisterCancelB;

    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

    }

    @Click(R.id.customerRegisterSigninButton)
    public void signin(){
        String checkUsername = customerRegisterU.getText().toString();
        String pw1 = customerRegisterP.getText().toString();
        String pw2 = customerRegisterCP.getText().toString();

        if (checkUsername.equals("")) {
            Toast t = Toast.makeText(this, "Name must not be blank", Toast.LENGTH_LONG);
            t.show();
        } else if(pw1.equals(pw2)) {
            RealmResults<Users> result = realm.where(Users.class).equalTo("username", ""+checkUsername).findAll();

            if (result.isEmpty()){
                Users u = new Users();
                u.setUuid(UUID.randomUUID().toString());
                u.setUsername(checkUsername);
                u.setPassword(pw1);
                u.setAddress("");
                u.setFullName("");
                u.setContactNumber("");

//                SharedPreferences.Editor edit = prefs.edit();
//                edit.putString("contactNumber", "");
//                edit.putString("address", "");
//                edit.putString("fullName", "");
//                edit.apply();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(u);
                realm.commitTransaction();
                long count = realm.where(Users.class).count();
                Toast t = Toast.makeText(this, "Successfully created an account! You may now login!"+count, Toast.LENGTH_LONG);
                t.show();
                finish();
                CustomerLogin_.intent(this).start();
            }
            else{
                Toast t = Toast.makeText(this, "The username you are trying to use already exists. Please login if you already have an account.", Toast.LENGTH_LONG);
                t.show();
            }

        } else {
            Toast t = Toast.makeText(this, "Confirm password does not match", Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Click(R.id.customerRegisterCancelButton)
    public void cancel(){
        finish();
    }
}