package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_admin_login)
public class AdminLogin extends AppCompatActivity {

    Realm realm;

    @ViewById(R.id.editText_AdminUsername)
    EditText uname;

    @ViewById(R.id.editText_AdminPassword)
    EditText pword;

    @ViewById(R.id.adminLoginButton)
    Button login;

    @ViewById(R.id.adminLoginRegisterLink)
    TextView register;

    @ViewById(R.id.adminLoginBackLink)
    TextView bback;

    @AfterViews
    public void init() {
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        bback.setPaintFlags(bback.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Click(R.id.adminLoginButton)
    public void AdminLogin(){
        realm = Realm.getDefaultInstance();
        String checkUname = uname.getText().toString();
        String checkPword = pword.getText().toString();

        Admin username = realm.where(Admin.class).equalTo("username", checkUname).findFirst();

        if (username != null){
            if (username.getPassword().equals(checkPword)){

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("adminUUID", username.getUuid());
                edit.apply();

                AdminWelcome_.intent(this).start();
            }
            else {
                Toast t = Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG);
                t.show();
            }
        }
        else if (username == null) {
            Toast t = Toast.makeText(this, "Username is incorrect", Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Click(R.id.adminLoginRegisterLink)
    public void register() {
        AdminRegister_.intent(this).start();
    }

    @Click(R.id.adminLoginBackLink)
    public void back(){
        finish();
        MainActivity_.intent(this).start();}

}