package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ShieldActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shield);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_shield).setBackgroundResource(R.color.gray_2); break;
        }
        try {
            showTotalUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initClickListener();
    }

    private void showTotalUser() throws SQLException {
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        TextView textTotalPeople = findViewById(R.id.shield_total_people);
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user";
                PreparedStatement ps = connection.prepareStatement(sql);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        int cnt = 0;
        while(resultSet.get().next()) {
            cnt++;
        }
        textTotalPeople.setText(String.valueOf(cnt));
    }

    private void commitFeedback() {
        Toast.makeText(this, "提交成功:)", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void jumpToHome() {
        Intent intent = new Intent();
        intent.setClass(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.feed_back_button_created).setOnClickListener(v -> commitFeedback());
        findViewById(R.id.feed_back_button_return).setOnClickListener(v -> jumpToHome());
    }
}
