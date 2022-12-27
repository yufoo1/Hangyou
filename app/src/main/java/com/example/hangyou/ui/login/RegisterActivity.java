package com.example.hangyou.ui.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
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
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text, username text, description text, phone text, gender text)");
    }

    private void register() {
        EditText et_account = findViewById(R.id.register_account);
        String account = et_account.getText().toString();
        EditText et_password = findViewById(R.id.register_password);
        String password = et_password.getText().toString();
        EditText et_username = findViewById(R.id.register_username);
        String username = et_username.getText().toString();
        EditText et_phone = findViewById(R.id.register_phone);
        String phone = et_phone.getText().toString();
        String gender;
        RadioButton rb_gender_male = findViewById(R.id.register_gender_male);
        RadioButton rb_gender_female = findViewById(R.id.register_gender_female);
        if(rb_gender_male.isChecked()) {
            gender = "男";
        } else {
            gender = "女";
        }
        String description = "这个人很懒，什么都留下了";
        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        if(cursor.moveToFirst()) {
            Toast.makeText(this, "账号已被注册，请重新输入", Toast.LENGTH_SHORT).show();
            et_account.setText("");
        } else {
            if(account.equals("")) {
                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            } else if(password.equals("")) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            } else if (phone.equals("")) {
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            } else {
                database.execSQL("insert into user(account, password, username, description, phone, gender) values (?, ?, ?, ?, ?, ?)", new Object[]{account, password, username, description, phone, gender});
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
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
