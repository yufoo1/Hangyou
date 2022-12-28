package com.example.hangyou.ui.tree_hole;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;

public class TreeHolePostRule extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_post_rule);
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
