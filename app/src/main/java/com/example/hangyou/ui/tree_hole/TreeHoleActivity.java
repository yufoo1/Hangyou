package com.example.hangyou.ui.tree_hole;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.home.HomePageActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeHoleActivity extends AppCompatActivity {
    SQLiteDatabase database;
    ArrayList<HashMap<String, Object>> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole);
        initClickListener();
        DataBaseHelper helper = new DataBaseHelper(TreeHoleActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists post(id integer primary key autoincrement, postName text, postText text, createTime text, userId int ,FOREIGN KEY(userId) REFERENCES user(id) )");
        showPostCards();
    }


    private void initClickListener() {
        findViewById(R.id.click_begin_post).setOnClickListener(v -> jumpToCreate());
        findViewById(R.id.tree_hole_group).setOnClickListener(v -> jumpToGroup());
        findViewById(R.id.tree_hole_home_page).setOnClickListener(v -> jumpToHomePage());
    }

    private void jumpToGroup() {
        Intent intent = new Intent();
        intent.setClass(TreeHoleActivity.this, GroupActivity.class);
        startActivity(intent);
    }

    private void jumpToHomePage() {
        Intent intent = new Intent();
        intent.setClass(TreeHoleActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    private void jumpToCreate() {
        Intent intent = new Intent();
        intent.setClass(TreeHoleActivity.this, TreeHolePostCreate.class);
        startActivity(intent);
    }

    private void showPostCards() {
        data = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from post", new String[]{});
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        while(cursor.moveToNext()) {
            item = new HashMap<>();
            item.put("postName", cursor.getString(cursor.getColumnIndex("postName")));
            item.put("postTime", cursor.getString(cursor.getColumnIndex("createTime")));
            int userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("userId")));
            Cursor cursor2 = database.rawQuery("select * from user where id=?", new String[]{String.valueOf(userId)});
            cursor2.moveToFirst();
            item.put("postUser", cursor2.getString(cursor2.getColumnIndex("username")));
            /*while(cursor2.moveToNext()) {
                System.out.println(cursor2.getString(cursor2.getColumnIndex("id")));
                System.out.println(userId);
                if(Objects.equals(cursor2.getString(cursor2.getColumnIndex("id")), String.valueOf(userId))){
                    item.put("postUser", cursor2.getString(cursor2.getColumnIndex("username")));
                    break;
                }
            }*/
            cursor2.close();
            item.put("id", cursor.getString(cursor.getColumnIndex("id")));
            idLists.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            data.add(item);
        }
        cursor.close();
        PostCardAdapter adapter = new PostCardAdapter(TreeHoleActivity.this, data);
        ListView postCards = findViewById(R.id.post_cards);
        postCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        postCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(TreeHoleActivity.this, TreeHolePostView.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

}
