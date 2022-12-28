package com.example.hangyou.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterActivity extends AppCompatActivity {
    ResultSet rs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        DataBaseHelper helper=new DataBaseHelper(RegisterActivity.this);
        initClickListener();
    }

    private void register() throws SQLException {
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
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user where account=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, account);
                rs = ps.executeQuery();
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        if(rs.next()) {
            System.out.println("已注册");
            Toast.makeText(this, "账号已被注册，请重新输入", Toast.LENGTH_SHORT).show();
            et_account.setText("");
        }else if(password.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else if (phone.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                try {
                    Connection conn = MysqlConnector.getConnection();
                    String s = "INSERT INTO user(account, username, description, phone, gender, password) VALUES(?, ?, ?, ?, ?, ?)";
                    PreparedStatement p = conn.prepareStatement(s);
                    String description = "这个人很懒，什么都没有留下";
                    p.setString(1, account);
                    p.setString(2, username);
                    p.setString(3, description);
                    p.setString(4, phone);
                    p.setString(5, gender);
                    p.setString(6, password);
                    p.executeUpdate();
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            Intent intent = new Intent();
            intent.setClass(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void returnToLogin() {
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.register_register).setOnClickListener(v -> {
            try {
                register();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.register_return).setOnClickListener(v -> returnToLogin());
    }
}
