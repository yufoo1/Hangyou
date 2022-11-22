package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;

public class HomePageActivity extends AppCompatActivity {
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_page);
        DataBaseHelper helper=new DataBaseHelper(HomePageActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists follow(id integer primary key autoincrement, followerAccount text, followingAccount text)");
        initTextView();
    }

    private void initTextView() {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        System.out.println(account);
        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        TextView username = findViewById(R.id.home_page_username);
        username.setText(cursor.getString(cursor.getColumnIndex("username")));
        int cnt;
        TextView followers = findViewById(R.id.home_page_followers);
        cursor = database.rawQuery("select * from follower where followerAccount=?", new String[]{account});
        cursor.moveToFirst();
        cnt = 0;
        if (cursor.moveToFirst()) {
            cnt++;
            while(cursor.moveToNext()) {
                cnt++;
            }
        }
        followers.setText(String.valueOf(cnt));
        TextView following = findViewById(R.id.home_page_following);
        cursor = database.rawQuery("select * from follower where followingAccount=?", new String[]{account});
        cursor.moveToFirst();
        cnt = 0;
        if (cursor.moveToFirst()) {
            cnt++;
            while(cursor.moveToNext()) {
                cnt++;
            }
        }
        following.setText(String.valueOf(cnt));
        TextView description = findViewById(R.id.home_page_description);
        description.setText(cursor.getString(cursor.getColumnIndex("description")));
        cursor.close();
    }
}
