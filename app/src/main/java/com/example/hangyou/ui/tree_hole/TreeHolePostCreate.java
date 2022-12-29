package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.EditText;

import com.example.hangyou.R;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class TreeHolePostCreate extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_create);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_tree_hole_create).setBackgroundResource(R.color.gray_2); break;
        }
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.click_to_post).setOnClickListener(v -> {
            try {
                commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.post_back_tree_hole).setOnClickListener(v -> returnToTreeHole());
    }

    private void returnToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(TreeHolePostCreate.this, TreeHoleActivity.class);
        startActivity(intent);
    }

    private void commit() throws SQLException {
        EditText et_postName = findViewById(R.id.postNameInput);
        String postName = et_postName.getText().toString();
        EditText et_postText = findViewById(R.id.postTextInput);
        String postText = et_postText.getText().toString();

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");


        AtomicReference<ResultSet> resultSet2 = new AtomicReference<>();
        AtomicBoolean flag2 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user where account=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,account);
                resultSet2.set(ps.executeQuery());
                flag2.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag2.get());
        resultSet2.get().first();

        String userId = resultSet2.get().getString("id");

        flag2.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "insert into post(postName, postText, userId,likeNum," +
                        " commentNum,reportNum) values (?, ?, ?, 0, 0, 0)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, postName);
                ps.setString(2, postText);
                ps.setString(3, userId);
                ps.executeUpdate();
                flag2.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag2.get());

        System.out.println("帖子发布成功");
        Intent intent = new Intent();
        intent.setClass(TreeHolePostCreate.this, TreeHoleActivity.class);
        startActivity(intent);

    }
}
