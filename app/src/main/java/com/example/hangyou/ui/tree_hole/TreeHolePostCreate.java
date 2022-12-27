package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.EditText;

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;


public class TreeHolePostCreate extends AppCompatActivity {
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_create);
        DataBaseHelper helper = new DataBaseHelper(TreeHolePostCreate.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists post(id integer primary key autoincrement, postName text, postText text, createTime text, userId int ,FOREIGN KEY(userId) REFERENCES user(id) )");
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.click_to_post).setOnClickListener(v -> commit());
        findViewById(R.id.post_back_tree_hole).setOnClickListener(v -> returnToTreeHole());
    }

    private void returnToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(TreeHolePostCreate.this, TreeHoleActivity.class);
        startActivity(intent);
    }

    private void commit() {
        EditText et_postName = findViewById(R.id.postNameInput);
        String postName = et_postName.getText().toString();
        EditText et_postText = findViewById(R.id.postTextInput);
        String postText = et_postText.getText().toString();

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");

        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        String userId = cursor.getString(cursor.getColumnIndex("id"));

        database.execSQL("insert into post(postName, postText, createTime, userId,likeNum,commentNum,reportNum) values (?, ?, datetime('now','localtime'), ?, 0, 0, 0)", new Object[]{postName, postText, userId});
        System.out.println("帖子发布成功");
        Intent intent = new Intent();
        intent.setClass(TreeHolePostCreate.this, TreeHoleActivity.class);
        startActivity(intent);

    }
}
