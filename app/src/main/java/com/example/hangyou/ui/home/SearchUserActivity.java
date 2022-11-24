package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.group.GroupCardAdapter;
import com.example.hangyou.ui.group.GroupCardDetailActivity;
import com.example.hangyou.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchUserActivity extends AppCompatActivity {
    SQLiteDatabase database;
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    ArrayList<String> idLists = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_user);
        DataBaseHelper helper=new DataBaseHelper(SearchUserActivity.this);
        initClickListener();
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text, username text, description text, phone text)");
        database.execSQL("create table if not exists follow(id integer primary key autoincrement, followerAccount text, followingAccount text)");
        Bundle receiver = getIntent().getExtras();
        String type = receiver.getString("type");
        Cursor cursor;
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        if(type.isEmpty()) {
            cursor = database.rawQuery("select * from user", new String[]{});
        } else if (type.equals("showFollowers")) {
            cursor = database.rawQuery("select user.* from user inner join follow on user.account=follow.followerAccount and user.account=?", new String[]{account});
        } else if (type.equals("showFollowing")) {
            cursor = database.rawQuery("select user.* from user inner join follow on user.account=follow.followingAccount and user.account=?", new String[]{account});
        } else {
            cursor = database.rawQuery("select * from user", new String[]{});
        }
        while(cursor.moveToNext()) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("username", cursor.getString(cursor.getColumnIndex("username")));
            item.put("description", cursor.getString(cursor.getColumnIndex("description")));
            idLists.add(cursor.getString(cursor.getColumnIndex("id")));
            data.add(item);
        }
        cursor.close();
        System.out.println(data.size());
        UserCardAdapter adapter = new UserCardAdapter(SearchUserActivity.this, data);
        ListView userCards = findViewById(R.id.search_user_user_list);
        userCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        userCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                String followerAccount = sp.getString("account", "defaultValue");
                String followingId = idLists.get(position);
                Cursor cur = database.rawQuery("select * from user where id=?", new String[]{followingId});
                cur.moveToFirst();
                String followingAccount = cur.getString(cur.getColumnIndex("account"));
                cur.close();
                database.execSQL("insert into follow(followerAccount, followingAccount) values (?, ?)", new Object[]{followerAccount, followingAccount});
            }
        });
    }

    private void jumpToHome() {
        Intent intent = new Intent();
        intent.setClass(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.search_user_return).setOnClickListener(v -> jumpToHome());
    }
}
