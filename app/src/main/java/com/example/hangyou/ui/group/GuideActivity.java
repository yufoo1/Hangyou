package com.example.hangyou.ui.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;

public class GuideActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_guide);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_guide).setBackgroundResource(R.color.gray_2); break;
        }
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(GuideActivity.this, GroupActivity.class);
            startActivity(intent);
        }, 5000);
    }
}
