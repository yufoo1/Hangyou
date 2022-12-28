package com.example.hangyou;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.hangyou.databinding.ActivityMainBinding;
import com.example.hangyou.ui.login.LoginActivity;
import com.example.hangyou.utils.MysqlConnector;


import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }, 3000);
    }
}