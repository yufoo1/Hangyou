package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;


public class ChangeThemeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        setContentView(R.layout.change_theme_page);
        switch (theme) {
            case 0: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.change_theme_page).setBackgroundResource(R.color.gray_2); break;
        }

        RadioButton rb_type0 = findViewById(R.id.change_theme_type_0);
        RadioButton rb_type1 = findViewById(R.id.change_theme_type_1);
        RadioButton rb_type2 = findViewById(R.id.change_theme_type_2);
        RadioButton rb_type3 = findViewById(R.id.change_theme_type_3);
        RadioButton rb_type4 = findViewById(R.id.change_theme_type_4);
        RadioButton rb_type5 = findViewById(R.id.change_theme_type_5);
        RadioButton rb_type6 = findViewById(R.id.change_theme_type_6);
        RadioButton rb_type7 = findViewById(R.id.change_theme_type_7);
        findViewById(R.id.change_theme_button_created).setOnClickListener(v -> commit());
        findViewById(R.id.change_theme_button_return).setOnClickListener(v -> returnToHome());
        findViewById(R.id.change_theme_type_0).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.change_theme_type_1).setOnClickListener(v -> {
            rb_type0.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.change_theme_type_2).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type0.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.change_theme_type_3).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type0.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.change_theme_type_4).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type0.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.change_theme_type_5).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type0.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.change_theme_type_6).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type0.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.change_theme_type_7).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type0.setChecked(false);
        });
    }

    private void commit() {
        RadioButton rb_type0 = findViewById(R.id.change_theme_type_0);
        RadioButton rb_type1 = findViewById(R.id.change_theme_type_1);
        RadioButton rb_type2 = findViewById(R.id.change_theme_type_2);
        RadioButton rb_type3 = findViewById(R.id.change_theme_type_3);
        RadioButton rb_type4 = findViewById(R.id.change_theme_type_4);
        RadioButton rb_type5 = findViewById(R.id.change_theme_type_5);
        RadioButton rb_type6 = findViewById(R.id.change_theme_type_6);
        RadioButton rb_type7 = findViewById(R.id.change_theme_type_7);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(rb_type0.isChecked()) {
            editor.putInt("theme", 0);
        } else if(rb_type1.isChecked()) {
            editor.putInt("theme", 1);
        } else if(rb_type2.isChecked()) {
            editor.putInt("theme", 2);
        } else if(rb_type3.isChecked()) {
            editor.putInt("theme", 3);
        } else if(rb_type4.isChecked()) {
            editor.putInt("theme", 4);
        } else if(rb_type5.isChecked()) {
            editor.putInt("theme", 5);
        } else if(rb_type6.isChecked()) {
            editor.putInt("theme", 6);
        } else {
            editor.putInt("theme", 7);
        }
        editor.apply();
        Intent intent = new Intent();
        intent.setClass(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void returnToHome() {
        Intent intent = new Intent();
        intent.setClass(this, HomePageActivity.class);
        startActivity(intent);
    }
}
