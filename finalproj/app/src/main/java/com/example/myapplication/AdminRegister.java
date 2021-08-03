package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;

@EActivity(R.layout.activity_admin_register)
public class AdminRegister extends AppCompatActivity {

    Realm realm;
    @ViewById(R.id.editText_AdminRegisterUname)
    EditText uname;

    @ViewById(R.id.editText_AdminRegisterPword)
    EditText pword;

    @ViewById(R.id.editText_AdminRegisterConfirm)
    EditText confirm;

    @ViewById(R.id.adminConfirmRegister)
    Button register;

    @ViewById(R.id.adminRegisterBackLink)
    TextView cancel;

    @AfterViews
    public void init() {

    }

    @Click(R.id.adminConfirmRegister)
    public void Register() {
        realm = Realm.getDefaultInstance();
        String checkUname = uname.getText().toString();
        String checkPassword = pword.getText().toString();
        String checkConfirm = confirm.getText().toString();
        String uuid = UUID.randomUUID().toString();

        Admin result = realm.where(Admin.class).equalTo("username", checkUname).findFirst();

        if (checkUname.equals("")){
            Toast t = Toast.makeText(this, "Name cannot be left blank", Toast.LENGTH_LONG);
            t.show();
        }
        else if (checkPassword.equals("") && checkConfirm.equals("")){
            Toast t = Toast.makeText(this, "Please put a password", Toast.LENGTH_LONG);
            t.show();
        }
        else if (result != null) {
            Toast t = Toast.makeText(this, "This account already exists", Toast.LENGTH_LONG);
            t.show();
        }
        else if ((result == null) && (checkPassword.equals(checkConfirm))){

            Admin newAdmin = new Admin();
            newAdmin.setUuid(uuid);
            newAdmin.setUsername(checkUname);
            newAdmin.setPassword(checkPassword);

            long count = 0;

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newAdmin);
            realm.commitTransaction();

            count = realm.where(Admin.class).count();

            Toast t = Toast.makeText(this, "Login Saved, Total Admins: " + count, Toast.LENGTH_LONG);
            t.show();

            finish();
            AdminLogin_.intent(this).start();

        }
        else {
            Toast t = Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Click(R.id.adminRegisterBackLink)
    public void Cancel() {
        finish();
        MainActivity_.intent(this).start();
    }

    @Click(R.id.adminRegisterLoginLink)
    public void Login() {
        finish();
        AdminLogin_.intent(this).start();
    }
}