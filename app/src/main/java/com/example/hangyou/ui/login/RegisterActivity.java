package com.example.hangyou.ui.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;

public class RegisterActivity extends AppCompatActivity {
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        DataBaseHelper helper=new DataBaseHelper(RegisterActivity.this);
        initClickListener();
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text, username text, description text)");
    }

    private void register() {
        EditText et_account = findViewById(R.id.register_account);
        String account = et_account.getText().toString();
        EditText et_password = findViewById(R.id.register_password);
        String password = et_password.getText().toString();
        EditText et_username = findViewById(R.id.register_username);
        String username = et_username.getText().toString();
        String description = "这个人很懒，什么都留下了";
        database.execSQL("insert into user(account, password, username, description) values (?, ?, ?, ?)", new Object[]{account, password, username, description});
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void returnToLogin() {
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.register_register).setOnClickListener(v -> register());
        findViewById(R.id.register_return).setOnClickListener(v -> returnToLogin());
    }
}
