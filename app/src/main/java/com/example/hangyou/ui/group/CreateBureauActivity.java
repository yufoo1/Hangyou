package com.example.hangyou.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.MainActivity;
import com.example.hangyou.R;
import com.example.hangyou.ui.login.LoginActivity;

public class CreateBureauActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_bureau);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(CreateBureauActivity.this, GroupActivity.class);
            startActivity(intent);
        }, 5000);
    }
}
