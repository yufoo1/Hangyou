package com.example.hangyou.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;


public class LoginActivity extends AppCompatActivity{
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        initClickListener();
        DataBaseHelper helper=new DataBaseHelper(LoginActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text)");
    }

    public void login() {
        EditText et_account = findViewById(R.id.account);
        String account = et_account.getText().toString();
        EditText et_password = findViewById(R.id.password);
        String password = et_password.getText().toString();
        Cursor cursor = database.rawQuery("select * from user where account=? and password=?", new String[]{account, password});
        if (cursor.moveToFirst()) {
            System.out.println("登录成功");
            SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("account", account);
            editor.apply();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, GroupActivity.class);
            startActivity(intent);
        } else {
            System.out.println("登录失败");
        }
        cursor.close();
    }

    public void register(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void initClickListener() {
        Button bt_login = findViewById(R.id.login);
        bt_login.setOnClickListener(v -> {
            login();
        });
        Button bt_register = findViewById(R.id.register);
        bt_register.setOnClickListener(v -> {
            register();
        });
    }
}
