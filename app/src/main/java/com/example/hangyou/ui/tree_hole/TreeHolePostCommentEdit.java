package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.utils.DataBaseHelper;

public class TreeHolePostCommentEdit extends AppCompatActivity {
    private int postId;
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_comment_create);
        DataBaseHelper helper = new DataBaseHelper(TreeHolePostCommentEdit.this);
        database = helper.getWritableDatabase();
        Bundle receiver = getIntent().getExtras();
        postId = receiver.getInt("postId");
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.click_to_comment).setOnClickListener(v -> commit());
        findViewById(R.id.comment_back_post).setOnClickListener(v -> returnToPost());
    }

    private void returnToPost() {
        Bundle bundle=new Bundle();
        bundle.putInt("id", postId);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(TreeHolePostCommentEdit.this, TreeHolePostView.class);
        startActivity(intent);
        finish();
    }

    private void commit() {
        EditText et_commentText = findViewById(R.id.post_comment_text_input);
        String commentText = et_commentText.getText().toString();

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        String userId = cursor.getString(cursor.getColumnIndex("id"));

        database.execSQL("insert into post_comment(commentText, commentTime, userId, postId) values (?, datetime('now','localtime'), ?, ?)", new Object[]{commentText, userId, String.valueOf(postId)});
        System.out.println("评论发布成功");

        Cursor cursor2 = database.rawQuery("select * from post where id=?", new String[]{String.valueOf(postId)});
        cursor2.moveToFirst();
        int commentNum = cursor2.getInt(cursor2.getColumnIndex("commentNum"));
        commentNum +=1;
        database.execSQL("UPDATE post SET commentNum = ? WHERE id = ?",new String[]{String.valueOf(commentNum),String.valueOf(postId)});

        Bundle bundle=new Bundle();
        bundle.putInt("id", postId);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(TreeHolePostCommentEdit.this, TreeHolePostView.class);
        startActivity(intent);
    }
}
