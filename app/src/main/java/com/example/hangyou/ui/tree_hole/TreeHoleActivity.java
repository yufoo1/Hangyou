package com.example.hangyou.ui.tree_hole;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.MainActivity;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.home.HomePageActivity;

public class TreeHoleActivity extends AppCompatActivity {
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole);
        RadioGroup mRadioGroup=findViewById(R.id.tree_hole_tabs);
        mRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            Intent intent = new Intent();
            switch (i) {
                case R.id.tree_hole_group:
                    intent.setClass(this, GroupActivity.class);
                    break;
                case R.id.tree_hole_tree_hole:
                    intent.setClass(this, TreeHoleActivity.class);
                    break;
                case R.id.tree_hole_home_page:
                    intent.setClass(this, HomePageActivity.class);
                    break;
            }
            startActivity(intent);
        });
    }
}
