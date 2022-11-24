package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.group.GuideActivity;
import com.example.hangyou.ui.login.LoginActivity;

public class UpdateHomePageActivity extends AppCompatActivity {
    SQLiteDatabase database;
    Cursor cursor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_home_page);
        initClickListener();
        DataBaseHelper helper=new DataBaseHelper(this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text, username text, description text, phone text)");
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        EditText et_update_username = findViewById(R.id.update_home_page_username);
        et_update_username.setText(cursor.getString(cursor.getColumnIndex("username")));
        EditText et_update_phone = findViewById(R.id.update_home_page_phone);
        et_update_phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
    }

    private void initClickListener() {
        findViewById(R.id.update_home_page_commit).setOnClickListener(v -> commit());
        findViewById(R.id.update_home_page_return).setOnClickListener(v -> returnToHomePage());
    }

    private void commit() {
        EditText et_update_username = findViewById(R.id.update_home_page_username);
        String username = et_update_username.getText().toString();
        EditText et_update_phone = findViewById(R.id.update_home_page_phone);
        String phone = et_update_phone.getText().toString();
        String account = cursor.getString(cursor.getColumnIndex("account"));
        database.execSQL("update user set username=?, phone=? where account=?", new String[]{username, phone, account});
        Intent intent = new Intent();
        intent.setClass(UpdateHomePageActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    private void returnToHomePage() {
        Intent intent = new Intent();
        intent.setClass(this, HomePageActivity.class);
        startActivity(intent);
    }

}
