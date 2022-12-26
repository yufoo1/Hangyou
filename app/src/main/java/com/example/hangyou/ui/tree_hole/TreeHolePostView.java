package com.example.hangyou.ui.tree_hole;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.AddGroupPageActivity;
import com.example.hangyou.ui.group.GroupActivity;

public class TreeHolePostView extends AppCompatActivity {
    private int id;
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_detail);
        DataBaseHelper helper = new DataBaseHelper(TreeHolePostView.this);
        database = helper.getWritableDatabase();
        Bundle receiver = getIntent().getExtras();
        id = receiver.getInt("id");
        initView();
        initClickListener();
    }

    private void initView() {
        Cursor cursor = database.rawQuery("select * from post where id=?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        String postName = cursor.getString(cursor.getColumnIndex("postName"));
        ((TextView) (findViewById(R.id.postName))).setText(postName);
        String postText = cursor.getString(cursor.getColumnIndex("postText"));
        ((TextView) (findViewById(R.id.postText))).setText(postText);
        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        ((TextView) (findViewById(R.id.postTime))).setText(createTime);
        int userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("userId")));
        Cursor cursor2 = database.rawQuery("select * from user where id=?", new String[]{String.valueOf(userId)});
        cursor2.moveToFirst();
        String username = cursor2.getString(cursor2.getColumnIndex("username"));
        ((TextView) (findViewById(R.id.postUserName))).setText(username);
        cursor2.close();
    }

    private void initClickListener() {
        findViewById(R.id.post_view_back_tree_hode).setOnClickListener(v -> jumpToTreeHole());
    }

    private void jumpToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(this, TreeHoleActivity.class);
        startActivity(intent);
    }
}
