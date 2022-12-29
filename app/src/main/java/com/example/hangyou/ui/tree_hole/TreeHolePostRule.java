package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;

public class TreeHolePostRule extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_post_rule);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_tree_hole_post_rule).setBackgroundResource(R.color.gray_2); break;
        }
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.rule_back_treehole).setOnClickListener(v -> jumpToTreeHole());
    }

    private void jumpToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(this, TreeHoleActivity.class);
        startActivity(intent);
    }

}
