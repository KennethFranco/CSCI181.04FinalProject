package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
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
    SharedPreferences prefs;

    @ViewById(R.id.editText_AdminUsername)
    EditText uname;

    @ViewById(R.id.editText_AdminPassword)
    EditText pword;

    @ViewById(R.id.adminLoginButton)
    Button login;

    @ViewById(R.id.adminLoginBackLink)
    TextView back;

    @ViewById(R.id.adminRememberMe)
    CheckBox remember;

    @ViewById(R.id.adminClearButton)
    Button adminClearB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        String uuid = prefs.getString("adminUUID", null);
        Boolean rememberValue = prefs.getBoolean("adminrV", false);
        if (rememberValue == true){
            Admin result = realm.where(Admin.class)
                    .equalTo("uuid", ""+uuid)
                    .findFirst();

            if (result != null){
                String u = result.getUsername();
                String p = result.getPassword();

                uname.setText(u);
                pword.setText(p);
                remember.setChecked(true);

                adminClearB.setTextColor(Color.parseColor("#ffffff"));
                adminClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                adminClearB.setEnabled(true);
            }
            login.setEnabled(true);
        } else{
            login.setEnabled(false);
            login.setTextColor(Color.parseColor("#8b8b8b"));
            login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));

            adminClearB.setEnabled(false);
            adminClearB.setTextColor(Color.parseColor("#8b8b8b"));
            adminClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        }


        uname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || (pword.getText().toString().equals(""))){
                    login.setEnabled(false);
                    login.setTextColor(Color.parseColor("#8b8b8b"));
                    login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                } else {
                    login.setTextColor(Color.parseColor("#ffffff"));
                    login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    login.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || uname.getText().toString().equals("")){
                    login.setEnabled(false);
                    login.setTextColor(Color.parseColor("#8b8b8b"));
                    login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                } else
                {
                    login.setEnabled(true);
                    login.setTextColor(Color.parseColor("#ffffff"));
                    login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Click(R.id.adminClearButton)
    public void AdminClear(){
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove("adminUUID");
        edit.apply();
        Toast t = Toast.makeText(this, "Successfully cleared credentials.", Toast.LENGTH_LONG);
        uname.setText("");
        pword.setText("");
        remember.setChecked(false);

        adminClearB.setEnabled(false);
        adminClearB.setTextColor(Color.parseColor("#8b8b8b"));
        adminClearB.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));

        t.show();
    }
    @Click(R.id.adminLoginButton)
    public void AdminLogin(){
        realm = Realm.getDefaultInstance();
        String checkUname = uname.getText().toString();
        String checkPword = pword.getText().toString();
        Boolean checker = remember.isChecked();

        Admin username = realm.where(Admin.class).equalTo("username", checkUname).findFirst();

        if (username != null){
            if (username.getPassword().equals(checkPword)){

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("adminUUID", username.getUuid());
                edit.putBoolean("adminrV", checker);
                edit.apply();
                finish();
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
        finish();
        AdminRegister_.intent(this).start();
    }

    @Click(R.id.adminLoginBackLink)
    public void CancelRegister() {
        finish();
        MainActivity_.intent(this).start();
    }

}