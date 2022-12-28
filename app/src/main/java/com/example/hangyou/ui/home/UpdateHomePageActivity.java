package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class UpdateHomePageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_home_page);
        initClickListener();
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user where account=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, account);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        try {
            resultSet.get().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EditText et_update_username = findViewById(R.id.update_home_page_username);
        try {
            et_update_username.setText(resultSet.get().getString("username"));
        } catch (SQLException e) {
            e.printStackTrace();
        };
        EditText et_update_phone = findViewById(R.id.update_home_page_phone);
        try {
            et_update_phone.setText(resultSet.get().getString("phone"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EditText et_update_description = findViewById(R.id.update_home_page_description);
        try {
            et_update_description.setText(resultSet.get().getString("description"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        EditText et_update_description = findViewById(R.id.update_home_page_description);
        String description = et_update_description.getText().toString();
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "update user set username=?, phone=?, description=? where account=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, phone);
                ps.setString(3, description);
                ps.executeUpdate();
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
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
