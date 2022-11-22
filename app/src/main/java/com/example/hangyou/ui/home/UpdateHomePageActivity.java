package com.example.hangyou.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.group.GuideActivity;

public class UpdateHomePageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_home_page);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(UpdateHomePageActivity.this, HomePageActivity.class);
            startActivity(intent);
        }, 5000);
    }


}
