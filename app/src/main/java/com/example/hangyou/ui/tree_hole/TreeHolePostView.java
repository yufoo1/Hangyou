package com.example.hangyou.ui.tree_hole;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeHolePostView extends AppCompatActivity {
    private int id;
    private boolean reportState=false;
    private boolean likeState=false;
    SQLiteDatabase database;
    ArrayList<HashMap<String, Object>> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_detail);
        DataBaseHelper helper = new DataBaseHelper(TreeHolePostView.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists post(id integer primary key autoincrement, postName text, postText text, createTime text, userId int ,FOREIGN KEY(userId) REFERENCES user(id) )");
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
        showCommentCards();
    }

    private void initClickListener() {
        findViewById(R.id.post_view_back_tree_hode).setOnClickListener(v -> jumpToTreeHole());
        findViewById(R.id.likePost).setOnClickListener(v -> likePost());
        findViewById(R.id.commentPost).setOnClickListener(v -> commentPost());
        findViewById(R.id.reportPost).setOnClickListener(v -> reportPost());
    }

    private void reportPost() {
        Cursor cursor = database.rawQuery("select * from post where id=?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        int reportNum = cursor.getInt(cursor.getColumnIndex("reportNum"));
        if(!reportState){
            reportNum+=1;
            reportState = true;
        } else {
            reportNum-=1;
            reportState = false;
        }
        database.execSQL("UPDATE post SET reportNum = ? WHERE ID = ?",new String[]{String.valueOf(reportNum),String.valueOf(id)});
    }

    private void commentPost() {
        Bundle bundle=new Bundle();
        bundle.putInt("postId", id);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, TreeHolePostCommentEdit.class);
        startActivity(intent);
        finish();
    }

    private void likePost() {
        Cursor cursor = database.rawQuery("select * from post where id=?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        int likeNum = cursor.getInt(cursor.getColumnIndex("likeNum"));
        if(!likeState){
            likeNum+=1;
            likeState = true;
        } else {
            likeNum-=1;
            likeState = false;
        }
        database.execSQL("UPDATE post SET likeNum = ? WHERE ID = ?",new String[]{String.valueOf(likeNum),String.valueOf(id)});

    }

    private void jumpToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(this, TreeHoleActivity.class);
        startActivity(intent);
    }

    private void showCommentCards() {
        data = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from post_comment where postId=?", new String[]{String.valueOf(id)});
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        while(cursor.moveToNext()) {
            item = new HashMap<>();
            item.put("commentText", cursor.getString(cursor.getColumnIndex("commentText")));
            item.put("commentTime", cursor.getString(cursor.getColumnIndex("commentTime")));
            int userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("userId")));

            Cursor cursor2 = database.rawQuery("select * from user where id=?", new String[]{String.valueOf(userId)});
            cursor2.moveToFirst();
            item.put("commentUsername", cursor2.getString(cursor2.getColumnIndex("username")));
            cursor2.close();

            item.put("id", cursor.getString(cursor.getColumnIndex("id")));
            idLists.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            data.add(item);
        }
        cursor.close();
        PostCommentAdapter adapter = new PostCommentAdapter(TreeHolePostView.this, data);
        ListView postCards = findViewById(R.id.post_comment_cards);
        postCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
