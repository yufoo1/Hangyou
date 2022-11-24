package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.MainActivity;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.group.GuideActivity;
import com.example.hangyou.ui.tree_hole.TreeHoleActivity;

public class HomePageActivity extends AppCompatActivity {
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_page);
        DataBaseHelper helper = new DataBaseHelper(HomePageActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists follow(id integer primary key autoincrement, followerAccount text, followingAccount text)");
        initTextView();
        initClickListener();
        RadioGroup mRadioGroup=findViewById(R.id.home_page_tabs);
        mRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            Intent intent = new Intent();
            switch (i) {
                case R.id.home_page_group:
                    intent.setClass(HomePageActivity.this, GroupActivity.class);
                    break;
                case R.id.home_page_tree_hole:
                    intent.setClass(HomePageActivity.this, TreeHoleActivity.class);
                    break;
                case R.id.home_page_home_page:
                    intent.setClass(HomePageActivity.this, HomePageActivity.class);
                    break;
            }
            startActivity(intent);
        });
    }

    private void initTextView() {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        System.out.println(account);
        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        TextView username = findViewById(R.id.home_page_username);
        System.out.println(cursor.getColumnIndex("username"));
        username.setText(cursor.getString(cursor.getColumnIndex("username")));
        TextView description = findViewById(R.id.home_page_description);
        description.setText(cursor.getString(cursor.getColumnIndex("description")));
        int cnt;
        TextView followers = findViewById(R.id.home_page_followers);
        cursor = database.rawQuery("select * from follow where followerAccount=?", new String[]{account});
        cursor.moveToFirst();
        cnt = 0;
        if (cursor.moveToFirst()) {
            cnt++;
            while(cursor.moveToNext()) {
                cnt++;
            }
        }
        cursor.close();
        followers.setText(String.valueOf(cnt));
        TextView following = findViewById(R.id.home_page_following);
        cursor = database.rawQuery("select * from follow where followingAccount=?", new String[]{account});
        cursor.moveToFirst();
        cnt = 0;
        if (cursor.moveToFirst()) {
            cnt++;
            while(cursor.moveToNext()) {
                cnt++;
            }
        }
        following.setText(String.valueOf(cnt));
        cursor.close();
    }

    private void jumpToUpdateHomePage() {
        Intent intent = new Intent();
        intent.setClass(HomePageActivity.this, UpdateHomePageActivity.class);
        startActivity(intent);
    }

    private void jumpToSearchUser() {
        Intent intent = new Intent();
        intent.setClass(HomePageActivity.this, SearchUserActivity.class);
        startActivity(intent);
    }

    private void jumpToShowFollowers() {
        Intent intent = new Intent();
        Bundle receive = new Bundle();
        receive.putString("type", "showFollowers");
        intent.putExtras(receive);
        intent.setClass(this, SearchUserActivity.class);
        startActivity(intent);
    }

    private void jumpToShowFollowings() {
        Intent intent = new Intent();
        Bundle receive = new Bundle();
        receive.putString("type", "showFollowing");
        intent.putExtras(receive);
        intent.setClass(this, SearchUserActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.home_page_update).setOnClickListener(v -> jumpToUpdateHomePage());
        findViewById(R.id.home_page_search_user).setOnClickListener(v -> jumpToSearchUser());
        findViewById(R.id.home_page_followers).setOnClickListener(v -> jumpToShowFollowers());
        findViewById(R.id.home_page_following).setOnClickListener(v -> jumpToShowFollowings());
    }
}
