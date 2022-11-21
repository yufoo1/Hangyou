package com.example.hangyou.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;

public class GuideActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_guide);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(GuideActivity.this, GroupActivity.class);
            startActivity(intent);
        }, 5000);
    }
}
