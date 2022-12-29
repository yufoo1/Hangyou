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

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchUserActivity extends AppCompatActivity {
    SQLiteDatabase database;
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    ArrayList<String> idLists = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_user);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.home_page_search_user).setBackgroundResource(R.color.gray_2); break;
        }
        DataBaseHelper helper=new DataBaseHelper(SearchUserActivity.this);
        initClickListener();
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text, username text, description text, phone text)");
        database.execSQL("create table if not exists follow(id integer primary key autoincrement, followerAccount text, followingAccount text)");
        Bundle receiver = getIntent().getExtras();
        String type = receiver.getString("type");
        Cursor cursor;
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        if(type.equals("showAll")) {
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
