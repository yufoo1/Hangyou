package com.example.hangyou.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;


public class LoginActivity extends AppCompatActivity{
    ResultSet rs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        initClickListener();
    }

    public void login() throws SQLException {
        EditText et_account = findViewById(R.id.account);
        String account = et_account.getText().toString();
        EditText et_password = findViewById(R.id.password);
        String password = et_password.getText().toString();
        if(account.equals("")) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
        } else if(password.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            AtomicBoolean flag1 = new AtomicBoolean(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user where account=? and password=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    ps.setString(2, password);
                    rs = ps.executeQuery();
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while(!flag1.get());
            if (rs.next()) {
                System.out.println("登录成功");
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("account", account);
                editor.apply();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, GroupActivity.class);
                startActivity(intent);
            } else {
                AtomicBoolean flag2 = new AtomicBoolean(false);
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "select * from user where account=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, account);
                        rs = ps.executeQuery();
                        flag2.set(true);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
                if(!flag2.get()) {
                    Toast.makeText(this, "请重新输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void register(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void initClickListener() {
        Button bt_login = findViewById(R.id.login);
        bt_login.setOnClickListener(v -> {
            try {
                login();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Button bt_register = findViewById(R.id.register);
        bt_register.setOnClickListener(v -> register());
    }
}
