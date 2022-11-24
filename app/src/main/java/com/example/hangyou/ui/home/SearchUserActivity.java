package com.example.hangyou.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.group.GroupCardAdapter;
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
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text, username text, description text, phone text)");
        Cursor cursor = database.rawQuery("select * from user", new String[]{});
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
    }
}
